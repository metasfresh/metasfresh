package de.metas.pos.returns;

import de.metas.inout.InOutId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Repository;

/**
 * Repository Tables: M_InOut, C_Invoice_Candidate
 * Repository Cluster: POSReturnRepository, POSReturnService
 */
@Repository
public class POSReturnRepository
{
	@NonNull
	public I_M_InOut getReturnById(@NonNull final InOutId returnId)
	{
		return InterfaceWrapperHelper.load(returnId, I_M_InOut.class);
	}

	public void save(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		InterfaceWrapperHelper.save(invoiceCandidate);
	}
}
