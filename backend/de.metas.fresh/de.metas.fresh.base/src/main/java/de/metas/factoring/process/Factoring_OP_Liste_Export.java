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

import com.google.common.base.Joiner;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.springframework.core.io.ByteArrayResource;

import javax.annotation.Nullable;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
 * <p>Byte-level format requirements:
 * <ul>
 *   <li>UTF-8 BOM (0xEF 0xBB 0xBF) as first 3 bytes</li>
 *   <li>Semicolons as field delimiter; trailing semicolon on every detail row (col_10 = '' renders as the trailing ';')</li>
 *   <li>CRLF ({@code \r\n}) line terminator, including on the last row</li>
 * </ul>
 *
 * <p>Error paths:
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
	// AD_Message rows for these keys are seeded alongside the AD_Process registration migration.
	// Note: throws below use the {@code @key@} placeholder form via {@link AdMessageKey#toAD_MessageWithMarkers()}
	// rather than {@code new AdempiereException(AdMessageKey, ...)} — the latter constructor calls
	// {@code MsgBL.getErrorCode()} at throw time, which requires the metasfresh Msg/DB layer to be
	// initialized. Deferring resolution to {@code Msg.parseTranslation()} at display time keeps
	// construction safe from any pre-Msg-init callsite.
	private static final AdMessageKey MSG_RoleScopeAllOrgs = AdMessageKey.of("Factoring_OP_Liste_EXT_RoleScopeAllOrgs");
	private static final AdMessageKey MSG_NoFactorer = AdMessageKey.of("Factoring_OP_Liste_EXT_NoFactorer");
	private static final AdMessageKey MSG_MultipleFactorers = AdMessageKey.of("Factoring_OP_Liste_EXT_MultipleFactorers");
	private static final AdMessageKey MSG_MissingContractNo = AdMessageKey.of("Factoring_OP_Liste_EXT_MissingContractNo");
	private static final AdMessageKey MSG_MissingClientAccountId = AdMessageKey.of("Factoring_OP_Liste_EXT_MissingClientAccountId");

	/** Parameter name: currency (mandatory). */
	static final String PARAM_C_CURRENCY_ID = "C_Currency_ID";

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private FactoringOpListeRepository factoringRepo;

	@Param(parameterName = PARAM_C_CURRENCY_ID, mandatory = true)
	private int p_C_Currency_ID;

	public Factoring_OP_Liste_Export()
	{
		SpringContextHolder.instance.autowire(this);
		try
		{
			this.factoringRepo = SpringContextHolder.instance.getBean(FactoringOpListeRepository.class);
		}
		catch (final Exception e)
		{
			// Spring context not available (e.g. in a unit-test env) — the test provides a repository via
			// {@link #setFactoringRepoForTesting(FactoringOpListeRepository)}.
			this.factoringRepo = null;
		}
	}

	/** Package-private test seam: lets a unit test inject a mocked repository (no Spring context). */
	void setFactoringRepoForTesting(@NonNull final FactoringOpListeRepository repo)
	{
		this.factoringRepo = repo;
	}

	// -------------------------------------------------------------------------
	// JavaProcess contract
	// -------------------------------------------------------------------------

	@Override
	protected String doIt() throws Exception
	{
		final int orgIdRepo = getProcessInfo().getAD_Org_ID();
		final int clientId = getProcessInfo().getAD_Client_ID();

		final ExportResult result = runExport(orgIdRepo, clientId, p_C_Currency_ID);
		getResult().setReportData(new ByteArrayResource(result.bytes), result.filename, "text/csv");
		addLog("File: {} ({} data row(s))", result.filename, result.rowCount);
		return MSG_OK;
	}

	// -------------------------------------------------------------------------
	// Core export logic (package-private for testability)
	// -------------------------------------------------------------------------

	/**
	 * Runs the full export: validates the factorer BP via {@link IBPartnerDAO}, calls the SQL
	 * function via {@link FactoringOpListeRepository}, and writes the CSV to a byte array.
	 */
	ExportResult runExport(
			final int orgIdRepo,
			final int clientId,
			final int currencyId) throws IOException
	{
		// Refuse a role-scope-'*' invocation — the export is org-scoped.
		if (orgIdRepo == 0)
		{
			throw new AdempiereException(MSG_RoleScopeAllOrgs.toAD_MessageWithMarkers())
					.markAsUserValidationError();
		}

		final OrgId orgId = OrgId.ofRepoId(orgIdRepo);

		final FactorerBpInfo factorerBp = resolveFactorerBp(orgId);

		final List<String[]> rows = factoringRepo.loadOpListRows(currencyId, orgIdRepo, clientId);

		final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		final String filename = Joiner.on("_").skipNulls()
				.join(factorerBp.contractNo, "INH", today) + ".csv";

		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		writeCsv(out, rows);

		final int dataRowCount = Math.max(0, rows.size() - 1); // total rows minus 1 header row
		return new ExportResult(out.toByteArray(), filename, dataRowCount);
	}

	// -------------------------------------------------------------------------
	// Factorer BP resolution
	// -------------------------------------------------------------------------

	private FactorerBpInfo resolveFactorerBp(@NonNull final OrgId orgId)
	{
		final List<I_C_BPartner> factorers = bpartnerDAO.retrieveFactorerBPartnersForOrg(orgId);

		if (factorers.isEmpty())
		{
			throw new AdempiereException(MSG_NoFactorer.toAD_MessageWithMarkers() + " " + resolveOrgName(orgId))
					.markAsUserValidationError();
		}

		if (factorers.size() > 1)
		{
			final StringBuilder names = new StringBuilder();
			for (final I_C_BPartner fp : factorers)
			{
				if (names.length() > 0)
				{
					names.append(", ");
				}
				names.append(fp.getName());
			}
			throw new AdempiereException(MSG_MultipleFactorers.toAD_MessageWithMarkers()
					+ " " + resolveOrgName(orgId) + ": " + names)
					.markAsUserValidationError();
		}

		final I_C_BPartner factorer = factorers.get(0);

		if (isBlank(factorer.getFactoringContractNo()))
		{
			throw new AdempiereException(MSG_MissingContractNo.toAD_MessageWithMarkers() + " " + factorer.getName())
					.markAsUserValidationError();
		}

		if (isBlank(factorer.getFactoringClientAccountId()))
		{
			throw new AdempiereException(MSG_MissingClientAccountId.toAD_MessageWithMarkers() + " " + factorer.getName())
					.markAsUserValidationError();
		}

		return new FactorerBpInfo(factorer.getName(),
				factorer.getFactoringContractNo(),
				factorer.getFactoringClientAccountId());
	}

	private String resolveOrgName(@NonNull final OrgId orgId)
	{
		try
		{
			return orgDAO.getById(orgId).getName();
		}
		catch (final Exception e)
		{
			return String.valueOf(orgId.getRepoId());
		}
	}

	// -------------------------------------------------------------------------
	// CSV writing
	// -------------------------------------------------------------------------

	/**
	 * Writes the row set to the given output stream.
	 *
	 * <p>Format: UTF-8 BOM, then each row as 11 values joined by {@code ;}
	 * (row_type + col_1..col_10 — col_11 from the SQL function is an internal spare
	 * and NOT emitted; the visible file has exactly 11 fields per row where
	 * field 11 = col_10 renders as the trailing {@code ;} after the D/C flag / totals).
	 * Terminated by CRLF ({@code \r\n}); last row also gets a trailing CRLF (matching the reference file).
	 */
	private void writeCsv(final OutputStream out, final List<String[]> rows) throws IOException
	{
		// Write UTF-8 BOM as literal bytes (required for Crédit Agricole CSV spec compliance)
		out.write(new byte[] { (byte) 0xEF, (byte) 0xBB, (byte) 0xBF });

		try (final BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(out, StandardCharsets.UTF_8)))
		{
			for (final String[] row : rows)
			{
				writer.write(buildCsvLine(row));
				writer.write("\r\n"); // CRLF line terminator — required by the spec; not the platform newline
			}
		}
	}

	private String buildCsvLine(final String[] row)
	{
		// row[0]     = row_type (field 1: literal '01' or '02')
		// row[1..10] = col_1..col_10 (fields 2..11)
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

	/** Factorer BP configuration, validated per the process contract. */
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
		@NonNull public final byte[] bytes;
		@NonNull public final String filename;
		public final int rowCount;

		ExportResult(@NonNull final byte[] bytes, @NonNull final String filename, final int rowCount)
		{
			this.bytes = bytes;
			this.filename = filename;
			this.rowCount = rowCount;
		}
	}
}
