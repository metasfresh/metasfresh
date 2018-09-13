package de.metas.document;

import org.adempiere.util.Services;
import org.compiere.model.I_AD_Sequence;

import de.metas.document.sequenceno.CustomSequenceNoProvider;
import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.JavaClassId;
import lombok.NonNull;
import lombok.Value;

/**
 * Immutable DocumentNo sequence definition.
 *
 * @author tsa
 *
 */
@Value
public class DocumentSequenceInfo
{
	public static final DocumentSequenceInfo of(@NonNull final I_AD_Sequence adSequence)
	{
		return new DocumentSequenceInfo(adSequence);
	}

	private final int adSequenceId;
	private final String name;

	//
	private final int incrementNo;
	private final String prefix;
	private final String suffix;
	private final String decimalPattern;
	private final boolean isAutoSequence;
	private final boolean isStartNewYear;
	private final String dateColumn;

	private CustomSequenceNoProvider customSequenceNoProvider;

	private DocumentSequenceInfo(@NonNull final I_AD_Sequence adSequence)
	{
		adSequenceId = adSequence.getAD_Sequence_ID();
		name = adSequence.getName();

		//
		incrementNo = adSequence.getIncrementNo();
		prefix = adSequence.getPrefix();
		suffix = adSequence.getSuffix();
		decimalPattern = adSequence.getDecimalPattern();
		isAutoSequence = adSequence.isAutoSequence();
		isStartNewYear = adSequence.isStartNewYear();
		dateColumn = adSequence.getDateColumn();

		final CustomSequenceNoProvider customSequenceNoProvider;
		if (adSequence.getCustomSequenceNoProvider_JavaClass_ID() > 0)
		{
			final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

			final JavaClassId javaClassId = JavaClassId.ofRepoId(adSequence.getCustomSequenceNoProvider_JavaClass_ID());
			customSequenceNoProvider = javaClassBL.newInstance(javaClassId);
		}
		else
		{
			customSequenceNoProvider = null;
		}
		this.customSequenceNoProvider = customSequenceNoProvider;
	}
}
