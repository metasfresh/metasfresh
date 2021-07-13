/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.project.service.listeners;

import org.compiere.model.I_C_Project;

public interface ProjectStatusListener
{
	default void onBeforeClose(final I_C_Project project)
	{
	}

	default void onAfterClose(final I_C_Project project)
	{
	}

	default void onBeforeUnClose(final I_C_Project project)
	{
	}

	default void onAfterUnClose(final I_C_Project project)
	{
	}
}
