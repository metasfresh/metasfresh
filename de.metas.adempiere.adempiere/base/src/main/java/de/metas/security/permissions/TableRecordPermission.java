package de.metas.security.permissions;

import java.util.List;

import org.compiere.model.POInfo;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;

import de.metas.security.RoleId;
import de.metas.security.impl.ParsedSql;
import de.metas.security.impl.ParsedSql.TableNameAndAlias;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder(toBuilder = true)
@Value
public class TableRecordPermission implements Permission
{
	@NonNull
	private final RoleId roleId;
	@NonNull
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
		final TableRecordPermission recordPermissionFrom = PermissionInternalUtils.checkCompatibleAndCastToTarget(this, permissionFrom);

		final TableRecordPermissionBuilder builder = toBuilder();

		// stronger permissions first
		if (!recordPermissionFrom.isReadOnly())
		{
			builder.readOnly(false);
		}
		if (!recordPermissionFrom.isDependentEntities())
		{
			builder.dependentEntities(false);
		}
		if (!recordPermissionFrom.isExclude())
		{
			builder.exclude(false);
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

	/**
	 * Get Synonym of {@link #getKeyColumnName()}
	 *
	 * @return Synonym Column Name
	 */
	private String getKeyColumnNameSynonym()
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

	private Optional<String> getKeyColumnName()
	{
		return keyColumnNameSupplier.get();
	}

	/**
	 * Get Key Column Name with consideration of table aliases
	 *
	 * @param tableNameAndAliases
	 * @return key column name or <code>null</code>
	 */
	String getKeyColumnName(final List<ParsedSql.TableNameAndAlias> tableNameAndAliases)
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
		for (final TableNameAndAlias tableNameAndAlias : tableNameAndAliases)
		{
			if ("AD_User_ID".equals(keyColumnName))
			{
				// List of tables where not to use SalesRep_ID
				if ("AD_User".equals(tableNameAndAlias.getTableName()))
				{
					return keyColumnName;
				}
			}
			else if ("AD_ElementValue_ID".equals(keyColumnName))
			{
				// List of tables where not to use Account_ID
				if ("AD_ElementValue".equals(tableNameAndAlias.getTableName()))
				{
					return keyColumnName;
				}
			}
		}	// tables to be ignored
		return columnSyn;
	}	// getKeyColumnInfo
}
