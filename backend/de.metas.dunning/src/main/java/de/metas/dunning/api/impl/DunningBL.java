package de.metas.dunning.api.impl;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.slf4j.Logger;

import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunnableSourceFactory;
import de.metas.dunning.api.IDunningBL;
import de.metas.dunning.api.IDunningCandidateProducer;
import de.metas.dunning.api.IDunningCandidateProducerFactory;
import de.metas.dunning.api.IDunningConfig;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.IDunningDAO;
import de.metas.dunning.api.IDunningProducer;
import de.metas.dunning.exception.DunningException;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunnableSource;
import de.metas.dunning.spi.IDunningCandidateSource;
import de.metas.dunning.spi.IDunningConfigurator;
import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.api.ShipmentConstraintCreateRequest;
import de.metas.logging.LogManager;
import lombok.NonNull;

public class DunningBL implements IDunningBL
{
	private final Logger logger = LogManager.getLogger(getClass());

	private ReentrantLock configLock = new ReentrantLock();

	/**
	 * Dunning configurator (if any)
	 */
	private IDunningConfigurator _dunningConfigurator;

	/**
	 * Dunning configuration
	 *
	 * NOTE: please always access it via {@link #getDunningConfig()} and never directly
	 */
	private IDunningConfig _config;

	@Override
	public IDunningConfig getDunningConfig()
	{
		configLock.lock();
		try
		{
			if (_config != null)
			{
				return _config;
			}

			if (_dunningConfigurator != null)
			{
				this._config = _dunningConfigurator.configure(new DunningConfig());
				Check.assumeNotNull(_config, "Configurator {} shall return a not null config", _dunningConfigurator);
			}
			else
			{
				this._config = new DunningConfig();
			}
		}
		finally
		{
			configLock.unlock();
		}

		return _config;
	}

	@Override
	public void setDunningConfigurator(final IDunningConfigurator configurator)
	{
		configLock.lock();
		try
		{
			if (_config != null)
			{
				// logger.warn("There is an already configured dunning config. Reseting it.");
				throw new DunningException("Cannot set an configurator when there is an already configured dunning config: " + _config);
			}

			_config = null;
			_dunningConfigurator = configurator;
		}
		finally
		{
			configLock.unlock();
		}
	}

	@Override
	public IDunningContext createDunningContext(final Properties ctx, final I_C_DunningLevel dunningLevel, final Date dunningDate, final String trxName)
	{
		final ITrxRunConfig defaultConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		return createDunningContext(ctx, dunningLevel, dunningDate, defaultConfig, trxName);
	}

	@Override
	public IDunningContext createDunningContext(final Properties ctx, final I_C_DunningLevel dunningLevel, final Date dunningDate, final ITrxRunConfig trxRunnerConfig, final String trxName)
	{
		final IDunningConfig config = getDunningConfig();
		final IDunningContext context = new DunningContext(ctx, config, dunningLevel, dunningDate, trxRunnerConfig, trxName);
		return context;
	}

	@Override
	public IDunningContext createDunningContext(final IDunningContext context, final String trxName)
	{
		return new DunningContext(context, trxName);
	}

	@Override
	public void validate(final I_C_Dunning_Candidate candidate)
	{
		Check.assume(candidate.isActive(), "Inactive dunning candidates are not allowed");

		Check.assume(candidate.isProcessed() || !candidate.isDunningDocProcessed(),
				"Documents needs to be first Processed and the DunningDocProcessed");

		if (candidate.isDunningDocProcessed())
		{
			Check.assumeNotNull(candidate.getDunningDateEffective(), "DunningDateEffective shall not be null when is DunningDocProcessed");
		}
	}

	@Override
	public int createDunningCandidates(final IDunningContext context)
	{
		final IDunningConfig config = context.getDunningConfig();
		final IDunnableSourceFactory sourceFactory = config.getDunnableSourceFactory();
		final IDunningCandidateProducerFactory candidateProducerFactory = config.getDunningCandidateProducerFactory();

		int countAll = 0;
		int countCreated = 0;

		final List<IDunnableSource> sources = sourceFactory.getSources(context);
		if (sources.isEmpty())
		{
			throw new DunningException("No " + IDunnableSource.class + "s were configured in " + config);
		}

		for (final IDunnableSource source : sources)
		{

			final Iterator<IDunnableDoc> dunnableDocs = source.iterator(context);
			try
			{
				while (dunnableDocs.hasNext())
				{
					countAll++;

					final IDunnableDoc sourceDoc = dunnableDocs.next();
					final IDunningCandidateProducer candidateProducer = candidateProducerFactory.getDunningCandidateProducer(sourceDoc);
					final I_C_Dunning_Candidate candidate = candidateProducer.createDunningCandidate(context, sourceDoc);
					if (candidate != null)
					{
						countCreated++;
					}
				}
			}
			finally
			{
				IteratorUtils.close(dunnableDocs);
			}
		}

		logger.info("Created {} from {} records evaluated", new Object[] { countCreated, countAll });

		return countCreated;
	}

	@Override
	public void processCandidates(final IDunningContext context)
	{
		final IDunningConfig config = context.getDunningConfig();

		final IDunningCandidateSource candidates = config.createDunningCandidateSource();
		candidates.setDunningContext(context);

		processCandidates(context, candidates);
	}

	@Override
	public void processCandidates(final IDunningContext context, final IDunningCandidateSource candidates)
	{
		Check.assumeNotNull(context, "context not null");
		Check.assumeNotNull(candidates, "candidates source not null");

		final IDunningConfig config = context.getDunningConfig();

		final IDunningProducer dunningProducer = config.createDunningProducer();
		dunningProducer.setDunningContext(context);

		for (final I_C_Dunning_Candidate candidate : candidates)
		{
			// 03663 : Check if the grace date isn't after the dunning date
			if (!isExpired(candidate, candidate.getDunningGrace()))
			{
				dunningProducer.addCandidate(candidate);
			}
		}

		dunningProducer.finish();
	}

	@Override
	public List<I_C_DunningLevel> getPreviousLevels(final I_C_DunningLevel level)
	{
		// refactor of org.compiere.model.MDunningLevel.getPreviousLevels()

		// NOTE: Only DaysAfterDue shall be considered when checking previous levels and not DaysAfterDue+DaysBetweenDunnings.

		final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);

		final I_C_Dunning dunning = level.getC_Dunning();

		final int totalDays = level.getDaysAfterDue().intValue();

		final List<I_C_DunningLevel> result = new ArrayList<>();
		for (final I_C_DunningLevel currentLevel : dunningDAO.retrieveDunningLevels(dunning))
		{
			if (currentLevel.getC_DunningLevel_ID() == level.getC_DunningLevel_ID())
			{
				continue;
			}

			final int currentTotalDays = currentLevel.getDaysAfterDue().intValue();

			if (currentTotalDays >= totalDays)
			{
				continue;
			}

			result.add(currentLevel);
		}

		return result;
	}

	@Override
	public void processDunningDoc(
			@NonNull final IDunningContext context,
			@NonNull final I_C_DunningDoc dunningDoc)
	{
		if (dunningDoc.isProcessed())
		{
			throw new DunningException("@Processed@=@Y@");
		}

		final IDunningDAO dao = Services.get(IDunningDAO.class);

		final List<I_C_DunningDoc_Line> lines = dao.retrieveDunningDocLines(context, dunningDoc);

		for (final I_C_DunningDoc_Line line : lines)
		{
			for (final I_C_DunningDoc_Line_Source source : dao.retrieveDunningDocLineSources(context, line))
			{
				processSourceAndItsCandidates(dunningDoc, source);
			}

			line.setProcessed(true);
			InterfaceWrapperHelper.save(line);
		}

		//
		// Delivery stop (https://github.com/metasfresh/metasfresh/issues/2499)
		makeDeliveryStopIfNeeded(dunningDoc);

		dunningDoc.setProcessed(true);
		dao.save(dunningDoc);
	}

	private void processSourceAndItsCandidates(
			@NonNull final I_C_DunningDoc dunningDoc,
			@NonNull final I_C_DunningDoc_Line_Source source)
	{
		final IDunningDAO dao = Services.get(IDunningDAO.class);

		final I_C_Dunning_Candidate candidate = source.getC_Dunning_Candidate();
		candidate.setProcessed(true); // make sure the Processed flag is set
		candidate.setIsDunningDocProcessed(true); // IsDunningDocProcessed
		candidate.setDunningDateEffective(dunningDoc.getDunningDate());
		dao.save(candidate);


		source.setProcessed(true);
		InterfaceWrapperHelper.save(source);
	}

	private void makeDeliveryStopIfNeeded(@NonNull final I_C_DunningDoc dunningDoc)
	{
		final org.compiere.model.I_C_DunningLevel dunningLevel = dunningDoc.getC_DunningLevel();
		if (!dunningLevel.isDeliveryStop())
		{
			return;
		}

		final IShipmentConstraintsBL shipmentConstraintsBL = Services.get(IShipmentConstraintsBL.class);
		shipmentConstraintsBL.createConstraint(ShipmentConstraintCreateRequest.builder()
				.billPartnerId(dunningDoc.getC_BPartner_ID())
				.sourceDocRef(TableRecordReference.of(dunningDoc))
				.deliveryStop(true)
				.build());

	}

	@Override
	public String getSummary(final I_C_Dunning_Candidate candidate)
	{
		if (candidate == null)
		{
			return null;
		}

		return "#" + candidate.getC_Dunning_Candidate_ID();
	}

	@Override
	public boolean isExpired(final I_C_Dunning_Candidate candidate, final Timestamp dunningGraceDate)
	{
		Check.assumeNotNull(candidate, "candidate not null");

		if (candidate.isProcessed())
		{
			// candidate already processed => not expired
			return false;
		}

		if (dunningGraceDate == null)
		{
			// no dunning grace date => not expired
			return false;
		}

		final Timestamp dunningDate = candidate.getDunningDate();
		if (dunningDate.compareTo(dunningGraceDate) >= 0)
		{
			// DunningDate >= DunningGrace => candidate is perfectly valid. It's date is after dunningGrace date
			return false;
		}

		// DunningDate < DunningGrace => candidate is no longer valid
		return true;
	}

	@Override
	public I_C_Dunning_Candidate getLastLevelCandidate(final List<I_C_Dunning_Candidate> candidates)
	{
		Check.errorIf(candidates.isEmpty(), "Error: No candidates selected.");

		I_C_Dunning_Candidate result = candidates.get(0);
		BigDecimal maxDaysAfterDue = result.getC_DunningLevel().getDaysAfterDue();

		for (final I_C_Dunning_Candidate candidate : candidates)
		{
			if (maxDaysAfterDue.compareTo(candidate.getC_DunningLevel().getDaysAfterDue()) < 0)
			{
				result = candidate;
				maxDaysAfterDue = candidate.getC_DunningLevel().getDaysAfterDue();
			}
		}
		return result;
	}
}
