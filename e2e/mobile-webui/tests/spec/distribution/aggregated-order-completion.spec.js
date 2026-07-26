import { test } from '../../../playwright.config';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { DistributionJobsListScreen } from '../../utils/screens/distribution/DistributionJobsListScreen';
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { allure } from 'allure-playwright';

//
// A distribution order can serve several deliveries at once. Completing it while part of the planned
// quantity was never moved would dispose of the document although other deliveries still wait for
// their goods — their demand would be dropped silently. So the mover is not allowed to complete a job
// with quantity still to move; if he really cannot move it, he has to give the remainder up explicitly,
// which closes the order short and lets the outstanding demand be re-issued.
//

const PLANNED_QTY = 15;
const MOVED_QTY = 6;
const OUTSTANDING_QTY = PLANNED_QTY - MOVED_QTY;

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US', workplace: 'workplace1' } },
            // The mover taps Complete himself — no auto-complete after the drop, so the completion
            // this spec is about is the mover's own explicit decision.
            mobileConfig: { distribution: { completeJobAutomatically: false } },
            // The mover works at the target warehouse, so only this test's job is offered to him.
            workplaces: { workplace1: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
            resources: { plantId: { type: 'PT' } },
            products: { P1: {} },
            warehouses: {
                wh1: { locators: { wh1_l1: {} } },
                wh2: { locators: { wh2_l1: {} } },
                whInTransit: { inTransit: true },
            },
            handlingUnits: {
                // The whole planned quantity is on the shelf, in two handling units, so nothing but
                // the mover's own decision limits how much he moves — and each unit can be followed
                // to where it ended up.
                HU_MOVED: { product: 'P1', warehouse: 'wh1', qty: MOVED_QTY },
                HU_REST: { product: 'P1', warehouse: 'wh1', qty: OUTSTANDING_QTY },
            },
            distributionOrders: {
                DD1: {
                    warehouseFrom: 'wh1',
                    warehouseTo: 'wh2',
                    warehouseInTransit: 'whInTransit',
                    plant: 'plantId',
                    lines: [{ product: 'P1', qtyEntered: PLANNED_QTY }],
                },
            },
        },
    });
};

/** What the mover is told is still to be moved, in his own language: "<qty> <uom> <product>". */
const qtyOutstandingText = (masterdata) => `${OUTSTANDING_QTY} Stk ${masterdata.products.P1.productName}`;

const openTheJob = async (masterdata) => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
};

/** The mover takes one handling unit off the source shelf and drops it at the target locator. */
const moveHandlingUnit = async ({ masterdata, hu, qty }) => {
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.scanHUToMove({
        huQRCode: masterdata.handlingUnits[hu].qrCode,
        expectedQtyToMove: String(qty),
    });
    await DistributionLineScreen.goBack();
    await DistributionJobScreen.dropAllTo({ dropToLocatorQRCode: masterdata.warehouses.wh2.locators.wh2_l1.qrCode });
};

// noinspection JSUnusedLocalSymbols
test('Completion is withheld while quantity is still to be moved, and succeeds once it is', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114'); // Standalone tag for Tags section;
    allure.story('Shared distribution order completion gate');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await openTheJob(masterdata);

    await test.step(`Move ${MOVED_QTY} of the planned ${PLANNED_QTY} to the target locator`, async () => {
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: `${PLANNED_QTY} Stk`, qtyPicked: '0 Stk' });
        await moveHandlingUnit({ masterdata, hu: 'HU_MOVED', qty: MOVED_QTY });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: `${PLANNED_QTY} Stk`, qtyPicked: `${MOVED_QTY} Stk` });
        await Backend.expect({
            title: `The ${MOVED_QTY} arrived at the target, the rest is still on the source shelf`,
            hus: {
                HU_MOVED: { huStatus: 'A', warehouse: 'wh2', storages: { P1: `${MOVED_QTY} PCE` } },
                HU_REST: { huStatus: 'A', warehouse: 'wh1', storages: { P1: `${OUTSTANDING_QTY} PCE` } },
            },
        });
    });

    await test.step(`Complete is refused and names the ${OUTSTANDING_QTY} that are still to be moved`, async () => {
        await DistributionJobScreen.completeExpectingRefusal({ expectedQtyOutstanding: qtyOutstandingText(masterdata) });
        // The job is still the mover's to finish: it is still on screen with the quantity still open,
        // and the way out for a mover who cannot move it is still offered.
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: `${PLANNED_QTY} Stk`, qtyPicked: `${MOVED_QTY} Stk` });
        await DistributionJobScreen.expectGiveUpRemainderButton({ visible: true });
    });

    await test.step(`Move the remaining ${OUTSTANDING_QTY} and complete`, async () => {
        await moveHandlingUnit({ masterdata, hu: 'HU_REST', qty: OUTSTANDING_QTY });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: `${PLANNED_QTY} Stk`, qtyPicked: `${PLANNED_QTY} Stk` });
        // Nothing outstanding any more, so giving the remainder up is no longer offered.
        await DistributionJobScreen.expectGiveUpRemainderButton({ visible: false });

        await DistributionJobScreen.complete();
        await DistributionJobsListScreen.expectJobButtons([]);
        // The whole planned quantity is at the target: both handling units, not just the last one.
        await Backend.expect({
            title: `All ${PLANNED_QTY} arrived at the target warehouse`,
            hus: {
                HU_MOVED: { huStatus: 'A', warehouse: 'wh2', storages: { P1: `${MOVED_QTY} PCE` } },
                HU_REST: { huStatus: 'A', warehouse: 'wh2', storages: { P1: `${OUTSTANDING_QTY} PCE` } },
            },
        });
    });
});

// noinspection JSUnusedLocalSymbols
test('The mover gives the remainder up and the order is finished short', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114: MobileUI Distribution');
    allure.tag('F5114'); // Standalone tag for Tags section;
    allure.story('Shared distribution order completion gate');
    allure.severity('critical');

    const masterdata = await createMasterdata();

    await openTheJob(masterdata);

    await test.step(`Move only ${MOVED_QTY} of the planned ${PLANNED_QTY} — the rest cannot be moved`, async () => {
        await moveHandlingUnit({ masterdata, hu: 'HU_MOVED', qty: MOVED_QTY });
        await DistributionJobScreen.expectLineButton({ index: 1, qtyToPick: `${PLANNED_QTY} Stk`, qtyPicked: `${MOVED_QTY} Stk` });
    });

    await test.step(`Give the remaining ${OUTSTANDING_QTY} up and finish the job`, async () => {
        await DistributionJobScreen.expectGiveUpRemainderButton({ visible: true });
        await DistributionJobScreen.completeGivingUpRemainder({ expectedQtyOutstanding: qtyOutstandingText(masterdata) });

        // The order is done with: it is no longer offered to the mover, and only what was really
        // moved arrived at the target.
        await DistributionJobsListScreen.expectJobButtons([]);
        await Backend.expect({
            title: `Only the ${MOVED_QTY} that were really moved arrived at the target; the ${OUTSTANDING_QTY} stayed behind`,
            hus: {
                HU_MOVED: { huStatus: 'A', warehouse: 'wh2', storages: { P1: `${MOVED_QTY} PCE` } },
                HU_REST: { huStatus: 'A', warehouse: 'wh1', storages: { P1: `${OUTSTANDING_QTY} PCE` } },
            },
        });
    });
});
