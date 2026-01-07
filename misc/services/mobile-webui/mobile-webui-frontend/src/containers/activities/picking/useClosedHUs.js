import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { getClosedLUs, setLUPickingTarget, hasClosedLUs } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { useQuery } from '../../../hooks/useQuery';
import { toQRCodeString } from '../../../utils/qrCode/hu';

export const useClosedHUs = ({ wfProcessId, lineId }) => {
  const dispatch = useDispatch();
  const [isMutating, setIsMutating] = useState(false);

  const { isPending: isLoading, data: closedLUs } = useQuery({
    queryKey: [wfProcessId, lineId],
    queryFn: () => getClosedLUs({ wfProcessId, lineId }).then(({ hus }) => hus),
  });

  const reopenLU = (hu) => {
    if (isLoading) return;

    setIsMutating(true);
    return setLUPickingTarget({
      wfProcessId,
      lineId,
      target: {
        id: hu.id,
        caption: hu.displayName,
        luId: hu.id,
        luQRCode: toQRCodeString(hu.qrCode),
      },
    })
      .then((wfProcess) => dispatch(updateWFProcess({ wfProcess })))
      .finally(() => setIsMutating(false));
  };

  return {
    isWorking: isLoading || isMutating,
    hasClosedLUs: closedLUs?.length > 0,
    closedLUs,
    reopenLU,
  };
};

export const useHasClosedHUs = ({ wfProcessId, lineId }) => {
  const { isPending: isLoading, data } = useQuery({
    queryKey: [wfProcessId, lineId],
    queryFn: () => hasClosedLUs({ wfProcessId, lineId }),
  });

  return {
    isLoading,
    hasClosedLUs: data === 'true' || data === true,
  };
};
