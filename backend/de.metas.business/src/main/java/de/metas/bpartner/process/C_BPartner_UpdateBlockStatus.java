/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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
import de.metas.bpartner.blockstatus.BPartnerBlockStatusService;
import de.metas.bpartner.blockstatus.BlockStatus;
import de.metas.bpartner.blockstatus.CreateBPartnerBlockStatusRequest;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_BlockStatus;

import java.util.Iterator;

public class C_BPartner_UpdateBlockStatus extends JavaProcess implements IProcessPrecondition
{
	@Param(parameterName = I_C_BPartner_BlockStatus.COLUMNNAME_BlockStatus, mandatory = true)
	private BlockStatus blockStatus;

	@Param(parameterName = I_C_BPartner_BlockStatus.COLUMNNAME_Reason)
	private String reason;

	private final BPartnerBlockStatusService bPartnerBlockStatusService = SpringContextHolder.instance.getBean(BPartnerBlockStatusService.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		getSelectedPartners()
				.forEachRemaining(bPartnerRecord -> {
					final CreateBPartnerBlockStatusRequest createBPartnerBlockStatusRequest = CreateBPartnerBlockStatusRequest.builder()
							.bPartnerId(BPartnerId.ofRepoId(bPartnerRecord.getC_BPartner_ID()))
							.blockStatus(blockStatus)
							.orgId(OrgId.ofRepoId(bPartnerRecord.getAD_Org_ID()))
							.reason(reason)
							.build();

					bPartnerBlockStatusService.createBPartnerBlockStatus(createBPartnerBlockStatusRequest);
				});

		return MSG_OK;
	}

	@NonNull
	private Iterator<I_C_BPartner> getSelectedPartners()
	{
		return retrieveAllSelectedRecordsQueryBuilder(I_C_BPartner.class)
				.create()
				.iterate(I_C_BPartner.class);
	}
}
