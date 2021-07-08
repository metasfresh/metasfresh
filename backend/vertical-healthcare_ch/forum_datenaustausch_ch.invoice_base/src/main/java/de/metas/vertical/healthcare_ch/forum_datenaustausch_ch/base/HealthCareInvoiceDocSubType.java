package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;

import javax.annotation.Nullable;
import java.util.Arrays;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public enum HealthCareInvoiceDocSubType
{
	/** "Eigenanteil" - invoice to the patient */
	EA("EA"),

	/** "Kanton" - invoice to one of switzerland's member states */
	KT("KT"),

	/** "Krankenversicherung" - invoice to a health insurance */
	KV("KV"),

	/** "Gemeinde" - invoice to a municipality */
	GM("GM");

	public static HealthCareInvoiceDocSubType ofCodeOrNull(@Nullable final String code)
	{
		final HealthCareInvoiceDocSubType type = typesByCode.get(code);
		return type;
	}

	private HealthCareInvoiceDocSubType(String code)
	{
		this.code = code;
	}

	@Getter
	String code;

	public String getPrefix()
	{
		return code + "_";
	}

	private static final ImmutableMap<String, HealthCareInvoiceDocSubType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), HealthCareInvoiceDocSubType::getCode);
}
