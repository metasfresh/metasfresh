/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.interceptor;

import de.metas.contracts.callorder.detail.document.DocumentChangeHandler_InOut;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut_CallOrder
{
	private final DocumentChangeHandler_InOut shipmentDocHandler;

	public M_InOut_CallOrder(@NonNull final DocumentChangeHandler_InOut shipmentDocHandler)
	{
		this.shipmentDocHandler = shipmentDocHandler;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REACTIVATE })
	public void handleReactivate(@NonNull final I_M_InOut inOut)
	{
		shipmentDocHandler.onReactivate(inOut);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void handleComplete(@NonNull final I_M_InOut inOut)
	{
		shipmentDocHandler.onComplete(inOut);
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_REVERSECORRECT })
	public void handleReverse(@NonNull final I_M_InOut inOut)
	{
		shipmentDocHandler.onReverse(inOut);
	}
}
