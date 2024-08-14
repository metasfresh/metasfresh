package de.metas.handlingunits.storage;

import java.util.List;
import java.util.stream.Stream;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;

/**
 * Handling Unit Instance Storage
 *
 * @author tsa
 *
 */
public interface IHUStorage extends IGenericHUStorage
{
	I_M_HU getM_HU();

	@NonNull IHUProductStorage getSingleHUProductStorage();

	List<IHUProductStorage> getProductStorages();

	default Stream<IHUProductStorage> streamProductStorages()
	{
		return getProductStorages().stream();
	}

	/**
	 * Gets product storage for given product.
	 *
	 * @param productId
	 * @return product storage; never return null;
	 * @throws AdempiereException in case product storage was not found
	 */
	IHUProductStorage getProductStorage(ProductId productId);

	/**
	 * Gets product storage for given product.
	 *
	 * @param productId
	 * @return product storage; if no storage was found, null is returned
	 */
	IHUProductStorage getProductStorageOrNull(ProductId productId);

	/**
	 * @return full qty of the {@link IHUProductStorage}s of this {@link IHUStorage}, in the given uom
	 */
	Quantity getQtyForProductStorages(I_C_UOM uom);

	/**
	 * @return full qty of the {@link IHUProductStorage}s of this {@link IHUStorage}, in the storage uom
	 */
	Quantity getQtyForProductStorages();

	/**
	 * Propagate ALL storage products & quantities - UOM-based - to parent (incremental)
	 */
	void rollup();

	/**
	 * Revert propagation of ALL storage products & quantities - UOM-based - to parent (incremental)
	 */
	void rollupRevert();

	/**
	 * Checks if this is a "Single Product Storage".
	 *
	 * A storage is considered to be a "Single Product Storage" when there is maximum one type of product in that storage.<br/>
	 * An empty storage is considered to be a "Single Product Storage".<br/>
	 * If a storage has more then one M_Product_ID, that's not a single product storage.
	 *
	 * @return true if this storage is "Single Product Storage"
	 */
	boolean isSingleProductStorage();

	/**
	 * Gets the {@link ProductId} stored in this HU Storage.
	 * 
	 * @return
	 *         <ul>
	 *         <li>single product stored in this storage
	 *         <li><code>null</code> if the storage is empty or there are more then one products stored
	 *         </ul>
	 * @see #isSingleProductStorage()
	 */
	ProductId getSingleProductIdOrNull();

	@Deprecated
	I_M_Product getSingleProductOrNull();

	/**
	 * <b>NOTE:</b> HU already contains it's children HUStorages due to storage lines already being created by rollupIncremental.<br>
	 * <br>
	 * <b>Returns</b> <code>null</code> if storage UOMType incompatibility was encountered (i.e one storage does not have it's UOM specified or has it different from the others)<br>
	 * <br>
	 * <b>Returns</b> <code>C_UOM</code> accepted among all the HU storages' UOMTypes<br>
	 *
	 * @return <code>C_UOM</code> or null
	 */
	I_C_UOM getC_UOMOrNull();

	boolean isSingleProductWithQtyEqualsTo(@NonNull ProductId productId, @NonNull Quantity qty);

	boolean isSingleProductStorageMatching(@NonNull ProductId productId);
}
