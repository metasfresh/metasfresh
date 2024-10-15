package de.metas.migration.applier.impl;

/*
 * #%L
 * de.metas.migration.base
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

import de.metas.migration.IScript;
import de.metas.migration.applier.IScriptsApplierListener;
import de.metas.migration.exception.ScriptExecutionException;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.File;

public class SwingUIScriptsApplierListener implements IScriptsApplierListener
{
	private static final Logger logger = LoggerFactory.getLogger(SwingUIScriptsApplierListener.class);

	public static final HyperlinkListener DEFAULT_HyperlinkListener = new HyperlinkListener()
	{
		@Override
		public void hyperlinkUpdate(final HyperlinkEvent e)
		{
			if (e.getEventType() != HyperlinkEvent.EventType.ACTIVATED)
			{
				return;
			}
			try
			{
				Desktop.getDesktop().browse(e.getURL().toURI());
			}
			catch (final Exception ex)
			{
				logger.warn(ex.getLocalizedMessage(), ex);
			}
		}
	};

	@Override
	public void onScriptApplied(final IScript script)
	{
		// nothing
	}

	@Override
	public ScriptFailedResolution onScriptFailed(final IScript script, final ScriptExecutionException e)
	{
		final File file = script.getLocalFile();
		final String exceptionMessage = e.toStringX(false); // printStackTrace=false

		final String message =
				"<html><body>"
						+ "Script failed to run. Shall we add it to ignore list?<br/><br/>"
						+ "<pre>"
						+ StringEscapeUtils.escapeHtml3(exceptionMessage)
						+ "</pre>"
						+ "<br/>"
						+ "Script File: <a href=\"" + file.toURI() + "\">" + StringEscapeUtils.escapeHtml3(file.toString()) + "</a><br/>"
						+ "</body></html>";

		final ScriptFailedResolution response = uiAsk("Add script to ignore list?",
				message,
				ScriptFailedResolution.values(),
				ScriptFailedResolution.Fail);

		if (response == ScriptFailedResolution.Fail)
		{
			throw e;
		}

		return response;
	}

	public static <T> T uiAsk(final String title, final String message, final T[] options, final T defaultOption)
	{
		final Object messageObj;
		if (message != null && message.startsWith("<html>"))
		{
			final JTextPane tp = new JTextPane();
			tp.setContentType("text/html");
			tp.setText(message);
			tp.setEditable(false);
			tp.setAutoscrolls(true);
			tp.addHyperlinkListener(DEFAULT_HyperlinkListener);

			final JScrollPane jsp = new JScrollPane(tp);
			jsp.setPreferredSize(new Dimension(800, 500));
			messageObj = jsp;
		}
		else
		{
			messageObj = message;
		}

		// Create the frame in which we will display the Option Dialog
		// NOTE: we are doing this because we want to have this window in taskbar (and in Windows, Dialogs are not shown in taskbar)
		final JFrame frame = new JFrame(title);
		frame.setUndecorated(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setVisible(true); // make it visible, if we are not doing this, we will have no icon if task bar

		final int responseIdx;
		try
		{
			responseIdx = JOptionPane.showOptionDialog(
					frame, // parentComponent
					messageObj, // message
					title,
					JOptionPane.DEFAULT_OPTION, // optionType,
					JOptionPane.WARNING_MESSAGE, // messageType,
					null, // icon,
					options, // options
					defaultOption // initialValue
					);
		}
		finally
		{
			// Make sure we are disposing the frame (note: it is not disposed by default)
			frame.dispose();
		}

		if (responseIdx < 0)
		{
			// user closed the popup => defaultOption shall be returned
			return defaultOption;
		}

		final T response = options[responseIdx];
		return response;
	}

}
