package de.metas.globalid.impexp;

import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_I_BPartner_GlobalID;
import org.compiere.process.AbstractImportJavaProcess;

/**
 * Import {@link I_I_BPartner_GlobalID} records to {@link I_C_BPartner}.
 * 
 * @author cg
 */
public class ImportBPartnerGlobalID extends AbstractImportJavaProcess<I_I_BPartner_GlobalID>
{

	public ImportBPartnerGlobalID()
	{
		super(I_I_BPartner_GlobalID.class);
	}
}
