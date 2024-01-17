import React from 'react';
import { useDispatch } from 'react-redux';
import PropTypes from 'prop-types';
import { handlingUnitLoaded } from '../actions';
import HUButton from '../components/HUButton';

const SelectHUIntermediateList = ({ huList }) => {
  const dispatch = useDispatch();

  return (
    <div className="pt-3 section">
      {huList.map((hu, index) => (
        <HUButton
          key={index}
          handlingUnitInfo={hu}
          onClick={() => dispatch(handlingUnitLoaded({ handlingUnitInfo: hu }))}
        />
      ))}
    </div>
  );
};

SelectHUIntermediateList.propTypes = {
  huList: PropTypes.array,
};

export default SelectHUIntermediateList;
