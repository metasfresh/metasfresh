package de.metas.acct.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_Fact_Acct;

/*
 * #%L
 * de.metas.acct.base
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

@AllArgsConstructor
public enum PostingType implements ReferenceListAwareEnum
{
	Actual(X_Fact_Acct.POSTINGTYPE_Actual), //
	Budget(X_Fact_Acct.POSTINGTYPE_Budget), //
	Commitment(X_Fact_Acct.POSTINGTYPE_Commitment), //
	Reservation(X_Fact_Acct.POSTINGTYPE_Reservation), //
	Statistical(X_Fact_Acct.POSTINGTYPE_Statistical), //
	ActualYearEnd(X_Fact_Acct.POSTINGTYPE_ActualYearEnd) //
	;

	@Getter
	private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<PostingType> index = ReferenceListAwareEnums.index(values());

	public static PostingType ofCode(@NonNull final String code) {return index.ofCode(code);}
}
