/**
 * 
 */
package org.adempiere.webui.component;

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


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.adempiere.util.StringUtils;
import org.compiere.util.CLogger;
import org.compiere.util.Util;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.Timer;

/**
 * @author cg
 * 
 */
public abstract class AbstractAutoCompleter extends AutoComplete implements EventListener
{
	private static final long serialVersionUID = -377518697359312755L;

	private static final int PopupDelayMillis = 500;
	/** Minimum chars required to popup */
	public static final int DEFAULT_PopupMinimumChars = 2;

	public static final String ITEM_More = "...";

	public static final int DEFAULT_MaxItems = 7;
	protected int m_maxItems = AbstractAutoCompleter.DEFAULT_MaxItems;

	protected final CLogger log = CLogger.getCLogger(getClass());

	private final Timer timer = new Timer(AbstractAutoCompleter.PopupDelayMillis);
	protected String defaultStyle;

	private Object m_userObject = null;
	private String m_text = null;

	public AbstractAutoCompleter()
	{
		super();
		defaultStyle = getStyle();
		addEventListener(Events.ON_CHANGING, this);
		addEventListener(Events.ON_CHANGE, this);
	}

	public void setUserObject(final Object userObject)
	{
		m_userObject = userObject;
		if (m_userObject == null && !Util.isEmpty(getText(), true))
		{
			setStyle("background:red");
		}
		else
		{
			setStyle(defaultStyle);
		}
		//
		setTooltiptext(userObject == null ? "" : userObject.toString());
	}

	public Object getUserOject()
	{
		return m_userObject;
	}

	private void showPopupDelayed()
	{
		timer.setRepeats(false);
		timer.start();
	}

	@Override
	public void onChanging(final InputEvent evt)
	{
		showPopupDelayed();
		setSearchText(evt.getValue());
		updateListData();
		super.onChanging(evt);
	}

	public String getSearchText()
	{
		return m_text;
	}

	public void setSearchText(final String txt)
	{
		m_text = txt;
	}

	public Textbox getComponent()
	{
		return getComponent();
	}

	private String convertUserObjectForTextField(final Object userObject)
	{
		return userObject == null ? "" : userObject.toString();
	}

	protected boolean isMatching(final Object userObject, final String search)
	{
		if (userObject == null)
		{
			return false;
		}

		final String s1 = StringUtils
				.stripDiacritics(convertUserObjectForTextField(userObject));
		final String s2 = StringUtils.stripDiacritics(search);

		return s1.equalsIgnoreCase(s2);
	}

	abstract protected String getSelectSQL(String search, List<Object> params);

	abstract protected Object fetchUserObject(ResultSet rs) throws SQLException;

	abstract protected boolean updateListData();
}
