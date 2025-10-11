import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryJobLocation } from '../routes';

const InventoryLineScreen = () => {
  // const { history, applicationId, wfProcessId } =
  useScreenDefinition({
    screenId: 'InventoryLineScreen',
    back: inventoryJobLocation,
  });

  return <div>InventoryLineScreen</div>;
};

export default InventoryLineScreen;
