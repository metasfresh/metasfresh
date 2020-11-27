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
import com.google.common.collect.ListMultimap;
import de.metas.cache.CCache;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ComponentGeneratorRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ImmutableMap<ProductId, ComponentGenerator>> cache = CCache.<Integer, ImmutableMap<ProductId, ComponentGenerator>>builder()
			.tableName(I_PP_ComponentGenerator.Table_Name)
			.additionalTableNameToResetFor(I_PP_ComponentGenerator_Param.Table_Name)
			.initialCapacity(1)
			.build();

	public Optional<ComponentGenerator> getByProductId(@NonNull final ProductId productId)
	{
		return Optional.ofNullable(cache.getOrLoad(0, this::loadAllGeneratorsAndParams).get(productId));
	}

	/*package*/ void generateDefaultParameters(@NonNull final I_PP_ComponentGenerator componentGenerator, @NonNull final ImmutableMap<String, String> defaultParameters)

	{
		for (final Map.Entry<String, String> param : defaultParameters.entrySet())
		{
			final I_PP_ComponentGenerator_Param paramPo = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator_Param.class);
			paramPo.setPP_ComponentGenerator_ID(componentGenerator.getPP_ComponentGenerator_ID());
			paramPo.setName(param.getKey());
			paramPo.setParamValue(param.getValue());
			InterfaceWrapperHelper.save(paramPo);
		}
	}

	private ImmutableMap<ProductId, ComponentGenerator> loadAllGeneratorsAndParams()
	{
		final ImmutableMap.Builder<ProductId, ComponentGenerator> generatorsBuilder = ImmutableMap.builder();

		final List<I_PP_ComponentGenerator> components = queryBL.createQueryBuilder(I_PP_ComponentGenerator.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final ListMultimap<Integer, I_PP_ComponentGenerator_Param> paramsMap = queryBL.createQueryBuilder(I_PP_ComponentGenerator_Param.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listMultimap(I_PP_ComponentGenerator_Param.class, I_PP_ComponentGenerator_Param::getPP_ComponentGenerator_ID);

		for (final I_PP_ComponentGenerator component : components)
		{
			final List<I_PP_ComponentGenerator_Param> paramsPO = paramsMap.get(component.getPP_ComponentGenerator_ID());
			ImmutableMap<String, String> params = paramsPO.stream()
					.map(param -> GuavaCollectors.entry(param.getName(), param.getParamValue()))
					.collect(GuavaCollectors.toImmutableMap());

			// technical detail: the AD_Sequence_ID is just a param. We added it to the header instead of lines so that we get a nice search box for the user.
			if (component.getAD_Sequence_ID() > 0)
			{
				params = ImmutableMap.<String, String>builder()
						.putAll(params)
						.put(ComponentGeneratorUtil.PARAM_AD_SEQUENCE_ID, Integer.toString(component.getAD_Sequence_ID()))
						.build();
			}

			generatorsBuilder.put(
					ProductId.ofRepoId(component.getM_Product_ID()),
					ComponentGenerator.builder()
							.javaClassId(JavaClassId.ofRepoId(component.getAD_JavaClass_ID()))
							.params(ComponentGeneratorParams.of(params))
							.build());
		}
		return generatorsBuilder.build();
	}
}

@Value
@Builder
class ComponentGenerator
{
	@NonNull JavaClassId javaClassId;
	@NonNull ComponentGeneratorParams params;
}
