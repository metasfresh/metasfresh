import * as CompleteStatus from '../../../constants/CompleteStatus';

export const CountStatus = Object.freeze({
  NOT_COUNTED: 'NOT_COUNTED',
  COUNTING_IN_PROGRESS: 'COUNTING_IN_PROGRESS',
  COUNTED: 'COUNTED',

  computeCompleteStatus({ countStatus }) {
    switch (countStatus) {
      case CountStatus.NOT_COUNTED:
        return CompleteStatus.NOT_STARTED;
      case CountStatus.COUNTING_IN_PROGRESS:
        return CompleteStatus.IN_PROGRESS;
      case CountStatus.COUNTED:
        return CompleteStatus.COMPLETED;
      default:
        return null;
    }
  },
});
