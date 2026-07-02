package de.metas.handlingunits.picking.job.service.shelflife;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.picking.job.service.external.bpartner.PickingJobBPartnerService;
import de.metas.handlingunits.picking.job.service.external.product.PickingJobProductService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.LocalDate;

/**
 * Checks whether the remaining shelf life of a HU would be undercut at the time of delivery.
 *
 * <p>Undercut condition:
 * {@code bestBeforeDate != null && guaranteedDays > 0 && bestBeforeDate.isBefore(deliveryDate.plusDays(guaranteedDays))}
 *
 * <p>Guaranteed days are resolved as follows:
 * {@code C_BPartner_Product.ShelfLifeMinDays} for the (product, bpartner) pair when {@code > 0},
 * otherwise {@code M_Product.GuaranteeDaysMin} (with fallback to product category).
 */
@Service
@RequiredArgsConstructor
public class PickingShelfLifeCheck
{
	@NonNull private final PickingJobProductService productService;
	@NonNull private final PickingJobBPartnerService bpartnerService;

	/**
	 * Returns {@code true} if the given best-before date would undercut the guaranteed shelf life
	 * at the time of delivery.
	 *
	 * @param productId      the product being picked
	 * @param bpartnerId     the customer business partner
	 * @param orgId          the org of the shipment schedule (used to resolve the org-specific
	 *                       {@code C_BPartner_Product} row, falling back to {@code OrgId.ANY})
	 * @param bestBeforeDate the best-before / MHD date of the HU (may be {@code null} — treated as "no undercut")
	 * @param deliveryDate   the planned delivery date
	 */
	public boolean isRemainingShelfLifeTooShort(
			@NonNull final ProductId productId,
			@NonNull final BPartnerId bpartnerId,
			@NonNull final OrgId orgId,
			@Nullable final LocalDate bestBeforeDate,
			@NonNull final LocalDate deliveryDate)
	{
		if (bestBeforeDate == null)
		{
			return false;
		}

		final int guaranteedDays = resolveGuaranteedDays(productId, bpartnerId, orgId);
		if (guaranteedDays <= 0)
		{
			return false;
		}

		return bestBeforeDate.isBefore(deliveryDate.plusDays(guaranteedDays));
	}

	/**
	 * Resolves the effective guaranteed days for the given (product, bpartner) pair.
	 *
	 * <p>Resolution order:
	 * <ol>
	 *   <li>{@code C_BPartner_Product.ShelfLifeMinDays} when {@code > 0}</li>
	 *   <li>{@code M_Product.GuaranteeDaysMin} (with fallback to product category)</li>
	 * </ol>
	 */
	private int resolveGuaranteedDays(@NonNull final ProductId productId, @NonNull final BPartnerId bpartnerId, @NonNull final OrgId orgId)
	{
		final int bpShelfLifeMinDays = bpartnerService.getBPartnerProductShelfLifeMinDays(bpartnerId, productId, orgId);
		if (bpShelfLifeMinDays > 0)
		{
			return bpShelfLifeMinDays;
		}

		return productService.getGuaranteeDaysMin(productId);
	}
}
