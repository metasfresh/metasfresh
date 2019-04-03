package org.adempiere.mm.attributes.api.impl;

import org.adempiere.mm.attributes.api.ISerialNoBL;
import org.adempiere.mm.attributes.api.SerialNoContext;
import org.compiere.util.Evaluatees;

import de.metas.document.sequence.DocSequenceId;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

public class SerialNoBL implements ISerialNoBL
{
	public String getAndIncrementSerialNo(
			@NonNull final DocSequenceId sequenceId,
			@NonNull final SerialNoContext context)
	{
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		return documentNoFactory.forSequenceId(sequenceId)
				.setFailOnError(true)
				.setClientId(context.getClientId())
				.setEvaluationContext(Evaluatees.mapBuilder()
						.put("ProductNo", context.getProductNo())
						.build())
				.build();
	}
}
