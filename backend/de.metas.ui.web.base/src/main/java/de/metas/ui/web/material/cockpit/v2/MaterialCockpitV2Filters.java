package de.metas.ui.web.material.cockpit.v2;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;
import de.metas.ui.web.document.filter.DocumentFilterParamDescriptor;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.document.filter.provider.ImmutableDocumentFilterDescriptorsProvider;
import de.metas.ui.web.view.CreateViewRequest;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.NonNull;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Component
public class MaterialCockpitV2Filters
{
	private static final String FILTER_ID_Product = "Product";
	private static final String FILTER_ID_Warehouse = "Warehouse";
	private static final String FILTER_ID_SupplyType = "SupplyType";

	private static final String PARAM_ProductValue = "ProductValue";
	private static final String PARAM_M_Warehouse_ID = "M_Warehouse_ID";
	private static final String PARAM_SupplyType = "SupplyType";

	public DocumentFilterDescriptorsProvider getFilterDescriptors()
	{
		return ImmutableDocumentFilterDescriptorsProvider.of(
				createProductFilterDescriptor(),
				createWarehouseFilterDescriptor(),
				createSupplyTypeFilterDescriptor());
	}

	public DocumentFilterList extractDocumentFilters(@NonNull final CreateViewRequest request)
	{
		return request.getFilters();
	}

	private static DocumentFilterDescriptor createProductFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_Product)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_ProductValue)
						.setDisplayName("Product")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.build())
				.build();
	}

	private static DocumentFilterDescriptor createWarehouseFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_Warehouse)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_M_Warehouse_ID)
						.setDisplayName("Warehouse")
						.setWidgetType(DocumentFieldWidgetType.Integer)
						.build())
				.build();
	}

	private static DocumentFilterDescriptor createSupplyTypeFilterDescriptor()
	{
		return DocumentFilterDescriptor.builder()
				.setFilterId(FILTER_ID_SupplyType)
				.setFrequentUsed(true)
				.addParameter(DocumentFilterParamDescriptor.builder()
						.setFieldName(PARAM_SupplyType)
						.setDisplayName("Supply Type")
						.setWidgetType(DocumentFieldWidgetType.Text)
						.build())
				.build();
	}

	/**
	 * Convert the frontend filter parameters to SQL WHERE clause parts.
	 *
	 * @return SQL WHERE clause and parameters
	 */
	public SqlWhereClause toSqlWhereClause(@NonNull final DocumentFilterList filters)
	{
		final StringBuilder whereClause = new StringBuilder();
		final List<Object> sqlParams = new ArrayList<>();

		for (final DocumentFilter filter : filters.toList())
		{
			final String filterId = filter.getFilterId();
			switch (filterId)
			{
				case FILTER_ID_Product:
				{
					final String productValue = filter.getParameterValueAsString(PARAM_ProductValue, null);
					if (productValue != null && !productValue.isEmpty())
					{
						if (whereClause.length() > 0)
						{
							whereClause.append(" AND ");
						}
						whereClause.append("(UPPER(v.ProductValue) LIKE UPPER(?) OR UPPER(v.ProductName) LIKE UPPER(?))");
						sqlParams.add("%" + productValue + "%");
						sqlParams.add("%" + productValue + "%");
					}
					break;
				}
				case FILTER_ID_Warehouse:
				{
					final int warehouseId = filter.getParameterValueAsInt(PARAM_M_Warehouse_ID, -1);
					if (warehouseId > 0)
					{
						if (whereClause.length() > 0)
						{
							whereClause.append(" AND ");
						}
						whereClause.append("v.M_Warehouse_ID = ?");
						sqlParams.add(warehouseId);
					}
					break;
				}
				case FILTER_ID_SupplyType:
				{
					final String supplyType = filter.getParameterValueAsString(PARAM_SupplyType, null);
					if (supplyType != null && !supplyType.isEmpty())
					{
						if (whereClause.length() > 0)
						{
							whereClause.append(" AND ");
						}
						whereClause.append("v.SupplyType = ?");
						sqlParams.add(supplyType);
					}
					break;
				}
				default:
					// ignore unknown filters
					break;
			}
		}

		return new SqlWhereClause(whereClause.toString(), sqlParams);
	}

	public static class SqlWhereClause
	{
		private final String sql;
		private final List<Object> params;

		public SqlWhereClause(@NonNull final String sql, @NonNull final List<Object> params)
		{
			this.sql = sql;
			this.params = ImmutableList.copyOf(params);
		}

		public String getSql()
		{
			return sql;
		}

		public List<Object> getParams()
		{
			return params;
		}

		public boolean isEmpty()
		{
			return sql.isEmpty();
		}
	}
}
