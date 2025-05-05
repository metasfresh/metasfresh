/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.flatrate;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_CallOrder;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_Commission;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_InterimInvoice;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_LicenseFee;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_MarginCommission;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_MediatedCommission;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_ModularContract;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_Procurement;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_QualityBasedInvoicing;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable;
import static de.metas.contracts.model.X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription;

@Getter
public enum TypeConditions implements ReferenceListAwareEnum
{
	FLAT_FEE(TYPE_CONDITIONS_FlatFee),
	HOLDING_FEE(TYPE_CONDITIONS_HoldingFee),
	SUBSCRIPTION(TYPE_CONDITIONS_Subscription),
	REFUNDABLE(TYPE_CONDITIONS_Refundable),
	QUALITY_BASED_INVOICING(TYPE_CONDITIONS_QualityBasedInvoicing),
	PROCUREMENT(TYPE_CONDITIONS_Procurement),
	REFUND(TYPE_CONDITIONS_Refund),
	COMMISSION(TYPE_CONDITIONS_Commission),
	MEDIATED_COMMISSION(TYPE_CONDITIONS_MediatedCommission),
	MARGIN_COMMISSION(TYPE_CONDITIONS_MarginCommission),
	LICENSE_FEE(TYPE_CONDITIONS_LicenseFee),
	CALL_ORDER(TYPE_CONDITIONS_CallOrder),

	INTERIM_INVOICE(TYPE_CONDITIONS_InterimInvoice),
	MODULAR_CONTRACT(TYPE_CONDITIONS_ModularContract);

	@Getter
	private final String code;

	TypeConditions(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static TypeConditions ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static TypeConditions ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	private static final ReferenceListAwareEnums.ValuesIndex<TypeConditions> typesByCode = ReferenceListAwareEnums.index(values());

	public boolean isModularContractType()
	{
		return MODULAR_CONTRACT.equals(this);
	}

	public boolean isInterimContractType()
	{
		return INTERIM_INVOICE.equals(this);
	}

	public boolean isModularOrInterim()
	{
		return isModularContractType() || isInterimContractType();
	}

	public boolean equalsByCode(@Nullable final String code)
	{
		return Objects.equals(this.code, code);
	}
}
