/*
 * #%L
 * de.metas.swat.base
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

package de.metas.product.quality.attribute;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Quality_Attribute;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Repository
public class ProductQualityAttributeRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<ProductId, ProductQualityAttributeLabels> cache = CCache.<ProductId, ProductQualityAttributeLabels>builder()
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(100)
			.tableName(I_M_Quality_Attribute.Table_Name)
			.build();

	@NonNull
	public ProductQualityAttributeLabels getByProductId(@NonNull final ProductId productId)
	{
		return cache.getOrLoad(productId, this::retrieveByProductId);
	}

	public void saveQualityAttributesForProduct(@NonNull final SaveQualityAttributeRequest request)
	{
		final ProductQualityAttributeLabels qualityAttributeLabelsToStore = request.getProductQualityAttributeLabels();

		final Map<QualityAttributeLabel, I_M_Quality_Attribute> existingRecordByQualityAttributeLabel =
				getQualityAttributeLabel2QualityAttributeRecord(qualityAttributeLabelsToStore.getProductId());

		//delete obsolete qualityAttributes
		existingRecordByQualityAttributeLabel.keySet()
				.stream()
				.filter(qualityAttributeLabel -> !qualityAttributeLabelsToStore.hasQualityAttributeLabel(qualityAttributeLabel))
				.map(existingRecordByQualityAttributeLabel::get)
				.forEach(InterfaceWrapperHelper::delete);

		//insert new qualityAttributes
		qualityAttributeLabelsToStore.getQualityAttributeLabels()
				.stream()
				.filter(qualityAttributeLabel -> !existingRecordByQualityAttributeLabel.containsKey(qualityAttributeLabel))
				.forEach(qualityAttributeLabelToStore -> {
					final I_M_Quality_Attribute qualityAttributeRecord = InterfaceWrapperHelper.newInstance(I_M_Quality_Attribute.class);

					qualityAttributeRecord.setAD_Org_ID(request.getOrgId().getRepoId());
					qualityAttributeRecord.setM_Product_ID(qualityAttributeLabelsToStore.getProductId().getRepoId());
					qualityAttributeRecord.setQualityAttribute(qualityAttributeLabelToStore.getCode());

					saveRecord(qualityAttributeRecord);
				});
	}

	@NonNull
	private Map<QualityAttributeLabel, I_M_Quality_Attribute> getQualityAttributeLabel2QualityAttributeRecord(@NonNull final ProductId productId)
	{
		return streamForProductId(productId)
				.collect(ImmutableMap.toImmutableMap(record -> QualityAttributeLabel.ofCode(record.getQualityAttribute()),
													 Function.identity()));
	}

	@NonNull
	private ProductQualityAttributeLabels retrieveByProductId(@NonNull final ProductId productId)
	{
		final ImmutableSet<QualityAttributeLabel> qualityAttributeLabels = streamForProductId(productId)
				.map(I_M_Quality_Attribute::getQualityAttribute)
				.filter(Objects::nonNull)
				.map(QualityAttributeLabel::ofCode)
				.collect(ImmutableSet.toImmutableSet());

		return ProductQualityAttributeLabels.builder()
				.productId(productId)
				.qualityAttributeLabels(qualityAttributeLabels)
				.build();
	}

	@NonNull
	private Stream<I_M_Quality_Attribute> streamForProductId(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_Quality_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.addNotNull(I_M_Quality_Attribute.COLUMNNAME_QualityAttribute)
				.addEqualsFilter(I_M_Quality_Attribute.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream();
	}
}
