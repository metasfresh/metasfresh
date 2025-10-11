export const getInventoryLinesArray = ({ activity }) => {
  const job = getInventoryJob({ activity });
  return job?.lines ?? [];
};

export const getInventoryJob = ({ activity }) => {
  return activity?.dataStored?.job ?? {};
};
