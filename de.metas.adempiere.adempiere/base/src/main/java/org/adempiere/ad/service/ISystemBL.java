package org.adempiere.ad.service;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_System;

public interface ISystemBL extends ISingletonService
{
	I_AD_System get(Properties ctx);

	String getProfileInfo(boolean recalc);

	String getStatisticsInfo(boolean recalc);

	boolean setInfo(I_AD_System system);

	boolean isLDAP();

	boolean isLDAP(String userName, String password);

	boolean isSwingRememberUserAllowed();

	boolean isSwingRememberPasswordAllowed();

	boolean isValid();
}

