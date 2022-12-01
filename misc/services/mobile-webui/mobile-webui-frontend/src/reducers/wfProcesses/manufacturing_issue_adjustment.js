import { registerHandler } from './activityStateHandlers';

const COMPONENT_TYPE = 'manufacturing/rawMaterialsIssueAdjust';

export const reducer = ({ draftState }) => {
  return draftState;
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.rawMaterialsIssueActivityId = fromActivity.componentProps.rawMaterialsIssueActivityId;
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? true;
    return draftActivityDataStored;
  },
});
