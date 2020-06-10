package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;

import de.metas.bpartner.BPartnerId;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class BPLocationBuilder
{
	private boolean shipTo;

	private boolean billTo;

	private boolean billToDefault;
	
	private boolean active = true;

	private final BPartnerId bpartnerId;

	public BPLocationBuilder(@NonNull BPartnerId bpartnerId)
	{
		this.bpartnerId = bpartnerId;
	}

	public I_C_BPartner_Location createRecord()
	{
		final I_C_BPartner_Location bpLocationRecord = newInstance(I_C_BPartner_Location.class);
		bpLocationRecord.setC_BPartner_ID(bpartnerId.getRepoId());
		bpLocationRecord.setIsActive(active);
		bpLocationRecord.setIsShipTo(shipTo);
		bpLocationRecord.setIsBillTo(billTo);
		bpLocationRecord.setIsBillToDefault(billToDefault);

		final int locationID = createLocation();
		bpLocationRecord.setC_Location_ID(locationID);
		
		saveRecord(bpLocationRecord);

		return bpLocationRecord;
	}
	
	private int createLocation()
	{
		final I_C_Location locationRecord = newInstance(I_C_Location.class);
		locationRecord.setAddress1("test");
		locationRecord.setC_Country_ID(101);
		saveRecord(locationRecord);

		return locationRecord.getC_Location_ID();
	}
	
	

	public BPLocationBuilder billTo(final boolean billTo)
	{
		this.billTo = billTo;
		return this;
	}

	public BPLocationBuilder shipTo(final boolean shipTo)
	{
		this.shipTo = shipTo;
		return this;
	}

	public BPLocationBuilder billToDefault(final boolean billToDefault)
	{
		this.billToDefault = billToDefault;
		return this;
	}
	
	public BPLocationBuilder active(final boolean active)
	{
		this.active = active;
		return this;
	}
}
