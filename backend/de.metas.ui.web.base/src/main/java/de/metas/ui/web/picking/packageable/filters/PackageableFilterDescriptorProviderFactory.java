/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.picking.packageable.filters;

import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;

@Component
public class PackageableFilterDescriptorProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	private final PackageableFilterDescriptorProvider provider;

	public PackageableFilterDescriptorProviderFactory(
			@NonNull final ProductBarcodeFilterServicesFacade services)
	{
		provider = new PackageableFilterDescriptorProvider(services);
	}

	@Nullable
	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final CreateFiltersProviderContext context,
			@NonNull final Collection<DocumentFieldDescriptor> fields)
	{
		if (I_M_Packageable_V.Table_Name.equals(context.getTableName()))
		{
			return provider;
		}
		else
		{
			return null;
		}
	}
}
