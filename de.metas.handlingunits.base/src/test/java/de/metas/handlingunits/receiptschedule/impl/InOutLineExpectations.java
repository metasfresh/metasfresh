package de.metas.handlingunits.receiptschedule.impl;

import static org.assertj.core.api.Assertions.assertThat;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.handlingunits.expectations.AbstractHUExpectation;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;

public class InOutLineExpectations<ParentExpectationType> extends AbstractHUExpectation<ParentExpectationType>
{
	private final List<InOutLineExpectation<InOutLineExpectations<ParentExpectationType>>> expectations = new ArrayList<>();

	private InOutLineExpectations(final ParentExpectationType parent)
	{
		super(parent);
	}

	public static InOutLineExpectations<Object> newExpectations()
	{
		final InOutLineExpectations<Object> inoutLineExpectations = new InOutLineExpectations<>(null);
		inoutLineExpectations.setContext(PlainContextAware.newOutOfTrxAllowThreadInherited(Env.getCtx()));
		return inoutLineExpectations;
	}

	public final InOutLineExpectation<InOutLineExpectations<ParentExpectationType>> newInOutLineExpectation()
	{
		final InOutLineExpectation<InOutLineExpectations<ParentExpectationType>> expectation = new InOutLineExpectation<>(this, null);
		expectations.add(expectation);
		return expectation;
	}

	public final InOutLineExpectations<ParentExpectationType> assertExpected(final I_M_InOut document)
	{
		final List<I_M_InOutLine> documentLines = Services.get(IInOutDAO.class).retrieveLines(document, I_M_InOutLine.class);

		assertThat(documentLines).as("Invalid inout lines count").hasSize(expectations.size());

		for (int i = 0; i < expectations.size(); i++)
		{
			final I_M_InOutLine inOutLine = documentLines.get(i);
			expectations.get(i).assertExpected(inOutLine);
		}

		return this;
	}
}
