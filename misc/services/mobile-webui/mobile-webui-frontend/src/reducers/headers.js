import * as types from '../constants/HeaderActionTypes';

export const initialState = {
  entries: [
    {
      location: '/',
      hidden: true,
      values: [],
    },
  ],
};

const launchersUrlRegExp = /\/launchers\/[A-Z]\w+/gi;

export default function reducer(state = initialState, action) {
  const { payload } = action;

  switch (action.type) {
    case types.HEADER_PUSH_ENTRY: {
      const { location, values } = payload;
      // if there are no header values, there's no reason to block space
      const hidden = values.length ? false : true;

      let existingEntryUpdated = false;
      let newEntries = state.entries.map((entry) => {
        if (entry.location === location) {
          existingEntryUpdated = true;
          return { ...entry, values, hidden };
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
        const newEntry = { location, values, hidden };
        newEntries.push(newEntry);
        // console.log('added newEntry: ', newEntry);
      }

      // console.log('HEADER_PUSH_ENTRY=>newEntries:', newEntries);
      return { ...state, entries: newEntries };
    }

    case '@@router/LOCATION_CHANGE': {
      const {
        location: { pathname },
      } = payload;

      let newEntries = null;

      // clear header on main urls
      if (pathname === '/' || launchersUrlRegExp.test(pathname)) {
        newEntries = [...initialState.entries];
      } else {
        newEntries = removeEntries({
          entriesArray: state.entries,
          startLocation: pathname,
          inclusive: false,
        });
      }

      // console.log('@@router/LOCATION_CHANGE: pathname=%o', pathname);
      // console.log('@@router/LOCATION_CHANGE=>newEntries:', newEntries);
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
