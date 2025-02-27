import { toQRCodeString } from '../../utils/qrCode/hu';
import { getLineByIdFromActivity, getStepsArrayFromLine } from './index';

export const getNonIssuedStepByQRCodeFromActivity = ({ activity, lineId, qrCode }) => {
  const qrCodeNorm = toQRCodeString(qrCode);
  const line = getLineByIdFromActivity(activity, lineId);
  const steps = getStepsArrayFromLine(line);
  return steps.filter((step) => !isStepIssued(step)).find((step) => toQRCodeString(step.huQRCode) === qrCodeNorm);
};

export const getNonIssuedStepByHuIdFromActivity = ({ activity, lineId, huId }) => {
  const line = getLineByIdFromActivity(activity, lineId);
  const steps = getStepsArrayFromLine(line);
  return steps.filter((step) => !isStepIssued(step)).find((step) => step.huId === huId);
};

const isStepIssued = (step) => step.qtyIssued > 0 || step.qtyRejected > 0;
