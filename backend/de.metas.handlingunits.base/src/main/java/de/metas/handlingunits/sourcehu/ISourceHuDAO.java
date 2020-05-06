package de.metas.handlingunits.sourcehu;

import java.util.List;
import java.util.Set;

import org.adempiere.util.proxy.Cached;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Source_HU;
import de.metas.handlingunits.sourcehu.SourceHUsService.MatchingSourceHusQuery;
import de.metas.util.ISingletonService;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * Declares data retrieval methods whose implementations are annotated as {@link Cached}
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ISourceHuDAO extends ISingletonService
{
	/**
	 * Return {@code true} if the given HU is referenced by an active {@link I_M_Source_HU}.<br>
	 * Note that we use the ID for performance reasons.
	 *
	 * @param huId
	 * @return
	 */
	boolean isSourceHu(HuId huId);

	I_M_Source_HU retrieveSourceHuMarkerOrNull(I_M_HU hu);

	/**
	 * Returns those fine picking source HUs whose location and product match any the given query.
	 *
	 * @param query
	 * @return
	 */
	List<I_M_HU> retrieveActiveSourceHus(MatchingSourceHusQuery query);

	Set<HuId> retrieveActiveSourceHUIds(MatchingSourceHusQuery query);

	List<I_M_Source_HU> retrieveActiveSourceHuMarkers(MatchingSourceHusQuery query);

}
