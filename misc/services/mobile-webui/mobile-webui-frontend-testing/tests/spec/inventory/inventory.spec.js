import { test } from "../../../playwright.config";
import { ApplicationsListScreen } from "../../utils/screens/ApplicationsListScreen";
import { Backend } from "../../utils/screens/Backend";
import { LoginScreen } from "../../utils/screens/LoginScreen";
import { InventoryJobsListScreen } from '../../utils/screens/inventory/InventoryJobsListScreen';
import { InventoryJobScreen } from '../../utils/screens/inventory/InventoryJobScreen';

const createMasterdata = async () => {
    return await Backend.createMasterdata({
        language: 'en_US',
        request: {
            login: {
                user: { language: 'en_US', workplace: "workplace1" }
            },
            bpartners: { "BP1": {} },
            warehouses: { "wh": {} },
            workplaces: {
                workplace1: { warehouse: 'wh' },
            },
            products: {
                "P1": { price: 1 },
            },
            packingInstructions: {
                "PI":
                    { lu: "LU", qtyTUsPerLU: 20, tu: "TU", product: "P1", qtyCUsPerTU: 4 },
            },
            handlingUnits: {
                "HU1": { product: 'P1', warehouse: 'wh', packingInstructions: 'PI' }
            },
            inventories: {
                "inv1": {
                    warehouse: 'wh',
                    date: '2025-03-01T00:00:00.000+02:00',
                    products: ['P1'],
                }
            },
        }
    })
}

// noinspection JSUnusedLocalSymbols
test('Simple inventory test', async ({ page }) => {
    const masterdata = await createMasterdata();

    await LoginScreen.login(masterdata.login.user);
    await ApplicationsListScreen.expectVisible();
    await ApplicationsListScreen.startApplication('inventory');
    await InventoryJobsListScreen.waitForScreen();

    await InventoryJobsListScreen.startJob({ index: 1 })
    await InventoryJobScreen.expectLineButton({
        productId: masterdata.products.P1.id,
        locatorId: masterdata.warehouses.wh.locatorId,
        qtyBooked: '80 Stk',
        qtyCount: '80 Stk',
    })
    await InventoryJobScreen.countHU({
        locatorQRCode: masterdata.warehouses.wh.locatorQRCode,
        huQRCode: masterdata.handlingUnits.HU1.qrCode,
        expectQtyBooked: '80 Stk',
        qtyCount: 90,
    })
    await InventoryJobScreen.expectLineButton({
        productId: masterdata.products.P1.id,
        locatorId: masterdata.warehouses.wh.locatorId,
        qtyBooked: '80 Stk',
        qtyCount: '90 Stk',
    })
});
