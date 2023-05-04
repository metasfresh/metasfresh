package de.metas.document.sequenceno;

import de.metas.document.DocumentSequenceInfo;
import io.micrometer.core.lang.NonNull;
import org.compiere.util.Evaluatee;
import java.util.function.Supplier;

public class ExternalProjectRefReversedSequenceNoProvider extends AbstractExternalProjectRefSequenceNoProvider
{
	@Override
	public boolean isApplicable(@lombok.NonNull final Evaluatee context, @lombok.NonNull final DocumentSequenceInfo docSeqInfo)
	{
		return true;
	}

	public @NonNull String provideCustomSeqNo(
			@NonNull final Supplier<String> incrementalSeqNoSupplier,
			@NonNull final Evaluatee context,
			@NonNull final DocumentSequenceInfo documentSequenceInfo)
	{
		final String parentResult = super.provideSeqNo(incrementalSeqNoSupplier, context, documentSequenceInfo);
		final String[] parts = parentResult.split("-");
		return parts[1] + "-" + parts[0];
	}
}
