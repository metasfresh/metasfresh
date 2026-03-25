# WebAPI Validation - Parent-Child Record Creation

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Quick Reference** for test reliability

## The Problem

When creating parent-child records (orders → order lines, invoices → invoice lines):

1. Parent record is created in memory but **NOT saved to database**
2. Mandatory fields must be filled
3. Some fields are auto-filled when key fields (like C_BPartner) are set
4. Only after **ALL mandatory fields are valid** can the record be saved
5. Only after the record is **saved** can you add child records

**Common Error**: Attempting to add order lines before the parent order is saved will fail silently.

## The Solution: WebAPI Validation

metasfresh WebAPI provides complete record state via:
```
GET http://localhost:8080/rest/api/window/{windowId}/{recordId}
```

Returns:
- **`saveStatus`**: Is record saved? What's blocking save?
- **`validStatus`**: Are all mandatory fields valid?
- **`fieldsByName`**: Each field's value, validation status, mandatory flag
- **`includedTabsInfo`**: Can child records be added? (`allowCreateNew`)

## Workflow Pattern

```javascript
import {
  debugRecordValidation,
  waitForRecordSaved,
  waitForTabAllowsNew,
} from '../utils/WebAPIValidation';

// 1. Fill parent record fields
await SalesOrderPage.goto();
await SalesOrderPage.clickNew();
await SalesOrderPage.selectCustomer(customerCode);

// 2. Get record ID from URL
const url = page.url();  // e.g., http://localhost:3000/#/window/143/1000020
const recordId = url.split('/').pop();

// 3. DEBUG: Check what's missing (during development)
await debugRecordValidation('143', recordId);

// 4. Fill remaining mandatory fields (if any)
await SalesOrderPage.fillWarehouse('Standard Warehouse');

// 5. Wait for record to be saved
await waitForRecordSaved('143', recordId, { maxRetries: 10, retryDelayMs: 500 });

// 6. Wait for tab to allow creating child records
await waitForTabAllowsNew('143', recordId, 'AD_Tab-187', { maxRetries: 10 });

// 7. NOW safe to add order lines
await SalesOrderPage.addOrderLine({ product: productCode, quantity: '10' });
```

## Auto-Fill Behavior

When you fill key fields, metasfresh **auto-fills related fields**:

**Example: Sales Order after setting C_BPartner:**
- `C_BPartner_Location_ID` → Auto-set from partner's default location
- `M_PricingSystem_ID` → Auto-set from partner's pricing system
- `C_Currency_ID` → Auto-set from pricing system's currency
- `PaymentRule` → Auto-set from partner's default payment rule

**If auto-fill doesn't happen:**
- Partner data is incomplete (e.g., no pricing system assigned)
- You must fill these fields manually in the test

## Key Utilities

```javascript
// Get complete record data
const recordData = await getRecordData(windowId, recordId);

// Get validation status (what's blocking save?)
const validation = await getValidationStatus(windowId, recordId);
console.log('Missing fields:', validation.missingFields);

// Get save status
const saveStatus = await getSaveStatus(windowId, recordId);
console.log('Is saved:', saveStatus.saved);

// Get tab info (can add child records?)
const tabInfo = await getTabInfo(windowId, recordId, tabId);
console.log('Can add lines:', tabInfo.allowCreateNew);
console.log('Reason:', tabInfo.allowCreateNewReason);

// Wait for record to be saved (with retries)
await waitForRecordSaved(windowId, recordId);

// Wait for tab to allow new records (with retries)
await waitForTabAllowsNew(windowId, recordId, tabId);

// Debug: Print comprehensive validation summary
await debugRecordValidation(windowId, recordId);
```

## Common Tab IDs

| Window | Tab Name | Tab ID | Purpose |
|--------|----------|--------|---------|
| Sales Order (143) | Order Line | AD_Tab-187 | Sales order lines (C_OrderLine) |
| Purchase Order (181) | PO Line | AD_Tab-294 | Purchase order lines (C_OrderLine) |
| Shipment Schedule (500221) | N/A | N/A | Single record, no tabs |

## Debugging Tips

1. **Always use `debugRecordValidation()` during development** - shows exactly what's missing

2. **Check auto-fill results** - after setting C_BPartner, verify Currency, Pricing System were auto-filled

3. **Common reasons for "allowCreateNew: false":**
   - `"ParentDocumentNew"` → Parent record not yet saved
   - `"ParentInvalid"` → Parent record has validation errors

4. **Timeouts are normal** - Auto-fill happens asynchronously. Use retries.

## Integration with Page Objects

Page Object methods that create parent records should:
1. Return the record ID (extracted from URL)
2. Wait for record to be saved before returning
3. Provide methods to fill all potentially mandatory fields

```javascript
static async createNewSalesOrder(customerCode) {
  const page = getPage();

  await this.goto();
  await this.clickNew();

  // Get record ID from URL
  const url = page.url();
  const recordId = url.split('/').pop();

  // Fill customer (triggers auto-fill)
  await this.selectCustomer(customerCode);

  // Wait for auto-fill and save
  await waitForRecordSaved('143', recordId);

  return recordId;
}
```

## Checklist

- [ ] Get record ID from URL after creating new record
- [ ] Use `debugRecordValidation()` during development to see missing fields
- [ ] Fill all mandatory fields (check auto-fill results)
- [ ] Call `waitForRecordSaved()` before adding child records
- [ ] Call `waitForTabAllowsNew()` to verify tab is ready
- [ ] Add child records only after both waits succeed
