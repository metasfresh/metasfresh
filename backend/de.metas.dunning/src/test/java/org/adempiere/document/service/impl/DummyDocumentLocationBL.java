package org.adempiere.document.service.impl;

/*
 * #%L
 * de.metas.dunning
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


import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.IDocumentBillLocation;
import de.metas.document.model.IDocumentDeliveryLocation;
import de.metas.document.model.IDocumentHandOverLocation;
import de.metas.document.model.IDocumentLocation;

/**
 * Dummy Document Location BL. This service is required for testing Dunning module, decoupled from database.
 * 
 * @author ad
 * 
 */
public class DummyDocumentLocationBL implements IDocumentLocationBL
{

	@Override
	public void setBPartnerAddress(IDocumentLocation doc)
	{
		doc.setBPartnerAddress("Dummy BP Address: " + doc.toString());
	}

	@Override
	public void setBillToAddress(IDocumentBillLocation doc)
	{
		doc.setBillToAddress("Dummy BillTo Address: " + doc.toString());
	}

	@Override
	public void setDeliveryToAddress(IDocumentDeliveryLocation doc)
	{
		doc.setDeliveryToAddress("Dummy DeliveryTo Address: " + doc.toString());
	}

	@Override
	public void setHandOverAddress(IDocumentHandOverLocation doc) 
	{
		doc.setHandOverAddress("Dummy Handover Address: " + doc.toString());	
	}

}
