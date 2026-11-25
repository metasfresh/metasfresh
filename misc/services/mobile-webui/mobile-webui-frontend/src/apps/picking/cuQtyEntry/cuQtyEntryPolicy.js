import PropTypes from 'prop-types';

/** What the CU quantity entry mode of a picking line means for the screens */
export const MANUAL_POLICY = Object.freeze({
  isLabelRequired: false,
  isPackIdRequired: false,
  isScanOnce: false,
  isOneCUPerLabel: false,
});

export const getCUQtyEntryPolicy = (line) => line?.cuQtyEntryPolicy ?? MANUAL_POLICY;

/**
 * The shared quantity components (also used by distribution and manufacturing) know behaviour switches, not picking
 * modes: this is the one place that maps the picking policy onto them.
 */
export const toQtyEntryBehaviorProps = (policy) => ({
  isRepeatedBooking: !!policy?.isScanOnce,
  isBookedOnScan: !!policy?.isOneCUPerLabel,
  isLotAndBestBeforeShownReadOnly: !!policy?.isLabelRequired,
  isSubmitOnEnter: !policy?.isLabelRequired,
});

export const CUQtyEntryPolicyPropType = PropTypes.shape({
  isLabelRequired: PropTypes.bool,
  isPackIdRequired: PropTypes.bool,
  isOneCUPerLabel: PropTypes.bool,
  isScanOnce: PropTypes.bool,
});
