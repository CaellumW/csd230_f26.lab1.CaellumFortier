import { useState, useEffect } from 'react';
import AddBookForm from './AddBookForm.jsx';
import BookList from './BookList.jsx';
import './App.css';

export default function App() {
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  useEffect(() => {
    let active = true;
    fetch('/api/rest/books')
        .then(res => {
          if (!res.ok) throw new Error(`Load failed (${res.status})`);
          return res.json();
        })
        .then(data => { if (active) setBooks(data); })
        .catch(err => { if (active) setError(err.message); })
        .finally(() => { if (active) setLoading(false); });
    return () => { active = false; };
  }, []);

  function handleBookAdded(savedBook) {
    setBooks(previous => [...previous, savedBook]);
  }

  async function handleDeleteBook(id) {
    if (!window.confirm('Delete this book?')) return;
    setError('');
    try {
      const response = await fetch(`/api/rest/books/${id}`, { method: 'DELETE' });
      if (!response.ok) throw new Error(`Delete failed (${response.status})`);
      setBooks(previous => previous.filter(book => book.id !== id));
    } catch (err) { setError(err.message); }
  }

  async function handleUpdateBook(id, data) {
    const response = await fetch(`/api/rest/books/${id}`, {
      method: 'PUT', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error(`Update failed (${response.status})`);
    const savedBook = await response.json();
    setBooks(previous => previous.map(book => book.id === id ? savedBook : book));
  }

  return <main className="app-container">
    <h1>My Bookstore Frontend</h1>
    <AddBookForm onBookAdded={handleBookAdded} />
    <BookList books={books} loading={loading} error={error}
              onDelete={handleDeleteBook} onUpdate={handleUpdateBook} />
  </main>;
}