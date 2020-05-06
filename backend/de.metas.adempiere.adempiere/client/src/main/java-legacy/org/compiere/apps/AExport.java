/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2008 SC ARHIPAC SERVICE SRL. All Rights Reserved.            *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.apps;

import java.awt.Cursor;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JFileChooser;

import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.ExtensionFileFilter;
import org.slf4j.Logger;

import com.google.common.io.Files;

import de.metas.impexp.excel.ExcelFormats;
import de.metas.impexp.excel.GridTabExcelExporter;
import de.metas.logging.LogManager;
import lombok.NonNull;

/**
 * Export button consequences
 * @author Teo Sarca, www.arhipac.ro
 *  			<li>FR [ 1943731 ] Window data export functionality
 */
public class AExport
{
	private static final Logger log = LogManager.getLogger(AExport.class);	
	private int m_WindowNo = 0;
	
	public AExport(final APanel parent)
	{
		m_WindowNo = parent.getWindowNo();
		//
		final JFileChooser chooser = new JFileChooser();
		final Set<String> fileExtensions = ExcelFormats.getFileExtensionsDefaultFirst();
		for(String fileExtension : fileExtensions)
		{
			final String formatName = ExcelFormats.getFormatByFileExtension(fileExtension).getName();
			final ExtensionFileFilter filter = new ExtensionFileFilter(fileExtension, fileExtension + " - " + formatName);
			chooser.addChoosableFileFilter(filter);
		}
		
		if (chooser.showSaveDialog(parent) != JFileChooser.APPROVE_OPTION)
		{
			return;
		}

		//	Create File
		File outFile = ExtensionFileFilter.getFile(chooser.getSelectedFile(), chooser.getFileFilter());
		try
		{
			outFile.createNewFile();
		}
		catch (IOException e)
		{
			log.error("", e);
			ADialog.error(m_WindowNo, parent, "FileCannotCreate", e.getLocalizedMessage());
			return;
		}

		final String fileExtension = Files.getFileExtension(outFile.getPath());

		parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		try
		{
			if(fileExtensions.contains(fileExtension))
			{
				createXLS(outFile, parent.getCurrentTab());
			}
			else
			{
				ADialog.error(m_WindowNo, parent, "FileInvalidExtension");
			}
		}
		catch (Exception e)
		{
			ADialog.error(m_WindowNo, parent, "Error", e.getLocalizedMessage());
			if (LogManager.isLevelFinest())
				e.printStackTrace();
		}
		finally
		{
			parent.setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private void createXLS(@NonNull final File outFile, @NonNull final GridTab tab)
	{
		GridTabExcelExporter.builder()
				.excelFormat(ExcelFormats.getFormatByFile(outFile))
				.tab(tab)
				.build()
				.exportToFile(outFile);

		Env.startBrowser(outFile);
	}
}
