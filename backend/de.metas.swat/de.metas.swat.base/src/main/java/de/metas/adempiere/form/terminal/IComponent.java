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


import de.metas.adempiere.form.terminal.context.ITerminalContext;

public interface IComponent extends IDisposable
{
	/**
	 * Return GUI component.
	 * 
	 * WARNING: this method is returning the actually GUI specific component and NOT the {@link IComponent} !!!
	 * 
	 * @return GUI component
	 */
	Object getComponent();

	/**
	 * @return the component's {@link ITerminalContext}
	 */
	ITerminalContext getTerminalContext();

	/**
	 * Dispose this component
	 */
	@Override
	void dispose();
}
