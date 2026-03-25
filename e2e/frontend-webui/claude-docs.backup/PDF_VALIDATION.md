# PDF Validation with Layout Detection

> **Parent**: [../CLAUDE.md](../CLAUDE.md) | **Unified PDF validation for all document types**

## Quick Start

```javascript
const { PdfValidator } = require('../utils/PdfValidator');

// Download PDF (in Page Object)
const download = await SalesOrderPage.downloadPDF();

// Validate content + layout
await PdfValidator.validate(download, {
  documentNo: 'SO-001',
  customerName: 'CUST001',
  productCode: 'PROD001',     // Optional
  quantity: '10',              // Optional (requires productCode)
  language: 'en_US',
  checkOverlaps: true,         // Detect overlapping text (default: true)
  checkMargins: false,         // Validate page margins (default: false)
  overlapTolerance: 2,         // Tolerance in pixels (default: 2)
});
```

## Real-World Usage Pattern

**In Page Object (e.g., InvoicePage.js, ShipmentPage.js):**
```javascript
static async validatePdfContent(download, expectedData) {
  return await test.step('Validate PDF content', async () => {
    const { PdfValidator } = require('../PdfValidator');

    await PdfValidator.validate(download, {
      documentNo: expectedData.documentNo,
      customerName: expectedData.customerName,
      productCode: expectedData.productCode,
      quantity: expectedData.quantity,
      language: expectedData.language,
      checkOverlaps: true,
      overlapTolerance: 2,
    });
  });
}
```

**In Test (e.g., invoice.spec.js):**
```javascript
const masterdata = await Backend.createMasterdata({
  request: {
    login: { user: { language: 'en_US' } },
    bpartners: { CUSTOMER1: { isCustomer: true } },
    products: { Product1: { prices: [{ price: 10.0 }] } },
  },
});

const download = await InvoicePage.downloadPDF();

await InvoicePage.validatePdfContent(download, {
  documentNo: invoiceNo,
  customerName: masterdata.bpartners.CUSTOMER1.bpartnerCode,
  productCode: masterdata.products.Product1.productCode,
  quantity: '10',
  language: 'en_US',
});
```

## Features

### Text Content Validation
- Document number presence
- Customer name/code presence
- Product code presence (optional)
- Quantity validation with locale support ("10.00" vs "10,00")

### Layout Validation
- **Overlap detection**: Detects overlapping text elements using PDF.js coordinate analysis
- **Margin validation**: Checks if text stays within page boundaries (opt-in)
- **Tolerance configuration**: 1-2 pixel tolerance for minor rendering variations
- **No visual baselines needed**: Mathematical coordinate comparison, not image comparison

## Architecture

**PdfValidator.js** - Main utility (use this in tests)
- Combines text + layout validation
- Reusable across all document types
- Single entry point for all PDF checks

**PdfLayoutValidator.js** - Low-level coordinate analysis
- Extracts text coordinates using PDF.js
- Detects bounding box overlaps
- Validates margin violations

## Why This Approach?

**Coordinate-based detection (not visual regression):**
- Fast - No image conversion required
- CI/CD friendly - Works headless without display server
- No baseline maintenance - Mathematical comparison
- Precise - Exact pixel-level overlap detection
- Deterministic - No false positives from font anti-aliasing

## Options Reference

| Option | Type | Default | Description |
|--------|------|---------|-------------|
| `documentNo` | string | required | Document number to validate |
| `customerName` | string | required | Customer name/code to validate |
| `productCode` | string | optional | Product code to validate |
| `quantity` | string | optional | Quantity to validate (requires productCode) |
| `language` | string | optional | Language for logging (e.g., 'en_US') |
| `checkOverlaps` | boolean | true | Enable overlap detection |
| `checkMargins` | boolean | false | Enable margin validation |
| `overlapTolerance` | number | 2 | Overlap tolerance in pixels (1-2 recommended) |
| `margins` | object | - | Custom margins `{top, right, bottom, left}` |
| `pageSize` | object | A4 | Custom page size `{width, height}` in PDF units |

## Document-Specific Examples

### Sales Order
```javascript
static async validatePdfContent(download, expectedData) {
  const { PdfValidator } = require('../PdfValidator');

  await PdfValidator.validate(download, {
    documentNo: expectedData.documentNo,
    customerName: expectedData.customerName,
    productCode: expectedData.productCode,
    quantity: expectedData.quantity,
    language: expectedData.language,
    checkOverlaps: true,
    overlapTolerance: 2,
  });
}
```

### Invoice
```javascript
static async validatePdfContent(download, expectedData) {
  const { PdfValidator } = require('../PdfValidator');

  await PdfValidator.validate(download, {
    documentNo: expectedData.invoiceNo,
    customerName: expectedData.customerName,
    language: expectedData.language,
    checkOverlaps: true,
  });
}
```

### Shipment
```javascript
static async validatePdfContent(download, expectedData) {
  const { PdfValidator } = require('../PdfValidator');

  await PdfValidator.validate(download, {
    documentNo: expectedData.shipmentNo,
    customerName: expectedData.customerName,
    productCode: expectedData.productCode,
    quantity: expectedData.shippedQuantity,
    language: expectedData.language,
    checkOverlaps: true,
  });
}
```

## Error Messages

**Text validation failure:**
```
PDF text validation failed:
Customer name "CUST001" not found in PDF
Product code "PROD001" not found in PDF
```

**Layout validation failure:**
```
PDF layout validation failed:
Overlap detected on page 1: "Product Name" and "Quantity" overlap by 5.2px (X) / 0.0px (Y)
Overlap detected on page 1: "Total" and "Tax" overlap by 3.1px (X) / 1.5px (Y)
```

## Current Status: Overlap Detection Findings

**Layout validation successfully detects 857 overlaps in current metasfresh PDFs:**
- Footer elements overlap (metasfresh AG, UBS AG, legal text, page numbers)
- Header elements overlap (dates, customer codes)
- These are **real layout issues** in the PDF template, not false positives

**Recommendation:** Fix the PDF template first, then enable `checkOverlaps: true` to prevent regressions.

## Dependencies

- **pdf-parse@^1.1.1** - Text extraction
- **pdfjs-dist@^3.11.174** - Coordinate extraction and layout analysis (CommonJS compatible)
