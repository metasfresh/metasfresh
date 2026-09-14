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

package de.metas.cucumber.stepdefs.bpartner;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.metas.bpartner.service.BPartnerNumberService;
import de.metas.cache.CacheMgt;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerUpsertItem;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.api.REST_API_StepDef;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.util.DB;


import javax.annotation.Nullable;
import java.io.IOException;
import java.util.UUID;

import static de.metas.bpartner.service.BPartnerNumberGenerator.SYSCONFIG_CREDITOR_SEQ;
import static de.metas.bpartner.service.BPartnerNumberGenerator.SYSCONFIG_DEBTOR_SEQ;
import static de.metas.bpartner.service.BPartnerNumberGenerator.SYSCONFIG_OVERRIDE;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Shared parameterized step definitions for BPartner debtor/creditor number generation.
 *
 * <p>These steps drive the BPartner V2 upsert REST path end-to-end: sequence setup,
 * sysconfig wiring, upsert request, and response-field assertions.
 *
 * <p>Setup steps manipulate:
 * <ul>
 *   <li>An {@link I_AD_Sequence} row for the given org.</li>
 *   <li>AD_SysConfig {@code de.metas.bpartner.DebtorNoSequence} or {@code CreditorNoSequence}
 *       pointing to that sequence's ID, scoped to test-client + given org.</li>
 *   <li>AD_SysConfig {@code de.metas.bpartner.NumberResolverOverride} for override-function testing.</li>
 * </ul>
 *
 * <p>All sysconfig values are set at {@link ClientId#METASFRESH} client scope and at
 * the specific org resolved from the org Value column.
 *
 * @cucumber.stepdef
 * @cucumber.columns
 *   <b>Given a debtor sequence for org &lt;orgValue&gt; starting at &lt;start&gt;</b> — creates AD_Sequence + sets DebtorNoSequence sysconfig<br>
 *   <b>Given a creditor sequence for org &lt;orgValue&gt; starting at &lt;start&gt;</b> — creates AD_Sequence + sets CreditorNoSequence sysconfig<br>
 *   <b>Given org &lt;orgValue&gt; uses number resolver "&lt;fn&gt;"</b> — sets NumberResolverOverride sysconfig<br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor|neither|both&gt; "&lt;id&gt;"</b> — {@code "both"} sets isCustomer=true AND isVendor=true<br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor|both&gt; "&lt;id&gt;" with debtorId &lt;n&gt;</b><br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor|both&gt; "&lt;id&gt;" with creditorId &lt;n&gt;</b><br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor|neither|both&gt; "&lt;id&gt;" in org &lt;orgValue&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.debtorId is &lt;expected&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.debtorId is null</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.creditorId is &lt;expected&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.creditorId is null</b><br>
 *   <b>Then the upsert is rejected</b><br>
 * @cucumber.example
 * <pre>
 * Given a debtor sequence for org "001" starting at 10000
 * When I upsert a "non-company" "customer" "TC1-cust"
 * Then responseItems[0].responseBPartnerItem.debtorId is 10000
 * </pre>
 */
@RequiredArgsConstructor
public class BPartnerNumberGen_StepDef
{
	@NonNull private final TestContext testContext;
	@NonNull private final REST_API_StepDef restApiStepDef;
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Per-scenario-instance unique suffix appended to every external identifier, so each run
	 * creates fresh business partners. The local provided-infrastructure DB is NOT reset between
	 * runs; without this, an {@code ifExists=UPDATE_MERGE} upsert would resolve a prior run's
	 * partner and return its already-assigned number (e.g. the "no config" case would see a stale
	 * number instead of null). On CI's fresh preloaded DB this is harmless.
	 */
	private final String runNonce = UUID.randomUUID().toString().replace("-", "").substring(0, 8);

	// ─── GIVEN: sequence / override setup ────────────────────────────────────

	/**
	 * Creates an {@link I_AD_Sequence} and configures {@code de.metas.bpartner.DebtorNoSequence}
	 * sysconfig to point to it for the given org.
	 *
	 * <p>A UUID suffix is appended to the sequence name to prevent cross-scenario contamination
	 * on the same executor.
	 *
	 * @param orgValue Value column of the org (e.g. {@code "001"})
	 * @param startNo  starting value; both {@code StartNo} and {@code CurrentNext} are set to this
	 */
	@Given("a debtor sequence for org {string} starting at {int}")
	public void a_debtor_sequence_for_org_starting_at(
			@NonNull final String orgValue,
			final int startNo)
	{
		final int orgId = resolveOrgId(orgValue);
		final int seqId = createSequence("debtor_" + orgValue + "_" + UUID.randomUUID(), startNo);
		setSysconfigIntForOrg(SYSCONFIG_DEBTOR_SEQ, seqId, orgId);
	}

	/**
	 * Creates an {@link I_AD_Sequence} and configures {@code de.metas.bpartner.CreditorNoSequence}
	 * sysconfig to point to it for the given org.
	 *
	 * @param orgValue Value column of the org (e.g. {@code "001"})
	 * @param startNo  starting value; both {@code StartNo} and {@code CurrentNext} are set to this
	 */
	@Given("a creditor sequence for org {string} starting at {int}")
	public void a_creditor_sequence_for_org_starting_at(
			@NonNull final String orgValue,
			final int startNo)
	{
		final int orgId = resolveOrgId(orgValue);
		final int seqId = createSequence("creditor_" + orgValue + "_" + UUID.randomUUID(), startNo);
		setSysconfigIntForOrg(SYSCONFIG_CREDITOR_SEQ, seqId, orgId);
	}

	/**
	 * Sets {@code de.metas.bpartner.NumberResolverOverride} sysconfig to the given DB function
	 * name for the given org.
	 *
	 * <p><b>Runtime dependency:</b> the DB function named {@code metas_bpartner_numbgen_test_override}
	 * must exist in the database before any override-function scenario runs. The function must accept the
	 * standard signature {@code (p_ad_org_id int, p_c_bpartner_id int, p_iscustomer bool, p_isvendor bool,
	 * p_iscompany bool, p_kind text, p_explicit int)} and return a fixed sentinel value (e.g. 999)
	 * so the caller can assert the exact returned number. The function is not created by this step.
	 *
	 * @param orgValue     Value column of the org
	 * @param functionName plain or schema-qualified SQL identifier of the override function
	 */
	@Given("org {string} uses number resolver {string}")
	public void org_uses_number_resolver(
			@NonNull final String orgValue,
			@NonNull final String functionName)
	{
		final int orgId = resolveOrgId(orgValue);
		if (functionName.trim().isEmpty())
		{
			// Clearing the override: delete the org row (AD_SysConfig.Value is NOT NULL, so a blank
			// value cannot be stored) — the generator then falls back to the blank System base row.
			deleteOrgSysconfig(SYSCONFIG_OVERRIDE, orgId);
		}
		else
		{
			setSysconfigStringForOrg(SYSCONFIG_OVERRIDE, functionName, orgId);
		}
	}

	/** Deletes the org-level AD_SysConfig row for a single key + org, then flushes the cache. */
	private void deleteOrgSysconfig(@NonNull final String name, final int orgId)
	{
		queryBL.createQueryBuilder(I_AD_SysConfig.class)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_Name, name)
				.addEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, orgId)
				.create()
				.delete();
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	/**
	 * Creates (or replaces) the override-function {@code functionName} in the database so TC9 can
	 * call it without relying on pre-existing seed-DB state.
	 *
	 * <p>The function accepts the standard BPartner number-generation signature and returns the
	 * given {@code returnValue} unconditionally. Drop-and-create with {@code OR REPLACE} is safe
	 * because the name is test-owned and guaranteed not to conflict with production functions
	 * (the metasfresh function-name convention uses the {@code metas_} prefix for internal names).
	 *
	 * @param functionName plain SQL identifier of the function (no schema prefix — created in {@code public})
	 * @param returnValue  the fixed integer the function should return, for assertion in the scenario
	 */
	@Given("the override test function {string} returns {int}")
	public void the_override_test_function_returns(
			@NonNull final String functionName,
			final int returnValue)
	{
		// Validate with the exact same rule the production caller uses (reused, not re-declared).
		if (!BPartnerNumberService.FUNCTION_NAME_PATTERN.matcher(functionName).matches())
		{
			throw new IllegalArgumentException("Test override function name is not a valid SQL identifier: " + functionName);
		}
		// No framework API creates a DB function in a test — raw DDL is required here (the function name is
		// validated above; returnValue is an int, not interpolated user text).
		final String sql = "CREATE OR REPLACE FUNCTION " + functionName + "("
				+ "p_ad_org_id int, p_iscompany bool, p_kind text, p_explicit int"
				+ ") RETURNS int LANGUAGE sql AS $$ SELECT " + returnValue + " $$";
		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_None);
	}

	/**
	 * Creates (or replaces) an override function that unconditionally {@code RAISE EXCEPTION}s — a proxy
	 * for a real resolver hitting an error (e.g. a per-range ceiling). Proves the {@code RAISE} surfaces:
	 * it becomes a {@code SQLException} → {@code DBException} in {@code BPartnerNumberService.callOverrideFunction},
	 * which propagates out of the {@code C_BPartner} save and rejects the upsert (rather than being swallowed).
	 *
	 * @param functionName plain SQL identifier (no schema prefix — created in {@code public})
	 */
	@Given("the override test function {string} raises an error")
	public void the_override_test_function_raises_an_error(@NonNull final String functionName)
	{
		if (!BPartnerNumberService.FUNCTION_NAME_PATTERN.matcher(functionName).matches())
		{
			throw new IllegalArgumentException("Test override function name is not a valid SQL identifier: " + functionName);
		}
		// plpgsql (not sql) so the body can RAISE; raw DDL — no framework API creates a function in a test.
		final String sql = "CREATE OR REPLACE FUNCTION " + functionName + "("
				+ "p_ad_org_id int, p_iscompany bool, p_kind text, p_explicit int"
				+ ") RETURNS int LANGUAGE plpgsql AS $$ BEGIN RAISE EXCEPTION 'bpartner number override failed (test)'; END $$";
		DB.executeUpdateAndThrowExceptionOnFail(sql, ITrx.TRXNAME_None);
	}

	// ─── WHEN: upsert ─────────────────────────────────────────────────────────

	/**
	 * Sends a BPartner V2 upsert PUT request.
	 *
	 * <p>Role mapping:
	 * <ul>
	 *   <li>{@code "customer"} → {@code "customer": true, "vendor": false}</li>
	 *   <li>{@code "vendor"}   → {@code "customer": false, "vendor": true}</li>
	 *   <li>{@code "neither"}  → {@code "customer": false, "vendor": false}</li>
	 * </ul>
	 * Company mapping: {@code "company"} adds {@code "companyName"}; {@code "non-company"} omits it.
	 * The request uses org {@value StepDefConstants#ORG_VALUE}.
	 *
	 * @param companyType {@code "company"} or {@code "non-company"}
	 * @param role        {@code "customer"}, {@code "vendor"}, or {@code "neither"}
	 * @param id          external identifier (used as name and in the external reference)
	 */
	@When("I upsert a {string} {string} {string}")
	public void i_upsert_a_bpartner(
			@NonNull final String companyType,
			@NonNull final String role,
			@NonNull final String id) throws IOException
	{
		doUpsert(companyType, role, id, null, null, StepDefConstants.ORG_VALUE);
	}

	/**
	 * Upsert variant with an explicit debtor number.
	 *
	 * @param companyType {@code "company"} or {@code "non-company"}
	 * @param role        {@code "customer"}, {@code "vendor"}, or {@code "neither"}
	 * @param id          external identifier
	 * @param debtorId    explicit debtor number to include in the request payload
	 */
	@When("I upsert a {string} {string} {string} with debtorId {int}")
	public void i_upsert_a_bpartner_with_debtor_id(
			@NonNull final String companyType,
			@NonNull final String role,
			@NonNull final String id,
			final int debtorId) throws IOException
	{
		doUpsert(companyType, role, id, debtorId, null, StepDefConstants.ORG_VALUE);
	}

	/**
	 * Upsert variant with an explicit creditor number.
	 *
	 * @param companyType {@code "company"} or {@code "non-company"}
	 * @param role        {@code "customer"}, {@code "vendor"}, or {@code "neither"}
	 * @param id          external identifier
	 * @param creditorId  explicit creditor number to include in the request payload
	 */
	@When("I upsert a {string} {string} {string} with creditorId {int}")
	public void i_upsert_a_bpartner_with_creditor_id(
			@NonNull final String companyType,
			@NonNull final String role,
			@NonNull final String id,
			final int creditorId) throws IOException
	{
		doUpsert(companyType, role, id, null, creditorId, StepDefConstants.ORG_VALUE);
	}

	/**
	 * Upsert variant that targets a specific org, for multi-org isolation testing.
	 *
	 * @param companyType {@code "company"} or {@code "non-company"}
	 * @param role        {@code "customer"}, {@code "vendor"}, or {@code "neither"}
	 * @param id          external identifier
	 * @param orgValue    Value column of the org to use as the path parameter
	 */
	@When("I upsert a {string} {string} {string} in org {string}")
	public void i_upsert_a_bpartner_in_org(
			@NonNull final String companyType,
			@NonNull final String role,
			@NonNull final String id,
			@NonNull final String orgValue) throws IOException
	{
		doUpsert(companyType, role, id, null, null, orgValue);
	}

	// ─── THEN: response assertions ────────────────────────────────────────────

	/**
	 * Asserts that {@code responseItems[index].responseBPartnerItem.debtorId} equals {@code expected}.
	 *
	 * @param index    zero-based index into {@code responseItems}
	 * @param expected expected exact value
	 */
	@Then("responseItems[{int}].responseBPartnerItem.debtorId is {int}")
	public void responseItems_responseBPartnerItem_debtorId_is(
			final int index,
			final int expected) throws JsonProcessingException
	{
		final Integer debtorId = getResponseBPartnerUpsertItem(index).getDebtorId();
		assertThat(debtorId).as("responseItems[%d].responseBPartnerItem.debtorId", index).isEqualTo(expected);
	}

	/**
	 * Asserts that {@code responseItems[index].responseBPartnerItem.debtorId} is {@code null} (not set).
	 *
	 * @param index zero-based index
	 */
	@Then("responseItems[{int}].responseBPartnerItem.debtorId is null")
	public void responseItems_responseBPartnerItem_debtorId_is_null(
			final int index) throws JsonProcessingException
	{
		final Integer debtorId = getResponseBPartnerUpsertItem(index).getDebtorId();
		assertThat(debtorId).as("responseItems[%d].responseBPartnerItem.debtorId", index).isNull();
	}

	/**
	 * Asserts that {@code responseItems[index].responseBPartnerItem.creditorId} equals {@code expected}.
	 *
	 * @param index    zero-based index
	 * @param expected expected exact value
	 */
	@Then("responseItems[{int}].responseBPartnerItem.creditorId is {int}")
	public void responseItems_responseBPartnerItem_creditorId_is(
			final int index,
			final int expected) throws JsonProcessingException
	{
		final Integer creditorId = getResponseBPartnerUpsertItem(index).getCreditorId();
		assertThat(creditorId).as("responseItems[%d].responseBPartnerItem.creditorId", index).isEqualTo(expected);
	}

	/**
	 * Asserts that {@code responseItems[index].responseBPartnerItem.creditorId} is {@code null} (not set).
	 *
	 * @param index zero-based index
	 */
	@Then("responseItems[{int}].responseBPartnerItem.creditorId is null")
	public void responseItems_responseBPartnerItem_creditorId_is_null(
			final int index) throws JsonProcessingException
	{
		final Integer creditorId = getResponseBPartnerUpsertItem(index).getCreditorId();
		assertThat(creditorId).as("responseItems[%d].responseBPartnerItem.creditorId", index).isNull();
	}

	/**
	 * Asserts that the most recent upsert returned a non-2xx (error) HTTP status.
	 *
	 * <p>The preceding "When I upsert…" step sends the request without an expected status-code
	 * guard so the response is captured regardless of success or failure. This step then checks
	 * that the server returned a 4xx or 5xx status, which is the expected outcome when the
	 * unique-index constraint on {@code C_BPartner.DebtorId} / {@code C_BPartner.CreditorId} fires.
	 */
	@Then("the upsert is rejected")
	public void the_upsert_is_rejected()
	{
		final Integer statusCode = testContext.getApiResponse().getStatusCode();
		assertThat(statusCode)
				.as("Expected upsert to be rejected (4xx/5xx) — statusCode must not be null and must be >= 400")
				.isNotNull()
				.isGreaterThanOrEqualTo(400);
	}

	// ─── helpers ─────────────────────────────────────────────────────────────

	/**
	 * Core upsert implementation: builds the JSON payload and sends the PUT request.
	 * The response is stored in {@link TestContext} and is available to subsequent assertion steps.
	 *
	 * <p>The request is sent <em>without</em> an {@code expectedStatusCode} constraint so
	 * failure-testing scenarios can capture the error response via {@link #the_upsert_is_rejected()}.
	 *
	 * @param companyType "company" or "non-company"
	 * @param role        "customer", "vendor", or "neither"
	 * @param id          external identifier
	 * @param debtorId    optional explicit debtor number (null = omit from payload)
	 * @param creditorId  optional explicit creditor number (null = omit from payload)
	 * @param orgValue    org Value to use as the API path parameter
	 */
	private void doUpsert(
			@NonNull final String companyType,
			@NonNull final String role,
			@NonNull final String id,
			@Nullable final Integer debtorId,
			@Nullable final Integer creditorId,
			@NonNull final String orgValue) throws IOException
	{
		final boolean isCompany = "company".equalsIgnoreCase(companyType);
		final boolean isCustomer = "customer".equalsIgnoreCase(role) || "both".equalsIgnoreCase(role);
		final boolean isVendor = "vendor".equalsIgnoreCase(role) || "both".equalsIgnoreCase(role);

		final StringBuilder bpartnerJson = new StringBuilder();
		bpartnerJson.append("{");
		bpartnerJson.append("\"name\":\"").append(id).append("\",");
		bpartnerJson.append("\"language\":\"de\",");
		// Let the V2 upsert auto-create a per-org group by Value (idempotent — mirrors the proven
		// sibling bpartnerV2OrgConsistency.feature). A unique per-org name avoids reusing the
		// client-wide "Standard" group, whose Value would collide (C_BP_Group.Value unique) when a
		// freshly-created org (e.g. TC8's 002) tries to re-create it.
		bpartnerJson.append("\"group\":\"NumGen Test Group ").append(orgValue).append("\",");
		bpartnerJson.append("\"customer\":").append(isCustomer).append(",");
		bpartnerJson.append("\"vendor\":").append(isVendor);
		if (isCompany)
		{
			bpartnerJson.append(",\"companyName\":\"").append(id).append(" Corp\"");
		}
		if (debtorId != null)
		{
			bpartnerJson.append(",\"debtorId\":").append(debtorId);
		}
		if (creditorId != null)
		{
			bpartnerJson.append(",\"creditorId\":").append(creditorId);
		}
		bpartnerJson.append("}");

		// Use the registered "Test_System" external system (proven in sibling bpartner V2 features);
		// dash-safe reference because scenario ids contain dashes (e.g. "TC1-cust"). The per-scenario
		// runNonce keeps each run's partners fresh (see field docs).
		final String externalIdentifier = "ext-Test_System-" + id.replace("-", "_") + "_" + runNonce;

		final String payload = "{"
				+ "\"requestItems\":["
				+ "{"
				+ "\"bpartnerIdentifier\":\"" + externalIdentifier + "\","
				+ "\"bpartnerComposite\":{"
				+ "\"bpartner\":" + bpartnerJson
				+ "}"
				+ "}"
				+ "],"
				+ "\"syncAdvise\":{"
				+ "\"ifNotExists\":\"CREATE\","
				+ "\"ifExists\":\"UPDATE_MERGE\""
				+ "}"
				+ "}";

		testContext.setRequestPayload(payload);

		// expectedStatusCode is null — the HTTP call always completes without asserting the status here.
		// Assertion steps (debtorId/creditorId checks) validate success; "the upsert is rejected" validates failures.
		restApiStepDef.performHTTPRequest(
				restApiStepDef.newAPIRequest()
						.endpointPath("api/v2/bpartner/" + orgValue)
						.method("PUT")
						.payload(payload)
						.expectedStatusCode(null)
						.build()
		);
	}

	/**
	 * Deserializes the last API response body as {@link JsonResponseBPartnerCompositeUpsert}
	 * and returns the {@link JsonResponseBPartnerUpsertItem} at the given index.
	 *
	 * @param index zero-based index into {@code responseItems}
	 * @throws AssertionError when the response does not contain enough items
	 */
	private JsonResponseBPartnerUpsertItem getResponseBPartnerUpsertItem(final int index) throws JsonProcessingException
	{
		final JsonResponseBPartnerCompositeUpsert response =
				testContext.getApiResponseBodyAs(JsonResponseBPartnerCompositeUpsert.class);

		assertThat(response.getResponseItems())
				.as("responseItems must have at least %d element(s)", index + 1)
				.hasSizeGreaterThan(index);

		final JsonResponseBPartnerCompositeUpsertItem item = response.getResponseItems().get(index);
		assertThat(item.getResponseBPartnerItem())
				.as("responseItems[%d].responseBPartnerItem must not be null", index)
				.isNotNull();

		return item.getResponseBPartnerItem();
	}

	/**
	 * Creates a fresh {@link I_AD_Sequence} with the given name and returns its {@code AD_Sequence_ID}.
	 *
	 * <p>Both {@code StartNo} and {@code CurrentNext} are set to {@code startNo} so the first
	 * drawn number equals {@code startNo}.
	 *
	 * @param name    unique sequence name (caller appends a UUID to guarantee uniqueness)
	 * @param startNo starting / current-next value
	 * @return the persisted {@code AD_Sequence_ID}
	 */
	private int createSequence(@NonNull final String name, final int startNo)
	{
		final I_AD_Sequence seq = newInstance(I_AD_Sequence.class);
		seq.setName(name);
		seq.setIsTableID(false);
		seq.setIsAutoSequence(true);
		seq.setStartNo(startNo);
		seq.setCurrentNext(startNo);
		saveRecord(seq);
		return seq.getAD_Sequence_ID();
	}

	/**
	 * Sets an integer-valued AD_SysConfig for {@link ClientId#METASFRESH} + the given org,
	 * then flushes the sysconfig cache so the generator picks up the new value immediately.
	 *
	 * @param name  sysconfig Name
	 * @param value integer value (typically an AD_Sequence_ID)
	 * @param orgId repo-ID of the target org
	 */
	private void setSysconfigIntForOrg(
			@NonNull final String name,
			final int value,
			final int orgId)
	{
		sysConfigBL.setValue(name, value, ClientId.METASFRESH, OrgId.ofRepoId(orgId));
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	/**
	 * Sets a String-valued AD_SysConfig for {@link ClientId#METASFRESH} + the given org,
	 * then flushes the sysconfig cache.
	 *
	 * @param name    sysconfig Name
	 * @param value   string value
	 * @param orgId   repo-ID of the target org
	 */
	private void setSysconfigStringForOrg(
			@NonNull final String name,
			@NonNull final String value,
			final int orgId)
	{
		sysConfigBL.setValue(name, value, ClientId.METASFRESH, OrgId.ofRepoId(orgId));
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);
	}

	/**
	 * Isolation reset: blanks the per-org BPartner number-generation sysconfigs (override, debtor-seq,
	 * creditor-seq) for the orgs the scenarios use ({@code 001} and {@code 002}). Run from the Background
	 * so a value configured by an earlier scenario cannot leak into a later one on the same executor —
	 * essential for the "no config means no number" case. (The master on/off toggle is handled in the
	 * feature file via the standard {@code set sys config} step, not here.)
	 */
	@Given("the BPartner number-generation config is reset")
	public void bpartner_number_generation_config_is_reset()
	{
		// Delete every org-level (AD_Org_ID != 0) row for the three keys, leaving only the blank
		// System base rows (AD_Org_ID = 0) the migration ships. The generator then sees "no value"
		// for every org until a scenario configures one. (Blanking Value to '' is not an option —
		// AD_SysConfig.Value is NOT NULL and setValue('') stores NULL.)
		queryBL.createQueryBuilder(I_AD_SysConfig.class)
				.addInArrayFilter(I_AD_SysConfig.COLUMNNAME_Name, SYSCONFIG_OVERRIDE, SYSCONFIG_DEBTOR_SEQ, SYSCONFIG_CREDITOR_SEQ)
				.addNotEqualsFilter(I_AD_SysConfig.COLUMNNAME_AD_Org_ID, 0)
				.create()
				.delete();
		CacheMgt.get().reset(I_AD_SysConfig.Table_Name);

		// Free the debtor/creditor numbers held by this feature's partners from earlier runs. The local
		// provided-infrastructure DB is not reset between runs, so a fixed explicit number (e.g. 41000)
		// or a sequence value re-drawn at the same StartNo would otherwise collide with a persisted
		// partner via the (debtorid|creditorid, ad_org_id) unique index. Raw UPDATE bypasses the model
		// interceptor (no regeneration). On CI's fresh preloaded DB this matches nothing and is a no-op.
		DB.executeUpdateAndThrowExceptionOnFail(
				"UPDATE C_BPartner SET DebtorId = 0, CreditorId = 0"
						+ " WHERE Name LIKE 'TC%'"
						+ " AND AD_Org_ID IN (SELECT AD_Org_ID FROM AD_Org WHERE Value IN ('001','002'))",
				ITrx.TRXNAME_ThreadInherited);
		CacheMgt.get().reset("C_BPartner");
	}

	/**
	 * Resolves the {@code AD_Org_ID} for the org identified by its {@code Value} column.
	 *
	 * @param orgValue org Value (e.g. {@code "001"})
	 * @throws AdempiereException when no active org is found
	 */
	private int resolveOrgId(@NonNull final String orgValue)
	{
		return queryBL.createQueryBuilder(I_AD_Org.class)
				.addEqualsFilter(I_AD_Org.COLUMNNAME_Value, orgValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyOptional(I_AD_Org.class)
				.map(I_AD_Org::getAD_Org_ID)
				.orElseThrow(() -> new AdempiereException("No active AD_Org found for Value=" + orgValue));
	}
}
