package de.metas.payment.esr.document.refid.spi.impl;

import lombok.NonNull;

import org.compiere.model.I_C_Invoice;

import de.metas.document.refid.spi.IReferenceNoGenerator;
import de.metas.payment.esr.api.IESRBL;

/**
 * The implementation was moved to {@link IESRBL#createESRPaymentRequest(I_C_Invoice)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class RenderedCodeLineGenerator implements IReferenceNoGenerator
{
	@Override
	public String generateReferenceNo(@NonNull final Object sourceModel)
	{
		throw new UnsupportedOperationException("This method shall not be invoked. Rendered code lines are now created together with the respective C_Payment_Request");
	}
}
