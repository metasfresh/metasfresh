package de.metas.ui.web.window.descriptor;

import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.model.Document;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2021 metas GmbH
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
 * Describes which window to be used to capture the fields for quickly creating a record for a given table.
 */
@Value(staticConstructor = "of")
public class AdvancedSearchDescriptor
{
	public interface AdvancedSearchSelectionProcessor
	{
		void processSelection(WindowId windowId, Document document, String bpartnerFieldName, String selectionIdStr);
	}

	@NonNull String tableName;
	@NonNull WindowId windowId;
	@NonNull AdvancedSearchSelectionProcessor processor;
}
