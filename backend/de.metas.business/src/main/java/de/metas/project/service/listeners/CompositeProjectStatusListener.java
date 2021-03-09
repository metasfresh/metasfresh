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

import com.google.common.collect.ImmutableList;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.model.I_C_Project;

import java.util.List;

@EqualsAndHashCode
@ToString
public class CompositeProjectStatusListener implements ProjectStatusListener
{
	public static CompositeProjectStatusListener ofList(@NonNull final List<ProjectStatusListener> listeners)
	{
		return new CompositeProjectStatusListener(listeners);
	}

	private final ImmutableList<ProjectStatusListener> listeners;

	private CompositeProjectStatusListener(@NonNull final List<ProjectStatusListener> listeners)
	{
		this.listeners = ImmutableList.copyOf(listeners);
	}

	@Override
	public void onBeforeClose(@NonNull final I_C_Project project)
	{
		listeners.forEach(listener -> listener.onBeforeClose(project));
	}

	@Override
	public void onAfterClose(@NonNull final I_C_Project project)
	{
		listeners.forEach(listener -> listener.onAfterClose(project));
	}

	@Override
	public void onBeforeUnClose(@NonNull final I_C_Project project)
	{
		listeners.forEach(listener -> listener.onBeforeUnClose(project));
	}

	@Override
	public void onAfterUnClose(@NonNull final I_C_Project project)
	{
		listeners.forEach(listener -> listener.onAfterUnClose(project));
	}
}
