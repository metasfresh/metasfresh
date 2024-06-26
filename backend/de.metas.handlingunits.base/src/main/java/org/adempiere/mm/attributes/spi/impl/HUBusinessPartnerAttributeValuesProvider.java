package org.adempiere.mm.attributes.spi.impl;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.CCache;
import de.metas.cache.CCacheStats;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

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

class HUBusinessPartnerAttributeValuesProvider implements IAttributeValuesProvider
{
	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

	static final String ATTRIBUTEVALUETYPE = X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
	private static final String CACHE_PREFIX = IAttributeDAO.CACHEKEY_ATTRIBUTE_VALUE;

	private static final AdMessageKey MSG_noneOrEmpty = AdMessageKey.of("NoneOrEmpty");

	private static final CCache<Integer, ImmutableList<KeyNamePair>> businessPartnersCache = CCache.<Integer, ImmutableList<KeyNamePair>>builder()
			.cacheName(CACHE_PREFIX + "#HU#" + I_C_BPartner.COLUMNNAME_C_BPartner_ID)
			.tableName(I_C_BPartner.Table_Name)
			.initialCapacity(100)
			.expireMinutes(30)
			.build();

	private final I_M_Attribute attribute;

	public HUBusinessPartnerAttributeValuesProvider(@NonNull final I_M_Attribute attribute)
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
		return ImmutableList.of(businessPartnersCache.stats());
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
		return Evaluatees.empty();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx_NOTUSED)
	{
		// NOTE: the only reason why we are fetching and returning the business partners instead of returning NULL,
		// is because user needs to set it in purchase order's ASI / HU attributes.

		return getBusinessPartners();
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

		return getBusinessPartners()
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

	private List<KeyNamePair> getBusinessPartners()
	{
		final ImmutableList<KeyNamePair> businessPartners = businessPartnersCache.getOrLoad(0, this::retrieveBusinessPartnersKeyNamePairs);
		return ImmutableList.<KeyNamePair>builder()
				.add(staticNullValue())
				.addAll(businessPartners)
				.build();
	}


	private ImmutableList<KeyNamePair> retrieveBusinessPartnersKeyNamePairs()
	{
		return bpartnerDAO.retrieveBusinessPartners()
				.stream()
				.map(HUBusinessPartnerAttributeValuesProvider::toKeyNamePair)
				.sorted(Comparator.comparing(KeyNamePair::getName))
				.collect(ImmutableList.toImmutableList());
	}

	private static KeyNamePair toKeyNamePair(@NonNull final I_C_BPartner bpartner)
	{
		return KeyNamePair.of(bpartner.getC_BPartner_ID(), bpartner.getName(), bpartner.getDescription());
	}

}
