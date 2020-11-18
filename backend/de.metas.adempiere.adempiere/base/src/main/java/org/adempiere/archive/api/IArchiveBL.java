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
import java.util.Optional;

import lombok.NonNull;
import org.adempiere.ad.persistence.ModelDynAttributeAccessor;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.print.layout.LayoutEngine;

import de.metas.process.ProcessInfo;
import de.metas.util.ISingletonService;

/**
 * Archive related business logic
 *
 * @author tsa
 *
 */
public interface IArchiveBL extends ISingletonService
{
	/**
	 * Allow to store the required number of copies per archive. Storing it inside the AD_Archive record (i.e. DB) makes no sense, because one AD_Archive can be printed multiple times.
	 * The value that is set here will be used in the respective printing queue item
	 *
	 * Task https://github.com/metasfresh/metasfresh/issues/1240
	 */
	ModelDynAttributeAccessor<I_AD_Archive, Integer> COPIES_PER_ARCHIVE = new ModelDynAttributeAccessor<>(Integer.class);
	/**
	 * Archives given <code>data</code>.
	 *
	 * @param force if true, the document will be archived anyway (even if auto-archive is not activated)
	 */
	I_AD_Archive archive(byte[] data, ArchiveInfo archiveInfo, boolean force, String trxName);

	/**
	 * Like {@link #archive(LayoutEngine, ArchiveInfo, boolean, String)}, but allows to only create the <code>AD_Archive</code> without saving the record.
	 *
	 * Task http://dewiki908/mediawiki/index.php/09752_For_Umsatzreport_and_Mengenstatistiken%2C_two_printing_queue..._%28107420055849%29
	 */
	I_AD_Archive archive(byte[] data, ArchiveInfo archiveInfo, boolean force, boolean save, String trxName);

	/**
	 * Converts to PDF and archives given <code>layout</code>.
	 *
	 * @param force if true, the document will be archived anyway (even if auto-archive is not activated)
	 */
	@Deprecated
	I_AD_Archive archive(LayoutEngine layout, ArchiveInfo archiveInfo, boolean force, String trxName);

	/**
	 * Do we need to Auto-Archive ?
	 *
	 * @return true if we need to auto-archive
	 */
	boolean isToArchive(ArchiveInfo archiveInfo);

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

	Optional<I_AD_Archive> getLastArchive(@NonNull TableRecordReference reference);

	Optional<byte[]> getLastArchiveBinaryData(@NonNull TableRecordReference reference);
}
