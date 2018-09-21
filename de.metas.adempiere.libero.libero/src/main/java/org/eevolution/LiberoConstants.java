package org.eevolution;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import org.adempiere.service.ISysConfigBL;

import de.metas.util.Services;

/**
 * Misc Libero module constants.
 * 
 * @author tsa
 *
 */
public final class LiberoConstants
{
	/** Entity Type: e-Evolution Libero Manufacturing Management */
	public static final String ENTITYTYPE_Manufacturing = "EE01";
	/** Entity Type: e-Evolution Libero Human Resource & Payroll */
	public static final String ENTITYTYPE_HR = "EE02";
	/** Entity Type: e-Evolution Libero Global Tax Management */
	public static final String ENTITYTYPE_GlobalTaxManagement = "EE04";
	/** Entity Type: e-Evolution Replication Data */
	public static final String ENTITYTYPE_Replication = "EE05";
	
	private static final String SYSCONFIG_MRP_POQ_Disabled = "org.eevolution.LiberoConstants.MRP_POQ_Disabled";

	public static final boolean isMRP_POQ_Disabled()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final boolean defaultValue = false;
		return sysConfigBL.getBooleanValue(SYSCONFIG_MRP_POQ_Disabled, defaultValue);
	}

	private LiberoConstants()
	{
		super();
	}
}
