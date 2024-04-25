import { produce } from 'immer';
import { workflowReducer } from './workflow';
import { scanReducer } from './scan';
import { activityUserConfirmationReducer } from './confirmation';
import { distributionReducer } from './distribution';
import { manufacturingReducer as manufacturingIssueReducer } from './manufacturing_issue';
import { reducer as manufacturingIssueAdjustmentReducer } from './manufacturing_issue_adjustment';
import { manufacturingReducer as manufacturingReceiptReducer } from './manufacturing_receipt';
import { generateHUQRCodesReducer } from './generateHUQRCodes';
import { toQRCodeString } from '../../utils/qrCode/hu';

export const getWfProcess = (globalState, wfProcessId) => {
  if (!wfProcessId) {
    console.trace(`getWfProcess called with wfProcessId=${wfProcessId}`);
  }

  return globalState.wfProcesses[wfProcessId];
};

export const isWfProcessLoaded = (state, wfProcessId) => {
  if (!wfProcessId) {
    console.trace(`isWfProcessLoaded called with wfProcessId=${wfProcessId}`);
    return false;
  }

  return !!state.wfProcesses[wfProcessId];
};

export const getActivitiesInOrder = (wfProcess) => {
  const activityIdsInOrder = wfProcess.activityIdsInOrder ?? [];
  const activitiesById = wfProcess.activities ?? {};
  return activityIdsInOrder.map((activityId) => activitiesById[activityId]);
};

export const getFirstActivityByComponentType = ({ state, wfProcessId, componentType }) => {
  const wfProcess = getWfProcess(state, wfProcessId);
  const activityIdsInOrder = wfProcess.activityIdsInOrder ?? [];
  const activitiesById = wfProcess.activities ?? {};
  for (const activityId of activityIdsInOrder) {
    const activity = activitiesById[activityId];
    if (activity?.componentType === componentType) {
      return activity;
    }
  }

  return null;
};

export const getActivityById = (state, wfProcessId, activityId) => {
  const wfProcess = getWfProcess(state, wfProcessId);
  return getActivityByIdFromWFProcess(wfProcess, activityId);
};

export const getActivityByIdFromWFProcess = (wfProcess, activityId) => {
  return wfProcess?.activities?.[activityId] ?? {};
};

export const getLineByIdFromActivity = (activity, lineId) => {
  return getLinesFromActivity(activity)[lineId];
};

export const getLinesArrayFromActivity = (activity) => {
  return Object.values(activity?.dataStored?.lines ?? {});
};

const getLinesFromActivity = (activity) => {
  return activity?.dataStored?.lines ?? {};
};

export const getLineByIdFromWFProcess = (wfProcess, activityId, lineId) => {
  const activity = wfProcess?.activities?.[activityId];
  return getLineByIdFromActivity(activity, lineId);
};

export const getLineById = (state, wfProcessId, activityId, lineId) => {
  const activity = getActivityById(state, wfProcessId, activityId);
  return getLineByIdFromActivity(activity, lineId);
};

export const getStepsArrayFromLine = (line) => {
  const stepsById = line?.steps ?? {};
  return Object.values(stepsById);
};

export const getSteps = (state, wfProcessId, activityId, lineId) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return getStepsArrayFromLine(line);
};

export const getStepById = (state, wfProcessId, activityId, lineId, stepId) => {
  const line = getLineById(state, wfProcessId, activityId, lineId);
  return getStepByIdFromLine(line, stepId);
};

export const getStepByIdFromActivity = (activity, lineId, stepId) => {
  const line = getLineByIdFromActivity(activity, lineId);
  return getStepByIdFromLine(line, stepId);
};

export const getStepByIdFromLine = (line, stepId) => {
  return line?.steps?.[stepId];
};

export const getNonIssuedStepByQRCodeFromActivity = (activity, lineId, qrCode) => {
  const qrCodeNorm = toQRCodeString(qrCode);
  const line = getLineByIdFromActivity(activity, lineId);
  const steps = getStepsArrayFromLine(line);
  return steps.filter((step) => !isStepIssued(step)).find((step) => toQRCodeString(step.huQRCode) === qrCodeNorm);
};

export const getNonIssuedStepByHuIdFromActivity = (activity, lineId, huId) => {
  const line = getLineByIdFromActivity(activity, lineId);
  const steps = getStepsArrayFromLine(line);
  return steps.filter((step) => !isStepIssued(step)).find((step) => step.huId === huId);
};

export const getQtyRejectedReasonsFromActivity = (activity) => {
  return activity?.dataStored?.qtyRejectedReasons?.reasons ?? [];
};

export const getScaleDeviceFromActivity = (activity) => {
  return activity?.dataStored?.scaleDevice;
};

const isStepIssued = (step) => step.qtyIssued > 0 || step.qtyRejected > 0;

const reducer = produce((draftState, action) => {
  draftState = workflowReducer({ draftState, action });
  draftState = scanReducer({ draftState, action });
  draftState = activityUserConfirmationReducer({ draftState, action });
  //draftState = pickingReducer({ draftState, action });
  draftState = distributionReducer({ draftState, action });
  draftState = generateHUQRCodesReducer({ draftState, action });
  draftState = manufacturingIssueReducer({ draftState, action });
  draftState = manufacturingIssueAdjustmentReducer({ draftState, action });
  draftState = manufacturingReceiptReducer({ draftState, action });

  return draftState;
}, {});

export default reducer;
