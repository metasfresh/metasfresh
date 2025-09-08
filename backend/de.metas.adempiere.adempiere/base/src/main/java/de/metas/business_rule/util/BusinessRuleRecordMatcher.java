package de.metas.business_rule.util;

import de.metas.business_rule.descriptor.model.Validation;
import de.metas.business_rule.log.BusinessRuleLogger;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.validationRule.IValidationRule;
import org.adempiere.ad.validationRule.IValidationRuleFactory;
import org.adempiere.ad.validationRule.impl.CompositeValidationRule;
import org.adempiere.ad.validationRule.impl.EvaluateeValidationContext;
import org.adempiere.ad.validationRule.impl.NullValidationRule;
import org.adempiere.ad.validationRule.impl.SQLValidationRule;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.IQuery;
import org.compiere.util.Evaluatee2;
import org.compiere.util.Evaluatees;
import org.compiere.util.KeyNamePair;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class BusinessRuleRecordMatcher
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IValidationRuleFactory validationRuleFactory = Services.get(IValidationRuleFactory.class);
	@NonNull private final BusinessRuleLogger logger;

	public boolean isRecordMatching(
			@NonNull final TableRecordReference recordRef,
			@NonNull final Validation validation)
	{
		final IValidationRule validationRule = toValidationRule(validation, recordRef.getTableName());
		return isRecordMatching(recordRef, validationRule);
	}

	public boolean isRecordMatching(
			@NonNull final TableRecordReference recordRef,
			@NonNull final Collection<Validation> validations)
	{
		if (validations.isEmpty())
		{
			return true;
		}

		final IValidationRule validationRule = toValidationRule(validations, recordRef.getTableName());
		return isRecordMatching(recordRef, validationRule);
	}

	private IValidationRule toValidationRule(@NonNull final Collection<Validation> validations, @NonNull final String targetTableName)
	{
		if (validations.isEmpty())
		{
			return NullValidationRule.instance;
		}

		return validations.stream()
				.map(validation -> toValidationRule(validation, targetTableName))
				.collect(CompositeValidationRule.collect());
	}

	private IValidationRule toValidationRule(
			@NonNull final Validation validation,
			@NonNull final String targetTableName)
	{
		switch (validation.getType())
		{
			case SQL:
			{
				return SQLValidationRule.ofNullableSqlWhereClause(validation.getSql());
			}
			case ValidationRule:
			{
				return validationRuleFactory.create(targetTableName, validation.getAdValRuleId(), null, null);
			}
			default:
			{
				throw new AdempiereException("Unknown type: " + validation.getType());
			}
		}
	}

	public boolean isRecordMatching(
			@NonNull final TableRecordReference recordRef,
			@NonNull final IValidationRule validationRule)
	{
		if (NullValidationRule.isNull(validationRule))
		{
			return true;
		}

		final Evaluatee2 evaluate = Evaluatees.empty();

		// 
		// Step 1. Make sure record exists and matches provided SQL where clause
		{
			final String tableName = recordRef.getTableName();
			final String keyColumnName = InterfaceWrapperHelper.getKeyColumnName(tableName);
			final int targetRecordId = recordRef.getRecord_ID();
			final IQueryBuilder<Object> sqlQueryBuilder = queryBL.createQueryBuilder(tableName)
					.addEqualsFilter(keyColumnName, targetRecordId) // make sure record exists
					;

			final IStringExpression sqlWhereClauseExpression = validationRule.getPrefilterWhereClause();
			if (sqlWhereClauseExpression != null && !sqlWhereClauseExpression.isNullExpression())
			{
				final String sqlWhereClause = sqlWhereClauseExpression.evaluate(evaluate, IExpressionEvaluator.OnVariableNotFound.Fail);
				if (!Check.isBlank(sqlWhereClause))
				{
					sqlQueryBuilder.addFilter(TypedSqlQueryFilter.of(sqlWhereClause));
				}

				final IQuery<Object> sqlQuery = sqlQueryBuilder.create();

				try
				{
					if (!sqlQuery.anyMatch())
					{
						return false;
					}
				}
				catch (final Exception ex)
				{
					// NOTE we are logging it because in most of the cases it's an SQL exception 
					// and the current transaction is corrupted anyway and next SQL commands will fail with the error
					// "current transaction is aborted, commands ignored until end of transaction block"
					logger.warn("Failed running SQL while checking if record {} is matching {}. Returning false.\nSQL:\n{}", recordRef, validationRule, sqlQuery, ex);
					return false;
				}
			}
		}

		//
		// Step 2. Make sure non-SQL validator matches
		{
			return validationRule.getPostQueryFilter().accept(new EvaluateeValidationContext(evaluate), toKeyNamePair(recordRef));
		}
	}

	private static KeyNamePair toKeyNamePair(final @NonNull TableRecordReference recordRef)
	{
		return KeyNamePair.of(recordRef.getRecord_ID(), String.valueOf(recordRef.getRecord_ID()));
	}
}
