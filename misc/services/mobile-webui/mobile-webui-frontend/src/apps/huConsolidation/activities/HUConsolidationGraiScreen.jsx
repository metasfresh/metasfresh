import React, { useEffect } from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huConsolidationJobLocation } from '../routes';
import GraiCapturePanel from '../../../components/GraiCapturePanel';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useTargetGrais } from '../actions/useTargetGrais';

export const HUConsolidationGraiScreen = () => {
  const { history, wfProcessId } = useScreenDefinition({
    screenId: 'HUConsolidationGraiScreen',
    captionKey: 'huConsolidation.GraiScreen.caption',
    back: huConsolidationJobLocation,
  });

  const {
    graiCodes,
    assignedGrais,
    extraGrais,
    tuCount,
    isLoading,
    isSending,
    addGrais,
    removeGrai,
    clearAllGrais,
    sendToBackend,
    loadFromBackend,
  } = useTargetGrais({ wfProcessId });

  useEffect(() => {
    loadFromBackend();
  }, [loadFromBackend]);

  const onSend = () => {
    sendToBackend().then(() => history.goBack());
  };

  return (
    <GraiCapturePanel
      assignedGrais={assignedGrais}
      extraGrais={extraGrais}
      graiCodes={graiCodes}
      expectedCount={tuCount}
      loading={isLoading}
      countKey="huConsolidation.GraiScreen.count"
      countExtraKey="huConsolidation.GraiScreen.countExtra"
      clearAllButtonKey="huConsolidation.GraiScreen.clearAll"
      clearAllConfirmKey="huConsolidation.GraiScreen.clearAllConfirm"
      onAddGrais={addGrais}
      onRemoveGrai={removeGrai}
      onClearAll={clearAllGrais}
    >
      <ButtonWithIndicator
        captionKey="huConsolidation.GraiScreen.send"
        testId="grai-send-button"
        disabled={isSending || !graiCodes.length}
        onClick={onSend}
        additionalCssClass="action-button"
      />
    </GraiCapturePanel>
  );
};

export default HUConsolidationGraiScreen;
