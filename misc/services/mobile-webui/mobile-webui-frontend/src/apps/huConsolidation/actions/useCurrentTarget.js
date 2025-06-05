import { useWFActivity } from '../../../reducers/wfProcesses';
import { getCurrentTarget } from '../reducers';
import * as api from '../api';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useDispatch } from 'react-redux';

export const useCurrentTarget = ({ wfProcessId, activityId }) => {
  const dispatch = useDispatch();
  const activity = useWFActivity({ wfProcessId, activityId });

  return {
    currentTarget: getCurrentTarget({ activity }),
    closeTarget: () => {
      return api.closeTarget({ wfProcessId }).then((wfProcess) => dispatch(updateWFProcess({ wfProcess })));
    },
  };
};
