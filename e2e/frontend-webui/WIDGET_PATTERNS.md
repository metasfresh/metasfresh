# metasfresh Widget Rendering Patterns

This document describes how metasfresh frontend widgets are rendered and how to derive Playwright selectors from window layout JSON files.

## Overview

The metasfresh Web UI uses a **layout-driven architecture**. Window layouts (fetched from `/rest/api/window/{id}/layout`) define the structure, fields, and widgets. The React frontend renders these layouts dynamically.

**Key Principle**: Field selectors can be derived by combining:
1. **Layout API metadata** - Field names, widget types
2. **Frontend rendering patterns** - CSS classes, DOM structure

## Widget Types and Selectors

### Lookup Widget (Autocomplete)

**Layout JSON**:
```json
{
  "widgetType": "Lookup",
  "fields": [{
    "field": "C_BPartner_ID",
    "caption": "Business Partner",
    "source": "lookup"
  }]
}
```

**Rendered HTML Pattern**:
```html
<!-- Container ID uses field name: #lookup_{FIELD_NAME} -->
<div id="lookup_C_BPartner_ID" class="raw-lookup-wrapper">
  <div class="lookup-widget-wrapper lookup-widget-wrapper-bcg">
    <div class="input-dropdown input-block">
      <div class="input-editable">
        <input class="input-field js-input-field font-weight-semibold" type="text" />
      </div>
    </div>
  </div>
</div>

<!-- Dropdown list (appears when typing/clicking) -->
<div class="input-dropdown-list">
  <div class="input-dropdown-list-option ignore-react-onclickoutside"
       onMouseDown="handleSelect">
    Caption Text
  </div>
</div>
```

**CRITICAL: The dropdown options use `onMouseDown` NOT `onClick`!**

The React component `SelectionDropdown.js` handles selection via:
```javascript
// Source: frontend/src/components/widget/SelectionDropdown.js:224-226
handleMouseDown(option) {
  this.props.onSelect(option, true);  // true = isMouseEvent
}
```

**Playwright Selectors**:
- **Container by field ID** (RECOMMENDED): `#lookup_C_BPartner_ID`
- **Input field**: `#lookup_C_BPartner_ID input.input-field`
- **Dropdown list**: `.input-dropdown-list`
- **Dropdown options**: `.input-dropdown-list-option`
- **Loading spinner**: `#lookup_C_BPartner_ID .rotating`

**Working Interaction Pattern**:
```javascript
// CORRECT PATTERN - Verified against frontend source code
static async selectLookupValue(fieldName, searchText, optionText) {
  const page = getPage();

  // 1. Locate the input within the lookup container
  const lookupInput = page.locator(`#lookup_${fieldName} input.input-field`);
  await lookupInput.waitFor({ state: 'visible', timeout: 10000 });

  // 2. Click to focus the field
  await lookupInput.click();

  // 3. Wait for any initial loading to complete
  await page.locator(`#lookup_${fieldName} .rotating`).waitFor({
    state: 'detached',
    timeout: 10000,
  }).catch(() => {});

  // 4. Clear and type search text
  await lookupInput.fill('');
  await page.waitForTimeout(200);
  await lookupInput.fill(searchText);

  // 5. Wait for dropdown to load (debounce + API call)
  await page.waitForTimeout(500);
  await page.locator(`#lookup_${fieldName} .rotating`).waitFor({
    state: 'detached',
    timeout: 10000,
  }).catch(() => {});

  // 6. Wait for dropdown options to appear
  const dropdownList = page.locator('.input-dropdown-list');
  await dropdownList.waitFor({ state: 'visible', timeout: 10000 });

  // 7. Find the specific option (use filter for partial match)
  const option = page.locator('.input-dropdown-list-option')
    .filter({ hasText: optionText })
    .first();

  // 8. Verify option exists
  const optionCount = await option.count();
  if (optionCount === 0) {
    const allOptions = await page.locator('.input-dropdown-list-option').allTextContents();
    throw new Error(`Option "${optionText}" not found. Available: ${allOptions.join(', ')}`);
  }

  // 9. Click the option (triggers onMouseDown → handleSelect)
  await option.click();

  // 10. Wait for dropdown to close
  await dropdownList.waitFor({ state: 'detached', timeout: 5000 }).catch(() => {});

  // 11. Press Tab to confirm and trigger save
  await page.keyboard.press('Tab');

  // 12. Wait for save to complete
  await page.waitForTimeout(300);
  await page.locator('.rotating, .indicator-pending').waitFor({
    state: 'detached',
    timeout: 10000,
  }).catch(() => {});

  // 13. Wait for network idle (auto-save)
  await page.waitForLoadState('networkidle', { timeout: 10000 }).catch(() => {});
}
```

**Common Pitfalls**:
1. **Not waiting for spinner**: The lookup makes API calls - always wait for `.rotating` to detach
2. **Clicking too fast**: Allow 500ms debounce after typing before clicking option
3. **Not pressing Tab**: Selection commits on blur - Tab ensures the save triggers
4. **Wrong selector scope**: Dropdown list is NOT inside `#lookup_X`, it's a separate element

**Composite Lookups (BPartner + Location + Contact)**:
Some lookup fields are "composed" - multiple related fields in one widget.
**IMPORTANT**: Each sub-field has its OWN container ID, NOT indexed wrappers:
```html
<!-- Each sub-field gets its own #lookup_{fieldName} container -->
<div class="input-dropdown-container lookup-wrapper">
  <div id="lookup_C_BPartner_ID" class="raw-lookup-wrapper">
    <div class="lookup-widget-wrapper">...</div>
  </div>
  <div id="lookup_C_BPartner_Location_ID" class="raw-lookup-wrapper">
    <div class="lookup-widget-wrapper">...</div>
  </div>
  <div id="lookup_AD_User_ID" class="raw-lookup-wrapper">
    <div class="lookup-widget-wrapper">...</div>
  </div>
</div>
```

For composed lookups, target by the specific field's container ID:
```javascript
const partnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
const locationInput = page.locator('#lookup_C_BPartner_Location_ID input.input-field');
const contactInput = page.locator('#lookup_AD_User_ID input.input-field');
```

### Date Widget

**Layout JSON**:
```json
{
  "widgetType": "Date",
  "fields": [{
    "field": "DateOrdered",
    "caption": "Date"
  }]
}
```

**Rendered HTML Pattern**:
```html
<div class="input-dropdown input-date">
  <input type="text" />
  <button class="btn-icon"><!-- calendar icon --></button>
</div>
```

**Playwright Selectors**:
- **By input type**: `input[type="text"]` (within date container)
- **By parent class**: `.input-date input`

**Interaction Pattern**:
```javascript
// Fill date
await page.locator('.input-date input').fill('2025-01-15');
```

### List Widget (Dropdown)

**Layout JSON**:
```json
{
  "widgetType": "List",
  "fields": [{
    "field": "M_Warehouse_ID",
    "caption": "Warehouse",
    "source": "list"
  }]
}
```

**Rendered HTML Pattern**:
```html
<div class="input-dropdown input-editable">
  <div class="raw-lookup-wrapper">
    <input readonly />
  </div>
</div>

<!-- Dropdown options include language-independent data-testid -->
<div class="input-dropdown-list">
  <div class="input-dropdown-list-option" data-testid="option-1000009">
    Zahlungsausgang
  </div>
</div>
```

**Playwright Selectors**:
- **By parent class**: `.input-dropdown input`
- **Click to open dropdown**: `.input-dropdown`
- **By ID (RECOMMENDED)**: `[data-testid="option-{ID}"]` - Language-independent!

**Interaction Pattern**:
```javascript
// Click to open dropdown
await page.locator('.input-dropdown').click();

// RECOMMENDED: Select by ID (language-independent)
// Use constants from DocTypeIds.js, WindowIds.js, etc.
import { DOCTYPE_AP_PAYMENT } from '../utils/DocTypeIds';
await page.locator(`[data-testid="option-${DOCTYPE_AP_PAYMENT}"]`).click();

// FALLBACK: Select by text (language-dependent - avoid in multi-language tests)
await page.locator('.input-dropdown-list-option').filter({ hasText: 'Main Warehouse' }).click();
```

**Language-Independent Selection Pattern**:

For dropdown options that need to work across languages (German, English, etc.),
use the `data-testid` attribute which contains the record ID:

```javascript
// Document Type selection example
import { DOCTYPE_AP_PAYMENT, DOCTYPE_AR_RECEIPT } from '../utils/DocTypeIds';

// For vendor payments (Zahlungsausgang / AP Payment)
await page.locator(`[data-testid="option-${DOCTYPE_AP_PAYMENT}"]`).click();

// For customer receipts (Zahlungseingang / AR Receipt)
await page.locator(`[data-testid="option-${DOCTYPE_AR_RECEIPT}"]`).click();
```

See `tests/utils/DocTypeIds.js` for document type constants and their meanings.

### Text Widget

**Layout JSON**:
```json
{
  "widgetType": "Text",
  "fields": [{
    "field": "DocumentNo",
    "caption": "No."
  }]
}
```

**Rendered HTML Pattern**:
```html
<input type="text" class="input-primary" />
```

**Playwright Selectors**:
- **By field name**: `input[name="DocumentNo"]`
- **By label**: `text=No. >> input`

### Number/Integer Widget

**Layout JSON**:
```json
{
  "widgetType": "Integer",
  "fields": [{
    "field": "Line",
    "caption": "Line No"
  }]
}
```

**Rendered HTML Pattern**:
```html
<input type="text" class="input-number" />
```

**Playwright Selectors**:
- **By class**: `.input-number`
- **By field name**: `input[name="Line"]`

### YesNo Widget (Checkbox)

**Layout JSON**:
```json
{
  "widgetType": "YesNo",
  "fields": [{
    "field": "IsDropShip",
    "caption": "Different shipping address"
  }]
}
```

**Rendered HTML Pattern**:
```html
<label>
  <input type="checkbox" class="input-checkbox" />
  <span>Different shipping address</span>
</label>
```

**Playwright Selectors**:
- **By label**: `text=Different shipping address >> input[type="checkbox"]`
- **By field name**: `input[name="IsDropShip"][type="checkbox"]`

## Document Actions

**Layout JSON**:
```json
{
  "docActionElement": {
    "widgetType": "ActionButton",
    "fields": [{
      "field": "DocAction",
      "caption": "Process Order",
      "type": "ActionButton"
    }]
  }
}
```

**Rendered HTML Pattern**:
```html
<button class="btn btn-meta-primary">
  <span>Complete</span>
</button>
```

**Playwright Selectors**:
- **By text**: `button:has-text("Complete")`
- **By class**: `.btn-meta-primary`

**Interaction Pattern**:
```javascript
// Click action button (opens dropdown)
await page.locator('.btn-meta-primary').click();

// Select action from dropdown
await page.locator('text=Complete').click();
```

## Tabs

**Layout JSON**:
```json
{
  "tabs": [{
    "tabId": "AD_Tab-293",
    "internalName": "C_OrderLine",
    "caption": "PO Line"
  }]
}
```

**Rendered HTML Pattern**:
```html
<ul class="nav nav-tabs">
  <li class="nav-item">
    <a class="nav-link">PO Line</a>
  </li>
</ul>
```

**Playwright Selectors**:
- **By text**: `text=PO Line`
- **By role**: `role=tab[name="PO Line"]`

**Interaction Pattern**:
```javascript
// Switch to tab
await page.locator('role=tab[name="PO Line"]').click();
```

## Batch Entry (Alt+Q Pattern)

Many tables in metasfresh support **batch entry mode** triggered by Alt+Q:

**Layout Indicator**:
```json
{
  "supportQuickInput": true
}
```

**Interaction Pattern**:
```javascript
// Trigger batch entry
await page.keyboard.press('Alt+Q');

// Fill batch entry fields
await page.locator('.batch-entry-field').fill('value');

// Confirm
await page.keyboard.press('Enter');
```

## Field Properties

### Mandatory Fields

**Layout JSON**:
```json
{
  "mandatory": true
}
```

**Rendered HTML**:
- Adds class: `input-mandatory`
- Shows red asterisk in UI

**Detection**:
```javascript
const isMandatory = await page.locator('input').evaluate(el =>
  el.classList.contains('input-mandatory')
);
```

### Readonly Fields

**Layout JSON**:
```json
{
  "readonly": true
}
```

**Rendered HTML**:
- Input has `readonly` attribute
- May have class `input-disabled`

**Detection**:
```javascript
const isReadonly = await page.locator('input').getAttribute('readonly');
```

## Selector Strategy

### Recommended Priority

1. **Semantic selectors** (preferred):
   - `role=button[name="Save"]`
   - `text=Business Partner`
   - `placeholder="Search..."`

2. **Field-specific selectors**:
   - `input[name="C_BPartner_ID"]` (if name attribute exists)
   - `.lookup-widget-wrapper` (widget type class)

3. **Position-based selectors** (last resort):
   - `.input-dropdown >> nth=0`
   - `input >> nth=2`

### Best Practices

1. **Combine layout + rendering knowledge**:
   - Layout tells you: field name, widget type, caption
   - Rendering pattern tells you: CSS classes, DOM structure
   - Combine both for robust selectors

2. **Prefer stable selectors**:
   - Use `role`, `text`, `placeholder` over class names
   - Class names may change, semantic HTML is more stable

3. **Use data attributes** (if available):
   - `[data-field="C_BPartner_ID"]`
   - Future: May be added to metasfresh frontend

4. **Test selectors against layout changes**:
   - Re-fetch layouts regularly
   - Update tests if layout structure changes

## Example: Deriving Selectors from Layout

**Step 1: Read layout JSON**
```javascript
const layout = require('../layouts/purchase-order-181.json');
const bpartnerField = layout.sections[0].columns[0].elementGroups[0]
  .elementsLine[0].elements[0].fields.find(f => f.field === 'C_BPartner_ID');
```

**Step 2: Identify widget type**
```javascript
const widgetType = bpartnerField.widgetType; // "Lookup"
const fieldName = bpartnerField.field; // "C_BPartner_ID"
const caption = bpartnerField.caption; // "Business Partner"
```

**Step 3: Apply rendering pattern**
```javascript
// Lookup widget → .lookup-widget-wrapper input
// Caption → placeholder or label
const selector = `.lookup-widget-wrapper input[placeholder*="${caption}"]`;
```

**Step 4: Verify in browser**
```javascript
// Use Playwright inspector to confirm
await page.pause(); // Opens inspector
```

## Layout API Endpoints

### Detail Layout (Form View)
```
GET /rest/api/window/{windowId}/layout
```

Returns field definitions, tabs, sections for single-record view.

### Grid Layout (Table View)
```
GET /rest/api/documentView/{windowId}/layout?viewType=grid
```

Returns column definitions for table/list view.

### Using fetch-window-layout.js

```bash
# Fetch detail layout
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json

# Fetch grid layout
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid
```

See `layouts/README.md` for complete documentation.

## Common Pitfalls

### 1. Dynamic Field Names

Some fields have dynamic IDs or names generated at runtime. Prefer:
- `role` attributes
- `placeholder` text
- Parent class + position

### 2. Lazy-Loaded Content

Tabs and sections may load on-demand:
```javascript
// Wait for tab content to load
await page.locator('.nav-link:has-text("PO Line")').click();
await page.locator('.tab-content').waitFor({ state: 'visible' });
```

### 3. Autocomplete Timing

Lookup widgets trigger autocomplete searches:
```javascript
// Fill and wait for results
await page.locator('.lookup-widget-wrapper input').fill('test');
await page.waitForTimeout(500); // Wait for debounce
await page.locator('.input-dropdown-list-option').first().waitFor();
```

### 4. Multiple Lookup Fields

Composed lookups (e.g., partner + location + contact) have separate container IDs:
```javascript
// Each sub-field has its own container ID - use specific IDs, NOT indices
const partnerInput = page.locator('#lookup_C_BPartner_ID input.input-field');
const locationInput = page.locator('#lookup_C_BPartner_Location_ID input.input-field');
const contactInput = page.locator('#lookup_AD_User_ID input.input-field');
```

## Resources

- **Layout Fetcher**: `e2e/frontend-webui/scripts/fetch-window-layout.js`
- **Saved Layouts**: `e2e/frontend-webui/layouts/`
- **Frontend Components**: `frontend/src/components/widget/`
- **Playwright Docs**: https://playwright.dev/docs/selectors

## License

GPL-2.0
