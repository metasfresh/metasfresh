package org.adempiere.archive.api;

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

import java.io.InputStream;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.PrintInfo;
import org.compiere.print.layout.LayoutEngine;

import de.metas.process.ProcessInfo;

/**
 * Archive related business logic
 * 
 * @author tsa
 * 
 */
public interface IArchiveBL extends ISingletonService
{
	/**
	 * Archives the given binary data. Data is archived only if auto-archive option is enabled (see {@link #isToArchive(PrintInfo)})
	 * 
	 * @param data is it assumed (but not checked) that this is the binary data of a PDF document.
	 * @param printInfo used to determine if the data will be archived at all.
	 * @return the AD_Archive_ID of the new entry if the data has been archived, -1 otherwise.
	 * @see #archive(byte[], PrintInfo, boolean, String)
	 */
	int archive(byte[] data, PrintInfo printInfo);

	/**
	 * 
	 * @param data
	 * @param printInfo
	 * @param force
	 * @return archive or null
	 * @see #archive(byte[], PrintInfo, boolean, String)
	 */
	I_AD_Archive archive(byte[] data, PrintInfo printInfo, boolean force);

	/**
	 * Archives given <code>data</code>.
	 * 
	 * @param data
	 * @param printInfo
	 * @param force if true, the document will be archived anyway (even if auto-archive is not activated)
	 * @param trxName
	 * @return
	 */
	I_AD_Archive archive(byte[] data, PrintInfo printInfo, boolean force, String trxName);

	/**
	 * Like {@link #archive(LayoutEngine, PrintInfo, boolean, String)}, but allows to only create the <code>AD_Archive</code> without saving the record.
	 * 
	 * @param data
	 * @param printInfo
	 * @param force
	 * @param save
	 * @param trxName
	 * @return
	 * @task http://dewiki908/mediawiki/index.php/09752_For_Umsatzreport_and_Mengenstatistiken%2C_two_printing_queue..._%28107420055849%29
	 */
	I_AD_Archive archive(byte[] data, PrintInfo printInfo, boolean force, boolean save, String trxName);

	/**
	 * Converts to PDF and archives given <code>layout</code>.
	 * 
	 * @param layout
	 * @param printInfo
	 * @param force if true, the document will be archived anyway (even if auto-archive is not activated)
	 * @param trxName
	 * @return
	 */
	I_AD_Archive archive(LayoutEngine layout, PrintInfo printInfo, boolean force, String trxName);

	/**
	 * Do we need to Auto-Archive ?
	 * 
	 * @return true if we need to auto-archive
	 */
	boolean isToArchive(PrintInfo printInfo);

	/**
	 * Do we need to Auto-Archive ?
	 * 
	 * @return true if we need to auto-archive
	 */
	boolean isToArchive(ProcessInfo processInfo);

	String getContentType(I_AD_Archive archive);

	byte[] getBinaryData(I_AD_Archive archive);

	InputStream getBinaryDataAsStream(I_AD_Archive archive);

	void setBinaryData(I_AD_Archive archive, byte[] data);
}
