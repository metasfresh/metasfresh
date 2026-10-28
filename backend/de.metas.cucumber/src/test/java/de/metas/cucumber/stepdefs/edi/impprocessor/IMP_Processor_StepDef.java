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

package de.metas.cucumber.stepdefs.edi.impprocessor;

import de.metas.CommandLineParser;
import de.metas.ServerBoot;
import de.metas.cucumber.stepdefs.DataTableUtil;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.util.Services;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.I_AD_Message;
import org.compiere.model.I_AD_Message_Trl;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.I_IMP_Processor_Type;
import org.compiere.server.AdempiereServerMgr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static de.metas.cucumber.stepdefs.StepDefConstants.TABLECOLUMN_IDENTIFIER;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class IMP_Processor_StepDef
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final IMP_Processor_Type_StepDefData impProcessorTypeStepDefData;
	private final IMP_Processor_StepDefData impProcessorTable;

	public IMP_Processor_StepDef(
			@NonNull final IMP_Processor_Type_StepDefData impProcessorTypeStepDefData,
			@NonNull final IMP_Processor_StepDefData impProcessorTable)
	{
		this.impProcessorTypeStepDefData = impProcessorTypeStepDefData;
		this.impProcessorTable = impProcessorTable;
	}

	@Given("metasfresh contains IMP_Processor")
	public void create_IMP_Processor(@NonNull final DataTable dataTable)
	{
		final List<Map<String, String>> rows = dataTable.asMaps();
		for (final Map<String, String> row : rows)
		{
			createIMPProcessor(row);
		}
	}

	@And("^after not more than (.*)s, import processor has started: (.*)$")
	public void startImpProcessor(final int timeoutSec, @NonNull final String importProcessorIdentifier) throws InterruptedException
	{
		final I_IMP_Processor impProcessor = impProcessorTable.get(importProcessorIdentifier);
		assertThat(impProcessor).isNotNull();

		final AdempiereProcessor adempiereProcessor = Services.get(IIMPProcessorBL.class).asAdempiereProcessor(impProcessor);

		final AdempiereServerMgr adempiereServerMgr = AdempiereServerMgr.get();

		if (adempiereServerMgr.getServer(adempiereProcessor.getServerID()) == null)
		{
			adempiereServerMgr.add(adempiereProcessor);
		}
		else
		{
			adempiereServerMgr.start(adempiereProcessor.getServerID());
		}

		final Supplier<Boolean> isProcessorStarted = () ->
		{
			InterfaceWrapperHelper.refresh(impProcessor);
			return impProcessor.getDateLastRun() != null;
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isProcessorStarted);
	}

	/**
	 * Waits until the import processor has logged a failed import whose error text contains every given fragment.
	 * A fragment that is an AD_Message is matched by the fixed text parts of that message, in any of its translations.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Text</b> - (optional) literal text the error must contain, e.g. an exception message or a GLN identifying the import<br>
	 *   <b>AD_Message</b> - (optional) Value of an AD_Message whose text the error must contain<br>
	 * @cucumber.example
	 * <pre>
	 * Then after not more than 60s, the import of import processor impProcessor failed with:
	 *   | Text                    | AD_Message                           |
	 *   | 4000001000005           |                                      |
	 *   |                         | EDI_BPartnerLookup_AmbiguousNoDefault |
	 * </pre>
	 */
	@Then("^after not more than (.*)s, the import of import processor (.*) failed with:$")
	public void import_failed_with(final int timeoutSec, @NonNull final String importProcessorIdentifier, @NonNull final DataTable dataTable) throws InterruptedException
	{
		final I_IMP_Processor impProcessor = impProcessorTable.get(importProcessorIdentifier);
		assertThat(impProcessor).isNotNull();

		final List<String> errorTexts = new ArrayList<>();
		final Supplier<Boolean> isErrorLogged = () -> {
			errorTexts.clear();
			queryBL.createQueryBuilder(I_IMP_ProcessorLog.class)
					.addEqualsFilter(I_IMP_ProcessorLog.COLUMNNAME_IMP_Processor_ID, impProcessor.getIMP_Processor_ID())
					.addEqualsFilter(I_IMP_ProcessorLog.COLUMNNAME_IsError, true)
					.create()
					.stream()
					.forEach(log -> errorTexts.add(log.getSummary() + "\n" + log.getTextMsg()));
			return errorTexts.stream().anyMatch(errorText -> containsAllFragments(errorText, dataTable));
		};

		StepDefUtil.tryAndWait(timeoutSec, 500, isErrorLogged);
	}

	private boolean containsAllFragments(@NonNull final String errorText, @NonNull final DataTable dataTable)
	{
		return dataTable.asMaps().stream().allMatch(row -> {
			final String text = row.get("Text");
			if (text != null && !text.isEmpty() && !errorText.contains(text))
			{
				return false;
			}
			final String adMessage = row.get("AD_Message");
			return adMessage == null || adMessage.isEmpty() || containsAdMessageInAnyLanguage(errorText, adMessage);
		});
	}

	private boolean containsAdMessageInAnyLanguage(@NonNull final String errorText, @NonNull final String adMessageValue)
	{
		final I_AD_Message adMessage = queryBL.createQueryBuilder(I_AD_Message.class)
				.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, adMessageValue)
				.create()
				.firstOnlyNotNull(I_AD_Message.class);

		final List<String> messageTexts = new ArrayList<>();
		messageTexts.add(adMessage.getMsgText());
		queryBL.createQueryBuilder(I_AD_Message_Trl.class)
				.addEqualsFilter(I_AD_Message_Trl.COLUMNNAME_AD_Message_ID, adMessage.getAD_Message_ID())
				.create()
				.stream()
				.forEach(trl -> messageTexts.add(trl.getMsgText()));

		// the parameters {0}, {1}.. are filled in at runtime, so match the fixed parts between them
		return messageTexts.stream().anyMatch(messageText -> Arrays.stream(messageText.split("\\{\\d+}"))
				.map(String::trim)
				.filter(part -> !part.isEmpty())
				.allMatch(errorText::contains));
	}

	private void createIMPProcessor(@NonNull final Map<String, String> row)
	{
		final String name = DataTableUtil.extractStringForColumnName(row, I_IMP_Processor.COLUMNNAME_Name);

		final I_IMP_Processor impProcessor = queryBL.createQueryBuilder(I_IMP_Processor.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_IMP_Processor.COLUMNNAME_Name, name)
				.create()
				.firstOnlyOptional(I_IMP_Processor.class)
				.orElseGet(() -> InterfaceWrapperHelper.newInstance(I_IMP_Processor.class));

		impProcessor.setName(name);

		final String frequencyType = DataTableUtil.extractStringForColumnName(row, I_IMP_Processor.COLUMNNAME_FrequencyType);
		impProcessor.setFrequencyType(frequencyType);

		final int frequency = DataTableUtil.extractIntForColumnName(row, I_IMP_Processor.COLUMNNAME_Frequency);
		impProcessor.setFrequency(frequency);

		final String impProcessorTypeIdentifier = DataTableUtil.extractStringForColumnName(row, I_IMP_Processor_Type.COLUMNNAME_IMP_Processor_Type_ID + "." + TABLECOLUMN_IDENTIFIER);
		final I_IMP_Processor_Type impProcessorType = impProcessorTypeStepDefData.get(impProcessorTypeIdentifier);
		assertThat(impProcessorType).isNotNull();

		impProcessor.setIMP_Processor_Type_ID(impProcessorType.getIMP_Processor_Type_ID());

		final ServerBoot serverBoot = SpringContextHolder.instance.getBean(ServerBoot.class);
		final CommandLineParser.CommandLineOptions commandLineOptions = serverBoot.getCommandLineOptions();
		assertThat(commandLineOptions.getRabbitPort()).isNotNull();

		impProcessor.setPasswordInfo(commandLineOptions.getRabbitPassword());
		impProcessor.setAccount(commandLineOptions.getRabbitUser());
		impProcessor.setPort(commandLineOptions.getRabbitPort());
		impProcessor.setHost(commandLineOptions.getRabbitHost());

		saveRecord(impProcessor);

		final String impProcessorIdentifier = DataTableUtil.extractStringForColumnName(row, I_IMP_Processor.COLUMNNAME_IMP_Processor_ID + "." + TABLECOLUMN_IDENTIFIER);
		impProcessorTable.putOrReplace(impProcessorIdentifier, impProcessor);
	}
}
