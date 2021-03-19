package de.metas.ui.web.view;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
public class ViewHeaderPropertiesIncrementalResult
{
	public static ViewHeaderPropertiesIncrementalResult computed(@NonNull final ViewHeaderProperties computedHeaderProperties)
	{
		return new ViewHeaderPropertiesIncrementalResult(
				Resolution.COMPUTED,
				computedHeaderProperties);
	}

	public static ViewHeaderPropertiesIncrementalResult computedAsEmpty()
	{
		return COMPUTED_AS_EMPTY;
	}

	public static ViewHeaderPropertiesIncrementalResult fullRecomputeRequired()
	{
		return FULL_RECOMPUTE_REQUIRED;
	}

	private static final ViewHeaderPropertiesIncrementalResult COMPUTED_AS_EMPTY = new ViewHeaderPropertiesIncrementalResult(Resolution.COMPUTED, ViewHeaderProperties.EMPTY);
	private static final ViewHeaderPropertiesIncrementalResult FULL_RECOMPUTE_REQUIRED = new ViewHeaderPropertiesIncrementalResult(Resolution.FULL_RECOMPUTE_REQUIRED, null);

	private enum Resolution
	{
		COMPUTED, FULL_RECOMPUTE_REQUIRED,
	}

	private final Resolution resolution;
	private final ViewHeaderProperties computedHeaderProperties;

	public ViewHeaderPropertiesIncrementalResult(
			@NonNull final Resolution resolution,
			final ViewHeaderProperties computedHeaderProperties)
	{
		this.resolution = resolution;
		this.computedHeaderProperties = computedHeaderProperties;
	}

	public boolean isComputed()
	{
		return resolution == Resolution.COMPUTED;
	}

	public ViewHeaderProperties getComputeHeaderProperties()
	{
		return computedHeaderProperties;
	}

	public boolean isFullRecomputeRequired()
	{
		return resolution == Resolution.FULL_RECOMPUTE_REQUIRED;
	}
}
