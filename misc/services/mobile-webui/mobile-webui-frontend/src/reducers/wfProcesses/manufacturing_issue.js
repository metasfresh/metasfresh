import * as CompleteStatus from '../../constants/CompleteStatus';
import { registerHandler } from './activityStateHandlers';
import { current, isDraft } from 'immer';

const COMPONENT_TYPE = 'manufacturing/rawMaterialsIssue';

export const manufacturingReducer = ({ draftState /*, action*/ }) => {
  return draftState;
};

const updateStep = ({ draftStep }) => {
  draftStep.completeStatus = computeStepStatus({ draftStep });
};

// @VisibleForTesting
export const computeStepStatus = ({ draftStep }) => {
  const isStepCompleted = !!draftStep.qtyIssued || !!draftStep.qtyRejectedReasonCode;
  return isStepCompleted ? CompleteStatus.COMPLETED : CompleteStatus.NOT_STARTED;
};

const updateLineFromSteps = ({ draftLine }) => {
  // Prefer the backend-provided line.qtyIssued: it is already aggregated in the BOM-line UOM
  // (RawMaterialsIssueLine.computeQtyIssued converts each step's issued qty from the picked HU's
  // stocking UOM — e.g. Stk — to the line UOM — e.g. kg — before summing). Summing step qtys here
  // would be wrong whenever the step (stocking) UOM differs from the line UOM, because the frontend
  // has no conversion factor. Fall back to the step sum only when the backend value is absent
  // (legacy/malformed payloads); that fallback is correct only when step UOM == line UOM.
  if (draftLine.qtyIssued == null) {
    draftLine.qtyIssued = computeLineQtyIssuedFromSteps({ draftLine });
  }
  draftLine.qtyToIssueRemaining = Math.max(draftLine.qtyToIssue - draftLine.qtyIssued, 0);
  draftLine.completeStatus = computeLineStatus({ draftLine });
};

const computeLineStatus = ({ draftLine }) => {
  if (!draftLine.qtyIssued) {
    return CompleteStatus.NOT_STARTED;
  }
  const minQtyToIssueForCompletion = draftLine.qtyToIssueMin ?? draftLine.qtyToIssue;
  if (draftLine.qtyIssued >= minQtyToIssueForCompletion) {
    return CompleteStatus.COMPLETED;
  } else {
    return CompleteStatus.IN_PROGRESS;
  }
};

// @VisibleForTesting
export const computeLineQtyIssuedFromSteps = ({ draftLine }) => {
  const steps = isDraft(draftLine) ? Object.values(current(draftLine.steps)) : Object.values(draftLine.steps);
  return steps.reduce((acc, { qtyIssued }) => acc + (qtyIssued ? qtyIssued : 0), 0);
};

const extractDraftMapKeys = (draftMap) => {
  return isDraft(draftMap) ? Object.keys(current(draftMap)) : Object.keys(draftMap);
};

const updateActivityFromLines = ({ draftActivityDataStored }) => {
  draftActivityDataStored.completeStatus = computeActivityStatusFromLines({ draftActivityDataStored });
};

// @VisibleForTesting
export const updateActivityBottomUp = ({ draftActivityDataStored }) => {
  if (draftActivityDataStored.lines) {
    draftActivityDataStored.lines.forEach((line) => {
      if (line.steps) {
        Object.values(line.steps).forEach((step) => updateStep({ draftStep: step }));
      }
      updateLineFromSteps({ draftLine: line });
    });
  }

  updateActivityFromLines({ draftActivityDataStored });
};

const computeActivityStatusFromLines = ({ draftActivityDataStored }) => {
  const lineIds = extractDraftMapKeys(draftActivityDataStored.lines);

  const lineStatuses = [];
  lineIds.forEach((lineId) => {
    const draftLine = draftActivityDataStored.lines[lineId];
    const lineCompleteStatus = draftLine.completeStatus || CompleteStatus.NOT_STARTED;
    if (!lineStatuses.includes(lineCompleteStatus)) {
      lineStatuses.push(lineCompleteStatus);
    }
  });

  return CompleteStatus.reduceFromCompleteStatuesUniqueArray(lineStatuses);
};

// @VisibleForTesting
export const normalizeLines = (lines) => {
  return lines.map((line) => {
    return {
      productName: line.productName,
      productValue: line.productValue,
      uom: line.uom,
      hazardSymbols: line.hazardSymbols ?? [],
      allergens: line.allergens ?? [],
      weightable: line.weightable,
      readOnly: line.readOnly ?? false,
      qtyToIssue: line.qtyToIssue,
      qtyIssued: line.qtyIssued,
      qtyToIssueMin: line.qtyToIssueMin,
      qtyToIssueMax: line.qtyToIssueMax,
      qtyToIssueTolerance: line.qtyToIssueTolerance,
      userInstructions: line.userInstructions,
      seqNo: line.seqNo,
      steps: line.steps.reduce((accum, step) => {
        accum[step.id] = step;
        return accum;
      }, {}),
    };
  });
};

registerHandler({
  componentType: COMPONENT_TYPE,
  normalizeComponentProps: () => {}, // don't add componentProps to state
  mergeActivityDataStored: ({ draftActivityDataStored, fromActivity }) => {
    draftActivityDataStored.isAlwaysAvailableToUser = fromActivity.isAlwaysAvailableToUser ?? true;
    draftActivityDataStored.lines = normalizeLines(fromActivity.componentProps.lines);
    draftActivityDataStored.scaleDevice = fromActivity.componentProps.scaleDevice;
    draftActivityDataStored.qtyRejectedReasons = fromActivity.componentProps.qtyRejectedReasons;
    updateActivityBottomUp({ draftActivityDataStored });
  },
});
