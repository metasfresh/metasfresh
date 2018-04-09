package org.adempiere.mm.attributes.countryattribute.impl;

/*
 * #%L
 * de.metas.swat.base
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


import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareAttributeService;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareFactory;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_Product;

import de.metas.adempiere.model.I_C_InvoiceLine;

/**
 * Creates/Updates model's {@link I_M_AttributeInstance}s based on {@link ICountryAware}.
 * 
 * @author tsa
 *
 */
public class CountryAwareAttributeUpdater
{
	// services
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributeSetInstanceAwareFactoryService asiAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);
	private final transient IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

	private Object sourceModel;
	private ICountryAwareFactory countryAwareFactory;
	private ICountryAwareAttributeService countryAwareAttributeService;

	/**
	 * Updates source model's ASI.
	 */
	public void updateASI()
	{
		final Object sourceModel = getSourceModel();

		final IAttributeSetInstanceAware asiAware = asiAwareFactoryService.createOrNull(sourceModel);
		if (asiAware == null)
		{
			return;
		}

		final ICountryAware countryAware = getCountryAwareFactory().createCountryAware(sourceModel);
		if (countryAware == null)
		{
			// shall not happen, but skip it for now
			return;
		}

		//
		// We need to copy the attributes only for Purchase Transactions (06790)
		if (countryAware.isSOTrx())
		{
			return;
		}

		//
		// Get M_Attribute_ID
		final ICountryAwareAttributeService countryAwareAttributeService = getCountryAwareAttributeService();
		final int attributeId = countryAwareAttributeService.getM_Attribute_ID(countryAware);
		if (attributeId <= 0)
		{
			// Attribute was not configured
			return;
		}

		//
		// Get M_Attribute, if applies to our product
		final I_M_Product product = asiAware.getM_Product();
		final I_M_Attribute attribute = attributesBL.getAttributeOrNull(product, attributeId);
		if (attribute == null)
		{
			// The product's attribute set doesn't contain our attribute. Do nothing.
			return;
		}
		// Don't update the attribute if it's not document relevant (08642)
		if (!isAttrDocumentRelevant(attribute))
		{
			return;
		}

		//
		// Create Attribute Set Instance
		final I_M_AttributeSetInstance asi = attributeSetInstanceBL.getCreateASI(asiAware);

		// Check if our attribute was already set in the ASI
		final I_M_AttributeInstance ai = attributeDAO.retrieveAttributeInstance(asi, attributeId);
		if (ai != null)
		{
			// In case it was, just leave it as it is
			return;
		}

		//
		// Get M_AttributeValue
		final IContextAware ctx = InterfaceWrapperHelper.getContextAware(sourceModel);
		final I_M_AttributeValue attributeValue = countryAwareAttributeService.getCreateAttributeValue(ctx, countryAware);
		if (attributeValue == null)
		{
			// No attribute and sys config set to "Ignore". Nothing to do.
			return;
		}

		//
		// Create/Update the Attribute Instance
		attributeSetInstanceBL.getCreateAttributeInstance(asi, attributeValue);
	}

	private boolean isAttrDocumentRelevant(final I_M_Attribute attribute)
	{
		final String docTableName = getSourceTableName();
		if (I_C_InvoiceLine.Table_Name.equals(docTableName))
		{
			return attribute.isAttrDocumentRelevant();
		}
		else
		{
			return true;
		}
	}

	public CountryAwareAttributeUpdater setSourceModel(final Object sourceModel)
	{
		this.sourceModel = sourceModel;
		return this;
	}

	private final Object getSourceModel()
	{
		Check.assumeNotNull(sourceModel, "sourceModel not null");
		return sourceModel;
	}

	private final String getSourceTableName()
	{
		return InterfaceWrapperHelper.getModelTableName(getSourceModel());
	}

	public CountryAwareAttributeUpdater setCountryAwareFactory(final ICountryAwareFactory countryAwareFactory)
	{
		this.countryAwareFactory = countryAwareFactory;
		return this;
	}

	private final ICountryAwareFactory getCountryAwareFactory()
	{
		Check.assumeNotNull(countryAwareFactory, "countryAwareFactory not null");
		return countryAwareFactory;
	}

	public final CountryAwareAttributeUpdater setCountryAwareAttributeService(final ICountryAwareAttributeService countryAwareAttributeService)
	{
		this.countryAwareAttributeService = countryAwareAttributeService;
		return this;
	}

	private final ICountryAwareAttributeService getCountryAwareAttributeService()
	{
		Check.assumeNotNull(countryAwareAttributeService, "countryAwareAttributeService not null");
		return countryAwareAttributeService;
	}

}
