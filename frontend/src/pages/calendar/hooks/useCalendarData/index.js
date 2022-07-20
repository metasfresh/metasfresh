import { useEffect, useMemo, useState } from 'react';
import { mergeCalendarEntriesArrayToArray } from './utils/mergeCalendarEntriesArrayToArray';
import { mergeWSEntryEvents } from './utils/mergeWSEntryEvents';
import { updateEntriesFromConflicts } from './utils/updateEntriesFromConflicts';
import { mergeWSConflictChangesEvents } from './utils/mergeWSConflictChangesEvents';
import { extractCalendarIdsFromArray } from './utils/extractCalendarIdsFromArray';
import { isEqualEntryQueries, newEntryQuery } from './utils/entryQuery';
import { computeResources } from './utils/computeResources';

export const useCalendarData = ({
  simulationId: initialSimulationId,
  onlyResourceIds,
  onlyProjectId,
  fetchAvailableSimulationsFromAPI,
  fetchEntriesFromAPI,
  fetchConflictsFromAPI,
}) => {
  const [availableSimulations, setAvailableSimulations] = useState([]);
  const [simulationId, setSimulationId] = useState(initialSimulationId);

  const [calendars, setCalendars] = useState([]);
  const [resources, setResources] = useState([]);

  const [entries, setEntries] = useState({
    loading: false,
    query: null,
    array: [],
  });

  const [conflicts, setConflicts] = useState([]);

  useEffect(() => loadSimulationsFromAPI(), []);
  useEffect(
    () => loadConflictsFromAPI(),
    [simulationId, onlyResourceIds, onlyProjectId]
  );

  useEffect(() => {
    setResources(
      computeResources({ calendars, entries: entries.array, conflicts })
    );
  }, [calendars, entries.array, conflicts]);

  //
  //
  //
  //
  //

  const conflictsCount = useMemo(
    () => resources.reduce((sum, resource) => sum + resource.conflictsCount, 0),
    [resources]
  );

  //
  //
  //
  //
  //

  const loadSimulationsFromAPI = () => {
    console.log(`Loading simulations, including ${simulationId}...`);

    fetchAvailableSimulationsFromAPI({
      alwaysIncludeId: simulationId,
    }).then((simulations) => {
      console.log('Loaded simulations', { simulations });
      setAvailableSimulations(simulations);
    });
  };

  const addSimulationAndSelect = (newSimulation) => {
    setAvailableSimulations((prevAvailableSimulations) => [
      ...prevAvailableSimulations,
      newSimulation,
    ]);

    setSimulationId(newSimulation.simulationId);
  };

  const updateEntries = (entriesMapper) => {
    setEntries((prevState) => {
      const prevArray = prevState.array;
      const array = entriesMapper(prevArray);
      return prevArray === array ? prevState : { ...prevState, array };
    });
  };

  const setEntriesLoadingStart = ({ query }) => {
    //console.log('setEntriesLoadingStart', { query, prevQuery: entries.query });
    setEntries((prevState) => ({ ...prevState, loading: true, query }));
  };

  const setEntriesLoadingDone = ({ error, query, array, onFetchSuccess }) => {
    setEntries((prevEntries) => {
      // Discard result if the query is not matching
      if (
        prevEntries.query != null &&
        !isEqualEntryQueries(prevEntries.query, query)
      ) {
        console.log('Discarded entriesArray because query was not matching', {
          error,
          query,
          array,
          prevEntries,
        });
        return prevEntries;
      }

      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      onFetchSuccess && onFetchSuccess(array);

      console.log('setEntriesLoadingDone', { error, query, array });
      return { ...prevEntries, loading: false, error, query, array };
    });
  };

  const addEntriesArray = (entriesToAddArray) => {
    console.groupCollapsed('addEntriesArray', { entriesToAddArray });

    updateEntries((prevEntries) =>
      mergeCalendarEntriesArrayToArray(prevEntries, entriesToAddArray)
    );

    console.groupEnd();
  };

  const refreshEntriesFromAPI = () => {
    console.log('Refreshing entries...', { entries });
    if (!entries.query) {
      return;
    }

    loadEntriesFromAPI({
      startDate: entries.query.startDate,
      endDate: entries.query.endDate,
      forceRefresh: true,
    });
  };

  const loadEntriesWithConflicts = ({
    startDate,
    endDate,
    onFetchSuccess = null,
  }) => {
    loadEntriesFromAPI({
      startDate,
      endDate,
      onFetchSuccess: (entriesArray) => {
        const entriesWithConflicts = updateEntriesFromConflicts(
          entriesArray,
          conflicts
        );

        //console.log('Sending entriesWithConflicts', { entriesWithConflicts });
        onFetchSuccess(entriesWithConflicts);
      },
    });
  };

  const loadEntriesFromAPI = ({
    startDate,
    endDate,
    forceRefresh = false,
    onFetchSuccess = null,
  }) => {
    const query = newEntryQuery({
      calendarIds: extractCalendarIdsFromArray(calendars),
      simulationId,
      onlyResourceIds,
      onlyProjectId,
      startDate,
      endDate,
    });
    //console.log('Start loading entries...', { query, forceRefresh, entries });

    // If still loading, do nothing
    if (entries.loading) {
      //console.log('still loading, do nothing');
      return;
    }

    // If params matching our query (and not a force refresh)
    // => send current entries
    if (!forceRefresh && isEqualEntryQueries(entries.query, query)) {
      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      onFetchSuccess && onFetchSuccess(entries.array);
      return;
    }

    //
    // Load new entries
    setEntriesLoadingStart({ query });
    setEntries((prevState) => ({ ...prevState, loading: true, query }));
    fetchEntriesFromAPI(query)
      .then((entriesFromAPI) => {
        setEntriesLoadingDone({
          error: false,
          query,
          array: entriesFromAPI,
        });
      })
      .catch((error) => {
        console.log('Got error while loading entries', error);
        setEntriesLoadingDone({
          error: true,
          query,
          array: [],
        });
      });
  };

  const loadConflictsFromAPI = () => {
    console.log('Loading conflicts...', {
      simulationId,
      onlyResourceIds,
      onlyProjectId,
    });

    fetchConflictsFromAPI({
      simulationId,
      onlyResourceIds,
      onlyProjectId,
    }).then(setConflicts);
  };

  const applyWSEvents = (wsEvents) => {
    console.groupCollapsed('applyWSEvents', { wsEvents, simulationId });

    const changedSimulationIds = wsEvents?.changedSimulationIds ?? [];
    console.log('changedSimulationIds', changedSimulationIds);
    if (changedSimulationIds.length > 0) {
      loadSimulationsFromAPI();
    }

    if (simulationId && changedSimulationIds.includes(simulationId)) {
      refreshEntriesFromAPI();
      loadConflictsFromAPI();
    } else {
      setConflicts((prevConflicts) =>
        mergeWSConflictChangesEvents(prevConflicts, wsEvents.conflictEvents)
      );

      updateEntries((prevEntries) =>
        mergeWSEntryEvents(prevEntries, wsEvents.entryEvents)
      );
    }

    console.groupEnd();
  };

  return {
    setCalendars,
    getResourcesArray: () => resources, // IMPORTANT: don't copy it because we don't want to trigger a "react change"
    //
    loadSimulationsFromAPI,
    getSimulationsArray: () => availableSimulations, // IMPORTANT: don't copy it because we don't want to trigger a "react change"
    getSimulationId: () => simulationId,
    setSimulationId,
    addSimulationAndSelect,
    //
    addEntriesArray,
    loadEntriesFromAPI,
    loadEntriesWithConflicts,
    //
    getConflictsCount: () => conflictsCount,
    //
    applyWSEvents,
  };
};
