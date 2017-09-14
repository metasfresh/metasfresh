package de.metas.payment.esr.process;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.payment.esr.ESRConstants;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuer;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuerDataSource;
import de.metas.payment.esr.dataimporter.ESRImportEnqueuerDuplicateFilePolicy;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.RunOutOfTrx;
import lombok.NonNull;

/**
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ESR_Import_LoadFromFile
		extends JavaProcess
		implements IProcessDefaultParametersProvider
{
	// services
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	// Parameters
	@Param(mandatory = false, parameterName = I_ESR_Import.COLUMNNAME_ESR_Import_ID)
	private int p_ESR_Import_ID;

	@Param(mandatory = true, parameterName = "FileName")
	private String p_FileName;

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_Name)
	private String p_AsyncBatchName = "ESR Import";

	@Param(mandatory = true, parameterName = I_C_Async_Batch.COLUMNNAME_Description)
	private String p_AsyncBatchDesc = "ESR Import process";

	@Override
	public Object getParameterDefaultValue(@NonNull final IProcessDefaultParameter parameter)
	{
		if (I_ESR_Import.COLUMNNAME_ESR_Import_ID.equals(parameter.getColumnName()))
		{
			final int esrImportId = parameter.getContextAsInt(I_ESR_Import.COLUMNNAME_ESR_Import_ID);
			if (esrImportId > 0)
			{
				return esrImportId;
			}
		}
		return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
	}

	@Override
	@RunOutOfTrx // ...because we might show a dialog to the user
	protected String doIt() throws Exception
	{
		ESRImportEnqueuer.newInstance()
				.esrImport(retrieveESR_Import())
				.fromDataSource(ESRImportEnqueuerDataSource.ofFile(p_FileName))
				//
				.asyncBatchName(p_AsyncBatchName)
				.asyncBatchDesc(p_AsyncBatchDesc)
				.adPInstanceId(getAD_PInstance_ID())
				//
				.loggable(this)
				//
				.duplicateFilePolicy(new ESRImportEnqueuerDuplicateFilePolicy()
				{

					@Override
					public boolean isImportDuplicateFile()
					{
						// then show the user a warning with yes and no
						return clientUI.ask(Env.WINDOW_MAIN, ESRConstants.ASK_PreventDuplicateImportFiles);
					}

					@Override
					public void onNotImportingDuplicateFile()
					{
						// show the user a warning with OK button
						clientUI.warn(Env.WINDOW_MAIN, ESRConstants.WARN_PreventDuplicateImportFilesEntirely);
					}
				})
				//
				.execute();

		return MSG_OK;
	}

	private final I_ESR_Import retrieveESR_Import()
	{
		if (p_ESR_Import_ID <= 0)
		{
			if (I_ESR_Import.Table_Name.equals(getTableName()))
			{
				p_ESR_Import_ID = getRecord_ID();
			}
			if (p_ESR_Import_ID <= 0)
			{
				throw new FillMandatoryException(I_ESR_Import.COLUMNNAME_ESR_Import_ID);
			}
		}

		final I_ESR_Import esrImport = InterfaceWrapperHelper.create(getCtx(), p_ESR_Import_ID, I_ESR_Import.class, get_TrxName());
		if (esrImport == null)
		{
			throw new AdempiereException("@NotFound@ @ESR_Import_ID@");
		}
		return esrImport;
	}
}
