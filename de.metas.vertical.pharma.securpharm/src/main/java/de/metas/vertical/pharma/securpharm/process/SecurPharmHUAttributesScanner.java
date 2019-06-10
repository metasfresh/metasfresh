package de.metas.vertical.pharma.securpharm.process;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.util.List;
import java.util.Objects;

import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.logging.LogManager;
import de.metas.vertical.pharma.securpharm.attribute.SecurPharmAttributesStatus;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.ProductDetails;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.model.schema.ExpirationDate;
import de.metas.vertical.pharma.securpharm.service.SecurPharmService;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-pharma.securpharm
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

public class SecurPharmHUAttributesScanner
{
	private static final Logger logger = LogManager.getLogger(SecurPharmHUAttributesScanner.class);

	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHandlingUnitsDAO handlingUnitsRepo;
	private final SecurPharmService securPharmService;

	@Builder
	private SecurPharmHUAttributesScanner(
			@NonNull final SecurPharmService securPharmService,
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IHandlingUnitsDAO handlingUnitsRepo)
	{
		this.securPharmService = securPharmService;
		this.handlingUnitsBL = handlingUnitsBL;
		this.handlingUnitsRepo = handlingUnitsRepo;
	}

	public boolean isEligible(@NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsRepo.getById(huId);

		if (Objects.equals(hu.getHUStatus(), X_M_HU.HUSTATUS_Destroyed)
				|| Objects.equals(hu.getHUStatus(), X_M_HU.HUSTATUS_Shipped))
		{
			// return ProcessPreconditionsResolution.rejectWithInternalReason("handling unit status not appropriate");
			return false;
		}

		final IAttributeStorage attributeStorage = getAttributeStorage(hu);
		if (!attributeStorage.hasAttribute(AttributeConstants.ATTR_SerialNo)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_LotNr)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_Scanned))
		{
			// return ProcessPreconditionsResolution.rejectWithInternalReason("attributes missing");
			return false;
		}

		return true;
	}

	public void scanAndUpdate(@NonNull final DataMatrixCode dataMatrix, @NonNull final I_M_HU hu)
	{
		final IAttributeStorage huAttributes = getAttributeStorage(hu);

		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final SecurPharmProduct product = securPharmService.getAndSaveProduct(dataMatrix, huId);
		final SecurpharmAttributes scannedSecurPharmAttributes = extractSecurpharmAttributes(product);

		if (scannedSecurPharmAttributes.getStatus().isError())
		{
			updateHUAttributes(huAttributes, scannedSecurPharmAttributes);
		}
		else if (scannedSecurPharmAttributes.getStatus().isUnknown())
		{
			// shall not happen
			logger.warn("Skip because status is NOT SCANNED: {}", scannedSecurPharmAttributes);
			return;
		}
		else
		{
			final SecurpharmAttributes huSecurPharmAttributes = extractSecurpharmAttributes(huAttributes);
			if (huSecurPharmAttributes.canAssignFrom(scannedSecurPharmAttributes))
			{
				updateHUAttributes(huAttributes, scannedSecurPharmAttributes);
			}
			else
			{
				final String huStatus = hu.getHUStatus();
				final I_M_HU existingHU = getExistingHU(scannedSecurPharmAttributes, huStatus);

				// TODO: split 1 CU and move it to new or existing HU
			}
		}

		huAttributes.saveChangesIfNeeded();
	}

	private IAttributeStorage getAttributeStorage(final I_M_HU hu)
	{
		final IContextAware ctxAware = getContextAware(hu);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		return attributeStorageFactory.getAttributeStorage(hu);
	}

	private static SecurpharmAttributes extractSecurpharmAttributes(final IAttributeStorage huAttributes)
	{
		final SecurPharmAttributesStatus status = Util.coalesce(
				SecurPharmAttributesStatus.ofNullableCode(huAttributes.getValueAsString(AttributeConstants.ATTR_Scanned)),
				SecurPharmAttributesStatus.UNKNOW);

		final ExpirationDate bestBeforeDate = ExpirationDate.ofNullableLocalDate(huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate));

		final String serialNo = huAttributes.getValueAsString(AttributeConstants.ATTR_SerialNo);
		final String lotNo = huAttributes.getValueAsString(AttributeConstants.ATTR_LotNr);

		return SecurpharmAttributes.builder()
				.status(status)
				.bestBeforeDate(bestBeforeDate)
				.serialNo(serialNo)
				.lotNo(lotNo)
				.build();
	}

	private static SecurpharmAttributes extractSecurpharmAttributes(final SecurPharmProduct product)
	{
		if (!product.isError())
		{
			final ProductDetails productDetails = product.getProductDetails();
			if (productDetails.isActive())
			{
				return SecurpharmAttributes.builder()
						.status(SecurPharmAttributesStatus.OK)
						.bestBeforeDate(productDetails.getExpirationDate())
						.serialNo(productDetails.getSerialNumber())
						.lotNo(productDetails.getLot())
						.build();
			}
			else
			{
				// FRAUD!
				return SecurpharmAttributes.builder()
						.status(SecurPharmAttributesStatus.FRAUD)
						.bestBeforeDate(productDetails.getExpirationDate())
						.serialNo(productDetails.getSerialNumber())
						.lotNo(productDetails.getLot())
						.build();
			}
		}
		else
		{
			// Failed to validate
			return SecurpharmAttributes.builder()
					.status(SecurPharmAttributesStatus.ERROR)
					.build();
		}
	}

	private static void updateHUAttributes(
			@NonNull final IAttributeStorage huAttributes,
			@NonNull final SecurpharmAttributes from)
	{
		huAttributes.setValue(AttributeConstants.ATTR_Scanned, from.getStatus().getCode());

		final ExpirationDate bestBeforeDate = from.getBestBeforeDate();
		huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate != null ? bestBeforeDate.toLocalDate() : null);
		huAttributes.setValue(AttributeConstants.ATTR_SerialNo, from.getSerialNo());
		huAttributes.setValue(AttributeConstants.ATTR_LotNr, from.getLotNo());
	}

	private I_M_HU getExistingHU(
			@NonNull final SecurpharmAttributes attributes,
			@NonNull final String huStatus)
	{
		final ExpirationDate bestBeforeDate = attributes.getBestBeforeDate();

		final List<I_M_HU> hus = handlingUnitsRepo.createHUQueryBuilder()
				.addOnlyWithAttribute(AttributeConstants.ATTR_Scanned, attributes.getStatus().getCode())
				.addOnlyWithAttribute(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate != null ? bestBeforeDate.toTimestamp() : null)
				.addOnlyWithAttribute(AttributeConstants.ATTR_SerialNo, attributes.getSerialNo())
				.addOnlyWithAttribute(AttributeConstants.ATTR_LotNr, attributes.getLotNo())
				.addHUStatusToInclude(huStatus)
				.createQueryBuilder()
				.orderByDescending(I_M_HU.COLUMN_M_HU_ID)
				.setLimit(2)
				.create()
				.list();

		if (hus.isEmpty())
		{
			return null;
		}
		else if (hus.size() == 1)
		{
			return hus.get(0);
		}
		else
		{
			logger.warn("More than one HU found for {} and huStatus={}. Returning first one: {}", attributes, huStatus, hus);
			return hus.get(0);
		}
	}
}
