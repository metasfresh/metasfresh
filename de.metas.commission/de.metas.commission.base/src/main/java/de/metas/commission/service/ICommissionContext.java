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
import java.util.Properties;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_Sponsor;

public interface ICommissionContext
{
	Properties getCtx();

	String getTrxName();

	I_C_Sponsor getC_Sponsor();

	Timestamp getDate();

	ICommissionContext setDate(Timestamp date);

	ICommissionType getCommissionType();

	de.metas.adempiere.model.I_M_Product getM_Product();

	I_C_AdvComSystem getC_AdvComSystem();

	I_C_AdvComSystem_Type getC_AdvComSystem_Type();
}
