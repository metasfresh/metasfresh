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
 * Step definitions for verifying document print option descriptors resolved by
 * {@link DocumentPrintOptionDescriptorsRepository}.
 *
 * <p>Verifies that print option defaults (including {@code @SQL=} expressions) are
 * correctly resolved from {@code AD_Process_Para.DefaultValue}.
 *
 * <p>Example:
 * <pre>{@code
 * Then the print option descriptors for AD_Process "process" include:
 *   | OptionName               | DefaultValue |
 *   | PRINTER_OPTS_IsPrintLogo | true         |
 * }</pre>
 */
@RequiredArgsConstructor
public class DocumentPrintOption_StepDef
{
	@NonNull private final AD_Process_StepDefData processTable;
	@NonNull private final DocumentPrintOptionDescriptorsRepository printOptionDescriptorsRepository;

	/**
	 * Verifies that the print option descriptors for the given process match expectations.
	 *
	 * <p>Required columns:
	 * <ul>
	 *   <li>{@code OptionName} — internal name of the print option (e.g. {@code PRINTER_OPTS_IsPrintLogo})</li>
	 *   <li>{@code DefaultValue} — expected resolved default ({@code true} or {@code false})</li>
	 * </ul>
	 *
	 * @param processIdentifierStr identifier of the AD_Process (from {@link de.metas.cucumber.stepdefs.process.AD_Process_Create_StepDef})
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
