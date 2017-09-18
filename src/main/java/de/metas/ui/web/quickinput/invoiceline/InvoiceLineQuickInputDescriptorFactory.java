package de.metas.ui.web.quickinput.invoiceline;

import java.util.Set;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.validationRule.IValidationRuleDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;

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

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_C_InvoiceLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentType, documentTypeId, detailId);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(entityDescriptor, layout, InvoiceLineQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final DocumentEntityDescriptor.Builder entityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				// Defaults:
				.setDetailId(detailId);

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(msgBL.translatable(IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup).setValueClass(IntegerLookupValue.class)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setColumnName(IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID)
						.setDisplayType(DisplayType.Search)
						.setAD_Val_Rule_ID(Services.get(IValidationRuleDAO.class).retrieveValRuleIdByColumnName(I_C_InvoiceLine.Table_Name, I_C_InvoiceLine.COLUMNNAME_M_Product_ID))
						.buildProvider())
				.setMandatoryLogic(true)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IInvoiceLineQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IInvoiceLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setMandatoryLogic(true)
				.addCharacteristic(Characteristic.PublicField));

		return entityDescriptor.build();
	}

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.build(entityDescriptor, new String[][] {
				{ IInvoiceLineQuickInput.COLUMNNAME_M_Product_ID }, //
				{ IInvoiceLineQuickInput.COLUMNNAME_Qty }
		});
	}
}
