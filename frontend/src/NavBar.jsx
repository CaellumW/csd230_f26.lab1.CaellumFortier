import {Link, Route, useNavigate} from 'react-router';

import useAuth from './useAuth.js';
import DisplayCart from "./DisplayCart.jsx";

export default function NavBar({ cartCount }) {
    const { logout, isAdmin } = useAuth();
    const navigate = useNavigate();
    return <nav>
        <Link to="/">Home</Link>
        <Link to="/inventory">Books</Link>
        {isAdmin && <Link to="/add">Add Book</Link>}
        <Link to="/magazines">Magazines</Link>
        <Link to="/colourgels">Gel Colour Polishes</Link>
        <Link to="/buildergels">Builder Gel Polishes</Link>
        <Link to="/cart">Cart ({cartCount})</Link>
        <Route path="/cart" element={<DisplayCart cart={cart} loading={cartLoading} error={cartError} pending={cartPending} onRemove={id => changeCart(id, true)} />} />
        <NavBar cartCount={cart?.products?.length ?? 0} />
        <button onClick={() => { logout(); navigate('/login', { replace: true }); }}>Logout</button>
    </nav>;
}