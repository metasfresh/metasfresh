import { ID_BACK_BUTTON, page, SLOW_ACTION_TIMEOUT } from '../../../common';
import { test } from '../../../../../playwright.config';
import { expect } from '@playwright/test';
import { ReceiptReceiveTargetScreen } from './ReceiptReceiveTargetScreen';
import { GetQuantityDialog } from '../../picking/GetQuantityDialog';
import { ReceiptNewHUScreen } from './ReceiptNewHUScreen';
import { ManufacturingJobScreen } from '../ManufacturingJobScreen';
import { ManufacturingReceiptScanScreen } from './ManufacturingReceiptScanScreen';

const NAME = 'MaterialReceiptLineScreen';
/** @returns {import('@playwright/test').Locator} */
const containerElement = () => page.locator('#MaterialReceiptLineScreen');

export const MaterialReceiptLineScreen = {
    waitForScreen: async () => await test.step(`${NAME} - Wait for screen`, async () => {
        await containerElement().waitFor({ timeout: SLOW_ACTION_TIMEOUT });
        await page.locator('.loading').waitFor({ state: 'detached', timeout: SLOW_ACTION_TIMEOUT });
    }),

    expectVisible: async () => await test.step(`${NAME} - Expect screen to be displayed`, async () => {
        await expect(containerElement()).toBeVisible();
    }),

    clickReceiveTargetButton: async () => await test.step(`${NAME} - Click receive target button`, async () => {
        await page.getByTestId('receive-target-button').tap();
        await ReceiptReceiveTargetScreen.waitForScreen();
    }),

    // The receive target is picked without the "new Gebinde vs. scan an existing one" chooser:
    // tapping the receive target lands straight on the packing-instruction list.
    clickReceiveTargetButtonExpectingNewHUScreen: async () => await test.step(`${NAME} - Click receive target button (expecting the packing instruction list)`, async () => {
        await page.getByTestId('receive-target-button').tap();
        await ReceiptNewHUScreen.waitForScreen();
        await ReceiptReceiveTargetScreen.expectNotVisible();
    }),

    // The receive target needs no choosing at all: the chooser is switched off and a single pallet is
    // the only target on offer, so tapping the receive target selects that pallet and the operator
    // stays on the receive line, ready to enter the quantity.
    clickReceiveTargetButtonExpectingTargetSelected: async ({ luName }) => await test.step(`${NAME} - Click receive target button (expecting pallet "${luName}" to be selected right away)`, async () => {
        await page.getByTestId('receive-target-button').tap();
        await MaterialReceiptLineScreen.expectReceiveTargetButtonNames({ luName });
        await ReceiptNewHUScreen.expectNotVisible();
        await ReceiptReceiveTargetScreen.expectNotVisible();
    }),

    // Once a target is set, the receive target button names it instead of the generic "Receive target".
    expectReceiveTargetButtonNames: async ({ luName }) => await test.step(`${NAME} - Expect the receive target button to name "${luName}"`, async () => {
        await MaterialReceiptLineScreen.expectVisible();
        const button = page.getByTestId('receive-target-button');
        await expect(button).toBeVisible({ timeout: SLOW_ACTION_TIMEOUT });
        await expect(button).toContainText(luName, { timeout: SLOW_ACTION_TIMEOUT });
    }),

    selectNewLUTarget: async ({ luPIItemTestId }) => await test.step(`${NAME} - Select New LU target "${luPIItemTestId}"`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();
        await ReceiptNewHUScreen.clickLUTarget({ luPIItemTestId });
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    selectNewTUTarget: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Select New TU target "${tuPIItemProductTestId}"`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();
        await ReceiptNewHUScreen.clickTUTarget({ tuPIItemProductTestId });
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    expectNewTUTargetNotPresent: async ({ tuPIItemProductTestId }) => await test.step(`${NAME} - Expect New TU target "${tuPIItemProductTestId}" not present`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickNewHUButton();
        await ReceiptNewHUScreen.expectTUTargetNotPresent({ tuPIItemProductTestId });
    }),

    selectExistingHUTarget: async ({ huQRCode }) => await test.step(`${NAME} - Select existing HU target`, async () => {
        await MaterialReceiptLineScreen.clickReceiveTargetButton();
        await ReceiptReceiveTargetScreen.clickExistingHUButton();
        await ManufacturingReceiptScanScreen.typeQRCode(huQRCode);
        await MaterialReceiptLineScreen.waitForScreen();
    }),

    receiveQty: async ({ switchToManualInput, qtyEntered, expectQtyEntered, expectQtyInputVisible, expectCatchWeightVisible, lotNo, bestBeforeDate, expectLotNoVisible, expectBestBeforeDateVisible, catchWeight, catchWeightQRCode, expectGoBackToJob = true }) => await test.step(`${NAME} - Receive qty ${qtyEntered ? qtyEntered : ''}`, async () => {
        await page.getByTestId('receive-qty-button').tap();

        await GetQuantityDialog.waitForDialog();
        // Mfg receipt renders Lot / Best-before through the generic editable-attributes section
        // (attr-<code>-field), NOT the picking dialog's dedicated lotNo/bestBeforeDate rows. Their
        // M_Attribute codes are 'Lot-Nummer' and 'HU_BestBeforeDate'.
        if (expectLotNoVisible != null) {
            if (expectLotNoVisible) {
                await GetQuantityDialog.expectEditableAttributeVisible('Lot-Nummer');
            } else {
                await GetQuantityDialog.expectEditableAttributeNotVisible('Lot-Nummer');
            }
        }
        if (expectBestBeforeDateVisible != null) {
            if (expectBestBeforeDateVisible) {
                await GetQuantityDialog.expectEditableAttributeVisible('HU_BestBeforeDate');
            } else {
                await GetQuantityDialog.expectEditableAttributeNotVisible('HU_BestBeforeDate');
            }
        }
        if (lotNo != null) {
            await GetQuantityDialog.typeEditableAttribute('Lot-Nummer', lotNo);
        }
        if (bestBeforeDate != null) {
            await GetQuantityDialog.typeEditableAttributeDate('HU_BestBeforeDate', bestBeforeDate);
        }

        await GetQuantityDialog.fillAndPressDone({ switchToManualInput, expectQtyInputVisible, expectCatchWeightVisible, expectQtyEntered, qtyEntered, catchWeight, catchWeightQRCode });
        // await MaterialReceiptLineScreen.waitForScreen(); // while processing

        // final screen
        if (expectGoBackToJob) {
            await ManufacturingJobScreen.waitForScreen();
        } else {
            await MaterialReceiptLineScreen.waitForScreen();
        }
    }),

    receiveQtyWithQRCode: async ({ catchWeightQRCode }) => await test.step(`${NAME} - Receive via QrCode`, async () => {
        await page.getByTestId('receive-qty-button').tap();

        await GetQuantityDialog.fillAndPressDone({ catchWeightQRCode });
    }),

    goBack: async () => await test.step(`${NAME} - Go back`, async () => {
        await MaterialReceiptLineScreen.expectVisible();
        await page.locator(ID_BACK_BUTTON).tap();
        await ManufacturingJobScreen.waitForScreen();
    }),

    expectNoGebindeHintVisible: async () => await test.step(`${NAME} - Expect no-Gebinde hint near disabled Produzieren`, async () => {
        await expect(page.getByTestId('receive-no-gebinde-hint')).toBeVisible();
    }),

    // Restricting the offered target structures must not look like broken master data to the
    // operator: the red "no receiving Gebinde" hint belongs to the dead-end case only.
    expectNoGebindeHintNotVisible: async () => await test.step(`${NAME} - Expect NO no-Gebinde hint`, async () => {
        await MaterialReceiptLineScreen.expectVisible();
        await expect(page.getByTestId('receive-no-gebinde-hint')).toHaveCount(0);
    }),

    expectHeaderProperty:  async ({ caption, value }) => await test.step(`${NAME} - Check header property "${caption}" = "${value}"`, async () => {
        const row = await page.locator(
            `tr:has(th:text-is("${caption}")):has(td:has-text("${value}"))`
        );
        await expect(row).toHaveCount(1)
    }),
};