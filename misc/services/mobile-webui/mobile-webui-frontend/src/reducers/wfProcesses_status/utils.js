import { isDraft, original } from 'immer';
import * as CompleteStatus from '../../constants/CompleteStatus';
import {
  normalizeComponentProps,
  computeActivityDataStoredInitialValue,
  mergeActivityDataStored,
  computeActivityStatus,
} from './activityStateHandlers';

/**
 * Updates isUserEditable flag for all activities.
 */
export const updateUserEditable = ({ draftWFProcess }) => {
  const activityIds = Object.keys(
    isDraft(draftWFProcess.activities) ? original(draftWFProcess.activities) : draftWFProcess.activities
  );

  let previousActivity = null;
  activityIds.forEach((activityId) => {
    const currentActivity = draftWFProcess.activities[activityId];
    const currentActivityCompleteStatus = currentActivity.dataStored.completeStatus || CompleteStatus.NOT_STARTED;

    let isUserEditable;

    //
    // First activity is always editable
    if (previousActivity == null) {
      isUserEditable = true;
      console.log(
        `[ ${activityId} ${currentActivityCompleteStatus} ]: => isUserEditable=${isUserEditable} (first currentActivity)`
      );
    } else {
      const previousActivityCompleteStatus = previousActivity.dataStored.completeStatus || CompleteStatus.NOT_STARTED;

      //
      // Current activity is editable only if previous activity was completed
      isUserEditable = previousActivityCompleteStatus === CompleteStatus.COMPLETED;
      console.log(
        `[ ${activityId} ${currentActivityCompleteStatus} ]: => isUserEditable=${isUserEditable} (checked if prev currentActivity was completed)`
      );

      //
      // If current currentActivity was started
      // => previous currentActivity is no longer editable
      if (currentActivityCompleteStatus !== CompleteStatus.NOT_STARTED) {
        previousActivity.dataStored.isUserEditable = false;
        console.log(
          `[ ${activityId} ${currentActivityCompleteStatus} ]: => Update [ ${
            previousActivity.activityId
          } ${previousActivityCompleteStatus} ] => isUserEditable=${
            draftWFProcess.activities[previousActivity.activityId].dataStored.isUserEditable
          } because current activity is started/completed`
        );
      }
    }

    currentActivity.dataStored.isUserEditable = isUserEditable;

    previousActivity = currentActivity;
  });
};

export const mergeWFProcessToState = ({ draftWFProcess, fromWFProcess }) => {
  draftWFProcess.headerProperties = fromWFProcess.headerProperties;

  if (!draftWFProcess.activities) {
    draftWFProcess.activities = {};
  }

  mergeActivitiesToState({
    draftActivities: draftWFProcess.activities,
    fromActivities: fromWFProcess.activities,
  });

  updateUserEditable({ draftWFProcess });
};

const mergeActivitiesToState = ({ draftActivities, fromActivities }) => {
  fromActivities.forEach((fromActivity) => {
    if (!draftActivities[fromActivity.activityId]) {
      draftActivities[fromActivity.activityId] = { ...fromActivity };
    }

    const draftActivity = draftActivities[fromActivity.activityId];

    mergeActivityToState({ draftActivity, fromActivity });
  });
};

const mergeActivityToState = ({ draftActivity, fromActivity }) => {
  draftActivity.caption = fromActivity.caption;
  draftActivity.componentType = fromActivity.componentType;

  const componentPropsNormalized = normalizeComponentProps({
    componentType: fromActivity.componentType,
    componentProps: fromActivity.componentProps,
  });
  draftActivity.componentProps = componentPropsNormalized;

  if (!draftActivity.dataStored) {
    draftActivity.dataStored = {
      completeStatus: CompleteStatus.NOT_STARTED,
      isUserEditable: false,
      ...computeActivityDataStoredInitialValue({
        componentType: fromActivity.componentType,
        componentProps: componentPropsNormalized,
      }),
    };
  } else {
    draftActivity.dataStored = mergeActivityDataStored({
      componentType: draftActivity.componentType,
      draftActivity,
      fromActivity,
    });
  }

  const completeStatus = computeActivityStatus({ draftActivity });
  if (completeStatus != null) {
    draftActivity.dataStored.completeStatus = completeStatus;
  }
};
