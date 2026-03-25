# Language-Independent Testing

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Selectors that work across all UI languages**

## Core Principle

**ALL selectors must work across multiple languages (en_US, de_DE, fr_FR, etc.).**

## Selector Priority (Highest to Lowest)

### 1. data-testid attributes (BEST)
```javascript
await page.getByTestId('batch-entry-toggle');
await page.getByTestId('action-C_Order_Complete');
await page.getByTestId('tab-POLine');
```

### 2. Database column names in IDs (Good)
```javascript
await page.locator('#lookup_C_BPartner_ID input.input-field');
await page.locator('#lookup_M_Product_ID input.input-field');
```

### 3. CSS classes for structural elements (Good - if stable)
```javascript
await page.locator('.quick-input-container');
await page.locator('.input-dropdown-list-option');
await page.locator('.form-field-DocumentNo');
```

### 4. Roles with accessible names (USE WITH CAUTION)
```javascript
// BAD - Will fail in German:
await page.getByRole('button', { name: 'Save' });

// GOOD - Use data-testid instead:
await page.getByTestId('action-DocAction_Save');
```

### 5. Text content (NEVER USE)
```javascript
// NEVER DO THIS:
await page.getByText('Complete'); // Fails in German ("Abschließen")
await page.locator('button:has-text("New")'); // Fails in German ("Neu")
```

## Grid Cell Selection with data-cy

Grid data cells have language-independent `data-cy` attributes based on database column names.

```javascript
// GOOD - Language-independent (reads cell directly via data-cy)
const qtyCell = page.locator('[data-cy="cell-QtyOrdered_Calculated"]').first();
const quantity = await qtyCell.textContent();

// BAD - Language-dependent (matches column header by text)
const headers = await page.locator('thead th').all();
for (let i = 0; i < headers.length; i++) {
  const text = await headers[i].textContent();
  if (text === 'Quantity Ordered') {  // Fails in German!
    qtyOrderedIndex = i;
  }
}
```

### Number Formatting
Handle locale differences when comparing quantities:
```javascript
const normalizeNumber = (value) => {
  const str = String(value).trim().replace(',', '.');
  return parseFloat(str);
};

const expected = normalizeNumber('10');      // 10
const actual = normalizeNumber('10,00');     // 10 (from German UI)
expect(actual).toBe(expected);  // Pass
```

## When to Add data-testid

**Rule: Add data-testid when you can't find a language-independent selector**

Before guessing or using fragile selectors:
1. Read WIDGET_PATTERNS.md to understand rendering patterns
2. Check if database column name is available (lookup IDs)
3. Check if CSS class is stable and semantic
4. If none exist → **Add data-testid to the React component**

### How to Add data-testid

**Step 1: Identify the React component**
```bash
cd frontend/src
grep -r "button text or class name" .
```

**Step 2: Add data-testid attribute**
```javascript
// Example: ActionButton.js
<button
  className="btn btn-meta-primary"
  onClick={handleClick}
  data-testid={`action-${internalName}`}
>
  {caption}
</button>
```

**Step 3: Use semantic, stable IDs**
- Based on backend action IDs: `action-C_Order_Complete`
- Based on tab IDs: `tab-POLine`
- Based on field names: `field-C_BPartner_ID`
- **NOT** based on text: `button-save` (will change with translation)

**Step 4: Rebuild frontend**
```bash
cd frontend && yarn build-prod
```

**Step 5: Use in test**
```javascript
await page.getByTestId('action-C_Order_Complete');
```

## Components with data-testid

| Component | Pattern | Example |
|-----------|---------|---------|
| ActionButton.js | `action-${internalName}` | `action-C_Order_Complete` |
| Actions.js (header) | `action-${action.internalName}` | `action-DocAction_Save` |
| Tabs.js | `tab-${tabId}` | `tab-POLine` |
| ModalButton.js | `modal-${name}` | `modal-settings` |
| TableFilter.js | `batch-entry-toggle` | `batch-entry-toggle` |
| TableHeader.js | `column-${fieldName}` | `column-QtyOrdered` |

## Multi-Language Test Pattern

**All tests MUST validate both en_US and de_DE (minimum).**

```javascript
import { test } from '../../playwright.config';
import { Backend } from '../utils/Backend';
import { LoginPage } from '../utils/pages/LoginPage';

const testCases = [
  { language: 'en_US', label: 'English' },
  { language: 'de_DE', label: 'German' },
];

testCases.forEach(({ language, label }) => {
  test.describe(`My Feature (${label})`, () => {
    test(`Test case description (${label} UI)`, async ({ page }) => {
      const masterdata = await Backend.createMasterdata({
        request: {
          login: {
            user: {
              language,  // Language parameter
            },
          },
          // ... other test data
        },
      });

      await LoginPage.goto();
      await LoginPage.login(masterdata.login.user);

      // All selectors must work in all languages
      await page.getByTestId('action-save').click();
    });
  });
});
```

### Benefits
- **Validates language independence** - Catches language-dependent selectors immediately
- **Single source of truth** - One test file for all languages
- **Easy to extend** - Add `{ language: 'fr_FR', label: 'French' }` to array
- **Consistent logic** - Same test flow for all languages
