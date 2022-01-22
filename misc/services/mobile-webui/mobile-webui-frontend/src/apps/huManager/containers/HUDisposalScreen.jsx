import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';
import { useHistory } from 'react-router-dom';
import counterpart from 'counterpart';

import { toastError } from '../../../utils/toast';
import { disposeHU, getDisposalReasonsArray } from '../api';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';

import { HUInfoComponent } from '../components/HUInfoComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import QtyReasonsRadioGroup from '../../../components/QtyReasonsRadioGroup';

const HUDisposalScreen = () => {
  const [disposalReasons, setDisposalReasons] = useState([]);
  const [selectedDisposalReasonKey, setSelectedDisposalReasonKey] = useState(null);

  const history = useHistory();
  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

    getDisposalReasonsArray()
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
    disposeHU({
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
      <div className="pt-3 section">
        <HUInfoComponent handlingUnitInfo={handlingUnitInfo} />

        <div className="centered-text pb-5">
          <QtyReasonsRadioGroup reasons={disposalReasons} onReasonSelected={setSelectedDisposalReasonKey} />
        </div>

        <ButtonWithIndicator
          caption={counterpart.translate('huManager.action.dispose.buttonCaption')}
          disabled={!selectedDisposalReasonKey}
          onClick={onDisposeClick}
        />
      </div>
    </>
  );
};

export default HUDisposalScreen;
