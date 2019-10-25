package de.metas.inoutcandidate.modelvalidator;

import java.util.Collection;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.compiere.model.I_M_Product;
import org.compiere.model.MClient;
import org.compiere.model.MProduct;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import com.google.common.annotations.VisibleForTesting;

import de.metas.cache.CacheMgt;
import de.metas.inoutcandidate.agg.key.impl.ShipmentScheduleKeyValueHandler;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentScheduleInvalidateRepository;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.impl.DefaultCandidateProcessor;
import de.metas.inoutcandidate.spi.impl.OnlyOneOpenInvoiceCandProcessor;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.storage.IStorageListeners;
import de.metas.storage.IStorageSegment;
import de.metas.storage.StorageListenerAdapter;
import de.metas.util.Check;
import de.metas.util.Services;

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
	public void initialize(final ModelValidationEngine engine, final MClient client)
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
				final IShipmentScheduleInvalidateRepository invalidSchedulesRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
				invalidSchedulesRepo.invalidateStorageSegments(storageSegments);
			}
		});

		setupCaching();
	}

	private void setupCaching()
	{
		CacheMgt.get().enableRemoteCacheInvalidationForTableName(I_M_ShipmentSchedule.Table_Name);
	}

	@VisibleForTesting
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
				I_M_ShipmentSchedule.COLUMNNAME_C_Order_ID, // by adding this, we also cover DateOrdered and POReference
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
			productChange((MProduct)po, ModelChangeType.valueOf(type));
		}
		return null;
	}

	private void productChange(final I_M_Product productPO, final ModelChangeType type)
	{
		if(type.isNewOrChange() && type.isAfter())
		{
			final boolean isDiverseChanged = InterfaceWrapperHelper.isValueChanged(productPO, de.metas.adempiere.model.I_M_Product.COLUMNNAME_IsDiverse);
			final boolean isProductTypeChanged = InterfaceWrapperHelper.isValueChanged(productPO, I_M_Product.COLUMNNAME_ProductType);

			if (isDiverseChanged || isProductTypeChanged)
			{
				final ProductId productId = ProductId.ofRepoId(productPO.getM_Product_ID());
				final boolean display = Services.get(IProductBL.class).isItem(productPO);

				final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
				shipmentSchedulePA.setIsDiplayedForProduct(productId, display);

				final IShipmentScheduleInvalidateRepository shipmentScheduleInvalidateRepo = Services.get(IShipmentScheduleInvalidateRepository.class);
				shipmentScheduleInvalidateRepo.invalidateForProduct(productId);
			}
		}
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		return null; // nothing to do
	}
}
