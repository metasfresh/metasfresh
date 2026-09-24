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

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeSet;

import static org.assertj.core.api.Assertions.fail;

/**
 * Holds the two kinds of dictionary copy inside {@link ExternalSystem_Endpoint} against the live
 * application dictionary.
 * <p>
 * The interceptor decides which endpoint fields a new transport/authentication configuration hides by
 * evaluating a <b>verbatim string copy</b> of each field's {@code AD_Field.DisplayLogic}, and puts each
 * hidden field back to a <b>verbatim copy</b> of its column's {@code AD_Column.DefaultValue}. A copy cannot
 * see the dictionary, so nothing in that module's plain-JUnit tests notices when a migration script changes
 * a condition, changes a default -- or deactivates a field, which no string copy can express at all. These
 * steps are the only place in the build where both sides are present at once.
 * <p>
 * The display-logic step asserts in both directions: every rule's condition is byte-identical to that
 * column's live {@code AD_Field.DisplayLogic}, every column the window shows conditionally has a rule, and
 * no rule names a column the window does not show conditionally (an inactive {@code AD_Field} included).
 * The column-default step does the same for the defaults: same value, and the same columns carry one.
 * <p>
 * Gherkin usage -- no parameters, no DataTable:
 * <pre>
 *   Then the ExternalSystem_Endpoint interceptor's display logic is exactly the window's
 *   Then the ExternalSystem_Endpoint interceptor's column defaults are exactly the dictionary's
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

	/**
	 * Reports every disagreement between the copied defaults and {@code AD_Column.DefaultValue} in one
	 * failure, each naming the column and both values.
	 *
	 * @see ExternalSystem_Endpoint#getColumnDefaultByColumnName()
	 */
	@Then("the ExternalSystem_Endpoint interceptor's column defaults are exactly the dictionary's")
	public void interceptorColumnDefaultsAreExactlyTheDictionarys()
	{
		final ExternalSystem_Endpoint interceptor = new ExternalSystem_Endpoint();
		final ImmutableSet<String> hideableColumnNames = interceptor.getDisplayLogicByColumnName().keySet();
		final ImmutableMap<String, Object> copiedIntoCode = interceptor.getColumnDefaultByColumnName();
		final ImmutableMap<String, String> inTheDictionary = retrieveEndpointColumnDefaultValues();

		final List<String> problems = new ArrayList<>();
		for (final String columnName : hideableColumnNames)
		{
			problems.addAll(describeDefaultThatDiffers(
					columnName,
					asDictionaryText(copiedIntoCode.get(columnName)),
					inTheDictionary.get(columnName)));
		}

		if (!problems.isEmpty())
		{
			fail(DEFAULTS_HEADER + "\n\n" + String.join("\n\n", problems) + "\n");
		}
	}

	private static List<String> describeDefaultThatDiffers(
			@NonNull final String columnName,
			@Nullable final String inTheCode,
			@Nullable final String inTheDictionary)
	{
		if (Objects.equals(inTheCode, inTheDictionary))
		{
			return ImmutableList.of();
		}

		if (inTheCode == null)
		{
			return ImmutableList.of("NO DEFAULT COPIED for " + columnName + ":"
					+ "\n  dictionary : " + inTheDictionary
					+ "\n  code       : (none)"
					+ "\n  The window pre-fills this value on a newly created endpoint, and the rule table says the"
					+ "\n  column has no default -- so hiding the field leaves it empty and the same column behaves two"
					+ "\n  ways depending on how the operator got there. Change the hideable(...) entry to"
					+ "\n  hideableWithColumnDefault(...) carrying exactly the value above, in the type the column stores.");
		}

		if (inTheDictionary == null)
		{
			return ImmutableList.of("DEFAULT COPIED FOR A COLUMN THAT HAS NONE -- " + columnName + ":"
					+ "\n  dictionary : (none)"
					+ "\n  code       : " + inTheCode
					+ "\n  Hiding this field would write a value a newly created endpoint does not get. If the default"
					+ "\n  was dropped on purpose, turn the entry back into a plain hideable(...) that takes the value"
					+ "\n  away; if it was dropped by mistake, restore it in a migration script.");
		}

		return ImmutableList.of("DEFAULT DIFFERS for " + columnName + ":"
				+ "\n  dictionary : " + inTheDictionary
				+ "\n  code       : " + inTheCode
				+ "\n  The two are no longer the same value, so a hidden field is reset to something other than what"
				+ "\n  the window pre-fills on a new record. If a migration script changed this column's DefaultValue,"
				+ "\n  copy the dictionary's value into the rule -- it is the dictionary that decides what a new record"
				+ "\n  gets. If the rule is the newer of the two, the change was never written as a migration script.");
	}

	/**
	 * The copied default as {@code AD_Column.DefaultValue} spells it. The rule table carries each default in
	 * the type its column stores, so {@code IsFileUpload}'s {@code false} has to be rendered back as the
	 * dictionary's {@code 'N'} -- compared as {@code "false"} it would read as a permanent disagreement.
	 */
	@Nullable
	private static String asDictionaryText(@Nullable final Object columnDefault)
	{
		if (columnDefault == null)
		{
			return null;
		}
		if (columnDefault instanceof Boolean)
		{
			return StringUtils.ofBoolean((Boolean)columnDefault);
		}
		return columnDefault.toString();
	}

	/** Every endpoint column that carries a non-blank {@code AD_Column.DefaultValue}. */
	@NonNull
	private ImmutableMap<String, String> retrieveEndpointColumnDefaultValues()
	{
		final AdTableId adTableId = tableDAO.retrieveAdTableId(I_ExternalSystem_Endpoint.Table_Name);

		final ImmutableMap.Builder<String, String> defaultValueByColumnName = ImmutableMap.builder();
		queryBL.createQueryBuilder(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, adTableId)
				.create()
				.forEach(column -> {
					final String defaultValue = StringUtils.trimBlankToNull(column.getDefaultValue());
					if (defaultValue != null)
					{
						defaultValueByColumnName.put(column.getColumnName(), defaultValue);
					}
				});

		return defaultValueByColumnName.build();
	}

	private static final String DEFAULTS_HEADER = ""
			+ "The " + I_ExternalSystem_Endpoint.Table_Name + " interceptor and the dictionary it copied its defaults\n"
			+ "from disagree.\n"
			+ "  dictionary : AD_Column.DefaultValue, as the migration scripts left it in the database\n"
			+ "  code       : the verbatim copies in " + ExternalSystem_Endpoint.class.getName() + "#createHideableColumns()\n"
			+ "One of the two is stale. Which one tells you what to fix -- each finding below says how to tell.";

	private static final String HEADER = ""
			+ "The " + I_ExternalSystem_Endpoint.Table_Name + " interceptor and the window it was copied from disagree.\n"
			+ "  window : AD_Field.DisplayLogic, as the migration scripts left it in the database\n"
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
