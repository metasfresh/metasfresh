package org.compiere.process;

import org.compiere.model.I_I_Request;
import org.compiere.model.I_R_Request;

/**
 * Import {@link I_I_Request} records to {@link I_R_Request}.
 * 
 * @author cg
 */
public class ImportRequest extends AbstractImportJavaProcess<I_I_Request>
{

	public ImportRequest()
	{
		super(I_I_Request.class);
	}
}
