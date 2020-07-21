package de.metas.vertical.cables;

import org.adempiere.mm.attributes.AttributeCode;

import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-webui-api
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

@UtilityClass
public final class CablesConstants
{
	/**
	 * Note: to activate this spring profile, you can add an AD_SysConfig with
	 * {@code de.metas.spring.profiles.activeN = de.metas.vertical.cables}.
	 *
	 * There is a template AD_Sysconfig record for further info.
	 */
	public static final String PROFILE = "de.metas.vertical.cables";

	public static final AttributeCode ATTRIBUTE_CableLength = AttributeCode.ofString("CableLength");
}
