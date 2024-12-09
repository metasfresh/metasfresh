<<<<<<< HEAD
package de.metas.ui.web.base.model;

import com.google.common.collect.ImmutableList;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
/*
 * #%L
 * de.metas.ui.web.base
 * %%
<<<<<<< HEAD
 * Copyright (C) 2017 metas GmbH
=======
 * Copyright (C) 2024 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
<<<<<<< HEAD
 * 
=======
 *
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

<<<<<<< HEAD
=======
package de.metas.ui.web.base.model;

import com.google.common.collect.ImmutableList;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
public interface I_T_WEBUI_ViewSelectionLine
{
	String Table_Name = "T_WEBUI_ViewSelectionLine";

	String COLUMNNAME_UUID = "UUID";

	/** Line ID / Included record ID */
	String COLUMNNAME_Line_ID = "Line_ID";

	//
	// Record IDs / Aggregate record IDs / Root record IDs
	// IMPORTANT: keep in sync with I_T_WEBUI_ViewSelection !!!
	String COLUMNNAME_IntKey1 = "IntKey1";
	String COLUMNNAME_IntKey2 = "IntKey2";
	String COLUMNNAME_IntKey3 = "IntKey3";
	String COLUMNNAME_IntKey4 = "IntKey4";
<<<<<<< HEAD
	ImmutableList<String> COLUMNNAME_IntKeys = ImmutableList.of(COLUMNNAME_IntKey1, COLUMNNAME_IntKey2, COLUMNNAME_IntKey3, COLUMNNAME_IntKey4);
=======
	String COLUMNNAME_IntKey5 = "IntKey5";
	String COLUMNNAME_IntKey6 = "IntKey6";
	String COLUMNNAME_IntKey7 = "IntKey7";
	String COLUMNNAME_IntKey8 = "IntKey8";
	String COLUMNNAME_IntKey9 = "IntKey9";
	String COLUMNNAME_IntKey10 = "IntKey10";
	String COLUMNNAME_IntKey11 = "IntKey11";
	String COLUMNNAME_IntKey12 = "IntKey12";
	String COLUMNNAME_IntKey13 = "IntKey13";
	String COLUMNNAME_IntKey14 = "IntKey14";
	String COLUMNNAME_IntKey15 = "IntKey15";
	String COLUMNNAME_IntKey16 = "IntKey16";
	String COLUMNNAME_IntKey17 = "IntKey17";
	String COLUMNNAME_IntKey18 = "IntKey18";
	String COLUMNNAME_IntKey19 = "IntKey19";
	String COLUMNNAME_IntKey20 = "IntKey20";
	ImmutableList<String> COLUMNNAME_IntKeys = ImmutableList.of(
			COLUMNNAME_IntKey1, COLUMNNAME_IntKey2, COLUMNNAME_IntKey3, COLUMNNAME_IntKey4, COLUMNNAME_IntKey5, COLUMNNAME_IntKey6, COLUMNNAME_IntKey7, COLUMNNAME_IntKey8, COLUMNNAME_IntKey9, COLUMNNAME_IntKey10,
			COLUMNNAME_IntKey11, COLUMNNAME_IntKey12, COLUMNNAME_IntKey13, COLUMNNAME_IntKey14, COLUMNNAME_IntKey15, COLUMNNAME_IntKey16, COLUMNNAME_IntKey17, COLUMNNAME_IntKey18, COLUMNNAME_IntKey19, COLUMNNAME_IntKey20
	);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	String COLUMNNAME_StringKey1 = "StringKey1";
	String COLUMNNAME_StringKey2 = "StringKey2";
	String COLUMNNAME_StringKey3 = "StringKey3";
<<<<<<< HEAD
	ImmutableList<String> COLUMNNAME_StringKeys = ImmutableList.of(COLUMNNAME_StringKey1, COLUMNNAME_StringKey2, COLUMNNAME_StringKey3);
=======
	String COLUMNNAME_StringKey4 = "StringKey4";
	String COLUMNNAME_StringKey5 = "StringKey5";
	String COLUMNNAME_StringKey6 = "StringKey6";
	String COLUMNNAME_StringKey7 = "StringKey7";
	String COLUMNNAME_StringKey8 = "StringKey8";
	String COLUMNNAME_StringKey9 = "StringKey9";
	String COLUMNNAME_StringKey10 = "StringKey10";
	ImmutableList<String> COLUMNNAME_StringKeys = ImmutableList.of(COLUMNNAME_StringKey1, COLUMNNAME_StringKey2, COLUMNNAME_StringKey3, COLUMNNAME_StringKey4, COLUMNNAME_StringKey5, COLUMNNAME_StringKey6, COLUMNNAME_StringKey7, COLUMNNAME_StringKey8, COLUMNNAME_StringKey9, COLUMNNAME_StringKey10);

	String COLUMNNAME_TimestampKey1 = "TimestampKey1";
	ImmutableList<String> COLUMNNAME_TimestampKeys = ImmutableList.of(COLUMNNAME_TimestampKey1);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
