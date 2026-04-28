package de.metas.edi.model.validator;



/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.api.impl.EDIBPartnerConfigService;
import de.metas.edi.model.I_C_Order;
import de.metas.order.IOrderBL;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	@NonNull private final IOrderBL orderBL = Services.get(IOrderBL.class);
	@NonNull private final DesadvBL desadvBL;
	@NonNull private final EDIBPartnerConfigService ediBpartnerConfigService;

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void assertReActivationAllowed(final I_C_Order order)
	{
		final EDIDesadvId ediDesadvId = EDIDesadvId.ofRepoIdOrNull(order.getEDI_Desadv_ID());
		if (ediDesadvId == null)
		{
			return;
		}
		final EDIExportStatus desadvEDIStatus = EDIExportStatus.ofCode(desadvBL.getById(ediDesadvId).getEDI_ExportStatus());
		if (desadvEDIStatus.isInProgressOrSend())
		{
			throw new AdempiereException("@NotAllowed@ (@EDI_Desadv_ID@ @EDIStatus@: " + desadvEDIStatus.name() + ")")
					.markAsUserValidationError();
		}
	}

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void addToDesadv(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return;
		}

		if (Check.isEmpty(order.getPOReference()))
		{
			return;
		}

		final BPartnerId bpartnerId = orderBL.getEffectiveDropshipPartnerId(order);
		if (!ediBpartnerConfigService.isEdiDesadvRecipient(bpartnerId))
		{
			return;
		}

		desadvBL.addToDesadvCreateForOrderIfNotExist(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void removeFromDesadv(final I_C_Order order)
	{
		if (EDIDesadvId.ofRepoIdOrNull(order.getEDI_Desadv_ID()) != null)
		{
			desadvBL.removeOrderFromDesadv(order);
		}
	}
}
