import { useState } from 'react';

export default function AddBuilderGelForm({ onBuilderGelAdded }) {
    const [shadeName, setShadeName] = useState('');
    const [gelType, setGelType] = useState('');
    const [price, setPrice] = useState('');
    const [stock, setStock] = useState('');
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setError('');
        setSaving(true);

        try {
            const response = await fetch('/api/rest/buildergelpolishes/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    shadeName,
                    gelType,
                    price: Number(price),
                    stock: Number(stock)
                })
            });

            if (!response.ok) {
                throw new Error(`Save failed (${response.status})`);
            }

            const savedBuilderGel = await response.json();

            onBuilderGelAdded(savedBuilderGel);

            setShadeName('');
            setGelType('');
            setPrice('');
            setStock('');
        }
        catch (err) {
            setError(err.message);
        }
        finally {
            setSaving(false);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Add Builder Gel</h2>
            <label>Shade Name<input value={shadeName} onChange={e => setShadeName(e.target.value)} required/></label>
            <label>Gel Type<input value={gelType} onChange={e => setGelType(e.target.value)} required/></label>
            <label>Price<input
                    type="number"
                    min="0"
                    step="0.01"
                    value={price}
                    onChange={e => setPrice(e.target.value)}
                    required
                /></label>
            <label>Stock<input
                    type="number"
                    min="0"
                    step="1"
                    value={stock}
                    onChange={e => setStock(e.target.value)}
                    required
                /></label>
            <button disabled={saving}>
                Save Builder Gel
            </button>
            {error && <p role="alert">{error}</p>}
        </form>
    );
}