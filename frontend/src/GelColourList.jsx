import GelColourPolish from './GelColourPolish.jsx';

export default function BookList({ gels, loading, error, onDelete, onUpdate }) {
    if (loading) return <p>Loading gel polishes...</p>;
    return <section aria-label="Gel Polish inventory">
        <h2>Gel Polishes</h2>
        {error && <p role="alert">{error}</p>}
        {!error && gels.length === 0 && <p>No gels yet.</p>}
        {gels.map(gel => <GelColourPolish key={gel.id} gel={gel} onDelete={onDelete} onUpdate={onUpdate} />)}
    </section>;
}