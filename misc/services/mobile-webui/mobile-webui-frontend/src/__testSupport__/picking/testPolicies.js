import { MANUAL_POLICY } from '../../apps/picking/cuQtyEntry/cuQtyEntryPolicy';

export const POLICY = {
    MANUAL: MANUAL_POLICY,
    SCAN_EACH_CU: { isLabelRequired: true, isPackIdRequired: true, isScanOnce: false, isOneCUPerLabel: true },
    SCAN_ONCE_ENTER_QTY: { isLabelRequired: true, isPackIdRequired: false, isScanOnce: true, isOneCUPerLabel: false },
};
