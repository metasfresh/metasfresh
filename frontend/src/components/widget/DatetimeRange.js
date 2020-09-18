import Moment from 'moment';
import React, { Component } from 'react';
import DateRangePicker from 'react-bootstrap-daterangepicker';
import counterpart from 'counterpart';
import classnames from 'classnames';
import PropTypes from 'prop-types';

/**
 * @file Class based component.
 * @module DateTimeRange
 * @extends Component
 */
class DatetimeRange extends Component {
  constructor(props) {
    super(props);
    this.state = {
      startDate: undefined,
      endDate: undefined,
    };
  }

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentDidMount() {
    const { value, valueTo } = this.props;
    if (value && valueTo) {
      this.setState({
        startDate: Moment(value),
        endDate: Moment(valueTo),
      });
    }
  }

  /**
   * @method handleClear
   * @summary Clears the DatatimeRange input by updating the startDate and endDate into the local state
   */
  handleClear = () => {
    const { onChange } = this.props;

    this.setState(
      {
        startDate: undefined,
        endDate: undefined,
      },
      () => {
        onChange(undefined, undefined);
        // even if we reset the values we get autofocus on the input and calendar shown
        // we need to use this workaround below to cancel programatically such that we hide the calendars automatically
        const rangeInputEl = document.querySelector('div.range_inputs');
        const rangeInputButtons = rangeInputEl.querySelectorAll('button');
        const cancelBtn = rangeInputButtons ? rangeInputButtons[1] : null;
        cancelBtn && cancelBtn.click();
      }
    );
  };

  /**
   * @method handleApply
   * @summary ToDo: Describe the method
   * @param {object} event
   * @param {*} picker
   * @todo Write the documentation
   */
  handleApply = (event, picker) => {
    const { onChange } = this.props;

    this.setState(
      {
        startDate: picker.startDate.utc(true),
        endDate: picker.endDate.utc(true),
      },
      () => {
        onChange(picker.startDate, picker.endDate);
      }
    );
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    const today = counterpart.translate('window.daterange.today');
    const yesterday = counterpart.translate('window.daterange.yesterday');
    const last7days = counterpart.translate('window.daterange.last7days');
    const last30days = counterpart.translate('window.daterange.last30days');
    const thisMonth = counterpart.translate('window.daterange.thismonth');
    const lastMonth = counterpart.translate('window.daterange.lastmonth');
    const ranges = {
      [today]: [Moment(), Moment()],
      [yesterday]: [Moment().subtract(1, 'days'), Moment().subtract(1, 'days')],
      [last7days]: [Moment().subtract(6, 'days'), Moment()],
      [last30days]: [Moment().subtract(29, 'days'), Moment()],
      [thisMonth]: [Moment().startOf('month'), Moment().endOf('month')],
      [lastMonth]: [
        Moment()
          .subtract(1, 'month')
          .startOf('month'),
        Moment()
          .subtract(1, 'month')
          .endOf('month'),
      ],
    };
    const { startDate, endDate } = this.state;
    const { onShow, onHide, mandatory, validStatus, timePicker } = this.props;
    const format = timePicker ? 'LT' : 'l';

    let availableDates = counterpart.translate('window.daterange.filter.hint');

    if (startDate && endDate) {
      // Expliclitly setting locale here, since startDate and endDate could already be a (wrongly) localized Moment object
      const locale = Moment.locale();

      const startDateLocalized = Moment(startDate);
      startDateLocalized.locale(locale);
      const startDateFormatted = startDateLocalized.format(format);

      const endDateLocalized = Moment(endDate);
      endDateLocalized.locale(locale);
      const endDateFormatted = endDateLocalized.format(format);

      availableDates = `${startDateFormatted} - ${endDateFormatted}`;
    }

    return (
      <DateRangePicker
        startDate={startDate}
        endDate={endDate}
        ranges={ranges}
        alwaysShowCalendars={true}
        onApply={this.handleApply}
        onShow={onShow}
        onHide={onHide}
        onEvent={this.handleFocus}
        locale={{
          format: Moment.localeData().longDateFormat(format),
          firstDay: 1,
          monthNames: Moment.months(),
          applyLabel: counterpart.translate('window.daterange.apply'),
          cancelLabel: counterpart.translate('window.daterange.cancel'),
          customRangeLabel: counterpart.translate('window.daterange.custom'),
        }}
        autoApply={false}
        timePicker={timePicker}
        timePicker24Hour={true}
        linkedCalendars={false}
        autoUpdateInput={false}
      >
        <button
          className={classnames(
            'btn',
            'btn-block',
            'text-left',
            'btn-meta-outline-secondary',
            'btn-distance',
            'btn-sm input-icon-container',
            'input-primary',
            {
              'input-mandatory': mandatory && !startDate && !endDate,
              'input-error': validStatus && !validStatus.valid,
            }
          )}
        >
          {availableDates}
          {startDate && (
            <i
              className="meta-icon-close-alt input-icon-right"
              onClick={this.handleClear}
            />
          )}
          <i
            className={classnames('meta-icon-calendar', 'input-icon-right', {
              'input-icon-m-right input-icon-m-top-small': startDate && endDate,
            })}
          />
        </button>
      </DateRangePicker>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {*} value
 * @prop {*} valueTo
 * @prop {*} onChange
 * @prop {*} onHide
 * @prop {*} onShow
 * @prop {*} mandatory
 * @prop {*} validStatus
 * @prop {*} timePicker
 * @todo Check props. Which proptype? Required or optional?
 */
DatetimeRange.propTypes = {
  value: PropTypes.any,
  valueTo: PropTypes.any,
  onChange: PropTypes.any,
  onHide: PropTypes.any,
  onShow: PropTypes.any,
  mandatory: PropTypes.any,
  validStatus: PropTypes.any,
  timePicker: PropTypes.any,
};

export default DatetimeRange;
