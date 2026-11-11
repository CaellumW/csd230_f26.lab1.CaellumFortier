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

  return <main className="app-container">
    <h1>My Bookstore Frontend</h1>
    <AddBookForm onBookAdded={handleBookAdded} />
    <BookList books={books} loading={loading} error={error} />
  </main>;
}