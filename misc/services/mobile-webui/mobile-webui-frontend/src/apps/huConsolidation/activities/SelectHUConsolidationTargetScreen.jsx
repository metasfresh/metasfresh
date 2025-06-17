import React from 'react';
import { useScreenDefinition } from '../../../hooks/useScreenDefinition';
import { huConsolidationJobLocation } from '../routes';
import ButtonWithIndicator from '../../../components/buttons/ButtonWithIndicator';
import { useMobileNavigation } from '../../../hooks/useMobileNavigation';
import Spinner from '../../../components/Spinner';
import PropTypes from 'prop-types';
import { useAvailableTargets } from '../actions/useAvailableTargets';
import { useCurrentTarget } from '../actions/useCurrentTarget';

export const SelectHUConsolidationTargetScreen = () => {
  const { history, wfProcessId, activityId } = useScreenDefinition({
    screenId: 'SelectHUConsolidationTargetScreen',
    captionKey: 'huConsolidation.SelectHUConsolidationTargetScreen.caption',
    back: huConsolidationJobLocation,
  });

  const { currentTarget, isProcessing, closeTarget, printLabel } = useCurrentTarget({ wfProcessId, activityId });

  const onCloseTargetClicked = () => {
    closeTarget({ wfProcessId }).then(() => history.goBack());
  };

  return (
    <div className="section pt-2">
      {currentTarget && (
        <CurrentTarget disabled={isProcessing} onPrintLabel={printLabel} onCloseTarget={onCloseTargetClicked} />
      )}
      {!currentTarget && <NewTargets disabled={isProcessing} wfProcessId={wfProcessId} />}
    </div>
  );
};

//
//
//--------------------------------------------------------------------------
//
//

const NewTargets = ({ disabled, wfProcessId }) => {
  const history = useMobileNavigation();

  const { isTargetsLoading, targets, setTarget } = useAvailableTargets({ wfProcessId });

  const onSelectTargetClicked = async (target) => {
    setTarget({ target }).then(() => history.goBack()); // go back to the Job screen
  };

  return (
    <>
      {isTargetsLoading && <Spinner />}
      {targets?.map((target, index) => {
        return (
          <ButtonWithIndicator
            key={index}
            caption={target.caption}
            disabled={disabled}
            onClick={() => onSelectTargetClicked(target)}
            additionalCssClass={target.default ? 'green-border-button' : undefined}
          />
        );
      })}
    </>
  );
};
NewTargets.propTypes = {
  wfProcessId: PropTypes.string.isRequired,
  disabled: PropTypes.bool,
};

//
//
//--------------------------------------------------------------------------
//
//

const CurrentTarget = ({ disabled, onPrintLabel, onCloseTarget }) => {
  return (
    <>
      {onPrintLabel && (
        <ButtonWithIndicator
          captionKey="huConsolidation.SelectHUConsolidationTargetScreen.printLabel"
          disabled={disabled}
          onClick={onPrintLabel}
        />
      )}
      <ButtonWithIndicator
        captionKey="huConsolidation.SelectHUConsolidationTargetScreen.closeTarget"
        disabled={disabled}
        onClick={onCloseTarget}
      />
    </>
  );
};
CurrentTarget.propTypes = {
  disabled: PropTypes.bool,
  onPrintLabel: PropTypes.func,
  onCloseTarget: PropTypes.func.isRequired,
};
