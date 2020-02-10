import React, { Component } from 'react';
import PropTypes from 'prop-types';

const FLOAT_NUMBER_REGEX_FORMAT = /^-?[0-9]*[.,]?[0-9]*$/;

/*
 * This component was created because we want to accept both dot and comma (regarding which browser and locale
 * is used) for price fields
 */
export default class NumberInput extends Component {
  state = {
    value: '',
    initDone: false,
  };

  static getDerivedStateFromProps(props, state) {
    const { value } = props;

    if (value && !state.initDone) {
      return {
        initDone: true,
        value,
      };
    }
    return null;
  }

  handleChange = (e) => {
    const newValue = e.target.value;

    if (FLOAT_NUMBER_REGEX_FORMAT.test(newValue) || !newValue) {
      this.setState({
        value: e.target.value,
      });
    }
  };

  render() {
    const { value } = this.state;
    return (
      <input
        type="text"
        {...this.props}
        value={value}
        onChange={this.handleChange}
      />
    );
  }
}

NumberInput.propTypes = {
  value: PropTypes.string,
};
