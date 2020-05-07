package org.compiere.report.impl;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.HashMap;
import java.util.Map;

import org.adempiere.util.Check;
import org.compiere.report.IJasperService;
import org.compiere.report.IJasperServiceRegistry;

public class JasperServiceRegistry implements IJasperServiceRegistry
{

	private final Map<ServiceType, IJasperService> type2service = new HashMap<IJasperServiceRegistry.ServiceType, IJasperService>();

	@Override
	public IJasperService getJasperService(final ServiceType serviceType)
	{
		final IJasperService jasperService = type2service.get(serviceType);
		Check.errorIf(jasperService == null, "Missing IJasperService implementation for ServiceType={}", serviceType);
		
		return jasperService;
	}

	@Override
	public IJasperService registerJasperService(final ServiceType serviceType, final IJasperService jasperService)
	{
		return type2service.put(serviceType, jasperService);
	}

	@Override
	public boolean isRegisteredServiceFor(final ServiceType serviceType)
	{
		return type2service.containsKey(serviceType);
	}
}
