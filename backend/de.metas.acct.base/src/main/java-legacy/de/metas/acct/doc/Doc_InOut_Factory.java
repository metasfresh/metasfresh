package de.metas.acct.doc;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.order.IOrderBL;
import de.metas.shippingnotification.acct.ShippingNotificationAcctService;
import de.metas.util.Services;
import lombok.RequiredArgsConstructor;
import org.adempiere.util.LegacyAdapters;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.Doc_InOut;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class Doc_InOut_Factory implements IAcctDocProvider
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final ShippingNotificationAcctService shippingNotificationAcctService;

	@Override
	public Set<String> getDocTableNames() {return ImmutableSet.of(I_M_InOut.Table_Name);}

	@Override
	public Doc<?> getOrNull(final AcctDocRequiredServicesFacade services, final List<AcctSchema> acctSchemas, final TableRecordReference documentRef)
	{
		final InOutId inoutId = documentRef.getIdAssumingTableName(I_M_InOut.Table_Name, InOutId::ofRepoId);

		return new Doc_InOut(
				inOutBL,
				orderBL,
				shippingNotificationAcctService,
				AcctDocContext.builder()
						.services(services)
						.acctSchemas(acctSchemas)
						.documentModel(retrieveDocumentModel(documentRef))
						.build());
	}

	private AcctDocModel retrieveDocumentModel(final TableRecordReference documentRef)
	{
		final InOutId inoutId = documentRef.getIdAssumingTableName(I_M_InOut.Table_Name, InOutId::ofRepoId);
		final I_M_InOut inoutRecord = inOutBL.getById(inoutId);
		return new POAcctDocModel(LegacyAdapters.convertToPO(inoutRecord));
	}
}

