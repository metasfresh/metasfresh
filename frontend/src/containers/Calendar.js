import React from 'react';
import PropTypes from 'prop-types';
import FullCalendar from '@fullcalendar/react';
import timeGridPlugin from '@fullcalendar/timegrid';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';

import '@fullcalendar/core/main.css';
import '@fullcalendar/daygrid/main.css';
import '@fullcalendar/timegrid/main.css';

export default class Calendar extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      calendarWeekends: true,
      calendarEvents: [{ title: 'Current Date Event', start: new Date() }],
    };
  }

  handleDateClick = (params) => {
    if (confirm(`Would you like to add an event to ${params.dateStr}?`)) {
      this.setState({
        calendarEvents: this.state.calendarEvents.concat({
          title: 'New Event',
          start: params.date,
          allDay: params.allDay,
        }),
      });
    }
  };

  render() {
    return (
      <div className={this.props.className}>
        <FullCalendar
          defaultView="dayGridMonth"
          header={{
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek',
          }}
          plugins={[dayGridPlugin, timeGridPlugin, interactionPlugin]}
          weekends={this.state.calendarWeekends}
          events={this.state.calendarEvents}
          dateClick={this.handleDateClick}
        />
      </div>
    );
  }

  static propTypes = {
    className: PropTypes.string,
  };
}

Calendar.defaultProps = {
  className: 'container',
};
