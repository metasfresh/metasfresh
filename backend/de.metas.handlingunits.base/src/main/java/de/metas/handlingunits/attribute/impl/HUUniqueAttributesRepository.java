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
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

@Repository
public class HUUniqueAttributesRepository
{

	final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void createHUUniqueAttributeIfNotExists(@NonNull final HUUniqueAttributeRequest huUniqueAttributeRequest)
	{
		final boolean existsHUUniqueAttribute = existsHUUniqueAttribute(huUniqueAttributeRequest);

		if(existsHUUniqueAttribute)
		{
			// hu was unique attribute was already recorded. Nothing to do
			return;
		}
		final I_M_HU_UniqueAttribute huUniqueAttributeRecord = newInstance(I_M_HU_UniqueAttribute.class);

		huUniqueAttributeRecord.setM_HU_PI_Attribute_ID(huUniqueAttributeRequest.getHuPIAttributeId().getRepoId());
		huUniqueAttributeRecord.setM_HU_Attribute_ID(huUniqueAttributeRequest.getHuAttributeId());
		huUniqueAttributeRecord.setM_HU_ID(huUniqueAttributeRequest.getHuId().getRepoId());
		huUniqueAttributeRecord.setM_Product_ID(huUniqueAttributeRequest.getProductId().getRepoId());
		huUniqueAttributeRecord.setM_Attribute_ID(huUniqueAttributeRequest.getAttributeId().getRepoId());
		huUniqueAttributeRecord.setValue(huUniqueAttributeRequest.getAttributeValue());

		save(huUniqueAttributeRecord);
	}

	private boolean existsHUUniqueAttribute(@NonNull final HUUniqueAttributeRequest huUniqueAttributeRequest)
	{
		return queryBL.createQueryBuilder(I_M_HU_UniqueAttribute.class)
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_HU_ID, huUniqueAttributeRequest.getHuId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_Product_ID, huUniqueAttributeRequest.getProductId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_M_Attribute_ID, huUniqueAttributeRequest.getAttributeId())
				.addEqualsFilter(I_M_HU_UniqueAttribute.COLUMNNAME_Value, huUniqueAttributeRequest.getAttributeValue())
				.create()
				.anyMatch();
	}

	public List<I_M_HU_Attribute> retrieveHUAttributes(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId)
	{
		return queryBL.createQueryBuilder(I_M_HU_Attribute.class)
				.addEqualsFilter(I_M_HU_Attribute.COLUMNNAME_M_HU_PI_Attribute_ID, huPIAttributeId)
				.create()
				.list(I_M_HU_Attribute.class);
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

	public void updateLinkedHUAttributes(@NonNull final HuPackingInstructionsAttributeId huPiAttributeId, final boolean isUnique)
	{
		final List<I_M_HU_Attribute> huAttributes = retrieveHUAttributes(huPiAttributeId);

		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
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
