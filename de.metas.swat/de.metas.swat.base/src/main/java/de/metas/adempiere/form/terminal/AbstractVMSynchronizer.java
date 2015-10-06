package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Template used to do View to Model synchronizations.
 * 
 * How to use it?
 * <ul>
 * <li>implement {@link #onSaveToModel()}
 * <li>implement {@link #onLoadFromModel()}
 * <li>on your view components add {@link #getSaveToModelPropertyChangeListener()} listener
 * <li>in view class initialization call {@link #loadFromModel()} to have fetch all informations from model and set them to view components
 * <li>in your processing methods, first please call {@link #saveToModel()} to be sure that View data is completely pushed to Model.
 * </ul>
 * 
 * @author tsa
 * 
 */
public abstract class AbstractVMSynchronizer
{
	/**
	 * true if we have a loading operation which runs right now
	 */
	private boolean loadFromModelRunning = false;

	public final void saveToModel()
	{
		if (loadFromModelRunning)
		{
			return;
		}

		onSaveToModel();
	}

	/**
	 * Method called when we need to set data from View to Model
	 */
	protected abstract void onSaveToModel();

	/**
	 * Method called when we need to load data from Model to View
	 */
	protected abstract void onLoadFromModel();

	public final void loadFromModel()
	{
		if (loadFromModelRunning)
		{
			return;
		}

		loadFromModelRunning = true;
		try
		{
			onLoadFromModel();
		}
		finally
		{
			loadFromModelRunning = false;
		}
	}

	private final PropertyChangeListener saveToModelPropertyChangeListener = new PropertyChangeListener()
	{

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			saveToModel();
		}
	};

	/**
	 * Gets {@link PropertyChangeListener} to be used on view components.
	 * 
	 * When this listener is triggered then a {@link #saveToModel()} operation is executed.
	 * 
	 * @return
	 */
	public final PropertyChangeListener getSaveToModelPropertyChangeListener()
	{
		return saveToModelPropertyChangeListener;
	}

	private final PropertyChangeListener loadFromModelPropertyChangeListener = new PropertyChangeListener()
	{

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			loadFromModel();
		}
	};

	/**
	 * Gets {@link PropertyChangeListener} to be used on model components.
	 * 
	 * When this listener is triggered then a {@link #loadFromModel()} operation is executed.
	 * 
	 * @return
	 */
	public final PropertyChangeListener getLoadFromModelPropertyChangeListener()
	{
		return loadFromModelPropertyChangeListener;
	}

}
