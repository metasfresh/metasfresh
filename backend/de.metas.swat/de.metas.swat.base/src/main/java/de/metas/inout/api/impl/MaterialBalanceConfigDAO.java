package de.metas.inout.api.impl;

import de.metas.calendar.standard.CalendarId;
import de.metas.inout.api.IMaterialBalanceConfigBL;
import de.metas.inout.api.IMaterialBalanceConfigDAO;
import de.metas.inout.api.MaterialBalanceConfig;
import de.metas.inout.model.I_M_Material_Balance_Config;
import de.metas.inout.spi.IMaterialBalanceConfigMatcher;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_Product;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public class MaterialBalanceConfigDAO implements IMaterialBalanceConfigDAO
{

	@Override
	public MaterialBalanceConfig retrieveFitBalanceConfig(final I_M_InOutLine line)
	{
		final IQueryBuilder<I_M_Material_Balance_Config> queryBuilder = createQuery(line);

		final I_M_Material_Balance_Config configRecord = queryBuilder
				.create()
				.first();

		if (configRecord == null)
		{
			return null;
		}

		return toMaterialBalanceConfig(configRecord);
	}

	private IQueryBuilder<I_M_Material_Balance_Config> createQuery(final I_M_InOutLine line)
	{
		// Services
		final IMaterialBalanceConfigBL materialConfigBL = Services.get(IMaterialBalanceConfigBL.class);

		// header
		final I_M_InOut inout = line.getM_InOut();

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final IQueryBuilder<I_M_Material_Balance_Config> queryBuilder = queryBL.createQueryBuilder(I_M_Material_Balance_Config.class, line);

		// product id
		final I_M_Product product = loadOutOfTrx(line.getM_Product_ID(), I_M_Product.class);
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_M_Product_ID, product.getM_Product_ID(), null);

		// product category
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_M_Product_Category_ID, product.getM_Product_Category_ID(), null);

		// partner
		final I_C_BPartner partner = load(inout.getC_BPartner_ID(), I_C_BPartner.class);
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_C_BPartner_ID, partner.getC_BPartner_ID(), null);

		// partner group
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_C_BP_Group_ID, partner.getC_BP_Group_ID(), null);

		// warehouse
		queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_M_Warehouse_ID, inout.getM_Warehouse_ID(), null);

		// Do not allow a config to be fetched if the vendor/customer flags don't fit the partner's vendor/customer flags

		// only for vendor
		if (partner.isCustomer())
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsCustomer, true, null);
		}

		// only for customer
		if (partner.isVendor())
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsVendor, true, null);
		}

		// only for flat rate
		// make sure that is the config is only for flatrate, the inoutline is also for flatrate

		boolean isUseForFlatrate = true;
		for (final IMaterialBalanceConfigMatcher matcher : materialConfigBL.retrieveMatchers())
		{
			if (!matcher.matches(line))
			{
				isUseForFlatrate = false;
				break;
			}
		}

		if (isUseForFlatrate)
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsForFlatrate, true, null);
		}
		else
		{
			queryBuilder.addInArrayOrAllFilter(I_M_Material_Balance_Config.COLUMNNAME_IsForFlatrate, false, null);
		}

		// ORDER BY
		queryBuilder
				.orderBy()
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_M_Product_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_M_Product_Category_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_C_BPartner_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_C_BP_Group_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_M_Warehouse_ID, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_IsVendor, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_IsCustomer, Direction.Descending, Nulls.Last)
				.addColumn(I_M_Material_Balance_Config.COLUMNNAME_IsForFlatrate, Direction.Descending, Nulls.Last);
		return queryBuilder;
	}

	private static MaterialBalanceConfig toMaterialBalanceConfig(final I_M_Material_Balance_Config record)
	{
		return MaterialBalanceConfig.builder()
				.repoId(record.getM_Material_Balance_Config_ID())
				.calendarId(CalendarId.ofRepoId(record.getC_Calendar_ID()))
				.useForFlatrate(StringUtils.toBoolean(record.getIsForFlatrate(), null))
				.build();
	}
}
