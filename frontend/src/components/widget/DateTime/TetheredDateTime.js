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

  openCalendar = (e) => {
    //
    // In case we are about to open the calendar,
    // and we deal with a date with time
    // and there is no selected date yet
    // then make sure the view time is set to 23:59.
    const { timeFormat } = this.props;
    const { open, selectedDate } = this.state;
    if (!open && timeFormat && !selectedDate) {
      this.setState(({ viewDate }) => ({
        viewDate: this.convertMomentToEndOfDay(viewDate),
      }));
    }

    super.openCalendar(e);
  };

  convertMomentToEndOfDay = (date) => {
    let dateAtEndOfDay = date;
    if (!dateAtEndOfDay) {
      dateAtEndOfDay = this.localMoment();
    }

    dateAtEndOfDay.hours(23);
    dateAtEndOfDay.minutes(59);
    dateAtEndOfDay.seconds(59);
    dateAtEndOfDay.milliseconds(999);

    return dateAtEndOfDay;
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
