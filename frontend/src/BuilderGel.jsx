import { useState } from 'react';

export default function BuilderGel({ builderGel, onDelete, onUpdate }) {
    const [isEditing, setIsEditing] = useState(false);
    const [draft, setDraft] = useState(builderGel);
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    function beginEdit() {
        setDraft(builderGel);
        setError(''); setIsEditing(true);
    }
    function handleChange(event) {
        const { name, value } = event.target;
        setDraft(previous => ({ ...previous, [name]: value }));
    }
    async function handleSave(event) {
        event.preventDefault(); setError(''); setSaving(true);
        try {
            await onUpdate(builderGel.id, { ...draft, price: Number(draft.price), stock: Number(draft.stock) });
            setIsEditing(false);
        } catch (err) { setError(err.message); }
        finally { setSaving(false); }
    }
    if (isEditing) return <form className="builderGel-row editing" onSubmit={handleSave}>
        <label>Shade Name<input name="shadeName" value={draft.shadeName} onChange={handleChange} required /></label>
        <label>Gel Type<input name="gelType" value={draft.gelType} onChange={handleChange} required /></label>
        <label>Consistency<input name="consistency" value={draft.consistency} onChange={handleChange} required /></label>
        <label>Price<input name="price" type="number" min="0" step="0.01" value={draft.price} onChange={handleChange} required /></label>
        <label>Stock<input name="stock" type="number" min="0" step="1" value={draft.stock} onChange={handleChange} required /></label>
        <button disabled={saving}>Save</button>
        <button type="button" disabled={saving} onClick={() => setIsEditing(false)}>Cancel</button>
        {error && <p role="alert">{error}</p>}
    </form>;

    return <article className="builderGel-row">
        <h3>{builderGel.shadeName}</h3>
        <p>{builderGel.gelType} | ${Number(builderGel.price).toFixed(2)} | Stock: {builderGel.stock}</p>
        <button onClick={beginEdit}>Edit</button>
        <button onClick={() => onDelete(builderGel.id)}>Delete</button>
    </article>;
}