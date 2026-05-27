import React from 'react';
import { Link } from 'react-router-dom';
import { Leaf, Clock, Cpu, ArrowRight, Zap, Gauge } from 'lucide-react';

const LandingPage: React.FC = () => {
  return (
    <div className="bg-slate-950 text-slate-100 min-h-screen">
      {/* Hero Section */}
      <div className="relative overflow-hidden py-24 px-6 sm:px-12 lg:px-24">
        {/* Glow Effects */}
        <div className="absolute top-1/4 left-1/4 -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-emerald-500/10 rounded-full blur-3xl" />
        <div className="absolute bottom-1/4 right-1/4 translate-x-1/2 translate-y-1/2 w-96 h-96 bg-cyan-500/10 rounded-full blur-3xl" />

        <div className="max-w-5xl mx-auto text-center relative z-10">
          <span className="px-3 py-1.5 rounded-full bg-emerald-500/10 border border-emerald-500/30 text-emerald-400 text-xs font-semibold uppercase tracking-wider">
            Smart Logistics & Mobility
          </span>
          <h1 className="mt-8 text-5xl sm:text-6xl font-extrabold tracking-tight bg-gradient-to-r from-white via-slate-200 to-slate-400 bg-clip-text text-transparent">
            Carbon-Aware Route Optimization
          </h1>
          <p className="mt-6 text-lg sm:text-xl text-slate-400 max-w-3xl mx-auto leading-relaxed">
            Navigate the trade-off between time and emissions. Calculate travel paths using real-time algorithmic optimizations designed for green fleets.
          </p>
          <div className="mt-10 flex flex-col sm:flex-row justify-center items-center gap-4">
            <Link
              to="/routes"
              className="flex items-center space-x-2 bg-gradient-to-r from-emerald-500 to-cyan-500 hover:from-emerald-400 hover:to-cyan-400 text-slate-950 font-bold px-8 py-4 rounded-full shadow-lg shadow-emerald-500/20 hover:scale-105 hover:shadow-emerald-500/30 transition-all duration-300 group"
            >
              <span>Try Route Calculator</span>
              <ArrowRight className="h-5 w-5 group-hover:translate-x-1 transition-transform" />
            </Link>
            <Link
              to="/login"
              className="bg-slate-900/60 hover:bg-slate-900 border border-slate-800 text-slate-200 font-semibold px-8 py-4 rounded-full transition-all duration-200"
            >
              Partner Portal
            </Link>
          </div>
        </div>
      </div>

      {/* Explanations Section */}
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16 border-t border-slate-900">
        <div className="text-center max-w-3xl mx-auto mb-16">
          <h2 className="text-3xl sm:text-4xl font-bold tracking-tight">How Carbon-Aware Routing Works</h2>
          <p className="mt-4 text-slate-400">
            Routing is no longer just about the shortest distance. It's about finding the optimal balance between fleet speed and greenhouse gas footprint.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-8">
          {/* Card 1 */}
          <div className="bg-slate-900/40 border border-slate-800/80 rounded-3xl p-8 hover:border-slate-700/80 hover:bg-slate-900/60 transition-all duration-300">
            <div className="p-3 bg-emerald-500/10 text-emerald-400 rounded-2xl w-fit mb-6">
              <Leaf className="h-6 w-6" />
            </div>
            <h3 className="text-xl font-bold text-slate-100 mb-3">Carbon-Aware Routing</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Traditional GPS routing engines solve purely for travel duration. Carbon-aware routing incorporates vehicle profiles, fuel types, and speed efficiency curves to route vehicles where they burn the least amount of fuel and emit minimal CO2.
            </p>
          </div>

          {/* Card 2 */}
          <div className="bg-slate-900/40 border border-slate-800/80 rounded-3xl p-8 hover:border-slate-700/80 hover:bg-slate-900/60 transition-all duration-300">
            <div className="p-3 bg-cyan-500/10 text-cyan-400 rounded-2xl w-fit mb-6">
              <Clock className="h-6 w-6" />
            </div>
            <h3 className="text-xl font-bold text-slate-100 mb-3">Faster vs Greener Routes</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Faster routes prioritize highways with high-speed travel. However, aerodynamic drag increases non-linearly with speed, causing combustion engines to burn significantly more fuel. Greener routes select moderate-speed arterials or shorter distances to optimize fuel burn curves.
            </p>
          </div>

          {/* Card 3 */}
          <div className="bg-slate-900/40 border border-slate-800/80 rounded-3xl p-8 hover:border-slate-700/80 hover:bg-slate-900/60 transition-all duration-300">
            <div className="p-3 bg-indigo-500/10 text-indigo-400 rounded-2xl w-fit mb-6">
              <Cpu className="h-6 w-6" />
            </div>
            <h3 className="text-xl font-bold text-slate-100 mb-3">Algorithmic Optimization</h3>
            <p className="text-slate-400 text-sm leading-relaxed">
              Using Dijkstra's algorithm, we construct paths by adjusting edge weights. For the fastest path, edge weights represent time (Distance ÷ Speed). For the greenest path, edge weights represent carbon cost (Distance × Vehicle Emission Rate).
            </p>
          </div>
        </div>
      </div>

      {/* Strategy Comparison Section */}
      <div className="bg-slate-900/20 border-y border-slate-900 py-20 px-6 sm:px-12 lg:px-24">
        <div className="max-w-6xl mx-auto grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
          <div>
            <h2 className="text-3xl font-extrabold text-slate-100 tracking-tight sm:text-4xl">
              Algorithmic Core & Cost Strategies
            </h2>
            <p className="mt-4 text-slate-400 leading-relaxed">
              Our Dijkstra Router evaluates paths based on modular cost calculations injected at runtime. By swapping the routing strategy, the system automatically recalibrates the optimal pathway.
            </p>

            <ul className="mt-8 space-y-4">
              <li className="flex items-start">
                <div className="p-1 bg-cyan-500/15 text-cyan-400 rounded-lg mt-1 mr-3">
                  <Gauge className="h-4 w-4" />
                </div>
                <div>
                  <strong className="text-slate-200">Time Strategy</strong>
                  <p className="text-slate-400 text-sm mt-0.5">
                    Calculates costs as travel hours: <code>Edge Cost = Distance &divide; Average Speed</code>. Minimizes time spent on transit.
                  </p>
                </div>
              </li>
              <li className="flex items-start">
                <div className="p-1 bg-emerald-500/15 text-emerald-400 rounded-lg mt-1 mr-3">
                  <Leaf className="h-4 w-4" />
                </div>
                <div>
                  <strong className="text-slate-200">Carbon Strategy</strong>
                  <p className="text-slate-400 text-sm mt-0.5">
                    Calculates costs as carbon footprint: <code>Edge Cost = Distance &times; Vehicle Emission Rate</code>. Minimizes greenhouse gas emissions.
                  </p>
                </div>
              </li>
            </ul>
          </div>

          {/* Mathematical Card */}
          <div className="bg-slate-900 border border-slate-800 rounded-3xl p-8 shadow-2xl relative overflow-hidden">
            <div className="absolute top-0 right-0 p-4 opacity-10">
              <Cpu className="h-24 w-24 text-cyan-400" />
            </div>
            <h3 className="text-lg font-bold text-slate-200 border-b border-slate-800 pb-4 mb-4">
              Dijkstra Edge Weight Cost Formula
            </h3>

            <div className="space-y-4">
              <div className="bg-slate-950 p-4 rounded-2xl border border-slate-800/60 font-mono text-xs">
                <span className="text-slate-500">// Time-based Optimization Weight</span>
                <div className="text-cyan-400 mt-1">
                  W(u, v) = Distance(u, v) / Speed_Limit(u, v)
                </div>
              </div>
              <div className="bg-slate-950 p-4 rounded-2xl border border-slate-800/60 font-mono text-xs">
                <span className="text-slate-500">// Carbon-based Optimization Weight</span>
                <div className="text-emerald-400 mt-1">
                  W(u, v) = Distance(u, v) * Emission_Rate(Vehicle)
                </div>
              </div>
            </div>

            <div className="mt-6 flex gap-3 text-xs text-slate-500 bg-slate-950/40 p-4 rounded-2xl border border-slate-800/40">
              <Zap className="h-4 w-4 text-amber-400 shrink-0 mt-0.5" />
              <span>
                Note: In complex networks, road topology, vehicle weight, and driving patterns alter the emission rates dynamically across different segments.
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LandingPage;
