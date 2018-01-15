import PropTypes from "prop-types";
import React, { Component } from "react";
import Datetime from "react-datetime";
import { connect } from "react-redux";

import { addNotification } from "../../actions/AppActions";

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
    const { handleBackdropLock } = this.props;
    handleBackdropLock && handleBackdropLock(true);
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
    const { value } = this.props;
    this.setState({
      cache: value,
      open: true
    });
  };

  handleClose = () => {
    this.setState({
      open: false
    });
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
      <div tabIndex="-1" onKeyDown={this.handleKeydown}>
        <Datetime
          closeOnTab={true}
          renderDay={this.renderDay}
          renderInput={this.renderInput}
          onBlur={this.handleBlur}
          onFocus={this.handleFocus}
          {...this.props}
        />
      </div>
    );
  }
}

DatePicker.propTypes = propTypes;

export default connect()(DatePicker);
