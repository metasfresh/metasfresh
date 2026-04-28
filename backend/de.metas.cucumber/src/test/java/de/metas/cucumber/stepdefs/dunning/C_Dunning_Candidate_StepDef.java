/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.dunning;

import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class C_Dunning_Candidate_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
	private final IDunningBL dunningBL = Services.get(IDunningBL.class);

	@NonNull private final C_Dunning_Candidate_StepDefData dunningCandidateTable;
	@NonNull private final C_DunningLevel_StepDefData dunningLevelTable;
	@NonNull private final C_Invoice_StepDefData invoiceTable;
	@NonNull private final C_DunningDoc_StepDefData dunningDocTable;

	@And("invoke \"C_Dunning_Candidate_Create\" process:")
	public void createC_Dunning_Candidate(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::createDunningCandidate);
	}

	@And("^after not more than (.*)s, locate C_Dunning_Candidate:$")
	public void locateC_Dunning_Candidate(final int timeoutSec, @NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(row -> locateDunningCandidate(timeoutSec, row));
	}

	@And("invoke \"C_Dunning_Candidate_Process\" process:")
	public void processC_Dunning_Candidate(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.forEach(this::processDunningCandidate);
	}

	private void processDunningCandidate(@NonNull final DataTableRow row)
	{
		final I_C_Dunning_Candidate dunningCandidate = row.getAsIdentifier(I_C_Dunning_Candidate.COLUMNNAME_C_Dunning_Candidate_ID)
				.lookupIn(dunningCandidateTable);
		assertThat(dunningCandidate).isNotNull();

		final IDunningContext context = dunningBL.createDunningContext(Env.getCtx(),
				null, // DunningDate, not needed
				null, // C_DunningLevel, not needed
				ITrx.TRXNAME_None);

		row.getAsOptionalBoolean("AutoProcess").ifPresent(isAutoProcess -> context.setProperty(IDunningProducer.CONTEXT_ProcessDunningDoc, isAutoProcess));

		final IDunningProducer dunningProducer = context.getDunningConfig().createDunningProducer();
		dunningProducer.setDunningContext(context);
		dunningProducer.addCandidate(dunningCandidate);
		dunningProducer.finish();

		row.getAsIdentifier().putOrReplace(dunningDocTable, retrieveDunningDoc(dunningCandidate));
	}

	@NonNull
	private I_C_DunningDoc retrieveDunningDoc(@NonNull final I_C_Dunning_Candidate dunningCandidate)
	{
		return queryBL.createQueryBuilder(I_C_DunningDoc.class)
				.addEqualsFilter(I_C_DunningDoc.COLUMNNAME_C_BPartner_ID, dunningCandidate.getC_BPartner_ID())
				.addEqualsFilter(I_C_DunningDoc.COLUMNNAME_C_DunningLevel_ID, dunningCandidate.getC_DunningLevel_ID())
				.addEqualsFilter(I_C_DunningDoc.COLUMNNAME_DunningDate, dunningCandidate.getDunningDate())
				.create()
				.firstOnlyNotNull();
	}

	@NonNull
	private IQuery<I_C_Dunning_Candidate> buildQuery(@NonNull final DataTableRow row)
	{
		final String tableName = row.getAsString(I_AD_Table.COLUMNNAME_TableName);
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final IQueryBuilder<I_C_Dunning_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Dunning_Candidate.class)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID, tableId);

		final String recordIdentifier = row.getAsIdentifier(I_C_Dunning_Candidate.COLUMNNAME_Record_ID).getAsString();

		if (I_C_Invoice.Table_Name.equals(tableName))
		{
			final I_C_Invoice invoice = invoiceTable.get(recordIdentifier);
			queryBuilder.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_Record_ID, invoice.getC_Invoice_ID())
					.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_DocumentNo, invoice.getDocumentNo());
		}
		else
		{
			throw new AdempiereException("Unhandled tableName")
					.appendParametersToMessage()
					.setParameter("tableName:", tableName);
		}

		return queryBuilder.create();
	}

	private void createDunningCandidate(@NonNull final DataTableRow row)
	{
		final org.compiere.model.I_C_DunningLevel dunningLevel = row.getAsIdentifier(I_C_Dunning_Candidate.COLUMNNAME_C_DunningLevel_ID)
				.lookupIn(dunningLevelTable);
		assertThat(dunningLevel).isNotNull();

		final de.metas.dunning.interfaces.I_C_DunningLevel dunningLevelRecord = InterfaceWrapperHelper.load(dunningLevel.getC_DunningLevel_ID(), de.metas.dunning.interfaces.I_C_DunningLevel.class);

		final Date dunningDate = TimeUtil.asDate(row.getAsLocalDate(I_C_DunningDoc.COLUMNNAME_DunningDate));
		assertThat(dunningDate).isNotNull();

		final IDunningContext context = dunningBL.createDunningContext(Env.getCtx(), dunningLevelRecord, dunningDate, ITrx.TRXNAME_None);

		dunningDAO.deleteNotProcessedCandidates(context, dunningLevelRecord);

		dunningBL.createDunningCandidates(context);
	}

	private void locateDunningCandidate(final int timeoutSec, @NonNull final DataTableRow row) throws InterruptedException
	{
		final I_C_Dunning_Candidate dunningCandidate = StepDefUtil.tryAndWaitForItem(timeoutSec, 500, buildQuery(row));

		row.getAsIdentifier().putOrReplace(dunningCandidateTable, dunningCandidate);
	}
}
