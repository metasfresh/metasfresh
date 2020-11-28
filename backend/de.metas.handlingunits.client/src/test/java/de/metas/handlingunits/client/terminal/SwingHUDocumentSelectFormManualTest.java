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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.junit.Ignore;

import de.metas.handlingunits.HUDocumentSelectTestHelper;
import de.metas.handlingunits.client.terminal.receipt.view.ReceiptScheduleHUSelectFrame;

@Ignore
public class SwingHUDocumentSelectFormManualTest
{
	public static final void main(final String[] args)
	{
		final SwingHUDocumentSelectFormManualTest test = new SwingHUDocumentSelectFormManualTest();
		// test.showHUelectWindow();
		test.showLauncherWindow();
	}

	public SwingHUDocumentSelectFormManualTest()
	{
		super();

		final HUDocumentSelectTestHelper docHelper = new HUDocumentSelectTestHelper();
		docHelper.init();
	}

	public void showLauncherWindow()
	{
		final JFrame frame = new JFrame("Launcher");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JButton button = new JButton("Open window");
		frame.getContentPane().add(button);

		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(final ActionEvent e)
			{
				final JFrame huSelectFrame = showHUelectWindow();
				huSelectFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			}
		});

		frame.pack();
		frame.setVisible(true);
	}

	public JFrame showHUelectWindow()
	{
		//
		// HU select form
		final JFrame frame = new JFrame("HU Select");

		// decorate our frame
		new ReceiptScheduleHUSelectFrame(frame, 0);

		//
		// Frame
		{
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
		}

		return frame;
	}
}
