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

import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.cucumber.stepdefs.sectioncode.M_SectionCode_StepDefData;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.Check;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_SectionCode;
import org.compiere.util.Env;

import java.sql.Timestamp;
import java.util.Map;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.assertj.core.api.Assertions.*;

public class C_Dunning_Candidate_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
	private final IDunningBL dunningBL = Services.get(IDunningBL.class);

	private final C_Dunning_Candidate_StepDefData dunningCandidateTable;
	private final C_DunningLevel_StepDefData dunningLevelTable;
	private final M_SectionCode_StepDefData sectionCodeTable;
	private final C_Invoice_StepDefData invoiceTable;

	public C_Dunning_Candidate_StepDef(
			@NonNull final C_Dunning_Candidate_StepDefData dunningCandidateTable,
			@NonNull final C_DunningLevel_StepDefData dunningLevelTable,
			@NonNull final M_SectionCode_StepDefData sectionCodeTable,
			@NonNull final C_Invoice_StepDefData invoiceTable)
	{
		this.dunningCandidateTable = dunningCandidateTable;
		this.dunningLevelTable = dunningLevelTable;
		this.sectionCodeTable = sectionCodeTable;
		this.invoiceTable = invoiceTable;
	}

	@And("invoke \"C_Dunning_Candidate_Create\" process:")
	public void createC_Dunning_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String dunningLevelIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Dunning_Candidate.COLUMNNAME_C_DunningLevel_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
			final org.compiere.model.I_C_DunningLevel dunningLevel = dunningLevelTable.get(dunningLevelIdentifier);

			final de.metas.dunning.interfaces.I_C_DunningLevel dunningLevelRecord = InterfaceWrapperHelper.load(dunningLevel.getC_DunningLevel_ID(), de.metas.dunning.interfaces.I_C_DunningLevel.class);

			final Boolean isFullUpdate = DataTableUtil.extractBooleanForColumnNameOr(row, "OPT.IsFullUpdate", false);

			final Timestamp dunningDate = DataTableUtil.extractDateTimestampForColumnName(row, "DunningDate");

			trxManager.runInNewTrx(() ->
								   {
									   final IDunningContext context = dunningBL.createDunningContext(Env.getCtx(), dunningLevelRecord, dunningDate, ITrx.TRXNAME_None, null);

									   dunningDAO.deleteNotProcessedCandidates(context, dunningLevelRecord);

									   dunningBL.createDunningCandidates(context);
								   });
		}
	}

	@And("locate C_Dunning_Candidate:")
	public void locateC_Dunning_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final I_C_Dunning_Candidate dunningCandidate = buildQuery(row)
					.firstOnlyNotNull(I_C_Dunning_Candidate.class);

			final String dunningCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Dunning_Candidate.COLUMNNAME_C_Dunning_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			dunningCandidateTable.putOrReplace(dunningCandidateIdentifier, dunningCandidate);
		}
	}

	@And("invoke \"C_Dunning_Candidate_Process\" process:")
	public void processC_Dunning_Candidate(@NonNull final DataTable dataTable)
	{
		for (final Map<String, String> row : dataTable.asMaps())
		{
			final String dunningCandidateIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Dunning_Candidate.COLUMNNAME_C_Dunning_Candidate_ID + "." + TABLECOLUMN_IDENTIFIER);
			final I_C_Dunning_Candidate dunningCandidate = dunningCandidateTable.get(dunningCandidateIdentifier);

			final IDunningContext context = dunningBL.createDunningContext(Env.getCtx(),
																		   null, // DunningDate, not needed
																		   null, // C_DunningLevel, not needed
																		   ITrx.TRXNAME_None,
																		   null);

			final Boolean isAutoProcess = DataTableUtil.extractBooleanForColumnNameOr(row, "AutoProcess", false);
			context.setProperty(IDunningProducer.CONTEXT_ProcessDunningDoc, isAutoProcess);

			final IDunningProducer dunningProducer = context.getDunningConfig().createDunningProducer();
			dunningProducer.setDunningContext(context);
			dunningProducer.addCandidate(dunningCandidate);
			dunningProducer.finish();
		}
	}

	@NonNull
	private IQuery<I_C_Dunning_Candidate> buildQuery(@NonNull final Map<String, String> row)
	{
		final String tableName = DataTableUtil.extractStringForColumnName(row, I_AD_Table.COLUMNNAME_TableName);
		final AdTableId tableId = AdTableId.ofRepoIdOrNull(tableDAO.retrieveTableId(tableName));
		assertThat(tableId).isNotNull();

		final IQueryBuilder<I_C_Dunning_Candidate> queryBuilder = queryBL.createQueryBuilder(I_C_Dunning_Candidate.class)
				.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_AD_Table_ID, tableId);

		final String sectionCodeIdentifier = DataTableUtil.extractStringOrNullForColumnName(row, "OPT." + I_C_Dunning_Candidate.COLUMNNAME_M_SectionCode_ID + "." + TABLECOLUMN_IDENTIFIER);
		if (Check.isNotBlank(sectionCodeIdentifier))
		{
			final I_M_SectionCode sectionCode = sectionCodeTable.get(sectionCodeIdentifier);
			queryBuilder.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_M_SectionCode_ID, sectionCode.getM_SectionCode_ID());
		}

		final String recordIdentifier = DataTableUtil.extractStringForColumnName(row, I_C_Dunning_Candidate.COLUMNNAME_Record_ID + "." + TABLECOLUMN_IDENTIFIER);

		switch (tableName)
		{
			case I_C_Invoice.Table_Name:
				final I_C_Invoice invoice = invoiceTable.get(recordIdentifier);
				queryBuilder.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_Record_ID, invoice.getC_Invoice_ID())
						.addEqualsFilter(I_C_Dunning_Candidate.COLUMNNAME_DocumentNo, invoice.getDocumentNo());
				break;
			default:
				throw new AdempiereException("Unhandled tableName")
						.appendParametersToMessage()
						.setParameter("tableName:", tableName);
		}

		return queryBuilder.create();
	}
}
