package de.metas.commission.service;

/*
 * #%L
 * de.metas.commission.base
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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.IInstanceTrigger;
import de.metas.commission.model.I_C_AdvCommissionTerm;
import de.metas.commission.model.I_C_Sponsor;

public interface IInstanceTriggerBL extends ISingletonService
{
	String setCommissionPoints(Properties ctx, IInstanceTrigger it, boolean nullIfOk, String trxName);

	BigDecimal retrieveCommissionPoints(Properties ctx, IInstanceTrigger it, String trxName);

	String setCommissionPointsSum(Properties ctx, IInstanceTrigger it, boolean nullIfOk, String trxName);

	void updateCommissionPointsNet(IInstanceTrigger it);

	boolean isInCorrectProductCategory(I_C_AdvCommissionTerm term, I_C_Sponsor sponsor, Object ilOrOl);

	/**
	 * Checks if given {@link IInstanceTrigger} shall be exempted from commission calculation. e.g.
	 * <ul>
	 * <li>an {@link I_C_OrderLine} which has M_Promotion_ID set is exempted
	 * <li>an {@link I_C_OrderLine} which is a comment line is exempted
	 * </ul>
	 * 
	 * @param it
	 * @return true if is commission exempt
	 */
	boolean isCommissionExempt(IInstanceTrigger it);
}
