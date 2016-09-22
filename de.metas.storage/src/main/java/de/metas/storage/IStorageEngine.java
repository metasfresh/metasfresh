package de.metas.storage;

/*
 * #%L
 * de.metas.storage
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Set;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.model.IContextAware;
import org.compiere.model.I_M_AttributeSetInstance;

/**
 * Use {@link IStorageEngineService#getStorageEngine()} to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IStorageEngine
{
	List<IStorageRecord> retrieveStorageRecords(final IContextAware context, IStorageQuery storageQuery);

	Set<IStorageRecord> retrieveStorageRecords(IContextAware context, Set<IStorageQuery> storageQueries);

	IStorageQuery newStorageQuery();

	/**
	 * Creates the {@link IAttributeSet} for given ASI.
	 *
	 * TODO: actually this is not the right place for this method but because we lack time and all IAttributeSet implementations are in handlingunits module, I decided to but this here to have a
	 * quick&dirty way of getting it.
	 *
	 * @param asi
	 * @return attribute set
	 */
	IAttributeSet getAttributeSet(I_M_AttributeSetInstance asi);

}
