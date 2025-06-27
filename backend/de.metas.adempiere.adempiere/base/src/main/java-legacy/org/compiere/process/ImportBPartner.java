package org.compiere.process;

import de.metas.impexp.processing.IImportProcess;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner;

/**
 * Import {@link I_I_BPartner} records to {@link I_C_BPartner}.
 *
 * @author tsa
 */
public class ImportBPartner extends AbstractImportJavaProcess<I_I_BPartner>
{

	public ImportBPartner()
	{
		super(I_I_BPartner.class);
	}

	@Override
	protected void customizeImportProcess(final @NonNull IImportProcess<I_I_BPartner> importProcess)
	{
		importProcess.async(true);
		importProcess.limit(getBatchSize());
	}
}
