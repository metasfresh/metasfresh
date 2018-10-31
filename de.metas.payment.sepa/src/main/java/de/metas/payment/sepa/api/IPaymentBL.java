package de.metas.payment.sepa.api;

import org.compiere.model.I_C_PaySelection;

import de.metas.payment.sepa.model.I_SEPA_Export;
import de.metas.util.ISingletonService;

public interface IPaymentBL extends ISingletonService
{
	/**
	 * Creates a SEPA export from an {@link I_C_PaySelection}.
	 */
	I_SEPA_Export createSEPAExport(I_C_PaySelection source);

}
