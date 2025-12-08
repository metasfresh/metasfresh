package de.metas.fresh.ordercheckup;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.ISingletonService;
import org.compiere.model.I_C_Order;

public interface IOrderCheckupBL extends ISingletonService
{
	/**
	 * @return true if given order is eligible for generating {@link I_C_Order_MFGWarehouse_Report}s.
	 */
	boolean isEligibleForReporting(I_C_Order order);

	/**
	 * Generate all {@link I_C_Order_MFGWarehouse_Report}s if given order is eligible.
	 * 
	 * All previous generated reports will be automatically voided.
	 */
	void generateReportsIfEligible(I_C_Order order);

	/**
	 * Return <code>true</code> if the system is configured to generate the {@link I_C_Order_MFGWarehouse_Report}s when the given order is completed.
	 * <p>
	 * Note that this method can return <code>false</code>, but the order might still be eligible for reporting (see {@link #isEligibleForReporting(I_C_Order)}) if the reporting process is started
	 * manually.
	 */
	boolean isGenerateReportsOnOrderComplete(I_C_Order order);

	/** Void all generated {@link I_C_Order_MFGWarehouse_Report}s for given order */
	void voidReports(I_C_Order order);

	/**
	 * Return the number of copies (2 will result in two printouts in sum) that shall be set to the given <code>C_Printing_Queue</code>.
	 */
	int getNumberOfCopies(I_C_Printing_Queue queueItem, I_AD_Archive printOut);

	I_C_Order_MFGWarehouse_Report getReportOrNull(I_AD_Archive printOut);
}
