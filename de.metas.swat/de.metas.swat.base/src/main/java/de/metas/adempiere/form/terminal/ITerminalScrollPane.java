package de.metas.adempiere.form.terminal;

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


/**
 * Wraps a given {@link IComponent}, called here "Viewport" and adds horizontal and vertical scrolls to it.
 * 
 * @author tsa
 * 
 */
public interface ITerminalScrollPane extends IComponent
{
	enum ScrollPolicy
	{
		/**
		 * Always display the scroll bar
		 */
		ALWAYS,
		/**
		 * Never display the scroll bar
		 */
		NEVER,
		/**
		 * Let the system detect when scroll bar is needed and let it display only in that case
		 */
		WHEN_NEEDED,
	};

	/**
	 * set unit increment for vertical scroll bar
	 * 
	 * @param unit
	 */
	public void setUnitIncrementVSB(int unit);

	/**
	 * Determines when the horizontal scrollbar appears in the scroll pane
	 * 
	 * @param policy
	 */
	void setHorizontalScrollBarPolicy(ScrollPolicy policy);

	/**
	 * Determines when the vertical scrollbar appears in the scroll pane
	 * 
	 * @param policy
	 */
	void setVerticalScrollBarPolicy(ScrollPolicy policy);

	/**
	 * Gets viewport component (i.e. the component which is embedded this this scroll pane)
	 * 
	 * @return viewport component (or null)
	 */
	IComponent getViewport();

	/**
	 * Sets viewport component (i.e. the component which is embedded this this scroll pane)
	 * 
	 * @param view
	 */
	void setViewport(IComponent view);

	/**
	 * Show/Hide scroll pane borders
	 * 
	 * @param borderEnabled
	 */
	void setBorderEnabled(boolean borderEnabled);

	/**
	 * 
	 * @return true if scroll panel border is displayed
	 */
	boolean isBorderEnabled();

	public abstract boolean isVerticalStrechView();

	/**
	 * 
	 * @param verticalStrechView if true stretch view vertically if is smaller then the scroll pane component
	 */
	public abstract void setVerticalStrechView(final boolean verticalStrechView);

	public abstract boolean isHorizontalStrechView();

	/**
	 * 
	 * @param horizontalStrechView if true stretch view horizontally if is smaller then the scroll pane component
	 */
	public abstract void setHorizontalStrechView(final boolean horizontalStrechView);
}
