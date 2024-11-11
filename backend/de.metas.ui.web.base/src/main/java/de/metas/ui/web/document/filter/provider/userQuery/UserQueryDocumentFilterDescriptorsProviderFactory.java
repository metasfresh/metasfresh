package de.metas.ui.web.document.filter.provider.userQuery;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProviderFactory;
import de.metas.ui.web.document.filter.provider.NullDocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.descriptor.CreateFiltersProviderContext;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.apps.search.IUserQueryField;
import org.compiere.apps.search.UserQueryRepository;
import org.compiere.model.POInfo;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-webui-api
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

@Component
final public class UserQueryDocumentFilterDescriptorsProviderFactory implements DocumentFilterDescriptorsProviderFactory
{
	private final IADTableDAO adTablesRepo = Services.get(IADTableDAO.class);

	public UserQueryDocumentFilterDescriptorsProviderFactory()
	{
	}

	@Override
	public DocumentFilterDescriptorsProvider createFiltersProvider(
			@NonNull final CreateFiltersProviderContext context,
			final @NonNull Collection<DocumentFieldDescriptor> fields)
	{
		final String tableName = context.getTableName();
		final AdTabId adTabId = context.getAdTabId();

		if (tableName == null || adTabId == null)
		{
			return NullDocumentFilterDescriptorsProvider.instance;
		}

		Check.assumeNotEmpty(tableName, "tableName is not empty");

		final int adTableId = adTablesRepo.retrieveTableId(tableName);

		final List<IUserQueryField> searchFields = fields
				.stream()
				.map(field -> createUserQueryField(field))
				.collect(ImmutableList.toImmutableList());

		final UserQueryRepository repository = UserQueryRepository.builder()
				.setAD_Tab_ID(adTabId.getRepoId())
				.setAD_Table_ID(adTableId)
				.setAD_User_ID(UserId.METASFRESH.getRepoId()) // FIXME: hardcoded, see https://github.com/metasfresh/metasfresh-webui/issues/162
				.setSearchFields(searchFields)
				.setColumnDisplayTypeProvider(POInfo.getPOInfo(AdTableId.ofRepoId(adTableId)))
				.build();

		return new UserQueryDocumentFilterDescriptorsProvider(repository);
	}

	private static UserQueryField createUserQueryField(final DocumentFieldDescriptor field)
	{
		return UserQueryField.builder()
				.columnName(field.getFieldName())
				.displayName(field.getCaption())
				.widgetType(field.getWidgetType())
				.lookupDescriptor(field.getLookupDescriptorForFiltering())
				.build();
	}
}
