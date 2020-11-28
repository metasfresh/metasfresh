package de.metas.process.model.interceptor;

import de.metas.impexp.excel.process.ExportToExcelProcess;
import de.metas.postgrest.process.PostgRESTProcessExecutor;
import de.metas.process.ExecuteUpdateSQL;
import de.metas.process.ProcessType;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.MMenu;
import org.compiere.model.MWindow;
import org.compiere.model.ModelValidator;

import java.util.Properties;

import static de.metas.process.ProcessType.POSTGREST;

@Interceptor(I_AD_Process.class)
@Callout(I_AD_Process.class)
public class AD_Process
{
	public static final transient AD_Process instance = new AD_Process();

	private AD_Process()
	{
		Services.get(IProgramaticCalloutProvider.class).registerAnnotatedCallout(this);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Process.COLUMNNAME_Type)
	@CalloutMethod(columnNames = I_AD_Process.COLUMNNAME_Type)
	public void setClassnameIfTypeSQL(final I_AD_Process process)
	{
		final ProcessType processType = ProcessType.ofCode(process.getType());

		switch (processType)
		{
			case SQL:
				process.setClassname(ExecuteUpdateSQL.class.getName());
				break;
			case Excel:
				process.setClassname(ExportToExcelProcess.class.getName());
				break;
			case POSTGREST:
				process.setClassname(PostgRESTProcessExecutor.class.getName());
				break;
		}

		final boolean requiresJSONPath = POSTGREST.equals(processType);

		if (requiresJSONPath && Check.isBlank(process.getJSONPath()))
		{
			throw new FillMandatoryException(I_AD_Process.COLUMNNAME_JSONPath);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = { I_AD_Process.COLUMNNAME_IsActive, I_AD_Process.COLUMNNAME_Name, I_AD_Process.COLUMNNAME_Description,
			I_AD_Process.COLUMNNAME_Help })
	public void propagateNamesAndTrl(final I_AD_Process adProcess)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(adProcess);
		final String trxName = InterfaceWrapperHelper.getTrxName(adProcess);
		for (final I_AD_Menu menu : MMenu.get(ctx, I_AD_Menu.COLUMNNAME_AD_Process_ID + "=" + adProcess.getAD_Process_ID(), trxName))
		{
			menu.setIsActive(adProcess.isActive());
			menu.setName(adProcess.getName());
			menu.setDescription(adProcess.getDescription());
			InterfaceWrapperHelper.save(menu);
		}
		for (final I_AD_WF_Node node : MWindow.getWFNodes(ctx, I_AD_WF_Node.COLUMNNAME_AD_Process_ID + "=" + adProcess.getAD_Process_ID(), trxName))
		{
			boolean changed = false;
			if (node.isActive() != adProcess.isActive())
			{
				node.setIsActive(adProcess.isActive());
				changed = true;
			}
			if (node.isCentrallyMaintained())
			{
				node.setName(adProcess.getName());
				node.setDescription(adProcess.getDescription());
				node.setHelp(adProcess.getHelp());
				changed = true;
			}
			//
			if (changed)
			{
				InterfaceWrapperHelper.save(node);
			}
		}
	}
}
