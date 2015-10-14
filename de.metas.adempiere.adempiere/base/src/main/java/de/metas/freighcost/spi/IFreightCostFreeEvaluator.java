package de.metas.freighcost.spi;

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


import org.compiere.model.I_M_InOutLine;

import de.metas.freighcost.api.IFreightCostBL;

/**
 * Other functional modules can implement this interface to make sure that shipments with certain inout lines are free
 * of shipment costs.
 * 
 * @see IFreightCostBL#registerFreightCostFreeEvaluator(IFreightCostFreeEvaluator)
 * 
 * @author ts
 * 
 */
public interface IFreightCostFreeEvaluator
{
	public boolean isFreighCostFree(I_M_InOutLine inOutLine);
}
