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

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.annotation.Nullable;

import de.metas.common.util.time.SystemTime;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.io.IOUtils;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_AttachmentEntry;
import org.compiere.model.X_M_DiscountSchemaLine;

import de.metas.attachments.AttachmentEntryDataResource;
import de.metas.attachments.AttachmentEntryId;
import de.metas.attachments.AttachmentEntryService;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
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
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;

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

	public static final AdMessageKey MSG_PRICE_LIST_SCHEMA_SHOULD_HAVE_NO_LINES = AdMessageKey.of("de.metas.pricing.process.ImportPriceListSchemaLinesFromAttachment.PriceListSchemaShouldHaveNoLines");

	private static final int INDEX_SeqNo = 0;
	private static final int INDEX_Product = 1;
	private static final int INDEX_Product_Category = 3;
	private static final int INDEX_Business_Partner = 5;
	private static final int INDEX_Tax_Category = 7;
	private static final int INDEX_Std_Price_Surcharge_Amt = 8;
	private static final int INDEX_Standard_Price_Rounding = 9;
	private static final int INDEX_Target_Taxcategory = 10;

	private static final int EXPECTED_NUMBER_OF_COLUMNS = 11;

	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final PriceListSchemaRepository priceListSchemaRepository = SpringContextHolder.instance.getBean(PriceListSchemaRepository.class);
	private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	private final IBPartnerDAO partnerDAO = Services.get(IBPartnerDAO.class);
	private final IProductDAO productDAO = Services.get(IProductDAO.class);
	private final CurrencyConversionTypeId DEFAULT_CONVERSION_TYPE_ID = CurrencyConversionTypeId.ofRepoId(114); // no idea from where this 114 comes, but this is what i've got

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

		if (priceListSchemaRepository.hasAnyLines(PricingConditionsId.ofRepoId(context.getSingleSelectedRecordId())))
		{
			return ProcessPreconditionsResolution.reject(Services.get(IMsgBL.class).getTranslatableMsgText(MSG_PRICE_LIST_SCHEMA_SHOULD_HAVE_NO_LINES));
		}

		return ProcessPreconditionsResolution.accept();
	}

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
					.discountSchemaId(PricingConditionsId.ofRepoId(getRecord_ID()))
					.seqNo(NumberUtils.asIntOrZero(split[INDEX_SeqNo]))
					.productId(getProductId(split[INDEX_Product]))
					.productCategoryId(getProductCategoryId(split[INDEX_Product_Category]))
					.bPartnerId(getBPartnerId(split[INDEX_Business_Partner]))
					.taxCategoryId(getTaxCategoryId(split[INDEX_Tax_Category]))
					.std_AddAmt(NumberUtils.asBigDecimal(split[INDEX_Std_Price_Surcharge_Amt]))
					.std_Rounding(split[INDEX_Standard_Price_Rounding])
					.taxCategoryTargetId(getTaxCategoryId(split[INDEX_Target_Taxcategory]))

					// mandatory fields where the default value is set
					.conversionDate(SystemTime.asLocalDate())
					.list_Base(X_M_DiscountSchemaLine.LIST_BASE_Listenpreis)

					.conversionTypeId(DEFAULT_CONVERSION_TYPE_ID)
					.limit_AddAmt(BigDecimal.ZERO)
					.limit_Base(X_M_DiscountSchemaLine.LIMIT_BASE_Mindestpreis)
					.limit_Discount(BigDecimal.ZERO)
					.limit_MaxAmt(BigDecimal.ZERO)
					.limit_MinAmt(BigDecimal.ZERO)
					.limit_Rounding(X_M_DiscountSchemaLine.LIMIT_ROUNDING_CurrencyPrecision)

					.list_AddAmt(BigDecimal.ZERO)
					.list_Discount(BigDecimal.ZERO)
					.list_MaxAmt(BigDecimal.ZERO)
					.list_MinAmt(BigDecimal.ZERO)
					.list_Rounding(X_M_DiscountSchemaLine.LIST_ROUNDING_CurrencyPrecision)

					.std_Base(X_M_DiscountSchemaLine.STD_BASE_Standardpreis)
					.std_Discount(BigDecimal.ZERO)
					.std_MaxAmt(BigDecimal.ZERO)
					.std_MinAmt(BigDecimal.ZERO)
					.build();

			priceListSchemaRepository.createPriceListSchemaLine(priceListSchemaRequest);
		}

		return MSG_OK;
	}

	@Nullable
	private TaxCategoryId getTaxCategoryId(@Nullable final String name)
	{
		if (name == null)
		{
			return null;
		}
		return taxDAO.getTaxCategoryIdByName(name).orElse(null);
	}

	@Nullable
	private BPartnerId getBPartnerId(@Nullable final String value)
	{
		if (value == null)
		{
			return null;
		}
		return partnerDAO.getBPartnerIdByValue(value).orElse(null);
	}

	@Nullable
	private ProductCategoryId getProductCategoryId(@Nullable final String value)
	{
		if (value == null)
		{
			return null;
		}
		return productDAO.retrieveProductCategoryIdByCategoryValue(value).orElse(null);
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
}
