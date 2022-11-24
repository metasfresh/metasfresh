const registeredHandlers = {};

export const registerHandler = ({ componentType, normalizeComponentProps, computeActivityDataStoredInitialValue }) => {
  registeredHandlers[componentType] = {
    normalizeComponentProps,
    computeActivityDataStoredInitialValue,
  };

  console.log(`Registered handlers for ${componentType}`);
  console.log('=>registeredHandlers', registeredHandlers);
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
