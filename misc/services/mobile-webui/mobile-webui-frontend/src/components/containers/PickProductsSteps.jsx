import React, { Component } from 'react';
import PropTypes from 'prop-types';

class PickProductsSteps extends Component {
  render() {
    console.log('P:', this.props);
    return <div>Steps</div>;
  }
}

PickProductsSteps.propTypes = {
  steps: PropTypes.array.isRequired,
};

export default PickProductsSteps;
