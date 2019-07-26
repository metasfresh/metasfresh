import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

class PaypalReservationConfirmForm extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { error, errorMessage } = this.props;

    return (
      <div className="paypal-reservation-confirm-form panel panel-spaced-lg panel-shadowed panel-primary">
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        {!error && (
          <div className="text-center">Payment reservation confirmed.</div>
        )}
        {error && <div className="text-center error">{errorMessage}</div>}
      </div>
    );
  }
}

PaypalReservationConfirmForm.propTypes = {
  token: PropTypes.string,
};

export default connect()(PaypalReservationConfirmForm);
