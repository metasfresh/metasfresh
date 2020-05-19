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

package de.metas.ui.web.document.filter.provider.fact_acct;

import de.metas.i18n.IMsgBL;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsConstants;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collection;

@Component
public class FactAcctDocumentService implements DocumentFilterDescriptorsProviderFactory
{
	public static final String FACT_ACCT_TRANSACTIONS_VIEW = "Fact_Acct_Transactions_View";
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);

	public FactAcctDocumentService()
	{
	}

	@Override
	@NonNull
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@Nullable final AdTabId ignored,
			@Nullable final String tableName,
			@Nullable final Collection<DocumentFieldDescriptor> fields)
	{
		if (!FACT_ACCT_TRANSACTIONS_VIEW.equals(tableName))
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		return ImmutableDocumentFilterDescriptorsProvider.of(
				DocumentFilterDescriptor.builder()
						.setFilterId(FactAcctFilterConverter.FILTER_ID)
						.setSortNo(DocumentFilterDescriptorsConstants.SORT_NO_FACT_ACCT)
						.setDisplayName(msgBL.translatable("AccountNumber"))
						//
						.addParameter(DocumentFilterParamDescriptor.builder()
								.setFieldName(FactAcctFilterConverter.PARAM_ACCOUNT_VALUE_FROM)
								.setDisplayName(msgBL.translatable(FactAcctFilterConverter.PARAM_ACCOUNT_VALUE_FROM))
								.setWidgetType(DocumentFieldWidgetType.Text)
						)
						.addParameter(DocumentFilterParamDescriptor.builder()
								.setFieldName(FactAcctFilterConverter.PARAM_ACCOUNT_VALUE_TO)
								.setDisplayName(msgBL.translatable(FactAcctFilterConverter.PARAM_ACCOUNT_VALUE_TO))
								.setWidgetType(DocumentFieldWidgetType.Text)
						)
						//
						.build()
		);
	}
}
