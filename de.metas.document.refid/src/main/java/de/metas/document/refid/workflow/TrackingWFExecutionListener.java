package de.metas.document.refid.workflow;

import de.metas.document.refid.api.IReferenceNoBL;
import de.metas.util.Services;
import de.metas.workflow.api.IWFExecutionListener;

/**
 * Responsibilities: assign <code>toModel</code> to same reference numbers as it's <code>fromModel</code> assigned
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03745_Implement_case_number_tracking_%282013010310000039%29
 */
public class TrackingWFExecutionListener implements IWFExecutionListener
{
	@Override
	public void onActivityPerformed(Object fromModel, Object toModel)
	{
		Services.get(IReferenceNoBL.class).linkOnSameReferenceNo(fromModel, toModel);
	}
}
