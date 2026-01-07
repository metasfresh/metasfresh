/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.printing.api;

import com.google.common.collect.ImmutableList;
import de.metas.user.UserId;
import lombok.Getter;
import org.adempiere.util.lang.ObjectUtils;

import javax.annotation.concurrent.Immutable;
import java.util.List;

/**
 * Use {@link IPrintingQueueBL#createPrintingQueueProcessingInfo(java.util.Properties, IPrintingQueueQuery)} to get your instance.
 * 
 * TODO:
 * <ul>
 * <li>add a field for the number of copies.
 * <li>if there are new members, please don't extend the constructor but add a builder.
 * </ul>
 * 
 * @author ts
 *
 */
@Getter
@Immutable
public class PrintingQueueProcessingInfo
{
	/**
	 * -- GETTER --
	 *  Return the<code>AD_User_ID</code>s to be used for printing.<br>
	 *  Notes:
	 *  <ul>
	 *  <li>the IDs are always ordered by their value
	 *  <li>the list is never empty because that would mean "nothing to print" and therefore there would be no queue source instance to start with.
	 *  </ul>
	 */
	private final List<UserId> AD_User_ToPrint_IDs;
	/**
	 * -- GETTER --
	 *  The user who shall be the printjob's AD_User_ID/contact
	 */
	private final UserId AD_User_PrintJob_ID;
	/**
	 * -- GETTER --
	 *  Returns true if we want the system to create printjob instructions with a dedicated hostKey. 
	 *  That means that even if the job is created for a certain user, it can only be printed by that user if his/her client connects with the particular key.
	 */
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

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}
}
