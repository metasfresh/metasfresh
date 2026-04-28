package de.metas.manufacturing.order.weighting.run.quickinput;

import com.google.common.collect.ImmutableSet;
import de.metas.i18n.TranslatableStrings;
import de.metas.lang.SOTrx;
import de.metas.ui.web.quickinput.IQuickInputDescriptorFactory;
import de.metas.ui.web.quickinput.QuickInputDescriptor;
import de.metas.ui.web.quickinput.QuickInputLayoutDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.NonNull;
import org.adempiere.ad.expression.api.ConstantLogicExpression;
import org.eevolution.model.I_PP_Order_Weighting_RunCheck;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class PPOrderWeightingCheckQuickInputDescriptorFactory implements IQuickInputDescriptorFactory
{
	@Override
	public Set<MatchingKey> getMatchingKeys()
	{
		return ImmutableSet.of(MatchingKey.ofTableName(I_PP_Order_Weighting_RunCheck.Table_Name));
	}

	@Override
	public QuickInputDescriptor createQuickInputDescriptor(
			final DocumentType documentType,
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		final DocumentEntityDescriptor entityDescriptor = createEntityDescriptor(documentTypeId, detailId, soTrx);
		final QuickInputLayoutDescriptor layout = createLayout(entityDescriptor);
		return QuickInputDescriptor.of(entityDescriptor, layout, PPOrderWeightingCheckQuickInputProcessor.class);
	}

	private DocumentEntityDescriptor createEntityDescriptor(
			final DocumentId documentTypeId,
			final DetailId detailId,
			@NonNull final Optional<SOTrx> soTrx)
	{
		return DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.QuickInput, documentTypeId)
				.disableDefaultTableCallouts()
				.setDetailId(detailId)
				.setTableName(I_PP_Order_Weighting_RunCheck.Table_Name)
				.addField(DocumentFieldDescriptor.builder(PPOrderWeightingCheckQuickInput.COLUMNNAME_Weight)
						.setCaption(TranslatableStrings.adElementOrMessage(PPOrderWeightingCheckQuickInput.COLUMNNAME_Weight))
						.setWidgetType(DocumentFieldWidgetType.Quantity)
						.setReadonlyLogic(ConstantLogicExpression.FALSE)
						.setAlwaysUpdateable(true)
						.setMandatoryLogic(ConstantLogicExpression.TRUE)
						.setDisplayLogic(ConstantLogicExpression.TRUE)
						.addCharacteristic(DocumentFieldDescriptor.Characteristic.PublicField))
				.setIsSOTrx(soTrx)
				.build();
	}

	private static QuickInputLayoutDescriptor createLayout(final DocumentEntityDescriptor entityDescriptor)
	{
		return QuickInputLayoutDescriptor.build(entityDescriptor, new String[][] {
				{ PPOrderWeightingCheckQuickInput.COLUMNNAME_Weight }
		});
	}
}
