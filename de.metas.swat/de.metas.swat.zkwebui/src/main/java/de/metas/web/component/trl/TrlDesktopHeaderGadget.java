package de.metas.web.component.trl;

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


import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Message;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.DropEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Div;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;

public class TrlDesktopHeaderGadget extends Div
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6514864886335282475L;

	public TrlDesktopHeaderGadget()
	{
		super();
		if (!TranslatableLabelSupport.isEnabled())
		{
			return;
		}

		init();
	}

	private void init()
	{
		setWidth("50px");
		setHeight("30px");
		setStyle("padding: 3px; border: 1px solid black;");
		setDroppable("true");
		setTooltiptext("Drop any item here to translate it. Double click to edit newly generated messages.");

		final Hbox box = new Hbox();
		box.setWidth("100%");
		box.setParent(this);

		final Image image = new Image();
		image.setSrc("/images/Multilingual.jpg");
		image.setWidth("30px");
		image.setHeight("30px");
		image.setParent(box);
		image.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener()
		{

			@Override
			public void onEvent(final Event event) throws Exception
			{
				editNewMessages();
			}
		});

		final Label lbl = new Label("Trl box");
		lbl.setParent(box);
		lbl.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener()
		{

			@Override
			public void onEvent(final Event event) throws Exception
			{
				editNewMessages();
			}
		});

		addEventListener(Events.ON_DROP, new EventListener()
		{

			@Override
			public void onEvent(final Event event) throws Exception
			{
				final DropEvent de = (DropEvent)event;
				onDrop(de);
			}
		});
	}

	private void editNewMessages()
	{
		final List<I_AD_Message> list = TranslatableLabelSupport.fetchNewMessages();
		if (list.isEmpty())
		{
			return;
		}

		final StringBuffer sb = new StringBuffer();
		for (final I_AD_Message m : list)
		{
			sb.append(" @").append(m.getValue()).append("@");
		}

		final TrlLabel dummyLabel = new TrlLabel();
		dummyLabel.setPage(getPage());
		dummyLabel.setLabel(sb.toString());
		editTranslation(dummyLabel);
	}

	private void onDrop(final DropEvent de)
	{
		final Component comp = de.getDragged();
		editTranslation(comp);
	}

	private void editTranslation(final Component comp)
	{
		try
		{
			final TranslatableLabelSupport tls = TranslatableLabelSupport.forComponent(comp);
			if (tls != null)
			{
				if (!tls.isTranslatable())
				{
					return;
				}
				final TrlLabelEditor editorWin = new TrlLabelEditor(tls);
				editorWin.doModal();
			}
			else
			{
				Messagebox.show("Component has no translation support",
						"Warning",
						Messagebox.OK, Messagebox.EXCLAMATION);

			}
		}
		catch (final Exception e)
		{
			throw e instanceof AdempiereException ? (AdempiereException)e : new AdempiereException(e);
		}
	}
}
