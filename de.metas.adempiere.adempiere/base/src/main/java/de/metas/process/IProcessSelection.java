package de.metas.process;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.apache.poi.ss.formula.functions.T;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * Let your process implement this interface if you want to:
 * <ul>
 * <li>control when this process will be displayed in related processes toolbar (e.g. Gear in Swing)
 * <li>override displayed process caption
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProcessSelection
{
	
	default int createSelection(final IQueryBuilder<T> queryBuilder, final int adPInstanceId)
	{
		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");
		return queryBuilder
				.create()
				.setApplyAccessFilterRW(false)
				.createSelection(adPInstanceId);
	}
}
