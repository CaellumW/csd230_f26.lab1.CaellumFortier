import { useState } from 'react';
import useAuth from "./useAuth.js";
import api from "./api.js";

export default function AddColourGelForm({ onColourGelAdded }) {
    const { token } = useAuth();
    const isCurrent = () => localStorage.getItem('token') === token;
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
            const response = await api.post('/gelcolourpolishes/add', {shadeName, gelType, colour, glitter, price: Number(price), stock: Number(stock)});
            const savedGelColour = response.data;
            if (response.status !== 200) throw new Error(`Save failed (${response.status})`);
            if (!isCurrent()) return;
            onColourGelAdded(savedGelColour);
            setShadeName('');
            setGelType('');
            setColour('');
            setGlitter(false);
            setPrice('');
            setStock(0);
        } catch (err) { if (isCurrent()) setError(err.message);}
        finally { if (isCurrent()) setSaving(false); }
    }

    return <form onSubmit={handleSubmit}>
            <h2>Add New Colour Gel</h2>
            <label>Shade Name<input value={shadeName} onChange={e => setShadeName(e.target.value)} required/></label>
            <label>Gel Type<input value={gelType} onChange={e => setGelType(e.target.value)} required/></label>
            <label>Colour<input value={colour} onChange={e => setColour(e.target.value)} required/></label>
            <label>Glitter<input type="checkbox" checked={glitter} onChange={e => setGlitter(e.target.checked)}/></label>
            <label>Price<input type="number" min="0" step="0.01" value={price} onChange={e => setPrice(e.target.value)} required/></label>
            <label>Stock<input type="number" min="0" step="1" value={stock} onChange={e => setStock(e.target.value)} required/></label>
            <button disabled={saving}>Save Colour Gel</button>
            {error && <p role="alert">{error}</p>}
        </form>;
}