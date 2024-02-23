import React, { useState } from 'react';
import PropTypes from 'prop-types';

const QtyReasonsRadioGroup = ({ reasons, selectedReason, disabled, onReasonSelected }) => {
  const [currentSelectedReasonKey, setCurrentSelectedReasonKey] = useState(selectedReason);

  const handleInputChanged = (event) => {
    if (disabled) return;

    const selectedReasonKey = event.target.name;
    setCurrentSelectedReasonKey(selectedReasonKey);
    onReasonSelected(selectedReasonKey);
  };

  return (
    <div className="control">
      {reasons.map((reason, idx) => (
        <div key={idx} className="columns is-mobile">
          <div className="column">
            <label className="radio">
              <input
                className="mr-2"
                type="radio"
                name={reason.key}
                value={reason.key}
                disabled={disabled}
                onChange={handleInputChanged}
                checked={currentSelectedReasonKey === reason.key}
              />
              {reason.caption}
            </label>
          </div>
        </div>
      ))}
    </div>
  );
};

QtyReasonsRadioGroup.propTypes = {
  reasons: PropTypes.array.isRequired,
  selectedReason: PropTypes.string,
  disabled: PropTypes.bool,
  onReasonSelected: PropTypes.func.isRequired,
};

export default QtyReasonsRadioGroup;
