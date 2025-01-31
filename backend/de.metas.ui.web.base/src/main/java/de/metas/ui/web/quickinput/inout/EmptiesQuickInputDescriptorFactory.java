package de.metas.ui.web.quickinput.inout;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
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
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.model.I_M_InOutLine;
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
public class EmptiesQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	// FIXME: hardcoded AD_Window_IDs
	public static final AdWindowId CustomerReturns_Window_ID = AdWindowId.ofRepoId(540323); // Return from customers - Leergut Rücknahme
	public static final AdWindowId VendorReturns_Window_ID = AdWindowId.ofRepoId(540322); // Return to vendor - Leergut Rückgabe

	@NonNull final LookupDescriptorProviders lookupDescriptorProviders;

	public EmptiesQuickInputDescriptorFactory(
			final @NonNull LookupDescriptorProviders lookupDescriptorProviders)
	{
		this.lookupDescriptorProviders = lookupDescriptorProviders;
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.<MatchingKey>builder()
				.add(MatchingKey.includedTab(CustomerReturns_Window_ID, I_M_InOutLine.Table_Name))
				.add(MatchingKey.includedTab(VendorReturns_Window_ID, I_M_InOutLine.Table_Name))
				.build();
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
		return QuickInputDescriptor.of(entityDescriptor, layout, EmptiesQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final DocumentEntityDescriptor.Builder entityDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.setIsSOTrx(soTrx)
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
				.setLookupDescriptorProvider(lookupDescriptorProviders.sql()
						.setCtxTableName(null) // ctxTableName
						.setCtxColumnName(IEmptiesQuickInput.COLUMNNAME_M_HU_PackingMaterial_ID)
						.setDisplayType(DisplayType.Search)
						.build())
				.setValueClass(IntegerLookupValue.class)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		entityDescriptor.addField(DocumentFieldDescriptor.builder(IEmptiesQuickInput.COLUMNNAME_Qty)
				.setCaption(msgBL.translatable(IEmptiesQuickInput.COLUMNNAME_Qty))
				.setWidgetType(DocumentFieldWidgetType.Integer)
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAlwaysUpdateable(true)
				.setMandatoryLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(Characteristic.PublicField));

		return entityDescriptor.build();
	}

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.onlyFields(entityDescriptor, new String[][] {
				{ IEmptiesQuickInput.COLUMNNAME_M_HU_PackingMaterial_ID } //
				, { IEmptiesQuickInput.COLUMNNAME_Qty }
		});
	}

}
