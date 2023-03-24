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

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.common.product.v2.request.JsonRequestUpsertQualityAttribute;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.StringUtils;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

@Service
public class QualityAttributeService
{
	@NonNull
	private final ProductQualityAttributeRepository productQualityAttributeRepository;

	public QualityAttributeService(@NonNull final ProductQualityAttributeRepository productQualityAttributeRepository)
	{
		this.productQualityAttributeRepository = productQualityAttributeRepository;
	}

	@NonNull
	public ProductQualityAttributeLabels getProductQualityAttributeLabels(@NonNull final ProductId productId)
	{
		return productQualityAttributeRepository.getByProductId(productId);
	}

	public void upsertProductQualityAttributes(
			@NonNull final I_AD_Org org,
			@NonNull final ProductId productId,
			@NonNull final JsonRequestUpsertQualityAttribute qualityAttributeRequest)
	{
		final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());

		final ProductQualityAttributeLabels currentProductQualityAttributeLabels = getProductQualityAttributeLabels(productId);
		final ImmutableSet<QualityAttributeLabel> incomingQualityAttributeLabels = qualityAttributeRequest.getQualityAttributeLabels()
				.stream()
				.map(QualityAttributeLabel::ofJson)
				.collect(ImmutableSet.toImmutableSet());

		final ImmutableSet<QualityAttributeLabel> missingQualityAttributeLabels = currentProductQualityAttributeLabels.getMissingQualityAttributeLabelsForProduct(incomingQualityAttributeLabels);

		if (qualityAttributeRequest.getSyncAdvise().isFailIfNotExists() && !missingQualityAttributeLabels.isEmpty())
		{
			throw new AdempiereException("No M_Quality_Attribute records were found for given QualityAttributeLabels!")
					.appendParametersToMessage()
					.setParameter("QualityAttributeLabels", StringUtils.join(missingQualityAttributeLabels, ","));
		}

		if (qualityAttributeRequest.getSyncAdvise().getIfExists().isReplace())
		{
			productQualityAttributeRepository.saveQualityAttributesForProduct(SaveQualityAttributeRequest.of(orgId, currentProductQualityAttributeLabels.withQualityAttributeLabels(incomingQualityAttributeLabels)));
		}
		else
		{
			productQualityAttributeRepository.saveQualityAttributesForProduct(SaveQualityAttributeRequest.of(orgId, currentProductQualityAttributeLabels.addQualityAttributeLabels(incomingQualityAttributeLabels)));
		}

		//dev-note: force label-tab to refresh
		CacheMgt.get().reset(I_M_Product.Table_Name, productId.getRepoId());
	}
}
