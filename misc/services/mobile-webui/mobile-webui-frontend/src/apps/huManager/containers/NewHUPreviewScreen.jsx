import React, { useEffect, useRef, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import * as api from '../api';
import { clearLoadedData } from '../actions';
import { getNewHUPreviewFromGlobalState } from '../reducers';
import { huManagerLocation } from '../routes';
import QtyInputField from '../../../components/QtyInputField';
import { qtyInfos } from '../../../utils/qtyInfos';
import ChangeCurrentLocatorDialog from '../components/ChangeCurrentLocatorDialog';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import Button from '../../../components/buttons/Button';
import { trl } from '../../../utils/translations';
import { toastError, toastNotification } from '../../../utils/toast';
import { updateHeaderEntry } from '../../../actions/HeaderActions';

const toQtyInfo = (proposal) =>
  proposal?.qty != null
    ? qtyInfos.of({ qty: proposal.qty, qtyStr: `${proposal.qty}` })
    : qtyInfos.invalidOfNumber(null);
const toLocator = (proposal) => ({ qrCode: proposal?.locatorQRCode, caption: proposal?.locatorCaption });

/**
 * The "New HU" preview of the HU Manager: shown after a scanned product label found no existing HU.
 * Shows what the label would create (quantity editable inline, location changed with the "Change locator" button, which opens the scan
 * dialog of the HU Manager); OK creates the HU (one single-HU inventory on the server), shows a success message naming
 * the HU and the quantity and goes back to the scan screen (the new HU is NOT opened: the user scans the next label, or types the
 * HU number from the message to open it); Cancel creates nothing.
 */
const NewHUPreviewScreen = () => {
  const { history, url } = useScreenDefinition({
    screenId: 'NewHUPreviewScreen',
    captionKey: 'huManager.newHU.title',
    back: huManagerLocation,
  });
  const dispatch = useDispatch();

  const preview = useSelector((state) => getNewHUPreviewFromGlobalState(state));
  const { scannedCode, proposal } = preview ?? {};

  // the quantity field starts empty, or with the quantity the scanned code carries (the proposal); it forwards its qtyInfo on mount and on every keystroke
  const [qtyInfo, setQtyInfo] = useState(() => toQtyInfo(proposal));
  const [isChangeLocatorDialogOpen, setChangeLocatorDialogOpen] = useState(false);
  const [locator, setLocator] = useState(() => toLocator(proposal));
  const [isCreating, setCreating] = useState(false);
  const isCreatingRef = useRef(false); // synchronous guard against a double tap on OK (state updates are async)

  // a replaced proposal (a new preview in the store while the screen stays mounted) restarts from its quantity and location
  // (not reachable today: the preview unmounts between scans; kept so the screen never shows a stale quantity or location)
  useEffect(() => {
    setQtyInfo(toQtyInfo(proposal));
    setLocator(toLocator(proposal));
  }, [proposal]);

  // useScreenDefinition returns a new history object on every render: the effect below reads the latest one through a ref,
  // so that it runs only when the preview changes and not after every render
  const historyRef = useRef(history);
  historyRef.current = history;
  useEffect(() => {
    // no preview (e.g. a page reload) -> back to the scan screen; not after OK, which leaves the screen itself (history.replace below)
    if (!preview && !isCreatingRef.current) {
      historyRef.current.goBack();
    }
  }, [preview]);

  // what the label would create is shown in the app's caption/value header (same component as every other screen); the locator
  // is changeable, so the header values follow it
  const weightText = proposal?.weightKg != null ? `${proposal.weightKg} KGM` : proposal?.weightNote; // the label's weight (the X12DE355 code, not translated): shown when it becomes the CU's net weight, else the server's note that it is not used for this product
  const locatorCaption = locator.caption ?? trl('huManager.newHU.noLocator');
  useEffect(() => {
    if (!proposal) return;
    dispatch(
      updateHeaderEntry({
        location: url,
        values: [
          {
            caption: trl('huManager.newHU.product'),
            value: `${proposal.product.name} (${proposal.product.value})`,
            valueTestId: 'product-value',
          },
          ...proposal.attributes.map((attribute) => ({
            caption: attribute.caption,
            value: attribute.valueDisplay,
            valueTestId: `attribute-${attribute.code}-value`,
          })),
          {
            caption: trl('huManager.newHU.weight'),
            value: weightText,
            valueTestId: 'weight-value',
            hidden: !weightText,
          },
          { caption: trl('huManager.newHU.locator'), value: locatorCaption, valueTestId: 'locator-value' },
        ],
      })
    );
  }, [url, proposal, weightText, locatorCaption]);

  if (!preview) return <></>;

  const qty = qtyInfo.qty;
  const isOKEnabled = !isCreating && qtyInfo.isQtyValid && qty > 0 && !!locator.qrCode; // an emptied or 0 quantity disables OK

  const onOK = () => {
    if (!isOKEnabled || isCreatingRef.current) return;
    isCreatingRef.current = true;
    setCreating(true);

    api
      .createHUFromScannedCode({ scannedCode, qty, locatorQRCode: locator.qrCode })
      .then((handlingUnitInfo) => {
        // clear the preview: back on the scan screen it cannot be shown again, so a second OK cannot create a second HU
        history.replace(huManagerLocation());
        dispatch(clearLoadedData());
        // the message goes after the navigation: ScreenToaster dismisses all toasts on a location change, which would remove a toast raised before it
        toastNotification({
          plainMessage: trl('huManager.newHU.created', {
            huValue: handlingUnitInfo.displayName,
            qty: `${qty} ${proposal.uom}`,
          }),
        });
      })
      .catch((axiosError) => {
        isCreatingRef.current = false;
        setCreating(false);
        toastError({ axiosError });
      });
  };

  const onCancel = () => {
    history.goBack();
  };

  return (
    <>
      {/* the quantity row sits right under the header table, outside the padded `.section`, so its caption lines up with the header captions */}
      <table className="table view-header is-size-6">
        <tbody>
          <tr>
            <th>{trl('huManager.newHU.qty')}</th>
            <td data-testid="qty-value">
              <QtyInputField
                testId="qty-input"
                qty={qtyInfos.toNumberOrString(qtyInfo)}
                uom={proposal.uom}
                onQtyChange={setQtyInfo}
                readonly={isCreating}
              />
            </td>
          </tr>
        </tbody>
      </table>
      {/* the buttons: OK / Cancel / Change locator; no scanner on this screen (a locator is scanned in the dialog) */}
      <div className="pt-3 section">
        <Button testId="ok-button" caption={trl('huManager.newHU.ok')} disabled={!isOKEnabled} onClick={onOK} />
        <Button
          testId="change-locator-button"
          caption={trl('huManager.newHU.changeLocator')}
          disabled={isCreating}
          onClick={() => setChangeLocatorDialogOpen(true)}
        />
        <Button testId="cancel-button" caption={trl('general.cancelText')} isDanger onClick={onCancel} />
      </div>
      {isChangeLocatorDialogOpen && (
        <ChangeCurrentLocatorDialog
          onOK={(locatorQRCode) => {
            setLocator({ qrCode: locatorQRCode.code, caption: locatorQRCode.displayable });
            setChangeLocatorDialogOpen(false);
          }}
          onClose={() => setChangeLocatorDialogOpen(false)}
        />
      )}
    </>
  );
};

export default NewHUPreviewScreen;
