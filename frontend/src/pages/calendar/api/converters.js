const converters = {
  fromAPICalendar: (apiCalendar) => ({
    calendarId: apiCalendar.calendarId,
    name: apiCalendar.name,
    resources: apiCalendar.resources.map(converters.fromAPIResource),
  }),

  fromAPIResource: (apiResource) =>
    converters.fullcalendar_io.fromAPIResource(apiResource),

  fromAPIUpdateResult: (apiUpdateResult) => {
    //console.log('fromAPIUpdateResult', { apiUpdateResult });
    return [
      converters.fromAPIEntry(apiUpdateResult.changedEntry),
      ...apiUpdateResult.otherChangedEntries.map((entry) =>
        converters.fromAPIEntry(entry)
      ),
    ];
  },

  fromAPIEntry: (apiEntry) => {
    //console.log('fromAPIEntry', { apiEntry });
    return {
      calendarId: apiEntry.calendarId,
      ...converters.fullcalendar_io.fromAPIEntry(apiEntry),
    };
  },

  fromAPISimulation: (apiSimulation) => ({
    simulationId: apiSimulation.id,
    name: apiSimulation.name,
    processed: apiSimulation.processed,
  }),

  fromAPIConflict: (apiConflict) => ({
    entryId1: apiConflict.entryId1,
    entryId2: apiConflict.entryId2,
    simulationId: apiConflict.simulationId,
    status: apiConflict.status,
  }),

  fromAPIComputePlanStatus: (apiResponse) => ({
    simulationId: apiResponse.simulationId,
    status: apiResponse.status,
  }),

  //
  //
  // https://fullcalendar.io converters
  //
  //
  fullcalendar_io: {
    fromAPIResource: (apiResource) => {
      const resource = {
        id: apiResource.resourceId,
        title: apiResource.name,
      };

      // IMPORTANT: fullcalendar does not display the resource if parentId is not found (even if is null or undefined).
      if (apiResource.parentId) {
        resource.parentId = apiResource.parentId;
      }

      return resource;
    },

    fromAPIEntry: (apiEntry) => ({
      id: apiEntry.entryId,
      resourceId: apiEntry.resourceId,
      title: apiEntry.title,
      start: apiEntry.startDate,
      end: apiEntry.endDate,
      allDay: apiEntry.allDay,
      editable: apiEntry.editable,
      color: apiEntry.color,
      url: apiEntry.url,
      help: apiEntry.help,
    }),
  },
};

export default converters;
