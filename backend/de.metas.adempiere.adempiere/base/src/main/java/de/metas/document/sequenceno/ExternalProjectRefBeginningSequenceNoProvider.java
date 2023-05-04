package de.metas.document.sequenceno;

import de.metas.document.DocumentSequenceInfo;
import io.micrometer.core.lang.NonNull;
import org.compiere.util.Evaluatee;

import java.text.DecimalFormat;
import java.util.function.Supplier;

public class ExternalProjectRefBeginningSequenceNoProvider extends ExternalProjectRefSequenceNoProvider {

	@Override
	public @NonNull String provideSeqNo(
			@NonNull final Supplier<String> incrementalSeqNoSupplier,
			@NonNull final Evaluatee context,
			@NonNull final DocumentSequenceInfo documentSequenceInfo)
	{
		final String incrementalSeqNo = incrementalSeqNoSupplier.get();
		final String customPart = getCustomPart();

		final String decimalPattern = documentSequenceInfo.getDecimalPattern();
		if (decimalPattern == null)
		{
			return incrementalSeqNo + "-" + customPart;
		}

		final int incrementalSeqNoInt = Integer.parseInt(incrementalSeqNo);

		return new DecimalFormat(decimalPattern).format(incrementalSeqNoInt) + "-" + customPart;
	}
}
