package de.metas.inoutcandidate.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public interface I_M_ShipmentSchedule_Lock
{
	String Table_Name = "M_ShipmentSchedule_Lock";

	String COLUMNNAME_M_ShipmentSchedule_ID = "M_ShipmentSchedule_ID";
	String COLUMNNAME_LockedBy_User_ID = "LockedBy_User_ID";
	String COLUMNNAME_LockType = "LockType";
	String COLUMNNAME_Created = "Created";
}
