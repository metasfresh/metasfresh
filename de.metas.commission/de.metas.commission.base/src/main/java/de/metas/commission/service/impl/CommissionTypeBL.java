package de.metas.commission.service.impl;

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


import org.compiere.util.Util;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionType;
import de.metas.commission.service.ICommissionTypeBL;

public class CommissionTypeBL implements ICommissionTypeBL
{
	@Override
	public ICommissionType getBusinessLogic(final I_C_AdvCommissionType typeDef, final I_C_AdvComSystem_Type comSystemType)
	{
		final String className = typeDef.getClassname();

		final ICommissionType businessLogic = Util.getInstance(ICommissionType.class, className);
		businessLogic.setComSystemType(comSystemType);
		return businessLogic;
	}

	@Override
	public ICommissionType getBusinessLogic(final I_C_AdvComSystem_Type comSystemType)
	{
		final I_C_AdvCommissionType typeDef = comSystemType.getC_AdvCommissionType();
		return getBusinessLogic(typeDef, comSystemType);
	}

}
