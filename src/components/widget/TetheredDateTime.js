import React from 'react';
import PropTypes from 'prop-types';
import DateTime from 'react-datetime';
import CalendarContainer from 'react-datetime/src/CalendarContainer';
import TetherComponent from 'react-tether';

export default class TetheredDateTime extends DateTime {
  static propTypes = {
    initialViewMode: PropTypes.oneOf(['years', 'months', 'days', 'time']),
  };

  componentWillMount() {
    const { initialViewMode } = this.props;

    if (initialViewMode) {
      this.setState({
        currentView: initialViewMode,
      });
    }
  }

  render() {
    const { open } = this.state;
    let className =
      'rdt' +
      (this.props.className
        ? Array.isArray(this.props.className)
          ? ' ' + this.props.className.join(' ')
          : ' ' + this.props.className
        : '');
    let children = [];

    if (this.props.input) {
      const props = {
        type: 'text',
        className: 'form-control',
        onFocus: this.openCalendar,
        onChange: this.onInputChange,
        onKeyDown: this.onInputKey,
        value: this.state.inputValue,
        ...this.props.inputProps,
      };

      if (this.props.renderInput) {
        children = [
          <div key="i">{this.props.renderInput(props, this.openCalendar)}</div>,
        ];
      } else {
        children = [<input key="i" {...props} />];
      }
    } else {
      className += ' rdtStatic';
    }

    return (
      <div className={className}>
        <TetherComponent
          attachment="top left"
          targetAttachment="bottom left"
          constraints={[
            {
              to: 'scrollParent',
            },
            {
              to: 'window',
              pin: ['bottom'],
            },
          ]}
        >
          {children}
          {open && (
            <div className="rdtPicker">
              <CalendarContainer
                view={this.state.currentView}
                viewProps={this.getComponentProps()}
                onClickOutside={this.handleClickOutside}
              />
            </div>
          )}
        </TetherComponent>
      </div>
    );
  }
}
