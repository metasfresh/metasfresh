import { useQuery } from '../../../hooks/useQuery';
import { getQtyAvailable } from '../../../api/picking';

export const usePickingJobQtyAvailable = ({ wfProcessId, enabled }) => {
  const { isPending, data } = useQuery({
    enabled,
    queryKey: [wfProcessId],
    queryFn: () => getQtyAvailable({ wfProcessId }),
  });

  return enabled && !isPending ? data : null;
};
