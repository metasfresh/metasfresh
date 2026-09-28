/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.ui.web.quickinput.costrevaluationline;

import com.google.common.collect.ImmutableSet;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.lang.SOTrx;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.model.I_M_CostRevaluationLine;
import org.compiere.util.DisplayType;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class CostRevaluationLineQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private static final ReferenceId M_PRODUCT_STOCKED_AD_REFERENCE_ID = ReferenceId.ofRepoId(171);
	private static final AdMessageKey MSG_ZERO_STOCK_COST_PROVISIONAL = AdMessageKey.of("M_CostRevaluationLine_ZeroStockCostProvisional");

	private final LookupDescriptorProviders lookupDescriptorProviders;

	public CostRevaluationLineQuickInputDescriptorFactory(@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		this.lookupDescriptorProviders = lookupDescriptorProviders;
	}

	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_M_CostRevaluationLine.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(final DocumentType documentType, final DocumentId documentTypeId, final DetailId detailId, final Optional<SOTrx> soTrx)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId, soTrx);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);

		return QuickInputDescriptor.of(entityDescriptor, layout, CostRevaluationLineQuickInputProcessor.class);
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
				.addField(createNewCostPriceFieldDescriptor())
				.build();
	}

	private DocumentFieldDescriptor.Builder createProductFieldDescriptor()
	{
		return DocumentFieldDescriptor
				.builder(ICostRevaluationLineQuickInput.COLUMNNAME_M_Product_ID)
				.setCaption(msgBL.translatable(ICostRevaluationLineQuickInput.COLUMNNAME_M_Product_ID))
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(lookupDescriptorProviders.sql()
						.setCtxTableName(null)
						.setCtxColumnName(ICostRevaluationLineQuickInput.COLUMNNAME_M_Product_ID)
						.setDisplayType(DisplayType.Search)
						.setAD_Reference_Value_ID(M_PRODUCT_STOCKED_AD_REFERENCE_ID)
						.build())
				.setMandatoryLogic(true)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField);
	}

	private DocumentFieldDescriptor.Builder createNewCostPriceFieldDescriptor()
	{
		return DocumentFieldDescriptor.builder(ICostRevaluationLineQuickInput.COLUMNNAME_NewCostPrice)
				.setCaption(msgBL.translatable(ICostRevaluationLineQuickInput.COLUMNNAME_NewCostPrice))
				// Provisional-price hint (AVCO): a zero-stock cost entry is provisional until the first goods receipt.
				.setDescription(msgBL.getTranslatableMsgText(MSG_ZERO_STOCK_COST_PROVISIONAL))
				.setWidgetType(DocumentFieldWidgetType.CostPrice)
				.setMandatoryLogic(true)
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField);
	}

	private QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.onlyFields(entityDescriptor, new String[][] {
				{ ICostRevaluationLineQuickInput.COLUMNNAME_M_Product_ID },
				{ ICostRevaluationLineQuickInput.COLUMNNAME_NewCostPrice }
		});
	}
}
