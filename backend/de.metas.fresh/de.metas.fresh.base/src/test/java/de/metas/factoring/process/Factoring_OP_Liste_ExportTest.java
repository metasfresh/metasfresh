/*
 * #%L
 * de.metas.fresh.base
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

package de.metas.factoring.process;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test for the {@code report_factoring_op_liste} SQL function.
 *
 * <p>Requires the local DB to be running at localhost:22432 (deep_tundra_release_db container).
 * Inserts a controlled fixture, calls the function, and validates the returned rows.
 *
 * <p>Open-amount source: {@code C_Invoice.openamt} — the denormalized open-amount column
 * maintained by the invoiceopen/invoiceopentodate plpgsql functions on every allocation event.
 * This is the same source used by other metasfresh reports and provides the real-time open amount.
 */
class Factoring_OP_Liste_ExportTest
{
	// =========================================================================
	// DB connection constants — local deep_tundra_release_db container
	// =========================================================================
	private static final String DB_URL = "jdbc:postgresql://localhost:22432/metasfresh";
	private static final String DB_USER = "metasfresh";
	private static final String DB_PASSWORD = "metasfresh";

	// Client/Org from local DB
	private static final int AD_CLIENT_ID = 1000000;
	private static final int AD_ORG_ID = 1000000;
	private static final int AD_ORG_ID_OTHER = 1000001; // a second org for exclusion tests

	// BP group required (not-null column)
	private static final int C_BP_GROUP_ID = 1000000; // "Standard" group

	// Currency IDs from local DB
	private static final int EUR_CURRENCY_ID = 102;
	private static final int USD_CURRENCY_ID = 100;

	// DocType IDs (client-specific ones found in local DB)
	private static final int ARI_DOCTYPE_ID = 1000002; // Ausgangsrechnung ARI
	private static final int ARC_DOCTYPE_ID = 1000004; // Gutschrift ARC

	// Required FK values for C_Invoice (not-null columns) — from local DB
	private static final int C_BPARTNER_LOCATION_ID = 2205175;
	private static final int C_PAYMENTTERM_ID = 1000002;
	private static final int M_PRICELIST_ID = 100;

	// date formatter for verification
	private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	// Sequence tracking for cleanup
	private final List<Long> insertedBPartnerIds = new ArrayList<>();
	private final List<Long> insertedInvoiceIds = new ArrayList<>();
	private Long insertedOrgId2 = null;

	private Connection conn;

	@BeforeAll
	static void ensureDriverLoaded()
	{
		// Ensure PostgreSQL driver is loaded
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (final ClassNotFoundException e)
		{
			throw new RuntimeException("PostgreSQL JDBC driver not found — add postgresql dependency to test classpath", e);
		}
	}

	// Unique marker to scope test data to this test run — avoids unique-constraint collisions
	// from prior runs that didn't roll back (e.g. JVM kill, test-runner crash).
	private static final String TEST_MARKER = "TST_FKOP_";

	@BeforeEach
	void openConnection() throws SQLException
	{
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		conn.setAutoCommit(true);
		cleanupStaleTestData(); // remove any data left by prior non-rolled-back runs
		conn.setAutoCommit(false); // everything in one transaction, rolled back in @AfterEach
	}

	/**
	 * Removes any C_Invoice + C_BPartner rows created by a prior test run that didn't roll back.
	 * Identified by the TEST_MARKER prefix on the BPartner.value column.
	 */
	private void cleanupStaleTestData() throws SQLException
	{
		try (final Statement st = conn.createStatement())
		{
			// invoices reference BPs — delete invoices first
			st.executeUpdate("DELETE FROM c_invoice WHERE c_bpartner_id IN"
					+ " (SELECT c_bpartner_id FROM c_bpartner WHERE value LIKE '" + TEST_MARKER + "%'"
					+ "  AND ad_client_id = " + AD_CLIENT_ID + ")");
			st.executeUpdate("DELETE FROM c_bpartner WHERE value LIKE '" + TEST_MARKER + "%'"
					+ " AND ad_client_id = " + AD_CLIENT_ID);
		}
	}

	@AfterEach
	void rollbackAndClose() throws SQLException
	{
		if (conn != null && !conn.isClosed())
		{
			conn.rollback(); // roll back all fixture inserts — leaves DB clean
			conn.close();
		}
	}

	// =========================================================================
	// Test 1: normal fixture with mixed invoices
	// =========================================================================

	/**
	 * Fixture:
	 * <ul>
	 *   <li>1 factorer BP (IsFactorer='Y', FactoringContractNo='DE00001', FactoringClientAccountId='2500000000')
	 *   <li>2 factoring customers each with 1 invoice (ARI) + 1 credit note (ARC) in EUR
	 *       — customer1 invoice partially paid (openamt != grandtotal)
	 *   <li>1 non-factoring customer with 1 invoice (must be excluded)
	 *   <li>1 invoice in USD on a factoring customer (must be excluded)
	 *   <li>1 fully-paid factoring-customer invoice (openamt = 0, must be excluded)
	 * </ul>
	 *
	 * <p>Assert total rows = 5 (1 header + 4 detail); header fields per AC3; detail rows sorted by
	 * C_BPartner.Value then DateInvoiced; each row's 11 fields match hand-computed expected values.
	 */
	@Test
	void sql_function_returns_expected_rows_for_fixture() throws SQLException
	{
		// ---- BPartners ----
		// Factorer BP
		final long factorerBpId = insertBPartner(TEST_MARKER + "FACTORER01", "Faktoring GmbH", "Y", "N",
				"DE00001", "2500000000");

		// Customer 1 — Value sorts first alphabetically within TEST_MARKER namespace
		final long cust1BpId = insertBPartner(TEST_MARKER + "CUST-AA", "Alpha Kunde GmbH", "N", "Y",
				null, null);

		// Customer 2 — Value sorts second
		final long cust2BpId = insertBPartner(TEST_MARKER + "CUST-BB", "Beta Kunde AG", "N", "Y",
				null, null);

		// Non-factoring customer (IsFactoring='N') — must be excluded
		final long nonFactoringBpId = insertBPartner(TEST_MARKER + "NONFACT01", "Nicht Faktoring GmbH", "N", "N",
				null, null);

		// ---- Invoices ----
		final LocalDate dateInv1 = LocalDate.of(2025, 9, 1);
		final LocalDate dateInv2 = LocalDate.of(2025, 9, 5);
		final LocalDate dateDue = LocalDate.of(2025, 10, 1);

		// TST_FKOP_CUST-AA: ARI invoice, grandtotal=1000.00, openamt=750.00 (partially paid)
		final long invCust1Ari = insertInvoice(cust1BpId, ARI_DOCTYPE_ID, EUR_CURRENCY_ID,
				"INV-AA-001", dateInv1, dateDue, 1000.00, 750.00);

		// CUST-AA: ARC credit note, grandtotal=200.00, openamt=200.00
		final long invCust1Arc = insertInvoice(cust1BpId, ARC_DOCTYPE_ID, EUR_CURRENCY_ID,
				"CR-AA-001", dateInv2, dateDue, 200.00, 200.00);

		// CUST-BB: ARI invoice, grandtotal=500.00, openamt=500.00
		final long invCust2Ari = insertInvoice(cust2BpId, ARI_DOCTYPE_ID, EUR_CURRENCY_ID,
				"INV-BB-001", dateInv1, dateDue, 500.00, 500.00);

		// CUST-BB: ARC credit note, grandtotal=100.00, openamt=100.00
		final long invCust2Arc = insertInvoice(cust2BpId, ARC_DOCTYPE_ID, EUR_CURRENCY_ID,
				"CR-BB-001", dateInv2, dateDue, 100.00, 100.00);

		// Non-factoring customer invoice — must be excluded
		insertInvoice(nonFactoringBpId, ARI_DOCTYPE_ID, EUR_CURRENCY_ID,
				"INV-NF-001", dateInv1, dateDue, 300.00, 300.00);

		// USD invoice on cust1 — must be excluded (wrong currency)
		insertInvoice(cust1BpId, ARI_DOCTYPE_ID, USD_CURRENCY_ID,
				"INV-USD-001", dateInv1, dateDue, 400.00, 400.00);

		// Fully-paid ARI on cust2 — openamt=0, must be excluded
		insertInvoice(cust2BpId, ARI_DOCTYPE_ID, EUR_CURRENCY_ID,
				"INV-BB-PAID", dateInv1, dateDue, 600.00, 0.00);

		conn.commit(); // commit so the function sees the data (function runs in same session)
		conn.setAutoCommit(false); // keep transaction boundary for rollback in @AfterEach

		// ---- Call function ----
		final List<String[]> rows = callFunction(EUR_CURRENCY_ID, AD_ORG_ID, AD_CLIENT_ID);

		// ---- Assertions ----
		// 1 header + 4 detail rows
		assertThat(rows).as("total rows (1 header + 4 detail)").hasSize(5);

		// Header row (row_type='01')
		final String[] header = rows.get(0);
		assertThat(header[0]).as("header row_type").isEqualTo("01");
		assertThat(header[1]).as("header col_1 = SAF").isEqualTo("SAF");
		assertThat(header[2]).as("header col_2 = EFAG").isEqualTo("EFAG");
		assertThat(header[3]).as("header col_3 = FactoringContractNo").isEqualTo("DE00001");
		assertThat(header[4]).as("header col_4 = FactoringClientAccountId").isEqualTo("2500000000");
		assertThat(header[5]).as("header col_5 = currency ISO3").isEqualTo("EUR");
		assertThat(header[6]).as("header col_6 = empty (technical semicolon)").isEqualTo("");
		// header col_7 = today's date
		final String todayFormatted = LocalDate.now().format(DD_MM_YYYY);
		assertThat(header[7]).as("header col_7 = upload date (today)").isEqualTo(todayFormatted);
		// header col_8 = row count including header = 5 → "5,00"
		assertThat(header[8]).as("header col_8 = row count '5,00'").isEqualTo("5,00");
		// header col_9 = sum of D-rows Rechnungsbetrag = 1000+500 = 1500 → "1500,00"
		assertThat(header[9]).as("header col_9 = sum D Rechnungsbetrag").isEqualTo("1500,00");
		// header col_10 = sum of C-rows Rechnungsbetrag = 200+100 = 300 → "300,00"
		assertThat(header[10]).as("header col_10 = sum C Rechnungsbetrag").isEqualTo("300,00");

		// Detail rows — sorted by C_BPartner.Value then DateInvoiced
		// Row 1: CUST-AA INV-AA-001 (dateInv1=2025-09-01, ARI → D)
		final String[] r1 = rows.get(1);
		assertThat(r1[0]).as("detail[1] row_type").isEqualTo("02");
		assertThat(r1[1]).as("detail[1] Debitorennummer").isEqualTo(TEST_MARKER + "CUST-AA");
		assertThat(r1[2]).as("detail[1] Name").isEqualTo("Alpha Kunde GmbH");
		assertThat(r1[3]).as("detail[1] Dokumentennummer").isEqualTo("INV-AA-001");
		assertThat(r1[4]).as("detail[1] Dokumentendatum").isEqualTo("01.09.2025");
		assertThat(r1[5]).as("detail[1] Fälligkeitsdatum").isEqualTo("01.10.2025");
		assertThat(r1[6]).as("detail[1] Währung").isEqualTo("EUR");
		assertThat(r1[7]).as("detail[1] Rechnungsbetrag").isEqualTo("1000,00");
		assertThat(r1[8]).as("detail[1] Offener Rechnungsbetrag").isEqualTo("750,00");
		assertThat(r1[9]).as("detail[1] D/C flag").isEqualTo("D");
		assertThat(r1[10]).as("detail[1] trailing empty").isEqualTo("");

		// Row 2: CUST-AA CR-AA-001 (dateInv2=2025-09-05, ARC → C)
		final String[] r2 = rows.get(2);
		assertThat(r2[0]).as("detail[2] row_type").isEqualTo("02");
		assertThat(r2[1]).as("detail[2] Debitorennummer").isEqualTo(TEST_MARKER + "CUST-AA");
		assertThat(r2[3]).as("detail[2] Dokumentennummer").isEqualTo("CR-AA-001");
		assertThat(r2[4]).as("detail[2] Dokumentendatum").isEqualTo("05.09.2025");
		assertThat(r2[7]).as("detail[2] Rechnungsbetrag").isEqualTo("200,00");
		assertThat(r2[8]).as("detail[2] Offener Rechnungsbetrag").isEqualTo("200,00");
		assertThat(r2[9]).as("detail[2] D/C flag").isEqualTo("C");

		// Row 3: CUST-BB INV-BB-001 (dateInv1=2025-09-01, ARI → D)
		final String[] r3 = rows.get(3);
		assertThat(r3[1]).as("detail[3] Debitorennummer").isEqualTo(TEST_MARKER + "CUST-BB");
		assertThat(r3[3]).as("detail[3] Dokumentennummer").isEqualTo("INV-BB-001");
		assertThat(r3[7]).as("detail[3] Rechnungsbetrag").isEqualTo("500,00");
		assertThat(r3[8]).as("detail[3] Offener Rechnungsbetrag").isEqualTo("500,00");
		assertThat(r3[9]).as("detail[3] D/C flag").isEqualTo("D");

		// Row 4: CUST-BB CR-BB-001 (dateInv2=2025-09-05, ARC → C)
		final String[] r4 = rows.get(4);
		assertThat(r4[1]).as("detail[4] Debitorennummer").isEqualTo(TEST_MARKER + "CUST-BB");
		assertThat(r4[3]).as("detail[4] Dokumentennummer").isEqualTo("CR-BB-001");
		assertThat(r4[7]).as("detail[4] Rechnungsbetrag").isEqualTo("100,00");
		assertThat(r4[8]).as("detail[4] Offener Rechnungsbetrag").isEqualTo("100,00");
		assertThat(r4[9]).as("detail[4] D/C flag").isEqualTo("C");
	}

	// =========================================================================
	// Test 2: header-only result when no matches
	// =========================================================================

	/**
	 * Fixture: only excluded records (no factoring-customer invoices in EUR for this org).
	 * Assert exactly 1 row returned (the header), row-count field = '1,00', totals = '0,00'.
	 */
	@Test
	void sql_function_returns_header_only_when_no_matches() throws SQLException
	{
		// Factorer BP (required for the header)
		insertBPartner(TEST_MARKER + "FACTORER02", "Faktoring GmbH 2", "Y", "N",
				"DE00002", "9999999999");

		// Non-factoring customer — must be excluded
		final long nonFactoringBpId = insertBPartner(TEST_MARKER + "NONFACT02", "Kein Faktor GmbH", "N", "N",
				null, null);
		insertInvoice(nonFactoringBpId, ARI_DOCTYPE_ID, EUR_CURRENCY_ID,
				"INV-EXCL-001", LocalDate.of(2025, 9, 1), LocalDate.of(2025, 10, 1), 500.00, 500.00);

		conn.commit();
		conn.setAutoCommit(false);

		final List<String[]> rows = callFunction(EUR_CURRENCY_ID, AD_ORG_ID, AD_CLIENT_ID);

		// Exactly 1 row (header only)
		assertThat(rows).as("only header row when no matching invoices").hasSize(1);

		final String[] header = rows.get(0);
		assertThat(header[0]).as("header row_type").isEqualTo("01");
		assertThat(header[8]).as("row count = 1,00 (header only)").isEqualTo("1,00");
		assertThat(header[9]).as("sum D = 0,00").isEqualTo("0,00");
		assertThat(header[10]).as("sum C = 0,00").isEqualTo("0,00");
	}

	// =========================================================================
	// Helpers
	// =========================================================================

	/**
	 * Calls the SQL function and returns all rows as a list of String arrays (11 columns each).
	 * The row_type is stored at index 0; col_1..col_11 at indices 1..11.
	 */
	private List<String[]> callFunction(final int currencyId, final int orgId, final int clientId) throws SQLException
	{
		final List<String[]> rows = new ArrayList<>();
		final String sql = "SELECT row_type, col_1, col_2, col_3, col_4, col_5, col_6, col_7, col_8, col_9, col_10, col_11"
				+ " FROM report_factoring_op_liste(?, ?, ?)";
		try (final PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, currencyId);
			ps.setInt(2, orgId);
			ps.setInt(3, clientId);
			try (final ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					final String[] row = new String[12];
					row[0] = rs.getString("row_type").trim();
					for (int i = 1; i <= 11; i++)
					{
						final String val = rs.getString("col_" + i);
						row[i] = val != null ? val : "";
					}
					rows.add(row);
				}
			}
		}
		return rows;
	}

	/**
	 * Inserts a C_BPartner row for the test fixture and tracks its ID for cleanup.
	 *
	 * @return the generated c_bpartner_id
	 */
	private long insertBPartner(
			final String value,
			final String name,
			final String isFactorer,
			final String isFactoring,
			final String factoringContractNo,
			final String factoringClientAccountId) throws SQLException
	{
		final String sql = "INSERT INTO c_bpartner"
				+ " (c_bpartner_id, ad_client_id, ad_org_id, c_bp_group_id, value, name, isfactorer, isfactoring,"
				+ "  factoringcontractno, factoringclientaccountid,"
				+ "  isactive, iscustomer, isvendor, isonetime,"
				+ "  created, updated, createdby, updatedby)"
				+ " VALUES"
				+ " (nextval('c_bpartner_seq'), ?, ?, ?, ?, ?, ?, ?,"
				+ "  ?, ?,"
				+ "  'Y', 'Y', 'N', 'N',"
				+ "  now(), now(), 0, 0)"
				+ " RETURNING c_bpartner_id";

		try (final PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, AD_CLIENT_ID);
			ps.setInt(2, AD_ORG_ID);
			ps.setInt(3, C_BP_GROUP_ID);
			ps.setString(4, value);
			ps.setString(5, name);
			ps.setString(6, isFactorer);
			ps.setString(7, isFactoring);
			ps.setString(8, factoringContractNo);
			ps.setString(9, factoringClientAccountId);
			try (final ResultSet rs = ps.executeQuery())
			{
				rs.next();
				final long id = rs.getLong(1);
				insertedBPartnerIds.add(id);
				return id;
			}
		}
	}

	/**
	 * Inserts a C_Invoice row (minimal fields for the function to work).
	 *
	 * @return the generated c_invoice_id
	 */
	private long insertInvoice(
			final long bpartnerId,
			final int docTypeId,
			final int currencyId,
			final String documentNo,
			final LocalDate dateInvoiced,
			final LocalDate dueDate,
			final double grandTotal,
			final double openAmt) throws SQLException
	{
		// All NOT NULL columns are provided; defaults handle the rest.
		// ispaid = Y if openAmt == 0, else N
		final String isPaid = (openAmt == 0.0) ? "Y" : "N";

		final String sql = "INSERT INTO c_invoice"
				+ " (c_invoice_id, ad_client_id, ad_org_id, c_bpartner_id, c_bpartner_location_id,"
				+ "  c_doctype_id, c_doctypetarget_id, c_currency_id, c_paymentterm_id, m_pricelist_id,"
				+ "  documentno, dateinvoiced, dateacct, duedate,"
				+ "  grandtotal, totallines, openamt, ispaid,"
				+ "  docstatus, docaction, paymentrule, issotrx, isfinancial,"
				+ "  created, updated, createdby, updatedby)"
				+ " VALUES"
				+ " (nextval('c_invoice_seq'), ?, ?, ?, ?,"
				+ "  ?, ?, ?, ?, ?,"
				+ "  ?, ?, ?, ?,"
				+ "  ?, ?, ?, ?,"
				+ "  'CO', 'CL', 'B', 'Y', 'Y',"
				+ "  now(), now(), 0, 0)"
				+ " RETURNING c_invoice_id";

		try (final PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, AD_CLIENT_ID);
			ps.setInt(2, AD_ORG_ID);
			ps.setLong(3, bpartnerId);
			ps.setInt(4, C_BPARTNER_LOCATION_ID);
			ps.setInt(5, docTypeId);
			ps.setInt(6, docTypeId); // c_doctypetarget_id — same as c_doctype_id for test fixture
			ps.setInt(7, currencyId);
			ps.setInt(8, C_PAYMENTTERM_ID);
			ps.setInt(9, M_PRICELIST_ID);
			ps.setString(10, documentNo);
			ps.setTimestamp(11, Timestamp.valueOf(dateInvoiced.atStartOfDay()));
			ps.setTimestamp(12, Timestamp.valueOf(dateInvoiced.atStartOfDay())); // dateacct = dateinvoiced
			ps.setTimestamp(13, Timestamp.valueOf(dueDate.atStartOfDay()));
			ps.setBigDecimal(14, java.math.BigDecimal.valueOf(grandTotal));
			ps.setBigDecimal(15, java.math.BigDecimal.valueOf(grandTotal)); // totallines
			ps.setBigDecimal(16, java.math.BigDecimal.valueOf(openAmt));
			ps.setString(17, isPaid);
			try (final ResultSet rs = ps.executeQuery())
			{
				rs.next();
				final long id = rs.getLong(1);
				insertedInvoiceIds.add(id);
				return id;
			}
		}
	}
}
