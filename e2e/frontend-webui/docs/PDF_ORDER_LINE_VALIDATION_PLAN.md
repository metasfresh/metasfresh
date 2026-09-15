# PDF Order Line Validation: Future Enhancement Plan

**Status:** Research complete - implementation deferred
**Created:** 2025-12-06

## Goal

Enhance PDF validation to support structured order line validation:
1. **Structured table extraction** - Parse order lines as data (line #, product, qty, price, total)
2. **Total calculations** - Verify line totals and grand totals are mathematically correct
3. **Multiple line item matching** - Validate all order lines match expected data

---

## Assessment: Microsoft MarkItDown

**Verdict: NOT recommended**

| Requirement | MarkItDown Capability | Gap |
|-------------|----------------------|-----|
| Structured table extraction | Basic text scraping, loses table structure | Cannot reliably extract order line rows |
| Total calculations | No structure preservation | Can't identify which numbers are totals vs line items |
| Multiple line matching | Plain text output | No row/column separation |

**Additional concerns:**
- Python-based (adds complexity to Node.js/Playwright stack)
- No coordinate information (can't verify layout/alignment)
- "Markitdown is a basic text scraper, struggling with document structure and tables"

---

## Recommended Approach: Extend PdfLayoutValidator.js

Your existing `PdfLayoutValidator.js` already uses **pdfjs-dist** which provides coordinate data. Extend it to:

1. **Group text items into rows** using Y-coordinates
2. **Identify columns** using X-coordinate clustering
3. **Extract structured line items** as JavaScript objects
4. **Validate totals** with arithmetic checks

### Implementation Plan

#### Phase 1: Table Row Detection
```javascript
static groupTextItemsIntoRows(textItems, tolerance = 3) {
  // Sort by Y, then group items within tolerance pixels
  // Returns: [{y: 100, items: [{text, x, width}, ...]}, ...]
}
```

#### Phase 2: Column Detection
```javascript
static detectColumns(rows, headerPatterns = ['Pos', 'Product', 'Qty', 'Price', 'Total']) {
  // Find header row, extract column X positions
  // Returns: [{name: 'Product', x: 100, width: 150}, ...]
}
```

#### Phase 3: Line Item Extraction
```javascript
static extractOrderLines(pdfBuffer) {
  // Returns: [
  //   {pos: 10, product: 'PROD001', qty: 10, price: 99.00, lineTotal: 990.00},
  //   {pos: 20, product: 'PROD002', qty: 5, price: 50.00, lineTotal: 250.00},
  // ]
}
```

#### Phase 4: Validation Methods
```javascript
static validateOrderLines(pdfBuffer, expectedLines) {
  // Match each expected line to PDF line
  // Report missing/extra/mismatched lines
}

static validateTotals(pdfBuffer, expectedGrandTotal) {
  // Sum line totals, compare to grand total in PDF
  // Detect calculation errors
}
```

### Files to Modify

| File | Changes |
|------|---------|
| `tests/utils/PdfLayoutValidator.js` | Add row/column grouping, line extraction |
| `tests/utils/PdfValidator.js` | Add `validateOrderLines()`, `validateTotals()` methods |
| `tests/spec/*.spec.js` | Update tests to use new validation |

### Example Usage (After Implementation)

```javascript
const { PdfValidator } = require('../utils/PdfValidator');

// Download PDF
const download = await SalesOrderPage.downloadPDF();

// Validate structured order lines
await PdfValidator.validateOrderLines(download, {
  lines: [
    { product: 'PROD001', qty: 10, price: 99.00, lineTotal: 990.00 },
    { product: 'PROD002', qty: 5, price: 50.00, lineTotal: 250.00 },
  ],
  grandTotal: 1240.00,
  validateCalculations: true,  // Check lineTotal = qty * price
  validateGrandTotal: true,    // Check sum of lineTotals = grandTotal
});
```

---

## Alternative: Docling (Python)

If Node.js implementation proves insufficient, consider IBM's Docling:
- 97.9% accuracy on table extraction
- Uses AI models (DocLayNet, TableFormer)
- [Docling Table Export Documentation](https://docling-project.github.io/docling/examples/export_tables/)

**Trade-off:** Requires Python integration and 1GB+ installation

---

## Sources

- [Microsoft MarkItDown GitHub](https://github.com/microsoft/markitdown)
- [PDF Conversion Tools Comparison](https://systenics.ai/blog/2025-07-28-pdf-to-markdown-conversion-tools/)
- [7 PDF Parsing Libraries for Node.js](https://strapi.io/blog/7-best-javascript-pdf-parsing-libraries-nodejs-2025)
- [Docling Table Export](https://docling-project.github.io/docling/examples/export_tables/)
- [PDF Data Extraction Benchmark 2025](https://procycons.com/en/blogs/pdf-data-extraction-benchmark/)
