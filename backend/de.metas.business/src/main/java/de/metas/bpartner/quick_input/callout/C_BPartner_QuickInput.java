/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.bpartner.quick_input.callout;

import de.metas.bpartner.quick_input.service.BPartnerQuickInputService;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Access;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.compiere.model.I_C_BPartner_QuickInput;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

@Component
@Callout(I_C_BPartner_QuickInput.class)
public class C_BPartner_QuickInput
{
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final BPartnerQuickInputService bpartnerQuickInputService;

	public C_BPartner_QuickInput(
			@NonNull final BPartnerQuickInputService bpartnerQuickInputService)
	{
		this.bpartnerQuickInputService = bpartnerQuickInputService;
	}

	@PostConstruct
	void postConstruct()
	{
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = I_C_BPartner_QuickInput.COLUMNNAME_IsCompany)
	public void onIsCompanyFlagChanged(@NonNull final I_C_BPartner_QuickInput record)
	{
		bpartnerQuickInputService.updateNameAndGreetingNoSave(record);
	}

	@CalloutMethod(columnNames = I_C_BPartner_QuickInput.COLUMNNAME_C_Location_ID)
	public void onLocationChanged(@NonNull final I_C_BPartner_QuickInput record)
	{
		final OrgId orgInChangeId = getOrgInChangeViaLocationPostalCode(record);
		if (orgInChangeId == null)
		{
			return;
		}

		final boolean userHasOrgPermissions = Env.getUserRolePermissions().isOrgAccess(orgInChangeId, Access.WRITE);

		if (userHasOrgPermissions)
		{
			record.setAD_Org_ID(orgInChangeId.getRepoId());
		}
	}

	@Nullable
	private OrgId getOrgInChangeViaLocationPostalCode(final @NonNull I_C_BPartner_QuickInput record)
	{
		final LocationId locationId = LocationId.ofRepoIdOrNull(record.getC_Location_ID());
		if (locationId == null)
		{
			return null;
		}

		final I_C_Location locationRecord = locationDAO.getById(locationId);
		final PostalId postalId = PostalId.ofRepoIdOrNull(locationRecord.getC_Postal_ID());
		if (postalId == null)
		{
			return null;
		}

		final I_C_Postal postalRecord = locationDAO.getPostalById(postalId);
		final OrgId orgInChargeId = OrgId.ofRepoId(postalRecord.getAD_Org_InCharge_ID());
		return orgInChargeId.isRegular() ? orgInChargeId : null;
	}
}
