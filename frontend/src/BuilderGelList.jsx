import BuilderGelPolish from './BuilderGel.jsx';

export default function BuilderGelList({ builders, loading, error, onDelete, onUpdate }) {
    if (loading) return <p>Loading builder gels...</p>;
    return <section aria-label="Builder gel inventory">
        <h2>Builder gels</h2>
        {error && <p role="alert">{error}</p>}
        {!error && builders.length === 0 && <p>No builder gels yet.</p>}
        {builders.map(builder => <BuilderGelPolish key={builder.id} BuilderGel ={builders} onDelete={onDelete} onUpdate={onUpdate} />)}
    </section>;
}