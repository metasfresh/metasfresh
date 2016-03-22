package org.adempiere.webui.dashboard;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.panel.ADWindowPanel;
import org.adempiere.webui.session.SessionManager;
import org.adempiere.webui.util.IServerPushCallback;
import org.adempiere.webui.window.ADWindow;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;

/**
 * {@link IDPActivityItem} component
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03798_Dashboard:_Anzahl_Rechnungen_%282013011810000038%29
 */
public class DPActivityItemComponent implements IServerPushCallback
{
	private final IDPActivityItem activityItem;

	private final Button button;
	private final String label;
	
	private final transient Logger logger = LogManager.getLogger(getClass());

	// private Integer counter = null;
	/**
	 * Dynamic info which is displayed on button
	 */
	private String labelInfo = null;

	public DPActivityItemComponent(final IDPActivityItem activityItem)
	{
		this.activityItem = activityItem;
		Util.assume(activityItem != null, "activityItem not null");

		this.label = activityItem.isDynamicLabel() ? null : Msg.translate(Env.getCtx(), activityItem.getLabel());
		String description = Msg.translate(Env.getCtx(), activityItem.getDescription());

		button = new Button();
		button.setTooltiptext(description);
		button.setImage(activityItem.getImage());

		button.addEventListener(Events.ON_CLICK, new EventListener()
		{
			@Override
			public void onEvent(Event event) throws Exception
			{
				executeActivityItem(activityItem);
			}
		});

		updateUI();
	}

	public Component getComponent()
	{
		return button;
	}

	public boolean isAvailable()
	{
		return activityItem.isAvailable();
	}

	public void refresh()
	{
		try
		{
			if (!isAvailable())
			{
				labelInfo = null;
				return;
			}

			if (activityItem.isDynamicLabel())
			{
				labelInfo = activityItem.getLabel();
			}
			else
			{
				final int counter = retrieveCounter();
				labelInfo = counter >= 0 ? String.valueOf(counter) : null;
			}
		}
		catch (Exception e)
		{
			// 04112 : In case we have an error on a button label, only log, so the others are still displayed
			logger.warn(e.getLocalizedMessage(), e);
			labelInfo = null;
		}
	}

	@Override
	public void updateUI()
	{
		final boolean available = isAvailable();
		button.setDisabled(!available);
		button.setVisible(available);

		StringBuilder labelStr = new StringBuilder();

		if (label != null && !activityItem.isDynamicLabel())
		{
			labelStr.append(label);
		}

		if (labelInfo != null)
		{
			if (labelStr.length() > 0)
			{
				labelStr.append(" : ");
			}
			labelStr.append(labelInfo);
		}
		else
		{
			// 04112 : Button with no label. Set to invisible.
			button.setDisabled(true);
			button.setVisible(false);
		}

		button.setLabel(labelStr.toString());
	}

	private int retrieveCounter()
	{
		final Properties ctx = Env.getCtx();
		final String tableName = activityItem.getCounterTableName();
		final String whereClause = getCounterWhereClause(activityItem);
		int count = 0;
		try
		{
			count = new Query(ctx, tableName, whereClause, ITrx.TRXNAME_None)
					.setApplyAccessFilterRW(true)
					.count();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			count = -1;
		}
		return count;
	}

	private void executeActivityItem(final IDPActivityItem item)
	{
		final Properties ctx = Env.getCtx();

		final String tableName = item.getTableName();
		Util.assume(tableName != null, "tableName not null");

		int adWindowId = item.getAD_Window_ID();
		if (adWindowId <= 0)
		{
			adWindowId = MTable.get(ctx, tableName).getAD_Window_ID();
		}
		if (adWindowId <= 0)
		{
			throw new AdempiereException("@NotFound@ @AD_Window_ID@ (" + item + ")");
		}

		final String whereClause = getWhereClause(item);
		final MQuery query = new MQuery(tableName);
		query.addRestriction(whereClause);

		final ADWindow win = SessionManager.getAppDesktop().openWindow(adWindowId, query);
		if (win == null)
		{
			return;
		}

		//
		// Bind on window close event: and refresh current counters
		final ADWindowPanel winPanel = win.getADWindowPanel();
		winPanel.addEventListener(ADWindowPanel.EVENT_OnWindowClose, new EventListener()
		{

			@Override
			public void onEvent(Event event) throws Exception
			{
				refresh();
				updateUI();
			}
		});
	}

	public String getWhereClause(final IDPActivityItem item)
	{
		final StringBuilder whereClause = new StringBuilder();
		whereClause.append(item.getWhereClause());

		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());
		if (item.isFilterLoginOrgOnly() && adOrgId > 0)
		{
			if (whereClause.length() > 0)
			{
				whereClause.insert(0, "(").append(") AND ");
			}

			whereClause.append(item.getTableName()).append(".").append("AD_Org_ID=").append(adOrgId);
		}

		return whereClause.toString();
	}
	
	public String getCounterWhereClause(final IDPActivityItem item)
	{
		final StringBuilder whereClause = new StringBuilder();
		whereClause.append(item.getCounterWhereClause());

		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());
		if (item.isFilterLoginOrgOnly() && adOrgId > 0)
		{
			if (whereClause.length() > 0)
			{
				whereClause.insert(0, "(").append(") AND ");
			}

			whereClause.append(item.getCounterTableName()).append(".").append("AD_Org_ID=").append(adOrgId);
		}

		return whereClause.toString();
	}
}
