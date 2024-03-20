/*
 * #%L
 * ic114
 * %%
 * Copyright (C) 2024 metas GmbH
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

import MomentTZ from 'moment-timezone';
import {
  convertMomentToTimezone,
  setTimezoneToMoment
} from '../../utils/dateHelpers';

const DATETIME_FORMAT = 'YYYY-MM-DDTHH:mmZ';

const momentWithTZ = (dateAsString, timeZone) => {
  return MomentTZ(dateAsString, 'YYYY-MM-DDTHH:mm', true).tz(timeZone, true)
}

describe('dateHelpers', () => {
  describe('setTimezoneToMoment', () => {
    it('New York -> New York', () => {
      const moment_NY = momentWithTZ('2023-10-12T23:59', 'America/New_York');
      expect(moment_NY.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59-04:00');

      const moment_NY2 = setTimezoneToMoment(moment_NY, 'America/New_York');
      expect(moment_NY2.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59-04:00');
      expect(Object.is(moment_NY2, moment_NY)).toBe(true);
    });
    it('Berlin -> New York', () => {
      const moment_BERLIN = momentWithTZ('2023-10-12T23:59', 'Europe/Berlin');
      expect(moment_BERLIN.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+02:00');

      const moment_NY = setTimezoneToMoment(moment_BERLIN, 'America/New_York');
      expect(moment_NY.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59-04:00');
      expect(Object.is(moment_BERLIN, moment_NY)).toBe(false);
    });
    it('Bucharest -> Zurich', () => {
      const moment_BUCHAREST = momentWithTZ('2023-10-12T23:59', 'Europe/Bucharest');
      expect(moment_BUCHAREST.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+03:00');

      const moment_ZURICH = setTimezoneToMoment(moment_BUCHAREST, 'Europe/Zurich');
      expect(moment_ZURICH.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+02:00');
      expect(Object.is(moment_ZURICH, moment_BUCHAREST)).toBe(false);
    });
    it('Zurich -> Bucharest', () => {
      const moment_ZURICH = momentWithTZ('2023-10-12T23:59', 'Europe/Zurich');
      expect(moment_ZURICH.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+02:00');

      const moment_BUCHAREST = setTimezoneToMoment(moment_ZURICH, 'Europe/Bucharest');
      expect(moment_BUCHAREST.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+03:00');
      expect(Object.is(moment_ZURICH, moment_BUCHAREST)).toBe(false);
    });
  });

  describe('convertMomentToTimezone', () => {
    it('New York -> New York', () => {
      const moment_NY = momentWithTZ('2023-10-12T23:59', 'America/New_York');
      expect(moment_NY.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59-04:00');

      const moment_NY2 = convertMomentToTimezone(moment_NY, 'America/New_York');
      expect(moment_NY2.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59-04:00');
      expect(Object.is(moment_NY2, moment_NY)).toBe(true);
    });
    it('Bucharest -> Zurich', () => {
      const moment_BUCHAREST = momentWithTZ('2023-10-12T23:59', 'Europe/Bucharest');
      expect(moment_BUCHAREST.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+03:00');

      const moment_ZURICH = convertMomentToTimezone(moment_BUCHAREST, 'Europe/Zurich');
      expect(moment_ZURICH.format(DATETIME_FORMAT)).toEqual('2023-10-12T22:59+02:00');
      expect(Object.is(moment_ZURICH, moment_BUCHAREST)).toBe(false);
    });
    it('Zurich -> Bucharest', () => {
      const moment_ZURICH = momentWithTZ('2023-10-12T23:59', 'Europe/Zurich');
      expect(moment_ZURICH.format(DATETIME_FORMAT)).toEqual('2023-10-12T23:59+02:00');

      const moment_BUCHAREST = convertMomentToTimezone(moment_ZURICH, 'Europe/Bucharest');
      expect(moment_BUCHAREST.format(DATETIME_FORMAT)).toEqual('2023-10-13T00:59+03:00');
      expect(Object.is(moment_ZURICH, moment_BUCHAREST)).toBe(false);
    });
  });
});
