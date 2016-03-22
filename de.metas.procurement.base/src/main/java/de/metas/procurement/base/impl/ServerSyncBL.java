package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.base.model.I_PMM_WeekReport_Event;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncProductSupply;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ServerSyncBL implements IServerSyncBL
{
	@Override
	public List<SyncBPartner> getAllBPartners()
	{
		return SyncObjectsFactory.newFactory().createAllSyncBPartners();
	}

	@Override
	public List<SyncProduct> getAllNotContractedProducts()
	{
		return SyncObjectsFactory.newFactory().createAllNotContractedSyncProducts();
	}

	@Override
	public void reportProductSupplies(final SyncProductSuppliesRequest request)
	{
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final PlainContextAware ctxProvider = new PlainContextAware(Env.getCtx());

		final boolean soTrx = false;

		final List<SyncProductSupply> productSupplies = request.getProductSupplies();
		for (final SyncProductSupply productSupply : productSupplies)
		{
			final List<String> errors = new ArrayList<>();

			// first load the PMM_Product which also defines the new record's client and org
			final I_PMM_Product pmmProduct = getPMM_ProductAndUpdateCtx(ctxProvider, productSupply.getProduct_uuid());
			final I_M_Product product;
			final I_M_HU_PI_Item_Product huPIItemProduct;
			final I_C_UOM uom;
			final int warehouseId;
			final int orgId;
			if (pmmProduct != null)
			{
				product = pmmProduct.getM_Product();
				huPIItemProduct = pmmProduct.getM_HU_PI_Item_Product();
				if (huPIItemProduct != null)
				{
					uom = huPIItemProduct.getC_UOM();
				}
				else
				{
					errors.add("@Missing@ @" + I_M_HU_PI_Item_Product.COLUMNNAME_M_HU_PI_Item_Product_ID + "@");
					uom = null;
				}
				warehouseId = pmmProduct.getM_Warehouse_ID();
				orgId = pmmProduct.getAD_Org_ID();
			}
			else
			{
				errors.add("@Missing@ @" + I_PMM_Product.COLUMNNAME_PMM_Product_ID + "@");

				product = null;
				huPIItemProduct = null;
				uom = null;
				warehouseId = 0;
				orgId = 0;
			}

			final BigDecimal qtyPromised = productSupply.getQty().multiply(huPIItemProduct.getQty());
			final Timestamp datePromised = productSupply.getDay() == null ? null : new Timestamp(productSupply.getDay().getTime());

			final int bPartnerId = SyncUUIDs.getC_BPartner_ID(productSupply.getBpartner_uuid());
			final I_C_BPartner bPartner;
			if (bPartnerId > 0)
			{
				bPartner = InterfaceWrapperHelper.create(ctxProvider.getCtx(), bPartnerId, I_C_BPartner.class, ctxProvider.getTrxName());
			}
			else
			{
				errors.add("@Missing@ @" + I_C_BPartner.COLUMNNAME_C_BPartner_ID + "@");
				bPartner = null;
			}
			final int pricingSystemId = bPartnerDAO.retrievePricingSystemId(ctxProvider.getCtx(), bPartnerId, soTrx, ctxProvider.getTrxName());


			// now create the event record
			final I_PMM_QtyReport_Event qtyReportEvent = InterfaceWrapperHelper.newInstance(I_PMM_QtyReport_Event.class, ctxProvider);
			// try-finally to make sure we attempt to save the new instance, also if there are exceptions
			try
			{
				qtyReportEvent.setPartner_UUID(productSupply.getBpartner_uuid());
				qtyReportEvent.setProduct_UUID(productSupply.getProduct_uuid());

				// first set the "general stuff"
				qtyReportEvent.setAD_Org_ID(orgId);
				qtyReportEvent.setC_BPartner(bPartner);

				qtyReportEvent.setPMM_Product(pmmProduct);
				qtyReportEvent.setM_Product(product);
				qtyReportEvent.setM_HU_PI_Item_Product(huPIItemProduct);

				qtyReportEvent.setQtyPromised(qtyPromised);
				qtyReportEvent.setC_UOM(uom);
				qtyReportEvent.setDatePromised(datePromised);
				qtyReportEvent.setM_PricingSystem_ID(pricingSystemId);

				qtyReportEvent.setM_Warehouse_ID(warehouseId);

				if (Check.isEmpty(productSupply.getContractLine_uuid(), true))
				{
					final List<I_C_BPartner_Location> shipToLocations = bPartnerDAO.retrieveBPartnerShipToLocations(bPartner);

					// import a non-contract product.
					if (pricingSystemId <= 0)
					{
						// no term and no pricing system means that we can't figure out the price
						errors.add("@Missing@ @" + I_M_PricingSystem.COLUMNNAME_M_PricingSystem_ID + "@");
					}
					else if (shipToLocations.isEmpty())
					{
						errors.add("@Missing@ @" + I_C_BPartner_Location.COLUMNNAME_IsShipTo + "@");
					}
					else if (product == null || uom == null)
					{
						// nothing to do. We already added an error about the missing piip. Also shouldn't happen.
					}
					else
					{
						final IPricingBL pricingBL = Services.get(IPricingBL.class);
						final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(product.getM_Product_ID(), bPartnerId, uom.getC_UOM_ID(), qtyPromised, soTrx);
						pricingCtx.setM_PricingSystem_ID(pricingSystemId);
						pricingCtx.setPriceDate(datePromised);
						pricingCtx.setC_Country_ID(shipToLocations.get(0).getC_Location().getC_Country_ID());

						final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
						if (!pricingResult.isCalculated())
						{
							errors.add("@Missing@ " + I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + " " + pricingResult);

						}
						// these will be "empty" results, if the price was not calculated
						qtyReportEvent.setM_PriceList_ID(pricingResult.getM_PriceList_ID());
						qtyReportEvent.setC_Currency_ID(pricingResult.getC_Currency_ID());
						qtyReportEvent.setPrice(pricingResult.getPriceStd());
					}
				}
				else
				{
					// import a contracted product
					final I_C_Flatrate_Term flatrateTerm;
					final int currencyId;
					final List<I_C_Flatrate_DataEntry> dataEntries;
					final int flatrateTermId = SyncUUIDs.getC_Flatrate_Term_ID(productSupply.getContractLine_uuid());
					if (flatrateTermId > 0)
					{
						flatrateTerm = InterfaceWrapperHelper.create(ctxProvider.getCtx(), flatrateTermId, I_C_Flatrate_Term.class, ctxProvider.getTrxName());
						currencyId = flatrateTerm.getC_Currency_ID();

						dataEntries = InterfaceWrapperHelper.createList(
								flatrateDAO.retrieveDataEntries(flatrateTerm, datePromised, I_C_Flatrate_DataEntry.TYPE_Procurement_PeriodBased, true),      // onlyNonSim = true
								I_C_Flatrate_DataEntry.class);
					}
					else
					{
						errors.add("@Missing@ @" + I_C_Flatrate_Term.COLUMNNAME_C_Flatrate_Term_ID + "@");
						flatrateTerm = null;
						currencyId = 0;
						dataEntries = Collections.emptyList();
					}

					qtyReportEvent.setC_Flatrate_Term(flatrateTerm);
					qtyReportEvent.setC_Currency_ID(currencyId);

					I_C_Flatrate_DataEntry dataEntryForProduct = null;
					for (I_C_Flatrate_DataEntry dataEntry : dataEntries)
					{
						if (dataEntry.getM_Product_DataEntry_ID() != flatrateTerm.getM_Product_ID())
						{
							continue;
						}
						dataEntryForProduct = dataEntry;
						break;
					}
					if (dataEntryForProduct == null)
					{
						errors.add("@Missing@ " + I_C_Flatrate_DataEntry.COLUMNNAME_C_Flatrate_DataEntry_ID);
						qtyReportEvent.setPrice(BigDecimal.ZERO);
					}

					else if (product != null && huPIItemProduct.getC_UOM() != null && flatrateTerm != null)
					{
						final BigDecimal price = Services.get(IUOMConversionBL.class).convertPrice(
								product,
								dataEntryForProduct.getFlatrateAmtPerUOM(),
								flatrateTerm.getC_UOM(),                      // this is the flatrateAmt's UOM
								huPIItemProduct.getC_UOM(),                   // this is the qtyReportEvent's UOM
								flatrateTerm.getC_Currency().getStdPrecision());

						qtyReportEvent.setPrice(price);
					}
				}

				//
				// check if we have errors to report
				if (!errors.isEmpty())
				{
					final StringBuilder sb = new StringBuilder();
					for (String error : errors)
					{
						sb.append(error);
						sb.append("\n");
					}
					final IMsgBL msgBL = Services.get(IMsgBL.class);
					final Properties ctx = InterfaceWrapperHelper.getCtx(qtyReportEvent);
					qtyReportEvent.setErrorMsg(msgBL.translate(ctx, sb.toString()));
					qtyReportEvent.setIsError(true);
				}
			}
			finally
			{
				InterfaceWrapperHelper.save(qtyReportEvent);
			}
		}
	}

	@Override
	public void reportWeekSupply(final SyncWeeklySupplyRequest request)
	{
		for (final SyncWeeklySupply syncWeeklySupply : request.getWeeklySupplies())
		{
			createWeekReportEvent(syncWeeklySupply);
		}
	}

	/**
	 * Attempts to load the {@link I_PMM_Product} from the given uuid.<br>
	 * If successful, then it also updates the <code>AD_Client_ID</code> and <code>AD_Org_ID</code> of the given <code>ctxProvider</code>.
	 *
	 * @param ctxProvider
	 * @param product_uuid
	 * @return
	 */
	private I_PMM_Product getPMM_ProductAndUpdateCtx(final IContextAware ctxProvider, final String product_uuid)
	{
		final int pmmProductId = SyncUUIDs.getPMM_Product_ID(product_uuid);
		if (pmmProductId > 0)
		{
			return null;
		}

		final I_PMM_Product pmmProduct = InterfaceWrapperHelper.create(ctxProvider.getCtx(), pmmProductId, I_PMM_Product.class, ctxProvider.getTrxName());

		// required because if the ctx contains #AD_Client_ID = 0, we might not get the term's C_Flatrate_DataEntries from the DAO further down,
		// and also the new even record needs to have the PMM_Product's AD_Client_ID and AD_Org_ID
		Env.setContext(ctxProvider.getCtx(), Env.CTXNAME_AD_Client_ID, pmmProduct.getAD_Client_ID());
		Env.setContext(ctxProvider.getCtx(), Env.CTXNAME_AD_Org_ID, pmmProduct.getAD_Org_ID());

		return pmmProduct;
	}

	private void createWeekReportEvent(final SyncWeeklySupply syncWeeklySupply)
	{
		final PlainContextAware cxtAware = PlainContextAware.createUsingThreadInheritedTransaction();

		final I_PMM_Product pmmProduct = getPMM_ProductAndUpdateCtx(cxtAware, syncWeeklySupply.getProduct_uuid());

		final int bpartnerId = SyncUUIDs.getC_BPartner_ID(syncWeeklySupply.getBpartner_uuid());
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(cxtAware.getCtx(), bpartnerId, I_C_BPartner.class, cxtAware.getTrxName());

		final I_PMM_WeekReport_Event event = InterfaceWrapperHelper.newInstance(I_PMM_WeekReport_Event.class, bpartner, true);

		event.setAD_Org_ID(bpartner.getAD_Org_ID());
		event.setC_BPartner(bpartner);
		event.setM_Product_ID(pmmProduct.getM_Product_ID());

		final Timestamp weekDate = TimeUtil.trunc(syncWeeklySupply.getWeekDay(), TimeUtil.TRUNC_WEEK);
		event.setWeekDate(weekDate);

		final String trend = syncWeeklySupply.getTrend();
		event.setPMM_Trend(trend);

		InterfaceWrapperHelper.save(event);
	}
}
