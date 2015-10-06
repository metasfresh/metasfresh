package org.adempiere.document.model.impl;

/*
 * #%L
 * ADempiere ERP - Base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.document.model.IDocumentLocation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;

/**
 * Plain implementation of {@link IDocumentLocation} which will load dependent objects (bpartner, location, contact) on demand, using given context and trxName.
 * 
 * @author tsa
 * 
 */
public class PlainDocumentLocation implements IDocumentLocation
{

	private final Properties ctx;
	private final String trxName;

	private final int bpartnerId;
	private I_C_BPartner bpartner;

	private final int bpartnerLocationId;
	private I_C_BPartner_Location bpartnerLocation;

	private final int contactId;
	private I_AD_User contact;

	private String bpartnerAddress;

	public PlainDocumentLocation(final Properties ctx, final int bpartnerId, final int bpartnerLocationId, final int contactId, final String trxName)
	{
		this.ctx = ctx;
		this.bpartnerId = bpartnerId;
		this.bpartnerLocationId = bpartnerLocationId;
		this.contactId = contactId;
		this.trxName = trxName;
	}

	@Override
	public int getC_BPartner_ID()
	{
		return bpartnerId;
	}

	@Override
	public I_C_BPartner getC_BPartner()
	{
		if (bpartner == null)
		{
			bpartner = InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, trxName);
		}
		return bpartner;
	}

	@Override
	public int getC_BPartner_Location_ID()
	{
		return bpartnerLocationId;
	}

	@Override
	public I_C_BPartner_Location getC_BPartner_Location()
	{
		if (bpartnerLocation == null)
		{
			bpartnerLocation = InterfaceWrapperHelper.create(ctx, bpartnerLocationId, I_C_BPartner_Location.class, trxName);
		}
		return bpartnerLocation;
	}

	@Override
	public int getAD_User_ID()
	{
		return contactId;
	}

	@Override
	public I_AD_User getAD_User()
	{
		if (contact == null)
		{
			contact = InterfaceWrapperHelper.create(ctx, contactId, I_AD_User.class, trxName);
		}
		return contact;
	}

	@Override
	public String getBPartnerAddress()
	{
		return bpartnerAddress;
	}

	@Override
	public void setBPartnerAddress(String address)
	{
		this.bpartnerAddress = address;
	}

}
