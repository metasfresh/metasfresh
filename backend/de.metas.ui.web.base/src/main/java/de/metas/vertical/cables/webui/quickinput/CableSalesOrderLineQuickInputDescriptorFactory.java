package de.metas.vertical.cables.webui.quickinput;

import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.compiere.model.I_C_OrderLine;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_C_Order;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.ui.web.window.descriptor.sql.ProductLookupDescriptor;
import de.metas.util.Services;
import de.metas.vertical.cables.CablesConstants;
import lombok.NonNull;

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

@Component
@Profile(CablesConstants.PROFILE)
/* package */ class CableSalesOrderLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	private static final AdWindowId WINDOW_ID_SalesOrder_DEFAULT = AdWindowId.ofRepoId(143); // sales order window id = 143
	private static final AdWindowId WINDOW_ID_PurchaseOrder_DEFAULT = AdWindowId.ofRepoId(181); // purchase order window id = 181

	private final ProductLookupDescriptor productLookupDescriptor;

	public CableSalesOrderLineQuickInputDescriptorFactory()
	{
		productLookupDescriptor = ProductLookupDescriptor
				.builderWithoutStockInfo()
				.bpartnerParamName(I_C_Order.COLUMNNAME_C_BPartner_ID)
				.pricingDateParamName(I_C_Order.COLUMNNAME_DatePromised)
				.excludeBOMProducts(true)
				.build();
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		final IADWindowDAO windowDAO = Services.get(IADWindowDAO.class);
		final AdWindowId salesOrderWindowId = windowDAO.getAdWindowId(I_C_OrderLine.Table_Name, SOTrx.SALES, WINDOW_ID_SalesOrder_DEFAULT);
		final AdWindowId purchaseOrderWindowId = windowDAO.getAdWindowId(I_C_OrderLine.Table_Name, SOTrx.PURCHASE, WINDOW_ID_PurchaseOrder_DEFAULT);

		return ImmutableSet.of(
				MatchingKey.includedTab(salesOrderWindowId, I_C_OrderLine.Table_Name),
				MatchingKey.includedTab(purchaseOrderWindowId, I_C_OrderLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			final Optional<SOTrx> soTrx)
	{
		final DocumentEntityDescriptor entityDescriptor = createDescriptorBuilder(documentTypeId, detailId, soTrx)
				.addField(createProductFieldBuilder(ICablesOrderLineQuickInput.COLUMNNAME_Plug1_Product_ID)
						.setMandatoryLogic(true))
				.addField(createProductFieldBuilder(ICablesOrderLineQuickInput.COLUMNNAME_Cable_Product_ID)
						.setMandatoryLogic(false))
				.addField(createProductFieldBuilder(ICablesOrderLineQuickInput.COLUMNNAME_Plug2_Product_ID)
						.setMandatoryLogic(false))
				.addField(createQuantityFieldBuilder(ICablesOrderLineQuickInput.COLUMNNAME_CableLength)
						.setMandatoryLogic(false))
				.addField(createQuantityFieldBuilder(ICablesOrderLineQuickInput.COLUMNNAME_Qty)
						.setMandatoryLogic(true))
				.build();

		final QuickInputLayoutDescriptor.Builder quickInputBuilder = QuickInputLayoutDescriptor.builder();

		DocumentLayoutElementDescriptor
				.builderOrEmpty(entityDescriptor,
						ICablesOrderLineQuickInput.COLUMNNAME_Plug1_Product_ID,
						ICablesOrderLineQuickInput.COLUMNNAME_Cable_Product_ID,
						ICablesOrderLineQuickInput.COLUMNNAME_Plug2_Product_ID)
				.map(b -> b.setWidgetSize(WidgetSize.Large))
				.ifPresent(quickInputBuilder::element);

		DocumentLayoutElementDescriptor
				.builderOrEmpty(entityDescriptor,
						ICablesOrderLineQuickInput.COLUMNNAME_CableLength)
				.map(b -> b.setWidgetSize(WidgetSize.Small))
				.ifPresent(quickInputBuilder::element);

		DocumentLayoutElementDescriptor
				.builderOrEmpty(entityDescriptor,
						ICablesOrderLineQuickInput.COLUMNNAME_Qty)
				.map(b -> b.setWidgetSize(WidgetSize.Small))
				.ifPresent(quickInputBuilder::element);

		final QuickInputLayoutDescriptor layout = quickInputBuilder.build();

		return QuickInputDescriptor.of(entityDescriptor, layout, CableSalesOrderLineQuickInputProcessor.class);
	}

	private static DocumentEntityDescriptor.Builder createDescriptorBuilder(
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		return DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.setIsSOTrx(soTrx)
				.disableDefaultTableCallouts()
				// Defaults:
				.setDetailId(detailId)
				.setTableName(I_C_OrderLine.Table_Name); // TODO: figure out if it's needed
	}

	private DocumentFieldDescriptor.Builder createProductFieldBuilder(final String fieldName)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);

		final ITranslatableString caption = msgBL.translatable(fieldName);

		return DocumentFieldDescriptor.builder(fieldName)
				.setLookupDescriptorProvider(productLookupDescriptor)
				.setCaption(caption)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCallout(CableSalesOrderLineQuickInputDescriptorFactory::onProductChangedCallout)
				.addCharacteristic(Characteristic.PublicField);
	}

	private static DocumentFieldDescriptor.Builder createQuantityFieldBuilder(final String fieldName)
	{
		return DocumentFieldDescriptor.builder(fieldName)
				.setCaption(Services.get(IMsgBL.class).translatable(fieldName))
				.setWidgetType(DocumentFieldWidgetType.Quantity)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField);
	}

	private static void onProductChangedCallout(final ICalloutField calloutField)
	{
		// TODO
	}

}
