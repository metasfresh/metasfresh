package de.metas.bpartner.impexp;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.impexp.BPartnersCache.BPartner;
import de.metas.common.util.CoalesceUtil;
import de.metas.impexp.processing.IImportInterceptor;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationCreateRequest;
import de.metas.location.LocationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ class BPartnerLocationImportHelper
{
	public static BPartnerLocationImportHelper newInstance()
	{
		return new BPartnerLocationImportHelper();
	}

	private final ILocationDAO locationDAO = Services.get(ILocationDAO.class);
	private BPartnerImportProcess process;

	private BPartnerLocationImportHelper()
	{
	}

	public BPartnerLocationImportHelper setProcess(@NonNull final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	public void importRecord(@NonNull final BPartnerImportContext context)
	{
		// first, try to find an existent one
		I_C_BPartner_Location bpLocation = fetchAndUpdateExistingBPLocation(context);
		if (bpLocation == null)
		{
			bpLocation = createNewBPartnerLocation(context);
		}

		if (bpLocation != null)
		{
			final BPartnerLocationId bpLocationId = BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID());
			context.setCurrentBPartnerLocationId(bpLocationId);
		}

	}

	/**
	 * retrieve existent BPartner location and call method for updating the fields
	 */
	private I_C_BPartner_Location fetchAndUpdateExistingBPLocation(@NonNull final BPartnerImportContext context)
	{
		final BPartnersCache cache = context.getBpartnersCache();

		final I_I_BPartner importRecord = context.getCurrentImportRecord();
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(importRecord.getC_BPartner_ID());
		final BPartner bpartner = cache.getBPartnerById(bpartnerId);

		final BPartnerLocationId bpLocationIdOrNull = BPartnerLocationId.ofRepoIdOrNull(bpartnerId, importRecord.getC_BPartner_Location_ID());

		I_C_BPartner_Location bpartnerLocation = bpLocationIdOrNull != null
				? bpartner.getBPLocationById(bpLocationIdOrNull).orElse(null)
				: null;

		if (bpartnerLocation == null)
		{
			final BPartnerLocationMatchingKey bpLocationMatchingKey = BPartnerLocationMatchingKey.of(importRecord);
			bpartnerLocation = bpartner.getFirstBPLocationMatching(bpLocationMatchingKey).orElse(null);
		}

		if (bpartnerLocation != null)
		{
			updateBPartnerLocation(bpartner, bpartnerLocation, importRecord);
		}

		return bpartnerLocation;
	}

	/**
	 * create a new BPartner location
	 * <ul>
	 * * C_Country_ID > 0
	 * </ul>
	 * <ul>
	 * * City not empty
	 * </ul>
	 */
	private I_C_BPartner_Location createNewBPartnerLocation(@NonNull final BPartnerImportContext context)
	{
		final I_I_BPartner importRecord = context.getCurrentImportRecord();

		if (importRecord.getC_Country_ID() > 0
				&& !Check.isEmpty(importRecord.getCity(), true))
		{
			final BPartner bpartner = context.getCurrentBPartner();

			final I_C_BPartner_Location bpartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class);
			bpartnerLocation.setAD_Org_ID(bpartner.getOrgId());
			bpartnerLocation.setC_BPartner_ID(bpartner.getIdOrNull().getRepoId());

			updateBPartnerLocation(bpartner, bpartnerLocation, importRecord);

			return bpartnerLocation;
		}
		else
		{
			return null;
		}
	}

	private void updateBPartnerLocation(
			@NonNull final BPartner bpartner,
			@NonNull final I_C_BPartner_Location bpartnerLocation,
			@NonNull final I_I_BPartner from)
	{
		updateLocation(from, bpartnerLocation);

		updateBillToAndShipToFlags(from, bpartnerLocation);

		updatePhoneAndFax(from, bpartnerLocation);

		bpartnerLocation.setExternalId(from.getC_BPartner_Location_ExternalId());
		bpartnerLocation.setGLN(from.getGLN());

		fireImportValidator(from, bpartnerLocation);
		bpartner.addAndSaveLocation(bpartnerLocation);
	}

	private void updateLocation(
			@NonNull final I_I_BPartner importRecord,
			@NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		final LocationId locationId = locationDAO.createOrReuseLocation(LocationCreateRequest.builder()
				.address1(importRecord.getAddress1())
				.address2(importRecord.getAddress2())
				.address3(importRecord.getAddress3())
				.address4(importRecord.getAddress4())
				.postal(importRecord.getPostal())
				.postalAdd(importRecord.getPostal_Add())
				.city(importRecord.getCity())
				.regionId(importRecord.getC_Region_ID())
				.countryId(CountryId.ofRepoId(importRecord.getC_Country_ID()))
				.poBox(importRecord.getPOBox())
				.build());

		bpartnerLocation.setC_Location_ID(locationId.getRepoId());
		bpartnerLocation.setBPartnerName(importRecord.getlocation_bpartner_name());
		bpartnerLocation.setName(CoalesceUtil.firstNotBlank(importRecord.getlocation_name(), bpartnerLocation.getName(), "."));
		bpartnerLocation.setDelivery_Info(importRecord.getDelivery_Info());
	}

	private static void updateBillToAndShipToFlags(
			@NonNull final I_I_BPartner importRecord,
			@NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		bpartnerLocation.setIsShipToDefault(importRecord.isShipToDefault());
		bpartnerLocation.setIsShipTo(extractIsShipTo(importRecord));
		bpartnerLocation.setIsBillToDefault(importRecord.isBillToDefault());
		bpartnerLocation.setIsBillTo(extractIsBillTo(importRecord));
	}

	private static void updatePhoneAndFax(
			@NonNull final I_I_BPartner importRecord,
			@NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		if (importRecord.getPhone() != null)
		{
			bpartnerLocation.setPhone(importRecord.getPhone());
		}
		if (importRecord.getPhone2() != null)
		{
			bpartnerLocation.setPhone2(importRecord.getPhone2());
		}
		if (importRecord.getFax() != null)
		{
			bpartnerLocation.setFax(importRecord.getFax());
		}
	}

	private void fireImportValidator(
			@NonNull final I_I_BPartner importRecord,
			@NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		ModelValidationEngine.get().fireImportValidate(process, importRecord, bpartnerLocation, IImportInterceptor.TIMING_AFTER_IMPORT);
	}

	@VisibleForTesting
	static boolean extractIsShipTo(@NonNull final I_I_BPartner importRecord)
	{
		return importRecord.isShipToDefault() ? true : importRecord.isShipTo();
	}

	@VisibleForTesting
	static boolean extractIsBillTo(@NonNull final I_I_BPartner importRecord)
	{
		return importRecord.isBillToDefault() ? true : importRecord.isBillTo();
	}
}
