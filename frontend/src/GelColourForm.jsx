import { useState } from 'react';

export default function AddGelColourPolishForm({ onPolishAdded }) {
    const [stock, setStock] = useState(0);
    const [shadeName, setShadeName] = useState('');
    const [gelType, setGelType] = useState('');
    const [colour, setColour] = useState('');
    const [glitter, setGlitter] = useState(false);

    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setError('');
        setSaving(true);

        try {
            const response = await fetch('/api/rest/gel-colour-polish/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    stock: Number(stock),
                    shadeName,
                    gelType,
                    colour,
                    glitter
                })
            });

            if (!response.ok) {
                throw new Error(`Save failed (${response.status})`);
            }

            const savedPolish = await response.json();

            onPolishAdded(savedPolish);

            setStock(0);
            setShadeName('');
            setGelType('');
            setColour('');
            setGlitter(false);
        } catch (err) {
            setError(err.message);
        } finally {
            setSaving(false);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Add New Gel Colour Polish</h2>

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

            <label>
                Glitter
                <input
                    type="checkbox"
                    checked={glitter}
                    onChange={e => setGlitter(e.target.checked)}
                />
            </label>

            <button disabled={saving}>
                Save Polish
            </button>

            {error && (
                <p role="alert">
                    {error}
                </p>
            )}
        </form>
    );
}