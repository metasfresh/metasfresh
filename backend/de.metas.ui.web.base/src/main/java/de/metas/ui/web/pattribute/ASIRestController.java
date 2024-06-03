package de.metas.ui.web.pattribute;

import de.metas.lang.SOTrx;
import de.metas.product.ProductId;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.pattribute.json.JSONASIDocument;
import de.metas.ui.web.pattribute.json.JSONCompleteASIRequest;
import de.metas.ui.web.pattribute.json.JSONCreateASIRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Services;
import io.swagger.annotations.Api;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.util.ASIEditingInfo;
import org.adempiere.service.ISysConfigBL;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Function;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * @implNote IMPORTANT: Keep the API endpoints/requests/responses in sync with {@link de.metas.ui.web.address.AddressRestController} because on frontend side they are handled by the same code.
 */
@Api
@RestController
@RequestMapping(ASIRestController.ENDPOINT)
public class ASIRestController
{
	static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/pattribute";

	private static final ReasonSupplier REASON_ProcessASIDocumentChanges = () -> "process ASI document changes";

	private static final String SYSCONFIG_LookupAppendDescriptionToName = "webui.attributeValues.appendDescriptionToName";

	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final UserSession userSession;
	private final ASIRepository asiRepo;
	private final DocumentCollection documentsCollection;
	private final IViewsRepository viewsRepository;

	public ASIRestController(
			@NonNull final UserSession userSession,
			@NonNull final ASIRepository asiRepo,
			@NonNull final DocumentCollection documentsCollection,
			@NonNull final IViewsRepository viewsRepository)
	{
		this.userSession = userSession;
		this.asiRepo = asiRepo;
		this.documentsCollection = documentsCollection;
		this.viewsRepository = viewsRepository;
	}

	private JSONDocumentOptions newJsonDocumentOpts()
	{
		return JSONDocumentOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJsonDocumentLayoutOpts()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

	@PostMapping({ "", "/" })
	public JSONASIDocument createASIDocument(@RequestBody final JSONCreateASIRequest request)
	{
		userSession.assertLoggedIn();

		final WebuiASIEditingInfo info = createWebuiASIEditingInfo(request);
		return Execution.callInNewExecution(
				"createASI",
				() -> asiRepo.createNewFrom(info)
						.toJSONASIDocument(newJsonDocumentOpts(), newJsonDocumentLayoutOpts())
		);
	}

	private WebuiASIEditingInfo createWebuiASIEditingInfo(final JSONCreateASIRequest request)
	{
		final AttributeSetInstanceId attributeSetInstanceId = request.getTemplateId();
		final JSONDocumentPath source = request.getSource();

		if (source.isWindow())
		{
			return documentsCollection.forDocumentReadonly(
					source.toSingleDocumentPath(),
					contextDocument -> createWebuiASIEditingInfoForSingleDocument(contextDocument, attributeSetInstanceId));
		}
		else if (source.isView())
		{
			return createWebuiASIEditingInfoForViewRow(attributeSetInstanceId, source.getViewId(), source.getRowId());
		}
		else if (source.isProcess())
		{
			return WebuiASIEditingInfo.processParameterASI(attributeSetInstanceId, source.toSingleDocumentPath());
		}
		else
		{
			throw new AdempiereException("Cannot create ASI editing info from " + source);
		}
	}

	private static WebuiASIEditingInfo createWebuiASIEditingInfoForSingleDocument(
			final Document contextDocument,
			final AttributeSetInstanceId attributeSetInstanceId)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(contextDocument.asEvaluatee().get_ValueAsInt("M_Product_ID", -1));
		final SOTrx soTrx = SOTrx.ofBoolean(contextDocument.asEvaluatee().get_ValueAsBoolean("IsSOTrx", true));

		final String callerTableName = contextDocument.getEntityDescriptor().getTableNameOrNull();
		final int callerColumnId = -1; // FIXME implement
		final ASIEditingInfo info = ASIEditingInfo.of(productId, attributeSetInstanceId, callerTableName, callerColumnId, soTrx);

		return WebuiASIEditingInfo.builder(info)
				.contextDocumentPath(contextDocument.getDocumentPath())
				.build();
	}

	private WebuiASIEditingInfo createWebuiASIEditingInfoForViewRow(
			@NonNull final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final ViewId viewId,
			@NonNull final DocumentId rowId)
	{
		final IView view = viewsRepository.getView(viewId);
		final IViewRow row = view.getById(rowId);

		if (row instanceof WebuiASIEditingInfoAware)
		{
			final WebuiASIEditingInfoAware infoAware = (WebuiASIEditingInfoAware)row;
			return infoAware.getWebuiASIEditingInfo(attributeSetInstanceId);
		}
		else
		{
			throw new AdempiereException("In order to edit ASI, let the row implement " + WebuiASIEditingInfoAware.class);
		}
	}

	@GetMapping("/{asiDocId}")
	public JSONASIDocument getASIDocument(@PathVariable("asiDocId") final String asiDocIdStr)
	{
		userSession.assertLoggedIn();

		final DocumentId asiDocId = DocumentId.of(asiDocIdStr);
		return forASIDocumentReadonly(asiDocId, asiDoc -> asiDoc.toJSONASIDocument(newJsonDocumentOpts(), newJsonDocumentLayoutOpts()));
	}

	private <R> R forASIDocumentReadonly(@NonNull final DocumentId asiDocId, @NonNull final Function<ASIDocument, R> processor)
	{
		return asiRepo.forASIDocumentReadonly(asiDocId, documentsCollection, processor);
	}

	@PatchMapping("/{asiDocId}")
	public List<JSONDocument> processChanges(
			@PathVariable("asiDocId") final String asiDocIdStr,
			@RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final DocumentId asiDocId = DocumentId.of(asiDocIdStr);

		return Execution.callInNewExecution(
				"processChanges",
				() -> processASIDocumentChanges(asiDocId, events));
	}

	private List<JSONDocument> processASIDocumentChanges(
			@NonNull final DocumentId asiDocId,
			@NonNull final List<JSONDocumentChangedEvent> events)
	{
		final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull();
		asiRepo.forASIDocumentWritable(
				asiDocId,
				changesCollector,
				documentsCollection,
				asiDoc -> {
					asiDoc.processValueChanges(events, REASON_ProcessASIDocumentChanges);
					return null; // no response
				});

		return JSONDocument.ofEvents(changesCollector, newJsonDocumentOpts());
	}

	@GetMapping("/{asiDocId}/field/{attributeName}/typeahead")
	public JSONLookupValuesPage getAttributeTypeahead(
			@PathVariable("asiDocId") final String asiDocIdStr,
			@PathVariable("attributeName") final String attributeName,
			@RequestParam(name = "query") final String query)
	{
		userSession.assertLoggedIn();

		final DocumentId asiDocId = DocumentId.of(asiDocIdStr);
		return forASIDocumentReadonly(asiDocId, asiDoc -> asiDoc.getFieldLookupValuesForQuery(attributeName, query))
				.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language()));
	}

	private boolean isLookupsAppendDescriptionToName()
	{
		return sysConfigBL.getBooleanValue(SYSCONFIG_LookupAppendDescriptionToName, true);
	}

	private JSONLookupValuesList toJSONLookupValuesList(final LookupValuesList lookupValuesList)
	{
		return JSONLookupValuesList.ofLookupValuesList(lookupValuesList, userSession.getAD_Language(), isLookupsAppendDescriptionToName());
	}

	private JSONLookupValue toJSONLookupValue(final LookupValue lookupValue)
	{
		return JSONLookupValue.ofLookupValue(lookupValue, userSession.getAD_Language(), isLookupsAppendDescriptionToName());
	}

	@GetMapping("/{asiDocId}/field/{attributeName}/dropdown")
	public JSONLookupValuesList getAttributeDropdown(
			@PathVariable("asiDocId") final String asiDocIdStr,
			@PathVariable("attributeName") final String attributeName)
	{
		userSession.assertLoggedIn();

		final DocumentId asiDocId = DocumentId.of(asiDocIdStr);
		return forASIDocumentReadonly(asiDocId, asiDoc -> asiDoc.getFieldLookupValues(attributeName))
				.transform(this::toJSONLookupValuesList);
	}

	@PostMapping(value = "/{asiDocId}/complete")
	public JSONLookupValue complete(
			@PathVariable("asiDocId") final String asiDocIdStr,
			@RequestBody final JSONCompleteASIRequest request)
	{
		userSession.assertLoggedIn();

		final DocumentId asiDocId = DocumentId.of(asiDocIdStr);

		return Execution.callInNewExecution("complete", () -> completeInTrx(asiDocId, request))
				.transform(this::toJSONLookupValue);
	}

	private LookupValue completeInTrx(final DocumentId asiDocId, final JSONCompleteASIRequest request)
	{
		return asiRepo.forASIDocumentWritable(
				asiDocId,
				NullDocumentChangesCollector.instance,
				documentsCollection,
				asiDoc -> {
					final List<JSONDocumentChangedEvent> events = request.getEvents();
					if (events != null && !events.isEmpty())
					{
						asiDoc.processValueChanges(events, REASON_ProcessASIDocumentChanges);
					}
					return asiDoc.complete();
				});
	}
}
