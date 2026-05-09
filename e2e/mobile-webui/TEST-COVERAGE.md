# Mobile WebUI — Test Coverage

> Update this file whenever a test is added, changed, deleted, or refactored.

## Summary

| Module | Covered | Total | % |
|---|---|---|---|
| Login / Home | 8 | 10 | 80% |
| Picking | 44 | 47 | 94% |
| Distribution | 27 | 30 | 90% |
| Manufacturing | 19 | 29 | 66% |
| HU Manager | 14 | 16 | 88% |
| HU Consolidation | 4 | 5 | 80% |
| Inventory | 1 | 3 | 33% |

---

## Login / Home

### Authentication

| Scenario | Test |
|---|---|
| Login with user/password, en_US language, UserPass auth method → language verified, logout succeeds | `login.spec.js` |
| Login with user/password, en_US language, QR_Code auth method → language verified, logout succeeds | `login.spec.js` |
| Login with user/password, de_DE language, UserPass auth method → language verified, logout succeeds | `login.spec.js` |
| Login with user/password, de_DE language, QR_Code auth method → language verified, logout succeeds | `login.spec.js` |
| ❌ Failed login (wrong password or inactive user) → error shown, access denied | — |
| ❌ QR-code login flow end-to-end → user logged in via QR scan | — |

**4/6 — 67%**

### Home screen scanning

| Scenario | Test |
|---|---|
| Scan HU QR code from home screen → navigates to HU Manager, qty shown | `home_screen.spec.js` |
| Scan HU ID (M_HU_ID) from home screen → navigates to HU Manager, qty shown | `home_screen.spec.js` |
| Scan ExternalBarcode from home screen → navigates to HU Manager, qty shown | `home_screen.spec.js` |
| Scan workplace QR code from home screen → opens Workplace Manager, name and assigned status shown | `home_screen.spec.js` |

**4/4 — 100%**

---

## Picking

### Order-based picking — core flow

| Scenario | Test |
|---|---|
| Full pick, single HU, confirm, shipment schedule marked picked | `picking.spec.js` |
| Pick HU then unpick → HU returns to unallocated | `picking.spec.js` |
| Scan invalid picking slot QR code → error shown | `picking.spec.js` |
| Line status indicator transitions draft → in-progress → complete as HUs are picked | `picking.spec.js` |
| Partial pick, allowCompletingPartialPickingJob = N → complete blocked | `picking.spec.js` |
| Partial pick, allowCompletingPartialPickingJob = Y → complete succeeds | `picking.spec.js` |
| Close LU during picking → shipment created automatically | `picking.spec.js` |
| Close LU then reopen → state transitions verified | `picking.spec.js` |
| Job already started → "already started" indicator shown in jobs list | `picking.spec.js` |
| completeJobAutomatically=true, scan drop-to locator after pick → job auto-completed, removed from list | `picking/completeJobAutomatically.spec.js` |
| ❌ Scan HU from wrong warehouse/locator → error shown | — |

**10/11 — 91%**

### Order-based picking — filtering and facets

| Scenario | Test |
|---|---|
| Facet filter shows only jobs scheduled for current workplace | `picking/facets.spec.js` |
| Filter by qty available at locator → only jobs with sufficient stock shown | `picking/filterByQtyAvailableAtLocator.spec.js` |
| ❌ Multiple jobs for same customer → aggregation count correct | — |

**2/3 — 67%**

### Order-based picking — pick-all and attributes

| Scenario | Test |
|---|---|
| Pick All button picks all remaining HUs in one action | `picking/pickAllButton.spec.js` |
| Pick All button hidden when feature disabled in mobile config | `picking/pickAllButton.spec.js` |
| Only one matching HU → picking proceeds without qty dialog | `picking/pickAttributes.spec.js` |

**3/3 — 100%**

### Order-based picking — HU scanning variants

| Scenario | Test |
|---|---|
| Pick HU by custom QR code format | `picking/pick_by_customQRCode.spec.js` |
| Pick HU by EAN13 — LU/CU into top-level TU | `picking/pick_by_EAN13.spec.js` |
| Pick HU by EAN13 — LU/CU into LU/TU1 and LU/TU2 | `picking/pick_by_EAN13.spec.js` |
| Pick HU by EAN13 — LU/CU into LU/CU | `picking/pick_by_EAN13.spec.js` |
| Pick HU by EAN13 — LU/CU into top-level CUs | `picking/pick_by_EAN13.spec.js` |
| Pick HU by ExternalBarcode attribute | `picking/pick_by_ExternalBarcode.spec.js` |
| Pick HU by M_HU_ID — LU/CU into LU/CU | `picking/pick_by_HUId.spec.js` |
| ❌ Scan ambiguous code (resolves to more than one target) → routing handled | — |

**7/8 — 88%**

### Order-based picking — LU picking

| Scenario | Test |
|---|---|
| Pick partial qty from LU — ordered qty < full LU qty | `picking/pick_from_LUs.spec.js` |
| Pick entire LU — LU qty exactly matches ordered qty | `picking/pick_from_LUs.spec.js` |
| Pick entire LU — LU qty less than ordered qty (partial fulfillment) | `picking/pick_from_LUs.spec.js` |

**3/3 — 100%**

### Order-based picking — catch-weight

| Scenario | Test |
|---|---|
| Catch-weight pick, manual typed input → qty and weight recorded | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via L+M QR code | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via L+M code — invalid code → error | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via GS1 barcode | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via EAN13 prefix 28 | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via EAN13 prefix 28 — wrong product → error | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via EAN13 prefix 29 | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via EAN13 prefix 29 — wrong product → error | `picking/picking_catchWeight.spec.js` |
| Catch-weight pick via custom QR code format | `picking/picking_catchWeight.spec.js` |
| ShowLastPickedBestBeforeDateForLines = Y → last best-before date shown on picking line | `picking/picking_catchWeight.spec.js` |

**10/10 — 100%**

### Order-based picking — special flows

| Scenario | Test |
|---|---|
| Pick triggers on-the-fly manufacturing (assemble) → assembled HU picked into order | `picking/pick_and_assemble.spec.js` |
| Lines aggregated and grouped by delivery location | `picking/picking_deliveryLocationBasedAggregation.spec.js` |
| No suggestions configured → no suggested picking slots shown | `picking/pickingSlotSuggestions.spec.js` |
| Configured picking slot suggestions → shown and selectable | `picking/pickingSlotSuggestions.spec.js` |
| Single sales order split and picked to multiple workplaces | `picking/pick_what_was_scheduled_to_workplace.spec.js` |

**5/5 — 100%**

### Product-based picking

| Scenario | Test |
|---|---|
| Lines grouped by product instead of by order | `picking/productBasedPicking/standard.spec.js` |
| Filter jobs list by scanning product EAN13 | `picking/productBasedPicking/standard.spec.js` |
| Pick HUs not pre-assigned to a specific order line | `picking/productBasedPicking/standard.spec.js` |
| Pick-from HU identified by ExternalBarcode attribute | `picking/productBasedPicking/pick_by_ExternalBarcode.spec.js` |

**4/4 — 100%**

---

## Distribution

### Core distribution flow

| Scenario | Test |
|---|---|
| Full happy-path: pick HU, scan drop-to locator, complete job | `distribution/distribution.spec.js` |
| Scan HU from wrong locator → error shown | `distribution/distribution.spec.js` |
| Scan HU containing wrong product → error shown | `distribution/distribution.spec.js` |
| Two separate HU picks needed to fulfil a single line qty | `distribution/distribution.spec.js` |
| Pick HU then unpick in step screen → line returns to unallocated | `distribution/distribution.spec.js` |
| Filter distribution jobs by plant facet | `distribution/distribution.spec.js` |
| completeJobAutomatically=true, scan drop-to locator → job auto-completed | `distribution/completeJobAutomatically.spec.js` |
| ❌ Abort (cancel) in-progress distribution job → job cancelled, stock restored | — |

**7/8 — 88%**

### Distribution launchers

| Scenario | Test |
|---|---|
| Job launcher caption formatted with locator, product, GTIN, qty, priority | `distribution/caption.spec.js` |
| No restrictions configured → all jobs enabled | `distribution/lauchers_restrictions.spec.js` |
| allowStartNextJobOnly=true → only first unstarted job enabled; starting it enables next | `distribution/lauchers_restrictions.spec.js` |
| New distribution orders appear in launcher list via websockets | `distribution/launchers_websockets.spec.js` |
| Without workplace set → all distribution jobs visible | `distribution/filter_by_workplace.spec.js` |
| With workplace set → only jobs whose drop-to locator matches workplace shown | `distribution/filter_by_workplace.spec.js` |
| Sort by SeqNo when orderBys=SeqNo,Priority,DatePromised | `distribution/sorting.spec.js` |
| ❌ maxLaunchers cap — list truncated beyond N jobs | — |
| ❌ maxStartedLaunchers cap | — |

**7/9 — 78%**

### Distribution — job execution

| Scenario | Test |
|---|---|
| Job screen header shows correct From Locator and Locator To | `distribution/header.spec.js` |
| Pick multiple HUs; Drop All delivers in one action; warehouse validated per step | `distribution/job_dropAllButton.spec.js` |
| Pick multiple HUs by M_HU_ID; Drop All via locator code | `distribution/job_dropAllButton.spec.js` |
| Pick from multiple jobs in launchers list; Drop All from jobs-list screen | `distribution/launchers_dropAllButton.spec.js` |
| navigateToJobsListAfterPickFromComplete=true → last line pick navigates to next job | `distribution/navigateToJobsListAfterPickFromComplete.spec.js` |

**5/5 — 100%**

### Distribution — HU scanning

| Scenario | Test |
|---|---|
| GTIN-validation mode: scan HU by QR code, confirm with product GTIN | `distribution/pickFrom_validate_GTIN.spec.js` |
| GTIN-validation mode: scan HU by M_HU_ID, confirm with product GTIN | `distribution/pickFrom_validate_GTIN.spec.js` |
| GTIN-validation mode: scan HU by ExternalBarcode, confirm with product GTIN | `distribution/pickFrom_validate_GTIN.spec.js` |
| Only 1 matching unit → qty dialog skipped | `distribution/pickFrom_validate_GTIN.spec.js` |
| Scan distribution HU by QR code | `distribution/scan_HU_barcodes.spec.js` |
| Scan distribution HU by M_HU_ID | `distribution/scan_HU_barcodes.spec.js` |
| Scan distribution HU by ExternalBarcode | `distribution/scan_HU_barcodes.spec.js` |

**7/7 — 100%**

### Distribution + Manufacturing cross-flow

| Scenario | Test |
|---|---|
| Distribute two components, then issue in manufacturing job, receive finished product | `distribution/distributionandmanufacturing.spec.js` |

**1/1 — 100%**

---

## Manufacturing

### Component issue — basic

| Scenario | Test |
|---|---|
| Full job: issue two BOM components by scanning HUs, receive finished product into new LU | `manufacturing/manufacturing.spec.js` |
| BOM line with IssueOnlyForReceived → Scan button hidden; manual line → Scan button shown | `manufacturing/auto-issue.spec.js` |
| isAllowIssuingAnyHU=false, no stock → error toast on job start | `manufacturing/isAllowIssuingAnyHU.spec.js` |
| isAllowIssuingAnyHU=false, stock present → job starts OK | `manufacturing/isAllowIssuingAnyHU.spec.js` |
| isAllowIssuingAnyHU=true, no stock → job starts OK | `manufacturing/isAllowIssuingAnyHU.spec.js` |
| isAllowIssuingAnyHU=true, stock present → job starts OK | `manufacturing/isAllowIssuingAnyHU.spec.js` |
| ❌ Scan wrong product HU during issue → error shown | — |
| ❌ Issue BOM line across more than one HU → remaining qty decrements correctly between scans | — |
| ❌ Complete job before all BOM lines fully issued → blocked or warned | — |
| ❌ Navigate back from issue screen without scanning → job state unchanged | — |

**6/10 — 60%**

### Component issue — HU qty suggestion capping

| Scenario | Test |
|---|---|
| ❌ HU qty < BOM remaining → suggestion capped at HU qty | — |
| ❌ After partial issue, HU qty < remaining BOM → suggestion still capped at HU qty | — |
| ❌ HU qty ≥ BOM remaining → suggestion unchanged (no capping) | — |
| ❌ Type old wrong (uncapped) suggestion → over-issue, inventory adjustment created | — |
| ❌ Confirm HU-capacity suggestion → no inventory adjustment created | — |

**0/5 — 0%**

### Component issue — qty tolerance

| Scenario | Test |
|---|---|
| BOM qty 0.00384 kg: scan HU → non-zero qty shown (regression) | `manufacturing/manufacturing_small_qty_tolerance.spec.js` |
| BOM qty 0.01913 kg, 1% tolerance: full issue-and-receive cycle → HU storage and remaining qty correct | `manufacturing/manufacturing_small_qty_tolerance.spec.js` |

**2/2 — 100%**

### Receipt — main product

| Scenario | Test |
|---|---|
| Receive 100 PCE into new LU → splits into two LUs (80 + 20 PCE) | `manufacturing/receiving_main_products.spec.js` |
| Receive to new TU, manual input (9 PCE + 0.9 kg catch weight) → splits into 3 TUs | `manufacturing/receiving_main_products_catchweight.spec.js` |
| Receive to new TU via three L+M QR codes → 3 PCE and accumulated WeightNet validated | `manufacturing/receiving_main_products_catchweight.spec.js` |
| Two orders contribute catch-weight main product into same existing TU → accumulated storage validated | `manufacturing/receiving_main_products_catchweight.spec.js` |
| Receive to new LU via two GTIN QR code scans → 2 PCE received | `manufacturing/receiving_main_products_catchweight.spec.js` |
| Receive using two custom QR code formats (catch-weight + lot + dates) → WeightNet and CU child records validated | `manufacturing/receive_using_customQRCodeFormat.spec.js` |

**6/6 — 100%**

### Receipt — by-products

| Scenario | Test |
|---|---|
| Two manufacturing orders contribute by-product qty into same target TU → accumulated storage validated | `manufacturing/receiving_by_products.spec.js` |
| Receive second by-product type into HU that already holds a different by-product → error toast | `manufacturing/receiving_by_products.spec.js` |
| By-product receipt via manual input (typed qty + catch weight) into new TU → qty indicator validated | `manufacturing/receiving_by_products_catchweight.spec.js` |
| By-product receipt via three L+M QR codes into new TU → storage validated | `manufacturing/receiving_by_products_catchweight.spec.js` |
| Two orders contribute catch-weight by-product (L+M codes) into same existing TU → accumulated weight validated | `manufacturing/receiving_by_products_catchweight.spec.js` |

**5/5 — 100%**

### Manufacturing — jobs list

| Scenario | Test |
|---|---|
| ❌ Filter/search jobs list by document number, date, or status | — |

**0/1 — 0%**

---

## HU Manager

### Scanning

| Scenario | Test |
|---|---|
| Scan HU by QR code → qty displayed | `humanager/scan.spec.js` |
| Scan HU by M_HU_ID → qty displayed | `humanager/scan.spec.js` |
| Scan HU by ExternalBarcode → qty displayed | `humanager/scan.spec.js` |

**3/3 — 100%**

### Actions on scanned HU (QR code)

| Scenario | Test |
|---|---|
| Action buttons appear in expected order | `humanager/huManager.spec.js` |
| Dispose HU → HU destroyed | `humanager/huManager.spec.js` |
| Move HU via locator code scan → locator updated | `humanager/huManager.spec.js` |
| Change HU qty → new qty stored | `humanager/huManager.spec.js` |
| Set clearance status and note → both values saved | `humanager/huManager.spec.js` |
| Bulk actions — move HU to different locator → locator updated | `humanager/huManager.spec.js` |
| ❌ Print Labels action | — |

**6/7 — 86%**

### Actions on scanned HU (ExternalBarcode)

| Scenario | Test |
|---|---|
| Scan by ExternalBarcode, dispose HU | `humanager/by_ExternalBarcode_attribute.spec.js` |
| Scan by ExternalBarcode, move HU via locator code | `humanager/by_ExternalBarcode_attribute.spec.js` |
| Scan by ExternalBarcode, change qty → new value verified | `humanager/by_ExternalBarcode_attribute.spec.js` |
| Scan by ExternalBarcode, set clearance status and note | `humanager/by_ExternalBarcode_attribute.spec.js` |
| Scan by ExternalBarcode, bulk actions — move to another locator | `humanager/by_ExternalBarcode_attribute.spec.js` |
| ❌ Scan generated HU QR code, change locator | — |

**5/6 — 83%**

---

## HU Consolidation

### Consolidation flows

| Scenario | Test |
|---|---|
| Consolidate all TUs onto new LU in one action → complete | `hu_consolidation/hu_consolidation.spec.js` |
| Consolidate individual TUs one by one onto new LU | `hu_consolidation/hu_consolidation.spec.js` |
| Manually print current target LU label mid-consolidation | `hu_consolidation/hu_consolidation.spec.js` |
| Consolidate picked TUs onto existing LU → combined storage validated | `hu_consolidation/hu_consolidation.spec.js` |
| ❌ setTargetLU fails (LU already holds different customer's goods) → error shown | — |

**4/5 — 80%**

---

## Inventory

### Inventory counting

| Scenario | Test |
|---|---|
| Count HU with adjusted qty and attributes (lot, best-before), complete → HU storage and attributes updated | `inventory/inventory.spec.js` |
| ❌ Inventory job with multiple products/lines | — |
| ❌ Complete job with a line where no physical HU was scanned → qty remains as booked | — |

**1/3 — 33%**
