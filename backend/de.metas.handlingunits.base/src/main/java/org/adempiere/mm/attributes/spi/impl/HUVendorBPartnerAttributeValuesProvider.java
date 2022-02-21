package org.adempiere.mm.attributes.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CCacheStats;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CtxName;
import org.compiere.util.CtxNames;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Util;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Properties;

/*
 * #%L
 * de.metas.handlingunits.base
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

class HUVendorBPartnerAttributeValuesProvider implements IAttributeValuesProvider
{
	private final ISysConfigBL sysconfigBL = Services.get(ISysConfigBL.class);
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	static final String ATTRIBUTEVALUETYPE = X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
	private static final String CACHE_PREFIX = IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE;

	private static final AdMessageKey MSG_noneOrEmpty = AdMessageKey.of("NoneOrEmpty");

	private static final String SYSCONFIG_MAX_VENDORS = "org.adempiere.mm.attributes.spi.impl.HUVendorBPartnerAttributeValuesProvider.maxVendors";
	private static final int DEFAULT_MAX_VENDORS = 20;

	private static final CtxName CTXNAME_M_HU_ID = CtxNames.parse("M_HU_ID/-1");
	private static final CtxName CTXNAME_C_BPartner_ID = CtxNames.parse("C_BPartner_ID/-1");
	private static final CtxName CTXNAME_CurrentVendor_BPartner_ID = CtxNames.parse("CurrentVendor_BPartner_ID/-1");

	private static final CCache<Integer, ImmutableList<KeyNamePair>> vendorsCache = CCache.<Integer, ImmutableList<KeyNamePair>>builder()
			.tableName(I_C_BPartner.Table_Name)
			.cacheName(CACHE_PREFIX)
			.build();

	private final CCache<Util.ArrayKey, List<KeyNamePair>> hu2Vendors = CCache.<Util.ArrayKey, List<KeyNamePair>>builder()
			.cacheName(vendorsCache.getCacheName() + "#AndHU")
			.initialCapacity(100)
			.expireMinutes(10)
			.additionalTableNameToResetFor(I_C_BPartner.Table_Name)
			.build();

	private final I_M_Attribute attribute;

	public HUVendorBPartnerAttributeValuesProvider(@NonNull final I_M_Attribute attribute)
	{
		this.attribute = attribute;
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(vendorsCache.stats());
	}

	@Override
	public String getAttributeValueType()
	{
		return ATTRIBUTEVALUETYPE;
	}

	@Override
	public final KeyNamePair getNullValue()
	{
		return staticNullValue();
	}

	static KeyNamePair staticNullValue()
	{
		final String adLanguage = Env.getADLanguageOrBaseLanguage();
		final String displayName = TranslatableStrings.adMessage(MSG_noneOrEmpty).translate(adLanguage);

		// NOTE: we use KeyNamePair's Key=0 because "-1" is specially handled by KeyNamePair (see KeyNamePair.getID() which returns null)
		// and we run in some weird problems
		return KeyNamePair.of(0, displayName);

	}

	@Override
	public boolean isHighVolume()
	{
		return true;
	}

	@Override
	public boolean isAllowAnyValue()
	{
		return true;
	}

	@Override
	public Evaluatee prepareContext(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);
		final int bpartnerId = hu.getC_BPartner_ID();
		final int huId = hu.getM_HU_ID();

		int currentVendorBPartnerId = -1;
		if (attributeSet.hasAttribute(attribute))
		{
			currentVendorBPartnerId = attributeSet.getValueAsInt(attribute);
		}

		return Evaluatees.mapBuilder()
				.put(CTXNAME_M_HU_ID, huId > 0 ? huId : -1)
				.put(CTXNAME_C_BPartner_ID, bpartnerId > 0 ? bpartnerId : -1)
				.put(CTXNAME_CurrentVendor_BPartner_ID, currentVendorBPartnerId > 0 ? currentVendorBPartnerId : -1)
				.build();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx)
	{
		final int huId = CTXNAME_M_HU_ID.getValueAsInteger(evalCtx);
		final int bpartnerId = CTXNAME_C_BPartner_ID.getValueAsInteger(evalCtx);
		final int currentVendorBPartnerId = CTXNAME_CurrentVendor_BPartner_ID.getValueAsInteger(evalCtx);

		//
		// Create the cache key based on M_HU_ID, M_HU.C_BPartner_ID and current Vendor_BPartner_ID
		final Util.ArrayKey cacheKey = Util.ArrayKey.of(
				huId <= 0 ? System.currentTimeMillis() : huId,      // if M_HU_ID <= 0 then make it unique
				bpartnerId <= 0 ? -1 : bpartnerId,      // normalize
				currentVendorBPartnerId <= 0 ? -1 : currentVendorBPartnerId // normalize
		);

		//
		// Get from cache / Load
		return hu2Vendors.getOrLoad(cacheKey, () -> retrieveVendorKeyNamePairs(bpartnerId, currentVendorBPartnerId));
	}

	@Nullable
	private static BPartnerId normalizeValueKey(@Nullable final Object valueKey)
	{
		if (valueKey == null)
		{
			return null;
		}
		else if (valueKey instanceof Number)
		{
			final int valueInt = ((Number)valueKey).intValue();
			return BPartnerId.ofRepoIdOrNull(valueInt);
		}
		else
		{
			final int valueInt = NumberUtils.asInt(valueKey.toString(), -1);
			return BPartnerId.ofRepoIdOrNull(valueInt);
		}
	}

	@Override
	@Nullable
	public KeyNamePair getAttributeValueOrNull(final Evaluatee evalCtx_NOTUSED, final Object valueKey)
	{
		final BPartnerId bpartnerId = normalizeValueKey(valueKey);
		if (bpartnerId == null)
		{
			return null;
		}

		return getCachedVendors()
				.stream()
				.filter(vnp -> vnp.getKey() == bpartnerId.getRepoId())
				.findFirst()
				.orElseGet(() -> retrieveBPartnerKNPById(bpartnerId));
	}

	@Nullable
	private KeyNamePair retrieveBPartnerKNPById(@NonNull final BPartnerId bpartnerId)
	{
		final I_C_BPartner bpartner = bpartnerDAO.getByIdOutOfTrx(bpartnerId);
		return bpartner != null ? toKeyNamePair(bpartner) : null;
	}

	@Nullable
	@Override
	public AttributeValueId getAttributeValueIdOrNull(final Object valueKey)
	{
		return null;
	}

	private List<KeyNamePair> getCachedVendors()
	{
		final ImmutableList<KeyNamePair> vendors = vendorsCache.getOrLoad(0, this::retrieveVendorKeyNamePairs);

		return ImmutableList.<KeyNamePair>builder()
				.add(staticNullValue())
				.addAll(vendors)
				.build();
	}

	private ImmutableList<KeyNamePair> retrieveVendorKeyNamePairs()
	{
		return bpartnerDAO.retrieveVendors(getMaxVendors())
				.stream()
				.map(HUVendorBPartnerAttributeValuesProvider::toKeyNamePair)
				.sorted(Comparator.comparing(KeyNamePair::getName))
				.collect(ImmutableList.toImmutableList());
	}
	private QueryLimit getMaxVendors()
	{
		final int maxVendorsInt = sysconfigBL.getIntValue(SYSCONFIG_MAX_VENDORS, DEFAULT_MAX_VENDORS);
		return QueryLimit.ofInt(maxVendorsInt);
	}

	private List<KeyNamePair> retrieveVendorKeyNamePairs(final int bpartnerId,
			final int currentVendorBPartnerId)
	{
		final Properties ctx = Env.getCtx();

		final List<KeyNamePair> vendorBPartners = new ArrayList<>();
		vendorBPartners.add(staticNullValue());

		//
		// Retrieve vendors from cache
		if (bpartnerId > 0)
		{
			final List<KeyNamePair> vendorBPartnersCached = vendorsCache.getOrLoad(bpartnerId, () -> retrieveVendorKeyNamePairs());
			vendorBPartners.addAll(vendorBPartnersCached);
		}

		//
		// Make sure current vendor is in our list
		// NOTE: we need to make sure that attribute exists because if we are about generating seed values, at this moment attribute is not there yet.
		addVendor(vendorBPartners, ctx, currentVendorBPartnerId);

		return vendorBPartners;
	}

	private static void addVendor(final List<KeyNamePair> vendorPartners, final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return;
		}

		//
		// Check if our BPartner is already in the list.
		// If yes, there is no point to add it.
		if (vendorPartners != null)
		{
			for (final KeyNamePair vendor : vendorPartners)
			{
				if (vendor == null)
				{
					continue;
				}
				if (vendor.getKey() == bpartnerId)
				{
					// our bpartner is already in the list
					return;
				}
			}
		}

		//
		// Load the new BPartner and add it to our list
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(ctx, bpartnerId, I_C_BPartner.class, ITrx.TRXNAME_None);
		if (bpartner == null)
		{
			return;
		}
		final KeyNamePair bpartnerKNP = toKeyNamePair(bpartner);
		vendorPartners.add(bpartnerKNP);
	}

	private static KeyNamePair toKeyNamePair(@NonNull final I_C_BPartner bpartner)
	{
		return KeyNamePair.of(bpartner.getC_BPartner_ID(), bpartner.getName(), bpartner.getDescription());
	}

}
