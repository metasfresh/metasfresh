import React from 'react';
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

  const { graiCodes, assignedGrais, extraGrais, loading, sending, addGrais, removeGrai, clearAllGrais, sendToBackend } =
    useTargetGrais({ wfProcessId });

  const onSend = () => {
    sendToBackend().then(() => history.goBack());
  };

  return (
    <GraiCapturePanel
      assignedGrais={assignedGrais}
      extraGrais={extraGrais}
      graiCodes={graiCodes}
      loading={loading}
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
        disabled={sending || !graiCodes.length}
        onClick={onSend}
        additionalCssClass="action-button"
      />
    </GraiCapturePanel>
  );
};

export default HUConsolidationGraiScreen;
