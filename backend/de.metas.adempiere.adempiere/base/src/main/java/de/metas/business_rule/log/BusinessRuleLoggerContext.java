package de.metas.business_rule.log;

import de.metas.business_rule.descriptor.model.BusinessRule;
import de.metas.business_rule.descriptor.model.BusinessRuleId;
import de.metas.business_rule.descriptor.model.BusinessRulePreconditionId;
import de.metas.business_rule.descriptor.model.BusinessRuleTriggerId;
import de.metas.business_rule.event.BusinessRuleEventId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;
import java.util.function.Consumer;

@Value
public class BusinessRuleLoggerContext
{
	public static final BusinessRuleLoggerContext ROOT = _builder().isRootContext(true).parentLogLevel(BusinessRuleLogLevel.WARN).build();

	boolean isRootContext;

	@Nullable String moduleName;
	@NonNull BusinessRuleLogLevel parentLogLevel;
	@Nullable BusinessRuleLogLevel logLevel;
	@NonNull BusinessRuleLogLevel effectiveLogLevel;
	@Nullable BusinessRuleId businessRuleId;
	@Nullable BusinessRulePreconditionId preconditionId;
	@Nullable BusinessRuleTriggerId triggerId;
	@Nullable TableRecordReference sourceRecordRef;
	@Nullable TableRecordReference targetRecordRef;
	@Nullable BusinessRuleEventId eventId;

	@Builder(toBuilder = true, builderMethodName = "_builder")
	private BusinessRuleLoggerContext(
			final boolean isRootContext,
			@Nullable final String moduleName,
			@NonNull final BusinessRuleLogLevel parentLogLevel,
			@Nullable final BusinessRuleLogLevel logLevel,
			@Nullable final BusinessRuleId businessRuleId,
			@Nullable final BusinessRulePreconditionId preconditionId,
			@Nullable final BusinessRuleTriggerId triggerId,
			@Nullable final TableRecordReference sourceRecordRef,
			@Nullable final TableRecordReference targetRecordRef,
			@Nullable final BusinessRuleEventId eventId)
	{
		this.isRootContext = isRootContext;
		this.moduleName = moduleName;
		this.parentLogLevel = parentLogLevel;
		this.logLevel = logLevel;
		this.effectiveLogLevel = logLevel != null ? logLevel : parentLogLevel;
		this.businessRuleId = businessRuleId;
		this.preconditionId = preconditionId;
		this.triggerId = triggerId;
		this.sourceRecordRef = sourceRecordRef;
		this.targetRecordRef = targetRecordRef;
		this.eventId = eventId;
	}

	public static class BusinessRuleLoggerContextBuilder
	{
		public BusinessRuleLoggerContextBuilder businessRule(final BusinessRule businessRule)
		{
			businessRuleId(businessRule != null ? businessRule.getId() : null);
			logLevel(businessRule != null ? businessRule.getLogLevel() : null);
			return this;
		}
	}

	public BusinessRuleLoggerContextBuilder newChild()
	{
		return toBuilder()
				.isRootContext(false)
				.parentLogLevel(this.effectiveLogLevel)
				.logLevel(null);
	}

	public BusinessRuleLoggerContext newChild(@NonNull final Consumer<BusinessRuleLoggerContextBuilder> updater)
	{
		final BusinessRuleLoggerContextBuilder childContextBuilder = newChild();
		updater.accept(childContextBuilder);
		return childContextBuilder.build();
	}

	public boolean isDebugEnabled() {return isLogLevelEnabled(BusinessRuleLogLevel.DEBUG);}

	public boolean isLogLevelEnabled(@NonNull final BusinessRuleLogLevel logLevel) {return logLevel.ordinal() <= effectiveLogLevel.ordinal();}

	public BusinessRuleLoggerContext withTargetRecordRef(final TableRecordReference targetRecordRef)
	{
		return !TableRecordReference.equals(this.targetRecordRef, targetRecordRef)
				? toBuilder().targetRecordRef(targetRecordRef).build()
				: this;
	}

}
