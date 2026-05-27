import React, { useEffect, useState } from 'react';
import { User, Shield, Activity, Leaf, Route, RefreshCw, AlertCircle } from 'lucide-react';
import api from '../services/api';

interface UserProfile {
  username: string;
  roles: string[];
}

const Profile: React.FC = () => {
  const [profile, setProfile] = useState<UserProfile | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchProfile = async () => {
      try {
        const response = await api.get('/auth/me');
        setProfile(response.data);
      } catch (err: any) {
        console.error(err);
        setError('Failed to load user profile. Please check if you are logged in.');
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, []);

  if (loading) {
    return (
      <div className="bg-slate-950 min-h-screen text-slate-100 flex items-center justify-center">
        <RefreshCw className="h-8 w-8 text-emerald-400 animate-spin" />
      </div>
    );
  }

  if (error || !profile) {
    return (
      <div className="bg-slate-950 min-h-screen text-slate-100 flex items-center justify-center px-4">
        <div className="max-w-md w-full bg-slate-900 border border-slate-800 rounded-3xl p-8 text-center">
          <AlertCircle className="h-12 w-12 text-red-400 mx-auto mb-4" />
          <h2 className="text-xl font-bold text-slate-200 mb-2">Error Loading Profile</h2>
          <p className="text-slate-400 text-sm mb-6">{error || 'An unexpected error occurred.'}</p>
          <a
            href="/login"
            className="inline-block bg-gradient-to-r from-emerald-500 to-cyan-500 hover:from-emerald-400 hover:to-cyan-400 text-slate-950 font-bold px-6 py-2.5 rounded-full text-sm transition-all duration-200"
          >
            Go to Login
          </a>
        </div>
      </div>
    );
  }

  // Get initials for profile avatar
  const getInitials = (name: string) => {
    return name.slice(0, 2).toUpperCase();
  };

  return (
    <div className="bg-slate-950 text-slate-100 min-h-screen py-16 px-4 sm:px-6 lg:px-8 relative overflow-hidden">
      {/* Background glow */}
      <div className="absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[450px] h-[450px] bg-emerald-500/5 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-1/4 right-1/4 translate-x-1/2 translate-y-1/2 w-[350px] h-[350px] bg-cyan-500/5 rounded-full blur-3xl pointer-events-none" />

      <div className="max-w-4xl mx-auto relative z-10">
        {/* Page Header */}
        <div className="mb-10 flex items-center gap-3">
          <User className="h-6 w-6 text-emerald-400" />
          <h1 className="text-2xl font-bold tracking-tight text-slate-100">User Profile</h1>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-12 gap-8 items-start">
          {/* Left Column: Avatar & Basic Details */}
          <div className="md:col-span-4 bg-slate-900/60 border border-slate-800 rounded-3xl p-8 text-center backdrop-blur-md">
            <div className="h-24 w-24 bg-gradient-to-tr from-emerald-500 to-cyan-500 rounded-full flex items-center justify-center text-slate-950 font-extrabold text-3xl mx-auto shadow-lg shadow-emerald-500/20 mb-6">
              {getInitials(profile.username)}
            </div>
            <h2 className="text-xl font-bold text-slate-100">{profile.username}</h2>
            <p className="text-xs text-slate-400 mt-1 uppercase tracking-wider font-semibold">
              System Account
            </p>

            <div className="mt-8 pt-6 border-t border-slate-800 text-left space-y-4">
              <div>
                <span className="text-[10px] uppercase font-bold text-slate-500 tracking-wider">
                  Access Level Roles
                </span>
                <div className="flex flex-wrap gap-1.5 mt-2">
                  {profile.roles.map((role, idx) => (
                    <span
                      key={idx}
                      className="px-2.5 py-1 text-[10px] font-bold rounded-lg bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 uppercase tracking-wider"
                    >
                      {role.replace('ROLE_', '')}
                    </span>
                  ))}
                </div>
              </div>
              <div>
                <span className="text-[10px] uppercase font-bold text-slate-500 tracking-wider">
                  Security Status
                </span>
                <div className="flex items-center gap-1.5 mt-1.5 text-xs text-emerald-400 font-semibold">
                  <Shield className="h-4 w-4 shrink-0 text-emerald-400" />
                  <span>Authenticated via JWT</span>
                </div>
              </div>
            </div>
          </div>

          {/* Right Column: Simulated Statistics & System Access */}
          <div className="md:col-span-8 space-y-6">
            {/* Security Notice / Details */}
            <div className="bg-slate-900/60 border border-slate-800 rounded-3xl p-6 backdrop-blur-md">
              <h3 className="text-base font-bold text-slate-200 mb-4 flex items-center gap-2">
                <Activity className="h-5 w-5 text-cyan-400" />
                <span>Active Session Analytics</span>
              </h3>

              <div className="grid grid-cols-1 sm:grid-cols-3 gap-4">
                {/* Stat Card 1 */}
                <div className="bg-slate-950 border border-slate-800/80 p-4 rounded-2xl flex flex-col justify-between">
                  <span className="text-[10px] font-bold text-slate-500 uppercase tracking-wider">
                    Calculations Run
                  </span>
                  <div className="flex items-baseline gap-1 mt-2">
                    <span className="text-2xl font-extrabold text-white">12</span>
                    <span className="text-slate-500 text-xs font-semibold">queries</span>
                  </div>
                </div>

                {/* Stat Card 2 */}
                <div className="bg-slate-950 border border-slate-800/80 p-4 rounded-2xl flex flex-col justify-between">
                  <span className="text-[10px] font-bold text-slate-500 uppercase tracking-wider">
                    Carbon Saved
                  </span>
                  <div className="flex items-baseline gap-1 mt-2">
                    <span className="text-2xl font-extrabold text-emerald-400">1.45</span>
                    <span className="text-emerald-500 text-xs font-bold">kg CO2</span>
                  </div>
                </div>

                {/* Stat Card 3 */}
                <div className="bg-slate-950 border border-slate-800/80 p-4 rounded-2xl flex flex-col justify-between">
                  <span className="text-[10px] font-bold text-slate-500 uppercase tracking-wider">
                    Avg Green Offset
                  </span>
                  <div className="flex items-baseline gap-1 mt-2">
                    <span className="text-2xl font-extrabold text-cyan-400">22%</span>
                    <span className="text-cyan-500 text-xs font-bold">relative</span>
                  </div>
                </div>
              </div>
            </div>

            {/* Quick Actions / Security Policies */}
            <div className="bg-slate-900/60 border border-slate-800 rounded-3xl p-6 backdrop-blur-md">
              <h3 className="text-base font-bold text-slate-200 mb-4 flex items-center gap-2">
                <Leaf className="h-5 w-5 text-emerald-400" />
                <span>Environmental Offset Log</span>
              </h3>

              <div className="space-y-3.5">
                <div className="flex items-center justify-between p-3.5 bg-slate-950/60 border border-slate-800/60 rounded-xl">
                  <div className="flex items-center gap-2.5">
                    <div className="p-1.5 bg-emerald-500/10 text-emerald-400 rounded-lg">
                      <Route className="h-4 w-4" />
                    </div>
                    <div>
                      <h4 className="text-xs font-bold text-slate-200">Route Node A ➔ Node C</h4>
                      <p className="text-[10px] text-slate-500 mt-0.5">Heavy Semi-Truck Preset</p>
                    </div>
                  </div>
                  <span className="text-xs font-bold text-emerald-400">- 400g CO2</span>
                </div>

                <div className="flex items-center justify-between p-3.5 bg-slate-950/60 border border-slate-800/60 rounded-xl">
                  <div className="flex items-center gap-2.5">
                    <div className="p-1.5 bg-emerald-500/10 text-emerald-400 rounded-lg">
                      <Route className="h-4 w-4" />
                    </div>
                    <div>
                      <h4 className="text-xs font-bold text-slate-200">Route Node B ➔ Node A</h4>
                      <p className="text-[10px] text-slate-500 mt-0.5">Diesel Cargo Truck Preset</p>
                    </div>
                  </div>
                  <span className="text-xs font-bold text-emerald-400">- 180g CO2</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Profile;
