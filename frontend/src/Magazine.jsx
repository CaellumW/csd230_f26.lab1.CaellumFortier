export default function Magazine({ title, price, copies, orderQty, currentIssue }) {
    return <article className="magazine-row">
        <h3>{title}</h3>
        <p>Price: ${Number(price).toFixed(2)} | Copies: {copies} | Order Quantity: {orderQty}</p>
        <p>Current Issue: {currentIssue}</p>
    </article>;
}