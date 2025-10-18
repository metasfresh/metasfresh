import { useQuery } from '../../../hooks/useQuery';
import { getLineHUs } from '../api';

export const useLineHUs = ({ wfProcessId, lineId }) => {
  const { isPending, data } = useQuery({
    queryKey: [wfProcessId, lineId],
    queryFn: () => getLineHUs({ wfProcessId, lineId }),
  });

  return { isLineHUsLoading: isPending, lineHUs: data?.lineHUs ?? [] };
};
