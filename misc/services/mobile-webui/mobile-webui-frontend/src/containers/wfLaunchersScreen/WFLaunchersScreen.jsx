import React, { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { useFacets, useFilters } from '../../reducers/launchers';

import WFLauncherButton from './WFLauncherButton';
import BarcodeScannerComponent from '../../components/BarcodeScannerComponent';
import ButtonWithIndicator from '../../components/buttons/ButtonWithIndicator';
import { toQRCodeDisplayable } from '../../utils/qrCode/hu';
import WFLaunchersFilterButton from './filters/WFLaunchersFilterButton';
import { updateHeaderEntry } from '../../actions/HeaderActions';
import { trl } from '../../utils/translations';
import { appLaunchersBarcodeScannerLocation, appLaunchersFilterLocation } from '../../routes/launchers';
import { useCurrentWorkplace } from '../../api/workplace';
import { useApplicationInfo } from '../../reducers/applications';
import { useCurrentWorkstation } from '../../api/workstation';
import { useScreenDefinition } from '../../hooks/useScreenDefinition';
import { useMobileLocation } from '../../hooks/useMobileLocation';
import { useLaunchers } from './useLaunchers';
import { APPLICATION_ID_Distribution } from '../../apps/distribution/constants';
import DistributionJobsListActions from '../../apps/distribution/containers/DistributionJobsListActions';
import { useCurrentTrolley } from '../../api/trolley';
import { toastError } from '../../utils/toast';

const WFLaunchersScreen = () => {
  const { history } = useScreenDefinition({ screenId: 'WFLaunchersScreen', back: '/' });
  const dispatch = useDispatch();
  const { url, applicationId } = useMobileLocation();

  const { showFilterByQRCode, showFilters } = useApplicationInfo({ applicationId });
  const { isWorkstationLoading, isWorkstationRequired, workstation, setWorkstationByQRCode } = useCurrentWorkstation({
    applicationId,
  });
  const { isWorkplaceLoading, isWorkplaceRequired, workplace, setWorkplaceByQRCode } = useCurrentWorkplace({
    applicationId,
  });
  const { isTrolleyRequired, isTrolleyLoading, trolley, setTrolleyByScannedCode, clearTrolley } = useCurrentTrolley({
    applicationId,
  });
  const filters = useFilters({ applicationId });
  const facets = useFacets({ applicationId });
  const { isLaunchersLoading, launchers, filterByQRCode, actions } = useLaunchers({
    applicationId,
    showFilterByQRCode,
    filters,
    facets,
    isEnabled: !isWorkplaceLoading && !isTrolleyLoading,
  });

  const workplaceName = workplace?.name;
  const workstationName = workstation?.name;
  useEffect(() => {
    dispatch(
      updateHeaderEntry({
        location: url,
        values: [
          {
            caption: trl('general.workplace'),
            value: workplaceName,
            hidden: !workplaceName,
          },
          {
            caption: trl('general.workstation'),
            value: workstationName,
            hidden: !workstationName,
          },
        ],
      })
    );
  }, [url, workplaceName, workstationName]);

  //
  // Get Workstation
  if (isWorkstationLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (isWorkstationRequired && !workstation) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => setWorkstationByQRCode(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkstationPlaceholder')}
          continuousRunning={true}
        />
      </div>
    );
  }

  //
  // Get Workspace
  if (isWorkplaceLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (isWorkplaceRequired && !workplace) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => setWorkplaceByQRCode(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkplacePlaceholder')}
          continuousRunning={true}
        />
      </div>
    );
  }

  //
  // Trolley
  if (isTrolleyLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (isTrolleyRequired && !trolley) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) =>
            setTrolleyByScannedCode(scannedBarcode).catch((axiosError) => toastError({ axiosError }))
          }
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanTrolleyPlaceholder')}
          continuousRunning={true}
        />
      </div>
    );
  }

  //
  // Launchers
  return (
    <div className="container launchers-container">
      {showFilterByQRCode && (
        <ButtonWithIndicator
          typeFASIconName="fa-solid fa-barcode"
          caption={filterByQRCode ? toQRCodeDisplayable(filterByQRCode) : 'Scan barcode'}
          onClick={() => history.push(appLaunchersBarcodeScannerLocation({ applicationId }))}
          testId="filterByQRCode-button"
        />
      )}
      {isTrolleyRequired && (
        <ButtonWithIndicator
          additionalCssClass="action-button"
          typeFASIconName="fa-solid fa-cart-shopping"
          caption={trolley?.caption ?? trl('components.BarcodeScannerComponent.scanTrolleyPlaceholder')}
          onClick={() => clearTrolley().catch((axiosError) => toastError({ axiosError }))}
          testId="scanTrolley-button"
        />
      )}
      {showFilters && (
        <WFLaunchersFilterButton
          filters={filters}
          facets={facets}
          onClick={() => {
            history.push(appLaunchersFilterLocation({ applicationId }));
          }}
        />
      )}
      <br />
      {applicationId === APPLICATION_ID_Distribution && (
        <DistributionJobsListActions actions={actions} disabled={isLaunchersLoading} />
      )}
      {launchers &&
        launchers.map((launcher, index) => {
          const id = `launcher-${index}-button`;
          return (
            <WFLauncherButton
              key={id}
              applicationId={launcher.applicationId}
              caption={launcher.caption}
              startedWFProcessId={launcher.startedWFProcessId}
              wfParameters={launcher.wfParameters}
              showWarningSign={launcher.showWarningSign}
              indicator={launcher.indicator}
              testId={launcher.testId}
              disabled={!!launcher.disabled}
            />
          );
        })}
      {isLaunchersLoading && <Spinner />}
      {isTrolleyRequired && trolley && (
        <ButtonWithIndicator
          captionKey="general.releaseTrolley.buttonCaption"
          testId="release-trolley-button"
          isDanger
          onClick={() => clearTrolley().catch((axiosError) => toastError({ axiosError }))}
          additionalCssClass="action-button"
        />
      )}
    </div>
  );
};

export default WFLaunchersScreen;

//
//
// ------------------------------------------------------------------------------------------------------------------------------
//
//

const Spinner = () => {
  return (
    <div className="loading">
      <i className="loading-icon fas fa-solid fa-spinner fa-spin" />
    </div>
  );
};
