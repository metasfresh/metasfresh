package de.metas.acct.factacct_userchanges;

import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactAcctUserChangesService
{
	private final FactAcctUserChangesRepository repository;

	public FactAcctUserChangesService(
			@NonNull final FactAcctUserChangesRepository repository)
	{
		this.repository = repository;
	}

	public void save(
			@NonNull final FactAcctChangesList lineChangesList,
			@NonNull final TableRecordReference docRecordRef)
	{
		repository.save(lineChangesList, docRecordRef);
	}

	public FactAcctChangesList getByDocRecordRef(@NonNull final TableRecordReference docRecordRef)
	{
		return repository.getByDocRecordRef(docRecordRef);
	}
}
