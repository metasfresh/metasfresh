import * as types from '../constants/HeaderActionTypes';
import { shallowEqual, useSelector } from 'react-redux';
import { useRouteMatch } from 'react-router-dom';
import { LOCATION_CHANGE } from 'connected-react-router';

const DEFAULT_homeIconClassName = 'fas fa-home';
export const initialState = {
  entries: [
    {
      location: '/',
      hidden: true,
      values: [],
      userInstructions: null,
      isHomeStop: true,
      homeIconClassName: DEFAULT_homeIconClassName,
    },
  ],
};

const launchersUrlRegExp = /\/\w+\/launchers\/?$/i;

const isLaunchersPathname = (pathname) => launchersUrlRegExp.test(pathname);

const getHeaderEntries = (state) => state.headers.entries ?? [];

const getEntryItemsFromState = (state) => {
  const headersEntries = getHeaderEntries(state);

  let nextUniqueId = 1;
  const itemsByKey = {};

  headersEntries
    .filter((headersEntry) => !headersEntry.hidden && Array.isArray(headersEntry.values))
    .reduce((acc, headersEntry) => acc.concat(headersEntry.values), [])
    .filter((entryItem) => !entryItem.hidden)
    .forEach((entryItem) => {
      const caption = entryItem.caption;
      let key;
      if (!caption) {
        key = 'unique-' + nextUniqueId++;
      } else {
        key = caption;
      }

      itemsByKey[key] = entryItem;
    });

  return Object.values(itemsByKey);
};

const getCaptionFromHeaders = (state) => {
  // return last known caption
  return state.headers.entries.reduce((acc, entry) => (entry.caption ? entry.caption : acc), null);
};

const getUserInstructionsFromHeaders = (state) => {
  // return last known caption
  return state.headers.entries.reduce((acc, entry) => (entry.userInstructions ? entry.userInstructions : acc), null);
};

export const useHeaders = () => {
  return useSelector((state) => ({
    userInstructions: getUserInstructionsFromHeaders(state),
    entryItems: getEntryItemsFromState(state),
  }));
};

const getHomeLocation = ({ state, currentLocation }) => {
  const headersEntries = getHeaderEntries(state);
  //console.log('getHomeLocation', { headersEntries, currentLocation });

  for (let i = headersEntries.length - 1; i >= 0; i--) {
    const entry = headersEntries[i];

    if (entry.location === currentLocation) {
      continue;
    }

    if (entry.isHomeStop) {
      //console.log('getHomeLocation - returning', { entry });
      return {
        location: entry.location,
        iconClassName: entry.homeIconClassName ?? DEFAULT_homeIconClassName,
      };
    }
  }

  // shall not happen because we shall get to first element which is set by initialState
  //console.log('getHomeLocation - returning default');
  return { location: '/', iconClassName: DEFAULT_homeIconClassName };
};

const getBackLocation = ({ state }) => {
  const headersEntries = getHeaderEntries(state);

  for (let i = headersEntries.length - 1; i >= 0; i--) {
    const entry = headersEntries[i];

    if (!entry.backLocation) {
      continue;
    }

    // console.log(`getBackLocation - returning ${entry.backLocation}`, { entry, i, headersEntries });
    return entry.backLocation;
  }

  // console.log(`getBackLocation - no back location found, returning null`, { headersEntries });
  return null;
};

export const useBackLocationFromHeaders = () => {
  return useSelector((state) => getBackLocation({ state }), shallowEqual);
};

export const useNavigationInfoFromHeaders = () => {
  const { url: currentLocation } = useRouteMatch();

  return useSelector(
    (state) => ({
      caption: getCaptionFromHeaders(state),
      homeLocation: getHomeLocation({ state, currentLocation }),
    }),
    shallowEqual
  );
};

export default function reducer(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.HEADER_PUSH_ENTRY: {
      const newEntries = createOrUpdateEntry({ entries: state.entries, payload, isUpdateOnly: false });
      return { ...state, entries: newEntries };
    }
    case types.HEADER_UPDATE_ENTRY: {
      const newEntries = createOrUpdateEntry({ entries: state.entries, payload, isUpdateOnly: true });
      return { ...state, entries: newEntries };
    }

    case LOCATION_CHANGE: {
      const {
        location: { pathname },
      } = payload;

      let newEntries = null;

      // clear header on main urls
      const isLaunchersScreen = isLaunchersPathname(pathname);
      if (pathname === '/') {
        newEntries = [...initialState.entries];
        // console.log('cleared header entries for / url', { pathname, action });
      } else if (isLaunchersScreen) {
        newEntries = [...initialState.entries];
        // console.log('cleared header entries for launchers url', { pathname, action });
      } else {
        newEntries = removeEntries({
          entriesArray: state.entries,
          startLocation: pathname,
          inclusive: false,
        });
      }

      //console.log('HEADERS: @@router/LOCATION_CHANGE: pathname=%o', pathname);
      //console.log('HEADERS: @@router/LOCATION_CHANGE=>newEntries:', newEntries);
      return { ...state, entries: newEntries };
    }

    default: {
      return state;
    }
  }
}

const createOrUpdateEntry = ({ entries, payload, isUpdateOnly }) => {
  const { location } = payload;
  const hidden = payload.hidden || !payload.values?.length; // if there are no header values, there's no reason to block space
  const newEntryValues = { ...payload, hidden };

  //
  // Search by location and update an existing entry if possible
  let existingEntryUpdated = false;
  let newEntries = entries.map((entry) => {
    if (entry.location === location) {
      existingEntryUpdated = true;
      return mergeEntries(entry, newEntryValues);
    } else {
      return entry;
    }
  });

  //
  // If we are allowed to also create/remove entries then do so
  if (!isUpdateOnly) {
    if (existingEntryUpdated) {
      newEntries = removeEntries({
        entriesArray: newEntries,
        startLocation: location,
        inclusive: false,
      });
    } else {
      const newEntry = mergeEntries({ location }, newEntryValues);
      newEntries.push(newEntry);
      // console.log('added newEntry: ', newEntry);
    }
  } else {
    if (!existingEntryUpdated) {
      // console.log('Ignoring HEADER_UPDATE_ENTRY because no existing entry was found', {
      //   payload,
      //   isUpdateOnly,
      //   entries,
      // });

      return entries;
    }
  }

  return newEntries;
};

const mergeEntries = (entry, newValues) => {
  const newEntry = { ...entry };

  Object.keys(newValues).forEach((key) => {
    if (key === 'values') {
      newEntry[key] = mergeEntryValues(entry[key], newValues[key]);
    } else {
      const newValue = newValues[key];
      if (newValue !== undefined) {
        newEntry[key] = newValue;
      }
    }
  });

  return newEntry;
};

const mergeEntryValues = (valuesArray, newValuesArray) => {
  if (!newValuesArray?.length) return valuesArray ? [...valuesArray] : [];
  if (!valuesArray?.length) return [...newValuesArray];

  const newValuesByCaption = newValuesArray.reduce((accum, value) => {
    accum[value.caption] = value;
    return accum;
  }, {});

  const result = [];

  valuesArray.forEach((value) => {
    const caption = value.caption;
    const newValue = newValuesByCaption[caption];
    delete newValuesByCaption[caption];
    if (newValue) {
      result.push(newValue);
    } else {
      result.push(value);
    }
  });

  newValuesArray.forEach((value) => {
    const caption = value.caption;
    const newValue = newValuesByCaption[caption];
    delete newValuesByCaption[caption];
    if (newValue) {
      result.push(newValue);
    }
  });

  return result;
};

const removeEntries = ({ entriesArray, startLocation, inclusive }) => {
  let removeEntry = false;
  return entriesArray.reduce((accum, entry) => {
    if (removeEntry) {
      // don't copy the entry to accum
      // console.log('removing entry: ', entry);
    } else if (entry.location === startLocation) {
      removeEntry = true;

      if (!inclusive) {
        accum.push(entry);
      } else {
        // console.log('removing entry: ', entry);
      }
    } else {
      accum.push(entry);
    }

    return accum;
  }, []);
};
