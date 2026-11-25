import { allure } from 'allure-playwright';
import { Backend } from './screens/Backend';
import { LoginScreen } from './screens/LoginScreen';
import { ApplicationsListScreen } from './screens/ApplicationsListScreen';
import { PickingJobsListScreen } from './screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from './screens/picking/PickingJobScreen';
import { PickLineScanScreen } from './screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from './screens/picking/GetQuantityDialog';
import { PickingJobLineScreen } from './screens/picking/PickingJobLineScreen';
import { PickingJobStepScreen } from './screens/picking/PickingJobStepScreen';
import { SelectPickTargetLUScreen } from './screens/picking/SelectPickTargetLUScreen';
import { buildCULabel, buildCULabelFormat, defaultBestBefore, uniqueGTIN, uniqueLot, uniqueProductValue } from './cuLabel';

export const MODE_SCAN_ONCE = 'SCAN_ONCE_ENTER_QTY';
export const MODE_MANUAL = 'MANUAL';
export const BEST_BEFORE = defaultBestBefore();
export const SOURCE_QTY = 80; // 20 TUs x 4 pieces
export const GRAI_8003_PREFIX = '8003076132640030951';
export const MSG_NO_OPEN_TU = "Please open a TU first (scan a GRAI or 'Next TU').";

export const LAUNCHERS_LIMIT_SYSCONFIGS = { 'WorkflowRestAPIService.LaunchersLimit': '1000' };

let sysconfigsToRestore = new Map();

/** Backend.createMasterdata that remembers the sysconfig values it overwrote */
export const createMasterdataRememberingSysconfigs = async (args) => {
    const masterdata = await Backend.createMasterdata(args);
    for (const [name, value] of Object.entries(masterdata.previousSysconfigs ?? {})) {
        if (!sysconfigsToRestore.has(name)) {
            sysconfigsToRestore.set(name, value); // the first call of the test holds the values before the test
        }
    }
    return masterdata;
};

/** Call once at the top level of a spec file that creates masterdata through this module */
export const restoreSysconfigsAfterEach = (test) => {
    test.afterEach(async () => {
        const sysconfigs = Object.fromEntries(sysconfigsToRestore);
        sysconfigsToRestore = new Map();
        if (Object.keys(sysconfigs).length > 0) {
            await Backend.setSysconfigs(sysconfigs);
        }
    });
};

export const uniqueFormatName = () => `cuqe-so-${uniqueProductValue()}`;

export const createMasterdata = async ({
                                           formatName,
                                           orders,
                                           customers = [],
                                           profileMode = MODE_SCAN_ONCE,
                                           packingInstructions = {},
                                           products = {},
                                           pickTo = ['TU'],
                                           sysconfigs = {},
                                           anonymousPickHUsOnTheFly = false,
                                           allowCompletingPartialPickingJob = false,
                                           language = 'en_US',
                                           formats = [{ name: formatName, withPackId: false }],
                                       }) => {
    const bpartners = {};
    const salesOrders = {};
    orders.forEach(({ key, qty, graiRequired, piItemProduct = 'P1_4CU' }) => {
        bpartners[`cust${key}`] = graiRequired ? { graiRequired } : {};
        salesOrders[`SO_${key}`] = {
            bpartner: `cust${key}`,
            warehouse: 'wh',
            datePromised: '2025-03-01T00:00:00.000+02:00',
            lines: [{ product: 'P1', qty, ...(piItemProduct ? { piItemProduct } : {}) }]
        };
    });
    const slots = {};
    orders.forEach((_, idx) => { slots[`slot${idx + 1}`] = {}; });

    return await createMasterdataRememberingSysconfigs({
        language,
        request: {
            sysconfigs: { ...LAUNCHERS_LIMIT_SYSCONFIGS, ...sysconfigs },
            login: { user: { language } },
            mobileConfig: {
                picking: {
                    aggregationType: "product",
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    anonymousPickHUsOnTheFly,
                    showPromptWhenOverPicking: false,
                    allowCompletingPartialPickingJob,
                    pickTo,
                    ...(profileMode ? { cuQtyEntryMode: profileMode } : {}),
                    cuLabelFormatName: formatName,
                    customers,
                }
            },
            customQRCodeFormats: formats.map(({ name, withPackId = false }) => buildCULabelFormat({ name, withPackId })),
            bpartners,
            warehouses: { "wh": {} },
            pickingSlots: slots,
            products: {
                // product value of 7 characters, as on the real CU label
                "P1": { price: 1, randomValue: { size: 7, isIncludeDigits: true } },
                ...products,
            },
            packingInstructions: {
                "P1_20x4": { lu: "LU", qtyTUsPerLU: 20, tu: "P1_4CU", product: "P1", qtyCUsPerTU: 4 },
                ...packingInstructions,
            },
            handlingUnits: {
                "P1_HU": { product: 'P1', warehouse: 'wh', packingInstructions: 'P1_20x4' },
            },
            salesOrders,
        }
    });
};

export const startJob = async (masterdata, { qtyToDeliver }) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1, qtyToDeliver, productId: masterdata.products.P1.id });
    await PickingJobScreen.scanPickFromHU({ qrCode: masterdata.handlingUnits.P1_HU.qrCode });
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    return { pickingJobId };
};

/** Opens the scan screen of a Scan once line with nothing picked yet (source HU known */
export const openLine = async ({ index }) => {
    await PickingJobScreen.clickLineButton({ index });
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.expectCUQtyEntryMode(MODE_SCAN_ONCE);
};

export const setPalletTarget = async ({ lu }) => {
    await PickLineScanScreen.goBack();
    await PickingJobLineScreen.clickLUTargetButton();
    await SelectPickTargetLUScreen.clickLUButton({ lu });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.tapPickHUButton();
    await PickLineScanScreen.waitForScreen();
};

export const unpackFirstBooking = async ({ fromLineScreen = false } = {}) => {
    if (fromLineScreen) {
        await PickingJobLineScreen.waitForScreen();
    } else {
        await PickLineScanScreen.goBack(); // lands on the line screen, which lists the picked HUs
    }
    await PickingJobLineScreen.clickStepButton({ index: 0 });
    await PickingJobStepScreen.unpick();
    await PickingJobLineScreen.goBack(); // the unpick leaves on the line screen
};

/** Scan once */
export const scanLabel = async (label) => {
    await PickLineScanScreen.scanCULabel(label);
    await GetQuantityDialog.waitForDialog();
};

export const labelOf = ({ masterdata, gtin = uniqueGTIN(), lot = uniqueLot(), packId = 1, bestBefore = BEST_BEFORE }) =>
    buildCULabel({ gtin, productValue: masterdata.products.P1.productCode, lot, bestBefore, packId });

/** Types the count and presses OK */
export const book = async ({ qty, expectedQtyTarget }) => {
    await GetQuantityDialog.typeQtyEntered(qty);
    await GetQuantityDialog.clickDoneStayOpen({ expectedQtyTarget });
};

export const allureMeta = (story) => {
    allure.epic('E0105: Picking');
    allure.tag('F00230: MobileUI Picking');
    allure.tag('F00230');  // Standalone tag for Tags section;
    allure.story(story);
    allure.severity('normal');
};

export const qtyPickedRow = ({ qty, vhu, tu }) => ({ qtyPicked: `${qty} PCE`, qtyTUs: 1, qtyLUs: 0, vhu, tu, lu: '-', processed: false, shipmentLineId: '-' });

export { uniqueLot };
