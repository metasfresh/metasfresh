import React from 'react';
import PropTypes from 'prop-types';
import counterpart from 'counterpart';

import ConfirmButton from './confirmButton/ConfirmButton';

const NotFoundButton = (props) => {
  const { disabled, onNotFound } = props;
  const caption = counterpart.translate('activities.confirmButton.notFound');

  return (
    <div className="mt-5">
      <ConfirmButton isCancelMode={true} isUserEditable={!disabled} caption={caption} onUserConfirmed={onNotFound} />
    </div>
  );
};

NotFoundButton.propTypes = {
  onNotFound: PropTypes.func.isRequired,
  disabled: PropTypes.bool,
};

export default NotFoundButton;
