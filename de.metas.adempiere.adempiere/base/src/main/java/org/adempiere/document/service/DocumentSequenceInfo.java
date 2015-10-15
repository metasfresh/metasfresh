package org.adempiere.document.service;

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


import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_AD_Sequence;

/**
 * Immutable DocumentNo sequence definition.
 * 
 * @author tsa
 *
 */
public class DocumentSequenceInfo
{
	public static final DocumentSequenceInfo of(final I_AD_Sequence adSequence)
	{
		return new DocumentSequenceInfo(adSequence);
	}

	private final int adSequenceId;
	private final String name;
	private final int adClientId;
	private final int adOrgId;
	//
	private final int incrementNo;
	private final String prefix;
	private final String suffix;
	private final String decimalPattern;
	private final boolean isAutoSequence;
	private final boolean isStartNewYear;
	private final String dateColumn;

	private DocumentSequenceInfo(final I_AD_Sequence adSequence)
	{
		super();

		Check.assumeNotNull(adSequence, "adSequence not null");
		adSequenceId = adSequence.getAD_Sequence_ID();
		name = adSequence.getName();
		adClientId = adSequence.getAD_Client_ID();
		adOrgId = adSequence.getAD_Org_ID();
		//
		incrementNo = adSequence.getIncrementNo();
		prefix = adSequence.getPrefix();
		suffix = adSequence.getSuffix();
		decimalPattern = adSequence.getDecimalPattern();
		isAutoSequence = adSequence.isAutoSequence();
		isStartNewYear = adSequence.isStartNewYear();
		dateColumn = adSequence.getDateColumn();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public int getAD_Sequence_ID()
	{
		return adSequenceId;
	}

	public String getName()
	{
		return name;
	}

	public int getAD_Client_ID()
	{
		return adClientId;
	}

	public int getAD_Org_ID()
	{
		return adOrgId;
	}

	public int getIncrementNo()
	{
		return incrementNo;
	}

	public String getPrefix()
	{
		return prefix;
	}

	public String getSuffix()
	{
		return suffix;
	}

	public String getDecimalPattern()
	{
		return decimalPattern;
	}

	public boolean isAutoSequence()
	{
		return isAutoSequence;
	}

	public boolean isStartNewYear()
	{
		return isStartNewYear;
	}

	public String getDateColumn()
	{
		return dateColumn;
	}
}
