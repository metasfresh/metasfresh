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
import WorkplaceInfoComponent from '../components/WorkplaceInfoComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import * as api from '../../../api/workplace';
import { toastError } from '../../../utils/toast';
import { appLocation } from '../routes';
import { appTrl } from '../utils';
import { APPLICATION_ID as APPLICATION_ID_scanAnything } from '../../scanAnything/constants';
import * as scanAnythingRoutes from '../../scanAnything/routes';
import Spinner from '../../../components/Spinner';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';

const AppScreen = () => {
  const { history } = useScreenDefinition({ back: '/' });

  const [loading, setLoading] = useState(true);
  const [workplace, setWorkplace] = useState();

  const queryParameters = new URLSearchParams(window.location.search);
  const qrCodeParam = queryParameters.get('qrCode');
  const parentApplicationId = queryParameters.get('parent');
  useEffect(() => {
    if (qrCodeParam && !workplace) {
      setLoading(true);
      onBarcodeScanned({ scannedBarcode: qrCodeParam }).finally(() => setLoading(false));
    } else {
      setLoading(false);
    }
  }, []);

  const setWorkplaceAndUpdateUrl = (newWorkplace) => {
    setWorkplace(newWorkplace);
    history.replace(appLocation({ qrCode: newWorkplace?.qrCode, parent: parentApplicationId }));
  };

  const onBarcodeScanned = ({ scannedBarcode }) => {
    return api.getWorkplaceByQRCode(scannedBarcode).then((workplaceInfo) => setWorkplaceAndUpdateUrl(workplaceInfo));
  };

  const onAssignClick = () => {
    api
      .assignWorkplace(workplace.id)
      .then((workplace) => setWorkplace(workplace))
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onScanAgainClick = () => {
    if (parentApplicationId === APPLICATION_ID_scanAnything) {
      history.push(scanAnythingRoutes.appLocation());
    } else {
      setWorkplaceAndUpdateUrl(null);
    }
  };

  if (loading) {
    return (
      <div className="app-workstantionManager">
        <Spinner />
      </div>
    );
  } else if (workplace) {
    return (
      <>
        <WorkplaceInfoComponent workplaceInfo={workplace} />
        <div className="pt-3 section">
          {!workplace.userAssigned && (
            <ButtonWithIndicator caption={appTrl('action.assign.buttonCaption')} onClick={onAssignClick} />
          )}
          <ButtonWithIndicator caption={appTrl('action.scanAgain.buttonCaption')} onClick={onScanAgainClick} />
        </div>
      </>
    );
  } else {
    return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
  }
};

export default AppScreen;
