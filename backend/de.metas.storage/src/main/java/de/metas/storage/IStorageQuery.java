package de.metas.storage;

import de.metas.bpartner.BPartnerId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Attribute;

import java.util.Collection;

/**
 * Used to retrieve {@link IStorageRecord}s. Use {@link IStorageEngine#newStorageQuery()} to get an instance.
 */
public interface IStorageQuery
{
	IStorageQuery addProductId(ProductId productId);

	/**
	 * @param bpartnerId may also be <code>null</code> to indicate that storages without any bpartner assignment shall be matched.
	 */
	IStorageQuery addBPartnerId(BPartnerId bpartnerId);

	IStorageQuery addWarehouseId(WarehouseId warehouseId);

	IStorageQuery addWarehouseIds(Collection<WarehouseId> warehouseIds);

	/**
	 * Add another attribute filter, <b>if</b> the given <code>attribute</code> is relevant according to the respective storage engine implementation.
	 */
	IStorageQuery addAttribute(I_M_Attribute attribute, String attributeValueType, Object attributeValue);

	/**
	 * Add all attributes (that are allowed to be used in storage querying) from given attribute set.
	 * <p>
	 * NOTE: depends on implementation, it could be that not all attributes will be added but only the considered ones.
	 */
	IStorageQuery addAttributes(IAttributeSet attributeSet);

	/**
	 * @param storageRecord
	 * @return true if given record was matched by this query
	 */
	boolean matches(IStorageRecord storageRecord);

	/**
	 * Set if we shall exclude the after picking locators.
	 * By default, after picking locators are excluded.
	 */
	IStorageQuery setExcludeAfterPickingLocator(boolean excludeAfterPickingLocator);

	/**
	 * Set if we shall only include storages that are not reserved, or are reserved to the particular given orderLine.
	 */
	IStorageQuery setExcludeReservedToOtherThan(OrderLineId orderLineId);

	IStorageQuery setExcludeReserved();

	IStorageQuery setOnlyActiveHUs(boolean onlyActiveHUs);
}
