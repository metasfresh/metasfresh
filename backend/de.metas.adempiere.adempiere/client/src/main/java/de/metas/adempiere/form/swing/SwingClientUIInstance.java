package de.metas.adempiere.form.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.apps.ADialog;
import org.compiere.apps.AEnv;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.compiere.util.Util;

import de.metas.adempiere.form.AbstractClientUIInstance;
import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.adempiere.form.IClientUIAsyncInvoker;
import de.metas.adempiere.form.IClientUIInvoker;

class SwingClientUIInstance extends AbstractClientUIInstance
{

	public SwingClientUIInstance()
	{
		super();
	}

	@Override
	public void info(final int WindowNo, final String AD_Message)
	{
		ADialog.info(WindowNo, null, AD_Message);
	}

	@Override
	public void info(final int WindowNo, final String AD_Message, final String message)
	{
		ADialog.info(WindowNo, null, AD_Message, message);
	}

	@Override
	public boolean ask(final int WindowNo, final String AD_Message)
	{
		return ask()
				.setParentWindowNo(WindowNo)
				.setAD_Message(AD_Message)
				.getAnswer();
	}

	@Override
	public boolean ask(final int WindowNo, final String AD_Message, final String message)
	{
		return ask()
				.setParentWindowNo(WindowNo)
				.setAD_Message(AD_Message)
				.setAdditionalMessage(message)
				.getAnswer();
	}

	@Override
	public IAskDialogBuilder ask()
	{
		return new SwingAskDialogBuilder();
	}

	@Override
	public void warn(final int WindowNo, final String AD_Message)
	{
		ADialog.warn(WindowNo, null, AD_Message);
	}

	@Override
	public void warn(final int WindowNo, final String AD_Message, final String message)
	{
		ADialog.warn(WindowNo, null, AD_Message, message);
	}

	@Override
	public void error(final int WIndowNo, final String AD_Message)
	{
		ADialog.error(WIndowNo, null, AD_Message);
	}

	@Override
	public void error(final int WIndowNo, final String AD_Message, final String message)
	{
		ADialog.error(WIndowNo, null, AD_Message, message);
	}

	@Override
	public void download(final byte[] data, final String contentType, final String filename)
	{
		// NOTE: actually this is file preview...

		final File fileToUse = suggestFileToSave(filename, contentType, true); // temporaryFile=true
		if (fileToUse == null)
		{
			return;
		}

		Util.writeBytes(fileToUse, data == null ? new byte[0] : data);

		Env.startBrowser(fileToUse.toURI().toString());
	}

	@Override
	public void downloadNow(final InputStream content, final String contentType, final String filename)
	{
		// NOTE: ask the user where to save and then save it

		final File fileToUse = suggestFileToSave(filename, contentType, false); // temporaryFile=false
		if (fileToUse == null)
		{
			return;
		}

		final byte[] data = content == null ? new byte[0] : Util.readBytes(content);
		Util.writeBytes(fileToUse, data);
	}

	private File suggestFileToSave(final String filename, final String contentType, final boolean temporaryFile)
	{
		final String filenameSuggestion;
		final String fileExtensionSuggestion;
		if (Check.isEmpty(filename, true))
		{
			String fileExt = null;
			if (!Check.isEmpty(contentType, true))
			{
				fileExt = MimeType.getExtensionByType(contentType);
			}

			if (Check.isEmpty(fileExt, true))
			{
				filenameSuggestion = "data";
			}
			else
			{
				filenameSuggestion = "data" + fileExt;
			}

			fileExtensionSuggestion = fileExt;
		}
		else
		{
			filenameSuggestion = filename.trim();
			final String mimeType;
			if (Check.isEmpty(contentType, true))
			{
				mimeType = MimeType.getMimeType(filenameSuggestion);
			}
			else
			{
				mimeType = contentType.trim();
			}
			fileExtensionSuggestion = MimeType.getExtensionByType(mimeType);
		}

		final File fileToUse;
		if (temporaryFile)
		{
			try
			{
				fileToUse = File.createTempFile(filenameSuggestion, fileExtensionSuggestion);
			}
			catch (final IOException e)
			{
				throw new AdempiereException("Cannot create temporary file for " + filenameSuggestion, e);
			}
		}
		else
		{
			final JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setSelectedFile(new File(filenameSuggestion));
			if (!Check.isEmpty(fileExtensionSuggestion, true))
			{
				final String fileExtensionSuggestionLC = fileExtensionSuggestion.toLowerCase();
				fc.addChoosableFileFilter(new FileFilter()
				{

					@Override
					public String getDescription()
					{
						return "*" + fileExtensionSuggestion;
					}

					@Override
					public boolean accept(final File f)
					{
						if (f.isDirectory())
						{
							return true;
						}
						return f.getName().toLowerCase().endsWith(fileExtensionSuggestionLC);
					}
				});
			}

			if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			{
				fileToUse = fc.getSelectedFile();
			}
			else
			{
				fileToUse = null;
			}
		}

		return fileToUse;
	}

	@Override
	public String getClientInfo()
	{
		final String javaVersion = System.getProperty("java.version");
		return new StringBuilder("Swing, java.version=").append(javaVersion).toString();
	}

	@Override
	public void showWindow(final Object model)
	{
		Check.assumeNotNull(model, "model not null");
		final int adTableId = InterfaceWrapperHelper.getModelTableId(model);
		final int recordId = InterfaceWrapperHelper.getId(model);

		AEnv.zoom(adTableId, recordId);
	}

	@Override
	public IClientUIInvoker invoke()
	{
		return new SwingClientUIInvoker(this);
	}
	
	@Override
	public IClientUIAsyncInvoker invokeAsync()
	{
		return new SwingClientUIAsyncInvoker();
	}

	@Override
	public void showURL(final String url)
	{
		try
		{
			final URI uri = new URI(url);
			Desktop.getDesktop().browse(uri);
		}
		catch (Exception e)
		{
			logger.warn("Failed opening " + url, e.getLocalizedMessage());
		}
	}
}
