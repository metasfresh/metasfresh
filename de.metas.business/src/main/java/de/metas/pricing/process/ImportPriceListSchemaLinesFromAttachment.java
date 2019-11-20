/*
 * #%L
 * de.metas.business
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

package de.metas.pricing.process;

import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.money.CurrencyConversionTypeId;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.pricing.conditions.service.CreatePriceListSchemaRequest;
import de.metas.pricing.pricelistschema.PriceListSchemaRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductDAO;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.TaxCategoryId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.IOUtils;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

/**
 * Import Price List Schema Lines from an attachment.
 * <p>
 * File encoding must be utf-8.
 * Destination Price List Schema must have 0 lines.
 */
public class ImportPriceListSchemaLinesFromAttachment extends JavaProcess implements IProcessPrecondition
{
	@Param(parameterName = I_AD_AttachmentEntry.COLUMNNAME_AD_AttachmentEntry_ID, mandatory = true)
	private AttachmentEntryId p_AD_AttachmentEntry_ID;

	private static final int INDEX_SeqNo = 0;
	private static final int INDEX_Product = 1;
	private static final int INDEX_Product_Category = 2;
	private static final int INDEX_Business_Partner = 3;
	private static final int INDEX_Tax_Category = 4;
	private static final int INDEX_Std_Price_Surcharge_Amt = 5;
	private static final int INDEX_Standard_Price_Rounding = 6;
	private static final int INDEX_Target_Taxcategory = 7;
	private static final int INDEX_List_price_Base = 8;
	private static final int INDEX_Conversiontype = 9;
	private static final int INDEX_Limit_price_Surcharge_Amount = 10;
	private static final int INDEX_Limit_price_Base = 11;
	private static final int INDEX_Limit_price_Discount = 12;
	private static final int INDEX_Limit_price_max_Margin = 13;
	private static final int INDEX_Limit_price_min_Margin = 14;
	private static final int INDEX_Limit_price_Rounding = 15;
	private static final int INDEX_List_price_Surcharge_Amount = 16;
	private static final int INDEX_List_price_Discount = 17;
	private static final int INDEX_List_price_max_Margin = 18;
	private static final int INDEX_List_price_min_Margin = 19;
	private static final int INDEX_List_price_Rounding = 20;
	private static final int INDEX_Standard_price_Base = 21;
	private static final int INDEX_Standard_price_Discount = 22;
	private static final int INDEX_Standard_max_Margin = 23;
	private static final int INDEX_Standard_price_min_Margin = 24;

	private static final int EXPECTED_NUMBER_OF_COLUMNS = 25;

	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final PriceListSchemaRepository priceListSchemaRepository = SpringContextHolder.instance.getBean(PriceListSchemaRepository.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final List<String> lines = readAttachmentLines();

		// start with 1 instead of 0 because we ignore the header
		for (int i = 1; i < lines.size(); i++)
		{
			final String line = lines.get(i);
			final String[] split = line.split(";", -1);
			if (split.length != EXPECTED_NUMBER_OF_COLUMNS)
			{
				throw new AdempiereException("Line " + (i + 1) + "has wrong number of columns. Expected: " + EXPECTED_NUMBER_OF_COLUMNS + ". Actual: " + split.length + ".");
			}

			final CreatePriceListSchemaRequest priceListSchemaRequest = CreatePriceListSchemaRequest.builder()
					.discountSchemaId(PricingConditionsId.ofDiscountSchemaId(getRecord_ID()))
					.seqNo(getIntValue(split[INDEX_SeqNo]))
					.productId(getProductId(split[INDEX_Product]))
					.productCategoryId(getProductCategoryId(split[INDEX_Product_Category]))
					.bPartnerId(getBPartnerId(split[INDEX_Business_Partner]))
					.taxCategoryId(getTaxCategoryId(split[INDEX_Tax_Category]))
					.std_AddAmt(getDecimalValue(split[INDEX_Std_Price_Surcharge_Amt]))
					.std_Rounding(split[INDEX_Standard_Price_Rounding])
					.taxCategoryTargetId(getTaxCategoryId(split[INDEX_Target_Taxcategory]))

					// default values are just copied
					.conversionDate(TimeUtil.asTimestamp(LocalDate.now()))
					.list_Base(split[INDEX_List_price_Base])

					.c_ConversionType_ID(CurrencyConversionTypeId.ofRepoId(getIntValue(split[INDEX_Conversiontype])))
					.limit_AddAmt(getDecimalValue(split[INDEX_Limit_price_Surcharge_Amount]))
					.limit_Base(split[INDEX_Limit_price_Base])
					.limit_Discount(getDecimalValue(split[INDEX_Limit_price_Discount]))
					.limit_MaxAmt(getDecimalValue(split[INDEX_Limit_price_max_Margin]))
					.limit_MinAmt(getDecimalValue(split[INDEX_Limit_price_min_Margin]))
					.limit_Rounding(split[INDEX_Limit_price_Rounding])

					.list_AddAmt(getDecimalValue(split[INDEX_List_price_Surcharge_Amount]))
					.list_Discount(getDecimalValue(split[INDEX_List_price_Discount]))
					.list_MaxAmt(getDecimalValue(split[INDEX_List_price_max_Margin]))
					.list_MinAmt(getDecimalValue(split[INDEX_List_price_min_Margin]))
					.list_Rounding(split[INDEX_List_price_Rounding])

					.std_Base(split[INDEX_Standard_price_Base])
					.std_Discount(getDecimalValue(split[INDEX_Standard_price_Discount]))
					.std_MaxAmt(getDecimalValue(split[INDEX_Standard_max_Margin]))
					.std_MinAmt(getDecimalValue(split[INDEX_Standard_price_min_Margin]))
					.build();

			priceListSchemaRepository.createPriceListSchemaLine(priceListSchemaRequest);
		}

		return MSG_OK;
	}

	@NonNull
	private BigDecimal getDecimalValue(final String s)
	{
		return new BigDecimal(s);
	}

	private int getIntValue(final String val)
	{
		return getDecimalValue(val).intValue();
	}

	@Nullable
	private TaxCategoryId getTaxCategoryId(final String name)
	{
		return taxDAO.getTaxCategoryIdByNameOrNull(name);
	}

	@Nullable
	private BPartnerId getBPartnerId(final String value)
	{
		return partnerDAO.getBPartnerIdByValueOrNull(value);
	}

	@Nullable
	private ProductCategoryId getProductCategoryId(final String value)
	{
		return productDAO.retrieveProductCategoryIdByCategoryValueOrNull(value);
	}

	@Nullable
	private ProductId getProductId(final String value)
	{
		return productDAO.retrieveProductIdByValue(value);
	}

	private List<String> readAttachmentLines() throws IOException
	{
		final AttachmentEntryDataResource attachment = attachmentEntryService.retrieveDataResource(p_AD_AttachmentEntry_ID);
		return IOUtils.readLines(attachment.getInputStream(), StandardCharsets.UTF_8);
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!context.isSingleSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		if (priceListSchemaRepository.hasAnyLines(PricingConditionsId.ofDiscountSchemaId(context.getSingleSelectedRecordId())))
		{
			return ProcessPreconditionsResolution.reject("The Price List Schema Must have no Lines."); // todo translate this
		}

		return ProcessPreconditionsResolution.accept();
	}
}
