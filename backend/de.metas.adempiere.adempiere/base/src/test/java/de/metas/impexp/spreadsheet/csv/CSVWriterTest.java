package de.metas.impexp.spreadsheet.csv;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CSVWriterTest
{
	// UTF-8 BOM (3 bytes: EF BB BF) that CSVWriter prepends to every file
	private static final String UTF8_BOM = "\uFEFF";

	@TempDir
	File tempDir;

	private File outputFile;

	@BeforeEach
	void beforeEach()
	{
		outputFile = new File(tempDir, "test.csv");
	}

	private CSVWriter createWriter(final List<String> header, final String fieldQualifier)
	{
		return CSVWriter.builder()
				.outputFile(outputFile)
				.header(header)
				.adLanguage("en_US")
				.fieldQualifier(fieldQualifier)
				.build();
	}

	private CSVWriter createWriter(final List<String> header)
	{
		return CSVWriter.builder()
				.outputFile(outputFile)
				.header(header)
				.adLanguage("en_US")
				.build();
	}

	private CSVWriter createWriter(final List<String> header, final String fieldQualifier, final Boolean includeHeader)
	{
		return CSVWriter.builder()
				.outputFile(outputFile)
				.header(header)
				.adLanguage("en_US")
				.fieldQualifier(fieldQualifier)
				.includeHeader(includeHeader)
				.build();
	}

	private String readOutput() throws IOException
	{
		final String raw = new String(Files.readAllBytes(outputFile.toPath()), StandardCharsets.UTF_8);
		// Strip the UTF-8 BOM so assertions are cleaner
		if (raw.startsWith(UTF8_BOM))
		{
			return raw.substring(1);
		}
		return raw;
	}

	// -------------------------------------------------------------------
	// MUST: Empty qualifier produces unquoted output (the RTIC case)
	// -------------------------------------------------------------------

	@Test
	void emptyQualifier_headerNotQuoted() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1", "Col2"), "");
		writer.appendRow(Arrays.asList("a", "b"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines[0]).isEqualTo("Col1;Col2");
		assertThat(lines[1]).isEqualTo("a;b");
	}

	@Test
	void emptyQualifier_valueContainingQuotes_passedThrough() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"), "");
		writer.appendRow(Arrays.asList("He said \"hi\""));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		// With no qualifier, embedded quotes are NOT escaped — raw value
		assertThat(lines[1]).isEqualTo("He said \"hi\"");
	}

	@Test
	void emptyQualifier_nullValue_emptyUnquoted() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"), "");
		writer.appendRow(Arrays.asList((Object) null));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n", -1);

		// null becomes empty string, and with empty qualifier it stays empty
		assertThat(lines[1]).isEmpty();
	}

	// -------------------------------------------------------------------
	// MUST: Default qualifier produces double-quoted output (backward compat)
	// -------------------------------------------------------------------

	@Test
	void defaultQualifier_valuesDoubleQuoted() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1", "Col2"));
		writer.appendRow(Arrays.asList("hello", "world"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines[0]).isEqualTo("\"Col1\";\"Col2\"");
		assertThat(lines[1]).isEqualTo("\"hello\";\"world\"");
	}

	@Test
	void defaultQualifier_valueContainingQuotes_escaped() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"));
		writer.appendRow(Arrays.asList("He said \"hi\""));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		// Embedded double-quotes are doubled: " -> ""
		assertThat(lines[1]).isEqualTo("\"He said \"\"hi\"\"\"");
	}

	@Test
	void defaultQualifier_nullValue_emptyQuoted() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"));
		writer.appendRow(Arrays.asList((Object) null));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		// null becomes "" (quoted empty)
		assertThat(lines[1]).isEqualTo("\"\"");
	}

	// -------------------------------------------------------------------
	// MUST: End-to-end chain — null qualifier falls back to default
	// -------------------------------------------------------------------

	@Test
	void nullQualifier_fallsBackToDefault() throws IOException
	{
		// When fieldQualifier is null, CSVWriter should use DEFAULT_FIELD_QUALIFIER (")
		final CSVWriter writer = CSVWriter.builder()
				.outputFile(outputFile)
				.header(Arrays.asList("Col1"))
				.adLanguage("en_US")
				.fieldQualifier(null)
				.build();
		writer.appendRow(Arrays.asList("value"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines[1]).isEqualTo("\"value\"");
	}

	@Test
	void explicitDoubleQuoteQualifier_sameAsDefault() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"), "\"");
		writer.appendRow(Arrays.asList("value"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines[1]).isEqualTo("\"value\"");
	}

	// -------------------------------------------------------------------
	// SHOULD: Value containing qualifier gets properly escaped
	// -------------------------------------------------------------------

	@Test
	void singleQuoteQualifier_valueContainingSingleQuote_escaped() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"), "'");
		writer.appendRow(Arrays.asList("it's a test"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		// Single quote in value is doubled: ' -> ''
		assertThat(lines[1]).isEqualTo("'it''s a test'");
	}

	// -------------------------------------------------------------------
	// SHOULD: Value containing delimiter with empty qualifier
	// -------------------------------------------------------------------

	@Test
	void emptyQualifier_valueContainingDelimiter_notProtected() throws IOException
	{
		// Documents a known limitation: with no qualifier, delimiter in values
		// produces ambiguous CSV. This is expected for RTIC (values don't contain tabs).
		final CSVWriter writer = CSVWriter.builder()
				.outputFile(outputFile)
				.header(Arrays.asList("Col1", "Col2"))
				.adLanguage("en_US")
				.fieldDelimiter("\t")
				.fieldQualifier("")
				.build();
		writer.appendRow(Arrays.asList("no tab", "also clean"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines[0]).isEqualTo("Col1\tCol2");
		assertThat(lines[1]).isEqualTo("no tab\talso clean");
	}

	// -------------------------------------------------------------------
	// MUST: includeHeader=false suppresses the header row
	// -------------------------------------------------------------------

	@Test
	void includeHeaderFalse_dataRowsOnly() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1", "Col2"), "\"", false);
		writer.appendRow(Arrays.asList("a", "b"));
		writer.appendRow(Arrays.asList("c", "d"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		// No header line — only data rows
		assertThat(lines).hasSize(2);
		assertThat(lines[0]).isEqualTo("\"a\";\"b\"");
		assertThat(lines[1]).isEqualTo("\"c\";\"d\"");
	}

	@Test
	void includeHeaderFalse_noDataRows_emptyOutput() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1"), "\"", false);
		// No rows appended, just close
		writer.close();

		// File should contain only the UTF-8 BOM (stripped by readOutput), so empty
		final String content = readOutput();
		assertThat(content).isEmpty();
	}

	@Test
	void includeHeaderFalse_emptyQualifier_dataOnly() throws IOException
	{
		// RTIC scenario: no header, no quotes
		final CSVWriter writer = createWriter(Arrays.asList("Col1", "Col2"), "", false);
		writer.appendRow(Arrays.asList("val1", "val2"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines).hasSize(1);
		assertThat(lines[0]).isEqualTo("val1;val2");
	}

	@Test
	void includeHeaderNull_defaultIncludesHeader() throws IOException
	{
		// null defaults to true (backward compat)
		final CSVWriter writer = createWriter(Arrays.asList("Col1"), "\"", null);
		writer.appendRow(Arrays.asList("val"));
		writer.close();

		final String content = readOutput();
		final String[] lines = content.split("\n");

		assertThat(lines).hasSize(2);
		assertThat(lines[0]).isEqualTo("\"Col1\"");
		assertThat(lines[1]).isEqualTo("\"val\"");
	}

	@Test
	void includeHeaderFalse_linesWroteCountsOnlyDataRows() throws IOException
	{
		final CSVWriter writer = createWriter(Arrays.asList("Col1", "Col2"), "\"", false);
		writer.appendRow(Arrays.asList("a", "b"));
		writer.appendRow(Arrays.asList("c", "d"));
		writer.close();

		// linesWrote should count only data rows (no header line)
		assertThat(writer.getLinesWrote()).isEqualTo(2);
	}
}
