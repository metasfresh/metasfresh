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

## Asserting on the rendered PDF of an archived document

`AD_Archive_StepDef` lets a scenario assert against the **actual PDF** metasfresh rendered and archived
for a document - not against the report's SQL, and not against a mock. Use it whenever the thing under
test is what the customer or the warehouse will physically read. The steps resolve the record through the
normal identifier mechanism, so they work for any record that archives a PDF.

Run the document's jasper process first; `The jasper process is run` renders the real report through
`LocalReportServer` and archives it. The `Value` is the `AD_Process.Value`:

```gherkin
When the order identified by order is completed
And The jasper process is run
  | Value            | Record_ID |
  | Auftrag (Jasper) | order     |

Then an AD_Archive exists for the record identified by "order"
And the PDF archived for the record identified by "order" contains text "AlphaItem"
And the PDF archived for the record identified by "order" does not contain text "Sortimentsware"
```

The steps read the newest archive of the record (highest `AD_Archive_ID`) through `IArchiveBL`, so both
`AD_Client.StoreArchiveOnFileSystem` settings work. Text is extracted with PDFBox (`PDFTextStripper`,
`setSortByPosition(true)`) as one entry per rendered visual line, so **pick single distinctive words as
needles**: a multi-word needle stops matching as soon as the layout wraps between two of its words.
Every PDF a scenario asserts against is attached to that scenario's Allure report (once per archive).

PDFBox is pinned to 2.0.27 in this module's `pom.xml` on purpose: `PDDocument.load(byte[])` exists in
PDFBox 2 but not in 3, and this module skips the enforcer.
