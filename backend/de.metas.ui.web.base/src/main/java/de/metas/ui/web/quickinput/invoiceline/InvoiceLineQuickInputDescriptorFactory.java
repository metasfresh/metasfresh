package de.metas.ui.web.quickinput.invoiceline;

import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputConstants;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.quickinput.orderline.IOrderLineQuickInput;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptorProviderBuilder;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class InvoiceLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final InvoiceLineQuickInputCallout invoiceLineQuickInputCallout;
	private final LookupDescriptorProviders lookupDescriptorProviders;

	public InvoiceLineQuickInputDescriptorFactory(
			@NonNull final InvoiceLineQuickInputCallout invoiceLineQuickInputCallout,
			@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		this.invoiceLineQuickInputCallout = invoiceLineQuickInputCallout;
		this.lookupDescriptorProviders = lookupDescriptorProviders;
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_C_InvoiceLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId, soTrx);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(entityDescriptor, layout, InvoiceLineQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{

		return DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.setIsSOTrx(soTrx)
				.disableDefaultTableCallouts()
				.setDetailId(detailId)
				.addField(createProductFieldDescriptor())
				.addFieldIf(QuickInputConstants.isEnablePackingInstructionsField(), this::createPackingItemFieldDescriptor)
				.addFieldIf(QuickInputConstants.isEnableVatCodeField(), () -> this.createVatCodeField(soTrx))
				.addField(createQtyFieldDescriptor())
				.build();
	}

	private DocumentFieldDescriptor.Builder createProductFieldDescriptor()
	{
		return DocumentFieldDescriptor
				.builder(IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(msgBL.translatable(IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(
						ProductLookupDescriptor
								.builderWithoutStockInfo()
								.bpartnerParamName(I_C_Invoice.COLUMNNAME_C_BPartner_ID)
								.pricingDateParamName(I_C_Invoice.COLUMNNAME_DateInvoiced)
								.build())
				.setMandatoryLogic(true)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCallout(invoiceLineQuickInputCallout::onProductChange)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createVatCodeField(final @NonNull Optional<SOTrx> soTrx)
	{
		final SqlLookupDescriptorProviderBuilder sqlLookupDescriptorProviderBuilder = lookupDescriptorProviders.sql()
				.setCtxTableName(null) // ctxTableName
				.setCtxColumnName(IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID)
				.setDisplayType(DisplayType.TableDir)
				.setPageLength(QuickInputConstants.BIG_ENOUGH_PAGE_LENGTH);
		if (soTrx.orElse(SOTrx.PURCHASE).isSales())
		{
			sqlLookupDescriptorProviderBuilder.setAD_Val_Rule_ID(AdValRuleId.ofRepoId(540610));// FIXME: hardcoded "VAT_Code_for_SO"
		}
		else
		{
			sqlLookupDescriptorProviderBuilder.setAD_Val_Rule_ID(AdValRuleId.ofRepoId(540611));// FIXME: hardcoded "VAT_Code_for_PO"
		}

		return DocumentFieldDescriptor.builder(IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID)
				.setCaption(msgBL.translatable(IOrderLineQuickInput.COLUMNNAME_C_VAT_Code_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(sqlLookupDescriptorProviderBuilder.build())
				.setValueClass(LookupValue.IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createQtyFieldDescriptor()
	{
		return DocumentFieldDescriptor.builder(IInvoiceLineQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IInvoiceLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setMandatoryLogic(true)
				.addCharacteristic(Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createPackingItemFieldDescriptor()
	{
		return DocumentFieldDescriptor.builder(IInvoiceLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.setCaption(msgBL.translatable(IInvoiceLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(lookupDescriptorProviders.sql()
						.setCtxTableName(null) // ctxTableName
						.setCtxColumnName(IInvoiceLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
						.setDisplayType(DisplayType.TableDir)
						.setAD_Val_Rule_ID(AdValRuleId.ofRepoId(540480)) // FIXME: hardcoded "M_HU_PI_Item_Product_For_Org_Product_And_DateInvoiced"
						.build())
				.setValueClass(LookupValue.IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.onlyFields(entityDescriptor, new String[][] {
				{ IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID, IInvoiceLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID },
				{ IInvoiceLineQuickInput.COLUMNNAME_C_VAT_Code_ID },
				{ IInvoiceLineQuickInput.COLUMNNAME_Qty }
		});
	}
}
