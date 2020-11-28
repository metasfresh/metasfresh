package de.metas.requisition.interceptor;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.compiere.model.I_M_Requisition;
import org.springframework.stereotype.Component;

import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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

@Component
@Interceptor(I_M_Requisition.class)
public class M_Requisition
{
	@Init
	void init()
	{
		final IProgramaticCalloutProvider registry = Services.get(IProgramaticCalloutProvider.class);
		registry.registerAnnotatedCallout(new de.metas.requisition.callout.M_Requisition());
		registry.registerAnnotatedCallout(new de.metas.requisition.callout.M_RequisitionLine());
	}
}
