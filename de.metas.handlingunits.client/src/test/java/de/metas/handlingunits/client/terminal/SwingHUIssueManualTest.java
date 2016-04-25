/**
 *
 */
package de.metas.handlingunits.client.terminal;

/*
 * #%L
 * de.metas.handlingunits.client
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


import javax.swing.JFrame;

import org.junit.Ignore;

import de.metas.handlingunits.HUIssueTestHelper;
import de.metas.handlingunits.client.terminal.pporder.view.HUIssueFrame;

/**
 * @author cg
 *
 */
@Ignore
public class SwingHUIssueManualTest
{
	public static final void main(final String[] args)
	{
		final SwingHUIssueManualTest test = new SwingHUIssueManualTest();
		test.showWindow();
	}

	public SwingHUIssueManualTest()
	{
		super();

		final HUIssueTestHelper docHelper = new HUIssueTestHelper();
		docHelper.init();
	}

	public void showWindow()
	{
		//
		// HU select form
		final JFrame frame = new JFrame("HU Issue");

		// decorate our frame
		new HUIssueFrame(frame, 0);

		//
		// Frame
		{
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}
	}
}
