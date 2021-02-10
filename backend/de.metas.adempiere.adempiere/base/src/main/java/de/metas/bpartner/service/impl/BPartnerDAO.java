/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.bpartner.service.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.BPartnerType;
import de.metas.bpartner.GLN;
import de.metas.bpartner.GeographicalCoordinatesWithBPartnerLocationId;
import de.metas.bpartner.service.BPRelation;
import de.metas.bpartner.service.BPartnerContactQuery;
import de.metas.bpartner.service.BPartnerIdNotFoundException;
import de.metas.bpartner.service.BPartnerPrintFormat;
import de.metas.bpartner.service.BPartnerPrintFormatMap;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerDAO.BPartnerLocationQuery.Type;
import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.document.DocTypeId;
import de.metas.email.EMailAddress;
import de.metas.i18n.AdMessageKey;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.location.ILocationDAO;
import de.metas.location.LocationId;
import de.metas.location.geocoding.GeographicalCoordinates;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.organization.OrgInfo;
import de.metas.pricing.PricingSystemId;
import de.metas.report.PrintFormatId;
import de.metas.shipping.IShipperDAO;
import de.metas.shipping.ShipperId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_Group;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Location;
import org.compiere.model.X_C_Location;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

import static de.metas.util.Check.assumeNotNull;
import static de.metas.util.Check.isEmpty;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class BPartnerDAO implements IBPartnerDAO
{
	private static final Logger logger = LogManager.getLogger(BPartnerDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final GLNLoadingCache glnsLoadingCache = new GLNLoadingCache();
	private final CCache<BPartnerId, BPartnerPrintFormatMap> printFormatsCache = CCache.<BPartnerId, BPartnerPrintFormatMap>builder()
			.tableName(I_C_BP_PrintFormat.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.build();

	private static final AdMessageKey MSG_ADDRESS_INACTIVE = AdMessageKey.of("webui.salesorder.clone.inactivelocation");

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
		// NOTE: generally, don't load out of trx unless knowing the context that therefore knowing that it's OK.
		// You *don not* know that the C_BPartner wasn't just created and the DB was not yet committed.
		// Therefore, you can't assume that loading out of trx will be OK.
		final T bpartner = load(bpartnerId.getRepoId(), modelClass);
		return bpartner;
	}

	public List<I_C_BPartner> getByIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return loadByRepoIdAwaresOutOfTrx(bpartnerIds, I_C_BPartner.class);
	}

	@Override
	public Optional<BPartnerId> getBPartnerIdByValue(@NonNull final String value)
	{
		final String valueFixed = value.trim();

		final BPartnerId bpartnerId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_Value, valueFixed)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(BPartnerId::ofRepoIdOrNull);

		return Optional.ofNullable(bpartnerId);
	}

	@Override
	public Optional<BPartnerId> getBPartnerIdBySalesPartnerCode(
			@NonNull final String salesPartnerCode,
			@NonNull final Set<OrgId> onlyOrgIds)
	{
		if (isEmpty(salesPartnerCode, true))
		{
			return Optional.empty();
		}
		final BPartnerId bpartnerId = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_SalesPartnerCode, salesPartnerCode.trim())
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsSalesRep, true)
				.addInArrayOrAllFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, onlyOrgIds)
				.create()
				.firstIdOnly(BPartnerId::ofRepoIdOrNull);
		return Optional.ofNullable(bpartnerId);
	}

	@Override
	public Optional<BPartnerId> getBPartnerIdByExternalId(@NonNull final ExternalId externalId)
	{
		final BPartnerId bpartnerId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_ExternalId, externalId.getValue())
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(BPartnerId::ofRepoIdOrNull);
		return Optional.ofNullable(bpartnerId);
	}

	@Override
	public I_C_BPartner getByIdInTrx(@NonNull final BPartnerId bpartnerId)
	{
		return load(bpartnerId, I_C_BPartner.class);
	}

	@Override
	public Optional<BPartnerContactId> getContactIdByExternalId(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ExternalId externalId)
	{
		return retrieveContacts(bpartnerId)
				.stream()
				.filter(contact -> Objects.equals(externalId.getValue(), contact.getExternalId()))
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
	public I_AD_User getContactByIdInTrx(@NonNull final BPartnerContactId contactId)
	{
		return retrieveContactsInTrx(contactId.getBpartnerId())
				.stream()
				.filter(contact -> contact.getAD_User_ID() == contactId.getRepoId())
				.findFirst()
				.orElse(null);
	}

	@Override
	public <T extends I_AD_User> T getContactById(BPartnerContactId contactId, Class<T> modelClass)
	{
		return getContactById(contactId, modelClass);
	}

	@Override
	@NonNull
	public EMailAddress getContactEMail(@NonNull final BPartnerContactId contactId)
	{
		final I_AD_User contact = getContactById(contactId);
		if (contact == null)
		{
			throw new AdempiereException("@NotFound@ " + contactId);
		}

		final EMailAddress contactEmail = EMailAddress.ofNullableString(contact.getEMail());
		if (contactEmail == null)
		{
			throw new AdempiereException("Contact has no email: " + contact.getName());
		}
		return contactEmail;
	}

	@Override
	public boolean hasEmailAddress(@NonNull final BPartnerContactId contactId)
	{
		final I_AD_User contact = getContactById(contactId);
		if (contact == null)
		{
			throw new AdempiereException("@NotFound@ " + contactId);
		}

		final EMailAddress contactEmail = EMailAddress.ofNullableString(contact.getEMail());
		return null != contactEmail;
	}

	@Override
	public <T extends org.compiere.model.I_AD_User> T retrieveDefaultContactOrNull(final I_C_BPartner bPartner, final Class<T> clazz)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(bPartner);
		final String trxName = InterfaceWrapperHelper.getTrxName(bPartner);

		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bPartner.getC_BPartner_ID());

		return retrieveDefaultContactOrNull(ctx, bpartnerId, trxName, clazz);
	}

	@Override
	public Optional<UserId> getDefaultContactId(@NonNull final BPartnerId bpartnerId)
	{
		final I_AD_User defaultContact = retrieveDefaultContactOrNull(Env.getCtx(), bpartnerId, ITrx.TRXNAME_None, I_AD_User.class);
		return defaultContact != null
				? Optional.of(UserId.ofRepoId(defaultContact.getAD_User_ID()))
				: Optional.empty();
	}

	private <T extends I_AD_User> T retrieveDefaultContactOrNull(final Properties ctx, final BPartnerId bpartnerId, final String trxName, final Class<T> clazz)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_User.class, ctx, trxName)
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_AD_User.COLUMNNAME_IsDefaultContact, true)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnly(clazz);
	}

	@Override
	public Optional<BPartnerLocationId> getBPartnerLocationIdByExternalId(
			@NonNull final BPartnerId bpartnerId,
			@NonNull final ExternalId externalId)
	{
		return retrieveBPartnerLocations(bpartnerId)
				.stream()
				.filter(bpLocation -> Objects.equals(externalId.getValue(), bpLocation.getExternalId()))
				.findFirst()
				.map(record -> BPartnerLocationId.ofRepoId(bpartnerId, record.getC_BPartner_Location_ID()));
	}

	@Override
	public Optional<BPartnerLocationId> getBPartnerLocationIdByGln(@NonNull final BPartnerId bpartnerId, @NonNull final GLN gln)
	{
		final String glnCode = gln.getCode();

		return retrieveBPartnerLocations(bpartnerId)
				.stream()
				.filter(bpLocation -> glnCode.equals(bpLocation.getGLN()))
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
	public I_C_BPartner_Location getBPartnerLocationByIdEvenInactive(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return retrieveBPartnerLocations(bpartnerLocationId.getBpartnerId(), true)
				.stream()
				.filter(bpLocation -> bpLocation.getC_BPartner_Location_ID() == bpartnerLocationId.getRepoId())
				.findFirst()
				.orElse(null);
	}

	@Override
	public I_C_BPartner_Location getBPartnerLocationByIdInTrx(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		return retrieveBPartnerLocationsInTrx(bpartnerLocationId.getBpartnerId())
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
		final int bpartnerId = bpartner.getC_BPartner_ID();

		return retrieveBPartnerLocations(BPartnerId.ofRepoId(bpartnerId));
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
	public ImmutableList<I_C_BPartner_Location> retrieveBPartnerLocations(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveBPartnerLocations(bpartnerId, false);
	}

	@Override
	@Cached(cacheName = I_C_BPartner_Location.Table_Name + "#by#" + I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID)
	public ImmutableList<I_C_BPartner_Location> retrieveBPartnerLocations(@NonNull final BPartnerId bpartnerId, final boolean includeInactive)
	{
		final IQueryBuilder<I_C_BPartner_Location> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId);

		if (!includeInactive)
		{
			queryBuilder.addOnlyActiveRecordsFilter();
		}

		queryBuilder.orderBy()
				.addColumn(I_C_BPartner_Location.COLUMNNAME_IsActive);

		return queryBuilder
				.create()
				.listImmutable(I_C_BPartner_Location.class);
	}

	@Override
	public I_C_BPartner_Location getDefaultShipToLocation(@NonNull final BPartnerId bpartnerId)
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
	public Set<CountryId> retrieveBPartnerLocationCountryIds(@NonNull final BPartnerId bpartnerId)
	{
		final Set<LocationId> locationIds = retrieveBPartnerLocations(bpartnerId)
				.stream()
				.map(I_C_BPartner_Location::getC_Location_ID)
				.map(LocationId::ofRepoIdOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final ILocationDAO locationRepos = Services.get(ILocationDAO.class);
		return locationRepos.getByIds(locationIds)
				.stream()
				.map(I_C_Location::getC_Country_ID)
				.map(CountryId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public CountryId retrieveBPartnerLocationCountryId(@NonNull final BPartnerLocationId bpLocationId)
	{
		final I_C_BPartner_Location bpLocation = getBPartnerLocationById(bpLocationId);
		if (bpLocation == null)
		{
			throw new AdempiereException(MSG_ADDRESS_INACTIVE).markAsUserValidationError();
		}
		final LocationId locationId = LocationId.ofRepoId(bpLocation.getC_Location_ID());

		final ILocationDAO locationRepos = Services.get(ILocationDAO.class);
		return locationRepos.getCountryIdByLocationId(locationId);
	}

	@Override
	public CountryId retrieveBPartnerLocationCountryIdInTrx(@NonNull final BPartnerLocationId bpLocationId)
	{
		final I_C_BPartner_Location bpLocation = getBPartnerLocationByIdInTrx(bpLocationId);
		final LocationId locationId = LocationId.ofRepoId(bpLocation.getC_Location_ID());

		final ILocationDAO locationRepos = Services.get(ILocationDAO.class);
		return locationRepos.getCountryIdByLocationId(locationId);
	}

	@Override
	public CountryId getDefaultShipToLocationCountryIdOrNull(final BPartnerId bpartnerId)
	{
		final I_C_BPartner_Location bpl = getDefaultShipToLocation(bpartnerId);
		return bpl != null
				? CountryId.ofRepoId(bpl.getC_Location().getC_Country_ID())
				: null;
	}

	@Override
	public CountryId getBPartnerLocationCountryId(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		final I_C_BPartner_Location bpLocation = getBPartnerLocationByIdEvenInactive(bpartnerLocationId);
		return CountryId.ofRepoId(bpLocation.getC_Location().getC_Country_ID());
	}

	@Override
	@Cached(cacheName = I_AD_User.Table_Name + "#by#" + I_AD_User.COLUMNNAME_C_BPartner_ID)
	public ImmutableList<I_AD_User> retrieveContacts(@CacheCtx final Properties ctx, final int bpartnerId, @CacheTrx final String trxName)
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

	@Override
	public ImmutableList<I_AD_User> retrieveContacts(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveContacts(Env.getCtx(), bpartnerId.getRepoId(), ITrx.TRXNAME_None);
	}

	public ImmutableList<I_AD_User> retrieveContactsInTrx(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveContacts(Env.getCtx(), bpartnerId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
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
	public PricingSystemId retrievePricingSystemIdOrNull(@NonNull final BPartnerId bpartnerId, final SOTrx soTrx)
	{
		return retrievePricingSystemIdOrNull(bpartnerId, soTrx, ITrx.TRXNAME_None);
	}

	@Override
	public PricingSystemId retrievePricingSystemIdOrNullInTrx(@NonNull final BPartnerId bpartnerId, final SOTrx soTrx)
	{
		return retrievePricingSystemIdOrNull(bpartnerId, soTrx, ITrx.TRXNAME_ThreadInherited);
	}

	/**
	 * Returns the <code>M_PricingSystem_ID</code> to use for a given bPartner.
	 *
	 * @param bpartnerId the ID of the BPartner for which we need the pricing system id
	 * @param soTrx      <ul>
	 *                   <li>if <code>true</code>, then the method first checks <code>C_BPartner.M_PricingSystem_ID</code> , then (if the BPartner has a C_BP_Group_ID) in
	 *                   <code>C_BP_Group.M_PricingSystem_ID</code> and finally (if the C_BPArtner has a AD_Org_ID>0) in <code>AD_OrgInfo.M_PricingSystem_ID</code></li>
	 *                   <li>if <code>false</code></li>, then the method first checks <code>C_BPartner.PO_PricingSystem_ID</code>, then (if the BPartner has a C_BP_Group_ID!) in
	 *                   <code>C_BP_Group.PO_PricingSystem_ID</code>. Note that <code>AD_OrgInfo</code> has currently no <code>PO_PricingSystem_ID</code> column.
	 *                   </ul>
	 */
	private PricingSystemId retrievePricingSystemIdOrNull(
			@NonNull final BPartnerId bpartnerId,
			final SOTrx soTrx,
			final String trxName)
	{
		final Properties ctx = Env.getCtx();
		final I_C_BPartner bPartner = InterfaceWrapperHelper.create(ctx, bpartnerId.getRepoId(), I_C_BPartner.class, trxName);
		if (bPartner == null)
		{
			throw new AdempiereException("No BPartner found for " + bpartnerId);
		}

		final Integer bpPricingSysId;
		if (soTrx.isSales())
		{
			bpPricingSysId = bPartner.getM_PricingSystem_ID();
		}
		else
		{
			bpPricingSysId = bPartner.getPO_PricingSystem_ID();
		}

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

		final OrgId adOrgId = OrgId.ofRepoIdOrAny(bPartner.getAD_Org_ID());
		if (adOrgId.isRegular() && soTrx.isSales())
		{
			final OrgInfo orgInfo = Services.get(IOrgDAO.class).getOrgInfoById(adOrgId);
			if (orgInfo.getPricingSystemId() != null)
			{
				return orgInfo.getPricingSystemId();
			}
		}

		logger.warn("bPartner={} has no pricing system id (soTrx={}); returning null", bPartner, soTrx);
		return null;
	}

	@Override
	public ShipperId getShipperId(final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = getById(bpartnerId);
		final ShipperId shipperId = ShipperId.ofRepoIdOrNull(bpartner.getM_Shipper_ID());
		if (shipperId != null)
		{
			return shipperId;
		}
		else
		{
			return Services.get(IShipperDAO.class).getDefault(ClientId.ofRepoId(bpartner.getAD_Client_ID()));
		}
	}

	@Override
	public boolean existsDefaultAddressInTable(final I_C_BPartner_Location address, final String trxName, final String columnName)
	{

		return Services.get(IQueryBL.class).createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addEqualsFilter(columnName, true)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, address.getC_BPartner_ID())
				.create()
				.anyMatch();
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
				.anyMatch();
	}

	@Nullable
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
		return retrieveBPartnerLocations(BPartnerId.ofRepoId(bpartnerId))
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

	@Nullable
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
			final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoId(billtoRelation.getC_BPartnerRelation_ID(), billtoRelation.getC_BPartnerRelation_Location_ID());
			final I_C_BPartner_Location partnerRelationLocation = getBPartnerLocationById(bPartnerLocationId);
			return InterfaceWrapperHelper.create(partnerRelationLocation, I_C_BPartner_Location.class);
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
		return retrieveBPartnerLocationsInTrx(bpartnerId)
				.stream()
				.filter(I_C_BPartner_Location::isBillToDefault)
				.findFirst()
				.map(bpLocation -> BPartnerLocationId.ofRepoId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()), bpLocation.getC_BPartner_Location_ID()))
				.orElse(null);
	}

	private List<I_C_BPartner_Location> retrieveBPartnerLocationsInTrx(final BPartnerId bpartnerId)
	{
		return retrieveBPartnerLocations(bpartnerId);
	}

	@Override
	public BPartnerLocationId getShiptoDefaultLocationIdByBpartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return retrieveBPartnerLocations(bpartnerId)
				.stream()
				.filter(I_C_BPartner_Location::isShipToDefault) // we have only one location with isShipToDefault=Y
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
	public List<String> getBPartnerNamesByIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		final ImmutableMap<BPartnerId, String> bpartnerNamesById = getByIds(bpartnerIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						bpartner -> BPartnerId.ofRepoId(bpartner.getC_BPartner_ID()),
						bpartner -> bpartner.getName()));

		return bpartnerIds.stream()
				.map(bpartnerId -> {
					final String name = bpartnerNamesById.get(bpartnerId);
					return name != null ? name : "<" + bpartnerId.getRepoId() + ">";
				})
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public I_C_BPartner_Location retrieveBPartnerLocation(@NonNull final BPartnerLocationQuery query)
	{
		final BPartnerLocationId bPartnerLocationId = retrieveBPartnerLocationId(query);
		if (bPartnerLocationId == null)
		{
			return null;
		}
		return loadOutOfTrx(bPartnerLocationId, I_C_BPartner_Location.class);
	}

	@Override
	public BPartnerLocationId retrieveBPartnerLocationId(@NonNull final BPartnerLocationQuery query)
	{
		final String typeFilterColumnName = getFilterColumnNameForType(query.getType());

		final BPartnerId bpartnerId = query.getBpartnerId();

		final IQueryBuilder<I_C_BPartner_Location> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner_Location.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartnerId);
		if (query.isApplyTypeStrictly())
		{
			queryBuilder.addEqualsFilter(typeFilterColumnName, true);
		}
		else
		{
			queryBuilder.orderByDescending(typeFilterColumnName); // "Y" first
		}

		appendLocationChecks(query, queryBuilder);

		getOrderByColumnNameForType(query.getType()) // order by e.g. "IsDefaultShipToLocation"
				.ifPresent(queryBuilder::orderByDescending);

		queryBuilder.orderBy(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID);

		final I_C_BPartner_Location ownToLocation = queryBuilder
				.create()
				.first();

		// if we do not need to check relation, return location already found or null
		if (query.getRelationBPartnerLocationId() == null)
		{
			return createLocationIdOrNull(bpartnerId, ownToLocation);
		}

		final IQueryBuilder<I_C_BP_Relation> bpRelationQueryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_Relation.class)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addEqualsFilter(I_C_BP_Relation.COLUMNNAME_C_BPartner_Location_ID, query.getRelationBPartnerLocationId())
				.addEqualsFilter(typeFilterColumnName, true)
				.addOnlyActiveRecordsFilter();

		queryBuilder.orderBy()
				.addColumn(I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID);

		final Optional<BPartnerLocationId> relBPLocationId = bpRelationQueryBuilder
				.create()
				.stream()
				.map(bpRelationRecord -> ofRelationRecord(bpRelationRecord))
				.findFirst()
				.map(bpRelation -> bpRelation.getTargetBPLocationId());

		if (relBPLocationId.isPresent())
		{
			return BPartnerLocationId.ofRepoId(query.getBpartnerId(), relBPLocationId.get().getRepoId());
		}

		// if no location was found based on relation, return own location, null or non-null
		return createLocationIdOrNull(bpartnerId, ownToLocation);
	}

	private void appendLocationChecks(@NonNull final BPartnerLocationQuery query, @NonNull final IQueryBuilder<I_C_BPartner_Location> bpLocationQueryBuilder)
	{
		final boolean skipLocationChecks = !query.applyLocationChecks();

		if (skipLocationChecks)
		{
			return;
		}

		final IQueryBuilder<I_C_Location> locationIQueryBuilder = queryBL.createQueryBuilder(I_C_Location.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Location.COLUMNNAME_C_Country_ID, query.getCountryId());

		if (Check.isNotBlank(query.getCity()))
		{
			locationIQueryBuilder.addEqualsFilter(I_C_Location.COLUMNNAME_City, query.getCity());
		}

		if (Check.isNotBlank(query.getPostalCode()))
		{
			locationIQueryBuilder.addEqualsFilter(I_C_Location.COLUMNNAME_Postal, query.getPostalCode());
		}

		bpLocationQueryBuilder.addInSubQueryFilter(I_C_BPartner_Location.COLUMN_C_Location_ID,
				I_C_Location.COLUMN_C_Location_ID, locationIQueryBuilder.create());
	}

	private BPartnerLocationId createLocationIdOrNull(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final I_C_BPartner_Location bpLocationRecord)
	{
		if (bpLocationRecord == null)
		{
			return null;
		}
		return BPartnerLocationId.ofRepoId(bpartnerId, bpLocationRecord.getC_BPartner_Location_ID());
	}

	private BPRelation ofRelationRecord(@NonNull final I_C_BP_Relation bpRelationRecord)
	{
		return BPRelation.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpRelationRecord.getC_BPartner_ID()))
				.bpLocationId(BPartnerLocationId.ofRepoIdOrNull(bpRelationRecord.getC_BPartner_ID(), bpRelationRecord.getC_BPartner_Location_ID()))
				.targetBPartnerId(BPartnerId.ofRepoId(bpRelationRecord.getC_BPartnerRelation_ID()))
				.targetBPLocationId(BPartnerLocationId.ofRepoIdOrNull(bpRelationRecord.getC_BPartnerRelation_ID(), bpRelationRecord.getC_BPartnerRelation_Location_ID()))
				.name(bpRelationRecord.getName())
				.build();
	}

	private String getFilterColumnNameForType(BPartnerLocationQuery.Type type)
	{
		switch (type)
		{
			case BILL_TO:
				return I_C_BP_Relation.COLUMNNAME_IsBillTo;
			case SHIP_TO:
				return I_C_BP_Relation.COLUMNNAME_IsShipTo;
			case REMIT_TO:
				return I_C_BP_Relation.COLUMNNAME_IsRemitTo;
			default:
				Check.fail("Unexpected type={}", type);
				return null;
		}
	}

	private static Optional<String> getOrderByColumnNameForType(final BPartnerLocationQuery.Type type)
	{
		switch (type)
		{
			case BILL_TO:
				return Optional.of(I_C_BPartner_Location.COLUMNNAME_IsBillToDefault);
			case SHIP_TO:
				return Optional.of(I_C_BPartner_Location.COLUMNNAME_IsShipToDefault);
			default:
				// not every type has one
				return Optional.empty();
		}
	}

	@Override
	public Optional<BPartnerId> retrieveBPartnerIdBy(@NonNull final BPartnerQuery query)
	{
		if (query.getBPartnerId() != null)
		{
			return Optional.of(query.getBPartnerId());
		}

		final StringJoiner searchedByInfo = new StringJoiner(", ");
		BPartnerId existingBPartnerId = null;

		if (query.getExternalId() != null)
		{
			existingBPartnerId = getBPartnerIdByExternalIdIfExists(query).orElse(null);
			searchedByInfo.add(StringUtils.formatMessage("ExternalId={}", query.getExternalId()));
		}

		// ..BPartner code (aka value)
		if (existingBPartnerId == null && query.getBpartnerValue() != null)
		{
			existingBPartnerId = getBPartnerIdByValueIfExists(query).orElse(null);
			searchedByInfo.add(StringUtils.formatMessage("Value/Code={}", query.getBpartnerValue()));
		}

		// ..BPartner Name
		if (existingBPartnerId == null && query.getBpartnerName() != null)
		{
			existingBPartnerId = getBPartnerIdByNameIfExists(query).orElse(null);
			searchedByInfo.add(StringUtils.formatMessage("Name={}", query.getBpartnerName()));
		}

		// BPLocation's GLN
		if (existingBPartnerId == null && !query.getGlns().isEmpty())
		{
			final GLNQuery glnsQuery = toGLNQuery(query);

			existingBPartnerId = glnsLoadingCache.getSingleBPartnerId(glnsQuery).orElse(null);
			searchedByInfo.add(StringUtils.formatMessage("Location.GLNs={}", query.getGlns()));
		}

		if (existingBPartnerId == null && query.isFailIfNotExists())
		{
			final String msg = StringUtils.formatMessage("Found no existing BPartner;"
							+ " Searched via the following properties one-after-one (list may be empty): {};"
							+ " The search was restricted to the following orgIds (empty means no restriction): {}",
					searchedByInfo.toString(),
					query.getOnlyOrgIds().stream().map(OrgId::getRepoId).collect(ImmutableList.toImmutableList()).toString());
			throw new BPartnerIdNotFoundException(msg);
		}

		return Optional.ofNullable(existingBPartnerId);
	}

	@Override
	public ImmutableSet<BPartnerId> retrieveBPartnerIdsBy(@NonNull final BPartnerQuery query)
	{
		final IQueryBuilder<I_C_BPartner> queryBuilder = createQueryBuilder(I_C_BPartner.class)
				// .addOnlyContextClient()
				// .addOnlyActiveRecordsFilter() also load inactive records!
				;

		if (!query.getOnlyOrgIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, query.getOnlyOrgIds());
		}

		if (query.getBPartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_ID, query.getBPartnerId());
		}

		// ..BPartner external-id
		if (query.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_ExternalId, query.getExternalId().getValue());
		}

		// ..BPartner code (aka value)
		if (!isEmpty(query.getBpartnerValue(), true))
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_Value, query.getBpartnerValue());
		}

		// ..BPartner Name
		if (!isEmpty(query.getBpartnerName(), true))
		{
			queryBuilder.addEqualsFilter(I_C_BPartner.COLUMNNAME_Name, query.getBpartnerName());
		}

		// BPLocation's GLN
		if (!query.getGlns().isEmpty())
		{
			final ImmutableSet<BPartnerId> bpartnerIdsForGLN = glnsLoadingCache.getBPartnerIds(toGLNQuery(query));
			if (bpartnerIdsForGLN.isEmpty())
			{
				return ImmutableSet.of();
			}

			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMN_C_BPartner_ID, bpartnerIdsForGLN);
		}

		//
		// Execute
		final ImmutableSet<BPartnerId> bpartnerIds = queryBuilder
				.create()
				.listIds(BPartnerId::ofRepoId);

		if (bpartnerIds.isEmpty() && query.isFailIfNotExists())
		{
			throw new AdempiereException("@NotFound@ @C_BPartner_ID@")
					.setParameter("query", query);
		}

		return bpartnerIds;
	}

	private static GLNQuery toGLNQuery(@NonNull final BPartnerQuery query)
	{
		return GLNQuery.builder()
				.glns(query.getGlns())
				.onlyOrgIds(query.getOnlyOrgIds())
				.build();
	}

	private Optional<BPartnerId> getBPartnerIdByExternalIdIfExists(@NonNull final BPartnerQuery query)
	{
		final ExternalId externalId = assumeNotNull(query.getExternalId(), "Param query needs to have a non-null externalId; query={}", query);

		final IQueryBuilder<I_C_BPartner> queryBuilder = createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMN_ExternalId, externalId.getValue());

		if (!query.getOnlyOrgIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, query.getOnlyOrgIds());
		}

		final int bpartnerRepoId = queryBuilder.create().firstId();
		return BPartnerId.optionalOfRepoId(bpartnerRepoId);
	}

	private Optional<BPartnerId> getBPartnerIdByValueIfExists(@NonNull final BPartnerQuery query)
	{
		final IQueryBuilder<I_C_BPartner> queryBuilder = createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMN_Value, query.getBpartnerValue());

		if (!query.getOnlyOrgIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, query.getOnlyOrgIds());
		}

		final int bpartnerRepoId = queryBuilder.create().firstId();
		return BPartnerId.optionalOfRepoId(bpartnerRepoId);
	}

	private Optional<BPartnerId> getBPartnerIdByNameIfExists(@NonNull final BPartnerQuery query)
	{
		final IQueryBuilder<I_C_BPartner> queryBuilder = createQueryBuilder(I_C_BPartner.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMN_Name, query.getBpartnerName());

		if (!query.getOnlyOrgIds().isEmpty())
		{
			queryBuilder.addInArrayFilter(I_C_BPartner.COLUMNNAME_AD_Org_ID, query.getOnlyOrgIds());
		}

		final int bpartnerRepoId = queryBuilder.create()
				.firstId();
		return BPartnerId.optionalOfRepoId(bpartnerRepoId);
	}

	private <T> IQueryBuilder<T> createQueryBuilder(
			@NonNull final Class<T> modelClass)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(modelClass)
				// .addOnlyActiveRecordsFilter() // don't generally rule out inactive partners
				.orderByDescending(I_AD_Org.COLUMNNAME_AD_Org_ID); // prefer "more specific" AD_Org_ID > 0;
	}

	@Override
	public BPGroupId getBPGroupIdByBPartnerId(@NonNull final BPartnerId bpartnerId)
	{
		return BPGroupId.ofRepoId(getById(bpartnerId).getC_BP_Group_ID());
	}

	@Override
	public Stream<BPartnerId> streamBPartnerIdsBySalesRepBPartnerId(@NonNull final BPartnerId salesRepBPartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_C_BPartner_SalesRep_ID, salesRepBPartnerId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(BPartnerId::ofRepoId)
				.stream();
	}

	@Override
	public List<BPartnerId> getParentsUpToTheTopInTrx(@NonNull final BPartnerId bpartnerId)
	{
		final ArrayList<BPartnerId> path = new ArrayList<>();

		BPartnerId currentBPartnerId = bpartnerId;
		while (currentBPartnerId != null)
		{
			if (path.contains(currentBPartnerId))
			{
				throw new AdempiereException("Cycle detected: " + path);
			}

			path.add(0, currentBPartnerId);

			currentBPartnerId = getParentIdInTrx(currentBPartnerId);
		}

		return path;
	}

	private BPartnerId getParentIdInTrx(@NonNull final BPartnerId bpartnerId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BPartner.class)
				.addEqualsFilter(I_C_BPartner.COLUMN_C_BPartner_ID, bpartnerId)
				.addNotNull(I_C_BPartner.COLUMNNAME_BPartner_Parent_ID)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_C_BPartner.COLUMNNAME_BPartner_Parent_ID, Integer.class)
				.stream()
				.map(BPartnerId::ofRepoId)
				.findAny()
				.orElse(null);
	}

	@Override
	public boolean isCampaignPriceAllowed(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = getById(bpartnerId);
		return bpartner.isAllowActionPrice();
	}

	@Override
	public boolean pricingSystemBelongsToCustomerForPriceMutation(final PricingSystemId pricingSystemId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final boolean belongsToCustomerForMutation = queryBL.createQueryBuilder(I_C_BPartner.class)
				.addOnlyContextClient()
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsCustomer, true)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsAllowPriceMutation, true)
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_M_PricingSystem_ID, pricingSystemId.getRepoId())
				.create()
				.anyMatch();

		return belongsToCustomerForMutation;
	}

	@Override
	public Optional<BPartnerContactId> getBPartnerContactIdBy(@NonNull final BPartnerContactQuery contactQuery)
	{
		final IQueryBuilder<I_AD_User> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User.class)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				.addInArrayFilter(I_AD_User.COLUMN_AD_Org_ID, Env.getOrgId(), OrgId.ANY);

		if (contactQuery.getBPartnerId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, contactQuery.getBPartnerId());
		}
		else
		{ // we don't know which one, but is has to be some C_BPartner_ID
			queryBuilder.addNotEqualsFilter(I_AD_User.COLUMN_C_BPartner_ID, null);
		}

		if (contactQuery.getUserId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_AD_User_ID, contactQuery.getUserId());
		}
		else if (contactQuery.getExternalId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_ExternalId, contactQuery.getExternalId().getValue());
		}
		else if (!Check.isEmpty(contactQuery.getValue(), true))
		{
			queryBuilder.addEqualsFilter(I_AD_User.COLUMN_Value, contactQuery.getValue().trim());
		}

		final I_AD_User userRecord = queryBuilder
				.create()
				.firstOnlyOrNull(I_AD_User.class);

		if (userRecord == null)
		{
			return Optional.empty();
		}

		return Optional.of(BPartnerContactId.ofRepoId(userRecord.getC_BPartner_ID(), userRecord.getAD_User_ID()));
	}

	@Override
	public List<GeographicalCoordinatesWithBPartnerLocationId> getGeoCoordinatesByBPartnerIds(@NonNull final Collection<BPartnerId> bpartnerIds)
	{
		if (bpartnerIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT "
				+ " bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID
				+ ", bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID
				+ ", l." + I_C_Location.COLUMNNAME_Latitude
				+ ", l." + I_C_Location.COLUMNNAME_Longitude
				+ " FROM " + I_C_BPartner.Table_Name + " bp "
				+ " INNER JOIN " + I_C_BPartner_Location.Table_Name + " bpl ON (bpl.C_BPartner_ID=bp.C_BPartner_ID)"
				+ " INNER JOIN " + I_C_Location.Table_Name + " l on (l.C_Location_ID=bpl.C_Location_ID)"
				+ " WHERE " + DB.buildSqlList("bp." + I_C_BPartner.COLUMNNAME_C_BPartner_ID, bpartnerIds, sqlParams)
				+ " AND bpl.IsActive='Y' "
				+ " AND l." + I_C_Location.COLUMNNAME_GeocodingStatus + "=?";
		sqlParams.add(X_C_Location.GEOCODINGSTATUS_Resolved);

		return retrieveGeographicalCoordinatesWithBPartnerLocationIds(sql, sqlParams);
	}

	@Override
	public List<GeographicalCoordinatesWithBPartnerLocationId> getGeoCoordinatesByBPartnerLocationIds(@NonNull final Collection<Integer> bpartnerLocationRepoIds)
	{
		if (bpartnerLocationRepoIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT "
				+ " bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID
				+ ", bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID
				+ ", l." + I_C_Location.COLUMNNAME_Latitude
				+ ", l." + I_C_Location.COLUMNNAME_Longitude
				+ " FROM " + I_C_BPartner_Location.Table_Name + " bpl "
				+ " INNER JOIN " + I_C_Location.Table_Name + " l on l.C_Location_ID=bpl.C_Location_ID"
				+ " WHERE " + DB.buildSqlList("bpl." + I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID, bpartnerLocationRepoIds, sqlParams)
				+ " AND l." + I_C_Location.COLUMNNAME_GeocodingStatus + "=?";
		sqlParams.add(X_C_Location.GEOCODINGSTATUS_Resolved);

		return retrieveGeographicalCoordinatesWithBPartnerLocationIds(sql, sqlParams);
	}

	private List<GeographicalCoordinatesWithBPartnerLocationId> retrieveGeographicalCoordinatesWithBPartnerLocationIds(final String sql, final List<Object> sqlParams)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final List<GeographicalCoordinatesWithBPartnerLocationId> result = new ArrayList<>();
			while (rs.next())
			{
				final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoId(
						rs.getInt(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID),
						rs.getInt(I_C_BPartner_Location.COLUMNNAME_C_BPartner_Location_ID));

				final BigDecimal latitude = rs.getBigDecimal(I_C_Location.COLUMNNAME_Latitude);
				final BigDecimal longitude = rs.getBigDecimal(I_C_Location.COLUMNNAME_Longitude);
				if (latitude == null || longitude == null)
				{
					// shall not happen
					logger.warn("Ignored location for bpartnerLocationId={} because the coordonate is not valid: lat={}, long={}", bpartnerLocationId, latitude, longitude);
					continue;
				}
				final GeographicalCoordinates coordinate = GeographicalCoordinates.builder()
						.latitude(latitude)
						.longitude(longitude)
						.build();

				result.add(GeographicalCoordinatesWithBPartnerLocationId.of(bpartnerLocationId, coordinate));
			}

			return result;
		}
		catch (final SQLException ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	@Override
	public BPartnerLocationId retrieveCurrentBillLocationOrNull(final BPartnerId partnerId)
	{
		final BPartnerLocationQuery query = BPartnerLocationQuery
				.builder()
				.type(Type.BILL_TO)
				.bpartnerId(partnerId)
				.build();
		final I_C_BPartner_Location billToLocation = retrieveBPartnerLocation(query);

		return createLocationIdOrNull(partnerId, billToLocation);
	}

	@Override
	public BPartnerPrintFormatMap getPrintFormats(@NonNull final BPartnerId bpartnerId)
	{
		return printFormatsCache.getOrLoad(bpartnerId, this::retrievePrintFormats);
	}

	private BPartnerPrintFormatMap retrievePrintFormats(@NonNull final BPartnerId bpartnerId)
	{
		final ImmutableList<BPartnerPrintFormat> printFormats = queryBL.createQueryBuilderOutOfTrx(I_C_BP_PrintFormat.class)
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID, bpartnerId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toBPartnerPrintFormat(record))
				.collect(ImmutableList.toImmutableList());

		return BPartnerPrintFormatMap.ofList(printFormats);
	}

	private static BPartnerPrintFormat toBPartnerPrintFormat(final I_C_BP_PrintFormat record)
	{
		return BPartnerPrintFormat.builder()
				.docTypeId(DocTypeId.ofRepoId(record.getC_DocType_ID()))
				.adTableId(AdTableId.ofRepoIdOrNull(record.getAD_Table_ID()))
				.printFormatId(PrintFormatId.ofRepoId(record.getAD_PrintFormat_ID()))
				.build();
	}
}
