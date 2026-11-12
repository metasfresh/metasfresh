import { Backend } from './Backend';
import { LoginPage } from './pages/LoginPage';
import { DashboardPage } from './pages/DashboardPage';
import { PurchaseOrderPage } from './pages/PurchaseOrderPage';
import { ReceiptCandidatesPage } from './pages/ReceiptCandidatesPage';

/**
 * Creates a vendor and one purchase-priced product (the minimum to complete a purchase order and receive it).
 * @param {{language?: string, productName?: string}} [options]
 */
export const createPurchaseReceiptMasterdata = async ({ language = 'en_US', productName = 'Test Product for receipt' } = {}) =>
  await Backend.createMasterdata({
    request: {
      login: { user: { language } },
      bpartners: { VENDOR1: { isVendor: true, isCustomer: false, isSoPriceList: false } },
      products: {
        Product1: {
          name: `${productName} ${Date.now()}`,
          type: 'Item',
          prices: [{ price: 10.0, currencyCode: 'EUR' }],
        },
      },
    },
  });

/**
 * Logs in, creates and completes a purchase order for the masterdata's product (quantity 5) and opens the
 * order's receipt candidate (the non-modal receipt-candidates list) with its quick actions visible.
 */
export const openReceiptCandidateOfNewPurchaseOrder = async (masterdata) => {
  await LoginPage.goto();
  await LoginPage.login(masterdata.login.user);
  await DashboardPage.expectVisible();

  await PurchaseOrderPage.goto();
  await PurchaseOrderPage.clickNew();
  await PurchaseOrderPage.selectBusinessPartner(masterdata.bpartners.VENDOR1.bpartnerCode);
  await PurchaseOrderPage.goToOrderLineTab();
  await PurchaseOrderPage.addOrderLine({ product: masterdata.products.Product1.productCode, quantity: '5' });
  await PurchaseOrderPage.complete();

  await PurchaseOrderPage.openRelatedReceiptCandidate();
  await ReceiptCandidatesPage.expectQuickActionsVisible();
};
