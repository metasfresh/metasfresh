package de.metas.ui.web.view;

import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Component;

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

/**
 * Implementations of this interface are advising views how to convert the changed {@link TableRecordReference}s to rowIds.
 * 
 * Please annotate your implementation with {@link Component} and it will be automatically discovered and used for {@link #getWindowId()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IViewInvalidationAdvisor
{
	WindowId getWindowId();

	Set<DocumentId> findAffectedRowIds(final Set<TableRecordReference> recordRefs, IView view);
}
