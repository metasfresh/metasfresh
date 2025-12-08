import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';

import { setScannedBarcode, useScannedBarcodeSuggestions } from '../../../actions/ScanActions';
import { updateHeaderEntry } from '../../../actions/HeaderActions';
import { getActivityById } from '../../../reducers/wfProcesses';

import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { toastError } from '../../../utils/toast';
import Spinner from '../../../components/Spinner';
import * as uiTrace from '../../../utils/ui_trace';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { getWFProcessScreenLocation } from '../../../routes/workflow_locations';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import PropTypes from 'prop-types';
import ButtonDetails from '../../../components/buttons/ButtonDetails';

const ScanScreen = () => {
  const { history, url, applicationId, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'ScanScreen',
    back: getWFProcessScreenLocation,
  });

  const queryParameters = new URLSearchParams(window.location.search);
  const isUseTheAlreadyScannedQrCode = queryParameters.get('resendQr') === 'true';
  const validOptionIndex = queryParameters.get('validOptionIndex');

  const { activityCaption, userInstructions, currentValue, validOptions, isDisplaySuggestions } = useSelector(
    (state) => {
      const activity = getActivityById(state, wfProcessId, activityId);
      return {
        activityCaption: activity?.caption,
        userInstructions: activity?.userInstructions,
        currentValue: activity?.dataStored.currentValue,
        validOptions: activity?.dataStored?.validOptions,
        isDisplaySuggestions: activity?.dataStored?.displaySuggestions ?? false,
      };
    }
  );

  const dispatch = useDispatch();
  useEffect(() => {
    dispatch(updateHeaderEntry({ location: url, caption: activityCaption, userInstructions }));
  }, [url, activityCaption, userInstructions]);

  useEffect(() => {
    if (isUseTheAlreadyScannedQrCode && currentValue?.qrCode !== undefined) {
      onBarcodeScanned({ scannedBarcode: currentValue?.qrCode });
    }
  }, [isUseTheAlreadyScannedQrCode, currentValue?.qrCode]);

  const { isScannedBarcodeSuggestionsLoading, scannedBarcodeSuggestions } = useScannedBarcodeSuggestions({
    wfProcessId,
    activityId,
    enabled: isDisplaySuggestions,
  });

  const onBarcodeScanned = ({ scannedBarcode }) => {
    // console.log('onBarcodeScanned', { scannedBarcode, history });
    uiTrace.trace({ eventName: 'barcodeScanned', scannedBarcode, wfProcessId, activityId });

    //
    // Validate scanned barcode
    if (validOptionIndex != null && !validOptions?.length) {
      toastError({
        messageKey: 'activities.mfg.validateSourceLocator.noValidOption',
        context: { validOptionIndex, validOptions },
      });
      history.goBack();
      return;
    }

    const scannedBarcodeExpected = validOptionIndex != null ? validOptions[validOptionIndex]?.qrCode : null;
    if (scannedBarcodeExpected != null && scannedBarcode !== scannedBarcodeExpected) {
      toastError({
        messageKey: 'activities.mfg.validateSourceLocator.qrDoesNotMatch',
        context: { scannedBarcode, scannedBarcodeExpected, validOptionIndex, validOptions },
      });
      return;
    }

    dispatch(
      setScannedBarcode({
        applicationId,
        wfProcessId,
        activityId,
        scannedBarcode,
        history,
        onWFActivityCompleted: () => {
          history.goBack();
        },
      })
    );
  };

  if (isUseTheAlreadyScannedQrCode) {
    return <Spinner />;
  } else {
    return (
      <>
        <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />
        {isDisplaySuggestions && (
          <ScannedBarcodeSuggestions
            isLoading={isScannedBarcodeSuggestionsLoading}
            suggestions={scannedBarcodeSuggestions}
            onClick={(suggestion) => onBarcodeScanned({ scannedBarcode: suggestion.qrCode })}
          />
        )}
        )
      </>
    );
  }
};

export default ScanScreen;

//
//
//
//
//

const ScannedBarcodeSuggestions = ({ isLoading, suggestions, onClick }) => {
  if (isLoading) return <Spinner />;

  return (
    <>
      {suggestions?.list.map((suggestion, index) => (
        <ScannedBarcodeSuggestion key={index} suggestion={suggestion} onClick={onClick} />
      ))}
    </>
  );
};
ScannedBarcodeSuggestions.propTypes = {
  isLoading: PropTypes.bool,
  suggestions: PropTypes.object,
  onClick: PropTypes.func.isRequired,
};

//
//
//
//
//

const ScannedBarcodeSuggestion = ({ suggestion, onClick }) => {
  // console.log('ScannedBarcodeSuggestion', { suggestion });
  return (
    <ButtonWithIndicator
      caption={suggestion.caption + ' ' + suggestion.detail}
      onClick={() => onClick(suggestion)}
      additionalCssClass="pickingSlot-button" // needed for playwright testing
      testId={suggestion.qrCode}
      data-bpartnerlocationid={suggestion.additionalProperties?.bpartnerLocationId}
      data-detail-value1={suggestion.value1}
    >
      <ButtonDetails
        caption1={suggestion.property1}
        value1={suggestion.value1}
        caption2={suggestion.property2}
        value2={suggestion.value2}
      />
    </ButtonWithIndicator>
  );
};
ScannedBarcodeSuggestion.propTypes = {
  suggestion: PropTypes.object,
  onClick: PropTypes.func.isRequired,
};
