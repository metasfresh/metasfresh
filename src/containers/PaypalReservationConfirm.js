import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';

import PaypalReservationConfirmForm from '../components/app/PaypalReservationConfirmForm';

class PaypalReservationConfirm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      error: false,
      message: 'Checking...',
    };
  }

  approvePaypalReservation = () => {
    const { token } = this.props;
    return axios
      .post(`${config.API_URL}/paypal/approved?token=${token}`)
      .then(() => {
        this.setState({
          error: false,
          message: 'Payment reservation confirmed.',
        });
      });
  };

  componentDidMount() {
    this.approvePaypalReservation().catch(exchange => {
      if (exchange.response.data.status == 404) {
        this.setState({
          error: true,
          message: 'Order not found.',
        });
      } else {
        this.setState({
          error: true,
          message: exchange.response.data.message,
        });
      }
    });
  }

  render() {
    const { error, message } = this.state;
    const component = (
      <PaypalReservationConfirmForm {...{ error, message }} />
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
