package de.metas.business_rule.event;

import de.metas.business_rule.descriptor.BusinessRuleRepository;
import de.metas.business_rule.descriptor.model.BusinessRule;
import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRulePrecondition;
import de.metas.business_rule.descriptor.model.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.business_rule.log.BusinessRuleStopwatch;
import de.metas.business_rule.util.BusinessRuleRecordMatcher;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.record.warning.RecordWarningCreateRequest;
import de.metas.record.warning.RecordWarningQuery;
import de.metas.record.warning.RecordWarningRepository;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class BusinessRuleEventProcessorCommand
{
	@NonNull private static final String LOGGER_MODULE = "event-processor";

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);
	@NonNull private final IUserBL userBL = Services.get(IUserBL.class);
	@NonNull private final IErrorManager errorManager = Services.get(IErrorManager.class);
	@NonNull private final BusinessRuleRepository ruleRepository;
	@NonNull private final BusinessRuleEventRepository eventRepository;
	@NonNull private final RecordWarningRepository recordWarningRepository;
	@NonNull private final BusinessRuleLogger logger;
	@NonNull private final BusinessRuleRecordMatcher recordMatcher;

	@NonNull private final QueryLimit limit;
	@Nullable private BusinessRulesCollection rules;

	public void execute()
	{
		try (final IAutoCloseable ignored = setupLoggerContext())
		{
			eventRepository.updateAllNotProcessed(this::processEvent, limit);
		}
		catch (final Exception ex)
		{
			logger.warn("Failed processing events: {}", ex.getLocalizedMessage(), ex);
		}
	}

	private BusinessRulesCollection getRules()
	{
		if (rules == null)
		{
			rules = ruleRepository.getAll();
		}
		return rules;
	}

	private BusinessRule getRuleById(final BusinessRuleId businessRuleId)
	{
		return getRules().getById(businessRuleId);
	}

	@Nullable
	private BusinessRule getRuleByIdOrNull(@Nullable final BusinessRuleId businessRuleId)
	{
		return getRules().getByIdOrNull(businessRuleId);
	}

	private BusinessRuleEvent processEvent(final BusinessRuleEvent event)
	{
		if (event.isProcessed())
		{
			return event;
		}

		final BusinessRuleStopwatch stopwatch = logger.newStopwatch();
		try (final IAutoCloseable ignored = updateLoggerContextFrom(event))
		{
			trxManager.runInThreadInheritedTrx(() -> processEvent0(event));
			logger.debug(stopwatch, "Event processed successful");
			return event.markAsProcessedOK();
		}
		catch (final Exception ex)
		{
			final AdempiereException metasfreshException = AdempiereException.wrapIfNeeded(ex);
			final AdIssueId errorId = errorManager.createIssue(metasfreshException);
			logger.debug(stopwatch, "Failed processing event", ex);

			return event.markAsProcessingError(errorId);
		}
	}

	private IAutoCloseable setupLoggerContext()
	{
		return logger.temporaryChangeContext(contextBuilder -> contextBuilder.moduleName(LOGGER_MODULE));
	}

	private IAutoCloseable updateLoggerContextFrom(@NonNull final BusinessRuleEvent event)
	{
		final BusinessRule rule = getRuleByIdOrNull(event.getBusinessRuleId());

		return logger.temporaryChangeContext(contextBuilder -> contextBuilder
				.eventId(event.getId())
				.businessRule(rule)
				.triggerId(event.getTriggerId())
				.sourceRecordRef(event.getSourceRecordRef())
		);
	}

	private void processEvent0(final BusinessRuleEvent event)
	{
		logger.debug("Processing event: {}", event);

		final BusinessRuleStopwatch stopwatch = logger.newStopwatch();
		final TableRecordReference targetRecordRef = retrieveTargetRecordRef(event);
		logger.setTargetRecordRef(targetRecordRef);
		logger.debug(stopwatch, "Retrieved target record: {}", targetRecordRef);
		if (targetRecordRef == null)
		{
			logger.debug("Target record was not found. End processing event.");
			return;
		}

		stopwatch.restart();
		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		final boolean isPreconditionMatching = isPreconditionsMet(targetRecordRef, rule);
		logger.debug(stopwatch, "Checked if target record preconditions are met: {}", isPreconditionMatching);
		if (!isPreconditionMatching)
		{
			logger.debug("Record preconditions not met. End processing event.");
			return;
		}

		stopwatch.restart();
		final boolean isValid = isRecordValid(targetRecordRef, rule.getValidation());
		logger.debug(stopwatch, "Checked if target record valid: {}", isValid);

		stopwatch.restart();
		if (isValid)
		{
			recordWarningRepository.deleteByRecordRef(RecordWarningQuery.builder()
					.recordRef(targetRecordRef)
					.businessRuleId(rule.getId())
					.build());
			logger.debug(stopwatch, "=> Removed all warnings for target record");
		}
		else
		{
			final UserId userId = retrieveSourceUserId(event);
			if (userId != null)
			{
				final Language userLanguage = userBL.getUserLanguage(userId);
				final AdMessageKey messageKey = getAdMessageKey(rule);
				recordWarningRepository.createOrUpdate(RecordWarningCreateRequest.builder()
						.recordRef(targetRecordRef)
						.businessRuleId(rule.getId())
						.message(msgBL.getMsg(userLanguage.getAD_Language(), messageKey))
						.build());
				logger.debug(stopwatch, "=> Created/Updated warning for target record");

				BusinessRuleEventNotificationProducer.newInstance().createNotice(userId, targetRecordRef, messageKey);
				logger.debug(stopwatch, "=> Created user notification for target record");
			}

		}
	}

	@Nullable
	private TableRecordReference retrieveTargetRecordRef(@NonNull final BusinessRuleEvent event)
	{
		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		final BusinessRuleTrigger trigger = rule.getTriggerById(event.getTriggerId());

		final AdTableId sourceTableId = event.getSourceRecordRef().getAdTableId();
		final String sourceTableName = TableIdsCache.instance.getTableName(sourceTableId);
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(sourceTableName);
		final int sourceRecordId = event.getSourceRecordRef().getRecord_ID();
		final String sql = "SELECT " + trigger.getTargetRecordMappingSQL() + " FROM " + sourceTableName + " WHERE " + keyColumnName + "=?";
		final Integer targetRecordId = getRecordIdFromSql(sql, sourceRecordId, keyColumnName);
		return targetRecordId == null ? null : TableRecordReference.of(rule.getAdTableId(), targetRecordId);
	}

	@Nullable
	private static Integer getRecordIdFromSql(final String sql, final int sourceRecordId, final String keyColumnName)
	{
		final Integer targetRecordId = DB.retrieveFirstRowOrNull(sql, Collections.singletonList(sourceRecordId), rs -> {
			final int intValue = rs.getInt(1);
			return rs.wasNull() ? null : intValue;
		});

		if (targetRecordId == null)
		{
			return null;
		}

		final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(keyColumnName);
		if (targetRecordId < firstValidId)
		{
			return null;
		}
		return targetRecordId;
	}

	@Nullable
	private UserId retrieveSourceUserId(@NonNull final BusinessRuleEvent event)
	{
		final String sourceTableName = event.getSourceRecordRef().getTableName();
		final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(sourceTableName);
		final int sourceRecordId = event.getSourceRecordRef().getRecord_ID();
		final String sql = "SELECT updatedBy FROM " + sourceTableName + " WHERE " + keyColumnName + "=?";
		final Integer updatedByUserId = getRecordIdFromSql(sql, sourceRecordId, keyColumnName);
		return updatedByUserId == null ? null : UserId.ofRepoId(updatedByUserId);
	}

	private boolean isPreconditionsMet(
			@NonNull final TableRecordReference targetRecordRef,
			@NonNull final BusinessRule rule)
	{
		final List<Validation> validations = rule.getPreconditions().stream().map(BusinessRulePrecondition::getValidation).collect(Collectors.toList());
		return recordMatcher.isRecordMatching(targetRecordRef, validations);
	}

	private boolean isRecordValid(
			@NonNull final TableRecordReference targetRecordRef,
			@NonNull final Validation validation)
	{
		return recordMatcher.isRecordMatching(targetRecordRef, validation);
	}

	private AdMessageKey getAdMessageKey(final @NonNull BusinessRule rule)
	{
		return msgBL.getAdMessageKeyById(rule.getWarningMessage()).orElseThrow(() -> new AdempiereException("No message defined for business rule ID:" + rule.getId()));
	}
}
