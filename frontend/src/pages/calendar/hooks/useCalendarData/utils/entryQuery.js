import { isSameMoment } from '../../../utils/calendarUtils';

export const newEntryQuery = ({
  calendarIds = null,
  simulationId = null,
  onlyResourceIds = null,
  startDate = null,
  endDate = null,
}) => {
  return {
    calendarIds:
      calendarIds != null && calendarIds.length > 0 ? calendarIds : null,
    simulationId,
    onlyResourceIds:
      onlyResourceIds != null && onlyResourceIds.length > 0
        ? onlyResourceIds
        : null,
    startDate,
    endDate,
  };
};

export const isEqualEntryQueries = (query1, query2) => {
  if (query1 === query2) {
    return true;
  }
  if (!query1 || !query2) {
    return false;
  }

  return (
    isArraysEqual(query1.calendarIds, query2.calendarIds) &&
    query1.simulationId === query2.simulationId &&
    isArraysEqual(query1.onlyResourceIds, query2.onlyResourceIds) &&
    isSameMoment(query1.startDate, query2.startDate) &&
    isSameMoment(query1.endDate, query2.endDate)
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
