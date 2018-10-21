import PropTypes from 'prop-types';
import React, { Component } from 'react';
import classnames from 'classnames';
import { connect } from 'react-redux';

class Indicator extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    // TODO: We should be using indicator from the state instead of another variable
    const { indicator, isDocumentNotSaved } = this.props;
    return (
      <div>
        <div
          className={classnames('indicator-bar', {
            'indicator-error': isDocumentNotSaved,
            [`indicator-${indicator}`]: !isDocumentNotSaved,
          })}
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
