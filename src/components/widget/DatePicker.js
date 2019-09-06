import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import MomentTZ from 'moment-timezone';
import onClickOutside from 'react-onclickoutside';

import TetheredDateTime from './TetheredDateTime';
import { addNotification } from '../../actions/AppActions';
import {
  allowOutsideClick,
  disableOutsideClick,
} from '../../actions/WindowActions';

class DatePicker extends Component {
  static timeZoneRegex = new RegExp(/[+-]{1}\d+:\d+/);

  constructor(props) {
    super(props);
    this.state = {
      open: false,
      cache: null,
    };
  }

  componentDidMount() {
    const { handleBackdropLock, isOpenDatePicker } = this.props;
    handleBackdropLock && handleBackdropLock(true);

    if (isOpenDatePicker) {
      setTimeout(() => {
        this.picker.openCalendar();
      }, 100);
    }
  }

  handleBlur = date => {
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

  handleFocus = () => {
    const { dispatch } = this.props;
    const { value } = this.props;

    this.setState({
      cache: value,
      open: true,
    });
    dispatch(disableOutsideClick());
  };

  handleClose = () => {
    const { dispatch } = this.props;
    this.setState({
      open: false,
    });
    dispatch(allowOutsideClick());
  };

  handleClickOutside = () => {
    const { open } = this.state;

    if (!open) {
      return;
    }
    this.handleBlur(this.picker.state.selectedDate);
  };

  handleKeydown = e => {
    e.stopPropagation();
  };

  renderDay = (props, currentDate) => {
    return (
      <td {...props} onDoubleClick={() => this.handleBlur(currentDate)}>
        {currentDate.date()}
      </td>
    );
  };

  focusInput = () => {
    this.inputElement && this.inputElement.focus();
  };

  renderInput = ({ className, ...props }) => (
    <div className={className}>
      <input
        {...props}
        className="form-control"
        ref={input => {
          this.inputElement = input;
        }}
      />
    </div>
  );

  render() {
    return (
      <div tabIndex="-1" onKeyDown={this.handleKeydown} className="datepicker">
        <TetheredDateTime
          ref={c => (this.picker = c)}
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
};

export default connect()(onClickOutside(DatePicker));
