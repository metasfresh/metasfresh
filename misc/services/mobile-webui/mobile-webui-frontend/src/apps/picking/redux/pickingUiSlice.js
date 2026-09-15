import { createSlice } from '@reduxjs/toolkit';
import { getApplicationState } from '../../index';

// Keep in sync with APPLICATION_ID_Picking in apps/picking/index.js. Hardcoded here to avoid a
// direct child-to-parent import from apps/picking/index.js (which imports this module); the cycle
// via apps/index.js already exists and is tolerated by the bundler (refs resolve at call time).
const APPLICATION_ID_PICKING = 'picking';

//
// Module-level resolver for the pending shelf-life confirmation Promise.
// The thunk stores its resolve here; the dialog host calls it on Yes/No.
//
let _pendingShelfLifeResolver = null;

export const storePendingShelfLifeResolver = (resolve) => {
  _pendingShelfLifeResolver = resolve;
};

export const resolvePendingShelfLife = (confirmed) => {
  const resolve = _pendingShelfLifeResolver;
  _pendingShelfLifeResolver = null;
  if (resolve) {
    resolve(confirmed);
  }
};

//
// Slice
//

const pickingUiSlice = createSlice({
  name: 'pickingUi',
  initialState: {
    pendingShelfLifeConfirmation: null, // { message: string } or null
  },
  reducers: {
    setPendingShelfLifeConfirmation: (state, action) => {
      state.pendingShelfLifeConfirmation = action.payload; // { message }
    },
    clearPendingShelfLifeConfirmation: (state) => {
      state.pendingShelfLifeConfirmation = null;
    },
  },
});

export const { setPendingShelfLifeConfirmation, clearPendingShelfLifeConfirmation } = pickingUiSlice.actions;

export const pickingUiReducer = pickingUiSlice.reducer;

export const selectPendingShelfLifeConfirmation = (globalState) =>
  getApplicationState(globalState, APPLICATION_ID_PICKING)?.pendingShelfLifeConfirmation ?? null;
