package de.metas.edi.model.validator;

import de.metas.edi.api.EDIDocOutBoundLogService;
import de.metas.edi.model.I_C_Doc_Outbound_Log;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.edi
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

@Interceptor(I_C_Doc_Outbound_Log.class)
@Component
public class C_Doc_Outbound_Log
{
	private final EDIDocOutBoundLogService ediDocOutBoundLogService;

	private C_Doc_Outbound_Log(@NonNull final EDIDocOutBoundLogService ediDocOutBoundLogService)
	{
		this.ediDocOutBoundLogService = ediDocOutBoundLogService;
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setEdiExportStatusFromInvoice(@NonNull final I_C_Doc_Outbound_Log docOutboundLogRecord)
	{

		ediDocOutBoundLogService.setEdiExportStatusFromInvoiceRecord(docOutboundLogRecord);
	}
}
