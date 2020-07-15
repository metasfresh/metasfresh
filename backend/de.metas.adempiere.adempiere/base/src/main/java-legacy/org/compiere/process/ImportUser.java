package org.compiere.process;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_I_User;


/**
 * Import {@link I_I_User} records to {@link I_AD_User}.
 * 
 * @author cg
 */
public class ImportUser extends AbstractImportJavaProcess<I_I_User>
{

	public ImportUser()
	{
		super(I_I_User.class);
	}
}
