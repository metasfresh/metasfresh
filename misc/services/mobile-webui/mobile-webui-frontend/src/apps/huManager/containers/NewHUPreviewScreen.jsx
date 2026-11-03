import React, { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import * as api from '../api';
import { clearLoadedData } from '../actions';
import { getNewHUPreviewFromGlobalState } from '../reducers';
import { huManagerLocation } from '../routes';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { resolveLocatorQRCode } from '../../../api/warehouse';
import ReadQtyDialog from '../../../components/dialogs/ReadQtyDialog';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import Button from '../../../components/buttons/Button';
import { trl } from '../../../utils/translations';
import axios from 'axios';
import { toastError, toastNotification } from '../../../utils/toast';
import PropTypes from 'prop-types';

/**
 * The "New HU" preview of the HU Manager: shown after a scanned product label found no existing HU.
 * Shows what the label would create; OK creates the HU (one single-HU inventory on the server), shows a success message naming
 * the HU and the quantity and goes back to the scan screen (the new HU is NOT opened: the user scans the next label, or types the
 * HU number from the message to open it); Cancel creates nothing.
 */
const NewHUPreviewScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'NewHUPreviewScreen',
    captionKey: 'huManager.newHU.title',
    back: huManagerLocation,
  });
  const dispatch = useDispatch();

  const preview = useSelector((state) => getNewHUPreviewFromGlobalState(state));
  const { scannedCode, proposal } = preview ?? {};

  const [qty, setQty] = useState(proposal?.qty ?? 0);
  const [isQtyDialogOpen, setQtyDialogOpen] = useState(false);
  const [locator, setLocator] = useState({ qrCode: proposal?.locatorQRCode, caption: proposal?.locatorCaption });
  const [isCreating, setCreating] = useState(false);
  const isCreatingRef = useRef(false); // synchronous guard against a double tap on OK (state updates are async)

  useEffect(() => {
    // no preview (e.g. a page reload) -> back to the scan screen; not after OK, which leaves the screen itself (history.replace below)
    if (!preview && !isCreatingRef.current) {
      history.goBack();
    }
  }, [preview]);

  if (!preview) return <></>;

  // the label's weight (X12DE355 code like the qty row): shown when it becomes the CU's net weight, else the server's note that it is not used for this product
  const weightText = proposal.weightKg != null ? `${proposal.weightKg} KGM` : proposal.weightNote;
  const isOKEnabled = !isCreating && qty > 0 && !!locator.qrCode;

  const onOK = () => {
    if (!isOKEnabled || isCreatingRef.current) return;
    isCreatingRef.current = true;
    setCreating(true);

    api
      .createHUFromScannedCode({ scannedCode, qty, locatorQRCode: locator.qrCode })
      .then((handlingUnitInfo) => {
        toastNotification({
          plainMessage: trl('huManager.newHU.created', {
            huValue: handlingUnitInfo.displayName,
            qty: `${qty} ${proposal.uom}`,
          }),
        });
        // clear the preview: back on the scan screen it cannot be shown again, so a second OK cannot create a second HU
        history.replace(huManagerLocation());
        dispatch(clearLoadedData());
      })
      .catch((axiosError) => {
        isCreatingRef.current = false;
        setCreating(false);
        toastError({ axiosError });
      });
  };

  // A scan on the preview (no button): a locator (QR code or plain locator barcode, resolved like in the change-locator dialog)
  // replaces the location at once; any other code is refused (error toast + error beep, thrown to the scanner) and changes nothing.
  // Nothing is remembered for the next label.
  const onScanned = async ({ scannedBarcode }) => {
    const locatorQRCode = await resolveLocatorQRCode({ scannedBarcode }).catch((error) => {
      if (axios.isAxiosError(error)) throw error; // a failed call keeps its own message
      throw trl('huManager.newHU.notALocator');
    });
    setLocator({ qrCode: locatorQRCode.code, caption: locatorQRCode.displayable });
  };

  const onCancel = () => {
    history.goBack();
  };

  return (
    <div className="pt-3 section">
      {isQtyDialogOpen && (
        <ReadQtyDialog
          testId="NewHUQtyDialog"
          qtyLabelTrlKey="huManager.newHU.qty"
          initialQty={qty}
          uom={proposal.uom}
          onCloseDialog={() => setQtyDialogOpen(false)}
          onSubmit={({ qty: qtyEntered }) => {
            setQty(Number(qtyEntered));
            setQtyDialogOpen(false);
          }}
        />
      )}
      <table className="table view-header wrap-values is-size-6">
        <tbody>
          <Row
            captionKey="huManager.newHU.product"
            testId="product-value"
            value={`${proposal.product.name} (${proposal.product.value})`}
          />
          <Row captionKey="huManager.newHU.qty" testId="qty-value" value={`${qty} ${proposal.uom}`} />
          {proposal.attributes.map((attribute) => (
            <tr key={attribute.code}>
              <th>{attribute.caption}</th>
              <td data-testid={`attribute-${attribute.code}-value`}>{attribute.valueDisplay}</td>
            </tr>
          ))}
          {weightText && <Row captionKey="huManager.newHU.weight" testId="weight-value" value={weightText} />}
          <Row
            captionKey="huManager.newHU.locator"
            testId="locator-value"
            value={locator.caption ?? trl('huManager.newHU.noLocator')}
          />
        </tbody>
      </table>
      <Button
        testId="qty-edit-button"
        caption={trl('huManager.newHU.changeQty')}
        disabled={isCreating}
        onClick={() => setQtyDialogOpen(true)}
      />
      {/* the confirm buttons come before the scanner block so they stay reachable without scrolling on a handheld screen */}
      <Button testId="ok-button" caption={trl('huManager.newHU.ok')} disabled={!isOKEnabled} onClick={onOK} />
      <Button testId="cancel-button" caption={trl('general.cancelText')} isDanger onClick={onCancel} />
      {/* no scanner while the quantity dialog is open: its window-level key reader would swallow the typed digits */}
      {!isCreating && !isQtyDialogOpen && (
        <BarcodeScannerComponent inputPlaceholderText={trl('huManager.newHU.locator')} onResolvedResult={onScanned} />
      )}
    </div>
  );
};

export default NewHUPreviewScreen;

//
//
//

const Row = ({ captionKey, testId, value }) => (
  <tr>
    <th>{trl(captionKey)}</th>
    <td data-testid={testId}>{value}</td>
  </tr>
);
Row.propTypes = {
  captionKey: PropTypes.string.isRequired,
  testId: PropTypes.string,
  value: PropTypes.any,
};
