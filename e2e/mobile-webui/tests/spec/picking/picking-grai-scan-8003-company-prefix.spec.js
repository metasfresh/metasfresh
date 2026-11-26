/**
 * Playwright E2E — a canonical GRAI whose company prefix starts with "8003" is scanned on the
 * pick-target TU screen and is accepted as-is.
 *
 * "8003" is also the GS1 Application Identifier for GRAI, so the canonical value
 * `8003214.95365.<serial>` is ALSO accepted by the GS1 barcode parser as AI 8003. The mobile UI
 * must read it as canonical first (like the backend's de.metas.handlingunits.grai.GRAI.parse);
 * reading it as GS1 turned it into `14.9536.5.199.<…>`, which has no M_HU_PI_GRAI mapping, and the
 * backend answered 422 GRAINoMatchingTUType.
 *
 * The masterdata API normally draws a random 7-digit company prefix, so this hit about 1 in 10,000
 * test GRAIs, e.g. CI run https://github.com/metasfresh/metasfresh/actions/runs/38117422768
 * (hu_consolidation_grai.spec.js). Here the prefix is pinned, so the case runs every time.
 *
 * Same navigation as picking-grai-poreference-accepted.spec.js (product aggregation, per-line
 * TU-target screen, PickingGraiScanPanel).
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
import { PickLineScanScreen } from '../../utils/screens/picking/PickLineScanScreen';
import { GetQuantityDialog } from '../../utils/screens/picking/GetQuantityDialog';
import { PickingGraiScanPanel } from '../../utils/screens/picking/PickingGraiScanPanel';
import { ErrorToast } from '../../utils/dialogs/ErrorToast';

// The (companyPrefix, assetType) pair of the failing CI GRAI 8003214.95365.1999803101.
const COMPANY_PREFIX_STARTING_WITH_8003 = '8003214';
const ASSET_TYPE = '95365';

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
                PI_MAIN: {
                    lu: 'LU_MAIN',
                    qtyTUsPerLU: 20,
                    tu: 'TU_MAPPED',
                    product: 'P1',
                    qtyCUsPerTU: 4,
                    graiMapping: true,
                    graiCompanyPrefix: COMPANY_PREFIX_STARTING_WITH_8003,
                    graiAssetType: ASSET_TYPE,
                },
            },
            handlingUnits: {
                HU1: { product: 'P1', warehouse: 'wh', qty: 100 },
            },
            salesOrders: {
                SO1: {
                    bpartner: 'BP1',
                    warehouse: 'wh',
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                    lines: [{ product: 'P1', qty: 4, piItemProduct: 'TU_MAPPED' }],
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Canonical GRAI with an 8003… company prefix is accepted as canonical, TU created with that GRAI', async ({ page }) => {
    await allure.epic('E0105: Picking');
    await allure.feature('F5230: GRAI on Returnable Assets');
    await allure.story('GRAI scan picking — a canonical GRAI whose company prefix starts with 8003 is not misread as a GS1 AI 8003 barcode');
    await allure.severity('critical');

    const masterdata = await createMasterdata();
    const grai = masterdata.packingInstructions.PI_MAIN.grai;
    expect(grai.startsWith(`${COMPANY_PREFIX_STARTING_WITH_8003}.${ASSET_TYPE}.`)).toBe(true);

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('picking');
    await PickingJobsListScreen.waitForScreen();
    await PickingJobsListScreen.filterByDocumentNo(masterdata.salesOrders.SO1.documentNo);
    const { pickingJobId } = await PickingJobsListScreen.startJob({ index: 1 });

    await PickingJobScreen.scanPickingSlot({ qrCode: masterdata.pickingSlots.slot1.qrCode });
    await PickingJobScreen.setTargetLU({ lu: masterdata.packingInstructions.PI_MAIN.luName });
    await PickingJobScreen.clickLineButton({ index: 1 });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.clickTUTargetButton();
    await PickingGraiScanPanel.expectScannerVisible();

    // Read as GS1 AI 8003, this GRAI had no mapping: 422 GRAINoMatchingTUType and the screen stayed.
    await PickingGraiScanPanel.scanGrai({ graiString: grai });
    await PickingJobLineScreen.waitForScreen();
    await ErrorToast.expectNoErrorToast();

    await PickingJobLineScreen.clickScanButton();
    await PickLineScanScreen.waitForScreen();
    await PickLineScanScreen.typeQRCode(masterdata.handlingUnits.HU1.qrCode);
    await GetQuantityDialog.fillAndPressDone({ expectQtyEntered: '1' });
    await PickingJobLineScreen.waitForScreen();
    await PickingJobLineScreen.goBack();
    await PickingJobScreen.complete();

    await Backend.expect({
        title: 'The picked TU carries the scanned 8003… GRAI unchanged',
        pickings: {
            [pickingJobId]: {
                shipmentSchedules: {
                    P1: {
                        qtyPicked: [{ qtyPicked: '4 PCE', qtyTUs: 1, qtyLUs: 1, vhu: '-', tu: 'tu1', lu: 'lu1', processed: true, shipmentLineId: 'shipmentLineId1' }],
                    },
                },
            },
        },
        hus: {
            tu1: {
                attributes: { GRAI: grai },
                bpartner: 'BP1',
                bpartnerLocation: 'BP1',
            },
        },
    });
});
