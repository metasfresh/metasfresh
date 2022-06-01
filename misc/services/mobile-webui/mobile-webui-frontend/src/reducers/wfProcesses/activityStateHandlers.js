const registeredHandlers = {};

export const registerHandler = ({
  componentType,
  normalizeComponentProps,
  computeActivityDataStoredInitialValue,
  computeActivityStatus,
  mergeActivityDataStored,
}) => {
  registeredHandlers[componentType] = {
    normalizeComponentProps,
    computeActivityDataStoredInitialValue,
    computeActivityStatus,
    mergeActivityDataStored,
  };

  console.log(`Registered activity state handlers for ${componentType}`);
  //console.log('=>registeredHandlers', registeredHandlers);
};

export const normalizeComponentProps = ({ componentType, componentProps }) => {
  const componentHandlers = registeredHandlers[componentType];
  if (componentHandlers && componentHandlers.normalizeComponentProps) {
    return componentHandlers.normalizeComponentProps({ componentProps });
  } else {
    return componentProps;
  }
};

export const computeActivityDataStoredInitialValue = ({ componentType, componentProps }) => {
  const componentHandlers = registeredHandlers[componentType];
  if (componentHandlers && componentHandlers.computeActivityDataStoredInitialValue) {
    return componentHandlers.computeActivityDataStoredInitialValue({ componentProps });
  } else {
    return {};
  }
};

export const mergeActivityDataStored = ({ componentType, draftActivityDataStored, fromActivity }) => {
  const componentHandlers = registeredHandlers[componentType];
  if (componentHandlers && componentHandlers.mergeActivityDataStored) {
    componentHandlers.mergeActivityDataStored({ componentType, draftActivityDataStored, fromActivity });
  }
};

export const computeActivityStatus = ({ draftActivity }) => {
  const componentHandlers = registeredHandlers[draftActivity.componentType];
  if (componentHandlers && componentHandlers.computeActivityStatus) {
    return componentHandlers.computeActivityStatus({ draftActivity });
  } else {
    return null;
  }
};
