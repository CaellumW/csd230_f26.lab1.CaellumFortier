import { useState } from 'react';

export default function AddBuilderGelPolishForm({onBuilderPolishAdded}) {
    const [stock, setStock] = useState(0);
    const [shadeName, setShadeName] = useState('');
    const [gelType, setGelType] = useState('');
    const [consistency, setConsistency] = useState('');
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setError('');
        setSaving(true);
        try {const response = await fetch(
                '/api/rest/buildergelpolishes/add',
                {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify({
                        stock: Number(stock),
                        shadeName,
                        gelType,
                        consistency
                    })
                }
            );

            if (!response.ok) {
                throw new Error(
                    `Save failed (${response.status})`
                );
            }

            const savedPolish = await response.json();

            onBuilderPolishAdded(savedPolish);

            setStock(0);
            setShadeName('');
            setGelType('');
            setConsistency('');
        } catch (err) {
            setError(err.message);
        } finally {
            setSaving(false);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Add New Builder Gel Polish</h2>

            <label>
                Shade Name
                <input
                    value={shadeName}
                    onChange={e =>
                        setShadeName(e.target.value)
                    }
                    required
                />
            </label>

            <label>
                Gel Type
                <input
                    value={gelType}
                    onChange={e =>
                        setGelType(e.target.value)
                    }
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
                    onChange={e =>
                        setStock(e.target.value)
                    }
                    required
                />
            </label>

            <label>
                Consistency
                <input
                    value={consistency}
                    onChange={e =>
                        setConsistency(e.target.value)
                    }
                    required
                />
            </label>

            <button disabled={saving}>
                Save Builder Gel
            </button>

            {error && (
                <p role="alert">
                    {error}
                </p>
            )}
        </form>
    );
}