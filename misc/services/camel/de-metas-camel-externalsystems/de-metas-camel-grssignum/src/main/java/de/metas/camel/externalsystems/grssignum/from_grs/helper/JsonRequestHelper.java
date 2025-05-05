/*
 * #%L
 * de-metas-camel-grssignum
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.camel.externalsystems.grssignum.from_grs.helper;

import com.google.common.collect.ImmutableList;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonBOM;
import de.metas.camel.externalsystems.grssignum.to_grs.api.model.JsonProduct;
import de.metas.common.product.v2.JsonQualityAttribute;
import de.metas.common.product.v2.request.JsonRequestAllergenItem;
import de.metas.common.product.v2.request.JsonRequestUpsertProductAllergen;
import de.metas.common.product.v2.request.JsonRequestUpsertQualityAttribute;
import de.metas.common.rest_api.v2.SyncAdvise;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.List;

import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EU_AGRICULTURE;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.EU_NON_EU_AGRICULTURE;
import static de.metas.camel.externalsystems.grssignum.GRSSignumConstants.NON_EU_AGRICULTURE;

@UtilityClass
public class JsonRequestHelper
{
	@NonNull
	public static JsonRequestUpsertProductAllergen getAllergenUpsertRequest(@NonNull final List<JsonRequestAllergenItem> requestAllergenItems)
	{
		return JsonRequestUpsertProductAllergen.builder()
				.allergenList(requestAllergenItems)
				.syncAdvise(SyncAdvise.builder()
									.ifExists(SyncAdvise.IfExists.REPLACE)
									.ifNotExists(SyncAdvise.IfNotExists.CREATE)
									.build())
				.build();
	}

	@NonNull
	public JsonRequestUpsertQualityAttribute getQualityAttributeRequest(@NonNull final List<JsonQualityAttribute> qualityAttributeLabels)
	{
		return JsonRequestUpsertQualityAttribute.builder()
				.qualityAttributeLabels(qualityAttributeLabels)
				.syncAdvise(SyncAdvise.builder()
									.ifExists(SyncAdvise.IfExists.REPLACE)
									.ifNotExists(SyncAdvise.IfNotExists.CREATE)
									.build())
				.build();
	}

	@NonNull
	public ImmutableList.Builder<JsonQualityAttribute> initQualityAttributeLabelList(@NonNull final JsonBOM jsonBOM)
	{
		return toQualityAttributeListBuilder(jsonBOM.isBio(), jsonBOM.isHalal());
	}

	@NonNull
	public ImmutableList.Builder<JsonQualityAttribute> initQualityAttributeLabelList(@NonNull final JsonProduct jsonProduct)
	{
		return toQualityAttributeListBuilder(jsonProduct.isBio(), jsonProduct.isHalal());
	}

	@NonNull
	public JsonQualityAttribute getQualityLabelForAgricultureOrigin(@NonNull final String agricultureOrigin)
	{
		return switch (agricultureOrigin)
				{
					case EU_AGRICULTURE -> JsonQualityAttribute.EU_AGRICULTURE;
					case NON_EU_AGRICULTURE -> JsonQualityAttribute.NON_EU_AGRICULTURE;
					case EU_NON_EU_AGRICULTURE -> JsonQualityAttribute.EU_NON_EU_AGRICULTURE;
					default -> throw new RuntimeException("Agriculture Origin " + agricultureOrigin + " not supported!");
				};
	}

	@NonNull
	private ImmutableList.Builder<JsonQualityAttribute> toQualityAttributeListBuilder(
			final boolean isBio,
			final boolean isHalal)
	{
		final ImmutableList.Builder<JsonQualityAttribute> qualityAttributeLabelBuilder = new ImmutableList.Builder<>();

		if (isBio)
		{
			qualityAttributeLabelBuilder.add(JsonQualityAttribute.BIO);
		}

		if (isHalal)
		{
			qualityAttributeLabelBuilder.add(JsonQualityAttribute.HALAL);
		}

		return qualityAttributeLabelBuilder;
	}
}
