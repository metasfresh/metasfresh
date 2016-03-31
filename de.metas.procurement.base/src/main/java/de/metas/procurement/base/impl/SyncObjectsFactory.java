package de.metas.procurement.base.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.procurement.base.IPMMContractsDAO;
import de.metas.procurement.base.IPMMProductDAO;
import de.metas.procurement.base.model.I_AD_User;
import de.metas.procurement.base.model.I_PMM_Product;
import de.metas.procurement.sync.protocol.SyncBPartner;
import de.metas.procurement.sync.protocol.SyncContract;
import de.metas.procurement.sync.protocol.SyncContractLine;
import de.metas.procurement.sync.protocol.SyncProduct;
import de.metas.procurement.sync.protocol.SyncUser;

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

/**
 * Factory used to create all sync objects that we are sending from metasfresh server to webui server.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class SyncObjectsFactory
{
	public static final SyncObjectsFactory newFactory(final Date date)
	{
		return new SyncObjectsFactory(date);
	}

	public static final SyncObjectsFactory newFactory()
	{
		return new SyncObjectsFactory(SystemTime.asDayTimestamp());
	}

	//
	// services
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final IPMMContractsDAO pmmContractsDAO = Services.get(IPMMContractsDAO.class);
	private final IPMMProductDAO pmmProductDAO = Services.get(IPMMProductDAO.class);

	//
	// parameters
	private final Date date;

	//
	// state/cache

	private final Map<Integer, I_C_BPartner> bpartners = new HashMap<>();

	/** C_Partner_ID and M_Product_ID to {@link I_PMM_Product}s */
	private Multimap<IPair<Integer, Integer>, I_PMM_Product> _key2product = null;

	/** C_BPartner_ID to {@link I_C_Flatrate_Term}s */
	private final Multimap<Integer, I_C_Flatrate_Term> _bpartnerId2contract = MultimapBuilder.hashKeys().arrayListValues().build();
	private boolean _bpartnerId2contract_fullyLoaded = false;

	// Constants
	private static final Integer C_BPARTNER_ID_None = null;

	private SyncObjectsFactory(final Date date)
	{
		super();

		Check.assumeNotNull(date, "date not null");
		this.date = (Date)date.clone();
	}

	public List<SyncBPartner> createAllSyncBPartners()
	{
		final List<SyncBPartner> syncBPartners = new ArrayList<>();
		for (final int bpartnerId : getAllBPartnerIds())
		{
			final SyncBPartner syncBPartner = createSyncBPartner(bpartnerId);
			syncBPartners.add(syncBPartner);
		}

		return syncBPartners;
	}

	private SyncContract createSyncContract(final I_C_Flatrate_Term term)
	{
		final SyncContract syncContract = new SyncContract();
		syncContract.setUuid(SyncUUIDs.toUUIDString(term));
		syncContract.setDateFrom(term.getStartDate());
		syncContract.setDateTo(term.getEndDate());

		final int bpartnerId = term.getDropShip_BPartner_ID();
		final I_C_BPartner bpartner = getC_BPartnerById(bpartnerId);

		final I_M_Product product = term.getM_Product();
		final List<SyncProduct> syncProducts = createSyncProducts(bpartner, product);
		for (final SyncProduct syncProduct : syncProducts)
		{
			final SyncContractLine syncContractLine = new SyncContractLine();
			syncContractLine.setUuid(syncContract.getUuid());
			syncContractLine.setProduct(syncProduct);

			syncContract.getContractLines().add(syncContractLine);
		}

		return syncContract;
	}

	public boolean hasRunningContracts(final I_C_BPartner bpartner)
	{
		return pmmContractsDAO.hasRunningContract(bpartner);
	}

	public SyncBPartner createSyncBPartner(final int bpartnerId)
	{
		//
		// Create SyncBPartner with Users populated
		final SyncBPartner syncBPartner = createSyncBPartnerWithoutContracts(bpartnerId);

		//
		// Populate contracts
		syncBPartner.setSyncContracts(true);
		for (final I_C_Flatrate_Term term : getC_Flatrate_Terms_ForBPartnerId(bpartnerId))
		{
			final SyncContract syncContract = createSyncContract(term);
			if (syncContract == null)
			{
				continue;
			}
			syncBPartner.getContracts().add(syncContract);
		}

		return syncBPartner;
	}

	public SyncBPartner createSyncBPartnerWithoutContracts(final I_C_BPartner bpartner)
	{
		Check.assumeNotNull(bpartner, "bpartner not null");
		return createSyncBPartnerWithoutContracts(bpartner.getC_BPartner_ID());
	}

	private SyncBPartner createSyncBPartnerWithoutContracts(final int bpartnerId)
	{
		final I_C_BPartner bpartner = getC_BPartnerById(bpartnerId);
		final SyncBPartner syncBPartner = new SyncBPartner();
		syncBPartner.setName(bpartner.getName());
		syncBPartner.setUuid(SyncUUIDs.toUUIDString(bpartner));

		final String adLanguage = bpartner.getAD_Language();

		//
		// Fill Users
		final List<I_AD_User> contacts = InterfaceWrapperHelper.createList(
				bpartnerDAO.retrieveContacts(bpartner), I_AD_User.class);

		for (final I_AD_User contact : contacts)
		{
			final SyncUser syncUser = createSyncUser(contact, adLanguage);
			if (syncUser == null)
			{
				continue;
			}

			syncBPartner.getUsers().add(syncUser);
		}

		//
		// Contracts: we are not populating them here, so, for now we flag them as "do not sync"
		syncBPartner.setSyncContracts(false);

		return syncBPartner;
	}

	private SyncUser createSyncUser(final I_AD_User contact, final String adLanguage)
	{
		if (!contact.isActive() || !contact.isIsMFProcurementUser())
		{
			return null;
		}

		final String email = contact.getEMail();
		final String password = contact.getPassword();

		if (Check.isEmpty(email, true))
		{
			return null;
		}

		final SyncUser syncUser = new SyncUser();
		syncUser.setLanguage(adLanguage);
		syncUser.setUuid(SyncUUIDs.toUUIDString(contact));
		syncUser.setEmail(email);
		syncUser.setPassword(password);

		return syncUser;
	}

	private Multimap<Integer, I_C_Flatrate_Term> getC_Flatrate_Terms_IndexedByBPartnerId(final boolean fullyLoadedRequired)
	{
		if (fullyLoadedRequired && !_bpartnerId2contract_fullyLoaded)
		{
			_bpartnerId2contract.clear(); // clear all first

			final List<I_C_Flatrate_Term> terms = pmmContractsDAO.retrieveAllRunningContractsOnDate(date);
			for (final I_C_Flatrate_Term term : terms)
			{
				final int bpartnerId = term.getDropShip_BPartner_ID();
				_bpartnerId2contract.put(bpartnerId, term);
			}

			_bpartnerId2contract_fullyLoaded = true;
		}
		return _bpartnerId2contract;
	}

	private Set<Integer> getAllBPartnerIds()
	{
		final boolean fullyLoadedRequired = true;
		return getC_Flatrate_Terms_IndexedByBPartnerId(fullyLoadedRequired).keySet();
	}

	private I_C_BPartner getC_BPartnerById(final int bpartnerId)
	{
		I_C_BPartner bpartner = bpartners.get(bpartnerId);
		if (bpartner == null)
		{
			bpartner = InterfaceWrapperHelper.create(Env.getCtx(), bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_ThreadInherited);
			bpartners.put(bpartnerId, bpartner);
		}
		return bpartner;
	}

	private Collection<I_C_Flatrate_Term> getC_Flatrate_Terms_ForBPartnerId(final int bpartnerId)
	{
		final boolean fullyLoadedRequired = false;
		final Multimap<Integer, I_C_Flatrate_Term> bpartnerId2contract = getC_Flatrate_Terms_IndexedByBPartnerId(fullyLoadedRequired);
		if (!bpartnerId2contract.containsKey(bpartnerId))
		{
			final List<I_C_Flatrate_Term> contracts = pmmContractsDAO.retrieveRunningContractsOnDateForBPartner(date, bpartnerId);
			bpartnerId2contract.putAll(bpartnerId, contracts);
		}
		return bpartnerId2contract.get(bpartnerId);
	}

	private Multimap<IPair<Integer, Integer>, I_PMM_Product> getPMM_Products_IndexedByBPartnerIdAndProductId()
	{
		if (_key2product == null)
		{
			final List<I_PMM_Product> allPmmProducts = pmmProductDAO.retrieveAllPMMProductsValidOnDateQuery(date)
					.create()
					.list();
			final Multimap<IPair<Integer, Integer>, I_PMM_Product> key2product = MultimapBuilder.hashKeys().arrayListValues().build();
			for (final I_PMM_Product pmmProduct : allPmmProducts)
			{
				Integer bpartnerId = pmmProduct.getC_BPartner_ID();
				if (bpartnerId <= 0)
				{
					bpartnerId = C_BPARTNER_ID_None;
				}

				final IPair<Integer, Integer> key = createBPartnerAndProductKey(bpartnerId, pmmProduct.getM_Product_ID());
				key2product.put(key, pmmProduct);
			}
			_key2product = key2product;
		}
		return _key2product;
	}

	private List<I_PMM_Product> getPMM_ProductsFor(final I_C_BPartner bpartner, final I_M_Product product)
	{
		final List<I_PMM_Product> result = new ArrayList<>();

		final Multimap<IPair<Integer, Integer>, I_PMM_Product> key2pmmProduct = getPMM_Products_IndexedByBPartnerIdAndProductId();

		// Add the products linked to partner
		{
			final IPair<Integer, Integer> key = createBPartnerAndProductKey(bpartner.getC_BPartner_ID(), product.getM_Product_ID());
			result.addAll(key2pmmProduct.get(key));
		}

		// Add the products which are not linked to any partner
		{
			final IPair<Integer, Integer> key = createBPartnerAndProductKey(C_BPARTNER_ID_None, product.getM_Product_ID());
			result.addAll(key2pmmProduct.get(key));
		}

		return result;
	}

	private static final IPair<Integer, Integer> createBPartnerAndProductKey(final Integer bpartnerId, final int productId)
	{
		return ImmutablePair.of(bpartnerId, productId);
	}

	/**
	 * Creates one SyncProduct instance for each {@link I_PMM_Product} that references the given <code>product</code> and either the given <code>bPartner</code> or no bpartner at all.
	 *
	 * @param bpartner
	 * @param product
	 * @return
	 */
	private List<SyncProduct> createSyncProducts(final I_C_BPartner bpartner, final I_M_Product product)
	{
		if (product == null || bpartner == null)
		{
			// this should not, but *might* happen in case of a broken/inconsistent C_Flatrate_Term
			return Collections.emptyList();
		}

		final Collection<I_PMM_Product> pmmProducts = getPMM_ProductsFor(bpartner, product);

		final List<SyncProduct> syncProducts = new ArrayList<>(pmmProducts.size());
		for (final I_PMM_Product pmmProduct : pmmProducts)
		{
			final SyncProduct syncProduct = createSyncProduct(pmmProduct);
			syncProducts.add(syncProduct);
		}

		return syncProducts;
	}

	public SyncProduct createSyncProduct(final I_PMM_Product pmmProduct)
	{
		String productName = pmmProduct.getProductName();
		// Fallback to M_Product.Name (shall not happen)
		if (Check.isEmpty(productName, true))
		{
			final I_M_Product product = pmmProduct.getM_Product();
			productName = product == null ? null : product.getName();
		}

		final SyncProduct syncProduct = new SyncProduct();

		final boolean valid = pmmProduct.isActive()
				&& pmmProduct.getM_Warehouse_ID() > 0
				&& pmmProduct.getM_Product_ID() > 0
				&& pmmProduct.getM_HU_PI_Item_Product_ID() > 0;

		syncProduct.setUuid(SyncUUIDs.toUUIDString(pmmProduct));
		syncProduct.setName(productName);
		syncProduct.setPackingInfo(pmmProduct.getPackDescription());

		syncProduct.setShared(pmmProduct.getC_BPartner_ID() <= 0); // share, unless it is assigned to a particular BPartner
		syncProduct.setDeleted(!valid);

		return syncProduct;
	}

	public List<SyncProduct> createAllNotContractedSyncProducts()
	{
		final List<I_PMM_Product> allPmmProducts = Services.get(IPMMProductDAO.class).retrieveAllPMMProductsValidOnDateQuery(date)
				.addEqualsFilter(I_PMM_Product.COLUMNNAME_C_BPartner_ID, null)
				.create()
				.list();

		final List<SyncProduct> syncProducts = new ArrayList<SyncProduct>();
		for (final I_PMM_Product pmmProduct : allPmmProducts)
		{
			final SyncProduct syncProduct = createSyncProduct(pmmProduct);
			syncProducts.add(syncProduct);
		}
		return syncProducts;
	}
}
