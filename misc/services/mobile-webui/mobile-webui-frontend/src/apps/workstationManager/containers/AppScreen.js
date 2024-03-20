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
import { useDispatch } from 'react-redux';
import WorkstationInfoComponent from '../components/WorkstationInfoComponent';
import { useHistory, useRouteMatch } from 'react-router-dom';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import * as api from '../../../api/workstation';
import { toastError } from '../../../utils/toast';
import { appTrl } from '../utils';
import * as scanAnythingRoutes from '../../scanAnything/routes';

import { APPLICATION_ID as APPLICATION_ID_scanAnything } from '../../scanAnything/constants';
import { appLocation } from '../routes';
import Spinner from '../../../components/Spinner';

const AppScreen = () => {
  const { url } = useRouteMatch();
  const dispatch = useDispatch();
  const history = useHistory();
  const [loading, setLoading] = useState(true);
  const [workstation, setWorkstation] = useState();

  useEffect(() => {
    // IMPORTANT, else it won't restore the title when we move back to this screen
    dispatch(pushHeaderEntry({ location: url }));
  }, []);

  const queryParameters = new URLSearchParams(window.location.search);
  const qrCodeParam = queryParameters.get('qrCode');
  const parentApplicationId = queryParameters.get('parent');
  useEffect(() => {
    if (qrCodeParam && !workstation) {
      setLoading(true);
      onBarcodeScanned({ scannedBarcode: qrCodeParam }).finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const setWorkstationAndUpdateUrl = (newWorkstation) => {
    setWorkstation(newWorkstation);
    history.replace(appLocation({ qrCode: newWorkstation?.qrCode, parent: parentApplicationId }));
  };

  const onBarcodeScanned = ({ scannedBarcode }) => {
    return api
      .getWorkstationByQRCode(scannedBarcode)
      .then((workplaceInfo) => setWorkstationAndUpdateUrl(workplaceInfo));
  };

  const onAssignClick = () => {
    api
      .assignWorkstationById(workstation.id)
      .then((workstation) => setWorkstation(workstation))
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onScanAgainClick = () => {
    if (parentApplicationId === APPLICATION_ID_scanAnything) {
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
        <WorkstationInfoComponent workstationInfo={workstation} />
        <div className="pt-3 section">
          {!workstation.userAssigned && (
            <ButtonWithIndicator caption={appTrl('action.assign.buttonCaption')} onClick={onAssignClick} />
          )}
          <ButtonWithIndicator caption={appTrl('action.scanAgain.buttonCaption')} onClick={onScanAgainClick} />
        </div>
      </div>
    );
  } else {
    return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
  }
};

export default AppScreen;
