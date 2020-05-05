package de.metas.dunning.exception;

/*
 * #%L
 * de.metas.dunning
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


import de.metas.dunning.interfaces.I_C_Dunning;

/**
 * Exception to be thrown for dunning functionalities that are not implemented yet
 * 
 * @author tsa
 * 
 */
public class NotImplementedDunningException extends DunningException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2457493805503996580L;
	/**
	 * i.e. {@link I_C_Dunning#isCreateLevelsSequentially()} == false
	 */
	public static final String FEATURE_NonSequentialDunning = "NotSequentialDunnings";

	public NotImplementedDunningException(String featureName)
	{
		super("Feature not implemented: " + featureName);
	}
}
