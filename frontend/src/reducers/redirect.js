import { createSlice } from '@reduxjs/toolkit';
import { useSelector } from 'react-redux';

const initialState = {
  attemptedUrl: null,
  targetUrl: null,
  options: {},
};

const redirectSlice = createSlice({
  name: 'redirect',
  initialState,
  reducers: {
    requestRedirect: (state, action) => {
      state.attemptedUrl = action.payload;
    },
    confirmRedirect: (state) => {
      state.targetUrl = state.attemptedUrl;
      state.attemptedUrl = null;
    },
    cancelRedirect: (state) => {
      state.attemptedUrl = null;
      state.targetUrl = null;
      state.options = {};
    },
    resetRedirect: (state) => {
      state.attemptedUrl = null;
      state.targetUrl = null;
    },
  },
});

export const {
  requestRedirect,
  confirmRedirect,
  cancelRedirect,
  resetRedirect,
} = redirectSlice.actions;

export default redirectSlice.reducer;

export const useAttemptedUrl = () =>
  useSelector((state) => state.redirect.attemptedUrl);

export const useTargetUrl = () =>
  useSelector((state) => state.redirect.targetUrl);
