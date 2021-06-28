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

package de.metas.contracts.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.PostalId;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.util.Check;
import de.metas.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Postal;

import javax.annotation.Nullable;
import java.time.Instant;

public class C_BPartner_MoveToAnotherOrg_PostalChange extends C_BPartner_MoveToAnotherOrg_ProcessHelper
{

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final BPartnerId partnerId = BPartnerId.ofRepoId(getRecord_ID());
		final Instant orgChangeDate = CoalesceUtil.coalesce(p_startDate, SystemTime.asInstant());

		if (PARAM_DATE_ORG_CHANGE.equals(parameter.getColumnName()))
		{
			return SystemTime.asLocalDate().plusDays(1);
		}
		else if (PARAM_AD_ORG_TARGET_ID.equals(parameter.getColumnName()))
		{

			final BPartnerLocationId bpLocationId = bpartnerDAO.retrieveLastUpdatedLocation(partnerId);

			final I_C_BPartner_Location bpLocationRecord = bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpLocationId);

			final LocationId locationId = LocationId.ofRepoId(bpLocationRecord.getC_Location_ID());
			final I_C_Location locationRecord = locationDAO.getById(locationId);
			final PostalId newPostalId = PostalId.ofRepoIdOrNull(locationRecord.getC_Postal_ID());

			Check.assumeNotNull(newPostalId, "The Postal should not be null. If it was null, the process would not be triggered");

			final I_C_Postal postalRecord = locationDAO.getPostalById(newPostalId);

			return postalRecord.getAD_Org_InCharge_ID();
		}
		else if (PARAM_IS_SHOW_MEMBERSHIP_PARAMETER.equals(parameter.getColumnName()))
		{
			if (p_orgTargetId == null)
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}

			final OrgChangeBPartnerComposite orgChangePartnerComposite = service.getByIdAndOrgChangeDate(partnerId, orgChangeDate);

			isShowMembershipParameter = orgChangePartnerComposite.hasMembershipSubscriptions()
					&& service.hasAnyMembershipProduct(p_orgTargetId);

			return isShowMembershipParameter;
		}
		else if (PARAM_GroupCategory_ID.equals(parameter.getColumnName()))
		{
			if (p_orgTargetId == null)
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}

			if (!isShowMembershipParameter)
			{
				return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
			}

			final OrgChangeBPartnerComposite orgChangePartnerComposite = service.getByIdAndOrgChangeDate(partnerId, orgChangeDate);

			final GroupCategoryId groupCategoryId = orgChangePartnerComposite.getGroupCategoryId();

			if (groupCategoryId != null && service.isGroupCategoryContainsProductsInTargetOrg(groupCategoryId, p_orgTargetId))
			{
				return groupCategoryId.getRepoId();
			}

		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

}
