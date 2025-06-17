package de.metas.business_rule.event;

import de.metas.business_rule.descriptor.BusinessRuleRepository;
import de.metas.business_rule.descriptor.model.BusinessRule;
import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRulePrecondition;
import de.metas.business_rule.descriptor.model.BusinessRuleTrigger;
import de.metas.business_rule.descriptor.model.BusinessRuleWarningTarget;
import de.metas.business_rule.descriptor.model.BusinessRuleWarningTargetType;
import de.metas.business_rule.descriptor.model.BusinessRulesCollection;
import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.business_rule.log.BusinessRuleStopwatch;
import de.metas.business_rule.util.BusinessRuleRecordMatcher;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.record.warning.RecordWarningCreateRequest;
import de.metas.record.warning.RecordWarningId;
import de.metas.record.warning.RecordWarningQuery;
import de.metas.record.warning.RecordWarningRepository;
import de.metas.user.api.IUserBL;
import de.metas.util.Services;
import de.metas.util.StringUtils;
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
import org.compiere.model.I_C_Conversion_Rate;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.POInfo;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
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

		final TargetRecordInfo rootTargetRecordInfo = retrieveRootTargetRecordInfo(event);

		final TableRecordReference rootTargetRecordRef = rootTargetRecordInfo == null ? null : rootTargetRecordInfo.getTargetRecordRef();

		logger.setRootTargetRecordRef(rootTargetRecordRef);
		logger.debug(stopwatch, "Retrieved root target record: {}", rootTargetRecordRef);
		if (rootTargetRecordRef == null)
		{
			logger.debug("Root target record was not found. End processing event.");
			return;
		}

		stopwatch.restart();
		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		final boolean isPreconditionMatching = isPreconditionsMet(rootTargetRecordRef, rule);
		logger.debug(stopwatch, "Checked if target record preconditions are met: {}", isPreconditionMatching);
		if (!isPreconditionMatching)
		{
			logger.debug("Record preconditions not met. End processing event.");
			return;
		}

		stopwatch.restart();
		final boolean isValid = isRecordValid(rootTargetRecordRef, rule.getValidation());
		logger.debug(stopwatch, "Checked if target record valid: {}", isValid);

		stopwatch.restart();
		if (isValid)
		{
			recordWarningRepository.delete(RecordWarningQuery.builder()
					.rootRecordRef(rootTargetRecordRef)
					.businessRuleId(rule.getId())
					.build());
			logger.debug(stopwatch, "=> Removed all warnings for target record");
		}
		else
		{
			createWarningsAndSendNotifications(event, rootTargetRecordInfo);
		}
	}

	private void createWarningsAndSendNotifications(@NonNull final BusinessRuleEvent event, @NonNull final TargetRecordInfo rootTargetRecordInfo)
	{
		final BusinessRuleStopwatch stopwatch = logger.newStopwatch();

		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		if (rule.getWarningTargets().isEmpty())
		{
			logger.warn("No warning targets defined. Skip creating warnings and sending notifications");
			return;
		}

		try (final IAutoCloseable ignored = temporarySwitchContext(event))
		{
			final AdMessageKey messageKey = getAdMessageKey(rule);

			final TableRecordReference rootTargetRecordRef = rootTargetRecordInfo.getTargetRecordRef();
			final String documentSummary = msgBL.translate(Env.getCtx(), rootTargetRecordRef.getTableName()) + " " + rootTargetRecordInfo.getDocumentSummary();

			final RecordWarningNoticeRequest.RecordWarningNoticeRequestBuilder noticeRequestBuilder = RecordWarningNoticeRequest.builder()
					.userId(event.getTriggeringUserId())
					.notificationSeverity(rule.getSeverity().toNotificationSeverity())
					.messageKey(messageKey)
					.messageParams(Collections.singletonList(documentSummary));

			final RecordWarningCreateRequest.RecordWarningCreateRequestBuilder warningRequestBuilder = RecordWarningCreateRequest.builder()
					.rootRecordRef(rootTargetRecordRef)
					.businessRuleId(rule.getId())
					.message(msgBL.getMsg(Env.getADLanguageOrBaseLanguage(), messageKey, Collections.singletonList(documentSummary)))
					.userId(event.getTriggeringUserId())
					.severity(rule.getSeverity());

			for (final BusinessRuleWarningTarget warningTarget : rule.getWarningTargets())
			{
				final TableRecordReference warningTargetRef = retrieveWarningTargetRef(warningTarget, rootTargetRecordRef);
				if (warningTargetRef == null)
				{
					logger.debug("Warning target record was not found. Skip warning target: {}", warningTarget);
					continue;
				}

				logger.setTargetRecordRef(warningTargetRef);

				final RecordWarningId warningTargetRecordId = recordWarningRepository.createOrUpdate(
						warningRequestBuilder
								.recordRef(warningTargetRef)
								.build());
				logger.debug(stopwatch, "=> Created/Updated warning for target record");

				BusinessRuleEventNotificationProducer.newInstance().createNotice(
						noticeRequestBuilder
								.recordWarningId(warningTargetRecordId)
								.build());
				logger.debug(stopwatch, "=> Created user notification for target record");
			}
		}
	}

	private static IAutoCloseable temporarySwitchContext(@NonNull final BusinessRuleEvent event)
	{
		return Env.switchContext(createTemporaryContext(event));
	}

	@NonNull
	private static Properties createTemporaryContext(@NonNull final BusinessRuleEvent event)
	{
		final Properties tempCtx = Env.newTemporaryCtx();
		Env.setClientAndOrgId(tempCtx, event.getClientAndOrgId());
		return tempCtx;
	}

	@Nullable
	private TargetRecordInfo retrieveRootTargetRecordInfo(@NonNull final BusinessRuleEvent event)
	{
		final BusinessRule rule = getRuleById(event.getBusinessRuleId());
		final BusinessRuleTrigger trigger = rule.getTriggerById(event.getTriggerId());

		final AdTableId sourceTableId = event.getSourceRecordRef().getAdTableId();
		final String sourceTableName = TableIdsCache.instance.getTableName(sourceTableId);
		final String sourceKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(sourceTableName);
		final int sourceRecordId = event.getSourceRecordRef().getRecord_ID();

		final AdTableId targetTableId = rule.getAdTableId();
		final String targetTableName = TableIdsCache.instance.getTableName(targetTableId);
		final String targetKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(targetTableName);

		final POInfo targetPOInfo = POInfo.getPOInfo(targetTableName);
		if (targetPOInfo == null)
		{
			return null;
		}

		final String targetRecordMappingSQL = trigger.getTargetRecordMappingSQL();
		if (targetRecordMappingSQL == null)
		{
			return null;
		}

		final StringBuilder sql = new StringBuilder();

		sql.append("SELECT target.")
				.append(targetKeyColumnName)
				.append(", ''");

		if (targetPOInfo.hasColumnName(InterfaceWrapperHelper.COLUMNNAME_DocumentNo))
		{
			sql.append(" || ' ' || target.").append(InterfaceWrapperHelper.COLUMNNAME_DocumentNo);
		}

		if (targetPOInfo.hasColumnName(InterfaceWrapperHelper.COLUMNNAME_Value))
		{
			sql.append(" || ' ' || target.").append(InterfaceWrapperHelper.COLUMNNAME_Value);
		}

		if (targetPOInfo.hasColumnName(InterfaceWrapperHelper.COLUMNNAME_Name))
		{
			sql.append(" || ' ' || target.").append(InterfaceWrapperHelper.COLUMNNAME_Name);
		}

		if (targetPOInfo.hasColumnName(I_C_OrderLine.COLUMNNAME_Line))
		{
			sql.append(" || ' ' || target.").append(I_C_OrderLine.COLUMNNAME_Line);
		}

		if (targetPOInfo.hasColumnName(I_C_Conversion_Rate.COLUMNNAME_ValidFrom))
		{
			sql.append(" || ' ' || target.").append(I_C_Conversion_Rate.COLUMNNAME_ValidFrom).append("::date");
		}

		sql.append(" FROM ").append(sourceTableName).append(" JOIN ")
				.append(targetTableName).append(" target ")
				.append(" ON ").append("target.").append(targetKeyColumnName).append(" = ");

		if (targetRecordMappingSQL.startsWith("("))
		{
			sql.append(targetRecordMappingSQL);
		}
		else
		{
			sql.append(sourceTableName).append(".").append(targetRecordMappingSQL);
		}
		sql.append(" WHERE ").append(sourceTableName).append(".").append(sourceKeyColumnName).append("=?");

		return DB.retrieveFirstRowOrNull(sql.toString(), Collections.singletonList(sourceRecordId), rs -> {
			final int targetRecordId = rs.getInt(1);
			final String documentSummary = StringUtils.trimBlankToNull(rs.getString(2));
			if (targetRecordId <= 0)
			{
				return null;
			}

			final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(targetKeyColumnName);
			if (targetRecordId < firstValidId)
			{
				return null;
			}
			return TargetRecordInfo.builder()
					.targetRecordRef(TableRecordReference.of(targetTableId, targetRecordId))
					.documentSummary(documentSummary != null ? documentSummary : "" + targetRecordId)
					.build();
		});
	}

	@Nullable
	private TableRecordReference retrieveWarningTargetRef(@NonNull final BusinessRuleWarningTarget warningTarget, @NonNull final TableRecordReference rootTargetRecordInfo)
	{
		final BusinessRuleWarningTargetType type = warningTarget.getType();
		switch (type)
		{
			case ROOT_TARGET_RECORD:
				return rootTargetRecordInfo;
			case SQL_LOOKUP:
				return retrieveWarningTargetRef_SqlLookup(warningTarget.getSqlLookupNotNull(), rootTargetRecordInfo);
			default:
				throw new AdempiereException("Unknown target type: " + type);
		}
	}

	@Nullable
	private TableRecordReference retrieveWarningTargetRef_SqlLookup(@NonNull final BusinessRuleWarningTarget.SqlLookup sqlLookup, @NonNull final TableRecordReference rootTargetRecordInfo)
	{
		final AdTableId warningTargetTableId = sqlLookup.getAdTableId();
		final String warningTargetTableName = TableIdsCache.instance.getTableName(warningTargetTableId);
		final String warningTargetKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(warningTargetTableName);

		final AdTableId rootTableId = rootTargetRecordInfo.getAdTableId();
		final String rootTableName = TableIdsCache.instance.getTableName(rootTableId);
		final String rootKeyColumnName = InterfaceWrapperHelper.getKeyColumnName(rootTableName);

		final String sql = "SELECT target." + warningTargetKeyColumnName
				+ " FROM " + rootTableName
				+ " JOIN " + warningTargetTableName + " target " + " ON " + "target." + warningTargetKeyColumnName + " = " + sqlLookup.getSql()
				+ " WHERE " + rootTableName + "." + rootKeyColumnName + "=?";

		final Integer targetRecordId = DB.retrieveFirstRowOrNull(sql, Collections.singletonList(rootTargetRecordInfo.getRecord_ID()), rs -> {
			final int intValue = rs.getInt(1);
			return rs.wasNull() ? null : intValue;
		});

		if (targetRecordId == null)
		{
			return null;
		}

		final int firstValidId = InterfaceWrapperHelper.getFirstValidIdByColumnName(warningTargetKeyColumnName);
		if (targetRecordId < firstValidId)
		{
			return null;
		}
		return TableRecordReference.of(warningTargetTableId, targetRecordId);
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
		return msgBL.getAdMessageKeyById(rule.getWarningMessageId()).orElseThrow(() -> new AdempiereException("No message defined for business rule ID:" + rule.getId()));
	}
}
