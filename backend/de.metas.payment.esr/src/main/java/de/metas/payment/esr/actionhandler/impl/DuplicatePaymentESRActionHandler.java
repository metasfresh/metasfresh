package de.metas.payment.esr.actionhandler.impl;

import de.metas.logging.LogManager;
import de.metas.payment.PaymentId;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.api.impl.ESRImportBL;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.util.Services;
import org.slf4j.Logger;

import java.util.Optional;

/**
 * Handler for {@link de.metas.payment.esr.model.X_ESR_ImportLine#ESR_PAYMENT_ACTION_Duplicate_Payment}. This handler links esr line with existent payment
 */
public class DuplicatePaymentESRActionHandler extends AbstractESRActionHandler
{

	private static final transient Logger logger = LogManager.getLogger(ESRImportBL.class);
	private final IESRImportDAO esrImportDAO = Services.get(IESRImportDAO.class);

	@Override
	public boolean process(final I_ESR_ImportLine line, final String message)
	{
		super.process(line, message);

		final Optional<PaymentId> existentPaymentId = esrImportDAO.findExistentPaymentId(line);

		if (existentPaymentId.isPresent())
		{
			return true;
		}
		else
		{
			logger.warn("No payment found for line : " + line.getESR_ImportLine_ID());
			return false;
		}
	}

}
