package de.metas.handlingunits;

import com.google.common.collect.ImmutableSet;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Which LU PIs can hold the PIs of {@link #piItemIds} when packing {@link #productId}.
 *
 * @see IHandlingUnitsDAO#retrieveParentLUPIsForProducts(java.util.Collection, de.metas.bpartner.BPartnerId)
 */
@Value(staticConstructor = "of")
public class ParentLUPIsQuery
{
	@NonNull ImmutableSet<HuPackingInstructionsItemId> piItemIds;

	/**
	 * The product to be packed; {@code null} means product-less packing, which can only use items without product.
	 */
	@Nullable ProductId productId;
}
