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
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner_Location_QuickInput;
import org.compiere.model.I_C_Order;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BPartnerLocationQuickInputService
{
	final private static IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final CustomizedWindowInfoMapRepository customizedWindowInfoMapRepository;
	private final BPartnerCompositeRepository bpartnerCompositeRepository;

	public Optional<AdWindowId> getNewBPartnerLocationWindowId()
	{
		return RecordWindowFinder.newInstance(I_C_BPartner_Location_QuickInput.Table_Name, customizedWindowInfoMapRepository)
				.ignoreExcludeFromZoomTargetsFlag()
				.findAdWindowId();
	}

	@Nullable
	public BPartnerLocationId createBPartnerLocationFromTemplateOrNull(@NonNull final I_C_BPartner_Location_QuickInput template,
																	   @NonNull final TableRecordReference tableRecordReference,
																	   @NonNull final String triggeringField)
	{
		final BPartnerLocation locationFromBPartnerLocationTemplate = getLocationFromBPartnerLocationTemplate(template);

		if (locationFromBPartnerLocationTemplate == null)
		{
			return null;
		}
		final BPartnerId bpartnerId = getBpartnerIdFromRefAndTriggeringField(tableRecordReference, triggeringField);
		if (bpartnerId == null)
		{
			return null;
		}
		final BPartnerComposite bpartner = bpartnerCompositeRepository.getById(bpartnerId);
		bpartner.getLocations().add(locationFromBPartnerLocationTemplate);
		bpartnerCompositeRepository.save(bpartner, true);
		return Objects.requireNonNull(locationFromBPartnerLocationTemplate.getId());
	}

	private @Nullable BPartnerId getBpartnerIdFromRefAndTriggeringField(final @NonNull TableRecordReference tableRecordReference, final @NonNull String triggeringField)
	{
		//currently only implemented for C_Order
		tableRecordReference.assertTableName(I_C_Order.Table_Name);
		final I_C_Order order = orderDAO.getById(OrderId.ofRepoId(tableRecordReference.getRecord_ID()));
		switch (triggeringField)
		{
			case I_C_Order.COLUMNNAME_DropShip_Location_ID:
				return BPartnerId.ofRepoIdOrNull(order.getDropShip_BPartner_ID());
			case I_C_Order.COLUMNNAME_Bill_Location_ID:
				return BPartnerId.ofRepoIdOrNull(order.getBill_BPartner_ID());
			case I_C_Order.COLUMNNAME_C_BPartner_Location_ID:
				return BPartnerId.ofRepoIdOrNull(order.getC_BPartner_ID());
			case I_C_Order.COLUMNNAME_HandOver_Location_ID:
				return BPartnerId.ofRepoIdOrNull(order.getHandOver_Partner_ID());
			default:
				throw new AdempiereException("Logic not implemented for field: " + triggeringField);
		}
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
