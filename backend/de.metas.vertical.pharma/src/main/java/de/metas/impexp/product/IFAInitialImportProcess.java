package de.metas.impexp.product;

import org.adempiere.exceptions.AdempiereException;

import de.metas.impexp.processing.IImportProcess;
import de.metas.process.JavaProcess;
import de.metas.process.RunOutOfTrx;
import de.metas.vertical.pharma.model.I_I_Pharma_Product;

public class IFAInitialImportProcess extends JavaProcess
{
	private IImportProcess<I_I_Pharma_Product> importProcess;


	@Override
	protected final void prepare()
	{
		importProcess = newInstance(IFAInitialImportProcess2.class);
		importProcess.setCtx(getCtx());
		importProcess.setParameters(getParameterAsIParams());
		importProcess.setLoggable(this);
	}
	
	// NOTE: we shall run this process out of transaction because the actual import process is managing the transaction
	@Override
	@RunOutOfTrx
	protected final String doIt() throws Exception
	{
		importProcess.run();	
		return MSG_OK;
	}
	
	private IImportProcess<I_I_Pharma_Product> newInstance(final Class<?> importProcessClass)
	{
		try
		{
			@SuppressWarnings("unchecked")
			final IImportProcess<I_I_Pharma_Product> importProcess = (IImportProcess<I_I_Pharma_Product>)importProcessClass.newInstance();
			return importProcess;
		}
		catch (Exception e)
		{
			throw new AdempiereException("Failed instantiating " + importProcessClass, e);
		}
	}
}