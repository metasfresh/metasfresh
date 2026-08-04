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
import de.metas.cache.CacheMgt;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsert;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerCompositeUpsertItem;
import de.metas.common.bpartner.v2.response.JsonResponseBPartnerUpsertItem;
import de.metas.cucumber.stepdefs.context.TestContext;
import de.metas.cucumber.stepdefs.api.REST_API_StepDef;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Sequence;
import org.compiere.model.I_AD_SysConfig;

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
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor|neither&gt; "&lt;id&gt;"</b><br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor&gt; "&lt;id&gt;" with debtorId &lt;n&gt;</b><br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor&gt; "&lt;id&gt;" with creditorId &lt;n&gt;</b><br>
 *   <b>When I upsert a &lt;company|non-company&gt; &lt;customer|vendor&gt; "&lt;id&gt;" in org &lt;orgValue&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.debtorId is &lt;expected&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.debtorId is within &lt;from&gt;..&lt;to&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.debtorId is null</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.creditorId is &lt;expected&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.creditorId is within &lt;from&gt;..&lt;to&gt;</b><br>
 *   <b>Then responseItems[&lt;i&gt;].responseBPartnerItem.creditorId is null</b><br>
 *   <b>Then the upsert is rejected</b><br>
 * @cucumber.example
 * <pre>
 * Given a debtor sequence for org "001" starting at 10000
 * When I upsert a "non-company" "customer" "TC1-cust"
 * Then responseItems[0].responseBPartnerItem.debtorId is within 10000..10099
 * </pre>
 */
public class BPartnerNumberGen_StepDef
{
	/** Value column of the default org in the standard seed DB. */
	private static final String DEFAULT_ORG_VALUE = "001";

	private final TestContext testContext;
	private final REST_API_StepDef restApiStepDef;
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public BPartnerNumberGen_StepDef(
			@NonNull final TestContext testContext,
			@NonNull final REST_API_StepDef restApiStepDef)
	{
		this.testContext = testContext;
		this.restApiStepDef = restApiStepDef;
	}

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
	 * <p><b>Runtime dependency (TC9):</b> the DB function named {@code metas_bpartner_numbgen_test_override}
	 * must exist in the database before this scenario runs. The function must accept the standard
	 * signature {@code (p_ad_org_id int, p_c_bpartner_id int, p_iscustomer bool, p_isvendor bool,
	 * p_iscompany bool, p_kind text, p_explicit int)} and return a fixed sentinel value (e.g. 999)
	 * so TC9 can assert the exact returned number. The function is not created by this step.
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
		setSysconfigStringForOrg(SYSCONFIG_OVERRIDE, functionName, orgId);
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
	 * The request uses org {@value #DEFAULT_ORG_VALUE}.
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
		doUpsert(companyType, role, id, null, null, DEFAULT_ORG_VALUE);
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
		doUpsert(companyType, role, id, debtorId, null, DEFAULT_ORG_VALUE);
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
		doUpsert(companyType, role, id, null, creditorId, DEFAULT_ORG_VALUE);
	}

	/**
	 * Upsert variant under a specific org (TC8 multi-org test).
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
	 * Asserts that {@code responseItems[index].responseBPartnerItem.debtorId} is in the inclusive range [{@code from}..{@code to}].
	 *
	 * @param index zero-based index
	 * @param from  inclusive lower bound
	 * @param to    inclusive upper bound
	 */
	@Then("responseItems[{int}].responseBPartnerItem.debtorId is within {int}..{int}")
	public void responseItems_responseBPartnerItem_debtorId_is_within(
			final int index,
			final int from,
			final int to) throws JsonProcessingException
	{
		final Integer debtorId = getResponseBPartnerUpsertItem(index).getDebtorId();
		assertThat(debtorId)
				.as("responseItems[%d].responseBPartnerItem.debtorId in [%d..%d]", index, from, to)
				.isNotNull()
				.isBetween(from, to);
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
	 * Asserts that {@code responseItems[index].responseBPartnerItem.creditorId} is in the inclusive range [{@code from}..{@code to}].
	 *
	 * @param index zero-based index
	 * @param from  inclusive lower bound
	 * @param to    inclusive upper bound
	 */
	@Then("responseItems[{int}].responseBPartnerItem.creditorId is within {int}..{int}")
	public void responseItems_responseBPartnerItem_creditorId_is_within(
			final int index,
			final int from,
			final int to) throws JsonProcessingException
	{
		final Integer creditorId = getResponseBPartnerUpsertItem(index).getCreditorId();
		assertThat(creditorId)
				.as("responseItems[%d].responseBPartnerItem.creditorId in [%d..%d]", index, from, to)
				.isNotNull()
				.isBetween(from, to);
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
	 * <p>The request is sent <em>without</em> an {@code expectedStatusCode} constraint so TC4
	 * (which expects a 4xx rejection) can capture the error response via {@link #the_upsert_is_rejected()}.
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
		final boolean isCustomer = "customer".equalsIgnoreCase(role);
		final boolean isVendor = "vendor".equalsIgnoreCase(role);

		final StringBuilder bpartnerJson = new StringBuilder();
		bpartnerJson.append("{");
		bpartnerJson.append("\"name\":\"").append(id).append("\",");
		bpartnerJson.append("\"language\":\"de\",");
		bpartnerJson.append("\"group\":\"Standard\",");
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

		final String externalIdentifier = "ext-numbgen-" + id;

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

		// expectedStatusCode is set to null so the HTTP call always completes without asserting
		// the status here — the assertion steps (debtorId/creditorId checks) validate success cases
		// and "Then the upsert is rejected" validates TC4-style failure cases.
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
