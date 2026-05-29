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

import de.metas.materialtracking.IHandlingUnitsInfo;
import de.metas.materialtracking.impl.PlainHandlingUnitsInfo;

import javax.annotation.Nullable;

import static org.assertj.core.api.Assertions.assertThat;

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
	
	public void assertExpected(@Nullable final String message, final IHandlingUnitsInfo info)
	{
		assertThat(info).as("info not null").isNotNull();

		final String prefix = (message == null ? "" : message) 
				+ " Invalid \"" + info + "\" - ";

		if (this.qtyTU != null)
		{
			assertThat(info.getQtyTU()).as(prefix + "QtyTU").isEqualTo(this.qtyTU);
		}
		if (this.tuName != null)
		{
			assertThat(info.getTUName()).as(prefix + "TU Name").isEqualTo(this.tuName);
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
