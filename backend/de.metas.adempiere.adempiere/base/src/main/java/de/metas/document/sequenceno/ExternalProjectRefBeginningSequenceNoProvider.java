package de.metas.document.sequenceno;

import de.metas.document.DocumentSequenceInfo;
import org.compiere.util.Evaluatee;
import java.util.function.Supplier;

public class ExternalProjectRefBeginningSequenceNoProvider extends ExternalProjectRefSequenceNoProvider {

	@Override
	public String provideSeqNo(
			Supplier<String> incrementalSeqNoSupplier,
			Evaluatee context,
			DocumentSequenceInfo documentSequenceInfo)
	{
		String documentSeqNo = super.provideSeqNo(incrementalSeqNoSupplier, context, documentSequenceInfo);
		return documentSeqNo.substring(documentSeqNo.lastIndexOf("-")+1) + "-" + documentSeqNo.substring(0, documentSeqNo.lastIndexOf("-"));
	}
}
