package de.metas.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableSet;
import de.metas.order.PurchaseOrderProjectListener.ProjectCreatedEvent;
import de.metas.project.ProjectId;
import de.metas.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectCreatedEventTest
{
	private ObjectMapper objectMapper;

	@BeforeEach
	public void beforeEach()
	{
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testSerializeDeserialize() throws IOException
	{
		testSerializeDeserialize(ProjectCreatedEvent.builder()
				.projectId(ProjectId.ofRepoId(123))
				.byUserId(UserId.METASFRESH)
				.purchaseOrderLineIds(ImmutableSet.of(
						OrderAndLineId.ofRepoIds(1, 2),
						OrderAndLineId.ofRepoIds(1, 3),
						OrderAndLineId.ofRepoIds(1, 4)
				))
				.build());
	}

	private void testSerializeDeserialize(final Object obj) throws IOException
	{
		System.out.println("Object: " + obj);

		final String json = objectMapper.writeValueAsString(obj);
		System.out.println("Object->JSON: " + json);

		final Object objDeserialized = objectMapper.readValue(json, obj.getClass());
		System.out.println("Object deserialized: " + objDeserialized);
		assertThat(objDeserialized).usingRecursiveComparison().isEqualTo(obj);
		assertThat(objDeserialized).isEqualTo(obj);
	}

}