package de.metas.handlingunits;

import java.time.ZonedDateTime;
import java.util.Date;

import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.util.ISingletonService;

public interface IHUCapacityBL extends ISingletonService
{
	/**
	 *
	 * @param itemDefProduct
	 * @param productId the product to be returned in the resulting capacity definition; optional, unless the given <code>itemDefProduct</code> has <code>AllowAnyProduct='Y'</code>;
	 *            if <code>null</code> , then the given <code>itemDefProduct</code>'s M_Product is used.
	 * @param uom
	 * @return
	 * @throws HUException if <code>productId!=null</code> and the product's ID is different from <code>itemDefProduct</code>'s <code>M_Product_ID</code>.
	 *             Also, if <code>productId==null</code> and <code>itemDefProduct</code> does not reference any product either.
	 */
	Capacity getCapacity(I_M_HU_PI_Item_Product itemDefProduct, ProductId productId, I_C_UOM uom);

	@Deprecated
	Capacity getCapacity(I_M_HU_PI_Item_Product itemDefProduct, I_M_Product product, I_C_UOM uom);

	/**
	 * Retrieve the and evaluate the {@link I_M_HU_PI_Item_Product} for the given <code>huItem</code>, <code>product</code> and <code>date</code>. If there is no such record, if returns a capacity definition with a
	 * zero-capacity. If a record is found, return the result of {@link #getCapacity(I_M_HU_PI_Item_Product, I_M_Product, I_C_UOM)}.
	 *
	 * @param huItem
	 * @param productId
	 * @param uom
	 * @param date
	 * @return
	 * @see IHUPIItemProductDAO#retrievePIMaterialItemProduct(I_M_HU_Item, I_M_Product, Date) to learn which I_M_HU_PI_Item_Product's capacitiy is returned if there is more than one.
	 */
	Capacity getCapacity(I_M_HU_Item huItem, ProductId productId, I_C_UOM uom, ZonedDateTime date);

	@Deprecated
	Capacity getCapacity(I_M_HU_Item huItem, I_M_Product product, I_C_UOM uom, final ZonedDateTime date);

	boolean isInfiniteCapacity(I_M_HU_PI_Item_Product itemDefProduct);

	boolean isValidItemProduct(final I_M_HU_PI_Item_Product itemDefProduct);
}
