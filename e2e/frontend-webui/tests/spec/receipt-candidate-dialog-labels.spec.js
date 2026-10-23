import { expect } from '@playwright/test';
import { test } from '../../playwright.config';
import { allure } from 'allure-playwright';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';
import { DashboardPage } from '../utils/pages/DashboardPage';
import { PurchaseOrderPage } from '../utils/pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from '../utils/pages/ReceiptCandidatesPage';

/**
 * Features:
 * - F65010: Material Receipt Candidates
 * - F5001: Packing Instruction
 *
 * The receive dialogs of the purchase receipt candidates keep their own captions, independent of the
 * production receipt dialog that was changed to follow the packing instruction:
 * - "HUs annehmen" (receive with LU/TU configuration): the quantity per TU is "Menge pro TU" / "Qty CU per TU".
 * - "CUs annehmen mit Menge" (receive loose CUs): the quantity is "Menge CU" / "Quantity CU" (it is no quantity per TU,
 *   there is no TU), and receiving loose CUs still works.
 *
 * The captions are the requirement here, so this is the one place that compares localized text.
 */

const QTY_PER_TU = 15; // packing instruction "x 15 Stk"
const PALLET_CAPACITY = 4; // TUs per pallet
const PO_QTY = '60';
const QTY_LOOSE_CUS = 37; // received loose; differs from the order quantity and from the packing instruction quantity

const RECEIVE_HUS_USING_CONFIG = 'WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig';
const RECEIVE_CUS_WITH_QTY = 'WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam';

const LANGUAGES = [
  { language: 'de_DE', label: 'German', qtyPerTULabel: 'Menge pro TU', qtyCULabel: 'Menge CU' },
  { language: 'en_US', label: 'English', qtyPerTULabel: 'Qty CU per TU', qtyCULabel: 'Quantity CU' },
];

/** Purchase order for a product that has a packing instruction, completed; opens its receipt candidate. */
const openReceiptCandidateOfNewPurchaseOrder = async (language) => {
  const masterdata = await Backend.createMasterdata({
    language,
    request: {
      login: { user: { language } },
      bpartners: { VENDOR1: { isVendor: true, isCustomer: false, isSoPriceList: false } },
      products: { PROD1: { prices: [{ price: 10.0, currencyCode: 'EUR' }] } },
      packingInstructions: {
        PI1: { lu: 'LU1', qtyTUsPerLU: PALLET_CAPACITY, tu: 'TU1', product: 'PROD1', qtyCUsPerTU: QTY_PER_TU },
      },
    },
  });

  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await PurchaseOrderPage.goto();
  await PurchaseOrderPage.clickNew();
  await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
  await PurchaseOrderPage.goToOrderLineTab();
  await PurchaseOrderPage.addOrderLine({ product: masterdata.products.PROD1.productCode, quantity: PO_QTY });
  await PurchaseOrderPage.complete();
  await PurchaseOrderPage.openRelatedReceiptCandidate();
  await ReceiptCandidatesPage.expectQuickActionsVisible();
  return masterdata;
};

LANGUAGES.forEach(({ language, label, qtyPerTULabel, qtyCULabel }) => {
  test.describe(`Purchase receipt dialog captions (${label})`, () => {
    test(`TC7 "HUs annehmen" with LU/TU configuration still labels the quantity per TU (${label} UI)`, async ({ page }) => { // `page` must be requested: it initialises the shared page that getPage() returns
      allure.epic('E0140: Purchasing');
      allure.tag('F65010: Material Receipt Candidates');
      allure.tag('F65010');
      allure.tag('F5001: Packing Instruction');
      allure.tag('F5001');
      allure.story('Receive dialogs keep their captions');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      await openReceiptCandidateOfNewPurchaseOrder(language);

      await ReceiptCandidatesPage.selectQuickAction(RECEIVE_HUS_USING_CONFIG);

      expect(await ReceiptCandidatesPage.getProcessFieldLabel('QtyCUsPerTU')).toBe(qtyPerTULabel);

      await ReceiptCandidatesPage.cancelProcessDialog();
    });

    test(`TC8 "CUs annehmen mit Menge" labels the quantity "Menge CU" and still receives loose CUs (${label} UI)`, async ({ page }) => { // `page` must be requested: it initialises the shared page that getPage() returns
      allure.epic('E0140: Purchasing');
      allure.tag('F65010: Material Receipt Candidates');
      allure.tag('F65010');
      allure.tag('F5001: Packing Instruction');
      allure.tag('F5001');
      allure.story('Receive dialogs keep their captions');
      allure.severity('normal');
      allure.parameter('Language', language);
      allure.tag(language);

      await openReceiptCandidateOfNewPurchaseOrder(language);

      await ReceiptCandidatesPage.selectQuickAction(RECEIVE_CUS_WITH_QTY);

      expect(await ReceiptCandidatesPage.getProcessFieldLabel('QtyCUsReceived')).toBe(qtyCULabel);

      await ReceiptCandidatesPage.setProcessNumberField('QtyCUsReceived', QTY_LOOSE_CUS);
      await ReceiptCandidatesPage.startProcess();
      await ReceiptCandidatesPage.waitForReceiptCreation();

      // the received HU, read back from the server's HU editor view
      const receivedHUs = await ReceiptCandidatesPage.readHUEditorRows();
      expect(receivedHUs.length, 'one loose-CU HU received').toBe(1);
      // a whole cell is the quantity (e.g. "37" or "37.000"); HU code / date cells cannot match by digits inside them
      const quantityCells = receivedHUs[0].filter((cell) => new RegExp(`^${QTY_LOOSE_CUS}([.,]0+)?$`).test(cell));
      expect(quantityCells.length, `a quantity cell of ${QTY_LOOSE_CUS} in ${JSON.stringify(receivedHUs[0])}`).toBeGreaterThan(0);

      await ReceiptCandidatesPage.closeHUEditorModal();
    });
  });
});
