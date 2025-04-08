/*
 * #%L
 * de.metas.xfactura.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.xfactura.base.interceptor;

import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.xfactura.base.XFacturaService;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.compiere.model.ModelValidator;

@Interceptor(I_C_Doc_Outbound_Log.class)
@RequiredArgsConstructor
public class C_Doc_Outbound_Log
{
	private final XFacturaService xFacturaService;

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void afterNew(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		//TODO set initial xFactura generateStatus based on docType supported and active for Partner (don't generate, generate)
	}

	public void afterChange(@NonNull final I_C_Doc_Outbound_Log docOutboundLog)
	{
		//TODO generate if status generate
	}
}
