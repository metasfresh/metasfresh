package de.metas.inoutcandidate.modelvalidator;

import java.util.Collection;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.compiere.model.MClient;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.CacheMgt;

import de.metas.adempiere.model.I_M_Product;
import de.metas.inoutcandidate.agg.key.impl.ShipmentScheduleKeyValueHandler;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.housekeeping.sqi.impl.Reset_M_ShipmentSchedule_Recompute;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.impl.DefaultCandidateProcessor;
import de.metas.inoutcandidate.spi.impl.OnlyOneOpenInvoiceCandProcessor;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.product.IProductBL;
import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.storage.StorageListenerAdapter;

/**
 * Shipment Schedule / Receipt Schedule module activator
 *
 * NOTE: atm we have modelChange/docValidate interceptors here. Please don't add more like this but consider creating separate model interceptors.
 *
 * @author ts
 *
 */
public final class InOutCandidateValidator implements ModelValidator
{
	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}

		//
		// 07344: Register RS AggregationKey Dependencies
		registerSSAggregationKeyDependencies();

		engine.addModelValidator(new C_Order(), client);
		engine.addModelValidator(new C_Order_ShipmentSchedule(), client);
		engine.addModelValidator(new C_OrderLine_ShipmentSchedule(), client);
		engine.addModelValidator(new M_ShipmentSchedule(), client);
		engine.addModelValidator(new M_Shipment_Constraint(), client);
		engine.addModelValidator(new de.metas.inoutcandidate.modelvalidator.M_AttributeInstance(), client);
		engine.addModelValidator(new M_InOutLine_Shipment(), client);
		engine.addModelValidator(new M_InOut_Shipment(), client);
		engine.addModelValidator(new C_BPartner_ShipmentSchedule(), client);

		engine.addModelValidator(new M_ShipmentSchedule_QtyPicked(), client); // task 08123

		engine.addModelChange(org.compiere.model.I_M_Product.Table_Name, this);

		// FRESH-342: clean up stale M_ShipmentSchedule_Recompute records.
		Services.get(IHouseKeepingBL.class).registerStartupHouseKeepingTask(new Reset_M_ShipmentSchedule_Recompute());

		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);

		//
		// 08255: M_ShipmentSchedule update qtys
		programaticCalloutProvider.registerAnnotatedCallout(de.metas.inoutcandidate.callout.M_ShipmentSchedule.instance);

		//
		// SPI implementations. Note that we lean on Service autodetection
		// This fix a problem where another module calls "Services.get(IShipmentScheduleBL.class)"
		// and then this validator overwrites the already configured IShipmentScheduleBL with a new instance
		Check.assume(Services.isAutodetectServices(), "Assuming that Services.isAutodetectServices() is true");
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);

		shipmentScheduleBL.registerCandidateProcessor(new DefaultCandidateProcessor());
		shipmentScheduleBL.registerCandidateProcessor(new OnlyOneOpenInvoiceCandProcessor());

		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(OrderLineShipmentScheduleHandler.class);

		Services.get(IStorageListeners.class).addStorageListener(new StorageListenerAdapter()
		{
			@Override
			public void onStorageSegmentChanged(final Collection<IStorageSegment> storageSegments)
			{
				Services.get(IShipmentSchedulePA.class).invalidate(storageSegments);
			}
		});

		setupCaching();
	}

	private void setupCaching()
	{
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_M_ShipmentSchedule.Table_Name);
	}

	/**
	 * Public for testing purposes only!
	 */
	public static void registerSSAggregationKeyDependencies()
	{
		final IAggregationKeyRegistry keyRegistry = Services.get(IAggregationKeyRegistry.class);

		final String registrationKey = ShipmentScheduleHeaderAggregationKeyBuilder.REGISTRATION_KEY;

		//
		// Register Handlers
		keyRegistry.registerAggregationKeyValueHandler(registrationKey, new ShipmentScheduleKeyValueHandler());

		//
		// Register ShipmentScheduleHeaderAggregationKeyBuilder
		keyRegistry.registerDependsOnColumnnames(registrationKey,
				I_M_ShipmentSchedule.COLUMNNAME_C_DocType_ID,
				I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_ID,
				I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Override_ID,
				I_M_ShipmentSchedule.COLUMNNAME_C_BPartner_Location_ID,
				I_M_ShipmentSchedule.COLUMNNAME_C_BP_Location_Override_ID,
				I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, // DateOrdered, POReference fields also depend on this
				I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_ID,
				I_M_ShipmentSchedule.COLUMNNAME_M_Warehouse_Override_ID,
				I_M_ShipmentSchedule.COLUMNNAME_AD_User_ID,
				I_M_ShipmentSchedule.COLUMNNAME_AD_User_Override_ID,
				I_M_ShipmentSchedule.COLUMNNAME_AD_Org_ID,
				I_M_ShipmentSchedule.COLUMNNAME_DateOrdered);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		if (po instanceof MProduct)
		{
			productChange((MProduct)po, type);
		}
		return null;
	}

	private void productChange(final MProduct productPO, final int type)
	{
		if (type == ModelValidator.TYPE_AFTER_NEW || type == ModelValidator.TYPE_AFTER_CHANGE)
		{
			final boolean isDiversechanged = productPO.is_ValueChanged(I_M_Product.COLUMNNAME_IS_DIVERSE);
			final boolean isProductTypeChanged = productPO.is_ValueChanged(org.compiere.model.I_M_Product.COLUMNNAME_ProductType);

			if (isDiversechanged || isProductTypeChanged)
			{
				final boolean display = Services.get(IProductBL.class).isItem(productPO);

				final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
				shipmentSchedulePA.invalidateForProduct(productPO.get_ID(), productPO.get_TrxName());
				shipmentSchedulePA.setIsDiplayedForProduct(productPO.get_ID(), display, productPO.get_TrxName());
			}
		}
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
	}
}
