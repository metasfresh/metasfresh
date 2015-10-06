/**
 * 
 */
package de.metas.adempiere.webui.component;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.webui.window.FDialog;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;

/**
 * @author tsa
 * 
 */
public class WizardPanel extends Div
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6517423360806410380L;

	private final CLogger logger = CLogger.getCLogger(getClass());

	public static final String ACTION_Cancel = "Cancel";
	public static final String ACTION_Back = "back";
	public static final String ACTION_Next = "Next";
	public static final String ACTION_Finish = "Finish";
	public static final String ACTION_New = "New";

	private final Div mainPanel = new Div();
	private final Hbox buttonPanel = new Hbox();

	private final Map<String, Button> buttonsMap = new HashMap<String, Button>();
	private final List<WizardPage> pages = new ArrayList<WizardPage>();
	private int currentPageIndex = -1;
	private String action = null;

	public WizardPanel()
	{
		init();
	}

	public void setPanelAction(final String action)
	{
		this.action = action;
	}

	public String getPanelAction()
	{
		return action;
	}

	public void addPage(final WizardPage page)
	{
		pages.add(page);
		page.setWizardPanel(this);

		if (currentPageIndex < 0)
		{
			setCurrentPage(0);
		}
		else
		{
			updateButtonStatus();
		}
	}

	public WizardPage getPage(final int index)
	{
		return pages.get(index);
	}

	public WizardPage getCurrentPage()
	{
		if (currentPageIndex < 0)
		{
			return null;
		}
		return pages.get(currentPageIndex);
	}

	public int getCurrentPageIndex()
	{
		return currentPageIndex;
	}

	public int getPageIndex(final WizardPage page)
	{
		for (int i = 0; i < pages.size(); i++)
		{
			if (page == pages.get(i))
			{
				return i;
			}
		}
		return -1;
	}

	private void init()
	{
		setWidth("100%");
		setHeight("100%");

		final Borderlayout layout = new Borderlayout();
		appendChild(layout);

		final Center center = new Center();
		layout.appendChild(center);
		center.setFlex(true);
		mainPanel.setWidth("100%");
		mainPanel.setHeight("100%");
		center.appendChild(mainPanel);

		final South south = new South();
		south.setHeight("35px");
		layout.appendChild(south);
		south.appendChild(buttonPanel);

		createButton(WizardPanel.ACTION_Cancel);
		createButton(WizardPanel.ACTION_Back);
		createButton(WizardPanel.ACTION_Next);
		createButton(WizardPanel.ACTION_Finish);
		createButton(WizardPanel.ACTION_New);
	}

	private Button createButton(final String name)
	{
		return createButton(name, null);
	}

	public Button createButton(final String name, final EventListener listener)
	{
		if (buttonsMap.containsKey(name))
		{
			throw new AdempiereException("Action button " + name + " already exist");
		}
		final Button btn = new Button();
		btn.setLabel(Msg.translate(Env.getCtx(), name));
		btn.setWidth("70px");
		btn.setHeight("30px");
		if (listener == null)
		{
			btn.addEventListener(Events.ON_CLICK, new EventListener()
			{
				@Override
				public void onEvent(final Event arg0) throws Exception
				{
					try
					{
						actionButton(name);
					}
					catch (final Exception e)
					{
						FDialog.error(0, WizardPanel.this, "Error", e.getLocalizedMessage());
						logger.log(Level.INFO, e.getLocalizedMessage(), e);
					}
				}
			});
		}
		else
		{
			btn.addEventListener(Events.ON_CLICK, listener);
		}
		buttonPanel.appendChild(btn);
		buttonsMap.put(name, btn);
		return btn;
	}

	private void actionButton(final String name)
	{
		if (!isAllowAction(name))
		{
			return;
		}

		if (WizardPanel.ACTION_Cancel.equals(name))
		{
			actionCancel();
		}
		else if (WizardPanel.ACTION_Back.equals(name))
		{
			actionPrev();
		}
		else if (WizardPanel.ACTION_Next.equals(name))
		{
			actionNext();
		}
		else if (WizardPanel.ACTION_Finish.equals(name))
		{
			actionFinish();
		}
		else if (WizardPanel.ACTION_New.equals(name))
		{
			actionNew();
		}
	}

	public void actionNext()
	{
		setCurrentPage(currentPageIndex + 1);
	}

	private void actionPrev()
	{
		setCurrentPage(currentPageIndex - 1);
	}

	public void setCurrentPage(final int index)
	{
		final WizardPage oldPage = getCurrentPage();
		WizardPage newPage = null;
		boolean ok = false;
		try
		{
			if (oldPage != null)
			{
				oldPage.onHide(getAction());
			}

			newPage = getPage(index);
			if (!newPage.isInitialized())
			{
				newPage.init();
			}
			newPage.onShow();

			ok = true;
		}
		finally
		{
			if (ok)
			{
				if (oldPage != null)
				{
					oldPage.getComponent().setVisible(false);
					mainPanel.removeChild(oldPage.getComponent());
				}
				if (newPage != null)
				{
					final Component newComp = newPage.getComponent();
					if (newComp instanceof HtmlBasedComponent)
					{
						final HtmlBasedComponent hbc = (HtmlBasedComponent)newComp;
						hbc.setWidth("100%");
						hbc.setHeight("100%");
					}
					newComp.setVisible(true);
					mainPanel.appendChild(newComp);
				}
				currentPageIndex = index;
			}
			updateButtonStatus();
		}
	}

	private void actionFinish()
	{
		final WizardPage page = getCurrentPage();
		if (page != null)
		{
			page.onHide(WizardPanel.ACTION_Finish);
		}
	}

	private void actionNew()
	{
		setPanelAction(WizardPanel.ACTION_New);
		setCurrentPage(currentPageIndex - 2);
	}

	private void actionCancel()
	{
		final WizardPage page = getCurrentPage();
		if (page != null)
		{
			page.onHide(WizardPanel.ACTION_Cancel);
		}
	}

	public void updateButtonStatus()
	{
		for (final Entry<String, Button> e : buttonsMap.entrySet())
		{
			final String name = e.getKey();
			final Button button = e.getValue();
			final boolean enabled = isAllowAction(name, button);
			button.setDisabled(!enabled);
		}

	}

	private boolean isAllowAction(final String name)
	{
		final Button button = buttonsMap.get(name);
		return isAllowAction(name, button);
	}

	private boolean isAllowAction(final String name, final Button button)
	{
		final WizardPage currentPage = getCurrentPage();
		final int currentPageIndex = getCurrentPageIndex();

		if (currentPage == null)
		{
			return false;
		}

		if (currentPageIndex == 0 && WizardPanel.ACTION_Back.equals(name))
		{
			return false;
		}

		if (currentPageIndex == pages.size() - 1 && WizardPanel.ACTION_Next.equals(name))
		{
			return false;
		}

		return currentPage.isAllowAction(name);
	}
}
