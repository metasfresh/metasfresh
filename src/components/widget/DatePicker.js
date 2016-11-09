import React, { Component, PropTypes } from 'react';

import Datetime from 'react-datetime';

class DatePicker extends Component {
    constructor(props) {
        super(props);

        this.state = {
            open: undefined
        }
    }

    handleDoubleClick = () => {
        this.setState(Object.assign({}, this.state, {
            open: false
        }));
    }

    handleBlur = () => {
        this.setState(Object.assign({}, this.state, {
            open: true
        }));
    }

    renderDay = (props, currentDate, selectedDate) => {
        return (
            <td
                {...props}
            >
                {currentDate.date()}
            </td>
        );
    }

    render() {
        return (<Datetime
            renderDay={this.renderDay}
            {...this.props}
        />)
    }
}

export default DatePicker
