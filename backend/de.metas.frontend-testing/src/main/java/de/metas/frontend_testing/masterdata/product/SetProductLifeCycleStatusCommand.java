package de.metas.frontend_testing.masterdata.product;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.product.BBSStatus;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_Product;

/**
 * Updates {@code M_Product.ProductLifeCycleStatus} (BBS-Status) on an already-created product.
 * <p>
 * Deliberately separate from {@link CreateProductCommand}: the realistic life-cycle-block scenario is
 * TEMPORAL. A product is created and sold while still sellable, and only later flipped to a blocking
 * status. Setting a blocking status (e.g. {@code G} = Gesperrt) at creation time would make the
 * product's own order/shipment creation fail the life-cycle guards, so the flip must happen AFTER
 * those documents exist — typically issued as its own {@code productLifeCycleStatuses} masterdata
 * request once the order/picking-job setup is in place.
 * <p>
 * Loads and saves via {@link IProductDAO} rather than raw {@code InterfaceWrapperHelper}, per this module's
 * "create/query through DAOs" rule.
 */
@Builder
public class SetProductLifeCycleStatusCommand
{
	@NonNull private final MasterdataContext context;
	@NonNull private final Identifier identifier;
	@NonNull private final String statusCode;

	public void execute()
	{
		// Validate the code against the BBS-Status reference list (fails loudly on a typo).
		final BBSStatus status = BBSStatus.ofCode(statusCode);

		final IProductDAO productsRepo = Services.get(IProductDAO.class);

		final ProductId productId = context.getId(identifier, ProductId.class);
		final I_M_Product product = productsRepo.getById(productId);
		product.setProductLifeCycleStatus(status.getCode());
		productsRepo.save(product);
	}
}
