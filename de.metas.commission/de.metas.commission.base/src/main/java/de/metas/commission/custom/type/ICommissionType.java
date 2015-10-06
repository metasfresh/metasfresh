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

import de.metas.adempiere.service.IParameterizable;
import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.MCAdvCommissionFactCand;

public interface ICommissionType
{
	void evaluateCandidate(MCAdvCommissionFactCand candidate, final String status, final int adPinstanceId);

	/**
	 * NOTE: don't call it directly, it will be called by API
	 * 
	 * @param comSystemType
	 */
	void setComSystemType(I_C_AdvComSystem_Type comSystemType);

	I_C_AdvComSystem_Type getComSystemType();

	BigDecimal getFactor();

	BigDecimal getPercent(IAdvComInstance inst, String status, Timestamp date);

	/**
	 * Returns the commission points for the given parameters. Note that the result of this method shall not be multiplied with a qty! Example: if product A has 10 commission points and po is an
	 * orderLine with Product A and qty = 5, then this method returns 50 (rather than 10).
	 * 
	 * @param inst
	 * @param status
	 * @param date
	 * @param po
	 * @return
	 */
	BigDecimal getCommissionPointsSum(IAdvComInstance inst, String status, Timestamp date, Object po);

	/**
	 * Returns those parameters that are stored for a specific {@link de.metas.commission.model.I_C_AdvComSystem_Type}.
	 * 
	 * @param ctx
	 * @param system
	 * @param trxName
	 * @return
	 */
	IParameterizable getInstanceParams(final Properties ctx, final I_C_AdvComSystem system, final String trxName);

	/**
	 * Returns those parameters that are stored for a specific {@link de.metas.commission.model.I_C_AdvCommissionTerm}, because they can differ among instances that belong to the same commission trigger and the same commission
	 * type.
	 * 
	 * @param ctx
	 * @param contract
	 * @param trxName
	 * @return
	 */
	IParameterizable getSponsorParams(final Properties ctx, final I_C_AdvCommissionCondition contract, final String trxName);

	boolean isCommissionCalculated();

}
