package de.metas.storage.spi.hu;

import java.util.Set;

import org.adempiere.mm.attributes.AttributeId;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.storage.IStorageEngine;
import de.metas.storage.IStorageQuery;
import de.metas.uom.IUOMDAO;
import de.metas.util.ISingletonService;
import de.metas.util.Services;
import lombok.NonNull;

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

	static I_C_UOM extractUOM(@NonNull final I_M_HU_Storage storage)
	{
		return Services.get(IUOMDAO.class).getById(storage.getC_UOM_ID());
	}
}
