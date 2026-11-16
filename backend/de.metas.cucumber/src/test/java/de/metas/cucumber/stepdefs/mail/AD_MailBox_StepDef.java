/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.mail;

import de.metas.common.util.CoalesceUtil;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.X_AD_MailBox;

import java.util.Map;

/**
 * Step definitions for {@link I_AD_MailBox} — the "from" mailbox a document mail is sent through.
 *
 * <p>Supports the {@code $env}-suffix convention (mirrors {@code Mail_StepDef#resolveEnv}) so the
 * SMTP host/port can point at the running Mailpit container via the {@code TEST_SMTP_*} environment
 * variables defined in the infrastructure env files.
 */
@RequiredArgsConstructor
public class AD_MailBox_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final AD_MailBox_StepDefData mailBoxTable;

	/**
	 * Create / upsert an {@link I_AD_MailBox} per data-table row.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_MailBox_ID.Identifier</b> — (required) alias for cross-step reference<br>
	 *   <b>EMail</b> — (required) the FROM address; also the upsert key<br>
	 *   <b>SMTPHost</b> / <b>SMTPHost$env</b> — (required, one of) SMTP host, literal or env-var name<br>
	 *   <b>SMTPPort</b> / <b>SMTPPort$env</b> — (required, one of) SMTP port, literal or env-var name<br>
	 *   <b>IsSmtpAuthorization</b> — (optional) {@code Y}/{@code N}, default {@code N}<br>
	 *   <b>AD_Org_ID</b> — (optional) org id; defaults to the login context<br>
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains AD_MailBox:
	 *   | AD_MailBox_ID.Identifier | EMail                    | SMTPHost$env   | SMTPPort$env   | IsSmtpAuthorization |
	 *   | billingMailbox           | billing@metasfresh.local | TEST_SMTP_HOST | TEST_SMTP_PORT | N                   |
	 * </pre>
	 */
	@Given("metasfresh contains AD_MailBox:")
	public void metasfresh_contains_AD_MailBox(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createOrUpdateMailBox);
	}

	private void createOrUpdateMailBox(@NonNull final DataTableRow row)
	{
		final String email = row.getAsString(I_AD_MailBox.COLUMNNAME_EMail);

		final I_AD_MailBox mailBox = CoalesceUtil.coalesceSuppliersNotNull(
				() -> queryBL.createQueryBuilder(I_AD_MailBox.class)
						.addEqualsFilter(I_AD_MailBox.COLUMNNAME_EMail, email)
						.create()
						.firstOnlyOrNull(I_AD_MailBox.class),
				() -> InterfaceWrapperHelper.newInstance(I_AD_MailBox.class));

		mailBox.setEMail(email);
		mailBox.setType(X_AD_MailBox.TYPE_SMTP);
		mailBox.setSMTPHost(resolveEnv(row, I_AD_MailBox.COLUMNNAME_SMTPHost));
		mailBox.setSMTPPort(Integer.parseInt(resolveEnv(row, I_AD_MailBox.COLUMNNAME_SMTPPort)));
		mailBox.setIsSmtpAuthorization(row.getAsOptionalBoolean(I_AD_MailBox.COLUMNNAME_IsSmtpAuthorization).orElseFalse());

		final String userName = StringUtils.trimBlankToNull(resolveEnvOrNull(row, I_AD_MailBox.COLUMNNAME_UserName));
		if (userName != null)
		{
			mailBox.setUserName(userName);
		}
		final String password = StringUtils.trimBlankToNull(resolveEnvOrNull(row, I_AD_MailBox.COLUMNNAME_Password));
		if (password != null)
		{
			mailBox.setPassword(password);
		}

		row.getAsOptionalInt(I_AD_MailBox.COLUMNNAME_AD_Org_ID).ifPresent(mailBox::setAD_Org_ID);

		InterfaceWrapperHelper.save(mailBox);

		row.getAsIdentifier(I_AD_MailBox.COLUMNNAME_AD_MailBox_ID).putOrReplace(mailBoxTable, mailBox);
	}

	/**
	 * Resolves a column either from the literal {@code <name>} cell or — when absent — from the
	 * system property / environment variable named in the {@code <name>$env} cell.
	 * Mirrors {@code Mail_StepDef#resolveEnv} so SMTP settings can point at the running Mailpit.
	 */
	private static String resolveEnv(@NonNull final DataTableRow row, @NonNull final String name)
	{
		final Map<String, String> map = row.asMap();

		final String value = StringUtils.trimBlankToNull(map.get(name));
		if (value != null)
		{
			return value;
		}

		final String envColumn = name + "$env";
		final String env = StringUtils.trimBlankToNull(map.get(envColumn));
		if (env != null)
		{
			return CoalesceUtil.coalesceSuppliers(
					() -> System.getProperty(env),
					() -> System.getenv(env),
					() -> { throw new AdempiereException("No system property/environment variable '" + env + "' found"); });
		}

		throw new AdempiereException("Either '" + name + "' or '" + envColumn + "' is required");
	}

	/**
	 * Like {@link #resolveEnv} but returns {@code null} when neither the literal {@code <name>} nor the
	 * {@code <name>$env} column is present (for optional columns such as username/password).
	 */
	@javax.annotation.Nullable
	private static String resolveEnvOrNull(@NonNull final DataTableRow row, @NonNull final String name)
	{
		final Map<String, String> map = row.asMap();

		final String value = StringUtils.trimBlankToNull(map.get(name));
		if (value != null)
		{
			return value;
		}
		final String env = StringUtils.trimBlankToNull(map.get(name + "$env"));
		if (env != null)
		{
			return CoalesceUtil.coalesceSuppliers(() -> System.getProperty(env), () -> System.getenv(env));
		}
		return null;
	}
}
