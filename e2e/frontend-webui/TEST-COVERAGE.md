# Frontend Web UI E2E Test Coverage

**Last Updated**: 2026-01-23

This document provides a complete overview of E2E test coverage for the metasfresh desktop web UI.

## Test Execution

All tests run in **multiple languages** (en_US, de_DE) to ensure language-independent selectors.

```bash
# Run all tests
npm test

# Run specific test
npx playwright test tests/spec/shipment.spec.js

# View report
npm run test:report
```

## Test Suites

### 1. Login/Logout
**File**: `tests/spec/login.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~10 seconds per language

**Features Tested**:
- F14000: Username and Password Auth

**Epic**: E0193: System Authentication

**Workflow**:
1. Create test user via Backend API
2. Navigate to login page
3. Perform login with credentials
4. Verify dashboard is visible
5. Verify user is logged in

**Components Tested**:
- Login page
- Dashboard

---

### 2. Product Window
**File**: `tests/spec/product.spec.js`
**Status**: ✅ Passing
**Duration**: ~15 seconds

**Features Tested**:
- F6000: Maintain Product Data

**Epic**: E0380: Masterdata Products

**Tests**:
1. **View Product window** - Navigate to window, verify rows load
2. **Open Product detail view** - Click row, verify URL changes to detail view

**Components Tested**:
- Product window (140)
- Master window list view
- Master window detail view

---

### 3. Business Partner Window
**File**: `tests/spec/business-partner.spec.js`
**Status**: ✅ Passing
**Duration**: ~15 seconds

**Features Tested**:
- F00900: Business Partner

**Epic**: E0390: Masterdata Partner

**Tests**:
1. **View Business Partner window** - Navigate to window, verify rows load
2. **Open Business Partner detail view** - Click row, verify URL changes to detail view

**Components Tested**:
- Business Partner window (123)
- Master window list view
- Master window detail view

---

### 4. Sales Order to Shipment to Invoice (Complete O2C)
**File**: `tests/spec/shipment.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~38 seconds per language

**Features Tested**:
- F00100: Sales Order
- F00105: Sales Order Document (PDF)
- F00130: Shipment Schedule
- F00150: Sales Shipment
- F00200: Sales Invoice

**Epic**: E0100: Sales

**Workflow**:
1. Create sales order with customer and product (10 units)
2. Complete the sales order
3. Generate and validate Sales Order PDF (Alt+P)
4. Navigate to Shipment Schedule (Alt+6)
5. Validate ordered quantity appears in grid (10 units)
6. Create shipment from shipment schedule
7. Navigate to Shipment and validate PDF
8. Navigate to Invoice Candidates (Alt+6)
9. Create invoice from candidates
10. Navigate to Invoice and validate PDF

**Key Validations**:
- Language-independent selectors (`data-testid`, `data-cy`)
- PDF content validation (document number, customer, product, quantity)
- Grid cell reading via `data-cy="cell-QtyOrdered_Calculated"`
- Number formatting handling (10.00 vs 10,00)

**Components Tested**:
- Sales Order window (143)
- Order Lines tab (batch entry)
- Document actions (Complete)
- Print modal and PDF generation
- Related documents navigation (Alt+6)
- Shipment Schedule window (500221)
- Shipment window
- Invoice Candidate window
- Invoice window

---

### 5. Partial Receipt Workflow
**File**: `tests/spec/partial-receipt.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~60 seconds per language

**Features Tested**:
- F00600: Purchase Order
- F65010: Material Receipt Candidates

**Epic**: E0140: Purchasing

**Workflow**:
1. Create purchase order with vendor and product (qty=10)
2. Complete the purchase order
3. Navigate to Receipt Candidates (Alt+6)
4. Create first partial receipt with qty=6 using "Receive CUs with Qty"
5. Create second partial receipt with qty=4
6. Validate both partial receipt PDFs

**Key Validations**:
- Parameterized Quick Action (WEBUI_M_ReceiptSchedule_ReceiveCUs_WithParam)
- Parameter dialog input for quantity
- Multiple receipts from single PO line
- PDF validation for each partial receipt

**Components Tested**:
- Purchase Order window (181)
- Receipt Candidates window (540196)
- Material Receipt window (184)
- Quick Actions with parameters
- PDF generation and validation

---

### 6. Purchase-to-Invoice Workflow (Full P2P)
**File**: `tests/spec/receipt.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~50 seconds per language

**Features Tested**:
- F00600: Purchase Order
- F65010: Material Receipt Candidates
- F00700: Invoice Candidate (Purchase)
- F00710: Vendor Invoice

**Epic**: E0140: Purchasing

**Workflow**:
1. Create purchase order with vendor and product (qty=5)
2. Complete the purchase order
3. Navigate to Receipt Candidates (Alt+6)
4. Create material receipt via HU-Editor workflow
5. Navigate to Material Receipt and validate PDF
6. Navigate back to PO
7. Navigate to Invoice Candidates (Alt+6)
8. Generate vendor invoice via Quick Action
9. Navigate to Vendor Invoice (Alt+6)
10. Download and validate Vendor Invoice PDF

**Key Validations**:
- Complete PO → Receipt → Invoice flow
- Material Receipt PDF validation (vendor, product, quantity)
- Invoice Candidate processing (C_Invoice_Candidate_EnqueueSelectionForInvoicing)
- Async invoice generation with polling
- Vendor Invoice PDF validation (document number, vendor, product, quantity)

**Components Tested**:
- Purchase Order window (181)
- Receipt Candidates window (540196)
- Material Receipt window (184)
- Invoice Candidate Purchase window (540983)
- Vendor Invoice window (183)
- PDF generation and validation (both Receipt and Invoice)

---

## Test Architecture

### Page Objects
- **LoginPage.js** - Login and authentication
- **DashboardPage.js** - Main dashboard
- **MasterWindowPage.js** - Generic master data window (Product, Business Partner)
- **SalesOrderPage.js** - Sales orders, PDF validation, batch entry
- **PurchaseOrderPage.js** - Purchase orders, batch entry, invoice navigation
- **ShipmentSchedulePage.js** - Shipment schedule grid
- **ShipmentPage.js** - Shipment document and PDF
- **ReceiptCandidatesPage.js** - Receipt candidates, material receipt creation
- **InvoiceCandidatePage.js** - Invoice candidates (sales and purchase)
- **InvoicePage.js** - Sales invoice viewing and PDF validation
- **VendorInvoicePage.js** - Vendor invoice viewing and PDF validation

### Utilities
- **Backend.js** - Test data creation via `/api/v2/frontendTesting`
- **WebAPIValidation.js** - Record state validation via WebAPI
- **common.js** - Shared timeouts and helpers
- **WindowIds.js** - Window ID constants

### Test Data Strategy
- All test data created via Backend API
- Unique identifiers per test run (timestamps)
- Isolated data per test (no shared state)
- Automatic cleanup (database reset between runs)

## Language Independence

**Selector Priority** (highest to lowest):
1. ✅ `data-testid` attributes (e.g., `data-testid="action-C_Order_Complete"`)
2. ✅ `data-cy` attributes on grid cells (e.g., `data-cy="cell-QtyOrdered_Calculated"`)
3. ✅ Database column names in IDs (e.g., `#lookup_C_BPartner_ID`)
4. ✅ Stable CSS classes (e.g., `.quick-input-container`)
5. ❌ **NEVER** text-based selectors (breaks across languages)

**Components with data-testid**:
- Action buttons: `data-testid="action-{internalName}"`
- Tabs: `data-testid="tab-{tabId}"`
- Batch entry toggle: `data-testid="batch-entry-toggle"`
- Print modal button: `data-testid="print-modal-button"`

**Grid cells with data-cy**:
- Format: `data-cy="cell-{ColumnName}"`
- Example: `[data-cy="cell-QtyOrdered_Calculated"]`
- Works across all languages automatically

## Coverage Gaps

Areas **NOT yet covered** by E2E tests:
- Advanced search and filtering
- Document reversal/void actions
- Error handling scenarios
- Permission-based UI changes

## Test Quality Metrics

- **Total test specs**: 6
- **Total test cases**: 10 (6 specs, some with 2 languages × 2 tests)
- **Language coverage**: en_US, de_DE
- **Success rate**: 100% passing
- **Average execution time**: ~35 seconds per test
- **Total suite time**: ~6 minutes (sequential execution)

## CI/CD Integration

Tests run in GitHub Actions workflow (`.github/workflows/cicd.yaml`):
- Job: `frontend_webui_test`
- Browser: Chromium (headless)
- Retries: 2 attempts on failure
- Reports: JUnit XML + HTML with traces
- Artifacts: Screenshots, videos on failure

## Maintenance Notes

**When adding new tests**:
1. Use multi-language pattern (`testCases.forEach`)
2. Add language-independent selectors only
3. Create test data via Backend API
4. Follow Page Object Model pattern
5. Update this document with new coverage

**When modifying UI**:
1. Verify `data-testid` attributes remain stable
2. Run affected tests before merging
3. Update Page Objects if selectors change

**When tests fail**:
1. Check test results in `test-results/` directory
2. View HTML report: `npm run test:report`
3. Examine traces for failed tests
4. Verify both en_US and de_DE tests
