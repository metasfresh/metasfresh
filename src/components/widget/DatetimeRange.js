import Moment from "moment";
import React, { Component } from "react";
import DateRangePicker from "react-bootstrap-daterangepicker";
import counterpart from "counterpart";

class DatetimeRange extends Component {
  constructor(props) {
    super(props);
    this.state = {
      startDate: null,
      endDate: null
    };
  }

  componentDidMount() {
    const { value, valueTo, onChange } = this.props;
    if (value && valueTo) {
      this.setState({
        startDate: Moment(value),
        endDate: Moment(valueTo)
      });
    } else {
      let initStartDate = new Date();
      let initEndDate = new Date();
      initStartDate.setHours(0, 0, 0, 0);
      initEndDate.setHours(23, 59, 0, 0);
      this.setState(
        {
          startDate: initStartDate,
          endDate: initEndDate
        },
        () => {
          onChange(initStartDate, initEndDate);
        }
      );
    }
  }

  handleEvent = (event, picker) => {
    const { onChange } = this.props;

    this.setState(
      {
        startDate: picker.startDate,
        endDate: picker.endDate
      },
      () => {
        onChange(picker.startDate, picker.endDate);
      }
    );
  };

  render() {
    const today = counterpart.translate("window.daterange.today");
    const yesterday = counterpart.translate("window.daterange.yesterday");
    const last7days = counterpart.translate("window.daterange.last7days");
    const last30days = counterpart.translate("window.daterange.last30days");
    const thisMonth = counterpart.translate("window.daterange.thismonth");
    const lastMonth = counterpart.translate("window.daterange.lastmonth");
    const ranges = {
      [today]: [Moment(), Moment()],
      [yesterday]: [Moment().subtract(1, "days"), Moment().subtract(1, "days")],
      [last7days]: [Moment().subtract(6, "days"), Moment()],
      [last30days]: [Moment().subtract(29, "days"), Moment()],
      [thisMonth]: [Moment().startOf("month"), Moment().endOf("month")],
      [lastMonth]: [
        Moment()
          .subtract(1, "month")
          .startOf("month"),
        Moment()
          .subtract(1, "month")
          .endOf("month")
      ]
    };
    const { startDate, endDate } = this.state;
    const { onShow, onHide, mandatory, validStatus, timePicker } = this.props;

    const format = timePicker ? "L LT" : "L";

    return (
      <DateRangePicker
        startDate={startDate}
        endDate={endDate}
        ranges={ranges}
        alwaysShowCalendars={true}
        onApply={this.handleEvent}
        onShow={onShow}
        onHide={onHide}
        locale={{
          firstDay: 1,
          monthNames: Moment.months(),
          daysOfWeek: Moment.weekdaysMin(),
          customRangeLabel: counterpart.translate("window.daterange.custom")
        }}
        autoApply={false}
        timePicker={timePicker}
        timePicker24Hour={true}
      >
        <button
          className={
            "btn btn-block text-xs-left btn-meta-outline-secondary " +
            "btn-distance btn-sm input-icon-container input-primary " +
            (mandatory && !startDate && !endDate ? "input-mandatory " : "") +
            (validStatus && !validStatus.valid ? "input-error " : "")
          }
        >
          {!!startDate && !!endDate
            ? " " +
              Moment(startDate).format(format) +
              " - " +
              Moment(endDate).format(format)
            : " All dates available"}
          <i className="meta-icon-calendar input-icon-right" />
        </button>
      </DateRangePicker>
    );
  }
}

export default DatetimeRange;
