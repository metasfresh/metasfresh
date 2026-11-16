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
const NOT_REOPENABLE_HU_STATUSES = [HU_STATUS_SHIPPED, HU_STATUS_DESTROYED];
const isNotReopenable = (hu) => NOT_REOPENABLE_HU_STATUSES.includes(hu.huStatus);

const HU_STATUS_CAPTION_KEYS = {
  [HU_STATUS_SHIPPED]: 'activities.picking.luStatusShipped',
  [HU_STATUS_DESTROYED]: 'activities.picking.luStatusDestroyed',
};
const getHUStatusCaption = (hu) => {
  const captionKey = HU_STATUS_CAPTION_KEYS[hu.huStatus];
  return captionKey ? trl(captionKey) : undefined;
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
        getHUDisabled={isNotReopenable}
        getHUStatusCaption={getHUStatusCaption}
        onHuSelected={(lu) => reopenLU(lu).then(() => history.goBack())}
      />
    </>
  );
};
