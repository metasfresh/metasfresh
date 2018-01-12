package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

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

/**
 * The result of {@link WebuiHUTransformCommand#execute()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 */
@Value
@Builder
public class WebuiHUTransformCommandResult
{
	@Singular("huIdToAddToView")
	private final ImmutableSet<Integer> huIdsToAddToView;

	@Singular("huIdToRemoveFromView")
	private final ImmutableSet<Integer> huIdsToRemoveFromView;

	@Singular("huIdChanged")
	private final ImmutableSet<Integer> huIdsChanged;
	
	@Singular("huIdCreated")
	private final ImmutableSet<Integer> huIdsCreated;

	private final boolean fullViewInvalidation;
}
