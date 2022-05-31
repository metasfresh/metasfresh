import { DATE_TIMEZONE_FORMAT } from '../../constants/Constants';
import Moment from 'moment-timezone';
import MomentTZ from 'moment-timezone';
import { getCurrentActiveLocale } from '../../utils/locale';

const FULLCALENDAR_IO_DATE_FORMAT = 'YYYY-MM-DDTHH:mm:ssZ';

export const normalizeDateTime = (value) => {
  if (!value) {
    return null;
  }

  const moment = convertToMoment(value);
  return moment.format(DATE_TIMEZONE_FORMAT);
};

export const convertToMoment = (value) => {
  if (!value) {
    return null;
  } else if (Moment.isMoment(value)) {
    return value;
  } else {
    MomentTZ.locale(getCurrentActiveLocale());

    const moment = MomentTZ(value);
    if (moment.isValid()) {
      return moment;
    }

    return MomentTZ(value, FULLCALENDAR_IO_DATE_FORMAT, true);
  }
};
