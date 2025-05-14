package de.metas.ui.web.window.descriptor.factory.standard;

import com.google.common.collect.ImmutableList;
import de.metas.attributes_included_tab.descriptor.AttributesIncludedTabDescriptorService;
import de.metas.ui.web.quickinput.QuickInputDescriptorFactoryService;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.decorator.IDocumentDecorator;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
class LayoutFactorySupportingServices
{
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull @Getter private final LookupDataSourceFactory lookupDataSourceFactory;
	@NonNull private final QuickInputDescriptorFactoryService quickInputDescriptors;
	@NonNull @Getter private final AttributesIncludedTabDescriptorService attributesIncludedTabDescriptorService;
	@NonNull private final Optional<List<IDocumentDecorator>> documentDecorators;

	public List<IDocumentDecorator> getDocumentDecorators()
	{
		return documentDecorators.orElseGet(ImmutableList::of);
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean getSysConfigBooleanValue(final String name, final boolean defaultValue)
	{
		return sysConfigBL.getBooleanValue(name, defaultValue);
	}

	public boolean hasQuickInputEntityDescriptor(final DocumentEntityDescriptor.Builder entityDescriptor)
	{
		return quickInputDescriptors.hasQuickInputEntityDescriptor(
				entityDescriptor.getDocumentType(),
				entityDescriptor.getDocumentTypeId(),
				entityDescriptor.getTableName(),
				entityDescriptor.getDetailId(),
				entityDescriptor.getSOTrx()
		);
	}
}
