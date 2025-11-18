package de.metas.handlingunits.picking.job.service.external.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.ShipmentAllocationBestBeforePolicy;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.location.DocumentLocation;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.document.location.RenderedAddressProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PickingJobBPartnerService
{
	@NonNull private final BPartnerBL bpartnerBL;
	@NonNull private final IDocumentLocationBL documentLocationBL;

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

	public RenderedAddressProvider newRenderedAddressProvider()
	{
		return documentLocationBL.newRenderedAddressProvider();
	}
}
