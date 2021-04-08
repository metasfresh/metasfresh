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

package de.metas.contracts.bpartner.repository;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.OrgMappingId;
import de.metas.bpartner.composite.BPartnerBankAccount;
import de.metas.bpartner.composite.BPartnerContact;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_AD_Org_Mapping;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.springframework.stereotype.Repository;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class OrgMappingRepository
{
	final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

	public OrgMappingId createOrgMappingForBPartner(@NonNull final I_C_BPartner bPartnerRecord)
	{
		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BPartner.class));
		orgMapping.setValue(bPartnerRecord.getValue());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	public OrgMappingId getCreateOrgMappingId(@NonNull final BPartnerLocation location)
	{
		final OrgMappingId orgMappingId = location.getOrgMappingId();
		if (orgMappingId != null)
		{
			return orgMappingId;
		}

		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BPartner_Location.class));
		orgMapping.setValue(location.getName());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	public OrgMappingId getCreateOrgMappingId(@NonNull final BPartnerBankAccount existingBankAccountInInitialPartner)
	{
		final OrgMappingId orgMappingId = existingBankAccountInInitialPartner.getOrgMappingId();
		if (orgMappingId != null)
		{
			return orgMappingId;
		}

		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BP_BankAccount.class));
		orgMapping.setValue(existingBankAccountInInitialPartner.getIban());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	public OrgMappingId getCreateOrgMappingId(@NonNull final BPartnerContact contact)
	{
		final OrgMappingId orgMappingId = contact.getOrgMappingId();
		if (orgMappingId != null)
		{
			return orgMappingId;
		}

		final I_AD_Org_Mapping orgMapping = newInstance(I_AD_Org_Mapping.class);

		orgMapping.setAD_Org_ID(0);
		orgMapping.setAD_Table_ID(getTableId(I_C_BP_BankAccount.class));
		orgMapping.setValue(contact.getValue());

		save(orgMapping);

		return OrgMappingId.ofRepoId(orgMapping.getAD_Org_Mapping_ID());
	}

	public OrgMappingId getCreateOrgMappingId(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bPartnerRecord = bPartnerDAO.getById(bpartnerId);

		OrgMappingId orgMappingId = OrgMappingId.ofRepoIdOrNull(bPartnerRecord.getAD_Org_Mapping_ID());

		if (orgMappingId == null)
		{
			orgMappingId = createOrgMappingForBPartner(bPartnerRecord);

			bPartnerRecord.setAD_Org_Mapping_ID(orgMappingId.getRepoId());

			save(bPartnerRecord);
		}

		return orgMappingId;
	}
}
