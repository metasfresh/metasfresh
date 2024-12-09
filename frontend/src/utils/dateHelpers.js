/**
 * Date conversions/transforms used across the app should reside in this file
 */

import { DATE_FIELDS } from '../constants/Constants';

import { parseDateWithCurrentTimezone } from './documentListHelper';
import MomentTZ from 'moment-timezone';
import moment from 'moment';

/**
 * @method convertDateToReadable
 * @summary Converts the value of the corresponding widgetType to a readable string date
 * @param {*} widgetType - type of widget
 * @param {*} value - value for which we need the conversion
 */
export const convertDateToReadable = (widgetType, value) => {
  if (DATE_FIELDS.indexOf(widgetType) > -1) {
    return parseDateWithCurrentTimezone(value, widgetType);
  }
  return value;
};

export const setTimezoneToMoment = (moment, timeZone) => {
  if (!timeZone) {
    return moment;
  }

  if (moment.tz) {
    if (moment.tz() === timeZone) {
      return moment;
    } else {
      return MomentTZ(moment).tz(timeZone, true);
    }
  } else {
    const momentWithTZ = MomentTZ(
      moment.format('YYYY-MM-DDTHH:mm:ss.SSS'),
      'YYYY-MM-DDTHH:mm:ss.SSS',
      true
    );
    return momentWithTZ.tz(timeZone, true);
  }
};

export const convertMomentToTimezone = (momentDate, timeZone) => {
  if (!timeZone) {
    return momentDate;
  }

  if (momentDate.tz) {
    if (momentDate.tz() === timeZone) {
      return momentDate;
    } else {
      return MomentTZ(momentDate).tz(timeZone, false);
    }
  } else {
    const actualMoment = moment(momentDate);
    const momentWithTZ = MomentTZ(
      actualMoment.format('YYYY-MM-DDTHH:mm:ss.SSS'),
      'YYYY-MM-DDTHH:mm:ss.SSS',
      true
    );
    return momentWithTZ.tz(timeZone, false);
  }
};
<<<<<<< HEAD
=======

export const setMomentToEndOfDay = (moment) => {
  moment.hours(23);
  moment.minutes(59);
  moment.seconds(59);
  moment.milliseconds(999);
};
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
