/*
 * #%L
 * de-metas-camel-scriptedadapter
 * %%
 * Copyright (C) 2025 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.camel.externalsystems.scriptedadapter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Conformance test for external converter scripts (scripts maintained outside this repo).
 * <p>
 * <b>Fixture layout</b>:
 * <pre>
 *   &lt;scriptsDir&gt;/&lt;script&gt;.js
 *   &lt;fixturesDir&gt;/&lt;script&gt;/&lt;case&gt;.input.&lt;ext&gt;
 *   &lt;fixturesDir&gt;/&lt;script&gt;/&lt;case&gt;.expected.&lt;ext&gt;
 * </pre>
 * For every {@code <case>.input.*} the script's {@code transform} function is run and its result is compared with
 * {@code <case>.expected.*}. The test fails if a case dir has no matching {@code <scriptsDir>/<script>.js}, if a case
 * dir has no {@code *.input.*} case, or (when scripts are read from the fixtures dir) if a {@code <script>.js} has no
 * case dir.
 * <p>
 * <b>Directories:</b> by default the fixtures are the bundled sample dir {@code external-script-fixtures-sample}
 * (test resources) and the scripts are the module's {@code javascript_templates/} dir, so the test always runs in CI.
 * Override the fixtures dir with {@code -Dscriptedadapter.fixtures.dir=<dir>}; the scripts are then taken from the same
 * dir (i.e. {@code <dir>/<script>.js} next to {@code <dir>/<script>/<case>.*}), unless
 * {@code -Dscriptedadapter.scripts.dir=<dir>} names a separate scripts dir.
 * <p>
 * <b>Normalisation rule</b> (the one and only; a Jest test may mirror it): line endings ({@code \r\n}, {@code \r}) are
 * normalised to {@code \n} in both texts, and the texts are compared line by line (same line count required).
 * A line without the token {@code <<ANY>>} must be equal. For an expected line containing the token, the line is split
 * at each {@code <<ANY>>}, each literal segment is regex-quoted, the segments are joined with {@code .*?}, and the
 * actual line must match that regex entirely ({@code Pattern.matches}: full-line, backtracking, no DOTALL, so
 * {@code <<ANY>>} matches any run of characters, possibly empty, within a single line).
 */
class ExternalScriptFixturesTest
{
	static final String SYSTEM_PROPERTY_FIXTURES_DIR = "scriptedadapter.fixtures.dir";
	static final String SYSTEM_PROPERTY_SCRIPTS_DIR = "scriptedadapter.scripts.dir";
	private static final String DEFAULT_SCRIPTS_DIR = "javascript_templates";
	private static final String SAMPLE_DIR_RESOURCE = "external-script-fixtures-sample";
	private static final String ANY_TOKEN = "<<ANY>>";

	record FixtureCase(String scriptName, String caseName, Path scriptFile, Path inputFile, Path expectedFile)
	{
		@Override
		public String toString() {return scriptName + "/" + caseName;}
	}

	static Stream<FixtureCase> fixtureCases() throws IOException, URISyntaxException
	{
		final Path fixturesDir = getFixturesDir();
		final Path scriptsDir = getScriptsDir(fixturesDir);
		final List<FixtureCase> cases = new ArrayList<>();
		if (scriptsDir.equals(fixturesDir))
		{
			// scripts live next to their case dirs: every script must have a case dir
			try (final Stream<Path> scripts = Files.list(scriptsDir))
			{
				for (final Path scriptFile : (Iterable<Path>)scripts.filter(file -> file.getFileName().toString().endsWith(".js")).sorted()::iterator)
				{
					final String fileName = scriptFile.getFileName().toString();
					final Path caseDir = fixturesDir.resolve(fileName.substring(0, fileName.length() - ".js".length()));
					if (!Files.isDirectory(caseDir))
					{
						throw new IllegalStateException("No case dir " + caseDir + " for script " + scriptFile);
					}
				}
			}
		}
		try (final Stream<Path> caseDirs = Files.list(fixturesDir))
		{
			for (final Path caseDir : (Iterable<Path>)caseDirs.filter(Files::isDirectory).sorted()::iterator)
			{
				final String scriptName = caseDir.getFileName().toString();
				final Path scriptFile = scriptsDir.resolve(scriptName + ".js");
				if (!Files.isRegularFile(scriptFile))
				{
					throw new IllegalStateException("No script " + scriptFile + " for case dir " + caseDir);
				}
				final int casesBefore = cases.size();
				try (final Stream<Path> inputs = Files.list(caseDir))
				{
					for (final Path inputFile : (Iterable<Path>)inputs.filter(file -> file.getFileName().toString().contains(".input.")).sorted()::iterator)
					{
						final String inputName = inputFile.getFileName().toString();
						final String caseName = inputName.substring(0, inputName.indexOf(".input."));
						cases.add(new FixtureCase(scriptName, caseName, scriptFile, inputFile, findExpectedFile(caseDir, caseName)));
					}
				}
				if (cases.size() == casesBefore)
				{
					throw new IllegalStateException("No *.input.* cases in " + caseDir);
				}
			}
		}
		assertThat(cases).as("no fixture cases found in %s", fixturesDir).isNotEmpty();
		return cases.stream();
	}

	private static Path findExpectedFile(final Path caseDir, final String caseName) throws IOException
	{
		try (final Stream<Path> files = Files.list(caseDir))
		{
			final List<Path> matches = files
					.filter(file -> file.getFileName().toString().startsWith(caseName + ".expected."))
					.sorted()
					.toList();
			if (matches.size() != 1)
			{
				throw new IllegalStateException("Expected exactly one " + caseName + ".expected.* file in " + caseDir + " but found " + matches);
			}
			return matches.get(0);
		}
	}

	private static Path getFixturesDir() throws URISyntaxException
	{
		final String override = System.getProperty(SYSTEM_PROPERTY_FIXTURES_DIR);
		if (override != null && !override.isBlank())
		{
			return Paths.get(override).toAbsolutePath();
		}
		return Paths.get(Objects.requireNonNull(
				ExternalScriptFixturesTest.class.getClassLoader().getResource(SAMPLE_DIR_RESOURCE),
				"sample fixtures dir not on classpath").toURI());
	}

	private static Path getScriptsDir(final Path fixturesDir)
	{
		final String override = System.getProperty(SYSTEM_PROPERTY_SCRIPTS_DIR);
		if (override != null && !override.isBlank())
		{
			return Paths.get(override).toAbsolutePath();
		}
		final String fixturesOverride = System.getProperty(SYSTEM_PROPERTY_FIXTURES_DIR);
		if (fixturesOverride != null && !fixturesOverride.isBlank())
		{
			return fixturesDir; // external layout: scripts live next to their case dirs
		}
		return Paths.get(System.getProperty("user.dir")).resolve(DEFAULT_SCRIPTS_DIR);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("fixtureCases")
	void scriptProducesExpectedOutput(final FixtureCase fixture) throws IOException
	{
		final String script = Files.readString(fixture.scriptFile());
		final String input = Files.readString(fixture.inputFile());
		final String expected = Files.readString(fixture.expectedFile());

		final String actual = new JavaScriptExecutorService().executeScript(fixture.scriptName(), script, input);

		assertThat(isMatchingExpected(expected, actual))
				.as("script %s, case %s%n--- expected (%s):%n%s%n--- actual:%n%s",
						fixture.scriptName(), fixture.caseName(), fixture.expectedFile().getFileName(), expected, actual)
				.isTrue();
	}

	/** See the normalisation rule in the class Javadoc. */
	static boolean isMatchingExpected(final String expected, final String actual)
	{
		final String[] expectedLines = normalizeLineEndings(expected).split("\n", -1);
		final String[] actualLines = normalizeLineEndings(actual).split("\n", -1);
		if (expectedLines.length != actualLines.length)
		{
			return false;
		}
		for (int i = 0; i < expectedLines.length; i++)
		{
			if (expectedLines[i].contains(ANY_TOKEN))
			{
				// split at the token, quote each literal segment, join with the lazy wildcard
				final String[] parts = expectedLines[i].split(Pattern.quote(ANY_TOKEN), -1);
				final StringBuilder sb = new StringBuilder();
				for (int p = 0; p < parts.length; p++)
				{
					if (p > 0)
					{
						sb.append(".*?");
					}
					sb.append(Pattern.quote(parts[p]));
				}
				if (!Pattern.matches(sb.toString(), actualLines[i]))
				{
					return false;
				}
			}
			else if (!expectedLines[i].equals(actualLines[i]))
			{
				return false;
			}
		}
		return true;
	}

	private static String normalizeLineEndings(final String s)
	{
		return s.replace("\r\n", "\n").replace('\r', '\n');
	}

	@Test
	void anyToken_matchesRunOfCharactersWithinLineOnly()
	{
		assertThat(isMatchingExpected("a<<ANY>>c\nx\n", "abbbc\r\nx\n")).isTrue();
		assertThat(isMatchingExpected("a<<ANY>>c", "ac")).isTrue();
		assertThat(isMatchingExpected("a<<ANY>>c", "abd")).isFalse();
		assertThat(isMatchingExpected("a<<ANY>>", "a\nb")).isFalse(); // does not span lines
		assertThat(isMatchingExpected("a.c", "abc")).isFalse(); // everything else is literal
		assertThat(isMatchingExpected("a\nb", "a")).isFalse();
	}
}
