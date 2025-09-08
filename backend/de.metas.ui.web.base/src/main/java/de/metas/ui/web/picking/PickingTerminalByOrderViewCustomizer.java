package de.metas.ui.web.picking;

import de.metas.ad_reference.ADReferenceService;
import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.picking.model.X_M_Picking_Config;
import de.metas.ui.web.picking.packageable.filters.ProductBarcodeFilterConverter;
import de.metas.ui.web.view.SqlViewCustomizer;
import de.metas.ui.web.view.ViewProfile;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.ViewRow;
import de.metas.ui.web.view.descriptor.SqlViewBinding;
import de.metas.ui.web.view.descriptor.SqlViewGroupingBinding;
import de.metas.ui.web.view.descriptor.SqlViewRowIdsConverters;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.DocumentQueryOrderBy;
import lombok.NonNull;
import org.adempiere.ad.column.ColumnSql;
import org.springframework.stereotype.Component;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class PickingTerminalByOrderViewCustomizer implements SqlViewCustomizer
{
	private static final ViewProfileId PROFILE_ID = ViewProfileId.fromJson(X_M_Picking_Config.WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByOrder);
	private final ViewProfile PROFILE;

	private static final String FIELDNAME_OrderOrBPLocation = "OrderOrBPLocation";

	public PickingTerminalByOrderViewCustomizer(@NonNull final ADReferenceService adReferenceService)
	{
		final ITranslatableString caption = adReferenceService.retrieveListNameTranslatableString(X_M_Picking_Config.WEBUI_PICKINGTERMINAL_VIEWPROFILE_AD_Reference_ID, X_M_Picking_Config.WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByOrder);
		PROFILE = ViewProfile.of(PROFILE_ID, caption);
	}

	@Override
	public WindowId getWindowId()
	{
		return PickingConstants.WINDOWID_PackageableView;
	}

	@Override
	public ViewProfile getProfile()
	{
		return PROFILE;
	}

	@Override
	public void customizeSqlViewBinding(final SqlViewBinding.Builder sqlViewBindingBuilder)
	{
		sqlViewBindingBuilder
				.groupingBinding(createSqlViewGroupingBinding())
				.filterConverter(ProductBarcodeFilterConverter.instance)
				.clearDefaultOrderBys()
				.defaultOrderBy(DocumentQueryOrderBy.byFieldName(FIELDNAME_OrderOrBPLocation, true))
				.orderByAliasFieldNames(FIELDNAME_OrderOrBPLocation,
						I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID,
						I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID,
						I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
	}

	private static SqlViewGroupingBinding createSqlViewGroupingBinding()
	{
		return SqlViewGroupingBinding.builder()
				.groupBy(I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID)
				.groupBy(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID)
				.groupBy(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID)
				.columnSql(I_M_Packageable_V.COLUMNNAME_DeliveryDate, SqlSelectValue.builder()
						.virtualColumnSql(ColumnSql.ofSql("MIN(DeliveryDate)"))
						.columnNameAlias(I_M_Packageable_V.COLUMNNAME_DeliveryDate)
						.build())
				.columnSql(I_M_Packageable_V.COLUMNNAME_PreparationDate, SqlSelectValue.builder()
						.virtualColumnSql(ColumnSql.ofSql("IF_MIN(DeliveryDate, PreparationDate)"))
						.columnNameAlias(I_M_Packageable_V.COLUMNNAME_PreparationDate)
						.build())
				.rowIdsConverter(SqlViewRowIdsConverters.TO_INT_EXCLUDING_STRINGS)
				.build();
	}

	@Override
	public void customizeViewLayout(final ViewLayout.ChangeBuilder viewLayoutBuilder)
	{
		viewLayoutBuilder.element(DocumentLayoutElementDescriptor.builder()
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.addField(DocumentLayoutElementFieldDescriptor.builder(FIELDNAME_OrderOrBPLocation)
						.setPublicField(true))
				.build());

		viewLayoutBuilder.elementsOrder(
				FIELDNAME_OrderOrBPLocation,
				I_M_Packageable_V.COLUMNNAME_M_Product_ID,
				I_M_Packageable_V.COLUMNNAME_QtyOrdered,
				I_M_Packageable_V.COLUMNNAME_QtyPickedOrDelivered,
				I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID,
				I_M_Packageable_V.COLUMNNAME_PreparationDate);
	}

	@Override
	public void customizeViewRow(final ViewRow.Builder rowBuilder)
	{
		final LookupValue orderOrBPLocationLV = createOrderOrBPLocation(rowBuilder);
		rowBuilder.putFieldValue(FIELDNAME_OrderOrBPLocation, orderOrBPLocationLV);
	}

	private LookupValue createOrderOrBPLocation(final ViewRow.Builder rowBuilder)
	{
		// Grouping row
		if (rowBuilder.isRootRow())
		{
			final LookupValue orderLV = rowBuilder.getFieldValueAsLookupValue(I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID);
			final LookupValue bpartnerLV = rowBuilder.getFieldValueAsLookupValue(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID);
			return LookupValue.concat(orderLV, bpartnerLV);
		}
		// Detail/included row
		else
		{
			return rowBuilder.getFieldValueAsLookupValue(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
		}
	}

}
