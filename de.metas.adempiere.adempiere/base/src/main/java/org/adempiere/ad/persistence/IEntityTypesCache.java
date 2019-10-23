package org.adempiere.ad.persistence;

import java.util.Set;

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
	Set<String> getEntityTypeNames();

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

	/** @return true if given entity type exists and it's active */
	boolean isActive(String entityType);

	/** @return true if an entity which has given entity type shall be displayed in UI */ 
	boolean isDisplayedInUI(String entityType);

}
