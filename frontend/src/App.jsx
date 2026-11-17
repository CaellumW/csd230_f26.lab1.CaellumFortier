import { useState, useEffect } from 'react';
import { Routes, Route, useNavigate } from 'react-router';
import NavBar from './NavBar.jsx';
import Home from './Home.jsx';
import MagazineList from './MagazineList.jsx';
import AddBookForm from './AddBookForm.jsx';
import BookList from './BookList.jsx';
import './App.css';
import BuilderGelList from './BuilderGelList.jsx';
import AddBuilderGelForm from './BuilderGelForm.jsx';
import ColourGelList from './GelColourList.jsx';
import AddColourGelForm from './GelColourForm.jsx';

export default function App() {
  const navigate = useNavigate();
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [builderGels, setBuilderGels] = useState([]);
  const [colourGels, setColourGels] = useState([]);
  useEffect(() => {
    let active = true;

    Promise.all([
      fetch('/api/rest/books').then(r => r.json()),
      fetch('/api/rest/buildergelpolishes').then(r => r.json()),
      fetch('/api/rest/gelcolourpolishes').then(r => r.json())
    ])
        .then(([bookData, builderGelData, colourGelData]) => {
          if (active) {
            setBooks(bookData);
            setBuilderGels(builderGelData);
            setColourGels(colourGelData);
          }
        })
        .catch(err => {
          if (active) setError(err.message);
        })
        .finally(() => {
          if (active) setLoading(false);
        });

    return () => {
      active = false;
    };
  }, []);

  // ADD STUFF
  function handleBookAdded(savedBook) {
    setBooks(previous => [...previous, savedBook]);
    navigate('/inventory');
  }

  function handleBuilderGelAdded(savedBuilderGel) {
    setBuilderGels(previous => [...previous, savedBuilderGel]);
    navigate('/buildergels');
  }

  function handleColourGelAdded(savedColourGel) {
    setColourGels(previous => [...previous, savedColourGel]);
    navigate('/colourgels');
  }

  // DELETE STUFF
  async function handleDeleteBook(id) {
    if (!window.confirm('Delete this book?')) return;
    setError('');
    try {
      const response = await fetch(`/api/rest/books/${id}`, { method: 'DELETE' });
      if (!response.ok) throw new Error(`Delete failed (${response.status})`);
      setBooks(previous => previous.filter(book => book.id !== id));
    } catch (err) { setError(err.message); }
  }

  async function handleDeleteBuilderGel(id) {
    const response = await fetch(
        `/api/rest/buildergelpolishes/${id}`,
        {
          method: 'DELETE'
        }
    );

    if (!response.ok) {
      throw new Error(`Delete failed (${response.status})`);
    }

    setBuilderGels(previous =>
        previous.filter(gel => gel.id !== id)
    );
  }

  async function handleDeleteColourGel(id) {
    const response = await fetch(
        `/api/rest/gelcolourpolishes/${id}`,
        {
          method: 'DELETE'
        }
    );

    if (!response.ok) {
      throw new Error(`Delete failed (${response.status})`);
    }

    setColourGels(previous =>
        previous.filter(gel => gel.id !== id)
    );
  }

  // UPDATE STUFF
  async function handleUpdateBook(id, data) {
    const response = await fetch(`/api/rest/books/${id}`, {
      method: 'PUT', headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error(`Update failed (${response.status})`);
    const savedBook = await response.json();
    setBooks(previous => previous.map(book => book.id === id ? savedBook : book));
  }

  async function handleUpdateBuilderGel(id, data) {
    const response = await fetch(
        `/api/rest/buildergelpolishes/${id}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        }
    );

    const savedBuilderGel = await response.json();

    setBuilderGels(previous =>
        previous.map(gel =>
            gel.id === id ? savedBuilderGel : gel
        )
    );
  }

  async function handleUpdateColourGel(id, data) {
    const response = await fetch(
        `/api/rest/gelcolourpolishes/${id}`,
        {
          method: 'PUT',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(data)
        }
    );

    const savedColourGel = await response.json();

    setColourGels(previous =>
        previous.map(gel =>
            gel.id === id ? savedColourGel : gel
        )
    );
  }

  return <main className="app-container">
    <h1>My Bookstore Frontend</h1>
    <NavBar />
    <Routes>
      <Route path="/" element={<Home />} />
      <Route path="/inventory" element={<BookList books={books} loading={loading} error={error} onDelete={handleDeleteBook} onUpdate={handleUpdateBook} />} />
      <Route path="/add" element={<AddBookForm onBookAdded={handleBookAdded} />} />
      <Route path="/magazines" element={<MagazineList />} />
      <Route path="*" element={<h2>Page not found</h2>} />
      <Route path="/buildergels" element={<BuilderGelList buildergels={builderGels} loading={loading} error={error} onDelete={handleDeleteBuilderGel} onUpdate={handleUpdateBuilderGel}/>}/>
      <Route path="/addbuildergel" element={<AddBuilderGelForm onBuilderGelAdded={handleBuilderGelAdded}/>}/>
      <Route path="/colourgels" element={<ColourGelList colourGels={colourGels} loading={loading} error={error} onDelete={handleDeleteColourGel} onUpdate={handleUpdateColourGel}/>}/>
      <Route path="/addcolourgel" element={<AddColourGelForm onColourGelAdded={handleColourGelAdded}/>}/>
    </Routes>
  </main>;
}