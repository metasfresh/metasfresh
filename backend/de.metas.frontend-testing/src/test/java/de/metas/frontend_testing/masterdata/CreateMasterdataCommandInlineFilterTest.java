package de.metas.frontend_testing.masterdata;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.ad_reference.ADRefListItemCreateRequest;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.AdRefListRepositoryMocked;
import de.metas.ad_reference.AdRefTableRepositoryMocked;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.TranslatableStrings;
import de.metas.externalreference.ExternalReferenceRepository;
import de.metas.externalreference.ExternalReferenceTypes;
import de.metas.externalreference.ExternalSystems;
import de.metas.frontend_testing.masterdata.inlinefilter.JsonSetupInlineFilterRequest;
import de.metas.frontend_testing.masterdata.inlinefilter.JsonSetupInlineFilterResponse;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_AD_UI_Element;
import org.compiere.model.I_AD_Window;
import org.compiere.model.X_AD_UI_Element;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CreateMasterdataCommandInlineFilterTest
{
	private static final String TABLE = "C_Invoice";
	private static final String COLUMN = "C_BPartner_ID";

	private IQueryBL queryBL;
	private IADTableDAO adTableDAO;
	private CreateMasterdataCommandSupportingServices services;

	private int windowId;
	private int columnId;
	private int otherColumnId;
	private int mainTabId;
	private int elementId;
	private int otherElementId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		queryBL = Services.get(IQueryBL.class);
		adTableDAO = Services.get(IADTableDAO.class);

		// not newMocked(): that auto-creates lists on demand, which would make every widgetSize valid
		final AdRefListRepositoryMocked refListRepository = new AdRefListRepositoryMocked();
		final ADReferenceService adReferenceService = new ADReferenceService(refListRepository, new AdRefTableRepositoryMocked());
		refListRepository.setAutoCreateOnDemand(true);
		for (final String size : new String[] { "S", "M", "L", "XL", "XXL" })
		{
			adReferenceService.saveRefList(ADRefListItemCreateRequest.builder()
					.referenceId(ReferenceId.ofRepoId(X_AD_UI_Element.WIDGETSIZE_AD_Reference_ID))
					.value(size)
					.name(TranslatableStrings.constant(size))
					.build());
		}
		refListRepository.setAutoCreateOnDemand(false);

		services = new CreateMasterdataCommandSupportingServices(
				new ExternalReferenceRepository(queryBL, new ExternalSystems(), new ExternalReferenceTypes()),
				new ExternalReferenceTypes(),
				new ExternalSystems(),
				adReferenceService);

		final I_AD_Table table = InterfaceWrapperHelper.newInstance(I_AD_Table.class);
		table.setAD_Table_ID(adTableDAO.retrieveTableId(TABLE)); // in unit tests table ids are resolved by a fixed list
		table.setTableName(TABLE);
		InterfaceWrapperHelper.saveRecord(table);

		columnId = newColumn(table.getAD_Table_ID(), COLUMN, true);
		otherColumnId = newColumn(table.getAD_Table_ID(), "M_Warehouse_ID", true);

		final I_AD_Window window = InterfaceWrapperHelper.newInstance(I_AD_Window.class);
		window.setName("Invoice");
		InterfaceWrapperHelper.saveRecord(window);
		windowId = window.getAD_Window_ID();

		// a child tab with a lower SeqNo must not be taken for the main tab
		final int childTabId = newTab(windowId, table.getAD_Table_ID(), 5, 1);
		mainTabId = newTab(windowId, table.getAD_Table_ID(), 10, 0);
		newTab(windowId, table.getAD_Table_ID(), 20, 0);
		// inactive records that look like better candidates must be ignored: a main tab with a lower SeqNo, a duplicate field and a duplicate element
		final int inactiveTabId = newTab(windowId, table.getAD_Table_ID(), 1, 0);
		deactivate(I_AD_Tab.class, inactiveTabId);
		newElement(inactiveTabId, newField(inactiveTabId, columnId), "XXL");

		elementId = newElement(mainTabId, newField(mainTabId, columnId), "M");
		otherElementId = newElement(mainTabId, newField(mainTabId, otherColumnId), "L");
		final int inactiveFieldId = newField(mainTabId, columnId);
		deactivate(I_AD_Field.class, inactiveFieldId);
		newElement(mainTabId, inactiveFieldId, "XXL"); // an active element of an inactive field
		deactivate(I_AD_UI_Element.class, newElement(mainTabId, InterfaceWrapperHelper.load(elementId, I_AD_UI_Element.class).getAD_Field_ID(), "XXL"));
		// the same column in the child tab: its element must stay untouched
		newElement(childTabId, newField(childTabId, columnId), "XL");
	}

	private static <T> void deactivate(final Class<T> modelClass, final int id)
	{
		final T record = InterfaceWrapperHelper.load(id, modelClass);
		InterfaceWrapperHelper.setValue(record, "IsActive", false);
		InterfaceWrapperHelper.saveRecord(record);
	}

	private static int newColumn(final int tableId, final String name, final boolean showInline)
	{
		final I_AD_Column column = InterfaceWrapperHelper.newInstance(I_AD_Column.class);
		column.setAD_Table_ID(tableId);
		column.setColumnName(name);
		column.setName(name);
		column.setIsShowFilterInline(showInline);
		InterfaceWrapperHelper.saveRecord(column);
		return column.getAD_Column_ID();
	}

	private static int newTab(final int windowId, final int tableId, final int seqNo, final int tabLevel)
	{
		final I_AD_Tab tab = InterfaceWrapperHelper.newInstance(I_AD_Tab.class);
		tab.setAD_Window_ID(windowId);
		tab.setAD_Table_ID(tableId);
		tab.setName("tab" + seqNo);
		tab.setSeqNo(seqNo);
		tab.setTabLevel(tabLevel);
		InterfaceWrapperHelper.saveRecord(tab);
		return tab.getAD_Tab_ID();
	}

	private static int newField(final int tabId, final int columnId)
	{
		final I_AD_Field field = InterfaceWrapperHelper.newInstance(I_AD_Field.class);
		field.setAD_Tab_ID(tabId);
		field.setAD_Column_ID(columnId);
		field.setName("field" + columnId);
		InterfaceWrapperHelper.saveRecord(field);
		return field.getAD_Field_ID();
	}

	private static int newElement(final int tabId, final int fieldId, final String widgetSize)
	{
		final I_AD_UI_Element element = InterfaceWrapperHelper.newInstance(I_AD_UI_Element.class);
		element.setAD_Tab_ID(tabId);
		element.setAD_Field_ID(fieldId);
		element.setName("element" + fieldId);
		element.setWidgetSize(widgetSize);
		InterfaceWrapperHelper.saveRecord(element);
		return element.getAD_UI_Element_ID();
	}

	private JsonSetupInlineFilterRequest.JsonSetupInlineFilterRequestBuilder request()
	{
		return JsonSetupInlineFilterRequest.builder().tableName(TABLE).columnName(COLUMN).windowId(windowId);
	}

	private JsonCreateMasterdataResponse execute(final JsonSetupInlineFilterRequest... entries)
	{
		return CreateMasterdataCommand.builder()
				.services(services)
				.request(JsonCreateMasterdataRequest.builder().inlineFilters(ImmutableList.copyOf(entries)).build())
				.build()
				.execute();
	}

	private static boolean showInline(final int columnId)
	{
		return InterfaceWrapperHelper.load(columnId, I_AD_Column.class).isShowFilterInline();
	}

	private static String widgetSize(final int elementId)
	{
		return InterfaceWrapperHelper.load(elementId, I_AD_UI_Element.class).getWidgetSize();
	}

	@Test
	void setsColumnFlagAndWidgetSizeOfMainTabElement_andReturnsPreviousValues()
	{
		InterfaceWrapperHelper.save(withFlag(columnId, false));

		final JsonCreateMasterdataResponse response = execute(request().showInline(true).widgetSize("XXL").build());

		assertThat(showInline(columnId)).isTrue();
		assertThat(widgetSize(elementId)).isEqualTo("XXL");
		assertThat(response.getInlineFilters()).containsExactly(JsonSetupInlineFilterResponse.builder().previousShowInline(false).previousWidgetSize("M").build());
	}

	@Test
	void doesNotTouchOtherColumnsOrElements()
	{
		execute(request().showInline(false).widgetSize("S").build());

		assertThat(showInline(columnId)).isFalse();
		assertThat(showInline(otherColumnId)).isTrue();
		assertThat(widgetSize(otherElementId)).isEqualTo("L");
		assertThat(queryBL.createQueryBuilder(I_AD_UI_Element.class)
				.addEqualsFilter(I_AD_UI_Element.COLUMNNAME_WidgetSize, "XL").create().count())
				.as("the element of the same column in the child tab").isEqualTo(1);
	}

	@Test
	void nullValuesLeaveThingsUnchanged_andPreviousValuesAreStillReturned()
	{
		final JsonCreateMasterdataResponse response = execute(request().build());

		assertThat(showInline(columnId)).isTrue();
		assertThat(widgetSize(elementId)).isEqualTo("M");
		assertThat(response.getInlineFilters()).containsExactly(JsonSetupInlineFilterResponse.builder().previousShowInline(true).previousWidgetSize("M").build());
	}

	@Test
	void unknownWindow_isRejected_andNothingIsWritten()
	{
		assertThatThrownBy(() -> execute(request().windowId(windowId + 999).showInline(false).widgetSize("S").build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(String.valueOf(windowId + 999));
		assertThat(showInline(columnId)).isTrue();
	}

	@Test
	void unknownColumn_isRejected()
	{
		assertThatThrownBy(() -> execute(request().columnName("NoSuchColumn").build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoSuchColumn");
	}

	@Test
	void columnWithoutFieldInMainTab_isRejected()
	{
		final int tableId = InterfaceWrapperHelper.load(columnId, I_AD_Column.class).getAD_Table_ID();
		final int noFieldColumnId = newColumn(tableId, "NoField_ID", false);

		assertThatThrownBy(() -> execute(request().columnName("NoField_ID").showInline(true).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoField_ID");
		assertThat(showInline(noFieldColumnId)).isFalse();
	}

	@Test
	void columnWithFieldButWithoutUIElement_isRejected_andFlagIsNotSet()
	{
		final int tableId = InterfaceWrapperHelper.load(columnId, I_AD_Column.class).getAD_Table_ID();
		final int noElementColumnId = newColumn(tableId, "NoElement_ID", false);
		newField(mainTabId, noElementColumnId);

		assertThatThrownBy(() -> execute(request().columnName("NoElement_ID").showInline(true).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoElement_ID");
		assertThat(showInline(noElementColumnId)).isFalse();
	}

	@Test
	void ambiguousElements_areRejected()
	{
		newElement(mainTabId, InterfaceWrapperHelper.load(elementId, I_AD_UI_Element.class).getAD_Field_ID(), "S");

		assertThatThrownBy(() -> execute(request().showInline(false).widgetSize("XXL").build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("2 UI elements");
		assertThat(showInline(columnId)).isTrue();
		assertThat(widgetSize(elementId)).isEqualTo("M");
	}

	@Test
	void invalidWidgetSize_isRejected_andNothingIsWritten()
	{
		InterfaceWrapperHelper.save(withFlag(columnId, false));

		assertThatThrownBy(() -> execute(request().showInline(true).widgetSize("HUGE").build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("HUGE");

		assertThat(showInline(columnId)).isFalse();
		assertThat(widgetSize(elementId)).isEqualTo("M");
	}

	@Test
	void missingWindowId_isRejected()
	{
		assertThatThrownBy(() -> execute(request().windowId(0).showInline(false).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("windowId is required");
		assertThat(showInline(columnId)).isTrue();
	}

	@Test
	void oneBadEntry_writesNothing()
	{
		assertThatThrownBy(() -> execute(
				request().showInline(false).widgetSize("XXL").build(),
				request().columnName("NoSuchColumn").showInline(false).build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoSuchColumn");

		assertThat(showInline(columnId)).isTrue();
		assertThat(widgetSize(elementId)).isEqualTo("M");
	}

	@Test
	void severalEntries_areAppliedInOrder()
	{
		final JsonCreateMasterdataResponse response = execute(
				request().showInline(false).widgetSize("S").build(),
				request().columnName("M_Warehouse_ID").showInline(false).widgetSize("XL").build());

		assertThat(response.getInlineFilters()).containsExactly(
				JsonSetupInlineFilterResponse.builder().previousShowInline(true).previousWidgetSize("M").build(),
				JsonSetupInlineFilterResponse.builder().previousShowInline(true).previousWidgetSize("L").build());
		assertThat(widgetSize(elementId)).isEqualTo("S");
		assertThat(widgetSize(otherElementId)).isEqualTo("XL");
	}

	@Test
	void requestJsonContract() throws Exception
	{
		final JsonCreateMasterdataRequest request = JsonObjectMapperHolder.sharedJsonObjectMapper().readValue(
				"{\"inlineFilters\":[{\"tableName\":\"C_Invoice\",\"columnName\":\"C_BPartner_ID\",\"showInline\":true,\"windowId\":167,\"widgetSize\":\"L\"}]}",
				JsonCreateMasterdataRequest.class);

		assertThat(request.getInlineFilters()).containsExactly(JsonSetupInlineFilterRequest.builder()
				.tableName("C_Invoice").columnName("C_BPartner_ID").showInline(true).windowId(167).widgetSize("L").build());
	}

	@Test
	void responseJsonContract()
	{
		final JsonNode json = JsonObjectMapperHolder.sharedJsonObjectMapper().valueToTree(JsonCreateMasterdataResponse.builder()
				.inlineFilters(ImmutableList.of(JsonSetupInlineFilterResponse.builder().previousShowInline(true).previousWidgetSize("M").build()))
				.build());

		assertThat(json.at("/inlineFilters/0/previousShowInline").asBoolean()).isTrue();
		assertThat(json.at("/inlineFilters/0/previousWidgetSize").asText()).isEqualTo("M");
	}

	private static I_AD_Column withFlag(final int columnId, final boolean showInline)
	{
		final I_AD_Column column = InterfaceWrapperHelper.load(columnId, I_AD_Column.class);
		column.setIsShowFilterInline(showInline);
		return column;
	}
}
