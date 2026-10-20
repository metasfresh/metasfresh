package de.metas.order.compensationGroup.calibration;

import de.metas.cache.CCache;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_C_OrderLine;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
public class CalibrationFactorFieldRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, Boolean> factorEditableByTabId = CCache.<Integer, Boolean>builder()
			.tableName(I_AD_Field.Table_Name)
			.additionalTableNameToResetFor(I_AD_Column.Table_Name)
			.build();

	/**
	 * @return true if the given tab has an active, not read-only Kalibrierfaktor field (e.g. a customer's sales order window); false in core window 143, where it is read-only
	 */
	public boolean isFactorEditableInTab(final int adTabId)
	{
		return factorEditableByTabId.getOrLoad(adTabId, this::retrieveFactorEditable);
	}

	private boolean retrieveFactorEditable(@Nullable final Integer adTabId)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_Column.class)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, I_C_OrderLine.COLUMNNAME_GroupCompensationCalibrationFactor)
				.andCollectChildren(I_AD_Field.COLUMNNAME_AD_Column_ID, I_AD_Field.class)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_AD_Tab_ID, adTabId)
				.addEqualsFilter(I_AD_Field.COLUMNNAME_IsReadOnly, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}
}
