import PropTypes from 'prop-types';

/** What the CU quantity entry mode of a picking line means for the screens */
export const MANUAL_POLICY = Object.freeze({ labelRequired: false, packIdRequired: false, scanOnce: false });

export const getCUQtyEntryPolicy = (line) => line?.cuQtyEntryPolicy ?? MANUAL_POLICY;

export const CUQtyEntryPolicyPropType = PropTypes.shape({
  labelRequired: PropTypes.bool,
  packIdRequired: PropTypes.bool,
  scanOnce: PropTypes.bool,
});
