import useAuth from "./useAuth.js";
import {useState} from 'react';
import api from "./api.js";

export default function AddBuilderGelForm({ onBuilderGelAdded }) {
    const { token } = useAuth();
    const isCurrent = () => localStorage.getItem('token') === token;
    const [shadeName, setShadeName] = useState('');
    const [gelType, setGelType] = useState('');
    const [price, setPrice] = useState('');
    const [stock, setStock] = useState('');
    const [consistency, setConsistency] = useState('');
    const [error, setError] = useState('');
    const [saving, setSaving] = useState(false);

    async function handleSubmit(event) {
        event.preventDefault();
        setError('');
        setSaving(true);
        try {
            const response = await api.post('/buildergelpolishes/add', {shadeName, gelType, price: Number(price), stock: Number(stock), consistency});
            const savedBuilderGel = response.data;
            if (response.status !== 200) {throw new Error(`Save failed (${response.status})`);}
            if (!isCurrent()) return;
            onBuilderGelAdded(savedBuilderGel);
            setShadeName('');
            setGelType('');
            setPrice('');
            setStock('');
            setConsistency('');
        } catch (err) { if (isCurrent()) setError(err.message);}
        finally { if (isCurrent()) setSaving(false); }
    }

    return (
        <form onSubmit={handleSubmit}>
            <h2>Add Builder Gel</h2>
            <label>Shade Name<input value={shadeName} onChange={e => setShadeName(e.target.value)} required/></label>
            <label>Gel Type<input value={gelType} onChange={e => setGelType(e.target.value)} required/></label>
            <label>Price<input type="number" min="0" step="0.01" value={price} onChange={e => setPrice(e.target.value)} required/></label>
            <label>Stock<input type="number" min="0" step="1" value={stock} onChange={e => setStock(e.target.value)} required/></label>
            <button disabled={saving}>Save Builder Gel</button>
            {error && <p role="alert">{error}</p>}
        </form>
    );
}