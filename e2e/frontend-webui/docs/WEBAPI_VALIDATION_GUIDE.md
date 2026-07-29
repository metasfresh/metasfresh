# WebAPI Validation Guide - Quick Reference

**Critical Knowledge for E2E Test Development**

## The Golden Rule

**Parent records MUST be saved before you can add child records.**

## Quick Checklist

Before adding order lines, invoice lines, or any child records:

1. ☑️ Parent record created (`/window/{windowId}/NEW`)
2. ☑️ Key fields filled (e.g., C_BPartner triggers auto-fill)
3. ☑️ All mandatory fields valid
4. ☑️ Record saved to database (`saveStatus.saved = true`)
5. ☑️ Tab allows new records (`allowCreateNew = true`)
6. ☑️ NOW you can add child records

## Common Failure Pattern

```javascript
// ❌ WRONG - This will fail silently
await SalesOrderPage.clickNew();
await SalesOrderPage.selectCustomer(customer);
await SalesOrderPage.addOrderLine({ product, quantity });  // FAILS - not saved yet!
```

```javascript
// ✅ CORRECT - Wait for save first
await SalesOrderPage.clickNew();
const recordId = page.url().split('/').pop();

await SalesOrderPage.selectCustomer(customer);
await waitForRecordSaved('143', recordId);  // Wait for auto-fill and save
await waitForTabAllowsNew('143', recordId, 'AD_Tab-187');  // Wait for tab

await SalesOrderPage.addOrderLine({ product, quantity });  // NOW works!
```

## Essential Imports

```javascript
import {
  debugRecordValidation,
  waitForRecordSaved,
  waitForTabAllowsNew,
  getValidationStatus,
} from '../utils/WebAPIValidation';
```

## Debug During Development

```javascript
// Extract record ID from URL
const url = page.url();  // http://localhost:3000/#/window/143/1000020
const recordId = url.split('/').pop();  // "1000020"

// See exactly what's wrong
await debugRecordValidation('143', recordId);

// Output shows:
// === Record 143/1000020 Validation Summary ===
// Saved: false
// Valid: false
// Reason: Fill mandatory fields: Warehouse
//
// Missing Fields:
//   - M_Warehouse_ID: Fill mandatory fields: Warehouse
//   - PreparationDate: Fill mandatory fields: Date ready
//
// Tabs:
//   - AD_Tab-187 (Order Lines): allowCreateNew=false (ParentDocumentNew)
```

## Auto-Fill Magic

When you set `C_BPartner_ID`, metasfresh auto-fills:
- Location
- Pricing System
- Currency
- Payment Rule

**If these don't auto-fill:** The business partner data is incomplete. Fill manually or fix partner setup.

## Common Tab IDs

| Window | Tab ID | Purpose |
|--------|--------|---------|
| Sales Order (143) | AD_Tab-187 | Order lines |
| Purchase Order (181) | AD_Tab-294 | Order lines |
| Invoice (318) | AD_Tab-263 | Invoice lines |

## Utilities Quick Reference

```javascript
// Check if saved
const saveStatus = await getSaveStatus(windowId, recordId);
console.log(saveStatus.saved);  // true/false

// Check validation
const validation = await getValidationStatus(windowId, recordId);
console.log(validation.missingFields);  // Array of missing mandatory fields

// Wait for save (with retries)
await waitForRecordSaved(windowId, recordId, {
  maxRetries: 10,
  retryDelayMs: 500
});

// Wait for tab (with retries)
await waitForTabAllowsNew(windowId, recordId, tabId, {
  maxRetries: 10,
  retryDelayMs: 500
});
```

## Why This Matters

**Without validation checks:**
- Tests fail mysteriously ("batch entry button not found")
- Debugging takes hours
- No clear error messages

**With validation checks:**
- Clear error: "Fill mandatory fields: Warehouse"
- Know exactly what to fix
- Tests fail fast with useful messages

## Integration Pattern

```javascript
// In Page Object (e.g., SalesOrderPage.js)
static async createNewOrder(customerCode) {
  const page = getPage();

  await this.goto();
  await this.clickNew();

  // Get record ID
  const recordId = page.url().split('/').pop();

  // Fill customer (triggers auto-fill)
  await this.selectCustomer(customerCode);

  // Wait for save
  await waitForRecordSaved('143', recordId);

  // Return ID for use in tests
  return recordId;
}

// In Test
const recordId = await SalesOrderPage.createNewOrder(customerCode);
await waitForTabAllowsNew('143', recordId, 'AD_Tab-187');
await SalesOrderPage.addOrderLine({ product, quantity });  // Safe!
```

## Troubleshooting

**Problem**: `waitForRecordSaved` times out

**Solutions**:
1. Run `debugRecordValidation` to see missing fields
2. Check if business partner has complete data (pricing system, location)
3. Manually fill missing mandatory fields in test
4. Increase `maxRetries` if auto-fill is slow

**Problem**: `allowCreateNew` stays false

**Solutions**:
1. Parent record not saved - check `saveStatus.saved`
2. Parent has validation errors - check `validStatus.valid`
3. Wrong tab ID - verify with `fetch-window-layout.js`

## Real Example: Sales Order Test

```javascript
test('Create sales order with lines', async ({ page }) => {
  // Create test data
  const masterdata = await Backend.createMasterdata({
    request: {
      bpartners: { CUSTOMER1: { isCustomer: true, isSoPriceList: true } },
      products: { Product1: { name: 'Test Product', prices: [{ price: 50.0 }] } }
    }
  });

  // Login
  await LoginPage.login(masterdata.login.user);

  // Create sales order
  await SalesOrderPage.goto();
  await SalesOrderPage.clickNew();

  // Get record ID from URL
  const url = page.url();
  const recordId = url.split('/').pop();

  // Fill customer
  await SalesOrderPage.selectCustomer(masterdata.bpartners.CUSTOMER1.bpartnerCode);

  // DEBUG: See what's missing
  await debugRecordValidation('143', recordId);

  // Wait for auto-fill and save
  await waitForRecordSaved('143', recordId);

  // Wait for order lines tab
  await waitForTabAllowsNew('143', recordId, 'AD_Tab-187');

  // NOW add order line
  await SalesOrderPage.addOrderLine({
    product: masterdata.products.Product1.productCode,
    quantity: '10'
  });

  // Complete order
  await SalesOrderPage.complete();

  // Verify
  const docNo = await SalesOrderPage.getDocumentNo();
  expect(docNo).toBeTruthy();
});
```

## Key Takeaway

**Always validate parent records before adding child records.**

Use `waitForRecordSaved()` and `waitForTabAllowsNew()` in your Page Object methods to ensure proper sequencing.
