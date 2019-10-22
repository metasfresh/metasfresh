package de.metas.marketing.base.process;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import de.metas.marketing.base.bpartner.DefaultAddressType;
import de.metas.marketing.base.model.CampaignId;
import de.metas.marketing.base.model.I_MKTG_Campaign;
import de.metas.process.JavaProcess;
import de.metas.process.Param;

/*
 * #%L
 * marketing-base
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

public class MKTG_ContactPerson_CreateFrom_C_BPartner_WithAddress extends JavaProcess
{

	@Param(mandatory = true, parameterName = I_MKTG_Campaign.COLUMNNAME_MKTG_Campaign_ID)
	private int campaignRecordId;

	@Param(mandatory = true, parameterName = "DefaultAddressType")
	private String defaultAddresType;

	@Param(mandatory = true, parameterName = "IsRemoveAllExistingContactsFromCampaign")
	private boolean removeAllExistingContactsFromCampaign;

	@Override
	protected String doIt() throws Exception
	{
		// note: if the queryFilter is empty, then do not return everything
		final IQueryFilter<I_C_BPartner> currentSelectionFilter = getProcessInfo().getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final CampaignId campaignId = CampaignId.ofRepoId(campaignRecordId);

		final MKTG_ContactPerson_ProcessBase contactPersonProcessBase = SpringContextHolder.instance.getBean(MKTG_ContactPerson_ProcessBase.class);

		final MKTG_ContactPerson_ProcessParams params = MKTG_ContactPerson_ProcessParams.builder()
				.selectionFilter(currentSelectionFilter)
				.campaignId(campaignId)
				.addresType(DefaultAddressType.forCode(defaultAddresType))
				.removeAllExistingContactsFromCampaign(removeAllExistingContactsFromCampaign)
				.build();

		contactPersonProcessBase.createContactPersonsForPartner(params);

		return MSG_OK;
	}

}
