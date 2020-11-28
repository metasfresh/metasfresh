package de.metas.ui.web.view.descriptor;

import lombok.Builder;
import lombok.Builder.Default;
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

@Builder
@Value
public final class IncludedViewLayout
{
	/** Default: has included view support, no auto-open, blur main view when the included view is opened */
	public static IncludedViewLayout DEFAULT = builder().build();

	/** Automatically open the included view when a row from main view is selected */
	private final boolean openOnSelect;

	/** Blur main view when included view is open */
	@Default
	private final boolean blurWhenOpen = true;
}
