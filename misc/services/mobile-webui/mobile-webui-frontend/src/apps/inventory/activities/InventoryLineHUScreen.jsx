import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { inventoryLineScreenLocation } from '../routes';
import { useQuery } from '../../../hooks/useQuery';
import { getLineHU } from '../api';
import Spinner from '../../../components/Spinner';
import { useHeaderUpdate } from '../utils/useHeaderUpdate';

const InventoryLineHUScreen = () => {
  const { url, wfProcessId, lineId, lineHUId } = useScreenDefinition({
    screenId: 'InventoryLineHUScreen',
    captionKey: 'inventory.InventoryLineHUScreen.caption',
    back: inventoryLineScreenLocation,
  });

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

  console.log('InventoryLineHUScreen', { isLoading, lineHU });

  return <div className="section pt-2">{isLoading && <Spinner />}</div>;
};

export default InventoryLineHUScreen;
