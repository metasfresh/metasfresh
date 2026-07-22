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

import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.db.CConnection;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Factoring OP-Liste Export process.
 *
 * <p>Produces a semicolon-CSV file (UTF-8 + BOM, CRLF line terminator) containing
 * one header row and one detail row per open invoice/credit note of all factoring customers
 * (C_BPartner.IsFactoring='Y') in the current org and the selected currency.
 *
 * <p>The file is named {@code <FactoringContractNo>_INH_<YYYYMMDD>.csv} and attached
 * to the process result for download.
 *
 * <p>Byte-level format requirements (AC2, AC3, AC4):
 * <ul>
 *   <li>UTF-8 BOM (0xEF 0xBB 0xBF) as first 3 bytes</li>
 *   <li>Semicolons as field delimiter; trailing semicolon on every row (col_11 = '')</li>
 *   <li>CRLF ({@code \r\n}) line terminator, including on the last row</li>
 * </ul>
 *
 * <p>Error paths (AC6):
 * <ul>
 *   <li>Role scope is '*' (AD_Org_ID = 0) → AdempiereException</li>
 *   <li>Zero factorer BPs in the org → AdempiereException</li>
 *   <li>Multiple factorer BPs in the org → AdempiereException</li>
 *   <li>Empty FactoringContractNo on the factorer BP → AdempiereException</li>
 *   <li>Empty FactoringClientAccountId on the factorer BP → AdempiereException</li>
 * </ul>
 */
public class Factoring_OP_Liste_Export extends JavaProcess
{
	/** Parameter name: currency (mandatory). */
	static final String PARAM_C_CURRENCY_ID = "C_Currency_ID";

	@Param(parameterName = PARAM_C_CURRENCY_ID, mandatory = true)
	private int p_C_Currency_ID;

	// -------------------------------------------------------------------------
	// JavaProcess contract
	// -------------------------------------------------------------------------

	@Override
	@RunOutOfTrx
	protected String doIt() throws Exception
	{
		final int orgId = getProcessInfo().getAD_Org_ID();
		final int clientId = getProcessInfo().getAD_Client_ID();

		try (final Connection conn = createConnection())
		{
			final ExportResult result = runExport(conn, orgId, clientId, p_C_Currency_ID);
			getResult().setReportData(result.file, result.filename);
			return "OK — " + result.filename + ", " + result.rowCount + " data row(s)";
		}
	}

	// -------------------------------------------------------------------------
	// Core export logic (package-private for testability)
	// -------------------------------------------------------------------------

	/**
	 * Runs the full export: validates the factorer BP, calls the SQL function,
	 * and writes the CSV file to a temp path.
	 *
	 * @param conn      JDBC connection to the metasfresh database
	 * @param orgId     AD_Org_ID of the current user's organisation
	 * @param clientId  AD_Client_ID
	 * @param currencyId C_Currency_ID (the Währung parameter)
	 * @return export result containing the temp file and its filename
	 * @throws SQLException if a database error occurs
	 * @throws IOException  if the temp file cannot be written
	 */
	ExportResult runExport(
			@NonNull final Connection conn,
			final int orgId,
			final int clientId,
			final int currencyId) throws SQLException, IOException
	{
		// AC6: role scope must not be '*' (AD_Org_ID = 0 = any/all orgs)
		if (orgId == 0)
		{
			throw new AdempiereException("Please select a specific organisation before running the Factoring OP-Liste Export. "
					+ "Running with all-organisations scope ('*') is not supported.")
					.markAsUserValidationError();
		}

		// ---- Validate factorer BP (AC6) ----
		final FactorerBpInfo factorerBp = resolveFactorerBp(conn, orgId, clientId);

		// ---- Call SQL function ----
		final List<String[]> rows = callSqlFunction(conn, currencyId, orgId, clientId);

		// ---- Write CSV file ----
		final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		final String filename = factorerBp.contractNo + "_INH_" + today + ".csv";

		final File tempFile = File.createTempFile("factoring_op_liste_", ".csv");
		tempFile.deleteOnExit();

		writeCsv(tempFile, rows);

		// Number of data rows = total rows minus the 1 header row
		final int dataRowCount = Math.max(0, rows.size() - 1);
		return new ExportResult(tempFile, filename, dataRowCount);
	}

	// -------------------------------------------------------------------------
	// Factorer BP resolution (AC6 validations)
	// -------------------------------------------------------------------------

	private FactorerBpInfo resolveFactorerBp(
			final Connection conn,
			final int orgId,
			final int clientId) throws SQLException
	{
		final String sql = "SELECT name, factoringcontractno, factoringclientaccountid"
				+ " FROM c_bpartner"
				+ " WHERE isfactorer = 'Y'"
				+ " AND isactive = 'Y'"
				+ " AND ad_org_id = ?"
				+ " AND ad_client_id = ?";

		final List<FactorerBpInfo> factorers = new ArrayList<>();
		try (final PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, orgId);
			ps.setInt(2, clientId);
			try (final ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					factorers.add(new FactorerBpInfo(
							rs.getString("name"),
							rs.getString("factoringcontractno"),
							rs.getString("factoringclientaccountid")));
				}
			}
		}

		// AC6: zero factorers
		if (factorers.isEmpty())
		{
			final String orgName = getOrgName(conn, orgId);
			throw new AdempiereException("No factorer BPartner (IsFactorer='Y') found for organisation " + orgName + ". "
					+ "Please configure a BPartner with IsFactorer='Y' for this organisation.")
					.markAsUserValidationError();
		}

		// AC6: multiple factorers
		if (factorers.size() > 1)
		{
			final String orgName = getOrgName(conn, orgId);
			final StringBuilder names = new StringBuilder();
			for (final FactorerBpInfo fp : factorers)
			{
				if (names.length() > 0)
				{
					names.append(", ");
				}
				names.append(fp.name);
			}
			throw new AdempiereException("Multiple factorer BPartners (IsFactorer='Y') found for organisation " + orgName + ": " + names
					+ ". Exactly one factorer BPartner per organisation is required.")
					.markAsUserValidationError();
		}

		final FactorerBpInfo factorer = factorers.get(0);

		// AC6: empty FactoringContractNo
		if (isBlank(factorer.contractNo))
		{
			throw new AdempiereException("Factorer BPartner '" + factorer.name + "' has no FactoringContractNo set — required for the OP-Liste export.")
					.markAsUserValidationError();
		}

		// AC6: empty FactoringClientAccountId
		if (isBlank(factorer.clientAccountId))
		{
			throw new AdempiereException("Factorer BPartner '" + factorer.name + "' has no FactoringClientAccountId set — required for the OP-Liste export.")
					.markAsUserValidationError();
		}

		return factorer;
	}

	private String getOrgName(final Connection conn, final int orgId) throws SQLException
	{
		try (final PreparedStatement ps = conn.prepareStatement(
				"SELECT name FROM ad_org WHERE ad_org_id = ?"))
		{
			ps.setInt(1, orgId);
			try (final ResultSet rs = ps.executeQuery())
			{
				return rs.next() ? rs.getString("name") : String.valueOf(orgId);
			}
		}
	}

	// -------------------------------------------------------------------------
	// SQL function call
	// -------------------------------------------------------------------------

	private List<String[]> callSqlFunction(
			final Connection conn,
			final int currencyId,
			final int orgId,
			final int clientId) throws SQLException
	{
		final String sql = "SELECT row_type, col_1, col_2, col_3, col_4, col_5,"
				+ " col_6, col_7, col_8, col_9, col_10, col_11"
				+ " FROM report_factoring_op_liste(?, ?, ?)";

		final List<String[]> rows = new ArrayList<>();
		try (final PreparedStatement ps = conn.prepareStatement(sql))
		{
			ps.setInt(1, currencyId);
			ps.setInt(2, orgId);
			ps.setInt(3, clientId);
			try (final ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					// row_type at index 0; col_1..col_11 at indices 1..11
					final String[] row = new String[12];
					row[0] = nullToEmpty(rs.getString("row_type")).trim();
					for (int i = 1; i <= 11; i++)
					{
						row[i] = nullToEmpty(rs.getString("col_" + i));
					}
					rows.add(row);
				}
			}
		}
		return rows;
	}

	// -------------------------------------------------------------------------
	// CSV writing (AC2)
	// -------------------------------------------------------------------------

	/**
	 * Writes the row set to the given file.
	 *
	 * <p>Format: UTF-8 BOM, then each row as 11 values joined by {@code ;}
	 * (row_type + col_1..col_10 — col_11 from the SQL function is an internal spare
	 * and NOT emitted; per AC3/AC4 the visible file has exactly 11 fields per row where
	 * field 11 = col_10 renders as the trailing {@code ;} after the D/C flag / totals).
	 * Terminated by CRLF ({@code \r\n}); last row also gets a trailing CRLF (matching the reference file).
	 */
	private void writeCsv(final File file, final List<String[]> rows) throws IOException
	{
		try (final FileOutputStream fos = new FileOutputStream(file))
		{
			// Write UTF-8 BOM as literal bytes (AC2, AC9)
			fos.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });

			try (final BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(fos, StandardCharsets.UTF_8)))
			{
				for (final String[] row : rows)
				{
					writer.write(buildCsvLine(row));
					writer.write("\r\n"); // CRLF, not platform newline (AC2)
				}
			}
		}
	}

	private String buildCsvLine(final String[] row)
	{
		// row[0]     = row_type (AC3/AC4 field 1: literal '01' or '02')
		// row[1..10] = col_1..col_10 (AC3/AC4 fields 2..11)
		// row[11]    = col_11 (SQL function's spare — intentionally NOT emitted)
		// Total emitted tokens: 11 (row_type + col_1..col_10)
		final int emitUpTo = Math.min(row.length, 11); // row_type + 10 payload cols
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < emitUpTo; i++)
		{
			if (i > 0)
			{
				sb.append(';');
			}
			sb.append(nullToEmpty(row[i]));
		}
		return sb.toString();
	}

	// -------------------------------------------------------------------------
	// Connection factory (overridable in tests)
	// -------------------------------------------------------------------------

	/**
	 * Returns a JDBC connection to the metasfresh database.
	 *
	 * <p>In production this uses the configured metasfresh DB pool.
	 * In integration tests the subclass overrides this method to return the test connection.
	 */
	Connection createConnection() throws SQLException
	{
		return CConnection.get().getConnection(false, java.sql.Connection.TRANSACTION_READ_COMMITTED);
	}

	// -------------------------------------------------------------------------
	// Helpers
	// -------------------------------------------------------------------------

	private static String nullToEmpty(@Nullable final String s)
	{
		return s != null ? s : "";
	}

	private static boolean isBlank(@Nullable final String s)
	{
		return s == null || s.trim().isEmpty();
	}

	// -------------------------------------------------------------------------
	// Inner types
	// -------------------------------------------------------------------------

	/** Factorer BP configuration, validated per AC6. */
	static final class FactorerBpInfo
	{
		@NonNull public final String name;
		@Nullable public final String contractNo;
		@Nullable public final String clientAccountId;

		FactorerBpInfo(@NonNull final String name, @Nullable final String contractNo, @Nullable final String clientAccountId)
		{
			this.name = name;
			this.contractNo = contractNo;
			this.clientAccountId = clientAccountId;
		}
	}

	/** Result of a successful export run. */
	static final class ExportResult
	{
		@NonNull public final File file;
		@NonNull public final String filename;
		public final int rowCount;

		ExportResult(@NonNull final File file, @NonNull final String filename, final int rowCount)
		{
			this.file = file;
			this.filename = filename;
			this.rowCount = rowCount;
		}
	}
}
