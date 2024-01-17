import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import * as api from '../api';
import { toastError } from '../../../utils/toast';
import ReadQtyDialog from '../../../components/dialogs/ReadQtyDialog';

const HUPrintingOptionsScreen = () => {
  const dispatch = useDispatch();
  const history = useHistory();
  const { url } = useRouteMatch();

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const [printingOptions, setPrintingOptions] = useState([]);
  const [numberOfCopiesModalDisplayed, setNumberOfCopiesModalDisplayed] = useState(false);
  const [currentPrintingOption, setCurrentPrintingOption] = useState();
  const [isPrintingInProgress, setIsPrintingInProgress] = useState(false);

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }
    dispatch(pushHeaderEntry({ location: url, caption: trl('huManager.action.printLabels.windowName') }));
  }, []);

  useEffect(() => {
    if (!printingOptions || printingOptions.length === 0) {
      api.getPrintingOptions().then((options) => setPrintingOptions(options || []));
    }
  }, [printingOptions]);

  const toggleShowPrintOptionsModal = (display, printingOption) => {
    if (display && !printingOption) {
      toastError({ messageKey: 'huManager.action.printLabels.noPrintingOptionSelected' });
      return;
    }

    if (display) {
      setNumberOfCopiesModalDisplayed(true);
      setCurrentPrintingOption(printingOption);
    } else {
      setNumberOfCopiesModalDisplayed(false);
      setCurrentPrintingOption(undefined);
    }
  };

  const printLabel = ({ qty }) => {
    if (!currentPrintingOption) {
      toastError({ messageKey: 'huManager.action.printLabels.noPrintingOptionSelected' });
      return;
    }
    setIsPrintingInProgress(true);

    api
      .printHULabels({
        huQRCode: handlingUnitInfo.qrCode,
        huLabelProcessId: currentPrintingOption.processId,
        nrOfCopies: qty,
      })
      .then(() => history.goBack())
      .catch((axiosError) => toastError({ axiosError }))
      .finally(() => {
        toggleShowPrintOptionsModal(false);
        setIsPrintingInProgress(false);
      });
  };

  return (
    <div className="pt-3 section">
      {numberOfCopiesModalDisplayed && (
        <ReadQtyDialog
          qtyLabelTrlKey={'huManager.action.printLabels.nrOfCopies'}
          submitButtonTrlKey={'huManager.action.printLabels.print'}
          onCloseDialog={() => toggleShowPrintOptionsModal(false)}
          onSubmit={printLabel}
          isReadOnly={isPrintingInProgress}
        />
      )}
      {printingOptions.map((option, index) => (
        <ButtonWithIndicator
          key={index}
          caption={option.caption}
          onClick={() => toggleShowPrintOptionsModal(true, option)}
        />
      ))}
    </div>
  );
};

export default HUPrintingOptionsScreen;
