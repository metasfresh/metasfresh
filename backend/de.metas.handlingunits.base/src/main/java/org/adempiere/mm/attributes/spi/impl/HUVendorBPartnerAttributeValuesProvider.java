package org.adempiere.mm.attributes.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
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
import org.adempiere.ad.dao.QueryLimit;
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
import java.util.Collection;
import java.util.Collections;
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
			.expireMinutes(10)
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
		return ImmutableList.of(vendors.stats());
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
		return true;
	}

	@Override
	public Evaluatee prepareContext(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = huAttributesBL.getM_HU(attributeSet);
		final int bpartnerId = hu.getC_BPartner_ID();
		final int huId = hu.getM_HU_ID();

		return Evaluatees.mapBuilder()
				.put(CTXNAME_M_HU_ID, huId > 0 ? huId : -1)
				.put(CTXNAME_CurrentVendor_BPartner_ID, bpartnerId > 0 ? bpartnerId : -1)
				.build();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx)
	{
		return retrieveVendorPairs();
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
		final List<? extends NamePair> availableValues = retrieveVendorPairs();
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
	 * @return available values
	 */
	private static List<KeyNamePair> retrieveVendorPairs()
	{
		final List<KeyNamePair> vendors = new ArrayList<>();
		vendors.add(staticNullValue());

		final List<KeyNamePair> vendorsCached = HUVendorBPartnerAttributeValuesProvider.vendors.getOrLoad(0, () -> retrieveVendorKeyNamePairs());
			vendors.addAll(vendorsCached);

		return vendors;
	}

	private static List<KeyNamePair> retrieveVendorKeyNamePairs()
	{
		return bPartnerDAO.retrieveVendors(QueryLimit.ofInt(20))
				.stream()
				.map(bpartner -> toKeyNamePair(bpartner))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static KeyNamePair toKeyNamePair(@NonNull final I_C_BPartner bpartner)
	{
		return KeyNamePair.of(bpartner.getC_BPartner_ID(), bpartner.getName(), bpartner.getDescription());
	}

}
