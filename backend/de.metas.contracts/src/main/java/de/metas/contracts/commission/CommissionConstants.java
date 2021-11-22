package de.metas.contracts.commission;

import de.metas.contracts.ConditionsId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.time.Duration;

import static org.compiere.model.X_C_DocType.DOCBASETYPE_APInvoice;
import static org.compiere.model.X_C_DocType.DOCBASETYPE_ARInvoice;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class CommissionConstants
{
	public static final ConditionsId FLATRATE_CONDITION_0_COMMISSION_ID = ConditionsId.ofRepoId(540047);
	public static final Duration NO_COMMISSION_AGREEMENT_DEFAULT_CONTRACT_DURATION = Duration.ofDays(365);

	private static final String COMMISSION_DOC_SUBTYPE_VALUE = "CA";
	private static final String MEDIATED_COMMISSION_DOC_SUBTYPE_VALUE = "RD";

	@AllArgsConstructor
	@Getter
	public enum CommissionDocType
	{
		PURCHASE_COMMISSION(DOCBASETYPE_APInvoice, COMMISSION_DOC_SUBTYPE_VALUE),
		SALES_COMMISSION(DOCBASETYPE_ARInvoice, MEDIATED_COMMISSION_DOC_SUBTYPE_VALUE);

		private final String docBaseType;
		private final String docSubType;
	}
}
