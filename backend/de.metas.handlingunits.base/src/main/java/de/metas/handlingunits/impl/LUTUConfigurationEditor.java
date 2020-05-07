package de.metas.handlingunits.impl;

import java.util.function.Function;

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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;

/* package */class LUTUConfigurationEditor implements ILUTUConfigurationEditor
{
	//
	// Services
	private final transient ILUTUConfigurationFactory lutuFactory = Services.get(ILUTUConfigurationFactory.class);

	private final IDocumentLUTUConfigurationManager lutuConfigurationManager;

	private I_M_HU_LUTU_Configuration _lutuConfigurationInitial;
	private I_M_HU_LUTU_Configuration _lutuConfigurationEditing;

	public LUTUConfigurationEditor(final IDocumentLUTUConfigurationManager lutuConfigurationManager)
	{
		super();

		Check.assumeNotNull(lutuConfigurationManager, "lutuConfigurationManager not null");
		this.lutuConfigurationManager = lutuConfigurationManager;

		init();
	}

	private void init()
	{
		_lutuConfigurationInitial = lutuConfigurationManager.getCreateLUTUConfiguration();
		_lutuConfigurationEditing = lutuFactory.copy(_lutuConfigurationInitial);
	}

	@Override
	public I_M_HU_LUTU_Configuration getLUTUConfiguration()
	{
		Check.assumeNotNull(_lutuConfigurationInitial, "lutuConfigurationInitial not null");
		return _lutuConfigurationInitial;
	}

	@Override
	public I_M_HU_LUTU_Configuration getEditingLUTUConfiguration()
	{
		return _lutuConfigurationEditing;
	}

	private ILUTUConfigurationEditor setEditingLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfiguration)
	{
		if (lutuConfiguration != null && lutuConfiguration.getM_HU_LUTU_Configuration_ID() > 0)
		{
			throw new IllegalArgumentException("Editing configuration shall be a configuration which is not saved yet");
		}

		_lutuConfigurationEditing = lutuConfiguration;

		return this;
	}

	@Override
	public ILUTUConfigurationEditor edit(final Function<I_M_HU_LUTU_Configuration, I_M_HU_LUTU_Configuration> lutuConfigurationEditor)
	{
		assertEditing();

		Check.assumeNotNull(lutuConfigurationEditor, "lutuConfigurationEditor not null");

		final I_M_HU_LUTU_Configuration lutuConfigurationEditing = getEditingLUTUConfiguration();
		final I_M_HU_LUTU_Configuration lutuConfigurationEditingNew = lutuConfigurationEditor.apply(lutuConfigurationEditing);
		setEditingLUTUConfiguration(lutuConfigurationEditingNew);

		return this;
	}

	@Override
	public ILUTUConfigurationEditor updateFromModel()
	{
		assertEditing();

		final I_M_HU_LUTU_Configuration lutuConfigurationEditing = getEditingLUTUConfiguration();
		lutuConfigurationManager.updateLUTUConfigurationFromModel(lutuConfigurationEditing);

		return this;
	}

	@Override
	public boolean isEditing()
	{
		return _lutuConfigurationEditing != null;
	}

	private final void assertEditing()
	{
		Check.assume(isEditing(), HUException.class, "Editor shall be in editing mode");
	}

	@Override
	public ILUTUConfigurationEditor save()
	{
		assertEditing();

		final I_M_HU_LUTU_Configuration lutuConfigurationInitial = getLUTUConfiguration();
		final I_M_HU_LUTU_Configuration lutuConfigurationEditing = getEditingLUTUConfiguration();

		//
		// Check if we need to use the new configuration or we are ok with the old one
		final I_M_HU_LUTU_Configuration lutuConfigurationToUse;
		if (InterfaceWrapperHelper.isNew(lutuConfigurationInitial))
		{
			lutuFactory.save(lutuConfigurationEditing);
			lutuConfigurationToUse = lutuConfigurationEditing;
		}
		else if (lutuFactory.isSameForHUProducer(lutuConfigurationInitial, lutuConfigurationEditing))
		{
			// Copy all values from our new configuration to initial configuration
			InterfaceWrapperHelper.copyValues(lutuConfigurationEditing, lutuConfigurationInitial, false); // honorIsCalculated=false
			lutuFactory.save(lutuConfigurationInitial);
			lutuConfigurationToUse = lutuConfigurationInitial;
		}
		else
		{
			lutuFactory.save(lutuConfigurationEditing);
			lutuConfigurationToUse = lutuConfigurationEditing;
		}

		_lutuConfigurationInitial = lutuConfigurationToUse;
		_lutuConfigurationEditing = null; // stop editing mode

		return this;
	}

	@Override
	public ILUTUConfigurationEditor pushBackToModel()
	{
		if (isEditing())
		{
			save();
		}

		final I_M_HU_LUTU_Configuration lutuConfiguration = getLUTUConfiguration();
		lutuConfigurationManager.setCurrentLUTUConfigurationAndSave(lutuConfiguration);

		return this;
	}
}
