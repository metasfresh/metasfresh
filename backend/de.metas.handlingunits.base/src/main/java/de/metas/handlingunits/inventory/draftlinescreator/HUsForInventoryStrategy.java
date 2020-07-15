package de.metas.handlingunits.inventory.draftlinescreator;

import java.util.stream.Stream;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2018 metas GmbH
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
 * See {@link HUsForInventoryStrategies} for creating different instances.
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface HUsForInventoryStrategy
{
	Stream<HuForInventoryLine> streamHus();

	/**
	 * The number of different locators to look at when creating lines.
	 * A value less that or equal to zero means "no restriction"
	 */
	default int getMaxLocatorsAllowed()
	{
		return 0;
	}
}
