/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.ui.web.quickinput.ddorderline;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputConstants;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.SqlLookupDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.util.DisplayType;
import org.eevolution.model.I_DD_OrderLine;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class DDOrderLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final DDOrderLineQuickInputCallout ddOrderLineQuickInputCallout;

	private static final int M_PRODUCT_STOCKED_AD_REFERENCE_ID = 171;
	private static final int DD_ORDERLINE_M_HU_PI_ITEM_PRODUCT_AD_VAL_RULE_ID = 540299;

	public DDOrderLineQuickInputDescriptorFactory(final DDOrderLineQuickInputCallout ddOrderLineQuickInputCallout)
	{
		this.ddOrderLineQuickInputCallout = ddOrderLineQuickInputCallout;
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_DD_OrderLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId, final Optional<SOTrx> soTrx)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId, soTrx);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(entityDescriptor, layout, DDOrderLineQuickInputProcessor.class);
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
				.addField( createProductFieldDescriptor() )
				.addFieldIf(QuickInputConstants.isEnablePackingInstructionsField(), this::createPackingItemFieldDescriptor )
				.addField( createQtyFieldDescriptor() )
				.build();
	}

	private DocumentFieldDescriptor.Builder createProductFieldDescriptor()
	{
		return DocumentFieldDescriptor
				.builder(IDDOrderLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(msgBL.translatable(IDDOrderLineQuickInput.COLUMNNAME_M_Product_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(SqlLookupDescriptor.builder()
						.setCtxTableName(null)
						.setCtxColumnName(IDDOrderLineQuickInput.COLUMNNAME_M_Product_ID)
						.setDisplayType(DisplayType.Search)
						.setAD_Reference_Value_ID(M_PRODUCT_STOCKED_AD_REFERENCE_ID)
						.buildProvider())
				.setMandatoryLogic(true)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCallout(ddOrderLineQuickInputCallout::onProductChange)
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createPackingItemFieldDescriptor()
	{
		return DocumentFieldDescriptor.builder(IDDOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
				.setCaption(msgBL.translatable(IDDOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(
						SqlLookupDescriptor.builder()
						.setCtxTableName(null)
						.setCtxColumnName(IDDOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID)
						.setDisplayType(DisplayType.TableDir)
						.setAD_Val_Rule_ID(DD_ORDERLINE_M_HU_PI_ITEM_PRODUCT_AD_VAL_RULE_ID)
						.buildProvider())
				.setValueClass(LookupValue.IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField);
	}


	private DocumentFieldDescriptor.Builder createQtyFieldDescriptor()
	{
		return DocumentFieldDescriptor.builder(IDDOrderLineQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IDDOrderLineQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setMandatoryLogic(true)
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField);
	}

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.build(entityDescriptor, new String[][] {
				{ IDDOrderLineQuickInput.COLUMNNAME_M_Product_ID, IDDOrderLineQuickInput.COLUMNNAME_M_HU_PI_Item_Product_ID },
				{ IDDOrderLineQuickInput.COLUMNNAME_Qty }
		});
	}
}
