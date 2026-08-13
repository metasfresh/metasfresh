package de.metas.frontend_testing.masterdata.hu;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.compiere.model.I_M_PriceList_Version;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/**
 * Persistence for the {@code M_ProductPrice.M_HU_PI_Item_Product_ID} link that the masterdata API sets.
 * <p>
 * Separate from {@link CreatePackingInstructionsCommand} because persistence primitives
 * ({@code InterfaceWrapperHelper.saveRecord}) belong in a {@code *Repository}/{@code *DAO} rather than in a
 * command — see {@code docs/coding-rules/service-injection.md} §4. An ArchUnit rule enforces this.
 * <p>
 * The HU-flavoured {@code de.metas.handlingunits.model.I_M_ProductPrice} is used deliberately: only it exposes
 * {@code setM_HU_PI_Item_Product}, because {@code de.metas.business} cannot depend on the handling-units module.
 */
public class ProductPricePackingInstructionRepository
{
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	/**
	 * Points every product price of {@code productId} on the given price list version at {@code piItemProduct}.
	 *
	 * @return how many product prices were updated — zero means the product has no price on that price list
	 *         version, which the caller is expected to treat as a masterdata-request error.
	 */
	public int pointProductPricesAt(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ProductId productId,
			@NonNull final I_M_HU_PI_Item_Product piItemProduct)
	{
		final I_M_PriceList_Version priceListVersion = priceListDAO.getPriceListVersionById(priceListVersionId);

		final List<I_M_ProductPrice> productPrices = ProductPrices.newQuery(priceListVersion)
				.setProductId(productId)
				.list(I_M_ProductPrice.class);

		for (final I_M_ProductPrice productPrice : productPrices)
		{
			productPrice.setM_HU_PI_Item_Product(piItemProduct);
			saveRecord(productPrice);
		}

		return productPrices.size();
	}
}
