package de.metas.shipper.gateway.derkurier.misc;

import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.annotations.VisibleForTesting;

import de.metas.document.documentNo.IDocumentNoBuilder;
import de.metas.document.documentNo.IDocumentNoBuilderFactory;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public class ParcelNumberGenerator
{
	private final IDocumentNoBuilder documentNoBuilder;

	public ParcelNumberGenerator(@NonNull final String sequenceName)
	{
		final int adClientId = Env.getAD_Client_ID(Env.getCtx());
		final int adOrgId = Env.getAD_Org_ID(Env.getCtx());

		this.documentNoBuilder = Services.get(IDocumentNoBuilderFactory.class)
				.createDocumentNoBuilder()
				.setAD_Client_ID(adClientId)
				.setDocumentSequenceInfoByTableName(computeAndAppendCheckDigit(sequenceName), adClientId, adOrgId)
				.setFailOnError(true);
	}

	public String getNextParcelNumber()
	{
		final String parcelNumberWithoutCheckDigit = documentNoBuilder.build();
		return computeAndAppendCheckDigit(parcelNumberWithoutCheckDigit);
	}

	@VisibleForTesting
	String computeAndAppendCheckDigit(@NonNull final String parcelNumberWithoutCheckDigit)
	{
		// TODO https://github.com/metasfresh/metasfresh/issues/3991
		return parcelNumberWithoutCheckDigit;
	}
}
