package org.adempiere.mm.attributes.countryattribute.impl;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.asi_aware.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.asi_aware.factory.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.countryattribute.ICountryAware;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareAttributeService;
import org.adempiere.mm.attributes.countryattribute.ICountryAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

/**
 * Creates/Updates model's {@link I_M_AttributeInstance}s based on {@link ICountryAware}.
 *
 * @author tsa
 */
public class CountryAwareAttributeUpdater
{
	// services
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributeSetInstanceAwareFactoryService asiAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);

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
		final AttributeId attributeId = countryAwareAttributeService.getAttributeId(countryAware);
		if (attributeId == null)
		{
			// Attribute was not configured
			return;
		}

		//
		// Get M_Attribute, if applies to our product
		final ProductId productId = ProductId.ofRepoId(asiAware.getM_Product_ID());
		final I_M_Attribute attribute = attributesBL.getAttributeOrNull(productId, attributeId);
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
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());

		// Check if our attribute was already set in the ASI
		final I_M_AttributeInstance ai = attributeSetInstanceBL.getAttributeInstance(asiId, attributeId);
		if (ai != null)
		{
			// In case it was, just leave it as it is
			return;
		}

		//
		// Get M_AttributeValue
		final IContextAware ctx = InterfaceWrapperHelper.getContextAware(sourceModel);
		final AttributeListValue attributeValue = countryAwareAttributeService.getCreateAttributeValue(ctx, countryAware);
		if (attributeValue == null)
		{
			// No attribute and sys config set to "Ignore". Nothing to do.
			return;
		}

		//
		// Create/Update the Attribute Instance
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeValue);
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

	private Object getSourceModel()
	{
		Check.assumeNotNull(sourceModel, "sourceModel not null");
		return sourceModel;
	}

	private String getSourceTableName()
	{
		return InterfaceWrapperHelper.getModelTableName(getSourceModel());
	}

	public CountryAwareAttributeUpdater setCountryAwareFactory(final ICountryAwareFactory countryAwareFactory)
	{
		this.countryAwareFactory = countryAwareFactory;
		return this;
	}

	private ICountryAwareFactory getCountryAwareFactory()
	{
		Check.assumeNotNull(countryAwareFactory, "countryAwareFactory not null");
		return countryAwareFactory;
	}

	public final CountryAwareAttributeUpdater setCountryAwareAttributeService(final ICountryAwareAttributeService countryAwareAttributeService)
	{
		this.countryAwareAttributeService = countryAwareAttributeService;
		return this;
	}

	private ICountryAwareAttributeService getCountryAwareAttributeService()
	{
		Check.assumeNotNull(countryAwareAttributeService, "countryAwareAttributeService not null");
		return countryAwareAttributeService;
	}

}
