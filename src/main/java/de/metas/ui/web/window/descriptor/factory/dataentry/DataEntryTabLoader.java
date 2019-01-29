package de.metas.ui.web.window.descriptor.factory.dataentry;

import static org.assertj.core.api.Assertions.fail;

import java.util.List;

import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.compiere.Adempiere;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.dataentry.DataEntryField;
import de.metas.dataentry.DataEntryGroup;
import de.metas.dataentry.DataEntryRepository;
import de.metas.dataentry.DataEntrySubGroup;
import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutColumnDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementGroupDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementLineDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSectionDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class DataEntryTabLoader
{
	int adWindowId;

	@NonNull
	final WindowId windowId;

	public List<DocumentLayoutDetailDescriptor> loadDocumentLayout()
	{
		final DataEntryRepository dataEntryRepository = Adempiere.getBean(DataEntryRepository.class);

		final List<DataEntryGroup> dataEntryGroups = dataEntryRepository.getByWindowId(adWindowId);

		return createLayoutDescriptors(dataEntryGroups);
	}

	@VisibleForTesting
	List<DocumentLayoutDetailDescriptor> createLayoutDescriptors(@NonNull final List<DataEntryGroup> dataEntryGroups)
	{
		final ImmutableList.Builder<DocumentLayoutDetailDescriptor> result = ImmutableList.builder();
		for (final DataEntryGroup dataEntryGroup : dataEntryGroups)
		{
			final DocumentLayoutDetailDescriptor groupDescriptor = createGroupLayoutDescriptor(windowId, dataEntryGroup);
			result.add(groupDescriptor);
		}
		return result.build();
	}

	private static DocumentLayoutDetailDescriptor createGroupLayoutDescriptor(
			@NonNull final WindowId windowId,
			@NonNull final DataEntryGroup dataEntryGroup)
	{
		final de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor.Builder builder = DocumentLayoutDetailDescriptor
				.builder(windowId, createDetailIdFor(dataEntryGroup))
				.caption(dataEntryGroup.getName())
				.description(dataEntryGroup.getDescription())
				.internalName(dataEntryGroup.getInternalName())
				.queryOnActivate(true)
				.supportQuickInput(false);

		for (final DataEntrySubGroup dataEntrySubGroup : dataEntryGroup.getDataEntrySubGroups())
		{
			builder.addSubLayout(createSubGroupLayoutDescriptor(windowId, dataEntrySubGroup));
		}

		return builder.build();
	}

	private static DocumentLayoutDetailDescriptor createSubGroupLayoutDescriptor(
			@NonNull final WindowId windowId,
			@NonNull final DataEntrySubGroup dataEntrySubGroup)
	{
		final DetailId subgroupDetailId = createDetailIdFor(dataEntrySubGroup);

		final DocumentLayoutDetailDescriptor.Builder subGroupDescriptor = DocumentLayoutDetailDescriptor
				.builder(windowId, subgroupDetailId)
				.caption(dataEntrySubGroup.getName())
				.description(dataEntrySubGroup.getDescription())
				.internalName(dataEntrySubGroup.getInternalName())
				.queryOnActivate(true)
				.supportQuickInput(false);

		final ViewLayout.Builder viewLayout = ViewLayout
				.builder();

		final DocumentLayoutColumnDescriptor.Builder column = DocumentLayoutColumnDescriptor
				.builder();
		final DocumentLayoutSectionDescriptor.Builder section = DocumentLayoutSectionDescriptor
				.builder()
				.addColumn(column);
		final DocumentLayoutSingleRow.Builder singleRowLayout = DocumentLayoutSingleRow
				.builder()
				.setWindowId(windowId)
				.addSections(ImmutableList.of(section));

		final List<DataEntryField> fields = dataEntrySubGroup.getDataEntryFields();
		for (final DataEntryField field : fields)
		{
			final DocumentLayoutElementDescriptor.Builder element = createFieldElementDescriptor(field);

			viewLayout.addElement(element);

			final DocumentLayoutElementLineDescriptor.Builder elementLine = DocumentLayoutElementLineDescriptor
					.builder()
					.addElement(element);
			final DocumentLayoutElementGroupDescriptor.Builder elementGroup = DocumentLayoutElementGroupDescriptor
					.builder()
					.addElementLine(elementLine);
			column.addElementGroup(elementGroup);
		}
		subGroupDescriptor.gridLayout(viewLayout);
		subGroupDescriptor.singleRowLayout(singleRowLayout);

		return subGroupDescriptor.build();
	}

	private static DocumentLayoutElementDescriptor.Builder createFieldElementDescriptor(final DataEntryField field)
	{
		DocumentLayoutElementFieldDescriptor.Builder fieldBuilder = DocumentLayoutElementFieldDescriptor
				.builder(createFieldNameFor(field))
				.setEmptyText(ITranslatableString.empty());

		final DocumentLayoutElementDescriptor.Builder element = DocumentLayoutElementDescriptor
				.builder()
				.setCaption(field.getCaption())
				.setDescription(field.getDescription())
				.setViewEditorRenderMode(ViewEditorRenderMode.ALWAYS)
				.setWidgetType(ofFieldType(field.getType()))
				.addField(fieldBuilder);
		return element;
	}

	private static String createFieldNameFor(@NonNull final DataEntryField field)
	{
		return Integer.toString(field.getId().getRepoId());
	}

	private static DocumentFieldWidgetType ofFieldType(@NonNull final DataEntryField.Type fieldType)
	{
		switch (fieldType)
		{
			case DATE:
				return DocumentFieldWidgetType.Date;
			case LIST:
				return DocumentFieldWidgetType.List;
			case NUMBER:
				return DocumentFieldWidgetType.Number;
			case STRING:
				return DocumentFieldWidgetType.Text;
			case YESNO:
				return DocumentFieldWidgetType.YesNo;
			default:
				fail("Unexpected DataEntryField.Type={}", fieldType);
				return null;
		}
	}

	public List<DocumentEntityDescriptor> loadDocumentEntity()
	{
		final DataEntryRepository dataEntryRepository = Adempiere.getBean(DataEntryRepository.class);

		final List<DataEntryGroup> dataEntryGroups = dataEntryRepository.getByWindowId(adWindowId);

		return createGroupEntityDescriptors(dataEntryGroups);
	}

	@VisibleForTesting
	List<DocumentEntityDescriptor> createGroupEntityDescriptors(@NonNull final List<DataEntryGroup> dataEntryGroups)
	{
		final ImmutableList.Builder<DocumentEntityDescriptor> result = ImmutableList.builder();
		for (final DataEntryGroup dataEntryGroup : dataEntryGroups)
		{
			final DocumentEntityDescriptor.Builder documentEntityDescriptor = createGroupEntityDescriptor(dataEntryGroup);
			result.add(documentEntityDescriptor.build());
		}
		return result.build();
	}

	private DocumentEntityDescriptor.Builder createGroupEntityDescriptor(@NonNull final DataEntryGroup dataEntryGroup)
	{
		final DataEntryGroupBindingDescriptorBuilder dataEntryDocumentBinding = new DataEntryGroupBindingDescriptorBuilder();

		final DocumentEntityDescriptor.Builder documentEntityDescriptor = DocumentEntityDescriptor
				.builder()
				.setDocumentType(DocumentType.Window, getAdWindowId())
				.setDetailId(createDetailIdFor(dataEntryGroup))
				.setInternalName(dataEntryGroup.getInternalName())
				.setCaption(dataEntryGroup.getName())
				.setDescription(dataEntryGroup.getDescription())

				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				// this is just a "grouping" tab with no data(-records) of its own
				.setAllowCreateNewLogic(ConstantLogicExpression.FALSE)
				.setAllowDeleteLogic(ConstantLogicExpression.FALSE)

				.setDataBinding(dataEntryDocumentBinding);

		for (final DataEntrySubGroup dataEntrySubGroup : dataEntryGroup.getDataEntrySubGroups())
		{
			documentEntityDescriptor.addIncludedEntity(createSubGroupEntityDescriptor(dataEntrySubGroup));
		}
		return documentEntityDescriptor;
	}

	private DocumentEntityDescriptor createSubGroupEntityDescriptor(@NonNull final DataEntrySubGroup dataEntrySubGroup)
	{
		final DataEntrySubGroupBindingDescriptorBuilder dataEntryDocumentBinding = new DataEntrySubGroupBindingDescriptorBuilder();

		final DocumentEntityDescriptor.Builder documentEntityDescriptor = DocumentEntityDescriptor
				.builder()
				.setDocumentType(DocumentType.Window, getAdWindowId())
				.setDetailId(createDetailIdFor(dataEntrySubGroup))
				.setInternalName(dataEntrySubGroup.getInternalName())
				.setCaption(dataEntrySubGroup.getName())
				.setDescription(dataEntrySubGroup.getDescription())

				.setReadonlyLogic(ConstantLogicExpression.FALSE)
				.setAllowCreateNewLogic(ConstantLogicExpression.TRUE)
				.setAllowDeleteLogic(ConstantLogicExpression.TRUE)
				.setDisplayLogic(ConstantLogicExpression.TRUE)

				.setDataBinding(dataEntryDocumentBinding);

		for (final DataEntryField dataEntryField : dataEntrySubGroup.getDataEntryFields())
		{
			documentEntityDescriptor.addField(createFieldDescriptor(dataEntryField));
		}

		return documentEntityDescriptor.build();
	}

	private DocumentFieldDescriptor.Builder createFieldDescriptor(@NonNull final DataEntryField dataEntryField)
	{
		final DocumentFieldDataBindingDescriptor dataBinding = new DataEntryFieldBindingDescriptor(
				createFieldNameFor(dataEntryField),
				dataEntryField.isMandatory());

		return DocumentFieldDescriptor.builder(createFieldNameFor(dataEntryField))
				.setCaption(dataEntryField.getCaption())
				.setDescription(dataEntryField.getDescription())
				.setWidgetType(ofFieldType(dataEntryField.getType()))
				.setDataBinding(dataBinding);
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntryGroup dataEntryGroup)
	{
		return DetailId.fromAD_Tab_ID(dataEntryGroup.getId().getRepoId());
	}

	private static DetailId createDetailIdFor(@NonNull final DataEntrySubGroup dataEntrySubGroup)
	{
		return DetailId.fromAD_Tab_ID(dataEntrySubGroup.getId().getRepoId() * 2); // TODO allow detail-ID with letter-prefix?
	}

}
