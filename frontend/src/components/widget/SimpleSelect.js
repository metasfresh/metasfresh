import React, { Component } from 'react';
import PropTypes from 'prop-types';
import cx from 'classnames';

import QuickActionsDropdown from '../app/QuickActionsDropdown';

const initialState = {
  isDropdownOpen: false,
};

/**
 * @file Class based component.
 * @module SimpleSelect
 * @extends Component
 */
export default class SimpleSelect extends Component {
  constructor(props) {
    super(props);

    this.state = initialState;
  }

  handleClickOutside = () => {
    this.toggleDropdown();
  };

  handleClick = (option) => {
    const { onSelect } = this.props;

    onSelect(option);
    this.toggleDropdown();
  };

  /**
   * @method toggleDropdown
   * @summary toggles select dropdown
   * @param {*} option
   */
  toggleDropdown = (option) => {
    this.setState({
      isDropdownOpen: option,
    });
  };

  render() {
    const { isDropdownOpen } = this.state;
    const { options, selected, className } = this.props;

    if (options.length) {
      return (
        <div className={cx('js-not-unselect', className)}>
          <div className="quick-actions-wrapper">
            <div
              className={
                'tag tag-success tag-xlg spacer-right ' +
                'quick-actions-tag tag-default pointer'
              }
            >
              {selected.caption}
            </div>
            <div
              className={
                'btn-meta-outline-secondary btn-icon-sm ' +
                'btn-inline btn-icon pointer tooltip-parent ' +
                (isDropdownOpen ? 'btn-disabled ' : '')
              }
              onClick={() => {
                this.toggleDropdown(!isDropdownOpen);
              }}
            >
              <i className="meta-icon-down-1" />
            </div>
            {isDropdownOpen && (
              <QuickActionsDropdown
                actions={options}
                handleClick={this.handleClick}
                handleClickOutside={() => this.toggleDropdown(false)}
                disableOnClickOutside={!isDropdownOpen}
              />
            )}
          </div>
        </div>
      );
    } else {
      return null;
    }
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} onSelect
 * @prop {array} options
 * @prop {string} [className]
 * @prop {object} selected
 */
SimpleSelect.propTypes = {
  options: PropTypes.array,
  selected: PropTypes.object,
  className: PropTypes.string,
  onSelect: PropTypes.func,
};
