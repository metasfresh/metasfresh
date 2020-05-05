package de.metas.acct.gljournal.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.math.BigDecimal;

import de.metas.acct.gljournal.IGLJournalLineGroup;
import de.metas.util.Check;

public class GLJournalLineGroup implements IGLJournalLineGroup
{
	private final int groupNo;
	private final BigDecimal amtDr;
	private final BigDecimal amtCr;

	public GLJournalLineGroup(int groupNo, BigDecimal amtDr, BigDecimal amtCr)
	{
		super();

		Check.assume(groupNo > 0, "groupNo > 0");
		this.groupNo = groupNo;

		Check.assumeNotNull(amtDr, "amtDr not null");
		this.amtDr = amtDr;

		Check.assumeNotNull(amtCr, "amtCr not null");
		this.amtCr = amtCr;
	}

	@Override
	public String toString()
	{
		return "GLJournalLineGroup [groupNo=" + groupNo + ", amtDr=" + amtDr + ", amtCr=" + amtCr + "]";
	}
	
	@Override
	public BigDecimal getBalance()
	{
		return getAmtDr().subtract(getAmtCr());
	}

	@Override
	public BigDecimal getAmtDr()
	{
		return amtDr;
	}

	@Override
	public BigDecimal getAmtCr()
	{
		return amtCr;
	}

	@Override
	public int getGroupNo()
	{
		return groupNo;
	}

}
