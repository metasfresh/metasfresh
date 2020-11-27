/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import com.google.common.collect.ImmutableMap;
import de.metas.javaclasses.JavaClassId;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_PP_ComponentGenerator;
import org.compiere.model.I_PP_ComponentGenerator_Param;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ComponentGeneratorRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	// TODO tbp: CCache this

	public Optional<ComponentGenerator> getByProductId(@NonNull final ProductId productId)
	{
		final I_PP_ComponentGenerator po = queryBL.createQueryBuilder(I_PP_ComponentGenerator.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_ComponentGenerator.COLUMNNAME_M_Product_ID, productId)
				.create()
				.first();

		if (po == null)
		{
			return Optional.empty();
		}

		final List<I_PP_ComponentGenerator_Param> paramsPO = queryBL.createQueryBuilder(I_PP_ComponentGenerator_Param.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_PP_ComponentGenerator_Param.COLUMNNAME_PP_ComponentGenerator_ID, po.getPP_ComponentGenerator_ID())
				.create()
				.list();

		final ImmutableMap<String, String> params = paramsPO.stream()
				.map(param -> GuavaCollectors.entry(param.getName(), param.getValue()))
				.collect(GuavaCollectors.toImmutableMap());

		return Optional.of(ComponentGenerator.builder()
								   .javaClassId(JavaClassId.ofRepoId(po.getAD_JavaClass_ID()))
								   .params(ComponentGeneratorParams.of(params))
								   .build());
	}

	void generateDefaultParameters(@NonNull final I_PP_ComponentGenerator componentGenerator, @NonNull final ImmutableMap<String, String> defaultParameters)
	{
		for (final Map.Entry<String, String> param : defaultParameters.entrySet())
		{
			final I_PP_ComponentGenerator_Param paramPo = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator_Param.class);
			paramPo.setPP_ComponentGenerator_ID(componentGenerator.getPP_ComponentGenerator_ID());
			paramPo.setName(param.getKey());
			paramPo.setValue(param.getValue());
			InterfaceWrapperHelper.save(paramPo);
		}
	}
}

@Value
@Builder
class ComponentGenerator
{
	@NonNull JavaClassId javaClassId;
	@NonNull ComponentGeneratorParams params;
}
