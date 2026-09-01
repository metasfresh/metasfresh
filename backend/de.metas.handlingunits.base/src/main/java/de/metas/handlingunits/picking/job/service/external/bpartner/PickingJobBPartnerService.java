package de.metas.handlingunits.picking.job.service.external.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import de.metas.handlingunits.grai.GRAIRequired;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobBPartnerService
{
	@NonNull private final BPartnerBL bpartnerBL;
	@NonNull private final IDocumentLocationBL documentLocationBL;
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	public String getBPartnerName(@Nullable final BPartnerId bpartnerId)
	{
		return bpartnerBL.getBPartnerName(bpartnerId);
	}

	public Map<BPartnerId, String> getBPartnerNames(@NonNull final Set<BPartnerId> bpartnerIds)
	{
		return bpartnerBL.getBPartnerNames(bpartnerIds);
	}

	public ShipmentAllocationBestBeforePolicy getBestBeforePolicy(@NonNull final BPartnerId bpartnerId)
	{
		return bpartnerBL.getBestBeforePolicy(bpartnerId);
	}

	public Set<DocumentLocation> getDocumentLocations(@NonNull final Set<BPartnerLocationId> bpartnerLocationIds)
	{
		return documentLocationBL.getDocumentLocations(bpartnerLocationIds);
	}

	public I_C_BPartner_Location getBPartnerLocationByIdEvenInactive(final @NonNull BPartnerLocationId id)
	{
		return bpartnerBL.getBPartnerLocationByIdEvenInactive(id);
	}

	public List<I_C_BPartner_Location> getBPartnerLocationsByIds(final Set<BPartnerLocationId> ids)
	{
		return bpartnerBL.getBPartnerLocationsByIds(ids);
	}

	public RenderedAddressProvider newRenderedAddressProvider()
	{
		return documentLocationBL.newRenderedAddressProvider();
	}

	@NonNull
	public GRAIRequired getGRAIRequired(@NonNull final BPartnerId customerId)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getById(customerId);
		return GRAIRequired.optionalOfNullableCode(bpartner.getGRAIRequired()).orElse(GRAIRequired.No);
	}

	/**
	 * @return C_BPartner_Product.ShelfLifeMinDays for the given (bpartner, product) pair,
	 *         or 0 if no association exists. The org-specific row for {@code orgId} is preferred,
	 *         with {@code OrgId.ANY} as fallback (handled by the DAO query).
	 */
	public int getBPartnerProductShelfLifeMinDays(@NonNull final BPartnerId bpartnerId, @NonNull final ProductId productId, @NonNull final OrgId orgId)
	{
		final I_C_BPartner_Product bpProduct = bpartnerProductDAO.retrieveBPartnerProductAssociation(
				Env.getCtx(),
				bpartnerId,
				productId,
				orgId);
		if (bpProduct == null)
		{
			return 0;
		}
		return bpProduct.getShelfLifeMinDays();
	}
}
