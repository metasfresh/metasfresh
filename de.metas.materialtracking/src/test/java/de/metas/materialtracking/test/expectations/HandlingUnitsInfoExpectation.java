package de.metas.materialtracking.test.expectations;

/*
 * #%L
 * de.metas.materialtracking
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


import org.junit.Assert;

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.impl.PlainHandlingUnitsInfo;

public class HandlingUnitsInfoExpectation<ParentExpectationType> extends AbstractExpectation
{
	private final ParentExpectationType parent;

	private Integer qtyTU = null;
	private String tuName = null;

	public HandlingUnitsInfoExpectation(ParentExpectationType parent)
	{
		super();
		this.parent = parent;
	}

	@Override
	public String toString()
	{
		return "HandlingUnitsInfoExpectation ["
				+ "tuName=" + tuName
				+ ", qtyTU=" + qtyTU
				+ "]";
	}

	public ParentExpectationType endExpectation()
	{
		return parent;
	}

	public void assertExpected(final IHandlingUnitsInfo info)
	{
		final String message = null;
		assertExpected(message, info);
	}
	
	public void assertExpected(final String message, final IHandlingUnitsInfo info)
	{
		Assert.assertNotNull("info not null", info);

		final String prefix = (message == null ? "" : message) 
				+ " Invalid \"" + info + "\" - ";

		if (this.qtyTU != null)
		{
			Assert.assertEquals(prefix + "QtyTU", (int)this.qtyTU, info.getQtyTU());
		}
		if (this.tuName != null)
		{
			Assert.assertEquals(prefix + "TU Name", this.tuName, info.getTUName());
		}
	}

	public HandlingUnitsInfoExpectation<ParentExpectationType> qtyTU(final int qtyTU)
	{
		this.qtyTU = qtyTU;
		return this;
	}

	public HandlingUnitsInfoExpectation<ParentExpectationType> tuName(final String tuName)
	{
		this.tuName = tuName;
		return this;
	}

	public PlainHandlingUnitsInfo createPlainHandlingUnitsInfo()
	{
		return new PlainHandlingUnitsInfo(tuName, qtyTU);
	}
}
