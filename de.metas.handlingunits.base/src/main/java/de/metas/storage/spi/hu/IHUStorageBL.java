package de.metas.storage.spi.hu;

import java.util.Set;

import org.adempiere.mm.attributes.AttributeId;

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
	 */
	Set<AttributeId> getAvailableAttributeIds();

}
