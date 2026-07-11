/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
import React, { useEffect, useState } from 'react';
import WorkstationInfoComponent from '../components/WorkstationInfoComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import * as api from '../../../api/workstation';
import * as workplaceApi from '../../../api/workplace';
import { toastError } from '../../../utils/toast';
import { appTrl } from '../utils';
import * as scanAnythingRoutes from '../../scanAnything/routes';

import { APPLICATION_ID as APPLICATION_ID_scanAnything } from '../../scanAnything/constants';
import { appLocation } from '../routes';
import Spinner from '../../../components/Spinner';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';

const AppScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'WorkstationManagerScreen',
    back: '/',
  });

  const [loading, setLoading] = useState(true);
  const [workstation, setWorkstation] = useState();
  // The operator's CURRENT active workplace (system of record: GET /workplace), shown alongside the
  // workstation so a drift between the two is visible to the operator (AC3). Distinct from the
  // workstation's statically-linked workplace.
  const [currentWorkplace, setCurrentWorkplace] = useState();

  const queryParameters = new URLSearchParams(window.location.search);
  const qrCodeParam = queryParameters.get('qrCode');
  const callerApplicationId = queryParameters.get('callerApplicationId');
  useEffect(() => {
    setLoading(true);
    // Scan path: onBarcodeScanned assigns, then refreshes the current workplace itself — so it reflects
    // the POST-assign workplace. Do NOT also refresh here in parallel: that parallel GET reads the
    // PRE-assign (drifted) workplace and, resolving last, would clobber the correct post-assign value.
    // Entry path (no scan): load the current workstation + current active workplace so drift is visible.
    const init =
      qrCodeParam && !workstation
        ? onBarcodeScanned({ scannedBarcode: qrCodeParam })
        : Promise.all([loadCurrentWorkstation(), refreshCurrentWorkplace()]);
    init.finally(() => setLoading(false));
  }, []);

  const loadCurrentWorkstation = () => {
    return api
      .getCurrentWorkstationInfo()
      .then(({ assignedWorkstation }) => {
        if (assignedWorkstation) {
          setWorkstation(assignedWorkstation);
        }
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const refreshCurrentWorkplace = () => {
    return workplaceApi
      .getCurrentWorkplaceInfo()
      .then(({ assignedWorkplace }) => setCurrentWorkplace(assignedWorkplace))
      .catch((axiosError) => toastError({ axiosError }));
  };

  const setWorkstationAndUpdateUrl = (newWorkstation) => {
    setWorkstation(newWorkstation);
    history.replace(appLocation({ qrCode: newWorkstation?.qrCode, callerApplicationId }));
  };

  const onBarcodeScanned = ({ scannedBarcode }) => {
    // Assign on scan (mirror the workplace app): a scan — including a re-scan of an already-assigned
    // workstation — re-assigns it, switching the operator's active workplace back to the scanned
    // workstation's workplace. A read-only lookup here would silently fail to re-switch a drifted workplace.
    return api
      .assignWorkstationByQRCode(scannedBarcode)
      .then((workstationInfo) => {
        setWorkstationAndUpdateUrl(workstationInfo);
        return refreshCurrentWorkplace();
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onAssignClick = () => {
    api
      .assignWorkstationById(workstation.id)
      .then((newWorkstation) => {
        setWorkstation(newWorkstation);
        return refreshCurrentWorkplace();
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onScanAgainClick = () => {
    if (callerApplicationId === APPLICATION_ID_scanAnything) {
      history.push(scanAnythingRoutes.appLocation());
    } else {
      setWorkstationAndUpdateUrl(null);
    }
  };

  if (loading) {
    return (
      <div className="app-workstantionManager">
        <Spinner />
      </div>
    );
  } else if (workstation) {
    return (
      <div className="app-workstantionManager">
        <WorkstationInfoComponent workstationInfo={workstation} currentWorkplaceName={currentWorkplace?.name} />
        <div className="pt-3 section">
          {!workstation.userAssigned && (
            <ButtonWithIndicator
              caption={appTrl('action.assign.buttonCaption')}
              onClick={onAssignClick}
              testId="assign-button"
            />
          )}
          <ButtonWithIndicator caption={appTrl('action.scanAgain.buttonCaption')} onClick={onScanAgainClick} />
        </div>
      </div>
    );
  } else {
    return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} />;
  }
};

export default AppScreen;
