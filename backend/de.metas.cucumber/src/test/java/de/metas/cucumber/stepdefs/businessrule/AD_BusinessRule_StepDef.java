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


package de.metas.cucumber.stepdefs.businessrule;

import de.metas.cucumber.stepdefs.AD_User_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.M_Product_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.cucumber.stepdefs.message.AD_Message_StepDefData;
import de.metas.business_rule.BusinessRuleService;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_BusinessRule;
import org.compiere.model.I_AD_BusinessRule_Event;
import org.compiere.model.I_AD_BusinessRule_Trigger;
import org.compiere.model.I_AD_BusinessRule_WarningTarget;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Record_Warning;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_Val_Rule;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_AD_BusinessRule;
import org.compiere.model.X_AD_Message;
import org.compiere.model.X_AD_Val_Rule;
import org.compiere.util.DB;
import org.compiere.util.Env;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Creates and checks business rules (AD_BusinessRule) that watch M_Product records, and runs the processing of their events.
 */
@RequiredArgsConstructor
public class AD_BusinessRule_StepDef
{
	/** Value of the {@code UpdatedBy} column of {@code set M_Product.UpdatedBy by SQL:} that stands for an id without any AD_User row. */
	private static final String UPDATED_BY_NO_SUCH_USER = "NO_SUCH_USER";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);

	@NonNull private final AD_BusinessRule_StepDefData ruleTable;
	@NonNull private final AD_Message_StepDefData messageTable;
	@NonNull private final M_Product_StepDefData productTable;
	@NonNull private final AD_User_StepDefData userTable;

	/** AD_User_ID -> NotificationType the user had before this scenario first changed it. */
	private final Map<Integer, String> priorNotificationTypeByUserId = new LinkedHashMap<>();

	/**
	 * Creates one active AD_BusinessRule per row, triggered by new and changed M_Product records that match the trigger condition.
	 * Per rule it also creates the AD_Val_Rule (type SQL), the warning AD_Message (Value {@code <Identifier>_warning},
	 * stored in the AD_Message identifiers under that same name), the trigger and one warning target on the M_Product itself.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (required) alias of the AD_BusinessRule<br>
	 *   <b>Name</b> — (required) name of the rule and of its AD_Val_Rule<br>
	 *   <b>ValidationSQL</b> — (required) where clause of the AD_Val_Rule; a product that does not match it gets a warning<br>
	 *   <b>TriggerConditionSQL</b> — (required) where clause that restricts the trigger to the products of the scenario<br>
	 * @cucumber.depends StepDefData: AD_BusinessRule_StepDefData, AD_Message_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains AD_BusinessRules with M_Product trigger:
	 *   | Identifier | Name       | ValidationSQL                   | TriggerConditionSQL              |
	 *   | rule_1     | Rule one   | M_Product.Description IS NULL   | M_Product.Value LIKE 'abc%'      |
	 * </pre>
	 */
	@Given("metasfresh contains AD_BusinessRules with M_Product trigger:")
	public void metasfresh_contains_AD_BusinessRules_with_M_Product_trigger(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createRule);
	}

	private void createRule(@NonNull final DataTableRow row)
	{
		final String identifier = row.getAsIdentifier().getAsString();
		final String name = row.getAsString(I_AD_BusinessRule.COLUMNNAME_Name);
		final int productTableId = InterfaceWrapperHelper.getTableId(I_M_Product.class);

		final I_AD_Message message = getOrCreateWarningMessage(identifier + "_warning");
		final I_AD_Val_Rule valRule = getOrCreateValidationRule(name, row.getAsString("ValidationSQL"));

		final I_AD_BusinessRule rule = InterfaceWrapperHelper.newInstance(I_AD_BusinessRule.class);
		rule.setAD_Org_ID(0);
		// the rule validators run in BEFORE_NEW and need the record id, so allocate it up front
		rule.setAD_BusinessRule_ID(nextId(I_AD_BusinessRule.Table_Name));
		rule.setName(name);
		rule.setAD_Table_ID(productTableId);
		rule.setValidation_Rule_ID(valRule.getAD_Val_Rule_ID());
		rule.setWarning_Message_ID(message.getAD_Message_ID());
		rule.setSeverity(X_AD_BusinessRule.SEVERITY_Notice);
		rule.setIsCreateWarningOnTarget(false);
		InterfaceWrapperHelper.saveRecord(rule);
		ruleTable.putOrReplace(row.getAsIdentifier(), rule);

		final I_AD_BusinessRule_Trigger trigger = InterfaceWrapperHelper.newInstance(I_AD_BusinessRule_Trigger.class);
		trigger.setAD_Org_ID(0);
		trigger.setAD_BusinessRule_Trigger_ID(nextId(I_AD_BusinessRule_Trigger.Table_Name));
		trigger.setAD_BusinessRule_ID(rule.getAD_BusinessRule_ID());
		trigger.setSource_Table_ID(productTableId);
		trigger.setOnNew(true);
		trigger.setOnUpdate(true);
		trigger.setOnDelete(false);
		trigger.setConditionSQL(row.getAsString("TriggerConditionSQL"));
		trigger.setTargetRecordMappingSQL("M_Product_ID");
		InterfaceWrapperHelper.saveRecord(trigger);

		// without a warning target the processor neither creates the warning nor sends the notice
		final I_AD_BusinessRule_WarningTarget warningTarget = InterfaceWrapperHelper.newInstance(I_AD_BusinessRule_WarningTarget.class);
		warningTarget.setAD_Org_ID(0);
		warningTarget.setAD_BusinessRule_WarningTarget_ID(nextId(I_AD_BusinessRule_WarningTarget.Table_Name));
		warningTarget.setAD_BusinessRule_ID(rule.getAD_BusinessRule_ID());
		warningTarget.setAD_Table_ID(productTableId);
		warningTarget.setSeqNo(10);
		warningTarget.setLookupSQL("(SELECT p.M_Product_ID FROM M_Product p WHERE p.M_Product_ID = M_Product.M_Product_ID)");
		InterfaceWrapperHelper.saveRecord(warningTarget);
	}

	private static int nextId(@NonNull final String tableName)
	{
		return DB.getNextID(StepDefConstants.CLIENT_ID.getRepoId(), tableName);
	}

	private I_AD_Message getOrCreateWarningMessage(@NonNull final String value)
	{
		I_AD_Message message = queryBL.createQueryBuilder(I_AD_Message.class)
				.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, value)
				.create()
				.firstOnly(I_AD_Message.class);
		if (message == null)
		{
			message = InterfaceWrapperHelper.newInstance(I_AD_Message.class);
			message.setAD_Org_ID(0);
			message.setValue(value);
			message.setEntityType("U");
		}
		message.setIsActive(true);
		message.setMsgType(X_AD_Message.MSGTYPE_Information);
		message.setMsgText("Test warning {0}");
		InterfaceWrapperHelper.saveRecord(message);
		messageTable.putOrReplace(value, message);
		return message;
	}

	private I_AD_Val_Rule getOrCreateValidationRule(@NonNull final String name, @NonNull final String validationSql)
	{
		I_AD_Val_Rule valRule = queryBL.createQueryBuilder(I_AD_Val_Rule.class)
				.addEqualsFilter(I_AD_Val_Rule.COLUMNNAME_Name, name)
				.create()
				.firstOnly(I_AD_Val_Rule.class);
		if (valRule == null)
		{
			valRule = InterfaceWrapperHelper.newInstance(I_AD_Val_Rule.class);
			valRule.setAD_Org_ID(0);
			valRule.setName(name);
			valRule.setEntityType("U");
		}
		valRule.setIsActive(true);
		valRule.setType(X_AD_Val_Rule.TYPE_SQL);
		valRule.setCode(validationSql);
		InterfaceWrapperHelper.saveRecord(valRule);
		return valRule;
	}

	/**
	 * Sets {@code AD_User.NotificationType} through the model, so that the cached notification config of the user is reset;
	 * the {@code @After} hook puts the prior value back.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Given set notification type of AD_User 0 to N
	 * </pre>
	 */
	@Given("set notification type of AD_User {int} to {word}")
	public void set_notification_type_of_AD_User(final int adUserId, @NonNull final String notificationType)
	{
		final I_AD_User user = userDAO.getById(UserId.ofRepoId(adUserId));
		priorNotificationTypeByUserId.putIfAbsent(adUserId, user.getNotificationType());
		user.setNotificationType(notificationType);
		userDAO.save(user);
	}

	@And("deactivate AD_BusinessRule {word}")
	public void deactivate_AD_BusinessRule(@NonNull final String ruleIdentifier)
	{
		deactivateRule(ruleTable.get(ruleIdentifier));
	}

	/**
	 * Writes {@code M_Product.UpdatedBy} with plain SQL, i.e. a value the model would never write, and reloads the product
	 * in the scenario so that its next save starts from that stored value.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the product<br>
	 *   <b>UpdatedBy</b> — (required) the AD_User_ID to store, or the identifier of an AD_User of the scenario, or {@code NO_SUCH_USER} for an id (highest AD_User_ID + 1000) that has no AD_User row<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And set M_Product.UpdatedBy by SQL:
	 *   | M_Product_ID | UpdatedBy |
	 *   | product_1    | -1        |
	 * </pre>
	 */
	@And("set M_Product.UpdatedBy by SQL:")
	public void set_M_Product_UpdatedBy_by_SQL(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
			final String updatedByStr = row.getAsString("UpdatedBy");
			final int updatedBy = UPDATED_BY_NO_SUCH_USER.equals(updatedByStr)
					? DB.getSQLValueEx(ITrx.TRXNAME_None, "SELECT COALESCE(MAX(AD_User_ID), 0) + 1000 FROM AD_User")
					: resolveUserId(updatedByStr);

			DB.executeUpdateAndThrowExceptionOnFail(
					"UPDATE M_Product SET UpdatedBy=? WHERE M_Product_ID=?",
					new Object[] { updatedBy, product.getM_Product_ID() },
					ITrx.TRXNAME_None);

			InterfaceWrapperHelper.refresh(product);
			assertThat(product.getUpdatedBy()).as("M_Product.UpdatedBy").isEqualTo(updatedBy);
		});
	}

	/**
	 * Checks the AD_BusinessRule_Event(s) the save of a product created for a rule.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>M_Product_ID</b> — (required, identifier-ref) the saved product<br>
	 *   <b>AD_BusinessRule_ID</b> — (required, identifier-ref) the rule<br>
	 *   <b>Triggering_User_ID</b> — (required) the AD_User_ID, or the identifier of an AD_User of the scenario, that every event of that product and rule must name<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, AD_BusinessRule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate AD_BusinessRule_Events for:
	 *   | M_Product_ID | AD_BusinessRule_ID | Triggering_User_ID |
	 *   | product_1    | rule_1             | 100                |
	 * </pre>
	 */
	@Then("validate AD_BusinessRule_Events for:")
	public void validate_AD_BusinessRule_Events(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final I_M_Product product = row.getAsIdentifier(I_M_Product.COLUMNNAME_M_Product_ID).lookupNotNullIn(productTable);
			final I_AD_BusinessRule rule = row.getAsIdentifier(I_AD_BusinessRule_Event.COLUMNNAME_AD_BusinessRule_ID).lookupNotNullIn(ruleTable);
			final int expectedTriggeringUserId = resolveUserId(row.getAsString(I_AD_BusinessRule_Event.COLUMNNAME_Triggering_User_ID));

			final List<I_AD_BusinessRule_Event> events = queryBL.createQueryBuilder(I_AD_BusinessRule_Event.class)
					.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_Source_Table_ID, InterfaceWrapperHelper.getTableId(I_M_Product.class))
					.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_Source_Record_ID, product.getM_Product_ID())
					.addEqualsFilter(I_AD_BusinessRule_Event.COLUMNNAME_AD_BusinessRule_ID, rule.getAD_BusinessRule_ID())
					.create()
					.list();

			assertThat(events).as("AD_BusinessRule_Events of M_Product_ID=" + product.getM_Product_ID() + ", AD_BusinessRule_ID=" + rule.getAD_BusinessRule_ID()).isNotEmpty();
			assertThat(events)
					.extracting(I_AD_BusinessRule_Event::getTriggering_User_ID)
					.as("Triggering_User_ID of the events")
					.containsOnly(expectedTriggeringUserId);
		});
	}

	/**
	 * Checks that the last save of the product wrote the user of the session (the logged-in user) as {@code M_Product.UpdatedBy}.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: M_Product_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate M_Product.UpdatedBy of product_1 is the logged-in user
	 * </pre>
	 */
	@Then("validate M_Product.UpdatedBy of {word} is the logged-in user")
	public void validate_M_Product_UpdatedBy_is_logged_in_user(@NonNull final String productIdentifier)
	{
		final I_M_Product product = productTable.get(productIdentifier);
		InterfaceWrapperHelper.refresh(product);
		final UserId loggedUserId = Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM);
		assertThat(product.getUpdatedBy()).as("M_Product.UpdatedBy of M_Product_ID=" + product.getM_Product_ID()).isEqualTo(loggedUserId.getRepoId());
	}

	private int resolveUserId(@NonNull final String userIdOrIdentifier)
	{
		final String trimmed = userIdOrIdentifier.trim();
		return trimmed.matches("-?\\d+")
				? Integer.parseInt(trimmed)
				: userTable.get(trimmed).getAD_User_ID();
	}

	@When("process business rule events")
	public void process_business_rule_events()
	{
		SpringContextHolder.instance.getBean(BusinessRuleService.class).processEvents(QueryLimit.ofInt(100));
	}

	/**
	 * Checks that the processing of the events left an AD_Record_Warning on the product for the rule.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>AD_BusinessRule_ID</b> — (required, identifier-ref) the rule that raised the warning<br>
	 * @cucumber.depends StepDefData: M_Product_StepDefData, AD_BusinessRule_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate AD_Record_Warning for M_Product product_1
	 *   | AD_BusinessRule_ID |
	 *   | rule_1             |
	 * </pre>
	 */
	@Then("validate AD_Record_Warning for M_Product {word}")
	public void validate_AD_Record_Warning_for_M_Product(@NonNull final String productIdentifier, @NonNull final DataTable dataTable)
	{
		final I_M_Product product = productTable.get(productIdentifier);
		DataTableRows.of(dataTable).forEach(row -> {
			final I_AD_BusinessRule rule = row.getAsIdentifier(I_AD_Record_Warning.COLUMNNAME_AD_BusinessRule_ID).lookupNotNullIn(ruleTable);

			final List<I_AD_Record_Warning> warnings = queryBL.createQueryBuilder(I_AD_Record_Warning.class)
					.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_AD_Table_ID, InterfaceWrapperHelper.getTableId(I_M_Product.class))
					.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_Record_ID, product.getM_Product_ID())
					.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_AD_BusinessRule_ID, rule.getAD_BusinessRule_ID())
					.create()
					.list();
			assertThat(warnings).as("AD_Record_Warning of M_Product_ID=" + product.getM_Product_ID() + ", AD_BusinessRule_ID=" + rule.getAD_BusinessRule_ID()).hasSize(1);
		});
	}

	/**
	 * Runs also when a step failed: no rule of the scenario stays active on M_Product, no user keeps a changed NotificationType.
	 */
	@After
	public void cleanUpAfterScenario()
	{
		RuntimeException firstError = null;

		for (final I_AD_BusinessRule rule : ruleTable.getRecords())
		{
			try
			{
				deactivateRule(rule);
			}
			catch (final RuntimeException ex)
			{
				firstError = firstError != null ? firstError : ex;
			}
		}

		for (final Map.Entry<Integer, String> entry : priorNotificationTypeByUserId.entrySet())
		{
			try
			{
				final I_AD_User user = userDAO.getById(UserId.ofRepoId(entry.getKey()));
				user.setNotificationType(entry.getValue());
				userDAO.save(user);
			}
			catch (final RuntimeException ex)
			{
				firstError = firstError != null ? firstError : ex;
			}
		}
		priorNotificationTypeByUserId.clear();

		if (firstError != null)
		{
			throw firstError;
		}
	}

	private static void deactivateRule(@NonNull final I_AD_BusinessRule rule)
	{
		final I_AD_BusinessRule freshRule = InterfaceWrapperHelper.load(rule.getAD_BusinessRule_ID(), I_AD_BusinessRule.class);
		freshRule.setIsActive(false);
		InterfaceWrapperHelper.saveRecord(freshRule);
	}
}
