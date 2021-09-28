import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { v4 as uuidv4 } from 'uuid';
import PickStep from '../PickStep';

class PickProductsSteps extends Component {
  render() {
    const { steps } = this.props;
    return (
      <div className="steps-container">
        {steps.length > 0 &&
          steps.map((stepItem) => {
            let uniqueId = uuidv4();
            return <PickStep key={uniqueId} id={uniqueId} {...stepItem} />;
          })}
      </div>
    );
  }
}

PickProductsSteps.propTypes = {
  steps: PropTypes.array.isRequired,
};

export default PickProductsSteps;
