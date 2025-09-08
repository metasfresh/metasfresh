package de.metas.attributes_included_tab.descriptor;

import de.metas.i18n.TranslatableStrings;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_AttributeSet_IncludedTab;
import org.springframework.stereotype.Repository;

@Repository
class AttributesIncludedTabUserConfigRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AttributesIncludedTabUserConfigList list()
	{
		return queryBL.createQueryBuilder(I_M_AttributeSet_IncludedTab.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(AttributesIncludedTabUserConfigRepository::fromRecord)
				.collect(GuavaCollectors.collectUsingListAccumulator(AttributesIncludedTabUserConfigList::of));
	}

	private static AttributesIncludedTabUserConfig fromRecord(final I_M_AttributeSet_IncludedTab record)
	{
		return AttributesIncludedTabUserConfig.builder()
				.id(AttributesIncludedTabId.ofRepoId(record.getM_AttributeSet_IncludedTab_ID()))
				.attributeSetId(AttributeSetId.ofRepoId(record.getM_AttributeSet_ID()))
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				.parentTableId(AdTableId.ofRepoId(record.getAD_Table_ID()))
				.caption(TranslatableStrings.anyLanguage(record.getName()))
				.seqNo(record.getM_AttributeSet_IncludedTab_ID())
				.build();
	}
}
