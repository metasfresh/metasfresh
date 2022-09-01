export const NOT_STARTED = 'NOT_STARTED';
export const IN_PROGRESS = 'IN_PROGRESS';
export const COMPLETED = 'COMPLETED';

export const reduceFromCompleteStatuesUniqueArray = (statusesArray) => {
  // IMPORTANT: we assume the statuses are unique
  if (statusesArray.length === 0) {
    // corner case, shall not happen: there are no steps in current line => consider it completed
    return COMPLETED;
  } else if (statusesArray.length === 1) {
    return statusesArray[0];
  } else {
    return IN_PROGRESS;
  }
};
