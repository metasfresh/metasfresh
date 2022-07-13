import React from 'react';

export const useAvailableCalendars = () => {
  const [state, setState] = React.useState({
    calendarsArray: [],
    resourcesArray: [],
  });

  return {
    setFromArray: (calendarsArray) => {
      console.log('setCalendarsArray', { calendarsArray, state });
      setState({
        calendarsArray: calendarsArray || [],
        resourcesArray: extractResourcesFromCalendarsArray(calendarsArray),
      });
    },

    getCalendarIds: () => {
      return state.calendarsArray.map((calendar) => calendar.calendarId);
    },

    getResourcesArray: () => {
      //console.log('getResourcesArray', { state });
      // IMPORTANT: don't copy it because we don't want to trigger a "react change"
      return state.resourcesArray;
    },
  };
};

const extractResourcesFromCalendarsArray = (calendars) => {
  if (!calendars) {
    return [];
  }

  const resourcesById = calendars
    .flatMap((calendar) => calendar.resources)
    .reduce((accum, resource) => {
      accum[resource.id] = resource;
      return accum;
    }, {});

  const resources = Object.values(resourcesById);

  // IMPORTANT: completely remove 'parentId' property if it's not found in our list of resources
  // Else fullcalendar.io won't render that resource at all.
  resources.forEach((resource) => {
    if ('parentId' in resource && !resourcesById[resource.parentId]) {
      console.log('removing parentId because was not found: ', resource);
      delete resource.parentId;
    }
  });
  //console.log('extractResourcesFromCalendarsArray', resources);

  return resources;
};
