# Frontend Web UI E2E Test Coverage

**Last Updated**: 2025-12-04

This document provides a complete overview of E2E test coverage for the metasfresh desktop web UI.

## Test Execution

All tests run in **multiple languages** (en_US, de_DE) to ensure language-independent selectors.

```bash
# Run all tests
npm test

# Run specific test
npx playwright test tests/spec/shipment-schedule.spec.js

# View report
npm run test:report
```

## Test Suites

### 1. Sales Order to Shipment Schedule
**File**: `tests/spec/shipment-schedule.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~38 seconds per language

**Workflow**:
1. Create sales order with customer and product (10 units)
2. Complete the sales order
3. Generate and validate PDF (Alt+P)
   - Document number validation
   - Customer name validation
   - Product code validation
   - Quantity validation (strict)
4. Navigate to Shipment Schedule (Alt+6)
5. Validate ordered quantity appears in grid (10 units)

**Key Validations**:
- Language-independent selectors (`data-testid`, `data-cy`)
- PDF content validation (all fields throw errors if missing)
- Grid cell reading via `data-cy="cell-QtyOrdered_Calculated"`
- Number formatting handling (10.00 vs 10,00)

**Components Tested**:
- Sales Order window (143)
- Order Lines tab (batch entry)
- Document actions (Complete)
- Print modal and PDF generation
- Related documents navigation (Alt+6)
- Shipment Schedule window (500221)

---

### 2. Purchase Order Workflow
**File**: `tests/spec/purchase-order.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~27 seconds per language

**Workflow**:
1. Create purchase order with vendor and product
2. Add order line via batch entry (Alt+Q)
3. Complete the purchase order
4. Verify document number assigned

**Key Validations**:
- Batch entry modal timing (two-step wait pattern)
- Lookup field interaction (`#lookup_C_BPartner_ID`)
- Dropdown selection (vendor, product)
- Document completion action
- Document number field reading

**Components Tested**:
- Purchase Order window (181)
- Batch entry form (TableQuickInput)
- Lookup widgets (RawLookup)
- Document actions toolbar

---

### 3. Business Partner Management
**File**: `tests/spec/business-partner.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~15 seconds per language

**Workflow**:
1. Navigate to Business Partner window
2. Create new business partner
3. Fill name and save
4. Verify record created

**Key Validations**:
- Window navigation
- New record creation
- Field filling (name)
- Record save verification

**Components Tested**:
- Business Partner window (123)
- Form fields and validation
- Save action

---

## Test Architecture

### Page Objects
- **LoginPage.js** - Login and authentication
- **DashboardPage.js** - Main dashboard
- **SalesOrderPage.js** - Sales orders, PDF validation, batch entry
- **PurchaseOrderPage.js** - Purchase orders, batch entry
- **ShipmentSchedulePage.js** - Shipment schedule grid
- **BusinessPartnerPage.js** - Business partner management

### Utilities
- **Backend.js** - Test data creation via `/api/v2/frontendTesting`
- **WebAPIValidation.js** - Record state validation via WebAPI
- **common.js** - Shared timeouts and helpers

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
- Material Receipt workflow
- Invoice Candidate workflow
- Product window management
- Advanced search and filtering
- Document reversal/void actions
- Multi-tab workflows (beyond order lines)
- Error handling scenarios
- Permission-based UI changes

## Test Quality Metrics

- **Total test specs**: 3
- **Total test cases**: 6 (3 specs × 2 languages)
- **Language coverage**: en_US, de_DE
- **Success rate**: 100% (6/6 passing)
- **Average execution time**: ~27 seconds per test
- **Total suite time**: ~2.7 minutes (sequential execution)

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
5. Update this report with new coverage

**When modifying UI**:
1. Verify `data-testid` attributes remain stable
2. Run affected tests before merging
3. Update Page Objects if selectors change

**When tests fail**:
1. Check test results in `test-results/` directory
2. View HTML report: `npm run test:report`
3. Examine traces for failed tests
4. Verify both en_US and de_DE tests
