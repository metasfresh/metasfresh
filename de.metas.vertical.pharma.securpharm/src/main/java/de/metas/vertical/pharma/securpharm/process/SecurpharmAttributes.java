package de.metas.vertical.pharma.securpharm.process;

import java.util.Objects;

import de.metas.util.StringUtils;
import de.metas.vertical.pharma.securpharm.attribute.SecurPharmAttributesStatus;
import de.metas.vertical.pharma.securpharm.model.schema.ExpirationDate;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.securpharm
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@Value class SecurpharmAttributes
{
	@NonNull
	SecurPharmAttributesStatus status;

	ExpirationDate bestBeforeDate;
	String serialNo;
	String lotNo;

	@Builder
	private SecurpharmAttributes(
			@NonNull final SecurPharmAttributesStatus status,
			final ExpirationDate bestBeforeDate,
			final String serialNo,
			final String lotNo)
	{
		this.status = status;
		this.bestBeforeDate = bestBeforeDate;
		this.serialNo = StringUtils.trimBlankToNull(serialNo);
		this.lotNo = StringUtils.trimBlankToNull(lotNo);
	}

	public boolean canAssignFrom(@NonNull final SecurpharmAttributes other)
	{
		if (other.getStatus().isUnknown())
		{
			return false;
		}

		switch (status)
		{
			case UNKNOW:
				return true;
			case OK:
			case FRAUD:
			case ERROR:
			default:
				return equalsIgnoringStatus(other);
		}

	}

	public boolean equalsIgnoringStatus(final SecurpharmAttributes other)
	{
		if (other == null)
		{
			return false;
		}

		return Objects.equals(bestBeforeDate, other.bestBeforeDate)
				&& Objects.equals(serialNo, other.serialNo)
				&& Objects.equals(lotNo, other.lotNo);
	}
}