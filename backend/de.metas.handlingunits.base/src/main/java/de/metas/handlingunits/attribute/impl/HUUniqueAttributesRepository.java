/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.attribute.impl;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsAttributeId;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_UniqueAttribute;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class HUUniqueAttributesRepository
{
	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createOrUpdateHUUniqueAttribute(@NonNull final HUUniqueAttributeUpsertRequest huUniqueAttributeUpsertRequest)
	{
		final I_M_HU_UniqueAttribute existingHUUniqueAttribute = retrieveHUUniqueAttributeForHU(huUniqueAttributeUpsertRequest);

		if (existingHUUniqueAttribute != null)
		{
			// hu was unique attribute was already recorded. Update the value if needed
			if (!huUniqueAttributeUpsertRequest.getAttributeValue().equals(existingHUUniqueAttribute.getValue()))
			{
				existingHUUniqueAttribute.setValue(huUniqueAttributeUpsertRequest.getAttributeValue());
				save(existingHUUniqueAttribute);
			}

			return;
		}

		final I_M_HU_UniqueAttribute huUniqueAttributeRecord = newInstance(I_M_HU_UniqueAttribute.class);

		huUniqueAttributeRecord.setM_HU_PI_Attribute_ID(huUniqueAttributeUpsertRequest.getHuPIAttributeId().getRepoId());
		huUniqueAttributeRecord.setM_HU_Attribute_ID(huUniqueAttributeUpsertRequest.getHuAttributeId());
		huUniqueAttributeRecord.setM_HU_ID(huUniqueAttributeUpsertRequest.getHuId().getRepoId());
		huUniqueAttributeRecord.setM_Product_ID(huUniqueAttributeUpsertRequest.getProductId().getRepoId());
		huUniqueAttributeRecord.setM_Attribute_ID(huUniqueAttributeUpsertRequest.getAttributeId().getRepoId());
		huUniqueAttributeRecord.setValue(huUniqueAttributeUpsertRequest.getAttributeValue());

		save(huUniqueAttributeRecord);
	}

	private I_M_HU_UniqueAttribute retrieveHUUniqueAttributeForHU(@NonNull final HUUniqueAttributeUpsertRequest huUniqueAttributeUpsertRequest)
	{
		return queryBL.createQueryBuilder(I_M_HU_UniqueAttribute.class)
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_HU_ID, huUniqueAttributeUpsertRequest.getHuId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_Attribute_ID, huUniqueAttributeUpsertRequest.getAttributeId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_Product_ID, huUniqueAttributeUpsertRequest.getProductId())
				.create()
				.firstOnly(I_M_HU_UniqueAttribute.class);
	}

	public I_M_HU_UniqueAttribute retrieveHUUniqueAttributeForProductAndAttributeValue(@NonNull final HUUniqueAttributeParameters huUniqueAttributeParameters)
	{
		final IQueryBuilder<I_M_HU_UniqueAttribute> queryBuilder = queryBL.createQueryBuilder(I_M_HU_UniqueAttribute.class)
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_Product_ID, huUniqueAttributeParameters.getProductId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_Attribute_ID, huUniqueAttributeParameters.getAttributeId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_Value, huUniqueAttributeParameters.getAttributeValue());

		if (huUniqueAttributeParameters.getHuIdToIgnore() != null)
		{
			queryBuilder.addNotEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_HU_ID, huUniqueAttributeParameters.getHuIdToIgnore());
		}
		return queryBuilder
				.create()
				.firstOnly(I_M_HU_UniqueAttribute.class);
	}

	public Iterator<I_M_HU_Attribute> retrieveHUAttributes(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId)
	{
		return queryBL.createQueryBuilder(I_M_HU_Attribute.class)
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_PI_Attribute_ID, huPIAttributeId)
				.create()
				.iterate(I_M_HU_Attribute.class);
	}

	public void deleteHUUniqueAttributesForHUPIAttribute(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId)
	{
		queryBL.createQueryBuilder(I_M_HU_UniqueAttribute.class)
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_HU_PI_Attribute_ID, huPIAttributeId)
				.create().delete();
	}

	public void deleteHUUniqueAttributesForHUAttribute(@NonNull final I_M_HU_Attribute huAttribute)
	{
		queryBL.createQueryBuilder(I_M_HU_UniqueAttribute.class)
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_HU_Attribute_ID, huAttribute.getM_HU_Attribute_ID())
				.create().delete();
	}

	public void updateLinkedHUAttributes(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId, final boolean isUnique)
	{
		final Iterator<I_M_HU_Attribute> huAttributes = retrieveHUAttributes(huPIAttributeId);

		while (huAttributes.hasNext())
		{
			final I_M_HU_Attribute huAttribute = huAttributes.next();
			huAttribute.setIsUnique(isUnique);
			save(huAttribute);
		}
	}

	public void deleteHUUniqueAttributesForHUAttribute(@NonNull final HuId huId)
	{
		queryBL.createQueryBuilder(I_M_HU_UniqueAttribute.class)
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_HU_ID, huId)
				.create().delete();
	}
}
