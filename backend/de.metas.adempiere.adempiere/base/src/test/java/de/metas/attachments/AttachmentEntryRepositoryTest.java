/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.attachments;

import de.metas.attachments.listener.TableAttachmentListenerRepository;
import de.metas.attachments.listener.TableAttachmentListenerService;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.FileUtil;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.IOUtils;
import org.compiere.model.I_AD_AttachmentEntry;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.*;

public class AttachmentEntryRepositoryTest
{
	private AttachmentEntryRepository attachmentEntryRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final AttachmentEntryFactory attachmentEntryFactory = new AttachmentEntryFactory();
		final TableAttachmentListenerService tableAttachmentListenerService = new TableAttachmentListenerService(new TableAttachmentListenerRepository());
		attachmentEntryRepository = new AttachmentEntryRepository(attachmentEntryFactory, tableAttachmentListenerService);
	}

	@Test
	public void givenURLTypeAttachment_whenRetrieveAttachmentEntryData_thenReturnEmptyData()
	{
		//given
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename("someName")
				.url(URI.create("https://metasfresh.com"))
				.build();

		//when
		final byte[] data = attachmentEntryRepository.retrieveAttachmentEntryData(attachmentEntryId);

		//then
		assertThat(data).isNull();
	}

	@Test
	public void givenLocalFileURLTypeAttachment_whenRetrieveAttachmentEntryData_thenReturnReferencedData() throws IOException
	{
		//given
		final String fileName = "localFile.txt";
		final String fileData = "localFile data";

		final  File localFile = createFile(fileName, fileData);

		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.LocalFileURL)
				.filename(localFile.getName())
				.url(normalizePath(localFile.getAbsolutePath()).toUri())
				.build();

		//when
		final byte[] data = attachmentEntryRepository.retrieveAttachmentEntryData(attachmentEntryId);

		//then
		assertThat(data).isEqualTo(fileData.getBytes());
	}

	@Test
	public void givenDataTypeAttachment_whenRetrieveAttachmentEntryData_thenReturnData() throws IOException
	{
		//given
		final String fileName = "filename.txt";
		final String fileData = "File data content to be tested";

		final  File testFile = createFile(fileName, fileData);

		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord().type(AttachmentEntryType.Data)
				.filename(testFile.getName())
				.url(normalizePath(testFile.getAbsolutePath()).toUri())
				.data(fileData)
				.build();

		//when
		final byte[] data = attachmentEntryRepository.retrieveAttachmentEntryData(attachmentEntryId);

		//then
		assertThat(data).isEqualTo(fileData.getBytes());
	}

	@Test
	public void givenLocalFileURLTypeAttachment_whenRetrieveAttachmentEntryDataResource_thenReturnResource() throws IOException
	{
		//given
		final String fileName = "localFile.txt";
		final String fileData = "localFile data";

		final  File localFile = createFile(fileName, fileData);

		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.LocalFileURL)
				.filename(localFile.getName())
				.url(normalizePath(localFile.getAbsolutePath()).toUri())
				.build();

		//when
		final AttachmentEntryDataResource attachmentEntryDataResource = attachmentEntryRepository.retrieveAttachmentEntryDataResource(attachmentEntryId);

		//then
		assertThat(attachmentEntryDataResource.getFilename()).isEqualTo(fileName);
		assertThat(attachmentEntryDataResource.getDescription()).isNull();
		assertThat(IOUtils.toByteArray(attachmentEntryDataResource.getInputStream())).isEqualTo(fileData.getBytes());
	}

	@Builder(builderMethodName = "newAttachmentEntryRecord")
	private static AttachmentEntryId attachmentEntry(
			@NonNull final AttachmentEntryType type,
			@NonNull final String filename,
			@Nullable final URI url,
			@Nullable final String data)
	{
		final I_AD_AttachmentEntry attachmentEntryRecord = InterfaceWrapperHelper.newInstance(I_AD_AttachmentEntry.class);

		attachmentEntryRecord.setFileName(filename);
		attachmentEntryRecord.setType(type.getCode());

		if (url != null)
		{
			attachmentEntryRecord.setURL(url.toString());
		}

		if (EmptyUtil.isNotBlank(data))
		{
			attachmentEntryRecord.setBinaryData(data.getBytes());
		}

		InterfaceWrapperHelper.saveRecord(attachmentEntryRecord);

		return AttachmentEntryId.ofRepoId(attachmentEntryRecord.getAD_AttachmentEntry_ID());
	}

	@NonNull
	private static File createFile(@NonNull final String filename, @NonNull final String fileData) throws IOException
	{
		final File testFile = new File(filename);

		final FileWriter myWriter = new FileWriter(testFile.getName());
		myWriter.write(fileData);
		myWriter.close();

		return testFile;
	}

	@NonNull
	private static Path normalizePath(@NonNull final String path) throws MalformedURLException
	{
		final String normalizedPath = path.replaceAll("\\\\","/");

		return FileUtil.getFilePath(URI.create("file://" + normalizedPath).toURL());
	}
}
