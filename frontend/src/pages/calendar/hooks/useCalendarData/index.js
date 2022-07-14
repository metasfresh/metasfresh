import { useState } from 'react';
import { mergeCalendarEntriesArrayToArray } from './utils/mergeCalendarEntriesArrayToArray';
import { mergeWSEventsToCalendarEntries } from './utils/mergeWSEventsToCalendarEntries';
import { updateEntriesFromConflicts } from './utils/updateEntriesFromConflicts';
import { mergeWSConflictChangesEvents } from './utils/mergeWSConflictChangesEvents';
import { extractResourcesFromCalendarsArray } from './utils/extractResourcesFromCalendarsArray';

export const useCalendarData = () => {
  const [state, setState] = useState({
    calendarsArray: [],
    resourcesArray: [],
    //
    entriesQuery: { startDate: null, endDate: null, simulationId: null },
    entriesLoading: false,
    entries: [],
    //
    conflicts: [],
  });

  const setCalendars = (calendarsArray) => {
    //console.log('setCalendarsArray', { calendarsArray, state });
    setState((prevState) => ({
      ...prevState,
      calendarsArray: calendarsArray || [],
      resourcesArray: extractResourcesFromCalendarsArray(calendarsArray),
    }));
  };

  const getCalendarIds = () => {
    return state.calendarsArray.map((calendar) => calendar.calendarId);
  };

  const getResourcesArray = () => {
    //console.log('getResourcesArray', { state });
    // IMPORTANT: don't copy it because we don't want to trigger a "react change"
    return state.resourcesArray;
  };

  const updateStateEntriesAndConflicts = (entriesMapper, conflictsMapper) => {
    setState((prevState) => {
      const prevEntries = prevState.entries;
      let newEntries = entriesMapper ? entriesMapper(prevEntries) : prevEntries;

      const prevConflicts = prevState.conflicts;
      const newConflicts = conflictsMapper
        ? conflictsMapper(prevConflicts)
        : prevConflicts;

      if (prevEntries === newEntries && prevConflicts === newConflicts) {
        return prevState;
      }

      newEntries = updateEntriesFromConflicts(newEntries, newConflicts);

      return { ...prevState, entries: newEntries, conflicts: newConflicts };
    });
  };

  const isStateMatchingQuery = ({ startDate, endDate, simulationId }) => {
    console.log('isStateMatchingQuery', { state });
    return (
      state.entriesQuery.startDate === startDate &&
      state.entriesQuery.endDate === endDate &&
      state.entriesQuery.simulationId === simulationId
    );
  };

  const addEntriesArray = (entriesToAddArray) => {
    console.groupCollapsed('addEntriesArray', { entriesToAddArray });

    updateStateEntriesAndConflicts(
      (prevEntries) =>
        mergeCalendarEntriesArrayToArray(prevEntries, entriesToAddArray),
      (prevConflicts) => prevConflicts
    );

    console.groupEnd();
  };

  const applyWSEvents = (wsEvents) => {
    console.groupCollapsed('applyWSEvents', { wsEvents });

    updateStateEntriesAndConflicts(
      (prevEntries) =>
        mergeWSEventsToCalendarEntries(prevEntries, wsEvents.entryEvents),
      (prevConflicts) =>
        mergeWSConflictChangesEvents(prevConflicts, wsEvents.conflictEvents)
    );

    console.groupEnd();
  };

  const updateEntriesFromAPI = ({
    startDate,
    endDate,
    simulationId,
    fetchFromAPI,
    onFetchSuccess,
    onFetchError,
  }) => {
    if (
      state.entriesLoading ||
      isStateMatchingQuery({ startDate, endDate, simulationId })
    ) {
      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      onFetchSuccess(state.entries);
    } else {
      setState((prevState) => ({ ...prevState, entriesLoading: true }));

      fetchFromAPI({
        calendarIds: getCalendarIds(),
        simulationId,
        startDate,
        endDate,
      })
        .then((entriesFromAPI) => {
          setState((prevState) => {
            const conflicts = prevState.conflicts;
            const entries = updateEntriesFromConflicts(
              entriesFromAPI,
              conflicts
            );
            //onFetchSuccess(entries);

            return {
              entriesQuery: { startDate, endDate, simulationId },
              entriesLoading: false,
              entries,
              conflicts,
            };
          });
        })
        .catch((error) => {
          console.log('got error', error);
          setState((prevState) => ({ ...prevState, entriesLoading: false }));
          onFetchError(error);
        });
    }
  };

  const setConflicts = (conflictsArray) => {
    console.groupCollapsed('setConflicts', { conflictsArray });

    updateStateEntriesAndConflicts(
      (prevEntries) => prevEntries,
      () => conflictsArray
    );

    console.groupEnd();
  };

  const getConflictsCount = () => {
    return state.conflicts?.length ?? 0;
  };

  return {
    setCalendars,
    getResourcesArray,
    //
    addEntriesArray,
    updateEntriesFromAPI,
    applyWSEvents,
    //
    setConflicts,
    getConflictsCount,
  };
};
