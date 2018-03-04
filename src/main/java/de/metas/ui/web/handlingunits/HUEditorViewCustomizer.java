package de.metas.ui.web.handlingunits;

import org.springframework.stereotype.Component;

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

/**
 * {@link HUEditorView} customizer.
 * 
 * Implementations of this interface which are annotated with {@link Component} will be automatically discovered and registered.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface HUEditorViewCustomizer
{
	/** @return referencing tableName to be matched */
	String getReferencingTableNameToMatch();

	default HUEditorRowIsProcessedPredicate getHUEditorRowIsProcessedPredicate()
	{
		return null;
	}
	
	default Boolean isAttributesAlwaysReadonly()
	{
		return null;
	}

	/**
	 * Called before the {@link HUEditorView} is created.
	 * 
	 * The method is called only if the view it's matching our criteria (i.e. {@link #getReferencingTableNameToMatch()}).
	 * 
	 * @param viewBuilder
	 */
	void beforeCreate(HUEditorViewBuilder viewBuilder);

}
