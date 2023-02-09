package de.metas.handlingunits.receiptschedule.impl;

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;

class UpdateHUAttributesFromReceiptScheduleCommand
{
	private final IAttributeStorageFactoryService attributeStorageFactoryService = Services.get(IAttributeStorageFactoryService.class);
	private final IAttributeStorageFactory attributeStorageFactory = attributeStorageFactoryService.createHUAttributeStorageFactory();
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

	private final I_M_HU hu;
	private final I_M_ReceiptSchedule receiptSchedule;

	@Builder
	private UpdateHUAttributesFromReceiptScheduleCommand(
			@NonNull final I_M_HU hu,
			@NonNull final I_M_ReceiptSchedule receiptSchedule)
	{
		this.hu = hu;
		this.receiptSchedule = receiptSchedule;
	}

	public void execute()
	{
		final IAttributeStorage huAttributes = attributeStorageFactory.getAttributeStorage(hu);
		setAttributeLotNumber(hu, huAttributes);
		setAttributeBBD(receiptSchedule, huAttributes);
		setVendorValueFromReceiptSchedule(receiptSchedule, huAttributes);

	}

	private void setAttributeLotNumber(final @NonNull I_M_HU hu, @NonNull final IAttributeStorage huAttributes)
	{

		if (huAttributes.hasAttribute(AttributeConstants.ATTR_LotNumber)
				&& Check.isBlank(huAttributes.getValueAsString(AttributeConstants.ATTR_LotNumber))
				&& huAttributesBL.isAutomaticallySetLotNumber()
		)
		{
			final String lotNumberToSet = hu.getValue();
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNumberToSet);
			huAttributes.saveChangesIfNeeded();
		}
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
