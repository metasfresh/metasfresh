import React, { useState } from 'react';
import PropTypes from 'prop-types';

import { trl } from '../utils/translations';

import QtyReasonsRadioGroup from './QtyReasonsRadioGroup';
import Button from './buttons/Button';

const QtyReasonsView = ({ qtyRejected, uom, qtyRejectedReasons, onHide }) => {
  const [selectedRejectedReason, setSelectedRejectedReason] = useState('');

  return (
    <div className="section pt-3">
      <h5>{`${trl('activities.picking.rejectedPrompt', { qtyRejected, uom })}`}</h5>
      <div className="centered-text is-size-5">
        <QtyReasonsRadioGroup reasons={qtyRejectedReasons} onReasonSelected={setSelectedRejectedReason} />
      </div>
      <Button
        caption={trl('activities.picking.confirmDone')}
        disabled={!selectedRejectedReason}
        onClick={() => onHide(selectedRejectedReason)}
      />
    </div>
  );
};

QtyReasonsView.propTypes = {
  //
  // Properties:
  uom: PropTypes.string.isRequired,
  qtyRejected: PropTypes.number.isRequired,
  qtyRejectedReasons: PropTypes.array.isRequired,
  //
  // Callbacks:
  onHide: PropTypes.func.isRequired,
};

export default QtyReasonsView;
