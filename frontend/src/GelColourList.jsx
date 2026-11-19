import { useState, useEffect } from 'react';
import GelColour from "./GelColour.jsx";
import GelColourForm from "./GelColourForm.jsx";

export default function GelColourList() {
    const [gelColours, setGelcolours] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    useEffect(() => {
        let active = true;
        fetch('/api/rest/gelcolourpolishes')
            .then(res => {
                if (!res.ok) throw new Error(`Load failed (${res.status})`);
                return res.json();
            })
            .then(data => { if (active) setGelcolours(data); })
            .catch(err => { if (active) setError(err.message); })
            .finally(() => {if (active) setLoading(false); });
        return () => { active = false; };
    }, []);

    async function handleDeleteGelColour(id) {
        if (!window.confirm('Delete this gel colour?')) return;
        setError('');
        try {
            const res = await fetch(`/api/rest/gelcolourpolishes/${id}`, { method: 'DELETE'});
            if (!res.ok) throw new Error(`Delete failed (${res.status})`);
            setGelcolours(previous => previous.filter(gelcolour => gelcolour.id !== id));
        } catch (err) { setError(err.message); }
    }

    async function handleUpdateGelColour(id, data) {
        const res = await fetch(`/api/rest/gelcolourpolishes/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body:JSON.stringify(data)
        });
        if (!res.ok) throw new Error(`Update failed (${res.status})`);
        const saved = await res.json();
        setGelcolours(previous => previous.map(gelcolour => gelcolour.id === id ? saved : gelcolour));
    }

    return <section>
        <h1>Gel Colours</h1>
        <GelColourForm onColourGelAdded={saved => setGelcolours(previous => [...previous, saved])}/>
        {loading && <p>Loading Gel Colours...</p>}
        {error && <p role="alert">{error}</p>}
        {!loading && !error && gelColours.length === 0 && <p>No gel colours yet.</p>}
        <div className="gelcolour-list">
            {gelColours.map(gelcolour => <GelColour key={gelcolour.id} {...gelcolour} onUpdate={handleUpdateGelColour} onDelete={handleDeleteGelColour} />)}
        </div>
    </section>;
}