package de.metas.customs.process;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.customs.CustomsInvoiceId;
import de.metas.customs.CustomsInvoiceService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
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

public class M_InOut_AddTo_CustomsInvoice extends JavaProcess implements IProcessPrecondition
{
	private final CustomsInvoiceService customsInvoiceService = SpringContextHolder.instance.getBean(CustomsInvoiceService.class);
	private final ShipmentLinesForCustomsInvoiceRepo shipmentLinesForCustomsInvoiceRepo = SpringContextHolder.instance.getBean(ShipmentLinesForCustomsInvoiceRepo.class);

	@Param(parameterName = "C_BPartner_ID")
	private BPartnerId p_BPartnerId;

	@Param(parameterName = "C_BPartner_Location_ID")
	private int p_C_BPartner_Location_ID;

	@Param(parameterName = "C_Customs_Invoice_ID")
	private CustomsInvoiceId p_C_Customs_Invoice_ID;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
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
		final IQueryFilter<I_M_InOut> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(p_BPartnerId, p_C_BPartner_Location_ID);

		if (p_C_Customs_Invoice_ID == null)
		{
			customsInvoiceService.generateNewCustomsInvoice(bpartnerLocationId, null, queryFilter);
		}

		else
		{
			customsInvoiceService.addShipmentsToCustomsInvoice(p_C_Customs_Invoice_ID, queryFilter);
		}

		return MSG_OK;

	}

}
