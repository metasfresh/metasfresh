export const getReadAttributesFromActivity = ({ activity }) => {
  return activity.dataStored.readAttributes ?? [];
};

// attributes shown but not editable (set by the backend on the receipt), e.g. a lot number that is not editable
export const getReadOnlyAttributesFromActivity = ({ activity }) => {
  return activity.dataStored.readOnlyAttributes ?? [];
};

export const isNoReadAttributes = ({ activity }) => {
  return getReadAttributesFromActivity({ activity }).length === 0;
};
