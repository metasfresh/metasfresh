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


/**
 * Implemented by classes which have {@link IKeyLayoutSelectionModel} support.
 * 
 * Mainly this interface is used in {@link IKeyLayout} implementations to advice the {@link TerminalKeyPanel} to use the {@link IKeyLayout}'s selection model, instead of creating one on
 * {@link TerminalKeyPanel} level.
 * 
 * @author tsa
 *
 */
public interface IKeyLayoutSelectionModelAware
{
	IKeyLayoutSelectionModel getKeyLayoutSelectionModel();
}
