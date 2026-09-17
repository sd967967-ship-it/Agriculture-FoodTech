import { useEffect, useState } from 'react';
import { Routes, Route, useLocation } from 'react-router-dom';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import ErrorBoundary from './components/ErrorBoundary';
import HomePage from './pages/HomePage';
import DiagnosePage from './pages/DiagnosePage';
import AboutPage from './pages/AboutPage';
import ProfilePage from './pages/ProfilePage';
import ToolsPage from './pages/ToolsPage';
import PestLogPage from './pages/PestLogPage';

export default function App() {
  const { search } = useLocation();
  const mode = new URLSearchParams(search).get('mode');
  const [isSmallScreen, setIsSmallScreen] = useState(false);

  useEffect(() => {
    const mediaQuery = window.matchMedia('(max-width: 767px)');
    const updateScreenMode = () => setIsSmallScreen(mediaQuery.matches);
    updateScreenMode();
    mediaQuery.addEventListener('change', updateScreenMode);
    return () => mediaQuery.removeEventListener('change', updateScreenMode);
  }, []);

  const isMobileMode = mode === 'mobile' || (!mode && isSmallScreen);
  const isDesktopMode = mode === 'desktop' || (!mode && !isSmallScreen);

  return (
    <ErrorBoundary>
      <div className={`app-shell site-shell flex min-h-screen flex-col soft-grid${isMobileMode ? ' mobile-mode' : ''}${isDesktopMode ? ' desktop-mode' : ''}`}>
        <div className="ambient-glow ambient-glow-one" />
        <div className="ambient-glow ambient-glow-two" />
        <Navbar />
        <main className="page-enter relative z-10 flex-1">
          <Routes>
            <Route path="/" element={<HomePage />} />
            <Route path="/diagnose" element={<DiagnosePage />} />
            <Route path="/about" element={<AboutPage />} />
            <Route path="/profile" element={<ProfilePage />} />
            <Route path="/tools" element={<ToolsPage />} />
            <Route path="/pest-log" element={<PestLogPage />} />
            <Route path="/pest-observations" element={<PestLogPage />} />
            <Route path="*" element={<HomePage />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </ErrorBoundary>
  );
}
