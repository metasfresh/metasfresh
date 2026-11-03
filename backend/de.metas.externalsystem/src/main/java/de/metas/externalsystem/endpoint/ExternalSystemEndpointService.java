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

package de.metas.externalsystem.endpoint;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.endpoint.ExternalSystemEndpointRepository.ColumnValue;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.expression.api.IExpressionEvaluator.OnVariableNotFound;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluatees;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * Resets every endpoint field the window no longer shows to the value a newly created record would carry:
 * whenever the transport type or one of the two authentication types changes, each field the resulting
 * configuration HIDES is set back to its {@code AD_Column.DefaultValue}, or to no value where the column
 * carries none. {@code AD_Column.DefaultValue} itself fires on record creation only, which is why this
 * service has to apply it.
 * <p>
 * <b>Scope: only columns whose {@code AD_Field} is active.</b> A column no configuration renders is hidden
 * by none either, and resetting it would destroy a value with no field left to restore it from.
 * {@code Type} is the case in point: its field is inactive, and its {@code AD_Column.MandatoryLogic} is
 * {@code @TransportType/X@='HTTP'} -- so clearing it would leave every HTTP endpoint unsaveable.
 * <p>
 * <b>Assumption: a save never arrives with a half-configured transport</b> -- {@code TransportType=SFTP}
 * with {@code SftpAuthType} still unset would read as "not password authentication" and take the password
 * away. The SFTP columns' {@code AD_Column.MandatoryLogic} rules that out for a WINDOW save, but
 * {@link de.metas.externalsystem.endpoint.interceptor.ExternalSystem_Endpoint} fires on every
 * {@code save()}: a writer outside a window is not covered.
 */
@Service
@RequiredArgsConstructor
public class ExternalSystemEndpointService
{
	/**
	 * The columns whose values decide what the window shows, and exactly the columns
	 * {@link #resetFieldsHiddenByTheNewConfiguration(I_ExternalSystem_Endpoint)} is triggered on: a
	 * {@link HideableColumn#getDisplayLogic() display logic} referencing a fourth column would not be
	 * re-evaluated when that column changed. {@code ExternalSystemEndpointServiceTest.VisibilityRules} pins
	 * this.
	 */
	@VisibleForTesting
	static final ImmutableSet<String> VISIBILITY_GOVERNING_COLUMN_NAMES = ImmutableSet.of(
			I_ExternalSystem_Endpoint.COLUMNNAME_TransportType,
			I_ExternalSystem_Endpoint.COLUMNNAME_AuthType,
			I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType);

	@NonNull private final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);

	@NonNull private final ExternalSystemEndpointRepository endpointRepository;

	/**
	 * Every column this endpoint's window can hide, with the condition it is shown under and the value being
	 * hidden leaves behind. Each {@code displayLogic} is a <b>verbatim copy</b> of that field's
	 * {@code AD_Field.DisplayLogic} and each {@link #hideableWithColumnDefault} value of that column's
	 * {@code AD_Column.DefaultValue}, so a migration script changing either must change the copy here;
	 * {@code externalSystemEndpointDisplayLogic.feature} holds both against the live dictionary. A column
	 * with no display logic is always visible and does not belong here (e.g. {@code IsArrayFanOut}).
	 * <p>
	 * Lazily built: compiling a logic expression asks a sysconfig, not necessarily answerable while this
	 * bean is being constructed.
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
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_HttpEndPoint, VISIBLE_FOR_HTTP),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_OutboundHttpMethod, VISIBLE_FOR_HTTP),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_ContentType, VISIBLE_FOR_HTTP,
						"application/json"),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_IsFileUpload, VISIBLE_FOR_HTTP,
						false),
				// HTTP authentication
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_AuthType, VISIBLE_FOR_HTTP),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_AuthToken,
						"@TransportType/X@='HTTP' & @AuthType/X@='Token'"),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_LoginUsername,
						"@TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='Basic' | @AuthType/X@='OAuth2')"),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_Password,
						"(@TransportType/X@='HTTP' & @AuthType/X@='Basic') | (@TransportType/X@='SFTP' & @SftpAuthType/X@='PASSWORD') | (@TransportType/X@='HTTP' & @AuthType/X@='OAuth2') | (@TransportType/X@='HTTP' & @AuthType/X@='OAuth')"),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ClientId,
						"@TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='OAuth2')"),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ClientSecret,
						"@TransportType/X@='HTTP' & (@AuthType/X@='OAuth' | @AuthType/X@='OAuth2')"),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SasSignature,
						"@TransportType/X@='HTTP' & @AuthType/X@='SAS'"),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_OAuthTokenUrl, VISIBLE_FOR_HTTP_OAUTH2),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_OAuthScope, VISIBLE_FOR_HTTP_OAUTH2),
				// SFTP transport
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpHost, VISIBLE_FOR_SFTP),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPort, VISIBLE_FOR_SFTP,
						22),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpUsername, VISIBLE_FOR_SFTP),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpRemotePath, VISIBLE_FOR_SFTP),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpFilenamePattern, VISIBLE_FOR_SFTP),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_SftpPollingIntervalMs, VISIBLE_FOR_SFTP,
						60_000),
				// SFTP authentication
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SftpAuthType, VISIBLE_FOR_SFTP),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_SshPrivateKey,
						"@TransportType/X@='SFTP' & @SftpAuthType/X@='SSH_KEY'"),
				// LOCAL_FILE transport
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_LocalRootLocation, VISIBLE_FOR_LOCAL_FILE),
				hideableWithColumnDefault(I_ExternalSystem_Endpoint.COLUMNNAME_Frequency, VISIBLE_FOR_LOCAL_FILE,
						60_000),
				hideable(I_ExternalSystem_Endpoint.COLUMNNAME_ImportFileNamePattern, VISIBLE_FOR_LOCAL_FILE));
	}

	/** A column with no {@code AD_Column.DefaultValue}: hiding it leaves it with no value. */
	private HideableColumn hideable(
			@NonNull final String columnName,
			@NonNull final String displayLogic)
	{
		return new HideableColumn(columnName, displayLogic, compile(displayLogic), null);
	}

	/**
	 * A column that carries an {@code AD_Column.DefaultValue}: hiding it puts that value back.
	 * {@code columnDefault} is the dictionary's value in the type the column stores -- {@code 22}, not
	 * {@code "22"}, and {@code false} for {@code 'N'}.
	 */
	private HideableColumn hideableWithColumnDefault(
			@NonNull final String columnName,
			@NonNull final String displayLogic,
			@NonNull final Object columnDefault)
	{
		return new HideableColumn(columnName, displayLogic, compile(displayLogic), columnDefault);
	}

	private ILogicExpression compile(@NonNull final String displayLogic)
	{
		return expressionFactory.compile(displayLogic, ILogicExpression.class);
	}

	/**
	 * The {@code AD_Field.DisplayLogic} copy this class carries, per column it can hide. Public for
	 * {@code externalSystemEndpointDisplayLogic.feature} -- the only test home that runs against a database
	 * with the migration scripts applied.
	 */
	@VisibleForTesting
	public ImmutableMap<String, String> getDisplayLogicByColumnName()
	{
		return hideableColumns.get().stream()
				.collect(ImmutableMap.toImmutableMap(HideableColumn::getColumnName, HideableColumn::getDisplayLogic));
	}

	/**
	 * The {@code AD_Column.DefaultValue} copy this class carries; an absent column is claimed to have no
	 * default, which the same reader as {@link #getDisplayLogicByColumnName()} checks.
	 */
	@VisibleForTesting
	public ImmutableMap<String, Object> getColumnDefaultByColumnName()
	{
		return hideableColumns.get().stream()
				.filter(column -> column.getValueWhenHidden() != null)
				.collect(ImmutableMap.toImmutableMap(HideableColumn::getColumnName, HideableColumn::getValueWhenHidden));
	}

	/** Puts every field the endpoint's new configuration hides back to its column default. */
	public void resetFieldsHiddenByTheNewConfiguration(@NonNull final I_ExternalSystem_Endpoint endpoint)
	{
		assertTransportTypeIsAKnownCode(endpoint);

		// ONE snapshot of the state the record is about to be stored in decides every field: clearing e.g.
		// AuthType must not change the verdict already reached for Password
		final Evaluatee newConfiguration = extractVisibilityGoverningValues(endpoint);

		final ImmutableList<ColumnValue> hiddenColumnValues = hideableColumns.get().stream()
				.filter(column -> !column.isVisible(newConfiguration))
				.map(column -> ColumnValue.of(column.getColumnName(), column.getValueWhenHidden()))
				.collect(ImmutableList.toImmutableList());

		endpointRepository.setColumnValues(endpoint, hiddenColumnValues);
	}

	/**
	 * Refuses a transport code {@link TransportType} has no constant for -- no condition in
	 * {@link #createHideableColumns()} is keyed on it, so the reset would hit EVERY hideable column in a
	 * single save. No new restriction: {@code ExternalSystemEndpointRepository#fromRecord} resolves the very
	 * same {@link TransportType#ofCode(String)}, so such a record would be unloadable anyway. A known code
	 * with no rule keyed on it passes here; that is
	 * {@code ExternalSystemEndpointServiceTest.VisibilityRules}' separate guarantee.
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
	 * which no rule compares against (see {@link HideableColumn#isVisible}).
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
		 * {@code null} where the dictionary gives the column no default -- hiding then takes the value away
		 */
		@Nullable Object valueWhenHidden;

		boolean isVisible(@NonNull final Evaluatee configuration)
		{
			// Agrees with the window only while every rule stays inside the three governing columns and
			// compares against a literal that is neither empty nor the variable's own CtxName default: on a
			// ROOT tab an unset ref-list field reads as "" in the window, while here the column is simply
			// absent, so the default "X" wins. Pinned by ExternalSystemEndpointServiceTest.VisibilityRules.
			final Boolean visible = visibleIf.evaluate(configuration, OnVariableNotFound.ReturnNoResult);
			return Boolean.TRUE.equals(visible);
		}
	}
}
