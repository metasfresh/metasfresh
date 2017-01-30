import React, { Component } from 'react';
import DateRangePicker from 'react-bootstrap-daterangepicker';
import Moment from 'moment';

class DatetimeRange extends Component {
    constructor(props) {
        super(props);
        this.state = {
            startDate: null,
            endDate: null
        };
    }

    componentDidMount() {
        const {value, valueTo, onChange} = this.props;
        if(value && valueTo){
            this.setState(Object.assign({}, this.state, {
                startDate: Moment(value),
                endDate: Moment(valueTo)
            }));
        }else{
            const initDate = new Date();
            this.setState(Object.assign({}, this.state, {
                startDate: initDate,
                endDate: initDate
            }), () => {
                onChange(initDate, initDate)
            });
        }
    }

    handleEvent = (event, picker) => {
        const {onChange} = this.props;

        this.setState(Object.assign({}, this.state, {
            startDate: picker.startDate,
            endDate: picker.endDate
        }), () => {
            onChange(picker.startDate, picker.endDate);
        });
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
        const {isShown, isHidden, mandatory} = this.props;

        return (
            <DateRangePicker
                startDate={startDate}
                endDate={endDate}
                ranges={ranges}
                alwaysShowCalendars={true}
                onApply={this.handleEvent}
                onShow={isShown}
                onHide={isHidden}
                locale={{
                    "firstDay": 1,
                    "monthNames": Moment.months()
                }}
                autoApply={false}
            >
                <button className={
                    "btn btn-block text-xs-left btn-meta-outline-secondary " +
                    "btn-distance btn-sm input-icon-container input-primary" +
                    (mandatory && !startDate && !endDate ? " input-mandatory " : "")
                }>
                    {!!startDate && !!endDate ?
                        " " + Moment(startDate).format('L') + " - " + Moment(endDate).format('L') :
                        " All dates available"
                    }
                    <i className="meta-icon-calendar input-icon-right"/>
                </button>

            </DateRangePicker>
        )
    }
}

export default DatetimeRange
