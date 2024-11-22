package de.metas.ui.web.attributes_included_tab;

import de.metas.attributes_included_tab.AttributesIncludedTabService;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptor;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabFieldDescriptor;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.attributes_included_tab.AttributesIncludedTabEntityBinding.AttributesIncludedTabEntityBindingBuilder;
import de.metas.ui.web.document.filter.provider.DocumentFilterDescriptorsProvidersService;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.compiere.SpringContextHolder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AttributesIncludedTabLoader
{
	private static final String DETAIL_ID_PREFIX = "Attributes";

	@NonNull private final AttributesIncludedTabService attributesIncludedTabService = SpringContextHolder.instance.getBean(AttributesIncludedTabService.class);
	@NonNull private final DocumentFilterDescriptorsProvidersService filterDescriptorsProvidersService = SpringContextHolder.instance.getBean(DocumentFilterDescriptorsProvidersService.class);
	@NonNull private final AttributesIncludedTabDocumentsRepository documentsRepository = SpringContextHolder.instance.getBean(AttributesIncludedTabDocumentsRepository.class);

	@NonNull private final AdWindowId adWindowId;
	@NonNull private final DocumentEntityDescriptor.Builder rootEntityDescriptor;
	@NonNull private final DocumentLayoutDescriptor.Builder layoutBuilder;

	@Builder
	private AttributesIncludedTabLoader(
			@NonNull final AdWindowId adWindowId,
			@NonNull final DocumentEntityDescriptor.Builder rootEntityDescriptor,
			@NonNull final DocumentLayoutDescriptor.Builder layoutBuilder)
	{
		this.adWindowId = adWindowId;
		this.rootEntityDescriptor = rootEntityDescriptor;
		this.layoutBuilder = layoutBuilder;
	}

	public void load()
	{
		final AdTableId adTableId = TableIdsCache.instance.getTableIdNotNull(rootEntityDescriptor.getTableNameNotNull());
		attributesIncludedTabService.getDescriptorsBy(adTableId).forEach(this::loadTab);
	}

	private void loadTab(@NonNull final AttributesIncludedTabDescriptor descriptor)
	{
		final DocumentEntityDescriptor entityDescriptor = toDocumentEntityDescriptor(descriptor);
		rootEntityDescriptor.addIncludedEntity(entityDescriptor);

		layoutBuilder.addDetail(toDocumentLayoutDetailDescriptor(entityDescriptor, extractFieldNamesInOrder(descriptor)));
	}

	private static List<String> extractFieldNamesInOrder(final @NonNull AttributesIncludedTabDescriptor descriptor)
	{
		return descriptor.getFieldsInOrder()
				.stream()
				.map(AttributesIncludedTabFieldBinding::computeFieldName)
				.distinct()
				.collect(Collectors.toList());
	}

	//
	//
	// Entity Descriptor
	//
	//

	private DocumentEntityDescriptor toDocumentEntityDescriptor(AttributesIncludedTabDescriptor includedTab)
	{
		final DocumentEntityDescriptor.Builder builder = DocumentEntityDescriptor.builder()
				.setSingleRowDetail(true)
				.setDocumentType(adWindowId)
				.setDetailId(computeDetailId(includedTab))
				.setCaption(includedTab.getCaption())
				.disableCallouts()
				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.setAllowCreateNewLogic(ConstantLogicExpression.FALSE)
				.setAllowDeleteLogic(ConstantLogicExpression.FALSE)
				.setDataBinding(newEntityBinding()
						.includedTab(includedTab)
						.build())
				.setFilterDescriptorsProvidersService(filterDescriptorsProvidersService);

		includedTab.getFieldsInOrder()
				.stream()
				.map(this::toDocumentFieldDescriptor)
				.forEach(builder::addField);

		return builder.build();
	}

	private AttributesIncludedTabEntityBindingBuilder newEntityBinding()
	{
		return AttributesIncludedTabEntityBinding.builder()
				.documentsRepository(documentsRepository);
	}

	private static DetailId computeDetailId(final AttributesIncludedTabDescriptor includedTab)
	{
		return DetailId.fromPrefixAndId(DETAIL_ID_PREFIX, includedTab.getId().getRepoId());
	}

	private DocumentFieldDescriptor.Builder toDocumentFieldDescriptor(@NonNull final AttributesIncludedTabFieldDescriptor includedTabField)
	{
		final AttributesIncludedTabFieldBinding fieldBinding = AttributesIncludedTabFieldBinding.builder()
				.attributesIncludedTabService(attributesIncludedTabService)
				.includedTabField(includedTabField)
				.build();

		return DocumentFieldDescriptor.builder(fieldBinding.getColumnName())
				.setCaption(includedTabField.getCaption())
				.setDescription(includedTabField.getDescription())
				//
				.setValueClass(fieldBinding.getValueClass())
				.setWidgetType(fieldBinding.getWidgetType())
				.setLookupDescriptorProvider(fieldBinding.getLookupDescriptor())
				//
				.setDefaultValueExpression(Optional.empty())
				.setReadonlyLogic(ConstantLogicExpression.of(includedTabField.isReadOnlyValues()))
				.setDisplayLogic(ConstantLogicExpression.TRUE)
				.setMandatoryLogic(ConstantLogicExpression.of(fieldBinding.isMandatory()))
				//
				.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField)
				//
				.setDataBinding(fieldBinding)
				//
				;
	}

	//
	//
	// Layout
	//
	//

	private DocumentLayoutDetailDescriptor toDocumentLayoutDetailDescriptor(
			@NonNull final DocumentEntityDescriptor entityDescriptor,
			@NonNull final List<String> fieldNamesInOrder)
	{
		final WindowId windowId = WindowId.of(adWindowId);

		return DocumentLayoutDetailDescriptor
				.builder(windowId, entityDescriptor.getDetailIdNotNull())
				.caption(entityDescriptor.getCaption())
				.description(entityDescriptor.getDescription())
				.queryOnActivate(true)
				.singleRowDetailLayout(true)
				.singleRowLayout(DocumentLayoutSingleRow.builder()
						.setWindowId(windowId)
						.addSection(DocumentLayoutSectionDescriptor.builder()
								.setCaption(TranslatableStrings.empty())
								.setDescription(TranslatableStrings.empty())
								.addColumn(DocumentLayoutColumnDescriptor.builder()
										.addElementGroup(DocumentLayoutElementGroupDescriptor.builder()
												.addElementLines(fieldNamesInOrder.stream()
														.map(fieldName -> createLayoutElementLine(entityDescriptor.getField(fieldName)))
														.collect(Collectors.toList()))))
								.setClosableMode(DocumentLayoutSectionDescriptor.ClosableMode.INITIALLY_OPEN)
								.setCaptionMode(DocumentLayoutSectionDescriptor.CaptionMode.DISPLAY)))
				.build();
	}

	private DocumentLayoutElementLineDescriptor.Builder createLayoutElementLine(@NonNull final DocumentFieldDescriptor field)
	{
		return DocumentLayoutElementLineDescriptor.builder()
				.addElement(DocumentLayoutElementDescriptor.builder()
						.setCaption(field.getCaption())
						.setDescription(field.getDescription())
						.setViewEditorRenderMode(ViewEditorRenderMode.ALWAYS)
						.setWidgetType(field.getWidgetType())
						.setWidgetSize(WidgetSize.Default)
						.addField(DocumentLayoutElementFieldDescriptor.builder(field.getFieldName())
								.setEmptyFieldText(TranslatableStrings.empty())));
	}
}
