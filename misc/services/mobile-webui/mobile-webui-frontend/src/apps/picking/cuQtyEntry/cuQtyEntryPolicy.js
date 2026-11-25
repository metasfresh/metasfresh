import PropTypes from 'prop-types';

/** What the CU quantity entry mode of a picking line means for the screens */
export const MANUAL_POLICY = Object.freeze({
  isLabelRequired: false,
  isPackIdRequired: false,
  isScanOnce: false,
  isOneCUPerLabel: false,
});

export const getCUQtyEntryPolicy = (line) => line?.cuQtyEntryPolicy ?? MANUAL_POLICY;

export const CUQtyEntryPolicyPropType = PropTypes.shape({
  isLabelRequired: PropTypes.bool,
  isPackIdRequired: PropTypes.bool,
  isOneCUPerLabel: PropTypes.bool,
  isScanOnce: PropTypes.bool,
});
