package de.metas.contracts.commission.commissioninstance.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.contracts.commission.model.I_C_Commission_Share;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_C_Commission_Share.class)
@Component
public class C_Commission_Share
{
	private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);

	@ModelChange(//
			timings = ModelValidator.TYPE_AFTER_CHANGE, //
			ifColumnsChanged = { I_C_Commission_Share.COLUMNNAME_PointsSum_Forecasted, I_C_Commission_Share.COLUMNNAME_PointsSum_Invoiceable, I_C_Commission_Share.COLUMNNAME_PointsSum_Invoiced })
	public void invalidateIc(@NonNull final I_C_Commission_Share shareRecord)
	{
		invoiceCandidateHandlerBL.invalidateCandidatesFor(shareRecord);
	}
}
