# Mobile WebUI — E2E Test Coverage

> Update this file whenever a test is added or removed in any mobile UI workflow.

## Section 1 — Test catalog

### Login / Home

| Spec file | Test name | Scenario |
|---|---|---|
| `login.spec.js` | By user/pass, using en_US language, UserPass as default auth method | Login with username/password using en\_US language and UserPass auth method; verifies language, then logs out |
| `login.spec.js` | By user/pass, using en_US language, QR_Code as default auth method | Login with username/password using en\_US language and QR\_Code as default auth method; verifies language, then logs out |
| `login.spec.js` | By user/pass, using de_DE language, UserPass as default auth method | Login with username/password using de\_DE language and UserPass auth method; verifies language, then logs out |
| `login.spec.js` | By user/pass, using de_DE language, QR_Code as default auth method | Login with username/password using de\_DE language and QR\_Code as default auth method; verifies language, then logs out |
| `home_screen.spec.js` | Scan HU QR Code | Scanning an HU QR code from the home/applications screen navigates directly to HU Manager and shows qty |
| `home_screen.spec.js` | Scan HU ID Code | Scanning an HU ID (M\_HU\_ID) from the home screen navigates to HU Manager and shows qty |
| `home_screen.spec.js` | Scan ExternalBarcode | Scanning an external barcode from the home screen navigates to HU Manager and shows qty |
| `home_screen.spec.js` | Scan Workplace code | Scanning a workplace QR code from the home screen opens the Workplace Manager screen and shows the workplace name and assigned status |

### Picking

| Spec file | Test name | Scenario |
|---|---|---|
| `picking/picking.spec.js` | Simple picking test | Full happy-path picking: start job, scan picking slot, pick HU, complete job, verify shipment schedule marked as picked |
| `picking/picking.spec.js` | Pick - unpick | Pick an HU then unpick it via the step screen; verifies HU returns to unallocated state |
| `picking/picking.spec.js` | Scan invalid picking slot QR code | Scanning a barcode that does not match any picking slot triggers an error |
| `picking/picking.spec.js` | Test picking line complete status - draft \| in progress \| complete | Verifies that line status indicator transitions through draft → in-progress → complete as HUs are picked |
| `picking/picking.spec.js` | Should fail when partial picking and allowCompletingPartialPickingJob = N | Attempting to complete a job before all lines are fully picked is blocked when the config flag is N |
| `picking/picking.spec.js` | Should succeed when partial picking and allowCompletingPartialPickingJob = Y | Completing a job with partially picked lines succeeds when the config flag is Y |
| `picking/picking.spec.js` | Ship on close LU | Closing a LU during picking automatically creates a shipment |
| `picking/picking.spec.js` | Close LU / Reopen LU | A closed LU can be reopened; verifies state transitions |
| `picking/picking.spec.js` | Check launcher already started indicator | A job that has already been started shows an "already started" indicator in the jobs list |
| `picking/completeJobAutomatically.spec.js` | Happy case | With `completeJobAutomatically=true`, scanning the drop-to locator after picking completes the job automatically and removes it from the list |
| `picking/facets.spec.js` | Check facets when only scheduled for workplace is enabled | Facet filter shows only jobs scheduled for the user's current workplace |
| `picking/filterByQtyAvailableAtLocator.spec.js` | Filter by Qty Available flag | Jobs list can be filtered to show only jobs where the required qty is available at the pick-from locator |
| `picking/pickAllButton.spec.js` | Pick using Pick All button | The "Pick All" button picks all remaining HUs in a single action |
| `picking/pickAllButton.spec.js` | Expect Pick All button hidden when feature is not active | The "Pick All" button is not visible when the feature is disabled in mobile config |
| `picking/pickAttributes.spec.js` | Expect picking directly without dialog | When only one matching HU exists, picking proceeds without showing a quantity dialog |
| `picking/pick_and_assemble.spec.js` | Assemble/Manufacture while picking test | Pick triggers an on-the-fly manufacturing (assemble) step; verifies assembled HU is picked into the order |
| `picking/pick_by_customQRCode.spec.js` | Pick using custom QR code | HU is identified and picked by scanning a customer-defined custom QR code format |
| `picking/pick_by_EAN13.spec.js` | LU/CU -> top level TU | Pick from an LU/CU source into a top-level TU target using EAN13 barcode |
| `picking/pick_by_EAN13.spec.js` | LU/CU -> LU/TU1, LU/TU2 | Pick from an LU/CU source into two LU/TU targets using EAN13 barcode |
| `picking/pick_by_EAN13.spec.js` | LU/CU -> LU/CU | Pick from an LU/CU source into an LU/CU target using EAN13 barcode |
| `picking/pick_by_EAN13.spec.js` | LU/CU -> top level CUs | Pick from an LU/CU source into top-level CU targets using EAN13 barcode |
| `picking/pick_by_ExternalBarcode.spec.js` | Pick by scanning ExternalBarcode attribute | HU is identified and picked by scanning its ExternalBarcode attribute value |
| `picking/pick_by_HUId.spec.js` | LU/CU -> LU/CU | HU is identified and picked by scanning its internal M\_HU\_ID instead of a QR code |
| `picking/pick_from_LUs.spec.js` | Pick less than a LU because ordered qty is less than an LU | Picking from an LU when ordered qty is smaller than the full LU; a partial pick is created |
| `picking/pick_from_LUs.spec.js` | Pick entire LU which is exactly the qty that was ordered | Picking an entire LU whose qty exactly matches the ordered qty |
| `picking/pick_from_LUs.spec.js` | Pick entire LU but less then ordered | Picking an entire LU when the LU qty is smaller than the ordered qty (partial fulfillment) |
| `picking/picking_catchWeight.spec.js` | Manual | Catch-weight picking via manual typed input; verifies pick qty and weight recorded |
| `picking/picking_catchWeight.spec.js` | Leich+Mehl | Catch-weight picking via L+M QR code scan |
| `picking/picking_catchWeight.spec.js` | Leich+Mehl - invalid code | Scanning an invalid L+M code produces an error |
| `picking/picking_catchWeight.spec.js` | GS1 | Catch-weight picking via GS1 barcode scan |
| `picking/picking_catchWeight.spec.js` | EAN13 with prefix 28 | Catch-weight picking via EAN13 barcode with prefix 28 |
| `picking/picking_catchWeight.spec.js` | EAN13 with prefix 28 and not matching product | EAN13 prefix-28 barcode for wrong product triggers an error |
| `picking/picking_catchWeight.spec.js` | EAN13 with prefix 29 | Catch-weight picking via EAN13 barcode with prefix 29 |
| `picking/picking_catchWeight.spec.js` | EAN13 with prefix 29 and not matching product | EAN13 prefix-29 barcode for wrong product triggers an error |
| `picking/picking_catchWeight.spec.js` | Custom QR code format | Catch-weight picking via a configured custom QR code format |
| `picking/picking_catchWeight.spec.js` | Check Last BestBeforeDate is displayed when MobileUIPickingProfile.ShowLastPickedBestBeforeDateForLines = Y | Last picked best-before date is shown on the picking line when the profile flag is enabled |
| `picking/picking_deliveryLocationBasedAggregation.spec.js` | Delivery Location based aggregation | Picking lines are aggregated and grouped by delivery location |
| `picking/pickingSlotSuggestions.spec.js` | Test NO picking slot suggestions | When no suggestions are configured, no suggested picking slots are shown |
| `picking/pickingSlotSuggestions.spec.js` | Test picking slot suggestions | Configured picking slot suggestions are shown and selectable |
| `picking/pick_what_was_scheduled_to_workplace.spec.js` | Pick one sales order to different workplaces | A single sales order can be split and picked to multiple workplaces |
| `picking/productBasedPicking/standard.spec.js` | Product based aggregation | Product-based picking: lines are grouped by product instead of by order |
| `picking/productBasedPicking/standard.spec.js` | Filter by EAN13 | Product-based picking list can be filtered by scanning a product EAN13 barcode |
| `picking/productBasedPicking/standard.spec.js` | Anonymous pick HUs on the fly | Pick HUs that are not pre-assigned to a specific order line |
| `picking/productBasedPicking/pick_by_ExternalBarcode.spec.js` | Scan the pick from HU by ExternalBarcode | In product-based picking mode, the pick-from HU is identified by scanning its ExternalBarcode attribute |

### Distribution

| Spec file | Test name | Scenario |
|---|---|---|
| `distribution/distribution.spec.js` | Simple distribution test | Full happy-path distribution: pick HU from source locator, scan drop-to locator, complete job |
| `distribution/distribution.spec.js` | Try picking an HU from a different locator than pick from locator | Scanning an HU from a locator that does not match the order's pick-from locator shows an error |
| `distribution/distribution.spec.js` | Try picking an HU containing a different product than expected | Scanning an HU containing the wrong product shows an error |
| `distribution/distribution.spec.js` | Distribution using 2 steps to pick the needed qty. | Distribution job requiring two separate HU picks to fulfil a single line qty |
| `distribution/distribution.spec.js` | Pick & Unpick in distribution step screen | Picking an HU then unpicking it in the step screen leaves the line in unallocated state |
| `distribution/distribution.spec.js` | Filter distribution orders by plantId | Distribution job list can be filtered by plant facet |
| `distribution/caption.spec.js` | LocatorFrom,ProductValueAndName,ProductGTIN,Qty,Priority | Job launcher caption is correctly formatted with multiple fields (locator, product, GTIN, qty, priority) |
| `distribution/completeJobAutomatically.spec.js` | Happy case | With `completeJobAutomatically=true`, scanning the drop-to locator completes the job automatically |
| `distribution/distributionandmanufacturing.spec.js` | Distribution and manufacturing test | End-to-end scenario: distribute two components to a warehouse, then issue them in a manufacturing job and receive finished product |
| `distribution/filter_by_workplace.spec.js` | Show all jobs when no current workplace | Without a workplace set, all distribution jobs are visible |
| `distribution/filter_by_workplace.spec.js` | Show only jobs suitable for workplace1 | Only jobs whose drop-to locator matches the current workplace's locator are shown |
| `distribution/header.spec.js` | Happy case | Job screen header shows correct From Locator and Locator To values |
| `distribution/job_dropAllButton.spec.js` | Pick multiple HUs and drop them all together in one step | Pick three lines across multiple HUs; use Drop All to deliver them in a single action; validates HU warehouse at each step |
| `distribution/job_dropAllButton.spec.js` | Pick multiple HUs (by HU code) and drop them all together in one step (using locator code) | Same as above but HUs are identified by M\_HU\_ID and the drop-to locator is scanned by locator code |
| `distribution/lauchers_restrictions.spec.js` | No restrictions | With no launcher restrictions configured, all jobs are enabled |
| `distribution/lauchers_restrictions.spec.js` | Allow starting next job only | With `allowStartNextJobOnly=true`, only the first unstarted job is enabled; starting it enables the next |
| `distribution/launchers_dropAllButton.spec.js` | Pick multiple HUs (by HU code) and drop them all together in one step (using locator code) | Pick from multiple jobs across the launchers list; use Drop All from the jobs-list screen to deliver all picked HUs at once |
| `distribution/launchers_websockets.spec.js` | Check launchers are refreshed via websockets | New distribution orders created on the backend appear in the launchers list in real time via websockets |
| `distribution/navigateToJobsListAfterPickFromComplete.spec.js` | Happy case | With `navigateToJobsListAfterPickFromComplete=true`, completing a pick-from step on the last line of a job navigates to the next job's pick-from screen (or back to the list if no more jobs) |
| `distribution/pickFrom_validate_GTIN.spec.js` | Scan the HU by QRCode | In GTIN-validation mode, scan HU by QR code then confirm with product GTIN |
| `distribution/pickFrom_validate_GTIN.spec.js` | Scan the HU by Value/M_HU_ID | In GTIN-validation mode, scan HU by M\_HU\_ID then confirm with product GTIN |
| `distribution/pickFrom_validate_GTIN.spec.js` | Scan the HU by External Attribute | In GTIN-validation mode, scan HU by ExternalBarcode then confirm with product GTIN |
| `distribution/pickFrom_validate_GTIN.spec.js` | Do not ask for picked qty when it is one | When only 1 unit matches, the qty dialog is skipped |
| `distribution/scan_HU_barcodes.spec.js` | Scan by HU QR Code | Distribution line HU can be scanned by its QR code |
| `distribution/scan_HU_barcodes.spec.js` | Scan by M_HU_ID | Distribution line HU can be scanned by its M\_HU\_ID |
| `distribution/scan_HU_barcodes.spec.js` | Scan by ExternalBarcode | Distribution line HU can be scanned by its ExternalBarcode attribute |
| `distribution/sorting.spec.js` | Sort by SeqNo | Distribution jobs list is sorted by SeqNo when `orderBys=SeqNo,Priority,DatePromised` |

### Manufacturing

| Spec file | Test name | Scenario |
|---|---|---|
| `manufacturing/manufacturing.spec.js` | Simple manufacturing test | Full end-to-end manufacturing job: issue two raw material components by scanning HUs, receive finished product into a new LU; validates received HU status and storage |
| `manufacturing/auto-issue.spec.js` | Auto-issue first line hides Scan button; manual second line shows it | BOM line with `IssueOnlyForReceived` must not show Scan button; manually-issued line must show it |
| `manufacturing/isAllowIssuingAnyHU.spec.js` | isAllowIssuingAnyHU=false, isCreateRawMaterialsStock=false => expect ERROR | `isAllowIssuingAnyHU=false` with no stock causes an error toast when starting a manufacturing job |
| `manufacturing/isAllowIssuingAnyHU.spec.js` | isAllowIssuingAnyHU=false, isCreateRawMaterialsStock=true => expect OK | `isAllowIssuingAnyHU=false` with stock present allows starting a job |
| `manufacturing/isAllowIssuingAnyHU.spec.js` | isAllowIssuingAnyHU=true, isCreateRawMaterialsStock=false => expect OK | `isAllowIssuingAnyHU=true` allows starting a job even with no stock |
| `manufacturing/isAllowIssuingAnyHU.spec.js` | isAllowIssuingAnyHU=true, isCreateRawMaterialsStock=true => expect OK | `isAllowIssuingAnyHU=true` with stock present allows starting a job |
| `manufacturing/manufacturing_small_qty_tolerance.spec.js` | Issue raw material with very small qty (0.00384 kg) shows non-zero qty after scan | Scanning an HU for a BOM line with a very small qty (0.00384 kg) must display a non-zero pre-filled qty (regression) |
| `manufacturing/manufacturing_small_qty_tolerance.spec.js` | Issue raw material with small qty (0.01913 kg) and 1% tolerance | Full issue-and-receive cycle for a BOM with 0.01913 kg and 1% issuing tolerance; validates final HU storage and remaining quantity |
| `manufacturing/receive_using_customQRCodeFormat.spec.js` | Receive using custom QR Code format | Receipt of finished product using two different custom QR code formats (catch-weight + lot + dates); validates HU storage, WeightNet, and CU child records |
| `manufacturing/receiving_by_products.spec.js` | Receive By-Products from 2 manufacturing orders into same HU | Two manufacturing orders contribute by-product qty into the same target TU; validates accumulated HU storage |
| `manufacturing/receiving_by_products.spec.js` | Fail receiving different By-Products in same HU | Attempting to receive a second by-product type into an HU that already holds a different by-product type must produce an error toast |
| `manufacturing/receiving_by_products_catchweight.spec.js` | Receive several by-products using manual input to a new TU | By-product receipt in manual-input mode (typed qty + catch weight) into a new TU; validates qty indicator |
| `manufacturing/receiving_by_products_catchweight.spec.js` | Receive several by-products using L+M codes to a new TU | By-product receipt via three L+M QR codes into a new TU; validates storage after receipt |
| `manufacturing/receiving_by_products_catchweight.spec.js` | Receive By-Products from 2 manufacturing orders into same HU | Two manufacturing orders contribute catch-weight by-product qty (L+M codes) into the same existing TU; validates accumulated weight |
| `manufacturing/receiving_main_products.spec.js` | Receive 1 full LU, 1 half LU | Receipt of 100 PCE finished product into a new LU that splits into two LUs (80 + 20 PCE); validates both received LUs |
| `manufacturing/receiving_main_products_catchweight.spec.js` | To a new TU, manual input | Main product receipt in manual-input mode (9 PCE + 0.9 kg catch weight) into a new TU; validates split into 3 TUs |
| `manufacturing/receiving_main_products_catchweight.spec.js` | To a new TU, scanning L+M QR codes | Main product receipt via three L+M QR codes into a new TU; validates 3 PCE and accumulated WeightNet |
| `manufacturing/receiving_main_products_catchweight.spec.js` | To a new TU, scanning L+M QR codes from 2 manufacturing orders | Two orders contribute catch-weight main product receipts into the same existing TU; validates accumulated storage |
| `manufacturing/receiving_main_products_catchweight.spec.js` | TO a new LU, scanning 2 x GTIN codes | Main product receipt via two GTIN QR code scans into a new LU; validates 2 PCE received |

### HU Manager

| Spec file | Test name | Scenario |
|---|---|---|
| `humanager/scan.spec.js` | Scan by HU QR Code | Open HU Manager and scan HU by QR code; verifies qty is displayed |
| `humanager/scan.spec.js` | Scan by M_HU_ID | Open HU Manager and scan HU by M\_HU\_ID; verifies qty is displayed |
| `humanager/scan.spec.js` | Scan by ExternalBarcode attribute | Open HU Manager and scan HU by ExternalBarcode; verifies qty is displayed |
| `humanager/huManager.spec.js` | Check action buttons order | Verifies the action buttons appear in the expected order after scanning an HU |
| `humanager/huManager.spec.js` | Dispose HU | Dispose an HU via the HU Manager; verifies HU is destroyed |
| `humanager/huManager.spec.js` | Move HU using locator code | Move an HU to a new locator by scanning the locator code; verifies locator updated |
| `humanager/huManager.spec.js` | Change Qty | Change the qty of an HU; verifies new qty is stored |
| `humanager/huManager.spec.js` | Change Clearance Status | Set a clearance status and note on an HU; verifies both values are saved |
| `humanager/huManager.spec.js` | Bulk actions - Move | Use bulk actions to move an HU to a different locator; verifies locator updated |
| `humanager/by_ExternalBarcode_attribute.spec.js` | Dispose HU | Scan HU by ExternalBarcode (no QR code generated); dispose the HU |
| `humanager/by_ExternalBarcode_attribute.spec.js` | Move HU using locator code | Scan HU by ExternalBarcode; move it to another warehouse by locator code |
| `humanager/by_ExternalBarcode_attribute.spec.js` | Change Qty | Scan HU by ExternalBarcode; change qty and verify new value |
| `humanager/by_ExternalBarcode_attribute.spec.js` | Change Clearance Status | Scan HU by ExternalBarcode; set clearance status and note |
| `humanager/by_ExternalBarcode_attribute.spec.js` | Bulk actions - Move | Scan HU by ExternalBarcode; use bulk actions to move to another locator |

### HU Consolidation

| Spec file | Test name | Scenario |
|---|---|---|
| `hu_consolidation/hu_consolidation.spec.js` | Simple HU consolidate all test | Pick HUs into a picking slot, then use HU Consolidation to consolidate all TUs onto a new LU and complete |
| `hu_consolidation/hu_consolidation.spec.js` | Simple HU consolidate HUs one by one test | Pick HUs into a picking slot, then consolidate individual TUs one by one onto a new LU |
| `hu_consolidation/hu_consolidation.spec.js` | Manual print current target label | Consolidate some HUs partially, then manually print the current target LU label |
| `hu_consolidation/hu_consolidation.spec.js` | Consolidate to an existing LU | Consolidate picked TUs onto an existing LU (rather than a new one); validates combined storage |

### Inventory

| Spec file | Test name | Scenario |
|---|---|---|
| `inventory/inventory.spec.js` | Simple inventory test | Start an inventory job, count an HU with adjusted qty and attributes (lot, best-before), complete; validates HU storage and attributes are updated |

---

## Section 2 — Gap analysis

By workflow area, paths that have **no** Playwright test or known weak coverage:

**Login / Home**
- No test covers failed login (wrong password, inactive user).
- No test covers the QR-code-based login flow end-to-end (only auth method preference is checked in `login.spec.js`).

**Picking**
- No test covers scanning an HU from the wrong warehouse/locator in the standard (non-distribution) picking flow.
- No test covers multiple picking jobs visible simultaneously for the same customer and verifies the aggregation count.
- No test covers the "Scan Anything" generic routing when the scanned code is ambiguous (resolves to more than one target).

**Distribution**
- No test covers the `maxLaunchers` config cap (list is truncated beyond N jobs).
- No test covers the `maxStartedLaunchers` config cap.
- No test covers abort (cancel) of an in-progress distribution job.

**Manufacturing**
- No test exercises search/filter on the manufacturing jobs list (filter by document number, date, or status via facets).
- `manufacturing.spec.js` does not call `Backend.expect` to verify issued component quantities in the backend after issue (only the received HU is validated).
- No test covers issuing a BOM line across more than one HU and verifying remaining qty decrements correctly between scans.
- No test covers scanning the wrong HU product (zero-stock or product mismatch) during issue.
- No test covers completing a job before all BOM lines are fully issued.
- No test covers navigating back from `RawMaterialIssueLineScreen` without scanning and verifying the job state is unchanged.
- `isAllowIssuingAnyHU.spec.js` only tests the job start step; no test scans an HU from a different product when `isAllowIssuingAnyHU=true` to verify the full issue path.
- No test covers AC3–AC5 from https://github.com/metasfresh/me03/issues/27759 (regression: suggestion when HU capacity exceeds remaining BOM qty; over-issue creates inventory adjustment; confirming HU-capacity suggestion creates no adjustment). These are reserved for the remaining tasks in that issue.

**HU Manager**
- No test covers the "Print Labels" action.
- No test covers scanning a generated HU QR code (the commented-out test `Change Locator of a generated HU QR Code` was broken and skipped).

**HU Consolidation**
- No test covers the case where `setTargetLU` fails (e.g., scanning an LU that already contains a different customer's goods).

**Inventory**
- No test covers an inventory job with multiple products/lines.
- No test covers completing an inventory job that has a line with no physical HU scanned (qty remains as booked).

---

## Section 3 — Tests added by issue https://github.com/metasfresh/me03/issues/27759

These 5 tests are planned for `tests/spec/manufacturing/issue_hu_qty_suggestion.spec.js`.
Task 4 will write the complete 5-test file (replacing an existing 2-test draft). AC3–AC5 are new; AC1 and AC2 are rewrites of the draft.

| Test name | Scenario | AC covered | Status |
|---|---|---|---|
| AC1: Suggested qty capped at HU capacity — HU smaller than BOM requirement | BOM 20, HU 5, nothing issued → suggestion = 5 | AC1 | Draft (to be replaced) |
| AC2: Suggested qty capped at HU capacity after partial issue (customer scenario) | BOM 20, 7 issued, HU 5 → suggestion = 5 | AC2 | Draft (to be replaced) |
| AC3 (regression): Suggestion unchanged when HU capacity exceeds remaining BOM qty | BOM 20, HU 100 → suggestion = 20 | AC3 | New |
| AC4 (regression): Typing the old wrong suggestion (13) over-issues by 8 — inventory adjustment created | BOM 20, HU 5, type 13 → 13 issued, HU depleted, adj 8 | AC4 | New |
| AC5 (regression): Confirming HU-capacity suggestion creates no inventory adjustment | BOM 20, HU 5, confirm 5 → 5 issued, HU depleted, no adj | AC5 | New |
