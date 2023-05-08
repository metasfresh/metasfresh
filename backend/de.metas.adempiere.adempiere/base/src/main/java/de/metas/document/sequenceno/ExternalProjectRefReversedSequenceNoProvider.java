package de.metas.document.sequenceno;

import lombok.NonNull;

import java.text.DecimalFormat;

public class ExternalProjectRefReversedSequenceNoProvider extends AbstractExternalProjectRefSequenceNoProvider
{
	@NonNull
	@Override
	protected String formatSeqNo(@NonNull final String decimalPattern, final int incrementalSeqNoInt)
	{
		final String customPart = getCustomPart();
		return new DecimalFormat(decimalPattern).format(incrementalSeqNoInt) + "-" + customPart;
	}
}
