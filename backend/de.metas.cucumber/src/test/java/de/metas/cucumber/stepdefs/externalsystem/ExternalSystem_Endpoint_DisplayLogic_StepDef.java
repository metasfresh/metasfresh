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

package de.metas.cucumber.stepdefs.externalsystem;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.endpoint.interceptor.ExternalSystem_Endpoint;
import de.metas.externalsystem.model.I_ExternalSystem_Endpoint;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.fail;

/**
 * Holds the display-logic copies inside {@link ExternalSystem_Endpoint} against the live application
 * dictionary.
 * <p>
 * The interceptor decides which endpoint fields a new transport/authentication configuration hides by
 * evaluating a <b>verbatim string copy</b> of each field's {@code AD_Field.DisplayLogic}. A copy cannot see
 * the dictionary, so nothing in that module's plain-JUnit tests notices when a migration script changes a
 * condition -- or deactivates a field, which no string copy can express at all. This step is the only place
 * in the build where both sides are present at once.
 * <p>
 * It asserts in both directions: every rule's condition is byte-identical to that column's live
 * {@code AD_Field.DisplayLogic}, every column the window shows conditionally has a rule, and no rule names a
 * column the window does not show conditionally (an inactive {@code AD_Field} included).
 * <p>
 * Gherkin usage -- no parameters, no DataTable:
 * <pre>
 *   Then the ExternalSystem_Endpoint interceptor's display logic is exactly the window's
 * </pre>
 */
public class ExternalSystem_Endpoint_DisplayLogic_StepDef
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	/**
	 * Reports every disagreement between the two sides in one failure, each naming the column and both
	 * strings.
	 *
	 * @see ExternalSystem_Endpoint#getDisplayLogicByColumnName()
	 */
	@Then("the ExternalSystem_Endpoint interceptor's display logic is exactly the window's")
	public void interceptorDisplayLogicIsExactlyTheWindows()
	{
		final ImmutableMap<String, String> copiedIntoCode = new ExternalSystem_Endpoint().getDisplayLogicByColumnName();
		final WindowFields windowFields = retrieveEndpointWindowFields();

		final List<String> problems = new ArrayList<>();
		problems.addAll(windowFields.getAmbiguities());
		problems.addAll(describeConditionsThatDiffer(copiedIntoCode, windowFields));
		problems.addAll(describeColumnsWithoutARule(copiedIntoCode, windowFields));
		problems.addAll(describeRulesWithoutAColumn(copiedIntoCode, windowFields));

		if (!problems.isEmpty())
		{
			fail(HEADER + "\n\n" + String.join("\n\n", problems) + "\n");
		}
	}

	private static final String HEADER = ""
			+ "The " + I_ExternalSystem_Endpoint.Table_Name + " interceptor and the window it was copied from disagree.\n"
			+ "  window : AD_Field.DisplayLogic, as this branch's migration scripts left it in the database\n"
			+ "  code   : the verbatim copies in " + ExternalSystem_Endpoint.class.getName() + "#createHideableColumns()\n"
			+ "One of the two is stale. Which one tells you what to fix -- each finding below says how to tell.";

	private static List<String> describeConditionsThatDiffer(
			@NonNull final ImmutableMap<String, String> copiedIntoCode,
			@NonNull final WindowFields windowFields)
	{
		final List<String> problems = new ArrayList<>();
		for (final Map.Entry<String, String> shownConditionally : windowFields.getShownConditionally().entrySet())
		{
			final String columnName = shownConditionally.getKey();
			final String inTheWindow = shownConditionally.getValue();
			final String inTheCode = copiedIntoCode.get(columnName);
			if (inTheCode == null || inTheCode.equals(inTheWindow))
			{
				continue;
			}

			problems.add("CONDITION DIFFERS for " + columnName + ":"
					+ "\n  window : " + inTheWindow
					+ "\n  code   : " + inTheCode
					+ "\n  The two are no longer the same string. If a migration script changed this field's"
					+ "\n  DisplayLogic, copy the window's string into the rule -- it is the window that decides what the"
					+ "\n  operator sees. If the rule is the newer of the two, the change to the window was never written"
					+ "\n  as a migration script, and the operator still sees the old condition.");
		}
		return problems;
	}

	private static List<String> describeColumnsWithoutARule(
			@NonNull final ImmutableMap<String, String> copiedIntoCode,
			@NonNull final WindowFields windowFields)
	{
		final List<String> problems = new ArrayList<>();
		for (final Map.Entry<String, String> shownConditionally : windowFields.getShownConditionally().entrySet())
		{
			final String columnName = shownConditionally.getKey();
			if (copiedIntoCode.containsKey(columnName))
			{
				continue;
			}

			problems.add("NO RULE for " + columnName + ", which the window shows only under:"
					+ "\n  window : " + shownConditionally.getValue()
					+ "\n  Every other configuration hides this column, and with no rule its value is never taken away:"
					+ "\n  it stays in the database where nobody can see it and the dispatch keeps reading it. Add a"
					+ "\n  hideable(...) entry carrying exactly the string above, plus the way to clear the column.");
		}
		return problems;
	}

	private static List<String> describeRulesWithoutAColumn(
			@NonNull final ImmutableMap<String, String> copiedIntoCode,
			@NonNull final WindowFields windowFields)
	{
		final List<String> problems = new ArrayList<>();
		for (final Map.Entry<String, String> rule : copiedIntoCode.entrySet())
		{
			final String columnName = rule.getKey();
			if (windowFields.getShownConditionally().containsKey(columnName))
			{
				continue;
			}

			problems.add("RULE FOR A COLUMN THE WINDOW DOES NOT SHOW CONDITIONALLY -- " + columnName + ":"
					+ "\n  window : " + windowFields.describeWhyNotShownConditionally(columnName)
					+ "\n  code   : " + rule.getValue()
					+ "\n  The rule claims this column is shown under that condition, which the window does not say."
					+ "\n  If the field was retired on purpose, drop the rule: a field the window renders under no"
					+ "\n  configuration is hidden by none either, and clearing it would destroy a value that has no"
					+ "\n  field left to restore it from. If it was retired by mistake, reactivate it in a migration"
					+ "\n  script.");
		}
		return problems;
	}

	/**
	 * Every {@code AD_Field} of the endpoint table, read as the window reads it.
	 * <p>
	 * Fields are read across all tabs, not just the one window that exists today: a second window on this
	 * table would render the same columns under its own conditions, and the interceptor has one rule per
	 * column to cover both.
	 */
	@NonNull
	private WindowFields retrieveEndpointWindowFields()
	{
		final AdTableId adTableId = tableDAO.retrieveAdTableId(I_ExternalSystem_Endpoint.Table_Name);

		final ImmutableMap<Integer, String> columnNamesById = queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, adTableId)
				.create()
				.stream()
				.collect(ImmutableMap.toImmutableMap(I_AD_Column::getAD_Column_ID, I_AD_Column::getColumnName));

		final ImmutableList<I_AD_Field> fields = queryBL.createQueryBuilder(I_AD_Field.class)
				.addInArrayFilter(I_AD_Field.COLUMNNAME_AD_Column_ID, columnNamesById.keySet())
				.create()
				.listImmutable(I_AD_Field.class);

		final ImmutableListMultimap.Builder<String, String> conditionsOfActiveFields = ImmutableListMultimap.builder();
		final ImmutableSet.Builder<String> columnsWithAnActiveField = ImmutableSet.builder();
		final ImmutableSet.Builder<String> columnsWithAnInactiveField = ImmutableSet.builder();
		for (final I_AD_Field field : fields)
		{
			final String columnName = columnNamesById.get(field.getAD_Column_ID());
			if (!field.isActive())
			{
				columnsWithAnInactiveField.add(columnName);
				continue;
			}

			columnsWithAnActiveField.add(columnName);

			final String displayLogic = StringUtils.trimBlankToNull(field.getDisplayLogic());
			if (displayLogic != null)
			{
				conditionsOfActiveFields.put(columnName, displayLogic);
			}
		}

		return WindowFields.of(
				conditionsOfActiveFields.build(),
				columnsWithAnActiveField.build(),
				columnsWithAnInactiveField.build());
	}

	@Value
	private static class WindowFields
	{
		/** column -> the condition under which the window shows it; only columns whose active field has one */
		@NonNull ImmutableMap<String, String> shownConditionally;

		/** the columns whose several active fields disagree about that condition, phrased as findings */
		@NonNull ImmutableList<String> ambiguities;

		@NonNull ImmutableSet<String> columnsWithAnActiveField;

		@NonNull ImmutableSet<String> columnsWithAnInactiveField;

		@NonNull
		static WindowFields of(
				@NonNull final ImmutableListMultimap<String, String> conditionsOfActiveFields,
				@NonNull final ImmutableSet<String> columnsWithAnActiveField,
				@NonNull final ImmutableSet<String> columnsWithAnInactiveField)
		{
			final ImmutableMap.Builder<String, String> shownConditionally = ImmutableMap.builder();
			final ImmutableList.Builder<String> ambiguities = ImmutableList.builder();

			for (final String columnName : conditionsOfActiveFields.keySet())
			{
				// a TreeSet so the finding lists the distinct conditions in a stable order
				final TreeSet<String> distinctConditions = new TreeSet<>(conditionsOfActiveFields.get(columnName));
				if (distinctConditions.size() > 1)
				{
					ambiguities.add("TWO ACTIVE FIELDS DISAGREE about when the window shows " + columnName + ":"
							+ "\n  window : " + String.join("\n           ", distinctConditions)
							+ "\n  The interceptor carries one rule per column and cannot honour both. Make the fields"
							+ "\n  agree in a migration script, or the rule will be right for one tab and wrong for the"
							+ "\n  other.");
					continue;
				}

				shownConditionally.put(columnName, distinctConditions.first());
			}

			return new WindowFields(
					shownConditionally.build(),
					ambiguities.build(),
					columnsWithAnActiveField,
					columnsWithAnInactiveField);
		}

		@NonNull
		String describeWhyNotShownConditionally(@NonNull final String columnName)
		{
			if (columnsWithAnActiveField.contains(columnName))
			{
				return "the active AD_Field for this column carries no DisplayLogic, so the window always shows it";
			}
			if (columnsWithAnInactiveField.contains(columnName))
			{
				return "every AD_Field for this column is IsActive='N', so the window renders it under no configuration";
			}
			return "this column has no AD_Field at all, so the window never renders it";
		}
	}
}
