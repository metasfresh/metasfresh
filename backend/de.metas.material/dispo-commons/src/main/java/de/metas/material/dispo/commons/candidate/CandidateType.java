package de.metas.material.dispo.commons.candidate;

import de.metas.material.dispo.model.X_MD_Candidate;
import de.metas.util.Check;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Please keep in sync with the values of {@link X_MD_Candidate#MD_CANDIDATE_TYPE_AD_Reference_ID}
 */
public enum CandidateType implements ReferenceListAwareEnum
{
	DEMAND(X_MD_Candidate.MD_CANDIDATE_TYPE_DEMAND),

	SUPPLY(X_MD_Candidate.MD_CANDIDATE_TYPE_SUPPLY),

	STOCK_UP(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK_UP),

	/**
	 * Might or might not be related to a {@link #SUPPLY}; at any rate it's attributesKey and/or timestamp are different from any existing record.
	 * <p>
	 * Example: you have a supply record for a material receipt schedule with AttributesKey=NONE; then the actual receipt has a different timestamp and ASI, but still belongs to the existing material receipt schedule.
	 */
	UNEXPECTED_INCREASE(X_MD_Candidate.MD_CANDIDATE_TYPE_UNEXPECTED_INCREASE),

	/**
	 * Analog to {@link #UNEXPECTED_INCREASE}.
	 * Might or might not be related to a {@link #DEMAND}; at any rate it's attributesKey and/or timestamp are different from any existing record
	 */
	UNEXPECTED_DECREASE(X_MD_Candidate.MD_CANDIDATE_TYPE_UNEXPECTED_DECREASE),

	INVENTORY_DOWN(X_MD_Candidate.MD_CANDIDATE_TYPE_INVENTORY_DOWN),

	INVENTORY_UP(X_MD_Candidate.MD_CANDIDATE_TYPE_INVENTORY_UP),

	/** TODO: remove this type; instead, "just" add an ATP column to candidate. */
	STOCK(X_MD_Candidate.MD_CANDIDATE_TYPE_STOCK),

	ATTRIBUTES_CHANGED_FROM(X_MD_Candidate.MD_CANDIDATE_TYPE_ATTRIBUTES_CHANGED_FROM),

	ATTRIBUTES_CHANGED_TO(X_MD_Candidate.MD_CANDIDATE_TYPE_ATTRIBUTES_CHANGED_TO);

	@Getter
	private final String code;

	CandidateType(final String code)
	{
		this.code = code;
	}

	public static CandidateType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ValuesIndex<CandidateType> index = ReferenceListAwareEnums.index(values());
}
