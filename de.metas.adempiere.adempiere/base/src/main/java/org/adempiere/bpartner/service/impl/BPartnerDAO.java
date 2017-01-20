package org.adempiere.bpartner.service.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.bpartner.service.IBPartnerDAO;
import org.adempiere.bpartner.service.OrgHasNoBPartnerLinkException;
import org.adempiere.bpartner.service.ProductHasNoVendorException;
import org.adempiere.db.IDatabaseBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Greeting;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrgInfo;
import org.compiere.model.Query;
import org.compiere.model.X_C_BPartner;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.model.I_C_BPartner_Location;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;

public class BPartnerDAO implements IBPartnerDAO
{
	private static final Logger logger = LogManager.getLogger(BPartnerDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public <T extends I_C_BPartner> T retrieveBPartner(
			final Properties ctx,
			final String value,
			final Class<T> clazz,
			final String trxName)
	{
		Check.assume(value != null, "Param 'value' is not null");

		final String wc = I_C_BPartner.COLUMNNAME_Value + "=?";
		final T result = new Query(ctx, I_C_BPartner.Table_Name, wc, trxName)
				.setParameters(value)
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(clazz);

		if (result == null)
		{
			logger.debug("Didn't find bPartner with value '{}'. Returning null.", value);
			return null;
		}
		logger.debug("Returning bPartner with value '{}'", value);
		return result;
	}

	@Override
	public <T extends I_AD_User> T retrieveDefaultContactOrNull(
			final I_C_BPartner bPartner, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final String wc = I_AD_User.COLUMNNAME_IsDefaultContact + "=" + DB.TO_STRING("Y") + " AND " +
				org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID + "=?";

		final T result = new Query(ctx, org.compiere.model.I_AD_User.Table_Name, wc, trxName)
				.setParameters(bPartner.getC_BPartner_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(clazz);

		return result;
	}

	@Override
	public <T extends I_C_Location> List<T> retrieveLocation(
			final Properties ctx,
			final String address1, final String city, final String postal, final String countryCode,
			final Class<T> clazz,
			final String trxName)
	{
		final I_C_Country country = retrieveCountry(countryCode, trxName);

		final String wc = I_C_Location.COLUMNNAME_Address1 + "=? AND "
				+ I_C_Location.COLUMNNAME_City + "=? AND "
				+ I_C_Location.COLUMNNAME_Postal + "=? AND "
				+ I_C_Location.COLUMNNAME_C_Country_ID + "=?";

		final List<T> result = new Query(ctx, I_C_Location.Table_Name, wc, trxName)
				.setParameters(address1, city, postal, country.getC_Country_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.list(clazz);

		return result;
	}

	/**
	 * @throws an IllegalArgumentException if there is no country for the given country code.
	 */
	@Override
	public I_C_Country retrieveCountry(final String countryCode, final String trxName)
	{
		Check.assumeNotNull(countryCode, "countryCode not null");

		final I_C_Country country = Services.get(IQueryBL.class).createQueryBuilder(I_C_Country.class, Env.getCtx(), trxName)
				.addEqualsFilter(I_C_Country.COLUMNNAME_CountryCode, countryCode)
				.addOnlyActiveRecordsFilter()
				.create()
				.first(I_C_Country.class);

		if (country == null)
		{
			throw new AdempiereException("Unable to retrieve country for given country code '" + countryCode + "'");
		}

		return country;
	}

	@Override
	public I_C_BPartner_Location retrieveBPartnerLocation(final String name, final String trxName)
	{
		Check.assumeNotNull(name, "name not null");

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Location.class, Env.getCtx(), trxName)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_Name, name)
				.addOnlyActiveRecordsFilter()
				.create()
				.first(I_C_BPartner_Location.class);
	}

	private static final String SQL_DEFAULT_VENDOR =  //
	"SELECT bp.* " //
			+ " FROM C_BPartner bp "
			+ "   LEFT JOIN M_Product_PO p ON p.C_BPartner_ID = bp.C_BPartner_ID "
			+ " WHERE p.M_Product_ID=? " + " ORDER BY p.IsCurrentVendor DESC";

	@Override
	public I_C_BPartner retrieveDefaultVendor(final int productId, final String trxName) throws ProductHasNoVendorException
	{
		final IDatabaseBL db = Services.get(IDatabaseBL.class); // FIXME update this query to current standards

		final List<X_C_BPartner> result = db.retrieveList(BPartnerDAO.SQL_DEFAULT_VENDOR,
				new Object[] { productId }, X_C_BPartner.class, trxName);

		if (result.isEmpty())
		{
			final I_M_Product prod = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, trxName);
			throw new ProductHasNoVendorException("Unable to retrieve default vendor for product " + prod);
		}
		return InterfaceWrapperHelper.create(result.get(0), I_C_BPartner.class);
	}

	@Override
	public List<I_C_BPartner_Location> retrieveBPartnerLocations(final int partnerId, final boolean reload, final String trxName)
	{
		final Properties ctx = Env.getCtx();
		return retrieveBPartnerLocations(ctx, partnerId, trxName);
	}

	@Override
	public List<I_C_BPartner_Location> retrieveBPartnerLocations(final I_C_BPartner bpartner)
	{
		Check.assumeNotNull(bpartner, "bpartner not null");
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final int bpartnerId = bpartner.getC_BPartner_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);

		return retrieveBPartnerLocations(ctx, bpartnerId, trxName);
	}

	@Override
	public List<I_C_BPartner_Location> retrieveBPartnerShipToLocations(final I_C_BPartner bpartner)
	{
		final List<I_C_BPartner_Location> bpLocations = retrieveBPartnerLocations(bpartner);
		final List<I_C_BPartner_Location> shipToLocations = new ArrayList<>();
		for (final I_C_BPartner_Location bpl : bpLocations)
		{
			if (!bpl.isShipTo())
			{
				continue;
			}
			if (bpl.isShipToDefault())
			{
				shipToLocations.add(0, bpl);
			}
			else
			{
				shipToLocations.add(bpl);
			}
		}

		return shipToLocations;
	}

	@Override
	@Cached(cacheName = I_C_BPartner_Location.Table_Name + "#by#" + I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID)
	public List<I_C_BPartner_Location> retrieveBPartnerLocations(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
	{
		if (bpartnerId <= 0)
		{
			return Collections.emptyList();
		}
		final IQueryBuilder<I_C_BPartner_Location> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class, ctx, trxName)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);

		return queryBuilder
				.create()
				.list();
	}

	@Override
	public List<org.compiere.model.I_AD_User> retrieveContacts(final int partnerId, final boolean reload, final String trxName)
	{
		final MBPartner bPArtner = new MBPartner(Env.getCtx(), partnerId, trxName);
		final org.compiere.model.I_AD_User[] users = bPArtner.getContacts(reload);

		return Arrays.asList(users);
	}

	@Override
	@Cached(cacheName = I_AD_User.Table_Name + "#by#" + I_AD_User.COLUMNNAME_C_BPartner_ID)
	public List<I_AD_User> retrieveContacts(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
	{
		final IQueryBuilder<I_AD_User> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, ctx, trxName);

		final ICompositeQueryFilter<I_AD_User> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId);

		queryBuilder.orderBy()
				.addColumn(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID);

		return queryBuilder.create().list();
	}

	@Override
	public List<I_AD_User> retrieveContacts(final I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);
		final int bpartnerId = bpartner.getC_BPartner_ID();
		return retrieveContacts(ctx, bpartnerId, trxName);
	}

	@Override
	@Cached(cacheName = I_AD_User.Table_Name +
			"#" +
			I_AD_User.COLUMNNAME_C_BPartner_ID
			+ "#"
			+ I_AD_User.COLUMNNAME_IsSalesContact
			+ "#"
			+ I_AD_User.COLUMNNAME_IsPurchaseContact)
	public I_AD_User retrieveContact(
			@CacheCtx final Properties ctx,
			final int bpartnerId,
			final boolean isSOTrx,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_AD_User> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, ctx, trxName);

		final ICompositeQueryFilter<I_AD_User> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx);

		// Sales
		if (isSOTrx)
		{
			queryBuilder.orderBy()
					.addColumn(I_AD_User.COLUMNNAME_IsSalesContact, Direction.Descending, Nulls.Last);
		}
		// Purchase
		else
		{
			queryBuilder.orderBy()
					.addColumn(I_AD_User.COLUMNNAME_IsPurchaseContact, Direction.Descending, Nulls.Last);
		}

		queryBuilder.orderBy()
				.addColumn(I_AD_User.COLUMNNAME_IsDefaultContact, Direction.Descending, Nulls.Last)
				.addColumn(I_AD_User.COLUMNNAME_AD_User_ID, Direction.Ascending, Nulls.Last);

		return queryBuilder.create().first();

	}

	@Override
	public I_C_Greeting retrieveGreeting(final String name, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Greeting.class, Env.getCtx(), trxName)
				.addEqualsFilter(I_C_Country.COLUMNNAME_Name, name)
				.addOnlyActiveRecordsFilter()
				.create()
				.first(I_C_Greeting.class);
	}

	@Override
	public <T extends I_C_BPartner> T retrieveOrgBPartner(
			final Properties ctx,
			final int orgId,
			final Class<T> clazz,
			final String trxName)
	{
		final T result = Services.get(IQueryBL.class).createQueryBuilder(clazz, ctx, trxName)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_AD_OrgBP_ID, orgId)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(clazz);

		if (result == null)
		{
			throw new OrgHasNoBPartnerLinkException(orgId);
		}
		return result;
	}

	// metas: Added the Method retrievePriceListId.
	@Override
	public int retrievePriceListId(
			final Properties ctx,
			final int bPartnerId,
			final boolean soTrx,
			final String trxName)
	{

		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bPartnerId, I_C_BPartner.class, trxName);

		// Try to set PriceList from BPartner
		final Integer bpPriceListId;

		if (soTrx)
		{
			bpPriceListId = bPartner.getM_PriceList_ID();
		}
		else
		{
			bpPriceListId = bPartner.getPO_PriceList_ID();
		}

		if (bpPriceListId != null && bpPriceListId > 0)
		{
			logger.debug("Got M_PriceList_ID={} from bPartner={}", bpPriceListId, bPartner);
			return bpPriceListId;
		}
		// If there is no Pricelist in BPartner, Try to set PriceList from BPGroup

		final int bpGroupId = bPartner.getC_BP_Group_ID();
		if (bpGroupId > 0)
		{
			final de.metas.adempiere.model.I_C_BP_Group bpGroup = InterfaceWrapperHelper.create(ctx, bpGroupId, de.metas.adempiere.model.I_C_BP_Group.class, trxName);

			final Integer bpGroupPriceListId;

			if (soTrx)
			{
				bpGroupPriceListId = bpGroup.getM_PricingSystem_ID();
			}
			else
			{
				bpGroupPriceListId = bpGroup.getPO_PricingSystem_ID();
			}

			if (bpGroupPriceListId != null && bpGroupPriceListId > 0)
			{
				logger.debug("Got M_PricingSystem_ID={} from bpGroup={}", bpGroupPriceListId, bpGroup);
				return bpGroupPriceListId;
			}
		}

		logger.warn("bPartner={} has no pricelist id", bPartner);
		return 0;
	}

	// metas: end

	@Override
	public int retrievePricingSystemId(
			final Properties ctx,
			final int bPartnerId,
			final boolean soTrx,
			final String trxName)
	{
		final de.metas.interfaces.I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bPartnerId, de.metas.interfaces.I_C_BPartner.class, trxName);
		// try to set the pricing system from BPartner

		// metas: The method always retrieved SO-PricingSys. This caused errors in PO-Documents.
		final Integer bpPricingSysId;

		if (soTrx)
		{
			bpPricingSysId = bPartner.getM_PricingSystem_ID();
		}
		else
		{
			bpPricingSysId = bPartner.getPO_PricingSystem_ID();
		}
		// metas: end
		if (bpPricingSysId != null && bpPricingSysId > 0)
		{
			logger.debug("Got M_PricingSystem_ID={} from bPartner={}", bpPricingSysId, bPartner);
			return bpPricingSysId;
		}

		final int bpGroupId = bPartner.getC_BP_Group_ID();
		if (bpGroupId > 0)
		{
			final de.metas.adempiere.model.I_C_BP_Group bpGroup = InterfaceWrapperHelper.create(ctx, bpGroupId, de.metas.adempiere.model.I_C_BP_Group.class, trxName);
			final Integer bpGroupPricingSysId;

			// metas: Same problem as above: The method always retrieved SO-PricingSys. This caused errors in
			// PO-Documents.
			if (soTrx)
			{
				bpGroupPricingSysId = bpGroup.getM_PricingSystem_ID();
			}
			else
			{
				bpGroupPricingSysId = bpGroup.getPO_PricingSystem_ID();
			}
			// metas: end
			if (bpGroupPricingSysId != null && bpGroupPricingSysId > 0)
			{
				logger.debug("Got M_PricingSystem_ID={} from bpGroup={}", bpGroupPricingSysId, bpGroup);
				return bpGroupPricingSysId;
			}
		}

		final int adOrgId = bPartner.getAD_Org_ID();
		if (adOrgId > 0 && soTrx)
		{
			final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.create(MOrgInfo.get(ctx, adOrgId, null), I_AD_OrgInfo.class);
			if (orgInfo.getM_PricingSystem_ID() > 0)
			{
				return orgInfo.getM_PricingSystem_ID();
			}
		}

		logger.warn("bPartner={} has no pricing system id (soTrx={}); returning 0", bPartner, soTrx);
		return 0;
	}

	@Override
	public I_M_DiscountSchema retrieveDiscountSchemaOrNull(final I_C_BPartner bPartner, final boolean soTrx)
	{
		{
			final I_M_DiscountSchema discountSchema;
			if (soTrx)
			{
				discountSchema = bPartner.getM_DiscountSchema();
			}
			else
			{
				discountSchema = bPartner.getPO_DiscountSchema();
			}
			if (discountSchema != null && discountSchema.getM_DiscountSchema_ID() > 0)
			{
				return discountSchema; // we are done
			}
		}

		// didn't get the schema yet; now we try to get the discount schema from the C_BPartner's C_BP_Group
		final I_C_BP_Group bpGroup = bPartner.getC_BP_Group();
		if (bpGroup != null && bpGroup.getC_BP_Group_ID() > 0)
		{
			final I_M_DiscountSchema groupDiscountSchema;
			if (soTrx)
			{
				groupDiscountSchema = bpGroup.getM_DiscountSchema();
			}
			else
			{
				groupDiscountSchema = bpGroup.getPO_DiscountSchema();
			}
			if (groupDiscountSchema != null && groupDiscountSchema.getM_DiscountSchema_ID() > 0)
			{
				return groupDiscountSchema; // we are done
			}
		}

		return null;
	}

	@Override
	public I_M_Shipper retrieveShipper(final int bPartnerId, final String trxName)
	{
		if (bPartnerId <= 0)
		{
			return null;
		}

		final Properties ctx = Env.getCtx();
		final de.metas.interfaces.I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bPartnerId, de.metas.interfaces.I_C_BPartner.class, trxName);

		if (bPartner.getM_Shipper_ID() > 0)
		{
			return bPartner.getM_Shipper();
		}
		else
		{
			return retrieveDefaultShipper();
		}
	}

	@Override
	public I_M_Shipper retrieveDefaultShipper()
	{
		final Properties ctx = Env.getCtx();
		return new Query(ctx, I_M_Shipper.Table_Name, de.metas.interfaces.I_M_Shipper.COLUMNNAME_IsDefault + "=?", null)
				.setParameters(true)
				.setClient_ID()
				.firstOnly(de.metas.interfaces.I_M_Shipper.class);
	}

	@Override
	public boolean existsDefaultAddressInTable(final I_C_BPartner_Location address, final String trxName, final String columnName)
	{
		final String whereClause = columnName + " = ? AND "
				+ I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + " = ?";
		final int rows = new Query(Env.getCtx(), I_C_BPartner_Location.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(true, address.getC_BPartner_ID())
				.setClient_ID()
				.count();
		if (rows == 0)
		{
			return false;
		}
		return true;
	}

	@Override
	public boolean existsDefaultContactInTable(final de.metas.adempiere.model.I_AD_User user, final String trxName)
	{
		final String whereClause = de.metas.adempiere.model.I_AD_User.COLUMNNAME_IsDefaultContact + " = ? AND "
				+ org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID + " = ?";
		final int rows = new Query(Env.getCtx(), org.compiere.model.I_AD_User.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(true, user.getC_BPartner_ID())
				.setClient_ID()
				.count();
		if (0 == rows)
		{
			return false;
		}

		return true;
	}

	@Override
	public I_C_BPartner retrieveBPartnerByValue(final Properties ctx, final String value)
	{
		if (value == null)
		{
			return null;
		}

		final String valueFixed = value.trim();

		final IQueryBuilder<I_C_BPartner> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.getFilters()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_Value, valueFixed)
				.addOnlyContextClient(ctx)
				.addOnlyActiveRecordsFilter();

		final I_C_BPartner result = queryBuilder.create()
				.firstOnly(I_C_BPartner.class);

		return result;
	}

	@Override
	public I_C_BPartner retrieveBPartnerByValueOrSuffix(final Properties ctx,
			final String bpValue,
			final String bpValueSuffixToFallback)
	{
		//
		// try exact match
		if (!Check.isEmpty(bpValue, true))
		{
			final String bpValueFixed = bpValue.trim();

			final IQueryBuilder<I_C_BPartner> queryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(I_C_BPartner.class, ctx, ITrx.TRXNAME_None);

			queryBuilder.getFilters()
					.addEqualsFilter(I_C_BPartner.COLUMNNAME_Value, bpValueFixed)
					.addOnlyContextClient(ctx)
					.addOnlyActiveRecordsFilter();

			final I_C_BPartner result = queryBuilder.create().firstOnly(I_C_BPartner.class);
			if (result != null)
			{
				return result; // we are done here
			}
		}

		//
		// try fallback
		if (Check.isEmpty(bpValueSuffixToFallback, true))
		{
			return null;
		}
		final String bpValueSuffixToFallbackFixed = bpValueSuffixToFallback.trim();

		final IQueryBuilder<I_C_BPartner> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class, ctx, ITrx.TRXNAME_None);

		queryBuilder.getFilters()
				.addEndsWithQueryFilter(I_C_BPartner.COLUMNNAME_Value, bpValueSuffixToFallbackFixed)
				.addOnlyContextClient(ctx)
				.addOnlyActiveRecordsFilter();

		final I_C_BPartner result = queryBuilder.create().firstOnly(I_C_BPartner.class);
		return result;
	}

	@Override
	public boolean hasMoreLocations(final Properties ctx, final int bpartnerId, final int excludeBPLocationId, final String trxName)
	{
		Check.assume(bpartnerId > 0, "bpartnerId > 0");

		final String whereClause = I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "=?"
				+ " AND " + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID + "<>?";
		return new Query(ctx, I_C_BPartner_Location.Table_Name, whereClause, trxName)
				.setParameters(bpartnerId, excludeBPLocationId)
				.match();
	}

	@Override
	public I_C_BP_Relation retrieveBillBPartnerRelationFirstEncountered(final Object contextProvider, final I_C_BPartner partner, final I_C_BPartner_Location location)
	{
		Check.assumeNotNull(partner, "partner not null");

		final IQueryBuilder<I_C_BP_Relation> queryBuilder = queryBL.createQueryBuilder(I_C_BP_Relation.class, contextProvider);

		//
		// Filter by partner
		queryBuilder.addEqualsFilter(org.compiere.model.I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID());

		//
		// Filter by location or null (accept bill relations with no location)
		final Integer partnerLocationId;
		if (location != null)
		{
			partnerLocationId = location.getC_BPartner_Location_ID();
		}
		else
		{
			partnerLocationId = null;
		}
		queryBuilder.addInArrayOrAllFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_Location_ID, partnerLocationId, null);
		queryBuilder.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsBillTo, true);

		final IQuery<I_C_BP_Relation> query = queryBuilder
				.addOnlyActiveRecordsFilter()
				.create();

		//
		// Order by BillTo DESC
		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_C_BPartner_Location.class)
				.addColumn(I_C_BPartner_Location.COLUMNNAME_IsBillTo, false) // descending
				.createQueryOrderBy();
		query.setOrderBy(orderBy);

		return query.first(I_C_BP_Relation.class);
	}

	@Override
	@Cached(cacheName = I_C_BPartner_Location.Table_Name + "#by#" + I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID + "#" + I_C_BPartner_Location.COLUMNNAME_IsBillToDefault)
	public I_C_BPartner_Location retrieveBillToLocation(
			@CacheCtx final Properties ctx,
			final int bPartnerId,
			final boolean alsoTryBilltoRelation,
			@CacheTrx final String trxName)
	{
		final IQueryBuilder<I_C_BPartner_Location> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class, ctx, trxName);

		final ICompositeQueryFilter<I_C_BPartner_Location> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartnerId);
		filters.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_IsBillTo, true);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner_Location.COLUMNNAME_IsBillToDefault, Direction.Descending, Nulls.Last);

		final I_C_BPartner_Location ownBillToLocation = queryBuilder
				.create()
				.first();
		if (!alsoTryBilltoRelation || ownBillToLocation != null)
		{
			// !alsoTryBilltoRelation => we return whatever we got here (null or not)
			// ownBillToLocation != null => we return the not-null location we found
			return ownBillToLocation;
		}

		final IQueryBuilder<I_C_BP_Relation> bpRelationQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Relation.class, ctx, trxName)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bPartnerId)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_IsBillTo, true)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID);

		final I_C_BP_Relation billtoRelation = bpRelationQueryBuilder
				.create()
				.firstOnly(I_C_BP_Relation.class); // just added an UC
		if (billtoRelation != null)
		{
			return InterfaceWrapperHelper.create(billtoRelation.getC_BPartnerRelation_Location(), I_C_BPartner_Location.class);
		}
		return null;
	}

	@Override
	public I_C_BPartner_Location retrieveShipToLocation(
			final Properties ctx,
			final int bPartnerId,
			final String trxName)
	{
		final IQueryBuilder<I_C_BPartner_Location> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class, ctx, trxName);

		final ICompositeQueryFilter<I_C_BPartner_Location> filters = queryBuilder.getFilters();
		filters.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bPartnerId);
		filters.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_IsShipTo, true);
		filters.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner_Location.COLUMNNAME_IsShipToDefault, Direction.Descending, Nulls.Last)
				// FRESH-339: In case there is no shipToDefault set, select the last created location
				.addColumn(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, Direction.Descending, Nulls.Last);

		return queryBuilder.create()
				.first();
	}
}
