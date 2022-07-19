export const newEntryQuery = ({
  calendarIds = [],
  simulationId = null,
  startDate = null,
  endDate = null,
}) => {
  return { calendarIds, simulationId, startDate, endDate };
};

export const isEqualEntryQueries = (query1, query2) => {
  if (query1 === query2) {
    return true;
  }
  if (!query1 || !query2) {
    return false;
  }

  // { startDate, endDate, simulationId }
  return (
    isArraysEqual(query1.calendarIds, query2.calendarIds) &&
    query1.simulationId === query2.simulationId &&
    query1.startDate === query2.startDate &&
    query1.endDate === query2.endDate
  );
};

const isArraysEqual = (array1, array2) => {
  if (array1 === array2) {
    return true;
  }
  if (array1 == null || array2 == null) {
    return false;
  }

  if (array1.length === array2.length) {
    return array1.every((e, index) => e === array2[index]);
  } else {
    return false;
  }
};
