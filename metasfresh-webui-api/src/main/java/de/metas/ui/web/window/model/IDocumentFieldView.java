package de.metas.ui.web.window.model;

import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;

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

/**
 * Document field view.
 *
 * @implNote
 * 			This interface it's just a view of a {@link Document}'s field. Please don't setters or any other method which can chance document field's state.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocumentFieldView
{
	//@formatter:off
	DocumentFieldDescriptor getDescriptor();
	String getFieldName();
	boolean isKey();
	boolean isCalculated();
	boolean isVirtualField();
	//@formatter:on

	//@formatter:off
	boolean isReadonly();
	boolean isMandatory();
	boolean isDisplayed();
	boolean isLookupValuesStale();
	//@formatter:on

	//@formatter:off
	Object getInitialValue();
	//@formatter:on

	//@formatter:off
	Object getValue();
	Object getValueAsJsonObject();
	boolean getValueAsBoolean();
	int getValueAsInt(final int defaultValue);
	//@formatter:on

	//@formatter:off
	Object getOldValue();
	//@formatter:on
}
