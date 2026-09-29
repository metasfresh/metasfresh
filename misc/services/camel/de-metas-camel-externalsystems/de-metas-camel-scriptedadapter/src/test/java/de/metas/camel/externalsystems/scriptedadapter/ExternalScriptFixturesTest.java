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
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Conformance test for external converter scripts (scripts maintained outside this repo).
 * <p>
 * <b>Fixture layout</b> (all in one directory):
 * <pre>
 *   &lt;dir&gt;/&lt;script&gt;.js
 *   &lt;dir&gt;/&lt;script&gt;/&lt;case&gt;.input.&lt;ext&gt;
 *   &lt;dir&gt;/&lt;script&gt;/&lt;case&gt;.expected.&lt;ext&gt;
 * </pre>
 * For every {@code <case>.input.*} the script's {@code transform} function is run and its result is compared with
 * {@code <case>.expected.*}.
 * <p>
 * <b>Directory:</b> by default the bundled sample dir {@code external-script-fixtures-sample} (test resources), so the
 * test always runs in CI. Override with {@code -Dscriptedadapter.fixtures.dir=<dir>}.
 * <p>
 * <b>Normalisation rule</b> (the one and only; a Jest test may mirror it): line endings ({@code \r\n}, {@code \r}) are
 * normalised to {@code \n} in both texts, and the texts are compared line by line (same line count required).
 * Wherever an expected line contains the token {@code <<ANY>>}, that token matches any run of characters (possibly
 * empty), up to the next literal character of the expected line. Implementation: the expected line is escaped as a
 * regex literal, the escaped {@code <<ANY>>} is replaced by {@code .*?}, and the actual line must match it entirely.
 * Lines without the token must be equal.
 */
class ExternalScriptFixturesTest
{
	static final String SYSTEM_PROPERTY_FIXTURES_DIR = "scriptedadapter.fixtures.dir";
	private static final String SAMPLE_DIR_RESOURCE = "external-script-fixtures-sample";
	private static final String ANY_TOKEN = "<<ANY>>";

	record FixtureCase(String scriptName, String caseName, Path scriptFile, Path inputFile, Path expectedFile)
	{
		@Override
		public String toString() {return scriptName + "/" + caseName;}
	}

	static Stream<FixtureCase> fixtureCases() throws IOException, URISyntaxException
	{
		final Path dir = getFixturesDir();
		final List<FixtureCase> cases = new ArrayList<>();
		try (final Stream<Path> scripts = Files.list(dir))
		{
			for (final Path scriptFile : (Iterable<Path>)scripts.filter(p -> p.getFileName().toString().endsWith(".js")).sorted()::iterator)
			{
				final String fileName = scriptFile.getFileName().toString();
				final String scriptName = fileName.substring(0, fileName.length() - ".js".length());
				final Path caseDir = dir.resolve(scriptName);
				if (!Files.isDirectory(caseDir))
				{
					continue;
				}
				try (final Stream<Path> inputs = Files.list(caseDir))
				{
					for (final Path inputFile : (Iterable<Path>)inputs.filter(p -> p.getFileName().toString().contains(".input.")).sorted()::iterator)
					{
						final String inputName = inputFile.getFileName().toString();
						final String caseName = inputName.substring(0, inputName.indexOf(".input."));
						cases.add(new FixtureCase(scriptName, caseName, scriptFile, inputFile, findExpectedFile(caseDir, caseName)));
					}
				}
			}
		}
		assertThat(cases).as("no fixture cases found in %s", dir).isNotEmpty();
		return cases.stream();
	}

	private static Path findExpectedFile(final Path caseDir, final String caseName) throws IOException
	{
		try (final Stream<Path> files = Files.list(caseDir))
		{
			return files
					.filter(p -> p.getFileName().toString().startsWith(caseName + ".expected."))
					.findFirst()
					.orElseThrow(() -> new IllegalStateException("No " + caseName + ".expected.* file in " + caseDir));
		}
	}

	private static Path getFixturesDir() throws URISyntaxException
	{
		final String override = System.getProperty(SYSTEM_PROPERTY_FIXTURES_DIR);
		if (override != null && !override.isBlank())
		{
			return Paths.get(override).toAbsolutePath();
		}
		return Paths.get(ExternalScriptFixturesTest.class.getClassLoader().getResource(SAMPLE_DIR_RESOURCE).toURI());
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("fixtureCases")
	void scriptProducesExpectedOutput(final FixtureCase fixture) throws IOException
	{
		final String script = Files.readString(fixture.scriptFile());
		final String input = Files.readString(fixture.inputFile());
		final String expected = Files.readString(fixture.expectedFile());

		final String actual = new JavaScriptExecutorService().executeScript(fixture.scriptName(), script, input);

		assertThat(matchesExpected(expected, actual))
				.as("script %s, case %s%n--- expected (%s):%n%s%n--- actual:%n%s",
						fixture.scriptName(), fixture.caseName(), fixture.expectedFile().getFileName(), expected, actual)
				.isTrue();
	}

	/** See the normalisation rule in the class Javadoc. */
	static boolean matchesExpected(final String expected, final String actual)
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
				// Pattern.quote wraps in \Q..\E, so the token sits inside a quoted block: close it around the wildcard
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
		assertThat(matchesExpected("a<<ANY>>c\nx\n", "abbbc\r\nx\n")).isTrue();
		assertThat(matchesExpected("a<<ANY>>c", "ac")).isTrue();
		assertThat(matchesExpected("a<<ANY>>c", "abd")).isFalse();
		assertThat(matchesExpected("a<<ANY>>", "a\nb")).isFalse(); // does not span lines
		assertThat(matchesExpected("a.c", "abc")).isFalse(); // everything else is literal
		assertThat(matchesExpected("a\nb", "a")).isFalse();
	}
}
