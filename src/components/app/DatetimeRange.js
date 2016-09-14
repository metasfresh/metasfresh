import React, { Component } from 'react';
import DateRangePicker from 'react-bootstrap-daterangepicker';
import Moment from 'moment';

class DatetimeRange extends Component {
    constructor(props){
        super(props);
        this.state = {
            startDate: null,
            endDate: null
        };
    }
    handleEvent = (event, picker) => {
        this.setState(Object.assign({}, this.state, {
            startDate: picker.startDate,
            endDate: picker.endDate
        }))
    }
    render() {
        const ranges = {
			'Today': [Moment(), Moment()],
			'Yesterday': [Moment().subtract(1, 'days'), Moment().subtract(1, 'days')],
			'Last 7 Days': [Moment().subtract(6, 'days'), Moment()],
			'Last 30 Days': [Moment().subtract(29, 'days'), Moment()],
			'This Month': [Moment().startOf('month'), Moment().endOf('month')],
			'Last Month': [Moment().subtract(1, 'month').startOf('month'), Moment().subtract(1, 'month').endOf('month')]
		}
        const {startDate, endDate} = this.state;
        return (
            <DateRangePicker
                startDate={Moment(new Date('1/1/2014'))}
                endDate={Moment(new Date('3/1/2014'))}
                ranges={ranges}
                alwaysShowCalendars={true}
                onApply={this.handleEvent}
            >
                <button className="btn btn-meta-outline-secondary btn-distance btn-sm">
                    <i className="meta-icon-calendar" />
                        {!!startDate && !!endDate ?
                            Moment(startDate).format('L') + " - " + Moment(endDate).format('L') :
                            "All dates available"
                        }
                </button>
            </DateRangePicker>
        )
    }
}

export default DatetimeRange
