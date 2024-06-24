package org.adempiere.ad.column.callout;

import com.google.common.collect.ImmutableList;
import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;
import de.metas.reflist.ReferenceId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_Table;
import org.compiere.model.MColumn;
import org.compiere.model.X_AD_Column;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Optional;
import java.util.regex.Pattern;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

@Callout(value = I_AD_Column.class, recursionAvoidanceLevel = Callout.RecursionAvoidanceLevel.CalloutMethod)
@Component
public class AD_Column
{
	private static final Logger logger = LogManager.getLogger(AD_Column.class);
	public static final String ENTITYTYPE_Dictionary = "D";

	private final IColumnBL columnBL = Services.get(IColumnBL.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

	public AD_Column()
	{
		final IProgramaticCalloutProvider programmaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programmaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_ColumnName })
	public void onColumnName(final I_AD_Column column)
	{
		if (column == null || Check.isBlank(column.getColumnName()))
		{
			return;
		}

		final String columnName = column.getColumnName();
		column.setIsAllowLogging(columnBL.getDefaultAllowLoggingByColumnName(columnName));

		if (MColumn.isSuggestSelectionColumn(column.getColumnName(), true))
		{
			column.setIsSelectionColumn(true);
		}
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_IsSelectionColumn })
	public void onIsSelectionColumn(final I_AD_Column column)
	{
		if (column.isSelectionColumn() && Check.isBlank(column.getFilterOperator()))
		{
			column.setFilterOperator(X_AD_Column.FILTEROPERATOR_EqualsOrLike);
		}
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_AD_Element_ID })
	public void onAD_Element_ID(final I_AD_Column column)
	{
		if (column.getAD_Element_ID() <= 0)
		{
			//noinspection ConstantConditions
			column.setColumnName(null);
			column.setName(null);
			return;
		}

		final I_AD_Element element = column.getAD_Element();

		final String elementColumnName = element.getColumnName();
		Check.assumeNotNull(elementColumnName, "The element {} needs to have a column name set", element);

		column.setColumnName(elementColumnName);
		column.setName(element.getName());
		column.setDescription(element.getDescription());
		column.setHelp(element.getHelp());
		column.setIsCalculated(columnBL.getDefaultIsCalculatedByColumnName(elementColumnName));

		final AdTableId adTableId = AdTableId.ofRepoId(column.getAD_Table_ID());
		final I_AD_Table table = adTableDAO.retrieveTable(adTableId);

		String entityType = table.getEntityType();

		if (ENTITYTYPE_Dictionary.equals(entityType))
		{
			entityType = element.getEntityType();
		}

		column.setEntityType(entityType);
		setTypeAndLength(column);

		if ("DocumentNo".equals(elementColumnName)
				|| "Value".equals(elementColumnName))
		{
			column.setIsUseDocSequence(true);
		}

		updateIsExcludeFromZoomTargets(column);
	}

	private void setTypeAndLength(final I_AD_Column column)
	{
		final String columnName = column.getColumnName();
		final int previousDisplayType = column.getAD_Reference_ID();
		final AdTableId adTableId = AdTableId.ofRepoId(column.getAD_Table_ID());
		final I_AD_Table table = adTableDAO.retrieveTable(adTableId);
		final String tableName = table.getTableName();

		if (columnName.equalsIgnoreCase(tableName + "_ID"))
		{
			updateTableIdColumn(column);
		}

		else if (columnName.toUpperCase().endsWith("_ACCT"))
		{
			updateAccountColumn(column);
		}

		else if (columnName.equalsIgnoreCase("C_Location_ID"))
		{
			updateLocationColumn(column);
		}

		else if (columnName.equalsIgnoreCase("M_AttributeSetInstance_ID"))
		{
			updateAttributeSetInstanceColumn(column);
		}

		else if (columnName.equalsIgnoreCase("SalesRep_ID"))
		{
			updateSalesRepColumn(column);
		}

		else if (columnName.toUpperCase().endsWith("_ID"))
		{
			updateIdColumn(column);
		}

		else if (columnName.equalsIgnoreCase("Created")
				|| columnName.equalsIgnoreCase("Updated"))
		{
			updateCreatedOrUpdatedColumn(column);
		}
		else if (columnName.contains("Date"))
		{
			updateDateColumn(column, previousDisplayType);
		}

		else if (columnName.equalsIgnoreCase("CreatedBy")
				|| columnName.equalsIgnoreCase("UpdatedBy"))
		{
			updateCreatedByOrUpdatedByColumn(column);
		}

		else if (columnName.equalsIgnoreCase("EntityType"))
		{
			updateEntityTypeColumn(column);
		}

		else if (columnName.toUpperCase().contains("AMT")
				|| columnName.contains("Amount"))
		{
			updateAmountColumn(column);
		}
		else if (columnName.toUpperCase().endsWith("PRICE"))
		{
			updateCostPriceColumn(column);
		}
		else if (columnName.toUpperCase().contains("QTY"))
		{
			updateQtyColumn(column);
		}

		else if (columnName.endsWith("Number"))
		{
			updateNumberColumn(column);
		}

		else if (columnName.toUpperCase().startsWith("IS")
				|| Pattern.matches("(Allow)[A-Z].*", columnName)
				|| Pattern.matches("(Has)[A-Z].*", columnName)
				|| "Processed".equals(columnName))
		{
			column.setAD_Reference_ID(DisplayType.YesNo);

			updateFlagColumn(column);
		}

		else if ("Name".equalsIgnoreCase(columnName)
				|| "DocumentNo".equals(columnName))
		{
			updateNameOrDocumentNoColumn(column);
		}

		else
		{
			AdElementId.optionalOfRepoId(column.getAD_Element_ID())
					.flatMap(this::suggestColumnTypeAndLength)
					.ifPresent(suggestion -> updateColumnFromSuggestion(column, suggestion));
		}

		if (column.getAD_Reference_ID() <= 0)
		{
			column.setAD_Reference_ID(DisplayType.String);
		}

		if (column.isUpdateable()
				&& (table.isView()
				|| columnName.equalsIgnoreCase("AD_Client_ID")
				|| columnName.equalsIgnoreCase("AD_Org_ID")
				|| columnName.toUpperCase().startsWith("CREATED")
				|| columnName.equalsIgnoreCase("UPDATED")))
		{
			column.setIsUpdateable(false);
		}
	}

	private static void updateTableIdColumn(final I_AD_Column column)
	{
		column.setIsKey(true);
		column.setAD_Reference_ID(DisplayType.ID);
		column.setIsUpdateable(false);
		column.setFieldLength(10);
	}

	private static void updateAccountColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Account);
		column.setFieldLength(10);
	}

	private static void updateLocationColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Location);
		column.setFieldLength(10);
	}

	private static void updateAttributeSetInstanceColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.PAttribute);
		column.setFieldLength(10);
	}

	private static void updateSalesRepColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Table);
		column.setAD_Reference_Value_ID(190);
		column.setFieldLength(10);
	}

	private static void updateIdColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.TableDir);
		column.setFieldLength(10);
	}

	private static void updateCreatedOrUpdatedColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.DateTime);
		column.setFieldLength(7);
	}

	private static void updateDateColumn(final I_AD_Column column, final int previousDisplayType)
	{
		if (!DisplayType.isDate(previousDisplayType))
		{
			column.setAD_Reference_ID(DisplayType.Date);
		}
		column.setFieldLength(7);
	}

	private static void updateCreatedByOrUpdatedByColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Table);
		column.setAD_Reference_Value_ID(110);
		column.setIsUpdateable(false);
		column.setFieldLength(10);
	}

	private static void updateEntityTypeColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Table);
		column.setAD_Reference_Value_ID(389);
	}

	private static void updateAmountColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Amount);
		column.setFieldLength(10);
		column.setIsMandatory(true);
	}

	private static void updateCostPriceColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.CostPrice);
		column.setFieldLength(10);
		column.setIsMandatory(true);
	}

	private static void updateQtyColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Quantity);
		column.setFieldLength(10);
	}

	private static void updateNumberColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.Number);
		column.setFieldLength(10);
	}

	private static void updateFlagColumn(final I_AD_Column column)
	{
		column.setFieldLength(1);
		column.setIsMandatory(true);
		if (I_AD_Column.COLUMNNAME_IsActive.equals(column.getColumnName()))
		{
			column.setDefaultValue("Y");
		}
		else
		{
			column.setDefaultValue("N");
		}
	}

	private static void updateNameOrDocumentNoColumn(final I_AD_Column column)
	{
		column.setAD_Reference_ID(DisplayType.String);
		column.setIsIdentifier(true);
		column.setSeqNo(1);
		column.setFieldLength(40);
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_ColumnSQL })
	public void updateIsLazyLoading(final I_AD_Column column)
	{
		column.setIsLazyLoading(adTableDAO.isVirtualColumn(column));
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_AD_Reference_ID })
	public void onAD_Reference(final I_AD_Column column)
	{
		updateIsExcludeFromZoomTargets(column);

		final int referenceId = column.getAD_Reference_ID();
		if (referenceId == DisplayType.Integer)
		{
			updateColumnForReferenceInteger(column);
		}
		else if (referenceId == DisplayType.YesNo)
		{
			updateColumnForYesNoReference(column);
		}
	}

	@CalloutMethod(columnNames = { I_AD_Column.COLUMNNAME_AD_Reference_Value_ID })
	public void onAD_Reference_Value(final I_AD_Column column)
	{
		updateIsExcludeFromZoomTargets(column);
	}

	private void updateColumnForReferenceInteger(final I_AD_Column column)
	{
		column.setIsMandatory(true);
		column.setDefaultValue("0");
	}

	private void updateColumnForYesNoReference(final I_AD_Column column)
	{
		final String columnName = column.getColumnName();

		if (columnName == null)
		{
			// nothing to do
			return;
		}

		updateFlagColumn(column);
	}

	private void updateIsExcludeFromZoomTargets(final I_AD_Column column)
	{
		final ReferenceId referenceId = ReferenceId.ofRepoIdOrNull(column.getAD_Reference_ID());
		if (referenceId == null)
		{
			return;
		}

		final boolean includeInRelatedDocuments = referenceId.getRepoId() == DisplayType.TableDir
				|| (referenceId.getRepoId() == DisplayType.Search && column.getAD_Reference_Value_ID() <= 0);

		column.setIsExcludeFromZoomTargets(!includeInRelatedDocuments);
	}

	@Data
	@Builder
	private static class ColumnTypeAndLengthStats
	{
		@NonNull private final ReferenceId displayType;
		@Nullable private final ReferenceId referenceValueId;
		private final int fieldLength;
		private final int existingCasesCount;
	}

	private Optional<ColumnTypeAndLengthStats> suggestColumnTypeAndLength(@NonNull final AdElementId adElementId)
	{
		final String sql = "SELECT "
				+ " " + I_AD_Column.COLUMNNAME_AD_Reference_ID
				+ ", " + I_AD_Column.COLUMNNAME_AD_Reference_Value_ID
				+ ", " + I_AD_Column.COLUMNNAME_FieldLength
				+ ", COUNT(1) as count"
				+ " FROM " + I_AD_Column.Table_Name
				+ " WHERE " + I_AD_Column.COLUMNNAME_AD_Element_ID + "=?"
				+ " GROUP BY "
				+ " " + I_AD_Column.COLUMNNAME_AD_Reference_ID
				+ ", " + I_AD_Column.COLUMNNAME_AD_Reference_Value_ID
				+ ", " + I_AD_Column.COLUMNNAME_FieldLength
				+ " ORDER BY count DESC";

		final ImmutableList<ColumnTypeAndLengthStats> suggestions = DB.retrieveRows(
				sql,
				Collections.singletonList(adElementId),
				rs -> ColumnTypeAndLengthStats.builder()
						.displayType(ReferenceId.ofRepoId(rs.getInt(I_AD_Column.COLUMNNAME_AD_Reference_ID)))
						.referenceValueId(ReferenceId.ofRepoIdOrNull(rs.getInt(I_AD_Column.COLUMNNAME_AD_Reference_Value_ID)))
						.fieldLength(rs.getInt(I_AD_Column.COLUMNNAME_FieldLength))
						.existingCasesCount(rs.getInt("count"))
						.build());

		if (suggestions.isEmpty())
		{
			return Optional.empty();
		}
		else
		{
			logger.info("Returning first suggestion from: {}", suggestions);
			return Optional.of(suggestions.get(0));
		}
	}

	private void updateColumnFromSuggestion(@NonNull final I_AD_Column column, @NonNull final ColumnTypeAndLengthStats suggestion)
	{
		column.setAD_Reference_ID(suggestion.getDisplayType().getRepoId());
		column.setAD_Reference_Value_ID(ReferenceId.toRepoId(suggestion.getReferenceValueId()));
		column.setFieldLength(suggestion.getFieldLength());
	}
}
