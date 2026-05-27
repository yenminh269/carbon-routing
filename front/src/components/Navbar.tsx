import React from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Route, LogIn, LogOut, Info, Shield, User } from 'lucide-react';
import api from '../services/api';

const Navbar: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const isLoggedIn = !!localStorage.getItem('accessToken');

  const handleLogout = async () => {
    try {
      await api.post('/auth/logout');
    } catch (err) {
      console.error('Logout error on backend:', err);
    } finally {
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      navigate('/login');
    }
  };

  const isActive = (path: string) => location.pathname === path;

  return (
    <nav className="sticky top-0 z-50 backdrop-blur-md bg-slate-950/80 border-b border-slate-800 text-white">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Logo / Brand */}
          <Link to="/" className="flex items-center space-x-2 group">
            <Route className="h-8 w-8 text-emerald-400 group-hover:rotate-12 transition-transform duration-300" />
            <span className="font-extrabold text-xl tracking-tight bg-gradient-to-r from-emerald-400 to-cyan-400 bg-clip-text text-transparent">
              EcoRoute
            </span>
          </Link>

          {/* Nav Items */}
          <div className="flex items-center space-x-6">
            <Link
              to="/"
              className={`flex items-center space-x-1.5 text-sm font-medium transition-colors duration-200 ${
                isActive('/') ? 'text-emerald-400' : 'text-slate-300 hover:text-white'
              }`}
            >
              <Info className="h-4 w-4" />
              <span>About</span>
            </Link>

            <Link
              to="/routes"
              className={`flex items-center space-x-1.5 text-sm font-medium transition-colors duration-200 ${
                isActive('/routes') ? 'text-emerald-400' : 'text-slate-300 hover:text-white'
              }`}
            >
              <Shield className="h-4 w-4" />
              <span>Calculator</span>
            </Link>

            {isLoggedIn && (
              <Link
                to="/profile"
                className={`flex items-center space-x-1.5 text-sm font-medium transition-colors duration-200 ${
                  isActive('/profile') ? 'text-emerald-400' : 'text-slate-300 hover:text-white'
                }`}
              >
                <User className="h-4 w-4" />
                <span>Profile</span>
              </Link>
            )}

            {isLoggedIn ? (
              <button
                onClick={handleLogout}
                className="flex items-center space-x-1.5 text-sm font-medium bg-red-950/40 hover:bg-red-900/60 text-red-300 px-4 py-2 rounded-full border border-red-900/50 transition-all duration-200"
              >
                <LogOut className="h-4 w-4" />
                <span>Logout</span>
              </button>
            ) : (
              <Link
                to="/login"
                className={`flex items-center space-x-1.5 text-sm font-medium bg-emerald-500 hover:bg-emerald-400 text-slate-950 px-4 py-2 rounded-full font-semibold shadow-md shadow-emerald-950/20 hover:scale-105 transition-all duration-200`}
              >
                <LogIn className="h-4 w-4" />
                <span>Sign In</span>
              </Link>
            )}
          </div>
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
