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
 *
 * The captions are the requirement here, so this is the one place that compares localized text.
 */

const QTY_PER_TU = 15; // packing instruction "x 15 Stk"
const PALLET_CAPACITY = 4; // TUs per pallet
const PO_QTY = '60';

const RECEIVE_HUS_USING_CONFIG = 'WEBUI_M_ReceiptSchedule_ReceiveHUs_UsingConfig';

const LANGUAGES = [
  { language: 'de_DE', label: 'German', qtyPerTULabel: 'Menge pro TU' },
  { language: 'en_US', label: 'English', qtyPerTULabel: 'Qty CU per TU' },
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

LANGUAGES.forEach(({ language, label, qtyPerTULabel }) => {
  test.describe(`Purchase receipt dialog captions (${label})`, () => {
    test(`TC7 "HUs annehmen" with LU/TU configuration still labels the quantity per TU (${label} UI)`, async ({ page }) => {
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
  });
});
