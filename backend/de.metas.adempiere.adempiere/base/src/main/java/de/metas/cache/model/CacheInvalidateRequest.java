package de.metas.cache.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Trace;

import javax.annotation.Nullable;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class CacheInvalidateRequest
{
	public static Builder builder()
	{
		return new Builder();
	}

	public static CacheInvalidateRequest all()
	{
		return ALL;
	}

	public static CacheInvalidateRequest allRecordsForTable(@NonNull final String rootTableName)
	{
		final int rootRecordId = RECORD_ID_ALL;
		final String childTableName = null;
		final int childRecordId = RECORD_ID_ALL;
		final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
		return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
	}

	public static CacheInvalidateRequest rootRecord(@NonNull final String rootTableName, final int rootRecordId)
	{
		Check.assume(rootRecordId >= 0, "rootRecordId >= 0");

		final String childTableName = null;
		final int childRecordId = RECORD_ID_ALL;
		final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
		return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
	}

	public static CacheInvalidateRequest rootRecord(@NonNull final String rootTableName, @NonNull final RepoIdAware rootId)
	{
		return rootRecord(rootTableName, rootId.getRepoId());
	}

	public static CacheInvalidateRequest allChildRecords(@NonNull final String rootTableName, @NonNull final RepoIdAware rootId, @NonNull final String childTableName)
	{
		return allChildRecords(rootTableName, rootId.getRepoId(), childTableName);
	}

	public static CacheInvalidateRequest allChildRecords(@NonNull final String rootTableName, final int rootRecordId, @NonNull final String childTableName)
	{
		Check.assume(rootRecordId >= 0, "rootRecordId >= 0");

		final int childRecordId = RECORD_ID_ALL;
		final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
		return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
	}

	public static CacheInvalidateRequest fromTableNameAndRecordId(final String tableName, final int recordId)
	{
		if (tableName == null)
		{
			return all();
		}
		else if (recordId < 0)
		{
			return allRecordsForTable(tableName);
		}
		else
		{
			return rootRecord(tableName, recordId);
		}
	}

	private static final boolean DEBUG = false;

	private static final int RECORD_ID_ALL = -1;
	private static final CacheInvalidateRequest ALL = new CacheInvalidateRequest(null, RECORD_ID_ALL, null, RECORD_ID_ALL, "ALL");

	@JsonProperty("rootTableName")
	private final String rootTableName;
	@JsonProperty("rootRecordId")
	private final int rootRecordId;

	@JsonProperty("childTableName")
	private final String childTableName;
	@JsonProperty("childRecordId")
	private final int childRecordId;

	@JsonProperty("debugFrom")
	final String debugFrom;

	@JsonCreator
	private CacheInvalidateRequest(
			@JsonProperty("rootTableName") final String rootTableName,
			@JsonProperty("rootRecordId") final int rootRecordId,
			@JsonProperty("childTableName") final String childTableName,
			@JsonProperty("childRecordId") final int childRecordId,
			@JsonProperty("debugFrom") final String debugFrom)
	{
		this.rootTableName = rootTableName;
		this.rootRecordId = rootRecordId >= 0 ? rootRecordId : RECORD_ID_ALL;
		this.childTableName = childTableName;
		this.childRecordId = childRecordId >= 0 ? childRecordId : RECORD_ID_ALL;
		this.debugFrom = debugFrom;
	}

	public boolean isAll()
	{
		return this == ALL;
	}

	public boolean isAllRecords()
	{
		if (!Check.isEmpty(childTableName))
		{
			return childRecordId == RECORD_ID_ALL;
		}
		else if (!Check.isEmpty(rootTableName))
		{
			return rootRecordId == RECORD_ID_ALL;
		}
		else
		{
			return false;
		}
	}

	@Nullable
	public TableRecordReference getRootRecordOrNull()
	{
		if (rootTableName != null && rootRecordId >= 0)
		{
			return TableRecordReference.of(rootTableName, rootRecordId);
		}
		else
		{
			return null;
		}
	}

	@Nullable
	public TableRecordReference getChildRecordOrNull()
	{
		if (childTableName != null && childRecordId >= 0)
		{
			return TableRecordReference.of(childTableName, childRecordId);
		}
		else
		{
			return null;
		}
	}

	public TableRecordReference getRecordEffective()
	{
		if (childTableName != null && childRecordId >= 0)
		{
			return TableRecordReference.of(childTableName, childRecordId);
		}
		else if (rootTableName != null && rootRecordId >= 0)
		{
			return TableRecordReference.of(rootTableName, rootRecordId);
		}
		else
		{
			throw new AdempiereException("Cannot extract effective record from " + this);
		}
	}

	public String getTableNameEffective()
	{
		return childTableName != null ? childTableName : rootTableName;
	}

	public int getRecordIdEffective()
	{
		return childTableName != null ? childRecordId : rootRecordId;
	}

	public static final class Builder
	{
		private String rootTableName;
		private int rootRecordId = -1;

		private String childTableName;
		private int childRecordId = -1;

		private Builder()
		{
		}

		public CacheInvalidateRequest build()
		{
			final String debugFrom = DEBUG ? Trace.toOneLineStackTraceString() : null;
			return new CacheInvalidateRequest(rootTableName, rootRecordId, childTableName, childRecordId, debugFrom);
		}

		public Builder rootRecord(@NonNull final String tableName, final int recordId)
		{
			Check.assume(recordId >= 0, "recordId >= 0");

			rootTableName = tableName;
			rootRecordId = recordId;
			return this;
		}

		public Builder childRecord(@NonNull final String tableName, final int recordId)
		{
			Check.assume(recordId >= 0, "recordId >= 0");

			childTableName = tableName;
			childRecordId = recordId;
			return this;
		}

	}
}
