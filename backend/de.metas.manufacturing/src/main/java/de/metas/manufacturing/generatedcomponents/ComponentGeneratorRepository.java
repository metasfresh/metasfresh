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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.document.sequence.DocSequenceId;
import de.metas.javaclasses.JavaClassId;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_PP_ComponentGenerator;
import org.compiere.model.I_PP_ComponentGenerator_Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ComponentGeneratorRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, ComponentGeneratorMap> cache = CCache.<Integer, ComponentGeneratorMap>builder()
			.tableName(I_PP_ComponentGenerator.Table_Name)
			.additionalTableNameToResetFor(I_PP_ComponentGenerator_Param.Table_Name)
			.initialCapacity(1)
			.build();

	public Optional<ComponentGenerator> getByProductId(@NonNull final ProductId productId)
	{
		return getComponentGeneratorMap().getByProductId(productId);
	}

	private ComponentGeneratorMap getComponentGeneratorMap()
	{
		return cache.getOrLoad(0, this::retrieveComponentGeneratorMap);
	}

	/*package*/ void createDefaultParameters(
			@NonNull final ComponentGeneratorId componentGeneratorId,
			@NonNull final ComponentGeneratorParams defaultParameters)

	{
		for (final String parameterName : defaultParameters.getParameterNames())
		{
			final I_PP_ComponentGenerator_Param paramRecord = InterfaceWrapperHelper.newInstance(I_PP_ComponentGenerator_Param.class);
			paramRecord.setPP_ComponentGenerator_ID(componentGeneratorId.getRepoId());
			paramRecord.setName(parameterName);
			paramRecord.setParamValue(defaultParameters.getValue(parameterName));
			InterfaceWrapperHelper.save(paramRecord);
		}
	}

	private ComponentGeneratorMap retrieveComponentGeneratorMap()
	{
		final List<I_PP_ComponentGenerator> componentRecords = queryBL.createQueryBuilder(I_PP_ComponentGenerator.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
		if (componentRecords.isEmpty())
		{
			return ComponentGeneratorMap.EMPTY;
		}

		final ListMultimap<Integer, I_PP_ComponentGenerator_Param> paramsMap = queryBL.createQueryBuilder(I_PP_ComponentGenerator_Param.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.listMultimap(I_PP_ComponentGenerator_Param.class, I_PP_ComponentGenerator_Param::getPP_ComponentGenerator_ID);

		final ArrayList<ComponentGenerator> components = new ArrayList<>();
		for (final I_PP_ComponentGenerator componentRecord : componentRecords)
		{
			final List<I_PP_ComponentGenerator_Param> paramRecords = paramsMap.get(componentRecord.getPP_ComponentGenerator_ID());
			components.add(toComponentGenerator(componentRecord, paramRecords));
		}

		return new ComponentGeneratorMap(components);
	}

	private static ComponentGenerator toComponentGenerator(
			@NonNull final I_PP_ComponentGenerator component,
			@NonNull final List<I_PP_ComponentGenerator_Param> paramRecords)
	{
		return ComponentGenerator.builder()
				.componentId(ProductId.ofRepoId(component.getM_Product_ID()))
				.javaClassId(JavaClassId.ofRepoId(component.getAD_JavaClass_ID()))
				.params(toComponentGeneratorParams(component, paramRecords))
				.build();
	}

	private static ComponentGeneratorParams toComponentGeneratorParams(
			@NonNull final I_PP_ComponentGenerator component,
			@NonNull final List<I_PP_ComponentGenerator_Param> paramRecords)
	{
		final ImmutableMap<String, String> paramsMap = paramRecords.stream()
				.filter(param -> param.getName() != null && param.getParamValue() != null)
				.map(param -> GuavaCollectors.entry(param.getName(), param.getParamValue()))
				.collect(GuavaCollectors.toImmutableMap());

		return ComponentGeneratorParams.builder()
				// technical detail: the AD_Sequence_ID is just a param. We added it to the header instead of lines so that we get a nice search box for the user.
				.sequenceId(DocSequenceId.ofRepoIdOrNull(component.getAD_Sequence_ID()))
				.parameters(paramsMap)
				.build();
	}

	@ToString
	private static class ComponentGeneratorMap
	{
		public static final ComponentGeneratorMap EMPTY = new ComponentGeneratorMap(ImmutableList.of());

		private final ImmutableMap<ProductId, ComponentGenerator> generatorsByProductId;

		public ComponentGeneratorMap(@NonNull final List<ComponentGenerator> generators)
		{
			generatorsByProductId = Maps.uniqueIndex(generators, ComponentGenerator::getComponentId);
		}

		public Optional<ComponentGenerator> getByProductId(@NonNull final ProductId productId)
		{
			return Optional.ofNullable(generatorsByProductId.get(productId));
		}
	}
}

