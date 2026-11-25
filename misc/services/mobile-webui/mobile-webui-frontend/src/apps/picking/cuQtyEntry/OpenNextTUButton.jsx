import React from 'react';
import PropTypes from 'prop-types';

import DialogButton from '../../../components/dialogs/DialogButton';

/** Button "Next TU" (picking, lines with a CU quantity entry mode that books into TUs) */
const OpenNextTUButton = ({ isProcessing = false, onNextTU }) => (
  <DialogButton
    captionKey="picking.nextTU"
    testId="next-tu-button"
    className="is-link"
    type="button"
    disabled={isProcessing}
    onClick={onNextTU}
  />
);

OpenNextTUButton.propTypes = {
  isProcessing: PropTypes.bool,
  onNextTU: PropTypes.func.isRequired,
};

export default OpenNextTUButton;
