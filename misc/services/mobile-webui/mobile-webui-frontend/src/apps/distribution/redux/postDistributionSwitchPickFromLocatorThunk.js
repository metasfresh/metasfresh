import { switchDistributionPickFromLocatorToNext } from '../../../api/distribution';
import { updateWFProcess } from '../../../actions/WorkflowActions';

export const postDistributionSwitchPickFromLocatorThunk =
  ({ wfProcessId }) =>
  async (dispatch) => {
    const wfProcess = await switchDistributionPickFromLocatorToNext({ wfProcessId });
    dispatch(updateWFProcess({ wfProcess }));
  };
