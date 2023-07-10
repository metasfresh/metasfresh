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
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.storage.IHUStorageDAO;
import de.metas.handlingunits.storage.IHUStorageFactory;
import de.metas.i18n.AdMessageKey;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;

@Service
public class HUUniqueAttributesService
{

	private static final String ERR_HU_Qty_Invalid_For_Unique_Attribute = "M_HU_UniqueAttribute_HUQtyError";
	private static final String ERR_HU_Unique_Attribute_Duplicate = "M_HU_UniqueAttribute_DuplicateValue_Error";
	final IHandlingUnitsDAO huDAO = Services.get(IHandlingUnitsDAO.class);
	final IHUStorageFactory storageFactory = Services.get(IHandlingUnitsBL.class).getStorageFactory();
	final IHUStorageDAO huStorageDAO = storageFactory.getHUStorageDAO();
	final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	final IHUAttributesDAO huAttributesDAO = Services.get(IHUAttributesDAO.class);
	final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	final IProductDAO productDAO = Services.get(IProductDAO.class);
	final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final HUUniqueAttributesRepository repo;

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

	private void validateHUQty(@NonNull final I_M_HU_Storage huStorage, @NonNull final AttributeId attributeId)
	{
		if (!(uomDAO.isUOMEach(UomId.ofRepoId(huStorage.getC_UOM_ID()))
				&& BigDecimal.ONE.equals(huStorage.getQty())))
		{
			final I_M_Attribute attribute = attributeDAO.getAttributeById(attributeId);
			throw new AdempiereException(AdMessageKey.of(ERR_HU_Qty_Invalid_For_Unique_Attribute), attribute.getName(), huStorage.getM_HU_ID(), uomDAO.getEachUOM().getName())
					.markAsUserValidationError();
		}
	}

	public void validateHUUniqueAttribute(@NonNull final I_M_HU_Attribute huAttribute)
	{
		if (Check.isBlank(huAttribute.getValue()))
		{
			// nothing to do
			return;
		}
		final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));

		if (belongsToQualityWarehouse(huRecord))
		{
			// don't validate unique attributes in the quality warehouse
			return;
		}

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
			final I_M_Attribute attribute = attributeDAO.getAttributeById(AttributeId.ofRepoId(huUniqueAttribute.getM_Attribute_ID()));
			final I_M_Product product = productDAO.getById(ProductId.ofRepoId(huUniqueAttribute.getM_Product_ID()));
			throw new AdempiereException(AdMessageKey.of(ERR_HU_Unique_Attribute_Duplicate), huUniqueAttribute.getM_HU_ID(),
										 attribute.getName(),
										 huUniqueAttribute.getValue(),
										 product.getName())
					.markAsUserValidationError();
		}
	}

	public void createOrUpdateHUUniqueAttribute(@NonNull final I_M_HU_Attribute huAttribute)
	{
		if (Check.isBlank(huAttribute.getValue()))
		{
			// nothing to do
			return;
		}
		final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));

		if (belongsToQualityWarehouse(huRecord))
		{
			// don't validate unique attributes in the quality warehouse
			return;
		}

		final List<I_M_HU_Storage> huStorages = huStorageDAO.retrieveStorages(huRecord);

		for (final I_M_HU_Storage huStorage : huStorages)
		{
			final HUUniqueAttributeUpsertRequest request = HUUniqueAttributeUpsertRequest.builder()
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
		final Iterator<I_M_HU_Attribute> huAttributes = repo.retrieveHUAttributes(huPIAttributeId);

		while (huAttributes.hasNext())
		{
			final I_M_HU_Attribute huAttribute = huAttributes.next();
			if (Check.isBlank(huAttribute.getValue()))
			{
				// nothing to do
				continue;
			}

			final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));
			if (!huStatusBL.isQtyOnHand(huRecord.getHUStatus()))
			{
				// don't validate HU Statuses that are not qtyOnHand here
				continue;
			}

			validateHUUniqueAttribute(huAttribute);
		}

	}

	public void createOrUpdateHUUniqueAttribute(@NonNull final HuPackingInstructionsAttributeId huPIAttributeId)
	{
		final Iterator<I_M_HU_Attribute> huAttributes = repo.retrieveHUAttributes(huPIAttributeId);

		while (huAttributes.hasNext())
		{
			final I_M_HU_Attribute huAttribute = huAttributes.next();

			final I_M_HU huRecord = huDAO.getById(HuId.ofRepoId(huAttribute.getM_HU_ID()));

			if (!huStatusBL.isQtyOnHand(huRecord.getHUStatus()))
			{
				// nothing to do for non-qtyOnHand statuses
				continue;
			}
			createOrUpdateHUUniqueAttribute(huAttribute);
		}
	}

	public void createOrUpdateHUUniqueAttribute(@NonNull final HuId huId)
	{
		final I_M_HU huRecord = huDAO.getById(huId);
		huAttributesDAO.retrieveAttributesOrdered(huRecord).getHuAttributes()
				.stream()
				.filter(I_M_HU_Attribute::isUnique)
				.forEach(this::createOrUpdateHUUniqueAttribute);
	}

	public void validateASI(@NonNull final AttributeSetInstanceId attributeSetInstanceId, @NonNull final ProductId productId)
	{
		if (!attributeSetInstanceId.isRegular())
		{
			//nothing to do
			return;
		}

		final List<I_M_AttributeInstance> attributeInstances = attributeDAO.retrieveAttributeInstances(attributeSetInstanceId);

		for (final I_M_AttributeInstance attributeInstance : attributeInstances)
		{
			if (Check.isBlank(attributeInstance.getValue()))
			{
				continue;
			}

			final HUUniqueAttributeParameters parameters = HUUniqueAttributeParameters.builder()
					.productId(productId)
					.attributeId(AttributeId.ofRepoId(attributeInstance.getM_Attribute_ID()))
					.attributeValue(attributeInstance.getValue())
					.build();

			validateHUUniqueAttributeValue(parameters);
		}
	}

	public boolean belongsToQualityWarehouse(@NonNull final I_M_HU hu)
	{
		final I_M_Warehouse huWarehouse = IHandlingUnitsBL.extractWarehouseOrNull(hu);
		return huWarehouse == null ? false : huWarehouse.isQualityReturnWarehouse();
	}
}
