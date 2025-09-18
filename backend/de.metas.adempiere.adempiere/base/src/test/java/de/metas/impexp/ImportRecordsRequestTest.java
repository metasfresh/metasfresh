package de.metas.impexp;

import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ClientId;
import org.adempiere.util.api.Params;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImportRecordsRequestTest
{
	@Test
	void test_toParams_fromParams()
	{
		final ImportRecordsRequest request = ImportRecordsRequest.builder()
				.importTableName("MyImportTable")
				.clientId(ClientId.ofRepoId(1))
				.selectionId(PInstanceId.ofRepoId(2))
				.limit(QueryLimit.ofInt(3))
				.notifyUserId(UserId.ofRepoId(4))
				.completeDocuments(true)
				.additionalParameters(Params.builder()
						.value("param1", "value1")
						.build())
				.build();

		final Params params = request.toParams();
		final ImportRecordsRequest request2 = ImportRecordsRequest.ofParams(params);
		assertThat(request2).usingRecursiveComparison().isEqualTo(request);
		assertThat(request2).isEqualTo(request);
	}
}