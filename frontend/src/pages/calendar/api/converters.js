const converters = {
  fromAPICalendar: (calendar) => ({
    calendarId: calendar.calendarId,
    name: calendar.name,
    resources: calendar.resources.map(converters.fromAPIResource),
  }),

  fromAPIResource: (resource) =>
    converters.fullcalendar_io.fromAPIResource(resource),

  fromAPIUpdateResult: (updateResult) => {
    //console.log('fromAPIUpdateResult', { updateResult });
    return [
      converters.fromAPIEntry(updateResult.changedEntry),
      ...updateResult.otherChangedEntries.map((entry) =>
        converters.fromAPIEntry(entry)
      ),
    ];
  },

  fromAPIEntry: (entry) => {
    //console.log('fromAPIEntry', { entry });
    return {
      calendarId: entry.calendarId,
      ...converters.fullcalendar_io.fromAPIEntry(entry),
    };
  },

  fromAPISimulation: (simulation) => ({
    simulationId: simulation.id,
    name: simulation.name,
  }),

  fromAPIConflict: (conflict) => ({
    entryId1: conflict.entryId1,
    entryId2: conflict.entryId2,
    simulationId: conflict.simulationId,
    status: conflict.status,
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
    }),
  },
};

export default converters;
