import React, { Component, PropTypes } from 'react';
import { connect } from 'react-redux';
import onClickOutside from 'react-onclickoutside';

import Datetime from 'react-datetime';

class DatePicker extends Component {
    constructor(props) {
        super(props);

        this.state = {
            open: undefined
        }
    }

    handleFocus = () => {
        this.setState(Object.assign({}, this.state, {
            open: true
        }));
    }

    handleClose = () => {
        this.setState(Object.assign({}, this.state, {
            open: false
        }));
    }

    handleClickOutside = () => {
		this.handleClose();
	}

    renderDay = (props, currentDate) => {
        return (
            <td
                {...props}
                onDoubleClick={this.handleClose}
            >
                {currentDate.date()}
            </td>
        );
    }

    render() {
        const {open} = this.state;
        return (<Datetime
            className={open && "rdtOpen"}
            onFocus={this.handleFocus}
            onBlur={this.handleClose}
            renderDay={this.renderDay}
            {...this.props}
        />)
    }
}

DatePicker = connect()(onClickOutside(DatePicker))

export default DatePicker
