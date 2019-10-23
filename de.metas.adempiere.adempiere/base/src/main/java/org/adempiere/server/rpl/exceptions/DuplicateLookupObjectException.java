package org.adempiere.server.rpl.exceptions;

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


import java.util.List;

import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.compiere.model.PO;

/**
 * Exception meant to contain data for failed lookups.
 * 
 * @author ts
 * @author al
 */
public final class DuplicateLookupObjectException extends ReplicationException
{
	private static final long serialVersionUID = 5099228399627874129L;

	private final List<PO> lookedUpPOs;
	private final I_EXP_ReplicationTrxLine trxLineDraft;
	private final boolean doLookup;

	/**
	 * Constructs a {@link DuplicateLookupObjectException} with <code>lookedUpPOs=null</code>, <code>trxLineDraft=null</code>, and <code>doLookup=false</code>
	 * 
	 * @param adMessage
	 */
	public DuplicateLookupObjectException(final String adMessage)
	{
		this(adMessage, null, null, false);
	}

	public DuplicateLookupObjectException(final String adMessage, final List<PO> lookedUpPOs, final I_EXP_ReplicationTrxLine trxLineDraft, final boolean doLookup)
	{
		super(adMessage);
		this.lookedUpPOs = lookedUpPOs;
		this.trxLineDraft = trxLineDraft;
		this.doLookup = doLookup;
	}

	public List<PO> getLookedUpPOs()
	{
		return lookedUpPOs;
	}

	public I_EXP_ReplicationTrxLine getTrxLineDraftOrNull()
	{
		return trxLineDraft;
	}

	public boolean isDoLookup()
	{
		return doLookup;
	}
}
