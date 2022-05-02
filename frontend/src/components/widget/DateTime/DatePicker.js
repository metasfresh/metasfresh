import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import MomentTZ from 'moment-timezone';
import Moment from 'moment-timezone';
import onClickOutsideHOC from 'react-onclickoutside';

import TetheredDateTime from './TetheredDateTime';
import { getCurrentActiveLocale } from '../../../utils/locale';
import {
  DATE_FORMAT,
  DATE_TIMEZONE_FORMAT,
  TIME_FORMAT,
} from '../../../constants/Constants';

class DatePicker extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isCalendarOpen: false,
      datePatched: null,
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

  handleDateChange = (dateObj) => {
    if (!dateObj) {
      this.callPatchIfNeeded(null);
    } else {
      const dateAsMoment = this.convertToMoment(dateObj, true);
      if (dateAsMoment && dateAsMoment.isValid()) {
        this.callPatchIfNeeded(dateAsMoment);
      }
    }
  };

  resetPickerSelectedDateAndInputValueToLastPatchedDate = () => {
    if (this.picker) {
      const { datePatched } = this.state;

      let inputValue, selectedDate;
      if (!datePatched) {
        inputValue = '';
        selectedDate = null;
      } else if (Moment.isMoment(datePatched)) {
        inputValue = datePatched.isValid()
          ? datePatched.format(this.getMomentDisplayFormat())
          : '';
        selectedDate = datePatched;
      } else {
        inputValue = `${datePatched}`;
        selectedDate = this.convertToMoment(datePatched, true);
      }

      this.picker.setSelectedDateAndInputValue({ selectedDate, inputValue });
    }
  };

  callPatchIfNeeded = (date) => {
    const { patch, field, handleChange } = this.props;
    const { datePatched } = this.state;
    try {
      if (!this.isSameMoment(date, datePatched)) {
        // calling handleChange manually to update date stored in the MasterWidget
        handleChange && handleChange(field, date);

        // console.log('callPatchIfNeeded: patching date', { date, datePatched });
        patch && patch(date);
      }
      //else { console.log('callPatchIfNeeded: !!!!NOT!!!!! patching date', { date, datePatched }); }
    } catch (error) {
      console.log(`callPatchIfNeeded: error patching field ${field}`, {
        date,
        datePatched,
        error,
      });
    }

    this.setState({ datePatched: date });
  };

  handleBlur = (/*dateObj*/) => {
    // NOTE: for some reason `dateObj` is not always the up-to-date value of the date
    // for that reason we cannot use it and we relly entirely on the date we get on "handleDateChange".

    this.closeCalendarAndResetToLastPatchedDate();

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
      const moment = value;
      if (moment.isValid()) {
        return this.normalizeMomentFormat(moment);
      } else {
        return moment;
      }
    } else {
      MomentTZ.locale(getCurrentActiveLocale());

      let moment = MomentTZ(value, this.getMomentDisplayFormat(value), strict);
      //console.log('convertToMoment1:', { value, moment });
      if (moment && moment.isValid()) {
        return this.normalizeMomentFormat(moment);
      }

      moment = MomentTZ(value, this.getMomentNormalizedFormat(), strict);
      //console.log('convertToMoment2:', { value, moment });
      return moment;
    }
  };

  normalizeMomentFormat = (moment) => {
    const format = this.getMomentNormalizedFormat();

    MomentTZ.locale(getCurrentActiveLocale());
    const normalizedString = moment.format(format);

    let momentNorm = MomentTZ(normalizedString, format);

    const { dateFormat, timeFormat, timeZone } = this.props;
    if (dateFormat && timeFormat && timeZone) {
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
    this.setState({ datePatched: this.props.value });

    this.openCalendarIfEditable();
  };

  // called by onClickOutsideHOC
  handleClickOutside = () => {
    // Because this method is called when clicking outside a date field,
    // for all the date fields from the page,
    // we have to narrow this down to the current date field.
    // For that reason we check the isCalendarOpen to see if it has the calendar open.

    const { isCalendarOpen } = this.state;
    if (isCalendarOpen) {
      this.handleBlur();
    }
  };

  handleKeydown = (event) => {
    event.stopPropagation();
  };

  renderCalendarDay = (dayProps, currentDate /*, selected*/) => {
    return <td {...dayProps}>{currentDate.date()}</td>;
  };

  focusInput = () => this.inputElement && this.inputElement.focus();

  toggleCalendarOpenClosed = () => {
    const { isCalendarOpen } = this.state;
    if (isCalendarOpen) {
      this.closeCalendarAndResetToLastPatchedDate();
    } else {
      this.openCalendarIfEditable();
    }
  };

  setIsCalendarOpen = (isCalendarOpen) => {
    this.setState({ isCalendarOpen });
  };

  openCalendarIfEditable = () => {
    if (!this.isReadonly()) {
      this.setIsCalendarOpen(true);
    }
  };

  closeCalendarAndResetToLastPatchedDate = () => {
    this.setIsCalendarOpen(false);
    this.resetPickerSelectedDateAndInputValueToLastPatchedDate();
  };

  isReadonly = () => !!this.props?.inputProps?.disabled;

  renderInput = ({ className, ...props }) => {
    const dateString = this.renderInputDateString(props.value);

    const onClick = (event) => {
      props.onClick && props.onClick(event);
      this.openCalendarIfEditable();
    };

    return (
      <div className={className}>
        <input
          {...props}
          className="form-control"
          ref={(input) => (this.inputElement = input)}
          value={dateString}
          onClick={onClick}
        />
      </div>
    );
  };

  renderInputDateString = (valueOrig) => {
    const valueAsMoment = this.convertToMoment(valueOrig, true);

    return valueAsMoment && valueAsMoment.isValid()
      ? valueAsMoment.format(this.getMomentDisplayFormat())
      : `${valueOrig}`;
  };

  render() {
    const { dateFormat, timeFormat } = this.props;
    const { isCalendarOpen } = this.state;

    const isTimeOnly = !dateFormat && timeFormat;

    return (
      <div tabIndex="-1" onKeyDown={this.handleKeydown} className="datepicker">
        <TetheredDateTime
          {...this.props}
          ref={(picker) => (this.picker = picker)}
          closeOnSelect={true}
          closeOnTab={true}
          open={isCalendarOpen}
          setDebounced={this.setDebounced}
          strictParsing={!isTimeOnly}
          renderDay={this.renderCalendarDay}
          renderInput={this.renderInput}
          onFocus={this.handleFocus}
          onFocusInput={this.focusInput}
          onBlur={this.handleBlur}
          onChange={this.handleDateChange}
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
  value: PropTypes.oneOfType([PropTypes.object, PropTypes.string]),
  isOpenDatePicker: PropTypes.bool,
  dateFormat: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  timeFormat: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
  hasTimeZone: PropTypes.bool,
  timeZone: PropTypes.string,
  defaultValue: PropTypes.string,
  updateItems: PropTypes.func,
  isFilterActive: PropTypes.bool,
  inputProps: PropTypes.object.isRequired,
};

export default connect()(onClickOutsideHOC(DatePicker));
