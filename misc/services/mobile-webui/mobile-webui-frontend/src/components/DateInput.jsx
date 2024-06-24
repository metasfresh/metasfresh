import React, { useCallback } from 'react';
import cx from 'classnames';
import PropTypes from 'prop-types';
import { useBooleanSetting } from '../reducers/settings';

const DateInput = ({ value, readOnly, onChange }) => {
  const isUseNativeComponent = useBooleanSetting('dateInput.isUseNativeComponent');

  if (isUseNativeComponent) {
    return <DateInputNative value={value} readOnly={readOnly} onChange={onChange} />;
  } else {
    return <DateInputLegacy value={value} readOnly={readOnly} onChange={onChange} />;
  }
};

DateInput.propTypes = {
  value: PropTypes.any,
  readOnly: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
};

export default DateInput;

//
//
//
//
//

const DateInputNative = ({ value, readOnly, onChange }) => {
  const handleChange = useCallback(
    (e) => {
      const dateNew = e.target.value ? e.target.value : '';
      onChange({ date: dateNew, isValid: true });
    },
    [onChange]
  );
  return <input className="input" type="date" value={value} disabled={readOnly} onChange={handleChange} />;
};
DateInputNative.propTypes = {
  value: PropTypes.any,
  readOnly: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
};

//
//
//
//
//

const DateInputLegacy = ({ value, readOnly, onChange }) => {
  let displayedDate = value;
  let isValid = true;
  try {
    displayedDate = convertDateToDisplay(value);
  } catch (error) {
    console.log('Got error while converting date to display format. Returning empty.', { value, displayedDate, error });
    isValid = false;
  }

  const handleChange = useCallback(
    (e) => {
      const displayedDateNew = e.target.value ? e.target.value : '';
      let dateNew = displayedDateNew;
      let isValidNew;
      try {
        dateNew = convertDateFromDisplay(displayedDateNew);
        isValidNew = true;
      } catch (error) {
        isValidNew = false;
        console.log('Got error while converting from display. Returning empty.', { displayedDateNew, error });
      }

      onChange({ date: dateNew, isValid: isValidNew });
    },
    [onChange]
  );

  return (
    <input
      className={cx('input', { 'is-danger': !isValid })}
      type="text"
      value={displayedDate}
      placeholder="DD.MM.YYYY"
      disabled={readOnly}
      onChange={handleChange}
    />
  );
};
DateInputLegacy.propTypes = {
  value: PropTypes.any,
  readOnly: PropTypes.bool,
  onChange: PropTypes.func.isRequired,
};

const DISPLAY_DATE_FORMAT = /^(\d{2}).(\d{2}).(\d{4})$/;
const convertDateFromDisplay = (displayedDate) => {
  if (!displayedDate) return '';

  const [, day, month, year] = DISPLAY_DATE_FORMAT.exec(displayedDate);
  assertDayMonthYearValid({ day, month, year, dateString: displayedDate });
  return `${year}-${month}-${day}`;
};

const STANDARD_DATE_FORMAT = /^(\d{4})-(\d{2})-(\d{2})$/;
const convertDateToDisplay = (date) => {
  if (!date) return '';
  const [, year, month, day] = STANDARD_DATE_FORMAT.exec(date);
  assertDayMonthYearValid({ day, month, year, dateString: date });
  return `${day}.${month}.${year}`;
};

const assertDayMonthYearValid = ({ day, month, year, dateString }) => {
  if (day < 1 || day > 31) throw `Invalid day ${day} in ${dateString}`;
  if (month < 1 || month > 12) throw `Invalid month ${month} in ${dateString}`;
  if (year < 2000 || year > 2500) throw `Invalid year ${year} in ${dateString}`;
};
