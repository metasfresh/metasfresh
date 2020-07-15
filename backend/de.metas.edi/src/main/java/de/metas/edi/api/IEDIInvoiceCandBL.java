package de.metas.edi.api;

import de.metas.edi.model.I_C_Invoice_Candidate;
import de.metas.util.ISingletonService;

public interface IEDIInvoiceCandBL extends ISingletonService
{

	/**
	 * Set isEDIEnabled flag based on the table and record
	 * 
	 * @param candidate
	 * @task http://dewiki908/mediawiki/index.php/08926_EDI-Ausschalten_f%C3%BCr_bestimmte_Belege_%28109751792947%29
	 */
	void setEdiEnabled(final I_C_Invoice_Candidate candidate);
}
