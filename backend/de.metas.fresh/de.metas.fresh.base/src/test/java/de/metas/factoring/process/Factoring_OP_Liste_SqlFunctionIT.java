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
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for the {@code report_factoring_op_liste} PostgreSQL set-returning function.
 *
 * <p>Requires the local metasfresh DB container at {@code localhost:22432}. Skipped on CI runners
 * (no such DB) via an availability probe in {@code @BeforeEach}. Companion to the mocked unit
 * tests in {@link Factoring_OP_Liste_ExportTest}, which cover the JavaProcess business logic
 * without requiring a live database.
 *
 * <p>Fixture rows are prefixed with {@link #TEST_MARKER} on {@code C_BPartner.Value} so cleanup
 * scoped to prior non-rolled-back runs targets only this test class's data.
 */
class Factoring_OP_Liste_SqlFunctionIT
{
	// DB connection — the local deep_tundra_release stack container maps to port 22432
	private static final String DB_URL = "jdbc:postgresql://localhost:22432/metasfresh";
	private static final String DB_USER = "metasfresh";
	private static final String DB_PASSWORD = "metasfresh";

	// Client/Org from the local DB
	private static final int AD_CLIENT_ID = 1000000;
	private static final int AD_ORG_ID = 1000000;

	// BP group (non-null column)
	private static final int C_BP_GROUP_ID = 1000000;

	// Currency IDs
	private static final int EUR_CURRENCY_ID = 102;

	// DocType IDs (client-specific values from local DB)
	private static final int ARI_DOCTYPE_ID = 1000002;
	private static final int ARC_DOCTYPE_ID = 1000004;

	// Required FK values for C_Invoice (non-null columns)
	private static final int C_BPARTNER_LOCATION_ID = 2205175;
	private static final int C_PAYMENTTERM_ID = 1000002;
	private static final int M_PRICELIST_ID = 100;

	private static final DateTimeFormatter DD_MM_YYYY = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	// Unique marker to scope test data to this test run — avoids collisions with prior crashed runs.
	private static final String TEST_MARKER = "TST_FKOP_IT_";

	private final List<Long> insertedBPartnerIds = new ArrayList<>();
	private final List<Long> insertedInvoiceIds = new ArrayList<>();

	private Connection conn;

	@BeforeAll
	static void ensureDriverLoaded()
	{
		try
		{
			Class.forName("org.postgresql.Driver");
		}
		catch (final ClassNotFoundException e)
		{
			throw new RuntimeException("PostgreSQL JDBC driver not found", e);
		}
	}

	private static boolean isLocalDbAvailable()
	{
		try (Connection probe = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD))
		{
			return probe.isValid(2);
		}
		catch (final SQLException e)
		{
			return false;
		}
	}

	@BeforeEach
	void openConnection() throws SQLException
	{
		Assumptions.assumeTrue(isLocalDbAvailable(),
				"Local metasfresh DB not reachable at " + DB_URL + " — skipping SQL-function IT.");
		conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		conn.setAutoCommit(true);
		cleanupStaleTestData();
		conn.setAutoCommit(false);
	}

	@AfterEach
	void rollbackAndClose() throws SQLException
	{
		if (conn != null && !conn.isClosed())
		{
			conn.rollback();
			conn.close();
		}
	}

	private void cleanupStaleTestData() throws SQLException
	{
		try (final Statement st = conn.createStatement())
		{
			// Also target the legacy prefix `TST_FKOP_` used by earlier iterations of this test class
			// (the one that lived in Factoring_OP_Liste_ExportTest before the modernization refactor).
			st.executeUpdate("DELETE FROM c_invoice WHERE c_bpartner_id IN"
					+ " (SELECT c_bpartner_id FROM c_bpartner"
					+ "  WHERE (value LIKE '" + TEST_MARKER + "%' OR value LIKE 'TST_FKOP_%')"
					+ "  AND ad_client_id = " + AD_CLIENT_ID + ")");
			st.executeUpdate("DELETE FROM c_bpartner"
					+ " WHERE (value LIKE '" + TEST_MARKER + "%' OR value LIKE 'TST_FKOP_%')"
					+ " AND ad_client_id = " + AD_CLIENT_ID);
		}
	}

	private long insertBPartner(final String value, final String name, final String isFactorer, final String isFactoring,
			final String factoringContractNo, final String factoringClientAccountId) throws SQLException
	{
		try (final PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO c_bpartner (c_bpartner_id, ad_client_id, ad_org_id, value, name, isactive, isfactorer, isfactoring,"
						+ " factoringcontractno, factoringclientaccountid, c_bp_group_id, created, createdby, updated, updatedby)"
						+ " VALUES (nextval('c_bpartner_seq'), ?, ?, ?, ?, 'Y', ?, ?, ?, ?, ?, now(), 100, now(), 100) RETURNING c_bpartner_id"))
		{
			ps.setInt(1, AD_CLIENT_ID);
			ps.setInt(2, AD_ORG_ID);
			ps.setString(3, value);
			ps.setString(4, name);
			ps.setString(5, isFactorer);
			ps.setString(6, isFactoring);
			ps.setString(7, factoringContractNo);
			ps.setString(8, factoringClientAccountId);
			ps.setInt(9, C_BP_GROUP_ID);
			try (final ResultSet rs = ps.executeQuery())
			{
				rs.next();
				final long id = rs.getLong(1);
				insertedBPartnerIds.add(id);
				return id;
			}
		}
	}

	private void insertInvoice(final long bpId, final int docTypeId, final int currencyId,
			final String documentNo, final LocalDate dateInvoiced, final LocalDate dueDate,
			final double grandTotal, final double openAmt) throws SQLException
	{
		try (final PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO c_invoice (c_invoice_id, ad_client_id, ad_org_id, c_bpartner_id, c_bpartner_location_id, c_paymentterm_id,"
						+ " m_pricelist_id, c_doctype_id, c_doctypetarget_id, c_currency_id,"
						+ " documentno, dateinvoiced, dateacct, duedate, grandtotal, openamt,"
						+ " issotrx, ispayschedulevalid, ispaid, isprinted, isdiscountprinted, istransferred,"
						+ " isactive, docstatus, docaction, paymentrule, invoicecollectiontype,"
						+ " created, createdby, updated, updatedby)"
						+ " VALUES (nextval('c_invoice_seq'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
						+ " 'Y', 'N', 'N', 'N', 'N', 'N', 'Y', 'CO', 'CL', 'B', 'E',"
						+ " now(), 100, now(), 100) RETURNING c_invoice_id"))
		{
			ps.setInt(1, AD_CLIENT_ID);
			ps.setInt(2, AD_ORG_ID);
			ps.setLong(3, bpId);
			ps.setInt(4, C_BPARTNER_LOCATION_ID);
			ps.setInt(5, C_PAYMENTTERM_ID);
			ps.setInt(6, M_PRICELIST_ID);
			ps.setInt(7, docTypeId);
			ps.setInt(8, docTypeId);
			ps.setInt(9, currencyId);
			ps.setString(10, documentNo);
			ps.setDate(11, java.sql.Date.valueOf(dateInvoiced));
			ps.setDate(12, java.sql.Date.valueOf(dateInvoiced));
			ps.setDate(13, java.sql.Date.valueOf(dueDate));
			ps.setDouble(14, grandTotal);
			ps.setDouble(15, openAmt);
			try (final ResultSet rs = ps.executeQuery())
			{
				rs.next();
				insertedInvoiceIds.add(rs.getLong(1));
			}
		}
	}

	@Test
	void sql_function_returns_expected_rows_for_fixture() throws Exception
	{
		insertBPartner(TEST_MARKER + "FACT01", "Test-Factor GmbH", "Y", "N", "DE00001", "2500000000");
		final long cust1 = insertBPartner(TEST_MARKER + "CUST-AA", "Alpha Kunde GmbH", "N", "Y", null, null);
		final long cust2 = insertBPartner(TEST_MARKER + "CUST-BB", "Beta Kunde AG", "N", "Y", null, null);
		final long nonFactoring = insertBPartner(TEST_MARKER + "CUST-NON", "Non-Factoring", "N", "N", null, null);

		final LocalDate d1 = LocalDate.of(2025, 9, 1);
		final LocalDate due = LocalDate.of(2025, 10, 1);

		insertInvoice(cust1, ARI_DOCTYPE_ID, EUR_CURRENCY_ID, "INV-AA-001", d1, due, 1000.00, 750.00);
		insertInvoice(cust1, ARC_DOCTYPE_ID, EUR_CURRENCY_ID, "CR-AA-001", d1.plusDays(4), due, 200.00, 200.00);
		insertInvoice(cust2, ARI_DOCTYPE_ID, EUR_CURRENCY_ID, "INV-BB-001", d1, due, 500.00, 500.00);
		insertInvoice(cust2, ARC_DOCTYPE_ID, EUR_CURRENCY_ID, "CR-BB-001", d1.plusDays(4), due, 100.00, 100.00);
		insertInvoice(nonFactoring, ARI_DOCTYPE_ID, EUR_CURRENCY_ID, "INV-NON-001", d1, due, 999.00, 999.00); // excluded

		try (final PreparedStatement ps = conn.prepareStatement(
				"SELECT row_type, col_1, col_2, col_3, col_4, col_5,"
						+ " col_6, col_7, col_8, col_9, col_10, col_11"
						+ " FROM report_factoring_op_liste(?, ?, ?)"))
		{
			ps.setInt(1, EUR_CURRENCY_ID);
			ps.setInt(2, AD_ORG_ID);
			ps.setInt(3, AD_CLIENT_ID);
			try (final ResultSet rs = ps.executeQuery())
			{
				final List<String[]> rows = new ArrayList<>();
				while (rs.next())
				{
					final String[] row = new String[12];
					row[0] = rs.getString("row_type").trim();
					for (int i = 1; i <= 11; i++)
					{
						row[i] = rs.getString("col_" + i);
					}
					rows.add(row);
				}

				assertThat(rows).as("1 header + 4 detail rows").hasSize(5);
				assertThat(rows.get(0)[0]).as("header row_type").isEqualTo("01");
				assertThat(rows.get(0)[1]).as("SAF literal").isEqualTo("SAF");
				assertThat(rows.get(0)[2]).as("EFAG literal").isEqualTo("EFAG");
				assertThat(rows.get(0)[3]).as("contract number").isEqualTo("DE00001");
				assertThat(rows.get(0)[4]).as("client account id").isEqualTo("2500000000");
				assertThat(rows.get(0)[5]).as("currency").isEqualTo("EUR");
				assertThat(rows.get(0)[6]).as("technical semicolon = empty").isEmpty();
				assertThat(rows.get(0)[7]).as("upload date").isEqualTo(LocalDate.now().format(DD_MM_YYYY));
				assertThat(rows.get(0)[8]).as("row count formatted as n,00").isEqualTo("5,00");
				assertThat(rows.get(0)[9]).as("sum D grandtotal").isEqualTo("1500,00");
				assertThat(rows.get(0)[10]).as("sum C grandtotal").isEqualTo("300,00");

				// Detail rows sorted by C_BPartner.Value then DateInvoiced
				for (int i = 1; i <= 4; i++)
				{
					assertThat(rows.get(i)[0]).as("detail row_type").isEqualTo("02");
				}
			}
		}
	}

	@Test
	void sql_function_returns_header_only_when_no_matches() throws Exception
	{
		insertBPartner(TEST_MARKER + "FACT02", "Test-Factor GmbH", "Y", "N", "DE00002", "2500000001");
		// No factoring customers or invoices — the function should return the header only.

		try (final PreparedStatement ps = conn.prepareStatement(
				"SELECT row_type, col_8, col_9, col_10 FROM report_factoring_op_liste(?, ?, ?)"))
		{
			ps.setInt(1, EUR_CURRENCY_ID);
			ps.setInt(2, AD_ORG_ID);
			ps.setInt(3, AD_CLIENT_ID);
			try (final ResultSet rs = ps.executeQuery())
			{
				assertThat(rs.next()).as("header row").isTrue();
				assertThat(rs.getString("row_type").trim()).isEqualTo("01");
				assertThat(rs.getString("col_8")).as("row count = 1 (header only)").isEqualTo("1,00");
				assertThat(rs.getString("col_9")).as("sum D = 0,00").isEqualTo("0,00");
				assertThat(rs.getString("col_10")).as("sum C = 0,00").isEqualTo("0,00");
				assertThat(rs.next()).as("no detail rows").isFalse();
			}
		}
	}
}
