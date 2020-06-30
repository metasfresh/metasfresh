package de.metas.printing.api;

/*
 * #%L
 * de.metas.printing.base
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

import javax.annotation.concurrent.Immutable;

import de.metas.user.UserId;
import org.adempiere.util.lang.ObjectUtils;

import com.google.common.collect.ImmutableList;

/**
 * Use {@link IPrintingQueueBL#createPrintingQueueProcessingInfo(java.util.Properties, IPrintingQueueQuery)} to get your instance.
 * 
 * TODO:
 * <ul>
 * <li>add a field for the number of copies.
 * <li>if there are move members, please don't extend the constructor, but add a builder.
 * </ul>
 * 
 * @author ts
 *
 */
@Immutable
public class PrintingQueueProcessingInfo
{
	private final List<UserId> AD_User_ToPrint_IDs;
	private final UserId AD_User_PrintJob_ID;
	private final boolean createWithSpecificHostKey;

	public PrintingQueueProcessingInfo(
			final UserId aD_User_PrintJob_ID,
			final ImmutableList<UserId> aD_User_ToPrint_IDs,
			final boolean createWithSpecificHostKey)
	{
		this.AD_User_PrintJob_ID = aD_User_PrintJob_ID;
		this.AD_User_ToPrint_IDs = ImmutableList.<UserId> copyOf(aD_User_ToPrint_IDs);
		this.createWithSpecificHostKey = createWithSpecificHostKey;
	}

	/**
	 * Return the<code>AD_User_ID</code>s to be used for printing.<br>
	 * Notes:
	 * <ul>
	 * <li>the IDs are always ordered by their value
	 * <li>the list is never empty because that would mean "nothing to print" and therefore there would be no queue source instance to start with.
	 * </ul>
	 */
	public List<UserId> getAD_User_ToPrint_IDs()
	{
		return AD_User_ToPrint_IDs;
	}

	/**
	 * The user who shall be the printjob's AD_User_ID/contact
	 */
	public UserId getAD_User_PrintJob_ID()
	{
		return AD_User_PrintJob_ID;
	}

	/**
	 * Returns true if we want the system to create printjob instructions with a dedicated hostKey. 
	 * That means that even if the job is created for a certain user, it can only be printed by that suer if his/her client connects with the particular key.
	 */
	public boolean isCreateWithSpecificHostKey()
	{
		return createWithSpecificHostKey;
	}
	
	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
