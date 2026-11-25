import BuilderGel from './BuilderGel.jsx';

export default function BuilderGelList({ buildergels, loading, error, onDelete, onUpdate, onAddToCart, cartPending, canManage}) {
    if (loading) return <p>Loading builder gels...</p>;
    return <section aria-label="Builder Gels inventory">
        <h2>Builder Gels</h2>
        {error && <p role="alert">{error}</p>}
        {!error && buildergels.length === 0 && <p>No builder gels yet.</p>}
        {buildergels.map(buildergel => <BuilderGel key={buildergel.id} buildergel={buildergel} onDelete={onDelete} onUpdate={onUpdate} onAddToCart={onAddToCart} cartPending={cartPending} canManage={canManage}/>)}
    </section>;
}