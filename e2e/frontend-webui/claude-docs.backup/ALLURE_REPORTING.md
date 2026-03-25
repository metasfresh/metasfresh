# Allure Reporting with Google Sheets Integration

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Rich HTML test reports with Epic > Feature > Story hierarchy**

## Overview

Tests are annotated with Allure metadata that links to the Feature/Epic tracking in Google Sheets:
- **Epics**: High-level business areas (Sales, Purchasing, Invoicing, etc.)
- **Features**: Specific capabilities within epics (Sales Order, Shipment Schedule, etc.)
- **Stories**: Test scenarios within features

## Key Files

- **`tests/utils/AllureHelpers.js`** - Utility for setting epic/feature/story and attachments
- **`tests/utils/FeatureRegistry.generated.js`** - Auto-generated mapping of Feature IDs to names/links
- **`scripts/sync-google-sheets.js`** - Sync Epic/Feature data from Google Sheets
- **`credentials/google-sheets-service-account.json`** - Service account credentials (gitignored)

## Usage in Tests

```javascript
const { AllureHelpers } = require('../utils/AllureHelpers');

test('My test', async ({ page }) => {
  // Link to Feature from Google Sheets (auto-sets Epic too)
  await AllureHelpers.setFeature('F00100');  // Sales Order
  await AllureHelpers.setStory('Create SO → View Shipment Schedule');
  await AllureHelpers.setSeverity('critical');
  await AllureHelpers.addParameter('Language', language);

  // Rich description (Markdown supported)
  await AllureHelpers.setDescription(`
## Test Scenario
This test validates the sales order workflow...
  `);

  // Attach test data summary
  await AllureHelpers.attachTestData(masterdata);

  // Attach tabular data (order lines, etc.)
  await AllureHelpers.attachTable('Order Lines',
    [{ Product: 'PROD-001', Quantity: '10', Price: '50.00' }],
    ['Product', 'Quantity', 'Price']
  );

  // Attach PDF with metadata
  const pdfPath = await download.path();
  await AllureHelpers.attachPdf('Sales Order PDF', pdfPath, {
    documentNo: 'SO-001',
    customer: 'CUST-001',
  });

  // Attach screenshot
  await AllureHelpers.attachScreenshot(page, 'Final State');
});
```

## AllureHelpers Methods

| Method | Purpose |
|--------|---------|
| `setFeature(featureId)` | Set feature + epic from FeatureRegistry, adds Google Sheets links |
| `setEpic(epicName)` | Set epic manually (usually auto-set via setFeature) |
| `setStory(storyName)` | Set story description |
| `setSeverity(level)` | Set severity: blocker, critical, normal, minor, trivial |
| `setDescription(markdown)` | Set test description (Markdown) |
| `addParameter(name, value)` | Add parameter for report display |
| `addTags(...tags)` | Add tags for filtering |
| `linkIssue(number, desc)` | Link to GitHub issue |
| `attachTable(name, data, cols)` | Attach HTML table |
| `attachPdf(name, content, meta)` | Attach PDF with optional metadata |
| `attachScreenshot(page, name)` | Capture and attach screenshot |
| `attachTestData(masterdata)` | Summarize Backend.createMasterdata() result |

## Google Sheets Sync Commands

```bash
# View statistics (epics/features count)
npm run sheets:stats

# Fetch and display raw sheet data
npm run sheets:fetch

# Regenerate FeatureRegistry.generated.js
npm run sheets:generate
```

**Sheet Structure:**
- **Epic sheet**: `No` (Epic ID), `Epic` (Name), `Product Type` (filter by "Software")
- **Feature sheet**: `No` (Feature ID), `Feature` (Name), `Epic` (links to Epic.Epic by name)

## Generating Allure Reports

```bash
# Run tests (generates allure-results/)
npm test

# Generate HTML report
npm run allure:generate

# Open generated report
npm run allure:open

# Or serve results directly (combines generate + open)
npm run allure:serve
```

## Reference Implementation

See `tests/spec/shipment-schedule.spec.js` for a complete example with:
- Feature/Story linking
- Test data attachment
- Order line table attachment
- PDF attachment with metadata
- Screenshot attachment
- Validation summary table
