package de.metas.payment.esr.actionhandler.impl;

import de.metas.logging.LogManager;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.dataimporter.ESRDataLoaderUtil;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Payment;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Unknown_Invoice}.
 * Does nothing, just informs the user that is an external invoice
 */
public class UnknownInvoiceESRActionHandler extends AbstractESRActionHandler
{
	private static final transient Logger logger = LogManager.getLogger(UnknownInvoiceESRActionHandler.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		super.process(line, message);

		logger.info("Nothing to do here; We can not match any invoice, nor partner");
		return true;

	}
}