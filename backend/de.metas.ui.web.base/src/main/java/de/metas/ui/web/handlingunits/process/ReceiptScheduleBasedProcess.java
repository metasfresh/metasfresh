package de.metas.ui.web.handlingunits.process;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.document.sequence.DocSequenceId;
import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.ui.web.receiptSchedule.HUsToReceiveViewFactory;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_DocType;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

public abstract class ReceiptScheduleBasedProcess extends JavaProcess implements IProcessPrecondition
{
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);
	private final ILotNumberBL lotNumberBL = Services.get(ILotNumberBL.class);

	private Optional<String> lotNumberFromSeq = null;

	protected final void openHUsToReceive(final Collection<I_M_HU> hus)
	{
		getResult().setRecordsToOpen(TableRecordReference.ofCollection(hus), HUsToReceiveViewFactory.WINDOW_ID_STRING);
	}

	protected void updateAttributes(@NonNull final I_M_HU hu, @NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(hu);
		setAttributeLotNumber(hu, receiptSchedule, huAttributes);
		setAttributeBBD(receiptSchedule, huAttributes);
		setVendorValueFromReceiptSchedule(receiptSchedule, huAttributes);
	}

	private void setAttributeLotNumber(final @NonNull I_M_HU hu, final @NonNull I_M_ReceiptSchedule receiptSchedule, @NonNull final IAttributeStorage huAttributes)
	{
		final String lotNumberToSet;
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber)
				&& Check.isBlank(huAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
				&& huAttributesBL.isAutomaticallySetLotNumber()
		)
		{
			lotNumberToSet = hu.getValue();
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumberToSet);
		}
		else
		{
			final String lotNumber = getOrLoadLotNoFromSeq(receiptSchedule);
			if (Check.isNotBlank(lotNumber))
			{
				huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumber);
			}
		}
		huAttributes.saveChangesIfNeeded();
	}

	@Nullable
	private String getOrLoadLotNoFromSeq(final @NonNull I_M_ReceiptSchedule receiptSchedule)
	{
		if (lotNumberFromSeq == null)
		{
			final I_C_DocType docType = docTypeDAO.getById(DocTypeId.ofRepoId(receiptSchedule.getC_DocType_ID()));
			final DocSequenceId lotNoSequenceId = DocSequenceId.ofRepoIdOrNull(docType.getLotNo_Sequence_ID());
			if (lotNoSequenceId != null)
			{
				lotNumberFromSeq = lotNumberBL.getAndIncrementLotNo(LotNoContext.builder()
						.sequenceId(lotNoSequenceId)
						.clientId(ClientId.ofRepoId(receiptSchedule.getAD_Client_ID()))
						.build());
			}
		}
		return lotNumberFromSeq.orElse(null);
	}

	private void setAttributeBBD(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final IAttributeStorage huAttributes)
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

	private void setVendorValueFromReceiptSchedule(@NonNull final I_M_ReceiptSchedule receiptSchedule, @NonNull final IAttributeStorage huAttributes)
	{
		if (huAttributes.hasAttribute(AttributeConstants.ATTR_Vendor_BPartner_ID)
				&& huAttributes.getValueAsInt(AttributeConstants.ATTR_Vendor_BPartner_ID) > -1)
		{
			final int bpId = receiptSchedule.getC_BPartner_ID();
			if (bpId > 0)
			{
				huAttributes.setValue(AttributeConstants.ATTR_Vendor_BPartner_ID, bpId);
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
