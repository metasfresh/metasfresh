import React from 'react';
import DateTime from 'react-datetime';
import CalendarContainer from 'react-datetime/src/CalendarContainer';
import TetherComponent from 'react-tether';
import classnames from 'classnames';
import { debounce } from 'lodash';

// TODO: This monkeypatching that's happening here has to go at some point.
class TetheredDateTime extends DateTime {
  constructor(props) {
    super(props);

    // we set this fn as debounced to have 300ms for catching any double clicks
    // that might happen
    this.debouncedSetDate = debounce(this.debouncedSetDate, 300, {
      leading: false,
      trailing: true,
    });

    // and if doubleclick happened, we can cancel this function call and
    // only trigger the doubleclick handler
    props.setDebounced(this.debouncedSetDate);
  }

  handleClick = () => {
    this.onInputChange({
      target: {
        value: this.state.viewDate,
      },
    });
  };

  onInputKey = (e) => {
    if (
      (e.key === 'Tab' && this.props.closeOnTab) ||
      e.key === 'Enter' ||
      e.key === 'Escape'
    ) {
      this.closeCalendar();
    }
  };

  // @Override
  updateSelectedDate = (e, close) => {
    // we need to persist the event as it might be reused in the 300ms debounce wait
    e.persist();

    this.debouncedSetDate(e, close);
  };

  debouncedSetDate = (e, close) => {
    if (this.props.onFocusInput) {
      this.props.onFocusInput();
    }

    // because event is debounced, currentTarget gets lost and `react-datetime` uses it to get the day value
    e.currentTarget = e.target;

    super.updateSelectedDate(e, close);
  };

  setSelectedDateAndInputValue = ({ selectedDate, inputValue }) => {
    this.setState({ selectedDate, inputValue });
  };

  // localMoment = (date, format, props) => {
  //   const result = super.localMoment(date, format, props);
  //   //console.log('localMoment', { result, date, format, props });
  //   return result;
  // };

  render() {
    const { open, className, input, inputProps, renderInput } = this.props;
    const { inputValue, selectedDate, currentView } = this.state;

    const classNames = classnames('rdt', className, { rdtStatic: !input });

    let renderedInput = null;
    if (input) {
      const props = {
        type: 'text',
        className: 'form-control',
        onFocus: this.openCalendar,
        onChange: this.onInputChange,
        onKeyDown: this.onInputKey,
        value: inputValue,
        ...inputProps,
      };
      renderedInput = renderInput(props, this.openCalendar);
    }

    return (
      <div className={classNames}>
        <TetherComponent
          attachment="top left"
          targetAttachment="bottom left"
          constraints={[{ to: 'scrollParent' }, { to: 'window' }]}
          renderTarget={(ref) =>
            renderedInput && (
              <div ref={ref} key="i">
                {renderedInput}
              </div>
            )
          }
          renderElement={(ref) =>
            open && (
              <div
                ref={ref}
                className="ignore-react-onclickoutside rdtPicker"
                onClick={
                  (currentView === 'time' &&
                    !selectedDate &&
                    this.handleClick) ||
                  null
                }
              >
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
