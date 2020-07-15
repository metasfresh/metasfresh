/**
 *
 */
package de.metas.adempiere.form.terminal.swing;

/*
 * #%L
 * de.metas.swat.base
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


import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.Border;

import org.compiere.swing.CScrollPane;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.ScrollableSizeHint;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * Swing implementation of a Scroll Pane
 *
 * @author tsa
 *
 */
/* package */class SwingTerminalScrollPane
		implements ITerminalScrollPane, IComponentSwing
{
	private final CScrollPane scrollPaneSwing;

	/**
	 * Scroll panel border to be used in case border is displayed
	 */
	private final Border scrollBorder;

	private IComponent view;
	private Component viewSwing;

	/**
	 * True if scroll border is displayed.
	 */
	private boolean borderEnabled;

	private boolean horizontalStrechView = true;
	private boolean verticalStrechView = true;

	private boolean disposed = false;

	/**
	 * @param view
	 */
	public SwingTerminalScrollPane(final IComponent view)
	{
		//
		// Setup Swing Scroll Pane
		scrollPaneSwing = new CScrollPane();
		scrollPaneSwing.getVerticalScrollBar().setPreferredSize(new Dimension(SwingTerminalFactory.SCROLL_Size, 0));
		scrollPaneSwing.getHorizontalScrollBar().setPreferredSize(new Dimension(0, SwingTerminalFactory.SCROLL_Size));

		scrollPaneSwing.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);

		this.scrollBorder = scrollPaneSwing.getBorder();

		setBorderEnabled(true); // backward compatibility

		//
		// Set view component
		setViewport(view);

		getTerminalContext().addToDisposableComponents(this);
	}

	@Override
	public void setViewport(IComponent view)
	{
		final Component viewSwing;
		if (view == null)
		{
			viewSwing = null;
		}
		else
		{
			viewSwing = SwingTerminalFactory.getUI(view);
		}

		scrollPaneSwing.setViewportView(viewSwing);
		this.view = view;
		this.viewSwing = viewSwing;

		updateView();
	}

	@Override
	public IComponent getViewport()
	{
		return view;
	}

	@Override
	public void setBorderEnabled(boolean borderEnabled)
	{
		if (this.borderEnabled == borderEnabled)
		{
			return;
		}

		this.borderEnabled = borderEnabled;
		scrollPaneSwing.setBorder(borderEnabled ? scrollBorder : null);
	}

	@Override
	public boolean isBorderEnabled()
	{
		return this.borderEnabled;
	}

	@Override
	public void setHorizontalScrollBarPolicy(final ScrollPolicy policy)
	{
		scrollPaneSwing.setHorizontalScrollBarPolicy(toSwingHorizontalScrollPolicy(policy));
		updateView();
	}

	@Override
	public void setVerticalScrollBarPolicy(final ScrollPolicy policy)
	{
		scrollPaneSwing.setVerticalScrollBarPolicy(toSwingVerticalScrollPolicy(policy));
		updateView();
	}

	@Override
	public void setHorizontalStrechView(final boolean horizontalStrechView)
	{
		if (this.horizontalStrechView == horizontalStrechView)
		{
			return;
		}

		this.horizontalStrechView = horizontalStrechView;

		updateView();
	}

	@Override
	public boolean isHorizontalStrechView()
	{
		return horizontalStrechView;
	}

	@Override
	public void setVerticalStrechView(final boolean verticalStrechView)
	{
		if (this.verticalStrechView == verticalStrechView)
		{
			return;
		}

		this.verticalStrechView = verticalStrechView;

		updateView();
	}

	@Override
	public boolean isVerticalStrechView()
	{
		return verticalStrechView;
	}

	private final int toSwingHorizontalScrollPolicy(final ScrollPolicy policy)
	{
		if (policy == null)
		{
			return JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		}
		else if (policy == ScrollPolicy.ALWAYS)
		{
			return JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS;
		}
		else if (policy == ScrollPolicy.NEVER)
		{
			return JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
		}
		else if (policy == ScrollPolicy.WHEN_NEEDED)
		{
			return JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED;
		}
		else
		{
			throw new IllegalArgumentException("Unknown scroll policy: " + policy);
		}
	}

	private final int toSwingVerticalScrollPolicy(final ScrollPolicy policy)
	{
		if (policy == null)
		{
			return JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
		}
		else if (policy == ScrollPolicy.ALWAYS)
		{
			return JScrollPane.VERTICAL_SCROLLBAR_ALWAYS;
		}
		else if (policy == ScrollPolicy.NEVER)
		{
			return JScrollPane.VERTICAL_SCROLLBAR_NEVER;
		}
		else if (policy == ScrollPolicy.WHEN_NEEDED)
		{
			return JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED;
		}
		else
		{
			throw new IllegalArgumentException("Unknown scroll policy: " + policy);
		}
	}

	@Override
	public Component getComponent()
	{
		return this.scrollPaneSwing;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return view.getTerminalContext();
	}

	@Override
	public void setUnitIncrementVSB(int unit)
	{
		scrollPaneSwing.getVerticalScrollBar().setUnitIncrement(unit);
	}

	private void updateView()
	{
		if (viewSwing == null)
		{
			return;
		}

		if (viewSwing instanceof JXPanel)
		{
			final JXPanel jxPanel = (JXPanel)viewSwing;

			//
			// Horizontal: set ScrollableWidthHint
			final int horizontalScrollBarPolicy = scrollPaneSwing.getHorizontalScrollBarPolicy();
			final ScrollableSizeHint scrollableWidthHint;
			switch (horizontalScrollBarPolicy)
			{
				case JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS:
					scrollableWidthHint = horizontalStrechView ? ScrollableSizeHint.HORIZONTAL_STRETCH : ScrollableSizeHint.NONE;
					break;
				case JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED:
					scrollableWidthHint = horizontalStrechView ? ScrollableSizeHint.HORIZONTAL_STRETCH : ScrollableSizeHint.NONE;
					break;
				case JScrollPane.HORIZONTAL_SCROLLBAR_NEVER:
					scrollableWidthHint = horizontalStrechView ? ScrollableSizeHint.FIT : ScrollableSizeHint.NONE;
					break;
				default:
					throw new IllegalStateException("Unknown horizontalScrollBarPolicy: " + horizontalScrollBarPolicy);
			}
			jxPanel.setScrollableWidthHint(scrollableWidthHint);

			//
			// Vertical: set ScrollableHeightHint
			final int verticalScrollBarPolicy = scrollPaneSwing.getVerticalScrollBarPolicy();
			final ScrollableSizeHint scrollableHeightHint;
			switch (verticalScrollBarPolicy)
			{
				case JScrollPane.VERTICAL_SCROLLBAR_ALWAYS:
					scrollableHeightHint = verticalStrechView ? ScrollableSizeHint.VERTICAL_STRETCH : ScrollableSizeHint.NONE;
					break;
				case JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED:
					scrollableHeightHint = verticalStrechView ? ScrollableSizeHint.VERTICAL_STRETCH : ScrollableSizeHint.NONE;
					break;
				case JScrollPane.VERTICAL_SCROLLBAR_NEVER:
					scrollableHeightHint = verticalStrechView ? ScrollableSizeHint.FIT : ScrollableSizeHint.NONE;
					break;
				default:
					throw new IllegalStateException("Unknown verticalScrollBarPolicy: " + verticalScrollBarPolicy);
			}
			jxPanel.setScrollableHeightHint(scrollableHeightHint);
		}
	}

	@Override
	public void dispose()
	{
		setViewport(null);
		disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}

}
