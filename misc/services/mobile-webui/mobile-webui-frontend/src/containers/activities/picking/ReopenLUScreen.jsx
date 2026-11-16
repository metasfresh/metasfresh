import React from 'react';
import Spinner from '../../../components/Spinner';
import SelectHUIntermediateList from '../../../apps/huManager/containers/SelectHUIntermediateList';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { pickingJobOrLineLocation } from '../../../routes/picking';
import { useClosedHUs } from './useClosedHUs';

const HU_STATUS_SHIPPED = 'E';
const HU_STATUS_DESTROYED = 'D';

// shipped / destroyed LUs are no physical HUs any more: listed for information, but cannot be re-opened
const NOT_REOPENABLE_HU_STATUSES = [HU_STATUS_SHIPPED, HU_STATUS_DESTROYED];
const isNotReopenable = (hu) => NOT_REOPENABLE_HU_STATUSES.includes(hu.huStatus);

// the plain HU status caption of the server (AD_Reference M_HU.HUStatus), shown for the LUs that cannot be re-opened
const getHUStatusCaption = (hu) => (isNotReopenable(hu) ? hu.huStatusCaption : undefined);

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
        getHUDisabled={isNotReopenable}
        getHUStatusCaption={getHUStatusCaption}
        onHuSelected={(lu) => reopenLU(lu).then(() => history.goBack())}
      />
    </>
  );
};
