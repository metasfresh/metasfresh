import React, { useState } from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryLineScreenLocation } from '../routes';
import { useQuery } from '../../../hooks/useQuery';
import { getLineHU, reportInventoryCounting } from '../api';
import Spinner from '../../../components/Spinner';
import { useHeaderUpdate } from '../utils/useHeaderUpdate';
import InventoryCountComponent from './InventoryCountComponent';
import { CountStatus } from '../utils/CountStatus';
import { updateWFProcess } from '../../../actions/WorkflowActions';
import { toastErrorFromObj } from '../../../utils/toast';
import { useDispatch } from 'react-redux';

const InventoryLineHUScreen = () => {
  const { url, applicationId, wfProcessId, activityId, lineId, lineHUId, history } = useScreenDefinition({
    screenId: 'InventoryLineHUScreen',
    captionKey: 'inventory.InventoryLineHUScreen.caption',
    back: inventoryLineScreenLocation,
  });

  const dispatch = useDispatch();
  const [processing, setProcessing] = useState(false);
  const { isPending: isLoading, data: lineHU } = useQuery({
    queryKey: [],
    queryFn: () => getLineHU({ wfProcessId, lineId, lineHUId }),
  });

  useHeaderUpdate({
    url,
    locatorName: lineHU?.locatorName,
    huDisplayName: lineHU?.huDisplayName,
    productName: lineHU?.productName,
    uom: lineHU?.uom,
    qtyBooked: lineHU?.qtyBooked,
    qtyCount: lineHU?.qtyCount,
    attributes: lineHU?.attributes,
  });

  const onInventoryCountSubmit = ({ qtyCount, attributes, lineCountingDone }) => {
    setProcessing(true);
    reportInventoryCounting({
      wfProcessId,
      lineId,
      //scannedCode: resolvedHU?.scannedCode,
      huId: lineHU.huId,
      qtyCount,
      lineCountingDone,
      attributes,
    })
      .then((wfProcess) => {
        dispatch(updateWFProcess({ wfProcess }));
        history.goTo(inventoryLineScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
      })
      .catch((error) => toastErrorFromObj(error))
      .finally(() => setProcessing(false));
  };

  return (
    <>
      {isLoading && <Spinner />}
      {!isLoading && lineHU && (
        <InventoryCountComponent
          url={url}
          disabled={isLoading || processing}
          huDisplayName={lineHU.huDisplayName}
          productName={lineHU.productName}
          locatorName={lineHU.locatorName}
          uom={lineHU.uom}
          qtyBooked={lineHU.qtyBooked}
          counted={lineHU.countStatus === CountStatus.COUNTED}
          qtyCount={lineHU.qtyCount}
          attributes={lineHU.attributes}
          onInventoryCountSubmit={onInventoryCountSubmit}
        />
      )}
    </>
  );
};

export default InventoryLineHUScreen;
