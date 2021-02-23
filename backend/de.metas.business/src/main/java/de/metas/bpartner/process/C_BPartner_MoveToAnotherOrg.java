/*
 * #%L
 * de.metas.business
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

package de.metas.bpartner.process;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.OrgChangeParameters;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;

import java.time.LocalDate;

public class C_BPartner_MoveToAnotherOrg extends JavaProcess implements IProcessPrecondition
{
	@Param(parameterName = "AD_Org_New_ID", mandatory = true)
	private OrgId p_orgNewId;

	@Param(parameterName = "M_Membership_Product_ID", mandatory = true)
	private ProductId p_membershipProductId;

	@Param(parameterName = "StartDate", mandatory = true)
	private LocalDate p_startDate;

	@Override
	protected String doIt() throws Exception
	{
		final I_C_BPartner bpartnerRecord = getProcessInfo().getRecord(I_C_BPartner.class);

		final OrgChangeParameters orgChangeParameters = OrgChangeParameters.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpartnerRecord.getC_BPartner_ID()))
				.startDate(p_startDate)
				.membershipProductId(p_membershipProductId)
				.orgToId(p_orgNewId)
				.build();

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

}
