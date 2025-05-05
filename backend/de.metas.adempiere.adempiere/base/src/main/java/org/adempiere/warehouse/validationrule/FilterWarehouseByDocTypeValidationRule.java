package org.adempiere.warehouse.validationrule;

import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.NamePair;

import java.util.Set;

import javax.annotation.Nullable;

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

		final DocTypeId docTypeId = DocTypeId.ofRepoIdOrNull(StringUtils.toIntegerOrZero(docType));
		final DocTypeId docTypeTargetId = DocTypeId.ofRepoIdOrNull(StringUtils.toIntegerOrZero(docTypeTarget));

		if (docTypeId == null && docTypeTargetId == null)
		{
			// Not a document. All warehouses available.
			return true;
		}

		final WarehouseId warehouseId = WarehouseId.ofRepoIdOrNull(NumberUtils.asInt(item.getID(), -1));
		Check.assumeNotNull(warehouseId, "Invalid warehouse {}", item.getID());

		// Check if we have any available doc types assigned to our warehouse.
		// If not, we shall accept this warehouse right away (task 09301).
		// As soon as there is assigned at least on doc type, we will enforce the restrictions.
		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		if (warehousesRepo.isAllowAnyDocType(warehouseId))
		{
			return true; // no restrictions defined => accept this warehouse
		}

		// First check for doc type.
		if (docTypeId != null)
		{
			final String docBaseType = Services.get(IDocTypeDAO.class).getRecordById(docTypeId).getDocBaseType();
			return warehousesRepo.isDocTypeAllowed(warehouseId, docBaseType);
		}

		// For orders, also check doc type target
		if (docTypeTargetId != null)
		{
			final String docBaseType = Services.get(IDocTypeDAO.class).getRecordById(docTypeTargetId).getDocBaseType();
			return warehousesRepo.isDocTypeAllowed(warehouseId, docBaseType);
		}

		return false;
	}

	@Override
	public Set<String> getParameters(@Nullable final String contextTableName)
	{
		return PARAMS;
	}

}
