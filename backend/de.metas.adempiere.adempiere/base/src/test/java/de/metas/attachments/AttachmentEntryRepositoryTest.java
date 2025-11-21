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

import com.google.common.collect.ImmutableList;
import de.metas.CreatedUpdatedInfo;
import de.metas.common.util.EmptyUtil;
import de.metas.common.util.FileUtil;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
import java.time.ZonedDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AttachmentEntryRepositoryTest
{
	private AttachmentEntryRepository attachmentEntryRepository;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final AttachmentEntryFactory attachmentEntryFactory = new AttachmentEntryFactory();
		attachmentEntryRepository = new AttachmentEntryRepository(attachmentEntryFactory);
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

		final File localFile = createFile(fileName, fileData);

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

		final File testFile = createFile(fileName, fileData);

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

		final File localFile = createFile(fileName, fileData);

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

	@Test
	public void givenDataTypeAttachment_whenRetrieveAttachmentEntryDataResource_thenReturnResource() throws IOException
	{
		//given
		final String fileName = "dataFile.txt";
		final String fileData = "data content";
		final String description = "test description";

		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.Data)
				.filename(fileName)
				.data(fileData)
				.description(description)
				.build();

		//when
		final AttachmentEntryDataResource attachmentEntryDataResource = attachmentEntryRepository.retrieveAttachmentEntryDataResource(attachmentEntryId);

		//then
		assertThat(attachmentEntryDataResource.getFilename()).isEqualTo(fileName);
		assertThat(attachmentEntryDataResource.getDescription()).isEqualTo(description);
		assertThat(IOUtils.toByteArray(attachmentEntryDataResource.getInputStream())).isEqualTo(fileData.getBytes());
	}

	@Test
	public void givenURLTypeAttachment_whenRetrieveAttachmentEntryDataResource_thenReturnResource()
	{
		//given
		final String fileName = "urlFile.txt";
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename(fileName)
				.url(URI.create("https://metasfresh.com"))
				.build();

		//when
		final AttachmentEntryDataResource attachmentEntryDataResource = attachmentEntryRepository.retrieveAttachmentEntryDataResource(attachmentEntryId);

		//then
		assertThat(attachmentEntryDataResource.getFilename()).isEqualTo(fileName);
	}

	@Test
	public void givenAttachmentEntryId_whenGetById_thenReturnAttachmentEntry()
	{
		//given
		final String fileName = "testFile.txt";
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename(fileName)
				.url(URI.create("https://metasfresh.com"))
				.build();

		//when
		final AttachmentEntry attachmentEntry = attachmentEntryRepository.getById(attachmentEntryId);

		//then
		assertThat(attachmentEntry).isNotNull();
		assertThat(attachmentEntry.getId()).isEqualTo(attachmentEntryId);
		assertThat(attachmentEntry.getFilename()).isEqualTo(fileName);
		assertThat(attachmentEntry.getType()).isEqualTo(AttachmentEntryType.URL);
	}

	@Test
	public void givenURLTypeAttachment_whenUpdateAttachmentEntryData_thenSucceed()
	{
		//given
		final String fileName = "urlFile.txt";
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename(fileName)
				.url(URI.create("https://metasfresh.com"))
				.build();

		final byte[] newData = "new data".getBytes();

		//when
		assertThatThrownBy(() -> attachmentEntryRepository.updateAttachmentEntryData(attachmentEntryId, newData)).isInstanceOf(AdempiereException.class);
	}

	@Test
	public void givenDataTypeAttachment_whenUpdateAttachmentEntryData_thenThrowException()
	{
		//given
		final String fileName = "dataFile.txt";
		final String initialData = "initial data";
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.Data)
				.filename(fileName)
				.data(initialData)
				.build();
		assertThat(attachmentEntryRepository.retrieveAttachmentEntryData(attachmentEntryId)).isEqualTo(initialData.getBytes()); // guard
		
		final byte[] newData = "new data".getBytes();

		//when - should throw AdempiereException
		attachmentEntryRepository.updateAttachmentEntryData(attachmentEntryId, newData);

		final byte[] retrievedData = attachmentEntryRepository.retrieveAttachmentEntryData(attachmentEntryId);
		assertThat(retrievedData).isEqualTo(newData);
		
	}

	@Test
	public void givenNewAttachmentEntry_whenSave_thenPersistAndReturnWithId()
	{
		//given
		final AttachmentEntry newEntry = AttachmentEntry.builder()
				.type(AttachmentEntryType.URL)
				.filename("newFile.txt")
				.url(URI.create("https://example.com"))
				.createdUpdatedInfo(CreatedUpdatedInfo.createNew(UserId.ofRepoId(10), ZonedDateTime.now()))
				.build();

		assertThat(newEntry.getId()).isNull();

		//when
		final AttachmentEntry savedEntry = attachmentEntryRepository.save(newEntry);

		//then
		assertThat(savedEntry.getId()).isNotNull();
		assertThat(savedEntry.getFilename()).isEqualTo("newFile.txt");
		assertThat(savedEntry.getType()).isEqualTo(AttachmentEntryType.URL);
		assertThat(savedEntry.getUrl()).isEqualTo(URI.create("https://example.com"));
	}

	@Test
	public void givenExistingAttachmentEntry_whenSave_thenUpdate()
	{
		//given
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename("originalFile.txt")
				.url(URI.create("https://original.com"))
				.build();

		final AttachmentEntry existingEntry = attachmentEntryRepository.getById(attachmentEntryId);
		final AttachmentEntry updatedEntry = existingEntry.toBuilder()
				.filename("updatedFile.txt")
				.build();

		//when
		final AttachmentEntry savedEntry = attachmentEntryRepository.save(updatedEntry);

		//then
		assertThat(savedEntry.getId()).isEqualTo(attachmentEntryId);
		assertThat(savedEntry.getFilename()).isEqualTo("updatedFile.txt");
	}

	@Test
	public void givenMultipleAttachmentEntries_whenSaveAll_thenPersistAll()
	{
		//given
		final AttachmentEntry entry1 = AttachmentEntry.builder()
				.type(AttachmentEntryType.URL)
				.filename("file1.txt")
				.url(URI.create("https://example1.com"))
				.createdUpdatedInfo(CreatedUpdatedInfo.createNew(UserId.ofRepoId(10), ZonedDateTime.now()))
				.build();

		final AttachmentEntry entry2 = AttachmentEntry.builder()
				.type(AttachmentEntryType.URL)
				.filename("file2.txt")
				.url(URI.create("https://example2.com"))
				.createdUpdatedInfo(CreatedUpdatedInfo.createNew(UserId.ofRepoId(10), ZonedDateTime.now()))
				.build();

		final java.util.List<AttachmentEntry> entries = Arrays.asList(entry1, entry2);

		//when
		final ImmutableList<AttachmentEntry> savedEntries = attachmentEntryRepository.saveAll(entries);

		//then
		assertThat(savedEntries).hasSize(2);
		assertThat(savedEntries.get(0).getId()).isNotNull();
		assertThat(savedEntries.get(0).getFilename()).isEqualTo("file1.txt");
		assertThat(savedEntries.get(1).getId()).isNotNull();
		assertThat(savedEntries.get(1).getFilename()).isEqualTo("file2.txt");
	}

	@Test
	public void givenAttachmentEntry_whenDeleteById_thenRemove()
	{
		//given
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename("toDelete.txt")
				.url(URI.create("https://example.com"))
				.build();

		final AttachmentEntry entry = attachmentEntryRepository.getById(attachmentEntryId);

		//when
		attachmentEntryRepository.deleteById(entry);

		//then - attempting to retrieve should fail
		try
		{
			attachmentEntryRepository.getById(attachmentEntryId);
			org.junit.Assert.fail("Expected exception when retrieving deleted attachment");
		}
		catch (final Exception e)
		{
			// Expected - entry was deleted
		}
	}

	@Test
	public void givenAttachmentEntryId_whenDeleteById_thenRemove()
	{
		//given
		final AttachmentEntryId attachmentEntryId = newAttachmentEntryRecord()
				.type(AttachmentEntryType.URL)
				.filename("toDelete.txt")
				.url(URI.create("https://example.com"))
				.build();

		//when
		attachmentEntryRepository.deleteById(attachmentEntryId);

		//then - attempting to retrieve should fail
		try
		{
			attachmentEntryRepository.getById(attachmentEntryId);
			org.junit.Assert.fail("Expected exception when retrieving deleted attachment");
		}
		catch (final Exception e)
		{
			// Expected - entry was deleted
		}
	}

	@Test
	public void givenNullId_whenDeleteById_thenDoNothing()
	{
		//given
		final AttachmentEntryId nullId = null;

		//when - should not throw exception
		attachmentEntryRepository.deleteById(nullId);

		//then - no exception thrown, method handles null gracefully
	}

	@Builder(builderMethodName = "newAttachmentEntryRecord")
	private static AttachmentEntryId attachmentEntry(
			@NonNull final AttachmentEntryType type,
			@NonNull final String filename,
			@Nullable final URI url,
			@Nullable final String data,
			@Nullable final String description)
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

		if (EmptyUtil.isNotBlank(description))
		{
			attachmentEntryRecord.setDescription(description);
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
		final String normalizedPath = path.replaceAll("\\\\", "/");

		return FileUtil.getFilePath(URI.create("file://" + normalizedPath).toURL());
	}
}
