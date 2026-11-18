import { Link } from 'react-router';

export default function NavBar() {
    return <nav>
        <Link to="/">Home</Link>
        <Link to="/inventory">Books</Link>
        <Link to="/add">Add Book</Link>
        <Link to="/magazines">Magazines</Link>
        <Link to="/magazines/add">Add Magazine</Link>
        <Link to="/colourgels">Gel Colour Polishes</Link>
        <Link to="/colourgels/add">Add Colour Polish</Link>
        <Link to="/buildergels">Builder Gel Polishes</Link>
        <Link to="/buildergels/add">Add Builder Gel Polish</Link>
    </nav>;
}