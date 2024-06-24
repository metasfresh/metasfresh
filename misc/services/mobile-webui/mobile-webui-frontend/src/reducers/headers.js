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

const launchersUrlRegExp = /\/\w+\/launchers/gi;

const isLaunchersPathname = (pathname) => launchersUrlRegExp.test(pathname);

const getHeaderEntries = (state) => state.headers.entries ?? [];

export const getEntryItemsFromState = (state) => {
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

export const getCaptionFromHeaders = (state) => {
  // return last known caption
  return state.headers.entries.reduce((acc, entry) => (entry.caption ? entry.caption : acc), null);
};

export const getUserInstructionsFromHeaders = (state) => {
  // return last known caption
  return state.headers.entries.reduce((acc, entry) => (entry.userInstructions ? entry.userInstructions : acc), null);
};

export const useHomeLocation = () => {
  const { url: currentLocation } = useRouteMatch();
  return useSelector((state) => getHomeLocation({ state, currentLocation }), shallowEqual);
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

export default function reducer(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.HEADER_PUSH_ENTRY: {
      const { location, caption, values, userInstructions, isHomeStop, homeIconClassName } = payload;

      // if there are no header values, there's no reason to block space
      const hidden = !values.length;

      //
      // Search by location and update an existing entry if possible
      let existingEntryUpdated = false;
      let newEntries = state.entries.map((entry) => {
        if (entry.location === location) {
          existingEntryUpdated = true;
          return { ...entry, caption, values, userInstructions, isHomeStop, homeIconClassName, hidden };
        } else {
          return entry;
        }
      });

      if (existingEntryUpdated) {
        newEntries = removeEntries({
          entriesArray: newEntries,
          startLocation: location,
          inclusive: false,
        });
      } else {
        const newEntry = { location, caption, values, userInstructions, isHomeStop, homeIconClassName, hidden };
        newEntries.push(newEntry);
        // console.log('added newEntry: ', newEntry);
      }

      // console.log('HEADER_PUSH_ENTRY=>newEntries:', newEntries);
      return { ...state, entries: newEntries };
    }

    case LOCATION_CHANGE: {
      const {
        location: { pathname },
      } = payload;

      let newEntries = null;

      // clear header on main urls
      if (pathname === '/' || isLaunchersPathname(pathname)) {
        newEntries = [...initialState.entries];
        //console.log('HEADERS: @@router/LOCATION_CHANGE: CLEAR!!!');
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
