import React, { useEffect, useState } from 'react';
import { useSelector } from 'react-redux';

import { trl } from '../../../utils/translations';
import { toastError } from '../../../utils/toast';
import * as api from '../api';
import { getHandlingUnitInfoFromGlobalState } from '../reducers';

import { HUInfoComponent } from '../components/HUInfoComponent';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import QtyReasonsRadioGroup from '../../../components/QtyReasonsRadioGroup';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';

const HUDisposalScreen = () => {
  const { history } = useScreenDefinition({
    captionKey: 'huManager.action.dispose.buttonCaption',
    back: huManagerLocation,
  });
  const [disposalReasons, setDisposalReasons] = useState([]);
  const [selectedDisposalReasonKey, setSelectedDisposalReasonKey] = useState(null);

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
      return;
    }

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
        history.goHome();
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
