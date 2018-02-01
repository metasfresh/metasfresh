package de.metas.ui.web.view;

import java.util.Objects;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;

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

public final class DefaultViewInvalidationAdvisor implements IViewInvalidationAdvisor
{
	public static final transient DefaultViewInvalidationAdvisor instance = new DefaultViewInvalidationAdvisor();

	private DefaultViewInvalidationAdvisor()
	{
	}

	@Override
	public WindowId getWindowId()
	{
		// this method shall never be called
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<DocumentId> findAffectedRowIds(final Set<TableRecordReference> recordRefs, final IView view)
	{
		final String viewTableName = view.getTableNameOrNull();
		if (viewTableName == null)
		{
			return ImmutableSet.of();
		}

		return recordRefs.stream()
				.filter(recordRef -> Objects.equals(viewTableName, recordRef.getTableName()))
				.map(recordRef -> DocumentId.of(recordRef.getRecord_ID()))
				.collect(ImmutableSet.toImmutableSet());
	}

}
