import {useState} from 'react';

export default function BuilderGelPolish({polish, onDelete, onUpdate}) {
    const [isEditing, setIsEditing] = useState(false);
    const [draft, setDraft] = useState(polish);
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    function beginEdit() {
        setDraft(polish);
        setError('');
        setIsEditing(true);
    }

    function handleChange(event) {
        const {name, value} = event.target;

        setDraft(previous => ({
            ...previous,
            value
        }));
    }

    async function handleSave(event) {
        event.preventDefault();
        setError('');
        setSaving(true);

        try {
            await onUpdate(polish.id, {
                ...draft,
                stock: Number(draft.stock)
            });

            setIsEditing(false);
        } catch (err) {
            setError(err.message);
        } finally {
            setSaving(false);
        }
    }

    if (isEditing) {
        return (
            <form
                className="polish-row editing"
                onSubmit={handleSave}
            >
                <label>
                    Shade Name
                    <input
                        name="shadeName"
                        value={draft.shadeName}
                        onChange={handleChange}
                        required
                    />
                </label>

                <label>
                    Gel Type
                    <input
                        name="gelType"
                        value={draft.gelType}
                        onChange={handleChange}
                        required
                    />
                </label>

                <label>
                    Stock
                    <input
                        name="stock"
                        type="number"
                        min="0"
                        step="1"
                        value={draft.stock}
                        onChange={handleChange}
                        required
                    />
                </label>

                <label>
                    Consistency
                    <input
                        name="consistency"
                        value={draft.consistency}
                        onChange={handleChange}
                        required
                    />
                </label>

                <button disabled={saving}>
                    Save
                </button>

                <button
                    type="button"
                    disabled={saving}
                    onClick={() => setIsEditing(false)}
                >
                    Cancel
                </button>

                {error && (
                    <p role="alert">
                        {error}
                    </p>
                )}
            </form>
        );
    }

    return (
        <article className="polish-row">
            <h3>{polish.shadeName}</h3>

            <p>
                Type: {polish.gelType}
                {' | '}
                Stock: {polish.stock}
                {' | '}
                Consistency: {polish.consistency}
            </p>

            <button onClick={beginEdit}>
                Edit
            </button>

            <button
                onClick={() => onDelete(polish.id)}
            >
                Delete
            </button>
        </article>
    );
}