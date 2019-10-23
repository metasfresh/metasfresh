package org.compiere.model;

import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Env;

import de.metas.security.IUserRolePermissions;
import de.metas.security.permissions.WindowMaxQueryRecordsConstraint;

/**
 * {@link GridTab} query restriction checker.
 * 
 * To create a new instance, use {@link #builder()} which will assist you configuring and building the new {@link GridTabMaxRowsRestrictionChecker}.
 * 
 * After you have one instance, you can use it to:
 * <ul>
 * <li>resolve {@link GridTabMaxRows} restrictions to actual numbers
 * <li>check above how many records you need to ask the user to refine they query that will be performed: {@link #isQueryRequire(int)}.
 * <li>check if a number of rows is exceeding the maximum allowed to be displayed in a window: {@link #isQueryMax(int)}.
 * <li>etc
 * </ul>
 * 
 * @author tsa
 *
 */
public class GridTabMaxRowsRestrictionChecker
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final WindowMaxQueryRecordsConstraint constraint;
	private final int maxQueryRecordsPerTab;

	private GridTabMaxRowsRestrictionChecker(final Builder builder)
	{
		super();
		this.constraint = builder.getConstraint();
		this.maxQueryRecordsPerTab = builder.getMaxQueryRecordsPerTab();
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * Checks if given <code>noRecords</code> is exceeding the maximum rows allowed to be displayed in a window.
	 *
	 * @param noRecords records
	 * @return true if maximum allowed rows were exceeded by given <code>noRecords</code>.
	 */
	public boolean isQueryMax(final int noRecords)
	{
		int max = getMaxQueryRecords();
		return max > 0 && noRecords > max;
	}	// isQueryMax

	/**
	 * Checks if given <code>noRecords</code> shall be presented to the user as they are,
	 * or they are too many and user needs to refine the query because displaying the records.
	 *
	 * @param noRecords records
	 * @return <code>true</code> if they are too many and user would need to the refine the query.
	 */
	public boolean isQueryRequire(final int noRecords)
	{
		if (noRecords < 2)
		{
			return false;
		}

		final int max = getMaxQueryRecords();
		if (max > 0 && noRecords > max)
		{
			return true;
		}

		final int qu = getConfirmQueryRecords();
		return (noRecords > qu);
	}	// isQueryRequire

	/**
	 * @return maximum allowed rows to be presented to user in a window.
	 */
	public int getMaxQueryRecords()
	{
		//
		// Check if we have it set on Tab level
		final int maxQueryRecordsPerRole = constraint.getMaxQueryRecordsPerRole();
		if (maxQueryRecordsPerRole > 0 && maxQueryRecordsPerTab > 0)
		{
			return Math.min(maxQueryRecordsPerTab, maxQueryRecordsPerRole);
		}
		else if (maxQueryRecordsPerTab > 0)
		{
			return maxQueryRecordsPerTab;
		}
		else if (maxQueryRecordsPerRole > 0)
		{
			return maxQueryRecordsPerRole;
		}
		else
		{
			return 0;
		}
	}

	/**
	 * Gets the maximum allowed records to be presented to user, without asking him to confirm/refine the initial query.
	 * 
	 * If there was no limit configured on role level or in other places, {@value #DEFAULT_ConfirmQueryRecords} will be returned.
	 * 
	 * @return maximum allowed records to be presented to user, without asking him to confirm/refine the initial query.
	 */
	public int getConfirmQueryRecords()
	{
		return constraint.getConfirmQueryRecords();
	}

	/**
	 * Converts the given <code>maxQueryRecords</code> restriction to an actual number of rows.
	 * 
	 * If there was NO maximum number of rows configured, then ZERO will be returned.
	 * 
	 * @param maxQueryRecords
	 * @return maximum number of rows allowed to be presented to a user in a window.
	 */
	public int resolve(final GridTabMaxRows maxQueryRecords)
	{
		// Case: we were asked to use the default
		if (maxQueryRecords != null && maxQueryRecords.isDefault())
		{
			return getMaxQueryRecords();
		}
		// Case: we were asked to not enforce at all
		else if (maxQueryRecords == null || maxQueryRecords.isNoRestriction())
		{
			return 0;
		}
		// Case: we got a specific maximum number of records we shall display
		else
		{
			return maxQueryRecords.getMaxRows();
		}
	}

	public static class Builder
	{
		private IUserRolePermissions userRolePermissions;
		private GridTab gridTab;
		private Integer maxQueryRecordsPerTab = null;

		private Builder()
		{
			super();
		}

		public final GridTabMaxRowsRestrictionChecker build()
		{
			return new GridTabMaxRowsRestrictionChecker(this);
		}

		public Builder setUserRolePermissions(final IUserRolePermissions userRolePermissions)
		{
			this.userRolePermissions = userRolePermissions;
			return this;
		}

		private WindowMaxQueryRecordsConstraint getConstraint()
		{
			return getUserRolePermissions()
					.getConstraint(WindowMaxQueryRecordsConstraint.class)
					.or(WindowMaxQueryRecordsConstraint.DEFAULT);
		}

		private final IUserRolePermissions getUserRolePermissions()
		{
			if (userRolePermissions != null)
			{
				return userRolePermissions;
			}

			return Env.getUserRolePermissions();
		}

		public Builder setAD_Tab(final GridTab gridTab)
		{
			this.gridTab = gridTab;
			return this;
		}

		public Builder setMaxQueryRecordsPerTab(final int maxQueryRecordsPerTab)
		{
			this.maxQueryRecordsPerTab = maxQueryRecordsPerTab;
			return this;
		}

		private int getMaxQueryRecordsPerTab()
		{
			if (maxQueryRecordsPerTab != null)
			{
				return maxQueryRecordsPerTab;
			}
			else if (gridTab != null)
			{
				return gridTab.getMaxQueryRecords();
			}
			return 0;
		}

	}
}
