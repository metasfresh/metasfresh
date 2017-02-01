package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.List;
import java.util.function.Function;

import org.adempiere.util.Check;

import de.metas.handlingunits.IDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

public final class DocumentLUTUConfigurationManager<T> implements IDocumentLUTUConfigurationManager
{
	private final IDocumentLUTUConfigurationHandler<T> handler;
	private final T _documentLine;

	public DocumentLUTUConfigurationManager(final T documentLine, final IDocumentLUTUConfigurationHandler<T> handler)
	{
		super();

		Check.assumeNotNull(handler, "handler not null");
		this.handler = handler;

		Check.assumeNotNull(documentLine, "documentLine not null");
		this._documentLine = documentLine;
	}

	@Override
	public I_M_HU_LUTU_Configuration createAndEdit(final Function<I_M_HU_LUTU_Configuration, I_M_HU_LUTU_Configuration> lutuConfigurationEditor)
	{
		final ILUTUConfigurationEditor editor = startEditing();
		editor.updateFromModel();

		if (lutuConfigurationEditor != null)
		{
			editor.edit(lutuConfigurationEditor);
			if (!editor.isEditing())
			{
				return null;
			}
		}

		// Save the edited configuration and push it back to model
		editor.pushBackToModel();

		return editor.getLUTUConfiguration();
	}

	@Override
	public ILUTUConfigurationEditor startEditing()
	{
		final ILUTUConfigurationEditor editor = new LUTUConfigurationEditor(this);

		return editor;
	}

	@Override
	public I_M_HU_LUTU_Configuration getCreateLUTUConfiguration()
	{
		//
		// If there is an already existing configuration, return it without any changes
		final I_M_HU_LUTU_Configuration lutuConfigurationExisting = getCurrentLUTUConfigurationOrNull();
		if (lutuConfigurationExisting != null && lutuConfigurationExisting.getM_HU_LUTU_Configuration_ID() > 0)
		{
			return lutuConfigurationExisting;
		}

		//
		// Create a new LU/TU configuration and return it
		final I_M_HU_LUTU_Configuration lutuConfigurationNew = createNewLUTUConfiguration();
		return lutuConfigurationNew;
	}

	@Override
	public I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull()
	{
		final T documentLine = getDocumentLine();
		return handler.getCurrentLUTUConfigurationOrNull(documentLine);
	}

	@Override
	public List<I_M_HU_LUTU_Configuration> getCurrentLUTUConfigurationAlternatives()
	{
		final T documentLine = getDocumentLine();
		return handler.getCurrentLUTUConfigurationAlternatives(documentLine);
	}

	@Override
	public void setCurrentLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final T documentLine = getDocumentLine();
		handler.setCurrentLUTUConfiguration(documentLine, lutuConfiguration);
	}

	@Override
	public void setCurrentLUTUConfigurationAndSave(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		setCurrentLUTUConfiguration(lutuConfiguration);

		// Save it
		final T documentLine = getDocumentLine();
		handler.save(documentLine);
	}

	protected I_M_HU_LUTU_Configuration createNewLUTUConfiguration()
	{
		final T documentLine = getDocumentLine();
		return handler.createNewLUTUConfiguration(documentLine);
	}

	private final T getDocumentLine()
	{
		return _documentLine;
	}

	@Override
	public void updateLUTUConfigurationFromModel(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		final T documentLine = getDocumentLine();
		handler.updateLUTUConfiguration(lutuConfiguration, documentLine);
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
	{
		final T documentLine = getDocumentLine();
		return handler.getM_HU_PI_Item_Product(documentLine);
	}
}
