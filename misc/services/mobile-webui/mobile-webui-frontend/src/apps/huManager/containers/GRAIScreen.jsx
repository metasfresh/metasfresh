import React, { useEffect } from 'react';
import { useSelector } from 'react-redux';

import { getHandlingUnitInfoFromGlobalState } from '../reducers';

import { HUInfoComponent } from '../components/HUInfoComponent';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huManagerLocation } from '../routes';
import GraiCapturePanel from '../../../components/GraiCapturePanel';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useGrais } from '../hooks/useGrais';

const GRAIScreen = () => {
  const { history } = useScreenDefinition({
    screenId: 'GRAIScreen',
    captionKey: 'huManager.action.scanGRAI.windowName',
    back: huManagerLocation,
  });

  const handlingUnitInfo = useSelector((state) => getHandlingUnitInfoFromGlobalState(state));
  const {
    graiCodes,
    assignedGrais,
    extraGrais,
    tuCount,
    loading,
    dirty,
    sending,
    addGrais,
    removeGrai,
    clearAllGrais,
    sendToBackend,
    loadFromBackend,
  } = useGrais({ huId: handlingUnitInfo?.id });

  useEffect(() => {
    if (!handlingUnitInfo) {
      history.goBack();
    }
  }, []);

  if (!handlingUnitInfo) return null;

  return (
    <GraiCapturePanel
      graiCodes={graiCodes}
      assignedGrais={assignedGrais}
      extraGrais={extraGrais}
      expectedCount={tuCount}
      loading={loading}
      countKey="huManager.action.scanGRAI.count"
      countExtraKey="huManager.action.scanGRAI.countExtra"
      clearAllButtonKey="huManager.action.scanGRAI.clearAll.buttonCaption"
      clearAllConfirmKey="huManager.action.scanGRAI.clearAll.confirmQuestion"
      onAddGrais={addGrais}
      onRemoveGrai={removeGrai}
      onClearAll={clearAllGrais}
      header={<HUInfoComponent handlingUnitInfo={handlingUnitInfo} />}
    >
      <ButtonWithIndicator
        captionKey="huManager.action.scanGRAI.send.buttonCaption"
        testId="grai-send-button"
        disabled={!dirty || sending || extraGrais.length > 0}
        onClick={sendToBackend}
        additionalCssClass="action-button"
      />
      <ButtonWithIndicator
        captionKey="huManager.action.scanGRAI.undo.buttonCaption"
        testId="grai-undo-button"
        disabled={!dirty}
        onClick={loadFromBackend}
      />
    </GraiCapturePanel>
  );
};

export default GRAIScreen;
