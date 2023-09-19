package de.metas.acct.doc;

import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.Doc_InOut;
import org.compiere.model.I_M_InOut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class Doc_InOut_Factory implements IAcctDocProvider
{
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	@Override
	public Set<String> getDocTableNames() {return ImmutableSet.of(I_M_InOut.Table_Name);}

	@Override
	public Doc<?> getOrNull(final AcctDocRequiredServicesFacade services, final List<AcctSchema> acctSchemas, final TableRecordReference documentRef)
	{
		final InOutId inoutId = documentRef.getIdAssumingTableName(I_M_InOut.Table_Name, InOutId::ofRepoId);

		return new Doc_InOut(
				inOutBL,
				AcctDocContext.builder()
						.services(services)
						.acctSchemas(acctSchemas)
						.documentModel(inOutBL.getById(inoutId))
						.build());
	}
}
