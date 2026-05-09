# Mobile WebUI — Manufacturing E2E Test Coverage

> Update this file whenever a manufacturing test is added or removed.

## Section 1 — Existing manufacturing test catalog

| Spec file | Test name | Scenario | ACs covered |
|---|---|---|---|
| `manufacturing.spec.js` | Simple manufacturing test | Full end-to-end manufacturing job: issue two raw material components (COMP1, COMP2) by scanning HUs, then receive finished product into a new LU; validates received HU status and storage | — |
| `auto-issue.spec.js` | Auto-issue first line hides Scan button; manual second line shows it | BOM line with `IssueOnlyForReceived` (auto-issue method) must not show Scan button in the mobile UI; manually-issued line must show it | — |
| `isAllowIssuingAnyHU.spec.js` | `isAllowIssuingAnyHU=false, isCreateRawMaterialsStock=false => expect ERROR` | Mobile config `isAllowIssuingAnyHU=false` with no stock in warehouse causes an error toast when starting a manufacturing job | — |
| `isAllowIssuingAnyHU.spec.js` | `isAllowIssuingAnyHU=false, isCreateRawMaterialsStock=true => expect OK` | Mobile config `isAllowIssuingAnyHU=false` with stock present allows starting a manufacturing job | — |
| `isAllowIssuingAnyHU.spec.js` | `isAllowIssuingAnyHU=true, isCreateRawMaterialsStock=false => expect OK` | Mobile config `isAllowIssuingAnyHU=true` allows starting a job even with no stock | — |
| `isAllowIssuingAnyHU.spec.js` | `isAllowIssuingAnyHU=true, isCreateRawMaterialsStock=true => expect OK` | Mobile config `isAllowIssuingAnyHU=true` with stock present allows starting a manufacturing job | — |
| `manufacturing_small_qty_tolerance.spec.js` | Issue raw material with very small qty (0.00384 kg) shows non-zero qty after scan | Scanning an HU for a BOM line with a very small qty (0.00384 kg) must display a non-zero pre-filled qty in the dialog (regression for https://github.com/metasfresh/tf201/issues/242) | — |
| `manufacturing_small_qty_tolerance.spec.js` | Issue raw material with small qty (0.01913 kg) and 1% tolerance | Full issue-and-receive cycle for a BOM with 0.01913 kg and 1% issuing tolerance; validates final HU storage and remaining quantity after partial issue | — |
| `receive_using_customQRCodeFormat.spec.js` | Receive using custom QR Code format | Receipt of finished product using two different custom QR code formats (catch-weight + lot + dates); validates HU storage, WeightNet attribute, and CU child records | — |
| `receiving_by_products.spec.js` | Receive By-Products from 2 manufacturing orders into same HU | Two manufacturing orders both contribute by-product qty into the same target TU (no catch-weight); validates HU storage accumulates across orders | — |
| `receiving_by_products.spec.js` | Fail receiving different By-Products in same HU | Attempting to receive a second by-product type into an HU that already holds a different by-product type must produce an error toast | — |
| `receiving_by_products_catchweight.spec.js` | Receive several by-products using manual input to a new TU | By-product receipt in manual-input mode (typed qty + catch weight) into a new TU; validates qty indicator on job screen | — |
| `receiving_by_products_catchweight.spec.js` | Receive several by-products using L+M codes to a new TU | By-product receipt via three L+M QR codes into a new TU; validates storage after receipt | — |
| `receiving_by_products_catchweight.spec.js` | Receive By-Products from 2 manufacturing orders into same HU | Two manufacturing orders contribute catch-weight by-product qty (via L+M codes) into the same existing TU; validates accumulated HU weight | — |
| `receiving_main_products.spec.js` | Receive 1 full LU, 1 half LU | Receipt of 100 PCE finished product into a new LU that splits into two LUs (80 + 20 PCE based on packing instructions); validates both received LUs | — |
| `receiving_main_products_catchweight.spec.js` | To a new TU, manual input | Main product receipt in manual-input mode (9 PCE + 0.9 kg catch weight) into a new TU; validates split into 3 TUs (4+4+1 PCE) in backend | — |
| `receiving_main_products_catchweight.spec.js` | To a new TU, scanning L+M QR codes | Main product receipt via three L+M QR codes into a new TU; validates 3 PCE and accumulated WeightNet 0.303 kg | — |
| `receiving_main_products_catchweight.spec.js` | To a new TU, scanning L+M QR codes from 2 manufacturing orders | Two orders contribute catch-weight main product receipts into the same existing TU via L+M codes; validates accumulated storage (4 PCE, 0.131 kg) | — |
| `receiving_main_products_catchweight.spec.js` | TO a new LU, scanning 2 x GTIN codes | Main product receipt via two GTIN QR code scans into a new LU; validates 2 PCE received into LU | — |

## Section 2 — Gap analysis

Manufacturing workflow paths that currently have **no** Playwright test:

- **Job list navigation / filtering**: No test exercises search/filter on the manufacturing jobs list (e.g., filter by document number, date, or status via facets). The existing tests always start a specific job by `documentNo` immediately.
- **Component issue: happy path confirmed end-to-end with backend validation**: `manufacturing.spec.js` scans components and receives, but does not call `Backend.expect` to verify the issued component quantities were recorded correctly in the backend after issue (only receipt HU is validated).
- **Component issue: partial issue (multiple HUs, same BOM line)**: No test issues a BOM line component across more than one HU and checks that the remaining qty decrements correctly on the job screen between scans.
- **Component issue: HU qty < remaining BOM qty (the bug fixed by this issue)**: Before this issue, no test verified that the qty suggestion is capped at HU capacity when HU qty is smaller than the BOM requirement. This gap is closed by Task 4.
- **Component issue: over-issue (worker types more than HU capacity)**: No test covers the path where a worker manually types a qty larger than the scanned HU's available qty, triggering an inventory adjustment. This is covered by Task 4 (AC4).
- **Component issue: zero-stock / no matching HU**: No test scans a QR code for an HU that belongs to the wrong product or has zero stock, to verify the error message.
- **Manufacturing job completion without issuing all components**: No test verifies the behavior (error or warning) when a worker tries to complete a job before all BOM lines are fully issued.
- **Manufacturing job: navigate back from issue screen without scanning**: No test verifies that navigating back from `RawMaterialIssueLineScreen` without scanning leaves the job state unchanged.
- **Receipt: selecting an existing LU as target**: `receive_using_customQRCodeFormat.spec.js` and by-product catch-weight tests use `selectExistingHUTarget`, but none of the main-product receipt tests do (they always create a new LU/TU).
- **Receipt: multiple receipt lines on same job (LU and TU in same job)**: No test exercises receiving finished product into a mix of LU and TU targets in a single job.
- **Mobile config: `isAllowIssuingAnyHU` exhaustive issue path**: `isAllowIssuingAnyHU.spec.js` only tests the job start step; no test actually scans an HU from a different product when `isAllowIssuingAnyHU=true` to verify the full issue path completes.

## Section 3 — Tests added by this issue (Task 4)

These 5 tests will be written in `tests/spec/manufacturing/issue_hu_qty_suggestion.spec.js`:

| Test name | Scenario | AC covered |
|---|---|---|
| AC1: Suggested qty capped at HU capacity — HU smaller than BOM requirement | BOM 20, HU 5, nothing issued → suggestion = 5 | AC1 |
| AC2: Suggested qty capped at HU capacity after partial issue (customer scenario) | BOM 20, 7 issued, HU 5 → suggestion = 5 | AC2 |
| AC3 (regression): Suggestion unchanged when HU capacity exceeds remaining BOM qty | BOM 20, HU 100 → suggestion = 20 | AC3 |
| AC4 (regression): Typing the old wrong suggestion (13) over-issues by 8 — inventory adjustment created | BOM 20, HU 5, type 13 → 13 issued, HU depleted, adj 8 | AC4 |
| AC5 (regression): Confirming HU-capacity suggestion creates no inventory adjustment | BOM 20, HU 5, confirm 5 → 5 issued, HU depleted, no adj | AC5 |
