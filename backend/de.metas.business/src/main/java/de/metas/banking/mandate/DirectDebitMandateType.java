/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.banking.mandate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.X_C_BP_BankAccount_DirectDebitMandate;


/**
 * SEPA direct debit scheme of a mandate (reference list {@code C_BP_BankAccount_DirectDebitMandate.MandateType}).
 */
@RequiredArgsConstructor
@Getter
public enum DirectDebitMandateType implements ReferenceListAwareEnum
{
	CORE(X_C_BP_BankAccount_DirectDebitMandate.MANDATETYPE_CORE),
	B2B(X_C_BP_BankAccount_DirectDebitMandate.MANDATETYPE_B2B),
	;

	@NonNull private static final ReferenceListAwareEnums.ValuesIndex<DirectDebitMandateType> index = ReferenceListAwareEnums.index(values());

	@NonNull private final String code;

	@JsonCreator
	@NonNull
	public static DirectDebitMandateType ofCode(@NonNull final String code) {return index.ofCode(code);}

	@JsonValue
	public String toJson() {return code;}
}
