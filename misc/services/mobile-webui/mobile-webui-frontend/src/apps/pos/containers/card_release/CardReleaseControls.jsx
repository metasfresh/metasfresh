import React from 'react';
import PropTypes from 'prop-types';
import './CardReleaseControls.scss';
import { trl } from '../../../../utils/translations';

const _ = (key) => trl(`pos.cardRelease.${key}`);

/**
 * The cashier's check / release of a card payment whose result is unknown (see {@link useCardRelease}): the "SumUp not
 * reachable" note, a refusal, and either the "not charged" confirmation or the release button (enabled from the
 * payment's {@code releasableAt}). Shared by the till's card sale and the invoice settlement's card payment.
 *
 * @param testIdPrefix the prefix of every {@code data-testid}, e.g. {@code pos-card-settlement}
 */
const CardReleaseControls = ({
  testIdPrefix,
  isCheckFailed,
  releaseMessage,
  isReleaseShown,
  isReleaseEnabled,
  onRelease,
  isConfirmationShown,
  onConfirmRelease,
  onCancelConfirmation,
  isDisabled,
}) => {
  if (!isCheckFailed && !releaseMessage && !isConfirmationShown && !isReleaseShown) {
    return null;
  }

  return (
    <div className="pos-card-release-controls">
      {isCheckFailed && (
        <div className="status-note" data-testid={`${testIdPrefix}-check-failed-message`}>
          {_('checkFailed')}
        </div>
      )}
      {releaseMessage && (
        <div className="status-note" data-testid={`${testIdPrefix}-release-message`}>
          {releaseMessage}
        </div>
      )}
      {isConfirmationShown ? (
        <div className="release-confirmation" data-testid={`${testIdPrefix}-release-confirmation`}>
          <div className="release-confirmation-text" data-testid={`${testIdPrefix}-release-confirmation-text`}>
            {_('releaseConfirmation')}
          </div>
          <div className="buttons">
            <button
              className="button is-medium is-danger"
              data-testid={`${testIdPrefix}-release-confirm-button`}
              disabled={isDisabled}
              onClick={onConfirmRelease}
            >
              {_('actions.confirmRelease')}
            </button>
            <button
              className="button is-medium"
              data-testid={`${testIdPrefix}-release-cancel-button`}
              disabled={isDisabled}
              onClick={onCancelConfirmation}
            >
              {_('actions.cancelRelease')}
            </button>
          </div>
        </div>
      ) : (
        isReleaseShown && (
          <button
            className="button is-medium is-fullwidth release-button"
            data-testid={`${testIdPrefix}-release-button`}
            disabled={isDisabled || !isReleaseEnabled}
            onClick={onRelease}
          >
            {_('actions.release')}
          </button>
        )
      )}
    </div>
  );
};

CardReleaseControls.propTypes = {
  testIdPrefix: PropTypes.string.isRequired,
  isCheckFailed: PropTypes.bool,
  releaseMessage: PropTypes.string,
  isReleaseShown: PropTypes.bool,
  isReleaseEnabled: PropTypes.bool,
  onRelease: PropTypes.func.isRequired,
  isConfirmationShown: PropTypes.bool,
  onConfirmRelease: PropTypes.func.isRequired,
  onCancelConfirmation: PropTypes.func.isRequired,
  isDisabled: PropTypes.bool,
};

export default CardReleaseControls;
