import { Backend } from '../../utils/screens/Backend';
import { test } from '../../../playwright.config';
import { LoginScreen } from '../../utils/screens/LoginScreen';
import { ApplicationsListScreen } from '../../utils/screens/ApplicationsListScreen';
import { ManufacturingJobsListScreen } from '../../utils/screens/manufacturing/ManufacturingJobsListScreen';
import { ManufacturingJobScreen } from '../../utils/screens/manufacturing/ManufacturingJobScreen';
import { MaterialReceiptLineScreen } from '../../utils/screens/manufacturing/receipt/MaterialReceiptLineScreen';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: "en_US",
        request: {
            login: {
                user: { language: "en_US" },
            },
            mobileConfig: {},
            warehouses: {
                "wh": {},
            },
            products: {
                "COMP1": {},
                "COMP2": {},
                "BOM": {
                    uomConversions: [{ from: 'PCE', to: 'KGM', multiplyRate: 0.10, isCatchUOMForProduct: true }],
                    bom: {
                        lines: [
                            { product: 'COMP1', qty: 1 },
                            { product: 'COMP2', qty: 2 },
                        ]
                    }
                },
            },
            packingInstructions: {
                "BOM_PI": { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "BOM", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU_COMP1": { product: 'COMP1', warehouse: 'wh', qty: 1000 },
                "HU_COMP2": { product: 'COMP2', warehouse: 'wh', qty: 1000 },
            },
            manufacturingOrders: {
                "PP1": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                },
                "PP2": {
                    warehouse: 'wh',
                    product: 'BOM',
                    qty: 100,
                    datePromised: '2025-03-01T00:00:00.000+02:00',
                }
            },
        }
    });
}

// noinspection JSUnusedLocalSymbols
test('To a new TU, manual input', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({
        switchToManualInput: true,
        qtyEntered: 9,
        catchWeight: 0.987,
        expectGoBackToJob: true
    });
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '100 Stk',
        qtyReceived: '9 Stk',
        //catchWeight: '987 g', // not displayed, missing feature
    })
    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '9 PCE' },
                ]
            }
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '9 PCE' },
                attributes: { 'WeightNet': '0.987' }
            }
        }
    })

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('To a new TU, scanning L+M QR codes', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();
    const { jobId } = await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

    await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
    await MaterialReceiptLineScreen.selectNewTUTarget({ tuPIItemProductTestId: masterdata.packingInstructions.BOM_PI.tuPIItemProductTestId });
    await MaterialReceiptLineScreen.receiveQty({
        catchWeightQRCode: [
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500',
            'LMQ#1#0.101#08.11.2025#500'
        ],
        expectGoBackToJob: false
    });
    await MaterialReceiptLineScreen.goBack();
    await Backend.expect({
        manufacturings: {
            [jobId]: {
                receivedHUs: [
                    { tu: 'tu1', qty: '4 PCE' },
                    { tu: 'tu1', qty: '1 PCE' },
                    { tu: 'tu1', qty: '1 PCE' },
                ]
            }
        },
        hus: {
            'tu1': {
                storages: { 'BOM': '6 PCE' },
                attributes: { 'WeightNet': '0.303' }
            }
        }
    })
    await ManufacturingJobScreen.expectReceiveButton({
        index: 1,
        qtyToReceive: '100 Stk',
        qtyReceived: '6 Stk', // 3 x 4 (4 is the standard qtyCUsPerTU of the TU)
        //catchWeight: '303 g', // not displayed, missing feature
    })

    await ManufacturingJobScreen.complete();
});

// noinspection JSUnusedLocalSymbols
test('To a new TU, scanning L+M QR codes from 2 manufacturing orders', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('mfg');
    await ManufacturingJobsListScreen.waitForScreen();

    let mainProductsQRCode;
    await test.step("Receive on first manufacturing order", async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP1.documentNo });

        mainProductsQRCode = await ManufacturingJobScreen.generateSingleHUQRCode({ piTestId: masterdata.packingInstructions.BOM_PI.tuPITestId, numberOfHUs: 1 });

        await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: mainProductsQRCode });
        await MaterialReceiptLineScreen.receiveQty({
            catchWeightQRCode: ['LMQ#1#0.60#08.11.2025#500', 'LMQ#1#0.041#08.11.2025#500'],
            expectGoBackToJob: false
        });
        await MaterialReceiptLineScreen.goBack();
        await ManufacturingJobScreen.expectReceiveButton({
            index: 1,
            qtyToReceive: '100 Stk',
            qtyReceived: '2 Stk',
            //catchWeight: '101 g', // not displayed, missing feature
        })
        await Backend.expect({
            hus: {
                [mainProductsQRCode]: {
                    storages: { 'BOM': '2 PCE' },
                    attributes: { 'WeightNet': '0.101' }
                }
            }
        });

        await ManufacturingJobScreen.complete();
        // await ManufacturingJobScreen.goBack();
    });

    await test.step("Receive on second manufacturing order in the same HU", async () => {
        await ManufacturingJobsListScreen.startJob({ documentNo: masterdata.manufacturingOrders.PP2.documentNo });

        await ManufacturingJobScreen.clickReceiveButton({ index: 1 }); // i.e., main product
        await MaterialReceiptLineScreen.selectExistingHUTarget({ huQRCode: mainProductsQRCode });
        await MaterialReceiptLineScreen.receiveQty({
            catchWeightQRCode: ['LMQ#1#0.010#09.11.2025#501', 'LMQ#1#0.020#09.11.2025#501'],
            expectGoBackToJob: false
        });
        await MaterialReceiptLineScreen.goBack();
        await ManufacturingJobScreen.expectReceiveButton({
            index: 1,
            qtyToReceive: '100 Stk',
            qtyReceived: '2 Stk',
            //catchWeight: '30 g', // not displayed, missing feature
        })
        await Backend.expect({
            hus: {
                [mainProductsQRCode]: {
                    storages: { 'BOM': '4 PCE' },
                    attributes: { 'WeightNet': '0.131' }
                }
            }
        });

        await ManufacturingJobScreen.complete();
        // await ManufacturingJobScreen.goBack();
    });
});
