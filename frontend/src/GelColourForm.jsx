import { useState } from 'react';

export default function AddColourGelForm({ onColourGelAdded }) {
    const [shadeName, setShadeName] = useState('');
    const [gelType, setGelType] = useState('');
    const [colour, setColour] = useState('');
    const [glitter, setGlitter] = useState(false);
    const [price, setPrice] = useState('');
    const [stock, setStock] = useState(0);
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setError('');
        setSaving(true);

        try {
            const response = await fetch('/api/rest/gelcolourpolishes/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    shadeName,
                    gelType,
                    colour,
                    glitter,
                    price: Number(price),
                    stock: Number(stock)
                })
            });

            if (!response.ok) {
                throw new Error(`Save failed (${response.status})`);
            }

            const savedColourGel = await response.json();

            onColourGelAdded(savedColourGel);

            setShadeName('');
            setGelType('');
            setColour('');
            setGlitter(false);
            setPrice('');
            setStock(0);
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
            <h2>Add New Colour Gel</h2>

            <label>
                Shade Name
                <input
                    value={shadeName}
                    onChange={e => setShadeName(e.target.value)}
                    required
                />
            </label>

            <label>
                Gel Type
                <input
                    value={gelType}
                    onChange={e => setGelType(e.target.value)}
                    required
                />
            </label>

            <label>
                Colour
                <input
                    value={colour}
                    onChange={e => setColour(e.target.value)}
                    required
                />
            </label>

            <label>
                Glitter
                <input
                    type="checkbox"
                    checked={glitter}
                    onChange={e => setGlitter(e.target.checked)}
                />
            </label>

            <label>
                Price
                <input
                    type="number"
                    min="0"
                    step="0.01"
                    value={price}
                    onChange={e => setPrice(e.target.value)}
                    required
                />
            </label>

            <label>
                Stock
                <input
                    type="number"
                    min="0"
                    step="1"
                    value={stock}
                    onChange={e => setStock(e.target.value)}
                    required
                />
            </label>

            <button disabled={saving}>
                Save Colour Gel
            </button>

            {error && <p role="alert">{error}</p>}
        </form>
    );
}