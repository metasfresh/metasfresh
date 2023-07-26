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
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.bpartner.service.OrgChangeBPartnerComposite;
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.bpartner.service.OrgChangeService;
import de.metas.order.compensationGroup.GroupCategoryId;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_CompensationGroup_Schema_Category;

import java.time.Instant;

public abstract class C_BPartner_MoveToAnotherOrg_ProcessHelper extends JavaProcess implements IProcessPrecondition,
		IProcessParametersCallout,
		IProcessDefaultParametersProvider
{

	final OrgChangeService service = SpringContextHolder.instance.getBean(OrgChangeService.class);
	final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

	public static final String PARAM_AD_ORG_TARGET_ID = "AD_Org_Target_ID";
	public static final String PARAM_GroupCategory_ID = I_C_CompensationGroup_Schema_Category.COLUMNNAME_C_CompensationGroup_Schema_Category_ID;
	public static final String PARAM_DATE_ORG_CHANGE = "Date_OrgChange";
	public static final String PARAM_IS_SHOW_MEMBERSHIP_PARAMETER = "IsShowMembershipParameter";

	@Param(parameterName = PARAM_AD_ORG_TARGET_ID, mandatory = true)
	protected OrgId p_orgTargetId;

	@Param(parameterName = PARAM_GroupCategory_ID)
	protected GroupCategoryId p_groupCategoryId;

	@Param(parameterName = PARAM_DATE_ORG_CHANGE, mandatory = true)
	protected Instant p_startDate;

	@Param(parameterName = PARAM_IS_SHOW_MEMBERSHIP_PARAMETER, mandatory = true)
	protected boolean isShowMembershipParameter;

	@Override
	protected String doIt() throws Exception
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(getProcessInfo().getRecord_ID());

		final I_C_BPartner bpartnerRecord = bpartnerBL.getById(bpartnerId);

		final OrgChangeRequest orgChangeRequest = OrgChangeRequest.builder()
				.bpartnerId(bpartnerId)
				.startDate(p_startDate)
				.groupCategoryId(p_groupCategoryId)
				.orgFromId(OrgId.ofRepoId(bpartnerRecord.getAD_Org_ID()))
				.orgToId(p_orgTargetId)
				.build();

		service.moveToNewOrg(orgChangeRequest);

		return MSG_OK;
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_AD_ORG_TARGET_ID.equals(parameterName) || PARAM_DATE_ORG_CHANGE.equals(parameterName))
		{
			if (p_orgTargetId == null)
			{
				return;
			}
			final BPartnerId partnerId = BPartnerId.ofRepoId(getRecord_ID());

			final Instant orgChangeDate = CoalesceUtil.coalesce(p_startDate, SystemTime.asInstant());

			final OrgChangeBPartnerComposite orgChangePartnerComposite = service.getByIdAndOrgChangeDate(partnerId, orgChangeDate);

			isShowMembershipParameter = orgChangePartnerComposite.hasMembershipSubscriptions()
					&& service.hasAnyMembershipProduct(p_orgTargetId);

			final GroupCategoryId groupCategoryId = orgChangePartnerComposite.getGroupCategoryId();

			if (groupCategoryId != null && service.isGroupCategoryContainsProductsInTargetOrg(groupCategoryId, p_orgTargetId))
			{
				p_groupCategoryId = groupCategoryId;
			}
		}
	}

}
