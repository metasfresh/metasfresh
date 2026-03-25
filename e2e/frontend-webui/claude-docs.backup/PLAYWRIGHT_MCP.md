# Playwright MCP Integration

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Interactive browser automation for E2E tests**

## Prerequisites

**You MUST have a running metasfresh instance:**
- Frontend: `http://localhost:3000` (React dev server)
- Backend API: `http://localhost:8080/rest/api`
- App server: `http://localhost:8282` with `frontend.testing=Y` system config

## Available Skills

| Skill | Purpose | Use When |
|-------|---------|----------|
| `playwright-explore` | UI exploration | Understanding workflows, finding selectors, debugging UI |
| `playwright-create` | Interactive test creation | Creating new E2E tests (prefer manual data-testid approach) |
| `playwright-test` | Run existing tests | Running or debugging existing tests |

## Key MCP Tools

### Primary Tools

**`browser_snapshot`** - **ALWAYS USE THIS FIRST**
- Returns accessibility tree representation of the page
- Provides stable selectors (role + name + ref)
- Faster and more reliable than screenshots

**Example output:**
```
button "Save" [ref=btn-123]
textbox "Name" [ref=input-name] (required)
combobox "Product Category" [ref=select-category]
```

**`browser_take_screenshot`** - Visual reference only
- Use when you need to see layout or styling
- NOT for finding selectors (use snapshots instead)

### Interaction Tools

| Tool | Purpose | Example |
|------|---------|---------|
| `browser_click` | Click elements | `browser_click({ element: "Save button", ref: "btn-123" })` |
| `browser_type` | Type text | `browser_type({ element: "Name field", ref: "input-name", text: "Test" })` |
| `browser_fill_form` | Fill multiple fields | See below |
| `browser_evaluate` | Run JavaScript | `browser_evaluate({ function: "() => window.config" })` |
| `browser_run_code` | Execute Playwright code | For complex interactions |

**`browser_fill_form` example:**
```javascript
browser_fill_form({
  fields: [
    { name: "Name", ref: "input-name", type: "textbox", value: "Test" },
    { name: "Active", ref: "chk-active", type: "checkbox", value: "true" }
  ]
})
```

### Inspection Tools

| Tool | Purpose |
|------|---------|
| `browser_console_messages` | Check for JavaScript errors |
| `browser_network_requests` | Monitor API calls |

## Test Creation Workflow

### 1. Exploration Phase

```
/playwright-explore Product window (ID 140)
```

**What happens:**
1. Browser navigates to the window
2. Takes accessibility snapshot
3. Shows available elements and actions
4. You can interact and ask questions

### 2. Test Creation Phase

```
/playwright-create-test Create a test for Product window - verify grid loads
```

**Generated output:**
- `tests/spec/product-workflow.spec.js` - Test specification
- `tests/utils/pages/ProductPage.js` - Page Object (if new)

### 3. Validation Phase

```
/playwright-test product-workflow
```

## Code Patterns for MCP-Created Tests

### Always Create Test Data via Backend API

```javascript
// GOOD - Isolated test data
const masterdata = await Backend.createMasterdata({
  request: {
    login: { user: { language: 'en_US' } },
    products: { PROD1: { name: 'Test Product', price: '99.99' } }
  }
});
await LoginPage.login(masterdata.login.user);

// BAD - Don't rely on existing data
await LoginPage.login('admin', 'password');
```

### Selector Discovery with MCP

```javascript
// 1. Use browser_snapshot to discover selectors
// Returns: button "Save" [ref=btn-save]

// 2. Convert to Playwright selector
await page.getByRole('button', { name: 'Save' }).click();

// 3. BUT: If text is language-dependent, add data-testid instead
await page.getByTestId('action-DocAction_Save').click();
```

## Common Debugging Workflows

### Find Out Why Element Won't Click

```
/playwright-explore
"The Save button in Product form won't click - investigate"
```

**Skill will:**
1. Take snapshot to verify button exists
2. Check button state (disabled, hidden, etc.)
3. Look for JavaScript errors
4. Test hover interaction
5. Report findings

### Understand Why Data Won't Load

```
/playwright-explore
"Product grid shows 'No data' but should have products - investigate"
```

**Skill will:**
1. Monitor network request to API
2. Check response status and data
3. Inspect console for errors
4. Verify UI filters/parameters
5. Report root cause

## Important: MCP vs data-testid Approach

**MCP exploration is useful for:**
- Understanding UI structure
- Debugging issues
- One-off investigations

**BUT for production tests, always:**
- Add `data-testid` to components (language-independent)
- Use the multi-language test pattern
- Don't rely on MCP-discovered text-based selectors

**Why:** MCP finds accessible names like `button "Save"` which fails in German (`button "Speichern"`).

## Debugging Tests

### Visual Debugging
```bash
npm run test:headed      # Visible browser
npm run test:ui          # Interactive mode
npx playwright test --debug tests/spec/purchase-order.spec.js
```

### Trace Viewer
```bash
npx playwright show-trace test-results/failed-test/trace.zip
npm run test:report      # HTML report
```

### Investigating Selector Failures
1. **Use browser inspector** - `await page.pause()`
2. **Check component source** - Search frontend/src
3. **Read WIDGET_PATTERNS.md** - Understand rendering
4. **Check layout JSON** - Use `scripts/fetch-window-layout.js`
5. **Add data-testid if needed**

### Fetching Window Layout

**Do NOT use curl directly** - WebAPI requires authentication.

```bash
# Fetch layout for Purchase Order (ID 181)
node scripts/fetch-window-layout.js 181

# Extract tab IDs
cat layouts/window-181.json | jq '.tabs[] | {tabId, internalName, caption}'
```

**Common Window IDs:**
| Window | ID | Order Lines Tab |
|--------|----|-----------------|
| Sales Order | 143 | AD_Tab-187 |
| Purchase Order | 181 | AD_Tab-293 |
| Shipment Schedule | 500221 | (no tabs) |
