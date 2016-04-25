package org.adempiere.util.testservice.impl;

/*
 * #%L
 * de.metas.util
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


import org.adempiere.util.testservice.IMockedMultitonService;
import org.junit.Ignore;

@Ignore
public class MockedMultitonService implements IMockedMultitonService
{
	public static transient int NEXT_INSTANCE_NUMBER = 1;

	public static final void resetNextInstanceNumber()
	{
		NEXT_INSTANCE_NUMBER = 1;
	}

	private final int instanceNo;

	public MockedMultitonService()
	{
		super();
		instanceNo = NEXT_INSTANCE_NUMBER;
		NEXT_INSTANCE_NUMBER++;
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "instanceNo=" + instanceNo
				+ "]";
	}

	@Override
	public int getInstanceNo()
	{
		return instanceNo;
	}
}
