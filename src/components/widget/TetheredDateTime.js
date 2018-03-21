import React from 'react';
import DateTime from 'react-datetime';
import CalendarContainer from 'react-datetime/src/CalendarContainer';
import TetherComponent from 'react-tether';
import classnames from 'classnames';

// TODO: This monkeypatching that's happening here has to go.
class TetheredDateTime extends DateTime {
  render() {
    const { open } = this.props;
    let className = classnames('rdt', this.props.className, {
      rdtStatic: !this.props.input,
    });
    const children = [];

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

      children.push(
        <div key="i">{this.props.renderInput(props, this.openCalendar)}</div>
      );
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
            <div className="ignore-react-onclickoutside rdtPicker">
              <CalendarContainer
                view={this.state.currentView}
                viewProps={this.getComponentProps()}
              />
            </div>
          )}
        </TetherComponent>
      </div>
    );
  }
}

export default TetheredDateTime;
