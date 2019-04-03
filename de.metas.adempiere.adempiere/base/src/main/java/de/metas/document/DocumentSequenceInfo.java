package de.metas.document;

import de.metas.document.sequenceno.CustomSequenceNoProvider;
import lombok.Builder;
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
	private final int adSequenceId;
	private final String name;

	//
	private final int incrementNo;
	private final String prefix;
	private final String suffix;
	private final String decimalPattern;
	private final boolean autoSequence;
	private final boolean startNewYear;
	private final String dateColumn;

	private final CustomSequenceNoProvider customSequenceNoProvider;

	@Builder
	private DocumentSequenceInfo(
			final int adSequenceId,
			final String name,
			final int incrementNo,
			final String prefix,
			final String suffix,
			final String decimalPattern,
			final boolean autoSequence,
			final boolean startNewYear,
			final String dateColumn,
			final CustomSequenceNoProvider customSequenceNoProvider)
	{
		this.adSequenceId = adSequenceId;
		this.name = name;
		this.incrementNo = incrementNo;
		this.prefix = prefix;
		this.suffix = suffix;
		this.decimalPattern = decimalPattern;
		this.autoSequence = autoSequence;
		this.startNewYear = startNewYear;
		this.dateColumn = dateColumn;
		this.customSequenceNoProvider = customSequenceNoProvider;
	}
}
