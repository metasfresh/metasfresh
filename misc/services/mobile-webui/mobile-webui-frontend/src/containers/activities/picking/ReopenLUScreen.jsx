import React from 'react';
import Spinner from '../../../components/Spinner';
import SelectHUIntermediateList from '../../../apps/huManager/containers/SelectHUIntermediateList';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { pickingJobOrLineLocation } from '../../../routes/picking';
import { trl } from '../../../utils/translations';
import { useClosedHUs } from './useClosedHUs';

const HU_STATUS_SHIPPED = 'E';
const HU_STATUS_DESTROYED = 'D';

// shipped / destroyed LUs are no physical HUs any more: listed for information, but cannot be re-opened
const getHUStatusCaption = (hu) => {
  switch (hu.huStatus) {
    case HU_STATUS_SHIPPED:
      return trl('activities.picking.luStatusShipped');
    case HU_STATUS_DESTROYED:
      return trl('activities.picking.luStatusDestroyed');
    default:
      return undefined;
  }
};

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
        getHUDisabled={(hu) => !!getHUStatusCaption(hu)}
        getHUStatusCaption={getHUStatusCaption}
        onHuSelected={(lu) => reopenLU(lu).then(() => history.goBack())}
      />
    </>
  );
};
