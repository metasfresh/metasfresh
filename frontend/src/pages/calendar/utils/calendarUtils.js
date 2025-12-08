import { DATE_TIMEZONE_FORMAT } from '../../../constants/Constants';
import Moment from 'moment-timezone';
import MomentTZ from 'moment-timezone';
import { getCurrentActiveLocale } from '../../../utils/locale';

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

export const isSameMoment = (date1, date2) => {
  if (date1 === date2) {
    return true;
  }

  const moment1 = convertToMoment(date1);
  const moment2 = convertToMoment(date2);
  //console.log('isSameMoment', { date1, moment1, date2, moment2 });

  return (
    moment1 === moment2 ||
    (moment1 != null && moment2 != null && moment1.isSame(moment2))
  );
};
