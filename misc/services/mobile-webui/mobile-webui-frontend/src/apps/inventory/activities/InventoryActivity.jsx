import React from 'react';
import PropTypes from 'prop-types';
import { useWFActivity } from '../../../reducers/wfProcesses';
import { getInventoryLinesArray } from '../reducers/utils';
import InventoryLineButton from './InventoryLineButton';
import { inventoryLineScreenLocation, inventoryScanScreenLocation } from '../routes';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';

const InventoryActivity = ({ applicationId, wfProcessId, activityId }) => {
  const history = useMobileNavigation();
  const activity = useWFActivity({ wfProcessId, activityId });
  const lines = getInventoryLinesArray({ activity });
  // console.log('InventoryActivity', { applicationId, wfProcessId, activityId, activity, lines });

  const onScanButtonClick = () => {
    history.push(inventoryScanScreenLocation({ applicationId, wfProcessId, activityId }));
  };
  const onButtonClicked = ({ line }) => {
    history.goTo(inventoryLineScreenLocation({ applicationId, wfProcessId, activityId, lineId: line.id }));
  };

  return (
    <div className="mt-5">
      <ButtonWithIndicator testId="scanQRCode-button" captionKey="inventory.scanQRCode" onClick={onScanButtonClick} />

      {lines.map((line) => (
        <InventoryLineButton
          key={line.id}
          lineId={line.id}
          caption={line.caption}
          uom={line.uom}
          qtyBooked={line.qtyBooked}
          qtyCount={line.qtyCount}
          productId={line.productId}
          locatorId={line.locatorId}
          onClick={() => onButtonClicked({ line })}
        />
      ))}
    </div>
  );
};

InventoryActivity.propTypes = {
  applicationId: PropTypes.string.isRequired,
  wfProcessId: PropTypes.string.isRequired,
  activityId: PropTypes.string.isRequired,
};

export default InventoryActivity;
