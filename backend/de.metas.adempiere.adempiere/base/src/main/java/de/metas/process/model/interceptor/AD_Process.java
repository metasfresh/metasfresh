package de.metas.process.model.interceptor;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_WF_Node;
import org.compiere.model.MMenu;
import org.compiere.model.MWindow;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_AD_Process;

import de.metas.process.ExecuteUpdateSQL;

@Interceptor(I_AD_Process.class)
public class AD_Process
{
	public static final transient AD_Process instance = new AD_Process();

	private AD_Process()
	{
		super();
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_AD_Process.COLUMNNAME_Type)
	public void setClassnameIfTypeSQL(final I_AD_Process process)
	{
		final String processType = process.getType();
		if (X_AD_Process.TYPE_SQL.equals(processType))
		{
			process.setClassname(ExecuteUpdateSQL.class.getName());
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
