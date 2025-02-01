package de.metas.ui.web.document.filter.provider;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * {@link DocumentFilterDescriptorsProvider}s factory.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public final class DocumentFilterDescriptorsProvidersService
{
	private static final Logger logger = LogManager.getLogger(DocumentFilterDescriptorsProvidersService.class);

	private final ImmutableList<DocumentFilterDescriptorsProviderFactory> providerFactories;

	public DocumentFilterDescriptorsProvidersService(final List<DocumentFilterDescriptorsProviderFactory> providerFactories)
	{
		this.providerFactories = ImmutableList.copyOf(providerFactories);
		logger.info("Provider factories: {}", providerFactories);
	}

	public DocumentFilterDescriptorsProvider createFiltersProvider(@NonNull final CreateFiltersProviderContext context)
	{
		final ImmutableList<DocumentFilterDescriptorsProvider> providers = providerFactories
				.stream()
				.filter(DocumentFilterDescriptorsProviderFactory::isActive)
				.map(provider -> provider.createFiltersProvider(context))
				.filter(NullDocumentFilterDescriptorsProvider::isNotNull)
				.collect(ImmutableList.toImmutableList());

		return CompositeDocumentFilterDescriptorsProvider.compose(providers);
	}
}
