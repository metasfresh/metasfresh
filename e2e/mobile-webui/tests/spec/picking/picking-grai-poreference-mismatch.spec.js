/**
 * Playwright E2E — a Migros returnable-asset GRAI whose PO-reference-derived serial does NOT match
 * the current sales order's PO reference is REFUSED by the target-selection GRAI scanner, before
 * the TU type is even resolved.
 *
 * This exercises the GRAI-scan (target-selection) flow — `PickingGraiScanPanel` +
 * `SelectPickTargetTUScreen` (see `picking-grai-scan.spec.js`) — the ONLY flow that runs the
 * PO-reference-ownership gate (`PickingJobGraiTargetService.resolveTuTypeAndCapacity` →
 * `assertBelongsToCurrentOrderIfMigros`); the inline mass-capture flow (`PickGraiScreen`) never
 * hits this gate. The Migros structure check runs on the scanned GRAI's shape alone, so no
 * `M_HU_PI_GRAI` mapping is needed to reach this error — the mismatch is asserted before any TU
 * lookup happens.
 *
 * Migros GRAI structure (see de.metas.handlingunits.grai.DummyGRAITemplate):
 *   "{MIGROS_COMPANY_PREFIX}.{MIGROS_ASSET_TYPE}.{PO reference, zero-padded to 10}{2-digit counter}"
 *   = "7613204.00307.<poReference padStart(10,'0')><counter>"
 */

import { test } from '../../../playwright.config';
import { expect } from '@playwright/test';
import { allure } from 'allure-playwright';
import { Backend } from '../../utils/screens/Backend';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { PickingJobsListScreen } from '../../utils/screens/picking/PickingJobsListScreen';
import { PickingJobScreen } from '../../utils/screens/picking/PickingJobScreen';
import { PickingJobLineScreen } from '../../utils/screens/picking/PickingJobLineScreen';
import { SelectPickTargetTUScreen } from '../../utils/screens/picking/SelectPickTargetTUScreen';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { expectErrorToast } from '../../utils/common';

const MIGROS_COMPANY_PREFIX = '7613204';
const MIGROS_ASSET_TYPE = '00307';
const ORDER_PO_REFERENCE = '12345';
const OTHER_PO_REFERENCE = '99999';

/** Builds a canonical Migros dummy-GRAI: see de.metas.handlingunits.grai.DummyGRAITemplate.migros(poReference).buildGRAI(counter). */
const buildMigrosGrai = (poReference, counter) =>
    `${MIGROS_COMPANY_PREFIX}.${MIGROS_ASSET_TYPE}.${poReference.padStart(10, '0')}${String(counter).padStart(2, '0')}`;

/**
 * A GRAIRequired customer whose sales order carries a PO reference. The TU packing instruction is
 * an ordinary (non-GRAI-mapped) one — the mismatch is asserted before any TU/mapping lookup, so no
 * M_HU_PI_GRAI mapping is needed to reach it. Product aggregation is required for the line-level
 * TU-target screen (see picking-grai-scan.spec.js).
 */
const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: { user: { language: 'en_US' } },
            mobileConfig: {
                picking: {
                    aggregationType: 'product',
                    allowPickingAnyCustomer: true,
                    createShipmentPolicy: 'CL',
                    allowPickingAnyHU: true,
                    shipOnCloseLU: false,
                    pickTo: ['LU_TU'],
                    allowCompletingPartialPickingJob: false,
                },
            },
            bpartners: { BP1: { graiRequired: 'Y' } },
            warehouses: { wh: {} },
            pickingSlots: { slot1: {} },
            products: {
                P1: { prices: [{ price: 1 }] },
            },
            packingInstructions: {
                PI_MAIN: { lu: 'LU_MAIN', qtyTUsPerLU: 20, tu: 'TU_MAIN', product: 'P1', qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    poReference: ORDER_PO_REFERENCE,
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU_MAIN' }],
                },
            },
        },
    });
};

/**
 * Navigate to the pick-target TU screen for the first line (per-line path, carries a lineId — the
 * GRAI scan REST endpoint needs it to resolve the order's PO reference).
 *
 * Precondition: PickingJobScreen is showing.
 * Postcondition: SelectPickTargetTUScreen is showing.
 */
const navigateToTUTargetScreen = async (masterdata) => {
    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_MAIN.luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
};

// noinspection JSUnusedLocalSymbols
test('Migros GRAI belonging to another order\'s PO reference is refused', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F5230: GRAI on Returnable Assets');
    await allure.story('GRAI scan picking — Migros GRAI belonging to another order\'s PO reference is refused');
    await allure.severity('critical');

    const masterdata = await createMasterdata();
    // Migros-structured (matches companyPrefix/assetType) but derived from a DIFFERENT PO reference
    // than the current order's — must be refused, regardless of any TU mapping.
    const mismatchedGrai = buildMigrosGrai(OTHER_PO_REFERENCE, 1);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    // Product-aggregated jobs (aggregationType: 'product') are listed by product, so the launcher
    // caption carries no documentNo — start by index after the documentNo filter has narrowed the
    // list to a single launcher (mirrors picking-grai-scan.spec.js's product-aggregation flow).
    await PickingJobsListScreen.startJob({ index: 1 });

    await navigateToTUTargetScreen(masterdata);
    await PickingGraiScanPanel.expectScannerVisible();

    await expectErrorToast(
        'GRAIPOReferenceMismatch error',
        async () => {
            await PickingGraiScanPanel.scanGrai({ graiString: mismatchedGrai });
            await SelectPickTargetTUScreen.waitForScreen();
        },
        async ({ textContent }) => {
            expect(textContent).toContain('belongs to another order');
        },
    );
});
