/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.externalsystem.endpoint.interceptor;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.endpoint.TransportType;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Resets every endpoint field the window no longer shows to the value a newly created record would carry.
 * <p>
 * An endpoint's transport type and the two authentication types decide which of its fields the window
 * renders. Whenever one of them changes, every field the resulting configuration HIDES is set back to that
 * column's {@code AD_Column.DefaultValue} -- which, for the columns that carry none, is no value at all. So
 * whatever the operator had typed under the transport being left is gone, and a transport reached by a
 * switch presents the same field values as one reached by creating the record: same window, same column,
 * one behaviour. {@code AD_Column.DefaultValue} itself fires on record creation only, which is why this
 * handler has to apply it.
 * <p>
 * <b>Scope: only columns whose {@code AD_Field} is active.</b> A column no configuration renders is hidden
 * by none either, and clearing it would destroy a value that has no field left to restore it from.
 * {@code Type} is the case in point: its {@code AD_Field} and {@code AD_UI_Element} are both
 * {@code IsActive='N'}, and its {@code AD_Column.MandatoryLogic} is {@code @TransportType/X@='HTTP'} -- so
 * clearing it would leave every HTTP endpoint unsaveable. Retiring such a column means dropping the column,
 * a change of its own.
 * <p>
 * <b>Assumption: a WINDOW save never arrives with a half-configured transport.</b> The rules read the
 * record as it is about to be stored, so {@code TransportType=SFTP} with {@code SftpAuthType} still unset
 * would read as "SFTP, and not password authentication" and take the password away. What rules that state
 * out is {@code AD_Column.MandatoryLogic}: {@code SftpHost}, {@code SftpPort}, {@code SftpUsername},
 * {@code SftpRemotePath} and {@code SftpAuthType} each carry {@code @TransportType/X@='SFTP'}, so picking
 * SFTP alone cannot be saved. That check is the window's, though, and this fires on every {@code save()}:
 * a writer that stores the record outside a window is not covered.
 */
@Interceptor(I_ExternalSystem_Endpoint.class)
@Component
public class ExternalSystem_Endpoint
{
	/**
	 * The columns whose values decide what the window shows. Nothing else may appear in a
	 * {@link HideableColumn#getDisplayLogic() display logic} here, because these three are exactly the
	 * columns {@link #resetFieldsHiddenByTheNewConfiguration(I_ExternalSystem_Endpoint)} triggers on — a
	 * rule that depended on a fourth column would simply not be re-evaluated when that column changed.
	 * {@code ExternalSystem_EndpointTest.VisibilityRules} pins this.
	 */
	@VisibleForTesting
	static final ImmutableSet<String> VISIBILITY_GOVERNING_COLUMN_NAMES = ImmutableSet.of(
			I_ExternalSystem_Endpoint.COLUMNNAME_TransportType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType);

	@NonNull private final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	/**
	 * Every column this endpoint's window can hide, together with the condition under which it is shown and
	 * the value being hidden leaves behind. Each {@code displayLogic} is a <b>verbatim copy</b> of that
	 * field's {@code AD_Field.DisplayLogic}, and each {@link #hideableWithColumnDefault} value a verbatim
	 * copy of that column's {@code AD_Column.DefaultValue}; whoever changes either in a migration script
	 * changes the copy here. {@code externalSystemEndpointDisplayLogic.feature} holds both against the live
	 * dictionary.
	 * <p>
	 * A column with no display logic at all is always visible and therefore does not belong here:
	 * {@code IsArrayFanOut} is the case in point — every transport shows it, so no transport switch may
	 * clear it.
	 * <p>
	 * Lazily built: compiling a logic expression asks the sysconfig that turns operator precedence on or
	 * off, which is not necessarily answerable while this bean is being constructed.
	 */
	@VisibleForTesting
	final Supplier<ImmutableList<HideableColumn>> hideableColumns = Suppliers.memoize(this::createHideableColumns);

	private static final String VISIBLE_FOR_HTTP = "@TransportType/X@='HTTP'";
	private static final String VISIBLE_FOR_SFTP = "@TransportType/X@='SFTP'";
	private static final String VISIBLE_FOR_LOCAL_FILE = "@TransportType/X@='LOCAL_FILE'";
	private static final String VISIBLE_FOR_HTTP_OAUTH2 = "@TransportType/X@='HTTP' & @AuthType/X@='OAuth2'";

	private ImmutableList<HideableColumn> createHideableColumns()
	{
		return ImmutableList.of(
				// HTTP transport
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_HttpEndPoint, VISIBLE_FOR_HTTP,
						endpoint -> endpoint.setHttpEndPoint(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_OutboundHttpMethod, VISIBLE_FOR_HTTP,
						endpoint -> endpoint.setOutboundHttpMethod(null)),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_ContentType, VISIBLE_FOR_HTTP,
						"application/json"),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_IsFileUpload, VISIBLE_FOR_HTTP,
						false),
				// HTTP authentication
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_AuthType, VISIBLE_FOR_HTTP,
						endpoint -> endpoint.setAuthType(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_AuthToken,
						"@TransportType/X@='HTTP' & @AuthType/X@='Token'",
						endpoint -> endpoint.setAuthToken(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_LoginUsername,
						"@TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='Basic' | @AuthType/X@='OAuth2')",
						endpoint -> endpoint.setLoginUsername(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_Password,
						"(@TransportType/X@='HTTP' & @AuthType/X@='Basic') | (@TransportType/X@='SFTP' & @SftpAuthType/X@='PASSWORD') | (@TransportType/X@='HTTP' & @AuthType/X@='OAuth2') | (@TransportType/X@='HTTP' & @AuthType/X@='OAuth')",
						endpoint -> endpoint.setPassword(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ClientId,
						"@TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='OAuth2')",
						endpoint -> endpoint.setClientId(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ClientSecret,
						"@TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='OAuth2')",
						endpoint -> endpoint.setClientSecret(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SasSignature,
						"@TransportType/X@='HTTP' & @AuthType/X@='SAS'",
						endpoint -> endpoint.setSasSignature(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_OAuthTokenUrl, VISIBLE_FOR_HTTP_OAUTH2,
						endpoint -> endpoint.setOAuthTokenUrl(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_OAuthScope, VISIBLE_FOR_HTTP_OAUTH2,
						endpoint -> endpoint.setOAuthScope(null)),
				// SFTP transport
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpHost, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpHost(null)),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort, VISIBLE_FOR_SFTP,
						22),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpUsername, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpUsername(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpRemotePath, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpRemotePath(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpFilenamePattern, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpFilenamePattern(null)),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPollingIntervalMs, VISIBLE_FOR_SFTP,
						60_000),
				// SFTP authentication
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpAuthType(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SshPrivateKey,
						"@TransportType/X@='SFTP' & @SftpAuthType/X@='SSH_KEY'",
						endpoint -> endpoint.setSshPrivateKey(null)),
				// LOCAL_FILE transport
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_LocalRootLocation, VISIBLE_FOR_LOCAL_FILE,
						endpoint -> endpoint.setLocalRootLocation(null)),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_Frequency, VISIBLE_FOR_LOCAL_FILE,
						60_000),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ImportFileNamePattern, VISIBLE_FOR_LOCAL_FILE,
						endpoint -> endpoint.setImportFileNamePattern(null)));
	}

	/** A column with no {@code AD_Column.DefaultValue}: hiding it leaves it with no value. */
	private HideableColumn hideable(
			@NonNull final String columnName,
			@NonNull final String displayLogic,
			@NonNull final Consumer<I_ExternalSystem_Endpoint> hiddenValueAction)
	{
		return new HideableColumn(columnName, displayLogic, compile(displayLogic), null, hiddenValueAction);
	}

	/**
	 * A column that carries an {@code AD_Column.DefaultValue}: hiding it puts that value back, so the
	 * operator finds the field exactly as a newly created record would present it.
	 * <p>
	 * {@code columnDefault} is the dictionary's value in the type the column stores -- {@code 22}, not
	 * {@code "22"}, and {@code false} for a {@code DefaultValue} of {@code 'N'}. It is written through
	 * {@link InterfaceWrapperHelper#setValue} rather than the generated setter, which for {@code SftpPort}
	 * and {@code Frequency} takes an {@code int}: the one value that must never reach those two columns is
	 * {@code 0}, because it satisfies their {@code MandatoryLogic} while
	 * {@code ExternalSystemEndpointRepository} reads it as unconfigured -- the window would call a portless,
	 * poll-less endpoint valid.
	 */
	private HideableColumn hideableWithColumnDefault(
			@NonNull final String columnName,
			@NonNull final String displayLogic,
			@NonNull final Object columnDefault)
	{
		return new HideableColumn(columnName, displayLogic, compile(displayLogic), columnDefault,
				endpoint -> InterfaceWrapperHelper.setValue(endpoint, columnName, columnDefault));
	}

	private ILogicExpression compile(@NonNull final String displayLogic)
	{
		return expressionFactory.compile(displayLogic, ILogicExpression.class);
	}

	/**
	 * The {@code AD_Field.DisplayLogic} copy this class carries, per column it can hide. Public for one
	 * reader: {@code externalSystemEndpointDisplayLogic.feature}, which holds these copies against the live
	 * dictionary -- the only test home that runs against a database with the migration scripts applied.
	 */
	@VisibleForTesting
	public ImmutableMap<String, String> getDisplayLogicByColumnName()
	{
		return hideableColumns.get().stream()
				.collect(ImmutableMap.toImmutableMap(HideableColumn::getColumnName, HideableColumn::getDisplayLogic));
	}

	/**
	 * The {@code AD_Column.DefaultValue} copy this class carries, for the columns it restores one to. A
	 * column absent from this map is one the rule table claims has no default -- a claim the same reader as
	 * {@link #getDisplayLogicByColumnName()} holds against the live dictionary.
	 */
	@VisibleForTesting
	public ImmutableMap<String, Object> getColumnDefaultByColumnName()
	{
		return hideableColumns.get().stream()
				.filter(column -> column.getColumnDefault() != null)
				.collect(ImmutableMap.toImmutableMap(HideableColumn::getColumnName, HideableColumn::getColumnDefault));
	}

	/**
	 * Puts every field the endpoint's new configuration hides back to its column default -- for most of them
	 * no value at all, for the five that carry an {@code AD_Column.DefaultValue} that value.
	 * <p>
	 * One handler for all three governing columns on purpose: a save may change more than one of them at
	 * once, and a condition such as {@code Password}'s spans all three -- a handler keyed on a single column
	 * would decide that field's fate from a part of the change only.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_ExternalSystem_Endpoint.COLUMNNAME_TransportType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType })
	public void resetFieldsHiddenByTheNewConfiguration(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		assertTransportTypeIsAKnownCode(endpoint);

		// ONE snapshot of the state the record is about to be stored in decides every field: clearing e.g.
		// AuthType must not change the verdict already reached for Password
		final Evaluatee newConfiguration = extractVisibilityGoverningValues(endpoint);

		final ImmutableList<HideableColumn> hiddenColumns = hideableColumns.get().stream()
				.filter(column -> !column.isVisible(newConfiguration))
				.collect(ImmutableList.toImmutableList());

		hiddenColumns.forEach(column -> column.applyHiddenValue(endpoint));
	}

	/**
	 * Refuses a transport code {@link TransportType} has no constant for -- no condition in
	 * {@link #createHideableColumns()} is keyed on it, so the handler would reset EVERY hideable column in a
	 * single save. Refusing adds no restriction: {@code ExternalSystemEndpointRepository#fromRecord}
	 * resolves the very same {@link TransportType#ofCode(String)}, so the record would be unloadable anyway.
	 * <p>
	 * A code the enum DOES have a constant for but no rule is keyed on passes here; that is the separate
	 * guarantee {@code ExternalSystem_EndpointTest.VisibilityRules} carries.
	 */
	private static void assertTransportTypeIsAKnownCode(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final String transportTypeCode = Check.assumeNotEmpty(
				endpoint.getTransportType(),
				"TransportType must be set on {} -- it is a mandatory column",
				I_ExternalSystem_Endpoint.Table_Name);

		TransportType.ofCode(transportTypeCode);
	}

	/**
	 * The record's {@link #VISIBILITY_GOVERNING_COLUMN_NAMES} values, as the display logic expressions read
	 * them. A column that holds no value is left out, so the expression falls back to its own default "X" --
	 * which no rule compares against, because {@code ExternalSystem_EndpointTest.VisibilityRules} forbids a
	 * literal equal to the variable's own default (see {@link HideableColumn#isVisible}).
	 */
	private static Evaluatee extractVisibilityGoverningValues(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		final Map<String, String> values = new HashMap<>();
		putIfNotNull(values, I_ExternalSystem_Endpoint.COLUMNNAME_TransportType, endpoint.getTransportType());
		putIfNotNull(values, I_ExternalSystem_Endpoint.COLUMNNAME_AuthType, endpoint.getAuthType());
		putIfNotNull(values, I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType, endpoint.getSftpAuthType());
		return Evaluatees.ofMap(values);
	}

	private static void putIfNotNull(
			@NonNull final Map<String, String> values,
			@NonNull final String columnName,
			@Nullable final String value)
	{
		if (value != null)
		{
			values.put(columnName, value);
		}
	}

	/** A column the window hides under some configurations, and what "hidden" must leave behind. */
	@Value
	@VisibleForTesting
	static class HideableColumn
	{
		@NonNull String columnName;

		/** verbatim copy of this column's field's {@code AD_Field.DisplayLogic} */
		@NonNull String displayLogic;

		@NonNull ILogicExpression visibleIf;

		/**
		 * verbatim copy of this column's {@code AD_Column.DefaultValue}, in the type the column stores;
		 * {@code null} where the dictionary gives the column no default
		 */
		@Nullable Object columnDefault;

		/** writes {@link #columnDefault}, or takes the value away where there is none */
		@NonNull Consumer<I_ExternalSystem_Endpoint> hiddenValueAction;

		boolean isVisible(@NonNull final Evaluatee configuration)
		{
			// Agrees with the window only while every rule stays inside the three governing columns and
			// compares against a literal that is neither empty nor the variable's own CtxName default: on a
			// ROOT tab an unset ref-list field reads as "" in the window (DocumentEvaluatee hands it back),
			// while here the column is simply absent, so the default "X" wins. All three conditions are
			// pinned by ExternalSystem_EndpointTest.VisibilityRules.
			final Boolean visible = visibleIf.evaluate(configuration, OnVariableNotFound.ReturnNoResult);
			return Boolean.TRUE.equals(visible);
		}

		void applyHiddenValue(@NonNull final I_ExternalSystem_Endpoint endpoint)
		{
			hiddenValueAction.accept(endpoint);
		}
	}
}
