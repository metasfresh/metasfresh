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


import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.commission.custom.type.ICommissionType;
import de.metas.commission.model.I_C_AdvComSystem_Type;
import de.metas.commission.model.I_C_AdvCommissionType;

public interface ICommissionTypeDAO extends ISingletonService
{

	I_C_AdvCommissionType retrieve(final Properties ctx, final int typeId, final String trxName);

	I_C_AdvCommissionType retrieveForClass(final Properties ctx,
			final Class<? extends ICommissionType> commissionTypeClass,
			final int orgId,
			final String trxName);

	List<I_C_AdvComSystem_Type> retrieveSystemTypesForCommissionType(I_C_AdvCommissionType type);

}
