package de.metas.fresh.ordercheckup;

/*
 * #%L
 * de.metas.fresh.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Order;

import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;

public interface IOrderCheckupBL extends ISingletonService
{
	/**
	 * @return true if given order is eligible for generating {@link I_C_Order_MFGWarehouse_Report}s.
	 */
	boolean isEligibleForReporting(I_C_Order order);

	/**
	 * Generate all {@link I_C_Order_MFGWarehouse_Report}s if given order is eligible.
	 * 
	 * All previous generated reports will be automatically voided.
	 * 
	 * @param order
	 */
	void generateReportsIfEligible(I_C_Order order);

	/**
	 * @param order
	 * @return true if the system is configured to generate the {@link I_C_Order_MFGWarehouse_Report}s when the given order is completed.
	 */
	boolean isGenerateReportsOnOrderComplete(I_C_Order order);

	/** Void all generated {@link I_C_Order_MFGWarehouse_Report}s for given order */
	void voidReports(I_C_Order order);
}
