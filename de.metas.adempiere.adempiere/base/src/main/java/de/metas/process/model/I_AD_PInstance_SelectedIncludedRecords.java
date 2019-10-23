package de.metas.process.model;

import org.compiere.model.I_AD_PInstance;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Selected included rows of a {@link I_AD_PInstance#getAD_Table_ID()} / {@link I_AD_PInstance#getRecord_ID()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface I_AD_PInstance_SelectedIncludedRecords
{
	String Table_Name = "AD_PInstance_SelectedIncludedRecords";

	String COLUMNNAME_AD_PInstance_ID = "AD_PInstance_ID";
	String COLUMNNAME_AD_Table_ID = "AD_Table_ID";
	String COLUMNNAME_Record_ID = "Record_ID";
	String COLUMNNAME_SeqNo = "SeqNo";
}
