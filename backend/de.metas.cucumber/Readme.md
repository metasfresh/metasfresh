# Writing cucumber tests

External references:
* https://medium.com/agile-vision/cucumber-bdd-part-2-creating-a-sample-java-project-with-cucumber-testng-and-maven-127a1053c180
* https://javapointers.com/automation/cucumber/cucumber-scenario-outline-example/ with "table-examples"
* https://automationrhapsody.com/introduction-to-cucumber-and-bdd-with-examples/ with maven-infos
* https://javapointers.com/automation/cucumber/cucumber-data-tables-example-in-java/ with tables in "given"




# Fast Cucumber Development Runner

This utility class, `FastCucumberDevRunner`, is designed to drastically cut down the feedback loop time when developing and debugging Cucumber scenarios in the `de.metas` project. By loading the core application context (Metasfresh) once and keeping the JVM alive, it eliminates the lengthy startup time associated with running individual feature files as new processes.

**Expected Speed-up:** Cuts 1.5 - 2 minute test startup time to near-instant execution (less than 1 second).

## 1\. How It Works

The runner operates in a continuous loop:

1.  It starts the application once (outside of the loop).
2.  It waits for input on the console (`System.in`).
3.  When a path is provided, it uses `io.cucumber.core.cli.Main.run()` to execute the scenario within the existing, running JVM.
4.  After execution, it cleans up and waits for the next command.

## 2\. Setup and Execution (IntelliJ IDEA)

### Step 1: Start the Fast Runner (Server Mode)

Run the `FastCucumberDevRunner.main()` method as a standard IntelliJ **Application** run configuration.

**Crucial:** Do not stop this process. Leave the console window open and wait for the "WAITING" prompt. This process keeps the application server (Metasfresh) fully booted.

### Step 2: Running a Scenario

Once the console shows the `WAITING` prompt, you can execute feature files rapidly:

1.  **Get Feature Path:** Navigate to the `.feature` file you want to run.
2.  **Copy Absolute Path:** Right-click the file in the Project pane and select **Copy Path/Reference** \> **Absolute Path**.
3.  **Paste and Run (Initial Run):**
      * Paste the path into the running `FastCucumberDevRunner` console.
      * Hit `ENTER`.
4.  **Wait for `WAITING` Prompt:** The test will execute in seconds.

### Step 3: Rapid Iteration (The Power User Feature)

To re-run the *last executed* feature file after making changes:

  * Go to the `FastCucumberDevRunner` console window.
  * **Just hit `ENTER`**.

The console will display the last run file name and prompt you:
```
======================================================= WAITING: Paste absolute path to .feature file (or 'exit'):

Hit ENTER to re-run last file: my_scenario.feature
```

## 3\. Report Generation

After every execution, the runner automatically generates a detailed HTML report and provides a clickable link in the console output.

### Report Path Structure

Reports are stored dynamically based on the feature file name to prevent results from overwriting each other.

  * **Console Output:** A clickable link will be printed:
    ```
    >>> 📄 **Report Link (Clickable):**
    file:///.../target/FastCucumberDevRunner/my_scenario/test_results.html
    ```
  * **Physical Location:** The report is saved to:
    ```
    target/FastCucumberDevRunner/<feature_file_name_without_extension>/test_results.html
    ```

## 4\. Troubleshooting and Clean State

### Critical Note on State Management

Since the application context is **never shut down** between test runs, developers must ensure their tests are completely isolated.

1.  **Test Data Strategy:** Developers shall ensure test scenarios are isolated by **using unique master data** for every run (e.g., appending a timestamp or UUID to names for new business partners, documents, or users). This prevents one test's output from interfering with the next.
2.  **Singleton/Static Cache Reset:** If your application uses static caches (e.g., for configuration), you may need to explicitly reset them in a core Step Definition or Hook to prevent the second test from using stale data from the first run.

If subsequent test runs fail unexpectedly or use data from a previous run, the likely cause is **incomplete state isolation**.

### Shutting Down

To cleanly shut down the server and exit the runner, type `exit` at the `>>>` prompt and hit `ENTER`.

# Asserting on a rendered document PDF

`AD_Archive_StepDef` lets a scenario assert against the **actual PDF** metasfresh rendered and archived
for a document — not against the report's SQL, and not against a mock. Use it whenever the thing under
test is what the customer or the warehouse will physically read.

The steps resolve the record through the normal step-def identifier mechanism, so they work for **any**
record that archives a PDF — sales order, shipment, invoice, and so on.

## Getting a PDF to assert on

Two sysconfigs must be set in your `Background`, or you will assert against a placeholder:

```gherkin
# render the real jasper report; the mock report service would archive a placeholder PDF instead
And set sys config boolean value false for sys config de.metas.report.jasper.IsMockReportService
# keep the archive in the DB, so the steps can read it back
And update AD_Client
  | Identifier | StoreArchiveOnFileSystem |
  | 1000000    | false                    |
```

Then complete the document and run its jasper process — the `Value` is the `AD_Process.Value`, so a
different document just means a different process:

```gherkin
When the order identified by order is completed
And The jasper process is run
  | Value            | Record_ID |
  | Auftrag (Jasper) | order     |
```

`Lieferschein (Jasper)`, `Rechnung (Jasper)`, `Bestellung (Jasper)`, `Wareneingang (Jasper)` and the
rest work the same way.

## The steps

```gherkin
# the document was archived at all
Then an AD_Archive exists for the record identified by "order"

# the text reached the PDF (or must not be there)
Then the PDF archived for the record identified by "order" contains text "Bitte gekuehlt liefern"
Then the PDF archived for the record identified by "order" does not contain text "Zwischenpalette"

# WHERE the text sits — counted in rendered visual lines
Then in the PDF archived for the record identified by "order", exactly 0 lines appear between text "ALPHA-NR" and text "BetaItem"
Then in the PDF archived for the record identified by "order", at least 2 lines appear between text "Diese Position" and text "BetaItem"

# geometry — compare one vertical gap against another
Then in the PDF archived for the record identified by "order", the vertical distance from text "A" to text "B" equals the distance from text "C" to text "D"
```

**Prefer the positional steps over `contains text` alone.** "The text is somewhere in the document" is a
weak assertion: it passes even when the text lands in the wrong block, above the wrong article, or on
the wrong page. The `lines between` and `vertical distance` steps are what make *placement* testable.

**Pick single distinctive words as needles.** The extraction yields one entry per rendered visual line,
so a multi-word needle stops matching as soon as the layout wraps between two of its words.

**A blank line emits no glyphs.** If what you are testing is that something occupies vertical *space*
(an empty line inside a text block, or the absence of a suppressed block), text adjacency cannot see it
— use the `vertical distance … equals …` step against a reference block of known height.

## Checking the layout: nothing printed on top of anything else

```gherkin
Then the PDF archived for the record identified by "order" has no overlapping text
Then the PDF archived for the record identified by "order" has no overlapping text within 2 points
```

A generic layout net. It needs no knowledge of the document, so it can be added to any scenario that
already prints one. It builds a bounding box per glyph and flags a pair only when the boxes intersect by
more than the tolerance in **both** dimensions, so glyphs that merely share a column or a baseline are
not a collision. The tolerance is in PDF user-space **points** (not pixels) and defaults to 2.

It catches an element that stretches or is positioned into its neighbour: a long product name running
into the quantity column, a block that grew into the row beneath.

**It does not catch clipping**, which is the more common Jasper failure. An element too small for its
content with `isStretchWithOverflow` off does not overlap anything - it silently truncates. That shows
up as *missing* text, so assert `contains text` on a word you expect near the end of the content.

Measured on a plain three-position order confirmation: 775 glyphs examined, 0 overlaps at the default
tolerance.

### Why this works per GLYPH, and not per word

Worth knowing before you "improve" it, because two coarser groupings were tried first and both failed
against a real document:

- **Per content-stream run** (no `setSortByPosition`): a run in these documents spans several visually
  separate columns - one came back as `10,00AlphaItem`. Boxes built from that span the whole row and
  collide with everything on it, so every row reported overlaps a reader cannot see.
- **Per word** (`setSortByPosition(true)`, which is what makes `writeString` fire once per word): the
  same sorting that produces words also **fuses colliding glyphs into one word**. With a field
  deliberately moved on top of the product name, the two texts came back as the single word
  `ASltpkhaItem` - so there were no longer two boxes to compare, and the real collision was invisible.

Per glyph neither happens: characters inside a word merely abut, because the advance width places the
next glyph exactly where the previous one ends, so their horizontal overlap is about 0 and stays under
the tolerance; whereas two texts printed on top of each other overlap by most of a glyph width.

## The PDF is attached to the Allure report

Every PDF a scenario asserts against is attached to that scenario's Allure report automatically,
deduplicated per archive, on pass as well as on failure. That gives a reviewer the actual document
instead of a description of it.

Allure nests the attachment **under the step that read the PDF**, so the test-case page looks empty
until you expand that step; the entry is named `PDF archived for <identifier> (AD_Archive_ID=…)`.

To get direct links out of a published report instead of clicking through, for a report base
`https://test-reports.metasfresh.com/branches/<branch>/builds/<build-tag>/allure/cucumber`:

1. `data/suites.json` → walk to the leaf nodes for each scenario's `uid`
2. `data/test-cases/<uid>.json` → walk `testStage` recursively over `steps`, collecting `attachments`
   whose `type` is `application/pdf`; each carries a `source`
3. the file is at `data/attachments/<source>.pdf`

## How it works, in case a step surprises you

Text is extracted with PDFBox (`PDFTextStripper`, `setSortByPosition(true)`), grouped into visual lines
by page and y-coordinate. "Lines between" counts those rendered lines, not `\n` characters in the source
data — which is the point: it measures what the reader sees.

PDFBox is pinned to 2.0.27 in this module's `pom.xml` on purpose: `PDDocument.load(byte[])` exists in
PDFBox 2 but not in 3, and this module skips the enforcer, so an unpinned transitive bump would break
these steps with nothing else catching it.
