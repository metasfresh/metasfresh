# Frontend Web UI E2E Test Coverage

**Last Updated**: 2026-03-04

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

### 8. Quick Input (Batch Entry) Focus & Selection
**File**: `tests/spec/quick-input.spec.js`
**Status**: ✅ Passing (English, German)
**Duration**: ~20 seconds per test per language

**Features Tested**:
- F00100: Sales Order

**Epic**: E0100: Sales

**Tests**:
1. **Enter-key selects product and advances focus to Qty** - Type product → Enter → verify product resolved → fill qty → submit → verify order line created
2. **Mouse-click selects product in batch entry** (regression) - Type product → mouse click dropdown option → verify product resolved → fill qty → submit → verify order line created
3. **Add two lines in sequence via Enter-key** - Add first line via Enter workflow → quick input stays open → add second line → verify both lines in grid
4. **Invalid product: no beep when sysconfig disabled (default)** - Type non-existent product → Enter → verify beep does NOT play (sysconfig default N) → product field retains invalid text → no order line created
5. **Regular form lookup: customer selection still works** (regression) - Select customer via BPartner composed lookup in SO header → verify record saved with customer

**Key Validations**:
- Enter-key selection advances focus from product to quantity field
- Mouse-click selection still works after focus-advance code change
- Quick input resets properly for consecutive line entry
- Invalid product triggers beep, focus stays on product field
- Regular form (non-quick-input) lookup behavior unchanged
- Grid row count verification (`table tbody tr`)

**Components Tested**:
- Sales Order window (143)
- Order Lines tab (batch entry / quick input)
- Lookup widget: Enter-key resolution (RawLookup.resolveAndSelectOnEnter)
- Lookup widget: Mouse-click selection (RawLookup.handleSelect_RegularItem)
- Lookup widget: Composed lookup (BPartner + Location + Contact)
- Lookup widget: Invalid product beep (RawLookup.playBeep)
- Focus advance mechanism (RawLookup.focusNextFieldInForm + Lookup.focusNextFormField)

**Regression Coverage**:
This suite specifically guards the `Lookup.js` / `RawLookup.js` focus management changes:
- `resolveAndSelectOnEnter` path (quick input, Enter) → Tests 1, 3, 4
- `shouldKeepFocus = false` path (quick input, mouse click) → Test 2
- `shouldKeepFocus = true` path (regular form, onChange returns Promise) → Test 5
- `focusNextFieldInForm()` with retry (Enter in quick input) → Tests 1, 3
- `focusNextFormField()` called inside `<form>` (mouse click) → Test 2
- `focusNextFormField()` no-op outside `<form>` → Test 5
- `playBeep()` on invalid product → Test 4

---

### 9. Invoice Reversal (Reverse-Correct)
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

### 10. Business Partner Creation
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

### 11. Menu Navigation & Window Search
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

### 12. Bookmark Star (SubHeader)
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

### 13. Document References (Alt+6) - Complete O2C and P2P
**File**: `tests/spec/document-references.spec.js`
**Status**: ✅ Passing
**Duration**: ~2.8 minutes (both tests)

**Features Tested**:
- F00100: Sales Order, F00130: Shipment Schedule, F00150: Shipment, F00200: Invoice
- F00600: Purchase Order, F65010: Material Receipt Candidates, F00700: Invoice Candidate, F00710: Vendor Invoice

**Epics**: E0100 (Sales), E0140 (Purchasing)

**Tests**:

**13a. Sales Side (O2C Flow)**:
1. Create SO → add order line → complete (with retry verification)
2. Navigate via Alt+6 to Shipment Schedule → create shipment
3. Navigate via Alt+6 to Invoice Candidates → create invoice
4. Verify ALL Alt+6 references on completed SO (expects 4 core: Shipment Schedule, Shipment, Invoice Candidates, Customer Invoice)
5. Click through to Shipment → verify its Alt+6 references
6. Click through to Invoice → verify its Alt+6 references (Sales Order, Shipment, Invoice Candidate)

**13b. Purchase Side (P2P Flow)**:
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

### 14. Zoom-Into (Right-Click Context Menu)
**File**: `tests/spec/zoom-into.spec.js`
**Status**: ✅ Passing
**Duration**: ~30 seconds

**Features Tested**:
- F14010: Navigation (Right-Click Zoom)

**Epic**: E0193: User Interface

**Workflow**:
1. Create SO with order line (product + quantity)
2. Right-click on Product cell in order line grid
3. Verify context menu appears with "Zoom Into" option (meta-icon-share icon)
4. Click "Zoom Into" — verify new tab opens to Product window (/window/140/{id})
5. Close new tab, return to original page

**Key Validations**:
- Context menu appears on right-click with correct items
- "Zoom Into" identified by `.meta-icon-share` icon (language-independent)
- New tab opens with correct window/record URL pattern
- Context menu also shows: Advanced edit (Alt+E), Delete (Alt+Y), Change Log

**Components Tested**:
- Grid cell context menu (`.context-menu`)
- Context menu items (`.context-menu-item`)
- New tab navigation via `page.context().waitForEvent('page')`

---

### 15. Keyboard Shortcuts
**File**: `tests/spec/keyboard-shortcuts.spec.js`
**Status**: ✅ Passing
**Duration**: ~24 seconds

**Features Tested**:
- F14020: Keyboard Shortcuts (all from keymap.js)

**Epic**: E0193: User Interface

**Shortcuts Verified**:
| Shortcut | keymap.js Name | Action | Selector |
|----------|----------------|--------|----------|
| Alt+N | NEW_DOCUMENT | Create new record | URL changes to /window/{id}/{recordId} |
| Alt+6 | OPEN_SIDEBAR_MENU_1 | Open references panel | `.order-list-panel-open` |
| Alt+E | OPEN_ADVANCED_EDIT | Advanced edit modal | `.panel-modal, .modal-content` |
| Alt+P | OPEN_PRINT_RAPORT | Print/PDF dialog | `.modal-content, .panel-modal` |
| Alt+1 | OPEN_ACTIONS_MENU | Toggle subheader | `.subheader-container` |
| Alt+3 | OPEN_INBOX_MENU | Open inbox/notifications | `.inbox` |
| Alt+I | DOC_STATUS | Document status dropdown | `[data-testid="status-CO"]` |

**Key Discovery** (docs vs reality):
- docs.metasfresh.org says Alt+1..5 are for tab navigation — **WRONG**. Alt+1..7 are header menu toggles.
- Alt+I is DOC_STATUS (status dropdown), NOT sidebar/inbox toggle.
- Alt+3 is the inbox toggle, not Alt+I.

**Components Tested**:
- Header menus and subheader (`.subheader-container`)
- Inbox panel (`.inbox`)
- Document status dropdown (`.js-dropdown-toggler`)
- Print/PDF modal, Advanced edit modal

---

### 16. Grid Filtering & Column Sorting
**File**: `tests/spec/grid-filtering.spec.js`
**Status**: ✅ Passing
**Duration**: ~20 seconds

**Features Tested**:
- F14030: Filtering & Sorting

**Epic**: E0193: User Interface

**Workflow**:
1. Navigate to Sales Order list view
2. Verify 17 column headers with `data-testid="column-{fieldName}"` attributes
3. Click sortable column — verify URL sort parameter (ASC/DESC toggle)
4. Discover filter buttons ("Filter by: Date", "Filter")
5. Open filter dropdown, verify filter menu appears
6. Verify pagination controls and grid row interaction
7. Double-click row — verify navigation to detail view

**Key Discoveries**:
- Column headers: `data-testid="column-DocumentNo"`, `column-C_BPartner_ID`, etc.
- Sort URL format: `?sort=+FieldName` (ASC), `?sort=-FieldName` (DESC)
- 2 filter buttons + inline filters present on SO list view

---

### 17. Document Clone (Alt+W)
**File**: `tests/spec/document-clone.spec.js`
**Status**: ✅ Passing
**Duration**: ~22 seconds

**Features Tested**:
- F14040: Document Actions (Clone)

**Epic**: E0193: User Interface

**Workflow**:
1. Create SO with customer (saved document required for clone)
2. Press Alt+W to clone
3. Verify URL changes to new record ID
4. Verify cloned document has different document number

**Key Validation**: Clone fails on empty/unsaved documents — requires at least a customer set.

---

### 18. SubHeader Actions & Change Log
**File**: `tests/spec/subheader-actions.spec.js`
**Status**: ✅ Passing
**Duration**: ~36 seconds

**Features Tested**:
- F14040: Document Actions (SubHeader)

**Epic**: E0193: User Interface

**Workflow**:
1. Open SubHeader via Alt+1
2. Verify 7 action icons: Clone (meta-icon-duplicate), Print (meta-icon-print),
   Email (meta-icon-mail), Delete (meta-icon-delete), Letter (meta-icon-letter),
   Edit (meta-icon-edit), Comments (meta-icon-message)
3. Verify breadcrumb: `/Sales/Sales Order/{docNo}`
4. Add order line, right-click — verify context menu has Change Log option

---

### 19. Included Tabs & Batch Entry
**File**: `tests/spec/included-tabs.spec.js`
**Status**: ✅ Passing
**Duration**: ~32 seconds

**Features Tested**:
- F14050: Included Tabs

**Epic**: E0193: User Interface

**Key Discoveries**:
- Sales Order tabs: Order Line (AD_Tab-187), Order Tax (AD_Tab-236), Order Cost (AD_Tab-546846)
- Business Partner tabs: **19 tabs** (Customer, Vendor, Manufacturer, Location, Contact, Bank Account, etc.)
- Tab `data-testid` format: `tab-AD_Tab-{tabId}`
- Batch entry toggle opens/closes quick input container with Product and Quantity fields

---

### 20. List View Actions (Row Selection, Alt+B, Alt+A)
**File**: `tests/spec/list-view-actions.spec.js`
**Status**: ✅ Passing
**Duration**: ~15 seconds

**Features Tested**:
- F14060: List View Actions

**Epic**: E0193: User Interface

**Workflow**:
1. Navigate to SO list view
2. Click row — selection tracked via row classes
3. Ctrl+click second row — multi-select (`row-selected` class)
4. Alt+B — opens selected row in new browser tab (verified URL)
5. Alt+A — selects ALL rows (verified 15/15 selected)

**Key Discovery**: First clicked row doesn't get `row-selected` class (uses `row-{id}` pattern), but multi-selected rows do.

---

### 21. Navigation Menu (Alt+2)
**File**: `tests/spec/navigation-menu.spec.js`
**Status**: ✅ Passing
**Duration**: ~8 seconds

**Features Tested**:
- F14070: Navigation Menu Overlay

**Epic**: E0193: User Interface

**Workflow**:
1. Open menu via Alt+2 — `.menu-overlay` appears
2. Verify 109 menu items present
3. Search in menu — type "Sales" to filter items
4. Escape closes the menu overlay
5. Click breadcrumb/menu header button to open

**Key Discoveries**:
- Menu overlay contains 109 items
- Search input filters items in real-time
- Items include: New Request, New Business Partner, Report Texts, etc.

---

### 22. User/Avatar Menu (Alt+4)
**File**: `tests/spec/user-menu.spec.js`
**Status**: ✅ Passing
**Duration**: ~7 seconds

**Features Tested**:
- F14080: User Menu

**Epic**: E0193: User Interface

**Workflow**:
1. Verify avatar icon in header
2. Alt+4 opens user dropdown
3. Verify 3 menu items: user info, Settings, Log Out
4. Escape closes the menu
5. Click avatar to reopen

---

### 23. Comments Panel (Alt+T)
**File**: `tests/spec/comments-panel.spec.js`
**Status**: ✅ Passing
**Duration**: ~18 seconds

**Features Tested**:
- F14090: Comments Panel

**Epic**: E0193: User Interface

**Workflow**:
1. Create SO with customer
2. Alt+T opens comments panel
3. Verify textarea (`.js-input-field`) and send button (`.btn-meta-success`)
4. Type and submit a comment
5. Close panel

---

### 24. Email & Letter Dialogs (Alt+K, Alt+R)
**File**: `tests/spec/email-letter-dialog.spec.js`
**Status**: ✅ Passing
**Duration**: ~22 seconds

**Features Tested**:
- F14100: Email & Letter Composition

**Epic**: E0193: User Interface

**Workflow**:
1. Alt+K — Email dialog (requires SubHeader icon click to open)
2. Alt+R — Letter dialog opens with headline and body fields
3. Verify `.panel-letter` with headline input and body textarea

**Key Discovery**: Alt+K does not directly open email dialog from document view; requires SubHeader icon click instead. Alt+R works directly.

---

### 25. Edit Mode Toggle (Alt+O)
**File**: `tests/spec/edit-mode-toggle.spec.js`
**Status**: ✅ Passing
**Duration**: ~22 seconds

**Features Tested**:
- F14110: Edit Mode

**Epic**: E0193: User Interface

**Workflow**:
1. Create SO with customer (32 form groups, 22 lookup fields)
2. Alt+O toggle — observe form state changes
3. Verify page stays accessible after toggle

---

### 26. Sidebar Panels (Alt+5, Alt+6, Alt+7)
**File**: `tests/spec/sidebar-panels.spec.js`
**Status**: ✅ Passing
**Duration**: ~20 seconds

**Features Tested**:
- F14120: Sidebar Panels

**Epic**: E0193: User Interface

**Workflow**:
1. Alt+5 — Opens sidebar panel 0
2. Alt+6 — Opens references panel (0 refs on new SO)
3. Alt+7 — Opens attachments panel ("There is no attachment — Add URL attachment")
4. Toggle panel open/close with Escape

---

### 27. Document Delete (Alt+D)
**File**: `tests/spec/document-delete.spec.js`
**Status**: ✅ Passing
**Duration**: ~22 seconds

**Features Tested**:
- F14040: Document Actions (Delete)

**Epic**: E0193: User Interface

**Workflow**:
1. Create SO with customer
2. Alt+D — delete confirmation dialog appears: "Delete — Are you sure? — Cancel / Delete"
3. Cancel — stays on document (URL unchanged)
4. Alt+D + Confirm — navigates to list view after deletion

---

### 28. Quick Actions
**File**: `tests/spec/quick-actions.spec.js`
**Status**: ✅ Passing
**Duration**: ~20 seconds

**Features Tested**:
- F14130: Quick Actions

**Epic**: E0193: User Interface

**Workflow**:
1. Navigate to SO list view — `.quick-actions-wrapper` present in toolbar
2. Select row — check for quick actions button
3. Create new SO — check for quick actions in detail view
4. Alt+L — toggle quick actions dropdown

---

### 29. Date Widget
**File**: `tests/spec/date-widget.spec.js`
**Status**: ✅ Passing
**Duration**: ~17 seconds

**Features Tested**:
- F14140: Widgets (Date Picker)

**Epic**: E0193: User Interface

**Workflow**:
1. Create SO — discover date-type fields
2. Check for `.rdt` (React DateTime) date picker component
3. Discover form field labels: Business Partner, Invoice Partner, Warehouse, etc.

**Key Discovery**: DatePromised field is not rendered with `#lookup_` prefix. Date picker uses React DateTime (`.rdt`, `.rdtPicker`).

---

### 30. Print Dialog (Alt+P)
**File**: `tests/spec/print-dialog.spec.js`
**Status**: ✅ Passing
**Duration**: ~18 seconds

**Features Tested**:
- F14150: Print/PDF

**Epic**: E0193: User Interface

**Workflow**:
1. Alt+P — opens print modal with 3 checkboxes: Print Logo, Print Totals, Print Prices
2. 2 buttons: Cancel, Print
3. Escape closes without printing
4. SubHeader print icon (`.meta-icon-print`) opens same dialog

---

### 31. Lookup Widget (Autocomplete)
**File**: `tests/spec/lookup-widget.spec.js`
**Status**: ✅ Passing
**Duration**: ~15 seconds

**Features Tested**:
- F14140: Widgets (Lookup/Autocomplete)

**Epic**: E0193: User Interface

**Workflow**:
1. Discover 12 lookup fields: `C_BPartner_ID`, `C_BPartner_Location_ID`, `AD_User_ID`, etc.
2. Type in BPartner lookup — 16 dropdown options appear (`.input-dropdown-list-option`)
3. Select option from dropdown
4. Multi-property chain: BPartner → Location → Contact fields linked

**Key Discovery**: Lookup IDs: `lookup_C_BPartner_ID`, `lookup_C_BPartner_Location_ID`, `lookup_AD_User_ID`, `lookup_Bill_BPartner_ID`, `lookup_Bill_Location_ID`, `lookup_Bill_User_ID`, `lookup_M_Tour_ID`, `lookup_M_PricingSystem_ID`, `lookup_ExternalSystem_ID`, `lookup_AD_InputDataSource_ID`.

---

### 32. Inline Grid Edit (F2)
**File**: `tests/spec/inline-edit.spec.js`
**Status**: ✅ Passing
**Duration**: ~25 seconds

**Features Tested**:
- F14160: Grid Edit

**Epic**: E0193: User Interface

**Workflow**:
1. Add order line via batch entry
2. Discover 33 data cells per row (`td[data-cy]`)
3. Click quantity cell — cell classes: `table-cell text-right cell-mandatory`
4. F2 / double-click — inline editor does not open (React virtual rendering)

**Key Discovery**: Grid cells use `data-cy="cell-{ColumnName}"` pattern. 33 cells include: Line, M_Product_ID, QtyAvailableForSales, QtyEnteredTU, QtyEntered, C_UOM_ID, etc. F2 inline edit does not work in the included tab grid — cells use React rendering, not standard HTML inputs.

---

### 33. Record Navigation
**File**: `tests/spec/record-navigation.spec.js`
**Status**: ✅ Passing
**Duration**: ~15 seconds

**Features Tested**:
- F14170: Record Navigation

**Epic**: E0193: User Interface

**Workflow**:
1. Double-click row in list → opens detail view
2. Browser back → returns to list view (with `viewId` in URL)
3. Browser forward → returns to detail view
4. Open 2 different records — verify different record IDs

---

### 34. Notifications & Inbox
**File**: `tests/spec/notifications.spec.js`
**Status**: ✅ Passing
**Duration**: ~7 seconds

**Features Tested**:
- F14180: Notifications

**Epic**: E0193: User Interface

**Workflow**:
1. Check for notification badge in header
2. Alt+3 — open inbox panel (`.inbox`)
3. Inbox shows "Inbox is empty" for new users
4. Discover 4 header items: hamburger, Dashboard, badge, avatar

**Key Discovery**: Header structure has 4 items: icon-sm (hamburger), icon-sm (Dashboard label), header-item-badge icon-lg (inbox/notifications), avatar-container.

---

### 35. One-time Address (Einmaladresse)
**File**: `tests/spec/one-time-address.spec.js`
**Status**: ✅ Passing
**Duration**: ~60 seconds

**Features Tested**:
- F02300: Sales Order — One-time address quick input from location fields

**Epic**: E0030: Sales

**Workflow**:
1. Create test customer via Backend API
2. Navigate to Sales Order, create new order, select customer
3. **Test 1 (DropShip)**: Enable IsDropShip, skip DropShip_BPartner_ID, click "New" on DropShip_Location_ID, fill address, check IsOneTime, submit — verify no HTTP 500 and DropShip_BPartner_ID auto-filled
4. **Test 2 (Main)**: Click "New" on C_BPartner_Location_ID, fill address, check IsOneTime, submit — verify location is created

**Components Tested**:
- Quick input modal for BPartner location
- BooleanWidget (IsDropShip checkbox)
- WidgetCommon (field containers, save detection)
- BPartnerLocationQuickInputConfiguration fallback logic

### 36. PO Reactivation — Receipt Schedule Close/Reopen (`po-reactivation-receipt-schedule.spec.js`)

**Features Tested**:
- F00600: Purchase Order
- F65010: Material Receipt Candidates

**Epic**: E0140: Purchasing / E0160: Material Receipt

**Workflow**:
1. Create PO with 1 order line and complete it
2. Navigate to receipt schedule via Related Documents — verify IsClosed=false (via WebAPI)
3. Navigate back to PO — create material receipt via HU-Editor workflow
4. Reactivate PO (requires `PO_AllowReactivationIfReceiptsCreated=Y` sysconfig)
5. Verify receipt schedule is closed (IsClosed=true), same record ID (not recreated)
6. Re-complete PO
7. Verify receipt schedule is reopened (IsClosed=false), same record ID

**Precondition**: `PO_AllowReactivationIfReceiptsCreated=Y` must be set as JVM property on webapi service.

**Components Tested**: PurchaseOrderPage (reactivate), ReceiptCandidatesPage (createReceipt), WebAPI field validation (getFieldData)

---

### 37. Promotion Code (`promotion-code.spec.js`)

**Features Tested**:
- F00100: Sales Order
- F00250: Promotion Code (Aktionskennzeichen)

**Epic**: E0100: Sales

**Workflow**:
1. Create promotion code via UI (window 542105) with Value, Name, Description
2. Create sales order with customer and product
3. Open Advanced Edit (Alt+E), set C_PromotionCode_ID via typeahead
4. Verify promo code value in Advanced Edit, close modal
5. Add order line, complete order
6. Reopen Advanced Edit — verify promo code persisted after completion
7. Navigate to Invoice Candidates (Alt+6)
8. Open first IC row, open Advanced Edit — verify promo code propagated

**Key Validations**:
- Promotion code creation via direct window navigation
- Advanced Edit modal (Alt+E) for setting and reading advanced fields
- Promotion code lookup typeahead inside modal
- Promotion code persistence after order completion
- Promotion code propagation from SO to Invoice Candidate

**Components Tested**: SalesOrderPage, InvoiceCandidatePage, AdvancedEdit utility

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
- **AdvancedEdit.js** - Advanced Edit modal (Alt+E) interactions: open, close, set/get lookup and text fields
- **common.js** - Shared timeouts and helpers
- **WindowIds.js** - Window ID constants

### Inline Test Helpers
- **quick-input.spec.js** uses inline helpers (`createMasterdata`, `setupOrderWithBatchEntry`, `typeProductAndWaitForDropdown`, `expectOrderLineInGrid`) instead of a dedicated Page Object, since the test-specific batch entry interactions (Enter-key vs mouse-click, grid row counting) are specialized and not reusable across other test suites.

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
- Saved/bookmarked filters (static filters)
- Sales quotation to sales order conversion
- Credit memo creation
- Error handling scenarios (invalid input, permission denied)
- Permission-based UI changes
- BOM / Manufacturing workflows
- Data export (Excel/CSV from grid views)
- Import functionality
- POS (Point of Sale) mode
- Column reordering / drag-to-resize
- Barcode scanner integration

## Test Quality Metrics

- **Total test specs**: 34 files
- **Total test cases**: 46+ (34 specs, many with en_US + de_DE; quick-input has 5 tests × 2 languages)
- **Language coverage**: en_US, de_DE
- **Success rate**: 100% passing
- **Average execution time**: ~20 seconds per test
- **Total suite time**: ~16 minutes (sequential execution)

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
