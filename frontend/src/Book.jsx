export default function Book({ book }) {
    return <article className="book-row">
        <h3>{book.title}</h3>
        <p>{book.author} | ${Number(book.price).toFixed(2)} | Copies: {book.copies}</p>
    </article>;
}