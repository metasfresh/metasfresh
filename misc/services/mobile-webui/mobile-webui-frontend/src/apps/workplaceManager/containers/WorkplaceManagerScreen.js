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
import WorkplaceInfoComponent from '../components/WorkplaceInfoComponent';
import { useRouteMatch } from 'react-router-dom';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import BarcodeScannerComponent from '../../../components/BarcodeScannerComponent';
import * as api from '../../../api/workplace';
import { toastError } from '../../../utils/toast';

const WorkplaceManagerScreen = () => {
  const { url } = useRouteMatch();
  const dispatch = useDispatch();

  const [workplace, setWorkplace] = useState();
  console.log('ctor', { workplace });

  useEffect(() => {
    // IMPORTANT, else it won't restore the title when we move back to this screen
    dispatch(pushHeaderEntry({ location: url }));
  }, []);

  const onBarcodeScanned = ({ scannedBarcode }) => {
    console.log('onBarcodeScanned', { scannedBarcode });
    api
      .getWorkplaceByQRCode(scannedBarcode)
      .then((workplaceInfo) => {
        console.log('got', { workplaceInfo });
        setWorkplace(workplaceInfo);
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onAssignClick = () => {
    api
      .assignWorkplace(workplace.id)
      .then((workplace) => setWorkplace(workplace))
      .catch((axiosError) => toastError({ axiosError }));
  };

  const onScanAgainClick = () => {
    setWorkplace(null);
  };

  if (workplace) {
    return (
      <>
        <WorkplaceInfoComponent workplaceInfo={workplace} />
        <div className="pt-3 section">
          {!workplace.userAssigned && (
            <ButtonWithIndicator
              caption={trl('workplaceManager.action.assign.buttonCaption')}
              onClick={onAssignClick}
            />
          )}
          <ButtonWithIndicator
            caption={trl('workplaceManager.action.scanAgain.buttonCaption')}
            onClick={onScanAgainClick}
          />
        </div>
      </>
    );
  } else {
    return <BarcodeScannerComponent onResolvedResult={onBarcodeScanned} continuousRunning={true} />;
  }
};

export default WorkplaceManagerScreen;
