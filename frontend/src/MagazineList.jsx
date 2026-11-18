import { useState, useEffect } from 'react';
import MagazineForm from './MagazineForm.jsx';
import Magazine from './Magazine.jsx';

export default function MagazineList() {
    const [magazines, setMagazines] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    useEffect(() => {
        let active = true;
        api.get('/magazines')
            .then(response => { if (active) setMagazines(response.data); })
            .catch(err => { if (active) setError(err.message); })
            .finally(() => { if (active) setLoading(false); });
        return () => { active = false; };
    }, []);

    async function handleDeleteMagazine(id) {
        if (!window.confirm('Delete this magazine?')) return;
        setError('');
        try {
            await api.delete(`/magazines/${id}`);
            setMagazines(previous => previous.filter(magazine => magazine.id !== id));
        } catch (err) { setError(err.message); }
    }

    async function handleUpdateMagazine(id, data) {
        const res = await api.put(`/magazines/${id}`, data);
        const saved = res.data;
        setMagazines(previous => previous.map(magazine => magazine.id === id ? saved : magazine));
    }

    return <section>
        <h1>Magazines</h1>
        <MagazineForm onMagazineAdded={saved => setMagazines(previous => [...previous, saved])} />
        {loading && <p>Loading magazines...</p>}
        {error && <p role="alert">{error}</p>}
        {!loading && !error && magazines.length === 0 && <p>No magazines yet.</p>}
        <div className="magazine-list">
            {magazines.map(magazine => <Magazine key={magazine.id} {...magazine} onUpdate={handleUpdateMagazine} onDelete={handleDeleteMagazine} />)}
        </div>
    </section>;
}