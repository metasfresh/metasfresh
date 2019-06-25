package de.metas.document.engine;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public enum DocStatus implements ReferenceListAwareEnum
{
	Drafted(IDocument.STATUS_Drafted), //
	Completed(IDocument.STATUS_Completed),//
	Approved(IDocument.STATUS_Approved),//
	Invalid(IDocument.STATUS_Invalid),//
	NotApproved(IDocument.STATUS_NotApproved),//
	Voided(IDocument.STATUS_Voided),//
	Reversed(IDocument.STATUS_Reversed),//
	Closed(IDocument.STATUS_Closed),//
	Unknown(IDocument.STATUS_Unknown),//
	InProgress(IDocument.STATUS_InProgress),//
	WaitingPayment(IDocument.STATUS_WaitingPayment),//
	WaitingConfirmation(IDocument.STATUS_WaitingConfirmation) //
	;

	@Getter
	private final String code;

	DocStatus(final String code)
	{
		this.code = code;
	}

	public static DocStatus ofCode(@NonNull final String code)
	{
		final DocStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DocStatus.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, DocStatus> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public boolean isReversedOrVoided()
	{
		return this == DocStatus.Reversed || this == DocStatus.Voided;
	}

	public boolean isCompletedOrClosedOrReversed()
	{
		return this == DocStatus.Completed || this == DocStatus.Reversed || this == DocStatus.Voided;
	}

}
