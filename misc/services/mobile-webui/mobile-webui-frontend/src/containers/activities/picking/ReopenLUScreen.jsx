import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import Spinner from '../../../components/Spinner';
import { getClosedLUs, setLUPickingTarget } from '../../../api/picking';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import SelectHUIntermediateList from '../../../apps/huManager/containers/SelectHUIntermediateList';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { useQuery } from '../../../hooks/useQuery';
import { pickingJobOrLineLocation } from '../../../routes/picking';
import { toQRCodeString } from '../../../utils/qrCode/hu';

export const ReopenLUScreen = () => {
  const { history, wfProcessId, lineId } = useScreenDefinition({
    captionKey: 'activities.picking.reopenLU',
    back: pickingJobOrLineLocation,
  });
  const { isWorking, closedLUs, reopenLU } = useClosedHUs({ wfProcessId, lineId });

  return (
    <>
      {isWorking && <Spinner />}
      <SelectHUIntermediateList
        huList={closedLUs}
        disabled={isWorking}
        onHuSelected={(lu) => reopenLU(lu).then(() => history.goBack())}
      />
    </>
  );
};

//
//
//

const useClosedHUs = ({ wfProcessId, lineId }) => {
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

  return { isWorking: isLoading || isMutating, closedLUs, reopenLU };
};
