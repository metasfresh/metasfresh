package de.metas.procurement.base.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.pricing.api.IEditablePricingContext;
import org.adempiere.pricing.api.IPricingBL;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Check;
import org.adempiere.util.Pair;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.process.DocAction;
import org.compiere.util.Env;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.interfaces.I_C_BPartner_Product;
import de.metas.procurement.base.IServerSyncBL;
import de.metas.procurement.base.model.I_C_Flatrate_Conditions;
import de.metas.procurement.base.model.I_C_Flatrate_DataEntry;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.base.model.I_PMM_QtyReport_Event;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncContract;
import de.metas.procurement.sync.protocol.SyncContractLine;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncProductSuppliesRequest;
import de.metas.procurement.sync.protocol.SyncProductSupply;
import de.metas.procurement.sync.protocol.SyncUser;
import de.metas.procurement.sync.protocol.SyncWeeklySupplyRequest;
import de.metas.procurement.sync.util.UUIDs;
import de.metas.purchasing.api.IBPartnerProductDAO;

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
		try
		{
			return getAllBPartners0();
		}
		catch (Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	private List<SyncBPartner> getAllBPartners0() throws Exception
	{
		final LoadingCache<Integer, SyncBPartner> bpartner2sycBPartner = CacheBuilder.newBuilder()
				.build(new CacheLoader<Integer, SyncBPartner>()
				{
					@Override
					public SyncBPartner load(final Integer bpartnerId) throws Exception
					{
						return createSyncBPartner(bpartnerId);
					}
				});

		// retrieve the flatrate terms
		final Timestamp time = SystemTime.asTimestamp();
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Flatrate_Term> terms = queryBL.createQueryBuilder(I_C_Flatrate_Conditions.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(de.metas.flatrate.model.I_C_Flatrate_Conditions.COLUMNNAME_Type_Conditions, I_C_Flatrate_Conditions.TYPE_CONDITIONS_Procuremnt)
				.andCollectChildren(I_C_Flatrate_Term.COLUMN_C_Flatrate_Conditions_ID, I_C_Flatrate_Term.class)
				.addOnlyActiveRecordsFilter()
				// .addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_StartDate, Operator.LESS_OR_EQUAL, time) also add terms that start in future, so we already have them on the frontend-UI
				.addCompareFilter(I_C_Flatrate_Term.COLUMNNAME_EndDate, Operator.GREATER_OR_EQUAL, time)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_DocStatus, DocAction.STATUS_Completed)
				.addEqualsFilter(I_C_Flatrate_Term.COLUMNNAME_ContractStatus, X_C_Flatrate_Term.CONTRACTSTATUS_Laufend)
				.create()
				.list();

		final List<I_PMM_Product> allPmmProducts =
				createPMMProductQueryBuilder(time)
						.create()
						.list();

		final Multimap<Pair<Integer, Integer>, I_PMM_Product> key2product = MultimapBuilder.hashKeys().arrayListValues().build();
		for (final I_PMM_Product product : allPmmProducts)
		{
			key2product.put(
					new Pair<>(product.getC_BPartner_ID(), product.getM_Product_ID()),
					product);
		}

		// create the objects to send
		for (final I_C_Flatrate_Term term : terms)
		{
			final int bpartnerId = term.getDropShip_BPartner_ID();
			final SyncBPartner syncBPartner = bpartner2sycBPartner.get(bpartnerId);

			final SyncContract syncContract = new SyncContract();
			syncContract.setUuid(UUIDs.fromIdAsString(term.getC_Flatrate_Term_ID()));
			syncContract.setDateFrom(term.getStartDate());
			syncContract.setDateTo(term.getEndDate());

			syncBPartner.getContracts().add(syncContract);

			final I_M_Product product = term.getM_Product();

			final I_C_BPartner bPartner = term.getDropShip_BPartner();
			final List<SyncProduct> syncProducts = createSyncProducts(bPartner, product, key2product);
			for (final SyncProduct syncProduct : syncProducts)
			{
				final SyncContractLine syncContractLine = new SyncContractLine();
				syncContractLine.setUuid(syncContract.getUuid());
				syncContractLine.setProduct(syncProduct);

				syncContract.getContractLines().add(syncContractLine);
			}
		}

		return new ArrayList<>(bpartner2sycBPartner.asMap().values());
	}

	private IQueryBuilder<I_PMM_Product> createPMMProductQueryBuilder(final Timestamp time)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final ICompositeQueryFilter<I_PMM_Product> validToIsAfterOrNull = queryBL.createCompositeQueryFilter(I_PMM_Product.class)
				.setJoinOr()
				.addCompareFilter(I_PMM_Product.COLUMNNAME_ValidTo, Operator.GREATER_OR_EQUAL, time)
				.addEqualsFilter(I_PMM_Product.COLUMNNAME_ValidTo, null);

		final ICompositeQueryFilter<I_PMM_Product> validFromIsOrNull = queryBL.createCompositeQueryFilter(I_PMM_Product.class)
				.setJoinOr()
				.addCompareFilter(I_PMM_Product.COLUMNNAME_ValidFrom, Operator.LESS_OR_EQUAL, time)
				.addEqualsFilter(I_PMM_Product.COLUMNNAME_ValidFrom, null);

		final IQueryBuilder<I_PMM_Product> queryBuilder = queryBL.createQueryBuilder(I_PMM_Product.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.filter(validFromIsOrNull)
				.filter(validToIsAfterOrNull)
				.addNotEqualsFilter(I_PMM_Product.COLUMNNAME_M_HU_PI_Item_Product_ID, null)
				.addNotEqualsFilter(I_PMM_Product.COLUMNNAME_M_Warehouse_ID, null);
		return queryBuilder;
	}

	@Override
	public List<SyncProduct> getAllNotContractedProducts()
	{
		final Timestamp time = SystemTime.asTimestamp();

		final List<I_PMM_Product> allPmmProducts =
				createPMMProductQueryBuilder(time)
						.addEqualsFilter(I_PMM_Product.COLUMNNAME_C_BPartner_ID, null)
						.create()
						.list();

		final List<SyncProduct> syncProducts = new ArrayList<SyncProduct>();
		for (final I_PMM_Product pmmProduct : allPmmProducts)
		{
			final SyncProduct syncProduct = createSyncProduct(pmmProduct.getM_Product().getName(), pmmProduct);
			syncProducts.add(syncProduct);
		}

		return syncProducts;
	}

	private List<SyncProduct> createSyncProducts(
			final I_C_BPartner bPartner,
			final I_M_Product product,
			final Multimap<Pair<Integer, Integer>, I_PMM_Product> key2product)
	{
		final String productName = getProductName(bPartner, product);

		final List<SyncProduct> syncProducts = new ArrayList<>();

		final Collection<I_PMM_Product> pmmProducts = key2product.get(new Pair<>(bPartner.getC_BPartner_ID(), product.getM_Product_ID()));
		pmmProducts.addAll(key2product.get(new Pair<>(0, product.getM_Product_ID())));

		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			final SyncProduct syncProduct = createSyncProduct(productName, pmmProduct);
			syncProducts.add(syncProduct);
		}

		return syncProducts;
	}

	private SyncProduct createSyncProduct(final String productName, final I_PMM_Product pmmProduct)
	{
		final I_M_HU_PI_Item_Product piip = pmmProduct.getM_HU_PI_Item_Product();

		final SyncProduct syncProduct = new SyncProduct();
		syncProduct.setUuid(UUIDs.fromIdAsString(pmmProduct.getPMM_Product_ID()));
		syncProduct.setName(productName);
		syncProduct.setPackingInfo(piip.getDescription());
		syncProduct.setShared(pmmProduct.getC_BPartner_ID() <= 0); // share, unless it is assigned to a particular BPartner

		return syncProduct;
	}

	private String getProductName(final I_C_BPartner bPartner, final I_M_Product product)
	{
		final IBPartnerProductDAO bPartnerProductDAO = Services.get(IBPartnerProductDAO.class);

		final I_C_BPartner_Product bPartnerProduct = InterfaceWrapperHelper.create(
				bPartnerProductDAO.retrieveBPartnerProductAssociation(bPartner, product),
				I_C_BPartner_Product.class);

		final String productName;
		if (bPartnerProduct != null && !Check.isEmpty(bPartnerProduct.getProductName(), true))
		{
			productName = bPartnerProduct.getProductName();
		}
		else
		{
			productName = product.getName();
		}
		return productName;
	}

	private SyncBPartner createSyncBPartner(final int bpartnerId)
	{
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(Env.getCtx(), bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
		final SyncBPartner syncBPartner = new SyncBPartner();
		syncBPartner.setName(bPartner.getName());
		syncBPartner.setUuid(UUIDs.fromIdAsString(bPartner.getC_BPartner_ID()));

		final List<I_AD_User> contacts = bPartnerDAO.retrieveContacts(bPartner);
		for (final I_AD_User contact : contacts)
		{
			if (Check.isEmpty(contact.getEMail(), true))
			{
				continue;
			}
			final SyncUser syncUser = new SyncUser();
			syncUser.setLanguage(bPartner.getAD_Language());
			syncUser.setUuid(UUIDs.fromIdAsString(contact.getAD_User_ID()));
			syncUser.setEmail(contact.getEMail());
			syncUser.setPassword(contact.getPassword());

			syncBPartner.getUsers().add(syncUser);
		}
		return syncBPartner;
	}

	@Override
	public Response reportProductSupplies(final SyncProductSuppliesRequest request)
	{
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);

		final PlainContextAware initialContextProvider = new PlainContextAware(Env.getCtx());

		final boolean soTrx = false;

		final List<SyncProductSupply> productSupplies = request.getProductSupplies();
		for (final SyncProductSupply productSupply : productSupplies)
		{
			final List<String> errors = new ArrayList<>();

			final int bPartnerId = UUIDs.toId(productSupply.getBpartner_uuid());
			final I_C_BPartner bPartner = InterfaceWrapperHelper.create(initialContextProvider.getCtx(), bPartnerId, I_C_BPartner.class, initialContextProvider.getTrxName());

			final int pricingSystemId = bPartnerDAO.retrievePricingSystemId(initialContextProvider.getCtx(), bPartnerId, soTrx, initialContextProvider.getTrxName());

			final int pmmProductId = UUIDs.toId(productSupply.getProduct_uuid());
			final I_PMM_Product pmmProduct = InterfaceWrapperHelper.create(initialContextProvider.getCtx(), pmmProductId, I_PMM_Product.class,
					initialContextProvider.getTrxName());

			final I_M_HU_PI_Item_Product huPIItemProduct = pmmProduct.getM_HU_PI_Item_Product();

			final I_M_Product product = pmmProduct.getM_Product();

			final BigDecimal qtyPromised = productSupply.getQty().multiply(huPIItemProduct.getQty());

			final Timestamp datePromised = new Timestamp(productSupply.getDay().getTime());

			final List<I_C_BPartner_Location> shipToLocations = bPartnerDAO.retrieveBPartnerShipToLocations(bPartner);

			final I_PMM_QtyReport_Event qtyReportEvent = InterfaceWrapperHelper.newInstance(I_PMM_QtyReport_Event.class, bPartner);

			// first set the "general stuff"
			qtyReportEvent.setC_BPartner(bPartner);
			qtyReportEvent.setAD_Org_ID(bPartner.getAD_Org_ID()); // might be override in this method
			qtyReportEvent.setM_Product(product);
			qtyReportEvent.setM_HU_PI_Item_Product(huPIItemProduct);

			qtyReportEvent.setQtyPromised(qtyPromised);
			final int uomId = huPIItemProduct.getC_UOM_ID();
			qtyReportEvent.setC_UOM_ID(uomId);
			qtyReportEvent.setDatePromised(datePromised);
			qtyReportEvent.setM_PricingSystem_ID(pricingSystemId);

			qtyReportEvent.setM_Warehouse_ID(pmmProduct.getM_Warehouse_ID());

			if (Check.isEmpty(productSupply.getContractLine_uuid(), true))
			{
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
				else
				{
					final IPricingBL pricingBL = Services.get(IPricingBL.class);
					final IEditablePricingContext pricingCtx = pricingBL.createInitialContext(product.getM_Product_ID(), bPartnerId, uomId, qtyPromised, soTrx);
					pricingCtx.setM_PricingSystem_ID(pricingSystemId);
					pricingCtx.setPriceDate(datePromised);
					pricingCtx.setC_Country_ID(shipToLocations.get(0).getC_Location().getC_Country_ID());

					final IPricingResult pricingResult = pricingBL.calculatePrice(pricingCtx);
					if (!pricingResult.isCalculated())
					{
						errors.add("@Missing@ " + I_M_ProductPrice.COLUMNNAME_M_ProductPrice_ID + " " + pricingResult);

					}
					// these will be "empty" results, if the price was not calcualted
					qtyReportEvent.setM_PriceList_ID(pricingResult.getM_PriceList_ID());
					qtyReportEvent.setC_Currency_ID(pricingResult.getC_Currency_ID());
					qtyReportEvent.setPrice(pricingResult.getPriceStd());
				}
			}
			else
			{
				// import a contracted product
				final int flatrateTermId = UUIDs.toId(productSupply.getContractLine_uuid());
				final I_C_Flatrate_Term flatrateTerm = InterfaceWrapperHelper.create(initialContextProvider.getCtx(), flatrateTermId, I_C_Flatrate_Term.class, initialContextProvider.getTrxName());

				qtyReportEvent.setC_Flatrate_Term(flatrateTerm);
				qtyReportEvent.setC_Currency_ID(flatrateTerm.getC_Currency_ID());

				final List<I_C_Flatrate_DataEntry> dataEntries = InterfaceWrapperHelper.createList(
						flatrateDAO.retrieveDataEntries(flatrateTerm, datePromised, I_C_Flatrate_DataEntry.TYPE_Procurement_PeriodBased, true),
						I_C_Flatrate_DataEntry.class);
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
				}
				else
				{
					final BigDecimal price = Services.get(IUOMConversionBL.class).convertPrice(
							product,
							dataEntryForProduct.getFlatrateAmtPerUOM(),
							flatrateTerm.getC_UOM(), // this is the flatrateAmt's UOM
							huPIItemProduct.getC_UOM(), // this is the qtyReportEvent's UOM
							flatrateTerm.getC_Currency().getStdPrecision());

					qtyReportEvent.setPrice(price);
				}
			}
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

			InterfaceWrapperHelper.save(qtyReportEvent);
		}
		return Response.ok().build();
	}

	@Override
	public Response reportWeekSupply(final SyncWeeklySupplyRequest request)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
