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
import org.adempiere.webui.session.SessionManager;

/**
 * @author Cristina Ghita, METAS.RO
 * 
 */
public class WB2BOrderHistory extends ADForm
{

	private static final long serialVersionUID = 4372493753450867163L;

	private int m_WindowNo;

	public WB2BOrderHistory()
	{
		super();
		this.setBorder("normal");
		m_WindowNo = SessionManager.getAppDesktop().registerWindow(this);
	}

	@Override
	protected void initForm()
	{
		WB2BOrderHistoryPanel ohp = new WB2BOrderHistoryPanel(m_WindowNo, true);
		appendChild(ohp);
		ohp.setWidth("100%");
		ohp.setHeight("100%");
	}

}
