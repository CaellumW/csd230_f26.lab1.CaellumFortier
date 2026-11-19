import { useState } from 'react';

export default function GelColour({ id, gelType, price, shadeName, colour, stock, glitter, onDelete, onUpdate }) {
    const [isEditing, setIsEditing] = useState(false);
    const [draft, setDraft] = useState({gelType, price, shadeName, colour, stock, glitter});
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    function beginEdit() {
        setDraft({gelType, price, shadeName, colour, stock, glitter});
        setError(''); setIsEditing(true);
    }
    function handleChange(event) {
        const { name, value } = event.target;
        setDraft(previous => ({ ...previous, [name]: value }));
    }

    async function handleSave(event) {
        event.preventDefault(); setError(''); setSaving(true);
        try {
            await onUpdate(id, {
                ...draft,
                price: Number(draft.price),
                stock: Number(draft.stock) });
            setIsEditing(false);
        } catch (err) { setError(err.message); }
        finally { setSaving(false); }
    }

    if (isEditing) return <form className="gelcolour-row editing" onSubmit={handleSave}>
        <label>Shade Name<input name="shadeName" value={draft.shadeName} onChange={handleChange} required/></label>
        <label>Gel Type<input name="gelType" value={draft.gelType} onChange={handleChange} required/></label>
        <label>Colour<input name="colour" value={draft.colour} onChange={handleChange} required/></label>
        <label>Glitter<input type="checkbox" checked={draft.glitter} onChange={(e) => setDraft(previous => ({...previous, glitter: e.target.checked}))}/></label>
        <label>Price<input name="price" type="number" min="0" step="0.01" value={draft.price} onChange={handleChange} required/></label>
        <label>Stock<input name="stock" type="number" min="0" step="1" value={draft.stock} onChange={handleChange} required/></label>
        <button disabled={saving}>Save</button>
        <button type="button" disabled={saving} onClick={() => setIsEditing(false)}>Cancel</button>
        {error && <p role="alert">{error}</p>}
    </form>;

    return <article className="gelcolour-row">
        <h3>{shadeName}</h3>
        <p>{gelType} | {colour} | glitter: {glitter ? "Yes" : "No"} | ${Number(price).toFixed(2)} | stock: {stock}</p>
        <button onClick={beginEdit}>Edit</button>
        <button onClick={() => onDelete(id)}>Delete</button>
    </article>;
}