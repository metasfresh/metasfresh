# Cucumber Test Plan: GLN with Label (glnl-) BPartner Identifier for Order Candidates REST API

**Date Created**: 2025-11-02
**Feature**: GLN with Lookup Label Support
**Related PR**: #21588
**Status**: Ready for Implementation

---

## Test Overview

**Objective**: Verify that the new `glnl-` BPartner identifier format works correctly when creating order candidates via the REST API `/api/v2/orders/sales/candidates`.

**Feature Location**: `backend/de.metas.cucumber/src/test/resources/de/metas/cucumber/features/automateOrderCand/orderCandidateGlnWithLabel.feature`

**Reference Tests**:
- `automateOrderCandToOrderAndShipmentAndInvoice.feature` (basic order cand flow)
- `salesOrderCandidateToOrderWorkpackageProcess.feature` (uses `gln-` identifiers)

**Background Context**:
- The feature adds support for differentiating BPartners that share the same GLN using a `Lookup_Label` field
- New identifier format: `glnl-{GLN}_{LABEL}` (e.g., `glnl-1234567890123_DIVISION_A`)
- Implemented in `GlnWithLabel` class and integrated throughout REST API

---

## Test Scenarios

### **Scenario 1: Create order candidate using glnl- identifier (positive path)**

**Purpose**: Verify that order candidates can be created using the new `glnl-{GLN}_{LABEL}` format when multiple BPartners share the same GLN.

**Test ID**: `@Id:S_GLNL_001`

**Test Setup**:
1. Create 2 BPartners that share the same GLN (`1234567890123`)
   - BPartner 1: GLN=`1234567890123`, Lookup_Label=`DIVISION_A`
   - BPartner 2: GLN=`1234567890123`, Lookup_Label=`DIVISION_B`

2. Create BPartner locations with the same GLN for both partners
   - Location 1: GLN=`1234567890123`, linked to BPartner 1
   - Location 2: GLN=`1234567890123`, linked to BPartner 2

3. Create product, pricing system, price list, price list version, product price

**Test Execution**:
```gherkin
When a 'POST' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates' and fulfills with '201' status code
```
- First request: `"bpartnerIdentifier": "glnl-1234567890123_DIVISION_A"`
- Second request: `"bpartnerIdentifier": "glnl-1234567890123_DIVISION_B"`

**Expected Results**:
- First OLCand is created and linked to BPartner 1 (DIVISION_A)
- Second OLCand is created and linked to BPartner 2 (DIVISION_B)
- Both OLCands can be processed to orders successfully
- Orders are linked to correct BPartners with correct locations

**Validation Steps**:
```gherkin
Then process metasfresh response JsonOLCandCreateResponse
And validate C_OLCand:
  | C_OLCand_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | IsError |
  | olcand_divA            | bpartner_divA            | bplocation_divA                   | N       |
  | olcand_divB            | bpartner_divB            | bplocation_divB                   | N       |
```

---

### **Scenario 2: Ambiguous GLN without label (edge case)**

**Purpose**: Verify behavior when multiple BPartners share a GLN and only `gln-` identifier is used (without label).

**Test ID**: `@Id:S_GLNL_002`

**Test Setup**:
- Same as Scenario 1 (2 BPartners with same GLN, different labels)

**Test Execution**:
- POST order candidate with `"bpartnerIdentifier": "gln-1234567890123"` (no label)

**Expected Results**:
- Order candidate creation should either:
  - **Option A**: Return the first matching BPartner (default behavior)
  - **Option B**: Fail with clear error message indicating ambiguous BPartner (multiple matches)

**Note**: This tests backward compatibility. Behavior should be verified with business stakeholders.

---

### **Scenario 3: GLN with label - only one BPartner exists with that label**

**Purpose**: Verify that glnl- identifier works correctly when only one BPartner has the specific label.

**Test ID**: `@Id:S_GLNL_003`

**Test Setup**:
1. Create 1 BPartner: GLN=`9876543210987`, Lookup_Label=`WAREHOUSE_1`
2. Create BPartner location with same GLN

**Test Execution**:
- POST order candidate with `"bpartnerIdentifier": "glnl-9876543210987_WAREHOUSE_1"`

**Expected Results**:
- OLCand is created successfully
- OLCand is linked to correct BPartner
- Can be processed to order

---

### **Scenario 4: Invalid label for GLN (negative path)**

**Purpose**: Verify error handling when glnl- identifier specifies a label that doesn't exist for that GLN.

**Test ID**: `@Id:S_GLNL_004`

**Test Setup**:
- Same as Scenario 3 (BPartner with `WAREHOUSE_1` label)

**Test Execution**:
- POST order candidate with `"bpartnerIdentifier": "glnl-9876543210987_INVALID_LABEL"`

**Expected Results**:
- Order candidate creation fails
- Response status: 4xx (likely 404 or 400)
- Clear error message in response indicating BPartner not found for GLN with label

---

### **Scenario 5: Process order candidates to orders with glnl- identifiers**

**Purpose**: End-to-end test verifying that order candidates created with glnl- identifiers can be processed through the full order lifecycle.

**Test ID**: `@Id:S_GLNL_005`

**Test Setup**:
- Same as Scenario 1 (2 BPartners with same GLN, different labels)

**Test Execution**:
1. POST order candidate with `glnl-1234567890123_DIVISION_A`
2. POST order candidate with `glnl-1234567890123_DIVISION_B`
3. Process order candidates to orders (`PUT /api/v2/orders/sales/candidates/process`)
4. Optionally: create shipments and invoices

**Expected Results**:
- 2 separate orders are created
- Order 1 is linked to BPartner 1 (DIVISION_A) with correct location
- Order 2 is linked to BPartner 2 (DIVISION_B) with correct location
- All order lines have correct products, quantities, prices
- Shipment schedules created (if requested)
- Invoices created (if requested)

**Validation Steps**:
```gherkin
When a 'PUT' request with the below payload is sent to the metasfresh REST-API 'api/v2/orders/sales/candidates/process' and fulfills with '200' status code

Then process metasfresh response
  | C_Order_ID.Identifier |
  | order_divA,order_divB |

And validate the created orders
  | C_Order_ID.Identifier | C_BPartner_ID.Identifier | C_BPartner_Location_ID.Identifier | processed | DocStatus |
  | order_divA            | bpartner_divA            | bplocation_divA                   | true      | CO        |
  | order_divB            | bpartner_divB            | bplocation_divB                   | true      | CO        |
```

---

## Test Data Structure

### Background Setup (Common for all scenarios)

```gherkin
Background:
  Given infrastructure and metasfresh are running
  And the existing user with login 'metasfresh' receives a random a API token for the existing role with name 'WebUI'
  And metasfresh has date and time 2025-11-02T13:30:13+01:00[Europe/Berlin]
  And set sys config boolean value true for sys config SKIP_WP_PROCESSOR_FOR_AUTOMATION
```

### Pricing System Setup

```gherkin
Given metasfresh contains M_PricingSystems
  | Identifier | Name                     | Value                    | OPT.IsActive |
  | ps_glnl    | pricing_system_glnl_test | pricing_system_glnl_test | true         |

And metasfresh contains M_PriceLists
  | Identifier | M_PricingSystem_ID.Identifier | OPT.C_Country.CountryCode | C_Currency.ISO_Code | Name             | SOTrx | IsTaxIncluded | PricePrecision | OPT.IsActive |
  | pl_glnl    | ps_glnl                       | DE                        | EUR                 | pl_glnl_test     | true  | false         | 2              | true         |

And metasfresh contains M_PriceList_Versions
  | Identifier | M_PriceList_ID.Identifier | Name                 | ValidFrom  |
  | plv_glnl   | pl_glnl                   | plv_glnl_test        | 2025-11-01 |
```

### Product Setup

```gherkin
And metasfresh contains M_Products:
  | Identifier       | Name                   |
  | product_glnl     | product_glnl_test      |

And metasfresh contains M_ProductPrices
  | Identifier      | M_PriceList_Version_ID.Identifier | M_Product_ID.Identifier | PriceStd | C_UOM_ID.X12DE355 | C_TaxCategory_ID.InternalName |
  | pp_glnl         | plv_glnl                          | product_glnl            | 10.0     | PCE               | Normal                        |
```

### BPartner Setup (Scenario 1 & 5)

```gherkin
And metasfresh contains C_BPartners:
  | Identifier    | Name                     | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.Lookup_Label | GLN           | deliveryRule |
  | bpartner_divA | Division A Customer      | Y              | ps_glnl                       | DIVISION_A       | 1234567890123 | F            |
  | bpartner_divB | Division B Customer      | Y              | ps_glnl                       | DIVISION_B       | 1234567890123 | F            |

And metasfresh contains C_BPartner_Locations:
  | Identifier       | GLN           | C_BPartner_ID.Identifier |
  | bplocation_divA  | 1234567890123 | bpartner_divA            |
  | bplocation_divB  | 1234567890123 | bpartner_divB            |
```

### BPartner Setup (Scenario 3 & 4)

```gherkin
And metasfresh contains C_BPartners:
  | Identifier         | Name                   | OPT.IsCustomer | M_PricingSystem_ID.Identifier | OPT.Lookup_Label | GLN           | deliveryRule |
  | bpartner_warehouse | Warehouse 1 Customer   | Y              | ps_glnl                       | WAREHOUSE_1      | 9876543210987 | F            |

And metasfresh contains C_BPartner_Locations:
  | Identifier             | GLN           | C_BPartner_ID.Identifier |
  | bplocation_warehouse   | 9876543210987 | bpartner_warehouse       |
```

---

## REST API Payload Examples

### Order Candidate with glnl- identifier (Scenario 1 - Division A)

```json
{
    "orgCode": "001",
    "externalHeaderId": "GLNL_TEST_001",
    "externalLineId": "GLNL_LINE_001",
    "externalSystemCode": "TestSystem",
    "dataSource": "int-Test",
    "bpartner": {
        "bpartnerIdentifier": "glnl-1234567890123_DIVISION_A",
        "bpartnerLocationIdentifier": "glnl-1234567890123_DIVISION_A"
    },
    "dateRequired": "2025-11-15",
    "dateOrdered": "2025-11-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product_glnl",
    "qty": 5,
    "price": 10.00,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "PO_GLNL_DIV_A",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
```

### Order Candidate with glnl- identifier (Scenario 1 - Division B)

```json
{
    "orgCode": "001",
    "externalHeaderId": "GLNL_TEST_002",
    "externalLineId": "GLNL_LINE_002",
    "externalSystemCode": "TestSystem",
    "dataSource": "int-Test",
    "bpartner": {
        "bpartnerIdentifier": "glnl-1234567890123_DIVISION_B",
        "bpartnerLocationIdentifier": "glnl-1234567890123_DIVISION_B"
    },
    "dateRequired": "2025-11-15",
    "dateOrdered": "2025-11-02",
    "orderDocType": "SalesOrder",
    "paymentTerm": "val-1000002",
    "productIdentifier": "val-product_glnl",
    "qty": 3,
    "price": 10.00,
    "currencyCode": "EUR",
    "discount": 0,
    "poReference": "PO_GLNL_DIV_B",
    "deliveryViaRule": "S",
    "deliveryRule": "F"
}
```

### Process Order Candidates Request

```json
{
    "externalHeaderId": "GLNL_TEST_001",
    "externalSystemCode": "TestSystem",
    "ship": false,
    "invoice": false,
    "closeOrder": false
}
```

---

## Implementation Steps

### 1. Create Feature File

**Location**: `backend/de.metas.cucumber/src/test/resources/de/metas/cucumber/features/automateOrderCand/orderCandidateGlnWithLabel.feature`

**Structure**:
```gherkin
@from:cucumber
@topic:orderCandidate
@ghActions:run_on_executor3
Feature: Order Candidate with GLN and Lookup Label (glnl-) identifier
  As an API user
  I want to create order candidates using BPartner GLN with lookup label
  So that I can differentiate between BPartners sharing the same GLN

  Background:
    [Common setup steps]

  @from:cucumber
  @topic:orderCandidate
  @Id:S_GLNL_001
  Scenario: Create order candidates for different BPartners sharing same GLN using glnl- identifier
    [Scenario 1 implementation]

  [Additional scenarios...]
```

### 2. Reuse Existing Step Definitions

**No new step definitions needed!**

Use existing steps from:
- `C_BPartner_StepDef.java` - BPartner and location creation
- `RestApiController_StepDef.java` - REST API calls
- `C_OLCand_StepDef.java` - OLCand validation
- `C_Order_StepDef.java` - Order validation

### 3. Test Data Guidelines

- Use descriptive identifiers: `bpartner_divisionA_glnl`, not `bp1`
- Keep test data minimal - only what's needed for the scenario
- Reuse pricing systems and products across scenarios where possible
- Use realistic GLN values (13 digits)
- Use clear label names (DIVISION_A, WAREHOUSE_1, etc.)

### 4. Validation Checklist

For each scenario, validate:

✅ **OLCand Creation**:
- Correct BPartner linkage
- Correct BPartner Location linkage
- IsError = N
- Processed status
- ExternalHeaderId and ExternalLineId match

✅ **Order Creation** (for processing scenarios):
- Correct BPartner
- Correct BPartner Location
- DocStatus = CO (completed)
- Processed = true
- Order lines have correct product, qty, price

✅ **Error Handling** (for negative scenarios):
- Appropriate HTTP status code
- Clear error message in response
- No partial data created

---

## Success Criteria

### Functional Requirements

✅ **Scenario 1**: OLCands created successfully with correct BPartner mapping for both labels
✅ **Scenario 2**: Ambiguous GLN handled appropriately (either error or first match)
✅ **Scenario 3**: Single BPartner with label works correctly
✅ **Scenario 4**: Invalid label returns clear error
✅ **Scenario 5**: Full end-to-end flow works (OLCand → Order → optional Shipment/Invoice)

### Non-Functional Requirements

✅ **Performance**: Each scenario completes in < 30 seconds
✅ **Maintainability**: Uses existing step definitions, no custom code
✅ **CI/CD**: Tests run successfully in GitHub Actions
✅ **No Regressions**: Existing `gln-` tests still pass
✅ **Code Coverage**: Tests cover both positive and negative paths

---

## File Structure

```
backend/de.metas.cucumber/
├── TEST_PLAN_GLN_WITH_LABEL.md  [THIS FILE]
├── src/test/resources/de/metas/cucumber/features/
│   └── automateOrderCand/
│       ├── automateOrderCandToOrderAndShipmentAndInvoice.feature (existing)
│       ├── salesOrderCandidateToOrderWorkpackageProcess.feature (existing)
│       └── orderCandidateGlnWithLabel.feature  [NEW - TO BE CREATED]
└── src/test/java/de/metas/cucumber/stepdefs/
    └── (reuse existing step definitions, no new files needed)
```

---

## Tags Reference

```gherkin
@from:cucumber          # Standard cucumber test tag
@topic:orderCandidate   # Topical grouping
@ghActions:run_on_executor3  # CI/CD executor assignment
@Id:S_GLNL_001         # Test case ID (use sequential numbering)
```

---

## Testing Approach

### Local Testing (Recommended)

1. **Start infrastructure**:
   ```bash
   cd misc/dev-support/docker/infrastructure/scripts
   ./95_start.sh
   ```

2. **Set environment variables**:
   ```bash
   export CUCUMBER_IS_USING_PROVIDED_INFRASTRUCTURE=true
   export CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_HOST=localhost
   export CUCUMBER_EXTERNALLY_RUNNING_POSTGRESQL_PORT=5432
   # ... other vars
   ```

3. **Run specific feature**:
   ```bash
   cd backend/de.metas.cucumber
   mvn test -s ../../../misc/dev-support/maven/settings.xml \
            -Dmaven.gitcommitid.skip=true \
            -Dcucumber.features="src/test/resources/de/metas/cucumber/features/automateOrderCand/orderCandidateGlnWithLabel.feature"
   ```

### IntelliJ Testing

1. Open `orderCandidateGlnWithLabel.feature`
2. Click green play button next to scenario
3. View results in test runner

---

## Related Documentation

- **Feature Implementation PR**: #21588
- **GlnWithLabel Class**: `backend/de.metas.adempiere.adempiere/base/src/main/java/de/metas/bpartner/GlnWithLabel.java`
- **REST API Controller**: `backend/de.metas.business.rest-api-impl/src/main/java/de/metas/rest_api/v2/bpartner/BpartnerRestController.java`
- **Cucumber Documentation**: `backend/de.metas.cucumber/CLAUDE.md`

---

## Estimated Effort

| Task | Estimated Time |
|------|----------------|
| Write Scenario 1 (happy path) | 30 minutes |
| Write Scenario 2 (ambiguous GLN) | 20 minutes |
| Write Scenario 3 (single label) | 15 minutes |
| Write Scenario 4 (invalid label) | 15 minutes |
| Write Scenario 5 (end-to-end) | 45 minutes |
| Local testing and debugging | 1 hour |
| CI/CD verification | 30 minutes |
| **Total** | **~3 hours** |

**Test Execution Time**: ~30-45 seconds per scenario (150-225 seconds total)

---

## Next Actions

1. ✅ Review this test plan
2. ⬜ Create feature file with Scenario 1 (happy path)
3. ⬜ Test Scenario 1 locally
4. ⬜ Add remaining scenarios incrementally
5. ⬜ Run full cucumber test suite for regression testing
6. ⬜ Commit and push to branch
7. ⬜ Verify tests pass in CI/CD

---

**Document Version**: 1.0
**Last Updated**: 2025-11-02
**Author**: Generated via Claude Code
**Status**: Ready for Implementation
