package de.metas.banking.payment.process;

import org.compiere.model.I_C_Payment;
import org.compiere.process.AbstractImportJavaProcess;

import de.metas.banking.model.I_I_Datev_Payment;

/**
 * @author metas-dev <dev@metasfresh.com>
 * 
 * Import {@link I_I_Datev_Payment} records to {@link I_C_Payment}.
 *
 */
public class ImportDatevPayment extends AbstractImportJavaProcess<I_I_Datev_Payment>
{

	public ImportDatevPayment()
	{
		super(I_I_Datev_Payment.class);
	}
}
