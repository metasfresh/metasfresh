/*
 * #%L
 * de-metas-camel-sap-file-import
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.sap.model.bpartner;

import lombok.NonNull;
import lombok.Value;

@Value(staticConstructor = "of")
public class PartnerCode
{
	private final static String GROUP_SUFFIX = "00";

	@NonNull
	String rawPartnerCode;

	public boolean matchesGroup(@NonNull final PartnerCode partnerCode)
	{
		return getPartnerGroupCode().equals(partnerCode.getPartnerGroupCode());
	}

	@NonNull
	public String getPartnerCode()
	{
		return getPartnerGroupCode() + GROUP_SUFFIX;
	}

	@NonNull
	private String getPartnerGroupCode()
	{
		return rawPartnerCode.substring(0, rawPartnerCode.length() - 2);
	}
}
