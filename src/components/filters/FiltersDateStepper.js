import cx from 'classnames';
import Moment from 'moment';
import PropTypes from 'prop-types';
import React, { Component } from 'react';

import { DATE_FORMAT }  from '../../constants/Constants';

const classes = 'btn btn-filter btn-meta-outline-secondary btn-sm btn-active';

export default class FiltersDateStepper extends Component {
    static propTypes = {
        active: PropTypes.object.isRequired,
        applyFilters: PropTypes.func.isRequired,
        filter: PropTypes.object.isRequired,
        next: PropTypes.bool
    };

    static defaultProps = {
        next: false
    };

    handleClick = () => {
        const { active, applyFilters, filter, next } = this.props;

        const activeParameter = active.parameters[0];
        const date = new Date(activeParameter.value);
        date.setDate(date.getDate() + (next ? 1 : -1));

        applyFilters({
            ...filter,
            ...active,
            parameters: [{
                ...filter.parameters[0],
                ...activeParameter,
                value: Moment(date).format(DATE_FORMAT)
            }]
        });
    };

    render() {
        const { next } = this.props;

        return (
            <button
                onClick={this.handleClick}
                className={cx(classes, { ['btn-distance']: next })}
            >
                {next ? '>' : '<'}
            </button>
        );
    }
}
