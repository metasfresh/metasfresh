package de.metas.cucumber.stepdefs.report;

import de.metas.cucumber.stepdefs.process.AD_Process_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentPrintOptionDescriptor;
import de.metas.report.DocumentPrintOptionDescriptorsList;
import de.metas.report.DocumentPrintOptionDescriptorsRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.compiere.model.I_AD_Process;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for verifying document print option descriptors.
 *
 * <p>Verifies that {@link DocumentPrintOptionDescriptorsRepository} correctly resolves
 * print option defaults, including {@code @SQL=} expressions.
 */
@RequiredArgsConstructor
public class DocumentPrintOption_StepDef
{
	@NonNull private final AD_Process_StepDefData processTable;
	@NonNull private final DocumentPrintOptionDescriptorsRepository printOptionDescriptorsRepository;

	/**
	 * Verifies print option descriptors for a given process.
	 *
	 * <p>Required columns: {@code OptionName}, {@code DefaultValue} (true/false)
	 */
	@Then("the print option descriptors for AD_Process {string} include:")
	public void the_print_option_descriptors_include(
			@NonNull final String processIdentifierStr,
			@NonNull final DataTable dataTable)
	{
		final I_AD_Process process = processTable.get(StepDefDataIdentifier.ofString(processIdentifierStr));
		final AdProcessId processId = AdProcessId.ofRepoId(process.getAD_Process_ID());

		final DocumentPrintOptionDescriptorsList descriptors = printOptionDescriptorsRepository.getPrintingOptionDescriptors(processId);

		DataTableRows.of(dataTable).forEach(row -> assertPrintOption(descriptors, row));
	}

	private static void assertPrintOption(
			@NonNull final DocumentPrintOptionDescriptorsList descriptors,
			@NonNull final DataTableRow row)
	{
		final String optionName = row.getAsString("OptionName");
		final boolean expectedDefaultValue = row.getAsBoolean("DefaultValue");

		final DocumentPrintOptionDescriptor descriptor = descriptors.getOptions().stream()
				.filter(d -> optionName.equals(d.getInternalName()))
				.findFirst()
				.orElse(null);

		assertThat(descriptor)
				.as("Print option descriptor for '%s' should exist", optionName)
				.isNotNull();
		assertThat(descriptor.getDefaultValue())
				.as("Print option '%s' default value", optionName)
				.isEqualTo(expectedDefaultValue);
	}
}
