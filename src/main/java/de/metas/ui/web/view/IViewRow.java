package de.metas.ui.web.view;

import java.util.List;
import java.util.Map;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IViewRow
{
	//
	// Document info
	// @formatter:off
	DocumentId getId();
	IViewRowType getType();
	boolean isProcessed();
	// @formatter:on

	/**
	 * Gets row's document path.
	 * Usually the row's document path consist of view's WindowId and row's ID.
	 * But there are views which mix data from multiple windows/tables and in that case this method will use row's actual WindowId instead of view's WindowId.
	 *
	 * @return row's document path
	 */
	DocumentPath getDocumentPath();

	//
	// Fields
	// @formatter:off
	Map<String, Object> getFieldNameAndJsonValues();
	// @formatter:on

	//
	// Included documents (children)
	List<? extends IViewRow> getIncludedRows();

	//
	// Attributes
	// @formatter:off
	boolean hasAttributes();
	IViewRowAttributes getAttributes() throws EntityNotFoundException;
	// @formatter:on

	//
	// Attributes
	// @formatter:off
	boolean hasIncludedView();
	default ViewId getIncludedViewId() { return null; }
	// @formatter:on

	//
	// Single column row
	// @formatter:off
	/** @return true if frontend shall display one single column */
	default boolean isSingleColumn() { return false; }
	/** @return text to be displayed if {@link #isSingleColumn()} */
	default ITranslatableString getSingleColumnCaption() { return ITranslatableString.empty(); }
	// @formatter:on
}
