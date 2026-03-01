# Frontend Web UI E2E Test Coverage

**Last Updated**: 2026-03-01

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

### 7. Payment Discount Workflow
**File**: `tests/spec/receipt.spec.js` (extended test)
**Status**: ✅ Passing (English, German)
**Duration**: ~100 seconds per language (includes full P2P + payment)

**Features Tested**:
- F00600: Purchase Order
- F65010: Material Receipt Candidates
- F00700: Invoice Candidate (Purchase)
- F00710: Vendor Invoice
- F00750: Payment Term (with discount)
- F00760: Vendor Payment
- F00770: Payment Allocation

**Epic**: E0140: Purchasing, E0160: Payments

**Workflow**:
1. Create Payment Term with 5% discount if paid within 10 days (via WebUI)
2. Set PO_PaymentTerm_ID on vendor (via WebAPI - field is in included tab)
3. Create purchase order with vendor and product (qty=5)
4. Verify payment term auto-fills on PO (via WebAPI - field not displayed)
5. Complete PO, create receipt, create invoice (standard P2P flow)
6. Create vendor payment with discounted amount (PayAmt = GrandTotal - 5%)
7. Complete the payment
8. Validate payment allocation (IsPaid, IsAllocated flags, payment amount)

**Key Validations**:
- Payment Term creation with discount percentage and days
- PO_PaymentTerm_ID field update via WebAPI (included tab pattern)
- C_PaymentTerm_ID verification on Purchase Order via WebAPI
- Payment amount calculation with discount applied
- Payment IsAllocated flag validation
- Invoice IsPaid flag validation
- Allocation line details (when available via API)

**Components Tested**:
- Payment Term window (161)
- Business Partner window (123) - Vendor tab (AD_Tab-224)
- Purchase Order window (181)
- Payment window (195)
- Document type selection for AP Payment
- Document actions (Complete)

**Technical Notes**:
- PO_PaymentTerm_ID is in an "included tab" (TabLevel=1, IsSingleRow=Y) that doesn't render separately
- C_PaymentTerm_ID on Purchase Order has IsDisplayed='N' in AD_Field
- Both fields are updated/verified via WebAPI instead of UI navigation
- Allocation validation is resilient to async processing (logs warnings if pending)

---

### 8. Invoice Reversal (Reverse-Correct)
**File**: `tests/spec/invoice-reversal.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~70 seconds per language

**Features Tested**:
- F00100: Sales Order
- F00130: Shipment Schedule
- F00150: Sales Shipment
- F00200: Sales Invoice
- F00210: Invoice Reversal (Document Actions)

**Epic**: E0100: Sales

**Workflow**:
1. Create sales order with customer and product (qty=5)
2. Complete the sales order
3. Navigate to Shipment Schedule (Alt+6), create shipment
4. Navigate to Invoice Candidates (Alt+6), create invoice
5. Navigate to completed Invoice (Alt+6)
6. Open invoice detail view
7. Execute Reverse-Correct action (status-RC)
8. Verify reversal completed without errors

**Key Validations**:
- Document status actions (Reverse-Correct)
- Invoice reversal creates reversal document
- Full O2C flow followed by invoice correction
- Language-independent status action selectors (`data-testid="status-RC"`)

**Components Tested**:
- Sales Order window (143)
- Shipment Schedule window (500221)
- Invoice Candidate window (540092)
- Invoice window (167)
- Document status actions (Complete, Reverse-Correct)

---

### 9. Business Partner Creation
**File**: `tests/spec/business-partner-create.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~14 seconds per language

**Features Tested**:
- F00900: Business Partner
- F00910: Business Partner Creation (via WebUI)

**Epic**: E0390: Masterdata Partner

**Workflow**:
1. Login with test user
2. Navigate to Business Partner window
3. Create new record (Alt+N)
4. Set company name
5. Verify auto-generated search key
6. Verify record is persisted

**Key Validations**:
- New record creation via keyboard shortcut (Alt+N)
- Form field input and auto-save
- Search key auto-generation
- Record persistence after navigation

**Components Tested**:
- Business Partner window (123)
- Record creation via Alt+N
- Form fields (Name, Value)
- Auto-save behavior

---

### 10. Menu Navigation & Window Search
**File**: `tests/spec/menu-navigation.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~15 seconds per language

**Features Tested**:
- F14010: Menu Navigation
- F14020: Window Search

**Epic**: E0193: User Interface

**Workflow**:
1. Login with test user
2. Navigate to Sales Order window via direct URL
3. Navigate to Business Partner window via direct URL
4. Navigate to Product window via direct URL
5. Interact with menu/breadcrumb navigation
6. Verify list view renders with column headers

**Key Validations**:
- URL-based window navigation
- Multiple windows load correctly
- List view table structure (column headers present)
- Navigation between windows

**Components Tested**:
- Sales Order window (143) - list view
- Business Partner window (123) - list view
- Product window (140) - list view
- Menu overlay / breadcrumb navigation

---

### 11. Bookmark Star (SubHeader)
**File**: `tests/spec/bookmark-star.spec.js`
**Status**: ✅ Passing
**Duration**: ~8 seconds

**Features Tested**:
- Bookmark star always visible in SubHeader (no hover required)
- Bookmark toggle via PATCH API (`/rest/api/menu/node/{nodeId}`)

**Epic**: E0193: System Authentication

**Workflow**:
1. Login with default credentials (handles role selection)
2. Navigate to Organisation window (`/window/110`)
3. Open SubHeader panel (click actions/more button)
4. Verify bookmark star is visible
5. Click star to bookmark — verify PATCH API call and `active` class
6. Click star again to un-bookmark — verify PATCH API and class removed

**Key Validations**:
- Star always visible in SubHeader when viewing a window (not hidden until hover)
- CSS class `active` toggled on bookmark/un-bookmark
- PATCH API call to `/rest/api/menu/node/{nodeId}` succeeds with status 200

**Components Tested**:
- SubHeader panel (`.subheader-column`)
- BookmarkButton component (`.btn-bookmark-icon`)
- Actions/more button (`.meta-icon-more`)

---

### 12. Document References (Alt+6) - Complete O2C and P2P
**File**: `tests/spec/document-references.spec.js`
**Status**: ✅ Passing
**Duration**: ~2.8 minutes (both tests)

**Features Tested**:
- F00100: Sales Order, F00130: Shipment Schedule, F00150: Shipment, F00200: Invoice
- F00600: Purchase Order, F65010: Material Receipt Candidates, F00700: Invoice Candidate, F00710: Vendor Invoice

**Epics**: E0100 (Sales), E0140 (Purchasing)

**Tests**:

**12a. Sales Side (O2C Flow)**:
1. Create SO → add order line → complete (with retry verification)
2. Navigate via Alt+6 to Shipment Schedule → create shipment
3. Navigate via Alt+6 to Invoice Candidates → create invoice
4. Verify ALL Alt+6 references on completed SO (expects 4 core: Shipment Schedule, Shipment, Invoice Candidates, Customer Invoice)
5. Click through to Shipment → verify its Alt+6 references
6. Click through to Invoice → verify its Alt+6 references (Sales Order, Shipment, Invoice Candidate)

**12b. Purchase Side (P2P Flow)**:
1. Create PO → add order line → complete (with retry verification)
2. Navigate via Alt+6 to Receipt Candidates → create material receipt
3. Navigate via Alt+6 to Invoice Candidates → create vendor invoice
4. Verify ALL Alt+6 references on completed PO (expects 4 core: Receipt Candidates, Material Receipt, Invoice Candidates, Vendor Invoice)
5. Click through to Material Receipt → verify its Alt+6 references

**Key Validations**:
- ALL Alt+6 references present after complete document flow
- Reference navigation (click-through) works correctly
- Additional/undocumented references are discovered and logged
- Robust batch entry with retry (verifies grid row exists after addOrderLine)
- Robust completion with status verification (checks innerText, retries on failure)

**Components Tested**:
- Alt+6 references panel (SSE-based, `data-cy="reference-{InternalName}"`)
- DocumentReferences.js utility (openReferencesPanel, openRelatedDocument, getVisibleReferences)
- Batch entry (quick-input-container), document status actions (status-button, status-CO)

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
- **PaymentTermPage.js** - Payment term creation with discount settings
- **PaymentPage.js** - Vendor payment creation and document actions
- **BusinessPartnerPage.js** - Business partner navigation and PO_PaymentTerm_ID setting

### Utilities
- **Backend.js** - Test data creation via `/api/v2/frontendTesting`
- **WebAPIValidation.js** - Record state validation via WebAPI
- **PaymentValidation.js** - Payment allocation and IsPaid/IsAllocated flag validation
- **DocumentReferences.js** - Alt+6 reference panel operations, reference constants, SSE resilience
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
- Advanced search and filtering (column filters, saved filters)
- Sales quotation to sales order conversion
- Credit memo creation
- Error handling scenarios (invalid input, permission denied)
- Permission-based UI changes
- Multi-product order lines
- BOM / Manufacturing workflows

## Test Quality Metrics

- **Total test specs**: 12 files
- **Total test cases**: 20+ (12 specs, most with en_US + de_DE, receipt.spec.js has 2 tests per language)
- **Language coverage**: en_US, de_DE
- **Success rate**: 100% passing
- **Average execution time**: ~40 seconds per test
- **Total suite time**: ~10 minutes (sequential execution)

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
