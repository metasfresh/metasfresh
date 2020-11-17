import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import MomentTZ from 'moment-timezone';
import onClickOutside from 'react-onclickoutside';

import { getCurrentActiveLocale } from '../../utils/locale';
import { DATE_FIELD_FORMATS } from '../../constants/Constants';
import { addNotification } from '../../actions/AppActions';
import {
  allowOutsideClick,
  disableOutsideClick,
} from '../../actions/WindowActions';

import TetheredDateTime from './TetheredDateTime';

/**
 * @file Class based component.
 * @module DatePicker
 * @extends Component
 */
class DatePicker extends Component {
  static timeZoneRegex = new RegExp(/[+-]{1}\d+:\d+/);

  constructor(props) {
    super(props);
    this.state = {
      open: false,
      cache: null,
    };
  }

  /**
   * @method componentDidMount
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  componentDidMount() {
    const { handleBackdropLock, isOpenDatePicker } = this.props;
    handleBackdropLock && handleBackdropLock(true);

    if (isOpenDatePicker) {
      setTimeout(() => {
        this.picker.openCalendar();
      }, 100);
    }
  }

  /**
   * @method handleBlur
   * @summary ToDo: Describe the method
   * @param {*} date
   * @todo Write the documentation
   */
  handleBlur = (date) => {
    const {
      patch,
      handleBackdropLock,
      dispatch,
      field,
      handleChange,
      timeZone,
    } = this.props;
    const { cache, open } = this.state;

    if (!open) {
      return;
    }

    if (date && !MomentTZ.isMoment(date)) {
      date = MomentTZ(date, `L LT`);
      date = date.tz(timeZone, true);
    }

    try {
      if (
        JSON.stringify(cache) !==
        (date !== '' ? JSON.stringify(date && date.toDate()) : '')
      ) {
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

    handleBackdropLock && handleBackdropLock(false);
  };

  /**
   * @method handleFocus
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleFocus = () => {
    const { dispatch } = this.props;
    const { value } = this.props;

    this.setState({
      cache: value,
      open: true,
    });
    dispatch(disableOutsideClick());
  };

  /**
   * @method handleClose
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClose = () => {
    const { dispatch } = this.props;

    this.setState({
      open: false,
    });
    dispatch(allowOutsideClick());
  };

  /**
   * @method handleClickOutside
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  handleClickOutside = () => {
    const { open } = this.state;

    if (!open) {
      return;
    }
    this.handleBlur(this.picker.state.selectedDate);
  };

  /**
   * @method handleKeydown
   * @summary ToDo: Describe the method
   * @param {object} event
   * @todo Write the documentation
   */
  handleKeydown = (e) => {
    e.stopPropagation();
  };

  /**
   * @method renderDay
   * @summary ToDo: Describe the method
   * @param {*} props
   * @param {*} currentDate
   * @todo Write the documentation
   */
  renderDay = (props, currentDate) => {
    return (
      <td {...props} onDoubleClick={() => this.handleBlur(currentDate)}>
        {currentDate.date()}
      </td>
    );
  };

  /**
   * @method focusInput
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  focusInput = () => {
    this.inputElement && this.inputElement.focus();
  };

  renderInput = ({ className, ...props }) => {
    let { value } = props;
    // patch pre-formatated date that comes like this from BE
    if (value && value.includes('-')) {
      MomentTZ.locale(getCurrentActiveLocale());
      value = MomentTZ(value).format(DATE_FIELD_FORMATS.Date);
    }
    return (
      <div className={className}>
        <input
          {...props}
          value={value}
          className="form-control"
          ref={(input) => {
            this.inputElement = input;
          }}
        />
      </div>
    );
  };

  /**
   * @method render
   * @summary ToDo: Describe the method
   * @todo Write the documentation
   */
  render() {
    return (
      <div tabIndex="-1" onKeyDown={this.handleKeydown} className="datepicker">
        <TetheredDateTime
          ref={(c) => (this.picker = c)}
          closeOnTab={true}
          renderDay={this.renderDay}
          renderInput={this.renderInput}
          onBlur={this.handleBlur}
          onFocus={this.handleFocus}
          open={this.state.open}
          onFocusInput={this.focusInput}
          closeOnSelect={false}
          {...this.props}
        />
        <i className="meta-icon-calendar" key={0} />
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
 * @todo Check title, buttons. Which proptype? Required or optional?
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
  timeZone: PropTypes.any,
};

export default connect()(onClickOutside(DatePicker));
