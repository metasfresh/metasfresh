package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnableAdapter;
import org.compiere.util.Util;
import org.slf4j.Logger;

import com.google.common.base.Joiner;

import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.logging.LogManager;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.base.model.I_PMM_RfQResponse_ChangeEvent;
import de.metas.procurement.base.model.I_PMM_WeekReport_Event;
import de.metas.procurement.base.model.X_PMM_RfQResponse_ChangeEvent;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncProductSupply;
import de.metas.procurement.sync.protocol.SyncRfQChangeRequest;
import de.metas.procurement.sync.protocol.SyncRfQPriceChangeEvent;
import de.metas.procurement.sync.protocol.SyncRfQQtyChangeEvent;
import de.metas.procurement.sync.protocol.SyncWeeklySupply;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class ServerSyncBL implements IServerSyncBL
{
	private static final Logger logger = LogManager.getLogger(ServerSyncBL.class);

	@Override
	public List<SyncBPartner> getAllBPartners()
	{
		List<SyncBPartner> syncBPartners = SyncObjectsFactory.newFactory().createAllSyncBPartners();
		logger.debug("Returning: {}", syncBPartners);
		return syncBPartners;
	}

	@Override
	public List<SyncProduct> getAllProducts()
	{
		List<SyncProduct> syncProducts = SyncObjectsFactory.newFactory().createAllSyncProducts();
		logger.debug("Returning: {}", syncProducts);
		return syncProducts;
	}

	@Override
	public String getInfoMessage()
	{
		final String infoMessage = SyncObjectsFactory.newFactory().createSyncInfoMessage();
		logger.debug("Returning: {}", infoMessage);
		return infoMessage;
	}

	@Override
	public void reportProductSupplies(final SyncProductSuppliesRequest request)
	{
		logger.debug("Got request: {}", request);

		final List<SyncProductSupply> syncProductSupplies = request.getProductSupplies();
		for (final SyncProductSupply syncProductSupply : syncProductSupplies)
		{
			try
			{
				createQtyReportEvent(syncProductSupply);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing " + syncProductSupply + ". Skipped.", e);
			}
		}
	}

	private void createQtyReportEvent(final SyncProductSupply syncProductSupply)
	{
		final String product_uuid = syncProductSupply.getProduct_uuid();
		loadPMMProductAndProcess(
				product_uuid,
				new IEventProcessor()
				{
					@Override
					public void processEvent(final IContextAware context, final I_PMM_Product pmmProduct)
					{
						createQtyReportEvent(context, pmmProduct, syncProductSupply);
					}
				});
	}

	private void createQtyReportEvent(final IContextAware context, final I_PMM_Product pmmProduct, final SyncProductSupply syncProductSupply)
	{
		logger.debug("Creating QtyReport event from {} ({})", syncProductSupply, pmmProduct);

		final List<String> errors = new ArrayList<>();

		final I_PMM_QtyReport_Event qtyReportEvent = InterfaceWrapperHelper.newInstance(I_PMM_QtyReport_Event.class, context);
		try
		{
			qtyReportEvent.setEvent_UUID(syncProductSupply.getUuid());

			// Product
			qtyReportEvent.setProduct_UUID(syncProductSupply.getProduct_uuid());
			qtyReportEvent.setPMM_Product(pmmProduct);

			// BPartner
			final String bpartner_uuid = syncProductSupply.getBpartner_uuid();
			qtyReportEvent.setPartner_UUID(bpartner_uuid);
			if (!Check.isEmpty(bpartner_uuid, true))
			{
				final int bpartnerId = SyncUUIDs.getC_BPartner_ID(bpartner_uuid);
				qtyReportEvent.setC_BPartner_ID(bpartnerId);
			}
			else
			{
				errors.add("@Missing@ @" + I_C_BPartner.COLUMNNAME_C_BPartner_ID + "@");
			}

			// C_FlatrateTerm
			final String contractLine_uuid = syncProductSupply.getContractLine_uuid();
			qtyReportEvent.setContractLine_UUID(contractLine_uuid);
			if (!Check.isEmpty(contractLine_uuid, true))
			{
				final int flatrateTermId = SyncUUIDs.getC_Flatrate_Term_ID(contractLine_uuid);
				qtyReportEvent.setC_Flatrate_Term_ID(flatrateTermId);
			}

			// QtyPromised(CU)
			final BigDecimal qtyPromised = Util.coalesce(syncProductSupply.getQty(), BigDecimal.ZERO);
			qtyReportEvent.setQtyPromised(qtyPromised);

			// DatePromised
			final Timestamp datePromised = TimeUtil.asTimestamp(syncProductSupply.getDay());
			qtyReportEvent.setDatePromised(datePromised);

			// Is a weekly planning record?
			qtyReportEvent.setIsPlanning(syncProductSupply.isWeekPlanning());

			//
			// Update the QtyReport event
			updateFromPMMProduct(qtyReportEvent, errors);
		}
		catch (final Exception e)
		{
			logger.error("Failed importing " + syncProductSupply, e);
			errors.add(e.getLocalizedMessage());
		}
		finally
		{
			//
			// check if we have errors to report
			if (!errors.isEmpty())
			{
				final String errorMsg = Joiner.on("\n").skipNulls().join(errors);
				final IMsgBL msgBL = Services.get(IMsgBL.class);
				final Properties ctx = InterfaceWrapperHelper.getCtx(qtyReportEvent);
				qtyReportEvent.setErrorMsg(msgBL.translate(ctx, errorMsg));
				qtyReportEvent.setIsError(true);
				InterfaceWrapperHelper.save(qtyReportEvent);

				logger.debug("Got following errors while importing {} to {}:\n{}", syncProductSupply, qtyReportEvent, errorMsg);
			}
			else
			{
				InterfaceWrapperHelper.save(qtyReportEvent);

				logger.debug("Imported {} to {}:\n{}", syncProductSupply, qtyReportEvent);
			}

			//
			// Notify agent that we got the message
			final boolean isInternalGenerated = syncProductSupply.getUuid() == null;
			if (!isInternalGenerated)
			{
				final String serverEventId = String.valueOf(qtyReportEvent.getPMM_QtyReport_Event_ID());
				SyncConfirmationsSender.forCurrentTransaction().confirm(syncProductSupply, serverEventId);
			}
		}
	}

	private void updateFromPMMProduct(final I_PMM_QtyReport_Event qtyReportEvent, final Collection<String> errors)
	{
		final I_PMM_Product pmmProduct = qtyReportEvent.getPMM_Product();
		if (pmmProduct == null)
		{
			errors.add("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_PMM_Product_ID + "@");
			return;
		}

		final I_M_HU_PI_Item_Product huPIItemProduct = pmmProduct.getM_HU_PI_Item_Product();
		final I_M_Product product = pmmProduct.getM_Product();
		final I_C_UOM uom;
		if (huPIItemProduct != null)
		{
			uom = huPIItemProduct.getC_UOM();
		}
		else
		{
			errors.add("@Missing@ @" + I_PMM_QtyReport_Event.COLUMNNAME_M_HU_PI_Item_Product_ID + "@");
			uom = null;
		}

		// Product, UOM, PI Item Product
		qtyReportEvent.setM_Product(product);
		qtyReportEvent.setM_HU_PI_Item_Product(huPIItemProduct);
		qtyReportEvent.setC_UOM(uom);

		// ASI
		if (pmmProduct.getM_AttributeSetInstance_ID() > 0)
			qtyReportEvent.setM_AttributeSetInstance_ID(pmmProduct.getM_AttributeSetInstance_ID());
		else
			qtyReportEvent.setM_AttributeSetInstance(null);

		// M_Warehouse
		final int warehouseId = pmmProduct.getM_Warehouse_ID();
		qtyReportEvent.setM_Warehouse_ID(warehouseId);

		// AD_Org_ID
		final int orgId = pmmProduct.getAD_Org_ID();
		qtyReportEvent.setAD_Org_ID(orgId);

		// QtyPromised_TU
		if (huPIItemProduct != null)
		{
			final BigDecimal qtyPromisedCU = qtyReportEvent.getQtyPromised();
			final BigDecimal qtyCUsPerTU = huPIItemProduct.getQty();
			final BigDecimal qtyPromisedTU;
			if (qtyCUsPerTU.signum() == 0)
			{
				qtyPromisedTU = BigDecimal.ONE;
			}
			else
			{
				qtyPromisedTU = qtyPromisedCU.divide(qtyCUsPerTU, 0, RoundingMode.UP);
			}
			qtyReportEvent.setQtyPromised_TU(qtyPromisedTU);
		}
	}

	@Override
	public void reportWeekSupply(final SyncWeeklySupplyRequest request)
	{
		for (final SyncWeeklySupply syncWeeklySupply : request.getWeeklySupplies())
		{
			try
			{
				createWeekReportEvent(syncWeeklySupply);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing " + syncWeeklySupply + ". Skipped.", e);
			}
		}
	}

	public static interface IEventProcessor
	{
		void processEvent(IContextAware context, I_PMM_Product pmmProduct);
	}

	private void createWeekReportEvent(final SyncWeeklySupply syncWeeklySupply)
	{
		final String product_uuid = syncWeeklySupply.getProduct_uuid();
		loadPMMProductAndProcess(product_uuid, new IEventProcessor()
		{
			@Override
			public void processEvent(final IContextAware context, final I_PMM_Product pmmProduct)
			{
				createWeekReportEvent(context, pmmProduct, syncWeeklySupply);
			}
		});
	}

	private void createWeekReportEvent(final IContextAware context, final I_PMM_Product pmmProduct, final SyncWeeklySupply syncWeeklySupply)
	{
		logger.debug("Creating Week Report event from {} ({})", syncWeeklySupply, pmmProduct);

		final I_PMM_WeekReport_Event event = InterfaceWrapperHelper.newInstance(I_PMM_WeekReport_Event.class, context);
		event.setEvent_UUID(syncWeeklySupply.getUuid());

		// BPartner
		final int bpartnerId = SyncUUIDs.getC_BPartner_ID(syncWeeklySupply.getBpartner_uuid());
		if (bpartnerId <= 0)
		{
			throw new AdempiereException("No C_BPartner found for " + syncWeeklySupply);
		}
		event.setC_BPartner_ID(bpartnerId);
		final I_C_BPartner bpartner = event.getC_BPartner();
		if (bpartner == null)
		{
			throw new AdempiereException("No C_BPartner found for ID=" + bpartnerId);
		}

		// Organization
		event.setAD_Org_ID(bpartner.getAD_Org_ID());

		// Product
		event.setPMM_Product(pmmProduct);
		event.setM_Product_ID(pmmProduct.getM_Product_ID());
		if (pmmProduct.getM_HU_PI_Item_Product_ID() > 0)
		{
			event.setM_HU_PI_Item_Product_ID(pmmProduct.getM_HU_PI_Item_Product_ID());
		}
		if (pmmProduct.getM_AttributeSetInstance_ID() > 0)
		{
			event.setM_AttributeSetInstance_ID(pmmProduct.getM_AttributeSetInstance_ID());
		}

		// WeekDate
		final Timestamp weekDate = TimeUtil.trunc(syncWeeklySupply.getWeekDay(), TimeUtil.TRUNC_WEEK);
		event.setWeekDate(weekDate);

		// Trend
		final String trend = syncWeeklySupply.getTrend();
		event.setPMM_Trend(trend);

		// Save
		event.setProcessed(false);
		event.setIsActive(true);
		InterfaceWrapperHelper.save(event);

		logger.debug("Imported {} to {}:\n{}", syncWeeklySupply, event);

		// Notify agent that we got the message
		final String serverEventId = String.valueOf(event.getPMM_WeekReport_Event_ID());
		SyncConfirmationsSender.forCurrentTransaction().confirm(syncWeeklySupply, serverEventId);
	}

	/**
	 * Loads the {@link I_PMM_Product} for the given <code>product_uuid</code>, then creates and updates a temporary context and invokes the given <code>processor</code>.
	 *
	 * @param product_uuid
	 * @param processor
	 */
	private void loadPMMProductAndProcess(final String product_uuid, final IEventProcessor processor)
	{
		final int pmmProductId = SyncUUIDs.getPMM_Product_ID(product_uuid);
		if (pmmProductId <= 0)
		{
			throw new AdempiereException("@NotFound@ @PMM_Product_ID@ for UUID=" + product_uuid);
		}

		final Properties tempCtx = Env.newTemporaryCtx();
		final I_PMM_Product pmmProduct = InterfaceWrapperHelper.create(tempCtx, pmmProductId, I_PMM_Product.class, ITrx.TRXNAME_None);
		if (pmmProduct == null)
		{
			throw new AdempiereException("@NotFound@ @PMM_Product_ID@ for ID=" + pmmProductId + " (record not found)");
		}

		// required because if the ctx contains #AD_Client_ID = 0, we might not get the term's C_Flatrate_DataEntries from the DAO further down,
		// and also the new even record needs to have the PMM_Product's AD_Client_ID and AD_Org_ID
		Env.setContext(tempCtx, Env.CTXNAME_AD_Client_ID, pmmProduct.getAD_Client_ID());
		Env.setContext(tempCtx, Env.CTXNAME_AD_Org_ID, pmmProduct.getAD_Org_ID());

		try (final IAutoCloseable contextRestorer = Env.switchContext(tempCtx))
		{
			Services.get(ITrxManager.class).run(new TrxRunnableAdapter()
			{
				@Override
				public void run(final String localTrxName) throws Exception
				{
					final IContextAware context = PlainContextAware.newWithThreadInheritedTrx(tempCtx);
					processor.processEvent(context, pmmProduct);
				}
			});
		}
	}

	@Override
	public void reportRfQChanges(final SyncRfQChangeRequest request)
	{
		for (final SyncRfQPriceChangeEvent priceChangeEvent : request.getPriceChangeEvents())
		{
			try
			{
				createRfQPriceChangeEvent(priceChangeEvent);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing " + priceChangeEvent + ". Skipped.", e);
			}
		}

		for (final SyncRfQQtyChangeEvent qtyChangeEvent : request.getQtyChangeEvents())
		{
			try
			{
				createRfQQtyChangeEvent(qtyChangeEvent);
			}
			catch (final Exception e)
			{
				logger.error("Failed importing " + qtyChangeEvent + ". Skipped.", e);
			}
		}

	}

	private void createRfQPriceChangeEvent(final SyncRfQPriceChangeEvent priceChangeEvent)
	{
		final String product_uuid = priceChangeEvent.getProduct_uuid();
		loadPMMProductAndProcess(
				product_uuid,
				new IEventProcessor()
				{
					@Override
					public void processEvent(final IContextAware context, final I_PMM_Product pmmProduct)
					{
						createRfQPriceChangeEvent(context, pmmProduct, priceChangeEvent);
					}
				});
	}

	private void createRfQPriceChangeEvent(final IContextAware context, final I_PMM_Product pmmProduct, final SyncRfQPriceChangeEvent syncPriceChangeEvent)
	{
		logger.debug("Creating event from {} ({})", syncPriceChangeEvent, pmmProduct);

		final I_PMM_RfQResponse_ChangeEvent event = InterfaceWrapperHelper.newInstance(I_PMM_RfQResponse_ChangeEvent.class, context);
		event.setEvent_UUID(syncPriceChangeEvent.getUuid());

		//
		// RfQ Response Line
		final String rfqResponseLine_UUID = syncPriceChangeEvent.getRfq_uuid();
		event.setC_RfQResponseLine_UUID(rfqResponseLine_UUID);

		//
		// Price
		event.setType(X_PMM_RfQResponse_ChangeEvent.TYPE_Price);
		event.setPrice(syncPriceChangeEvent.getPrice());

		// Product
		event.setPMM_Product(pmmProduct);

		// Save
		event.setProcessed(false);
		event.setIsActive(true);
		InterfaceWrapperHelper.save(event);

		logger.debug("Imported {} to {}:\n{}", syncPriceChangeEvent, event);

		// Notify agent that we got the message
		final String serverEventId = String.valueOf(event.getPMM_RfQResponse_ChangeEvent_ID());
		SyncConfirmationsSender.forCurrentTransaction().confirm(syncPriceChangeEvent, serverEventId);
	}

	private void createRfQQtyChangeEvent(final SyncRfQQtyChangeEvent qtyChangeEvent)
	{
		final String product_uuid = qtyChangeEvent.getProduct_uuid();
		loadPMMProductAndProcess(
				product_uuid,
				new IEventProcessor()
				{
					@Override
					public void processEvent(final IContextAware context, final I_PMM_Product pmmProduct)
					{
						createRfQQtyChangeEvent(context, pmmProduct, qtyChangeEvent);
					}
				});
	}

	private void createRfQQtyChangeEvent(final IContextAware context, final I_PMM_Product pmmProduct, final SyncRfQQtyChangeEvent syncQtyChangeEvent)
	{
		logger.debug("Creating event from {} ({})", syncQtyChangeEvent, pmmProduct);

		final I_PMM_RfQResponse_ChangeEvent event = InterfaceWrapperHelper.newInstance(I_PMM_RfQResponse_ChangeEvent.class, context);
		event.setEvent_UUID(syncQtyChangeEvent.getUuid());

		//
		// RfQ Response Line
		final String rfqResponseLine_UUID = syncQtyChangeEvent.getRfq_uuid();
		event.setC_RfQResponseLine_UUID(rfqResponseLine_UUID);

		//
		// Qty
		event.setType(X_PMM_RfQResponse_ChangeEvent.TYPE_Quantity);
		event.setQty(syncQtyChangeEvent.getQty());
		
		//
		// Date
		final Timestamp datePromised = syncQtyChangeEvent.getDay() == null ? null : TimeUtil.trunc(syncQtyChangeEvent.getDay(), TimeUtil.TRUNC_DAY);
		event.setDatePromised(datePromised);

		// Product
		event.setPMM_Product(pmmProduct);

		// Save
		event.setProcessed(false);
		event.setIsActive(true);
		InterfaceWrapperHelper.save(event);

		logger.debug("Imported {} to {}:\n{}", syncQtyChangeEvent, event);

		// Notify agent that we got the message
		final String serverEventId = String.valueOf(event.getPMM_RfQResponse_ChangeEvent_ID());
		SyncConfirmationsSender.forCurrentTransaction().confirm(syncQtyChangeEvent, serverEventId);
	}

}
