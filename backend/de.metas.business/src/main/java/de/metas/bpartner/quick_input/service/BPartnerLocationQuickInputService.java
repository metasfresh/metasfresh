package de.metas.bpartner.quick_input.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.location.LocationId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_C_BPartner_Location_QuickInput;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BPartnerLocationQuickInputService
{
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;

	public Optional<AdWindowId> getNewBPartnerLocationWindowId()
	{
		return RecordWindowFinder.newInstance(I_C_BPartner_Location_QuickInput.Table_Name, customizedWindowInfoMapRepository)
				.ignoreExcludeFromZoomTargetsFlag()
				.findAdWindowId();
	}

	@NonNull
	public BPartnerLocationId createBPartnerLocationFromTemplate(@NonNull final I_C_BPartner_Location_QuickInput template, @NonNull final BPartnerId bpartnerId)
	{
		final BPartnerLocation locationFromBPartnerLocationTemplate = getLocationFromBPartnerLocationTemplate(template);
		//TODO implement me
		throw new UnsupportedOperationException("Not yet implemented");
	}

	@Nullable
	private BPartnerLocation getLocationFromBPartnerLocationTemplate(final I_C_BPartner_Location_QuickInput template)
	{
		final LocationId uniqueLocationIdOfBPartnerTemplate = LocationId.ofRepoIdOrNull(template.getC_Location_ID());

		if (uniqueLocationIdOfBPartnerTemplate == null)
		{
			return null;
		}

		return BPartnerLocation.builder()
				.locationType(BPartnerLocationType.builder()
						.billTo(template.isBillTo())
						.billToDefault(template.isBillToDefault())
						.shipTo(template.isShipTo())
						.shipToDefault(template.isShipToDefault())
						.build())
				.name(".")
				.ephemeral(template.isOneTime())
				.existingLocationId(uniqueLocationIdOfBPartnerTemplate)
				.build();
	}
}
