package de.metas.ui.web.document.filter.provider;

import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;

import javax.annotation.Nullable;
import java.util.Collection;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public interface DocumentFilterDescriptorsProviderFactory
{
	@Nullable
	DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable AdTabId adTabId,
			@Nullable String tableName,
			@NonNull Collection<DocumentFieldDescriptor> fields);

	default boolean isActive()
	{
		return true;
	}
}
