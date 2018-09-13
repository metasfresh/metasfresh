package de.metas.bpartner.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.NumberUtils;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_M_Shipper;
import org.compiere.model.MOrgInfo;
import org.compiere.model.Query;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_AD_OrgInfo;
import de.metas.adempiere.model.I_AD_User;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.pricing.PricingSystemId;
import lombok.NonNull;

public class BPartnerDAO implements IBPartnerDAO
{
	private static final Logger logger = LogManager.getLogger(BPartnerDAO.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void save(@NonNull final I_C_BPartner bpartner)
	{
		InterfaceWrapperHelper.saveRecord(bpartner);
	}

	@Override
	public void save(@NonNull final I_C_BPartner_Location bpartnerLocation)
	{
		InterfaceWrapperHelper.saveRecord(bpartnerLocation);
	}

	@Override
	public void save(@NonNull final I_AD_User bpartnerContact)
	{
		Check.assume(bpartnerContact.getC_BPartner_ID() > 0, "C_BPartner_ID shall be set for {}", bpartnerContact);
		InterfaceWrapperHelper.saveRecord(bpartnerContact);
	}

	@Override
	public I_C_BPartner getById(final int bpartnerId)
	{
		return getById(BPartnerId.ofRepoId(bpartnerId), I_C_BPartner.class);
	}

	@Override
	public <T extends I_C_BPartner> T getById(final int bpartnerId, final Class<T> modelClass)
	{
		return getById(BPartnerId.ofRepoId(bpartnerId), modelClass);
	}

	@Override
	public I_C_BPartner getById(@NonNull final BPartnerId bpartnerId)
	{
		return getById(bpartnerId, I_C_BPartner.class);
	}

	@Override
	public <T extends I_C_BPartner> T getById(@NonNull final BPartnerId bpartnerId, final Class<T> modelClass)
	{
		final T bpartner = loadOutOfTrx(bpartnerId.getRepoId(), modelClass);
		return bpartner;
	}

	@Override
	public Optional<BPartnerContactId> getContactIdByExternalId(@NonNull final BPartnerId bpartnerId, @NonNull final String externalId)
	{
		return retrieveContacts(bpartnerId)
				.stream()
				.filter(contact -> externalId.equals(contact.getExternalId()))
				.findFirst()
				.map(record -> BPartnerContactId.ofRepoId(bpartnerId, record.getAD_User_ID()));
	}

	@Override
	public I_AD_User getContactById(@NonNull final BPartnerContactId contactId)
	{
		return retrieveContacts(contactId.getBpartnerId())
				.stream()
				.filter(contact -> contact.getAD_User_ID() == contactId.getRepoId())
				.findFirst()
				.orElse(null);
	}

	@Override
	public <T extends org.compiere.model.I_AD_User> T retrieveDefaultContactOrNull(final I_C_BPartner bPartner, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final String wc = org.compiere.model.I_AD_User.COLUMNNAME_IsDefaultContact + "=" + DB.TO_STRING("Y") + " AND " +
				org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID + "=?";

		final T result = new Query(ctx, org.compiere.model.I_AD_User.Table_Name, wc, trxName)
				.setParameters(bPartner.getC_BPartner_ID())
				.setOnlyActiveRecords(true)
				.setClient_ID()
				.firstOnly(clazz);

		return result;
	}

	@Override
	public Optional<BPartnerLocationId> getBPartnerLocationIdByExternalId(@NonNull final BPartnerId bpartnerId, @NonNull final String externalId)
	{
		return retrieveBPartnerLocations(bpartnerId)
				.stream()
				.filter(bpLocation -> externalId.equals(bpLocation.getExternalId()))
				.findFirst()
				.map(record -> BPartnerLocationId.ofRepoId(bpartnerId, record.getC_BPartner_Location_ID()));
	}

	@Override
	public I_C_BPartner_Location getBPartnerLocationById(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return retrieveBPartnerLocations(bpartnerLocationId.getBpartnerId())
				.stream()
				.filter(bpLocation -> bpLocation.getC_BPartner_Location_ID() == bpartnerLocationId.getRepoId())
				.findFirst()
				.orElse(null);
	}

	@Override
	public boolean exists(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return getBPartnerLocationById(bpartnerLocationId) != null;
	}

	@Override
	public List<I_C_BPartner_Location> retrieveBPartnerLocations(@NonNull final I_C_BPartner bpartner)
	{
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
	public List<I_C_BPartner_Location> retrieveBPartnerLocations(final int bpartnerId)
	{
		return retrieveBPartnerLocations(Env.getCtx(), bpartnerId, ITrx.TRXNAME_None);
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
				.listImmutable(I_C_BPartner_Location.class);
	}

	@Override
	public I_C_BPartner_Location getDefaultShipToLocation(final BPartnerId bpartnerId)
	{
		final List<I_C_BPartner_Location> bpLocations = retrieveBPartnerLocations(bpartnerId);
		if (bpLocations.isEmpty())
		{
			return null;
		}
		else if (bpLocations.size() == 1)
		{
			return bpLocations.get(0);
		}
		else
		{
			return bpLocations.stream()
					.filter(I_C_BPartner_Location::isShipTo)
					.sorted(Comparator.comparing(bpl -> bpl.isShipToDefault() ? 0 : 1))
					.findFirst()
					.orElse(null);
		}
	}

	@Override
	public int getDefaultShipToLocationCountryId(final BPartnerId bpartnerId)
	{
		final I_C_BPartner_Location bpl = getDefaultShipToLocation(bpartnerId);
		return bpl != null ? bpl.getC_Location().getC_Country_ID() : -1;
	}

	@Override
	@Cached(cacheName = I_AD_User.Table_Name + "#by#" + I_AD_User.COLUMNNAME_C_BPartner_ID)
	public List<I_AD_User> retrieveContacts(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class, ctx, trxName)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.orderBy(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID)
				.create()
				.listImmutable(I_AD_User.class);
	}

	@Override
	public List<I_AD_User> retrieveContacts(final I_C_BPartner bpartner)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bpartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bpartner);
		final int bpartnerId = bpartner.getC_BPartner_ID();
		return retrieveContacts(ctx, bpartnerId, trxName);
	}

	public List<I_AD_User> retrieveContacts(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveContacts(Env.getCtx(), bpartnerId.getRepoId(), ITrx.TRXNAME_None);
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

		final ICompositeQueryFilter<I_AD_User> filters = queryBuilder.getCompositeFilter();
		filters.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient(ctx);

		// #928
		// Only retrieve users that are default for sales or purchase (depending on the isSOTrx)
		// Sales
		if (isSOTrx)
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsSalesContact, true);
			queryBuilder.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsSalesContact_Default, true);

		}
		// Purchase
		else
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsPurchaseContact, true);
			queryBuilder.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsPurchaseContact_Default, true);
		}

		queryBuilder.orderBy()
				// #928: DefaultContact is no longer relevant in contact retrieval. The Sales and Purchase defaults are used instead
				// .addColumn(I_AD_User.COLUMNNAME_IsDefaultContact, Direction.Descending, Nulls.Last)
				.addColumn(org.compiere.model.I_AD_User.COLUMNNAME_AD_User_ID, Direction.Ascending, Nulls.Last);

		return queryBuilder.create().first();

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

	@Override
	public PricingSystemId retrievePricingSystemId(@NonNull final BPartnerId bPartnerId, final SOTrx soTrx)
	{
		return retrievePricingSystemId(Env.getCtx(), bPartnerId.getRepoId(), soTrx, ITrx.TRXNAME_None);
	}

	@Override
	public PricingSystemId retrievePricingSystemId(
			final Properties ctx,
			final int bPartnerId,
			final SOTrx soTrx,
			final String trxName)
	{
		final de.metas.interfaces.I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bPartnerId, de.metas.interfaces.I_C_BPartner.class, trxName);
		// try to set the pricing system from BPartner

		// metas: The method always retrieved SO-PricingSys. This caused errors in PO-Documents.
		final Integer bpPricingSysId;

		if (soTrx.isSales())
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
			return PricingSystemId.ofRepoId(bpPricingSysId);
		}

		final int bpGroupId = bPartner.getC_BP_Group_ID();
		if (bpGroupId > 0)
		{
			final I_C_BP_Group bpGroup = InterfaceWrapperHelper.create(ctx, bpGroupId, I_C_BP_Group.class, trxName);
			final Integer bpGroupPricingSysId;

			// metas: Same problem as above: The method always retrieved SO-PricingSys. This caused errors in
			// PO-Documents.
			if (soTrx.isSales())
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
				return PricingSystemId.ofRepoId(bpGroupPricingSysId);
			}
		}

		final int adOrgId = bPartner.getAD_Org_ID();
		if (adOrgId > 0 && soTrx.isSales())
		{
			final I_AD_OrgInfo orgInfo = InterfaceWrapperHelper.create(MOrgInfo.get(ctx, adOrgId, null), I_AD_OrgInfo.class);
			if (orgInfo.getM_PricingSystem_ID() > 0)
			{
				return PricingSystemId.ofRepoId(orgInfo.getM_PricingSystem_ID());
			}
		}

		logger.warn("bPartner={} has no pricing system id (soTrx={}); returning null", bPartner, soTrx);
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

	private I_M_Shipper retrieveDefaultShipper()
	{
		final Properties ctx = Env.getCtx();
		return new Query(ctx, I_M_Shipper.Table_Name, I_M_Shipper.COLUMNNAME_IsDefault + "=?", null)
				.setParameters(true)
				.setClient_ID()
				.firstOnly(de.metas.interfaces.I_M_Shipper.class);
	}

	@Override
	public boolean existsDefaultAddressInTable(final I_C_BPartner_Location address, final String trxName, final String columnName)
	{

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(columnName, true)
				.addEqualsFilter(I_C_BPartner_Location.COLUMN_C_BPartner_ID, address.getC_BPartner_ID())
				.create()
				.match();
	}

	@Override
	public boolean existsDefaultContactInTable(final de.metas.adempiere.model.I_AD_User user, final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_IsDefaultContact, true)
				.addEqualsFilter(org.compiere.model.I_AD_User.COLUMNNAME_C_BPartner_ID, user.getC_BPartner_ID())
				.addOnlyContextClient()
				.create()
				.match();
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

		queryBuilder.getCompositeFilter()
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

			queryBuilder.getCompositeFilter()
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

		queryBuilder.getCompositeFilter()
				.addEndsWithQueryFilter(I_C_BPartner.COLUMNNAME_Value, bpValueSuffixToFallbackFixed)
				.addOnlyContextClient(ctx)
				.addOnlyActiveRecordsFilter();

		final I_C_BPartner result = queryBuilder.create().firstOnly(I_C_BPartner.class);
		return result;
	}

	@Override
	public boolean hasMoreLocations(final Properties ctx, final int bpartnerId, final int excludeBPLocationId, final String trxName)
	{
		Check.assumeGreaterThanZero(bpartnerId, "bpartnerId");
		return retrieveBPartnerLocations(ctx, bpartnerId, trxName)
				.stream()
				.anyMatch(bpartnerLocation -> bpartnerLocation.getC_BPartner_Location_ID() != excludeBPLocationId);
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
				.addColumnDescending(I_C_BPartner_Location.COLUMNNAME_IsBillTo)
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

		final ICompositeQueryFilter<I_C_BPartner_Location> filters = queryBuilder.getCompositeFilter();
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

		final ICompositeQueryFilter<I_C_BPartner_Location> filters = queryBuilder.getCompositeFilter();
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

	@Override
	public Map<BPartnerId, Integer> retrieveAllDiscountSchemaIdsIndexedByBPartnerId(@NonNull final BPartnerType bpartnerType)
	{
		final String discountSchemaIdColumnName = getBPartnerDiscountSchemaColumnNameOrNull(bpartnerType);
		if (discountSchemaIdColumnName == null)
		{
			return ImmutableMap.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQuery<I_C_BP_Group> bpGroupIdQuery = queryBL
				.createQueryBuilderOutOfTrx(I_C_BP_Group.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(discountSchemaIdColumnName)
				.create();

		//
		// get bpartnerIds that don't have their own discount schema ID, but their C_BP_Group has one

		// first get the respective bpGroups
		final ImmutableMap<BPGroupId, Integer> groupId2discountId = bpGroupIdQuery
				.listDistinct(I_C_BP_Group.COLUMNNAME_C_BP_Group_ID, discountSchemaIdColumnName)
				.stream()
				.map(row -> GuavaCollectors.entry(
						BPGroupId.ofRepoId(NumberUtils.asInt(row.get(I_C_BP_Group.COLUMNNAME_C_BP_Group_ID), -1)),
						NumberUtils.asInt(row.get(discountSchemaIdColumnName), -1)))
				.collect(GuavaCollectors.toImmutableMap());

		// then get the bpartners that belong to those bpGroups *and* have no discountId of their own
		final HashMap<BPartnerId, Integer> bPartnerId2DiscountId = queryBL
				.createQueryBuilderOutOfTrx(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_BPartner.COLUMN_C_BP_Group_ID, groupId2discountId.keySet())
				.addEqualsFilter(discountSchemaIdColumnName, null) // they have no own discount schema!
				.create()
				.listDistinct(I_C_BPartner.COLUMNNAME_C_BPartner_ID, I_C_BPartner.COLUMNNAME_C_BP_Group_ID)
				.stream()
				.map(bPartner2GroupRow -> {

					final BPartnerId bPartnerId = BPartnerId.ofRepoId(NumberUtils.asInt(bPartner2GroupRow.get(I_C_BPartner.COLUMNNAME_C_BPartner_ID), -1));
					final BPGroupId bpGroupId = BPGroupId.ofRepoId(NumberUtils.asInt(bPartner2GroupRow.get(I_C_BPartner.COLUMNNAME_C_BP_Group_ID), -1));
					final Integer groupDiscountId = groupId2discountId.get(bpGroupId);

					return GuavaCollectors.entry(bPartnerId, groupDiscountId);
				})
				.collect(GuavaCollectors.toHashMap());

		// finally get bpartners that have their own discount schema
		final ImmutableMap<BPartnerId, Integer> partnerIdWithoutGroup2DiscountId = queryBL
				.createQueryBuilderOutOfTrx(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(discountSchemaIdColumnName)
				.create()
				.listDistinct(I_C_BPartner.COLUMNNAME_C_BPartner_ID, discountSchemaIdColumnName)
				.stream()
				.map(row -> GuavaCollectors.entry(
						BPartnerId.ofRepoId(NumberUtils.asInt(row.get(I_C_BPartner.COLUMNNAME_C_BPartner_ID), -1)),
						NumberUtils.asInt(row.get(discountSchemaIdColumnName), -1)))
				.collect(GuavaCollectors.toImmutableMap());

		// add/override the with the more specific bpartner-result onto the more general bpGroup-result
		final ImmutableSet<Entry<BPartnerId, Integer>> entrySet = partnerIdWithoutGroup2DiscountId.entrySet();
		for (final Entry<BPartnerId, Integer> entry : entrySet)
		{
			bPartnerId2DiscountId.put(entry.getKey(), entry.getValue());
		}

		return ImmutableMap.copyOf(bPartnerId2DiscountId);
	}

	private static final String getBPartnerDiscountSchemaColumnNameOrNull(final BPartnerType bpartnerType)
	{
		switch (bpartnerType)
		{
			case CUSTOMER:
				return I_C_BPartner.COLUMNNAME_M_DiscountSchema_ID;
			case VENDOR:
				return I_C_BPartner.COLUMNNAME_PO_DiscountSchema_ID;
			default:
				return null;
		}
	}

	@Override
	public BPartnerLocationId getBilltoDefaultLocationIdByBpartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveBPartnerLocations(bpartnerId)
				.stream()
				.filter(I_C_BPartner_Location::isBillToDefault)
				.findFirst()
				.map(bpLocation -> BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()), bpLocation.getC_BPartner_Location_ID()))
				.orElse(null);
	}

	@Override
	public BPartnerLocationId getShiptoDefaultLocationIdByBpartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveBPartnerLocations(bpartnerId)
				.stream()
				.filter(I_C_BPartner_Location::isShipToDefault)
				.findFirst()
				.map(bpLocation -> BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()), bpLocation.getC_BPartner_Location_ID()))
				.orElse(null);
	}

	@Override
	public String getBPartnerNameById(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = getById(bpartnerId);
		return bpartner.getName();
	}

	@Override
	public BPartnerId getBPartnerIdByValue(@NonNull final String bpartnerValue)
	{
		return getBPartnerIdByValueIfExists(bpartnerValue)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @C_BPartner_ID@: @Value@=" + bpartnerValue));
	}

	@Override
	public Optional<BPartnerId> getBPartnerIdByValueIfExists(@NonNull final String bpartnerValue)
	{
		final int bpartnerRepoId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMN_Value, bpartnerValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();

		return BPartnerId.optionalOfRepoId(bpartnerRepoId);
	}
}
