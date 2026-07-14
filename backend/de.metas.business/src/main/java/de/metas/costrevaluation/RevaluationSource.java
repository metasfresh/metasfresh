package de.metas.costrevaluation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_CostRevaluation;

import javax.annotation.Nullable;
import java.util.Objects;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2024 metas GmbH
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
 * NOTE to developers: Please keep in sync with list reference "M_CostRevaluation RevaluationSource" {@code AD_Reference_ID=542117}
 */
@AllArgsConstructor
public enum RevaluationSource implements ReferenceListAwareEnum
{
	/** Cost is recalculated for this revaluation (default, existing behaviour). */
	Calculated(X_M_CostRevaluation.REVALUATIONSOURCE_Calculated),
	/** Cost is copied unchanged from the cost element selected under CopyFrom_M_CostElement_ID (value-neutral). */
	CopyFromCostElement(X_M_CostRevaluation.REVALUATIONSOURCE_CopyFromCostElement),
	;

	@Getter
	@NonNull private final String code;

	private static final ReferenceListAwareEnums.ValuesIndex<RevaluationSource> index = ReferenceListAwareEnums.index(values());

	@JsonCreator
	public static RevaluationSource ofCode(@NonNull final String code) {return index.ofCode(code);}

	@Nullable
	public static RevaluationSource ofNullableCode(@Nullable final String code) {return index.ofNullableCode(code);}

	@JsonValue
	public String toJson() {return code;}

	@Nullable
	public static String toCodeOrNull(@Nullable final RevaluationSource source) {return source != null ? source.getCode() : null;}

	public static boolean equals(@Nullable final RevaluationSource o1, @Nullable final RevaluationSource o2) {return Objects.equals(o1, o2);}

	public boolean isCopyFromCostElement() {return this == CopyFromCostElement;}
}
