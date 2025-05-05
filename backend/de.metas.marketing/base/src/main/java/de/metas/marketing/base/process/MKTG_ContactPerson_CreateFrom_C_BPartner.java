package de.metas.marketing.base.process;

import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Export;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.marketing
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MKTG_ContactPerson_CreateFrom_C_BPartner extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Param(mandatory = true, parameterName = I_MKTG_Campaign.COLUMNNAME_MKTG_Campaign_ID)
	private int campaignRecordId;

	@Param(mandatory = true, parameterName = "IsRemoveAllExistingContactsFromCampaign")
	private boolean removeAllExistingContactsFromCampaign;

	@Override
	protected String doIt() throws Exception
	{
		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecordId);

		final MKTG_ContactPerson_ProcessBase contactPersonProcessBase = SpringContextHolder.instance.getBean(MKTG_ContactPerson_ProcessBase.class);

		final MKTG_ContactPerson_ProcessParams params = MKTG_ContactPerson_ProcessParams.builder()
				.selectionFilter(getBPartnerSelectionFilter())
				.campaignId(campaignId)
				.addresType(getAddressType())
				.removeAllExistingContactsFromCampaign(removeAllExistingContactsFromCampaign)
				.build();

		contactPersonProcessBase.createContactPersonsForPartner(params);

		return MSG_OK;
	}

	@Nullable
	protected DefaultAddressType getAddressType()
	{
		return null;
	}

	@NonNull
	private IQueryFilter<I_C_BPartner> getBPartnerSelectionFilter()
	{
		final String tableName = getTableName();

		if (tableName == null)
		{
			throw new AdempiereException("Process started from unknown source!")
					.appendParametersToMessage()
					.setParameter("AD_Table_ID", getProcessInfo().getTable_ID())
					.setParameter("AD_Window_ID", getProcessInfo().getAD_Window_ID());
		}

		switch (tableName)
		{
			case I_C_BPartner.Table_Name:
				return getProcessInfo().getQueryFilterOrElseFalse();
			case I_C_BPartner_Export.Table_Name:
				return getBPartnerFilterFromBPartnerExportSelection();
			default:
				throw new AdempiereException(tableName + " is not supported as starting point for process!");
		}

	}

	@NonNull
	private IQueryFilter<I_C_BPartner> getBPartnerFilterFromBPartnerExportSelection()
	{
		Check.assume(I_C_BPartner_Export.Table_Name.equals(getProcessInfo().getTableNameOrNull()),
					 "Process must be started from a C_BPartner_Export based window!");

		final IQueryFilter<I_C_BPartner_Export> bpExportFilter = getProcessInfo().getQueryFilterOrElseFalse();

		final IQuery<I_C_BPartner> exportBPToBPQuery = queryBL
				.createQueryBuilder(I_C_BPartner_Export.class)
				.filter(bpExportFilter)
				.andCollect(I_C_BPartner_Export.COLUMNNAME_C_BPartner_ID, I_C_BPartner.class)
				.create();

		// note that with "andCollect" we created a query, but we need to return an IQueryFilter; thats why we crete the query filter here
		return queryBL.createCompositeQueryFilter(I_C_BPartner.class)
				.addInSubQueryFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_BPartner.COLUMNNAME_C_BPartner_ID, exportBPToBPQuery);
	}
}
