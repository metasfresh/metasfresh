package de.metas.vertical.pharma.securpharm.service;

import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.HUTransformService.HUsToNewCUsRequest;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.product.ProductId;
import de.metas.vertical.pharma.securpharm.attribute.SecurPharmAttributesStatus;
import de.metas.vertical.pharma.securpharm.model.DataMatrixCode;
import de.metas.vertical.pharma.securpharm.model.SecurPharmProduct;
import de.metas.vertical.pharma.securpharm.model.schema.ExpirationDate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHandlingUnitsDAO handlingUnitsRepo;
	private final SecurPharmService securPharmService;

	//
	// Status
	private final Set<HuId> extractedCUIds = new HashSet<>();

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

		final IAttributeStorage attributeStorage = getHUAttributes(hu);
		if (!attributeStorage.hasAttribute(AttributeConstants.ATTR_SecurPharmScannedStatus)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_LotNr)
				|| !attributeStorage.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
		// !attributeStorage.hasAttribute(AttributeConstants.ATTR_SerialNo)
		)
		{
			// return ProcessPreconditionsResolution.rejectWithInternalReason("attributes missing");
			return false;
		}

		return true;
	}

	public void scanAndUpdateHUAttributes(@NonNull final DataMatrixCode dataMatrix, @NonNull final HuId huId)
	{
		final I_M_HU hu = handlingUnitsRepo.getById(huId);
		scanAndUpdateHUAttributes(dataMatrix, hu);
	}

	public void scanAndUpdateHUAttributes(@NonNull final DataMatrixCode dataMatrix, @NonNull final I_M_HU hu)
	{
		final HuId huId = HuId.ofRepoId(hu.getM_HU_ID());
		final SecurPharmProduct scannedProduct = securPharmService.getAndSaveProduct(dataMatrix, huId);

		//
		// Case: error while scanning
		if (scannedProduct.isError())
		{
			updateHUAttributes(hu, HUAttributesUpdateRequest.ERROR);
		}
		//
		// Case: product is OK
		else if (scannedProduct.isActive())
		{
			updateHUAttributes(hu, HUAttributesUpdateRequest.builder()
					.status(SecurPharmAttributesStatus.OK)
					.bestBeforeDate(scannedProduct.getProductDetails().getExpirationDate())
					.lotNo(scannedProduct.getProductDetails().getLot())
					.skipUpdatingSerialNo(true)
					.build());
		}
		//
		// Case: product is NOT OK (FRAUD)
		else
		{
			final I_M_HU cu = extractOneCU(hu);

			updateHUAttributes(cu, HUAttributesUpdateRequest.builder()
					.status(SecurPharmAttributesStatus.OK)
					.bestBeforeDate(scannedProduct.getProductDetails().getExpirationDate())
					.lotNo(scannedProduct.getProductDetails().getLot())
					.skipUpdatingSerialNo(false)
					.serialNo(scannedProduct.getProductDetails().getSerialNumber())
					.build());

			extractedCUIds.add(HuId.ofRepoId(cu.getM_HU_ID()));
		}
	}

	public Set<HuId> getExtractedCUIds()
	{
		return ImmutableSet.copyOf(extractedCUIds);
	}

	private I_M_HU extractOneCU(@NonNull final I_M_HU hu)
	{
		final IHUProductStorage huProductStorage = getProductStorage(hu);
		final List<I_M_HU> result = HUTransformService
				.newInstance()
				.husToNewCUs(HUsToNewCUsRequest.builder()
						.productId(huProductStorage.getProductId())
						.qtyCU(huProductStorage.getQty().toOne())
						.sourceHU(hu)
						.build());

		final I_M_HU cu;
		if (result.isEmpty())
		{
			throw new AdempiereException("Failed extracting 1 CU from " + hu.getValue());
		}
		else if (result.size() == 1)
		{
			cu = result.get(0);
		}
		else
		{
			cu = result.get(0);
		}

		return cu;
	}

	private IAttributeStorage getHUAttributes(final I_M_HU hu)
	{
		final IContextAware ctxAware = getContextAware(hu);
		final IHUContext huContext = handlingUnitsBL.createMutableHUContext(ctxAware);
		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		return attributeStorageFactory.getAttributeStorage(hu);
	}

	private IHUProductStorage getProductStorage(final I_M_HU hu)
	{
		final IHUStorage huStorage = handlingUnitsBL
				.getStorageFactory()
				.getStorage(hu);

		final ProductId productId = huStorage.getSingleProductIdOrNull();
		if (productId == null)
		{
			throw new AdempiereException("HU is empty");
		}

		return huStorage.getProductStorage(productId);
	}

	private void updateHUAttributes(
			@NonNull final I_M_HU hu,
			@NonNull final HUAttributesUpdateRequest from)
	{
		final IAttributeStorage huAttributes = getHUAttributes(hu);
		updateHUAttributes(huAttributes, from);
		huAttributes.saveChangesIfNeeded();
	}

	private static void updateHUAttributes(
			@NonNull final IAttributeStorage huAttributes,
			@NonNull final HUAttributesUpdateRequest from)
	{
		huAttributes.setValue(AttributeConstants.ATTR_SecurPharmScannedStatus, from.getStatus().getCode());

		if (!from.isSkipUpdatingBestBeforeDate())
		{
			final ExpirationDate bestBeforeDate = from.getBestBeforeDate();
			huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate != null ? bestBeforeDate.toLocalDate() : null);
		}

		if (!from.isSkipUpdatingLotNo())
		{
			huAttributes.setValue(AttributeConstants.ATTR_LotNr, from.getLotNo());
		}

		if (!from.isSkipUpdatingSerialNo())
		{
			huAttributes.setValue(AttributeConstants.ATTR_SerialNo, from.getSerialNo());
		}
	}

	@Value
	@Builder
	private static class HUAttributesUpdateRequest
	{
		public static final HUAttributesUpdateRequest ERROR = builder()
				.status(SecurPharmAttributesStatus.ERROR)
				// UPDATE just the status field, skip the others
				.skipUpdatingBestBeforeDate(true)
				.skipUpdatingLotNo(true)
				.skipUpdatingSerialNo(true)
				.build();

		@NonNull
		SecurPharmAttributesStatus status;

		boolean skipUpdatingBestBeforeDate;
		@Nullable
		ExpirationDate bestBeforeDate;

		boolean skipUpdatingLotNo;
		@Nullable
		String lotNo;

		boolean skipUpdatingSerialNo;
		String serialNo;
	}
}
