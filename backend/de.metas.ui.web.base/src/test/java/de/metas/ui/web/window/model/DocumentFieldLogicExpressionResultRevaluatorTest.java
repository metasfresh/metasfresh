package de.metas.ui.web.window.model;

import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleGroup;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import org.adempiere.service.ClientId;
import org.adempiere.ad.expression.api.IExpressionEvaluator;
import org.adempiere.ad.expression.api.LogicExpressionResult;
import org.adempiere.ad.expression.api.impl.LogicExpressionCompiler;
import org.compiere.util.Evaluatees;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.annotation.Nullable;

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

	private static IUserRolePermissions roleWithId(final int adRoleId)
	{
		return perms(adRoleId, null, null);
	}

	private static IUserRolePermissions perms(@Nullable final Integer roleId, @Nullable final Integer userId, @Nullable final Integer clientId)
	{
		final IUserRolePermissions p = Mockito.mock(IUserRolePermissions.class);
		if (roleId != null)   { Mockito.doReturn(RoleId.ofRepoId(roleId)).when(p).getRoleId(); }
		if (userId != null)   { Mockito.doReturn(UserId.ofRepoId(userId)).when(p).getUserId(); }
		if (clientId != null) { Mockito.doReturn(ClientId.ofRepoId(clientId)).when(p).getClientId(); }
		return p;
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

		/**
		 * Regression coverage for the per-request {@code #AD_Role_ID} substitution path
		 * (mf15#4157). A ReadOnlyLogic / DisplayLogic expression referencing {@code @#AD_Role_ID/0@}
		 * must NOT be evaluated against the role-id of the user who happened to load the descriptor
		 * first — every subsequent caller has to see the substitution under their own role.
		 */
		@Nested
		class expression_AD_Role_ID_equals_540174
		{
			// Compiled at descriptor build time with @#AD_Role_ID/0@ defaulting to 0 (i.e. unknown).
			// Without the revaluator's per-request substitution, every later caller would inherit '0=540174 -> false'.
			final LogicExpressionResult expression = expr("@#AD_Role_ID/0@=540174");

			@Test
			void compile_time_default_value_resolves_to_false()
			{
				assertThat(expression.booleanValue()).isFalse();
			}

			@Test
			void caller_with_role_id_540174_sees_TRUE()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator =
						DocumentFieldLogicExpressionResultRevaluator.using(roleWithId(540174));
				assertThat(revaluator.revaluate(expression).booleanValue()).isTrue();
			}

			@Test
			void caller_with_different_role_id_sees_FALSE()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator =
						DocumentFieldLogicExpressionResultRevaluator.using(roleWithId(540173));
				assertThat(revaluator.revaluate(expression).booleanValue()).isFalse();
			}

			@Test
			void substitution_is_independent_of_first_loaded_value()
			{
				// First request: role 540174 → substitution flips the cached '0' to '540174' → result TRUE
				final DocumentFieldLogicExpressionResultRevaluator first =
						DocumentFieldLogicExpressionResultRevaluator.using(roleWithId(540174));
				assertThat(first.revaluate(expression).booleanValue()).isTrue();

				// Second request, same cached `expression` instance: role 540173 → substitution flips to '540173' → result FALSE.
				// If the revaluator were to forget #AD_Role_ID, the second caller would still see TRUE.
				final DocumentFieldLogicExpressionResultRevaluator second =
						DocumentFieldLogicExpressionResultRevaluator.using(roleWithId(540173));
				assertThat(second.revaluate(expression).booleanValue()).isFalse();
			}
		}

		/**
		 * Regression coverage for the per-request {@code #AD_User_ID} substitution path.
		 * Mirrors {@code expression_AD_Role_ID_equals_540174} — same latent-bug shape.
		 */
		@Nested
		class expression_AD_User_ID_equals_12345
		{
			// Compiled at descriptor build time with @#AD_User_ID/0@ defaulting to 0 (i.e. unknown).
			final LogicExpressionResult expression = expr("@#AD_User_ID/0@=12345");

			@Test
			void compile_time_default_value_resolves_to_false()
			{
				assertThat(expression.booleanValue()).isFalse();
			}

			@Test
			void caller_with_matching_id_sees_TRUE()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, 12345, null));
				assertThat(revaluator.revaluate(expression).booleanValue()).isTrue();
			}

			@Test
			void caller_with_different_id_sees_FALSE()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, 99999, null));
				assertThat(revaluator.revaluate(expression).booleanValue()).isFalse();
			}

			@Test
			void substitution_is_independent_of_first_loaded_value()
			{
				// First request: user 12345 → substitution flips the cached '0' to '12345' → result TRUE
				final DocumentFieldLogicExpressionResultRevaluator first =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, 12345, null));
				assertThat(first.revaluate(expression).booleanValue()).isTrue();

				// Second request, same cached `expression` instance: user 99999 → substitution flips to '99999' → result FALSE.
				final DocumentFieldLogicExpressionResultRevaluator second =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, 99999, null));
				assertThat(second.revaluate(expression).booleanValue()).isFalse();
			}
		}

		/**
		 * Regression coverage for the per-request {@code #AD_Client_ID} substitution path.
		 * Mirrors {@code expression_AD_Role_ID_equals_540174} — same latent-bug shape.
		 */
		@Nested
		class expression_AD_Client_ID_equals_1000000
		{
			// Compiled at descriptor build time with @#AD_Client_ID/0@ defaulting to 0 (i.e. unknown).
			final LogicExpressionResult expression = expr("@#AD_Client_ID/0@=1000000");

			@Test
			void compile_time_default_value_resolves_to_false()
			{
				assertThat(expression.booleanValue()).isFalse();
			}

			@Test
			void caller_with_matching_id_sees_TRUE()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, null, 1000000));
				assertThat(revaluator.revaluate(expression).booleanValue()).isTrue();
			}

			@Test
			void caller_with_different_id_sees_FALSE()
			{
				final DocumentFieldLogicExpressionResultRevaluator revaluator =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, null, 1000001));
				assertThat(revaluator.revaluate(expression).booleanValue()).isFalse();
			}

			@Test
			void substitution_is_independent_of_first_loaded_value()
			{
				// First request: client 1000000 → substitution flips the cached '0' to '1000000' → result TRUE
				final DocumentFieldLogicExpressionResultRevaluator first =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, null, 1000000));
				assertThat(first.revaluate(expression).booleanValue()).isTrue();

				// Second request, same cached `expression` instance: client 1000001 → substitution flips to '1000001' → result FALSE.
				final DocumentFieldLogicExpressionResultRevaluator second =
						DocumentFieldLogicExpressionResultRevaluator.using(perms(null, null, 1000001));
				assertThat(second.revaluate(expression).booleanValue()).isFalse();
			}
		}
	}
}