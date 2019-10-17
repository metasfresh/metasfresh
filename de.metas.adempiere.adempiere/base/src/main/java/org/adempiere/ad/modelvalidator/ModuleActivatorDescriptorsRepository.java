package org.adempiere.ad.modelvalidator;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_ModelValidator;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;

import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class ModuleActivatorDescriptorsRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public ModuleActivatorDescriptorsCollection getDescriptors()
	{
		final ImmutableList<ModuleActivatorDescriptor> descriptors = queryBL.createQueryBuilderOutOfTrx(I_AD_ModelValidator.class)
				// .addOnlyActiveRecordsFilter() // retrieve ALL
				.orderBy(I_AD_ModelValidator.COLUMNNAME_SeqNo)
				.orderBy(I_AD_ModelValidator.COLUMNNAME_AD_ModelValidator_ID)
				.create()
				.stream()
				.map(record -> toModelInterceptorDescriptorOrNull(record))
				.filter(Predicates.notNull())
				.collect(ImmutableList.toImmutableList());

		return ModuleActivatorDescriptorsCollection.of(descriptors);
	}

	private static ModuleActivatorDescriptor toModelInterceptorDescriptorOrNull(@NonNull final I_AD_ModelValidator record)
	{
		final String modelValidationClass = record.getModelValidationClass();
		if (Check.isEmpty(modelValidationClass, true))
		{
			return null;
		}

		return ModuleActivatorDescriptor.builder()
				.id(record.getAD_ModelValidator_ID())
				.classname(modelValidationClass.trim())
				.seqNo(record.getSeqNo())
				.active(record.isActive())
				.entityType(record.getEntityType())
				.description(record.getDescription())
				.created(TimeUtil.asInstant(record.getCreated()))
				.lastUpdated(TimeUtil.asInstant(record.getUpdated()))
				.build();
	}
}
