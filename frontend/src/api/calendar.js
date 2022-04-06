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
    .then(({ calendars }) =>
      calendars.map((entry) => ({ id: entry.calendarId, title: entry.name }))
    );
};

const convertFromAPICalendarEntryToCalendarEvent = (entry) => ({
  title: entry.title,
  start: entry.startDate,
  end: entry.endDate,
  resourceId: entry.calendarId,
});

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
    .then(({ entries }) =>
      entries.map(convertFromAPICalendarEntryToCalendarEvent)
    );
};

export const addCalendarEvent = ({
  calendarId,
  startDate,
  endDate,
  title,
  description = null,
}) => {
  console.log('api.addCalendarEvent', {
    calendarId,
    startDate,
    endDate,
    title,
    description,
  });

  return axios
    .post(`${config.API_URL}/calendars/entries/add`, {
      calendarId,
      startDate,
      endDate,
      title,
      description,
    })
    .then(extractAxiosResponseData)
    .then(convertFromAPICalendarEntryToCalendarEvent);
};
