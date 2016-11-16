package de.metas.adempiere.modelvalidator;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_Process;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_Process;

import de.metas.adempiere.process.ExecuteUpdateSQL;

@Interceptor(I_AD_Process.class)
public class AD_Process
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Process.COLUMNNAME_Type)
	public void setClassnameIfTypeSQL(final I_AD_Process process)
	{
		final String processType = process.getType();
		if (X_AD_Process.TYPE_SQL.equals(processType))
		{
			process.setClassname(ExecuteUpdateSQL.class.getName());
		}
	}
}
