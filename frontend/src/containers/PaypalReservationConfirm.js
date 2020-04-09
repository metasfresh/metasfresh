import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';

import PaypalReservationConfirmForm from '../components/app/PaypalReservationConfirmForm';

class PaypalReservationConfirm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      confirmStatus: 'checking',
      message: null,
    };
  }

  approvePaypalReservation = () => {
    const { token } = this.props;
    return axios
      .post(`${config.API_URL}/paypal/approved?token=${token}`)
      .then(() => {
        this.setState({
          confirmStatus: 'confirmed',
          message: null,
        });
      });
  };

  componentDidMount() {
    this.approvePaypalReservation().catch((exchange) => {
      if (exchange.response.data.status == 404) {
        this.setState({
          confirmStatus: 'error',
          message: 'Order not found.',
        });
      } else {
        this.setState({
          confirmStatus: 'error',
          message: exchange.response.data.message,
        });
      }
    });
  }

  render() {
    const { confirmStatus, message } = this.state;
    const component = (
      <PaypalReservationConfirmForm {...{ confirmStatus, message }} />
    );

    return (
      <div className="fullscreen">
        <div className="paypal-reservation-container">{component}</div>
      </div>
    );
  }
}

PaypalReservationConfirm.propTypes = {
  token: PropTypes.string,
};

export default connect()(PaypalReservationConfirm);
