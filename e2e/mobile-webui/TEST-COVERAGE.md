# Mobile WebUI — Test Coverage

> Update this file whenever a test is added, changed, deleted, or refactored.

## Summary

| Module | Covered | Total | % |
|---|---|---|---|
| Login / Home | 8 | 11 | 73% |
| Barcode Scanner Modes | 7 | 12 | 58% |
| Picking | 86 | 90 | 96% |
| Distribution | 41 | 41 | 93% |
| Manufacturing | 26 | 32 | 81% |
| HU Manager | 14 | 16 | 88% |
| HU Consolidation | 8 | 9 | 89% |
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
| ❌ Generated HU QR code scan from home screen → navigates correctly | — |

**4/5 — 80%**

---

## Barcode Scanner Modes

### HTML state of #input-text

| Scenario | Test |
|---|---|
| `type=text`, `inputmode=none`, `readonly` absent, CSS-hidden — DataWedge IME contract (visible-input editable, virtual keyboard suppressed via soft hint) | `barcode_scanner_modes.spec.js` |
| `type=text`, `inputmode=none`, `readOnly` present, CSS-hidden, no `<video>` — Honeywell CT60 / Android 11 keystroke-wedge contract (HARD keyboard suppression via `readOnly`; camera disabled) | `barcode_scanner_modes.spec.js` |

**2/2 — 100%**

### Scan paths

| Scenario | Test |
|---|---|
| Mode A — DataWedge IME: set input value + fire input/change + Enter keyup → barcode forwarded | `barcode_scanner_modes.spec.js` |
| Mode C1 — Keystroke scanner: keydown events on window, rate-based buffering → barcode forwarded | `barcode_scanner_modes.spec.js` |
| Mode C2 — Ctrl+V paste: clipboard mocked, keydown Ctrl+V on window → barcode forwarded | `barcode_scanner_modes.spec.js` |
| Mode C3 — Manual typing: fill visible editable input + Enter → barcode forwarded | `barcode_scanner_modes.spec.js` |
| ❌ Mode B — Camera (ZXing/BrowserMultiFormatReader): getUserMedia() decode → barcode forwarded | — (not testable in CI, requires real camera) |
| ❌ `scanDuplicatesIntervalMillis` — duplicate barcode within interval suppressed, outside interval forwarded | — |
| ❌ `triggerOnChangeIfLengthGreaterThan` — onChange fires only once input length exceeds threshold | — |
| Footer — hardware/camera toggle: renders only when both hw + camera enabled; clicking toggle switches to camera mode (camera panel shown; live `<video>` feed validation requires physical hardware) | `barcode_scanner_modes.spec.js` |
| ❌ Footer — "Enter manually": shows only when manual mode enabled and activeMode ≠ MANUAL; clicking sets activeMode to MANUAL | — |
| ❌ Footer — "Back to scanner": shows when activeMode=MANUAL and at least one of hw/camera enabled; clicking returns to HARDWARE (or CAMERA if only camera enabled) | — |

**5/10 — 50%** (Mode B excluded — untestable in Playwright CI; `scanDuplicatesIntervalMillis`, `triggerOnChangeIfLengthGreaterThan`, and two footer buttons not yet covered)

---

## Picking

### Order-based picking — core flow

| Scenario | Test |
|---|---|
| Full pick, single HU, confirm, shipment schedule marked picked | `picking/picking.spec.js` |
| Pick HU then unpick → HU returns to unallocated | `picking/picking.spec.js` |
| Partial unpack: scan product GTIN → qty dialog → scan target HU → chosen qty removed into target, rest stays packed; re-pick loop repeatable | `picking/picking_partial_unpack.spec.js` |
| Partial unpack: scan product not in the package → one error toast, nothing removed | `picking/picking_partial_unpack.spec.js` |
| Partial unpack: partial-unpick then complete the job → shipment carries the net packed qty in exactly one line, no negative counter-row | `picking/picking_partial_unpack.spec.js` |
| Partial unpack: remove item to the floor by canceling/skipping the target-HU scan → removed qty leaves the pick, rest stays packed | `picking/picking_partial_unpack.spec.js` |
| Partial unpack: transient network failure on submit → error toast, panel stays on SCAN_TARGET; retry succeeds, net qty moved into target HU | `picking/picking_partial_unpack.spec.js` |
| Partial unpack: mis-scan the product GTIN as the target HU → backend rejects (4xx), error toast, panel stays on SCAN_TARGET; scanning the correct target HU then commits | `picking/picking_partial_unpack.spec.js` |
| Scan invalid picking slot QR code → error shown | `picking/picking.spec.js` |
| Line status indicator transitions draft → in-progress → complete as HUs are picked | `picking/picking.spec.js` |
| Partial pick, allowCompletingPartialPickingJob = N → complete blocked | `picking/picking.spec.js` |
| Partial pick, allowCompletingPartialPickingJob = Y → complete succeeds | `picking/picking.spec.js` |
| Close LU during picking → shipment created automatically | `picking/picking.spec.js` |
| Close LU then reopen → state transitions verified | `picking/picking.spec.js` |
| Close LU (header-level, DELIVERY_LOCATION aggregation) → closed LU and its cascaded TU/CU carry the picking consignee (BPartner + location) | `picking/closeLU_stampsConsignee.spec.js` |
| Close LU (line-level, PRODUCT aggregation) → closed LU and its cascaded TU/CU carry the picking consignee (BPartner + location) | `picking/closeLU_stampsConsignee.spec.js` |
| Job already started → "already started" indicator shown in jobs list | `picking/picking.spec.js` |
| completeJobAutomatically=true, scan drop-to locator after pick → job auto-completed, removed from list | `picking/completeJobAutomatically.spec.js` |
| ❌ Scan HU from wrong warehouse/locator → error shown | — |

**18/19 — 95%**

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
| Pick All completes a job that already has a fully-picked line (no abort on the zero-remaining line) | `picking/pickAllButton.spec.js` |
| Pick All button hidden when feature disabled in mobile config | `picking/pickAllButton.spec.js` |
| Only one matching HU → picking proceeds without qty dialog | `picking/pickAttributes.spec.js` |

**4/4 — 100%**

### Order-based picking — serial-no scan

| Scenario | Test |
|---|---|
| Serial-no product → scan one serial per picked unit ("N of N"), confirm gated until N distinct serials, persisted comma-separated on the picked HU | `picking/picking_serialNo.spec.js` |
| Duplicate serial scan is silently deduped (count unchanged; must scan N distinct serials) | `picking/picking_serialNo.spec.js` |
| Misconfigured serial-no product (flag set, no serial-capable attribute set) → no prompt, picks directly | `picking/picking_serialNo.spec.js` |

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

### Order-based picking — mass printing

| Scenario | Test |
|---|---|
| Mass-printing scan of an LU → one box and one label per unit packed for open demand (DO_NOT_CREATE policy, no shipment) | `picking/massPrinting.spec.js` |
| Mass-printing scan of an LU → leftover units stay on the LU when demand is smaller than the LU | `picking/massPrinting.spec.js` |
| FIFO partial fill: LU capacity < total demand → earliest-date order(s) fully filled, latest order left short, open demand remaining | `picking/massPrinting.spec.js` |
| CREATE_AND_COMPLETE policy: scanning LU triggers packing and produces a completed (CO) shipment per order | `picking/massPrinting.spec.js` |
| CREATE_DRAFT policy: scanning LU triggers packing and produces a draft (DR) shipment per order | `picking/massPrinting.spec.js` |
| DO_NOT_CREATE policy: scanning LU packs boxes but produces no shipment | `picking/massPrinting.spec.js` |
| Mixed LU (self-packed only): product is packed, skipped-products section is absent | `picking/massPrinting.spec.js` |
| Non-self-packed-only LU: no boxes packed, skipped-products section is visible | `picking/massPrinting.spec.js` |
| Off-mode guard: mass-printing trigger button is absent when feature is disabled in picking profile | `picking/massPrinting.spec.js` |
| Null PackTo PI: self-packed product with no TU packing instruction packs as VHU (one label per unit, not a TU box) | `picking/massPrinting.spec.js` |
| Cross-warehouse: LU stored in one warehouse, demand + workplace in another warehouse of the same picking group → demand is found and packed (searches by the workplace warehouse, not the LU's storage warehouse) | `picking/massPrinting.spec.js` |
| LU outside the workplace's picking group → scan is rejected with an error, nothing packed | `picking/massPrinting.spec.js` |
| ❌ CREATE_COMPLETE_CLOSE policy: scanning LU packs + produces a completed shipment and closes the shipment schedule — but the schedule must be closed **only on a full pick**; a partially-picked schedule must stay **open** (closing it would discard the remaining open demand) | — |

**12/13 — 92%**

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
| Picking slot not required → slot-scan step absent, job pickable directly | `picking/pickingSlotRequired.spec.js` |
| Single sales order split and picked to multiple workplaces | `picking/pick_what_was_scheduled_to_workplace.spec.js` |
| DO_NOT_CREATE: fully-picked order completed with no shipment → must NOT appear in the picking launcher | `picking/picking_DO_NOT_CREATE_shipment_reappearance.spec.js` |
| DO_NOT_CREATE: partially-picked order (qty still open) → must STAY in the picking launcher | `picking/picking_DO_NOT_CREATE_shipment_reappearance.spec.js` |
| DO_NOT_CREATE: picked qty fully bound to a draft shipment → must NOT appear in the picking launcher | `picking/picking_DO_NOT_CREATE_shipment_reappearance.spec.js` |
| Reverse (void) an aggregate-HU shipment → recreate must not collide on duplicate QtyPicked rows | `picking/recreate_shipment_after_void.spec.js` |


**9/9 — 100%**

### Product-based picking

| Scenario | Test |
|---|---|
| Lines grouped by product instead of by order | `picking/productBasedPicking/standard.spec.js` |
| Filter jobs list by scanning product EAN13 | `picking/productBasedPicking/standard.spec.js` |
| Pick HUs not pre-assigned to a specific order line | `picking/productBasedPicking/standard.spec.js` |
| Pick-from HU identified by ExternalBarcode attribute | `picking/productBasedPicking/pick_by_ExternalBarcode.spec.js` |

**4/4 — 100%**

### GRAI-scan picking (product aggregation)

| Scenario | Test |
|---|---|
| Scan one GRAI → TU auto-created with GRAI attribute | `picking/picking-grai-scan.spec.js` |
| Scanned GRAI has no M_HU_PI_GRAI mapping → GRAINoMatchingTUType error | `picking/picking-grai-scan.spec.js` |
| Resolved TU not allowed on picking-target LU → GRAITUNotAllowedOnLU error | `picking/picking-grai-scan.spec.js` |
| Two distinct GRAIs before debounce → GRAIMultipleScanned error, no list | `picking/picking-grai-scan.spec.js` |
| Unparseable barcode → scanner ignores it, stays live for valid scan | `picking/picking-grai-scan.spec.js` |
| Resolved TU has no capacity for product → GRAINoCapacityForProduct error | `picking/picking-grai-scan.spec.js` |
| BPartner GRAIRequired=No → no GRAI scanner shown | `picking/picking-grai-scan.spec.js` |
| Scan one GRAI into a top-level TU (no LU) → GRAI stamped on the top-level TU and persists through complete | `picking/picking-grai-scan.spec.js` |

**8/8 — 100%**

### Inline GRAI capture in Flow Through (LU_TU) picking

| Scenario | Test |
|---|---|
| Pick 10 crates onto one LU; confirming the quantity auto-invokes the inline GRAI capture; capture all 10 GRAIs (one typed via manual entry, the rest scanned) → save enabled, the atomic pick is sent and the job completes | `picking/picking-grai-flowthrough.spec.js` |
| Pick 10 crates onto one LU; capture fewer than 10 GRAIs in the inline capture → save stays disabled (and the backend completion guard blocks completing with a GRAI-less crate) | `picking/picking-grai-flowthrough.spec.js` |
| Pick two products onto one shared LU; each pick auto-invokes its own inline GRAI capture for that pick's crates (an RFID re-read of a crate within the burst is deduped) → each product's VHU carries exactly its own GRAIs and the job completes | `picking/picking-grai-flowthrough-mixed-product.spec.js` |
| "OK und LU schließen" still demands one GRAI per picked crate → GRAIs stamped, LU closed, job completes | `picking/picking-grai-flowthrough.spec.js` |

**4/4 — 100%**

### Navigation — device/browser Back

| Scenario | Test |
|---|---|
| Device/browser Back is a pure no-op: pressing it does nothing (screen unchanged, operator never leaves the PWA); only the footer Back navigates | `picking/deviceBackIsNoOp.spec.js` |
| Rapidly mashing device/browser Back many times stays put, never leaves the app, and the title bar does not revert to the app caption | `picking/deviceBackIsNoOp.spec.js` |

**2/2 — 100%**

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
| IsPackingPlace=Y workplace → only DD orders targeting its pick-from locator; IsPackingPlace=N workplace → only DD orders NOT targeting any packing-place locator | `distribution/filter_by_packingplace.spec.js` |
| Sort by SeqNo when orderBys=SeqNo,Priority,DatePromised | `distribution/sorting.spec.js` |
| Sort by locator priority when orderBys=LocatorPriority → lower FROM-locator PriorityNo first | `distribution/sort_by_locator_priority.spec.js` |
| ❌ maxLaunchers cap — list truncated beyond N jobs | — |
| ❌ maxStartedLaunchers cap | — |

**8/10 — 80%**

### Distribution — job execution

| Scenario | Test |
|---|---|
| Job-detail header renders the configured profile caption items (From Locator, To Locator, Product Value and Name) | `distribution/header.spec.js` |
| Pick multiple HUs; Drop All delivers in one action; warehouse validated per step | `distribution/job_dropAllButton.spec.js` |
| Pick multiple HUs by M_HU_ID; Drop All via locator code | `distribution/job_dropAllButton.spec.js` |
| Pick from multiple jobs in launchers list; Drop All from jobs-list screen | `distribution/launchers_dropAllButton.spec.js` |
| navigateToJobsListAfterPickFromComplete=true → last line pick navigates to next job | `distribution/navigateToJobsListAfterPickFromComplete.spec.js` |
| Packing-table operator: orders offered sorted by priority then locator priority; pick + scan auto-advances order→order through the run | `distribution/packingTable_navigateToNextOrder.spec.js` |
| "Lagerort leer" button advances the job's pick-from locator to the next active locator | `distribution/switchPickFromLocator.spec.js` |
| "Lagerort leer" successive presses cycle round-robin through all active locators | `distribution/switchPickFromLocator.spec.js` |
| "Lagerort leer" button stays visible after picking has started (mid-job switch supported) | `distribution/switchPickFromLocator.spec.js` |
| "Lagerort leer" — after round-robin wrap, pick HU + drop completes end-to-end | `distribution/switchPickFromLocator.spec.js` |
| "Lagerort leer" — after switch, scanning an HU from the original locator is rejected with "HU is not at the target trolley" | `distribution/switchPickFromLocator.spec.js` |
| "Lagerort leer" — fulfill one line from two locators: pick from A, switch mid-job, pick from B; pick-from dialog proposes the scanned HU's qty (HU-limited then remaining-limited) | `distribution/switchPickFromLocator.spec.js` |
| "Lagerort leer" — ground-locator mode: skips non-ground and no-stock locators, respects priorityNo order, cycles round-robin | `distribution/switchPickFromLocator_groundLocator.spec.js` |
| "Lagerort leer" — ground-locator mode (AC6): no eligible ground alternative → no-alternative toast, pick-from unchanged | `distribution/switchPickFromLocator_groundLocator_noAlternative.spec.js` |

**14/14 — 100%**

### Distribution — HU scanning

| Scenario | Test |
|---|---|
| GTIN-validation mode: scan HU by QR code, confirm with product GTIN | `distribution/pickFrom_validate_GTIN.spec.js` |
| GTIN-validation mode: scan HU by M_HU_ID, confirm with product GTIN | `distribution/pickFrom_validate_GTIN.spec.js` |
| GTIN-validation mode: scan HU by ExternalBarcode, confirm with product GTIN | `distribution/pickFrom_validate_GTIN.spec.js` |
| Only 1 matching unit → qty dialog skipped | `distribution/pickFrom_validate_GTIN.spec.js` |
| GTIN-validation mode: scan unknown product code → "no product found" error, line stays unpicked | `distribution/pickFrom_validate_GTIN.spec.js` |
| Scan distribution HU by QR code | `distribution/scan_HU_barcodes.spec.js` |
| Scan distribution HU by M_HU_ID | `distribution/scan_HU_barcodes.spec.js` |
| Scan distribution HU by ExternalBarcode | `distribution/scan_HU_barcodes.spec.js` |

**8/8 — 100%**

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
| HU qty drops after plan creation → suggestion capped at live HU qty | `manufacturing/issue_hu_qty_suggestion.spec.js` |
| ❌ After partial issue of PP1, HU qty < remaining BOM → suggestion still capped at HU qty | — |
| HU qty ≥ BOM remaining → suggestion unchanged (no capping) | `manufacturing/issue_hu_qty_suggestion.spec.js` |
| Type qty > HU capacity → over-issue accepted, inventory adjustment created | `manufacturing/issue_hu_qty_suggestion.spec.js` |
| Confirm HU-capacity suggestion → HU depleted, no inventory adjustment created | `manufacturing/issue_hu_qty_suggestion.spec.js` |

**4/5 — 80%**

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

### Receipt — editable Lot / Best-Before

| Scenario | Test |
|---|---|
| Receive finished goods entering Lot + Best-Before (inputs shown by default) → produced HU carries both attributes | `manufacturing/receiving_editable_attributes.spec.js` |
| Receive finished goods leaving Lot + Best-Before empty → produced HU gets no such attribute | `manufacturing/receiving_editable_attributes.spec.js` |

**2/2 — 100%**

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

### GRAI-scan TU selection

| Scenario | Test |
|---|---|
| Scan each picked TU's GRAI on the picking slot → both TUs consolidated onto the target LU, slot emptied | `hu_consolidation/hu_consolidation_grai.spec.js` |
| Scan an unknown GRAI → "No HU found" error, nothing consolidated | `hu_consolidation/hu_consolidation_grai.spec.js` |
| Scan the GRAI of a TU sitting in a different picking slot → "HU not at picking slot" error | `hu_consolidation/hu_consolidation_grai_cross_slot.spec.js` |
| Scan a garbage / non-GRAI barcode → rejected ("No HU found"), nothing consolidated | `hu_consolidation/hu_consolidation_grai.spec.js` |

**4/4 — 100%**

---

## Inventory

### Inventory counting

| Scenario | Test |
|---|---|
| Count HU with adjusted qty and attributes (lot, best-before), complete → HU storage and attributes updated | `inventory/inventory.spec.js` |
| ❌ Inventory job with multiple products/lines | — |
| ❌ Complete job with a line where no physical HU was scanned → qty remains as booked | — |

**1/3 — 33%**
