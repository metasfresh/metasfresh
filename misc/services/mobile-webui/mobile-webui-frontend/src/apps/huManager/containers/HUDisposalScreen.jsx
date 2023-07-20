import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { useHistory, useRouteMatch } from 'react-router-dom';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import * as api from '../api';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';

import { HUInfoComponent } from '../components/HUInfoComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import QtyReasonsRadioGroup from '../../../components/QtyReasonsRadioGroup';
import { pushHeaderEntry } from '../../../actions/HeaderActions';

const HUDisposalScreen = () => {
  const [disposalReasons, setDisposalReasons] = useState([]);
  const [selectedDisposalReasonKey, setSelectedDisposalReasonKey] = useState(null);

  const history = useHistory();
  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  const { url } = useRouteMatch();
  const dispatch = useDispatch();
  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    dispatch(pushHeaderEntry({ location: url, caption: trl('huManager.action.dispose.buttonCaption') }));

    api
      .getDisposalReasonsArray()
      .then((disposalReasons) => {
        setDisposalReasons(disposalReasons);
        setSelectedDisposalReasonKey(null);
      })
      .catch((axiosError) => {
        //toastError({ axiosError });
        console.trace('Failed loading disposal reasons', axiosError);
      });
  }, []);

  const onDisposeClick = () => {
    api
      .disposeHU({
        huId: handlingUnitInfo.id,
        reasonCode: selectedDisposalReasonKey,
      })
      .then(() => {
        history.push('/');
      })
      .catch((axiosError) => toastError({ axiosError }));
  };

  if (!handlingUnitInfo) return null;
  return (
    <>
      <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />

      <div className="centered-text pb-5">
        <QtyReasonsRadioGroup reasons={disposalReasons} onReasonSelected={setSelectedDisposalReasonKey} />
      </div>

      <ButtonWithIndicator
        caption={trl('huManager.action.dispose.buttonCaption')}
        disabled={!selectedDisposalReasonKey}
        onClick={onDisposeClick}
      />
    </>
  );
};

export default HUDisposalScreen;
