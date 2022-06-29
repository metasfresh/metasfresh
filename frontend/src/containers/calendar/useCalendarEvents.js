import React from 'react';
import { isSameMoment } from './calendarUtils';

export const useCalendarEvents = () => {
  const [state, setState] = React.useState({
    startDate: null,
    endDate: null,
    simulationId: null,
    events: [],
  });

  const setStateEvents = (eventsMapper) => {
    setState((prevState) => {
      const prevEvents = prevState.events;
      const newEvents = eventsMapper(prevEvents);
      if (prevEvents === newEvents) {
        console.log('NO events changed', { prevEvents });
        return prevState;
      } else {
        console.log('changed events', { newEvents, prevEvents });
      }

      return { ...prevState, events: newEvents };
    });
  };

  const isStateMatchingQuery = ({ startDate, endDate, simulationId }) => {
    return (
      state.startDate === startDate &&
      state.endDate === endDate &&
      state.simulationId === simulationId
    );
  };

  const addEventsArray = (eventsArrayToAdd) => {
    console.log('addEventsArray', { eventsArrayToAdd });
    setStateEvents((prevEvents) =>
      mergeCalendarEventsArrayToArray(prevEvents, eventsArrayToAdd)
    );
  };

  const applyWSEventsArray = (wsEventsArray) => {
    console.log('applyWSEvents', { wsEventsArray });
    setStateEvents((prevEvents) =>
      mergeWSEventsToCalendarEvents(prevEvents, wsEventsArray)
    );
  };

  const updateEventsAndGet = ({
    calendarIds,
    startDate,
    endDate,
    simulationId,
    newEventsSupplier,
  }) => {
    if (isStateMatchingQuery({ startDate, endDate, simulationId })) {
      //console.log('updateEventsAndGet: already fetched');

      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      return Promise.resolve(state.events);
    } else {
      //console.log('updateEventsAndGet: start fetching from supplier: ', newEventsSupplier);

      newEventsSupplier({
        calendarIds,
        simulationId,
        startDate,
        endDate,
      }).then((events) => {
        //console.log('fetchCalendarEvents: got result', { events });
        setState({
          startDate,
          endDate,
          simulationId,
          events,
        });

        return events;
      });
    }
  };

  return {
    addEventsArray,
    applyWSEventsArray,
    updateEventsAndGet,
  };
};

//
//
// Helper static functions
//
//

const mergeCalendarEventsArrayToArray = (eventsArray, eventsToAdd) => {
  console.log('mergeCalendarEventsArrayToArray', { eventsArray, eventsToAdd });
  if (!eventsToAdd || eventsToAdd.length === 0) {
    console.log('=> empty eventsToAdd => NO CHANGE');
    return eventsArray;
  }

  const resultEventsById = [];
  eventsArray.forEach((event) => {
    resultEventsById[event.id] = event;
  });

  let hasChanges = false;
  eventsToAdd.forEach((event) => {
    if (!isEqualEvents(resultEventsById[event.id], event)) {
      console.log('changing event', {
        oldEvent: resultEventsById[event.id],
        newEvent: event,
      });
      resultEventsById[event.id] = event;
      hasChanges = true;
    } else {
      console.log('NOT changing event because it is the same', {
        oldEvent: resultEventsById[event.id],
        newEvent: event,
      });
    }
  });

  if (!hasChanges) {
    console.log('=> same => NO CHANGE');
    return eventsArray;
  }

  console.log('=> new array of ', resultEventsById);
  return Object.values(resultEventsById);
};

const mergeWSEventsToCalendarEvents = (eventsArray, wsEventsArray) => {
  console.log('mergeWSEventsToCalendarEvents', { eventsArray, wsEventsArray });
  if (!wsEventsArray || wsEventsArray.length === 0) {
    console.log('=> empty wsEventsArray => NO CHANGE');
    return;
  }

  const resultEventsById = [];
  if (eventsArray) {
    eventsArray.forEach((event) => {
      resultEventsById[event.id] = event;
    });
  }

  let hasChanges = false;
  wsEventsArray.forEach((wsEvent) => {
    if (wsEvent.type === 'addOrChange') {
      if (!isEqualEvents(resultEventsById[wsEvent.entry.id], wsEvent.entry)) {
        console.log('changing event', {
          oldEvent: resultEventsById[wsEvent.entry.id],
          newEvent: wsEvent.entry,
        });
        resultEventsById[wsEvent.entry.id] = wsEvent.entry;
        hasChanges = true;
      } else {
        console.log('NOT changing event because it is the same', {
          oldEvent: resultEventsById[wsEvent.entry.id],
          newEvent: wsEvent.entry,
        });
      }
    } else if (wsEvent.type === 'remove') {
      if (wsEvent.entryId in resultEventsById) {
        delete resultEventsById[wsEvent.entryId];
        hasChanges = true;
      }
    } else {
      console.warn('Unhandled websocket event: ', wsEvent);
    }
  });

  if (!hasChanges) {
    console.log('=> same => NO CHANGE');
    return eventsArray;
  }

  console.log('=> new array of ', resultEventsById);
  return Object.values(resultEventsById);
};

const isEqualEvents = (event1, event2) => {
  if (event1 === event2) {
    return true;
  }
  if (!event1 || !event2) {
    return false;
  }

  return (
    event1.calendarId === event2.calendarId &&
    event1.id === event2.id &&
    event1.resourceId === event2.resourceId &&
    event1.title === event2.title &&
    isSameMoment(event1.start, event2.start) &&
    isSameMoment(event1.end, event2.end) &&
    event1.allDay === event2.allDay &&
    event1.editable === event2.editable &&
    event1.color === event2.color &&
    event1.url === event2.url
  );
};
