import { useState } from 'react';
import { isSameMoment } from '../utils/calendarUtils';
import {
  WSEventType_addOrChange,
  WSEventType_remove,
} from './useCalendarWebsocketEvents';

export const useCalendarEvents = () => {
  const [state, setState] = useState({
    startDate: null,
    endDate: null,
    simulationId: null,
    eventsLoading: false,
    events: [],
    conflicts: [],
  });

  const updateStateEventsAndConflicts = (eventsMapper, conflictsMapper) => {
    setState((prevState) => {
      const prevEvents = prevState.events;
      let newEvents = eventsMapper ? eventsMapper(prevEvents) : prevEvents;

      const prevConflicts = prevState.conflicts;
      const newConflicts = conflictsMapper
        ? conflictsMapper(prevConflicts)
        : prevConflicts;

      if (prevEvents === newEvents && prevConflicts === newConflicts) {
        return prevState;
      }

      newEvents = updateEventsFromConflicts(newEvents, newConflicts);

      return { ...prevState, events: newEvents, conflicts: newConflicts };
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
    console.groupCollapsed('addEventsArray', { eventsArrayToAdd });

    updateStateEventsAndConflicts(
      (prevEvents) =>
        mergeCalendarEventsArrayToArray(prevEvents, eventsArrayToAdd),
      (prevConflicts) => prevConflicts
    );

    console.groupEnd();
  };

  const applyWSEvents = (wsEvents) => {
    console.groupCollapsed('applyWSEvents', { wsEvents });

    updateStateEventsAndConflicts(
      (prevEvents) =>
        mergeWSEventsToCalendarEvents(prevEvents, wsEvents.entryEvents),
      (prevConflicts) =>
        mergeWSConflictChangesEvents(prevConflicts, wsEvents.conflictEvents)
    );

    console.groupEnd();
  };

  const updateEventsFromAPI = ({
    calendarIds,
    startDate,
    endDate,
    simulationId,
    fetchFromAPI,
    onFetchSuccess,
    onFetchError,
  }) => {
    if (
      state.eventsLoading ||
      isStateMatchingQuery({ startDate, endDate, simulationId })
    ) {
      //console.log('updateEventsAndGet: already fetched');

      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      onFetchSuccess(state.events);
    } else {
      //console.log('updateEventsAndGet: start fetching from supplier: ', newEventsSupplier);

      setState((prevState) => ({ ...prevState, eventsLoading: true }));

      fetchFromAPI({
        calendarIds,
        simulationId,
        startDate,
        endDate,
      })
        .then((eventsFromAPI) => {
          //console.log('fetchCalendarEvents: got result', { events });

          setState((prevState) => {
            const conflicts = prevState.conflicts;
            const events = updateEventsFromConflicts(eventsFromAPI, conflicts);
            //onFetchSuccess(events);

            return {
              startDate,
              endDate,
              simulationId,
              events,
              eventsLoading: false,
              conflicts,
            };
          });
        })
        .catch((error) => {
          console.log('got error', error);
          setState((prevState) => ({ ...prevState, eventsLoading: false }));
          onFetchError(error);
        });
    }
  };

  const setConflicts = (conflictsArray) => {
    console.groupCollapsed('setConflicts', { conflictsArray });

    updateStateEventsAndConflicts(
      (prevEvents) => prevEvents,
      () => conflictsArray
    );

    console.groupEnd();
  };

  const getConflictsCount = () => {
    return state.conflicts?.length ?? 0;
  };

  return {
    addEventsArray,
    applyWSEvents,
    updateEventsFromAPI,
    setConflicts,
    getConflictsCount,
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
  console.groupCollapsed('mergeWSEventsToCalendarEvents', {
    eventsArray,
    wsEventsArray,
  });

  if (!wsEventsArray || wsEventsArray.length === 0) {
    console.log('=> empty wsEventsArray => NO CHANGE');
    console.groupEnd();
    return eventsArray;
  }

  const resultEventsById = [];
  if (eventsArray) {
    eventsArray.forEach((event) => {
      resultEventsById[event.id] = event;
    });
  }

  let hasChanges = false;
  wsEventsArray.forEach((wsEvent) => {
    if (wsEvent.type === WSEventType_addOrChange) {
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
    } else if (wsEvent.type === WSEventType_remove) {
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
    console.groupEnd();
    return eventsArray;
  }

  console.log('=> new array of ', resultEventsById);
  console.groupEnd();
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
    event1.url === event2.url &&
    !!event1.conflict === !!event2.conflict
  );
};

const updateEventsFromConflicts = (eventsArray, conflictsArray) => {
  if (!eventsArray) {
    return eventsArray;
  }

  const entryIdsWithConflicts = [];
  if (conflictsArray) {
    conflictsArray.forEach((conflict) => {
      if (conflict.status === 'CONFLICT') {
        entryIdsWithConflicts.push(conflict.entryId1);
        entryIdsWithConflicts.push(conflict.entryId2);
      }
    });
  }

  let hasChanges = false;
  const changedEvents = eventsArray.map((event) => {
    const conflictFlagNew = entryIdsWithConflicts.includes(event.id);
    if (!!event.conflict === conflictFlagNew) {
      return event;
    } else {
      const newEvent = {
        ...event,
        conflict: conflictFlagNew,
      };
      hasChanges = true;
      return newEvent;
    }
  });

  if (!hasChanges) {
    return eventsArray;
  }

  console.log('updateEventsFromConflicts', {
    changedEvents,
    eventsArray,
    conflictsArray,
  });
  return changedEvents;
};

const mergeWSConflictChangesEvents = (conflictsArray, wsEventsArray) => {
  if (!wsEventsArray || wsEventsArray.length === 0) {
    return conflictsArray;
  }

  let changedConflictsArray = conflictsArray;
  wsEventsArray.forEach((wsEvent) => {
    changedConflictsArray = mergeSingleWSConflictChangesEvent(
      changedConflictsArray,
      wsEvent
    );
  });

  return changedConflictsArray;
};

const mergeSingleWSConflictChangesEvent = (conflictsArray, wsEvent) => {
  console.groupCollapsed('mergeSingleWSConflictChangesEvent', {
    conflictsArray,
    wsEvent,
  });

  //
  // Remove conflicts which are affected by this event
  const affectedEntryIds = wsEvent.affectedEntryIds;
  let changedConflictsArray = conflictsArray.filter(
    (conflict) =>
      !affectedEntryIds.includes(conflict.entryId1) &&
      !affectedEntryIds.includes(conflict.entryId2)
  );
  console.log('conflicts after removing affected:', changedConflictsArray);

  //
  // Add conflicts from this event
  changedConflictsArray = [...changedConflictsArray, ...wsEvent.conflicts];
  console.log('conflicts after adding new ones:', changedConflictsArray);

  console.groupEnd();
  return changedConflictsArray;
};
