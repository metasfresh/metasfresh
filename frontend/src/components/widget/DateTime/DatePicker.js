import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import MomentTZ from 'moment-timezone';
import onClickOutsideHOC from 'react-onclickoutside';

import TetheredDateTime from './TetheredDateTime';
import { getCurrentActiveLocale } from '../../../utils/locale';
import {
  DATE_FORMAT,
  DATE_TIMEZONE_FORMAT,
  TIME_FORMAT,
} from '../../../constants/Constants';
import {
  convertMomentToTimezone,
  setTimezoneToMoment,
} from '../../../utils/dateHelpers';

class DatePicker extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      isCalendarOpen: false,
      datePatched: this.props.value,
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
    // console.log('handleDateChange', { dateObj });
    if (!dateObj) {
      // console.log('handleDateChange - calling patch with null param');
      this.callPatchIfNeeded(null);
    } else {
      let dateAsMoment = this.convertToMoment(dateObj, true);
      // const dateAsMoment_BeforeSetTZ = dateAsMoment;

      // IMPORTANT: because the date is coming from TetheredDateTime/DateTime(react-datetime),
      // and because that component is returning a Moment using browser's timezone but with date/time correct for our timezone,
      // we have to simply change the timezone to our timezone preserving the time.
      dateAsMoment = this.setTimezone(dateAsMoment);

      // console.log('handleDateChange - calling patch', {
      //   dateAsMoment: dateAsMoment.format(this.getMomentNormalizedFormat()),
      //   dateAsMoment_BeforeSetTZ: dateAsMoment_BeforeSetTZ.format(
      //     this.getMomentNormalizedFormat()
      //   ),
      // });

      if (dateAsMoment && dateAsMoment.isValid()) {
        this.callPatchIfNeeded(dateAsMoment);
      } else {
        // console.log('handleDateChange - not calling patch', { dateAsMoment });
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
      } else if (MomentTZ.isMoment(datePatched)) {
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
      // console.log('callPatchIfNeeded: checking', { date, datePatched });
      if (!this.isSameMoment(date, datePatched)) {
        // calling handleChange manually to update date stored in the MasterWidget
        handleChange && handleChange(field, date);

        // console.log('callPatchIfNeeded: patching date', { date, datePatched });
        patch && patch(date);
      } else {
        // console.log('callPatchIfNeeded: !!!!NOT!!!!! patching date', {
        //   date,
        //   datePatched,
        // });
      }
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
    //console.log('isSameMoment', { date1, moment1, date2, moment2 });

    return (
      moment1 === moment2 ||
      (moment1 != null && moment2 != null && moment1.isSame(moment2))
    );
  };

  convertToMoment = (value, strict = false) => {
    //console.log('convertToMoment --------------------', { value, strict });
    if (!value) {
      return null;
    } else if (MomentTZ.isMoment(value)) {
      // console.log('convertToMoment: (2) returning valid moment', { value });
      return value;
    } else {
      MomentTZ.locale(getCurrentActiveLocale());

      //
      // Try converting using display format
      let moment = MomentTZ(
        value,
        this.getMomentDisplayFormat(value),
        strict,
        this.props.timeZone
      );
      if (moment && moment.isValid()) {
        // IMPORTANT: display format might not contain timezone so make sure we set it to our timezone.
        // noinspection UnnecessaryLocalVariableJS
        const momentNorm = this.setTimezone(moment);

        // console.log('convertToMoment: (3) returning valid moment', {
        //   toString: momentNorm.format(this.getMomentNormalizedFormat()),
        //   momentNorm,
        //   format: this.getMomentDisplayFormat(value),
        //   moment_beforeSetTZ: moment.format(this.getMomentNormalizedFormat()),
        //   value,
        //   strict,
        // });

        return momentNorm;
      }

      //
      // Try converting using normalized format
      moment = MomentTZ(value, this.getMomentNormalizedFormat(), strict);
      if (moment && moment.isValid()) {
        // console.log('convertToMoment: (4) returning valid moment', {
        //   toString: moment.format(this.getMomentNormalizedFormat()),
        //   moment,
        //   format: this.getMomentNormalizedFormat(),
        //   value,
        //   strict,
        // });

        return moment;
      }

      //
      // Last try, try converting using standard ISO format
      moment = MomentTZ(value, DATE_TIMEZONE_FORMAT, strict);
      // console.log('convertToMoment: (5) try using ISO format', {
      //   toString: moment.format(this.getMomentNormalizedFormat()),
      //   moment,
      //   isValid: moment && moment.isValid(),
      // });

      return moment;
    }
  };

  setTimezone = (moment) => {
    if (!moment) {
      return null;
    }

    const { dateFormat, timeFormat, timeZone } = this.props;
    if (dateFormat && timeFormat && timeZone) {
      return setTimezoneToMoment(moment, timeZone);
    } else {
      return moment;
    }
  };

  convertToTimezone = (moment) => {
    if (!moment) {
      return null;
    }

    const { dateFormat, timeFormat, timeZone } = this.props;
    if (dateFormat && timeFormat && timeZone) {
      return convertMomentToTimezone(moment, timeZone);
    } else {
      return moment;
    }
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
    let valueAsMoment = this.convertToMoment(valueOrig, true);
    //valueAsMoment = this.setTimezone(valueAsMoment);

    // noinspection UnnecessaryLocalVariableJS
    const valueAsString =
      valueAsMoment && valueAsMoment.isValid()
        ? valueAsMoment.format(this.getMomentDisplayFormat())
        : `${valueOrig}`;

    // console.log('renderInputDateString', {
    //   valueOrig,
    //   valueAsMoment_toString: valueAsMoment.format(
    //     this.getMomentNormalizedFormat()
    //   ),
    //   valueAsString,
    // });

    return valueAsString;
  };

  render() {
    const { dateFormat, timeFormat } = this.props;
    const { isCalendarOpen } = this.state;

    const isTimeOnly = !dateFormat && timeFormat;

    // IMPORTANT: convert the value property to our timezone
    // this will lead to getting right date/time rendered by react-datetime component.
    let valueNorm = this.convertToTimezone(this.props.value);
    // console.log(`render ${this.props.field}`, {
    //   valueNorm,
    //   valueNorm_toString: valueNorm?.format(this.getMomentNormalizedFormat()),
    //   value: this.props.value,
    //   value_toString: this.props.value?.format(
    //     this.getMomentNormalizedFormat()
    //   ),
    //   props: this.props,
    // });

    return (
      <div tabIndex="-1" onKeyDown={this.handleKeydown} className="datepicker">
        <TetheredDateTime
          ref={(picker) => (this.picker = picker)}
          {...this.props}
          value={valueNorm}
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
