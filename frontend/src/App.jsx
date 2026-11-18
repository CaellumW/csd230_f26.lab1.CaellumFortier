import './App.css';
import BuilderGelList from './BuilderGelList.jsx';
import AddBuilderGelForm from './BuilderGelForm.jsx';
import ColourGelList from './GelColourList.jsx';
import AddColourGelForm from './GelColourForm.jsx';
import api from './api.js';
import { useState, useEffect } from 'react';
import { Routes, Route, useNavigate } from 'react-router';
import NavBar from './NavBar.jsx';
import Home from './Home.jsx';
import MagazineList from './MagazineList.jsx';
import AddBookForm from './AddBookForm.jsx';
import BookList from './BookList.jsx';
import './App.css';
import AuthProvider from './AuthProvider.jsx';
import useAuth from './useAuth.js';
import Login from './Login.jsx';
import ProtectedRoute from './ProtectedRoute.jsx';
import DisplayCart from './DisplayCart.jsx';

export default function App() {
    return <AuthProvider><main className="app-container">
        <h1>My Bookstore Frontend</h1>
        <Routes>
            <Route path="/login" element={<Login />} />
            <Route element={<ProtectedRoute />}><Route path="*" element={<SignedInBookApp />} /></Route>
        </Routes>
    </main></AuthProvider>;
}

function SignedInBookApp() {
    const { token } = useAuth();
    // A session change unmounts every old draft, collection and pending callback.
    return <BookApp key={token} />;
}

function BookApp() {
    const {token} = useAuth();
    const isCurrent = () => localStorage.getItem('token') === token;
    const navigate = useNavigate();
    const [cart, setCart] = useState(null);
    const [cartLoading, setCartLoading] = useState(true);
    const [cartError, setCartError] = useState('');
    const [cartPending, setCartPending] = useState(false);
    // === KEEP YOUR EXISTING NICHE STATE ===
    // Keep private state in BookApp or its original child owner.
    // Do not move it above the token-keyed session boundary.
    const [books, setBooks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');
    // === KEEP YOUR EXISTING NICHE EFFECTS AND HANDLERS ===
    // Retain your effects, callbacks, handlers and signatures.
    // Keep their existing ownership and props when merging.
    useEffect(() => {
        let active = true;
        api.get('/books')
            .then(response => {
                if (active && localStorage.getItem('token') === token) setBooks(response.data);
            })
            .catch(err => {
                if (active && localStorage.getItem('token') === token) setError(err.message);
            })
            .finally(() => {
                if (active && localStorage.getItem('token') === token) setLoading(false);
            });
        return () => {
            active = false;
        };
    }, [token]);
    useEffect(() => {
        let active = true;
        api.get('/carts/mine')
            .then(response => { if (active && localStorage.getItem('token') === token) setCart(response.status === 204 ? null : response.data); })
            .catch(err => { if (active && localStorage.getItem('token') === token) setCartError(err.message); })
            .finally(() => { if (active && localStorage.getItem('token') === token) setCartLoading(false); });
        return () => { active = false; };
    }, [token]);

    async function changeCart(productId, remove = false) {
        if (cartLoading || cartPending || !isCurrent()) return;
        setCartError(''); setCartPending(true);
        try {
            const path = `/carts/mine/products/${productId}`;
            const response = remove ? await api.delete(path) : await api.post(path);
            if (isCurrent()) setCart(response.status === 204 ? null : response.data);
        } catch (err) { if (isCurrent()) setCartError(err.message); }
        finally { if (isCurrent()) setCartPending(false); }
    }


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
            await api.delete(`/books/${id}`);
            setBooks(previous => previous.filter(book => book.id !== id));
        } catch (err) {
            setError(err.message);
        }
    }

// DELETE BUILDER GEL
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

// DELETE COLOUR GEL
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

// DELETE MAGAZINE
    async function handleDeleteMagazine(id) {
        const response = await fetch(
            `/api/rest/magazines/${id}`,
            {
                method: 'DELETE'
            }
        );

        if (!response.ok) {
            throw new Error(`Delete failed (${response.status})`);
        }

        setMagazines(previous =>
            previous.filter(magazine => magazine.id !== id)
        );
    }

// UPDATE BOOKS
    async function handleUpdateBook(id, data) {
        const response = await api.put(`/books/${id}`, data);
        const savedBook = response.data;
        setBooks(previous => previous.map(book => book.id === id ? savedBook : book));
    }

// UPDATE MAGAZINES
    async function handleUpdateMagazine(id, data) {
        const response = await fetch(`/api/rest/magazines/${id}`, {
            method: 'PUT', headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });
        if (!response.ok) throw new Error(`Update failed (${response.status})`);
        const savedMagazine = await response.json();
        setBooks(previous => previous.map(magazine => magazine.id === id ? savedMagazine : magazine));
    }

// UPDATE BUILDER GEL
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

// UPDATE COLOUR GELS
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
        <NavBar/>
        <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/inventory" element={<BookList books={books} loading={loading} error={error} onDelete={handleDeleteBook} onUpdate={handleUpdateBook} onAddToCart={changeCart} cartPending={cartLoading || cartPending} />} /><Route path="/magazines" element={<MagazineList />} />
            <Route path="*" element={<h2>Page not found</h2>} />
            <Route path="/add" element={<AddBookForm onBookAdded={handleBookAdded} />} />
            <Route path="/magazines" element={<MagazineList onAddToCart={changeCart} cartPending={cartLoading || cartPending} />} />
            <Route path="/buildergels" element={<BuilderGelList buildergels={builderGels} loading={loading} error={error} onDelete={handleDeleteBuilderGel} onUpdate={handleUpdateBuilderGel}/>}/>
            <Route path="/addbuildergel" element={<AddBuilderGelForm onBuilderGelAdded={handleBuilderGelAdded}/>}/>
            <Route path="/colourgels" element={<ColourGelList colourGels={colourGels} loading={loading} error={error} onDelete={handleDeleteColourGel} onUpdate={handleUpdateColourGel}/>}/>
            <Route path="/addcolourgel" element={<AddColourGelForm onColourGelAdded={handleColourGelAdded}/>}/>
            <Route path="/cart" element={<DisplayCart cart={cart} loading={cartLoading} error={cartError} pending={cartPending} onRemove={id => changeCart(id, true)} />} />
            <NavBar cartCount={cart?.products?.length ?? 0} />
            {cartError && <p role="status">Cart: {cartError}</p>}
        </Routes>
    </main>;
}