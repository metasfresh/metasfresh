export const LOCAL_LANG = 'metasfreshLanguage';
export const VIEW_EDITOR_RENDER_MODES_NEVER = 'never';
export const VIEW_EDITOR_RENDER_MODES_ON_DEMAND = 'on-demand';
export const VIEW_EDITOR_RENDER_MODES_ALWAYS = 'always';
export const INITIALLY_CLOSED = 'INITIALLY_CLOSED';
export const INITIALLY_OPEN = 'INITIALLY_OPEN';

export const AMOUNT_FIELD_TYPES = ['Amount', 'CostPrice', 'Quantity'];
export const AMOUNT_FIELD_FORMATS_BY_PRECISION = [
  '0,0.[00000]',
  '0,0.0[0000]',
  '0,0.00[000]',
  '0,0.000[00]',
  '0,0.0000[0]',
  '0,0.00000',
];
export const SPECIAL_FIELD_TYPES = ['Color'];

export const DATE_FORMAT = 'YYYY-MM-DD';
export const TIME_FORMAT = `HH:mm`;
export const DATE_TIMEZONE_FORMAT = `YYYY-MM-DDTHH:mm:ss.SSSZ`;
export const DATE_FIELDS = ['Date', 'DateTime'];
export const DATE_FIELD_TYPES = [
  'Date',
  'DateTime',
  'ZonedDateTime',
  'Timestamp',
];
export const DATE_FIELD_FORMATS = {
  Date: 'L',
  ZonedDateTime: 'L LTS',
  DateTime: 'L LTS',
  Time: 'LT',
  Timestamp: 'x',
};
export const TIME_FIELD_TYPES = ['Time'];
