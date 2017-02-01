package org.adempiere.mm.attributes.spi.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ISubProducerAttributeDAO;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CCache;
import org.compiere.util.CCache.CCacheStats;
import org.compiere.util.CtxName;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Util.ArrayKey;

import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.ITranslatableString;
import de.metas.interfaces.I_C_BP_Relation;

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

class HUSubProducerBPartnerAttributeValuesProvider implements IAttributeValuesProvider
{
	static final String ATTRIBUTEVALUETYPE = X_M_Attribute.ATTRIBUTEVALUETYPE_Number;

	private static final String CACHE_PREFIX = I_M_AttributeValue.Table_Name;

	/**
	 * Cache: C_BPartner_ID to list of sub-producer partners (as KeyNamePair)
	 *
	 * NOTE: we use a static cache for optimization purposes
	 */
	private static final CCache<Integer, List<KeyNamePair>> bpartnerId2subProducers = new CCache<>(
			CACHE_PREFIX + "#by#" + I_C_BP_Relation.COLUMNNAME_C_BPartner_ID + "#" + I_C_BP_Relation.COLUMNNAME_IsMainProducer,      // name
			10,      // initial capacity
			0 // expires 0min => never
	);

	private static final ITranslatableString DISPLAYNAME_None = Services.get(IMsgBL.class).translatable("NoneOrEmpty");
	private static final ConcurrentHashMap<String, KeyNamePair> adLanguage2keyNamePairNone = new ConcurrentHashMap<>();

	private static final CtxName CTXNAME_M_HU_ID = CtxName.parse("M_HU_ID/-1");
	private static final CtxName CTXNAME_C_BPartner_ID = CtxName.parse("C_BPartner_ID/-1");
	private static final CtxName CTXNAME_CurrentSubProducer_BPartner_ID = CtxName.parse("CurrentSubProducer_BPartner_ID/-1");

	//
	//
	private final I_M_Attribute attribute;

	/**
	 * Cache M_HU_ID/C_BPartner_ID/Current_SubProducer_BPartnerId to list of sub-producer partners (as KeyNamePair).
	 *
	 * Used for short term purposes.
	 */
	private final CCache<ArrayKey, List<KeyNamePair>> hu2subProducers = new CCache<>(bpartnerId2subProducers.getName() + "#AndHU",
			100,     // initial capacity
			10  // expires in 10min
	);

	public HUSubProducerBPartnerAttributeValuesProvider(final I_M_Attribute attribute)
	{
		super();
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
		return ImmutableList.of(bpartnerId2subProducers.stats(), hu2subProducers.stats());
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

	static final KeyNamePair staticNullValue()
	{
		final String adLanguage = Env.getAD_Language(Env.getCtx());

		final KeyNamePair nullValue = KeyNamePair.of(0, DISPLAYNAME_None.translate(adLanguage));
		if (adLanguage == null)
		{
			return nullValue; // guard against NPE, which happened when running this code via async-processor on an "embedded server"
		}
		// NOTE: we use KeyNamePair's Key=0 because "-1" is specially handled by KeyNamePair (see KeyNamePair.getID() which returns null)
		// and we run in some weird problems
		return adLanguage2keyNamePairNone.computeIfAbsent(adLanguage, key -> nullValue);

	}

	@Override
	public boolean isHighVolume()
	{
		// NOTE: we don't expect to have too many sub-producers for one producer
		return false;
	}

	@Override
	public boolean isAllowAnyValue()
	{
		// NOTE: we need to allow setting any BPartner no matter what.
		// Anyways, in UI user will be allowed to pick only from suggested ones,
		// and this change is mainly for the case when we are programatically set the BPartner from our BL.
		// e.g. when the HU was receipt we are setting the HU's BPartner as sub-producer (08027)
		return true;

		// final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU_OrNull(attributeSet);
		// if (hu == null)
		// {
		// // in case we don't have an uderlying HU (e.g. we have an ASI attribute set) then accept any value
		// return true;
		// }
		//
		// return false;
	}

	@Override
	public Evaluatee prepareContext(final IAttributeSet attributeSet)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);
		final int bpartnerId = hu.getC_BPartner_ID();
		final int huId = hu.getM_HU_ID();

		int currentSubProducerBPartnerId = -1;
		if (attributeSet.hasAttribute(attribute))
		{
			currentSubProducerBPartnerId = attributeSet.getValueAsInt(attribute);
		}

		return Evaluatees.mapBuilder()
				.put(CTXNAME_M_HU_ID, huId > 0 ? huId : -1)
				.put(CTXNAME_C_BPartner_ID, bpartnerId > 0 ? bpartnerId : -1)
				.put(CTXNAME_CurrentSubProducer_BPartner_ID, currentSubProducerBPartnerId > 0 ? currentSubProducerBPartnerId : -1)
				.build();
	}

	@Override
	public List<? extends NamePair> getAvailableValues(final Evaluatee evalCtx)
	{
		final int huId = CTXNAME_M_HU_ID.getValueAsInteger(evalCtx);
		final int bpartnerId = CTXNAME_C_BPartner_ID.getValueAsInteger(evalCtx);
		final int currentSubProducerBPartnerId = CTXNAME_CurrentSubProducer_BPartner_ID.getValueAsInteger(evalCtx);

		//
		// Create the cache key based on M_HU_ID, M_HU.C_BPartner_ID and current SubProducer_BPartner_ID
		final ArrayKey cacheKey = ArrayKey.of(
				huId <= 0 ? System.currentTimeMillis() : huId,      // if M_HU_ID <= 0 then make it unique
				bpartnerId <= 0 ? -1 : bpartnerId,      // normalize
				currentSubProducerBPartnerId <= 0 ? -1 : currentSubProducerBPartnerId // normalize
		);

		//
		// Get from cache / Load
		return hu2subProducers.getOrLoad(cacheKey, () -> retrieveSubProducerKeyNamePairs(attribute, bpartnerId, currentSubProducerBPartnerId));
	}

	@Override
	public NamePair getAttributeValueOrNull(final Evaluatee evalCtx, final String value)
	{
		final List<? extends NamePair> availableValues = getAvailableValues(evalCtx);
		for (final NamePair vnp : availableValues)
		{
			if (Objects.equals(vnp.getID(), value))
			{
				return vnp;
			}
		}

		return null;
	}

	@Override
	public int getM_AttributeValue_ID(final String valueKey)
	{
		return -1;
	}

	/**
	 * Retrieve allowed subproducers KeyNamePairs.
	 *
	 * Mainly it will contain following values:
	 * <ul>
	 * <li>{@link #getNullValue()}
	 * <li>allowed SubProducer BPartners for M_HU.C_BPartner_ID
	 * <li>current HU's SubProducer BPartner if not present in the list above
	 * </ul>
	 *
	 * @param attribute subproducer attribute
	 * @param bpartnerId i.e. M_HU.C_BPartner_ID
	 * @param currentSubProducerBPartnerId current subproducer BPartner ID
	 * @return available values
	 */
	private static List<KeyNamePair> retrieveSubProducerKeyNamePairs(
			final I_M_Attribute attribute,
			final int bpartnerId,
			final int currentSubProducerBPartnerId)
	{
		final Properties ctx = Env.getCtx();

		final List<KeyNamePair> subProducerBPartners = new ArrayList<>();
		subProducerBPartners.add(staticNullValue());

		//
		// Retrieve subProducers from cache
		if (bpartnerId > 0)
		{
			final List<KeyNamePair> subProducerBPartnersCached = bpartnerId2subProducers.getOrLoad(bpartnerId, () -> retrieveSubProducerKeyNamePairs(ctx, bpartnerId));
			subProducerBPartners.addAll(subProducerBPartnersCached);
		}

		//
		// Make sure current subProducer is in our list
		// NOTE: we need to make sure that attribute exists because if we are about generating seed values, at this moment attribute is not there yet.
		addSubProducer(subProducerBPartners, ctx, currentSubProducerBPartnerId);

		return subProducerBPartners;
	}

	private static final void addSubProducer(final List<KeyNamePair> subProducerBPartners, final Properties ctx, final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return;
		}

		//
		// Check if our BPartner is already in the list.
		// If yes, there is no point to add it.
		if (subProducerBPartners != null)
		{
			for (final KeyNamePair subProducer : subProducerBPartners)
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
		subProducerBPartners.add(bpartnerKNP);
	}

	/**
	 * Retrieves suggested SubProducer BPartners for given <code>bpartnerId</code>
	 *
	 * @param ctx
	 * @param bpartnerId
	 * @return list of bpartner's suggested SubProducers
	 */
	private static List<KeyNamePair> retrieveSubProducerKeyNamePairs(final Properties ctx, final int bpartnerId)
	{
		return Services.get(ISubProducerAttributeDAO.class)
				.retrieveSubProducerBPartners(ctx, bpartnerId)
				.stream()
				.map(bpartner -> toKeyNamePair(bpartner))
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * Convert given {@link I_C_BPartner} to a {@link KeyNamePair}.
	 *
	 * @param bpartner
	 * @return
	 */
	private static final KeyNamePair toKeyNamePair(final I_C_BPartner bpartner)
	{
		Check.assumeNotNull(bpartner, "bpartner not null");
		return new KeyNamePair(bpartner.getC_BPartner_ID(), bpartner.getName());
	}

}
