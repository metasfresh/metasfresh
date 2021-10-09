package de.metas.migration.cli.workspace_migrate;

import java.io.File;

import com.google.common.collect.ImmutableSet;

import de.metas.migration.applier.IScriptsApplierListener;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.migration.cli
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class WorkspaceMigrateConfig
{
	public static final String PROP_DB_URL_DEFAULT = "jdbc:postgresql://localhost/metasfresh";
	public static final String PROP_DB_USERNAME_DEFAULT = "metasfresh";
	public static final String PROP_DB_PASSWORD_DEFAULT = "metasfresh";
	public static final String PROP_LABELS_DEFAULT = "mf15,common";

	@NonNull
	File workspaceDir;

	@NonNull
	@Builder.Default
	String dbUrl = PROP_DB_URL_DEFAULT;

	@NonNull
	@Builder.Default
	String dbUsername = PROP_DB_USERNAME_DEFAULT;

	@NonNull
	@Builder.Default
	String dbPassword = PROP_DB_PASSWORD_DEFAULT;

	boolean dryRunMode;
	boolean skipExecutingAfterScripts;

	@NonNull
	@Builder.Default
	ImmutableSet<Label> labels = Label.ofCommaSeparatedString(PROP_LABELS_DEFAULT);

	public enum OnScriptFailure
	{
		ASK, FAIL;
	}

	@NonNull
	@Builder.Default
	OnScriptFailure onScriptFailure = OnScriptFailure.ASK;
}
