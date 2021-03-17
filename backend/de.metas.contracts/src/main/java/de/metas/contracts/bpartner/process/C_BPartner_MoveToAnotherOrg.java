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
/*
 * #%L
 * de.metas.swat.base
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

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.bpartner.service.OrgChangeRequest;
import de.metas.contracts.bpartner.service.OrgChangeService;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class C_BPartner_MoveToAnotherOrg extends JavaProcess implements IProcessPrecondition,
		IProcessParametersCallout,
		IProcessDefaultParametersProvider
{
	@Param(parameterName = "AD_Org_Target_ID", mandatory = true)
	private OrgId p_orgTargetId;

	@Param(parameterName = "M_Product_ID")
	private ProductId p_membershipProductId;

	@Param(parameterName = "DateFrom", mandatory = true)
	private LocalDate p_startDate;

	@Param(parameterName = "IsShowMembershipParameter", mandatory = true)
	private boolean isShowMembershipParameter;

	//final OrgChangeRepository repo = SpringContextHolder.instance.getBean(OrgChangeRepository.class);
	final OrgChangeService service = SpringContextHolder.instance.getBean(OrgChangeService.class);

	@Override
	protected String doIt() throws Exception
	{
		final I_C_BPartner bpartnerRecord = getProcessInfo().getRecord(I_C_BPartner.class);

		final OrgChangeRequest orgChangeRequest = OrgChangeRequest.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.startDate(p_startDate)
				.membershipProductId(p_membershipProductId)
				.orgToId(p_orgTargetId)
				.build();

		service.moveBPartnerToOrg(orgChangeRequest);

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
		if ("AD_Org_Target_ID".equals(parameterName))
		{
			if(p_orgTargetId == null)
			{
				return;
			}
			final BPartnerId partnerId = BPartnerId.ofRepoId(getRecord_ID());

			final LocalDate orgChangeDate = CoalesceUtil.coalesce(p_startDate, SystemTime.asLocalDate());

			if (service.hasMembershipSubscriptions(partnerId, orgChangeDate) && service.hasAnyMembershipProduct(p_orgTargetId))
			{
				isShowMembershipParameter = true;
			}
			else
			{
				isShowMembershipParameter = false;
			}
		}
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if ("DateFrom".equals(parameter.getColumnName()))
		{
			return SystemTime.asLocalDate().plusDays(1);
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}
}
