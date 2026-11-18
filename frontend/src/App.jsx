import {useState, useEffect} from 'react';
import {Routes, Route, useNavigate} from 'react-router';
import './App.css';
import NavBar from './NavBar.jsx';
import Home from './Home.jsx';
import BookList from './BookList.jsx';
import AddBookForm from './AddBookForm.jsx';
import MagazineList from './MagazineList.jsx';
import AddMagazineForm from "./AddMagazineForm.jsx";
import BuilderGelList from './BuilderGelList.jsx';
import AddBuilderGelForm from './AddBuilderGelForm.jsx';
import ColourGelList from './GelColourList.jsx';
import AddColourGelForm from './GelColourForm.jsx';

export default function App() {
    const navigate = useNavigate();
    const [books, setBooks] = useState([]);
    const [magazines, setMagazines] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    const [builderGels, setBuilderGels] = useState([]);
    const [colourGels, setColourGels] = useState([]);
    useEffect(() => {
        let active = true;

        Promise.all([
            fetch('/api/rest/books').then(r => r.json()),
            fetch('/api/rest/buildergelpolishes').then(r => r.json()),
            fetch('/api/rest/gelcolourpolishes').then(r => r.json()),
            fetch('/api/rest/magazines').then(r => r.json())
        ])
            .then(([bookData, builderGelData, colourGelData, magazineData]) => {
                if (active) {
                    setBooks(bookData);
                    setBuilderGels(builderGelData);
                    setColourGels(colourGelData);
                    setMagazines(magazineData);
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

    // ADD BOOK
    function handleBookAdded(savedBook) {
        setBooks(previous => [...previous, savedBook]);
        navigate('/inventory');
    }

    // ADD BUILDER GEL
    function handleBuilderGelAdded(savedBuilderGel) {
        setBuilderGels(previous => [...previous, savedBuilderGel]);
        navigate('/buildergels');
    }

    // ADD COLOUR GEL
    function handleColourGelAdded(savedColourGel) {
        setColourGels(previous => [...previous, savedColourGel]);
        navigate('/colourgels');
    }

    // ADD MAGAZINE
    function handleMagazineAdded(savedMagazine) {
        setMagazines(previous => [...previous, savedMagazine]);
        navigate('/magazines')
    }

    // DELETE BOOK
    async function handleDeleteBook(id) {
        if (!window.confirm('Delete this book?')) return;
        setError('');
        try {
            const response = await fetch(`/api/rest/books/${id}`, {method: 'DELETE'});
            if (!response.ok) throw new Error(`Delete failed (${response.status})`);
            setBooks(previous => previous.filter(book => book.id !== id));
        } catch (err) {
            setError(err.message);
        }
    }

    // DELETE BUILDER GEL
    async function handleDeleteBuilderGelPolish(id) {
        if (!window.confirm('Delete this builder gel polish?')) return;
        setError('');
        try {
            const response = await fetch(`/api/rest/buildergelpolishes/${id}`, {method: 'DELETE'});
            if (!response.ok) {throw new Error(`Delete failed (${response.status})`);}
            setBuilderGelPolishes(previous => previous.filter(polish => polish.id !== id));
        } catch (err) {
            setError(err.message);
        }
    }

    // DELETE COLOUR GEL
    async function handleDeleteGelColourPolish(id) {
        if (!window.confirm('Delete this colour gel polish?')) {return;}
        setError('');
        try {const response = await fetch(`/api/rest/gelcolourpolishes/${id}`, {method: 'DELETE'});
            if (!response.ok) {throw new Error(`Delete failed (${response.status})`);}
            setGelColourPolishes(previous => previous.filter(polish => polish.id !== id));
        } catch (err) {
            setError(err.message);
        }
    }

    // DELETE MAGAZINE
    async function handleDeleteMagazine(id) {
        if (!window.confirm('Delete this magazine?')) {return;}
        setError('');
        try {const response = await fetch(`/api/rest/magazines/${id}`, {method: 'DELETE'});
            if (!response.ok) {throw new Error(`Delete failed (${response.status})`);}
            setMagazines(previous => previous.filter(magazine => magazine.id !== id));
        } catch (err) {
            setError(err.message);
        }
    }

    // UPDATE BOOKS
    async function handleUpdateBook(id, data) {
        const response = await fetch(`/api/rest/books/${id}`, {
            method: 'PUT', headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error(`Update failed (${response.status})`);
        const savedBook = await response.json();
        setBooks(previous => previous.map(book => book.id === id ? savedBook : book));
    }

    // UPDATE MAGAZINES
    async function handleUpdateMagazine(id, data) {
        const response = await fetch(
            `/api/rest/magazines/${id}`,
            {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            }
        );

        if (!response.ok) {
            throw new Error(
                `Update failed (${response.status})`
            );
        }

        const savedMagazine = await response.json();

        setMagazines(previous =>
            previous.map(magazine =>
                magazine.id === id
                    ? savedMagazine
                    : magazine
            )
        );
    }

    // UPDATE BUILDER GEL
    async function handleUpdateBuilderGelPolish(id, data) {
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

        if (!response.ok) {
            throw new Error(
                `Update failed (${response.status})`
            );
        }

        const savedBuilderGelPolish = await response.json();

        setBuilderGelPolishes(previous =>
            previous.map(polish =>
                polish.id === id
                    ? savedBuilderGelPolish
                    : polish
            )
        );
    }

    // UPDATE COLOUR GELS
    async function handleUpdateGelColourPolish(id, data) {
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

        if (!response.ok) {
            throw new Error(
                `Update failed (${response.status})`
            );
        }

        const savedGelColourPolish = await response.json();

        setGelColourPolishes(previous =>
            previous.map(polish =>
                polish.id === id
                    ? savedGelColourPolish
                    : polish
            )
        );
    }

    return <main className="app-container">
        <h1>My Bookstore Frontend</h1>
        <NavBar/>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/inventory" element={<BookList books={books} loading={loading} error={error} onDelete={handleDeleteBook} onUpdate={handleUpdateBook}/>}/>
            <Route path="/add" element={<AddBookForm onBookAdded={handleBookAdded}/>}/>
            <Route path="/magazines" element={<MagazineList/>}/>
            <Route path="/magazines/add" element={<AddMagazineForm onMagazineAdded={handleMagazineAdded}/>}/>
            <Route path="/buildergels" element={<BuilderGelList buildergels={builderGels} loading={loading} error={error} onDelete={handleDeleteBuilderGelPolish} onUpdate={handleUpdateBuilderGelPolish}/>}/>
            <Route path="/buildergels/add" element={<AddBuilderGelForm onBuilderPolishAdded={handleBuilderGelAdded}/>}/>
            <Route path="/colourgels" element={<ColourGelList colourGels={colourGels} loading={loading} error={error} onDelete={handleDeleteColourGel} onUpdate={handleUpdateColourGel}/>}/>
            <Route path="/colourgels/add" element={<AddColourGelForm onPolishAdded={handleColourGelAdded}/>}/>
            <Route path="*" element={<h2>Page not found</h2>}/>
        </Routes>
    </main>;
}