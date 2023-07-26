/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.bpartner.service;

import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerComposite;
import lombok.NonNull;
import org.compiere.model.I_AD_OrgChange_History;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class OrgChangeHistoryRepository
{
	public I_AD_OrgChange_History getOrgChangeHistoryById(@NonNull final OrgChangeHistoryId orgChangeHistoryId)
	{
		return load(orgChangeHistoryId, I_AD_OrgChange_History.class);
	}

	public OrgChangeHistoryId createOrgChangeHistory(
			@NonNull final OrgChangeRequest orgChangeRequest,
			@NonNull final OrgMappingId orgMappingId,
			@NonNull final BPartnerComposite bPartnerToComposite)
	{
		final I_AD_OrgChange_History orgChangeHistory = newInstance(I_AD_OrgChange_History.class);

		orgChangeHistory.setAD_Org_From_ID(orgChangeRequest.getOrgFromId().getRepoId());
		orgChangeHistory.setAD_OrgTo_ID(orgChangeRequest.getOrgToId().getRepoId());
		orgChangeHistory.setAD_Org_Mapping_ID(orgMappingId.getRepoId());
		orgChangeHistory.setC_BPartner_From_ID(orgChangeRequest.getBpartnerId().getRepoId());
		orgChangeHistory.setC_BPartner_To_ID(bPartnerToComposite.getBpartner().getId().getRepoId());
		orgChangeHistory.setDate_OrgChange(TimeUtil.asTimestamp(orgChangeRequest.getStartDate()));

		saveRecord(orgChangeHistory);

		return OrgChangeHistoryId.ofRepoId(orgChangeHistory.getAD_OrgChange_History_ID());
	}
}
