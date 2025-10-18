export const getInventoryLineById = ({ activity, lineId }) => {
  const lines = getInventoryLinesArray({ activity });
  return lines.find((line) => String(line.id) === String(lineId));
};

export const getInventoryLinesArray = ({ activity }) => {
  const job = getInventoryJob({ activity });
  return job?.lines ?? [];
};

export const getInventoryJob = ({ activity }) => {
  return activity?.dataStored?.job ?? {};
};
