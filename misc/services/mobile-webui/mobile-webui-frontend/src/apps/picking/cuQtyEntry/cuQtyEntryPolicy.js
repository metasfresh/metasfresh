import PropTypes from 'prop-types';

/**
 * What the CU quantity entry mode of a picking line means for the screens. The backend decides and publishes it on every picking
 * line (`JsonPickingJobLine.cuQtyEntryPolicy`); this module only reads it, it does not know the modes and holds no rules.
 *
 * Java counterpart: `JsonCUQtyEntryPolicy` and `CUQtyEntryPolicy`
 * (backend/de.metas.handlingunits.base/.../picking/config/mobileui/CUQtyEntryPolicy.java). When you change what the screens need to ask,
 * change the Java classes and this file together.
 *
 * - labelRequired: a CU label is read (lot / best-before come from it, the box is open before booking)
 * - packIdRequired: the label must carry a pack ID, one scan books one CU
 * - scanOnce: one label is scanned, then the count is typed
 */
export const MANUAL_POLICY = Object.freeze({ labelRequired: false, packIdRequired: false, scanOnce: false });

/** @returns the policy published on the picking line; the Manual one (nothing required) for a line without it */
export const getCUQtyEntryPolicy = (line) => line?.cuQtyEntryPolicy ?? MANUAL_POLICY;

export const CUQtyEntryPolicyPropType = PropTypes.shape({
  labelRequired: PropTypes.bool,
  packIdRequired: PropTypes.bool,
  scanOnce: PropTypes.bool,
});
