import React, { Component, PropTypes } from 'react';

import Datetime from 'react-datetime';

class DatePicker extends Component {
    constructor(props) {
        super(props);

        this.state = {
            open: null
        }
    }

    handleDoubleClick = (e) => {
        e.preventDefault();

        // this.setState(Object.assign({}, this.state, {
        //     open: false
        // }), () => {
        //     this.setState(Object.assign({}, this.state, {
        //         open: null
        //     }));
        // })
    }

    renderDay = (props, currentDate, selectedDate) => {
        return (
            <td
                {...props}
                onDoubleClick={this.handleDoubleClick}
            >
                {currentDate.date()}
            </td>
        );
    }

    render() {
        const {open} = this.state;

        return (<Datetime
            renderDay={this.renderDay}
            {...this.props}
        />)
    }
}

export default DatePicker
