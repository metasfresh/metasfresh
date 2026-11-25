import { PickingJobScreen } from './screens/picking/PickingJobScreen';
import { PickLineScanScreen } from './screens/picking/PickLineScanScreen';
import { buildCULabel, uniqueGTIN, uniqueLot } from './cuLabel';
import { MESSAGES } from './cuQtyEntryMessages';
import { BEST_BEFORE, createMasterdata, MODE_SCAN_ONCE } from './cuQtyEntryCommon';

export const MODE_SCAN_EACH = 'SCAN_EACH_CU';
export { MODE_SCAN_ONCE };
export const MSG_FORMAT_MISMATCH = 'The label does not match the label format of the picking profile';
export const MSG_NO_PACK_ID = 'The label contains no pack ID. Nothing was booked.';
export const MSG_OVER_PICK = MESSAGES.overPick.en;
export const MSG_ALREADY_PICKED = (packId) => `This CU was already picked in this job (pack ID ${packId}). Nothing was booked.`;

/** Raw label of one CU */
export const packLabelOf = ({ masterdata, lot = uniqueLot(), packId, bestBefore = BEST_BEFORE, gtin = uniqueGTIN() }) =>
    buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, bestBefore, packId });

/** Masterdata of a Scan each CU spec */
export const createScanEachMasterdata = async ({ packFormatName, orders, customers = [], formats = [], ...rest }) =>
    await createMasterdata({
        formatName: packFormatName,
        orders,
        customers,
        profileMode: MODE_SCAN_EACH,
        formats: [{ name: packFormatName, withPackId: true }, ...formats],
        ...rest,
    });

/** Opens the scan screen of a line (source HU known), checks its mode, opens the first TU */
export const openScanEachLine = async ({ index, mode = MODE_SCAN_EACH, openTU = true }) => {
    await PickingJobScreen.clickLineButton({ index });
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.expectCUQtyEntryMode(mode);
    if (openTU) {
        await PickLineScanScreen.openTU();
    }
};

/** Scan each CU */
export const scanPack = async (label) => {
    await PickLineScanScreen.scanCULabel(label);
};
