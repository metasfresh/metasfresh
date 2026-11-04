package de.metas.handlingunits.rest_api.createfromlabel;

import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.math.BigDecimal;

/**
 * What a label's weight means for a product (a design default): shared by the proposal and the create command.
 * <ul>
 * <li>product stocked in kg: the label weight is the quantity</li>
 * <li>catch-weight product: quantity 1, the label weight becomes the CU's net weight</li>
 * <li>any other product: the weight is not used</li>
 * <li>no weight on the label: quantity 1</li>
 * </ul>
 */
@RequiredArgsConstructor
public class LabelQtyAndWeightService
{
	@NonNull private final IProductBL productBL;
	@NonNull private final IUOMDAO uomDAO;

	@NonNull
	LabelQtyAndWeight compute(@NonNull final ProductId productId, @Nullable final BigDecimal labelWeightKg)
	{
		final UomId stockUomId = productBL.getStockUOMId(productId);
		final X12DE355 stockUom = uomDAO.getX12DE355ById(stockUomId);

		final LabelQtyAndWeight.LabelQtyAndWeightBuilder result = LabelQtyAndWeight.builder().stockUomId(stockUomId).stockUom(stockUom).qty(BigDecimal.ONE);
		if (labelWeightKg != null)
		{
			if (X12DE355.KILOGRAM.equals(stockUom))
			{
				result.qty(labelWeightKg);
			}
			else if (productBL.getCatchUOMId(productId).isPresent())
			{
				result.weightKgForCU(labelWeightKg);
			}
			else
			{
				result.isWeightIgnored(true);
			}
		}
		return result.build();
	}
}
