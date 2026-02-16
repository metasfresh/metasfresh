package de.metas.cucumber;

import io.cucumber.core.cli.Main;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FastCucumberDevRunner
{
	private static final String CUCUMBER_GLUE_PACKAGE = "de.metas.cucumber.stepdefs"; // The package where your Step Definitions live

	public static void main(final String[] args)
	{
		System.setProperty("user.timezone", "Europe/Berlin");
		CucumberLifeCycleSupport.beforeAll();
		loopReadAndExecute();
	}

	private static void loopReadAndExecute()
	{
		final Scanner scanner = new Scanner(System.in);

		String lastFeatureFilePath = null;
		while (true)
		{
			System.out.flush();
			System.err.flush();
			System.out.println("\n\n\n=======================================================");
			System.out.println("WAITING: Paste absolute path to .feature file (or 'exit'):");
			// Line 2: Contextual Instruction (Conditional)
			if (lastFeatureFilePath != null)
			{
				// Extract just the filename for a cleaner display or use the full path if needed
				final String filename = new File(lastFeatureFilePath).getName();
				System.out.println("  > Hit ENTER to re-run last file: **" + filename + "**");
			}
			else
			{
				System.out.println("  > (No previous file run)");
			}
			System.out.print(">>> ");

			String input = scanner.nextLine().trim();
			if ("exit".equalsIgnoreCase(input))
			{
				System.out.println("Shutting down...");
				break;
			}

			if (input.isEmpty())
			{
				if (lastFeatureFilePath == null)
				{
					continue;
				}
				else
				{
					input = lastFeatureFilePath;
				}
			}

			// Remove surrounding quotes if the user pasted them (common on Windows)
			input = input.replace("\"", "");

			runCucumberFile(input);
			lastFeatureFilePath = input;
		}
	}

	private static void runCucumberFile(@NonNull final String featureFilePath)
	{
		System.out.println("\n>>> 🏃 RUNNING SCENARIO: " + featureFilePath);

		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

		try
		{
			final Path reportFilePath = createHtmlReportPathAndEnsureDirectories(featureFilePath);

			final String[] argv = new String[] {
					"--glue", CUCUMBER_GLUE_PACKAGE,
					"--plugin", "pretty", // Console output
					"--plugin", "html:" + reportFilePath, // HTML report
					"--monochrome",
					featureFilePath
			};

			// Run Cucumber 7 using the Main CLI entry point
			// This returns a byte exit status (0 = success, 1 = failure) but does NOT kill the JVM
			final byte exitStatus = Main.run(argv, classLoader);

			if (exitStatus == 0)
			{
				System.out.println(">>> ✅ PASS");
			}
			else
			{
				System.out.println(">>> ❌ FAIL");
			}

			// --- PRINT CLICKABLE LINK ---
			if (java.nio.file.Files.exists(reportFilePath))
			{
				final String clickableUri = reportFilePath.toUri().toString();
				System.out.println("\n>>> 📄 **Report Link (Clickable):**");
				System.out.println(clickableUri);
			}
			// ----------------------------------------
		}
		catch (final Throwable t)
		{
			//noinspection CallToPrintStackTrace
			t.printStackTrace();
		}
	}

	private static Path createHtmlReportPathAndEnsureDirectories(@NonNull final String featureFilePath)
	{
		// Strip line number suffix if present (e.g., "path/file.feature:123" -> "path/file.feature")
		final String pathWithoutLineNumber = featureFilePath.contains(":") && featureFilePath.lastIndexOf(":") > featureFilePath.lastIndexOf(File.separator)
				? featureFilePath.substring(0, featureFilePath.lastIndexOf(":"))
				: featureFilePath;

		final Path featurePath = Paths.get(pathWithoutLineNumber);

		final String featureFileName = featurePath.getFileName().toString();
		final String baseFolderName = featureFileName.replace(".feature", "");

		final Path baseReportPath = Paths.get("target", "FastCucumberDevRunner", baseFolderName);
		final Path reportFilePath = baseReportPath.resolve("test_results.html");

		// 4. Ensure the dynamic report directory exists
		try
		{
			java.nio.file.Files.createDirectories(baseReportPath);
			System.out.println(">>> Report directory created/verified: " + baseReportPath.toAbsolutePath());
		}
		catch (final IOException e)
		{
			// Throw a RuntimeException since this is a critical failure before running the test
			throw new RuntimeException("Failed to create report directory: " + baseReportPath.toAbsolutePath(), e);
		}

		return reportFilePath;
	}
}
