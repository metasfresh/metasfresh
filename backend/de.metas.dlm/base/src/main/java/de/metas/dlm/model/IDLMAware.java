package de.metas.dlm.model;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public interface IDLMAware
{
	/**
	 * No getter for I_DLM_Partition records, because they might or might not exists at any given time.
	 */
	String COLUMNNAME_DLM_Partition_ID = "DLM_Partition_ID";

	int getDLM_Partition_ID();

	void setDLM_Partition_ID(int DLM_Partition_ID);

	String COLUMNNAME_DLM_Level = "DLM_Level";

	int getDLM_Level();
	
	void setDLM_Level(int DLM_Level);
}
