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
public class LocalToRemoteSyncResult implements SyncResult
{
	/** We don't know (and for performance reasons didn't check)  if the record was existing remotely. But at any rate, now it's there. */
	public static LocalToRemoteSyncResult upserted(@NonNull final DataRecord datarecord)
	{
		return LocalToRemoteSyncResult.builder()
				.synchedDataRecord(datarecord)
				.localToRemoteStatus(LocalToRemoteStatus.UPSERTED_ON_REMOTE)
				.build();
	}

	public static LocalToRemoteSyncResult inserted(@NonNull final DataRecord datarecord)
	{
		return LocalToRemoteSyncResult.builder()
				.synchedDataRecord(datarecord)
				.localToRemoteStatus(LocalToRemoteStatus.INSERTED_ON_REMOTE)
				.build();
	}

	public static LocalToRemoteSyncResult updated(@NonNull final DataRecord datarecord)
	{
		return LocalToRemoteSyncResult.builder()
				.synchedDataRecord(datarecord)
				.localToRemoteStatus(LocalToRemoteStatus.UPDATED_ON_REMOTE)
				.build();
	}

	public static LocalToRemoteSyncResult deleted(@NonNull final DataRecord datarecord)
	{
		return LocalToRemoteSyncResult.builder()
				.synchedDataRecord(datarecord)
				.localToRemoteStatus(LocalToRemoteStatus.DELETED_ON_REMOTE)
				.build();
	}

	public static LocalToRemoteSyncResult error(
			@NonNull final DataRecord datarecord,
			@NonNull final String errorMessage)
	{
		return LocalToRemoteSyncResult.builder()
				.synchedDataRecord(datarecord)
				.localToRemoteStatus(LocalToRemoteStatus.ERROR)
				.errorMessage(errorMessage)
				.build();
	}

	public enum LocalToRemoteStatus
	{
		INSERTED_ON_REMOTE,
		UPDATED_ON_REMOTE,
		UPSERTED_ON_REMOTE,
		DELETED_ON_REMOTE, UNCHANGED, ERROR;
	}

	LocalToRemoteStatus localToRemoteStatus;

	String errorMessage;

	DataRecord synchedDataRecord;

	@Builder
	private LocalToRemoteSyncResult(
			@NonNull final DataRecord synchedDataRecord,
			@Nullable final LocalToRemoteStatus localToRemoteStatus,
			@Nullable final String errorMessage)
	{
		this.synchedDataRecord = synchedDataRecord;
		this.localToRemoteStatus = localToRemoteStatus;
		this.errorMessage = errorMessage;
	}
}
