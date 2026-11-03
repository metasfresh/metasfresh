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
import de.metas.i18n.AdMessageKey;
import de.metas.util.Check;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.springframework.core.io.ByteArrayResource;

import java.time.format.DateTimeFormatter;

/**
 * Factoring OP-Liste Export process.
 *
 * <p>Produces a semicolon-CSV file (UTF-8 + BOM, CRLF line terminator) containing
 * one header row and one detail row per open invoice/credit note of every factoring
 * customer ({@code C_BPartner.IsFactoring='Y'}) in the current org and the selected currency.
 *
 * <p>Thin glue on top of {@link FactoringOpListeService} + {@link FactoringOpListeCsvWriter}:
 * <ol>
 *   <li>validate role scope (must be a specific org, not '*'),</li>
 *   <li>delegate to the service to fetch + aggregate the typed export data
 *       (which validates the factorer BP configuration and throws {@code AdempiereException}
 *       with a {@code @key@} marker on any missing/ambiguous config),</li>
 *   <li>delegate to the writer to serialise the CSV bytes,</li>
 *   <li>attach the bytes to the process result.</li>
 * </ol>
 */
public class Factoring_OP_Liste_Export extends JavaProcess
{
	private static final AdMessageKey MSG_RoleScopeAllOrgs =
			AdMessageKey.of("Factoring_OP_Liste_EXT_RoleScopeAllOrgs");

	static final String PARAM_C_CURRENCY_ID = "C_Currency_ID";

	private FactoringOpListeService service;

	@Param(parameterName = PARAM_C_CURRENCY_ID, mandatory = true)
	private int p_C_Currency_ID;

	public Factoring_OP_Liste_Export()
	{
		SpringContextHolder.instance.autowire(this);
		try
		{
			this.service = SpringContextHolder.instance.getBean(FactoringOpListeService.class);
		}
		catch (final Exception e)
		{
			// No Spring context available (unit-test env) — the test provides a mock via
			// setServiceForTesting. In production a genuine Spring misconfiguration lands here
			// too; runExport's Check.assumeNotNull below surfaces that clearly at run time,
			// citing the missing bean rather than throwing a downstream NPE.
			this.service = null;
		}
	}

	/** Package-private test seam. */
	void setServiceForTesting(@NonNull final FactoringOpListeService service)
	{
		this.service = service;
	}

	@Override
	protected String doIt() throws Exception
	{
		final ExportResult result = runExport(getProcessInfo().getAD_Org_ID(), p_C_Currency_ID);
		getResult().setReportData(new ByteArrayResource(result.getBytes()), result.getFilename(), "text/csv");
		addLog("File: {} ({} data row(s))", result.getFilename(), result.getDataRowCount());
		return MSG_OK;
	}

	/**
	 * Core export logic — testable without a full {@code ProcessInfo} setup.
	 * Returns the CSV bytes + resolved filename + data-row count.
	 */
	@NonNull
	ExportResult runExport(final int orgIdRepo, final int currencyIdRepo) throws java.io.IOException
	{
		if (orgIdRepo == 0)
		{
			throw new AdempiereException(MSG_RoleScopeAllOrgs.toAD_MessageWithMarkers())
					.markAsUserValidationError();
		}
		Check.assumeNotNull(service, "FactoringOpListeService bean is not available — Spring context "
				+ "is not initialised, or the bean is missing from the Spring configuration");
		final OrgId orgId = OrgId.ofRepoId(orgIdRepo);
		final CurrencyId currencyId = CurrencyId.ofRepoId(currencyIdRepo);

		final FactoringOpListeExportData data = service.buildExportData(orgId, currencyId);
		final byte[] bytes = FactoringOpListeCsvWriter.toCsvBytes(data);
		return new ExportResult(bytes, buildFilename(data), data.getDetailRows().size());
	}

	/** {@code <FactoringContractNo>_INH_<yyyyMMdd>.csv}. */
	@NonNull
	private static String buildFilename(@NonNull final FactoringOpListeExportData data)
	{
		final String today = data.getUploadDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
		return Joiner.on("_").skipNulls().join(data.getContractNo(), "INH", today) + ".csv";
	}

	@lombok.Value
	static class ExportResult
	{
		byte[] bytes;
		String filename;
		int dataRowCount;
	}
}
