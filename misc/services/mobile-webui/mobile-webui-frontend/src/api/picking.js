import axios from 'axios';
import { apiBasePath } from '../constants';
import { toUrl, unboxAxiosResponse } from '../utils';
import { QTY_REJECTED_REASON_TO_IGNORE_KEY } from '../reducers/wfProcesses';
import { useQuery } from '../hooks/useQuery';
import { PickingTargetType } from '../constants/PickingTargetType';
import { toQRCodeString } from '../utils/qrCode/hu';

// Stable empty-array reference: avoids handing callers a fresh [] literal every render (a caller may
// use this as a useCallback dependency — e.g. ScanHUAndGetQtyComponent's handleAddGrais — where a
// changing reference would defeat the stable-handler-identity guarantee needed for RFID burst safety).
const EMPTY_LU_GRAIS = [];

export const useAvailablePickingTargets = ({ wfProcessId, lineId, stepId, altStepId, type }) => {
  const isTU = type === PickingTargetType.TU;
  const { isPending: isTargetsLoading, data: responseData } = useQuery({
    // stepId/altStepId are in the key (not the URL) purely to force a re-fetch on every pick-step
    // entry: two steps of the SAME line share lineId, and PickStepScanScreen is mounted on sibling
    // routes that differ only by altStepId (react-router v5, no <Switch>), so a step→step move need
    // not remount the component. Without stepId in the key, step 2's capture would reuse step 1's
    // stale existingLuGrais and miss a same-line, same-LU duplicate. The server still resolves the
    // response from lineId alone. Callers that omit stepId (line-scan / select-target screens) pass a
    // stable undefined, so their fetch behaviour is unchanged.
    queryKey: [wfProcessId, lineId, stepId, altStepId, type],
    queryFn: () => getAvailablePickingTargets({ wfProcessId, lineId }),
  });

  const targets = responseData ? (isTU ? responseData.tuTargets : responseData.targets) : undefined;
  const graiScanEnabled = isTU ? responseData?.graiScanEnabled ?? false : false;
  // Canonical GRAI strings already assigned to the line's effective LU (from prior picks on this LU) —
  // lets the mobile capture panel mirror the server-side LU-wide dedupe. Re-fetched on every pick-step
  // entry (see the queryKey note above), so it always reflects the LU state AFTER the prior pick's
  // atomic Save committed.
  const existingLuGrais = isTU ? responseData?.existingLuGrais ?? EMPTY_LU_GRAIS : EMPTY_LU_GRAIS;

  return {
    isTargetsLoading,
    targets,
    graiScanEnabled,
    existingLuGrais,
    setPickingTarget: ({ target }) => {
      return isTU
        ? setTUPickingTarget({ wfProcessId, lineId, target })
        : setLUPickingTarget({ wfProcessId, lineId, target });
    },
  };
};

const getAvailablePickingTargets = ({ wfProcessId, lineId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/available`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

export const setLUPickingTarget = ({ wfProcessId, lineId, target }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target`, { lineId }), target)
    .then((response) => unboxAxiosResponse(response));
};

const setTUPickingTarget = ({ wfProcessId, lineId, target }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/tu`, { lineId }), target)
    .then((response) => unboxAxiosResponse(response));
};

export const setTUPickingTargetFromGrai = ({ wfProcessId, lineId, graiString }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/tu`, { lineId }), { grai: graiString })
    .then((response) => unboxAxiosResponse(response));
};

export const closePickingTarget = ({ wfProcessId, lineId, type }) => {
  return type === PickingTargetType.TU
    ? closeTUPickingTarget({ wfProcessId, lineId })
    : closeLUPickingTarget({ wfProcessId, lineId });
};

const closeLUPickingTarget = ({ wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/close`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

const closeTUPickingTarget = ({ wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/tu/close`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

export const postStepPicked = ({
  wfProcessId,
  activityId,
  lineId,
  stepId,
  huQRCode,
  qtyPicked,
  qtyRejected,
  qtyRejectedReasonCode,
  catchWeight,
  pickWholeTU,
  checkIfAlreadyPacked,
  setBestBeforeDate,
  bestBeforeDate,
  productionDate,
  setLotNo,
  lotNo,
  setGrais,
  graiCodes,
  setSerialNos,
  serialNos,
  isCloseTarget = false,
  isShelfLifeConfirmed = false,
}) => {
  const realRejectedQtyReason =
    qtyRejectedReasonCode === QTY_REJECTED_REASON_TO_IGNORE_KEY ? null : qtyRejectedReasonCode;

  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    pickingStepId: stepId,
    type: 'PICK',
    huQRCode,
    qtyPicked,
    qtyRejectedReasonCode: realRejectedQtyReason,
    qtyRejected,
    catchWeight,
    pickWholeTU,
    checkIfAlreadyPacked,
    setBestBeforeDate,
    bestBeforeDate,
    productionDate,
    setLotNo,
    lotNo,
    setGrais,
    graiCodes,
    setSerialNos,
    serialNos,
    isCloseTarget,
    isShelfLifeConfirmed,
  });
};

export const postStepUnPicked = ({ wfProcessId, activityId, lineId, stepId, huQRCode, unpickToTargetQRCode }) => {
  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    pickingStepId: stepId,
    type: 'UNPICK',
    huQRCode,
    unpickToTargetQRCode,
  });
};

export const resolveUnpickByScannedCode = ({ wfProcessId, scannedCode }) => {
  return axios
    .post(`${apiBasePath}/picking/unpick/resolve`, { wfProcessId, scannedCode })
    .then((response) => unboxAxiosResponse(response));
};

export const postStepPartiallyUnPicked = ({
  wfProcessId,
  activityId,
  lineId,
  scannedCode,
  unpickProductId,
  unpickQty,
  unpickToTargetQRCode,
}) => {
  // Job-scoped removal: no pickingStepId, so the backend reverses the product across the whole job.
  // huQRCode is structurally required (non-blank) by the event DTO but unused on the product+qty
  // subset path, so the scanned product code stands in for it.
  return postEvent({
    wfProcessId,
    wfActivityId: activityId,
    pickingLineId: lineId,
    type: 'UNPICK',
    huQRCode: scannedCode,
    unpickProductId,
    unpickQty,
    unpickToTargetQRCode,
  });
};

const postEvent = (event) => {
  return axios.post(`${apiBasePath}/picking/event`, event).then((response) => unboxAxiosResponse(response));
};

export const postPickAll = ({ wfProcessId }) => {
  return axios
    .post(`${apiBasePath}/picking/job/${wfProcessId}/pickAll`)
    .then((response) => unboxAxiosResponse(response));
};

export const closePickingJobLine = ({ wfProcessId, lineId }) => {
  return axios
    .post(`${apiBasePath}/picking/closeLine`, { wfProcessId, pickingLineId: lineId })
    .then((response) => unboxAxiosResponse(response));
};

export const openPickingJobLine = ({ wfProcessId, lineId }) => {
  return axios
    .post(`${apiBasePath}/picking/openLine`, { wfProcessId, pickingLineId: lineId })
    .then((response) => unboxAxiosResponse(response));
};

export const hasClosedLUs = ({ wfProcessId, lineId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/has-closed-lu`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

export const getClosedLUs = ({ wfProcessId, lineId }) => {
  return axios
    .get(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/closed-lu`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};

export const getScannedHUQRCodeInfo = ({ qrCode, productNo, wfProcessId, lineId }) => {
  return axios
    .post(`${apiBasePath}/picking/hu/byScannedCode`, { scannedCode: qrCode, productNo, wfProcessId, lineId })
    .then((response) => unboxAxiosResponse(response));
};

export const getNextEligibleLineToPack = ({ wfProcessId, huScannedCode, excludeLineId }) => {
  return axios
    .post(`${apiBasePath}/picking/nextEligibleLineToPack`, {
      wfProcessId,
      huScannedCode: toQRCodeString(huScannedCode),
      excludeLineId,
    })
    .then((response) => unboxAxiosResponse(response));
};

export const getQtyAvailable = ({ wfProcessId }) => {
  return axios
    .get(`${apiBasePath}/picking/job/${wfProcessId}/qtyAvailable`)
    .then((response) => unboxAxiosResponse(response));
};

export const completePickingJob = ({ wfProcessId }) => {
  return axios
    .post(`${apiBasePath}/picking/job/${wfProcessId}/complete`)
    .then((response) => unboxAxiosResponse(response));
};

export const postMassPrintingScan = ({ scannedCode }) => {
  return axios
    .post(`${apiBasePath}/picking/massPrinting/scan`, { scannedCode })
    .then((response) => unboxAxiosResponse(response));
};

export const advisePickingTarget = ({ wfProcessId, lineId }) => {
  return axios
    .post(toUrl(`${apiBasePath}/picking/job/${wfProcessId}/target/advise`, { lineId }))
    .then((response) => unboxAxiosResponse(response));
};
