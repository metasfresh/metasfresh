import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huConsolidationJobLocation } from '../routes';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { usePickingSlot } from '../actions/usePickingSlot';
import { toQRCodeDisplayable } from '../../../utils/qrCode/hu';
import { useMobileLocation } from '../../../hooks/useMobileLocation';
import { toastErrorFromObj } from '../../../utils/toast';
import Spinner from '../../../components/Spinner';
import { formatQtyToHumanReadableStr } from '../../../utils/qtys';
import PropTypes from 'prop-types';

export const PickingSlotScreen = () => {
  const { wfProcessId, activityId, id: pickingSlotId } = useMobileLocation();
  const { isLoading, pickingSlotContent, pickingSlotQRCode, isProcessing, consolidate } = usePickingSlot({
    wfProcessId,
    activityId,
    pickingSlotId,
  });
  const { history } = useScreenDefinition({
    screenId: 'PickingSlotScreen',
    captionKey: 'huConsolidation.pickingSlot',
    back: huConsolidationJobLocation,
    values: [
      {
        captionKey: 'huConsolidation.pickingSlot',
        value: toQRCodeDisplayable(pickingSlotQRCode),
      },
    ],
  });

  const items = pickingSlotContent?.items ?? [];
  const isWorking = isLoading || isProcessing;

  const onConsolidateHUClicked = ({ huId }) => {
    return consolidate({ huId });
  };
  const onConsolidateAllClicked = () => {
    return consolidate()
      .then(() => history.goBack())
      .catch((error) => toastErrorFromObj(error));
  };

  // console.log('PickingSlotScreen', { pickingSlotContent });

  return (
    <div className="section pt-2">
      {isLoading && <Spinner />}
      <PickingSlotContent
        items={items}
        disabled={isWorking}
        onConsolidateHU={onConsolidateHUClicked}
        onConsolidateAll={onConsolidateAllClicked}
      />
    </div>
  );
};

//
//
//--------------------------------------------------------------------------
//
//

const PickingSlotContent = ({ items, disabled, onConsolidateHU, onConsolidateAll }) => {
  if (!items || items.length <= 0) return null;

  return (
    <>
      <ButtonWithIndicator
        captionKey="huConsolidation.PickingSlotScreen.consolidateAll"
        disabled={disabled}
        onClick={onConsolidateAll}
        testId="consolidateAll-button"
      />
      {items.map((item) => (
        <PickingSlotItem
          key={item.huId}
          huId={item.huId}
          displayName={item.displayName}
          packingInfo={item.packingInfo}
          storages={item.storages}
          disabled={disabled}
          onClick={onConsolidateHU}
        />
      ))}
    </>
  );
};
PickingSlotContent.propTypes = {
  items: PropTypes.array,
  disabled: PropTypes.bool,
  onConsolidateHU: PropTypes.func.isRequired,
  onConsolidateAll: PropTypes.func.isRequired,
};

//
//
//--------------------------------------------------------------------------
//
//

const PickingSlotItem = ({ displayName, huId, packingInfo, storages, disabled, onClick }) => {
  const caption = `${displayName} - ${packingInfo}`;

  return (
    <ButtonWithIndicator
      caption={caption}
      disabled={disabled}
      onClick={() => onClick({ huId })}
      testId={`consolidate-${huId}-button`}
    >
      {storages?.map(({ productName, qty, uom }) => (
        <div key={productName} className="row is-full is-size-7">
          {computeStorageInfo({ productName, qty, uom })}
        </div>
      ))}
    </ButtonWithIndicator>
  );
};
PickingSlotItem.propTypes = {
  displayName: PropTypes.string.isRequired,
  huId: PropTypes.number.isRequired,
  packingInfo: PropTypes.string.isRequired,
  storages: PropTypes.array,
  disabled: PropTypes.bool,
  onClick: PropTypes.func.isRequired,
};

//
//
//--------------------------------------------------------------------------
//
//

const computeStorageInfo = ({ productName, qty, uom }) => {
  const qtyStr = formatQtyToHumanReadableStr({ qty, uom });
  return `${productName} x ${qtyStr}`;
};
