import React from 'react';
import PropTypes from 'prop-types';
import HUButton from '../../../components/huSelector/HUButton';

const SelectHUIntermediateList = ({ huList, disabled, onHuSelected }) => {
  return (
    <div className="pt-3 section">
      {huList?.map((hu, index) => (
        <HUButton key={index} handlingUnitInfo={hu} disabled={disabled} onClick={() => onHuSelected(hu)} />
      ))}
    </div>
  );
};

SelectHUIntermediateList.propTypes = {
  huList: PropTypes.array,
  disabled: PropTypes.bool,
  onHuSelected: PropTypes.func.isRequired,
};

export default SelectHUIntermediateList;
