package de.metas.document;

import de.metas.document.impl.SequenceRestartFrequencyEnum;
import de.metas.document.sequenceno.CustomSequenceNoProvider;
import lombok.Builder;
import lombok.Value;
import org.adempiere.ad.expression.api.IStringExpression;

import javax.annotation.Nullable;

/**
 * Immutable DocumentNo sequence definition.
 *
 * @author tsa
 */
@Value
public class DocumentSequenceInfo
{
	int adSequenceId;
	String name;

	//
	int incrementNo;
	IStringExpression prefix;
	IStringExpression suffix;
	String decimalPattern;
	boolean autoSequence;
	SequenceRestartFrequencyEnum restartFrequency;
	String dateColumn;

	CustomSequenceNoProvider customSequenceNoProvider;

	@Builder
	private DocumentSequenceInfo(
			final int adSequenceId,
			final String name,
			final int incrementNo,
			final IStringExpression prefix,
			final IStringExpression suffix,
			final String decimalPattern,
			final boolean autoSequence,
			@Nullable final SequenceRestartFrequencyEnum restartFrequency,
			final String dateColumn,
			final CustomSequenceNoProvider customSequenceNoProvider)
	{
		this.adSequenceId = adSequenceId;
		this.name = name;
		this.incrementNo = incrementNo;
		this.prefix = prefix != null ? prefix : IStringExpression.NULL;
		this.suffix = suffix != null ? suffix : IStringExpression.NULL;
		this.decimalPattern = decimalPattern;
		this.autoSequence = autoSequence;
		this.restartFrequency = restartFrequency;
		this.dateColumn = dateColumn;
		this.customSequenceNoProvider = customSequenceNoProvider;
	}

	public boolean isStartNewMonth()
	{
		return restartFrequency == SequenceRestartFrequencyEnum.Month;
	}

	public boolean isStartNewDay()
	{
		return restartFrequency == SequenceRestartFrequencyEnum.Day;
	}
}
