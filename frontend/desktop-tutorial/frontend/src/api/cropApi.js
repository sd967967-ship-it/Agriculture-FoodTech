import axios from 'axios';

const api = axios.create({
  baseURL: '/api/v1',
  timeout: 30000,
});

function sessionHeaders() {
  const token = localStorage.getItem('fasal-sathi-session-token');
  return token ? { 'X-Session-Token': token } : {};
}

export function diagnose(image, metadata = {}) {
  const formData = new FormData();
  formData.append('image', image);

  Object.entries(metadata).forEach(([key, value]) => {
    if (value !== null && value !== undefined && value !== '') {
      formData.append(key, String(value));
    }
  });

  return api.post('/diagnose', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
}

export function transcribeAudio(audioBlob, language = 'en') {
  const formData = new FormData();
  formData.append('audio', audioBlob, 'voice.wav');
  formData.append('language', language);

  return api.post('/speech/transcribe', formData, {
    headers: { 'Content-Type': 'multipart/form-data' },
  });
}

export function getDistricts() {
  return api.get('/districts');
}

export function getCrops() {
  return api.get('/crops');
}

export function getTranslations() {
  return api.get('/translations');
}

export function getWeather(latitude, longitude) {
  return api.get('/weather', { params: { lat: latitude, lon: longitude } });
}

export function getKvkInfo(district, latitude, longitude) {
  return api.get('/kvk', { params: { district, lat: latitude, lon: longitude } });
}

export function getMandiPrices(crop, state, district) {
  return api.get('/mandi-prices', { params: { crop, state, district } });
}

export function healthCheck() {
  return api.get('/health');
}

export function getDiagnosisHistory() {
  return api.get('/diagnosis-history', { headers: sessionHeaders() });
}

export function login(username, password) {
  return api.post('/auth/login', { username, password });
}

export function register(username, password, role = 'FARMER') {
  return api.post('/auth/register', { username, password, role });
}

export function getCurrentUser() {
  return api.get('/auth/me', { headers: sessionHeaders() });
}

export function getFarms(farmerUsername) {
  return api.get('/farms', { params: farmerUsername ? { farmerUsername } : {} });
}

export function createPestObservation(observation) {
  return api.post('/pest-observations', observation);
}

export function getPestObservations(farmId) {
  return api.get('/pest-observations', { params: farmId ? { farmId } : {} });
}

export function getFollowUps(params = {}) {
  return api.get('/follow-ups', { params });
}

export function createFollowUp(task) {
  return api.post('/follow-ups', task);
}

export function completeFollowUp(id) {
  return api.post(`/follow-ups/${id}/complete`);
}

export function getHotspots(params = {}) {
  return api.get('/hotspots', { params });
}

export function getAdminDashboard() {
  return api.get('/admin/dashboard');
}

export function submitDiagnosisFeedback(feedback) {
  return api.post('/diagnosis-feedback', feedback);
}

export function createReferral(referral) {
  return api.post('/referrals', referral);
}

export default api;
