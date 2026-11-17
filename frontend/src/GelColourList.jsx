import GelColour from "./GelColour.jsx";

export default function GelColourList({ gelcolour, loading, error, onDelete, onUpdate }) {
    if (loading) return <p>Loading gel colours...</p>;
    return <section aria-label="Book inventory">
        <h2>Gel colours</h2>
        {error && <p role="alert">{error}</p>}
        {!error && gelcolour.length === 0 && <p>No gel colours yet.</p>}
        {gelcolour.map(gelcolour => <GelColour key={gelcolour.id} gelcolour={gelcolour} onDelete={onDelete} onUpdate={onUpdate} />)}
    </section>;
}