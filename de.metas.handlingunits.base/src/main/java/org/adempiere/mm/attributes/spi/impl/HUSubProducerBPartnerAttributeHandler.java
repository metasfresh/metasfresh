package org.adempiere.mm.attributes.spi.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.ISubProducerAttributeBL;
import org.adempiere.mm.attributes.api.ISubProducerAttributeDAO;
import org.adempiere.mm.attributes.model.I_M_Attribute;
import org.adempiere.mm.attributes.spi.AttributeValueCalloutAdapter;
import org.adempiere.mm.attributes.spi.IAttributeValueContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import com.google.common.base.Supplier;

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.conversion.ConversionHelper;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.interfaces.I_C_BP_Relation;

/**
 * Retrieve all subproducer BPartners for {@link I_M_HU#getC_BPartner_ID()}.
 *
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/06135_Gesch%C3%A4ftspartner_Unterlieferanten_%28108858601576%29
 */
public class HUSubProducerBPartnerAttributeHandler
		extends AttributeValueCalloutAdapter
		implements IAttributeValueGenerator, IAttributeValuesProvider
{
	private static final String MSG_None = "NoneOrEmpty";

	private final KeyNamePair KEYNAMEPAIR_None;

	/**
	 * Cache: C_BPartner_ID to list of sub-producer partners (as KeyNamePair)
	 *
	 * NOTE: we use a static cache for optimization purposes
	 */
	private static final CCache<Integer, List<KeyNamePair>> bpartnerId2subProducers = new CCache<Integer, List<KeyNamePair>>(
			I_C_BP_Relation.Table_Name + "#by#" + I_C_BP_Relation.COLUMNNAME_C_BPartner_ID + "#" + I_C_BP_Relation.COLUMNNAME_IsMainProducer, // name
			10, // initial capacity
			0 // expires 0min => never
	);

	/**
	 * Cache M_HU_ID/C_BPartner_ID/Current_SubProducer_BPartnerId to list of sub-producer partners (as KeyNamePair).
	 *
	 * Used for short term purposes.
	 */
	private final CCache<ArrayKey, List<KeyNamePair>> hu2subProducers = new CCache<ArrayKey, List<KeyNamePair>>(bpartnerId2subProducers.getName() + "#AndHU",
			100,// initial capacity
			10  // expires in 10min
	);

	public HUSubProducerBPartnerAttributeHandler()
	{
		super();

		// NOTE: we use Key=0 because "-1" is specially handled by KeyNamePair (see KeyNamePair.getID() which returns null)
		// and we run in some weird problems
		KEYNAMEPAIR_None = new KeyNamePair(0, Services.get(IMsgBL.class).translate(Env.getCtx(), MSG_None));
	}

	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_Number;
	}

	@Override
	public boolean isAllowAnyValue(final IAttributeSet attributeSet, final I_M_Attribute attribute)
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
	public List<? extends NamePair> getAvailableValues(final IAttributeSet attributeSet, final I_M_Attribute attribute)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU(attributeSet);
		final int bpartnerId = hu.getC_BPartner_ID();
		final int huId = hu.getM_HU_ID();

		//
		// Get current HU's SubProducer BPartner ID
		final int currentSubProducerBPartnerId;
		if (attributeSet.hasAttribute(attribute))
		{
			currentSubProducerBPartnerId = attributeSet.getValueAsInt(attribute);
		}
		else
		{
			currentSubProducerBPartnerId = -1;
		}

		//
		// Create the cache key based on M_HU_ID, M_HU.C_BPartner_ID and current SubProducer_BPartner_ID
		final ArrayKey cacheKey = Util.mkKey(
				huId <= 0 ? System.currentTimeMillis() : huId, // if M_HU_ID <= 0 then make it unique
				bpartnerId <= 0 ? -1 : bpartnerId, // normalize
				currentSubProducerBPartnerId <= 0 ? -1 : currentSubProducerBPartnerId // normalize
				);

		//
		// Get from cache / Load
		return hu2subProducers.get(cacheKey, new Supplier<List<KeyNamePair>>()
		{
			@Override
			public List<KeyNamePair> get()
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(hu);
				return retrieveSubProducerKeyNamePairs(ctx, attributeSet, attribute, bpartnerId, currentSubProducerBPartnerId);
			}
		});
	}

	@Override
	public NamePair getAttributeValueOrNull(final IAttributeSet attributeSet, final I_M_Attribute attribute, final String value)
	{
		final List<? extends NamePair> availableValues = getAvailableValues(attributeSet, attribute);
		for (final NamePair vnp : availableValues)
		{
			if (Check.equals(vnp.getID(), value))
			{
				return vnp;
			}
		}
		
		return null;
	}

	/**
	 * Retrieve allowed subproducers KeyNamePairs.
	 *
	 * Mainly it will contain following values:
	 * <ul>
	 * <li>{@link #KEYNAMEPAIR_None}
	 * <li>allowed SubProducer BPartners for M_HU.C_BPartner_ID
	 * <li>current HU's SubProducer BPartner if not present in the list above
	 * </ul>
	 *
	 * @param ctx
	 * @param attributeSet
	 * @param attribute subproducer attribute
	 * @param bpartnerId i.e. M_HU.C_BPartner_ID
	 * @param currentSubProducerBPartnerId current subproducer BPartner ID
	 * @return available values
	 */
	private List<KeyNamePair> retrieveSubProducerKeyNamePairs(
			final Properties ctx,
			final IAttributeSet attributeSet,
			final I_M_Attribute attribute,
			final int bpartnerId,
			final int currentSubProducerBPartnerId)
	{

		final List<KeyNamePair> subProducerBPartners = new ArrayList<>();
		subProducerBPartners.add(KEYNAMEPAIR_None);

		//
		// Retrieve subProducers from cache
		if (bpartnerId > 0)
		{
			final List<KeyNamePair> subProducerBPartnersCached = bpartnerId2subProducers.get(bpartnerId, new Supplier<List<KeyNamePair>>()
			{
				@Override
				public List<KeyNamePair> get()
				{
					return retrieveSubProducerKeyNamePairs(ctx, bpartnerId);
				}
			});
			subProducerBPartners.addAll(subProducerBPartnersCached);
		}

		//
		// Make sure current subProducer is in our list
		// NOTE: we need to make sure that attribute exists because if we are about generating seed values, at this moment attribute is not there yet.
		addSubProducer(subProducerBPartners, ctx, currentSubProducerBPartnerId);

		return subProducerBPartners;
	}

	private final void addSubProducer(final List<KeyNamePair> subProducerBPartners, final Properties ctx, final int bpartnerId)
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
	private List<KeyNamePair> retrieveSubProducerKeyNamePairs(final Properties ctx, final int bpartnerId)
	{
		final ISubProducerAttributeDAO subProducerAttributeDAO = Services.get(ISubProducerAttributeDAO.class);

		final List<I_C_BPartner> subProducerBPartners = subProducerAttributeDAO.retrieveSubProducerBPartners(ctx, bpartnerId);
		final List<KeyNamePair> subProducerBPartnerKNPs = new ArrayList<>(subProducerBPartners.size() + 1);
		subProducerBPartnerKNPs.addAll(toKeyNamePairs(subProducerBPartners));
		return subProducerBPartnerKNPs;
	}

	/**
	 * Convert the list of {@link I_C_BPartner}s to a list of {@link KeyNamePair}s.
	 *
	 * @param bpartners
	 * @return unmodifiable list of BPartner {@link KeyNamePair}s.
	 */
	private static final List<KeyNamePair> toKeyNamePairs(final List<I_C_BPartner> bpartners)
	{
		if (bpartners.isEmpty())
		{
			return Collections.emptyList();
		}

		final List<KeyNamePair> result = new ArrayList<KeyNamePair>(bpartners.size());
		for (final I_C_BPartner bpartner : bpartners)
		{
			final KeyNamePair knp = toKeyNamePair(bpartner);
			result.add(knp);
		}

		return Collections.unmodifiableList(result);
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

	@Override
	public boolean canGenerateValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		return false;
	}

	@Override
	public String generateStringValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public BigDecimal generateNumericValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}
	
	@Override
	public Date generateDateValue(final Properties ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	@Override
	public I_M_AttributeValue generateAttributeValue(final Properties ctx, final int tableId, final int recordId, final boolean isSOTrx, final String trxName)
	{
		throw new UnsupportedOperationException("Not supported");
	}

	/**
	 * Calls {@link ISubProducerAttributeBL#updateAttributesOnSubProducerChanged(Properties, IAttributeSet, boolean)} if at least one of valueOld and valueNew can be converted to not-zero BigDecimals.
	 * 
	 * @param valueOld old attribute value. converted to BigDecimal using {@link ConversionHelper#toBigDecimal(Object)}
	 * @param valueNew analog to valueOld
	 */
	@Override
	public void onValueChanged(final IAttributeValueContext attributeValueContext,
			final IAttributeSet attributeSet,
			final org.compiere.model.I_M_Attribute attribute,
			final Object valueOld,
			final Object valueNew)
	{
		final BigDecimal valueOldBD = ConversionHelper.toBigDecimal(valueOld);
		final BigDecimal valueNewBD = ConversionHelper.toBigDecimal(valueNew);
		if (valueNewBD.signum() == 0 && valueOldBD.signum() == 0)
		{
			// nothing to change in this case
			return;
		}

		// task 08782: goal of this parameter: we don't want to reset a pre-existing value unless the procuser is actually changed.
		final boolean subProducerInitialized = valueNewBD.signum() != 0 && valueOldBD.signum() == 0;

		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);
		Services.get(ISubProducerAttributeBL.class).updateAttributesOnSubProducerChanged(ctx, attributeSet, subProducerInitialized);
	}

	@Override
	public Object generateSeedValue(final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute, final Object valueInitialDefault)
	{
		// we don't support a value different from null
		Check.assumeNull(valueInitialDefault, "valueInitialDefault null");
		return KEYNAMEPAIR_None;
	}

	@Override
	public boolean isReadonlyUI(final IAttributeValueContext ctx, final IAttributeSet attributeSet, final org.compiere.model.I_M_Attribute attribute)
	{
		final I_M_HU hu = Services.get(IHUAttributesBL.class).getM_HU_OrNull(attributeSet);
		if (hu == null)
		{
			// If there is no HU (e.g. ASI), consider it editable
			return false;
		}

		final String huStatus = hu.getHUStatus();
		if (!X_M_HU.HUSTATUS_Planning.equals(huStatus))
		{
			// Allow editing only Planning HUs
			return true;
		}

		return false;
	}

	@Override
	public NamePair getNullValue(final I_M_Attribute attribute)
	{
		return KEYNAMEPAIR_None;
	}
	
	@Override
	public boolean isHighVolume()
	{
		// NOTE: we don't expect to have too many sub-producers for one producer
		return false;
	}
}
