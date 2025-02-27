import { getNextEligibleLineToPick } from '../../utils/picking';
import { getActivityById, getFirstActivityByComponentType } from '../../reducers/wfProcesses';
import { pickingLineScanScreenLocation } from '../../routes/picking';
import { COMPONENTTYPE_PickProducts } from '../../containers/activities/picking/PickProductsActivity';
import { NEXT_NextPickingLine } from '../../containers/activities/picking/PickLineScanScreen';

const APPLICATION_ID_Picking = 'picking';
const ACTIVITY_ID_ScanPickingSlot = 'scanPickingSlot'; // keep in sync with PickingMobileApplication.ACTIVITY_ID_ScanPickingSlot

export const applicationDescriptor = {
  applicationId: APPLICATION_ID_Picking,

  onWFActivityCompleted: ({ applicationId, wfProcessId, activityId, history, getState }) => {
    // console.log('onWFActivityCompleted', { applicationId, wfProcessId, activityId, dispatch });
    if (applicationId !== APPLICATION_ID_Picking) {
      return;
    }

    const state = getState();
    const activity = getActivityById(state, wfProcessId, activityId);
    // console.log('onWFActivityCompleted', { activity });

    if (activity.activityId === ACTIVITY_ID_ScanPickingSlot) {
      // Scan picking slot activity completed => consider scanning HU for the first pick line
      openFirstEligiblePickingLineScanner({ state, applicationId, wfProcessId, history });
    } else {
      history.goBack();
    }
  },
};

const openFirstEligiblePickingLineScanner = ({ state, applicationId, wfProcessId, history }) => {
  const pickActivity = getFirstActivityByComponentType({
    state,
    wfProcessId,
    componentType: COMPONENTTYPE_PickProducts,
  });

  if (pickActivity?.dataStored?.isPickWithNewLU) {
    history.goBack();
    return;
  }

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
