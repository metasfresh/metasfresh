/**
 * 
 */
package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.zkoss.zk.ui.Component;

import de.metas.adempiere.webui.component.WizardPage;
import de.metas.adempiere.webui.component.WizardPanel;

/**
 * @author tsa
 * 
 */
public class WB2BOrderHistoryWizardPage implements WizardPage
{
	private WB2BOrderHistoryPanel panel = null;
	private WizardPanel wizardPanel;
	private final int windowNo;
	private boolean isInitialized;
	private int order_id = -1;

	public WB2BOrderHistoryWizardPage(int windowNo)
	{
		this.windowNo = windowNo;
	}

	@Override
	public void init()
	{
		panel = new WB2BOrderHistoryPanel(windowNo, false);
		this.isInitialized = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.adempiere.webui.component.WizardPage#getComponent()
	 */
	@Override
	public Component getComponent()
	{
		return panel;
	}

	@Override
	public boolean isAllowAction(String name)
	{
		if (name.equals(WizardPanel.ACTION_New))
			return true;
		return false;
	}

	@Override
	public void onHide(String action)
	{
		if (wizardPanel.getPanelAction().equals(WizardPanel.ACTION_New))
		{
			// get billing page and set processed on false, because we will have new order
			((WB2BOrderBillingWizardPage)wizardPanel.getPage(1)).setProcessed(false);
		}
	}

	@Override
	public void onShow()
	{
		panel.loadOrderTab();
		panel.orderTab.query(false);
		panel.selectOrder(order_id);
		panel.queryOrder(order_id);
		panel.setInfoOrder(order_id);
		panel.orderTab.navigate(order_id);
		panel.orderPanel.scrollToCurrentRow();
	}

	@Override
	public void setWizardPanel(WizardPanel panel)
	{
		this.wizardPanel = panel;
	}

	@Override
	public boolean isInitialized()
	{
		return isInitialized;
	}

	public void setC_Order_ID(int C_Order_ID)
	{
		this.order_id = C_Order_ID;
	}

}
