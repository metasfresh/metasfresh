import React from 'react';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import { startApplicationByScannedCode } from '../../index';
import { useDispatch } from 'react-redux';
import { useApplicationInfoParameters } from '../../../reducers/applications';
import { APPLICATION_ID } from '../constants';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';

const AppScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'ScanAnythingScreen',
    back: '/',
  });

  const dispatch = useDispatch();
  const { handledApplicationIds } = useApplicationInfoParameters({ applicationId: APPLICATION_ID });

  const onResolvedResult = async ({ scannedBarcode }) => {
    await startApplicationByScannedCode({
      scannedBarcode,
      onlyApplicationIds: handledApplicationIds,
      callerApplicationId: APPLICATION_ID,
      dispatch,
      history,
    });
  };

  return <BarcodeScannerComponent onResolvedResult={onResolvedResult} continuousRunning={true} />;
};

export default AppScreen;
