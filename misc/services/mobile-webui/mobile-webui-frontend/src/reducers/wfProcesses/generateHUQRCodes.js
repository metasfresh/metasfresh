import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';

const COMPONENT_TYPE = 'manufacturing/generateHUQRCodes';

export const generateHUQRCodesReducer = ({ draftState }) => {
  // nothing atm
  return draftState;
};

registerHandler({
  componentType: COMPONENT_TYPE,
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? true;
    return draftActivityDataStored;
  },

  computeActivityStatus: () => CompleteStatus.COMPLETED,
});
