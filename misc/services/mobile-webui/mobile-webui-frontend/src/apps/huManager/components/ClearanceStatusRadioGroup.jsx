import React, { useState } from 'react';
import PropTypes from 'prop-types';

const ClearanceStatusRadioGroup = ({ clearanceStatuses, selectedStatus, onSelectedStatus }) => {
  const [currentSelectedStatus, setCurrentSelectedStatusKey] = useState(selectedStatus);

  const handleInputChanged = (event) => {
    const selectedStatusKey = event.target.name;
    setCurrentSelectedStatusKey(selectedStatusKey);
    onSelectedStatus({ key: selectedStatusKey, caption: event.target.value });
  };

  return (
    <div className="control">
      {clearanceStatuses.map((status, idx) => (
        <div key={idx} className="columns is-mobile">
          <div className="column is-full">
            <label className="radio">
              <input
                className="mr-2"
                type="radio"
                name={status.key}
                value={status.caption}
                onChange={handleInputChanged}
                checked={currentSelectedStatus === status.key}
              />
              {status.caption}
            </label>
          </div>
        </div>
      ))}
    </div>
  );
};

ClearanceStatusRadioGroup.propTypes = {
  clearanceStatuses: PropTypes.array.isRequired,
  selectedStatus: PropTypes.string,
  onSelectedStatus: PropTypes.func.isRequired,
};

export default ClearanceStatusRadioGroup;
