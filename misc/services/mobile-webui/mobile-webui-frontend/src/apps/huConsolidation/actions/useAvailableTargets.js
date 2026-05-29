import { useQuery } from '../../../hooks/useQuery';
import * as api from '../api';
import { useSetCurrentTarget } from './useSetCurrentTarget';

export const useAvailableTargets = ({ wfProcessId }) => {
  const { isPending: isTargetsLoading, data: targets } = useQuery({
    queryKey: [wfProcessId],
    queryFn: () => {
      return api.getAvailableTargets({ wfProcessId }).then((data) => data.targets);
    },
  });

  const setTarget = useSetCurrentTarget({ wfProcessId });

  return {
    isTargetsLoading,
    targets,
    setTarget,
  };
};
