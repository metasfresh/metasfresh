import cx from 'classnames';
import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { Component } from 'react';

import { DATE_FORMAT } from '../../constants/Constants';

const classes =
  'btn btn-filter btn-meta-outline-secondary btn-sm btn-empty btn-active';

export default class FiltersDateStepper extends Component {
  static propTypes = {
    active: PropTypes.object.isRequired,
    applyFilters: PropTypes.func.isRequired,
    filter: PropTypes.object.isRequired,
    next: PropTypes.bool,
  };

  static defaultProps = {
    next: false,
  };

  handleClick = () => {
    const { active, applyFilters, filter, next } = this.props;

    const step = next ? 1 : -1;
    const activeParameter = active.parameters[0];
    const date = new Date(activeParameter.value);
    date.setDate(date.getDate() + step);

    const parameter = {
      ...filter.parameters[0],
      ...activeParameter,
      value: Moment(date).format(DATE_FORMAT),
    };

    const valueTo = activeParameter.valueTo;
    if (valueTo) {
      const dateTo = new Date(valueTo);
      dateTo.setDate(dateTo.getDate() + step);
      parameter.valueTo = Moment(dateTo).format(DATE_FORMAT);
    }

    applyFilters({
      ...filter,
      ...active,
      parameters: [parameter],
    });
  };

  render() {
    const { next } = this.props;

    return (
      <button
        onClick={this.handleClick}
        className={cx(classes, { ['btn-distance']: next })}
      >
        <i className={next ? 'meta-icon-right' : 'meta-icon-left'} />
      </button>
    );
  }
}
