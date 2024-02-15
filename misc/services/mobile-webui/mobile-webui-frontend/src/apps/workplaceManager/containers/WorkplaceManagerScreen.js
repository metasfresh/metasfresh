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
import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { getWorkplaceInfoFromGlobalState } from '../reducers';
import WorkplaceInfoComponent from '../components/WorkplaceInfoComponent';
import { useRouteMatch } from 'react-router-dom';
import { pushHeaderEntry } from '../../../actions/HeaderActions';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { trl } from '../../../utils/translations';
import * as api from '../api';
import { setWorkplace } from '../actions';
import { push } from 'connected-react-router';
import { scanAnythingLocation } from '../../scanAnything/routes';

const WorkplaceManagerScreen = () => {
  const { url } = useRouteMatch();
  const dispatch = useDispatch();
  const workplaceInfo = useSelector((state) => getWorkplaceInfoFromGlobalState(state));
  if (!workplaceInfo) {
    dispatch(push(scanAnythingLocation()));
  }

  useEffect(() => {
    // IMPORTANT, else it won't restore the title when we move back to this screen
    dispatch(pushHeaderEntry({ caption: trl('workplaceManager.title'), location: url }));
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
    api.assignWorkplace(workplaceInfo.id).then((workplaceInfo) => dispatch(setWorkplace({ workplaceInfo })));
  };

  const onScanAgainClick = () => {
    dispatch(push(scanAnythingLocation()));
  };

  return (
    <>
      <WorkplaceInfoComponent workplaceInfo={workplaceInfo} />
      <div className="pt-3 section">
        {!workplaceInfo.userAssigned && (
          <ButtonWithIndicator caption={trl('workplaceManager.action.assign.buttonCaption')} onClick={onAssignClick} />
        )}
        <ButtonWithIndicator
          caption={trl('workplaceManager.action.scanAgain.buttonCaption')}
          onClick={onScanAgainClick}
        />
      </div>
    </>
  );
};

export default WorkplaceManagerScreen;
