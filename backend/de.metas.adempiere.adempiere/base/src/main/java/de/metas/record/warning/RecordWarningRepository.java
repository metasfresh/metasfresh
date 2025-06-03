package de.metas.record.warning;

import de.metas.business_rule.descriptor.model.Severity;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Record_Warning;
import org.springframework.stereotype.Repository;

@Repository
public class RecordWarningRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public RecordWarningId createOrUpdate(@NonNull final RecordWarningCreateRequest request)
	{
		final I_AD_Record_Warning record = toSqlQuery(RecordWarningQuery.builder()
				.rootRecordRef(request.getRootRecordRef())
				.businessRuleId(request.getBusinessRuleId())
				.build())
				.create()
				.firstOnlyOptional(I_AD_Record_Warning.class)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_AD_Record_Warning.class));

		record.setRoot_AD_Table_ID(request.getRootRecordRef().getAD_Table_ID());
		record.setRoot_Record_ID(request.getRootRecordRef().getRecord_ID());

		record.setAD_Table_ID(request.getRecordRef().getAD_Table_ID());
		record.setRecord_ID(request.getRecordRef().getRecord_ID());

		record.setAD_BusinessRule_ID(request.getBusinessRuleId().getRepoId());
		record.setMsgText(request.getMessage());
		record.setAD_User_ID(request.getUserId().getRepoId());
		record.setSeverity(request.getSeverity().getCode());

		InterfaceWrapperHelper.save(record);

		return RecordWarningId.ofRepoId(record.getAD_Record_Warning_ID());
	}
	
	public RecordWarning getById(@NonNull final RecordWarningId id)
	{
		final I_AD_Record_Warning record = InterfaceWrapperHelper.load(id, I_AD_Record_Warning.class);
		return fromRecord(record);
	}

	private RecordWarning fromRecord(final I_AD_Record_Warning record)
	{
		return RecordWarning.builder()
				.id(RecordWarningId.ofRepoId(record.getAD_Record_Warning_ID()))
				.msgText(record.getMsgText())
				.build();
	}

	public void deleteByRootRecordRef(@NonNull final RecordWarningQuery query)
	{
		toSqlQuery(query).create().delete();
	}

	private IQueryBuilder<I_AD_Record_Warning> toSqlQuery(@NonNull final RecordWarningQuery query)
	{
		final IQueryBuilder<I_AD_Record_Warning> queryBuilder = queryBL.createQueryBuilder(I_AD_Record_Warning.class);

		final TableRecordReference rootRecordRef = query.getRootRecordRef();
		queryBuilder.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_Root_AD_Table_ID, rootRecordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_Root_Record_ID, rootRecordRef.getRecord_ID());

		if (query.getBusinessRuleId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_AD_BusinessRule_ID, query.getBusinessRuleId());
		}

		if(query.getSeverity() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_Record_Warning.COLUMNNAME_Severity, query.getSeverity().getCode());
		}

		return queryBuilder;
	}

	public boolean hasErrors(@NonNull final TableRecordReference recordRef)
	{
		final RecordWarningQuery query = RecordWarningQuery.builder()
				.rootRecordRef(recordRef)
				.severity(Severity.Error)
				.build();

		return toSqlQuery(query)
				.create()
				.anyMatch();
	}
}
