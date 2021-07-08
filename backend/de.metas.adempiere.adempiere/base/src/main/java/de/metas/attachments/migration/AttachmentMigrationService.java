package de.metas.attachments.migration;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_AttachmentEntry;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryCreateRequest;
import de.metas.attachments.AttachmentEntryFactory;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Service
public class AttachmentMigrationService
{
	private static final Logger logger = LogManager.getLogger(AttachmentMigrationService.class);

	private static final String SYSCONFIG_EXIST_RECORDS_TO_MIGRATE = "de.metas.attachments.migration.ExistRecordsToMigrate";

	private final AttachmentEntryFactory attachmentEntryFactory;

	public AttachmentMigrationService(@NonNull final AttachmentEntryFactory attachmentEntryFactory)
	{
		this.attachmentEntryFactory = attachmentEntryFactory;
	}

	public List<AttachmentEntry> migrateAndGetByReferencedRecord(@NonNull final Object referencedRecord)
	{
		final TableRecordReference tableRecordReference = TableRecordReference.of(referencedRecord);

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_AD_Attachment> attachmentRecords = queryBL
				.createQueryBuilder(I_AD_Attachment.class)
				// .addOnlyActiveRecordsFilter() attempt to also migrate inactive records
				.addEqualsFilter(I_AD_Attachment.COLUMN_Record_ID, tableRecordReference.getRecord_ID())
				.addEqualsFilter(I_AD_Attachment.COLUMN_AD_Table_ID, tableRecordReference.getAD_Table_ID())
				.addEqualsFilter(I_AD_Attachment.COLUMN_MigrationDate, null)
				.create()
				.list();

		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();
		for (final I_AD_Attachment attachmentRecord : attachmentRecords)
		{
			final boolean attachmentRecordIsActive = attachmentRecord.isActive();
			final List<AttachmentEntry> migratedEntries = convertAttachmentLOBToEntries(attachmentRecord);

			if (attachmentRecordIsActive)
			{
				result.addAll(migratedEntries);
			}
		}
		return result.build();
	}

	public List<AttachmentEntry> convertAttachmentLOBToEntries(@NonNull final I_AD_Attachment attachment)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		return trxManager.callInNewTrx(() -> {

			return convertAttachmentLOBToEntries0(attachment);
		});
	}

	private List<AttachmentEntry> convertAttachmentLOBToEntries0(@NonNull final I_AD_Attachment attachment)
	{
		final byte[] data = attachment.getBinaryData();

		attachment.setBinaryData(null);
		attachment.setMigrationDate(SystemTime.asTimestamp());
		saveRecord(attachment);

		if (data == null || data.length == 0)
		{
			// already converted
			return ImmutableList.of();
		}

		final ImmutableList.Builder<AttachmentEntry> result = ImmutableList.builder();
		final String INDEX_Filename = ".index";
		try
		{
			final ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(data));
			ZipEntry zipEntry = zip.getNextEntry();
			while (zipEntry != null)
			{
				final String name = zipEntry.getName();

				final ByteArrayOutputStream out = new ByteArrayOutputStream();
				final byte[] buffer = new byte[2048];
				int length = zip.read(buffer);
				while (length != -1)
				{
					out.write(buffer, 0, length);
					length = zip.read(buffer);
				}

				if (INDEX_Filename.equals(name))
				{
					continue; // nothing
				}

				final String fileName = new File(name).getName();

				final AttachmentEntryCreateRequest request = AttachmentEntryCreateRequest.fromByteArray(fileName, out.toByteArray());

				final AttachmentEntry entry = attachmentEntryFactory.createAndSaveEntry(request);
				result.add(entry);

				if (!attachment.isActive())
				{
					final I_AD_AttachmentEntry entryRecord = load(entry.getId(), I_AD_AttachmentEntry.class);
					entryRecord.setIsActive(false);
					saveRecord(entryRecord);
				}

				zipEntry = zip.getNextEntry();
			}

			return result.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Failed convering legacy LOB attachments to entries", ex);
		}
	}

	public boolean isExistRecordsToMigrateCheckDB()
	{
		final boolean existRecordsToMigrate = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Attachment.class)
				.addEqualsFilter(I_AD_Attachment.COLUMN_MigrationDate, null)
				.create()
				.anyMatch();

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		sysConfigBL.setValue(SYSCONFIG_EXIST_RECORDS_TO_MIGRATE, existRecordsToMigrate, ClientId.SYSTEM, OrgId.ANY);
		Loggables.withLogger(logger, Level.DEBUG).addLog("Setting SysConfig {} to {}", SYSCONFIG_EXIST_RECORDS_TO_MIGRATE, StringUtils.ofBoolean(existRecordsToMigrate));

		return existRecordsToMigrate;
	}

	public boolean isExistRecordsToMigrate()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		return sysConfigBL.getBooleanValue(SYSCONFIG_EXIST_RECORDS_TO_MIGRATE, true);
	}
}
