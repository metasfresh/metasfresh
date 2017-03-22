/**
 * 
 */
package de.metas.process.callout;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;

/**
 * @author tsa
 * 
 */
@Callout(I_AD_Process_Para.class)
public class AD_Process_Para
{
	@CalloutMethod(columnNames = I_AD_Process_Para.COLUMNNAME_AD_Element_ID)
	public void onAD_Element_ID(final I_AD_Process_Para pp)
	{
		if (pp.getAD_Element_ID() <= 0)
		{
			return;
		}

		final boolean centrallyMaintained = pp.isCentrallyMaintained();
		final I_AD_Element adElement = pp.getAD_Element();

		pp.setColumnName(adElement.getColumnName());
		if (centrallyMaintained || Check.isEmpty(pp.getName()))
			pp.setName(adElement.getName());
		if (centrallyMaintained || Check.isEmpty(pp.getDescription()))
			pp.setDescription(adElement.getDescription());
		if (centrallyMaintained || Check.isEmpty(pp.getHelp()))
			pp.setHelp(adElement.getHelp());

		String entityType = adElement.getEntityType();
		if ("D".equals(entityType))
		{
			final I_AD_Process process = pp.getAD_Process();
			entityType = process.getEntityType();
		}
		if (!"D".equals(entityType))
		{
			pp.setEntityType(entityType);
		}
	}

}
