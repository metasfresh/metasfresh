import React, { Component } from 'react';
import PropTypes from 'prop-types';
import classNames from 'classnames';

/**
 * @file Class based component.
 * @module Filters
 * @extends Component
 */
class Tooltips extends Component {
  constructor(props) {
    super(props);

    this.state = {
      opacity: 0,
    };
  }

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method.
   */
  componentDidMount() {
    const { delay } = this.props;
    this.timeout = setTimeout(
      () => {
        this.setState({
          opacity: 1,
        });
      },
      delay ? delay : 1000
    );
  }

  /**
   * @method componentWillUnmount
   * @summary ToDo: Describe the method.
   */
  componentWillUnmount() {
    clearTimeout(this.timeout);
  }

  /**
   * @method render
   * @summary ToDo: Describe the method.
   */
  render() {
    const {
      name,
      action,
      type,
      extraClass,
      tooltipOnFirstlevelPositionLeft,
      className,
    } = this.props;

    const cx = classNames(
      'tooltip-wrapp',
      { [`tooltip-${type}`]: type },
      { [`${extraClass}`]: extraClass },
      { [`${className}`]: className }
    );

    const { opacity } = this.state;
    return (
      <div style={{ opacity: opacity }}>
        <div
          className={cx}
          style={{ left: tooltipOnFirstlevelPositionLeft + 'px' }}
        >
          <div className="tooltip-shortcut">{name}</div>
          <div className="tooltip-name">{action}</div>
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} action
 * @prop {*} className
 * @prop {*} delay
 * @prop {*} extraClass
 * @prop {*} name
 * @prop {*} tooltipOnFirstlevelPositionLeft
 * @prop {*} type
 */
Tooltips.propTypes = {
  action: PropTypes.any,
  className: PropTypes.any,
  delay: PropTypes.any,
  extraClass: PropTypes.any,
  name: PropTypes.any,
  tooltipOnFirstlevelPositionLeft: PropTypes.any,
  type: PropTypes.any,
};

export default Tooltips;
