import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import classnames from 'classnames';
import { connect } from 'react-redux';

/**
 * @file Indicator is a component that shows the save status to user in form of a save progress
 * line beneath the Header.
 * @module Indicator
 * @extends Component
 */
class Indicator extends PureComponent {
  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo We should be using indicator from the state instead of another variable
   */
  render() {
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

/**
 * @method mapStateToProps
 * @summary ToDo: Describe the method
 * @todo Write the documentation
 * @param {object} state
 */
function mapStateToProps(state) {
  const { windowHandler } = state;

  const { indicator } = windowHandler || {
    indicator: '',
  };

  return {
    indicator,
  };
}

/**
 * @typedef {object} Props Component props
 * @prop {string} indicator
 * @prop {bool} [isDocumentNotSaved]
 */
Indicator.propTypes = {
  indicator: PropTypes.string.isRequired,
  isDocumentNotSaved: PropTypes.bool,
};

export default connect(mapStateToProps)(Indicator);
