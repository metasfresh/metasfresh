package de.metas.bpartner.quick_input.service;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.BPartnerLocationType;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.document.references.zoom_into.RecordWindowFinder;
import de.metas.location.LocationId;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.compiere.model.I_C_BPartner_Location_QuickInput;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BPartnerLocationQuickInputService
{
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;

	public Optional<AdWindowId> getNewBPartnerLocationWindowId()
	{
		return RecordWindowFinder.newInstance(I_C_BPartner_Location_QuickInput.Table_Name, customizedWindowInfoMapRepository)
				.ignoreExcludeFromZoomTargetsFlag()
				.findAdWindowId();
	}

	@Nullable
	public BPartnerLocationId createBPartnerLocationFromTemplate(@NonNull final I_C_BPartner_Location_QuickInput template,
																 @NonNull final BPartnerId bpartnerId)
	{
		final BPartnerLocation locationFromBPartnerLocationTemplate = getLocationFromBPartnerLocationTemplate(template);

		if (locationFromBPartnerLocationTemplate == null)
		{
			return null;
		}
		final BPartnerComposite bpartner = bpartnerCompositeRepository.getById(bpartnerId);
		bpartner.getLocations().add(locationFromBPartnerLocationTemplate);
		bpartnerCompositeRepository.save(bpartner, true);
		return Objects.requireNonNull(locationFromBPartnerLocationTemplate.getId());
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
				.bpartnerName(template.getBPartnerName())
				.ephemeral(template.isOneTime())
				.existingLocationId(uniqueLocationIdOfBPartnerTemplate)
				.name(template.getName())
				.build();
	}
}
