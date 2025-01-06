package de.metas.email.mailboxes;

import com.google.common.collect.ImmutableList;
import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.email.mailboxes.MailboxQuery.MailboxQueryBuilder;
import de.metas.email.mailboxes.MailboxRouting.MailboxRoutingBuilder;
import de.metas.organization.OrgId;
import org.adempiere.service.ClientId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailboxRoutingTableTest
{
	@Test
	void oneRule_clientLevel()
	{
		final MailboxRouting routing1;
		final MailboxRoutingTable table = MailboxRoutingTable.ofList(ImmutableList.of(
				routing1 = MailboxRouting.builder().mailboxId(MailboxId.ofRepoId(1)).clientId(ClientId.METASFRESH).orgId(OrgId.ANY).build()
		));

		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.SYSTEM).build()))
				.isEmpty();
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).orgId(OrgId.MAIN).build()))
				.containsSame(routing1);
	}

	@Test
	void oneRule_orgLevel()
	{
		final MailboxRouting routing1;
		final MailboxRoutingTable table = MailboxRoutingTable.ofList(ImmutableList.of(
				routing1 = MailboxRouting.builder().mailboxId(MailboxId.ofRepoId(1)).clientId(ClientId.METASFRESH).orgId(OrgId.ofRepoId(1)).build()
		));

		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.SYSTEM).build()))
				.isEmpty();
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).build()))
				.isEmpty();
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).orgId(OrgId.ofRepoId(1)).build()))
				.containsSame(routing1);
		assertThat(table.findBestMatching(MailboxQuery.builder().clientId(ClientId.METASFRESH).orgId(OrgId.ofRepoId(2)).build()))
				.isEmpty();
	}

	@Nested
	class docSubType
	{
		MailboxRoutingBuilder routingTemplate = MailboxRouting.builder().clientId(ClientId.METASFRESH).orgId(OrgId.ANY).docBaseType(DocBaseType.SalesOrder).mailboxId(MailboxId.ofRepoId(1));
		MailboxQueryBuilder queryTemplate = MailboxQuery.builder().clientId(ClientId.METASFRESH);
		MailboxRouting routing1, routing2, routing3, routing4;
		MailboxRoutingTable table;

		@BeforeEach
		void beforeEach()
		{
			table = MailboxRoutingTable.ofList(ImmutableList.of(
					routing1 = routingTemplate.docSubType(DocSubType.ANY).build(),
					routing2 = routingTemplate.docSubType(DocSubType.NONE).build(),
					routing3 = routingTemplate.docSubType(DocSubType.StandardOrder).build(),
					routing4 = routingTemplate.docSubType(DocSubType.POSOrder).build()
			));
		}

		@Test
		void null_value()
		{
			assertThat(table.findBestMatching(queryTemplate.docBaseAndSubType(null).build())).containsSame(routing1);
		}

		@Test
		void NONE()
		{
			assertThat(table.findBestMatching(queryTemplate.docBaseAndSubType(DocBaseAndSubType.of(DocBaseType.SalesOrder, DocSubType.NONE)).build())).containsSame(routing2);
		}

		@Test
		void standardOrder()
		{
			assertThat(table.findBestMatching(queryTemplate.docBaseAndSubType(DocBaseAndSubType.of(DocBaseType.SalesOrder, DocSubType.StandardOrder)).build())).containsSame(routing3);
		}

		@Test
		void posOrder()
		{
			assertThat(table.findBestMatching(queryTemplate.docBaseAndSubType(DocBaseAndSubType.of(DocBaseType.SalesOrder, DocSubType.POSOrder)).build())).containsSame(routing4);
		}
	}
}