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
<div class="lookup-widget-wrapper">
  <div class="input-dropdown input-primary">
    <input class="input-dropdown-focused input-editable-field" />
  </div>
</div>
```

**Playwright Selectors**:
- **By field name attribute**: `input[name="C_BPartner_ID"]` (if available)
- **By placeholder**: `input[placeholder*="Business Partner"]`
- **By parent class**: `.lookup-widget-wrapper input`
- **By role**: `role=combobox`

**Interaction Pattern**:
```javascript
// Type to search
await page.locator('.lookup-widget-wrapper input').fill('partner name');

// Wait for dropdown
await page.locator('.input-dropdown-list').waitFor();

// Select first item
await page.locator('.input-dropdown-list-option').first().click();
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
```

**Playwright Selectors**:
- **By parent class**: `.input-dropdown input`
- **Click to open dropdown**: `.input-dropdown`

**Interaction Pattern**:
```javascript
// Click to open dropdown
await page.locator('.input-dropdown').click();

// Select from list
await page.locator('.input-dropdown-list-option').filter({ hasText: 'Main Warehouse' }).click();
```

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

Composed lookups (e.g., partner + location + contact) have multiple inputs:
```javascript
// Target specific field within composed lookup
const partnerInput = page.locator('.lookup-widget-wrapper').nth(0).locator('input');
const locationInput = page.locator('.lookup-widget-wrapper').nth(1).locator('input');
```

## Resources

- **Layout Fetcher**: `e2e/frontend-webui/scripts/fetch-window-layout.js`
- **Saved Layouts**: `e2e/frontend-webui/layouts/`
- **Frontend Components**: `frontend/src/components/widget/`
- **Playwright Docs**: https://playwright.dev/docs/selectors

## License

GPL-2.0
