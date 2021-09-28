import { SWITCHOFF_LINES_VISIBILITY } from '../constants/ActionTypes';

/**
 * @method switchoffLinesVisibility
 * @summary sets the lines visibility to `false` and by doing this the steps will be visible
 */
export function switchoffLinesVisibility({ wfProcessId, activityId }) {
  return {
    type: SWITCHOFF_LINES_VISIBILITY,
    payload: { wfProcessId, activityId },
  };
}
