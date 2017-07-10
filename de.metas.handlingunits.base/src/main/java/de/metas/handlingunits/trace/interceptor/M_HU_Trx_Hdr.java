package de.metas.handlingunits.trace.interceptor;

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.hutransaction.IHUTrxDAO;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.trace.HUTraceEventsService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_M_HU_Trx_Hdr.class)
/* package */  final class M_HU_Trx_Hdr
{
	@ModelChange(timings =
		{
				ModelValidator.TYPE_AFTER_CHANGE,
				ModelValidator.TYPE_AFTER_NEW
		}, ifColumnsChanged = I_M_HU_Trx_Hdr.COLUMNNAME_Processed, afterCommit = true)
	public void addTraceEvent(@NonNull final I_M_HU_Trx_Hdr huTrxHeader)
	{
		if (!huTrxHeader.isProcessed())
		{
			return;
		}

		final IHUTrxDAO huTrxDAO = Services.get(IHUTrxDAO.class);
		final List<I_M_HU_Trx_Line> huTrxLines = huTrxDAO.retrieveTrxLines(huTrxHeader);

		final HUTraceEventsService huTraceEventsService = HUTraceModuleInterceptor.INSTANCE.getHUTraceEventsService();
		huTraceEventsService.createAndAddFor(huTrxHeader, huTrxLines);
	}
}
