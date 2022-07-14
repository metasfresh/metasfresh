import { useState } from 'react';
import { mergeCalendarEntriesArrayToArray } from './utils/mergeCalendarEntriesArrayToArray';
import { mergeWSEventsToCalendarEntries } from './utils/mergeWSEventsToCalendarEntries';
import { updateEntriesFromConflicts } from './utils/updateEntriesFromConflicts';
import { mergeWSConflictChangesEvents } from './utils/mergeWSConflictChangesEvents';

export const useCalendarData = () => {
  const [state, setState] = useState({
    startDate: null,
    endDate: null,
    simulationId: null,
    entriesLoading: false,
    entries: [],
    conflicts: [],
  });

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
    return (
      state.startDate === startDate &&
      state.endDate === endDate &&
      state.simulationId === simulationId
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
    calendarIds,
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
        calendarIds,
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
              startDate,
              endDate,
              simulationId,
              entries,
              entriesLoading: false,
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
    addEntriesArray,
    applyWSEvents,
    updateEntriesFromAPI,
    setConflicts,
    getConflictsCount,
  };
};
