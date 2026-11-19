import { getLinesArrayFromActivity } from '../reducers/wfProcesses';
import * as CompleteStatus from '../constants/CompleteStatus';

export const isUserEditable = ({ activity }) => activity?.dataStored?.isUserEditable ?? false;

export const isLineLevelPickTarget = ({ activity }) => {
  return activity?.dataStored?.isLineLevelPickTarget;
};

export const getCurrentPickFromHUQRCode = ({ activity }) => activity.dataStored?.pickFromHU?.code;

export const getPickFromForStep = ({ stepProps, altStepId }) => {
  return altStepId ? stepProps.pickFromAlternatives[altStepId] : stepProps.mainPickFrom;
};

export const getQtyToPickForStep = ({ stepProps, altStepId }) => {
  return altStepId ? getPickFromForStep({ stepProps, altStepId }).qtyToPick : stepProps.qtyToPick;
};

export const getQtyToPickForLine = ({ line }) => {
  return line?.qtyToPick ?? 0;
};

export const getQtyPickedOrRejectedTotalForLine = ({ line }) => {
  return line?.qtyPickedOrRejected ?? 0;
};

export const getQtyToPickRemainingForLine = ({ line }) => {
  return line?.qtyRemainingToPick ?? 0;
};

export const getNextEligibleLineToPick = ({ activity, excludeLineId }) => {
  console.group('getNextEligibleLineToPick', { activity, excludeLineId });
  let lines = getLinesArrayFromActivity(activity);
  console.log('all activity lines', { lines });

  lines = lines.filter((line) => isLineNotCompleted({ line }) && isAllowPickingAnyHUForLine({ line }));
  console.log('lines not completed', { lines });

  if (lines.length > 0 && excludeLineId) {
    lines = lines.filter((line) => String(line.pickingLineId) !== String(excludeLineId));
    console.log('lines excluding given line', { lines, excludeLineId });
  }

  console.groupEnd();

  return lines.length > 0 ? lines[0] : null;
};

export const isAllowPickingAnyHUOnHeaderLevel = ({ activity }) => {
  if (isLineLevelPickTarget({ activity })) {
    // i.e. don't show the Scan HU button above the lines because it is supposed to be scanned by a previous activity
    return false;
  }

  return getLinesArrayFromActivity(activity).some((line) => isAllowPickingAnyHUForLine({ line }));
};

export const isAllowPickingAnyHUForLine = ({ line }) => {
  return !!line?.allowPickingAnyHU;
};

export const isLineNotCompleted = ({ line }) => line.completeStatus !== CompleteStatus.COMPLETED;

export const isCatchWeight = ({ line }) => {
  return !!line.catchWeightUOM;
};

/**
 * Scan once: the count the picker is offered for the next box = the last typed count, capped by the open quantity.
 *
 * @returns null before the first typed value
 */
export const computeSuggestedQty = ({ lastTypedQty, qtyOpen } = {}) => {
  if (lastTypedQty == null || lastTypedQty === '') {
    return null;
  }
  const last = Number(lastTypedQty);
  return qtyOpen == null ? last : Math.min(last, qtyOpen);
};

/**
 * The count to remember after a booking. Confirming the offered suggestion (also a capped one) keeps the count the
 * picker typed; any other count is a newly typed one.
 */
export const computeLastTypedQty = ({ previousLastTypedQty, suggestedQty, qtyBooked }) => {
  const booked = Number(qtyBooked);
  if (previousLastTypedQty != null && suggestedQty != null && booked === Number(suggestedQty)) {
    return Number(previousLastTypedQty);
  }
  return booked;
};

/** @returns true when the booking reaches (or exceeds) the open quantity, or when that quantity is unknown */
export const isLineDoneAfterBooking = ({ qtyBooked, qtyOpen }) => qtyOpen == null || Number(qtyBooked) >= qtyOpen;

/**
 * What the screen remembers after a scan once booking of a line: the last typed count and the label (with its lot and
 * best-before) the next booking uses.
 *
 * @returns {{lastTypedQty, label}} or null when the booking carried no label
 */
export const computeCUEntryToRemember = ({
  previousLastTypedQty,
  suggestedQty,
  qtyBooked,
  cuLabel,
  lotNo,
  bestBeforeDate,
}) =>
  cuLabel
    ? {
        lastTypedQty: computeLastTypedQty({ previousLastTypedQty, suggestedQty, qtyBooked }),
        label: { cuLabel, lotNo, bestBeforeDate },
      }
    : null;
