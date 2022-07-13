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
      converters.fromAPIEvent(updateResult.changedEntry),
      ...updateResult.otherChangedEntries.map((entry) =>
        converters.fromAPIEvent(entry)
      ),
    ];
  },

  fromAPIEvent: (entry) => {
    //console.log('fromAPIEvent', { entry });
    return {
      calendarId: entry.calendarId,
      ...converters.fullcalendar_io.fromAPIEvent(entry),
    };
  },

  fromAPISimulation: (simulation) => ({
    simulationId: simulation.id,
    name: simulation.name,
  }),

  // see de.metas.ui.web.calendar.json.JsonWebsocketEvent
  fromAPIWebsocketEvent: (wsEvent) => ({
    type: wsEvent.type,
    entry: wsEvent.entry ? converters.fromAPIEvent(wsEvent.entry) : null,
    entryId: wsEvent.entryId,
    simulationId: wsEvent.simulationId,
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

    fromAPIEvent: (apiEntry) => ({
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
