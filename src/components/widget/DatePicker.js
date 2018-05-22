import PropTypes from 'prop-types';
import React, { Component } from 'react';
import { connect } from 'react-redux';
import TetheredDateTime from './TetheredDateTime';
import onClickOutside from 'react-onclickoutside';
import { addNotification } from '../../actions/AppActions';
import {
  allowOutsideClick,
  disableOutsideClick,
} from '../../actions/WindowActions';

class DatePicker extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      cache: null,
    };
  }

  componentDidMount() {
    const { onBackdropLock, isOpenDatePicker } = this.props;
    onBackdropLock && onBackdropLock(true);
    if (isOpenDatePicker) {
      setTimeout(() => {
        this.picker.openCalendar();
      }, 100);
    }
  }

  handleBlur = date => {
    const { patch, onBackdropLock, dispatch, field } = this.props;
    const { cache, open } = this.state;

    if (!open) {
      return;
    }

    try {
      if (
        date &&
        JSON.stringify(cache) !==
          (date !== '' ? JSON.stringify(date && date.toDate()) : '')
      ) {
        patch(date);
      }
    } catch (error) {
      dispatch(
        addNotification(field, `${field} has an invalid date.`, 5000, 'error')
      );
    }

    this.handleClose();

    onBackdropLock && onBackdropLock(false);
  };

  handleFocus = () => {
    const { value, dispatch } = this.props;
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
  onBackdropLock: PropTypes.func,
  patch: PropTypes.func,
  field: PropTypes.string,
  value: PropTypes.any,
  isOpenDatePicker: PropTypes.bool,
};

export default connect()(onClickOutside(DatePicker));
