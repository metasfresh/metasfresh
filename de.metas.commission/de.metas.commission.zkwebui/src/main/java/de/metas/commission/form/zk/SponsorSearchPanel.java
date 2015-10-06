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


import org.adempiere.util.Check;
import org.adempiere.webui.apps.AEnv;
import org.adempiere.webui.component.Label;
import org.adempiere.webui.component.Panel;
import org.adempiere.webui.component.Textbox;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;

/**
 * @author tsa
 *
 */
public class SponsorSearchPanel extends Panel implements EventListener
{
	private final WSponsorBrowse sponsorBrowser;
    private Label lblSearch;
    private Textbox fSearch;
	
	public SponsorSearchPanel(WSponsorBrowse sponsorBrowser)
	{
		this.sponsorBrowser = sponsorBrowser;
		init();
	}

    private void init()
    {
    	Div div = new Div();
    	
        lblSearch = new Label();
        lblSearch.setValue(Msg.getMsg(Env.getCtx(),"TreeSearch").replaceAll("&", "") + ":");
        lblSearch.setTooltiptext(Msg.getMsg(Env.getCtx(),"TreeSearchText"));
        div.appendChild(lblSearch);
        String divStyle = "height: 20px; vertical-align: middle;";
        if (!AEnv.isInternetExplorer())
        {
        	divStyle += "display: inline-block;";
        }
        div.setStyle(divStyle);

        fSearch = new Textbox();
        fSearch.setWidth("300px");
        fSearch.addEventListener(Events.ON_OK, this);

        this.appendChild(div);
        this.appendChild(fSearch);
    }

	@Override
	public void onEvent(Event event) throws Exception
	{
		String search = fSearch.getText();
		search(search);
	}
	
	
	public void search(String search)
	{
		if (Check.isEmpty(search, true))
		{
			return;
		}
		
		SponsorTableModel model = sponsorBrowser.getSponsorTableModel();
		int currentIndex = model.getIndex(sponsorBrowser.getSelectedSponsorTreeNode());
		if (currentIndex < 0)
			currentIndex = 0;
		SponsorTreeNode matchedNode = null;
		for (int i = currentIndex + 1; i < model.getSize(); i++)
		{
			SponsorTreeNode node = (SponsorTreeNode)model.getElementAt(i);
			if (match(node, search))
			{
				matchedNode = node;
				break;
			}
		}
		if (matchedNode == null)
		{
			for (int i = 0; i < currentIndex; i++)
			{
				SponsorTreeNode node = (SponsorTreeNode)model.getElementAt(i);
				if (match(node, search))
				{
					matchedNode = node;
					break;
				}
			}
		}
		//
		if (matchedNode != null)
		{
			sponsorBrowser.select(matchedNode);
		}
	}
	
	private boolean match(SponsorTreeNode node, String search)
	{
		final String searchUC = search.toUpperCase();
		String nodeStr = node.toString().toUpperCase();
		if (nodeStr.startsWith(searchUC))
			return true;

		String name = node.getBPName();
		name = name == null ? "" : name.toUpperCase();
		if (name.startsWith(searchUC))
			return true;
		
		return false;
	}

}
