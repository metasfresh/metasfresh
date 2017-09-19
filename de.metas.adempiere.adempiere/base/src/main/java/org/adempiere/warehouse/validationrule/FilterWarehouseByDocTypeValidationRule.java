package org.adempiere.warehouse.validationrule;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableSet;

import lombok.NonNull;

/**
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/04416_Possibility_to_define_Warehouses_which_are_joined_to_certain_Business_Processes_%282013062010000051%29
 */
public final class FilterWarehouseByDocTypeValidationRule extends AbstractJavaValidationRule
{
	public static final transient FilterWarehouseByDocTypeValidationRule instance = new FilterWarehouseByDocTypeValidationRule();

	private static final String COLUMNNAME_C_DocType_ID = "C_DocType_ID";
	private static final String COLUMNNAME_C_DocTypeTarget_ID = "C_DocTypeTarget_ID";
	private static final Set<String> PARAMS = ImmutableSet.of(COLUMNNAME_C_DocType_ID, COLUMNNAME_C_DocTypeTarget_ID);

	private FilterWarehouseByDocTypeValidationRule()
	{
		super();
	}

	@Override
	public boolean accept(
			@NonNull final IValidationContext evalCtx,
			final NamePair item)
	{
		if (null == item)
		{
			// Should never happen.
			return false;
		}

		final String docType = evalCtx.get_ValueAsString(COLUMNNAME_C_DocType_ID);
		final String docTypeTarget = evalCtx.get_ValueAsString(COLUMNNAME_C_DocTypeTarget_ID);

		final int docTypeId = StringUtils.toIntegerOrZero(docType);
		final int docTypeTargetId = StringUtils.toIntegerOrZero(docTypeTarget);

		if (docTypeId <= 0 && docTypeTargetId <= 0)
		{
			// Not a document. All warehouses available.
			return true;
		}

		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		final int warehouseId = StringUtils.toIntegerOrZero(item.getID());
		Check.assume(warehouseId > 0, "Invalid warehouse {}", item.getID());

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_None;

		// Check if we have any available doc types assigned to our warehouse.
		// If not, we shall accept this warehouse right away (task 09301).
		// As soon as there is assigned at least on doc type, we will enforce the restrictions.
		if (!warehouseDAO.hasAvailableDocTypes(ctx, warehouseId, trxName))
		{
			return true; // no restrictions defined => accept this warehouse
		}

		// First check for doc type.
		if (docTypeId > 0)
		{
			final I_C_DocType type = loadOutOfTrx(docTypeId, I_C_DocType.class);
			return warehouseDAO.isDocTypeAllowed(ctx, warehouseId, type, trxName);
		}

		// For orders, also check doc type target
		if (docTypeTargetId > 0)
		{
			final I_C_DocType type = loadOutOfTrx(docTypeTargetId, I_C_DocType.class);
			return warehouseDAO.isDocTypeAllowed(ctx, warehouseId, type, trxName);
		}

		return false;
	}

	@Override
	public Set<String> getParameters()
	{
		return PARAMS;
	}

}
