import Moment from 'moment';

export function generateMomentObj(value, FORMAT) {
  if (Moment.isMoment(value)) {
    return value.format(FORMAT);
  }
  return value ? Moment(value).format(FORMAT) : null;
}

// TODO: No idea why somebody decided to reimplement classnames instead
// of using module. Need to check if it can be easily replaced.
function classNames(classObject) {
  return Object.entries(classObject)
    .filter(([, classActive]) => classActive)
    .map(([className]) => className)
    .join(' ');
}

export function getClassNames({ icon, forcedPrimary } = {}) {
  const { widgetData, gridAlign, type, updated, rowId, isModal } = this.props;
  const { isEdited } = this.state;
  const { readonly, value, mandatory, validStatus } = widgetData[0];

  const ret = classNames({
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
