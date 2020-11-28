package de.metas.marketing.base.model;

import javax.annotation.Nullable;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * marketing-base
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

@Value
public class RemoteToLocalSyncResult implements SyncResult
{
	/** email does exist remotely and we just got its remoteId */
	public static RemoteToLocalSyncResult obtainedRemoteId(@NonNull final DataRecord datarecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(datarecord)
				.remoteToLocalStatus(RemoteToLocalStatus.OBTAINED_REMOTE_ID)
				.build();
	}

	/** remoteId does exist remotely and we just got an email that differs from the one we have locally */
	public static RemoteToLocalSyncResult obtainedRemoteEmail(@NonNull final ContactPerson contactPerson)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(contactPerson)
				.remoteToLocalStatus(RemoteToLocalStatus.OBTAINED_REMOTE_EMAIL)
				.build();
	}

	public static RemoteToLocalSyncResult obtainedOtherRemoteData(@NonNull final DataRecord dataRecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(dataRecord)
				.remoteToLocalStatus(RemoteToLocalStatus.OBTAINED_OTHER_REMOTE_DATA)
				.build();
	}

	/** email exists remotely and locally, but we just obtained a changed remote bounce status */
	public static RemoteToLocalSyncResult obtainedEmailBounceInfo(@NonNull final ContactPerson datarecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(datarecord)
				.remoteToLocalStatus(RemoteToLocalStatus.OBTAINED_EMAIL_BOUNCE_INFO)
				.build();
	}

	/** email doesn't exist remotely but we do have a remoteId */
	public static RemoteToLocalSyncResult deletedOnRemotePlatform(@NonNull final DataRecord datarecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(datarecord)
				.remoteToLocalStatus(RemoteToLocalStatus.DELETED_ON_REMOTE_PLATFORM)
				.build();
	}

	/** email doesn't exist remotely and we don't have a remoteId */
	public static RemoteToLocalSyncResult notYetAddedToRemotePlatform(@NonNull final DataRecord datarecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(datarecord)
				.remoteToLocalStatus(RemoteToLocalStatus.NOT_YET_ADDED_TO_REMOTE_PLATFORM)
				.build();
	}

	/** contact or campaign that doesn't yet exist locally */
	public static RemoteToLocalSyncResult obtainedNewDataRecord(@NonNull final DataRecord datarecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(datarecord)
				.remoteToLocalStatus(RemoteToLocalStatus.OBTAINED_NEW_CONTACT_PERSON)
				.build();
	}

	public static RemoteToLocalSyncResult noChanges(@NonNull final DataRecord dataRecord)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(dataRecord)
				.remoteToLocalStatus(RemoteToLocalStatus.NO_CHANGES)
				.build();
	}

	public static RemoteToLocalSyncResult error(@NonNull final DataRecord datarecord, String errorMessage)
	{
		return RemoteToLocalSyncResult.builder()
				.synchedDataRecord(datarecord)
				.remoteToLocalStatus(RemoteToLocalStatus.ERROR)
				.errorMessage(errorMessage)
				.build();
	}

	public enum RemoteToLocalStatus
	{
		/** See {@link RemoteToLocalSyncResult#deletedOnRemotePlatform(DataRecord)}. */
		DELETED_ON_REMOTE_PLATFORM,

		/** See {@link RemoteToLocalSyncResult#notYetAddedToRemotePlatform(DataRecord)}. */
		NOT_YET_ADDED_TO_REMOTE_PLATFORM,

		/** See {@link RemoteToLocalSyncResult#obtainedRemoteId(DataRecord)}. */
		OBTAINED_REMOTE_ID,

		/** See {@link RemoteToLocalSyncResult#obtainedRemoteEmail(DataRecord)}. */
		OBTAINED_REMOTE_EMAIL,

		/** See {@link RemoteToLocalSyncResult#obtainedEmailBounceInfo(DataRecord)}. */
		OBTAINED_EMAIL_BOUNCE_INFO,


		OBTAINED_NEW_CONTACT_PERSON, NO_CHANGES, OBTAINED_OTHER_REMOTE_DATA, ERROR;

	}

	RemoteToLocalStatus remoteToLocalStatus;

	String errorMessage;

	DataRecord synchedDataRecord;

	@Builder
	private RemoteToLocalSyncResult(
			@NonNull final DataRecord synchedDataRecord,
			@Nullable final RemoteToLocalStatus remoteToLocalStatus,
			@Nullable final String errorMessage)
	{
		this.synchedDataRecord = synchedDataRecord;
		this.remoteToLocalStatus = remoteToLocalStatus;
		this.errorMessage = errorMessage;
	}
}
