/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.invoicecandidate.impexp;

import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_I_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_I_Invoice_Candidate;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.lang.IMutable;
import org.compiere.SpringContextHolder;

import java.sql.ResultSet;
import java.util.Properties;

public class InvoiceCandidateImportProcess extends SimpleImportProcessTemplate<I_I_Invoice_Candidate>
{
	private final ImportInvoiceCandidatesService createInvoiceCandidatesService = SpringContextHolder.instance.getBean(ImportInvoiceCandidatesService.class);

	@Override
	public Class<I_I_Invoice_Candidate> getImportModelClass()
	{
		return I_I_Invoice_Candidate.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Invoice_Candidate.Table_Name;
	}

	@Override
	protected String getTargetTableName()
	{
		return I_C_Invoice_Candidate.Table_Name;
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Invoice_Candidate.COLUMNNAME_I_Invoice_Candidate_ID;
	}

	@Override
	protected I_I_Invoice_Candidate retrieveImportRecord(final Properties ctx, final ResultSet rs)
	{
		return new X_I_Invoice_Candidate(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	@Override
	protected void updateAndValidateImportRecords()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		CInvoiceCandidateImportTableSqlUpdater.updateInvoiceCandImportTable(selection);

		final int countErrorRecords = CInvoiceCandidateImportTableSqlUpdater.countRecordsWithErrors(selection);

		getResultCollector().setCountImportRecordsWithValidationErrors(countErrorRecords);
	}

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> stateHolder,
			final @NonNull I_I_Invoice_Candidate importRecord,
			final boolean isInsertOnly) throws Exception
	{
		createInvoiceCandidatesService.createInvoiceCandidate(importRecord);

		return ImportRecordResult.Inserted;
	}
}
