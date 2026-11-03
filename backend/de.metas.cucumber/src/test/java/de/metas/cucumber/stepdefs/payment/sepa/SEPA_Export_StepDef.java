/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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


package de.metas.cucumber.stepdefs.payment.sepa;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.payment.C_PaySelection_StepDefData;
import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.payment.sepa.process.C_PaySelection_SEPA_XmlExport;
import de.metas.payment.sepa.model.I_SEPA_Export_Line;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.report.ReportResultData;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_PaySelection;
import org.compiere.util.Env;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlunit.builder.DiffBuilder;
import org.xmlunit.builder.Input;
import org.xmlunit.diff.Comparison;
import org.xmlunit.diff.ComparisonResult;
import org.xmlunit.diff.ComparisonType;
import org.xmlunit.diff.DefaultNodeMatcher;
import org.xmlunit.diff.Diff;
import org.xmlunit.diff.DifferenceEvaluators;
import org.xmlunit.diff.ElementSelectors;
import org.xmlunit.diff.NodeFilters;
import org.xmlunit.diff.NodeMatcher;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Runs the SEPA export process and asserts the produced XML / ZIP files.
 *
 * @see C_PaySelection_StepDefData
 */
@RequiredArgsConstructor
public class SEPA_Export_StepDef
{
	private static final String SPACE_TOKEN = "<SPACE>";
	private static final String IGNORE_PLACEHOLDER = "${ignore}";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	@NonNull private final C_PaySelection_StepDefData paySelectionTable;

	@Nullable private ProcessExecutionResult result;
	/** the files of the last run as exported, by scheme = text of {@code PmtTpInf/LclInstrm/Cd} */
	private final Map<String, byte[]> filesByScheme = new LinkedHashMap<>();
	private final List<String> filenames = new ArrayList<>();

	/**
	 * Runs the AD_Process {@code C_PaySelection_SEPA_XmlExport} on the given pay selection, with the process defaults
	 * (IsGroupTransactions=N, ReferenceAsEndToEndId=N, SEPA_Protocol unset), and stores the outcome for the following steps.
	 * <p>
	 * On success, the report data is read: a ZIP (PK magic or zip content type) is unpacked, every entry is parsed as XML; otherwise the single file is parsed.
	 * Each file is stored under its scheme (the text of {@code PmtTpInf/LclInstrm/Cd}, e.g. CORE or B2B); two files with the same scheme fail the step.
	 * Each file is also written as-is to {@code target/sepa-export-files/<pay selection identifier>/<filename>}, relative to the working directory
	 * (the module directory under surefire).
	 * A process that fails while running does not fail this step (its error is in the result); the failure steps assert it.
	 *
	 * @param paySelectionIdentifier identifier of a pay selection registered via {@code metasfresh contains Pay Selection}
	 * @cucumber.example
	 * <pre>{@code
	 * When the SEPA export process is run for pay selection "paySelection"
	 * }</pre>
	 */
	@When("the SEPA export process is run for pay selection {string}")
	public void run(@NonNull final String paySelectionIdentifier) throws Exception
	{
		result = null;
		filesByScheme.clear();
		filenames.clear();

		result = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(adProcessDAO.retrieveProcessIdByClass(C_PaySelection_SEPA_XmlExport.class))
				.setRecord(I_C_PaySelection.Table_Name, paySelectionTable.getId(paySelectionIdentifier))
				// the process defaults; SEPA_Protocol stays unset
				.addParameter("IsGroupTransactions", false)
				.addParameter("ReferenceAsEndToEndId", false)
				.buildAndPrepareExecution()
				.executeSync()
				.getResult();

		if (result.isError())
		{
			return;
		}

		final ReportResultData reportData = result.getReportData();
		assertThat(reportData).as("the SEPA export process must produce report data").isNotNull();
		final Resource resource = result.getReportDataResource();
		assertThat(resource).as("the SEPA export process must produce a file").isNotNull();
		final byte[] bytes = toBytes(resource);
		final String filename = reportData.getReportFilename();
		final String contentType = reportData.getReportContentType();

		final Path dumpDir = Paths.get("target", "sepa-export-files", sanitize(paySelectionIdentifier));
		Files.createDirectories(dumpDir);

		if (isZip(bytes, contentType))
		{
			try (final ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(bytes)))
			{
				ZipEntry entry;
				while ((entry = zis.getNextEntry()) != null)
				{
					if (entry.isDirectory())
					{
						continue;
					}
					final byte[] entryBytes = StreamUtils.copyToByteArray(zis);
					addFile(dumpDir, entry.getName(), entryBytes);
				}
			}
		}
		else
		{
			addFile(dumpDir, filename, bytes);
		}
	}

	/**
	 * Asserts that the last run succeeded and produced exactly one file, and that it is an {@code .xml} file.
	 *
	 * @cucumber.example
	 * <pre>{@code
	 * Then the SEPA export result is a single XML file
	 * }</pre>
	 */
	@Then("the SEPA export result is a single XML file")
	public void assertSingleXmlFile()
	{
		assertSuccess();
		assertThat(filenames).as("exported files").hasSize(1);
		assertThat(filenames.get(0)).as("exported file name").endsWith(".xml");
	}

	/**
	 * Asserts that the last run succeeded and produced exactly the given number of files, all of them {@code .xml} files (a ZIP is unpacked; the step does not distinguish a ZIP from a single file).
	 *
	 * @param expectedCount the number of XML files in the ZIP
	 * @cucumber.example
	 * <pre>{@code
	 * Then the SEPA export result is a ZIP with 2 XML files
	 * }</pre>
	 */
	@Then("the SEPA export result is a ZIP with {int} XML files")
	public void assertZipWithXmlFiles(final int expectedCount)
	{
		assertSuccess();
		assertThat(filenames).as("exported files").hasSize(expectedCount);
		assertThat(filenames).as("exported file names").allMatch(name -> name.endsWith(".xml"));
	}

	/**
	 * Compares the exported file of the given scheme with the expected pain.008 XML (the DocString), as a whole.
	 * <ul>
	 *   <li>Whitespace between elements (indentation, line breaks) is ignored; the text of an element is compared exactly, including its spaces.</li>
	 *   <li>XML comments are ignored, so the expected file may explain its elements with {@code <!-- ... -->}.</li>
	 *   <li>The comparison is for similarity: a namespace prefix, the order of sibling elements, the {@code xsi:schemaLocation} and the {@code standalone} flag may differ.</li>
	 *   <li>{@code ${ignore}} as the whole text of an element accepts any text (the element must still exist).
	 *   Use it for the values that differ from run to run: {@code MsgId} (the SEPA_Export DocumentNo), and {@code PmtInfId} and {@code EndToEndId}, which are derived from it.</li>
	 *   <li>{@code PmtInf} blocks are paired by their transaction (all texts of {@code DrctDbtTxInf} except {@code PmtId}), not by position,
	 *   because the order of the transactions in the file is not defined; a {@code PmtInf} without an equal transaction is paired by position.</li>
	 * </ul>
	 * On a mismatch, the step fails with all differences, the actual file and the expected file.
	 *
	 * @param scheme      the scheme of the file, e.g. CORE or B2B
	 * @param expectedXml the expected file
	 * @cucumber.example
	 * <pre>{@code
	 * Then the SEPA direct debit file for scheme B2B is:
	 *   """
	 *   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	 *   <Document xmlns="urn:iso:std:iso:20022:tech:xsd:pain.008.001.08">
	 *       <CstmrDrctDbtInitn>
	 *           <GrpHdr>
	 *               <MsgId>${ignore}</MsgId>
	 *               <CreDtTm>2026-10-28T10:00:00.000+01:00</CreDtTm>
	 *               ...
	 *   </Document>
	 *   """
	 * }</pre>
	 */
	@Then("the SEPA direct debit file for scheme {word} is:")
	public void assertFile(@NonNull final String scheme, @NonNull final String expectedXml)
	{
		final String actualXml = new String(getFile(scheme), StandardCharsets.UTF_8);

		final Diff diff = DiffBuilder.compare(Input.fromString(expectedXml))
				.withTest(Input.fromString(actualXml))
				.withNodeFilter(node -> NodeFilters.Default.test(node) && node.getNodeType() != Node.COMMENT_NODE && !isFormattingWhitespace(node))
				.withNodeMatcher(new PmtInfByTransactionNodeMatcher())
				.withDifferenceEvaluator(DifferenceEvaluators.chain(DifferenceEvaluators.Default, SEPA_Export_StepDef::evaluateIgnorePlaceholder))
				.checkForSimilar()
				.build();

		if (diff.hasDifferences())
		{
			final StringBuilder differences = new StringBuilder();
			diff.getDifferences().forEach(difference -> differences.append("- ").append(difference).append('\n'));
			throw new AssertionError("The SEPA direct debit file for scheme " + scheme + " is not as expected:\n" + differences
					+ "==================\nActual file:\n" + actualXml
					+ "\n==================\nExpected file:\n" + expectedXml);
		}
	}

	/**
	 * Asserts that the last run failed, carries no report data, and that its message contains every given substring.
	 * The table has no header row: every cell is one substring (the message is in the runtime language, de_DE); {@code <SPACE>} stands for one space.
	 * The message is the result summary plus the exception message.
	 *
	 * @cucumber.example
	 * <pre>{@code
	 * Then the SEPA export process fails with a message containing:
	 *   | Mandat |
	 *   | fehlt  |
	 * }</pre>
	 */
	@Then("the SEPA export process fails with a message containing:")
	public void assertFailsWithMessage(@NonNull final DataTable dataTable)
	{
		assertFailure(null, dataTable);
	}

	/**
	 * Like {@link #assertFailsWithMessage}, and additionally asserts the {@code errorCode} of the process exception
	 * (the language-independent AD_Message ErrorCode, or the message key when the message has none).
	 *
	 * @param expectedErrorCode the expected {@code AdempiereException.getErrorCode()}
	 * @cucumber.example
	 * <pre>{@code
	 * Then the SEPA export process fails with error code "de.metas.payment.sepa.SomeError" and a message containing:
	 *   | Mandat |
	 * }</pre>
	 */
	@Then("the SEPA export process fails with error code {string} and a message containing:")
	public void assertFailsWithErrorCodeAndMessage(@NonNull final String expectedErrorCode, @NonNull final DataTable dataTable)
	{
		assertFailure(expectedErrorCode, dataTable);
	}

	/**
	 * Asserts that no {@code SEPA_Export} record exists for the pay selection, e.g. after a failed export (everything rolled back).
	 *
	 * @cucumber.example
	 * <pre>{@code
	 * Then the pay selection "paySelection" has no SEPA_Export
	 * }</pre>
	 */
	@Then("the pay selection {string} has no SEPA_Export")
	public void assertNoSepaExport(@NonNull final String paySelectionIdentifier)
	{
		assertThat(retrieveSepaExports(paySelectionIdentifier)).as("SEPA_Export records of pay selection " + paySelectionIdentifier).isEmpty();
	}

	/**
	 * Asserts the {@code SEPA_Export} records of the pay selection (matched via AD_Table_ID/Record_ID), order-independent; a duplicate record counts.
	 * <ul>
	 *   <li><b>SEPA_Protocol</b> - the {@code SEPA_Export.SEPA_Protocol} code</li>
	 *   <li><b>NumberOfLines</b> - not a column of SEPA_Export: the number of its {@code SEPA_Export_Line} rows</li>
	 * </ul>
	 *
	 * @cucumber.example
	 * <pre>{@code
	 * Then the pay selection "paySelection" has SEPA_Export records:
	 *   | SEPA_Protocol | NumberOfLines |
	 *   | pain.008.001.08 | 2            |
	 * }</pre>
	 */
	@Then("the pay selection {string} has SEPA_Export records:")
	public void assertSepaExports(@NonNull final String paySelectionIdentifier, @NonNull final DataTable dataTable)
	{
		final List<String> actual = new ArrayList<>();
		for (final I_SEPA_Export export : retrieveSepaExports(paySelectionIdentifier))
		{
			final int numberOfLines = queryBL.createQueryBuilder(I_SEPA_Export_Line.class)
					.addEqualsFilter(I_SEPA_Export_Line.COLUMN_SEPA_Export_ID, export.getSEPA_Export_ID())
					.create()
					.count();
			actual.add(export.getSEPA_Protocol() + "|" + numberOfLines);
		}

		final List<String> expected = new ArrayList<>();
		DataTableRows.of(dataTable).forEach(row -> expected.add(expectedValue(row, "SEPA_Protocol") + "|" + row.getAsInt("NumberOfLines")));

		assertThat(actual).as("SEPA_Export records (SEPA_Protocol|NumberOfLines) of pay selection " + paySelectionIdentifier).containsExactlyInAnyOrderElementsOf(expected);
	}

	// ---------------------------------------------------------------------------------------------

	private void assertFailure(@Nullable final String expectedErrorCode, @NonNull final DataTable dataTable)
	{
		assertThat(result).as("the SEPA export process was run").isNotNull();
		assertThat(result.isError()).as("the SEPA export process must fail; summary=" + result.getSummary()).isTrue();
		assertThat(result.getReportData()).as("a failed SEPA export must carry no report data").isNull();

		final Throwable throwable = result.getThrowable();
		final StringBuilder messages = new StringBuilder();
		if (result.getSummary() != null)
		{
			messages.append(result.getSummary()).append('\n');
		}
		if (throwable != null)
		{
			messages.append(throwable.getLocalizedMessage());
		}

		if (expectedErrorCode != null)
		{
			assertThat(throwable).as("the failure must carry an exception").isNotNull();
			assertThat(AdempiereException.extractErrorCodeOrNull(throwable)).as("errorCode of " + throwable).isEqualTo(expectedErrorCode);
		}

		final SoftAssertions softly = new SoftAssertions();
		for (final List<String> row : dataTable.asLists())
		{
			for (final String cell : row)
			{
				assertThat(cell).as("empty cell in the substring table").isNotNull();
				softly.assertThat(messages.toString()).as("failure message").contains(unspace(cell));
			}
		}
		softly.assertAll();
	}

	private void assertSuccess()
	{
		assertThat(result).as("the SEPA export process was run").isNotNull();
		if (result.isError())
		{
			fail("the SEPA export process must not fail; summary=" + result.getSummary(), result.getThrowable());
		}
	}

	private byte[] getFile(final String scheme)
	{
		assertSuccess();
		final byte[] file = filesByScheme.get(scheme);
		assertThat(file).as("exported file for scheme " + scheme + " (available: " + filesByScheme.keySet() + ")").isNotNull();
		return file;
	}

	private List<I_SEPA_Export> retrieveSepaExports(final String paySelectionIdentifier)
	{
		return queryBL.createQueryBuilder(I_SEPA_Export.class)
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_AD_Table_ID, getTableId(I_C_PaySelection.class))
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_Record_ID, paySelectionTable.getId(paySelectionIdentifier))
				.create()
				.list();
	}

	private void addFile(final Path dumpDir, final String name, final byte[] bytes) throws Exception
	{
		final String simpleName = Paths.get(name).getFileName().toString();
		Files.write(dumpDir.resolve(simpleName), bytes);

		final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		final DocumentBuilder db = dbf.newDocumentBuilder();
		final Document doc = db.parse(new InputSource(new ByteArrayInputStream(bytes)));

		final XPath xpath = XPathFactory.newInstance().newXPath();
		final String scheme = text(xpath, doc, "//PmtTpInf/LclInstrm/Cd");
		assertThat(filesByScheme).as("two exported files with the same scheme " + scheme + ": " + name).doesNotContainKey(scheme);
		filesByScheme.put(scheme, bytes);
		filenames.add(simpleName);
	}

	private static boolean isZip(final byte[] bytes, @Nullable final String contentType)
	{
		return (bytes.length > 1 && bytes[0] == 'P' && bytes[1] == 'K')
				|| (contentType != null && contentType.toLowerCase(Locale.ROOT).contains("zip"));
	}

	private static byte[] toBytes(final Resource resource) throws IOException
	{
		try (final InputStream is = resource.getInputStream())
		{
			return StreamUtils.copyToByteArray(is);
		}
	}

	private static String sanitize(final String s)
	{
		return s.replaceAll("[^A-Za-z0-9._-]", "_");
	}

	private static String unspace(final String cell)
	{
		return cell.replace(SPACE_TOKEN, " ");
	}

	private static String expectedValue(final DataTableRow row, final String column)
	{
		return unspace(row.getAsString(column));
	}

	/**
	 * @return whether the node is whitespace between elements (the file's formatting), as opposed to the text of an element, which is compared exactly
	 */
	private static boolean isFormattingWhitespace(@NonNull final Node node)
	{
		if (node.getNodeType() != Node.TEXT_NODE || !node.getNodeValue().trim().isEmpty())
		{
			return false;
		}
		final NodeList siblings = node.getParentNode().getChildNodes();
		for (int i = 0; i < siblings.getLength(); i++)
		{
			if (siblings.item(i).getNodeType() == Node.ELEMENT_NODE)
			{
				return true;
			}
		}
		return false;
	}

	private static ComparisonResult evaluateIgnorePlaceholder(@NonNull final Comparison comparison, @NonNull final ComparisonResult outcome)
	{
		if (outcome != ComparisonResult.EQUAL
				&& comparison.getType() == ComparisonType.TEXT_VALUE
				&& IGNORE_PLACEHOLDER.equals(comparison.getControlDetails().getValue()))
		{
			return ComparisonResult.EQUAL;
		}
		return outcome;
	}

	/**
	 * Pairs the {@code PmtInf} elements by their transaction, then everything else (and the {@code PmtInf} without an equal transaction) by name, in document order.
	 */
	private static final class PmtInfByTransactionNodeMatcher implements NodeMatcher
	{
		private final NodeMatcher byName = new DefaultNodeMatcher(ElementSelectors.byName);

		@Override
		public Iterable<Map.Entry<Node, Node>> match(final Iterable<Node> controlNodes, final Iterable<Node> testNodes)
		{
			final List<Node> unmatchedControl = new ArrayList<>();
			controlNodes.forEach(unmatchedControl::add);
			final List<Node> unmatchedTest = new ArrayList<>();
			testNodes.forEach(unmatchedTest::add);

			final List<Map.Entry<Node, Node>> matches = new ArrayList<>();
			for (final Node control : new ArrayList<>(unmatchedControl))
			{
				if (!isPmtInf(control))
				{
					continue;
				}
				final String transactionKey = toTransactionKey(control);
				final Node test = unmatchedTest.stream()
						.filter(candidate -> isPmtInf(candidate) && transactionKey.equals(toTransactionKey(candidate)))
						.findFirst()
						.orElse(null);
				if (test != null)
				{
					matches.add(new AbstractMap.SimpleImmutableEntry<>(control, test));
					unmatchedControl.remove(control);
					unmatchedTest.remove(test);
				}
			}

			byName.match(unmatchedControl, unmatchedTest).forEach(matches::add);
			return matches;
		}

		private static boolean isPmtInf(final Node node)
		{
			return node.getNodeType() == Node.ELEMENT_NODE && "PmtInf".equals(node.getLocalName());
		}

		/**
		 * @return the texts of the {@code DrctDbtTxInf} of the given {@code PmtInf}, without its {@code PmtId}, with the element names
		 */
		private static String toTransactionKey(final Node pmtInf)
		{
			final StringBuilder key = new StringBuilder();
			final NodeList children = pmtInf.getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
			{
				final Node child = children.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE && "DrctDbtTxInf".equals(child.getLocalName()))
				{
					appendLeafTexts(child, key);
				}
			}
			return key.toString();
		}

		private static void appendLeafTexts(final Node element, final StringBuilder key)
		{
			if ("PmtId".equals(element.getLocalName()))
			{
				return;
			}
			boolean hasChildElements = false;
			final NodeList children = element.getChildNodes();
			for (int i = 0; i < children.getLength(); i++)
			{
				final Node child = children.item(i);
				if (child.getNodeType() == Node.ELEMENT_NODE)
				{
					hasChildElements = true;
					appendLeafTexts(child, key);
				}
			}
			if (!hasChildElements)
			{
				key.append(element.getLocalName()).append('=').append(element.getTextContent()).append('|');
			}
		}
	}

	// --- namespace-agnostic XPath helpers: "A/B//C" -> *[local-name()='A']/*[local-name()='B']//*[local-name()='C'] ---

	private static String toLocalNamePath(final String path)
	{
		final StringBuilder sb = new StringBuilder();
		final String[] parts = path.split("/", -1);
		for (int i = 0; i < parts.length; i++)
		{
			if (i > 0)
			{
				sb.append('/');
			}
			final String part = parts[i];
			if (part.isEmpty())
			{
				continue; // from "//" or leading "/"
			}
			final int bracket = part.indexOf('[');
			final String name = bracket < 0 ? part : part.substring(0, bracket);
			final String predicate = bracket < 0 ? "" : part.substring(bracket);
			sb.append("*[local-name()='").append(name).append("']").append(predicate);
		}
		return sb.toString();
	}

	private static NodeList nodes(final XPath xpath, final Object context, final String path) throws Exception
	{
		return (NodeList)xpath.evaluate(toLocalNamePath(path), context, XPathConstants.NODESET);
	}

	/** @return the text of the first matching node; empty string if there is none */
	private static String text(final XPath xpath, final Object context, final String path) throws Exception
	{
		final NodeList list = nodes(xpath, context, path);
		return list.getLength() == 0 ? "" : list.item(0).getTextContent();
	}
}
