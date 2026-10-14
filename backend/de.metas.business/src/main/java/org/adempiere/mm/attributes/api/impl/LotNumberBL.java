package org.adempiere.mm.attributes.api.impl;

import de.metas.adempiere.model.IPOReferenceAware;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.mm.attributes.api.ILotNumberBL;
import org.adempiere.mm.attributes.api.ILotNumberDateAttributeDAO;
import org.adempiere.mm.attributes.api.LotNoContext;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.compiere.util.TimeUtil;
import org.eevolution.api.PPOrderId;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class LotNumberBL implements ILotNumberBL
{

	@Override
	public String calculateLotNumber(final Date date)
	{
		final StringBuilder lotNumber = new StringBuilder();

		final int weekNumber = TimeUtil.getWeekNumber(date);

		if (weekNumber < 10)
		{
			lotNumber.append(0);
		}

		lotNumber.append(weekNumber);

		final int dayOfWeek = TimeUtil.getDayOfWeek(date);

		lotNumber.append(dayOfWeek);

		return lotNumber.toString();
	}

	@Override
	public Optional<String> getAndIncrementLotNo(@NonNull final LotNoContext context)
	{
		final IDocumentNoBuilderFactory documentNoFactory = Services.get(IDocumentNoBuilderFactory.class);

		// A CustomSequenceNoProvider (opt-in, configured on the sequence) may need the PP_Order behind this lot number.
		// Expose it under the standard Record_ID context key. Without a PP_Order the context carries no Record_ID: a
		// provider such as DBFunctionSequenceNoProvider then reports isApplicable=false, which makes
		// DocumentNoBuilder.getSequenceNoToUse() throw unconditionally (independent of failOnError) - it does NOT
		// silently fall back. Sequences with no provider are unaffected (empty context == today's behaviour).
		final PPOrderId ppOrderId = context.getPpOrderId();
		final Evaluatee evaluationContext = ppOrderId != null
				? Evaluatees.ofSingleton(IPOReferenceAware.COLUMNNAME_Record_ID, ppOrderId.getRepoId())
				: Evaluatees.empty();

		final String lotNo = documentNoFactory.forSequenceId(context.getSequenceId())
				.setFailOnError(true)
				.setClientId(context.getClientId())
				.setEvaluationContext(evaluationContext)
				.build();

		return lotNo != null && !Objects.equals(lotNo, IDocumentNoBuilder.NO_DOCUMENTNO)
				? Optional.of(lotNo)
				: Optional.empty();
	}

	@Override
	public String getLotNumberAttributeValueOrNull(@NonNull final I_M_AttributeSetInstance asi)
	{
		final AttributeId lotNumberAttrId = Services.get(ILotNumberDateAttributeDAO.class).getLotNumberAttributeId();
		if (lotNumberAttrId == null)
		{
			return null;
		}

		final IAttributeSetInstanceBL asiBL = Services.get(IAttributeSetInstanceBL.class);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(asi.getM_AttributeSetInstance_ID());
		final I_M_AttributeInstance lotNumberAI = asiBL.getAttributeInstance(asiId, lotNumberAttrId);

		if (lotNumberAI == null)
		{
			return null;
		}

		return lotNumberAI.getValue();
	}

}
