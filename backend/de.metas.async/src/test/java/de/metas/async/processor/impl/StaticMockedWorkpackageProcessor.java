package de.metas.async.processor.impl;

/*
 * #%L
 * de.metas.async
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


import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;

/**
 * Wraps one instance of {@link MockedWorkpackageProcessor} and always delegate all method calls to that instance.
 * 
 * In this way we ensure that even if we instantiate this class more then one time, a common {@link MockedWorkpackageProcessor} will be shared.
 * 
 * @author tsa
 * 
 */
public class StaticMockedWorkpackageProcessor implements IWorkpackageProcessor
{
	private static MockedWorkpackageProcessor processor = new MockedWorkpackageProcessor();

	@Override
	public Result processWorkPackage(I_C_Queue_WorkPackage workpackage, String localTrxName)
	{
		return processor.processWorkPackage(workpackage, localTrxName);
	}

	/**
	 * 
	 * @return underlying {@link MockedWorkpackageProcessor}
	 */
	public static MockedWorkpackageProcessor getMockedWorkpackageProcessor()
	{
		return processor;
	}

	public static void reset()
	{
		processor = new MockedWorkpackageProcessor();
	}
}
