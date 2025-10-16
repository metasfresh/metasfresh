import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation, inventoryScanScreenLocation } from '../routes';
import { useQuery } from '../../../hooks/useQuery';
import { getLineHUs } from '../api';
import Spinner from '../../../components/Spinner';
import InventoryLineHUButton from './InventoryLineHUButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

const InventoryLineScreen = () => {
  const { applicationId, wfProcessId, activityId, lineId, history } = useScreenDefinition({
    screenId: 'InventoryLineScreen',
    back: inventoryJobLocation,
  });

  const { isLineHUsLoading, lineHUs } = useLineHUs({ wfProcessId, lineId });

  const onScanButtonClick = () => {
    history.push(inventoryScanScreenLocation({ applicationId, wfProcessId, activityId, lineId }));
  };
  const onLineHUButtonClicked = ({ lineHU }) => {
    console.log('onLineHUButtonClicked', { lineHU });
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
              onClick={() => onLineHUButtonClicked({ lineHU })}
            />
          ))}
      </div>
    </div>
  );
};

export default InventoryLineScreen;

//
//
//
//
//

const useLineHUs = ({ wfProcessId, lineId }) => {
  const { isPending, data } = useQuery({
    queryKey: [wfProcessId, lineId],
    queryFn: () => getLineHUs({ wfProcessId, lineId }),
  });

  console.log('useLineHUs', { wfProcessId, lineId, isPending, data });

  return { isLineHUsLoading: isPending, lineHUs: data?.lineHUs ?? [] };
};
