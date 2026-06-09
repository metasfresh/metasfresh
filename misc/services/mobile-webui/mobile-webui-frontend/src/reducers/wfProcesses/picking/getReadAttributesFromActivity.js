export const getReadAttributesFromActivity = ({ activity }) => {
  return activity.dataStored.readAttributes ?? [];
};

export const isNoReadAttributes = ({ activity }) => {
  return getReadAttributesFromActivity({ activity }).length === 0;
};
