package de.metas.ui.web.window.model;

import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleGroup;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

class DocumentFieldLogicExpressionResultRevaluatorTest
{
	private static IUserRolePermissions role(String roleGroupStr)
	{
		final RoleGroup roleGroup = RoleGroup.ofNullableString(roleGroupStr);

		final IUserRolePermissions userRolePermissions = Mockito.mock(IUserRolePermissions.class);
		Mockito.doReturn(roleGroup).when(userRolePermissions).getRoleGroup();
		return userRolePermissions;
	}

	@SuppressWarnings("SameParameterValue")
	private static LogicExpressionResult expr(String expressionStr)
	{
		return LogicExpressionCompiler.instance
				.compile(expressionStr)
				.evaluateToResult(Evaluatees.empty(), IExpressionEvaluator.OnVariableNotFound.Fail);
	}

	@Nested
	class revaluate
	{
		@Nested
		class using_DEFAULT_revaluator
		{
			@Test
			void falseExpression()
			{
				final LogicExpressionResult expression = expr("@Var/X@=Y");
				assertThat(expression.booleanValue()).isFalse();
				assertThat(DocumentFieldLogicExpressionResultRevaluator.DEFAULT.revaluate(expression)).isSameAs(expression);
			}

			@Test
			void trueExpression()
			{
				final LogicExpressionResult expression = expr("@Var/X@=X");
				assertThat(expression.booleanValue()).isTrue();
				assertThat(DocumentFieldLogicExpressionResultRevaluator.DEFAULT.revaluate(expression)).isSameAs(expression);
			}
		}

		@Nested
		class using_ALWAYS_RETURN_FALSE_revaluator
		{
			private void revaluateAndTest(final LogicExpressionResult expression)
			{
				final LogicExpressionResult expressionRevaluated = DocumentFieldLogicExpressionResultRevaluator.ALWAYS_RETURN_FALSE.revaluate(expression);
				assertThat(expressionRevaluated).isNotSameAs(expression);
				assertThat(expressionRevaluated.booleanValue()).isFalse();
			}

			@Test
			void falseExpression()
			{
				final LogicExpressionResult expression = expr("@Var/X@=Y");
				assertThat(expression.booleanValue()).isFalse();
				revaluateAndTest(expression);
			}

			@Test
			void trueExpression()
			{
				final LogicExpressionResult expression = expr("@Var/X@=X");
				assertThat(expression.booleanValue()).isTrue();
				revaluateAndTest(expression);
			}
		}

		@Nested
		class expression_RoleGroup_equals_Accounting
		{
			final LogicExpressionResult expression = expr("@#AD_Role_Group/''@=Accounting");

			@Test
			void using_DEFAULT_revaluator()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator = DocumentFieldLogicExpressionResultRevaluator.DEFAULT;
				assertThat(revaluator.revaluate(expression)).isSameAs(expression);
			}

			@Test
			void using_ALWAYS_RETURN_FALSE_revaluator()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator = DocumentFieldLogicExpressionResultRevaluator.ALWAYS_RETURN_FALSE;
				assertThat(revaluator.revaluate(expression).booleanValue()).isFalse();
			}

			@Test
			void having_Accounting_roleGroup()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator = DocumentFieldLogicExpressionResultRevaluator.using(role("Accounting"));
				assertThat(revaluator.revaluate(expression).booleanValue()).isTrue();
			}

			@Test
			void having_Sales_roleGroup()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator = DocumentFieldLogicExpressionResultRevaluator.using(role("Sales"));
				assertThat(revaluator.revaluate(expression).booleanValue()).isFalse();
			}

			@Test
			void having_NO_roleGroup()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator = DocumentFieldLogicExpressionResultRevaluator.using(role(null));
				assertThat(revaluator.revaluate(expression)).isSameAs(expression);
			}

		}
	}
}