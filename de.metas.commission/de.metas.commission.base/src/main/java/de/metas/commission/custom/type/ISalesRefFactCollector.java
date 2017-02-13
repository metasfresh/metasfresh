package de.metas.commission.custom.type;

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
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_M_DiscountSchema;

import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.service.ICommissionContext;

public interface ISalesRefFactCollector extends ICommissionType
{
	boolean isCompress(ICommissionContext context);

	/**
	 * Returns the rank which the given sponsor should have with the given turnover points.
	 * 
	 * @param commissionCtx
	 * @param turnoverPoints
	 * @param status
	 * @return
	 */
	I_C_AdvCommissionSalaryGroup retrieveSalaryGroup(
			ICommissionContext commissionCtx, BigDecimal turnoverPoints, String status);

	String[] getTurnoverFactNames(ICommissionContext commissionCtx);

	int getPeriodsLookBack(Properties ctx, I_C_AdvCommissionCondition contract, String trxName);

	I_M_DiscountSchema retrieveDiscountSchema(I_C_AdvCommissionSalaryGroup forecastRank, I_C_Sponsor sponsor, Timestamp loginDate);
}
