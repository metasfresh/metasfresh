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
 * Keeps an endpoint record free of values the window no longer shows.
 * <p>
 * An endpoint's transport type and the two authentication types decide which of its fields the window
 * renders. Whenever one of them changes, every field the resulting configuration HIDES must end up without
 * a value: a value nobody can see is a value nobody can correct, and {@code ExternalSystemEndpointRepository}
 * keeps handing every one of these columns to the outbound/inbound dispatch.
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
	 * columns {@link #clearFieldsHiddenByTheNewConfiguration(I_ExternalSystem_Endpoint)} triggers on — a
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
	 * how to take its value away. Each {@code displayLogic} is a <b>verbatim copy</b> of that field's
	 * {@code AD_Field.DisplayLogic}; whoever changes one in a migration script changes the string here.
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
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ContentType, VISIBLE_FOR_HTTP,
						endpoint -> endpoint.setContentType(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_IsFileUpload, VISIBLE_FOR_HTTP,
						endpoint -> endpoint.setIsFileUpload(false)),
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
				// via setValue, because the generated setSftpPort(int) cannot express SQL NULL, and a stored
				// 0 satisfies this column's MandatoryLogic -- the window would call a portless endpoint valid
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort, VISIBLE_FOR_SFTP,
						endpoint -> InterfaceWrapperHelper.setValue(endpoint, I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort, null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpUsername, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpUsername(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpRemotePath, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpRemotePath(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpFilenamePattern, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpFilenamePattern(null)),
				// the plain int setter here, unlike SftpPort above and Frequency below: this column carries
				// no MandatoryLogic, so a stored 0 cannot make the window call a poll-less endpoint valid
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPollingIntervalMs, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpPollingIntervalMs(0)),
				// SFTP authentication
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType, VISIBLE_FOR_SFTP,
						endpoint -> endpoint.setSftpAuthType(null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SshPrivateKey,
						"@TransportType/X@='SFTP' & @SftpAuthType/X@='SSH_KEY'",
						endpoint -> endpoint.setSshPrivateKey(null)),
				// LOCAL_FILE transport
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_LocalRootLocation, VISIBLE_FOR_LOCAL_FILE,
						endpoint -> endpoint.setLocalRootLocation(null)),
				// via setValue, for the same reason as SftpPort above: a stored 0 satisfies this column's
				// MandatoryLogic too
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_Frequency, VISIBLE_FOR_LOCAL_FILE,
						endpoint -> InterfaceWrapperHelper.setValue(endpoint, I_ExternalSystem_Endpoint.COLUMNNAME_Frequency, null)),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ImportFileNamePattern, VISIBLE_FOR_LOCAL_FILE,
						endpoint -> endpoint.setImportFileNamePattern(null)));
	}

	private HideableColumn hideable(
			@NonNull final String columnName,
			@NonNull final String displayLogic,
			@NonNull final Consumer<I_ExternalSystem_Endpoint> clearAction)
	{
		final ILogicExpression visibleIf = expressionFactory.compile(displayLogic, ILogicExpression.class);
		return new HideableColumn(columnName, displayLogic, visibleIf, clearAction);
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
	 * Takes the value away from every field the endpoint's new configuration hides.
	 * <p>
	 * One handler for all three governing columns on purpose: a save may change more than one of them at
	 * once, and a condition such as {@code Password}'s spans all three -- a handler keyed on a single column
	 * would decide that field's fate from a part of the change only.
	 */
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = {
			I_ExternalSystem_Endpoint.COLUMNNAME_TransportType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType })
	public void clearFieldsHiddenByTheNewConfiguration(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		assertTransportTypeIsAKnownCode(endpoint);

		// ONE snapshot of the state the record is about to be stored in decides every field: clearing e.g.
		// AuthType must not change the verdict already reached for Password
		final Evaluatee newConfiguration = extractVisibilityGoverningValues(endpoint);

		final ImmutableList<HideableColumn> hiddenColumns = hideableColumns.get().stream()
				.filter(column -> !column.isVisible(newConfiguration))
				.collect(ImmutableList.toImmutableList());

		hiddenColumns.forEach(column -> column.clear(endpoint));
	}

	/**
	 * Refuses a transport code {@link TransportType} has no constant for -- no condition in
	 * {@link #createHideableColumns()} is keyed on it, so the handler would take EVERY hideable column away
	 * in a single save. Refusing adds no restriction: {@code ExternalSystemEndpointRepository#fromRecord}
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

		@NonNull Consumer<I_ExternalSystem_Endpoint> clearAction;

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

		void clear(@NonNull final I_ExternalSystem_Endpoint endpoint)
		{
			clearAction.accept(endpoint);
		}
	}
}
