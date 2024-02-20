import React from 'react';
import PropTypes from 'prop-types';
import HUButton from '../../../components/huSelector/HUButton';

const SelectHUIntermediateList = ({ huList, onHuSelected }) => {
  return (
    <div className="pt-3 section">
      {huList.map((hu, index) => (
        <HUButton key={index} handlingUnitInfo={hu} onClick={() => onHuSelected(hu)} />
      ))}
    </div>
  );
};

SelectHUIntermediateList.propTypes = {
  huList: PropTypes.array,
  onHuSelected: PropTypes.func.isRequired,
};

export default SelectHUIntermediateList;
