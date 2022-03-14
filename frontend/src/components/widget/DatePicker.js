import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import MomentTZ from 'moment-timezone';
import onClickOutside from 'react-onclickoutside';

import { addNotification } from '../../actions/AppActions';
import {
  allowOutsideClick,
  disableOutsideClick,
} from '../../actions/WindowActions';

import TetheredDateTime from './TetheredDateTime';
import Moment from 'moment-timezone';
import { getCurrentActiveLocale } from '../../utils/locale';
import {
  DATE_FORMAT,
  DATE_TIMEZONE_FORMAT,
  TIME_FORMAT,
} from '../../constants/Constants';

class DatePicker extends Component {
  debouncedFn = null;

  constructor(props) {
    super(props);
    this.state = {
      isCalendarOpen: false,
      cache: null,
    };
  }

  componentDidMount() {
    const {
      handleBackdropLock,
      isOpenDatePicker,
      isFilterActive,
      defaultValue,
      field,
      updateItems,
    } = this.props;

    handleBackdropLock && handleBackdropLock(true);

    if (!isFilterActive && defaultValue) {
      updateItems &&
        updateItems({
          widgetField: field,
          value: defaultValue,
        });
    }

    if (isOpenDatePicker) {
      setTimeout(() => this.picker.openCalendar(), 100);
    }
  }

  /**
   * @method setDebounced
   * @summary store a handle to the debounced click handler function so that it can
   * be cancelled in case there's a double click
   * @param {function} debounced
   */
  setDebounced = (debounced) => {
    this.debouncedFn = debounced;
  };

  handleBlur = (dateObj) => {
    this.debouncedFn && this.debouncedFn.cancel && this.debouncedFn.cancel();

    //
    // Get the most recent valid date
    const { cache } = this.state;
    let date = this.convertToMoment(dateObj, false);
    if (!date || !date.isValid()) {
      date = this.convertToMoment(cache, false);
    }
    if (!date || !date.isValid()) {
      date = this.convertToMoment(this.props.value, false);
    }
    if (!date || !date.isValid()) {
      date = null;
    }

    //
    // Update picker's selectedDate and inputValue
    // (just in case we didn't pick the date that is coming from picker because it wasn't valid)
    if (this.picker) {
      const inputValue =
        date && date.isValid()
          ? date.format(this.getMomentDisplayFormat())
          : '';

      this.picker.setSelectedDateAndInputValue({
        selectedDate: date,
        inputValue,
      });
    }

    //
    // Patch the date (i.e. send it to backend)
    const { patch, dispatch, field, handleChange } = this.props;
    try {
      if (!this.isSameMoment(date, cache)) {
        // calling handleChange manually to update date stored in the MasterWidget
        handleChange && handleChange(field, date);

        patch(date);
      }
    } catch (error) {
      dispatch(
        addNotification(field, `${field} has an invalid date.`, 5000, 'error')
      );
    }

    this.handleClose();

    const { handleBackdropLock } = this.props;
    handleBackdropLock && handleBackdropLock(false);
  };

  isSameMoment = (date1, date2) => {
    const moment1 = this.convertToMoment(date1);
    const moment2 = this.convertToMoment(date2);

    return (
      moment1 === moment2 ||
      (moment1 != null && moment2 != null && moment1.isSame(moment2))
    );
  };

  convertToMoment = (value, strict = false) => {
    if (!value) {
      return null;
    } else if (Moment.isMoment(value)) {
      return this.normalizeMomentFormat(value);
    } else {
      MomentTZ.locale(getCurrentActiveLocale());

      let moment = MomentTZ(value, this.getMomentDisplayFormat(value), strict);
      if (moment && moment.isValid()) {
        return this.normalizeMomentFormat(moment);
      }

      return MomentTZ(value, this.getMomentNormalizedFormat(), strict);
    }
  };

  normalizeMomentFormat = (moment) => {
    const format = this.getMomentNormalizedFormat();

    MomentTZ.locale(getCurrentActiveLocale());
    const normalizedString = moment.format(format);

    let momentNorm = MomentTZ(normalizedString, format);

    const { timeFormat, timeZone } = this.props;
    if (timeFormat) {
      momentNorm = momentNorm.tz(timeZone, true);
    }

    return momentNorm;
  };

  getMomentDisplayFormat = (dateToParse = null) => {
    const { dateFormat, timeFormat } = this.props;

    let format = '';

    if (dateFormat) {
      let dateFormatEffective;
      if (
        dateToParse &&
        typeof dateToParse === 'string' &&
        dateToParse.includes('-')
      ) {
        dateFormatEffective = 'YYYY-MM-DD';
      } else {
        dateFormatEffective = dateFormat === true ? 'L' : dateFormat;
      }

      format += dateFormatEffective;
    }

    if (timeFormat) {
      const timeFormatEffective = timeFormat === true ? 'LT' : timeFormat;
      if (format !== '') {
        format += ' ';
      }
      format += timeFormatEffective;
    }

    return format;
  };

  getMomentNormalizedFormat = () => {
    const { dateFormat, timeFormat } = this.props;
    if (dateFormat) {
      return timeFormat ? DATE_TIMEZONE_FORMAT : DATE_FORMAT;
    } else {
      return TIME_FORMAT;
    }
  };

  handleFocus = () => {
    const { dispatch } = this.props;

    this.setState({
      cache: this.props.value,
      isCalendarOpen: true,
    });
    dispatch(disableOutsideClick());
  };

  handleClose = () => {
    const { dispatch } = this.props;

    this.setState({ isCalendarOpen: false }); // close the calendar
    dispatch(allowOutsideClick());
  };

  handleClickOutside = () => {
    // Because this method is called when clicking outside an date field,
    // for all the date fields from the page,
    // we have narrow this down to the current date field.
    // For that reason we check the isCalendarOpen to see if it has the calendar open.
    const { isCalendarOpen } = this.state;
    if (!isCalendarOpen) {
      return;
    }

    this.handleBlur(this.picker.state.selectedDate);
  };

  handleKeydown = (event) => {
    event.stopPropagation();
  };

  renderCalendarDay = (props, day) => {
    return (
      <td {...props} onDoubleClick={() => this.onDayDoubleClicked(day)}>
        {day.date()}
      </td>
    );
  };

  onDayDoubleClicked = (day) => {
    let dateTime = day;

    // Preserve the time from current selected date+time.
    const { timeFormat } = this.props;
    if (timeFormat) {
      const selectedDate = this.picker.state.selectedDate;
      if (selectedDate) {
        dateTime = dateTime.set({
          hour: selectedDate.get('hour'),
          minute: selectedDate.get('minute'),
          second: selectedDate.get('second'),
          millisecond: 0,
        });
      }
    }

    this.handleBlur(dateTime);
  };

  focusInput = () => {
    this.inputElement && this.inputElement.focus();
  };

  toggleCalendarOpenClosed = () => {
    this.setState({ isCalendarOpen: !this.state.isCalendarOpen });
  };

  renderInput = ({ className, ...props }) => {
    const valueOrig = props.value;
    const valueAsMoment = this.convertToMoment(valueOrig, true);
    const valueAsDisplayString =
      valueAsMoment && valueAsMoment.isValid()
        ? valueAsMoment.format(this.getMomentDisplayFormat())
        : `${valueOrig}`;

    return (
      <div className={className}>
        <input
          {...props}
          value={valueAsDisplayString}
          className="form-control"
          ref={(input) => (this.inputElement = input)}
        />
      </div>
    );
  };

  setPicker = (picker) => {
    this.picker = picker;
  };

  render() {
    return (
      <div tabIndex="-1" onKeyDown={this.handleKeydown} className="datepicker">
        <TetheredDateTime
          ref={this.setPicker}
          closeOnTab={true}
          renderDay={this.renderCalendarDay}
          renderInput={this.renderInput}
          onBlur={this.handleBlur}
          onFocus={this.handleFocus}
          open={this.state.isCalendarOpen}
          onFocusInput={this.focusInput}
          closeOnSelect={true}
          setDebounced={this.setDebounced}
          strictParsing={true}
          {...this.props}
        />
        <div>
          <i
            className="meta-icon-calendar input-icon-right input-icon-clickable"
            key={0}
            onClick={this.toggleCalendarOpenClosed}
          />
        </div>
      </div>
    );
  }
}

/**
 * @typedef {object} Props Component props
 * @prop {func} dispatch
 * @prop {func} [handleChange]
 * @prop {func} [handleBackdropLock]
 * @prop {func} [patch]
 * @prop {string} [field]
 * @prop {*} [value]
 * @prop {bool} [isOpenDatePicker]
 * @prop {bool} [hasTimeZone]
 * @prop {bool|string} [dateFormat]
 * @prop {*} [timeZone]
 */
DatePicker.propTypes = {
  dispatch: PropTypes.func.isRequired,
  handleChange: PropTypes.func,
  handleBackdropLock: PropTypes.func,
  patch: PropTypes.func,
  field: PropTypes.string,
  value: PropTypes.any,
  isOpenDatePicker: PropTypes.bool,
  hasTimeZone: PropTypes.bool,
  dateFormat: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  timeFormat: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  timeZone: PropTypes.any,
  defaultValue: PropTypes.string,
  updateItems: PropTypes.func,
  isFilterActive: PropTypes.bool,
};

export default connect()(onClickOutside(DatePicker));
