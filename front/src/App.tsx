import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navbar from './components/Navbar';
import ProtectedRoute from './components/ProtectedRoute';
import LandingPage from './pages/LandingPage';
import LoginRegister from './pages/LoginRegister';
import RouteVisualization from './pages/RouteVisualization';
import Profile from './pages/Profile';

const App: React.FC = () => {
  return (
    <Router>
      <div className="bg-slate-950 min-h-screen text-slate-100 flex flex-col font-sans">
        <Navbar />
        <main className="flex-grow">
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/login" element={<LoginRegister />} />
            <Route
              path="/routes"
              element={
                <ProtectedRoute>
                  <RouteVisualization />
                </ProtectedRoute>
              }
            />
            <Route
              path="/profile"
              element={
                <ProtectedRoute>
                  <Profile />
                </ProtectedRoute>
              }
            />
          </Routes>
        </main>
        <footer className="border-t border-slate-900 bg-slate-950 py-6 text-center text-xs text-slate-500">
          &copy; {new Date().getFullYear()} EcoRoute Fleet Logistics. All rights reserved.
        </footer>
      </div>
    </Router>
  );
};

export default App;
