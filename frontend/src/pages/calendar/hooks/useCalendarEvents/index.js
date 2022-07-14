import { useState } from 'react';
import { mergeCalendarEventsArrayToArray } from './utils/mergeCalendarEventsArrayToArray';
import { mergeWSEventsToCalendarEvents } from './utils/mergeWSEventsToCalendarEvents';
import { updateEventsFromConflicts } from './utils/updateEventsFromConflicts';
import { mergeWSConflictChangesEvents } from './utils/mergeWSConflictChangesEvents';

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
