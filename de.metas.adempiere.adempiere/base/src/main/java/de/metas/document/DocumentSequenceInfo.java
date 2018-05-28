package de.metas.document;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_AD_Sequence;

import lombok.NonNull;

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

	private DocumentSequenceInfo(@NonNull final I_AD_Sequence adSequence)
	{
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
