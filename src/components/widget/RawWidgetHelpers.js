import Moment from 'moment-timezone';
import cx from 'classnames';

import {
  DATE_FORMAT,
  TIME_FORMAT,
  DATE_TIMEZONE_FORMAT,
} from '../../constants/Constants';

/*
 * Helper function returning proper date field formatting depending on the
 * given widget type
 *
 * @param {string} widgetType
 * @return {string} format
 */
export function getFormatForDateField(widgetType) {
  let fmt = DATE_TIMEZONE_FORMAT;
  if (widgetType === `Date`) {
    fmt = DATE_FORMAT;
  } else if (widgetType === `Time`) {
    fmt = TIME_FORMAT;
  }

  return fmt;
}

/*
 * Helper function to turn date value into a Moment object and optionally format it.
 *
 * @param {object} value
 * @param {string} [FORMAT]
 */
export function generateMomentObj(value, FORMAT) {
  if (Moment.isMoment(value)) {
    return value.format(FORMAT);
  }
  return value ? Moment(value).format(FORMAT) : null;
}

export function getClassNames({ icon, forcedPrimary } = {}) {
  const {
    getWidgetData,
    gridAlign,
    type,
    updated,
    rowId,
    isModal,
  } = this.props;
  let { widgetData } = this.props;
  widgetData = widgetData || getWidgetData();

  const { isEdited } = this.state;
  const { readonly, value, mandatory, validStatus } = widgetData[0];

  const ret = cx(`input-block`, {
    'input-icon-container': icon,
    'input-disabled': readonly,
    'input-mandatory': mandatory && (value ? value.length === 0 : value !== 0),
    'input-error':
      validStatus &&
      !validStatus.valid &&
      !validStatus.initialValue &&
      !isEdited,
    [`text-xs-${gridAlign}`]: gridAlign,
    [`input-${
      type === 'primary' || forcedPrimary ? 'primary' : 'secondary'
    }`]: true,
    [`pulse-${updated ? 'on' : 'off'}`]: true,
    'input-table': rowId && !isModal,
  });

  return ret;
}
