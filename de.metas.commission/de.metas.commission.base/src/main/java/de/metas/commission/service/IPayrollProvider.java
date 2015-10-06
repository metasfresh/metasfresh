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


import java.util.Collection;
import java.util.Properties;

import org.eevolution.model.I_HR_Concept;
import org.eevolution.model.I_HR_Employee;
import org.eevolution.model.I_HR_Movement;
import org.eevolution.model.I_HR_Period;
import org.eevolution.model.I_HR_Process;

import de.metas.commission.model.MCAdvCommissionPayrollLine;

public interface IPayrollProvider
{

	Collection<I_HR_Movement> createMovements(Properties ctx,
			I_HR_Process process, I_HR_Concept concept, I_HR_Period period,
			I_HR_Employee employee, String trxName);

	int retrieveTaxId(I_HR_Employee employee, I_HR_Period period, MCAdvCommissionPayrollLine line);

}
