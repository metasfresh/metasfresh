package de.metas.aggregation.api.impl;

import com.google.common.collect.ImmutableMap;
import de.metas.aggregation.api.Aggregation;
import de.metas.aggregation.api.AggregationAttribute;
import de.metas.aggregation.api.AggregationId;
import de.metas.aggregation.api.AggregationItem;
import de.metas.aggregation.api.AggregationItem.Type;
import de.metas.aggregation.api.AggregationItemId;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.I_C_AggregationItem;
import de.metas.aggregation.model.I_C_Aggregation_Attribute;
import de.metas.aggregation.model.X_C_AggregationItem;
import de.metas.aggregation.model.X_C_Aggregation_Attribute;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.expression.api.IExpressionFactory;
import org.adempiere.ad.expression.api.ILogicExpression;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.api.MinimalColumnInfo;
import org.adempiere.exceptions.AdempiereException;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Creates {@link Aggregation} from {@link I_C_Aggregation} structure.
 *
 * @author tsa
 *
 */
/* package */class C_Aggregation2AggregationBuilder
{
	// services
	private final IExpressionFactory expressionFactory = Services.get(IExpressionFactory.class);
	private final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
	private final AggregationDAO aggregationDAO;

	//
	// Parameters
	private I_C_Aggregation _rootAggregation;
	private String _tableName = null;

	//
	// Status
	private final Set<Integer> seenAggregationIds = new HashSet<>();

	C_Aggregation2AggregationBuilder(
			@NonNull final AggregationDAO aggregationDAO)
	{
		this.aggregationDAO = aggregationDAO;
	}

	public Aggregation build()
	{
		return createAggregation(getRootAggregation());
	}

	public C_Aggregation2AggregationBuilder setC_Aggregation(@NonNull final I_C_Aggregation aggregation)
	{
		_rootAggregation = aggregation;
		_tableName = getRootAggregation().getAD_Table().getTableName();
		return this;
	}

	private I_C_Aggregation getRootAggregation()
	{
		Check.assumeNotNull(_rootAggregation, "rootAggregation not null");
		return _rootAggregation;
	}

	private String getTableName()
	{
		Check.assumeNotEmpty(_tableName, "_tableName not empty");
		return _tableName;
	}

	private Aggregation createAggregation(@NonNull final I_C_Aggregation aggregationDef)
	{
		// Avoid cycles (i.e. self including an aggregation, direct or indirect)
		if (!checkEligible(aggregationDef))
		{
			throw new AdempiereException("Aggregation cycle detected: " + aggregationDef);
		}

		final String tableName = aggregationDef.getAD_Table().getTableName();
		final List<I_C_AggregationItem> aggregationItemsDef = aggregationDAO.retrieveAllItems(aggregationDef);

		final LinkedHashMap<AggregationItemId, AggregationItem> aggregationItems = createAggregationItems(aggregationItemsDef);
		if (aggregationItems.isEmpty())
		{
			throw new AdempiereException("Invalid aggregation " + aggregationDef.getName() + ". It shall have at least one item.")
					.setParameter("aggregation", aggregationDef)
					.setParameter("tableName", tableName);
		}

		//
		// Create and return the aggregation
		return Aggregation.builder()
				.id(AggregationId.ofRepoId(aggregationDef.getC_Aggregation_ID()))
				.tableName(tableName)
				.items(aggregationItems.values())
				.build();
	}

	private LinkedHashMap<AggregationItemId, AggregationItem> createAggregationItems(final List<I_C_AggregationItem> aggregationItemsDef)
	{
		//
		// Create aggregation items
		final LinkedHashMap<AggregationItemId, AggregationItem> aggregationItemsAll = new LinkedHashMap<>(aggregationItemsDef.size());
		for (final I_C_AggregationItem aggregationItemDef : aggregationItemsDef)
		{
			// Skip inactive items
			if (!aggregationItemDef.isActive())
			{
				continue;
			}

			final String itemType = aggregationItemDef.getType();
			final Map<AggregationItemId, AggregationItem> aggregationItems;
			if (X_C_AggregationItem.TYPE_Column.equals(itemType))
			{
				final AggregationItem aggregationItem = createAggregationItem_TypeColumn(aggregationItemDef);
				aggregationItems = aggregationItem == null ? null : ImmutableMap.of(aggregationItem.getId(), aggregationItem);
			}
			else if (X_C_AggregationItem.TYPE_IncludedAggregation.equals(itemType))
			{
				aggregationItems = createAggregationItem_TypeIncludedAggregation(aggregationItemDef);
			}
			else if (X_C_AggregationItem.TYPE_Attribute.equals(itemType))
			{
				aggregationItems = createAggregationItem_TypeAttribute(aggregationItemDef);
			}
			else
			{
				throw new AdempiereException("Unknown aggregation item type '" + itemType + "' for " + aggregationItemDef);
			}

			// Add it to items list
			if (aggregationItems != null && !aggregationItems.isEmpty())
			{
				aggregationItemsAll.putAll(aggregationItems);
			}
		}
		return aggregationItemsAll;
	}

	private AggregationItem createAggregationItem_TypeColumn(final I_C_AggregationItem aggregationItemDef)
	{
		// Skip items with inactive records (because those are not present in model, for sure)
		final AdColumnId adColumnId = AdColumnId.ofRepoIdOrNull(aggregationItemDef.getAD_Column_ID());
		final MinimalColumnInfo column = adColumnId != null
				? adTableDAO.getMinimalColumnInfo(adColumnId)
				: null;
		if (column == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Column_ID@"
					+ "\n @C_Aggregation_ID@: " + aggregationItemDef.getC_Aggregation().getName()
					+ "\n @C_AggregationItem_ID@: " + aggregationItemDef);
		}
		if (!column.isActive())
		{
			return null;
		}

		//
		// Create aggregation item
		final String columnName = column.getColumnName();
		final int displayType = column.getDisplayType();
		final ILogicExpression includeLogic = getLogicExpression(aggregationItemDef);
		return AggregationItem.builder()
				.id(AggregationItemId.ofRepoId(aggregationItemDef.getC_AggregationItem_ID()))
				.type(Type.ModelColumn)
				.columnName(columnName)
				.displayType(displayType)
				.attribute(null)
				.includeLogic(includeLogic)
				.build();
	}

	private ILogicExpression getLogicExpression(final I_C_AggregationItem aggregationItemDef)
	{
		final String includeLogicStr = aggregationItemDef.getIncludeLogic();
		final ILogicExpression includeLogic;
		if (Check.isEmpty(includeLogicStr, true))
		{
			includeLogic = ConstantLogicExpression.TRUE;
		}
		else
		{
			includeLogic = expressionFactory.compile(includeLogicStr, ILogicExpression.class);
		}
		return includeLogic;
	}

	private LinkedHashMap<AggregationItemId, AggregationItem> createAggregationItem_TypeIncludedAggregation(final I_C_AggregationItem aggregationItemDef)
	{
		final I_C_Aggregation includedAggregationDef = aggregationItemDef.getIncluded_Aggregation();
		if (includedAggregationDef == null)
		{
			return null;
		}
		if (!includedAggregationDef.isActive())
		{
			return null;
		}
		if (!checkEligible(includedAggregationDef))
		{
			return null;
		}

		// Make sure there is no IncludeLogic because that's not yet supported.
		// NOTE: when we will implement it we need to merge the IncludeLogic defined on this include item
		// into the IncludeLogic defined on included items.
		if (!Check.isEmpty(aggregationItemDef.getIncludeLogic(), true))
		{
			throw new AdempiereException("Defining IncludeLogic for an included aggregation is not supported yet");
		}

		final List<I_C_AggregationItem> includedAggregationItemsDef = aggregationDAO.retrieveAllItems(includedAggregationDef);
		return createAggregationItems(includedAggregationItemsDef);
	}

	private Map<AggregationItemId, AggregationItem> createAggregationItem_TypeAttribute(final I_C_AggregationItem aggregationItemDef)
	{
		final I_C_Aggregation_Attribute attributeDef = aggregationItemDef.getC_Aggregation_Attribute();
		if (attributeDef == null || attributeDef.getC_Aggregation_Attribute_ID() <= 0)
		{
			throw new AdempiereException("@NotFound@ @C_Aggregation_Attribute_ID@"
					+ "\n @C_Aggregation_ID@: " + aggregationItemDef.getC_Aggregation().getName()
					+ "\n @C_AggregationItem_ID@: " + aggregationItemDef);
		}
		if (!attributeDef.isActive())
		{
			return null;
		}

		final String attributeType = attributeDef.getType();
		final AggregationAttribute aggregationAttribute;
		if (X_C_Aggregation_Attribute.TYPE_Attribute.equals(attributeType))
		{
			final String attributeName = attributeDef.getCode();
			aggregationAttribute = new AggregationAttribute(attributeName);
		}
		else
		{
			throw new AdempiereException("@Unknown@ @Type@: " + attributeType
					+ "\n @C_Aggregation_ID@: " + aggregationItemDef.getC_Aggregation().getName()
					+ "\n @C_AggregationItem_ID@: " + aggregationItemDef
					+ "\n @C_Aggregation_Attribute_ID@: " + attributeDef);
		}

		//
		// Create aggregation item
		final ILogicExpression includeLogic = getLogicExpression(aggregationItemDef);
		final AggregationItem aggregationItem = AggregationItem.builder()
				.id(AggregationItemId.ofRepoId(aggregationItemDef.getC_AggregationItem_ID()))
				.type(Type.Attribute)
				.columnName(null)
				.displayType(-1)
				.attribute(aggregationAttribute)
				.includeLogic(includeLogic)
				.build();
		return ImmutableMap.of(aggregationItem.getId(), aggregationItem);
	}

	/**
	 *
	 * @return true if aggregation is eligible and shall be considered
	 * @throws AdempiereException if aggregation is not eligible and this is not acceptable
	 */
	private boolean checkEligible(final I_C_Aggregation aggregationDef)
	{
		if (aggregationDef == null)
		{
			return false; // not eligible
		}
		final int aggregationId = aggregationDef.getC_Aggregation_ID();
		if (aggregationId <= 0)
		{
			return false; // not eligible
		}

		// Avoid cycles (i.e. self including an aggregation, direct or indirect)
		if (!seenAggregationIds.add(aggregationId))
		{
			throw new AdempiereException("Aggregation cycle detected: " + aggregationDef);
			// return false; // not eligible
		}

		final String tableName = aggregationDef.getAD_Table().getTableName();
		final String tableNameExpected = getTableName();
		if (!Objects.equals(tableNameExpected, tableName))
		{
			throw new AdempiereException("Aggregation's table name does not match"
					+ "\n @C_Aggregation_ID@: " + aggregationDef
					+ "\n @TableName@: " + tableName
					+ "\n @TableName@ (expected): " + tableNameExpected);
		}

		return true; // eligible
	}
}
