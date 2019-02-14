import Moment from 'moment';
import classnames from 'classnames';

import { DATE_FORMAT } from '../../constants/Constants';

export function generateMomentObj(value) {
  if (Moment.isMoment(value)) {
    return value;
  }
  return value ? Moment(value).format(DATE_FORMAT) : null;
}

export function getClassNames({ icon, forcedPrimary } = {}) {
  const { widgetData, gridAlign, type, updated, rowId, isModal } = this.props;
  const { isEdited } = this.state;
  const { readonly, value, mandatory, validStatus } = widgetData[0];

  const ret = classnames({
    'input-block': true,
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
