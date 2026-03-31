import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { RawMaterialIssueLineScreen } from '../../utils/screens/manufacturing/issue/RawMaterialIssueLineScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';
/**
 * me03#28242: mobile UI production does not work with very small quantities (e.g. 0.01913 kg)
 * when issuing tolerance is enforced.
 *
 * Root cause: Quantity.add/subtract(Percent) rounds the base qty to C_UOM.StdPrecision
 * before computing the tolerance band, collapsing it for sub-gram quantities.
 */

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: {
                user: { language: 'en_US' },
            },
            mobileConfig: {},
            warehouses: {
                wh: {},
            },
            products: {
                COMP_SMALL: { uom: 'KGM' },
                BOM: {
                    bom: {
                        lines: [
                            {
                                product: 'COMP_SMALL',
                                qty: 0.01913,
                                uom: 'KGM',
                                issuingTolerancePerc: 1,
                            },
                        ],
                    },
                },
            },
            packingInstructions: {
                PI: { lu: 'LU', qtyTUsPerLU: 20, tu: 'TU', product: 'BOM', qtyCUsPerTU: 10 },
            },
            handlingUnits: {
                HU_SMALL: { product: 'COMP_SMALL', warehouse: 'wh', qty: 1 },
            },
            manufacturingOrders: {
                PP1: {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 1,
                    datePromised: '2026-03-30T00:00:00.000+02:00',
                },
            },
        },
    });
};

// noinspection JSUnusedLocalSymbols
test('Issue raw material with small qty (0.01913 kg) and 1% tolerance', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({
        documentNo: masterdata.manufacturingOrders.PP1.documentNo,
    });

    // Issue the small qty component — this should succeed with the fix
    // Before the fix, tolerance band collapses to a single point and rejects the qty
    await ManufacturingJobScreen.clickIssueButton({ index: 1 });
    await RawMaterialIssueLineScreen.scanQRCode({
        qrCode: masterdata.handlingUnits.HU_SMALL.qrCode,
    });
    await RawMaterialIssueLineScreen.goBack();

    // Receive the finished product
    await ManufacturingJobScreen.clickReceiveButton({ index: 1 });
    await MaterialReceiptLineScreen.selectNewLUTarget({
        luPIItemTestId: masterdata.packingInstructions.PI.luPIItemTestId,
    });
    await MaterialReceiptLineScreen.receiveQty({
        expectQtyEntered: '1',
        qtyEntered: '1',
    });

    await ManufacturingJobScreen.complete();

    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [{ lu: 'lu1', qty: '1 PCE' }],
            },
        },
        hus: {
            lu1: {
                huStatus: 'A',
                storages: { BOM: '1 PCE' },
            },
        },
    });
});
