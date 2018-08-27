import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';

class Indicator extends Component {
  constructor(props) {
    super(props);
  }

  renderIndicator = state => {
    switch (state) {
      case 'saved':
        return 'indicator-success';
      case 'pending':
        return 'indicator-pending';
      case 'error':
        return 'indicator-error';
    }
  };

  render() {
    // TODO: We should be using indicator from the state instead of another variable
    const { indicator, isDocumentNotSaved } = this.props;
    return (
      <div>
        <div
          className={
            'indicator-bar ' +
            (isDocumentNotSaved ? 'indicator-error ' : 'indicator-' + indicator)
          }
        />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const { windowHandler } = state;

  const { indicator } = windowHandler || {
    indicator: '',
  };

  return {
    indicator,
  };
}

Indicator.propTypes = {
  indicator: PropTypes.string.isRequired,
};

export default connect(mapStateToProps)(Indicator);
