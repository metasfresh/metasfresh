import React from 'react';
import Spinner from '../../../components/Spinner';
import SelectHUIntermediateList from '../../../apps/huManager/containers/SelectHUIntermediateList';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { pickingJobOrLineLocation } from '../../../routes/picking';
import { useClosedHUs } from './useClosedHUs';

export const ReopenLUScreen = () => {
  const { history, wfProcessId, lineId } = useScreenDefinition({
    screenId: 'ReopenLUScreen',
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
