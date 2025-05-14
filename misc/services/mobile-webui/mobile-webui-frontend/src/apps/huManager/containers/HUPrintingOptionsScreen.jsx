import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import * as api from '../api';
import * as actions from '../actions';
import { toastError } from '../../../utils/toast';
import ReadQtyDialog from '../../../components/dialogs/ReadQtyDialog';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';

const HUPrintingOptionsScreen = () => {
  const { history } = useScreenDefinition({
    captionKey: 'huManager.action.printLabels.windowName',
    back: huManagerLocation,
  });

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const [printingOptions, setPrintingOptions] = useState([]);
  const [numberOfCopiesModalDisplayed, setNumberOfCopiesModalDisplayed] = useState(false);
  const [currentPrintingOption, setCurrentPrintingOption] = useState();
  const [isPrintingInProgress, setIsPrintingInProgress] = useState(false);

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
    }
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

    actions
      .printHULabels({
        huId: handlingUnitInfo.id,
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

  const optionsWithLongCaption = printingOptions.some((printingOption) => (printingOption.caption || '').length > 20);
  const adjustCaption = (caption) => {
    if (caption.length > 50) {
      return `${caption.substring(0, 47).trim()}...`;
    }
    return caption;
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
          caption={adjustCaption(option.caption)}
          onClick={() => toggleShowPrintOptionsModal(true, option)}
          size={optionsWithLongCaption ? 'large' : undefined}
          additionalCssClass={optionsWithLongCaption ? 'normal-white-space' : undefined}
        />
      ))}
    </div>
  );
};

export default HUPrintingOptionsScreen;
