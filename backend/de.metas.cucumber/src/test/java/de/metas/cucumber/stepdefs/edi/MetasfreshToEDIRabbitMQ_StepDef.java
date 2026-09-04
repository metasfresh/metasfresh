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

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.EXP_Processor_StepDefData;
import de.metas.cucumber.stepdefs.StepDefConstants;
import de.metas.esb.edi.model.I_EDI_cctop_invoic_v;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
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

	/**
	 * Pulls the next EDI-export XML document off {@code queueName} and parses it, retrying until one
	 * arrives or 60s elapse.
	 * <p>
	 * Uses a synchronous {@link Channel#basicGet} pull-loop on the test thread (one message at a time,
	 * explicit ack) rather than an async push consumer — per {@code de.metas.cucumber} CLAUDE.md rule 15,
	 * this avoids the prefetch-storm / {@code AlreadyClosedException} race that an unbounded
	 * {@code basicConsume}/{@code DefaultConsumer} consumer causes when it closes the channel mid-dispatch.
	 * A non-XML / foreign message is acked-and-skipped so it neither returns the wrong document nor
	 * crashes the poll; any surplus messages (e.g. a second DESADV of a consolidated shipment) are left
	 * on the queue for the next call.
	 */
	@NonNull
	private Document pollDocumentFromQueue(@NonNull final String queueName) throws IOException, TimeoutException, InterruptedException, ParserConfigurationException, SAXException
	{
		final Connection connection = metasfreshToRabbitMQFactory.newConnection();
		try
		{
			// createChannel() is inside the outer try so the connection is still closed if it throws.
			final Channel channel = connection.createChannel();
			try
			{
				final long deadlineMillis = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(60);

				while (System.currentTimeMillis() < deadlineMillis)
				{
					// autoAck=false: pull exactly one message at a time on the test thread, then ack it
					// explicitly once we have read its body. No prefetch storm, no consumer-callback thread.
					final GetResponse getResponse = channel.basicGet(queueName, false);
					if (getResponse == null)
					{
						// Queue currently empty (the export workpackage may not have published yet) -> wait
						// briefly and retry until the deadline.
						try
						{
							Thread.sleep(250);
						}
						catch (final InterruptedException interrupted)
						{
							Thread.currentThread().interrupt();
							throw interrupted;
						}
						continue;
					}

					final String message = new String(getResponse.getBody(), StandardCharsets.UTF_8);
					channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);

					try
					{
						final Document document = parseXmlStringToDocument(message);
						logger.info("*** Queue: {}, received message: {}", queueName, message);
						return document;
					}
					catch (final SAXException | IOException foreignMessage)
					{
						// A leftover / foreign message that is not the expected EDI XML: it is already acked
						// (removed) above, so just skip it and keep polling for the message we expect.
						// ParserConfigurationException is intentionally NOT caught here: it signals a broken
						// JAXP/JVM configuration, not a per-message condition, so it must propagate and fail
						// the run loudly rather than be swallowed and masked by the 60s-timeout AssertionError.
						logger.warn("*** Queue: {}, skipping non-XML/foreign message (body={}): {}", queueName, message, foreignMessage.getMessage());
					}
				}

				throw new AssertionError("No EDI-export message received on queue '" + queueName + "' within 60s");
			}
			finally
			{
				// guard with isOpen(): if the broker already force-closed the channel, an unconditional
				// close() would throw AlreadyClosedException in finally and suppress the real failure.
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

	@NonNull
	private Document parseXmlStringToDocument(@NonNull final String xmlString) throws ParserConfigurationException, IOException, SAXException
	{
		final DocumentBuilder dBuilder = documentBuilderFactory.newDocumentBuilder();

		return dBuilder.parse(new ByteArrayInputStream(xmlString.getBytes()));
	}
}
