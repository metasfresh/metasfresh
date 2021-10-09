package org.adempiere.process.rpl;

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


public final class RPL_Constants
{
	private RPL_Constants()
	{
	}

	public static final String XML_ATTR_AD_Client_Value = "AD_Client_Value";
	public static final String XML_ATTR_Version = "Version";

	/**
	 * Attribute can be used to specifiy the caller's metasfresh AD_Session_ID
	 */
	public static final String XML_ATTR_AD_SESSION_ID = "AD_Session_ID"; // 03132

	public static final String XML_ATTR_REPLICATION_EVENT = "ReplicationEvent";
	public static final String XML_ATTR_REPLICATION_TYPE = "ReplicationType";
	public static final String XML_ATTR_REPLICATION_MODE = "ReplicationMode";

	public static final String XML_ATTR_SEQUENCE_NO = "SequenceNo"; // attribute was originally added due to 02596

	public static final String XML_ATTR_REPLICATION_TrxName = "TrxName"; // 06231
}
