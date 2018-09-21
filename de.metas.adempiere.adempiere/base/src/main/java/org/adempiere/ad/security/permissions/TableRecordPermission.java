package org.adempiere.ad.security.permissions;

import org.compiere.model.AccessSqlParser;
import org.compiere.model.I_AD_Record_Access;
import org.compiere.model.POInfo;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.util.Check;

public class TableRecordPermission extends AbstractPermission
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final TableRecordResource resource;
	private final boolean readOnly;
	private final boolean exclude;
	private final boolean dependentEntities;

	private final Supplier<Optional<String>> keyColumnNameSupplier = Suppliers.memoize(new Supplier<Optional<String>>()
	{

		@Override
		public Optional<String> get()
		{
			final POInfo poInfo = POInfo.getPOInfo(getAD_Table_ID());
			if (poInfo == null)
			{
				return Optional.absent();
			}
			final String keyColumnName = poInfo.getKeyColumnName();
			return Optional.fromNullable(keyColumnName);
		}
	});

	private TableRecordPermission(final Builder builder)
	{
		super();
		resource = builder.getResource();
		readOnly = builder.readOnly;
		exclude = builder.exclude;
		dependentEntities = builder.dependentEntities;
	}

	@Override
	public TableRecordResource getResource()
	{
		return resource;
	}

	@Override
	public boolean hasAccess(final Access access)
	{
		// TODO: return accesses.contains(access);
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public Permission mergeWith(final Permission permissionFrom)
	{
		final TableRecordPermission recordPermissionFrom = checkCompatibleAndCast(permissionFrom);

		final Builder builder = builder()
				.setFrom(this);

		// stronger permissions first
		if (!recordPermissionFrom.isReadOnly())
		{
			builder.setReadOnly(false);
		}
		if (!recordPermissionFrom.isDependentEntities())
		{
			builder.setDependentEntities(false);
		}
		if (!recordPermissionFrom.isExclude())
		{
			builder.setExclude(false);
		}

		return builder.build();
	}

	public int getAD_Table_ID()
	{
		return resource.getAD_Table_ID();
	}

	public int getRecord_ID()
	{
		return resource.getRecord_ID();
	}

	public boolean isReadOnly()
	{
		return readOnly;
	}

	public boolean isExclude()
	{
		return exclude;
	}

	public boolean isDependentEntities()
	{
		return dependentEntities;
	}

	/**
	 * Get Synonym of {@link #getKeyColumnName()}
	 *
	 * @return Synonym Column Name
	 */
	public String getKeyColumnNameSynonym()
	{
		final String keyColumnName = getKeyColumnName().orNull();
		if (keyColumnName == null)
		{
			return null;
		}
		else if ("AD_User_ID".equals(keyColumnName))
		{
			return "SalesRep_ID";
		}
		else if ("C_ElementValue_ID".equals(keyColumnName))
		{
			return "Account_ID";
		}
		//
		return null;
	}

	public Optional<String> getKeyColumnName()
	{
		return keyColumnNameSupplier.get();
	}

	/**
	 * Get Key Column Name with consideration of Synonym
	 *
	 * @param tableInfo
	 * @return key column name or <code>null</code>
	 */
	public String getKeyColumnName(final AccessSqlParser.TableInfo[] tableInfo)
	{
		final String keyColumnName = getKeyColumnName().orNull();
		if (keyColumnName == null)
		{
			return null;
		}

		final String columnSyn = getKeyColumnNameSynonym();
		if (columnSyn == null)
		{
			return keyColumnName;
		}
		// We have a synonym - ignore it if base table inquired
		for (int i = 0; i < tableInfo.length; i++)
		{
			if (keyColumnName.equals("AD_User_ID"))
			{
				// List of tables where not to use SalesRep_ID
				if (tableInfo[i].getTableName().equals("AD_User"))
				{
					return keyColumnName;
				}
			}
			else if (keyColumnName.equals("AD_ElementValue_ID"))
			{
				// List of tables where not to use Account_ID
				if (tableInfo[i].getTableName().equals("AD_ElementValue"))
				{
					return keyColumnName;
				}
			}
		}	// tables to be ignored
		return columnSyn;
	}	// getKeyColumnInfo

	public static class Builder
	{
		private TableRecordResource resource;
		private Boolean readOnly;
		private Boolean exclude;
		private Boolean dependentEntities;

		public TableRecordPermission build()
		{
			return new TableRecordPermission(this);
		}

		public Builder setFrom(final TableRecordPermission recordPermission)
		{
			setResource(recordPermission.getResource());
			setReadOnly(recordPermission.isReadOnly());
			setExclude(recordPermission.isExclude());
			setDependentEntities(recordPermission.isDependentEntities());
			return this;
		}

		public Builder setFrom(final I_AD_Record_Access recordAccess)
		{
			setResource(TableRecordResource.of(recordAccess.getAD_Table_ID(), recordAccess.getRecord_ID()));
			setReadOnly(recordAccess.isReadOnly());
			setExclude(recordAccess.isExclude());
			setDependentEntities(recordAccess.isDependentEntities());
			return this;
		}

		public TableRecordResource getResource()
		{
			Check.assumeNotNull(resource, "resource not null");
			return resource;
		}

		public Builder setResource(TableRecordResource resource)
		{
			this.resource = resource;
			return this;
		}

		private Builder setReadOnly(final boolean readOnly)
		{
			this.readOnly = readOnly;
			return this;
		}

		private Builder setExclude(final boolean exclude)
		{
			this.exclude = exclude;
			return this;
		}

		private Builder setDependentEntities(final boolean dependentEntities)
		{
			this.dependentEntities = dependentEntities;
			return this;
		}
	}

}
