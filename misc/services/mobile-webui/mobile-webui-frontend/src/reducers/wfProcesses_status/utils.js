import { current, isDraft, original } from 'immer';
import * as CompleteStatus from '../../constants/CompleteStatus';

/**
 * Updates isActivityEnabled flag for all activities.
 */
export const updateActivitiesStatus = ({ draftWFProcess }) => {
  console.log('draftWFProcess=%o', draftWFProcess);

  const activityIds = Object.keys(
    isDraft(draftWFProcess.activities) ? original(draftWFProcess.activities) : draftWFProcess.activities
  );

  let previousActivity = null;
  activityIds.forEach((activityId) => {
    const currentActivity = draftWFProcess.activities[activityId];
    const currentActivityCompleteStatus = currentActivity.dataStored.completeStatus || CompleteStatus.NOT_STARTED;

    let isActivityEnabled;

    //
    // First activity is always editable
    if (previousActivity == null) {
      isActivityEnabled = true;
      console.log(
        `[ ${activityId} ${currentActivityCompleteStatus} ]: => isActivityEnabled=${isActivityEnabled} (first currentActivity)`
      );
    } else {
      const previousActivityCompleteStatus = previousActivity.dataStored.completeStatus || CompleteStatus.NOT_STARTED;

      //
      // Current activity is editable only if previous activity was completed
      isActivityEnabled = previousActivityCompleteStatus === CompleteStatus.COMPLETED;
      console.log(
        `[ ${activityId} ${currentActivityCompleteStatus} ]: => isActivityEnabled=${isActivityEnabled} (checked if prev currentActivity was completed)`
      );

      //
      // If current currentActivity was started
      // => previous currentActivity is no longer editable
      if (currentActivityCompleteStatus !== CompleteStatus.NOT_STARTED) {
        previousActivity.dataStored.isActivityEnabled = false;
        console.log(
          `[ ${activityId} ${currentActivityCompleteStatus} ]: => Update [ ${
            previousActivity.activityId
          } ${previousActivityCompleteStatus} ] => isActivityEnabled=${
            draftWFProcess.activities[previousActivity.activityId].dataStored.isActivityEnabled
          } because current activity is started/completed`
        );
      }
    }

    currentActivity.dataStored.isActivityEnabled = isActivityEnabled;

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

  updateActivitiesStatus({ draftWFProcess });

  console.log('AFTER MERGE: %o', isDraft(draftWFProcess) ? current(draftWFProcess) : draftWFProcess);
  console.log('fromWFProcess=%o', fromWFProcess);
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
    draftActivity.dataStored = computeActivityDataStoredInitialValue({
      componentType: fromActivity.componentType,
      componentProps: componentPropsNormalized,
    });
  }
};

const computeActivityDataStoredInitialValue = ({ componentType, componentProps }) => {
  const template = {
    completeStatus: CompleteStatus.NOT_STARTED,
    isActivityEnabled: false,
  };

  switch (componentType) {
    case 'picking/pickProducts': {
      return {
        ...template,
        lines: componentProps.lines,
      };
    }
    default: {
      return template;
    }
  }
};

const normalizeComponentProps = ({ componentType, componentProps }) => {
  switch (componentType) {
    case 'picking/pickProducts': {
      return {
        ...componentProps,
        lines: normalizePickingLines(componentProps.lines),
      };
    }
    default: {
      return componentProps;
    }
  }
};

const normalizePickingLines = (lines) => {
  return lines.map((line) => {
    return {
      ...line,
      steps: line.steps.reduce((accum, step) => {
        accum[step.pickingStepId] = step;
        return accum;
      }, {}),
    };
  });
};
