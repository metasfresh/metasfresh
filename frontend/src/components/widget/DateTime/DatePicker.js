import PropTypes from 'prop-types';
import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import MomentTZ from 'moment-timezone';
import onClickOutsideHOC from 'react-onclickoutside';
import counterpart from 'counterpart';

import TetheredDateTime from './TetheredDateTime';
import { addNotification } from '../../../actions/AppActions';
import { getCurrentActiveLocale } from '../../../utils/locale';
import {
  DATE_FORMAT,
  DATE_TIMEZONE_FORMAT,
  TIME_FORMAT,
} from '../../../constants/Constants';
import {
  convertMomentToTimezone,
  setMomentToEndOfDay,
  setTimezoneToMoment,
} from '../../../utils/dateHelpers';

/**
 * The format itself first, then its short forms: day and month without the leading zero, a two-digit
 * year (`DD.MM.YYYY` gives `DD.MM.YYYY`, `D.MM.YYYY`, ..., `D.M.YY`).
 */
const getShortFormsOfDateFormat = (format) => {
  // only a plain numeric format (day, month, year and separators) has these short forms
  const tokens = format.split(/[^A-Za-z]+/).sort();
  if (tokens.join(',') !== 'DD,MM,YYYY') {
    return [format];
  }

  const formats = [];
  ['DD', 'D'].forEach((day) =>
    ['MM', 'M'].forEach((month) =>
      ['YYYY', 'YY'].forEach((year) => {
        const shortForm = format
          .replace('DD', day)
          .replace('MM', month)
          .replace('YYYY', year);
        if (!formats.includes(shortForm)) {
          formats.push(shortForm);
        }
      })
    )
  );
  return formats;
};

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
      // (a grid date cell only passes this when the edit was entered by a pointer action)
      setTimeout(() => this.picker.openCalendar(), 100);
    }
  }

  componentDidUpdate(prevProps) {
    const { datePatched } = this.state;

    // If "value" property changed then update the "datePatched" state variable.
    if (
      !this.isSameMoment(prevProps.value, this.props.value) &&
      !this.isSameMoment(datePatched, this.props.value)
    ) {
      this.setState({ datePatched: this.props.value });
    }
  }

  handleDateChange = (dateObj) => {
    // console.log('handleDateChange', { dateObj });
    // whether this change comes from typing into the input (set by the input's onChange); consumed
    // on every change so that a later calendar pick is never taken for typing
    const isTyped = this.isInputChangeInProgress;
    this.isInputChangeInProgress = false;

    if (!isTyped) {
      // a calendar pick replaces whatever was typed
      this.typedText = undefined;
    }

    if (!dateObj) {
      // Grid date cell: clearing the typed text is pending like any typing (Escape discards it)
      if (this.props.isGridCell && isTyped) {
        this.pendingTypedDate = null;
        this.isTypedClearPending = true;
        return;
      }
      // console.log('handleDateChange - calling patch with null param');
      this.callPatchIfNeeded(null);
    } else {
      let dateAsMoment = this.convertToMoment(dateObj, true);
      // const dateAsMoment_BeforeSetTZ = dateAsMoment;

      // Grid date cell: keep a typed date pending until the cell is left, so that Escape can discard it
      if (this.props.isGridCell && isTyped) {
        const typedAsMoment = this.setTimezone(dateAsMoment);
        this.pendingTypedDate =
          typedAsMoment && typedAsMoment.isValid() ? typedAsMoment : null;
        this.isTypedClearPending = false;
        return;
      }

      // IMPORTANT: because the date is coming from TetheredDateTime/DateTime(react-datetime),
      // and because that component is returning a Moment using browser's timezone but with date/time correct for our timezone,
      // we have to simply change the timezone to our timezone preserving the time.
      dateAsMoment = this.setTimezone(dateAsMoment);

      // Grid date cell: a calendar pick supersedes a typed date still pending in this edit
      if (this.props.isGridCell) {
        this.discardTypedDate();
      }

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

  /**
   * Saves what was typed since the input was entered, and forgets it: in a grid date cell the typed
   * moment (`pendingTypedDate`) or the clearing of the date (`isTypedClearPending`); in any date widget
   * a typed text the exact format did not take (`typedText`, see commitTypedText). Typing counts only
   * while the input still shows it.
   * @returns {boolean} whether there was typing to save
   */
  commitTypedDate = () => {
    const { pendingTypedDate, isTypedClearPending, typedText } = this;
    this.discardTypedDate();
    if (
      typedText !== undefined &&
      this.inputElement &&
      this.inputElement.value !== typedText &&
      // (the input shows a text it can read reformatted, e.g. ISO 2027-01-04 as 04.01.2027)
      this.inputElement.value !== this.renderInputDateString(typedText)
    ) {
      // a new value replaced the typing in the input
      return false;
    }
    if (pendingTypedDate) {
      this.callPatchIfNeeded(pendingTypedDate);
      return true;
    } else if (isTypedClearPending) {
      this.callPatchIfNeeded(null);
      return true;
    } else if (typedText && typedText.trim() && this.props.dateFormat) {
      // (a time-only field keeps react-datetime's own handling of the typed time)
      this.commitTypedText(typedText);
      return true;
    }
    return false;
  };

  discardTypedDate = () => {
    this.pendingTypedDate = null;
    this.isTypedClearPending = false;
    this.typedText = undefined;
  };

  /**
   * Saves a typed text that the exact display format did not accept, if it is a date in a short form
   * of that format (see parseTypedText); otherwise tells the user the text is no date. The stored
   * value is kept in that case (handleBlur resets the input to it).
   */
  commitTypedText = (typedText) => {
    const text = typedText.trim();
    // (also what typing itself accepts, see handleDateChange, so a text taken while typing is never reported)
    let moment = this.parseTypedText(text);
    if (!moment || !moment.isValid()) {
      moment = this.setTimezone(this.convertToMoment(text, true));
    }
    if (moment && moment.isValid()) {
      this.callPatchIfNeeded(moment);
    } else {
      this.notifyInvalidDate(typedText);
    }
  };

  /**
   * Parses a typed text with the session's date format, also accepting a day or month without its
   * leading zero and a two-digit year (`4.1.27` for `04.01.2027` in German, `1/4/27` for
   * `01/04/2027` in English). Text with a `-` is read as ISO `YYYY-MM-DD`, as everywhere in this
   * widget. A date typed without a time into a date+time field means the end of that day.
   */
  parseTypedText = (text) => {
    const { dateFormat, timeFormat } = this.props;
    if (!dateFormat) {
      return null;
    }

    MomentTZ.locale(getCurrentActiveLocale());
    const datePart = this.getMomentDisplayFormat_DatePart(text);
    // ISO (a text with "-") is accepted in full only
    const dateFormats =
      datePart === 'YYYY-MM-DD'
        ? [datePart]
        : getShortFormsOfDateFormat(
            MomentTZ.localeData().longDateFormat(datePart) || datePart
          );

    if (timeFormat) {
      const timePart = timeFormat === true ? 'LT' : timeFormat;
      const dateTime = MomentTZ(
        text,
        dateFormats.map((format) => `${format} ${timePart}`),
        true
      );
      if (dateTime.isValid()) {
        return this.setTimezone(dateTime);
      }
      // same conversion as convertInputStringToMomentOnBlur, with the short forms
      const dateOnly = MomentTZ(text, dateFormats, true);
      return dateOnly.isValid() ? this.toEndOfDayInTimezone(dateOnly) : null;
    }

    const date = MomentTZ(text, dateFormats, true);
    return date.isValid() ? date : null;
  };

  notifyInvalidDate = (typedText) => {
    const { dispatch } = this.props;
    const example = MomentTZ().format(this.getMomentDisplayFormat());
    dispatch(
      addNotification(
        counterpart.translate('widget.datePicker.invalidDate.title'),
        counterpart.translate('widget.datePicker.invalidDate.text', {
          value: typedText,
          example,
        }),
        5000,
        'error'
      )
    );
  };

  resetPickerSelectedDateAndInputValueToLastPatchedDate = () => {
    // the input shows the stored value again: what was typed before is gone (handleBlur commits it first)
    this.discardTypedDate();

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
    // a date patched earlier in the same batched event is not in the state yet
    const datePatched =
      this.datePatchedNotYetInState !== undefined
        ? this.datePatchedNotYetInState
        : this.state.datePatched;
    try {
      // console.log('callPatchIfNeeded: checking', { date, datePatched });
      if (!this.isSameMoment(date, datePatched)) {
        // calling handleChange manually to update date stored in the MasterWidget
        handleChange && handleChange(field, date);

        //console.log('callPatchIfNeeded: patching date', { date, datePatched });
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

    this.datePatchedNotYetInState = date;
    this.setState({ datePatched: date }, () => {
      this.datePatchedNotYetInState = undefined;
    });
  };

  handleBlur = (dateObj) => {
    // NOTE: for some reason `dateObj` is not always the up-to-date value of the date
    // for that reason we cannot use it and we relly entirely on the date we get on "handleDateChange".

    // react-datetime passes a moment when a date was picked or typed in full (for a field without value it
    // blurs on a calendar pick BEFORE reporting the change), so earlier typing must not be reported then
    if (MomentTZ.isMoment(dateObj)) {
      if (
        this.pendingTypedDate &&
        // (compared like pendingTypedDate was made: react-datetime's moment is browser-local)
        !this.isSameMoment(this.setTimezone(dateObj), this.pendingTypedDate)
      ) {
        // a calendar pick, not the typed date: the pick is saved by handleDateChange
        this.discardTypedDate();
      } else {
        this.typedText = undefined;
      }
    }

    // A date typed into the input is saved (grid date cell: only) when the input is left; a short form
    // is saved now, and a text that is no date is reported (grid date cell: Escape discards the typing,
    // see handleKeydown).
    const isTypingCommitted = this.commitTypedDate();

    //
    // Cover some corner cases when user is writing something into date/time field and then is TABing out
    // (not after the typing was committed above: that already covers it, and a second patch would be
    // compared with a datePatched state not yet updated)
    const moment = isTypingCommitted
      ? undefined
      : this.convertInputStringToMomentOnBlur();
    if (moment !== undefined && moment?.isValid()) {
      this.callPatchIfNeeded(moment);
    }

    this.closeCalendarAndResetToLastPatchedDate();

    const { handleBackdropLock } = this.props;
    handleBackdropLock && handleBackdropLock(false);
  };

  convertInputStringToMomentOnBlur = () => {
    const inputValue = this.inputElement.value;
    if (!inputValue) {
      return undefined;
    }

    //
    // Case: user wrote a date(w/o time) into a date+time field
    // => convert that date to end of day date+time
    if (this.isDateAndTime()) {
      const dateFormat = this.getMomentDisplayFormat_DatePart(inputValue);
      const moment = MomentTZ(inputValue, dateFormat, true);
      if (moment && moment.isValid()) {
        return this.toEndOfDayInTimezone(moment);
      }
    }

    //
    return undefined;
  };

  /** the end of the given (browser-local) moment's day in the session time zone, as a new moment */
  toEndOfDayInTimezone = (moment) => {
    const endOfDay = this.setTimezone(moment.clone());
    setMomentToEndOfDay(endOfDay);
    return endOfDay;
  };

  isDateAndTime = () => {
    const { dateFormat, timeFormat } = this.props;
    return dateFormat && timeFormat;
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
    let format = '';

    const dateFormatEffective =
      this.getMomentDisplayFormat_DatePart(dateToParse);
    if (dateFormatEffective) {
      format += dateFormatEffective;
    }

    const { timeFormat } = this.props;
    if (timeFormat) {
      const timeFormatEffective = timeFormat === true ? 'LT' : timeFormat;
      if (format !== '') {
        format += ' ';
      }
      format += timeFormatEffective;
    }

    return format;
  };

  getMomentDisplayFormat_DatePart = (dateToParse = null) => {
    const { dateFormat } = this.props;

    if (!dateFormat) {
      return '';
    }

    if (
      dateToParse &&
      typeof dateToParse === 'string' &&
      dateToParse.includes('-')
    ) {
      return 'YYYY-MM-DD';
    } else {
      return dateFormat === true ? 'L' : dateFormat;
    }
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

    // Grid date cell: entering the edit by keyboard must not pop the calendar up over the typing;
    // a pointer action (click into the cell / the field / the calendar icon) opens it.
    const { isGridCell, isPointerEdit } = this.props;
    if (!isGridCell || isPointerEdit) {
      this.openCalendarIfEditable();
    }
  };

  // called by onClickOutsideHOC
  handleClickOutside = () => {
    // Because this method is called when clicking outside a date field,
    // for all the date fields from the page,
    // we have to narrow this down to the current date field.
    // For that reason we check the isCalendarOpen to see if it has the calendar open.

    // (a grid date cell exists only while it is edited, and may have no calendar open; typing into
    // a date field with its calendar closed, e.g. after Escape, is left by a click outside too;
    // a time-only field keeps its behaviour)
    const { isCalendarOpen } = this.state;
    if (
      isCalendarOpen ||
      this.props.isGridCell ||
      (this.typedText !== undefined && this.props.dateFormat)
    ) {
      this.handleBlur();
    }
  };

  handleKeydown = (event) => {
    if (event.key === 'Escape') {
      // Escape cancels the typing: it is neither saved nor reported when the input is left
      this.typedText = undefined;
    }

    // Grid date cell: Escape (discard the typing, restore the stored value) and Enter (save the typed
    // date) belong to the grid's cell editing, so they must reach it.
    if (this.props.isGridCell) {
      if (event.key === 'Escape') {
        this.discardTypedDate();
        return;
      }
      if (event.key === 'Enter') {
        this.commitTypedDate();
        return;
      }
    }
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
    // reset once the state is applied, so that a date just patched (datePatched) is what the input shows
    this.setState(
      { isCalendarOpen: false },
      this.resetPickerSelectedDateAndInputValueToLastPatchedDate
    );
  };

  isReadonly = () => !!this.props?.inputProps?.disabled;

  renderInput = ({ className, ...props }) => {
    const dateString = this.renderInputDateString(props.value);

    const onClick = (event) => {
      props.onClick && props.onClick(event);
      this.openCalendarIfEditable();
    };

    const { isGridCell } = this.props;
    const onChange = (event) => {
      // react-datetime reports the typed date asynchronously; remember it came from typing
      this.isInputChangeInProgress = true;
      this.typedText = event.target.value;
      props.onChange && props.onChange(event);
    };

    return (
      <div className={className}>
        <input
          {...props}
          // the grid focuses its cell editor via this class (TableRow._editProperty)
          className={
            isGridCell ? 'form-control js-input-field' : 'form-control'
          }
          ref={(input) => (this.inputElement = input)}
          value={dateString}
          onClick={onClick}
          onChange={onChange}
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

  /**
   * The moment react-datetime opens on for a grid date cell. react-datetime compares its viewDate
   * prop by reference and resets the calendar to it when the reference changes, so the moment is
   * cached per `gridCellValue` (a string is parsed into a new moment on each call).
   */
  getGridViewDate = () => {
    const { isGridCell, gridCellValue } = this.props;
    if (!isGridCell || !gridCellValue) {
      return null;
    }
    if (this.gridViewDateSource !== gridCellValue) {
      const viewDate = this.convertToMoment(gridCellValue, false);
      this.gridViewDateSource = gridCellValue;
      this.gridViewDate = viewDate && viewDate.isValid() ? viewDate : null;
    }
    return this.gridViewDate;
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

    // Grid date cell: open the calendar on the month of the cell's stored value, also when the value
    // does not parse into react-datetime's own selected date (it would fall back to today's month)
    const gridViewDate = this.getGridViewDate();

    return (
      <div tabIndex="-1" onKeyDown={this.handleKeydown} className="datepicker">
        <TetheredDateTime
          ref={(picker) => (this.picker = picker)}
          {...this.props}
          {...(gridViewDate ? { viewDate: gridViewDate } : {})}
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
 * @prop {bool} [isGridCell] - set only by the grid (TableCell)
 * @prop {bool} [isPointerEdit] - grid only: the cell's edit was entered by a pointer action
 * @prop {*} [gridCellValue] - grid only: the cell's raw stored value
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
  isGridCell: PropTypes.bool,
  isPointerEdit: PropTypes.bool,
  gridCellValue: PropTypes.any,
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
