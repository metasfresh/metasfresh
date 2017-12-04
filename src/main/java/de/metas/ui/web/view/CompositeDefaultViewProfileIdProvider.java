package de.metas.ui.web.view;

import java.util.List;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.WindowId;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@ToString
public final class CompositeDefaultViewProfileIdProvider implements DefaultViewProfileIdProvider
{
	public static final CompositeDefaultViewProfileIdProvider of(final List<DefaultViewProfileIdProvider> providers)
	{
		return new CompositeDefaultViewProfileIdProvider(providers);
	}

	private final PlainDefaultViewProfileIdProvider overrides;
	private final ImmutableList<DefaultViewProfileIdProvider> providers;
	private final PlainDefaultViewProfileIdProvider fallback;

	private CompositeDefaultViewProfileIdProvider(final List<DefaultViewProfileIdProvider> providers)
	{
		overrides = new PlainDefaultViewProfileIdProvider();
		fallback = new PlainDefaultViewProfileIdProvider();
		this.providers = ImmutableList.<DefaultViewProfileIdProvider> builder()
				.add(overrides)
				.addAll(providers)
				.add(fallback)
				.build();
	}

	@Override
	public ViewProfileId getDefaultProfileIdByWindowId(final WindowId windowId)
	{
		return providers.stream()
				.map(provider -> provider.getDefaultProfileIdByWindowId(windowId))
				.filter(Predicates.notNull())
				.findFirst()
				.orElse(ViewProfileId.NULL);
	}

	public void setDefaultProfileIdOverride(WindowId windowId, ViewProfileId profileId)
	{
		overrides.setDefaultProfileId(windowId, profileId);
	}

	public void setDefaultProfileIdFallback(WindowId windowId, ViewProfileId profileId)
	{
		fallback.setDefaultProfileId(windowId, profileId);
	}
	
	public void setDefaultProfileIdFallbackIfAbsent(WindowId windowId, ViewProfileId profileId)
	{
		fallback.setDefaultProfileIdIfAbsent(windowId, profileId);
	}

}
