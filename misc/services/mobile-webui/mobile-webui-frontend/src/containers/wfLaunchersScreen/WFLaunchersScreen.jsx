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
import { APPLICATION_ID_Picking } from '../../apps/picking';
import PickingJobsListActions from '../../apps/picking/containers/PickingJobsListActions';
import { useCurrentTrolley } from '../../api/trolley';
import { toastError } from '../../utils/toast';

const WFLaunchersScreen = () => {
  const { history } = useScreenDefinition({ screenId: 'WFLaunchersScreen', back: '/', isHomeStop: true });
  const dispatch = useDispatch();
  const { url, applicationId } = useMobileLocation();

  const { showFilterByQRCode, showFilters } = useApplicationInfo({ applicationId });
  const {
    isWorkplaceLoading,
    isWorkplaceRequired,
    workplace,
    workplaceErrorMessage,
    retryWorkplace,
    reloadWorkplace,
    setWorkplaceByQRCode,
  } = useCurrentWorkplace({ applicationId });
  const {
    isWorkstationLoading,
    isWorkstationRequired,
    workstation,
    workstationErrorMessage,
    retryWorkstation,
    setWorkstationByQRCode,
  } = useCurrentWorkstation({
    applicationId,
    // Assigning a workstation re-assigns the operator's workplace server-side too, so the workplace this
    // screen shows (and filters its jobs by) has to be re-read — nothing remounts this screen.
    onWorkstationAssigned: reloadWorkplace,
  });
  const { isTrolleyRequired, isTrolleyLoading, trolley, setTrolleyByScannedCode, clearTrolley } = useCurrentTrolley({
    applicationId,
  });

  const operatorContextErrorMessage = workstationErrorMessage ?? workplaceErrorMessage;
  const isAskingForWorkstation = isWorkstationRequired && !workstation;
  const isAskingForWorkplace = isWorkplaceRequired && !workplace;
  const isAskingForTrolley = isTrolleyRequired && !trolley;
  // The launchers are filtered server-side by the operator's context (e.g. manufacturing jobs by the
  // assigned workstation), so fetching them before that context is established returns a list for the
  // wrong context — and nothing would refetch it once the operator scans. Gate the fetch on the very
  // conditions that gate rendering the list below; `isEnabled` is a fetch dependency, so the list is
  // fetched exactly once the context is complete.
  const isOperatorContextReady =
    !operatorContextErrorMessage &&
    !isWorkstationLoading &&
    !isWorkplaceLoading &&
    !isTrolleyLoading &&
    !isAskingForWorkstation &&
    !isAskingForWorkplace &&
    !isAskingForTrolley;

  const filters = useFilters({ applicationId });
  const facets = useFacets({ applicationId });
  const { isLaunchersLoading, launchers, filterByQRCode, actions } = useLaunchers({
    applicationId,
    showFilterByQRCode,
    filters,
    facets,
    isEnabled: isOperatorContextReady,
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
  // Operator context (workplace / workstation) could not be read, or could not be assigned from a scan
  // Takes over the screen: without it we would either hide the header row for good, ask the operator to
  // re-scan a workstation/workplace they are in fact still assigned to, or silently swallow their scan.
  if (operatorContextErrorMessage) {
    return (
      <div className="container launchers-container">
        <div className="notification is-danger mt-3" data-testid="operator-context-error-panel">
          <p className="mb-3">
            <strong>{trl('launchers.operatorContext.error.title')}</strong>
          </p>
          <p className="mb-3">{operatorContextErrorMessage}</p>
          <ButtonWithIndicator
            testId="operator-context-error-retry"
            captionKey="launchers.operatorContext.error.retry"
            // Re-fire only what actually failed — the message above is the workstation's whenever it
            // has one, so retrying the workplace too would be a wasted request the operator waits on.
            onClick={workstationErrorMessage ? retryWorkstation : retryWorkplace}
          />
        </div>
      </div>
    );
  }

  //
  // Get Workstation
  if (isWorkstationLoading) {
    return (
      <div className="container launchers-container">
        <Spinner />
      </div>
    );
  } else if (isAskingForWorkstation) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => setWorkstationByQRCode(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkstationPlaceholder')}
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
  } else if (isAskingForWorkplace) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) => setWorkplaceByQRCode(scannedBarcode)}
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanWorkplacePlaceholder')}
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
  } else if (isAskingForTrolley) {
    return (
      <div className="container launchers-container">
        <BarcodeScannerComponent
          onResolvedResult={({ scannedBarcode }) =>
            setTrolleyByScannedCode(scannedBarcode).catch((axiosError) => toastError({ axiosError }))
          }
          inputPlaceholderText={trl('components.BarcodeScannerComponent.scanTrolleyPlaceholder')}
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
      {applicationId === APPLICATION_ID_Picking && <PickingJobsListActions />}
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
