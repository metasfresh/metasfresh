package de.metas.inoutcandidate.modelvalidator;

import com.google.common.annotations.VisibleForTesting;
import de.metas.cache.CacheMgt;
import de.metas.inoutcandidate.agg.key.impl.ShipmentScheduleKeyValueHandler;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.IShipmentScheduleUpdater;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.impl.DefaultCandidateProcessor;
import de.metas.inoutcandidate.spi.impl.OnlyOneOpenInvoiceCandProcessor;
import de.metas.order.inoutcandidate.OrderLineShipmentScheduleHandler;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.agg.key.IAggregationKeyRegistry;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_M_Product;

/**
 * Shipment Schedule / Receipt Schedule module activator
 * <p>
 * NOTE: atm we have modelChange/docValidate interceptors here. Please don't add more like this but consider creating separate model interceptors.
 *
 * @author ts
 */

public final class InOutCandidateValidator extends AbstractModelInterceptor
{
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		//
		// 07344: Register RS AggregationKey Dependencies
		registerSSAggregationKeyDependencies();

		engine.addModelValidator(new C_Order(), client);
		// engine.addModelValidator(new C_Order_ShipmentSchedule(), client); initialized by spring
		engine.addModelValidator(new C_OrderLine_ShipmentSchedule(), client);
		engine.addModelValidator(new M_Shipment_Constraint(), client);
		// engine.addModelValidator(new de.metas.inoutcandidate.modelvalidator.M_AttributeInstance(), client); initialized by spring
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
		final IShipmentScheduleUpdater shipmentScheduleUpdater = Services.get(IShipmentScheduleUpdater.class);
		shipmentScheduleUpdater.registerCandidateProcessor(new DefaultCandidateProcessor());
		shipmentScheduleUpdater.registerCandidateProcessor(new OnlyOneOpenInvoiceCandProcessor());

		final OrderLineShipmentScheduleHandler orderLineShipmentScheduleHandler = SpringContextHolder.instance.getBean(OrderLineShipmentScheduleHandler.class);
		Services.get(IShipmentScheduleHandlerBL.class).registerHandler(orderLineShipmentScheduleHandler);

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
	public void onModelChange(@NonNull final Object model, @NonNull final ModelChangeType changeType) throws Exception
	{
		if (InterfaceWrapperHelper.isInstanceOf(model, I_M_Product.class))
		{
			final I_M_Product product = InterfaceWrapperHelper.create(model, I_M_Product.class);
			productChange(product, changeType);
		}
	}

	private void productChange(@NonNull final I_M_Product productPO, @NonNull final ModelChangeType type)
	{
		if (type.isChange() /* not on new, because a new product can't have any shipment schedules yet */
				&& type.isAfter())
		{
			final boolean isDiverseChanged = InterfaceWrapperHelper.isValueChanged(productPO, de.metas.adempiere.model.I_M_Product.COLUMNNAME_IsDiverse);
			final boolean isProductTypeChanged = InterfaceWrapperHelper.isValueChanged(productPO, I_M_Product.COLUMNNAME_ProductType);

			if (isDiverseChanged || isProductTypeChanged)
			{
				final ProductId productId = ProductId.ofRepoId(productPO.getM_Product_ID());
				final boolean display = ProductType.ofCode(productPO.getProductType()).isItem();

				final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
				shipmentSchedulePA.setIsDiplayedForProduct(productId, display);

				final IShipmentScheduleInvalidateBL shipmentScheduleInvalidator = Services.get(IShipmentScheduleInvalidateBL.class);
				shipmentScheduleInvalidator.flagForRecompute(productId);
			}
		}
	}
}
