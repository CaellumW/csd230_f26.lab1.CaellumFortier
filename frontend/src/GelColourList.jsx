import GelColour from "./GelColour.jsx";

export default function GelColourList({ gelcolours, loading, error, onDelete, onUpdate, onAddToCart, cartPending, canManage }) {
    if (loading) return <p>Loading colour gels...</p>;
    return <section aria-label="Colour gel inventory">
        <h2>Colour Gels</h2>
        {error && <p role="alert">{error}</p>}
        {!error && gelcolours.length === 0 && <p>No colour gels yet.</p>}
        {gelcolours.map(gelcolour => <GelColour key={gelcolour.id} gelcolour={gelcolour} onDelete={onDelete} onUpdate={onUpdate} onAddToCart={onAddToCart} cartPending={cartPending} canManage={canManage}  />)}
    </section>;
}