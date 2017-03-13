package de.metas.ui.web.view;

import java.util.List;

import de.metas.ui.web.view.descriptor.DocumentViewAttributesLayout;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IDocumentViewAttributes
{
	DocumentPath getDocumentPath();

	DocumentViewAttributesLayout getLayout();

	void processChanges(List<JSONDocumentChangedEvent> events);

	LookupValuesList getAttributeTypeahead(String attributeName, final String query);

	LookupValuesList getAttributeDropdown(String attributeName);

	JSONDocument toJSONDocument();

}
