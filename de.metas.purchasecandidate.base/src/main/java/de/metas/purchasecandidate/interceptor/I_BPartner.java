package de.metas.purchasecandidate.interceptor;

import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_AD_Client;
import org.springframework.stereotype.Component;

import de.metas.vertical.pharma.vendor.gateway.msv3.interceptor.MSV3PharmaImportPartnerInterceptor;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_I_BPartner;



/*
 * #%L
 * de.metas.purchasecandidate.base
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
@Interceptor(I_BPartner.class)
@Component
public class I_BPartner
{
	@Init
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addImportInterceptor(I_I_BPartner.Table_Name, BPPurchaseScheduleImportPartnerInterceptor.instance);
		engine.addImportInterceptor(I_I_BPartner.Table_Name, MSV3PharmaImportPartnerInterceptor.instance);
	}
}
