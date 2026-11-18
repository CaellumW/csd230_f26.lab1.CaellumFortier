import {useState, useEffect} from "react";
import BuilderGel from './BuilderGel.jsx';
import AddBuilderGelForm from "./BuilderGelForm.jsx";

export default function BuilderGelList({ buildergels, loading, error, onDelete, onUpdate }) {
    const [builderGels, setBuilderGels] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    useEffect(() => {
        let active = true;
        fetch('/api/rest/buildergelpolishes')
            .then(res => {
                if (!res.ok) throw new Error(`Load failed(${res.status})`);
                return res.json();
            })
            .then(data => { if (active) setBuilderGels(data); })
            .catch(err => { if (active) setError(err.message); })
            .finally(() => { if (active) setLoading(false); });
        return () => { active = false; };
    }, []);

    async function handleDeleteBuilderGel(id) {
        if (!window.confirm('Delete this builder gel?')) return;
        setError('');
        try {
            const res = await fetch(`/api/rest/buildergelpolishes/${id}`, { method: 'DELETE'})
            if (!res.ok) throw new Error(`Delete failed (${res.status})`);
            setBuilderGels(previous => previous.filter(buildergels => buildergels.id !== id));
        } catch (err) { setError(err.message); }
    }

    async function handleUpdateBuilderGel(id, data) {
        const res = await fetch(`/api/rest/buildergelpolishes/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });
        if (!res.ok) throw new Error(`Update failed (${res.status})`);
        const saved = await res.json();
        setBuilderGels(previous => previous.map(buildergels => buildergels.id === id ? saved : buildergels));
    }

    return <section>
        <h1>Builder Gels</h1>
        <AddBuilderGelForm onBuilderGelAdded={saved => setBuilderGels(previous => [...previous, saved])}/>
        {loading && <p>Loading builder gels...</p>}
        {error && <p role="alert">{error}</p> }
        {!loading && !error && builderGels.length === 0 && <p>No builder gels yet.</p>}
        <div className="buildergels-list">
            {builderGels.map(buildergels => <BuilderGel key={buildergels.id} {...buildergels} onUpdate={handleUpdateBuilderGel} onDelete={handleDeleteBuilderGel} />)}
        </div>
    </section>;
}