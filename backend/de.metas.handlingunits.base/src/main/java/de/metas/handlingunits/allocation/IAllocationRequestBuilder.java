package de.metas.handlingunits.allocation;

import de.metas.handlingunits.ClearanceStatusInfo;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.storage.EmptyHUListener;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/**
 * {@link IAllocationRequest} builder. Use it to create modified instances of your immutable {@link IAllocationRequest}.
 *
 * @author tsa
 * @see AllocationUtils#builder()
 * @see AllocationUtils#derive(IAllocationRequest)
 */
public interface IAllocationRequestBuilder
{
	/**
	 * Create the new allocation request
	 *
	 * @return newly create allocation request
	 */
	IAllocationRequest create();

	/**
	 * Sets base {@link IAllocationRequest}.
	 * <p>
	 * When building the new allocation request, if there are some values which were not set then those values are fetched from this allocation.
	 */
	IAllocationRequestBuilder setBaseAllocationRequest(final IAllocationRequest baseAllocationRequest);

	IAllocationRequestBuilder setHUContext(IHUContext huContext);

	IAllocationRequestBuilder setProduct(final I_M_Product product);

	IAllocationRequestBuilder setProduct(final ProductId productId);

	IAllocationRequestBuilder setQuantity(Quantity quantity);

	default IAllocationRequestBuilder setQuantity(final BigDecimal qty, final I_C_UOM uom)
	{
		setQuantity(Quantity.of(qty, uom));
		return this;
	}

	IAllocationRequestBuilder setDate(final ZonedDateTime date);

	IAllocationRequestBuilder setDateAsToday();

	/**
	 * @param forceQtyAllocation if null, the actual value will be fetched from base allocation request (if any)
	 * @return this
	 */
	IAllocationRequestBuilder setForceQtyAllocation(final Boolean forceQtyAllocation);

	/**
	 * Sets referenced model to be set to {@link IAllocationRequest} which will be created.
	 *
	 * @param referenceModel model, {@link ITableRecordReference} or null
	 * @return this
	 */
	IAllocationRequestBuilder setFromReferencedModel(@Nullable Object referenceModel);

	/**
	 * Sets referenced model to be set to {@link IAllocationRequest} which will be created.
	 *
	 * @param fromReferencedTableRecord {@link ITableRecordReference} or null
	 * @return this
	 */
	IAllocationRequestBuilder setFromReferencedTableRecord(@Nullable TableRecordReference fromReferencedTableRecord);

	IAllocationRequestBuilder addEmptyHUListener(EmptyHUListener emptyHUListener);

	IAllocationRequestBuilder setClearanceStatusInfo(@Nullable ClearanceStatusInfo clearanceStatusInfo);

	@Nullable
	ClearanceStatusInfo getClearanceStatusInfo();
}
