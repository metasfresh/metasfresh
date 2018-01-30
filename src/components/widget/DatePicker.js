import PropTypes from "prop-types";
import React, { Component } from "react";
import { connect } from "react-redux";
import TetheredDateTime from "./TetheredDateTime";
import { addNotification } from "../../actions/AppActions";
import {
  allowOutsideClick,
  disableOutsideClick
} from "../../actions/WindowActions";

const propTypes = {
  dispatch: PropTypes.func.isRequired,
  handleBackdropLock: PropTypes.func,
  patch: PropTypes.func,
  field: PropTypes.string,
  value: PropTypes.any,
  isOpenDatePicker: PropTypes.bool
};

class DatePicker extends Component {
  constructor(props) {
    super(props);
    this.state = {
      open: false,
      cache: null
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
    const { patch, handleBackdropLock, dispatch, field } = this.props;
    const { cache } = this.state;

    try {
      if (
        JSON.stringify(cache) !==
        (date !== "" ? JSON.stringify(date && date.toDate()) : "")
      ) {
        patch(date);
      }
    } catch (error) {
      dispatch(
        addNotification(field, `${field} has an invalid date.`, 5000, "error")
      );
    }

    this.handleClose();

    handleBackdropLock && handleBackdropLock(false);
  };

  handleFocus = () => {
    const { value, dispatch } = this.props;
    this.setState({
      cache: value,
      open: true
    });
    dispatch(disableOutsideClick());
  };

  handleClose = () => {
    const { dispatch } = this.props;
    this.setState({
      open: false
    });
    dispatch(allowOutsideClick());
  };

  handleClickOutside = () => {
    this.handleClose();
  };

  handleKeydown = e => {
    e.stopPropagation();
  };

  renderDay = (props, currentDate) => {
    return (
      <td {...props} onDoubleClick={() => this.handleClose()}>
        {currentDate.date()}
      </td>
    );
  };

  renderInput = ({ className, ...props }) => (
    <div className={className}>
      <input className="form-control" {...props} />
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
          {...this.props}
        />
        <i className="meta-icon-calendar" key={0} />
      </div>
    );
  }
}

DatePicker.propTypes = propTypes;

export default connect()(DatePicker);
