import React, { useEffect } from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation, inventoryScanScreenLocation } from '../routes';
import { useQuery } from '../../../hooks/useQuery';
import { getLineHUs } from '../api';
import Spinner from '../../../components/Spinner';
import InventoryLineHUButton from './InventoryLineHUButton';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useDispatch } from 'react-redux';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { trl } from '../../../utils/translations';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { getInventoryLineById } from '../reducers/utils';

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
              countStatus={lineHU.countStatus}
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

const useHeaderUpdate = ({ url, productName, locatorName, uom, qtyBooked, qtyCount }) => {
  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
        values: [
          {
            caption: trl('inventory.locator'),
            value: locatorName,
          },
          {
            caption: trl('inventory.product'),
            value: productName,
          },
          {
            caption: trl('inventory.qtyBooked'),
            value: formatQtyToHumanReadableStr({ qty: qtyBooked, uom }),
          },
          {
            caption: trl('inventory.qtyCount'),
            value: formatQtyToHumanReadableStr({ qty: qtyCount, uom }),
          },
        ],
      })
    );
  }, [url, productName, locatorName, uom, qtyBooked, qtyCount, dispatch]);
};

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

  return { isLineHUsLoading: isPending, lineHUs: data?.lineHUs ?? [] };
};
