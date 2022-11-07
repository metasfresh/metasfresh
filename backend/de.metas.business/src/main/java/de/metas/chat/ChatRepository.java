package de.metas.chat;

import de.metas.order.OrderAndLineId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_CM_Chat;
import org.compiere.model.I_C_OrderLine_Detail;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Properties;

@Repository
public class ChatRepository
{

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public void deleteByTableAndRecord(@NonNull final TableRecordReference tableRecordReference)
	{
		queryBL.createQueryBuilder(I_CM_Chat.class)
				.addEqualsFilter(I_CM_Chat.COLUMNNAME_AD_Table_ID, tableRecordReference.getAdTableId())
				.addEqualsFilter(I_CM_Chat.COLUMNNAME_Record_ID, tableRecordReference.getRecord_ID())
				.create()
				.deleteDirectly();
	}

}
