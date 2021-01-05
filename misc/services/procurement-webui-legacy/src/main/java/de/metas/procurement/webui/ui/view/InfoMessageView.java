package de.metas.procurement.webui.ui.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.annotations.VisibleForTesting;
import com.google.gwt.thirdparty.guava.common.base.Optional;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.service.ISettingsService;

/*
 * #%L
 * de.metas.procurement.webui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Display an info message which is provided by {@link ISettingsService#getInfoMessage()}.
 * 
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@SuppressWarnings("serial")
public class InfoMessageView extends MFProcurementNavigationView
{
	private static final String STYLE = "info-message-view";

	@Autowired
	private I18N i18n;
	@Autowired
	private ISettingsService settingsService;

	private final Label label;

	private Optional<String> infoMessage;

	public InfoMessageView()
	{
		super();

		Application.autowire(this);

		addStyleName(STYLE);

		this.setCaption(i18n.getWithDefault("InfoMessageView.caption", "Info"));

		label = new Label();
		label.addStyleName("text");
		label.setContentMode(ContentMode.HTML);
		setContent(new CssLayout(label));
	}

	@Override
	protected void onBecomingVisible()
	{
		load();
	}

	public final void resetAndLoad()
	{
		infoMessage = null;
		load();
	}

	private final void load()
	{
		final String infoMessageText = getInfoMessageText();
		label.setValue(infoMessageText);
	}

	private String getInfoMessageText()
	{
		if (infoMessage == null)
		{
			String infoMessageText = settingsService.getInfoMessage();
			if (infoMessageText != null)
			{
				infoMessageText = replaceNewLineWithHtmlBreak(infoMessageText.trim());
			}

			infoMessage = Optional.fromNullable(infoMessageText);
		}

		return infoMessage.orNull();
	}

	@VisibleForTesting
	static String replaceNewLineWithHtmlBreak(final String text)
	{
		if (text == null || text.isEmpty())
		{
			return text;
		}

		return text
				.replace("\r\n", "\n")
				.replace("\n", "<br/>");
	}

	public boolean isDisplayable()
	{
		final String infoMessageText = getInfoMessageText();
		return infoMessageText != null && !infoMessageText.isEmpty();
	}
}
