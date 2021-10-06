package org.adempiere.mm.attributes.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CCacheStats;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
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
import org.compiere.util.Util.ArrayKey;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private static final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private static final IMsgBL iMsgBL = Services.get(IMsgBL.class);

	static final String ATTRIBUTEVALUETYPE = X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
	private static final String CACHE_PREFIX = IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE;

	private static final CtxName CTXNAME_M_HU_ID = CtxNames.parse("M_HU_ID/-1");
	private static final CtxName CTXNAME_CurrentVendor_BPartner_ID = CtxNames.parse("CurrentVendor_BPartner_ID/-1");


	private static final CCache<Integer, List<KeyNamePair>> vendors =  CCache.<Integer, List<KeyNamePair>> builder()
			.tableName(IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE)
			.cacheName(CACHE_PREFIX)
			.initialCapacity(20)
			.build();

	private final CCache<ArrayKey, List<KeyNamePair>> hu2Vendors = CCache.<ArrayKey, List<KeyNamePair>> builder()
			.cacheName(vendors.getCacheName() + "#AndHU")
			.initialCapacity(100)
			.expireMinutes(10)
			.build();

	private final I_M_Attribute attribute;

	public HUVendorBPartnerAttributeValuesProvider(final I_M_Attribute attribute)
	{
		Check.assumeNotNull(attribute, "Parameter attribute is not null");
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
		return ImmutableList.of(vendors.stats(), hu2Vendors.stats());
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

		final ITranslatableString displayNameTrl = iMsgBL.translatable("NoneOrEmpty");

		final String adLanguage = Env.getAD_Language(Env.getCtx());
		final String displayName = adLanguage != null ? displayNameTrl.translate(adLanguage) : displayNameTrl.getDefaultValue();

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
		return false;
	}

	@Override
	public Evaluatee prepareContext(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = huAttributesBL.getM_HU(attributeSet);
		final int bpartnerId = hu.getC_BPartner_ID();
		final int huId = hu.getM_HU_ID();

		int currentVendorId = -1;
		if (attributeSet.hasAttribute(attribute))
		{
			currentVendorId = attributeSet.getValueAsInt(attribute);
		}

		return Evaluatees.mapBuilder()
				.put(CTXNAME_M_HU_ID, huId > 0 ? huId : -1)
				.put(CTXNAME_CurrentVendor_BPartner_ID, currentVendorId > 0 ? currentVendorId : -1)
				.build();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx)
	{
		final int huId = CTXNAME_M_HU_ID.getValueAsInteger(evalCtx);
		final int currentVendorId = CTXNAME_CurrentVendor_BPartner_ID.getValueAsInteger(evalCtx);

		//
		// Create the cache key based on M_HU_ID and current vendor
		final ArrayKey cacheKey = ArrayKey.of(
				huId <= 0 ? System.currentTimeMillis() : huId,      // if M_HU_ID <= 0 then make it unique
				currentVendorId <= 0 ? -1 : currentVendorId // normalize
		);

		//
		// Get from cache / Load
		return hu2Vendors.getOrLoad(cacheKey, () -> retrieveVendorKeyNamePairs(currentVendorId));
	}

	@Nullable
	private static String normalizeValueKey(final Object valueKey)
	{
		if (valueKey == null)
		{
			return null;
		}
		else if (valueKey instanceof Number)
		{
			final int valueKeyAsInt = ((Number)valueKey).intValue();
			return String.valueOf(valueKeyAsInt);
		}
		else
		{
			return valueKey.toString();
		}
	}

	@Override
	@Nullable
	public NamePair getAttributeValueOrNull(final Evaluatee evalCtx, final Object valueKey)
	{
		final List<? extends NamePair> availableValues = getAvailableValues(evalCtx);
		if (availableValues.isEmpty())
		{
			return null;
		}

		// Normalize the valueKey
		final String valueKeyNormalized = normalizeValueKey(valueKey);

		for (final NamePair vnp : availableValues)
		{
			if (Objects.equals(vnp.getID(), valueKeyNormalized))
			{
				return vnp;
			}
		}

		return null;
	}

	@Nullable
	@Override
	public AttributeValueId getAttributeValueIdOrNull(final Object valueKey)
	{
		return null;
	}

	/**
	 * Retrieve allowed vendor KeyNamePairs.
	 *
	 * Mainly it will contain following values:
	 * <ul>
	 * <li>{@link #getNullValue()}
	 * <li>available vendors
	 * <li>current HU's vendor if not present in the list above
	 * </ul>
	 *
	 * @param currentVendorBPartnerId current vendor ID
	 * @return available values
	 */
	private static List<KeyNamePair> retrieveVendorKeyNamePairs(
			final int currentVendorBPartnerId)
	{
		final Properties ctx = Env.getCtx();

		final List<KeyNamePair> vendors = new ArrayList<>();
		vendors.add(staticNullValue());

		final List<KeyNamePair> subProducerBPartnersCached = HUVendorBPartnerAttributeValuesProvider.vendors.getOrLoad(0, () -> retrieveVendorKeyNamePairs());
			vendors.addAll(subProducerBPartnersCached);

		addVendor(vendors, ctx, currentVendorBPartnerId);

		return vendors;
	}

	private static void addVendor(final List<KeyNamePair> vendors, final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return;
		}

		//
		// Check if our BPartner is already in the list.
		// If yes, there is no point to add it.
		if (vendors != null)
		{
			for (final KeyNamePair subProducer : vendors)
			{
				if (subProducer == null)
				{
					continue;
				}
				if (subProducer.getKey() == bpartnerId)
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
		vendors.add(bpartnerKNP);
	}

	private static List<KeyNamePair> retrieveVendorKeyNamePairs()
	{
		return bPartnerDAO.retrieveVendors()
				.stream()
				.map(bpartner -> toKeyNamePair(bpartner))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static KeyNamePair toKeyNamePair(@NonNull final I_C_BPartner bpartner)
	{
		return KeyNamePair.of(bpartner.getC_BPartner_ID(), bpartner.getName(), bpartner.getDescription());
	}

}
