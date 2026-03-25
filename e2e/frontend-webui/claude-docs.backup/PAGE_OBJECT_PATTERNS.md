# Page Object Patterns & Dynamic Form Loading

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Timing patterns and Page Object structure**

## Dynamic Form Loading - Critical Pattern

**Problem**: Batch entry modals, lookups, and dynamic forms load asynchronously with backend API calls.

**Symptom**: Test looks for element before it's rendered, causing timeout errors.

**Solution**: Two-step waiting strategy

### Two-Step Wait Pattern

```javascript
// STEP 1: Wait for container (proves modal/form opened)
await page.locator('.quick-input-container').waitFor({
  state: 'visible',
  timeout: SLOW_ACTION_TIMEOUT,
});

// STEP 2: Wait for specific element (proves field rendered)
const productInput = page.locator('#lookup_M_Product_ID input.input-field');
await productInput.waitFor({
  state: 'visible',
  timeout: SLOW_ACTION_TIMEOUT,
});

// STEP 3: Now safe to interact
await productInput.click();
```

### Why This Works
1. **Container wait catches early failures** - If modal doesn't open, fails immediately
2. **Element wait catches rendering delays** - Waits for specific field to render
3. **Clear error messages** - Timeout indicates which step failed

### Common Containers
- `.quick-input-container` - Batch entry form (TableQuickInput component)
- `.modal-content` - Modal dialogs
- `.input-dropdown-list` - Dropdown menus
- `.tab-content` - Tab panels

### Example: Batch Entry Modal

```javascript
static async addOrderLine({ product, quantity }) {
  return await test.step(`Add order line: ${product} x ${quantity}`, async () => {
    const page = getPage();

    // Click batch entry toggle
    await page.getByTestId('batch-entry-toggle').click();

    // CRITICAL: Two-step wait
    // 1. Wait for form container
    await page.locator('.quick-input-container').waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // 2. Wait for product input field
    const productInput = page.locator('#lookup_M_Product_ID input.input-field');
    await productInput.waitFor({
      state: 'visible',
      timeout: SLOW_ACTION_TIMEOUT,
    });

    // Now safe to interact
    await productInput.click();
    await productInput.fill(product);
    // ... rest of interaction
  });
}
```

## Timeout Configuration

### Timeouts in playwright.config.js
```javascript
{
  timeout: 60000,  // 1 minute per test
}
```

### Timeouts in tests/utils/common.js
```javascript
export const DEFAULT_ACTION_TIMEOUT = 10000;       // 10s - Normal actions
export const SLOW_ACTION_TIMEOUT = 20000;          // 20s - Backend API calls
export const VERY_SLOW_ACTION_TIMEOUT = 30000;     // 30s - Heavy operations
```

### When to Use Each
- **DEFAULT_ACTION_TIMEOUT (10s)**: Simple clicks, fills, navigation
- **SLOW_ACTION_TIMEOUT (20s)**: Backend API calls, dropdown loading, modal rendering
- **VERY_SLOW_ACTION_TIMEOUT (30s)**: Document completion, report generation

## Page Object Structure

```
tests/utils/pages/
├── LoginPage.js           # Login page
├── DashboardPage.js       # Main dashboard
├── PurchaseOrderPage.js   # Purchase order window
├── MaterialReceiptPage.js # Material receipt window
└── InvoiceCandidatePage.js # Invoice candidate window
```

## Page Object Template

```javascript
import { test } from '../../../playwright.config';
import { getPage } from '../common';

export class MyPage {
  /**
   * Navigate to the page
   */
  static async goto() {
    return await test.step('Navigate to My Page', async () => {
      const page = getPage();
      await page.goto(`${process.env.FRONTEND_BASE_URL}/#/window/123`);
      await page.waitForLoadState('networkidle');
    });
  }

  /**
   * Interact with page element
   * @param {string} value - Value to enter
   */
  static async fillField(value) {
    return await test.step(`Fill field: ${value}`, async () => {
      const page = getPage();

      // Use language-independent selector
      await page.getByTestId('field-name').fill(value);
    });
  }

  /**
   * Verify page state
   */
  static async expectVisible() {
    return await test.step('Verify page is visible', async () => {
      const page = getPage();
      await page.locator('.window-wrapper').waitFor({
        state: 'visible',
      });
    });
  }
}
```

## Best Practices

1. **All methods are static** - Page objects are stateless
2. **Wrap in test.step()** - Clear reporting in test results
3. **Use getPage()** - Access page object from context
4. **Language-independent selectors** - No text-based selectors
5. **Descriptive step names** - Include parameter values in step descriptions

## Backend API Integration

**All test data is created via Backend API** (`/api/v2/frontendTesting` endpoint).

### Masterdata Creation Pattern

```javascript
const masterdata = await Backend.createMasterdata({
  request: {
    login: {
      user: {
        language: 'en_US',
      },
    },
    bpartners: {
      Vendor1: {
        isVendor: true,
        isCustomer: false,
        isSoPriceList: false,
      },
    },
    products: {
      Product1: {
        name: 'Test Product',
        value: 'TEST-001',
        type: 'Item',
        prices: [{ price: 10.0, currencyCode: 'EUR' }],
      },
    },
  },
});

// Access created data
console.log('User:', masterdata.login.user.username);
console.log('Vendor:', masterdata.bpartners.Vendor1.bpartnerCode);
console.log('Product:', masterdata.products.Product1.productName);
```

### Test Data Isolation

**Each test creates its own data** - No shared state between tests.

```javascript
test('Test 1', async () => {
  const data1 = await Backend.createMasterdata({...});
  // Use data1
});

test('Test 2', async () => {
  const data2 = await Backend.createMasterdata({...});
  // Use data2 (independent from data1)
});
```

## Common Pitfalls and Solutions

### 1. Text-Based Selectors Break
```javascript
// PROBLEM: Breaks in German
await page.getByText('Complete');

// SOLUTION: Use data-testid
await page.getByTestId('action-C_Order_Complete');
```

### 2. Batch Entry Modal Not Loading
```javascript
// PROBLEM: Times out waiting for product field
await page.getByTestId('batch-entry-toggle').click();
await page.locator('#lookup_M_Product_ID input').click();

// SOLUTION: Two-step wait
await page.getByTestId('batch-entry-toggle').click();
await page.locator('.quick-input-container').waitFor({ state: 'visible' });
await page.locator('#lookup_M_Product_ID input').waitFor({ state: 'visible' });
await page.locator('#lookup_M_Product_ID input').click();
```

### 3. Lookup Dropdown Not Appearing
```javascript
// PROBLEM: Dropdown doesn't appear
await page.locator('#lookup_C_BPartner_ID input').fill('vendor');
await page.locator('.input-dropdown-list-option').first().click();

// SOLUTION: Wait for debounce and dropdown
await page.locator('#lookup_C_BPartner_ID input').fill('vendor');
await page.waitForTimeout(300);  // Debounce delay
await page.locator('.input-dropdown-list').waitFor({ state: 'visible' });
await page.locator('.input-dropdown-list-option').first().click();
```

### 4. Fixed Timeouts Are Unreliable
```javascript
// PROBLEM: May be too short or too long
await page.waitForTimeout(500);

// SOLUTION: Wait for specific condition
await page.locator('.expected-element').waitFor({ state: 'visible' });
```
