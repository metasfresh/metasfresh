import { COMPONENTTYPE_ScanBarcode } from '../../containers/activities/scan/ScanActivity';
import { getNextEligibleLineToPick } from '../../utils/picking';
import { getActivityById, getFirstActivityByComponentType } from '../../reducers/wfProcesses';
import { pickingLineScanScreenLocation } from '../../routes/picking';
import { COMPONENTTYPE_PickProducts } from '../../containers/activities/picking/PickProductsActivity';
import { NEXT_NextPickingLine } from '../../containers/activities/picking/PickLineScanScreen';

const APPLICATION_ID_Picking = 'picking';

export const applicationDescriptor = {
  applicationId: APPLICATION_ID_Picking,

  onWFActivityCompleted: ({ applicationId, wfProcessId, activityId, history, getState }) => {
    // console.log('onWFActivityCompleted', { applicationId, wfProcessId, activityId, dispatch });
    if (applicationId !== APPLICATION_ID_Picking) {
      return;
    }

    const state = getState();
    const activity = getActivityById(state, wfProcessId, activityId);

    //
    // Scan picking slot activity completed
    if (isScanPickingSlotActivity(activity)) {
      openFirstEligiblePickingLineScanner({ state, applicationId, wfProcessId, history });
    }
  },
};

const isScanPickingSlotActivity = (activity) => {
  // NOTE: we assume there is only one ScanBarcode activity and that's about scanning the current picking slot
  const componentType = activity?.componentType;
  return componentType === COMPONENTTYPE_ScanBarcode;
};

const openFirstEligiblePickingLineScanner = ({ state, applicationId, wfProcessId, history }) => {
  const pickActivity = getFirstActivityByComponentType({
    state,
    wfProcessId,
    componentType: COMPONENTTYPE_PickProducts,
  });

  const eligibleLine = getNextEligibleLineToPick({ activity: pickActivity });
  const lineId = eligibleLine?.pickingLineId;
  // console.log('onWFActivityCompleted', { lineId, lines, linesToPick, activity });
  if (!lineId) {
    return;
  }

  history.replace(
    pickingLineScanScreenLocation({
      applicationId,
      wfProcessId,
      activityId: pickActivity.activityId,
      lineId,
      next: NEXT_NextPickingLine,
    })
  );
};
