package de.metas.ui.web.quickinput.inout;

import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.DisplayType;

import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
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

public class EmptiesQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	public static final transient EmptiesQuickInputDescriptorFactory instance = new EmptiesQuickInputDescriptorFactory();

	public static final int CustomerReturns_Window_ID = 540323; // Return from customers - Leergut Rücknahme
	public static final int VendorReturns_Window_ID = 540322; // Return to vendor - Leergut Rückgabe

	private EmptiesQuickInputDescriptorFactory()
	{
	}

	public boolean matches(final DocumentType documentType, final DocumentId documentTypeId, final String tableName)
	{
		if (documentType == DocumentType.Window)
		{
			final int adWindowId = documentTypeId.toInt();
			if (adWindowId == CustomerReturns_Window_ID || adWindowId == VendorReturns_Window_ID)
			{
				if (I_M_InOutLine.Table_Name.equals(tableName))
				{
					return true;
				}
			}
		}

		return false;
	}

	@Override
	public QuickInputDescriptor createQuickInputEntityDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);
		return QuickInputDescriptor.of(entityDescriptor, layout, EmptiesQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(final DocumentId documentTypeId, final DetailId detailId)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final DocumentEntityDescriptor.Builder entityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				.setDataBinding(DocumentEntityDataBindingDescriptorBuilder.NULL)
				// Defaults:
				.setDetailId(detailId)
		//
		;

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IEmptiesQuickInput.COLUMNNAME_M_HU_PackingMaterial_ID)
				.setCaption(msgBL.translatable(IEmptiesQuickInput.COLUMNNAME_M_HU_PackingMaterial_ID))
				//
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setColumnName(IEmptiesQuickInput.COLUMNNAME_M_HU_PackingMaterial_ID)
						.setDisplayType(DisplayType.Search)
						.buildProvider())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ILogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ILogicExpression.TRUE)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IEmptiesQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IEmptiesQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Integer)
				.setReadonlyLogic(ILogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ILogicExpression.TRUE)
				.setDisplayLogic(ILogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		return entityDescriptor.build();
	}

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		final DocumentFieldDescriptor field_M_HU_PackingMaterial_ID = entityDescriptor.getField(IEmptiesQuickInput.COLUMNNAME_M_HU_PackingMaterial_ID);
		final DocumentFieldDescriptor field_Qty = entityDescriptor.getField(IEmptiesQuickInput.COLUMNNAME_Qty);

		final QuickInputLayoutDescriptor.Builder quickInputLayout = QuickInputLayoutDescriptor.builder()
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption(field_M_HU_PackingMaterial_ID.getCaption())
						.setWidgetType(field_M_HU_PackingMaterial_ID.getWidgetType())
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_M_HU_PackingMaterial_ID.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_M_HU_PackingMaterial_ID.getLookupSourceType())))
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption(field_Qty.getCaption())
						.setWidgetType(field_Qty.getWidgetType())
						.addField(DocumentLayoutElementFieldDescriptor.builder(field_Qty.getFieldName())
								.setPublicField(true)
								.setLookupSource(field_Qty.getLookupSourceType())));

		return quickInputLayout.build();
	}

}
