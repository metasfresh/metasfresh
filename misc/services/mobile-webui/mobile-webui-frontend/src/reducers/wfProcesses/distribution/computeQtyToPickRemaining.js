import { getStepsArrayFromLine } from '../index';

export const computeQtyToPickRemaining = ({ line }) => {
  const stepsArray = getStepsArrayFromLine(line);
  const qtyPicked = stepsArray.reduce((sum, step) => sum + (step.qtyPicked || 0), 0);
  return Math.max(line.qtyToMove - qtyPicked, 0);
};
