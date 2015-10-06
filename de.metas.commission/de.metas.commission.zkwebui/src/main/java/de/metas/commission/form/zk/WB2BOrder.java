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


import org.adempiere.webui.panel.ADForm;
import org.adempiere.webui.panel.CustomForm;
import org.adempiere.webui.panel.IFormController;

import de.metas.adempiere.webui.component.WizardPanel;

public class WB2BOrder implements IFormController
{
	private final CustomForm form = new CustomForm();
	private final WizardPanel wizardPanel = new WizardPanel();
	private final WB2BOrderProductWizardPage productPanel;
	private final WB2BOrderBillingWizardPage billingPanel;
	private final WB2BOrderHistoryWizardPage orderHistoryPanel;

//	/** Logger. */
//	private static final CLogger log = CLogger.getCLogger(WB2BOrderProductWizardPage.class);

	public WB2BOrder()
	{
		productPanel = new WB2BOrderProductWizardPage(form.getWindowNo());
		billingPanel = new WB2BOrderBillingWizardPage(productPanel.getOrderTabpanel());
		orderHistoryPanel = new WB2BOrderHistoryWizardPage(form.getWindowNo());
		orderHistoryPanel.setC_Order_ID(productPanel.getC_Order().getC_Order_ID());

		initLayout();
	}

	private void initLayout()
	{
		form.appendChild(wizardPanel);
		wizardPanel.addPage(productPanel);
		wizardPanel.addPage(billingPanel);
		wizardPanel.addPage(orderHistoryPanel);
	}

	@Override
	public ADForm getForm()
	{
		return form;
	}
}
