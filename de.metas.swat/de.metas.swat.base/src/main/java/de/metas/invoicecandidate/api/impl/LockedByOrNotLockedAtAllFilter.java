package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.Services;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.lock.api.ILock;
import de.metas.lock.api.ILockManager;

/**
 * Accepts all invoice candidates which are locked by a given lock.
 * If no lock was specified consider only those which were not locked at all.
 *
 * @author tsa
 *
 */
/* package */class LockedByOrNotLockedAtAllFilter implements IQueryFilter<I_C_Invoice_Candidate_Recompute>, ISqlQueryFilter
{
	/**
	 * @param lock lock or <code>null</code>
	 */
	public static final LockedByOrNotLockedAtAllFilter of(final ILock lock)
	{
		return new LockedByOrNotLockedAtAllFilter(lock);
	}
	
	// services
	private final transient ILockManager lockManager = Services.get(ILockManager.class);

	// parameters
	private final ILock lock;

	// status
	private boolean sqlBuilt = false;
	private String sql = null;

	private LockedByOrNotLockedAtAllFilter(final ILock lock)
	{
		super();
		this.lock = lock;
	}

	@Override
	public String getSql()
	{
		buildSqlIfNeeded();
		return sql;
	}

	@Override
	public List<Object> getSqlParams(final Properties ctx)
	{
		return Collections.emptyList();
	}

	private final void buildSqlIfNeeded()
	{
		if (sqlBuilt)
		{
			return;
		}

		final String columnNameInvoiceCandidateId = I_C_Invoice_Candidate_Recompute.Table_Name + "." + I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID;
		final String lockedWhereClause;

		//
		// Case: no Lock was mentioned
		// => we consider only those records which were NOT locked
		if (lock == null)
		{
			lockedWhereClause = lockManager.getNotLockedWhereClause(I_C_Invoice_Candidate.Table_Name, columnNameInvoiceCandidateId);
		}
		//
		// Case: Lock is set
		// => we consider only those records which were locked by given lock
		else
		{
			lockedWhereClause = lockManager.getLockedWhereClause(I_C_Invoice_Candidate.class, columnNameInvoiceCandidateId, lock.getOwner());
		}

		sql = "(" + lockedWhereClause + ")";
		sqlBuilt = true;
	}

	@Override
	public boolean accept(final I_C_Invoice_Candidate_Recompute model)
	{
		if (model == null)
		{
			return false;
		}

		final int invoiceCandidateId = model.getC_Invoice_Candidate_ID();
		return acceptInvoiceCandidateId(invoiceCandidateId);
	}

	public boolean acceptInvoiceCandidateId(final int invoiceCandidateId)
	{
		//
		// Case: no Lock was mentioned
		// => we consider only those records which were NOT locked
		if (lock == null)
		{
			return !lockManager.isLocked(I_C_Invoice_Candidate.class, invoiceCandidateId);
		}
		//
		// Case: Lock is set
		// => we consider only those records which were locked by given lock
		else
		{
			return lockManager.isLocked(I_C_Invoice_Candidate.class, invoiceCandidateId, lock.getOwner());
		}
	}

}
