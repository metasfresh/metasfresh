package de.metas.ordercandidate.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPriceListDAO;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.pricing.exceptions.ProductNotOnPriceListException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.currency.ICurrencyDAO;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.logging.LogManager;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderLineBL;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.api.IOLCandBL;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.api.IOLCandEffectiveValuesBL;
import de.metas.ordercandidate.api.OLCandAggregation;
import de.metas.ordercandidate.api.OLCandOrderDefaults;
import de.metas.ordercandidate.api.OLCandsProcessor;
import de.metas.ordercandidate.api.RelationTypeOLCandSource;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_OLCandGenerator;
import de.metas.ordercandidate.model.I_C_OLCandProcessor;
import de.metas.ordercandidate.spi.CompositeOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandCreator;
import de.metas.ordercandidate.spi.IOLCandGroupingProvider;
import de.metas.ordercandidate.spi.IOLCandListener;
import de.metas.relation.grid.ModelRelationTarget;
import de.metas.workflow.api.IWFExecutionFactory;
import lombok.NonNull;

public class OLCandBL implements IOLCandBL
{
	private static final Logger logger = LogManager.getLogger(OLCandBL.class);

	private final CompositeOLCandListener olCandListeners = new CompositeOLCandListener();
	private final CompositeOLCandGroupingProvider groupingValuesProviders = new CompositeOLCandGroupingProvider();

	@Override
	public void process(final I_C_OLCandProcessor processor)
	{
		final OLCandOrderDefaults orderDefaults = createOLCandOrderDefaults(processor);

		final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
		final int olCandProcessorId = processor.getC_OLCandProcessor_ID();
		final OLCandAggregation aggregationInfo = olCandDAO.retrieveOLCandAggregation(olCandProcessorId);

		final RelationTypeOLCandSource candidatesSource = RelationTypeOLCandSource.builder()
				.processor(processor)
				.build();

		OLCandsProcessor.builder()
				.orderDefaults(orderDefaults)
				.userInChargeId(processor.getAD_User_InCharge_ID())
				.olCandProcessorId(olCandProcessorId)
				.olCandListeners(olCandListeners)
				.aggregationInfo(aggregationInfo)
				.groupingValuesProviders(groupingValuesProviders)
				.candidatesSource(candidatesSource)
				.build()
				.process();
	}

	private OLCandOrderDefaults createOLCandOrderDefaults(final I_C_OLCandProcessor processor)
	{
		return OLCandOrderDefaults.builder()
				.deliveryRule(processor.getDeliveryRule())
				.deliveryViaRule(processor.getDeliveryViaRule())
				.shipperId(processor.getM_Shipper_ID())
				.warehouseId(processor.getM_Warehouse_ID())
				.freightCostRule(processor.getFreightCostRule())
				.paymentRule(processor.getPaymentRule())
				.paymentTermId(processor.getC_PaymentTerm_ID())
				.invoiceRule(processor.getInvoiceRule())
				.docTypeTargetId(processor.getC_DocTypeTarget_ID())
				.build();
	}

	@Override
	public int getPricingSystemId(@NonNull final I_C_OLCand olCand, final OLCandOrderDefaults orderDefaults)
	{
		Check.assumeNotNull(olCand, "Param 'olCand' not null");

		final int result;
		if (olCand.getM_PricingSystem_ID() > 0)
		{
			result = olCand.getM_PricingSystem_ID();
		}
		else if (orderDefaults != null && orderDefaults.getPricingSystemId() > 0)
		{
			result = orderDefaults.getPricingSystemId();
		}
		else
		{
			final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

			final int bpartnerId = effectiveValuesBL.getBill_BPartner_Effective_ID(olCand);
			final boolean soTrx = true;

			final int pricingSystemId = bPartnerDAO.retrievePricingSystemId(Env.getCtx(), bpartnerId, soTrx, ITrx.TRXNAME_None);
			result = pricingSystemId;
		}

		return result;
	}

	@Override
	public String mkRelationTypeInternalName(final I_C_OLCandProcessor processor)
	{
		return I_C_OLCandProcessor.Table_Name + "_" + processor.getC_OLCandProcessor_ID() + "<=>" + I_C_OLCand.Table_Name;
	}

	@Override
	public ModelRelationTarget mkModelRelationTarget(
			final I_C_OLCandProcessor processor,
			final int sourceWindowId,
			final String sourceTabName,
			String whereClause)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final Properties ctx = InterfaceWrapperHelper.getCtx(processor);
		final ModelRelationTarget model = new ModelRelationTarget();
		// configure the dialog's model parameters

		model.setAdTableSourceId(adTableDAO.retrieveTableId(I_C_OLCandProcessor.Table_Name));
		model.setRecordSourceId(processor.getC_OLCandProcessor_ID());

		// filter by AD_DataDestination_ID
		final I_AD_InputDataSource dataDest = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, true, ITrx.TRXNAME_None);

		if (!Check.isEmpty(whereClause, true))
		{
			whereClause += " AND ";
		}
		whereClause += I_C_OLCand.COLUMNNAME_AD_DataDestination_ID + " = " + dataDest.getAD_InputDataSource_ID();

		model.setTargetWhereClause(whereClause);

		model.setAdWindowSourceId(sourceWindowId);

		model.setAdTableTargetId(adTableDAO.retrieveTableId(I_C_OLCand.Table_Name));

		final MTable olCandTable = MTable.get(ctx, I_C_OLCand.Table_Name);
		model.setAdWindowTargetId(olCandTable.getAD_Window_ID());

		model.setRelationTypeInternalName(mkRelationTypeInternalName(processor));

		model.setRelationTypeName(
				(sourceTabName == null ? "" : sourceTabName + " ")
						+ processor.getName()
						+ "<=>"
						+ olCandTable.getName());

		return model;
	}


	@Override
	public I_C_OLCand invokeOLCandCreator(final PO po)
	{
		final IOLCandCreator olCandCreator = retrieveOlCandCreatorInstance(po.getCtx(), po.get_Table_ID(), po.get_TrxName());
		if (olCandCreator == null)
		{
			final String msg = "Unable to process '" + po + "'; Missing IOLCandCreator implmentation for table '" + Services.get(IADTableDAO.class).retrieveTableName(po.get_Table_ID()) + "'";
			OLCandBL.logger.warn(msg);
			throw new AdempiereException(msg);
		}

		return invokeOLCandCreator(po, olCandCreator);
	}

	@Override
	public I_C_OLCand invokeOLCandCreator(final PO po, final IOLCandCreator olCandCreator)
	{
		Check.assumeNotNull(olCandCreator, "olCandCreator is not null");

		final I_C_OLCand olCand = olCandCreator.createFrom(po);
		if (po.set_ValueOfColumn("Processed", true))
		{
			po.saveEx();
		}

		if (olCand == null)
		{
			OLCandBL.logger.info(olCandCreator + " returned null for " + po + "; Nothing to do.");
			return null;
		}

		olCand.setAD_Table_ID(po.get_Table_ID());
		olCand.setRecord_ID(po.get_ID());

		InterfaceWrapperHelper.save(olCand);

		Services.get(IWFExecutionFactory.class).notifyActivityPerformed(po, olCand); // 03745

		return olCand;
	}

	private IOLCandCreator retrieveOlCandCreatorInstance(final Properties ctx, final int tableId, final String trxName)
	{
		final I_C_OLCandGenerator olCandGenerator = Services.get(IOLCandDAO.class).retrieveOlCandCreator(ctx, tableId, trxName);
		return Util.getInstance(IOLCandCreator.class, olCandGenerator.getOCGeneratorImpl());
	}

	@Override
	public void registerOLCandListener(final IOLCandListener l)
	{
		olCandListeners.add(l);
	}

	@Override
	public void registerCustomerGroupingValuesProvider(final IOLCandGroupingProvider groupingProvider)
	{
		groupingValuesProviders.add(groupingProvider);
	}

	@Override
	public String toString()
	{
		return "OLCandBL [olCandListeners=" + olCandListeners + ", groupingValuesProviders=" + groupingValuesProviders + "]";
	}

	@Override
	public IPricingResult computePriceActual(
			final I_C_OLCand olCand,
			final BigDecimal qtyOverride,
			final int pricingSystemIdOverride,
			final Timestamp date)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(olCand);

		final IPricingBL pricingBL = Services.get(IPricingBL.class);
		final IEditablePricingContext pricingCtx = pricingBL.createPricingContext();
		pricingCtx.setReferencedObject(olCand);

		final IPricingResult pricingResult;
		if (olCand.isManualDiscount() && olCand.isManualPrice())
		{
			// create an empty one. we won't use the pricing engine to fill it.
			pricingResult = Services.get(IPricingBL.class).createInitialResult(pricingCtx);
		}
		else
		{
			final IOLCandEffectiveValuesBL effectiveValuesBL = Services.get(IOLCandEffectiveValuesBL.class);
			final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

			final int bill_BPartner_ID = effectiveValuesBL.getBill_BPartner_Effective_ID(olCand);

			final I_C_BPartner_Location dropShipLocation = effectiveValuesBL.getDropShip_Location_Effective(olCand);

			pricingCtx.setC_Country_ID(dropShipLocation.getC_Location().getC_Country_ID());

			final BigDecimal qty = qtyOverride != null ? qtyOverride : olCand.getQty();
			final int pricingSystemId = pricingSystemIdOverride > 0 ? pricingSystemIdOverride : getPricingSystemId(olCand, OLCandOrderDefaults.NULL);

			if (pricingSystemId <= 0)
			{
				throw new AdempiereException("@M_PricingSystem@ @NotFound@");
			}
			pricingCtx.setM_PricingSystem_ID(pricingSystemId); // set it to the context that way it will also be in the result, even if the pricing rules won't need it

			pricingCtx.setC_BPartner_ID(bill_BPartner_ID);
			pricingCtx.setQty(qty);
			pricingCtx.setPriceDate(date);
			pricingCtx.setSOTrx(true);

			pricingCtx.setDisallowDiscount(olCand.isManualDiscount());

			final I_M_PriceList pl = productPA.retrievePriceListByPricingSyst(ctx, pricingSystemId, dropShipLocation.getC_BPartner_Location_ID(), true, ITrx.TRXNAME_None);

			if (pl == null)
			{
				throw new AdempiereException("@M_PriceList@ @NotFound@: @M_PricingSystem@ " + pricingSystemId + ", @Bill_Location@ " + dropShipLocation.getC_BPartner_Location_ID());
			}
			pricingCtx.setM_PriceList_ID(pl.getM_PriceList_ID());
			pricingCtx.setM_Product_ID(effectiveValuesBL.getM_Product_Effective_ID(olCand));

			pricingResult = pricingBL.calculatePrice(pricingCtx);

			// Just for safety: in case the product price was not found, the code below shall not be reached.
			// The exception shall be already thrown
			// ts 2015-07-03: i think it is not, at least i don't see from where
			if (pricingResult == null || !pricingResult.isCalculated())
			{
				final int documentLineNo = -1; // not needed, the msg will be shown in the line itself
				throw new ProductNotOnPriceListException(pricingCtx, documentLineNo);
			}
		}

		final BigDecimal priceEntered;
		final BigDecimal discount;
		final int currencyId;

		if (olCand.isManualPrice())
		{
			// both price and currency need to be already set in the olCand (only a price amount doesn't make sense with an unspecified currency)
			priceEntered = olCand.getPriceEntered();
			currencyId = olCand.getC_Currency_ID();
		}
		else
		{
			priceEntered = pricingResult.getPriceStd();
			currencyId = pricingResult.getC_Currency_ID();
		}

		if (olCand.isManualDiscount())
		{
			discount = olCand.getDiscount();
		}
		else
		{
			discount = pricingResult.getDiscount();
		}

		if (currencyId <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Currency@"
					+ "\n Pricing context: " + pricingCtx
					+ "\n Pricing result: " + pricingResult);
		}

		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrency(ctx, currencyId);
		final BigDecimal priceActual = Services.get(IOrderLineBL.class).subtractDiscount(priceEntered, discount, currency.getStdPrecision());

		pricingResult.setPriceStd(priceActual);
		pricingResult.setDiscount(discount);

		return pricingResult;
	}
}
