package de.metas.ui.web.picking;

import org.adempiere.ad.service.IADReferenceDAO;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import de.metas.i18n.ITranslatableString;
import de.metas.inoutcandidate.model.I_M_Packageable_V;
import de.metas.picking.model.X_M_Picking_Config;
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
import de.metas.util.Services;

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
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PickingTerminalByWarehouseAndProductViewCustomizer implements SqlViewCustomizer
{
	private static final ViewProfileId PROFILE_ID = ViewProfileId.fromJson(X_M_Picking_Config.WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByProduct);
	private final ViewProfile PROFILE;

	private static final String FIELDNAME_ProductOrBPartner = "ProductOrBPartner";

	public PickingTerminalByWarehouseAndProductViewCustomizer()
	{
		final IADReferenceDAO referenceDAO = Services.get(IADReferenceDAO.class);
		final ITranslatableString caption = referenceDAO.retrieveListNameTranslatableString(X_M_Picking_Config.WEBUI_PICKINGTERMINAL_VIEWPROFILE_AD_Reference_ID, X_M_Picking_Config.WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByProduct);
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
				.clearDefaultOrderBys()
				.defaultOrderBy(DocumentQueryOrderBy.byFieldName(FIELDNAME_ProductOrBPartner, true))
				.orderByAliasFieldNames(FIELDNAME_ProductOrBPartner,
						I_M_Packageable_V.COLUMNNAME_M_Product_ID,
						I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID,
						I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
	}

	private static final SqlViewGroupingBinding createSqlViewGroupingBinding()
	{
		return SqlViewGroupingBinding.builder()
				.groupBy(I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID)
				.groupBy(I_M_Packageable_V.COLUMNNAME_M_Product_ID)
				.columnSql(I_M_Packageable_V.COLUMNNAME_QtyOrdered, SqlSelectValue.builder()
						.virtualColumnSql("SUM(QtyOrdered)")
						.columnNameAlias(I_M_Packageable_V.COLUMNNAME_QtyOrdered)
						.build())
				.columnSql(I_M_Packageable_V.COLUMNNAME_QtyPickedOrDelivered, SqlSelectValue.builder()
						.virtualColumnSql("SUM(" + I_M_Packageable_V.COLUMNNAME_QtyPickedOrDelivered + ")")
						.columnNameAlias(I_M_Packageable_V.COLUMNNAME_QtyPickedOrDelivered)
						.build())
				.columnSql(I_M_Packageable_V.COLUMNNAME_DeliveryDate, SqlSelectValue.builder()
						.virtualColumnSql("MIN(DeliveryDate)")
						.columnNameAlias(I_M_Packageable_V.COLUMNNAME_DeliveryDate)
						.build())
				.columnSql(I_M_Packageable_V.COLUMNNAME_PreparationDate, SqlSelectValue.builder()
						.virtualColumnSql("IF_MIN(DeliveryDate, PreparationDate)")
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
				.addField(DocumentLayoutElementFieldDescriptor.builder(FIELDNAME_ProductOrBPartner)
						.setPublicField(true))
				.build());

		viewLayoutBuilder.elementsOrder(
				FIELDNAME_ProductOrBPartner,
				I_M_Packageable_V.COLUMNNAME_C_OrderSO_ID,
				I_M_Packageable_V.COLUMNNAME_QtyOrdered,
				I_M_Packageable_V.COLUMNNAME_QtyPickedOrDelivered,
				I_M_Packageable_V.COLUMNNAME_M_Warehouse_ID,
				I_M_Packageable_V.COLUMNNAME_PreparationDate);
	}

	@Override
	public void customizeViewRow(final ViewRow.Builder rowBuilder)
	{
		final LookupValue productOrBPartnerLV = createProductOrBPartnerFieldValue(rowBuilder);
		rowBuilder.putFieldValue(FIELDNAME_ProductOrBPartner, productOrBPartnerLV);
	}

	private LookupValue createProductOrBPartnerFieldValue(final ViewRow.Builder rowBuilder)
	{
		// Grouping row
		if (rowBuilder.isRootRow())
		{
			final LookupValue productLV = rowBuilder.getFieldValueAsLookupValue(I_M_Packageable_V.COLUMNNAME_M_Product_ID);
			return productLV;
		}
		// Detail/included row
		else
		{
			final LookupValue bpartnerLV = rowBuilder.getFieldValueAsLookupValue(I_M_Packageable_V.COLUMNNAME_C_BPartner_Customer_ID);
			final LookupValue bpLocationLV = rowBuilder.getFieldValueAsLookupValue(I_M_Packageable_V.COLUMNNAME_C_BPartner_Location_ID);
			return LookupValue.concat(bpartnerLV, bpLocationLV);
		}
	}

}
