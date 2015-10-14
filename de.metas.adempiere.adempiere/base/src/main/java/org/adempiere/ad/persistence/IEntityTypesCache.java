package org.adempiere.ad.persistence;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.List;

/**
 * Implementations of this interface are responsible for:
 * <ul>
 * <li>loading all entity types without using ADempiere's persistence engine because it will be used in early stages of persistence engine initialization
 * <li>provide info regarding entity types (e.g. {@link #getModelPackage(String)})
 * </ul>
 * 
 * @author tsa
 * 
 */
interface IEntityTypesCache
{
	/** @return list of all known entity types */
	List<String> getEntityTypeNames();

	/**
	 * @param entityType
	 * @return model class package of given entity type.
	 *         This method could also return null if:
	 *         <ul>
	 *         <li>entityType is not known
	 *         <li>there is no model package set for given entity type
	 *         </ul>
	 */
	String getModelPackage(String entityType);
}
