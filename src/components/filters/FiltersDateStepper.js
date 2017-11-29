import cx from 'classnames';
import PropTypes from 'prop-types';
import React, { Component } from 'react';

const classes = 'btn btn-filter btn-meta-outline-secondary btn-sm btn-active';

export default class FiltersDateStepper extends Component {
    static propTypes = {
        next: PropTypes.bool
    };

    static defaultProps = {
        next: false
    };

    handleClick = () => {

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
