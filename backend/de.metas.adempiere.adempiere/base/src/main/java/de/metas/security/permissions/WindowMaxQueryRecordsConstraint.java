package de.metas.security.permissions;

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


import javax.annotation.concurrent.Immutable;

/**
 * Defines constraints that shall be applied when querying records in a window.
 * 
 * @author tsa
 *
 */
@Immutable
public final class WindowMaxQueryRecordsConstraint extends Constraint
{
	public static final WindowMaxQueryRecordsConstraint of(final int maxQueryRecordsPerRole, final int confirmQueryRecords)
	{
		return new WindowMaxQueryRecordsConstraint(maxQueryRecordsPerRole, confirmQueryRecords);
	}

	private static final int DEFAULT_MaxQueryRecordsPerTab = 0; // i.e. infinite
	private static final int DEFAULT_ConfirmQueryRecords = 500;
	public static final WindowMaxQueryRecordsConstraint DEFAULT = new WindowMaxQueryRecordsConstraint(DEFAULT_MaxQueryRecordsPerTab, DEFAULT_ConfirmQueryRecords);

	private final int maxQueryRecordsPerRole;
	private final int confirmQueryRecords;

	private WindowMaxQueryRecordsConstraint(int maxQueryRecordsPerRole, int confirmQueryRecords)
	{
		super();
		this.maxQueryRecordsPerRole = maxQueryRecordsPerRole <= 0 ? 0 : maxQueryRecordsPerRole;

		// NOTE: instead of throw exception it's better to fallback to default. Else, all our roles on will fail now.
		// Before changing thise, please make sure u check AD_Role.ConfirmQueryRecords.
		// Check.assume(confirmQueryRecords > 0, "confirmQueryRecords > 0 but it was {}", confirmQueryRecords);
		this.confirmQueryRecords = confirmQueryRecords <= 0 ? DEFAULT_ConfirmQueryRecords : confirmQueryRecords;
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen
		final int queryRecordsPerRole = getMaxQueryRecordsPerRole();
		final int confirmQueryRecords = getConfirmQueryRecords();
		return "WindowMaxQueryRecords["
				+ "@MaxQueryRecords@: " + queryRecordsPerRole
				+ ", @ConfirmQueryRecords@: " + confirmQueryRecords
				+ "]";
	}

	/** @return false, i.e. never inherit this constraint because it shall be defined by current role itself */
	@Override
	public boolean isInheritable()
	{
		return false;
	}

	/**
	 * @return maximum allowed rows to be presented to user in a window or ZERO if no restriction.
	 */
	public int getMaxQueryRecordsPerRole()
	{
		return maxQueryRecordsPerRole;
	}

	/**
	 * Gets the maximum allowed records to be presented to user, without asking him to confirm/refine the initial query.
	 * 
	 * @return maximum allowed records to be presented to user, without asking him to confirm/refine the initial query; always returns greather than zero.
	 */
	public int getConfirmQueryRecords()
	{
		return confirmQueryRecords;
	}

}
