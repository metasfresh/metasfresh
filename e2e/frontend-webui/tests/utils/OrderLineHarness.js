import { Backend } from './Backend';

/**
 * Masterdata for the sales order-line grid specs: a customer and a priced product, created per run.
 * A plain module, so importing it registers no test cases.
 */

export const ORDER_LINE_TAB_ID = 'AD_Tab-187';
export const DEFAULT_LANGUAGE = 'de_DE';

/**
 * Create a customer and a priced product for this run via the Backend masterdata API.
 *
 * The product MUST carry a resolvable price: the product-lookup typeahead silently excludes
 * any product with no price condition for the order's customer/price-list combination — an
 * out-of-candidate-set product then returns "no results" with no error, which reads like a
 * flake but is a real, silent business-rule filter.
 */
export async function createMasterdata(language = DEFAULT_LANGUAGE) {
  return await Backend.createMasterdata({
    request: {
      login: {
        user: { language, firstname: 'E2E', lastname: 'Tester' },
      },
      bpartners: {
        CUSTOMER1: {
          isVendor: false,
          isCustomer: true,
          isSoPriceList: true,
          name: 'Customer',
        },
      },
      products: {
        Product1: {
          name: 'BFPROD',
          type: 'Item',
          prices: [{ price: 12.5, currencyCode: 'EUR' }],
        },
      },
    },
  });
}
