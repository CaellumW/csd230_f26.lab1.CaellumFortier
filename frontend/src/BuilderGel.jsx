import { useState } from 'react';

export default function BuilderGel({ buildergel, onDelete, onUpdate, onAddToCart, cartPending, canManage }) {
    const [isEditing, setIsEditing] = useState(false);
    const [draft, setDraft] = useState(buildergel);
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    function beginEdit() {
        setDraft(buildergel);
        setError(''); setIsEditing(true);
    }

    function handleChange(event) {
        const { name, value } = event.target;
        setDraft(previous => ({ ...previous, [name]: value }));
    }

    async function handleSave(event) {
        event.preventDefault(); setError(''); setSaving(true);
        try {
            await onUpdate(buildergel.id, {...draft, price: Number(draft.price), stock: Number(draft.stock) });
            setIsEditing(false);
        } catch (err) { setError(err.message); }
        finally { setSaving(false); }
    }
    if (isEditing) return <form className="buildergel-row" onSubmit={handleSave}>
        <label>Shade Name<input name="shadeName" value={buildergel.shadeName} onChange={handleChange} required /></label>
        <label>Gel Type<input name="gelType" value={buildergel.gelType} onChange={handleChange} required /></label>
        <label>Consistency<input name="consistency" value={buildergel.consistency} onChange={handleChange} required /></label>
        <label>Price<input name="price" type="number" min="0" step="0.01" value={buildergel.price} onChange={handleChange} required /></label>
        <label>Stock<input name="stock" type="number" min="0" step="1" value={buildergel.stock} onChange={handleChange} required /></label>
        <button disabled={saving}>Save</button>
        <button type="button" disabled={saving} onClick={() => setIsEditing(false)}>Cancel</button>
        {error && <p role="alert">{error}</p>}
    </form>;

    return <article className="buildergel-row">
        <h3>{buildergel.shadeName}</h3>
        <p>{buildergel.gelType} | ${Number(buildergel.price).toFixed(2)} | Stock: {buildergel.stock}</p>
        <button disabled={cartPending} onClick={() => onAddToCart(buildergel.id)}>Add to Cart</button>
        {canManage && <button onClick={beginEdit}>Edit</button>}
        {canManage && <button onClick={() => onDelete(buildergel.id)}>Delete</button>}
    </article>;
}