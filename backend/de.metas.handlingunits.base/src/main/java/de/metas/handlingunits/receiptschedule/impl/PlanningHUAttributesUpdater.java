/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.receiptschedule.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.adempiere.service.ClientId;
import org.compiere.model.I_C_DocType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Optional;

class PlanningHUAttributesUpdater
{
	//
	// Services
	@NonNull private final IProductDAO productDAO;
	@NonNull private final IAttributeStorageFactory attributeStorageFactory;
	@NonNull private final IHUAttributesBL huAttributesBL;
	@NonNull private final IDocTypeDAO docTypeDAO;
	@NonNull private final ILotNumberBL lotNumberBL;

	//
	// Params
	@Nullable String fixedLotNumber;

	//
	// State
	private final HashMap<LotNumberSeqKey, Optional<String>> lotNumberFromSeqMap = new HashMap<>();

	@Builder
	private PlanningHUAttributesUpdater(
			@NonNull final IProductDAO productDAO,
			@NonNull final IAttributeStorageFactory attributeStorageFactory,
			@NonNull final IHUAttributesBL huAttributesBL,
			@NonNull final IDocTypeDAO docTypeDAO,
			@NonNull final ILotNumberBL lotNumberBL,
			@Nullable final String fixedLotNumber)
	{
		this.productDAO = productDAO;
		this.attributeStorageFactory = attributeStorageFactory;
		this.huAttributesBL = huAttributesBL;
		this.docTypeDAO = docTypeDAO;
		this.lotNumberBL = lotNumberBL;
		this.fixedLotNumber = fixedLotNumber;
	}

	public void updateAttributes(@NonNull final I_M_HU hu, @NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(hu);
		setAttributeLotNumber(huAttributes, hu, receiptSchedule);
		setAttributeBBD(huAttributes, receiptSchedule);
		setVendorValueFromReceiptSchedule(huAttributes, receiptSchedule);
	}

	private void setAttributeLotNumber(@NonNull final IAttributeStorage huAttributes, final @NonNull I_M_HU hu, @NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		if (!huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber))
		{
			return;
		}

		if (fixedLotNumber != null)
		{
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, fixedLotNumber);
		}
		else if (Check.isBlank(huAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
				&& huAttributesBL.isAutomaticallySetLotNumber()
		)
		{
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, hu.getValue());
		}
		else
		{
			final LotNumberSeqKey key = LotNumberSeqKey.builder()
					.docTypeId(DocTypeId.ofRepoId(receiptSchedule.getC_DocType_ID()))
					.clientId(ClientId.ofRepoId(receiptSchedule.getAD_Client_ID()))
					.build();

			getOrLoadLotNoFromSeq(key).ifPresent(lotNumber -> huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumber));
		}
		huAttributes.saveChangesIfNeeded();
	}

	private Optional<String> getOrLoadLotNoFromSeq(@NonNull final LotNumberSeqKey key)
	{
		return this.lotNumberFromSeqMap.computeIfAbsent(key, this::computeLotNumber);
	}

	private Optional<String> computeLotNumber(final LotNumberSeqKey key)
	{
		final I_C_DocType docType = docTypeDAO.getRecordById(key.getDocTypeId());
		final DocSequenceId lotNoSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getLotNo_Sequence_ID());
		if (lotNoSequenceId != null)
		{
			return lotNumberBL.getAndIncrementLotNo(LotNoContext.builder()
					.sequenceId(lotNoSequenceId)
					.clientId(key.getClientId())
					.build());
		}
		else
		{
			return Optional.empty();
		}
	}

	@Value
	@Builder
	private static class LotNumberSeqKey
	{
		@NonNull DocTypeId docTypeId;
		@NonNull ClientId clientId;
	}

	private void setAttributeBBD(@NonNull final IAttributeStorage huAttributes, @NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_BestBeforeDate)
				&& huAttributes.getValueAsLocalDate(AttributeConstants.ATTR_BestBeforeDate) == null
				&& huAttributesBL.isAutomaticallySetBestBeforeDate()
		)
		{
			final LocalDate bestBeforeDate = computeBestBeforeDate(ProductId.ofRepoId(receiptSchedule.getM_Product_ID()), TimeUtil.asLocalDate(receiptSchedule.getMovementDate()));
			if (bestBeforeDate != null)
			{
				huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
				huAttributes.saveChangesIfNeeded();
			}
		}
	}

	private void setVendorValueFromReceiptSchedule(@NonNull final IAttributeStorage huAttributes, @NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_Vendor_BPartner_ID)
				&& BPartnerId.ofRepoIdOrNull(huAttributes.getValueAsInt(AttributeConstants.ATTR_Vendor_BPartner_ID)) == null)
		{
			final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(receiptSchedule.getC_BPartner_ID());
			if (bPartnerId != null)
			{
				huAttributes.setValue(AttributeConstants.ATTR_Vendor_BPartner_ID, bPartnerId.getRepoId());
				huAttributes.setSaveOnChange(true);
				huAttributes.saveChangesIfNeeded();
			}
		}
	}

	@Nullable
	LocalDate computeBestBeforeDate(@NonNull final ProductId productId, final @NonNull LocalDate datePromised)
	{
		final int guaranteeDaysMin = productDAO.getProductGuaranteeDaysMinFallbackProductCategory(productId);

		if (guaranteeDaysMin <= 0)
		{
			return null;
		}

		return datePromised.plusDays(guaranteeDaysMin);
	}

}
