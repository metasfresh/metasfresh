/*
 * #%L
 * de.metas.async
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.async.api;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.logging.LogManager;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

@UtilityClass
public class WorkPackageLockHelper
{
	private static final Logger logger = LogManager.getLogger(WorkPackageLockHelper.class);

	public static boolean unlockNoFail(final I_C_Queue_WorkPackage workPackage)
	{
		try
		{
			workPackage.setLockedAt(null);
			InterfaceWrapperHelper.save(workPackage);
			return true;
		}
		catch (final Exception e)
		{
			logger.warn("Got exception while clearing LockedAt for " + workPackage, e);
			return false;
		}
	}
}
