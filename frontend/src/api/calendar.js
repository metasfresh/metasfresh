/*
 * #%L
 * metasfresh
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import axios from 'axios';

const extractAxiosResponseData = (axiosReponse) => axiosReponse.data;

export const getAvailableCalendars = () => {
  return axios
    .get(`${config.API_URL}/calendars/available`)
    .then(extractAxiosResponseData)
    .then(({ calendars }) => calendars.map(converters.fromAPICalendar));
};

export const getCalendarEvents = ({
  calendarIds = null,
  startDate = null,
  endDate = null,
}) => {
  return axios
    .post(`${config.API_URL}/calendars/entries/query`, {
      calendarIds: calendarIds || [],
      startDate,
      endDate,
    })
    .then(extractAxiosResponseData)
    .then(({ entries }) => entries.map(converters.fromAPIEvent));
};

export const addOrUpdateCalendarEvent = ({
  id,
  calendarId,
  resourceId,
  startDate,
  endDate,
  title,
  description = null,
}) => {
  const url = !id
    ? `${config.API_URL}/calendars/entries/add`
    : `${config.API_URL}/calendars/entries/${id}`;

  return axios
    .post(url, {
      calendarId,
      resourceId,
      startDate,
      endDate,
      title,
      description,
    })
    .then(extractAxiosResponseData)
    .then(converters.fromAPIEvent);
};

export const deleteCalendarEventById = (eventId) => {
  return axios
    .delete(`${config.API_URL}/calendars/entries/${eventId}`)
    .then(extractAxiosResponseData);
};

//
//
// Converters
//
//

const converters = {
  fromAPICalendar: (calendar) => ({
    calendarId: calendar.calendarId,
    name: calendar.name,
    resources: calendar.resources.map(converters.fromAPIResource),
  }),

  fromAPIResource: (resource) =>
    converters.fullcalendar_io.fromAPIResource(resource),

  fromAPIEvent: (entry) => ({
    calendarId: entry.calendarId,
    ...converters.fullcalendar_io.fromAPIEvent(entry),
  }),

  /**
   * https://fullcalendar.io converters
   */
  fullcalendar_io: {
    fromAPIResource: (resource) => ({
      id: resource.resourceId,
      title: resource.name,
    }),

    fromAPIEvent: (entry) => ({
      id: entry.entryId,
      title: entry.title,
      start: entry.startDate,
      end: entry.endDate,
      resourceId: entry.resourceId,
    }),
  },
};
