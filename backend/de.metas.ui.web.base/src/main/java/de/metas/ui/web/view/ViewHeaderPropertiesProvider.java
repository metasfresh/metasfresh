package de.metas.ui.web.view;

import java.util.Set;

import javax.annotation.Nullable;

import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

public interface ViewHeaderPropertiesProvider
{
	@Nullable
	String getAppliesOnlyToTableName();

	@NonNull
	ViewHeaderProperties computeHeaderProperties(@NonNull IView view);
	
	ViewHeaderPropertiesIncrementalResult computeIncrementallyOnRowsChanged(
			@NonNull ViewHeaderProperties currentHeaderProperties,
			@NonNull IView view,
			@NonNull Set<DocumentId> changedRowIds,
			final boolean watchedByFrontend);
}
