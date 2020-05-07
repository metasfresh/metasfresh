/**
 * 
 */
package de.metas.adempiere.gui;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Map;

import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.adempiere.util.Check;
import org.compiere.apps.AEnv;
import org.compiere.model.MQuery;

import de.metas.adempiere.util.ADHyperlink;
import de.metas.adempiere.util.ADHyperlinkBuilder;

/**
 * @author tsa
 *
 */
public class ADHyperlinkHandler implements HyperlinkListener
{
	private final ADHyperlinkBuilder helper = new ADHyperlinkBuilder();

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e)
	{
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
		{
			ADHyperlink link = helper.getADHyperlink(e.getURL().toString());
			handle(link);
		}
	}

	private void handle(ADHyperlink link)
	{
		if (link == null)
		{
			return;
		}
		else if (ADHyperlink.Action.ShowWindow == link.getAction())
		{
			handleShowWindow(link);
		}
	}

	private void handleShowWindow(ADHyperlink link)
	{
		Map<String, String> params = link.getParameters();
		int AD_Table_ID = Integer.valueOf(params.get("AD_Table_ID"));
		if (AD_Table_ID <= 0)
		{
			return;
		}

		int adWindowId = -1;
		String adWindowIdStr = params.get("AD_Window_ID");
		if (adWindowIdStr != null && !adWindowIdStr.isEmpty())
		{
			adWindowId = Integer.valueOf(adWindowIdStr);
		}

		String whereClause = params.get("WhereClause");

		MQuery query = new MQuery(AD_Table_ID);
		if (!Check.isEmpty(whereClause))
			query.addRestriction(whereClause);

		if (adWindowId > 0)
		{
			AEnv.zoom(query, adWindowId);
		}
		else
		{
			AEnv.zoom(query);
		}
	}
}
