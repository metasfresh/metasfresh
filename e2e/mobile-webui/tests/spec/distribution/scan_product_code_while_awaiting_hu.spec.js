import { test } from "../../../playwright.config";
import { Backend } from "../../utils/screens/Backend";
import { allure } from 'allure-playwright';
import { expect } from '@playwright/test';
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { DistributionJobsListScreen } from "../../utils/screens/distribution/DistributionJobsListScreen";
import { DistributionJobScreen } from '../../utils/screens/distribution/DistributionJobScreen';
import { DistributionLineScreen } from '../../utils/screens/distribution/DistributionLineScreen';
import { DistributionLinePickFromScreen } from '../../utils/screens/distribution/DistributionLinePickFromScreen';
import { DistributionUtils } from '../../utils/screens/distribution/DistributionUtils';
import { generateEAN13 } from '../../utils/ean13';
import { expectErrorToast } from '../../utils/common';

//
// The distribution pick-from screen is asking for a handling unit and the operator scans the ARTICLE
// code (the product's GTIN) instead. In distribution that can never identify a handling unit — a
// source locator holds many handling units of one article, so a bare article code cannot say which
// one to pick from, and the backend accordingly resolves that slot to a real handling-unit QR code
// only (DistributionHUService.resolveHUQRCode). So the app must say so on the screen, and must keep
// the article code out of the handling-unit slot rather than pass it on and let the server answer
// with an untranslated wrong-QR-code-type rejection ("Falscher QR-Code-Typ: EAN13HUQRCode") that
// tells the operator nothing they can act on.
//
// TWO tests, differing ONLY in whether product-code scanning is required. Both settings ship to
// customers, and the refusal itself is deliberately independent of them — it is decided before
// requireScanningProductCode is read — so NEITHER test discriminates on that setting, and neither is
// claimed to. What differs is the legitimate route each one then finishes the pick through: with the
// setting on, the article code is scanned again at the article-code prompt; with it off, identifying
// the handling unit goes straight to the quantity dialog. Each test asserts BOTH halves of the
// behaviour — the on-screen message, and that no request ever carried the article code in the
// huQRCode slot — so each setting is covered end to end.
//
// Everything else is held constant, and allowPickingAnyHU is on in both — the setting every customer
// runs, and the one that puts the "Scan QR Code" button on the line screen these scenarios enter
// through.
//
// Sibling coverage: scan_HU_barcodes.spec.js covers the other two things an operator can scan at this
// prompt — a locator QR code and an unrecognised barcode — both of which the backend already answers
// with a readable message and which this behaviour leaves alone.
//
// RESIDUAL GAP, stated rather than implied: only an EAN13 article code is refused on the screen,
// because that is the one article-code format the pick-from screen recognises without asking the
// backend. A GS1-encoded article code still reaches the backend and still surfaces its wrong-QR-type
// message, so it is NOT covered here or by any sibling spec.
//

// Moved in ONE pick, so the whole handling unit travels and keeps its identity — that is what lets
// the closing assertions name HU1 directly instead of chasing a split-off HU's QR code.
const QTY_TO_MOVE = 100;

// The run language is en_US (set by createMasterdata), so this is the en text of
// activities.distribution.qrcode.productCodeWhereHUExpected.
const EXPECTED_MESSAGE = 'This is an article barcode (GTIN), not an HU barcode. Please scan the HU first.';

/**
 * @param requireScanningProductCode the ONE configuration difference between the two tests — see the
 *        file header. Passed explicitly by both, never inherited.
 */
const createMasterdata = async ({ huExternalBarcode, requireScanningProductCode }) =>
    await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: { user: { language: "en_US", workplace: "articleCodeWorkplace" } },
            mobileConfig: {
                distribution: {
                    // allowPickingAnyHU is a sticky, global config row the masterdata API leaves
                    // untouched when omitted (e2e/mobile-webui/CLAUDE.md § "Debugging Flaky Tests"
                    // rule 3), so it is always set explicitly here. It is the setting every customer
                    // runs, and it renders the line screen's "Scan QR Code" button
                    // (DistributionLineScreen.jsx) — the operator's way onto the pick-from screen with
                    // no handling unit chosen yet, which is the situation under test.
                    allowPickingAnyHU: true,
                    requireScanningProductCode,
                },
            },
            resources: { "plantId": { type: "PT" } },
            products: { "P1": { gtin: generateEAN13().ean13 } },
            warehouses: {
                "wh1": {},
                "wh2": { locators: { wh2_l1: {} } },
                "whInTransit": { inTransit: true },
            },
            workplaces: { articleCodeWorkplace: { warehouse: 'wh2', pickFromLocator: 'wh2_l1' } },
            handlingUnits: {
                // Identified by an external barcode, the way the handling units in the reported
                // scenario are labelled.
                "HU1": { product: "P1", warehouse: "wh1", qty: QTY_TO_MOVE, externalBarcode: huExternalBarcode },
            },
            distributionOrders: {
                "DD1": {
                    warehouseFrom: "wh1",
                    warehouseTo: "wh2",
                    warehouseInTransit: "whInTransit",
                    plant: "plantId",
                    lines: [{ product: "P1", qtyEntered: QTY_TO_MOVE }],
                },
            },
        },
    });

const startJobAndOpenPickFrom = async ({ masterdata }) => await test.step('Open the Distribution app, start DD1 and open its line to pick from', async () => {
    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('distribution');
    await DistributionJobsListScreen.waitForScreen();
    await DistributionJobsListScreen.startJob({ launcherTestId: masterdata.distributionOrders.DD1.launcherTestId });
    await DistributionJobScreen.clickLineButton({ index: 1 });
    await DistributionLineScreen.openPickFromScreen();
});

const expectPickLanded = async () => await test.step('Backend: the pick landed — the handling unit is in transit with its full qty', async () => {
    await Backend.expect({
        title: 'DD1 picked from HU1',
        hus: {
            HU1: { warehouse: 'whInTransit', huStatus: 'A', storages: { P1: `${QTY_TO_MOVE} PCE` } },
        },
    });
});

// noinspection JSUnusedLocalSymbols
test('Article code scanned where the handling unit is expected: the operator is told, and the screen keeps asking for the handling unit', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.tag('F5114');
    allure.story('Scanning the article code while the pick-from screen asks for the handling unit is answered on the screen, and the prompt stays on the handling unit');
    allure.severity('critical');

    const huExternalBarcode = `EXT-ARTICLE-CODE-REQ-${Date.now()}`;
    const masterdata = await createMasterdata({ huExternalBarcode, requireScanningProductCode: true });
    const articleCode = masterdata.products.P1.gtin;

    // Recording starts before the operator does anything, so the closing assertion covers EVERY
    // request of the session — including the legitimate pick at the end, which does send this article
    // code, as productScannedCode alongside the real handling unit's QR code, and must still never
    // send it as huQRCode.
    const pickFromLineRequests = DistributionUtils.recordNextEligiblePickFromLineRequests();

    await startJobAndOpenPickFrom({ masterdata });

    // PRECONDITION, asserted before the target assertion so a fixture/config drift fails HERE and is
    // never mistaken for the behaviour under test: the screen really is asking for the handling unit.
    await test.step('The screen asks the operator to scan the handling unit', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    // *** THE ASSERTION THIS TEST EXISTS FOR ***
    // The operator scans the article's GTIN at the handling-unit prompt and must be told, in words
    // they can act on, that this is an article code and a handling unit is what is wanted.
    await expectErrorToast(
        'Scan the article code where the handling unit is expected',
        async () => await DistributionLinePickFromScreen.typeHUQRCode(articleCode),
        ({ textContent }) => {
            expect(textContent).toContain(EXPECTED_MESSAGE);
        }
    );

    // ... and the message must not cost them their place: the prompt is still the handling-unit one,
    // so their next scan is read as a handling unit. The pick-from screen renders exactly ONE of the
    // two scan inputs (ScanHUAndGetQtyComponent's progressStatus), so asserting the handling-unit
    // input is the one showing distinguishes "the scan was refused and the operator kept their place"
    // from "the article code was taken as the chosen handling unit and the screen moved on to the
    // article-code prompt" — the two outcomes differ in exactly this value.
    await test.step('The screen still asks for the handling unit', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Identify the handling unit by its external barcode, then scan the article code and confirm the qty', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(huExternalBarcode);
        await DistributionLinePickFromScreen.typeProductCode(articleCode);
        await DistributionLinePickFromScreen.fillQuantityDialog({ expectedQtyToMove: QTY_TO_MOVE });
        await DistributionLineScreen.waitForScreen();
    });

    await expectPickLanded();

    await DistributionUtils.expectNoPickFromLineRequestCarriedHUQRCode({
        recorder: pickFromLineRequests,
        huQRCode: articleCode,
    });
});

// noinspection JSUnusedLocalSymbols
test('Article code scanned where the handling unit is expected: it is never sent to the backend as a handling-unit code', async ({ page }) => {
    // === ALLURE METADATA ===
    allure.epic('E0370: Intralogistic (HUs)');
    allure.tag('F5114.3');
    allure.tag('F5114');
    allure.story('Scanning the article code while the pick-from screen asks for the handling unit never puts it in the huQRCode slot, so no wrong-QR-code-type rejection reaches the operator');
    allure.severity('critical');

    const huExternalBarcode = `EXT-ARTICLE-CODE-NOREQ-${Date.now()}`;
    const masterdata = await createMasterdata({ huExternalBarcode, requireScanningProductCode: false });
    const articleCode = masterdata.products.P1.gtin;

    const pickFromLineRequests = DistributionUtils.recordNextEligiblePickFromLineRequests();

    await startJobAndOpenPickFrom({ masterdata });

    // PRECONDITION — see the sibling test.
    await test.step('The screen asks the operator to scan the handling unit', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await expectErrorToast(
        'Scan the article code where the handling unit is expected',
        async () => await DistributionLinePickFromScreen.typeHUQRCode(articleCode),
        ({ textContent }) => {
            expect(textContent).toContain(EXPECTED_MESSAGE);
        }
    );

    await test.step('The screen still asks for the handling unit', async () => {
        await DistributionLinePickFromScreen.expectHUScanReady();
    });

    await test.step('Identify the handling unit by its external barcode and confirm the qty', async () => {
        await DistributionLinePickFromScreen.typeHUQRCode(huExternalBarcode);
        await DistributionLinePickFromScreen.fillQuantityDialog({ expectedQtyToMove: QTY_TO_MOVE });
        await DistributionLineScreen.waitForScreen();
    });

    await expectPickLanded();

    // *** THE ASSERTION THIS TEST EXISTS FOR ***
    // With no product code required, identifying the handling unit resolves it against the backend
    // straight away — so in this configuration the huQRCode slot is filled from the very first scan
    // the operator makes. Across the whole session it may only ever hold the real handling-unit code
    // scanned above, never the article code, whose rejection is what the backend answers with the
    // wrong-QR-code-type message the operator must never see.
    await DistributionUtils.expectNoPickFromLineRequestCarriedHUQRCode({
        recorder: pickFromLineRequests,
        huQRCode: articleCode,
    });
});
