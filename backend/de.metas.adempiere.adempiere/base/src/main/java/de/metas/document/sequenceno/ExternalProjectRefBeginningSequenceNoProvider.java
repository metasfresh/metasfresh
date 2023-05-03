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
		String seqNo = super.provideSeqNo(incrementalSeqNoSupplier, context, documentSequenceInfo);
		return seqNo.substring(0, seqNo.lastIndexOf("-")) + "-" + seqNo.substring(seqNo.lastIndexOf("-")+1);
	}

}
