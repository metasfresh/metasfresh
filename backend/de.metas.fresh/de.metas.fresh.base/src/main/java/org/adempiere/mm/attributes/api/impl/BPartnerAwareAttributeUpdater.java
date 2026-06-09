package org.adempiere.mm.attributes.api.impl;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.asi_aware.IAttributeSetInstanceAware;
import org.adempiere.mm.attributes.asi_aware.factory.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.IBPartnerAware;
import org.adempiere.mm.attributes.api.IBPartnerAwareAttributeService;
import org.adempiere.mm.attributes.api.IBPartnerAwareFactory;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;

/**
 * Creates/Updates model's {@link I_M_AttributeInstance}s based on {@link IBPartnerAware}.
 *
 * @author tsa
 */
public class BPartnerAwareAttributeUpdater
{
	// services
	private final transient IAttributeSetInstanceAwareFactoryService attributeSetInstanceAwareFactoryService = Services.get(IAttributeSetInstanceAwareFactoryService.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final transient IAttributesBL attributesBL = Services.get(IAttributesBL.class);

	private Object sourceModel;
	private IBPartnerAwareFactory bpartnerAwareFactory;
	private IBPartnerAwareAttributeService bpartnerAwareAttributeService;
	private boolean forceApplyForSOTrx = false;

	/**
	 * Updates source model's ASI.
	 */
	public void updateASI()
	{
		final Object sourceModel = getSourceModel();

		final IAttributeSetInstanceAware asiAware = attributeSetInstanceAwareFactoryService.createOrNull(sourceModel);
		if (asiAware == null)
		{
			return;
		}

		if (asiAware.getM_Product_ID() <= 0)
		{
			return;
		}

		final IBPartnerAware bpartnerAware = getBPartnerAwareFactory().createBPartnerAware(sourceModel);
		if (bpartnerAware == null)
		{
			// partnerAware was not created (because maybe it does not apply)
			return;
		}

		//
		// Make sure we are allowed to apply to IsSOTrx flag
		if (!isApplyForSOTrx(bpartnerAware))
		{
			return;
		}

		//
		// Get M_Attribute_ID
		final IBPartnerAwareAttributeService partnerAwareAttributeService = getBPartnerAwareAttributeService();
		final AttributeId attributeId = partnerAwareAttributeService.getAttributeId(bpartnerAware);
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
			// The product's attribute set doesn't contain the attribute. Do nothing.
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

		// Check if the attribute was already set in the ASI
		final I_M_AttributeInstance ai = attributeSetInstanceBL.getAttributeInstance(asiId, attributeId);
		if (ai != null)
		{
			// In case it was, just leave it as it is
			return;
		}

		//
		// Get M_AttributeValue
		final IContextAware ctx = InterfaceWrapperHelper.getContextAware(sourceModel);
		final AttributeListValue attributeValue = partnerAwareAttributeService.getCreateAttributeValue(ctx, bpartnerAware);
		if (attributeValue == null)
		{
			// No attribute and sys config set to "Ignore". Nothing to do.
			return;
		}

		//
		// Create/Update the Attribute Instance
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeValue);
	}

	private boolean isApplyForSOTrx(final IBPartnerAware bpartnerAware)
	{
		final boolean isSOTrx = bpartnerAware.isSOTrx();

		//
		// Case: sales transaction
		if (isSOTrx)
		{
			// We shall not copy the attributes for Sales Transactions (06790)
			// Except when we are explicitly asked to do so.
			return forceApplyForSOTrx;
		}
		//
		// Case: purchase transaction
		else
		{
			// We must copy the attributes for Purchase Transactions (06790)
			return true;
		}
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

	public BPartnerAwareAttributeUpdater setSourceModel(final Object sourceModel)
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

	public BPartnerAwareAttributeUpdater setBPartnerAwareFactory(final IBPartnerAwareFactory bpartnerAwareFactory)
	{
		this.bpartnerAwareFactory = bpartnerAwareFactory;
		return this;
	}

	private IBPartnerAwareFactory getBPartnerAwareFactory()
	{
		Check.assumeNotNull(bpartnerAwareFactory, "bpartnerAwareFactory not null");
		return bpartnerAwareFactory;
	}

	public final BPartnerAwareAttributeUpdater setBPartnerAwareAttributeService(final IBPartnerAwareAttributeService bpartnerAwareAttributeService)
	{
		this.bpartnerAwareAttributeService = bpartnerAwareAttributeService;
		return this;
	}

	private IBPartnerAwareAttributeService getBPartnerAwareAttributeService()
	{
		Check.assumeNotNull(bpartnerAwareAttributeService, "bpartnerAwareAttributeService not null");
		return bpartnerAwareAttributeService;
	}

	/**
	 * Sets if we shall copy the attribute even if it's a sales transaction (i.e. IsSOTrx=true)
	 */
	public final BPartnerAwareAttributeUpdater setForceApplyForSOTrx(final boolean forceApplyForSOTrx)
	{
		this.forceApplyForSOTrx = forceApplyForSOTrx;
		return this;
	}
}
