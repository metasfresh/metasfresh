package org.adempiere.uom.api;

/*
 * #%L
 * ADempiere ERP - Base
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_UOM;

public interface IUOMDAO extends ISingletonService
{
	String X12DE355_Each = "PCE";
	int C_UOM_ID_Each = 100;
	String X12DE355_Kilogram = "KGM";

	/**
	 * 
	 * @param ctx
	 * @param x12de355
	 * @return uom; never return null
	 */
	I_C_UOM retrieveByX12DE355(Properties ctx, String x12de355);

	/**
	 * 
	 * @param ctx
	 * @param x12de355
	 * @param throwExIfNull if <code>false</code> and there is no UOM with the given <code>x12de355</code>, then we return <code>null</code>.
	 * @return
	 */
	I_C_UOM retrieveByX12DE355(Properties ctx, String x12de355, boolean throwExIfNull);
	
	/**
	 * Gets UOM for Each/Stuck.
	 * 
	 * @param ctx
	 * @return
	 */
	I_C_UOM retrieveEachUOM(Properties ctx);


}
