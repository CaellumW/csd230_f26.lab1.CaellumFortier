import BuilderGel from './BuilderGel.jsx';

export default function BuilderGelList({ buildergels, loading, error, onDelete, onUpdate }) {
    if (loading) return <p>Loading builder gels...</p>;
    return <section aria-label="Builder Gel inventory">
        <h2>Builder Gels</h2>
        {error && <p role="alert">{error}</p>}
        {!error && buildergels.length === 0 && <p>No builder gels yet.</p>}
        {buildergels.map(buildergel => <BuilderGel key={buildergel.id} builderGel={buildergel} onDelete={onDelete} onUpdate={onUpdate} />)}
    </section>;
}