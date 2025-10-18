import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation, inventoryLineHUScreenLocation, inventoryScanScreenLocation } from '../routes';
import Spinner from '../../../components/Spinner';
import InventoryLineHUButton from './InventoryLineHUButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { getInventoryLineById } from '../reducers/utils';
import { useHeaderUpdate } from '../utils/useHeaderUpdate';
import { useLineHUs } from '../reducers/useLineHUs';

const InventoryLineScreen = () => {
  const { url, applicationId, wfProcessId, activityId, lineId, history } = useScreenDefinition({
    screenId: 'InventoryLineScreen',
    captionKey: 'inventory.InventoryLineScreen.caption',
    back: inventoryJobLocation,
  });

  const activity = useWFActivity({ wfProcessId, activityId });
  const line = getInventoryLineById({ activity, lineId });
  const { isLineHUsLoading, lineHUs } = useLineHUs({ wfProcessId, lineId });

  useHeaderUpdate({
    url,
    productName: line?.productName,
    locatorName: line?.locatorName,
    uom: line?.uom,
    qtyBooked: line?.qtyBooked,
    qtyCount: line?.qtyCount,
  });

  const onScanButtonClick = () => {
    history.push(inventoryScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };
  const onLineHUButtonClicked = ({ lineHU }) => {
    history.push(
      inventoryLineHUScreenLocation({ applicationId, wfProcessId, activityId, lineId, lineHUId: lineHU.id })
    );
  };

  return (
    <div className="section pt-2">
      <ButtonWithIndicator testId="scanQRCode-button" captionKey="inventory.scanQRCode" onClick={onScanButtonClick} />

      <div className="buttons">
        {isLineHUsLoading && <Spinner />}
        {!isLineHUsLoading &&
          lineHUs?.map((lineHU) => (
            <InventoryLineHUButton
              key={lineHU.id}
              id={lineHU.id}
              caption={lineHU.caption}
              uom={lineHU.uom}
              qtyBooked={lineHU.qtyBooked}
              qtyCount={lineHU.qtyCount}
              countStatus={lineHU.countStatus}
              onClick={() => onLineHUButtonClicked({ lineHU })}
            />
          ))}
      </div>
    </div>
  );
};

export default InventoryLineScreen;
