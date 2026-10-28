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
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Runs the SEPA export process ({@code C_PaySelection_SEPA_XmlExport}) and asserts the produced XML / ZIP files.
 * <p>
 * XPath is evaluated namespace-agnostically (via {@code local-name()}), so the {@code Namespace} column of the header step is the only namespace assertion.
 * In every expected-value cell, the token {@code <SPACE>} stands for one space (Cucumber trims table cells).
 * Every exported file is dumped to {@code target/sepa-export-files/<pay selection identifier>/<filename>}.
 */
@RequiredArgsConstructor
public class SEPA_Export_StepDef
{
	private static final String SPACE_TOKEN = "<SPACE>";
	private static final String ABSENT = "-";
	private static final String NOT_PROVIDED = "NOTPROVIDED";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	private final @NonNull C_PaySelection_StepDefData paySelectionTable;

	@Nullable private ProcessExecutionResult result;
	@Nullable private Throwable thrown;
	/** parsed documents of the last run, by scheme = text of {@code PmtTpInf/LclInstrm/Cd} */
	private final Map<String, Document> documentsByScheme = new LinkedHashMap<>();
	private final List<String> filenames = new ArrayList<>();

	@When("the SEPA export process is run for pay selection {string}")
	public void run(@NonNull final String paySelectionIdentifier) throws Exception
	{
		result = null;
		thrown = null;
		documentsByScheme.clear();
		filenames.clear();

		final int paySelectionId = paySelectionTable.getId(paySelectionIdentifier).getRepoId();

		try
		{
			result = ProcessInfo.builder()
					.setCtx(Env.getCtx())
					.setAD_Process_ID(adProcessDAO.retrieveProcessIdByClass(C_PaySelection_SEPA_XmlExport.class))
					.setRecord(I_C_PaySelection.Table_Name, paySelectionId)
					// the process defaults; SEPA_Protocol stays unset
					.addParameter("IsGroupTransactions", false)
					.addParameter("ReferenceAsEndToEndId", false)
					.buildAndPrepareExecution()
					.executeSync()
					.getResult();
		}
		catch (final Throwable t)
		{
			thrown = t;
			return;
		}

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
					final byte[] entryBytes = toBytes(zis);
					addFile(dumpDir, entry.getName(), entryBytes);
				}
			}
		}
		else
		{
			addFile(dumpDir, filename, bytes);
		}
	}

	@Then("the SEPA export result is a single XML file")
	public void assertSingleXmlFile()
	{
		assertSuccess();
		assertThat(filenames).as("exported files").hasSize(1);
		assertThat(filenames.get(0)).as("exported file name").endsWith(".xml");
	}

	@Then("the SEPA export result is a ZIP with {int} XML files")
	public void assertZipWithXmlFiles(final int expectedCount)
	{
		assertSuccess();
		assertThat(filenames).as("exported files").hasSize(expectedCount);
		assertThat(filenames).as("exported file names").allMatch(name -> name.endsWith(".xml"));
	}

	@Then("the SEPA direct debit file for scheme {word} has header:")
	public void assertHeader(@NonNull final String scheme, @NonNull final DataTable dataTable) throws Exception
	{
		final Document doc = getDocument(scheme);
		final Map<String, String> expected = dataTable.asMaps().get(0);
		final XPath xpath = XPathFactory.newInstance().newXPath();
		final SoftAssertions softly = new SoftAssertions();

		softly.assertThat(doc.getDocumentElement().getNamespaceURI()).as("Namespace").isEqualTo(expectedValue(expected, "Namespace"));

		final int expectedNbOfTxs = Integer.parseInt(expectedValue(expected, "NbOfTxs"));
		final BigDecimal expectedCtrlSum = new BigDecimal(expectedValue(expected, "CtrlSum"));

		final NodeList txs = nodes(xpath, doc, "//DrctDbtTxInf");
		final NodeList pmtInfs = nodes(xpath, doc, "//PmtInf");
		BigDecimal txSum = BigDecimal.ZERO;
		for (int i = 0; i < txs.getLength(); i++)
		{
			txSum = txSum.add(new BigDecimal(text(xpath, txs.item(i), "InstdAmt")));
		}

		softly.assertThat(Integer.parseInt(text(xpath, doc, "//GrpHdr/NbOfTxs"))).as("GrpHdr/NbOfTxs").isEqualTo(expectedNbOfTxs);
		softly.assertThat(txs.getLength()).as("number of DrctDbtTxInf").isEqualTo(expectedNbOfTxs);
		assertDecimal(softly, "GrpHdr/CtrlSum", new BigDecimal(text(xpath, doc, "//GrpHdr/CtrlSum")), expectedCtrlSum);
		assertDecimal(softly, "sum of DrctDbtTxInf/InstdAmt", txSum, expectedCtrlSum);

		// D3: one PmtInf per transaction, no batch booking
		softly.assertThat(pmtInfs.getLength()).as("number of PmtInf").isEqualTo(expectedNbOfTxs);
		softly.assertThat(nodes(xpath, doc, "//BtchBookg").getLength()).as("number of BtchBookg elements").isZero();

		for (int i = 0; i < pmtInfs.getLength(); i++)
		{
			final Node pmtInf = pmtInfs.item(i);
			final String p = "PmtInf[" + (i + 1) + "]";
			final NodeList pmtInfTxs = nodes(xpath, pmtInf, "DrctDbtTxInf");
			softly.assertThat(pmtInfTxs.getLength()).as(p + " number of DrctDbtTxInf").isEqualTo(1);
			softly.assertThat(text(xpath, pmtInf, "NbOfTxs")).as(p + "/NbOfTxs").isEqualTo("1");
			if (pmtInfTxs.getLength() == 1)
			{
				assertDecimal(softly, p + "/CtrlSum", new BigDecimal(text(xpath, pmtInf, "CtrlSum")), new BigDecimal(text(xpath, pmtInfTxs.item(0), "InstdAmt")));
			}

			softly.assertThat(text(xpath, pmtInf, "Cdtr/Nm")).as(p + "/Cdtr/Nm").isEqualTo(expectedValue(expected, "Cdtr.Nm"));
			softly.assertThat(text(xpath, pmtInf, "CdtrSchmeId//Othr/Id")).as(p + "/CdtrSchmeId").isEqualTo(expectedValue(expected, "CdtrSchmeId"));
			softly.assertThat(text(xpath, pmtInf, "ReqdColltnDt")).as(p + "/ReqdColltnDt").isEqualTo(expectedValue(expected, "ReqdColltnDt"));

			if (expected.containsKey("Cdtr.PstlAdr"))
			{
				final String expectedAdr = expected.get("Cdtr.PstlAdr");
				if (ABSENT.equals(expectedAdr))
				{
					softly.assertThat(nodes(xpath, pmtInf, "Cdtr/PstlAdr").getLength()).as(p + "/Cdtr/PstlAdr is absent").isZero();
				}
				else
				{
					softly.assertThat(nodes(xpath, pmtInf, "Cdtr/PstlAdr").getLength()).as(p + "/Cdtr/PstlAdr is present").isEqualTo(1);
				}
			}
			assertOptionalElement(softly, xpath, pmtInf, p, expected, "Cdtr.PstlAdr.TwnNm", "Cdtr/PstlAdr/TwnNm");
			assertOptionalElement(softly, xpath, pmtInf, p, expected, "Cdtr.PstlAdr.Ctry", "Cdtr/PstlAdr/Ctry");
			assertOptionalElement(softly, xpath, pmtInf, p, expected, "Cdtr.PstlAdr.StrtNm", "Cdtr/PstlAdr/StrtNm");
			assertOptionalElement(softly, xpath, pmtInf, p, expected, "Cdtr.PstlAdr.PstCd", "Cdtr/PstlAdr/PstCd");
		}

		softly.assertAll();
	}

	@Then("the SEPA direct debit file for scheme {word} contains exactly the transactions:")
	public void assertTransactions(@NonNull final String scheme, @NonNull final DataTable dataTable) throws Exception
	{
		final Document doc = getDocument(scheme);
		final XPath xpath = XPathFactory.newInstance().newXPath();

		final List<Map<String, String>> actualRows = new ArrayList<>();
		final NodeList txs = nodes(xpath, doc, "//DrctDbtTxInf");
		for (int i = 0; i < txs.getLength(); i++)
		{
			final Node tx = txs.item(i);
			final Node pmtInf = tx.getParentNode();
			final Map<String, String> row = new LinkedHashMap<>();
			row.put("Dbtr.Nm", text(xpath, tx, "Dbtr/Nm"));
			row.put("DbtrAcct.IBAN", text(xpath, tx, "DbtrAcct/Id/IBAN"));
			row.put("InstdAmt", text(xpath, tx, "InstdAmt"));
			row.put("MndtId", text(xpath, tx, "DrctDbtTx/MndtRltdInf/MndtId"));
			row.put("DtOfSgntr", text(xpath, tx, "DrctDbtTx/MndtRltdInf/DtOfSgntr"));
			row.put("SeqTp", text(xpath, pmtInf, "PmtTpInf/SeqTp"));
			row.put("DbtrAgt", extractDebtorAgent(xpath, tx));
			row.put("Ustrd", nodes(xpath, tx, "RmtInf/Ustrd").getLength() == 0 ? ABSENT : text(xpath, tx, "RmtInf/Ustrd"));
			actualRows.add(row);
		}

		final List<Map<String, String>> expectedRows = dataTable.asMaps();
		final List<Map<String, String>> unmatchedActual = new ArrayList<>(actualRows);
		final List<Map<String, String>> unmatchedExpected = new ArrayList<>();
		for (final Map<String, String> expected : expectedRows)
		{
			final Map<String, String> match = unmatchedActual.stream().filter(actual -> rowMatches(expected, actual)).findFirst().orElse(null);
			if (match == null)
			{
				unmatchedExpected.add(expected);
			}
			else
			{
				unmatchedActual.remove(match);
			}
		}

		assertThat(unmatchedExpected).as("expected transactions not found in the file (file has: " + actualRows + ")").isEmpty();
		assertThat(unmatchedActual).as("file transactions not matched by any expected row").isEmpty();
	}

	@Then("the SEPA export process fails with a message containing:")
	public void assertFailsWithMessage(@NonNull final DataTable dataTable)
	{
		assertFailure(null, dataTable);
	}

	@Then("the SEPA export process fails with error code {string} and a message containing:")
	public void assertFailsWithErrorCodeAndMessage(@NonNull final String expectedErrorCode, @NonNull final DataTable dataTable)
	{
		assertFailure(expectedErrorCode, dataTable);
	}

	@Then("the pay selection {string} has no SEPA_Export")
	public void assertNoSepaExport(@NonNull final String paySelectionIdentifier)
	{
		assertThat(retrieveSepaExports(paySelectionIdentifier)).as("SEPA_Export records of pay selection " + paySelectionIdentifier).isEmpty();
	}

	@Then("the pay selection {string} has SEPA_Export records:")
	public void assertSepaExports(@NonNull final String paySelectionIdentifier, @NonNull final DataTable dataTable)
	{
		final Set<String> actual = new TreeSet<>();
		for (final I_SEPA_Export export : retrieveSepaExports(paySelectionIdentifier))
		{
			final int numberOfLines = queryBL.createQueryBuilder(I_SEPA_Export_Line.class)
					.addEqualsFilter(I_SEPA_Export_Line.COLUMN_SEPA_Export_ID, export.getSEPA_Export_ID())
					.create()
					.count();
			actual.add(export.getSEPA_Protocol() + "|" + numberOfLines);
		}

		final Set<String> expected = new TreeSet<>();
		for (final Map<String, String> row : dataTable.asMaps())
		{
			expected.add(expectedValue(row, "SEPA_Protocol") + "|" + Integer.parseInt(expectedValue(row, "NumberOfLines")));
		}

		assertThat(actual).as("SEPA_Export records (SEPA_Protocol|NumberOfLines) of pay selection " + paySelectionIdentifier).isEqualTo(expected);
	}

	// ---------------------------------------------------------------------------------------------

	private void assertFailure(@Nullable final String expectedErrorCode, @NonNull final DataTable dataTable)
	{
		assertThat(result != null || thrown != null).as("the SEPA export process was run").isTrue();
		if (result != null)
		{
			assertThat(result.isError()).as("the SEPA export process must fail; summary=" + result.getSummary()).isTrue();
			assertThat(result.getReportData()).as("a failed SEPA export must carry no report data").isNull();
		}

		final Throwable throwable = result != null && result.getThrowable() != null ? result.getThrowable() : thrown;
		final StringBuilder messages = new StringBuilder();
		if (result != null && result.getSummary() != null)
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
				softly.assertThat(messages.toString()).as("failure message").contains(unspace(cell));
			}
		}
		softly.assertAll();
	}

	private void assertSuccess()
	{
		assertThat(thrown).as("the SEPA export process must not throw").isNull();
		assertThat(result).as("the SEPA export process was run").isNotNull();
		assertThat(result.isError()).as("the SEPA export process must not fail; summary=" + result.getSummary()).isFalse();
	}

	private Document getDocument(final String scheme)
	{
		assertSuccess();
		final Document doc = documentsByScheme.get(scheme);
		assertThat(doc).as("exported file for scheme " + scheme + " (available: " + documentsByScheme.keySet() + ")").isNotNull();
		return doc;
	}

	private List<I_SEPA_Export> retrieveSepaExports(final String paySelectionIdentifier)
	{
		return queryBL.createQueryBuilder(I_SEPA_Export.class)
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_AD_Table_ID, getTableId(I_C_PaySelection.class))
				.addEqualsFilter(I_SEPA_Export.COLUMNNAME_Record_ID, paySelectionTable.getId(paySelectionIdentifier).getRepoId())
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
		assertThat(documentsByScheme).as("two exported files with the same scheme " + scheme + ": " + name).doesNotContainKey(scheme);
		documentsByScheme.put(scheme, doc);
		filenames.add(simpleName);
	}

	private static boolean isZip(final byte[] bytes, @Nullable final String contentType)
	{
		return (bytes.length > 1 && bytes[0] == 'P' && bytes[1] == 'K')
				|| (contentType != null && contentType.toLowerCase().contains("zip"));
	}

	private static byte[] toBytes(final Resource resource) throws IOException
	{
		try (final InputStream is = resource.getInputStream())
		{
			return toBytes(is);
		}
	}

	private static byte[] toBytes(final InputStream is) throws IOException
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] buf = new byte[8192];
		int n;
		while ((n = is.read(buf)) > 0)
		{
			out.write(buf, 0, n);
		}
		return out.toByteArray();
	}

	private static String sanitize(final String s)
	{
		return s.replaceAll("[^A-Za-z0-9._-]", "_");
	}

	private static String unspace(final String cell)
	{
		return cell.replace(SPACE_TOKEN, " ");
	}

	private static String expectedValue(final Map<String, String> row, final String column)
	{
		final String value = row.get(column);
		assertThat(value).as("column " + column).isNotNull();
		return unspace(value);
	}

	private static void assertDecimal(final SoftAssertions softly, final String desc, final BigDecimal actual, final BigDecimal expected)
	{
		softly.assertThat(actual.compareTo(expected)).as(desc + ": expected " + expected + " but was " + actual).isZero();
	}

	private static void assertOptionalElement(final SoftAssertions softly, final XPath xpath, final Node context, final String prefix, final Map<String, String> expected, final String column, final String path) throws Exception
	{
		if (!expected.containsKey(column))
		{
			return;
		}
		final String expectedCell = expected.get(column);
		if (ABSENT.equals(expectedCell))
		{
			softly.assertThat(nodes(xpath, context, path).getLength()).as(prefix + "/" + path + " is absent").isZero();
		}
		else
		{
			softly.assertThat(nodes(xpath, context, path).getLength()).as(prefix + "/" + path + " is present").isEqualTo(1);
			softly.assertThat(text(xpath, context, path)).as(prefix + "/" + path).isEqualTo(unspace(expectedCell));
		}
	}

	private static String extractDebtorAgent(final XPath xpath, final Node tx) throws Exception
	{
		for (final String path : new String[] { "DbtrAgt/FinInstnId/BICFI", "DbtrAgt/FinInstnId/BIC", "DbtrAgt/FinInstnId/Othr/Id" })
		{
			if (nodes(xpath, tx, path).getLength() > 0)
			{
				return text(xpath, tx, path);
			}
		}
		return ABSENT;
	}

	private static boolean rowMatches(final Map<String, String> expected, final Map<String, String> actual)
	{
		for (final Map.Entry<String, String> e : expected.entrySet())
		{
			final String column = e.getKey();
			final String expectedValue = unspace(e.getValue());
			final String actualValue = actual.get(column);
			if (actualValue == null)
			{
				return false;
			}
			if ("InstdAmt".equals(column))
			{
				if (new BigDecimal(expectedValue).compareTo(new BigDecimal(actualValue)) != 0)
				{
					return false;
				}
			}
			else if (!Objects.equals(expectedValue, actualValue))
			{
				return false;
			}
		}
		return true;
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
