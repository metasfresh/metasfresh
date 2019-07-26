import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import axios from 'axios';

import PaypalReservationConfirmForm from '../components/app/PaypalReservationConfirmForm';

class PaypalReservationConfirm extends Component {
  constructor(props) {
    super(props);

    this.state = {
      error: true,
      errorMessage: 'no token',
    };
  }

  approvePaypalReservation = () => {
    const { token } = this.props;
    return axios
      .post(`${config.API_URL}/paypal/approved?token=${token}`)
      .then(() => {
        this.setState({
          error: false,
          errorMessage: null,
        });
      });
  };

  componentDidMount() {
    this.approvePaypalReservation().catch(exchange => {
      if (exchange.response.data.status == 404) {
        this.setState({
          error: true,
          errorMessage: 'Order not found',
        });
      } else {
        this.setState({
          error: true,
          errorMessage: exchange.response.data.message,
        });
      }
    });
  }

  render() {
    const { error, errorMessage } = this.state;
    const component = (
      <PaypalReservationConfirmForm {...{ error, errorMessage }} />
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
