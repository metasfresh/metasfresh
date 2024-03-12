import React from 'react';
import DateTime from 'react-datetime';
import CalendarContainer from 'react-datetime/src/CalendarContainer';
import TetherComponent from 'react-tether';
import classnames from 'classnames';

// TODO: This monkeypatching that's happening here has to go at some point.
class TetheredDateTime extends DateTime {
  onInputKeyDown = (e) => {
    if (
      (e.key === 'Tab' && this.props.closeOnTab) ||
      e.key === 'Enter' ||
      e.key === 'Escape'
    ) {
      this.closeCalendar();
    }
  };

  setSelectedDateAndInputValue = ({ selectedDate, inputValue }) => {
    this.setState({ selectedDate, inputValue });
  };

  render() {
    const { open, className, input, inputProps, renderInput } = this.props;
    const { inputValue, currentView } = this.state;

    const classNames = classnames('rdt', className, { rdtStatic: !input });

    let renderedInput = null;
    if (input) {
      const props = {
        ...inputProps,
        type: 'text',
        className: 'form-control',
        onFocus: this.openCalendar,
        onChange: this.onInputChange,
        onKeyDown: this.onInputKeyDown,
        value: inputValue,
      };

      renderedInput = renderInput(props, this.openCalendar);
    }

    // console.log('render', { componentProps: this.getComponentProps() });

    return (
      <div className={classNames}>
        <TetherComponent
          attachment="top left"
          targetAttachment="bottom left"
          constraints={[{ to: 'scrollParent' }, { to: 'window' }]}
          renderTarget={(ref) =>
            renderedInput && <div ref={ref}>{renderedInput}</div>
          }
          renderElement={(ref) =>
            open && (
              <div ref={ref} className="ignore-react-onclickoutside rdtPicker">
                <CalendarContainer
                  view={currentView}
                  viewProps={this.getComponentProps()}
                />
              </div>
            )
          }
        />
      </div>
    );
  }
}

export default TetheredDateTime;
