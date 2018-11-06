package de.metas.materialtracking.attributes;

import lombok.NonNull;
import lombok.Value;

import java.util.List;

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValuesProvider;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.KeyNamePair;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.BPartnerId;
import de.metas.cache.CCache;
import de.metas.cache.CCache.CCacheStats;
import de.metas.materialtracking.IMaterialTrackingDAO;
import de.metas.materialtracking.IMaterialTrackingQuery;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.product.ProductId;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.materialtracking
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class MaterialTrackingAttributeValuesProvider implements IAttributeValuesProvider
{

	private static final String CACHE_PREFIX = I_M_Material_Tracking.Table_Name;

	private final CCache<ProductAndBPartner, List<KeyNamePair>> productAndBPartner2materialTrackings = CCache.<ProductAndBPartner, List<KeyNamePair>> builder()
			.cacheName(CACHE_PREFIX + "#by#" + I_M_Material_Tracking.COLUMNNAME_M_Product_ID + "#" + I_M_Material_Tracking.COLUMNNAME_C_BPartner_ID)
			.initialCapacity(10)
			.build();

	private final CCache<String, KeyNamePair> key2materialTracking = CCache.<String, KeyNamePair> builder()
			.cacheName(CACHE_PREFIX + "#by#" + I_M_Material_Tracking.COLUMNNAME_M_Material_Tracking_ID)
			.initialCapacity(10)
			.build();

	@Value
	private static final class ProductAndBPartner
	{
		ProductId productId;
		BPartnerId bPartnerId;
	}

	public MaterialTrackingAttributeValuesProvider(final I_M_Attribute attributeRecord)
	{

	}

	/**
	 * @return "List", because it's the type of the "M_Material_Tracking_ID" M_Attribute record
	 */
	@Override
	public String getAttributeValueType()
	{
		return X_M_Attribute.ATTRIBUTEVALUETYPE_List;
	}

	@Override
	public boolean isAllowAnyValue()
	{
		return false;
	}

	@Override
	public Evaluatee prepareContext(IAttributeSet attributeSet)
	{
		return null; // nothing to prepare
	}

	@Override
	public List<? extends NamePair> getAvailableValues(@NonNull final Evaluatee evalCtx)
	{
		final ProductAndBPartner productAndBPartner = extractProductAndBPartnerOrNull(evalCtx);
		if (productAndBPartner == null)
		{
			return ImmutableList.of();
		}
		return productAndBPartner2materialTrackings.getOrLoad(productAndBPartner, p -> createNamePairs(p));
	}

	private List<KeyNamePair> createNamePairs(@NonNull final ProductAndBPartner productAndBPartner)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);
		final List<I_M_Material_Tracking> materialTrackingRecords = materialTrackingDAO.retrieveMaterialTrackings(Env.getCtx(), asMaterialTrackingQuery(productAndBPartner));

		final ImmutableList.Builder<KeyNamePair> result = ImmutableList.builder();
		final ImmutableMap.Builder<String, KeyNamePair> id2NamePair = ImmutableMap.builder();

		for (final I_M_Material_Tracking materialTrackingRecord : materialTrackingRecords)
		{
			final KeyNamePair valueNamePair = new KeyNamePair(
					materialTrackingRecord.getM_Material_Tracking_ID(),
					materialTrackingRecord.getLot());
			result.add(valueNamePair);

			id2NamePair.put(
					Integer.toString(materialTrackingRecord.getM_Material_Tracking_ID()),
					valueNamePair);
		}

		key2materialTracking.putAll(id2NamePair.build());

		return result.build();
	}

	private ProductAndBPartner extractProductAndBPartnerOrNull(@NonNull final Evaluatee evalCtx)
	{
		final int productId = evalCtx.get_ValueAsInt(I_M_Material_Tracking.COLUMNNAME_M_Product_ID, -1);
		final int bPartnerId = evalCtx.get_ValueAsInt(I_M_Material_Tracking.COLUMNNAME_C_BPartner_ID, -1);

		if (productId <= 0 || bPartnerId <= 0)
		{
			return null;
		}
		return new ProductAndBPartner(ProductId.ofRepoId(productId), BPartnerId.ofRepoId(bPartnerId));
	}

	private IMaterialTrackingQuery asMaterialTrackingQuery(@NonNull final ProductAndBPartner productAndBPartner)
	{
		final IMaterialTrackingDAO materialTrackingDAO = Services.get(IMaterialTrackingDAO.class);

		final IMaterialTrackingQuery materialTrackingQuery = materialTrackingDAO
				.createMaterialTrackingQuery()
				.setProcessed(false) // only show "open" trackings
				.setM_Product_ID(productAndBPartner.getProductId().getRepoId())
				.setC_BPartner_ID(productAndBPartner.getBPartnerId().getRepoId());

		return materialTrackingQuery;
	}

	@Override
	public NamePair getAttributeValueOrNull(Evaluatee evalCtx, Object valueKey)
	{
		final ProductAndBPartner productAndBPartner = extractProductAndBPartnerOrNull(evalCtx);
		productAndBPartner2materialTrackings.getOrLoad(productAndBPartner, p -> createNamePairs(p));

		return key2materialTracking.get(normalizeValueKey(valueKey));
	}

	private static final String normalizeValueKey(final Object valueKey)
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
	public int getM_AttributeValue_ID(Object valueKey)
	{
		return -1;
	}

	/** @return {@code null}. */
	@Override
	public NamePair getNullValue()
	{
		return null;
	}

	@Override
	public boolean isHighVolume()
	{
		return false;
	}

	@Override
	public String getCachePrefix()
	{
		return CACHE_PREFIX;
	}

	@Override
	public List<CCacheStats> getCacheStats()
	{
		return ImmutableList.of(productAndBPartner2materialTrackings.stats());
	}

}
