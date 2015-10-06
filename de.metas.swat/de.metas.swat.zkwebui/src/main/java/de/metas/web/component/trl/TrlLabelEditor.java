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
import java.util.Properties;

import org.adempiere.webui.component.ConfirmPanel;
import org.compiere.util.Env;
import org.zkoss.zk.ui.api.HtmlBasedComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zkex.zul.Center;
import org.zkoss.zkex.zul.South;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class TrlLabelEditor extends Window
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4793184514105928665L;

	public static void bindEditor(final TranslatableLabelSupport labelSupport)
	{
		if (!TranslatableLabelSupport.isEnabled())
		{
			return;
		}

		final ITranslatableLabel label = labelSupport.getTranslatableLabel();
		if (label instanceof HtmlBasedComponent)
		{
			final HtmlBasedComponent hc = (HtmlBasedComponent)label;
			hc.setDraggable("true");
		}

		label.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener()
		{
			@Override
			public void onEvent(final Event event) throws Exception
			{
				if (!labelSupport.isTranslatable())
				{
					return;
				}
				final TrlLabelEditor editorWin = new TrlLabelEditor(labelSupport);
				editorWin.doModal();
			}
		});
	}

	private final TranslatableLabelSupport label;
	private final Properties ctx;
	private final List<TrlElement> elements;

	public TrlLabelEditor(final TranslatableLabelSupport label)
	{
		this.label = label;
		ctx = Env.getCtx();
		elements = TrlElementHelper.parseAndCreateTrlElements(ctx, label.getLabelOrig());
		if (elements.isEmpty())
		{
			detach();
			try
			{
				Messagebox.show("No translatable elements found for \"" + label.getLabelOrig() + "\"",
						"Warning",
						Messagebox.OK, Messagebox.EXCLAMATION);
			}
			catch (final InterruptedException e)
			{
				e.printStackTrace();
			}
			return;
		}
		setPage(this.label.getPage());
		initUI();
	}

	private void initUI()
	{
		String title = "Translate: " + label.getLabel();
		if (title.length() > 70)
		{
			title = title.substring(0, 70) + "...";
		}

		setTitle(title);
		setClosable(true);
		setBorder("normal");
		setSizable(true);

		final Borderlayout layout = new Borderlayout();
		layout.setStyle("width: 500px; height: 400px;");
		layout.setParent(this);

		{
			final Center center = new Center();
			center.setParent(layout);

			final TrlElementRowRenderer renderer = new TrlElementRowRenderer();
			final Grid grid = new Grid();
			grid.setVflex(true);
			grid.setStyle("padding: 0px; margin: 0px;");
			grid.setModel(new TrlElementGroupsModel(elements));
			grid.setRowRenderer(renderer);
			renderer.renderColumns(grid);
			grid.setParent(center);
		}
		{
			final South south = new South();
			south.setParent(layout);

			final ConfirmPanel confirmPanel = new ConfirmPanel(true);
			confirmPanel.setParent(south);
			confirmPanel.addActionListener(new EventListener()
			{
				@Override
				public void onEvent(final Event event) throws Exception
				{
					if (event.getTarget().getId().equals(ConfirmPanel.A_OK))
					{
						TrlLabelEditor.this.doClose(true);
					}
					else if (event.getTarget().getId().equals(ConfirmPanel.A_CANCEL))
					{
						TrlLabelEditor.this.doClose(false);
					}
				}
			});
		}
	}

	private void doClose(final boolean save)
	{
		if (save)
		{
			TrlElementHelper.saveTrlElements(ctx, elements);
		}
		onClose();
	}

	@Override
	public void onClose()
	{
		if (label != null)
		{
			label.updateLabel();
		}
		super.onClose();
	}
}
