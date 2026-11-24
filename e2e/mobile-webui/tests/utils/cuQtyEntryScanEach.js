// Shared setup of the CU quantity entry specs that drive a product-based picking job in Scan each CU
// ("scan every pack label"; every scan books one CU of 1 into the open box). Builds on cuQtyEntryScanOnce.js.
// A Scan each CU label carries a pack ID, so these specs need a format with a pack-ID part
// (buildCULabelFormat({ withPackId: true })).
import { PickingJobScreen } from './screens/picking/PickingJobScreen';
import { PickLineScanScreen } from './screens/picking/PickLineScanScreen';
import { buildCULabel, uniqueGTIN, uniqueLot } from './cuLabel';
import { BEST_BEFORE, createMasterdata, MODE_SCAN_ONCE } from './cuQtyEntryScanOnce';

export const MODE_SCAN_EACH = 'SCAN_EACH_CU';
export { MODE_SCAN_ONCE };
export const MSG_FORMAT_MISMATCH = 'The label does not match the label format of the picking profile';
export const MSG_NO_PACK_ID = 'The label contains no pack ID. Nothing was booked.';
export const MSG_OVER_PICK = 'Cannot pick more than the ordered quantity for this line.';
export const MSG_ALREADY_PICKED = (packId) => `This pack was already picked in this job (pack ID ${packId}). Nothing was booked.`;

/** Raw label of one pack; `packId: null` = a label without counter (ends before the "92" marker). */
export const packLabelOf = ({ masterdata, lot = uniqueLot(), packId, bestBefore = BEST_BEFORE, gtin = uniqueGTIN() }) =>
    buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, bestBefore, packId });

/**
 * Masterdata of a Scan each CU spec: profile mode Scan each CU with the pack-ID format `packFormatName`;
 * `orders`, `customers`, `formats` as in cuQtyEntryScanOnce.createMasterdata.
 */
export const createScanEachMasterdata = async ({ packFormatName, orders, customers = [], formats = [], ...rest }) =>
    await createMasterdata({
        formatName: packFormatName,
        orders,
        customers,
        profileMode: MODE_SCAN_EACH,
        formats: [{ name: packFormatName, withPackId: true }, ...formats],
        ...rest,
    });

/** Opens the scan screen of a line (source HU known), checks its mode, opens the first box. */
export const openScanEachLine = async ({ index, mode = MODE_SCAN_EACH, openBox = true }) => {
    await PickingJobScreen.clickLineButton({ index });
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.expectCUQtyEntryMode(mode);
    if (openBox) {
        await PickLineScanScreen.openBox();
    }
};

/** Scan each CU: one label scan books one CU; no quantity block opens. */
export const scanPack = async (label) => {
    await PickLineScanScreen.scanCULabel(label);
};
