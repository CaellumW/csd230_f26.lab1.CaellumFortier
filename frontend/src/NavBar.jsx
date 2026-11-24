import {Link, Route, useNavigate} from 'react-router';
import useAuth from './useAuth.js';
import DisplayCart from "./DisplayCart.jsx";
import {useState} from "react";

export default function NavBar({ cartCount }) {
    const { logout, isAdmin } = useAuth();
    const navigate = useNavigate();
    const [cart] = useState(null);
    const [cartLoading] = useState(true);
    const [cartError] = useState('');
    const [cartPending] = useState(false);

    return <nav>
        <Link to="/">Home</Link>
        <Link to="/inventory">Books</Link>
        {isAdmin && <Link to="/add">Add Book</Link>}
        <Link to="/magazines">Magazines</Link>
        <Link to="/colourgels">Gel Colour Polishes</Link>
        {isAdmin && <Link to="/addcolourgel">Add colour gel</Link>}
        <Link to="/buildergels">Builder Gel Polishes</Link>
        {isAdmin && <Link to="/addbuildergel">Add builder gel</Link>}
        <Link to="/cart">Cart ({cartCount})</Link>
        <button onClick={() => { logout(); navigate('/login', { replace: true }); }}>Logout</button>
    </nav>;
}