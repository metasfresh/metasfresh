import { current, isDraft } from 'immer';

export const extractActivitiesStatus = ({ wfProcess }) => {
  const activitiesStatus = Object.values(wfProcess.activities).reduce((acc, activity) => {
    acc[activity.activityId] = {
      activityId: activity.activityId,
      isComplete: activity.dataStored.isComplete,
    };
    return acc;
  }, {});

  console.log('Extracted %o from %o', activitiesStatus, wfProcess);

  return activitiesStatus;
};

export const updateActivitiesStatus = ({ draftWFProcess, activitiesStatus }) => {
  console.log('Updating WF activities status from %o', activitiesStatus);
  console.log('draftWFProcess=%o', draftWFProcess);

  let previousActivityStatus = null;
  Object.values(activitiesStatus).forEach((activityStatus) => {
    console.log('activityStatus: %o', activityStatus);
    console.log('previousActivityStatus: %o', previousActivityStatus);

    let isActivityEnabled;
    if (previousActivityStatus == null) {
      // First activity: always enabled
      isActivityEnabled = true;
      console.log('=> isActivityEnabled: %o (first activity)', isActivityEnabled);
    } else {
      isActivityEnabled = previousActivityStatus.isComplete;
      console.log('isActivityEnabled: %o (checked if prev activity was completed)', isActivityEnabled);
    }

    draftWFProcess.activities[activityStatus.activityId].dataStored.isActivityEnabled = isActivityEnabled;

    previousActivityStatus = activityStatus;
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
  switch (componentType) {
    case 'picking/pickProducts': {
      return {
        isActivityEnabled: false,
        isComplete: false,
        lines: componentProps.lines,
      };
    }
    default: {
      return {};
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
