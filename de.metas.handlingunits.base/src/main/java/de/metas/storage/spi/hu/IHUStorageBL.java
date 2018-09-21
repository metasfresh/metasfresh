package de.metas.storage.spi.hu;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;
import java.util.Set;

import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageQuery;
import de.metas.util.ISingletonService;

/**
 * Misc helpers for {@link IStorageEngine} and handling units integration module.
 *
 * @author tsa
 *
 */
public interface IHUStorageBL extends ISingletonService
{
	/**
	 * Gets available M_Attribute_ID to be used in {@link IStorageQuery}s.
	 *
	 * Those which are not on this list shall be ignored when querying because for sure there won't be any match for them.
	 *
	 * @param ctx
	 * @return M_Attribute_IDs
	 */
	Set<Integer> getAvailableAttributeIds(Properties ctx);

}
