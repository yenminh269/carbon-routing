import React, { useState } from 'react';
import { Route, Clock, Leaf, ShieldAlert, ArrowRight, RefreshCw, Car, Info, Scale } from 'lucide-react';
import api from '../services/api';

interface EdgeInfo {
  toNodeId: string;
  distanceInMiles: number;
}

interface RouteDetail {
  path: string[];
  edges: EdgeInfo[];
  totalDistance: number;
  totalCarbon: number;
  totalTime: number;
}

interface CalculationResult {
  fastest: RouteDetail;
  greenest: RouteDetail;
}

interface VehiclePreset {
  name: string;
  avgSpeed: number;
  emissionRate: number;
  icon: string;
}

const VEHICLE_PRESETS: VehiclePreset[] = [
  { name: 'Electric Delivery Van', avgSpeed: 45, emissionRate: 40, icon: '⚡' },
  { name: 'Hybrid Courier Car', avgSpeed: 50, emissionRate: 110, icon: '🚗' },
  { name: 'Standard Diesel Truck', avgSpeed: 55, emissionRate: 320, icon: '🚛' },
  { name: 'Heavy Semi-Truck', avgSpeed: 50, emissionRate: 680, icon: '🛞' },
];

// Node coordinates for SVG visualization
const NODE_COORDS: { [key: string]: { x: number; y: number } } = {
  A: { x: 100, y: 220 },
  B: { x: 220, y: 80 },
  C: { x: 380, y: 180 },
};

const RouteVisualization: React.FC = () => {
  const [source, setSource] = useState('A');
  const [destination, setDestination] = useState('C');
  const [selectedPreset, setSelectedPreset] = useState(0);
  const [customSpeed, setCustomSpeed] = useState(50);
  const [customEmissions, setCustomEmissions] = useState(200);
  const [isCustom, setIsCustom] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [result, setResult] = useState<CalculationResult | null>(null);

  const getVehicleParams = () => {
    if (isCustom) {
      return {
        name: 'Custom Fleet Vehicle',
        avgSpeed: customSpeed,
        emissionRate: customEmissions,
      };
    }
    const preset = VEHICLE_PRESETS[selectedPreset];
    return {
      name: preset.name,
      avgSpeed: preset.avgSpeed,
      emissionRate: preset.emissionRate,
    };
  };

  const handleCalculate = async (e: React.FormEvent) => {
    e.preventDefault();
    setError('');
    setResult(null);

    if (source === destination) {
      setError('Start and destination nodes must be different.');
      return;
    }

    setLoading(true);
    const vehicleParams = getVehicleParams();

    try {
      const payload = {
        source,
        destination,
        vehicle: vehicleParams.name,
        emissionRate: vehicleParams.emissionRate,
        avgSpeedInMiles: vehicleParams.avgSpeed,
      };

      const response = await api.post('/route/calculate', payload);
      setResult(response.data);
    } catch (err: any) {
      console.error(err);
      if (err.response?.status === 403 || err.response?.status === 401) {
        setError('Authentication expired. Please sign in again.');
      } else {
        setError('Failed to compute routes. Make sure the backend server is running.');
      }
    } finally {
      setLoading(false);
    }
  };

  const formatTime = (hours: number) => {
    const totalMinutes = Math.round(hours * 60);
    if (totalMinutes < 60) return `${totalMinutes} mins`;
    const hrs = Math.floor(totalMinutes / 60);
    const mins = totalMinutes % 60;
    return mins > 0 ? `${hrs}h ${mins}m` : `${hrs}h`;
  };

  // Helper to render paths on SVG
  const getPathDString = (path: string[]) => {
    if (!path || path.length < 2) return '';
    return path.map((node, i) => {
      const coords = NODE_COORDS[node];
      return `${i === 0 ? 'M' : 'L'} ${coords.x} ${coords.y}`;
    }).join(' ');
  };

  return (
    <div className="bg-slate-950 text-slate-100 min-h-screen py-12 px-4 sm:px-6 lg:px-8">
      {/* Background glow */}
      <div className="absolute top-10 right-10 w-96 h-96 bg-cyan-500/5 rounded-full blur-3xl pointer-events-none" />
      <div className="absolute bottom-10 left-10 w-96 h-96 bg-emerald-500/5 rounded-full blur-3xl pointer-events-none" />

      <div className="max-w-7xl mx-auto relative z-10">
        {/* Title */}
        <div className="text-center mb-12">
          <span className="px-3 py-1.5 rounded-full bg-cyan-500/10 border border-cyan-500/30 text-cyan-400 text-xs font-semibold uppercase tracking-wider">
            Operational Dashboard
          </span>
          <h1 className="mt-4 text-4xl font-extrabold tracking-tight bg-gradient-to-r from-emerald-400 to-cyan-400 bg-clip-text text-transparent">
            Route Carbon & Time Analysis
          </h1>
          <p className="mt-2 text-slate-400 max-w-2xl mx-auto">
            Compare routing strategies using standard and custom fleet vehicles.
          </p>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-12 gap-8 items-start">
          {/* Form Side - 4 Columns */}
          <div className="lg:col-span-4 bg-slate-900/60 border border-slate-800 rounded-3xl p-6 backdrop-blur-md">
            <h2 className="text-lg font-bold text-slate-200 mb-6 flex items-center gap-2">
              <Car className="h-5 w-5 text-emerald-400" />
              <span>Route Parameters</span>
            </h2>

            {error && (
              <div className="flex items-start gap-2.5 p-4 mb-6 rounded-2xl border border-red-900/50 bg-red-950/20 text-red-400 text-sm">
                <ShieldAlert className="h-5 w-5 shrink-0" />
                <span>{error}</span>
              </div>
            )}

            <form onSubmit={handleCalculate} className="space-y-6">
              {/* Nodes */}
              <div className="grid grid-cols-2 gap-4">
                <div>
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2">
                    Start Node
                  </label>
                  <select
                    value={source}
                    onChange={(e) => setSource(e.target.value)}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-4 text-slate-300 focus:outline-none focus:border-emerald-500"
                  >
                    <option value="A">Node A</option>
                    <option value="B">Node B</option>
                    <option value="C">Node C</option>
                  </select>
                </div>
                <div>
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider mb-2">
                    Destination Node
                  </label>
                  <select
                    value={destination}
                    onChange={(e) => setDestination(e.target.value)}
                    className="w-full bg-slate-950 border border-slate-800 rounded-2xl py-3 px-4 text-slate-300 focus:outline-none focus:border-emerald-500"
                  >
                    <option value="A">Node A</option>
                    <option value="B">Node B</option>
                    <option value="C">Node C</option>
                  </select>
                </div>
              </div>

              {/* Vehicle Presets */}
              <div>
                <div className="flex items-center justify-between mb-2">
                  <label className="block text-xs font-semibold text-slate-400 uppercase tracking-wider">
                    Vehicle Type
                  </label>
                  <button
                    type="button"
                    onClick={() => setIsCustom(!isCustom)}
                    className="text-xs text-emerald-400 font-semibold hover:underline"
                  >
                    {isCustom ? 'Use Presets' : 'Use Custom Parameters'}
                  </button>
                </div>

                {isCustom ? (
                  <div className="space-y-4 p-4 bg-slate-950 rounded-2xl border border-slate-800">
                    <div>
                      <div className="flex justify-between text-xs text-slate-400 mb-1">
                        <span>Average Speed</span>
                        <span className="text-emerald-400 font-bold">{customSpeed} mph</span>
                      </div>
                      <input
                        type="range"
                        min="10"
                        max="80"
                        value={customSpeed}
                        onChange={(e) => setCustomSpeed(parseInt(e.target.value))}
                        className="w-full accent-emerald-500 bg-slate-800 rounded-lg h-2 outline-none"
                      />
                    </div>
                    <div>
                      <div className="flex justify-between text-xs text-slate-400 mb-1">
                        <span>CO2 Emission Rate</span>
                        <span className="text-emerald-400 font-bold">{customEmissions} g/mile</span>
                      </div>
                      <input
                        type="range"
                        min="10"
                        max="1000"
                        value={customEmissions}
                        onChange={(e) => setCustomEmissions(parseInt(e.target.value))}
                        className="w-full accent-emerald-500 bg-slate-800 rounded-lg h-2 outline-none"
                      />
                    </div>
                  </div>
                ) : (
                  <div className="grid grid-cols-2 gap-3">
                    {VEHICLE_PRESETS.map((preset, idx) => (
                      <button
                        key={idx}
                        type="button"
                        onClick={() => setSelectedPreset(idx)}
                        className={`flex flex-col items-start p-3 rounded-2xl border text-left transition-all ${
                          selectedPreset === idx
                            ? 'bg-emerald-950/30 border-emerald-500 text-white'
                            : 'bg-slate-950/40 border-slate-800/80 hover:border-slate-700 text-slate-400'
                        }`}
                      >
                        <span className="text-xl mb-1">{preset.icon}</span>
                        <span className="text-xs font-bold truncate w-full">{preset.name}</span>
                        <span className="text-[10px] opacity-70 mt-0.5">
                          {preset.avgSpeed}mph · {preset.emissionRate}g/mi
                        </span>
                      </button>
                    ))}
                  </div>
                )}
              </div>

              {/* Calculate Button */}
              <button
                type="submit"
                disabled={loading}
                className="w-full flex items-center justify-center bg-gradient-to-r from-emerald-500 to-cyan-500 hover:from-emerald-400 hover:to-cyan-400 text-slate-950 font-bold py-4 rounded-2xl shadow-lg shadow-emerald-500/10 hover:scale-[1.02] active:scale-98 transition-all duration-200"
              >
                {loading ? (
                  <RefreshCw className="h-5 w-5 animate-spin" />
                ) : (
                  <span className="flex items-center gap-1.5">
                    <span>Calculate Optimization</span>
                    <ArrowRight className="h-4 w-4" />
                  </span>
                )}
              </button>
            </form>
          </div>

          {/* Graph & Results - 8 Columns */}
          <div className="lg:col-span-8 space-y-8">
            {/* SVG Visualizer */}
            <div className="bg-slate-900/60 border border-slate-800 rounded-3xl p-6 backdrop-blur-md relative overflow-hidden">
              <h2 className="text-lg font-bold text-slate-200 mb-4 flex items-center gap-2">
                <Route className="h-5 w-5 text-cyan-400" />
                <span>Topology Map</span>
              </h2>

              <div className="flex justify-center bg-slate-950/60 rounded-2xl border border-slate-800/55 p-4">
                <svg width="480" height="280" className="max-w-full">
                  {/* Grid Lines background */}
                  <defs>
                    <pattern id="grid" width="20" height="20" patternUnits="userSpaceOnUse">
                      <path d="M 20 0 L 0 0 0 20" fill="none" stroke="rgba(255,255,255,0.015)" strokeWidth="1" />
                    </pattern>
                  </defs>
                  <rect width="100%" height="100%" fill="url(#grid)" />

                  {/* Edges Paths (Distances labels) */}
                  {/* A <-> B */}
                  <line x1="100" y1="220" x2="220" y2="80" stroke="#334155" strokeWidth="2" />
                  <text x="145" y="140" fill="#64748b" className="text-xs font-bold" textAnchor="middle">3 mi</text>

                  {/* A <-> C */}
                  <line x1="100" y1="220" x2="380" y2="180" stroke="#334155" strokeWidth="2" />
                  <text x="240" y="215" fill="#64748b" className="text-xs font-bold" textAnchor="middle">5 mi</text>

                  {/* B <-> C */}
                  <line x1="220" y1="80" x2="380" y2="180" stroke="#334155" strokeWidth="2" strokeDasharray="4 4" />
                  <text x="310" y="125" fill="#64748b" className="text-xs font-bold" textAnchor="middle">7 mi</text>

                  {/* ACTIVE PATHS */}
                  {result && (
                    <>
                      {/* Greenest Path Layer */}
                      <path
                        d={getPathDString(result.greenest.path)}
                        fill="none"
                        stroke="#10b981"
                        strokeWidth="5"
                        strokeLinecap="round"
                        className="opacity-70 animate-pulse"
                      />
                      {/* Fastest Path Layer */}
                      <path
                        d={getPathDString(result.fastest.path)}
                        fill="none"
                        stroke="#06b6d4"
                        strokeWidth="3"
                        strokeLinecap="round"
                        className="opacity-90"
                      />
                    </>
                  )}

                  {/* Node Circles */}
                  {Object.entries(NODE_COORDS).map(([id, coord]) => {
                    const isSource = id === source;
                    const isDest = id === destination;
                    let circleFill = '#1e293b';
                    let circleStroke = '#475569';
                    if (isSource) { circleFill = '#0369a1'; circleStroke = '#0ea5e9'; }
                    else if (isDest) { circleFill = '#065f46'; circleStroke = '#10b981'; }

                    return (
                      <g key={id}>
                        <circle
                          cx={coord.x}
                          cy={coord.y}
                          r={18}
                          fill={circleFill}
                          stroke={circleStroke}
                          strokeWidth="2"
                          className="transition-all duration-300"
                        />
                        <text
                          x={coord.x}
                          y={coord.y + 5}
                          fill="#f8fafc"
                          textAnchor="middle"
                          className="text-sm font-extrabold pointer-events-none"
                        >
                          {id}
                        </text>
                      </g>
                    );
                  })}
                </svg>
              </div>

              {/* Map Legend */}
              <div className="flex gap-6 justify-center mt-4 text-xs font-semibold text-slate-400">
                <div className="flex items-center gap-1.5">
                  <span className="h-2 w-4 bg-[#0ea5e9] rounded-full inline-block" />
                  <span>Fastest path (cyan)</span>
                </div>
                <div className="flex items-center gap-1.5">
                  <span className="h-2 w-4 bg-[#10b981] rounded-full inline-block animate-pulse" />
                  <span>Greenest path (green)</span>
                </div>
              </div>
            </div>

            {/* Calculations display */}
            {result ? (
              <div className="space-y-6">
                {/* Comparison Banner */}
                {result.fastest.totalCarbon !== result.greenest.totalCarbon ||
                result.fastest.totalTime !== result.greenest.totalTime ? (
                  <div className="bg-slate-900 border border-slate-800 rounded-3xl p-5 flex items-start gap-4">
                    <div className="p-2.5 bg-amber-500/10 text-amber-400 rounded-2xl shrink-0">
                      <Scale className="h-6 w-6" />
                    </div>
                    <div>
                      <h3 className="font-bold text-slate-200 text-sm">Decision Analysis</h3>
                      <p className="text-slate-400 text-xs mt-1 leading-relaxed">
                        {result.fastest.totalCarbon > result.greenest.totalCarbon ? (
                          <>
                            Taking the <strong className="text-emerald-400">Greenest Route</strong> saves{' '}
                            <span className="text-emerald-400 font-extrabold">
                              {Math.round(result.fastest.totalCarbon - result.greenest.totalCarbon)}g CO2
                            </span>{' '}
                            (
                            {Math.round(
                              ((result.fastest.totalCarbon - result.greenest.totalCarbon) /
                                result.fastest.totalCarbon) *
                                100
                            )}
                            % reduction), but increases travel time by{' '}
                            <span className="text-amber-400 font-extrabold">
                              {formatTime(result.greenest.totalTime - result.fastest.totalTime)}
                            </span>
                            .
                          </>
                        ) : (
                          'Fastest and Greenest routes represent the identical physical path for this vehicle parameters.'
                        )}
                      </p>
                    </div>
                  </div>
                ) : (
                  <div className="bg-emerald-950/20 border border-emerald-900/40 rounded-3xl p-5 flex items-start gap-4">
                    <div className="p-2.5 bg-emerald-500/10 text-emerald-400 rounded-2xl shrink-0">
                      <Info className="h-6 w-6" />
                    </div>
                    <div>
                      <h3 className="font-bold text-emerald-400 text-sm">Perfect Alignment</h3>
                      <p className="text-emerald-500/80 text-xs mt-1 leading-relaxed">
                        In this scenario, the fastest route is also the greenest route! You get optimal speed and zero redundant carbon.
                      </p>
                    </div>
                  </div>
                )}

                {/* Two Cards Side-by-Side */}
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
                  {/* Fastest Card */}
                  <div className="bg-slate-900/60 border border-slate-800 rounded-3xl p-6 flex flex-col relative overflow-hidden">
                    <div className="absolute top-0 right-0 h-24 w-24 bg-cyan-500/5 rounded-full blur-2xl" />
                    <div className="flex items-center justify-between mb-6">
                      <span className="px-2.5 py-1 rounded-lg bg-cyan-500/15 border border-cyan-500/30 text-cyan-400 text-[10px] font-bold uppercase tracking-wider">
                        Fastest Strategy
                      </span>
                      <Clock className="h-5 w-5 text-cyan-400" />
                    </div>

                    <div className="space-y-4">
                      {/* Metric 1 */}
                      <div>
                        <span className="text-[10px] uppercase font-bold tracking-wider text-slate-500">Travel Time</span>
                        <div className="text-2xl font-extrabold text-white mt-0.5">
                          {formatTime(result.fastest.totalTime)}
                        </div>
                      </div>
                      {/* Metric 2 */}
                      <div>
                        <span className="text-[10px] uppercase font-bold tracking-wider text-slate-500">CO2 Emissions</span>
                        <div className="text-lg font-bold text-slate-300 mt-0.5">
                          {result.fastest.totalCarbon} g
                        </div>
                      </div>
                      {/* Path */}
                      <div>
                        <span className="text-[10px] uppercase font-bold tracking-wider text-slate-500">Node Path</span>
                        <div className="flex items-center gap-1.5 mt-1">
                          {result.fastest.path.map((node, i) => (
                            <React.Fragment key={i}>
                              {i > 0 && <span className="text-slate-600 text-xs">➔</span>}
                              <span className="px-2 py-1 rounded bg-slate-950 border border-slate-800 text-xs font-bold">
                                {node}
                              </span>
                            </React.Fragment>
                          ))}
                        </div>
                      </div>
                      {/* Total Distance */}
                      <div className="border-t border-slate-800 pt-4 flex justify-between text-xs text-slate-400">
                        <span>Total Distance</span>
                        <span className="font-bold text-slate-300">{result.fastest.totalDistance} miles</span>
                      </div>
                    </div>
                  </div>

                  {/* Greenest Card */}
                  <div className="bg-slate-900/60 border border-slate-800 rounded-3xl p-6 flex flex-col relative overflow-hidden">
                    <div className="absolute top-0 right-0 h-24 w-24 bg-emerald-500/5 rounded-full blur-2xl" />
                    <div className="flex items-center justify-between mb-6">
                      <span className="px-2.5 py-1 rounded-lg bg-emerald-500/15 border border-emerald-500/30 text-emerald-400 text-[10px] font-bold uppercase tracking-wider">
                        Greenest Strategy
                      </span>
                      <Leaf className="h-5 w-5 text-emerald-400" />
                    </div>

                    <div className="space-y-4">
                      {/* Metric 1 */}
                      <div>
                        <span className="text-[10px] uppercase font-bold tracking-wider text-slate-500">CO2 Emissions</span>
                        <div className="text-2xl font-extrabold text-emerald-400 mt-0.5">
                          {result.greenest.totalCarbon} g
                        </div>
                      </div>
                      {/* Metric 2 */}
                      <div>
                        <span className="text-[10px] uppercase font-bold tracking-wider text-slate-500">Travel Time</span>
                        <div className="text-lg font-bold text-slate-300 mt-0.5">
                          {formatTime(result.greenest.totalTime)}
                        </div>
                      </div>
                      {/* Path */}
                      <div>
                        <span className="text-[10px] uppercase font-bold tracking-wider text-slate-500">Node Path</span>
                        <div className="flex items-center gap-1.5 mt-1">
                          {result.greenest.path.map((node, i) => (
                            <React.Fragment key={i}>
                              {i > 0 && <span className="text-slate-600 text-xs">➔</span>}
                              <span className="px-2 py-1 rounded bg-slate-950 border border-slate-800 text-xs font-bold">
                                {node}
                              </span>
                            </React.Fragment>
                          ))}
                        </div>
                      </div>
                      {/* Total Distance */}
                      <div className="border-t border-slate-800 pt-4 flex justify-between text-xs text-slate-400">
                        <span>Total Distance</span>
                        <span className="font-bold text-slate-300">{result.greenest.totalDistance} miles</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            ) : (
              <div className="h-64 border border-slate-800/80 border-dashed rounded-3xl flex flex-col items-center justify-center text-slate-500 bg-slate-900/10">
                <Scale className="h-10 w-10 text-slate-700 mb-2" />
                <span>Specify nodes and hit calculate to view comparison details</span>
              </div>
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default RouteVisualization;
