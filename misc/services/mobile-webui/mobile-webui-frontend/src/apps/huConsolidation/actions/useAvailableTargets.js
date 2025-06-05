import { useQuery } from '../../../hooks/useQuery';
import * as api from '../api';
import { useDispatch } from 'react-redux';
import { updateWFProcess } from '../../../actions/WorkflowActions';

export const useAvailableTargets = ({ wfProcessId }) => {
  const dispatch = useDispatch();

  const { isPending: isTargetsLoading, data: targets } = useQuery({
    queryKey: [wfProcessId],
    queryFn: () => {
      return api.getAvailableTargets({ wfProcessId }).then((data) => data.targets);
    },
  });

  return {
    isTargetsLoading,
    targets,
    setTarget: ({ target }) => {
      return api.setTarget({ wfProcessId, target }).then((wfProcess) => dispatch(updateWFProcess({ wfProcess })));
    },
  };
};
