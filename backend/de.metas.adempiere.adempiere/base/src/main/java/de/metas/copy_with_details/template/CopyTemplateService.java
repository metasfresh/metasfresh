package de.metas.copy_with_details.template;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.logging.LogManager;
import de.metas.util.InSetPredicate;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.POInfo;
import org.compiere.model.copy.ColumnCloningStrategy;
import org.compiere.model.copy.POValuesCopyStrategies;
import org.compiere.model.copy.TableDownlineCloningStrategy;
import org.compiere.model.copy.TableWhenChildCloningStrategy;
import org.compiere.model.copy.ValueToCopy;
import org.compiere.util.DisplayType;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class CopyTemplateService
{
	private static final Logger logger = LogManager.getLogger(CopyTemplateService.class);

	private static final String COLUMNNAME_Value = "Value";
	private static final String COLUMNNAME_Name = "Name";
	private static final String COLUMNNAME_IsActive = "IsActive";
	private static final String COLUMNNAME_Processed = "Processed";
	private static final String COLUMNNAME_Line = "Line";
	private static final String COLUMNNAME_SeqNo = "SeqNo";

	private final ImmutableMap<String, CopyTemplateCustomizer> customizersByTableName;

	CopyTemplateService(
			@NonNull final Optional<List<CopyTemplateCustomizer>> optionalCustomizers)
	{
		this.customizersByTableName = optionalCustomizers
				.map(customizers -> Maps.uniqueIndex(customizers, CopyTemplateCustomizer::getTableName))
				.orElseGet(ImmutableMap::of);
		logger.info("Customizers: {}", this.customizersByTableName);
	}

	private Optional<CopyTemplateCustomizer> getCustomizer(final String tableName) {return Optional.ofNullable(customizersByTableName.get(tableName));}

	public CopyTemplate getCopyTemplate(final POInfo poInfo)
	{
		final String tableName = poInfo.getTableName();
		final String keyColumnName = poInfo.getKeyColumnName();
		return CopyTemplate.builder()
				.tableName(tableName)
				.keyColumnName(keyColumnName)
				.columns(extractCopyTemplateColumns(poInfo))
				.childTemplates(getChildTemplates(tableName, keyColumnName, poInfo.getDownlineCloningStrategy()))
				.build();
	}

	@NonNull
	private ArrayList<CopyTemplateColumn> extractCopyTemplateColumns(@NonNull final POInfo poInfo)
	{
		final ArrayList<CopyTemplateColumn> columns = new ArrayList<>();
		for (int columnIndex = 0, columnsCount = poInfo.getColumnCount(); columnIndex < columnsCount; columnIndex++)
		{
			if (poInfo.isVirtualColumn(columnIndex) || poInfo.isKey(columnIndex))
			{
				continue;
			}

			final String columnName = poInfo.getColumnNameNotNull(columnIndex);
			final ValueToCopy valueToCopy = extractValueToCopy(poInfo, columnName, ValueToCopy.DIRECT_COPY);
			columns.add(CopyTemplateColumn.builder()
					.columnName(columnName)
					.valueToCopy(valueToCopy)
					.build());
		}
		return columns;
	}

	private ValueToCopy extractValueToCopy(
			@NonNull final POInfo poInfo,
			@NonNull final String columnName,
			@NonNull final ValueToCopy defaultValueToCopy)
	{
		final ColumnCloningStrategy cloningStrategy = poInfo.getColumnNotNull(columnName).getCloningStrategy();
		switch (cloningStrategy)
		{
			case Auto:
				return extractValueToCopy_Autodetect(poInfo, columnName, defaultValueToCopy);
			case DirectCopy:
				return ValueToCopy.DIRECT_COPY;
			case UseDefaultValue:
				return ValueToCopy.COMPUTE_DEFAULT;
			case MakeUnique:
				return ValueToCopy.APPEND_UNIQUE_SUFFIX;
			case Skip:
				return ValueToCopy.SKIP;
			default:
				throw new AdempiereException("Unknown column cloning policy: " + cloningStrategy);
		}
	}

	private ValueToCopy extractValueToCopy_Autodetect(
			@NonNull final POInfo poInfo,
			@NonNull final String columnName,
			@NonNull final ValueToCopy defaultValueToCopy)
	{
		final ValueToCopy valueToCopy = getCustomizer(poInfo.getTableName())
				.map(customizer -> customizer.extractValueToCopy(poInfo, columnName))
				.orElse(ValueToCopy.NOT_SPECIFIED);

		if (valueToCopy.isSpecified())
		{
			return valueToCopy;
		}
		else if (poInfo.isCalculated(columnName))
		{
			return ValueToCopy.COMPUTE_DEFAULT;
		}
		if (COLUMNNAME_IsActive.equals(columnName))
		{
			return ValueToCopy.DIRECT_COPY;
		}
		else if ((COLUMNNAME_Name.equals(columnName) || COLUMNNAME_Value.equals(columnName))
				&& DisplayType.isText(poInfo.getColumnDisplayType(columnName)))
		{
			return poInfo.isUseDocSequence(columnName)
					? ValueToCopy.SKIP
					: ValueToCopy.APPEND_UNIQUE_SUFFIX;
		}
		else if (POValuesCopyStrategies.isSkipStandardColumn(columnName, poInfo.isCalculated(columnName)))
		{
			return ValueToCopy.SKIP;
		}
		else
		{
			return defaultValueToCopy;
		}
	}

	private List<CopyTemplate> getChildTemplates(
			@NonNull final String tableName,
			@Nullable final String keyColumnName,
			@NonNull final TableDownlineCloningStrategy downlineCloningStrategy)
	{
		//
		// If we have multiple keys return empty list because there are no children for sure...
		if (keyColumnName == null)
		{
			return ImmutableList.of();
		}

		final ArrayList<CopyTemplate> result = new ArrayList<>();
		for (final POInfo childPOInfo : getChildPOInfos(tableName, keyColumnName, downlineCloningStrategy))
		{
			if (!childPOInfo.hasColumnName(keyColumnName))
			{
				continue;
			}

			result.add(CopyTemplate.builder()
					.tableName(childPOInfo.getTableName())
					.keyColumnName(childPOInfo.getKeyColumnName())
					.columns(extractCopyTemplateColumns(childPOInfo))
					.linkColumnName(keyColumnName)
					.orderByColumnNames(extractChildPOInfoOrderBys(childPOInfo))
					.build());
		}

		return result;
	}

	private List<POInfo> getChildPOInfos(
			@NonNull final String tableName,
			@NonNull String keyColumnName,
			@NonNull TableDownlineCloningStrategy downlineCloningStrategy)
	{
		if (downlineCloningStrategy.isSkip())
		{
			return ImmutableList.of();
		}

		final InSetPredicate<String> onlyChildTableNames = getCustomizer(tableName)
				.map(CopyTemplateCustomizer::getChildTableNames)
				.orElse(InSetPredicate.any());

		final Stream<POInfo> childPOInfos;
		if (onlyChildTableNames.isNone())
		{
			return ImmutableList.of();
		}
		else if (onlyChildTableNames.isAny())
		{
			childPOInfos = POInfo.getPOInfoMap().stream()
					.filter(childPOInfo -> isEligibleChildWhenAutoDiscovering(tableName, childPOInfo, keyColumnName));
		}
		else
		{
			childPOInfos = onlyChildTableNames.toSet().stream()
					.map(POInfo::getPOInfoNotNull);
		}

		return childPOInfos
				.filter(childPOInfo -> isIncludeChild(downlineCloningStrategy, childPOInfo))
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isIncludeChild(
			@NonNull final TableDownlineCloningStrategy parentDownlineStrategy,
			@NonNull final POInfo childPOInfo)
	{
		if (childPOInfo.getCloningEnabled().isDisabled())
		{
			return false;
		}

		final TableWhenChildCloningStrategy childCloningStrategy = childPOInfo.getWhenChildCloningStrategy();
		switch (parentDownlineStrategy)
		{
			case Auto:
				return !childCloningStrategy.isSkip();
			case OnlyIncluded:
				return childCloningStrategy.isIncluded();
			case Skip:
				return false;
			default:
				logger.warn("Parent downline strategy `{}` not handled. Considering child `{}` not included", parentDownlineStrategy, childPOInfo);
				return false;
		}
	}

	private static boolean isEligibleChildWhenAutoDiscovering(
			@NonNull String parentTableName,
			@NonNull POInfo childPOInfo,
			@NonNull String linkColumnName)
	{
		if (childPOInfo.isView())
		{
			return false;
		}
		if (!childPOInfo.isParentLinkColumn(linkColumnName))
		{
			return false;
		}

		final boolean isLine = (parentTableName + "Line").equals(childPOInfo.getTableName());
		if (!isLine && childPOInfo.hasColumnName(COLUMNNAME_Processed))
		{
			return false;
		}

		return !isSkipChildTableNameWhenAutodiscovering(childPOInfo.getTableName());
	}

	private static boolean isSkipChildTableNameWhenAutodiscovering(final String childTableName)
	{
		final String childTableNameUC = childTableName.toUpperCase();
		return childTableNameUC.endsWith("_ACCT") // acct table
				|| childTableNameUC.startsWith("I_") // import tables
				|| childTableNameUC.endsWith("_TRL") // translation tables
				|| childTableNameUC.startsWith("M_COST") // cost tables
				|| childTableNameUC.startsWith("T_") // temporary tables
				|| childTableNameUC.equals("M_PRODUCT_COSTING") // product costing
				|| childTableNameUC.equals("M_STORAGE") // storage table
				|| childTableNameUC.equals("C_BP_WITHHOLDING") // at Patrick's request, this was removed, because is not used
				|| childTableNameUC.startsWith("M_") && childTableNameUC.endsWith("MA") // material allocation table
				;
	}

	@NonNull
	private static ImmutableList<String> extractChildPOInfoOrderBys(final POInfo childPOInfo)
	{
		final ImmutableList.Builder<String> orderByColumnNames = ImmutableList.builder();
		if (childPOInfo.hasColumnName(COLUMNNAME_Line))
		{
			orderByColumnNames.add(COLUMNNAME_Line);
		}
		if (childPOInfo.hasColumnName(COLUMNNAME_SeqNo))
		{
			orderByColumnNames.add(COLUMNNAME_SeqNo);
		}
		if (childPOInfo.getKeyColumnName() != null)
		{
			orderByColumnNames.add(childPOInfo.getKeyColumnName());
		}

		return orderByColumnNames.build();
	}

}
