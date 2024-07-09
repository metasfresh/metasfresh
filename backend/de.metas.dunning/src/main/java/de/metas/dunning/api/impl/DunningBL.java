package de.metas.dunning.api.impl;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.DunningLevelId;
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
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.dunning.spi.IDunnableSource;
import de.metas.dunning.spi.IDunningCandidateSource;
import de.metas.dunning.spi.IDunningConfigurator;
import de.metas.email.EMailCustomType;
import de.metas.i18n.Language;
import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.api.ShipmentConstraintCreateRequest;
import de.metas.letter.BoilerPlate;
import de.metas.letter.BoilerPlateRepository;
import de.metas.letter.BoilerPlateWithLineId;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.getPO;

public class DunningBL implements IDunningBL
{

	public static final String MASS_GENERATE_LINES_EVALUATEE_PARAM = "Mass_Generate_Lines";
	private final Logger logger = LogManager.getLogger(getClass());

	private final ReentrantLock configLock = new ReentrantLock();

	/**
	 * Dunning configurator (if any)
	 */
	private IDunningConfigurator _dunningConfigurator;

	/**
	 * Dunning configuration
	 * <p>
	 * NOTE: please always access it via {@link #getDunningConfig()} and never directly
	 */
	private IDunningConfig _config;
	private final INotificationBL notificationBL = Services.get(INotificationBL.class);
	private final IDunningDAO dunningDAO = Services.get(IDunningDAO.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final BoilerPlateRepository boilerPlateRepository = SpringContextHolder.instance.getBean(BoilerPlateRepository.class);

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

	@NonNull
	@Override
	public IDunningContext createDunningContext(
			final Properties ctx,
			final I_C_DunningLevel dunningLevel,
			final LocalDateAndOrgId dunningDate,
			final String trxName,
			@Nullable final RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery)
	{
		final ITrxRunConfig defaultConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		return createDunningContext(ctx, dunningLevel, dunningDate, defaultConfig, trxName, recomputeDunningCandidatesQuery);
	}

	@NonNull
	@Override
	public IDunningContext createDunningContext(
			final Properties ctx,
			final I_C_DunningLevel dunningLevel,
			final LocalDateAndOrgId dunningDate,
			final ITrxRunConfig trxRunnerConfig,
			final String trxName,
			@Nullable final RecomputeDunningCandidatesQuery recomputeDunningCandidatesQuery)
	{
		final IDunningConfig config = getDunningConfig();
		return new DunningContext(ctx, config, dunningLevel, dunningDate, trxRunnerConfig, trxName, recomputeDunningCandidatesQuery);
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

		logger.info("Created/Updated {} from {} records evaluated", new Object[] { countCreated, countAll });

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

		sendMassNotifications(dunningProducer.getCreatedDunningDocIds(), Language.getBaseLanguage());
	}

	private void sendMassNotifications(@NonNull final Collection<DunningDocId> createdDunningDocIds,
			@NonNull final Language language)
	{
		dunningDAO.getByIdsInTrx(createdDunningDocIds)
				.stream()
				.filter(doc -> groupByBoilerPlate(doc) != null)
				.collect(Collectors.groupingBy(this::groupByBoilerPlate))
				.forEach((boilerPlateWithLineId, dunningDocs)-> sendMassNotifications(boilerPlateWithLineId,dunningDocs, language));
	}

	@Nullable
	private BoilerPlateWithLineId groupByBoilerPlate(final I_C_DunningDoc doc)
	{
		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(doc.getC_DocType_ID());
		if (docTypeId != null)
		{
			return docTypeDAO.getById(docTypeId).getMassGenerateNotification();
		}
		return null;
	}

	private void sendMassNotifications(@NonNull final BoilerPlateWithLineId boilerPlateWithLineId,
			@NonNull final List<I_C_DunningDoc> dunningDocs,
			@NonNull final Language language)
	{
		dunningDocs.stream()
				.collect(Collectors.groupingBy(doc -> OrgId.ofRepoId(doc.getAD_Org_ID())))
				.forEach((orgId, docs) -> this.sendMassNotifications(boilerPlateWithLineId, orgId, docs, language));
	}

	private void sendMassNotifications(final @NonNull BoilerPlateWithLineId boilerPlateWithLineId,
			@NonNull final OrgId orgId,
			@NonNull final List<I_C_DunningDoc> dunningDocs,
			@NonNull final Language language)
	{
		final BoilerPlate headerBoilerPlate = boilerPlateRepository.getByBoilerPlateId(boilerPlateWithLineId.getHeaderId(), language);
		final String lines = getMassGenerateLinesPlainString(boilerPlateWithLineId, dunningDocs, language);

		final Evaluatee headerEvaluatee = Evaluatees.ofSingleton(MASS_GENERATE_LINES_EVALUATEE_PARAM, lines)
				.andComposeWith(getPO(orgDAO.getById(orgId)));

		notificationBL.send(UserNotificationRequest.builder()
				.notificationGroupName(MASS_DUNNING_NOTIFICATION_GROUP_NAME)
				.recipient(Recipient.allOrgUsersForGroupAndOrgId(MASS_DUNNING_NOTIFICATION_GROUP_NAME, orgId))
				.contentPlain(headerBoilerPlate.evaluateTextSnippet(headerEvaluatee))
				.subjectPlain(headerBoilerPlate.evaluateSubject(headerEvaluatee))
				.eMailCustomType(EMailCustomType.MassDunning)
				.build());
	}

	@NonNull
	private String getMassGenerateLinesPlainString(final @NonNull BoilerPlateWithLineId boilerPlateWithLineId,
			final @NonNull List<I_C_DunningDoc> dunningDocs,
			final Language language)
	{
		final BoilerPlate lineBoilerPlate = boilerPlateRepository.getByBoilerPlateId(boilerPlateWithLineId.getLineId(), language);
		return dunningDocs.stream()
				.map(doc -> lineBoilerPlate.evaluateTextSnippet(createEvalContext(doc)))
				.collect(Collectors.joining("\n"));
	}

	private Evaluatee createEvalContext(final @NonNull I_C_DunningDoc doc)
	{
		return Evaluatees.compose(Evaluatees.ofSingleton("C_DunningLevel", dunningDAO.getById(DunningLevelId.ofRepoId(doc.getC_DunningLevel_ID())).getPrintName()),
				getPO(doc));
	}

	@Override
	public List<I_C_DunningLevel> getPreviousLevels(final I_C_DunningLevel level)
	{
		// refactor of org.compiere.model.MDunningLevel.getPreviousLevels()

		// NOTE: Only DaysAfterDue shall be considered when checking previous levels and not DaysAfterDue+DaysBetweenDunnings.

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
	public void processSourceAndItsCandidates(
			@NonNull final I_C_DunningDoc dunningDoc,
			@NonNull final I_C_DunningDoc_Line_Source source)
	{
		final I_C_Dunning_Candidate candidate = source.getC_Dunning_Candidate();
		candidate.setProcessed(true); // make sure the Processed flag is set
		candidate.setIsDunningDocProcessed(true); // IsDunningDocProcessed
		candidate.setDunningDateEffective(dunningDoc.getDunningDate());
		dunningDAO.save(candidate);

		source.setProcessed(true);
		InterfaceWrapperHelper.save(source);
	}

	@Override
	public void makeDeliveryStopIfNeeded(@NonNull final I_C_DunningDoc dunningDoc)
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
	public boolean isExpired(final @NonNull I_C_Dunning_Candidate candidate, @Nullable final Timestamp dunningGraceDate)
	{
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
