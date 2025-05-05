/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package org.adempiere.warehouse.api.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.exceptions.BPartnerNoBillToAddressException;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.location.DocumentLocation;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ResourceId;
import de.metas.user.User;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.CreateWarehouseRequest;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.api.Warehouse;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WarehouseBL implements IWarehouseBL
{
	private static final Logger logger = LogManager.getLogger(WarehouseBL.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	public I_M_Warehouse getById(@NonNull final WarehouseId warehouseId)
	{
		return warehouseDAO.getById(warehouseId);
	}

	@Override
	public I_M_Locator getOrCreateDefaultLocator(@NonNull final I_M_Warehouse warehouse)
	{
		return getOrCreateDefaultLocator(WarehouseId.ofRepoId(warehouse.getM_Warehouse_ID()));
	}

	@Override
	public I_M_Locator getOrCreateDefaultLocator(@NonNull final WarehouseId warehouseId)
	{
		final LocatorId defaultLocatorId = getOrCreateDefaultLocatorId(warehouseId);
		return warehouseDAO.getLocatorById(defaultLocatorId);
	}

	@Override
	@NonNull
	public LocatorId getOrCreateDefaultLocatorId(@NonNull final WarehouseId warehouseId)
	{
		final List<I_M_Locator> locators = warehouseDAO.getLocators(warehouseId);
		int activeLocatorsCount = 0;
		if (!locators.isEmpty())
		{
			I_M_Locator locatorFirst = null;

			//
			// Search for default locator if any
			for (final I_M_Locator locator : locators)
			{
				if (!locator.isActive())
				{
					continue;
				}

				activeLocatorsCount++;

				// Just found the default locator, return it now
				if (locator.isDefault())
				{
					return LocatorId.ofRecordOrNull(locator);
				}

				// Remember the first locator in case we don't find a default
				if (locatorFirst == null)
				{
					locatorFirst = locator;
				}
			}

			//
			// No Default Locator, return the first one
			if (locatorFirst != null)
			{
				// Log a warning, in case there are more then one active locators.
				if (activeLocatorsCount > 1)
				{
					final String warehouseName = warehouseDAO.getWarehouseName(warehouseId);
					logger.warn("No default locator for warehouse {}. Returning the first one: {}", warehouseName, locatorFirst);
				}

				return LocatorId.ofRecordOrNull(locatorFirst);
			}
		}

		//
		// No Locator was found: no default one and non which is active
		// => Create a new Locator and return it
		return warehouseDAO.createDefaultLocator(warehouseId);
	}

	private I_C_Location getC_Location(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		return getC_Location(warehouse);
	}

	private I_C_Location getC_Location(final I_M_Warehouse warehouse)
	{
		final BPartnerLocationId bpLocationId = extractBPartnerLocationId(warehouse);
		final LocationId locationId = getLocationIdFromBPartnerLocationId(bpLocationId);
		final I_C_Location location = locationDAO.getById(locationId);
		if (location == null)
		{
			throw new AdempiereException("No location found for " + locationId);
		}

		return location;
	}

	private LocationId getLocationIdFromBPartnerLocationId(@NonNull final BPartnerLocationId bpLocationId)
	{
		final I_C_BPartner_Location bpLocation = bpartnerDAO.getBPartnerLocationByIdEvenInactive(bpLocationId);
		if (bpLocation == null)
		{
			throw new AdempiereException("No location found for " + bpLocationId);
		}
		return LocationId.ofRepoId(bpLocation.getC_Location_ID());
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		return extractBPartnerLocationId(warehouse);
	}

	@Nullable
	@Override
	public CountryId getCountryId(@NonNull final WarehouseId warehouseId)
	{
		final I_C_Location location = getC_Location(warehouseId);
		return CountryId.ofRepoIdOrNull(location.getC_Country_ID());
	}

	@Override
	@NonNull
	public OrgId getWarehouseOrgId(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouseRecord = warehouseDAO.getById(warehouseId);
		return OrgId.ofRepoIdOrAny(warehouseRecord.getAD_Org_ID());
	}

	@Override
	public DocumentLocation getPlainDocumentLocation(@NonNull final WarehouseId warehouseId)
	{
		final BPartnerLocationAndCaptureId bpLocationId = warehouseDAO.getWarehouseLocationById(warehouseId);

		final IBPartnerBL partnerBL = SpringContextHolder.instance.getBean(IBPartnerBL.class); // NOTE: keep it local because if declared as a field would fail a lot of junit tests
		final LocationId locationId = partnerBL.getLocationId(bpLocationId);

		return DocumentLocation.builder()
				.bpartnerId(bpLocationId.getBpartnerId())
				.bpartnerLocationId(bpLocationId.getBpartnerLocationId())
				.locationId(locationId)
				.contactId(null) // N/A
				.bpartnerAddress(null) // N/A
				.build();
	}

	private static BPartnerLocationId extractBPartnerLocationId(final I_M_Warehouse warehouse)
	{
		final BPartnerLocationId warehouseBPLocationId = BPartnerLocationId.ofRepoIdOrNull(warehouse.getC_BPartner_ID(), warehouse.getC_BPartner_Location_ID());
		if (warehouseBPLocationId == null)
		{
			throw new AdempiereException("@NotFound@ @C_BPartner_Location_ID@ (@M_Warehouse_ID@:" + warehouse.getName() + ")");
		}
		return warehouseBPLocationId;
	}

	@Override
	public String getLocatorNameById(final LocatorId locatorId)
	{
		return warehouseDAO.getLocatorById(locatorId).getValue();
	}

	@Override
	public String getWarehouseName(@NonNull final WarehouseId warehouseId)
	{
		return warehouseDAO.getWarehouseName(warehouseId);
	}

	@Override
	public LocatorId getLocatorIdByRepoId(final int locatorRepoId)
	{
		return warehouseDAO.getLocatorIdByRepoId(locatorRepoId);
	}

	@Override
	public I_M_Locator getLocatorByRepoId(final int locatorRepoId)
	{
		return warehouseDAO.getLocatorByRepoId(locatorRepoId);
	}

	@Override
	public WarehouseId getInTransitWarehouseId(final OrgId adOrgId)
	{
		return warehouseDAO.getInTransitWarehouseId(adOrgId);
	}

	@Override
	public Optional<ResourceId> getPlantId(final WarehouseId warehouseId)
	{
		return ResourceId.optionalOfRepoId(warehouseDAO.getById(warehouseId).getPP_Plant_ID());
	}

	@Override
	public void updateWarehouseLocation(@NonNull final LocationId oldLocationId, @NonNull final LocationId newLocationId)
	{
		final ImmutableSet<WarehouseId> warehouseIds = warehouseDAO.retrieveWarehouseWithLocation(oldLocationId);

		for (final WarehouseId warehouseId : warehouseIds)
		{
			updateWarehouseLocation(warehouseId, newLocationId);
		}
	}

	@Override
	@NonNull
	public DocumentLocation getBPartnerBillingLocationDocument(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouseRecord = warehouseDAO.getById(warehouseId);

		final BPartnerLocationId warehouseBPartnerLocationId = BPartnerLocationId.ofRepoId(warehouseRecord.getC_BPartner_ID(), warehouseRecord.getC_BPartner_Location_ID());

		// prefer the warehouse's C_BPartner_Location_ID, but if it's not a billTolocation, then fall back to another bill-location of the bpartner
		final I_C_BPartner_Location billToLocation = Optional.ofNullable(bpartnerDAO.getBPartnerLocationByIdInTrx(warehouseBPartnerLocationId))
				.filter(I_C_BPartner_Location::isBillTo)
				.orElseGet(() -> bpartnerDAO.retrieveCurrentBillLocationOrNull(warehouseBPartnerLocationId.getBpartnerId()));

		if (billToLocation == null)
		{
			throw new BPartnerNoBillToAddressException(bpartnerDAO.getById(warehouseBPartnerLocationId.getBpartnerId()));
		}

		final BPartnerLocationId billingLocationId = BPartnerLocationId.ofRepoId(billToLocation.getC_BPartner_ID(), billToLocation.getC_BPartner_Location_ID());

		final IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);

		final User warehouseContact = bpartnerBL.retrieveContactOrNull(IBPartnerBL.RetrieveContactRequest.builder()
																					.onlyActive(true)
																					.contactType(IBPartnerBL.RetrieveContactRequest.ContactType.BILL_TO_DEFAULT)
																					.bpartnerId(billingLocationId.getBpartnerId())
																					.bPartnerLocationId(billingLocationId)
																					.ifNotFound(IBPartnerBL.RetrieveContactRequest.IfNotFound.RETURN_NULL)
																					.build());

		final BPartnerContactId billContactId = Optional.ofNullable(warehouseContact)
				.flatMap(User::getBPartnerContactId)
				.orElse(null);

		return DocumentLocation.builder()
				.bpartnerId(billingLocationId.getBpartnerId())
				.bpartnerLocationId(billingLocationId)
				.contactId(billContactId)
				.build();
	}

	private void updateWarehouseLocation(@NonNull final WarehouseId warehouseId, @NonNull final LocationId locationId)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getByIdInTrx(warehouseId, I_M_Warehouse.class);
		warehouse.setC_Location_ID(locationId.getRepoId());

		InterfaceWrapperHelper.save(warehouse);
	}

	@NonNull
	public WarehouseId getIdByLocatorRepoId(final int locatorId)
	{
		final I_M_Locator locator = getLocatorByRepoId(locatorId);

		return WarehouseId.ofRepoId(locator.getM_Warehouse_ID());
	}

	@Override
	public boolean isDropShipWarehouse(@NonNull final WarehouseId warehouseId, @NonNull final OrgId adOrgId)
	{
		final WarehouseId dropShipWarehouseId = orgDAO.getOrgDropshipWarehouseId(adOrgId);

		return warehouseId.equals(dropShipWarehouseId);
	}

	@Override
	public Optional<LocationId> getLocationIdByLocatorRepoId(final int locatorRepoId)
	{
		final WarehouseId warehouseId = getIdByLocatorRepoId(locatorRepoId);
		final I_M_Warehouse warehouse = getById(warehouseId);
		return Optional.ofNullable(LocationId.ofRepoIdOrNull(warehouse.getC_Location_ID()));
	}

	@Override
	public OrgId getOrgIdByLocatorRepoId(final int locatorId)
	{
		return warehouseDAO.retrieveOrgIdByLocatorId(locatorId);
	}

	@NonNull
	@Override
	public BPartnerId getBPartnerId(@NonNull final WarehouseId warehouseId)
	{
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);
		return BPartnerId.ofRepoId(warehouse.getC_BPartner_ID());
	}

	@NonNull
	public Optional<WarehouseId> getOptionalIdByValue(@NonNull final String value)
	{
		return warehouseDAO.getOptionalIdByValue(value);
	}

	@NonNull
	public Warehouse getByIdNotNull(@NonNull final WarehouseId id)
	{
		return warehouseDAO.getOptionalById(id)
				.orElseThrow(() -> new AdempiereException("No warehouse found for ID !")
						.appendParametersToMessage()
						.setParameter("WarehouseId", id));
	}

	public void save(@NonNull final Warehouse warehouse)
	{
		warehouseDAO.save(warehouse);
	}

	@NonNull
	public Warehouse createWarehouse(@NonNull final CreateWarehouseRequest request)
	{
		return warehouseDAO.createWarehouse(request);
	}

    @Override
    @NonNull
    public ImmutableSet<LocatorId> getLocatorIdsOfTheSamePickingGroup(@NonNull final WarehouseId warehouseId)
    {
        final Set<WarehouseId> pickFromWarehouseIds = warehouseDAO.getWarehouseIdsOfSamePickingGroup(warehouseId);
        return warehouseDAO.getLocatorIdsByWarehouseIds(pickFromWarehouseIds);
    }
}
