package de.metas.ui.web.vaadin.window.prototype.order.view;

import java.util.HashMap;
import java.util.Map;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;

/*
 * #%L
 * de.metas.ui.web.vaadin
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Record performance indicators
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
public class WindowRecordIndicators extends CustomComponent
{
	private final String STYLE = WindowViewImpl.STYLE + "-indicators";
	private final String STYLE_Indicator = WindowViewImpl.STYLE + "-indicator";

	private int nextIndicatorId = 1;
	private final CssLayout content;
	private final Map<String, String> indicatorId2color = new HashMap<>();

	public WindowRecordIndicators()
	{
		super();
		addStyleName(STYLE);

		this.content = new CssLayout()
		{
			@Override
			protected String getCss(Component c)
			{
				return getIndicatorCss(c);
			}
		};
		setCompositionRoot(content);
		content.addStyleName(STYLE + "-container");

		load();
	}

	private void load()
	{
		final Component[] indicatorComponents = new Component[] {
				createIndicatorComp("confirmed", "#e61919")//
				, createIndicatorComp("allocated", "#fcb605")//
				, createIndicatorComp("picked", "#f2eb0c") //
				, createIndicatorComp("shipped", "#94eb31") //
				, createIndicatorComp("invoiced", "#74b727") //
		};

		content.addComponents(indicatorComponents);
	}

	private Component createIndicatorComp(final String label, final String color)
	{
		final String indicatorId = STYLE_Indicator + "-" + (nextIndicatorId++);

		final Label comp = new Label();
		comp.setId(indicatorId);
		comp.setPrimaryStyleName(STYLE_Indicator);
		comp.setValue(label);

		indicatorId2color.put(indicatorId, color);

		return comp;
	}

	private String getIndicatorCss(final Component indicatorComp)
	{
		final String indicatorId = indicatorComp.getId();

		final StringBuilder css = new StringBuilder();

		final String color = indicatorId2color.get(indicatorId);
		if (color != null)
		{
			css.append("background-color: " + color + ";");
		}

		//
		if(css.length() == 0)
		{
			return null;
		}
		return css.toString();
	}
}
