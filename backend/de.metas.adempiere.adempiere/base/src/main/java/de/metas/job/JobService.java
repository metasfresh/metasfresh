package de.metas.job;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.Adempiere;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JobService
{
	@NonNull private final JobRepository repo;

	public static JobService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new JobService(new JobRepository());
	}

	public Job getById(@NonNull JobId id) {return repo.getById(id);}
}
