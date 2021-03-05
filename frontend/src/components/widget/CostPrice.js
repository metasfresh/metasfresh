import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

export default class CostPrice extends PureComponent {

  render() {
    
    console.log(this.props);

    return (
      <div>
        <input {...this.props} type="text" pattern="^[0-9.,']*$" />
      </div>
    );
  }
}

CostPrice.propTypes = {
};
