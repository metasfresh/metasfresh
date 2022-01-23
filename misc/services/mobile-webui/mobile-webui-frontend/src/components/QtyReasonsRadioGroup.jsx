import React, { useState } from 'react';
import PropTypes from 'prop-types';

const QtyReasonsRadioGroup = ({ reasons, onReasonSelected }) => {
  const [selectedReasonKey, setSelectedReasonKey] = useState(null);

  const handleInputChanged = (event) => {
    const selectedReasonKey = event.target.name;
    setSelectedReasonKey(selectedReasonKey);
    onReasonSelected(selectedReasonKey);
  };

  return (
    <div className="control">
      {reasons.map((reason, idx) => (
        <div key={idx} className="columns is-mobile">
          <div className="column is-full">
            <label className="radio">
              <input
                className="mr-2"
                type="radio"
                name={reason.key}
                value={reason.key}
                onChange={handleInputChanged}
                checked={selectedReasonKey === reason.key}
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
  onReasonSelected: PropTypes.func.isRequired,
};

export default QtyReasonsRadioGroup;
