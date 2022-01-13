/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.bpartner;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_BP_SupplierApproval;

import javax.annotation.Nullable;
import java.util.Arrays;

public enum SupplierApproval implements ReferenceListAwareEnum
{
	OneYear(X_C_BP_SupplierApproval.SUPPLIERAPPROVAL_1Year), // C
	TwoYears(X_C_BP_SupplierApproval.SUPPLIERAPPROVAL_2Years), // B
	ThreeYears(X_C_BP_SupplierApproval.SUPPLIERAPPROVAL_3Years), // A
	;

	@Getter
	private final String code;

	SupplierApproval(@NonNull final String code)
	{
		this.code = code;
	}

	public static SupplierApproval ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}
	
	public static SupplierApproval ofCode(@NonNull final String code)
	{
		SupplierApproval type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + SupplierApproval.class + " found for code: " + code);
		}
		return type;
	}

	public static String toCodeOrNull(final SupplierApproval type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, SupplierApproval> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), SupplierApproval::getCode);

	public boolean isOneYear()
	{
		return this == OneYear;
	}

	public boolean isTwoYears()
	{
		return this == TwoYears;
	}

	public boolean isThreeYears()
	{
		return this == ThreeYears;
	}

}
