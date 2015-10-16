package org.adempiere.plaf;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollPaneUI;

import org.adempiere.plaf.AdempierePLAF;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class AdempiereScrollPaneUI extends MetalScrollPaneUI
{
	/** the UI Class ID to bind this UI to */
	public static final String uiClassID = AdempierePLAF.getUIClassID(JScrollPane.class, "ScrollPaneUI");

	public static ComponentUI createUI(final JComponent b)
	{
		return new AdempiereScrollPaneUI();
	}

	public static final Object[] getUIDefaults()
	{
		return new Object[] {
				uiClassID, AdempiereScrollPaneUI.class.getName()
		};
	}

	@Override
	protected void installDefaults(final JScrollPane scrollpane)
	{
		super.installDefaults(scrollpane);

		// increase mouse-wheel scroll speed:
		scrollpane.getHorizontalScrollBar().setUnitIncrement(16);
		scrollpane.getVerticalScrollBar().setUnitIncrement(16);
	}

	@Override
	protected MouseWheelListener createMouseWheelListener()
	{
		return new ScrollPaneMouseWheelListener(super.createMouseWheelListener());
	}

	/**
	 * A {@link MouseWheelListener} which forward the event to parent scroll pane if any, in case the event is not eligible for this scroll pane.
	 *
	 * Basically an event is considered NOT eligible, when the scroll bars are not displayed.
	 *
	 * @author metas-dev <dev@metas-fresh.com>
	 *
	 */
	private class ScrollPaneMouseWheelListener implements MouseWheelListener
	{
		private final MouseWheelListener delegate;

		private ScrollPaneMouseWheelListener(final MouseWheelListener delegate)
		{
			super();
			this.delegate = delegate;
		}

		@Override
		public void mouseWheelMoved(final MouseWheelEvent e)
		{
			// If event is not eligible, try to forward it to parent scroll pane
			if (!isEligibleForWheelScrolling(e))
			{
				if (forwardEventToParentScrollPane(e))
				{
					// event was forwarded, stop here
					return;
				}
			}

			delegate.mouseWheelMoved(e);
		}

		private boolean isEligibleForWheelScrolling(final MouseWheelEvent e)
		{
			if (!scrollpane.getHorizontalScrollBar().isVisible()
					&& !scrollpane.getVerticalScrollBar().isVisible())
			{
				return false;
			}

			return true;
		}

		/**
		 * Forward given event to parent scroll pane if any
		 *
		 * @return true if event was forwarded
		 */
		private final boolean forwardEventToParentScrollPane(final MouseWheelEvent e)
		{
			final JScrollPane parent = getParentScrollPane(scrollpane);
			if (parent == null)
			{
				return false;
			}

			parent.dispatchEvent(cloneEvent(parent, e));
			return true;
		}

		/** @return The parent scroll pane, or null if there is no parent. */
		private JScrollPane getParentScrollPane(final JScrollPane scrollPane)
		{
			if (scrollPane == null)
			{
				return null;
			}

			Component parent = scrollPane.getParent();
			while (parent != null)
			{
				if (parent instanceof JScrollPane)
				{
					return (JScrollPane)parent;
				}
				parent = parent.getParent();
			}
			return null;
		}

		/** @return cloned event */
		private final MouseWheelEvent cloneEvent(final JScrollPane scrollPane, final MouseWheelEvent event)
		{
			return new MouseWheelEvent(scrollPane
					, event.getID()
					, event.getWhen()
					, event.getModifiers()
					, event.getX()
					, event.getY()
					, event.getClickCount()
					, event.isPopupTrigger()
					, event.getScrollType()
					, event.getScrollAmount()
					, event.getWheelRotation());
		}

	}

}
