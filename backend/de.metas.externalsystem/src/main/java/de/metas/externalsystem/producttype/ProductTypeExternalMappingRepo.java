/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.externalsystem.producttype;

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.ExternalSystemParentConfigId;
import de.metas.externalsystem.model.I_ProductType_External_Mapping;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

@Repository
public class ProductTypeExternalMappingRepo
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull
	public ImmutableList<ProductTypeExternalMapping> getByConfigId(@NonNull final ExternalSystemParentConfigId parentConfigId)
	{
		return queryBL.createQueryBuilder(I_ProductType_External_Mapping.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_ProductType_External_Mapping.COLUMNNAME_ExternalSystem_Config_ID, parentConfigId)
				.create()
				.stream()
				.map(ProductTypeExternalMappingRepo::ofRecord)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private static ProductTypeExternalMapping ofRecord(@NonNull final I_ProductType_External_Mapping record)
	{
		return ProductTypeExternalMapping.builder()
				.productTypeExternalMappingId(ProductTypeExternalMappingId.ofRepoId(record.getProductType_External_Mapping_ID()))
				.externalValue(record.getExternalValue())
				.value(record.getValue())
				.externalSystemParentConfigId(ExternalSystemParentConfigId.ofRepoId(record.getExternalSystem_Config_ID()))
				.build();
	}
}
