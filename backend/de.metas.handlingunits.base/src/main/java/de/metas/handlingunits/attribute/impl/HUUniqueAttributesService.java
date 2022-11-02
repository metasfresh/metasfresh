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
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUAttributesDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.handlingunits.model.I_M_HU_UniqueAttribute;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class HUUniqueAttributesService
{

	private static final String ERR_HU_Qty_Invalid_For_Unique_Attribute = "M_HU_UniqueAttribute_HUQtyError"; // TODO
	private static final String ERR_HU_Unique_Attribute_Duplicate = "M_HU_UniqueAttribute_DuplicateValue_Error"; // TODO
	private final HUUniqueAttributesRepository repo;
	final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);
	final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
	final IHUStorageDAO huStorageDAO = storageFactory.getHUStorageDAO();

	final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);

	public HUUniqueAttributesService(@NonNull final HUUniqueAttributesRepository repo)
	{
		this.repo = repo;
	}

	public void deleteHUUniqueAttributesForHUPIAttribute(final @NonNull HuPackingInstructionsAttributeId huPiAttributeId)
	{
		repo.deleteHUUniqueAttributesForHUPIAttribute(huPiAttributeId);
	}

	public void deleteHUUniqueAttributesForHUAttribute(@NonNull final I_M_HU_Attribute huAttribute)
	{
		repo.deleteHUUniqueAttributesForHUAttribute(huAttribute);
	}

	public void updateLinkedHUAttributes(@NonNull final HuPackingInstructionsAttributeId huPiAttributeId, final boolean isUnique)
	{
		repo.updateLinkedHUAttributes(huPiAttributeId, isUnique);
	}

	public void deleteHUUniqueAttributesForHUAttribute(@NonNull final HuId huId)
	{
		repo.deleteHUUniqueAttributesForHUAttribute(huId);
	}

	public void validateHU(@NonNull final HuId huId)
	{
		final I_M_HU hu = huDAO.getById(huId);
		huAttributesDAO.retrieveAttributesOrdered(hu).getHuAttributes()
				.stream()
				.filter(I_M_HU_Attribute::isUnique)
				.forEach(this::validateHUUniqueAttribute);
	}

	private void validateHUQty(@NonNull final I_M_HU_Storage huStorage, @NonNull AttributeId attributeId)
	{
		if(!uomDAO.isUOMEach(UomId.ofRepoId(huStorage.getC_UOM_ID()))
				&& BigDecimal.ONE.equals(huStorage.getQty()))
			throw new AdempiereException(AdMessageKey.of(ERR_HU_Qty_Invalid_For_Unique_Attribute), attributeId, uomDAO.getEachUOM());
	}

	public void validateHUUniqueAttribute(final I_M_HU_Attribute huAttribute)
	{
		final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));

		final List<I_M_HU_Storage> huStorages = huStorageDAO.retrieveStorages(huRecord);

		for (final I_M_HU_Storage huStorage : huStorages)
		{
			validateHUQty(huStorage, AttributeId.ofRepoId(huAttribute.getM_Attribute_ID()));
			final HUUniqueAttributeParameters parameters = HUUniqueAttributeParameters.builder()

					.huIdToIgnore(HuId.ofRepoId(huRecord.getM_HU_ID()))
					.productId(ProductId.ofRepoId(huStorage.getM_Product_ID()))
					.attributeId(AttributeId.ofRepoId(huAttribute.getM_Attribute_ID()))
					.attributeValue(huAttribute.getValue())
					.build();

			validateHUUniqueAttributeValue(parameters);
		}
	}
	private void validateHUUniqueAttributeValue(@NonNull final HUUniqueAttributeParameters parameters)
	{
		final I_M_HU_UniqueAttribute huUniqueAttribute = repo.retrieveHUUniqueAttributeForProductAndAttributeValue(parameters);

		if (huUniqueAttribute != null)
		{
			throw new AdempiereException(AdMessageKey.of(ERR_HU_Unique_Attribute_Duplicate), huUniqueAttribute.getM_HU_ID(),
										 huUniqueAttribute.getM_Attribute_ID(),
										 huUniqueAttribute.getValue(),
										 huUniqueAttribute.getM_Product_ID());
		}
	}


	public void createOrUpdateHUUniqueAttribute(@NonNull final I_M_HU_Attribute huAttribute)
	{
		final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));

		final List<I_M_HU_Storage> huStorages = huStorageDAO.retrieveStorages(huRecord);

		for (final I_M_HU_Storage huStorage : huStorages)
		{
			final HUUniqueAttributeCreateRequest request = HUUniqueAttributeCreateRequest.builder()
					.huPIAttributeId(HuPackingInstructionsAttributeId.ofRepoId(huAttribute.getM_HU_PI_Attribute_ID()))
					.huAttributeId(huAttribute.getM_HU_Attribute_ID())
					.huId(HuId.ofRepoId(huRecord.getM_HU_ID()))
					.productId(ProductId.ofRepoId(huStorage.getM_Product_ID()))
					.attributeId(AttributeId.ofRepoId(huAttribute.getM_Attribute_ID()))
					.attributeValue(huAttribute.getValue())
					.build();

			repo.createOrUpdateHUUniqueAttribute(request);
		}
	}

	public void validateHUUniqueAttribute(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId)
	{
		final List<I_M_HU_Attribute> huAttributes = repo.retrieveHUAttributes(huPIAttributeId);

		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			if (Check.isBlank(huAttribute.getValue()))
			{
				// nothing to do
				continue;
			}

			validateHUUniqueAttribute(huAttribute);
		}
	}
	public void createOrUpdateHUUniqueAttribute(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId)
	{
		final List<I_M_HU_Attribute> huAttributes = repo.retrieveHUAttributes(huPIAttributeId);

		for (final I_M_HU_Attribute huAttribute : huAttributes)
		{
			if (Check.isBlank(huAttribute.getValue()))
			{
				// nothing to do
				continue;
			}

			createOrUpdateHUUniqueAttribute(huAttribute);
		}
	}


	public void createOrUpdateHUUniqueAttribute(@NonNull final HuId huId)
	{
		final I_M_HU hu = huDAO.getById(huId);
		huAttributesDAO.retrieveAttributesOrdered(hu).getHuAttributes()
				.stream()
				.filter(I_M_HU_Attribute::isUnique)
				.forEach(this::createOrUpdateHUUniqueAttribute);
	}



}
