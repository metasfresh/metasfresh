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


import java.sql.Timestamp;
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_Period;

import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.MCAdvCommissionPayrollLine;

public interface ICommissionPayrollBL extends ISingletonService
{

	/**
	 * Created commission payroll lines for the given term, instance and period. Note: Implementors don't record those lines as commission facts, because that's the task of
	 * {@link ICommissionFactBL#recordCommissionPayrollLine(MCAdvCommissionPayrollLine, de.metas.commission.model.I_C_AdvCommissionPayroll)} .
	 * 
	 * @param term
	 * @param instance
	 * @param period
	 * @return The created lines. Might be empty.
	 */
	List<MCAdvCommissionPayrollLine> createPayrollLine(IAdvComInstance instance, I_C_Period period, Timestamp date, int adPInstanceId);

}
