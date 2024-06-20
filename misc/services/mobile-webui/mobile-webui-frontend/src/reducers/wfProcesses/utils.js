import { current, isDraft } from 'immer';
import * as CompleteStatus from '../../constants/CompleteStatus';
import { computeActivityDataStoredInitialValue, computeActivityStatus, mergeActivityDataStored, normalizeComponentProps, } from './activityStateHandlers';

/**
 * Updates isUserEditable flag for all activities.
 */
export const updateUserEditable = ({ draftWFProcess }) => {
  let previousActivity = null;

  const activityIdsInOrder = isDraft(draftWFProcess.activityIdsInOrder)
    ? current(draftWFProcess.activityIdsInOrder)
    : draftWFProcess.activityIdsInOrder;

  const isWFProcessing = activityIdsInOrder.some(
    (activityId) => !!draftWFProcess.activities[activityId]?.dataStored?.processing
  );

  activityIdsInOrder.forEach((activityId) => {
    const currentActivity = draftWFProcess.activities[activityId];

    if (isWFProcessing) {
      currentActivity.dataStored.isUserEditable = false;
    } else if (currentActivity.dataStored.isAlwaysAvailableToUser) {
      currentActivity.dataStored.isUserEditable = true;
      // NOTE: activities which are always available to user shall be always editable
      // They are out of the normal flow when talking about making a previous activity readonly because current activity is completed.
    } else {
      const { isUserEditable, makePreviousActivityReadOnly } = computeActivityIsUserEditable({
        currentActivity,
        previousActivity,
      });

      currentActivity.dataStored.isUserEditable = isUserEditable;
      if (makePreviousActivityReadOnly) {
        previousActivity.dataStored.isUserEditable = false;
      }

      previousActivity = currentActivity;
    }
  });
};

const computeActivityIsUserEditable = ({ currentActivity, previousActivity }) => {
  // IMPORTANT: we assume that currentActivity nor previousActivity have the isAlwaysAvailableToUser flag set.
  // Those activities shall be filtered out by the caller.

  const currentActivityCompleteStatus = currentActivity.dataStored.completeStatus || CompleteStatus.NOT_STARTED;

  let isUserEditable;
  let makePreviousActivityReadOnly = false;

  //
  // First activity is always editable
  if (previousActivity == null) {
    isUserEditable = true;
    // prettier-ignore
    console.log(`[ ${currentActivity.caption} ${currentActivityCompleteStatus} ]: => isUserEditable=${isUserEditable} (first currentActivity)`);
  }
  //
  // Second and next activity
  else {
    const previousActivityCompleteStatus = previousActivity.dataStored.completeStatus || CompleteStatus.NOT_STARTED;

    //
    // Current activity is editable if previous activity was completed
    // or if the activity was already started (we need to allow the user to complete it somehow).
    isUserEditable =
      previousActivityCompleteStatus === CompleteStatus.COMPLETED ||
      currentActivityCompleteStatus === CompleteStatus.IN_PROGRESS;
    // prettier-ignore
    console.log(`[ ${currentActivity.caption} ${currentActivityCompleteStatus} ]: => isUserEditable=${isUserEditable} (previous activity status: ${previousActivityCompleteStatus})`);

    //
    // If the previous activity was completed and the current one is in progress/completed
    // => previous currentActivity is no longer editable
    if (
      previousActivityCompleteStatus === CompleteStatus.COMPLETED &&
      currentActivityCompleteStatus !== CompleteStatus.NOT_STARTED
    ) {
      makePreviousActivityReadOnly = true;
      // prettier-ignore
      console.log(`[ ${currentActivity.caption} ${currentActivityCompleteStatus} ]: => Update [ ${previousActivity.caption} ${previousActivityCompleteStatus} ] => isUserEditable=${previousActivity.dataStored.isUserEditable} because current activity is started/completed`);
    }
  }

  return { isUserEditable, makePreviousActivityReadOnly };
};

export const mergeWFProcessToState = ({ draftWFProcess, fromWFProcess }) => {
  draftWFProcess.headerProperties = fromWFProcess.headerProperties;
  draftWFProcess.isAllowAbort = !!fromWFProcess.isAllowAbort;

  if (!draftWFProcess.activities) {
    draftWFProcess.activities = {};
  }

  mergeActivitiesToState({
    targetWFProcess: draftWFProcess,
    fromActivities: fromWFProcess.activities,
  });

  updateUserEditable({ draftWFProcess });

  return draftWFProcess;
};

// @VisibleForTesting
export const mergeActivitiesToState = ({ targetWFProcess, fromActivities }) => {
  const fromActivitiesById = fromActivities.reduce((acc, activity) => {
    acc[activity.activityId] = activity;
    return acc;
  }, {});

  //
  // Merge existing activities and collect those we have to remove from target
  const activityIdsToDelete = [];
  const targetActivitiesById = targetWFProcess.activities;
  const targetActivityIds = Object.keys(
    isDraft(targetActivitiesById) ? current(targetActivitiesById) : targetActivitiesById
  );
  targetActivityIds.forEach((targetActivityId) => {
    const fromActivity = fromActivitiesById[targetActivityId];
    if (!fromActivity) {
      activityIdsToDelete.push(targetActivityId);
    } else {
      delete fromActivitiesById[targetActivityId];
      const targetActivity = targetActivitiesById[targetActivityId];
      mergeActivityToState({ draftActivity: targetActivity, fromActivity });
    }
  });

  //
  // Remove activities which exist in target but not in source
  activityIdsToDelete.forEach((activityIdToDelete) => {
    delete targetActivitiesById[activityIdToDelete];
  });

  //
  // Add activities which exist in source but not yet in target
  Object.values(fromActivitiesById).forEach((activityToAdd) => {
    targetActivitiesById[activityToAdd.activityId] = {};
    mergeActivityToState({
      draftActivity: targetActivitiesById[activityToAdd.activityId],
      fromActivity: activityToAdd,
    });
  });

  //
  // Capture the order of the activities
  targetWFProcess.activityIdsInOrder = fromActivities.reduce((acc, activity) => {
    acc.push(activity.activityId);
    return acc;
  }, []);
};

const mergeActivityToState = ({ draftActivity, fromActivity }) => {
  draftActivity.activityId = fromActivity.activityId; // for new activities
  draftActivity.caption = fromActivity.caption;
  draftActivity.componentType = fromActivity.componentType;
  draftActivity.userInstructions = fromActivity.userInstructions;

  const componentPropsNormalized = normalizeComponentProps({
    componentType: fromActivity.componentType,
    componentProps: fromActivity.componentProps,
  });
  draftActivity.componentProps = componentPropsNormalized || {};

  if (!draftActivity.dataStored) {
    draftActivity.dataStored = {
      completeStatus: CompleteStatus.NOT_STARTED,
      isUserEditable: false,
      ...computeActivityDataStoredInitialValue({
        componentType: fromActivity.componentType,
        componentProps: componentPropsNormalized,
      }),
    };
  }

  mergeActivityDataStored({
    componentType: draftActivity.componentType,
    draftActivityDataStored: draftActivity.dataStored,
    fromActivity,
  });

  const completeStatus = computeActivityStatus({ draftActivity });
  if (completeStatus != null) {
    draftActivity.dataStored.completeStatus = completeStatus;
  }
};
