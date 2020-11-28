package de.metas.bpartner.process;

import org.compiere.SpringContextHolder;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.security.permissions.bpartner_hierarchy.BPartnerHierarchyRecordAccessHandler;
import de.metas.security.permissions.bpartner_hierarchy.BPartnerSalesRepChangedEvent;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2020 metas GmbH
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

public class C_BPartner_SetSalesRep extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	private final IBPartnerBL bpartnersService = Services.get(IBPartnerBL.class);
	private final BPartnerHierarchyRecordAccessHandler bpartnerHierarchyService = SpringContextHolder.instance.getBean(BPartnerHierarchyRecordAccessHandler.class);

	private static final String PARAM_SalesRep_ID = "SalesRep_ID";
	@Param(parameterName = PARAM_SalesRep_ID, mandatory = true)
	private UserId salesRepId;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (bpartnerId == null)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("no BPartnerId");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (PARAM_SalesRep_ID.equals(parameter.getColumnName()))
		{
			return getCurrentSalesRepId();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final BPartnerId bpartnerId = getBPartnerId();

		final UserId newSalesRepId = this.salesRepId;
		final UserId oldSalesRepId = bpartnersService.setSalesRepId(bpartnerId, newSalesRepId);

		bpartnerHierarchyService.onBPartnerSalesRepChanged(BPartnerSalesRepChangedEvent.builder()
				.bpartnerId(bpartnerId)
				.oldSalesRepId(oldSalesRepId)
				.newSalesRepId(newSalesRepId)
				.changedBy(getUserId())
				.build());

		return MSG_OK;
	}

	private BPartnerId getBPartnerId()
	{
		return BPartnerId.ofRepoId(getRecord_ID());
	}

	private UserId getCurrentSalesRepId()
	{
		final BPartnerId bpartnerId = getBPartnerId();
		return bpartnersService.getSalesRepIdOrNull(bpartnerId);
	}

}
