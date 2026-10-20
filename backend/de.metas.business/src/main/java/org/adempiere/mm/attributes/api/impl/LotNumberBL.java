package org.adempiere.mm.attributes.api.impl;

import de.metas.document.DocumentSequenceInfo;
import de.metas.document.IDocumentSequenceDAO;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoParts;
import de.metas.document.sequenceno.CustomSequenceNoProvider;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
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
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.LocalDate;
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
	private static final Logger logger = LogManager.getLogger(LotNumberBL.class);

	private final IDocumentSequenceDAO documentSequenceDAO = Services.get(IDocumentSequenceDAO.class);

	/**
	 * Lazy because the HU singletons (e.g. HUPPOrderBL) create LotNumberBL eagerly, before the Spring bean IDocumentNoBuilderFactory is registered.
	 */
	@Nullable private IDocumentNoBuilderFactory _documentNoBuilderFactory;

	private IDocumentNoBuilderFactory documentNoBuilderFactory()
	{
		IDocumentNoBuilderFactory result = _documentNoBuilderFactory;
		if (result == null)
		{
			result = _documentNoBuilderFactory = Services.get(IDocumentNoBuilderFactory.class);
		}
		return result;
	}

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
		final String lotNo = documentNoBuilderFactory().forSequenceId(context.getSequenceId())
				.setFailOnError(true)
				.setClientId(context.getClientId())
				.setEvaluationContext(createEvaluationContext(context))
				.build();

		return lotNo != null && !Objects.equals(lotNo, IDocumentNoBuilder.NO_DOCUMENTNO)
				? Optional.of(lotNo)
				: Optional.empty();
	}

	@Override
	public Optional<String> previewLotNo(@NonNull final LotNoContext context)
	{
		final DocumentSequenceInfo sequenceInfo = documentSequenceDAO.retriveDocumentSequenceInfo(context.getSequenceId());
		if (!isSequenceNoIndependentOfIncrement(sequenceInfo))
		{
			return Optional.empty();
		}

		final DocumentNoParts lotNoParts;
		try
		{
			lotNoParts = documentNoBuilderFactory().forSequenceId(context.getSequenceId())
					.setClientId(context.getClientId())
					.setEvaluationContext(createEvaluationContext(context))
					.setUsePreliminaryDocumentNo(true) // read the incremental number instead of consuming it
					.buildParts();
		}
		catch (final AdempiereException | IllegalArgumentException ex)
		{
			// A misconfigured sequence (e.g. missing or invalid DateSequenceProvider date format) shall only mean "no preview";
			// getAndIncrementLotNo still fails when the lot number is actually generated.
			// One line without stack trace, because this is called on every render of the receipt.
			logger.warn("Failed previewing the lot number for {}; returning empty: {}", context, ex.getLocalizedMessage());
			return Optional.empty();
		}

		return lotNoParts != null
				? Optional.of(lotNoParts.getPrefix() + lotNoParts.getSequenceNumber() + lotNoParts.getSuffix())
				: Optional.empty();
	}

	private static boolean isSequenceNoIndependentOfIncrement(@Nullable final DocumentSequenceInfo sequenceInfo)
	{
		final CustomSequenceNoProvider customSequenceNoProvider = sequenceInfo != null ? sequenceInfo.getCustomSequenceNoProvider() : null;
		return customSequenceNoProvider != null && customSequenceNoProvider.isSequenceNoIndependentOfIncrement(sequenceInfo);
	}

	private Evaluatee createEvaluationContext(@NonNull final LotNoContext context)
	{
		final LocalDate date = context.getDate();
		if (date == null)
		{
			return Evaluatees.empty();
		}

		final DocumentSequenceInfo sequenceInfo = documentSequenceDAO.retriveDocumentSequenceInfo(context.getSequenceId());
		final String dateColumn = sequenceInfo != null ? sequenceInfo.getDateColumn() : null;
		if (Check.isBlank(dateColumn))
		{
			return Evaluatees.empty();
		}

		return Evaluatees.ofSingleton(dateColumn, TimeUtil.asTimestamp(date));
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
