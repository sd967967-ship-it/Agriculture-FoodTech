import React from 'react';

export default function WeatherCard({ weatherContext, cropStageRelevance, districtContext, language = 'en' }) {
  if (!weatherContext && !cropStageRelevance && !districtContext) return null;
  const labels = {
    en: { title: 'Contextual Insights', weather: 'Weather', stage: 'Crop Stage', district: 'Soil & District' },
    bn: { title: 'প্রাসঙ্গিক তথ্য', weather: 'আবহাওয়া', stage: 'ফসলের পর্যায়', district: 'মাটি ও জেলা' },
    hi: { title: 'संदर्भ जानकारी', weather: 'मौसम', stage: 'फसल का चरण', district: 'मिट्टी और जिला' },
  }[language] || { title: 'Contextual Insights', weather: 'Weather', stage: 'Crop Stage', district: 'Soil & District' };

  return (
    <div className="rounded-[1.4rem] border border-emerald-500/20 bg-[linear-gradient(180deg,rgba(11,23,20,0.95),rgba(9,17,14,0.96))] p-5 shadow-[0_20px_40px_rgba(9,17,14,0.35)]">
      <h3 className="mb-4 border-b border-emerald-500/20 pb-2 text-lg font-semibold text-slate-100">{labels.title}</h3>
      <div className="space-y-4">
        {weatherContext && (
          <div className="flex gap-3">
            <span className="flex-shrink-0 text-xl">🌦️</span>
            <div>
              <h4 className="text-sm font-medium text-emerald-200">{labels.weather}</h4>
              <p className="text-sm text-slate-300">{weatherContext}</p>
            </div>
          </div>
        )}
        
        {weatherContext && (cropStageRelevance || districtContext) && (
          <div className="h-px w-full bg-slate-700/80"></div>
        )}

        {cropStageRelevance && (
          <div className="flex gap-3">
            <span className="flex-shrink-0 text-xl">🌱</span>
            <div>
              <h4 className="text-sm font-medium text-emerald-200">{labels.stage}</h4>
              <p className="text-sm text-slate-300">{cropStageRelevance}</p>
            </div>
          </div>
        )}
        
        {cropStageRelevance && districtContext && (
          <div className="h-px w-full bg-slate-700/80"></div>
        )}

        {districtContext && (
          <div className="flex gap-3">
            <span className="flex-shrink-0 text-xl">🗺️</span>
            <div>
              <h4 className="text-sm font-medium text-emerald-200">{labels.district}</h4>
              <p className="text-sm text-slate-300">{districtContext}</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}
