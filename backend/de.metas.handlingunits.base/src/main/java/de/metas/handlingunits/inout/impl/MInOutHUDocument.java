package de.metas.handlingunits.inout.impl;

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
import java.util.Collections;
import java.util.List;

import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.document.IHUDocumentLine;
import de.metas.handlingunits.document.impl.AbstractHUDocument;
import de.metas.util.Check;

/* package */class MInOutHUDocument extends AbstractHUDocument
{

	private final I_M_InOut inOut;
	private final List<IHUDocumentLine> lines;

	public MInOutHUDocument(final I_M_InOut inOut, final List<IHUDocumentLine> lines)
	{
		super();

		Check.assumeNotNull(inOut, "inOut not null");
		Check.assume(inOut.getM_InOut_ID() > 0, "{} is saved", inOut);
		this.inOut = inOut;

		Check.assumeNotNull(lines, "lines not null");
		this.lines = Collections.unmodifiableList(new ArrayList<IHUDocumentLine>(lines));
	}

	@Override
	public String getDisplayName()
	{
		return inOut.getDocumentNo();
	}

	@Override
	public List<IHUDocumentLine> getLines()
	{
		return lines;
	}

	private MInOutHUDocument reversal = null;

	@Override
	public MInOutHUDocument getReversal()
	{
		if (reversal == null)
		{
			reversal = new MInOutHUDocument(inOut.getReversal(), getReversalLines());
			reversal.setReversal(this);
		}
		return reversal;
	}

	private void setReversal(final MInOutHUDocument reversal)
	{
		this.reversal = reversal;
	}

}
