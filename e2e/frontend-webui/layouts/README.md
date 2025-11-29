# metasfresh Window Layouts

This directory contains window layout JSON files fetched from the metasfresh webapi. These layouts define the structure, fields, tabs, and UI configuration for metasfresh windows.

## Purpose

Window layouts are used to:
- **Understand window structure** - See all fields, tabs, and their properties
- **Generate Page Objects** - Use layout metadata to build Playwright page objects
- **Write robust tests** - Reference actual field names, types, and constraints
- **Document windows** - Have a snapshot of window configuration at a point in time

## Layout Types: Detail vs Grid

metasfresh windows have **two different layouts** that serve different purposes:

### Detail Layout (Single Record View)
**Endpoint:** `/rest/api/window/{id}/layout`

The detail layout defines the form view when viewing/editing a single record (e.g., one Business Partner, one Purchase Order).

**What it contains:**
- Field definitions organized into sections and columns
- All tabs (e.g., Order Lines, Payment Terms)
- Field properties: mandatory, readonly, default values
- UI logic: display rules, validation rules
- Document actions (Complete, Close, etc.)

**When to use:**
- Building Page Objects for detail views
- Understanding full field structure
- Testing single-record operations (create, update)

**Example:**
```bash
# Fetch Purchase Order detail layout
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json
```

### Grid Layout (Table View)
**Endpoint:** `/rest/api/documentView/{id}/layout?viewType=grid`

The grid layout defines the table view where users see a paginated list of all records.

**What it contains:**
- Column definitions for the table
- Column widths and field types
- Sort/filter capabilities
- Which fields are visible in the table

**When to use:**
- Building Page Objects for table/list views
- Understanding which columns appear in grids
- Testing list operations (search, filter, sort)

**Example:**
```bash
# Fetch Purchase Order grid layout
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid
```

### Visual Comparison

| Aspect | Detail Layout | Grid Layout |
|--------|--------------|-------------|
| **View** | Single record (form) | Multiple records (table) |
| **URL pattern** | `/window/181/12345` | `/window/181` |
| **Endpoint** | `/rest/api/window/{id}/layout` | `/rest/api/documentView/{id}/layout?viewType=grid` |
| **Contains** | All fields, tabs, sections | Table columns only |
| **File naming** | `purchase-order-181.json` | `purchase-order-181-grid.json` |
| **Flag** | (none - default) | `--grid` |

**Best practice:** Fetch **both** layouts for windows you're testing. Detail layout for form interactions, grid layout for table operations.

## ⚠️ Important: Layouts Change Over Time

**Window layouts evolve as metasfresh is developed.** Fields are added, removed, or modified as features are implemented and refined.

### Best Practice: Always Fetch Fresh Layouts When Writing Tests

**When writing new tests, always fetch the latest layout from your running instance:**

```bash
# DON'T rely on old layout files
# DO fetch fresh layouts when writing tests
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid
```

**Why?** Using outdated layouts can cause:
- Tests targeting non-existent fields
- Missing new mandatory fields
- Incorrect field types or validation rules
- Confusing test failures

### Keep Old Layouts for Change Tracking

**However, don't delete old layouts immediately.** Keep them to:
- **Track what changed** - Compare old vs new layouts to understand modifications
- **Debug test failures** - Identify if layout changes caused tests to break
- **Document evolution** - See how windows evolved over time

**Recommended workflow:**
```bash
# Before writing new tests, fetch fresh layouts (both types)
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid

# Git will show you what changed
git diff layouts/purchase-order-181*.json

# Review changes, then commit the updated layouts
git add layouts/purchase-order-181*.json
git commit -m "Update purchase order layouts (added field: XYZ)"
```

## Fetching Layouts

Use the `fetch-window-layout.js` script to fetch layouts from a running metasfresh instance:

```bash
# Fetch detail layout (default)
node scripts/fetch-window-layout.js <windowId> [outputFile]

# Fetch grid layout
node scripts/fetch-window-layout.js <windowId> [outputFile] --grid
```

### Examples

```bash
# Fetch Purchase Order detail layout
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json

# Fetch Purchase Order grid layout
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid

# Auto-generate filenames
node scripts/fetch-window-layout.js 183  # Creates: layouts/window-183.json
node scripts/fetch-window-layout.js 183 --grid  # Creates: layouts/window-183-grid.json

# Fetch both layouts for a window
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181.json
node scripts/fetch-window-layout.js 181 layouts/purchase-order-181-grid.json --grid

# Find window ID by opening the window in metasfresh
# URL format: http://localhost:3000/window/{windowId}
```

### Prerequisites

Before fetching layouts, ensure:

1. **metasfresh is running**
   - Frontend: http://localhost:3000
   - WebAPI: http://localhost:8080
   - App Server: http://localhost:8282

2. **Frontend testing API is enabled**
   ```sql
   INSERT INTO AD_SysConfig (AD_Client_ID, AD_Org_ID, Name, Value, Description)
   VALUES (0, 0, 'frontend.testing', 'Y', 'Enable frontend testing API')
   ON CONFLICT (Name) DO UPDATE SET Value = 'Y';
   ```

3. **Dependencies installed**
   ```bash
   cd e2e/frontend-webui
   npm install
   npx playwright install chromium
   ```

### How It Works

The script performs these steps:

1. **Create test user** - Uses `/api/v2/frontendTesting` endpoint to create a temporary user
2. **Login via Playwright** - Logs into the frontend UI to obtain session cookies
3. **Fetch layout** - Makes authenticated request to the appropriate endpoint
   - Detail: `/rest/api/window/{id}/layout`
   - Grid: `/rest/api/documentView/{id}/layout?viewType=grid`
4. **Save JSON** - Writes the layout to a file with pretty formatting

### Environment Variables

Customize URLs if your instance uses different ports:

```bash
export FRONTEND_BASE_URL=http://localhost:3000
export WEBAPI_BASE_URL=http://localhost:8080
export TESTING_API_BASE_URL=http://localhost:8282/api/v2

node scripts/fetch-window-layout.js 181
```

## Layout Structure

### Detail Layout Structure

Detail layouts contain metadata about a window's form view:

```json
{
  "windowId": "181",
  "caption": "Purchase Order",
  "description": "...",
  "documentSummaryElement": {...},
  "docActionElement": {...},
  "sections": [
    {
      "caption": "Purchase Order",
      "columns": [
        {
          "elementGroupId": "main",
          "elements": [
            {
              "caption": "Document No",
              "widgetType": "Text",
              "readonly": false,
              "mandatory": true,
              "fieldName": "DocumentNo",
              ...
            }
          ]
        }
      ]
    }
  ],
  "tabs": [
    {
      "tabId": "...",
      "caption": "Order Line",
      "fields": [...]
    }
  ]
}
```

### Grid Layout Structure

Grid layouts define the table/list view columns:

```json
{
  "elements": [
    {
      "fieldName": "DocumentNo",
      "caption": "Document No",
      "widgetType": "Text",
      "width": 120,
      ...
    },
    {
      "fieldName": "C_BPartner_ID",
      "caption": "Business Partner",
      "widgetType": "Lookup",
      "width": 200,
      ...
    }
  ]
}
```

### Key Properties

**Detail Layout:**
- **`windowId`** - Unique identifier for the window
- **`caption`** - Display name (translated based on language)
- **`sections`** - Main window sections with field groups
- **`tabs`** - Child tabs (e.g., Order Lines, Payment Terms)
- **`elements`** - Individual fields with properties:
  - `fieldName` - Database field name
  - `widgetType` - UI component type (Text, Number, Date, Lookup, etc.)
  - `readonly` - Whether field is read-only
  - `mandatory` - Whether field is required
  - `validationRule` - Dynamic display logic
  - `defaultValue` - Default field value

**Grid Layout:**
- **`elements`** - Array of column definitions
- **`fieldName`** - Database field name
- **`caption`** - Column header
- **`widgetType`** - Display widget type
- **`width`** - Column width in pixels

### Widget Types

Common widget types found in layouts:

- **`Text`** - Text input field
- **`Number`** - Numeric input (integer or decimal)
- **`Date`** - Date picker
- **`DateTime`** - Date and time picker
- **`Lookup`** - Dropdown with reference data
- **`List`** - Fixed dropdown list
- **`YesNo`** - Checkbox/toggle
- **`Button`** - Action button
- **`LongText`** - Multi-line text area
- **`Image`** - Image upload/display
- **`Color`** - Color picker

## Available Layouts

Currently saved layouts (both detail and grid):

| Window | ID | Detail Layout | Grid Layout |
|--------|-----|---------------|-------------|
| Purchase Order | 181 | `purchase-order-181.json` (55 KB) | `purchase-order-181-grid.json` (21 KB) |
| Vendor Invoice | 183 | `vendor-invoice-183.json` (52 KB) | `vendor-invoice-183-grid.json` (16 KB) |
| Material Receipt | 184 | `material-receipt-184.json` (29 KB) | `material-receipt-184-grid.json` (14 KB) |
| Invoice Candidate | 540983 | `invoice-candidate-540983.json` (68 KB) | `invoice-candidate-540983-grid.json` (25 KB) |

## Using Layouts in Tests

### Example: Finding Field Names (Detail Layout)

```javascript
// Read the detail layout file
const layout = require('../layouts/purchase-order-181.json');

// Find a specific field
const docNoField = layout.sections[0].columns[0].elements
  .find(el => el.fieldName === 'DocumentNo');

console.log('Field caption:', docNoField.caption);
console.log('Is mandatory:', docNoField.mandatory);
console.log('Widget type:', docNoField.widgetType);
```

### Example: Finding Grid Columns (Grid Layout)

```javascript
// Read the grid layout file
const gridLayout = require('../layouts/purchase-order-181-grid.json');

// Get all visible columns
const columns = gridLayout.elements;

columns.forEach(col => {
  console.log(`${col.fieldName}: ${col.caption} (width: ${col.width}px)`);
});

// Output:
// DocumentNo: Document No (width: 120px)
// C_BPartner_ID: Business Partner (width: 200px)
// DateOrdered: Date Ordered (width: 100px)
// ...
```

### Example: Generating Page Object Fields

```javascript
// Extract all fields from the main section (detail)
const mainFields = layout.sections[0].columns[0].elements;

mainFields.forEach(field => {
  console.log(`${field.fieldName}: ${field.widgetType}`);
});

// Output:
// DocumentNo: Text
// C_BPartner_ID: Lookup
// DateOrdered: Date
// ...
```

### Example: Validating Tab Structure

```javascript
// Check if Order Line tab exists (detail layout)
const orderLineTab = layout.tabs.find(tab =>
  tab.caption.includes('Order Line')
);

if (orderLineTab) {
  console.log('Order Line tab fields:',
    orderLineTab.fields.map(f => f.fieldName)
  );
}
```

## When to Re-fetch Layouts

Re-fetch window layouts when:

1. **Writing new tests** - Always fetch fresh layouts (both detail and grid) to ensure accuracy
2. **Application Dictionary changes** - New fields, tabs, or sections added
3. **Field properties change** - Mandatory, readonly, or default values modified
4. **UI logic updates** - Display logic or validation rules changed
5. **After metasfresh upgrade** - Major/minor version updates may change layouts
6. **Tests fail unexpectedly** - Layout changes might cause test failures

### Best Practices

- **Fetch both layout types** - Detail and grid layouts for comprehensive coverage
- **Fetch fresh layouts for new tests** - Don't rely on old layouts when writing tests
- **Version control layouts** - Commit layout files to track changes over time
- **Review diffs carefully** - Changes to layouts may require test updates
- **Keep old layouts** - Don't delete them, use git diff to see what changed
- **Fetch from stable environment** - Use a consistent test environment
- **Document custom layouts** - Add comments if you customize window definitions

## Finding Window IDs

### Method 1: Browser URL

Open the window in metasfresh frontend:
```
http://localhost:3000/window/181
                              ^^^
                         Window ID
```

### Method 2: Application Dictionary

Query the database:
```sql
SELECT AD_Window_ID, Name, InternalName
FROM AD_Window
WHERE Name ILIKE '%purchase%order%'
ORDER BY Name;
```

### Method 3: Backend Logs

Enable debug logging and watch for window layout requests:
```
GET /rest/api/window/181/layout
GET /rest/api/documentView/181/layout?viewType=grid
```

## Troubleshooting

### Error: "Frontend testing REST endpoints are disabled"

Enable the system config:
```sql
UPDATE AD_SysConfig SET Value = 'Y' WHERE Name = 'frontend.testing';
```

### Error: "Failed to create test user: 401 Unauthorized"

Check that:
- App server is running on port 8282
- Testing API endpoint is accessible: http://localhost:8282/api/v2/frontendTesting

### Error: "Login failed"

Verify:
- Frontend is running on port 3000
- WebAPI is running on port 8080
- Login endpoint is accessible: http://localhost:8080/rest/api/login/authenticate

### Error: "No SESSION cookie found"

This may indicate:
- Login was unsuccessful (check credentials)
- Session management changed in metasfresh
- Browser context is not preserving cookies

### Layout is missing fields

Ensure:
- You're fetching from the correct window ID
- You're using the right layout type (detail vs grid)
- Layout language matches (default: en_US)
- Application Dictionary is up to date

## Resources

- [metasfresh Application Dictionary](https://docs.metasfresh.org/developers_collection/en/application_dictionary)
- [Frontend Testing API](../../README.md)
- [Playwright Documentation](https://playwright.dev)
- [REST API Documentation](http://localhost:8080/rest/api/swagger-ui.html)

## License

GPL-2.0
