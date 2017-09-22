package org.adempiere.impexp.bpartner;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.impexp.IImportValidator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_I_BPartner;
import org.compiere.model.ModelValidationEngine;

import com.google.common.annotations.VisibleForTesting;

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

	private BPartnerImportProcess process;

	private BPartnerLocationImportHelper()
	{
	}

	public BPartnerLocationImportHelper setProcess(final BPartnerImportProcess process)
	{
		this.process = process;
		return this;
	}

	private Properties getCtx()
	{
		return process.getCtx();
	}

	public I_C_BPartner_Location importRecord(final I_I_BPartner importRecord, final List<I_I_BPartner> previousImportRecordsForSameBPartner)
	{
		I_C_BPartner_Location bpartnerLocation = importRecord.getC_BPartner_Location();

		final List<I_I_BPartner> alreadyImportedBPAddresses = previousImportRecordsForSameBPartner.stream()
				.filter(isSameAddress(importRecord))
				.collect(Collectors.toList());

		final boolean isAlreadyImportedBPAddresses = alreadyImportedBPAddresses.isEmpty() ? false : true;
		if (isAlreadyImportedBPAddresses
				|| bpartnerLocation != null && bpartnerLocation.getC_BPartner_Location_ID() > 0)// Update Location
		{
			if (isAlreadyImportedBPAddresses)
			{
				bpartnerLocation = alreadyImportedBPAddresses.get(0).getC_BPartner_Location();
			}

			updateExistingBPartnerLocation(importRecord, bpartnerLocation);
		}
		else 	// New Location
		if (importRecord.getC_Country_ID() > 0
				&& !Check.isEmpty(importRecord.getAddress1(), true)
				&& !Check.isEmpty(importRecord.getCity(), true))
		{
			bpartnerLocation = createNewBPartnerLocation(importRecord);
		}

		return bpartnerLocation;
	}

	private I_C_BPartner_Location createNewBPartnerLocation(final I_I_BPartner importRecord)
	{
		final I_C_BPartner bpartner = importRecord.getC_BPartner();
		final I_C_BPartner_Location bpartnerLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, bpartner);
		bpartnerLocation.setC_BPartner(bpartner);

		updateExistingBPartnerLocation(importRecord, bpartnerLocation);

		return bpartnerLocation;
	}

	private void updateExistingBPartnerLocation(final I_I_BPartner importRecord, final I_C_BPartner_Location bpartnerLocation)
	{
		//
		// Location
		I_C_Location location = bpartnerLocation.getC_Location();
		if (location == null)
		{
			location = InterfaceWrapperHelper.create(getCtx(), I_C_Location.class, ITrx.TRXNAME_ThreadInherited);
		}
		updateExistingLocation(importRecord, location);
		bpartnerLocation.setC_Location(location);

		//
		// IsBillTo and IsShipTo flags
		bpartnerLocation.setIsShipToDefault(importRecord.isShipToDefault());
		bpartnerLocation.setIsShipTo(extractIsShipTo(importRecord));
		bpartnerLocation.setIsBillToDefault(importRecord.isBillToDefault());
		bpartnerLocation.setIsBillTo(extractIsBillTo(importRecord));

		//
		// Phone, Fax etc
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

		//
		ModelValidationEngine.get().fireImportValidate(process, importRecord, bpartnerLocation, IImportValidator.TIMING_AFTER_IMPORT);
		InterfaceWrapperHelper.save(bpartnerLocation);

		importRecord.setC_BPartner_Location(bpartnerLocation);
	}

	private static void updateExistingLocation(final I_I_BPartner importRecord, final I_C_Location location)
	{
		location.setAddress1(importRecord.getAddress1());
		location.setAddress2(importRecord.getAddress2());
		location.setPostal(importRecord.getPostal());
		location.setPostal_Add(importRecord.getPostal_Add());
		location.setCity(importRecord.getCity());
		location.setC_Region_ID(importRecord.getC_Region_ID());
		location.setC_Country_ID(importRecord.getC_Country_ID());
		InterfaceWrapperHelper.save(location);
	}

	@VisibleForTesting
	static final boolean extractIsShipTo(final I_I_BPartner importRecord)
	{
		return importRecord.isShipToDefault() ? true : importRecord.isShipTo();
	}

	@VisibleForTesting
	static final boolean extractIsBillTo(final I_I_BPartner importRecord)
	{
		return importRecord.isBillToDefault() ? true : importRecord.isBillTo();
	}

	private static Predicate<I_I_BPartner> isSameAddress(final I_I_BPartner importRecord)
	{
		return p -> p.getC_BPartner_Location_ID() > 0
				&& importRecord.getC_Country_ID() == p.getC_Country_ID()
				&& importRecord.getC_Region_ID() == p.getC_Region_ID()
				&& Objects.equals(importRecord.getCity(), p.getCity())
				&& Objects.equals(importRecord.getAddress1(), p.getAddress1())
				&& Objects.equals(importRecord.getAddress2(), p.getAddress2())
				&& Objects.equals(importRecord.getPostal(), p.getPostal())
				&& Objects.equals(importRecord.getPostal_Add(), p.getPostal_Add());
	}
}
