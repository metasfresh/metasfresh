/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs.edi;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.EXP_Processor_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.esb.edi.model.I_EDI_cctop_invoic_v;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_EXP_Processor;
import org.compiere.model.I_EXP_ProcessorParameter;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.assertj.core.api.Assertions.*;

public class MetasfreshToEDIRabbitMQ_StepDef
{
	private static final Logger logger = LogManager.getLogger(MetasfreshToEDIRabbitMQ_StepDef.class);

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final EXP_Processor_StepDefData expProcessorTable;
	private final EDI_cctop_invoic_v_StepDefData invoicTable;
	private final EDI_Exp_Desadv_StepDefData ediExpDesadvTable;
	private final ConnectionFactory metasfreshToRabbitMQFactory;
	private final DocumentBuilderFactory documentBuilderFactory;

	public MetasfreshToEDIRabbitMQ_StepDef(
			@NonNull final EXP_Processor_StepDefData expProcessorTable,
			@NonNull final EDI_cctop_invoic_v_StepDefData invoicTable,
			@NonNull final EDI_Exp_Desadv_StepDefData ediExpDesadvTable)
	{
		this.expProcessorTable = expProcessorTable;
		this.invoicTable = invoicTable;
		this.ediExpDesadvTable = ediExpDesadvTable;
		this.documentBuilderFactory = DocumentBuilderFactory.newInstance();

		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull();

		metasfreshToRabbitMQFactory = new ConnectionFactory();
		metasfreshToRabbitMQFactory.setHost(commandLineOptions.getRabbitHost());
		metasfreshToRabbitMQFactory.setPort(commandLineOptions.getRabbitPort());
		metasfreshToRabbitMQFactory.setUsername(commandLineOptions.getRabbitUser());
		metasfreshToRabbitMQFactory.setPassword(commandLineOptions.getRabbitPassword());
	}

	/**
	 * Purges the given durable EDI-export RabbitMQ queue so the scenario starts from a known-empty
	 * state, regardless of what an earlier scenario (or an earlier feature on the same CI executor)
	 * left behind.
	 * <p>
	 * Why this is needed: the EDI export queues (e.g. {@code ediExport}) are <b>durable</b> and
	 * <b>shared</b> by every scenario in a feature and by every feature that exports through the same
	 * routing key on one CI executor (all run sequentially against one RabbitMQ broker). The consumer
	 * in {@link #pollDocumentFromQueue(String)} returns the message at the head of the queue, so a
	 * single leftover message (a foreign DESADV/INVOIC from a sibling scenario, or a message re-queued
	 * by the consumer's {@code basicNack(requeue=true)} extra-message path) makes the next scenario
	 * consume the <i>wrong</i> document. That manifests as the flaky
	 * {@code [EDI_Exp_Desadv_Pack] Expecting actual not to be null} (a foreign INVOIC has no pack
	 * element) and the sibling {@code ShutdownSignalException}. Purging in the Background (per the
	 * setUp-isolation convention) removes the bleed at its source.
	 * <p>
	 * The purge is a no-op when the queue does not exist yet (first run on a fresh broker): we
	 * passively check existence first and skip silently, because "no queue" already means "nothing to
	 * bleed".
	 * <p>
	 * DataTable-free step. Example:
	 * <pre>
	 * Given the EDI export RabbitMQ queue 'ediExport' is purged
	 * </pre>
	 */
	@Given("the EDI export RabbitMQ queue {string} is purged")
	public void edi_export_queue_is_purged(@NonNull final String queueName) throws IOException, TimeoutException
	{
		final Connection connection = metasfreshToRabbitMQFactory.newConnection();
		try
		{
			final Channel channel = connection.createChannel();
			try
			{
				// queueDeclarePassive throws (and closes the channel) when the queue does not exist yet;
				// in that case there is nothing to purge, so we treat it as a no-op and return. The dead
				// channel needs no explicit close (the broker already closed it; the finally below guards
				// it via isOpen()), and the outer finally closes the connection.
				channel.queueDeclarePassive(queueName);

				final AMQP.Queue.PurgeOk purgeOk = channel.queuePurge(queueName);
				logger.info("Purged {} message(s) from EDI export queue {}", purgeOk.getMessageCount(), queueName);
			}
			catch (final IOException queueAbsent)
			{
				logger.info("EDI export queue {} does not exist yet -> nothing to purge", queueName);
			}
			finally
			{
				if (channel.isOpen())
				{
					channel.close();
				}
			}
		}
		finally
		{
			connection.close();
		}
	}

	@Then("RabbitMQ receives a EDI_cctop_invoic_v")
	public void rabbitMQ_receives_edi_cctop_invoic(@NonNull final DataTable dataTable) throws IOException, InterruptedException, TimeoutException, ParserConfigurationException, SAXException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String processorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer expProcessorId = expProcessorTable.getOptional(processorIdentifier)
				.map(I_EXP_Processor::getEXP_Processor_ID)
				.orElseGet(() -> Integer.parseInt(processorIdentifier));

		final String routingKeyParameterValue = DataTableUtil.extractStringForColumnName(tableRow, I_EXP_ProcessorParameter.Table_Name + "." + I_EXP_ProcessorParameter.COLUMNNAME_Value);

		final I_EXP_ProcessorParameter expProcessorParameter = queryBL.createQueryBuilder(I_EXP_ProcessorParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID, expProcessorId)
				.addEqualsFilter(I_EXP_ProcessorParameter.COLUMNNAME_Value, routingKeyParameterValue)
				.create()
				.firstOnlyNotNull(I_EXP_ProcessorParameter.class);

		final Document export = pollDocumentFromQueue(expProcessorParameter.getParameterValue());

		final String invoicIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EDI_cctop_invoic_v.COLUMNNAME_EDI_cctop_invoic_v_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		invoicTable.put(invoicIdentifier, export);
	}

	@Then("RabbitMQ receives a EDI_Exp_Desadv")
	public void rabbitMQ_receives_edi_exp_desadv(@NonNull final DataTable dataTable) throws IOException, ParserConfigurationException, InterruptedException, TimeoutException, SAXException
	{
		final Map<String, String> tableRow = dataTable.asMaps().get(0);

		final String processorIdentifier = DataTableUtil.extractStringForColumnName(tableRow, I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);
		final Integer expProcessorId = expProcessorTable.getOptional(processorIdentifier)
				.map(I_EXP_Processor::getEXP_Processor_ID)
				.orElseGet(() -> Integer.parseInt(processorIdentifier));

		final String routingKeyParameterValue = DataTableUtil.extractStringForColumnName(tableRow, I_EXP_ProcessorParameter.Table_Name + "." + I_EXP_ProcessorParameter.COLUMNNAME_Value);

		final I_EXP_ProcessorParameter expProcessorParameter = queryBL.createQueryBuilder(I_EXP_ProcessorParameter.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EXP_ProcessorParameter.COLUMNNAME_EXP_Processor_ID, expProcessorId)
				.addEqualsFilter(I_EXP_ProcessorParameter.COLUMNNAME_Value, routingKeyParameterValue)
				.create()
				.firstOnlyNotNull(I_EXP_ProcessorParameter.class);

		final Document export = pollDocumentFromQueue(expProcessorParameter.getParameterValue());

		final String ediExpDesadvIdentifier = DataTableUtil.extractStringForColumnName(tableRow, "EDI_Exp_Desadv_ID" + "." + StepDefConstants.TABLECOLUMN_IDENTIFIER);

		ediExpDesadvTable.put(ediExpDesadvIdentifier, export);
	}

	@NonNull
	private Document pollDocumentFromQueue(@NonNull final String queueName) throws IOException, TimeoutException, InterruptedException, ParserConfigurationException, SAXException
	{
		final Connection connection = metasfreshToRabbitMQFactory.newConnection();
		final Channel channel = connection.createChannel();

		final CountDownLatch countDownLatch = new CountDownLatch(1);

		final String[] messages = new String[1];

		final DefaultConsumer consumer = new DefaultConsumer(channel)
		{
			@Override
			public void handleDelivery(final String consumerTag, final Envelope envelope, final AMQP.BasicProperties properties, final byte[] body) throws IOException
			{
				// Only accept the first delivery and ack it; cancel the consumer immediately so the
				// broker requeues / leaves any remaining messages (e.g. a second DESADV pre-queued
				// before this poll) for the next pollDocumentFromQueue call.
				if (countDownLatch.getCount() == 0)
				{
					// Reject without requeue is wrong (would lose the message); nack with requeue=true
					// keeps it in the queue for the next caller. With basicCancel below, this path is
					// only hit if the broker dispatched extra messages before cancellation took effect.
					channel.basicNack(envelope.getDeliveryTag(), false, true);
					return;
				}

				messages[0] = new String(body, StandardCharsets.UTF_8);

				logger.info("*** Queue: {}, received message: {}", queueName, messages[0]);

				countDownLatch.countDown();

				channel.basicAck(envelope.getDeliveryTag(), false);

				try
				{
					channel.basicCancel(consumerTag);
				}
				catch (final Exception e)
				{
					// best-effort cancel; channel may already be in the process of closing
					logger.warn("basicCancel failed (ignored): {}", e.getMessage());
				}
			}
		};

		try
		{
			// autoAck=false: we manually ack the first message and nack-with-requeue any extras the
			// broker dispatched before basicCancel took effect, so a follow-up pollDocumentFromQueue
			// call still finds those messages in the queue.
			channel.basicConsume(queueName, false, consumer);

			final boolean messageReceivedWithinTimeout = countDownLatch.await(60, TimeUnit.SECONDS);

			assertThat(messageReceivedWithinTimeout).isTrue();
		}
		finally
		{
			channel.close();
		}

		return parseXmlStringToDocument(messages[0]);
	}

	@NonNull
	private Document parseXmlStringToDocument(@NonNull final String xmlString) throws ParserConfigurationException, IOException, SAXException
	{
		final DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();

		return dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));
	}
}
