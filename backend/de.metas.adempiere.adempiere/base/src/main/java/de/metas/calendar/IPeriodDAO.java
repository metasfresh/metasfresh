package de.metas.calendar;

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

<<<<<<< HEAD
=======
import de.metas.document.DocBaseType;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_PeriodControl;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.util.Map;
import java.util.Properties;

<<<<<<< HEAD
import org.compiere.model.I_C_PeriodControl;

import de.metas.util.ISingletonService;

public interface IPeriodDAO extends ISingletonService
{
	Map<String, I_C_PeriodControl> retrievePeriodControlsByDocBaseType(Properties ctx, int periodId);
=======
public interface IPeriodDAO extends ISingletonService
{
	Map<DocBaseType, I_C_PeriodControl> retrievePeriodControlsByDocBaseType(Properties ctx, int periodId);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

}
