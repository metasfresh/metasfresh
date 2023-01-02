import PropTypes from 'prop-types';
import counterpart from 'counterpart';
import React, { Component } from 'react';

import logo from '../../assets/images/metasfresh_logo_green_thumb.png';

class PaypalReservationConfirmForm extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const { confirmStatus, message } = this.props;

    let error;
    let messageEffective;
    if (confirmStatus == 'checking') {
      error = false;
      messageEffective = counterpart.translate('paypal.confirm.pending');
    } else if (confirmStatus == 'confirmed') {
      error = false;
      messageEffective = counterpart.translate('paypal.confirm.ok');
    } else if (confirmStatus == 'error') {
      error = true;
      messageEffective = message ? message : 'Invalid token';
    } else {
      error = true;
      messageEffective = 'unknown status: [' + confirmStatus + ']';
    }

    return (
      <div className="paypal-reservation-confirm-form panel panel-spaced-lg panel-shadowed panel-primary">
        <div className="text-center">
          <img src={logo} className="header-logo mt-2 mb-2" />
        </div>
        {!error && <div className="text-center">{messageEffective}</div>}
        {error && <div className="text-center error">{messageEffective}</div>}
      </div>
    );
  }
}

PaypalReservationConfirmForm.propTypes = {
  confirmStatus: PropTypes.string,
  message: PropTypes.string,
};

export default PaypalReservationConfirmForm;
